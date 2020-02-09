package us.ilite.robot.modules;

import com.flybotix.hfr.codex.RobotCodex;
import com.flybotix.hfr.util.log.ILog;
import com.flybotix.hfr.util.log.Logger;
import com.revrobotics.*;
import static com.revrobotics.ControlType.*;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import us.ilite.common.config.Settings;
import us.ilite.common.lib.control.PIDController;
import us.ilite.common.lib.control.ProfileGains;
import us.ilite.common.lib.util.Conversions;
import us.ilite.common.lib.util.Units;
import us.ilite.common.types.ELimelightData;
import us.ilite.common.types.EMatchMode;

import static us.ilite.common.types.ELimelightData.TV;
import static us.ilite.common.types.ELimelightData.TX;
import static us.ilite.common.types.drive.EDriveData.*;

import us.ilite.common.types.sensor.EGyro;
import us.ilite.common.types.sensor.EPowerDistPanel;
import static us.ilite.common.types.sensor.EPowerDistPanel.*;
import us.ilite.robot.Robot;
import us.ilite.robot.hardware.*;

/**
 * Class for running all drivetrain train control operations from both autonomous and
 * driver-control.
 * TODO Support for rotation trajectories
 * TODO Turn-to-heading with Motion Magic
 */
public class DriveModule extends Module {
	private final ILog mLogger = Logger.createLog(DriveModule.class);
	public static double kDriveTrainMaxVelocityRPM = 5676;

	// This is approx 290 Degrees per second
	public static double kMaxDegreesPerCycle = Units.radians_to_degrees(5);

	public static double kGearboxRatio = (12.0 / 80.0) * (42.0 / 80.0);
	public static double kClosedLoopVoltageRampRate = 0.1 ;
	public static double kOpenLoopVoltageRampRate = 0.1;
	public static int kCurrentLimitAmps = 50;
	public static int kCurrentLimitTriggerDurationMs = 100;
	public static double kWheelDiameterInches = 6.0;
	public static double kWheelDiameterFeet = kWheelDiameterInches / 12.0;
	public static double kWheelCircumference = kWheelDiameterInches * Math.PI;
	public static double kDefaultRampRate = 120.0; // in V/sec
	public static double kMotorTicksPerWheelRotation = 42d / kGearboxRatio;
	public static double kEffectiveWheelbase = 23.25;
	public static double kCurvatureCircumference = kEffectiveWheelbase * Math.PI;
	public static double kInchesPerCurvatureDegree = kCurvatureCircumference / 360.0;
	public static double kWheelTurnsPerCurvatureDegree = kInchesPerCurvatureDegree / kWheelDiameterInches;

	// =============================================================================
	// Closed-Loop Velocity Constants
	// =============================================================================
	private static final int VELOCITY_PID_SLOT = 1;
	private static final int POSITION_PID_SLOT = 2;
	public static ProfileGains dPID = new ProfileGains().p(1.0).maxVelocity(5676d).maxAccel(56760d).slot(POSITION_PID_SLOT);
	public static ProfileGains vPID = new ProfileGains()
			.f(0.00015)
			.p(0.0001)
			// Enforce a maximum allowed speed, system-wide. DO NOT undo kMaxAllowedVelocityMultiplier without checking with a mentor first.
			.maxVelocity(kDriveTrainMaxVelocityRPM * Settings.Input.kMaxAllowedVelocityMultiplier)
			.maxAccel(kDriveTrainMaxVelocityRPM*kDriveTrainMaxVelocityRPM)
			.slot(VELOCITY_PID_SLOT);

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
	private EDriveState mDriveState;

	private PIDController mTargetAngleLockPid;
	private PIDController mYawPid;
//	private PIDController mHoldLeftPositionPid;
//	private PIDController mHoldRightPositionPid;
	private boolean mStartHoldingPosition;
	private double mLeftHoldSetpoint;
	private double mRightHoldSetpoint;
	private double mPreviousHeading = 0.0;
	private double mPreviousTime = 0;
	private double mLeftHoldSetpoint;
	private double mRightHoldSetpoint;

