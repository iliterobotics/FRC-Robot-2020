package us.ilite.robot.modules;

import com.revrobotics.*;
import us.ilite.common.Data;
import us.ilite.common.config.Settings;
import us.ilite.common.lib.control.PIDController;
import us.ilite.common.lib.control.ProfileGains;
import us.ilite.common.types.EHangerModuleData;
import us.ilite.common.types.EMatchMode;
import us.ilite.common.types.sensor.EPowerDistPanel;
import us.ilite.robot.Robot;
import us.ilite.robot.hardware.HardwareUtils;
import us.ilite.robot.hardware.SparkMaxFactory;

public class HangerModule extends Module {

    private CANSparkMax mHangerNeoMaster;
    private CANSparkMax mHangerNeoFollower;
    protected EHangerState mHangerState;
    private CANPIDController mHangerPIDMaster;
    private CANPIDController mHangerPIDFollower;

    private CANEncoder mHangerEncoderOne;
    private CANEncoder mHangerEncoderTwo;

    public static double kMaxRPM = 1000.0;
    private static final int VELOCITY_PID_SLOT = 0;
    private static ProfileGains mPrimaryVelocityPidGains = new ProfileGains()
            .p(0.01);
    private PIDController mPrimaryVelocityPidOne = new PIDController(mPrimaryVelocityPidGains, -1000, 1000, Settings.kControlLoopPeriod);
    private PIDController mPrimaryVelocityPidTwo = new PIDController(mPrimaryVelocityPidGains, -1000, 1000, Settings.kControlLoopPeriod);
    public static ProfileGains kHangerVelocityGains = new ProfileGains()
            .f(0.00015)
            .p(0.0001)
            // Enforce a maximum allowed speed, system-wide. DO NOT undo kMaxAllowedVelocityMultiplier without checking with a mentor first.
            .maxVelocity(kMaxRPM)
            .maxAccel(kMaxRPM*1.5)
            .slot(VELOCITY_PID_SLOT);

    private int kHangerWarnCurrentLimitThreshold = 60;

    public HangerModule(){

        mPrimaryVelocityPidOne.setOutputRange(-1, 1);
        mPrimaryVelocityPidTwo.setOutputRange(-1, 1);

        mHangerNeoMaster = SparkMaxFactory.createDefaultSparkMax(Settings.Hardware.CAN.kHangerNeoID1 ,
                CANSparkMaxLowLevel.MotorType.kBrushless);

        mHangerNeoFollower = SparkMaxFactory.createDefaultSparkMax(Settings.Hardware.CAN.kHangerNeoID2 , CANSparkMaxLowLevel.MotorType.kBrushless);


        mHangerPIDMaster = new CANPIDController(mHangerNeoMaster);
        mHangerPIDFollower = new CANPIDController(mHangerNeoFollower);
        HardwareUtils.setGains(mHangerPIDMaster, kHangerVelocityGains);
        HardwareUtils.setGains(mHangerPIDFollower, kHangerVelocityGains);

        mHangerNeoMaster.setIdleMode(CANSparkMax.IdleMode.kBrake);
        mHangerNeoFollower.setInverted(false);
        mHangerNeoMaster.setInverted(true);


        mHangerNeoMaster.burnFlash();
        mHangerNeoFollower.burnFlash();

        mHangerEncoderOne = mHangerNeoMaster.getEncoder();
        mHangerEncoderTwo = mHangerNeoFollower.getEncoder();

        mHangerNeoMaster.setSmartCurrentLimit(kHangerWarnCurrentLimitThreshold);
        mHangerNeoFollower.setSmartCurrentLimit(kHangerWarnCurrentLimitThreshold);

        zeroTheEncoders();
    }
    public enum EHangerState {
        HANGING(1.0),
        BRAKE (0.0),
        REVERSE(-1.0);

        private double position;
        EHangerState(double position){
            this.position = position;
        }
        private double getPower(){
            return this.position;
        }

    }

    @Override
    public void readInputs(double pNow) {
        db.hanger.set(EHangerModuleData.OUTPUT_CURRENT , mHangerNeoMaster.getOutputCurrent());
    }

    @Override
    public void modeInit(EMatchMode pMode, double pNow){


    }

    @Override
    public void setOutputs(double pNow) {
//        mHangerNeoFollower.set(Robot.DATA.hanger.get(EHangerModuleData.DESIRED_PCT));
//        mHangerNeoMaster.set(Robot.DATA.hanger.get(EHangerModuleData.DESIRED_PCT));

        mPrimaryVelocityPidOne.setSetpoint(0.0);
        mPrimaryVelocityPidTwo.setSetpoint(0.0);

        double highest = Math.max(mHangerEncoderOne.getPosition(), mHangerEncoderTwo.getPosition());
        double errorOne = highest - mHangerEncoderOne.getPosition();
        double errorTwo = highest - mHangerEncoderTwo.getPosition();

        double desiredPctOne = mPrimaryVelocityPidOne.calculate(errorOne, pNow);
        double desiredPctTwo = mPrimaryVelocityPidTwo.calculate(errorTwo, pNow);

        double desiredDirection = Math.signum(db.hanger.safeGet(EHangerModuleData.DESIRED_PCT));
        if (db.hanger.isSet(EHangerModuleData.DESIRED_PCT)) {
            mHangerPIDMaster.setReference(desiredDirection * desiredPctOne * kMaxRPM, ControlType.kVelocity, VELOCITY_PID_SLOT, 0);
            mHangerPIDFollower.setReference(desiredDirection * desiredPctTwo * kMaxRPM, ControlType.kVelocity, VELOCITY_PID_SLOT, 0);
        }
    }

    public EHangerState returnHangerState() {
        return this.mHangerState;
    }
    public void putDesiredHangerState(EHangerState desiredState){
        mHangerState = desiredState;
    }
    public boolean isCurrentLimiting(){
        return mHangerNeoMaster.getOutputCurrent() >= kHangerWarnCurrentLimitThreshold ||
                mHangerNeoFollower.getOutputCurrent() >= kHangerWarnCurrentLimitThreshold;
    }

    public void zeroTheEncoders(){
        mHangerEncoderOne.setPosition(0);
        mHangerEncoderTwo.setPosition(0);
    }

}
