package us.ilite.robot.modules;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.flybotix.hfr.util.log.ILog;
import com.flybotix.hfr.util.log.Logger;
import com.revrobotics.*;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import us.ilite.common.Data;
import us.ilite.common.config.Settings;
import us.ilite.common.lib.control.ProfileGains;
import us.ilite.common.types.EMatchMode;
import us.ilite.common.types.EPowerCellData;
import static us.ilite.common.types.EPowerCellData.*;

import us.ilite.robot.Robot;
import static us.ilite.robot.Enums.*;
import us.ilite.robot.hardware.DigitalBeamSensor;
import us.ilite.robot.hardware.HardwareUtils;
import us.ilite.robot.hardware.SparkMaxFactory;
import us.ilite.robot.hardware.TalonSRXFactory;

import java.util.Arrays;
import java.util.List;

public class PowerCellModule extends Module {

    //Change in drivetrain velocity to control intake speed
    public static double kDeltaIntakeVel = 0d;
    private Data db = Robot.DATA;


    // Serializer Motors
    private TalonSRX mConveyorMotorHorizontal;
    private TalonSRX mConveyorMotorVertical;

    //Arm
    private CANSparkMax mIntakePivot;
    private CANSparkMax mIntakeRoller;
    private CANPIDController mIntakePivotCtrl;
    private CANEncoder mIntakePivotEncoder;
    private DutyCycleEncoder mIntakePivotAbsoluteEncoder;
    private CANEncoder mIntakeRollerEncoder;
    private CANPIDController mIntakeRollerCtrl;

//    Beam Breakers
    private DigitalBeamSensor mEntryBeam;
    private DigitalBeamSensor mSecondaryBeam;
    private DigitalBeamSensor mExitBeam;

    private boolean allBeamsBroken;
    private int mEntryBeamNotBrokenCycles = 0;
    private int mSecondaryBeamNotBrokenCycles = 0;
    private int mExitBeamNotBrokenCycles = 0;

    //Constants
    public static double kIntakeTalonPower = 1d;
    public static double kForStopTalon = 0d;
    public static double kIntakePower = 1.0;
    public static double kForStop = 1.0;
    public static int kWarnCurrentLimitThreshold = 20;

    private static final int INTAKE_PIVOT_DOWN_SLOT = 1;
    private static final int INTAKE_PIVOT_UP_SLOT = 2;
    private static final int INTAKE_ROLLER_SLOT = 1;

    private static final double kIntakeRollerRatio = 12.0 / 30.0 * 22.0 / 36.0;
    private static final double kIntakeRollerDiameterFeet = 1.5 / 12.0;
    private static final double kIntakeRollerSpeedConversion = kIntakeRollerRatio * kIntakeRollerDiameterFeet * Math.PI / 60.0;
    // 100:1 AM Sport with 16:36 sprocket reduction
    // We may change this ratio to speed up the arm, so leave the math separated out like this
    private static final double kPivotGearRatio = 1.0/100.0 * 16.0 / 36.0;
    // 36T Driving sprocket on the pivot, 16T driven sprocket on the cross-axle
    private static final double kAbsoluteEncoderRatio = kPivotGearRatio * 36.0 / 16.0;
    // Rotations in degrees
    private static final double kPivotConversion = kPivotGearRatio * 360.0;
    // RPM to degrees / second
    private static final double kPivotVelocityConversion = kPivotConversion / 60.0;
    private static final double kPivotAngleConversionFactor = 0.0;
    private static final double kMaxIntakePivotVelocityDeg_s = 10.0;
    private static final ProfileGains mIntakePivotDownGains = new ProfileGains()
            .slot(INTAKE_PIVOT_DOWN_SLOT)
            .p(0.00025)
            .maxAccel(9000d)
            .maxVelocity(6000d)
            ;
    private static final ProfileGains mIntakeRollerGains = new ProfileGains()
            .slot(INTAKE_ROLLER_SLOT)
            .f(0.00015)
//            .p(0.0001)
            .maxAccel(9000d)
            .maxVelocity(11000d)
            ;

