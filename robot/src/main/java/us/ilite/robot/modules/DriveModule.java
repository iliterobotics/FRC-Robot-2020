package us.ilite.robot.modules;

import com.flybotix.hfr.util.log.ILog;
import com.flybotix.hfr.util.log.Logger;
import com.revrobotics.*;

import static com.revrobotics.ControlType.*;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import us.ilite.common.Distance;
import us.ilite.common.config.Settings;
import us.ilite.common.lib.control.PIDController;
import us.ilite.common.lib.control.ProfileGains;
import us.ilite.common.lib.util.Units;
import us.ilite.common.types.EMatchMode;

import static us.ilite.common.types.drive.EDriveData.*;

import us.ilite.common.types.sensor.EGyro;
import us.ilite.common.types.sensor.EPowerDistPanel;

import static us.ilite.common.types.sensor.EPowerDistPanel.*;

import us.ilite.robot.Robot;

import static us.ilite.robot.Enums.*;

import us.ilite.robot.hardware.*;

import java.util.Optional;

/**
 * Class for running all drivetrain train control operations from both autonomous and
 * driver-control.
 * TODO Support for rotation trajectories
 * TODO Turn-to-heading with Motion Magic
 */
public class DriveModule extends Module {
    private final ILog mLogger = Logger.createLog(DriveModule.class);
    // DO NOT CHANGE THE GEAR RATIO
    public static double kGearboxRatio = (10.0 / 40.0) * (14.0 / 40.0);
    // As of 2/9this is for omni wheels on a solid floor
    //public static double kWheelDiameterInches = 6.125;
    public static double kWheelDiameterInches = 5.875;
    public static double kWheelCircumferenceFeet = kWheelDiameterInches * Math.PI / 12.0;
    // For position, getPosition() returns raw rotations - so convert that to feet
    public static double kDriveNEOPositionFactor = kGearboxRatio * kWheelCircumferenceFeet;
    public static double kDriveNEOVelocityFactor = kDriveNEOPositionFactor / 60.0;

    // Actual measured was 5514 with a resting battery voltage of 12.75V
    public static double kDriveTrainMaxVelocityRPM = 5500.0;
    public static Distance kDriveMaxVelocity_measured = Distance.fromFeet(kDriveTrainMaxVelocityRPM * kDriveNEOVelocityFactor);
    //	public static Distance kDriveMaxAccel_measured = Distance.fromFeet()
    public static Distance kDriveMaxAccel_simulated = Distance.fromFeet(28.5);

    // This is approx 290 Degrees per second, measured with a Pigeon
    public static double kMaxDegreesPerSecond = Units.radians_to_degrees(5);
    // This is with the ADIS16470 IMU
    public static Rotation2d kDriveMaxOmega_measured = Rotation2d.fromDegrees(19.1);

    public static double kEffectiveWheelbase = 23.25;
    public static double kCurvatureCircumference = kEffectiveWheelbase * Math.PI;
    public static double kInchesPerCurvatureDegree = kCurvatureCircumference / 360.0;
    public static double kWheelTurnsPerCurvatureDegree = kInchesPerCurvatureDegree / kWheelDiameterInches;


