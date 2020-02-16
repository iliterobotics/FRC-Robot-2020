package us.ilite.robot.modules;

import com.revrobotics.*;
import us.ilite.common.config.Settings;
import us.ilite.common.types.EHangerModuleData;
import us.ilite.common.types.EMatchMode;
import us.ilite.robot.hardware.SparkMaxFactory;

import java.util.Optional;

public class HangerModule extends Module {

    private Optional<CANSparkMax> mHangerNeoOne;
    //TODO create sparkmax to follow to first
//    private CANSparkMax mHangerNeoTwo;

    private EHangerState mHangerState;
    private Optional<CANPIDController> mHangerPID;

    private Optional<CANEncoder> mHangerEncoderOne;
//    private CANEncoder mHangerEncoderTwo; O

    //PID Constants, to be used if needed
    private static final int UP_PID_SLOT_ID = 1;
    public static double P = 5.0e-4;
    public static double I = 0.0;
    public static double D = 0.0;
    public static double F = 0.000391419;
    public static double kMaxElevatorVelocity = 3700;
    public static double kMinElevatorVelocity = 0;
    public static double kMaxElevatorUpAcceleration = 4000 * 1.5;
    public static double kMaxElevatorDownAcceleration = 4000 * 1.5;

    private  double kHangerWarnCurrentLimitThreshold = 30;

    public HangerModule(){

        mHangerNeoOne = SparkMaxFactory.createDefaultSparkMax("HangerModule::hangerneoone",Settings.Hardware.CAN.kHangerNeoID1
        );

        CANPIDController hangerPID = null;
        CANEncoder hangerEncoder = null;
        if(mHangerNeoOne.isPresent()) {
            mHangerNeoOne.get().setInverted(true);
            hangerPID = new CANPIDController(mHangerNeoOne.get());
            hangerPID.setP(P, UP_PID_SLOT_ID);
            hangerPID.setI(I, UP_PID_SLOT_ID);
            hangerPID.setD(D, UP_PID_SLOT_ID);
            hangerPID.setSmartMotionMaxAccel(kMaxElevatorUpAcceleration, UP_PID_SLOT_ID);
            hangerPID.setSmartMotionMaxVelocity(kMaxElevatorVelocity, UP_PID_SLOT_ID);

            mHangerNeoOne.get().setIdleMode(CANSparkMax.IdleMode.kCoast);
            mHangerNeoOne.get().burnFlash();
            hangerEncoder = mHangerNeoOne.get().getEncoder();
        }

        mHangerPID = Optional.ofNullable(hangerPID);
        mHangerEncoderOne = Optional.ofNullable(hangerEncoder);

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
//        Robot.DATA.hanger.set(EHangerModuleData.DESIRED_HANGER_POWER1 , (double) returnHangerState().ordinal() );
//        Robot.DATA.hanger.set(EHangerModuleData.DESIRED_HANGER_POWER2 , (double) returnHangerState().ordinal() );

        if(mHangerEncoderOne.isPresent() && mHangerEncoderOne.isPresent() && mHangerNeoOne.isPresent()) {
            db.hanger.set(EHangerModuleData.CURRENT_HANGER_VELOCITY, mHangerEncoderOne.get().getVelocity());
            db.hanger.set(EHangerModuleData.CURRENT_POSITION, mHangerEncoderOne.get().getPosition());
            db.hanger.set(EHangerModuleData.OUTPUT_CURRENT, mHangerNeoOne.get().getOutputCurrent());
        }
    }

    @Override
    public void modeInit(EMatchMode pMode, double pNow){


    }

    @Override
    public void setOutputs(double pNow) {

        if(mHangerPID.isPresent()) {
            mHangerPID.get().setReference(db.hanger.get(EHangerModuleData.DESIRED_POSITION), ControlType.kSmartMotion, UP_PID_SLOT_ID);
        }
//        mHangerNeoOne.set(Robot.DATA.hanger.get(EHangerModuleData.CURRENT_HANGER_VELOCITY));

    }



    public EHangerState returnHangerState() {
        return this.mHangerState;
    }
    public void putDesiredHangerState(EHangerState desiredState){
        mHangerState = desiredState;
    }
    public boolean isCurrentLimiting(){
        boolean isCurrentLimiting = true; //TODO: Need to decide what the default should be. I would think true to prevent
        //orperations from working
        if(mHangerNeoOne.isPresent()) {
            isCurrentLimiting = mHangerNeoOne.get().getOutputCurrent() >= kHangerWarnCurrentLimitThreshold;
        }
        return isCurrentLimiting;
    }

    public void zeroTheEncoders(){
        if(mHangerNeoOne.isPresent()) {
            mHangerEncoderOne.get().setPosition(0);
        }
    }

//    public boolean isAtPosition() {
//        return mCurrentState == EElevatorState.SET_POSITION && (Math.abs(pPosition.getEncoderRotations() - mData.elevator.get(EElevator.CURRENT_ENCODER_TICKS)) <= SystemSettings.kElevatorAllowableError);
//    }

}
