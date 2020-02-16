package us.ilite.robot.modules;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.Servo;
import us.ilite.common.Angle;
import us.ilite.common.Distance;
import us.ilite.common.config.Settings;
import us.ilite.common.lib.control.PIDController;
import us.ilite.common.lib.control.ProfileGains;
import us.ilite.common.types.EPowerCellData;
import us.ilite.common.types.EShooterSystemData;
import us.ilite.common.types.EMatchMode;
import us.ilite.common.types.input.ELogitech310;
import us.ilite.robot.Robot;
import us.ilite.robot.hardware.SparkMaxFactory;

import java.util.Optional;


public class FlywheelModule extends Module {
    public static int kMaxTalonVelocity = 1500; // probably needs readjustment
    public static double kMaxNeoVelocity = 7500; // probably needs readjustments
    public static final ProfileGains kTurretAngleLockGains = new ProfileGains().p(0.0005);
    public static final ProfileGains kShooterGains = new ProfileGains().p(0.0005);

    public static final double kShooterTargetVelocity = 1885;
    public static final double kAcceleratorTargetVelocity = 0.5;
    public static final double kAcceleratorThreshold = kShooterTargetVelocity / kMaxNeoVelocity;
    private PigeonIMU mTurretGyro;
    private PIDController mTurretPid;
    private PIDController mShooterPid;

    private FlywheelModule.EShooterState mShooterState = FlywheelModule.EShooterState.STOP; // FINISHED FOR NOW ( CHANGE TO TALONFX )
    private FlywheelModule.EAcceleratorState mAcceleratorState = FlywheelModule.EAcceleratorState.STOP; // FINISHED FOR NOW ( CHANGE TO CANSPARKMAX )
    private FlywheelModule.ETurretMode mTurretMode = FlywheelModule.ETurretMode.GYRO; // TEST IT WHEN LIMELIGHT MOVED AND GYRO IMPLEMENTED
    private FlywheelModule.EHoodState mHoodState = FlywheelModule.EHoodState.STATIONARY; // TEST WHEN LIMELIGHT REMOUNTED

    public static final double kBaseHoodAngle = 60;
    public Optional<CANSparkMax> mShooter;
    private Servo mHoodAngler;
    private TalonSRX mTurret;
    private TalonSRX mAccelerator;

    public FlywheelModule() {
        mShooter = SparkMaxFactory.createDefaultSparkMax("FlywheelModule::shooter",Settings.Hardware.CAN.kShooterID, CANSparkMaxLowLevel.MotorType.kBrushless);
        mAccelerator = new TalonSRX(Settings.Hardware.CAN.kAcceleratorID);
        mHoodAngler = new Servo(Settings.Hardware.DIO.kAnglerID);
        mTurret = new TalonSRX(Settings.Hardware.CAN.kTurretID);
        mTurretGyro = new PigeonIMU(Settings.Hardware.CAN.kTurretGyroID);
//        mTurretPid = new PIDController(kTurretAngleLockGains, 0, 1, SeleLockGains, -1, 1, Settings.kControlLoopPeriod);
//        mShooterPid = new PIDController(kShooterGains);
    }

    public enum EShooterState {
        SHOOT,
        STOP
    }

    public enum ETurretMode {
        GYRO,
        LIMELIGHT
    }

    public enum EAcceleratorState {
        FEED,
        STOP
    }

    public enum EHoodState {
        STATIONARY,
        ADJUSTABLE
    }

    public double calcSpeedFromDistance(Distance distance) {
        return 0.09 * Math.pow(distance.inches(), 2) + 8.0;
    } // Need recalculation

    public Angle calcAngleFromDistance(Distance distance, Distance height) {
        return Angle.fromDegrees(Math.atan(height.inches() / distance.inches()));
    }

    public boolean isMaxVelocity() {
        boolean isMaxVelocity = true; //Need to decide what the default should be.

        if(mShooter.isPresent()) {
            isMaxVelocity = mShooter.get().getEncoder().getVelocity() >= kAcceleratorThreshold;
        }

        return isMaxVelocity;
    }

    @Override
    public void modeInit(EMatchMode pMode, double pNow) {

    }

    @Override
    public void readInputs(double pNow) {
        Robot.DATA.flywheel.set(EShooterSystemData.TARGET_FLYWHEEL_VELOCITY, 0.0);

        if(mShooter.isPresent()) {
            Robot.DATA.flywheel.set(EShooterSystemData.CURRENT_FLYWHEEL_VELOCITY, mShooter.get().getEncoder().getVelocity());
        }

        //TODO - move these lines to the controller
//        Robot.DATA.flywheel.set(EShooterSystemData.TARGET_LIMELIGHT_TARGET, (double) mTrackingType.ordinal());
//        if (Robot.DATA.limelight.isSet(ETargetingData.targetOrdinal)) {
//            Robot.DATA.flywheel.set(EShooterSystemData.CURRENT_LIMELIGHT_TARGET, Robot.DATA.limelight.get(ETargetingData.targetOrdinal));
//        }

        Robot.DATA.flywheel.set(EShooterSystemData.TARGET_TURRET_VELOCITY, 0.0);
        Robot.DATA.flywheel.set(EShooterSystemData.CURRENT_TURRET_VELOCITY, (double) mTurret.getSelectedSensorVelocity());

        Robot.DATA.flywheel.set(EShooterSystemData.TARGET_HOOD_ANGLE, kBaseHoodAngle);
        Robot.DATA.flywheel.set(EShooterSystemData.CURRENT_HOOD_ANGLE, mHoodAngler.getAngle());

        Robot.DATA.flywheel.set(EShooterSystemData.TARGET_ACCELERATOR_VELOCITY, 0.0);
        Robot.DATA.flywheel.set(EShooterSystemData.CURRENT_ACCELERATOR_VELOCITY, (double)mAccelerator.getSelectedSensorVelocity());

        Robot.DATA.flywheel.set(EShooterSystemData.TARGET_TURRET_MODE, (double) mTurretMode.ordinal());
        Robot.DATA.flywheel.set(EShooterSystemData.CURRENT_TURRET_MODE, Robot.DATA.flywheel.get(EShooterSystemData.TARGET_TURRET_MODE));



    }

    @Override
    public void setOutputs(double pNow) {
        if ( isMaxVelocity() ) {
            mAccelerator.set(ControlMode.PercentOutput, Robot.DATA.flywheel.get(EShooterSystemData.TARGET_ACCELERATOR_VELOCITY));
        }
        else {
            mAccelerator.set(ControlMode.PercentOutput, 0.0);
        }
        mHoodAngler.setAngle(Robot.DATA.flywheel.get(EShooterSystemData.TARGET_HOOD_ANGLE));

        if(mShooter.isPresent()) {
            mShooter.get().set(Robot.DATA.flywheel.get(EShooterSystemData.TARGET_FLYWHEEL_VELOCITY));
        }
        mTurret.set(ControlMode.Velocity, Robot.DATA.flywheel.get(EShooterSystemData.TARGET_TURRET_VELOCITY));
    }

    @Override
    public void shutdown(double pNow) {

    }
}