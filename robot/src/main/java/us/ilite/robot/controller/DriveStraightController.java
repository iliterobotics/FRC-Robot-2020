package us.ilite.robot.controller;

import us.ilite.common.Angle;
import us.ilite.common.Data;
import us.ilite.common.Distance;
import us.ilite.common.Field2020;
import us.ilite.common.types.ELimelightData;
import us.ilite.common.types.EPowerCellData;
import us.ilite.common.types.drive.EDriveData;
import us.ilite.robot.Enums;
import us.ilite.robot.Robot;

import static us.ilite.common.types.drive.EDriveData.*;

public class DriveStraightController extends BaseAutonController {
    private Distance mTargetDistance = Distance.fromInches(Field2020.Distances.INITIATION_LINE_TO_COLOR_WHEEL.mDistance);
    private Data db = Robot.DATA;
    private boolean mIsFirstLegDone;

    public DriveStraightController(){
        db.drivetrain.set(STATE, Enums.EDriveState.PERCENT_OUTPUT);
        mIsFirstLegDone = false;
    }

    @Override
    protected void updateImpl(double pNow) {
        super.updateImpl(pNow);
        db.drivetrain.set(EDriveData.DESIRED_THROTTLE_PCT, 0.5);
        if (isAtDistance(mTargetDistance)){
            db.drivetrain.set(EDriveData.DESIRED_THROTTLE_PCT, 0.5);
            mIsFirstLegDone = true;
        }
        activateSerializer(pNow);
        setIntakeArmEnabled(pNow, true);
        initiateAllModules();
    }

    public Distance getDistance() {
        return Distance.fromFeet(
                (Robot.DATA.drivetrain.get(L_ACTUAL_POS_FT) +
                        Robot.DATA.drivetrain.get(R_ACTUAL_POS_FT)) / 2.0);
    }

    private boolean isAtDistance(Distance pTargetDistance){
        return getDistance().inches() >= pTargetDistance.inches();
    }

    private void initiateAllModules(){
        Enums.FlywheelSpeeds flywheelState = Enums.FlywheelSpeeds.FAR;
        if (Field2020.canHitInnerGoal(tempCalcAngleToInnerGoal() , getDistance())){
            setFlywheelClosedLoop(flywheelState);
            if (isFlywheelUpToSpeed()) {
                setFeederClosedLoop(flywheelState);
                if (isFeederUpToSpeed()) {
                    db.powercell.set(EPowerCellData.DESIRED_V_VELOCITY, 0.6);
                    db.powercell.set(EPowerCellData.DESIRED_H_VELOCITY, 0.5);
                }
            }
        }
    }

    private Angle tempCalcAngleToInnerGoal() {
        double thetaGoal = db.limelight.get(ELimelightData.CALC_ANGLE_TO_TARGET);
        double distToGoal = db.limelight.get(ELimelightData.CALC_DIST_TO_TARGET); //TODO: Units??
        double b = 29.25 + (distToGoal * Math.cos(thetaGoal));
        double a = distToGoal * Math.sin(thetaGoal);
        double thetab = Math.tanh(b/a);
        return Angle.fromDegrees(90 - thetab);
    }

    public boolean isFirstLegDone() {
        return mIsFirstLegDone;
    }
}