	private final CANSparkMax mLeftMaster;
	private final CANSparkMax mLeftFollower;
	private final CANSparkMax mRightMaster;
	private final CANSparkMax mRightFollower;
	private final CANEncoder mLeftEncoder;
	private final CANEncoder mRightEncoder;
	private final CANPIDController mLeftCtrl;
	private final CANPIDController mRightCtrl;

	private static final SparkMaxFactory.Configuration kDriveConfig = new SparkMaxFactory.Configuration();
	static {
		kDriveConfig.IDLE_MODE = CANSparkMax.IdleMode.kCoast;
	}

	public DriveModule() {
		mLeftMaster = SparkMaxFactory.createSparkMax(Settings.Hardware.CAN.kDriveLeftMaster, kDriveConfig);
		mLeftFollower = SparkMaxFactory.createSparkMax(Settings.Hardware.CAN.kDriveLeftFollower, kDriveConfig);
		mLeftFollower.follow(mLeftMaster);
		mLeftEncoder = mLeftMaster.getEncoder();
		mLeftCtrl = mLeftMaster.getPIDController();
		mLeftCtrl.setOutputRange(-kDriveTrainMaxVelocityRPM, kDriveTrainMaxVelocityRPM);
		mRightMaster = SparkMaxFactory.createSparkMax(Settings.Hardware.CAN.kDriveRightMaster, kDriveConfig);
		mRightFollower = SparkMaxFactory.createSparkMax(Settings.Hardware.CAN.kDriveRightFollower, kDriveConfig);
		mRightFollower.follow(mRightMaster);
		mRightEncoder = mLeftMaster.getEncoder();
		mRightCtrl = mRightMaster.getPIDController();
		mRightCtrl.setOutputRange(-kDriveTrainMaxVelocityRPM, kDriveTrainMaxVelocityRPM);
		mLeftMaster.setInverted(true);
		mLeftFollower.setInverted(true);
		mGyro = new Pigeon(Settings.Hardware.CAN.kPigeon);

		HardwareUtils.setGains(mLeftCtrl, vPID);
		HardwareUtils.setGains(mRightCtrl, vPID);
		HardwareUtils.setGains(mLeftCtrl, dPID);
		HardwareUtils.setGains(mRightCtrl, dPID);
		mLeftMaster.burnFlash();
		mLeftFollower.burnFlash();
		mRightMaster.burnFlash();
		mRightFollower.burnFlash();

	}

	@Override
	public void modeInit(EMatchMode pMode, double pNow) {
		mTargetAngleLockPid = new PIDController(Settings.kTargetAngleLockGains, Settings.kTargetAngleLockMinInput, Settings.kTargetAngleLockMaxInput, Settings.kControlLoopPeriod);
		mTargetAngleLockPid.setOutputRange(Settings.kTargetAngleLockMinPower, Settings.kTargetAngleLockMaxPower);
		mTargetAngleLockPid.setSetpoint(0);
		mTargetAngleLockPid.reset();

		mYawPid = new PIDController(kYawGains,
									-kMaxDegreesPerCycle,
									kMaxDegreesPerCycle,
									Settings.kControlLoopPeriod);
		mYawPid.setOutputRange(-1, 1);
		mYawPid.reset();

//		mHoldLeftPositionPid = new PIDController(kHoldPositionGains,-99999, 99999, Settings.kControlLoopPeriod);
//		mHoldLeftPositionPid.setOutputRange(-1, 1);
//		mHoldLeftPositionPid.setSetpoint(0.0);
//		mHoldRightPositionPid = new PIDController(kHoldPositionGains,-99999, 99999, Settings.kControlLoopPeriod);
//		mHoldRightPositionPid.setOutputRange(-1, 1);
//		mHoldRightPositionPid.setSetpoint(0.0);
		mStartHoldingPosition = false;
		mLeftHoldSetpoint = 0.0;
		mRightHoldSetpoint = 0.0;

		mLeftMaster.getEncoder().setPosition(0.0);
		mRightMaster.getEncoder().setPosition(0.0);
		HardwareUtils.setGains(mLeftCtrl, vPID);
		HardwareUtils.setGains(mRightCtrl, vPID);
		HardwareUtils.setGains(mLeftCtrl, dPID);
		HardwareUtils.setGains(mRightCtrl, dPID);

	}