    // These are PRODUCTION BOT values
    // TODO - calbirate
    private static final double kPivotAbsoluteMin = -0.32;
    private static final double kPivotAbsoluteMax = 0.25;
    private static final ProfileGains mIntakePivotUpGains = mIntakePivotDownGains;

    //For indexing
    private int mGoalBeamCountBroken = 0;
    private int mBeamCountBroken = 0;
    //Array of beam-breakers (Used when indexing PowerCells)
    private DigitalBeamSensor[] mDigitalBeamSensors;

    private ILog mLog = Logger.createLog(this.getClass());

    public PowerCellModule() {
        mIntakeRoller = SparkMaxFactory.createDefaultSparkMax( Settings.Hardware.CAN.kMAXIntakeRollerId, CANSparkMaxLowLevel.MotorType.kBrushless );
        mIntakeRoller.setInverted(true);
        mIntakeRoller.setIdleMode(CANSparkMax.IdleMode.kCoast);
        mIntakeRoller.setSmartCurrentLimit(40);
        mIntakeRollerEncoder = mIntakeRoller.getEncoder();
        mIntakeRollerCtrl = mIntakeRoller.getPIDController();

        mConveyorMotorHorizontal = TalonSRXFactory.createDefaultTalon( Settings.Hardware.CAN.kTalonPowerCellSerializer);
        mConveyorMotorVertical = TalonSRXFactory.createDefaultTalon( Settings.Hardware.CAN.kTalonVerticalID );
        mConveyorMotorVertical.setInverted(true);

        mIntakePivot = SparkMaxFactory.createDefaultSparkMax( Settings.Hardware.CAN.kMAXIntakeArm, CANSparkMaxLowLevel.MotorType.kBrushless);
        mIntakePivot.setIdleMode(CANSparkMax.IdleMode.kCoast);
        mIntakePivot.setSmartCurrentLimit(40);

        double debounceTime_s = 0.1;
        mEntryBeam = new DigitalBeamSensor( Settings.Hardware.DIO.kEntryBeamChannel, debounceTime_s);
        mSecondaryBeam = new DigitalBeamSensor( Settings.Hardware.DIO.kSecondaryBeamChannel, debounceTime_s);
        mExitBeam = new DigitalBeamSensor( Settings.Hardware.DIO.kExitBeamChannel, debounceTime_s);
        mDigitalBeamSensors = new DigitalBeamSensor[]{mEntryBeam, mSecondaryBeam, mExitBeam};

        mIntakePivotEncoder = new CANEncoder(mIntakePivot);
        mIntakePivotAbsoluteEncoder = new DutyCycleEncoder(0);
        mIntakePivotCtrl = mIntakePivot.getPIDController();

        HardwareUtils.setGains(mIntakePivotCtrl, mIntakePivotDownGains);
        HardwareUtils.setGains(mIntakeRollerCtrl, mIntakeRollerGains);
        mIntakeRoller.burnFlash();
        mIntakePivot.burnFlash();
    }

    @Override
    public void modeInit(EMatchMode pMode, double pNow) {
        HardwareUtils.setGains(mIntakePivotCtrl, mIntakePivotUpGains);
//        HardwareUtils.setGains(mIntakePivotEncoder, mIntakePivotUpGains);
//        HardwareUtils.setGains(mIntakePivotCtrl, mIntakePivotDownGains);
//        HardwareUtils.setGains(mIntakePivotEncoder, mIntakePivotDownGains);
        HardwareUtils.setGains(mIntakeRollerCtrl, mIntakeRollerGains);
        mIntakePivotEncoder.setPosition(0.0);
        mIntakePivotCtrl.setOutputRange(0.0, 95.0);
        SmartDashboard.putNumber("Rotation Conversion (deg)", mIntakePivotDownGains.POSITION_CONVERSION_FACTOR);
        SmartDashboard.putNumber("Max Rotation Speed (deg/s)", kMaxIntakePivotVelocityDeg_s * kPivotVelocityConversion);
        mIntakeRollerEncoder.setPosition(0.0);
    }

