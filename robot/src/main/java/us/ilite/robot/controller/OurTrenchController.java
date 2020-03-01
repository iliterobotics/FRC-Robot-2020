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
import us.ilite.robot.auto.paths.SimplePath;
import us.ilite.robot.auto.paths.Squiggle;
import us.ilite.robot.auto.paths.YoinkTo;
import us.ilite.robot.modules.DriveModule;

import static us.ilite.common.types.drive.EDriveData.*;

public class OurTrenchController extends BaseAutonController {
    //TODO change the value of the TargetDistance;
    private Distance mTargetDistance = Distance.fromInches(Field2020.Distances.INITIATION_LINE_TO_COLOR_WHEEL.mDistance);
    private Data db = Robot.DATA;
    private boolean mIsFirstLegDone;

    public OurTrenchController(){
        mIsFirstLegDone = false;
    }

    @Override
    protected void updateImpl(double pNow) {
        db.drivetrain.set(STATE, Enums.EDriveState.SMART_MOTION);
        db.drivetrain.set(L_DESIRED_POS_FT , 10);
        db.drivetrain.set(R_DESIRED_POS_FT , 10);
        if (isAtDistance(mTargetDistance)){
            stopDrivetrain(pNow);
            mIsFirstLegDone = true;
        }
        activateSerializer(pNow);
        setIntakeArmEnabled(pNow, true);
        initiateShooter();
    }

    public Distance getDistance() {
        return Distance.fromFeet(
                (Robot.DATA.drivetrain.get(L_ACTUAL_POS_FT) +
                        Robot.DATA.drivetrain.get(R_ACTUAL_POS_FT)) / 2.0);
    }

    private boolean isAtDistance(Distance pTargetDistance){
        return getDistance().feet() >= pTargetDistance.feet();
    }

    private boolean canHitInnerGoal() {
        return Field2020.canHitInnerGoal(tempCalcAngleToInnerGoal() , Distance.fromFeet(db.limelight.get(ELimelightData.CALC_DIST_TO_TARGET))); //TODO - Units??
    }

    private void initiateShooter(){
        Enums.FlywheelSpeeds flywheelState = Enums.FlywheelSpeeds.FAR;
        if (canHitInnerGoal()){
            setTargetTracking(true);
            firingSequence(flywheelState);
        }
    }

    // TODO - figure out if this is the correct way we'll be targeting
    private Angle tempCalcAngleToInnerGoal() {
        double thetaGoal = db.limelight.get(ELimelightData.TX);
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