	@Override
	public void readInputs(double pNow) {
		mGyro.update(pNow);
		db.drivetrain.set(LEFT_POS_INCHES, ticksToInches(mLeftEncoder.getPosition()));
		db.drivetrain.set(LEFT_VEL_IPS, ticksToInches(mLeftEncoder.getVelocity()));
		db.drivetrain.set(LEFT_VEL_TICKS, mLeftEncoder.getVelocity());
		db.drivetrain.set(RIGHT_POS_INCHES, ticksToInches(mRightEncoder.getPosition()));
		db.drivetrain.set(RIGHT_VEL_IPS, ticksToInches(mRightEncoder.getVelocity()));
		db.drivetrain.set(RIGHT_VEL_TICKS, mRightEncoder.getVelocity());
		db.drivetrain.set(LEFT_CURRENT, mLeftMaster.getOutputCurrent());
		db.drivetrain.set(RIGHT_CURRENT, mRightMaster.getOutputCurrent());
		db.drivetrain.set(IS_CURRENT_LIMITING, EPowerDistPanel.isAboveCurrentThreshold(kCurrentLimitAmps, Robot.DATA.pdp, kPdpSlots));


		Robot.DATA.imu.set(EGyro.HEADING_DEGREES, -mGyro.getHeading().getDegrees());

//		mCurrentHeading = Robot.DATA.imu.get(EGyro.HEADING_DEGREES);
		Robot.DATA.imu.set(EGyro.YAW_DEGREES, -mGyro.getYaw().getDegrees());
		db.imu.set(EGyro.YAW_OMEGA_DEGREES, ( (-mGyro.getYaw().getDegrees())	 - mPreviousHeading ) / ( pNow - mPreviousTime ) );

	}

	@Override
	public void setOutputs(double pNow) {
		EDriveState mode = db.drivetrain.get(DESIRED_STATE, EDriveState.class);
		double turn = db.drivetrain.get(DESIRED_TURN_PCT);
		double throttle = db.drivetrain.get(DESIRED_THROTTLE_PCT);
		switch (mode) {
			case HOLD:
				if (!mStartHoldingPosition) {
					mLeftHoldSetpoint = db.drivetrain.get(LEFT_POS_INCHES);
					mRightHoldSetpoint = db.drivetrain.get(RIGHT_POS_INCHES);
					mRightMaster.getEncoder().setPosition(0.0);
					mLeftMaster.getEncoder().setPosition(0.0);
					mStartHoldingPosition = true;
				}

				if (db.drivetrain.get(LEFT_VEL_TICKS) < 100) {
					if (Math.abs(db.drivetrain.get(LEFT_POS_INCHES) - mLeftHoldSetpoint) > 2) {
						mLeftCtrl.setReference(inchesToRotations(mLeftHoldSetpoint), kPosition, POSITION_PID_SLOT, 0);
					}
				} else {
					mLeftCtrl.setReference(0.0, kSmartVelocity, VELOCITY_PID_SLOT, 0);
				}

				if (db.drivetrain.get(RIGHT_VEL_TICKS) < 100) {
					if (Math.abs(db.drivetrain.get(RIGHT_POS_INCHES) - mRightHoldSetpoint) > 2) {
						mRightCtrl.setReference(inchesToRotations(mRightHoldSetpoint), kPosition, POSITION_PID_SLOT, 0);
					}
				} else {
					mRightCtrl.setReference(0.0, kSmartVelocity, VELOCITY_PID_SLOT, 0);
				}
				break;

			case VELOCITY:
				mStartHoldingPosition = false;
				mYawPid.setSetpoint(db.drivetrain.get(DESIRED_TURN_PCT) * kMaxDegreesPerCycle);
//				turn = mYawPid.calculate(Robot.DATA.imu.get(EGyro.YAW_DEGREES), pNow);
				SmartDashboard.putNumber("DESIRED YAW", mYawPid.getSetpoint());
				SmartDashboard.putNumber("ACTUAL YAW", (Robot.DATA.imu.get(EGyro.YAW_DEGREES)));
				mLeftCtrl.setReference((throttle-turn) * kDriveTrainMaxVelocityRPM, kSmartVelocity, VELOCITY_PID_SLOT, 0);
				mRightCtrl.setReference((throttle+turn) * kDriveTrainMaxVelocityRPM, kSmartVelocity, VELOCITY_PID_SLOT, 0);
				break;
			case PERCENT_OUTPUT:
				mLeftMaster.set(throttle-turn);
				mRightMaster.set(throttle+turn);
				break;
		}
		mPreviousTime = pNow;
		mPreviousHeading = db.imu.get(EGyro.HEADING_DEGREES);
	}