    public static double kClosedLoopVoltageRampRate = 0.1;
    public static double kDefaultRampRate = 120.0; // in V/sec
    public static double kOpenLoopVoltageRampRate = 0.1;
    public static int kCurrentLimitAmps = 50;
    public static int kCurrentLimitTriggerDurationMs = 100;
    // =============================================================================
    // Closed-Loop Velocity Constants
    // =============================================================================
    private static final int VELOCITY_PID_SLOT = 1;
    private static final int POSITION_PID_SLOT = 2;
    public static ProfileGains dPID = new ProfileGains()
            .p(1.0).maxVelocity(kDriveTrainMaxVelocityRPM * Settings.Input.kMaxAllowedVelocityMultiplier)
            .maxAccel(56760d)
            .slot(POSITION_PID_SLOT)
            .velocityConversion(kDriveNEOPositionFactor);
    public static ProfileGains vPID = new ProfileGains()
            .f(0.00015)
            .p(0.0001)
            // Enforce a maximum allowed speed, system-wide. DO NOT undo kMaxAllowedVelocityMultiplier without checking with a mentor first.
            .maxVelocity(kDriveTrainMaxVelocityRPM * Settings.Input.kMaxAllowedVelocityMultiplier)
            // Divide by the simulated blue nitrile CoF 1.2, multiply by omni (on school floor) theoretical of 0.4
            .maxAccel(kDriveMaxAccel_simulated.feet() / kDriveNEOVelocityFactor / 1.2 * 0.8)
            .slot(VELOCITY_PID_SLOT)
            .velocityConversion(kDriveNEOVelocityFactor);
    public static ProfileGains kTurnToProfileGains = new ProfileGains().f(0.085);
    public static double kTurnSensitivity = 0.85;

    // =============================================================================
    // Heading Gains
    // =============================================================================
    public static ProfileGains kDriveHeadingGains = new ProfileGains().p(0.03);
    public static ProfileGains kYawGains = new ProfileGains().f(.15);
    public IMU mGyro;

    // =============================================================================
    // Hold Gains
    // =============================================================================
    public static ProfileGains kHoldPositionGains = new ProfileGains().p(.001);//.d(.00807);

    public static EPowerDistPanel[] kPdpSlots = new EPowerDistPanel[]{
            /* Left */
            CURRENT1,
            CURRENT2,

            /* Right */
            CURRENT13,
            CURRENT14,

    };

    private Rotation2d mGyroOffset = new Rotation2d();
    private PIDController mTargetAngleLockPid;
    private PIDController mYawPid;
    private PIDController mHoldLeftPositionPid;
    private PIDController mHoldRightPositionPid;
    private boolean mStartHoldingPosition;

    private final Optional<CANSparkMax> mLeftMaster;
    private final Optional<CANSparkMax> mLeftFollower;
    private final Optional<CANSparkMax> mRightMaster;
    private final Optional<CANSparkMax> mRightFollower;
    private final Optional<CANEncoder> mLeftEncoder;
    private final Optional<CANEncoder> mRightEncoder;
    private final Optional<CANPIDController> mLeftCtrl;
    private final Optional<CANPIDController> mRightCtrl;

    private static final SparkMaxFactory.Configuration kDriveConfig = new SparkMaxFactory.Configuration();

    static {
        kDriveConfig.IDLE_MODE = CANSparkMax.IdleMode.kCoast;
    }