    @Override
    public void readInputs(double pNow) {
//        mIntakeState = EIntakeState.values()[db.powercell.get(EPowerCellData.DESIRED_INTAKE_STATE).intValue()];
        Object[] brokenArray = Arrays.stream(mDigitalBeamSensors).map(e -> !e.isBroken()).toArray();

        db.powercell.set(CURRENT_AMOUNT_OF_SENSORS_BROKEN, List.of(mDigitalBeamSensors).stream().filter(e -> !e.isBroken()).count());
        db.powercell.set(INTAKE_ROLLER_CURRENT, mIntakeRoller.getOutputCurrent());
        db.powercell.set(INTAKE_VEL_ft_s, mIntakeRollerEncoder.getVelocity() * kIntakeRollerSpeedConversion);
        db.powercell.set(ARM_ANGLE_deg, mIntakePivotEncoder.getPosition() * kPivotConversion);
        db.powercell.set(SERIALIZER_CURRENT, mConveyorMotorHorizontal.getStatorCurrent());
        db.powercell.set(VERTICAL_CURRENT, mConveyorMotorVertical.getStatorCurrent());
        db.powercell.set(INTAKE_PIVOT_CURRENT, mIntakePivot.getOutputCurrent());

        db.powercell.set(ENTRY_BEAM, mEntryBeam.isBroken());
        db.powercell.set(H_BEAM, mSecondaryBeam.isBroken());
        db.powercell.set(EXIT_BEAM, mExitBeam.isBroken());
        //TODO Determine Indexer State
    }

    @Override
    public void setOutputs(double pNow) {
        mConveyorMotorHorizontal.set(ControlMode.PercentOutput, db.powercell.get(SET_H_pct));
        mConveyorMotorVertical.set(ControlMode.PercentOutput, db.powercell.get(SET_V_pct));
        setPivotArm();
    }

    private void setPivotArm() {
//        kPivotAbsoluteMin
        if(db.powercell.isSet(INTAKE_STATE)) {
//            mIntakeRoller.set(db.powercell.get(SET_INTAKE_VEL_ft_s) / 18.0); // 18ft/s theoretical max
            double rpm = db.powercell.get(SET_INTAKE_VEL_ft_s) / kIntakeRollerSpeedConversion;
            SmartDashboard.putNumber("Target Intake Roller RPM", rpm);
            mIntakeRollerCtrl.setReference(rpm, ControlType.kSmartVelocity, INTAKE_ROLLER_SLOT);
//            mIntakeRoller.set(0.6);
            EArmState state = db.powercell.get(INTAKE_STATE, EArmState.class);
            switch(state) {
                case OUT:
                case STOW:
                    mIntakePivotCtrl.setReference(state.angle / kPivotConversion, ControlType.kSmartMotion, INTAKE_PIVOT_DOWN_SLOT, 0.01);
                    break;
                default:
                    mIntakePivot.set(0.0);
            }
        } else {
            mIntakePivot.set(0.0);
        }
    }

    @Override
    public void shutdown(double pNow) {
//        mIntakeState = EIntakeState.STOP;
//        mArmState = EArmState.DISENGAGED;
//        mIndexingState = EIndexingState.NOT_INDEXING;
    }

    public void startIndexing() {
        //TODO determine V_Motor and H_Motor specifics with Beam breaker
        mBeamCountBroken = (int) List.of(mDigitalBeamSensors).stream().filter(e -> !e.isBroken()).count();

        SmartDashboard.putNumber("BeamCountBroken" , mBeamCountBroken);
        SmartDashboard.putNumber("BeamCountBrokenGoal" , mGoalBeamCountBroken);


//        for (DigitalBeamSensor mDigitalBeamSensor : mDigitalBeamSensors) {
//            if (mDigitalBeamSensor.isBroken()) mBeamCountBroken++;
//        }
        if ( mBeamCountBroken < mGoalBeamCountBroken) {
            mConveyorMotorHorizontal.set( ControlMode.PercentOutput, db.powercell.get(EPowerCellData.SET_H_pct) );
        } else {
            mConveyorMotorHorizontal.set( ControlMode.PercentOutput, 0.0 );
        }

        mGoalBeamCountBroken = mBeamCountBroken + 1;
    }
}