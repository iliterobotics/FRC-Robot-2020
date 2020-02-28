package us.ilite.robot.controller;

import com.team2363.commands.IliteHelixFollower;
import com.team319.trajectory.Path;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import us.ilite.common.Angle;
import us.ilite.common.Distance;
import us.ilite.common.Field2020;
import us.ilite.common.types.ELimelightData;
import us.ilite.common.types.EPowerCellData;
import us.ilite.common.types.EShooterSystemData;
import us.ilite.common.types.drive.EDriveData;
import us.ilite.robot.Enums;
import us.ilite.robot.auto.paths.*;

public class YoinkController extends BaseAutonController {
    private YoinkTo mYoinkTo = new YoinkTo();
    private YoinkFrom mYoinkFrom = new YoinkFrom();
    private boolean mHasReversed;
    private double mYoinkFromStartTime;

    public YoinkController() {
        super(new YoinkTo(), false);
        mHasReversed = false;
    }

    @Override
    public void updateImpl(double pNow) {
        SmartDashboard.putNumber("INDEX", BobUtils.getIndexForCumulativeTime(mActivePath, pNow, mPathStartTime));
        super.updateImpl(pNow);
        setIntakeArmEnabled(pNow, true);
        activateSerializer(pNow);

        if (BobUtils.isFinished(pNow, mYoinkTo, mPathStartTime)) {
            if (!mHasReversed) {
                setNewActivePath(mYoinkFrom, true);
                mYoinkFromStartTime = pNow;
                mHasReversed = true;

                // Update again since path has changed, follows process of BaseAutonController
                super.updateImpl(pNow);
            }
            if (BobUtils.isFinished(pNow, mYoinkFrom, mYoinkFromStartTime)) {
                Enums.FlywheelSpeeds flywheelState = Enums.FlywheelSpeeds.CLOSE;
                setTargetTracking(true);
                if (Field2020.canHitInnerGoal(tempCalcAngleToInnerGoal(), Distance.fromFeet(db.goaltracking.get(ELimelightData.CALC_DIST_TO_TARGET)))) {
                    setFlywheelClosedLoop(flywheelState);
                    if (isFlywheelUpToSpeed()) {
                        setFeederClosedLoop(flywheelState);
                        if (isFeederUpToSpeed()) {
                            db.powercell.set(EPowerCellData.SET_V_pct, 0.6);
                            db.powercell.set(EPowerCellData.SET_H_pct, 0.5);
                        }
                    }
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
}