    public DriveModule() {
        mLeftMaster = SparkMaxFactory.createSparkMax(Settings.Hardware.CAN.kDriveLeftMaster, kDriveConfig);
        mLeftFollower = SparkMaxFactory.createSparkMax(Settings.Hardware.CAN.kDriveLeftFollower, kDriveConfig);

        CANEncoder leftEncoder = null;
        CANPIDController leftCtrl = null;
        if (mLeftMaster.isPresent() && mLeftFollower.isPresent()) {
            mLeftFollower.get().follow(mLeftMaster.get());
            leftEncoder = new CANEncoder(mLeftMaster.get());
            leftCtrl = mLeftMaster.get().getPIDController();
            leftCtrl.setOutputRange(-kDriveTrainMaxVelocityRPM, kDriveTrainMaxVelocityRPM);
        }
        mLeftEncoder = Optional.ofNullable(leftEncoder);
        mLeftCtrl = Optional.ofNullable(leftCtrl);

        mRightMaster = SparkMaxFactory.createSparkMax(Settings.Hardware.CAN.kDriveRightMaster, kDriveConfig);
        mRightFollower = SparkMaxFactory.createSparkMax(Settings.Hardware.CAN.kDriveRightFollower, kDriveConfig);

        CANEncoder rightEncoder = null;
        CANPIDController rightCtrl = null;
        if (mRightMaster.isPresent() && mRightFollower.isPresent()) {
            mRightFollower.get().follow(mRightMaster.get());
            rightEncoder = new CANEncoder(mRightMaster.get());
            rightCtrl = mRightMaster.get().getPIDController();
            rightCtrl.setOutputRange(-kDriveTrainMaxVelocityRPM, kDriveTrainMaxVelocityRPM);
            mRightMaster.get().setInverted(true);
            mRightFollower.get().setInverted(true);
        }
        mRightCtrl = Optional.ofNullable(rightCtrl);
        mRightEncoder = Optional.ofNullable(rightEncoder);

        mGyro = new Pigeon(Settings.Hardware.CAN.kPigeon);

//		mGyro = new ADIS16470();

        if (mLeftCtrl.isPresent() && mRightCtrl.isPresent()) {
            HardwareUtils.setGains(mLeftCtrl.get(), vPID);
            HardwareUtils.setGains(mRightCtrl.get(), vPID);
            HardwareUtils.setGains(mLeftCtrl.get(), dPID);
            HardwareUtils.setGains(mRightCtrl.get(), dPID);
        }

        //TODO - we want to do use our conversion factor calculated above, but that requires re-turning of F & P
        if(mLeftEncoder.isPresent() && mRightEncoder.isPresent()) {
			mLeftEncoder.get().setPositionConversionFactor(1d);
			mLeftEncoder.get().setVelocityConversionFactor(1d);
			mRightEncoder.get().setPositionConversionFactor(1d);
			mRightEncoder.get().setPositionConversionFactor(1d);
		}

        if(mLeftMaster.isPresent() && mLeftFollower.isPresent()) {
			mLeftMaster.get().burnFlash();
			mLeftFollower.get().burnFlash();
		}

        if(mRightMaster.isPresent() && mRightFollower.isPresent()) {
			mRightMaster.get().burnFlash();
			mRightFollower.get().burnFlash();
		}

    }

    @Override
    public void modeInit(EMatchMode pMode, double pNow) {
//		mTargetAngleLockPid = new PIDController(Settings.kTargetAngleLockGains, Settings.kTargetAngleLockMinInput, Settings.kTargetAngleLockMaxInput, Settings.kControlLoopPeriod);
//		mTargetAngleLockPid.setOutputRange(Settings.kTargetAngleLockMinPower, Settings.kTargetAngleLockMaxPower);
//		mTargetAngleLockPid.setSetpoint(0);
//		mTargetAngleLockPid.reset();

        mYawPid = new PIDController(kYawGains,
                -kMaxDegreesPerSecond,
                kMaxDegreesPerSecond,
                Settings.kControlLoopPeriod);
        mYawPid.setOutputRange(-1, 1);

        mHoldLeftPositionPid = new PIDController(kHoldPositionGains, -99999, 99999, Settings.kControlLoopPeriod);
        mHoldLeftPositionPid.setOutputRange(-1, 1);
        mHoldLeftPositionPid.setSetpoint(0.0);
        mHoldRightPositionPid = new PIDController(kHoldPositionGains, -99999, 99999, Settings.kControlLoopPeriod);
        mHoldRightPositionPid.setOutputRange(-1, 1);
        mHoldRightPositionPid.setSetpoint(0.0);
        mStartHoldingPosition = false;

        reset();

        if(mLeftCtrl.isPresent() && mRightCtrl.isPresent()) {
			HardwareUtils.setGains(mLeftCtrl.get(), vPID);
			HardwareUtils.setGains(mRightCtrl.get(), vPID);
			HardwareUtils.setGains(mLeftCtrl.get(), dPID);
			HardwareUtils.setGains(mRightCtrl.get(), dPID);
		}

        System.err.println(" ==== DRIVE MAX ACCEL (RPM): " + (kDriveMaxAccel_simulated.feet() / kDriveNEOVelocityFactor / 1.2 * 0.4));
    }