	public void loop(double pNow) {
//		mUpdateTimer.start();
		mDriveState = db.drivetrain.get(DESIRED_STATE, EDriveState.class);
		switch(mDriveState) {
//			case PATH_FOLLOWING:
//				mDriveHardware.configureMode(ECommonControlMode.VELOCITY);
			case TARGET_ANGLE_LOCK:
//				mDriveHardware.configureMode(ECommonControlMode.PERCENT_OUTPUT);
//				mDriveHardware.set(DriveMessage.kNeutral);
				RobotCodex<ELimelightData> targetData = Robot.DATA.limelight;
				double pidOutput;
				if(mTargetAngleLockPid != null && targetData != null && targetData.isSet(TV) && targetData.isSet(TX)) {
					//if there is a target in the limelight's fov, lock onto target using feedback loop
					pidOutput = mTargetAngleLockPid.calculate(-1.0 * targetData.get(TX), pNow - mPreviousTime);
					pidOutput = pidOutput + (Math.signum(pidOutput) * Settings.kTargetAngleLockFrictionFeedforward);

					db.drivetrain.set(DESIRED_TURN_PCT, pidOutput);
					// If we've already seen the target and lose tracking, exit.
				}
				break;
			case NORMAL:
				break;
			default:
				mLogger.warn("Got drivetrain state: " + mDriveState+" which is unhandled");
				break;
		}
		mPreviousTime = pNow;
//		mUpdateTimer.stop();
	}

	public static double rotationsToInches(double rotations) {
		return rotations * (kWheelDiameterInches * Math.PI);
	}

	public static double rpmToInchesPerSecond(double rpm) {
		return rotationsToInches(rpm) / 60;
	}

	public static double inchesToRotations(double inches) {
		return inches / kWheelCircumference;
	}

	public static double inchesPerSecondToRpm(double inches_per_second) {
		return inchesToRotations(inches_per_second) * 60;
	}

	public static double radiansPerSecondToTicksPer100ms(double rad_s) {
		return rad_s / (Math.PI * 2.0) * kMotorTicksPerWheelRotation / 10.0;
	}

	public static double ticksToRotations(double ticks) {
		return ticks / kMotorTicksPerWheelRotation;
	}

	public static double ticksToInches(double ticks) {
		return ticksToRotations(ticks) * kWheelCircumference;
	}

	public static int inchesToTicks(double inches) {
		return (int)(inchesToRotations(inches) * kMotorTicksPerWheelRotation);
	}

	public static double ticksPer100msToRotationsPerSecond(double ticks) {
		return ticks / kMotorTicksPerWheelRotation * 10.0;
	}

	public static double ticksPer100msToInchesPerSecond(double ticks) {
		return ticksPer100msToRotationsPerSecond(ticks) * kWheelCircumference;
	}

	public static double ticksPer100msToRadiansPerSecond(double ticks) {
		return ticksPer100msToRotationsPerSecond(ticks) * (Math.PI * 2.0);
	}

}