    @Override
    public void readInputs(double pNow) {
        mGyro.update(pNow);

        if(mLeftEncoder.isPresent() && mRightEncoder.isPresent()) {
			db.drivetrain.set(L_ACTUAL_POS_FT, mLeftEncoder.get().getPosition() * kDriveNEOPositionFactor);
			db.drivetrain.set(L_ACTUAL_VEL_FT_s, mLeftEncoder.get().getVelocity() * kDriveNEOVelocityFactor);
			db.drivetrain.set(R_ACTUAL_POS_FT, mRightEncoder.get().getPosition() * kDriveNEOPositionFactor);
			db.drivetrain.set(R_ACTUAL_VEL_FT_s, mRightEncoder.get().getVelocity() * kDriveNEOVelocityFactor);
		}

        if(mLeftMaster.isPresent() && mRightMaster.isPresent()) {
			db.drivetrain.set(LEFT_CURRENT, mLeftMaster.get().getOutputCurrent());
			db.drivetrain.set(RIGHT_CURRENT, mRightMaster.get().getOutputCurrent());
			db.drivetrain.set(LEFT_VOLTAGE, mLeftMaster.get().getVoltageCompensationNominalVoltage());
			db.drivetrain.set(RIGHT_VOLTAGE, mRightMaster.get().getVoltageCompensationNominalVoltage());
		}
        db.drivetrain.set(IS_CURRENT_LIMITING, EPowerDistPanel.isAboveCurrentThreshold(kCurrentLimitAmps, Robot.DATA.pdp, kPdpSlots));
        db.imu.set(EGyro.HEADING_DEGREES, -mGyro.getHeading().getDegrees());
        db.imu.set(EGyro.YAW_OMEGA_DEGREES, mGyro.getYawRate().getDegrees());

    }

    @Override
    public void setOutputs(double pNow) {
        EDriveState mode = db.drivetrain.get(STATE, EDriveState.class);
        // Do this to prevent wonkiness while transitioning autonomous to teleop
        if (mode == null) return;
        double turn = db.drivetrain.get(DESIRED_TURN_PCT);
        double throttle = db.drivetrain.get(DESIRED_THROTTLE_PCT);
        switch (mode) {
            case RESET:
                reset();
                break;
//			case HOLD:
//				if (!mStartHoldingPosition) {
//					mHoldLeftPositionPid.setSetpoint(db.drivetrain.get(LEFT_POS_INCHES));
//					mHoldRightPositionPid.setSetpoint(db.drivetrain.get(RIGHT_POS_INCHES));
//					mStartHoldingPosition = true;
//				}
//				if (Math.abs(db.drivetrain.get(LEFT_POS_INCHES) - mHoldLeftPositionPid.getSetpoint()) > .5) {
//					double leftOutput = mHoldLeftPositionPid.calculate(db.drivetrain.get(LEFT_POS_INCHES), pNow);
//					mLeftCtrl.setReference(leftOutput * kDriveTrainMaxVelocity, kVelocity, VELOCITY_PID_SLOT, 0);
//				}
//				if (Math.abs(db.drivetrain.get(RIGHT_POS_INCHES) - mHoldRightPositionPid.getSetpoint()) > .5) {
//					double rightOutput = mHoldRightPositionPid.calculate(db.drivetrain.get( RIGHT_POS_INCHES), pNow);
//					mRightCtrl.setReference(rightOutput * kDriveTrainMaxVelocity, kVelocity, VELOCITY_PID_SLOT, 0);
//				}
//				break;

            case VELOCITY:
                mStartHoldingPosition = false;
                mYawPid.setSetpoint(db.drivetrain.get(DESIRED_TURN_PCT) * kMaxDegreesPerSecond);
//				double turn = mYawPid.calculate(Robot.DATA.imu.get(EGyro.YAW_DEGREES), pNow);
//				DriveMessage d = new DriveMessage().turn(turn).throttle(throttle).normalize();
                SmartDashboard.putNumber("DESIRED YAW", mYawPid.getSetpoint());
                SmartDashboard.putNumber("ACTUAL YAW", (Robot.DATA.imu.get(EGyro.YAW_DEGREES)));
//				mLeftCtrl.setReference(d.getLeftOutput() * kDriveTrainMaxVelocity, kVelocity, VELOCITY_PID_SLOT, 0);
//				mRightCtrl.setReference(d.getRightOutput() * kDriveTrainMaxVelocity, kVelocity, VELOCITY_PID_SLOT, 0);

				if(mLeftCtrl.isPresent() && mRightCtrl.isPresent()) {
					mLeftCtrl.get().setReference((throttle + turn) * kDriveTrainMaxVelocityRPM, kSmartVelocity, VELOCITY_PID_SLOT, 0);
					mRightCtrl.get().setReference((throttle - turn) * kDriveTrainMaxVelocityRPM, kSmartVelocity, VELOCITY_PID_SLOT, 0);
				}
                break;
            case PATH_FOLLOWING_BASIC:
            case PATH_FOLLOWING_HELIX:
            	if(mLeftCtrl.isPresent() && mRightCtrl.isPresent()) {
					mLeftCtrl.get().setReference(db.drivetrain.get(L_PATH_FT_s) / kDriveNEOVelocityFactor, kVelocity, VELOCITY_PID_SLOT, 0);
					mRightCtrl.get().setReference(db.drivetrain.get(R_PATH_FT_s) / kDriveNEOVelocityFactor, kVelocity, VELOCITY_PID_SLOT, 0);
				}
                break;
            case PERCENT_OUTPUT:
            	if(mLeftMaster.isPresent() && mRightMaster.isPresent()) {
					mLeftMaster.get().set(throttle + turn);
					mRightMaster.get().set(throttle - turn);
				}
                break;
        }
    }

    private void reset() {

    	if(mLeftEncoder.isPresent() && mRightEncoder.isPresent()) {
			mLeftEncoder.get().setPosition(0.0);
			mRightEncoder.get().setPosition(0.0);
		}
    }

    public void loop(double pNow) {
//		mUpdateTimer.start();
//		mDriveState = db.drivetrain.get(DESIRED_STATE, EDriveState.class);
//		switch(mDriveState) {
//			case PATH_FOLLOWING:
//				mDriveHardware.configureMode(ECommonControlMode.VELOCITY);
//			case TARGET_ANGLE_LOCK:
//				mDriveHardware.configureMode(ECommonControlMode.PERCENT_OUTPUT);
//				mDriveHardware.set(DriveMessage.kNeutral);
//				RobotCodex<ELimelightData> targetData = Robot.DATA.limelight;
//				double pidOutput;
//				if(mTargetAngleLockPid != null && targetData != null && targetData.isSet(TV) && targetData.isSet(TX)) {
//
//					//if there is a target in the limelight's fov, lock onto target using feedback loop
//					pidOutput = mTargetAngleLockPid.calculate(-1.0 * targetData.get(TX), pNow - mPreviousTime);
//					pidOutput = pidOutput + (Math.signum(pidOutput) * Settings.kTargetAngleLockFrictionFeedforward);
//
//					double mTargetTrackingThrottle = db.drivetrain.get(TARGET_TRACKING_THROTTLE);
//					mDriveMessage = new DriveMessage().throttle(mTargetTrackingThrottle).turn(pidOutput).calculateCurvature();
//					// If we've already seen the target and lose tracking, exit.
//				}
//				break;
//			case NORMAL:
//				break;
//			default:
//				mLogger.warn("Got drivetrain state: " + mDriveState+" which is unhandled");
//				break;
//		}
//		mPreviousTime = pNow;
//		mUpdateTimer.stop();
    }

}