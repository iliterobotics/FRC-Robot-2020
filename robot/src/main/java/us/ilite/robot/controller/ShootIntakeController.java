package us.ilite.robot.controller;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import us.ilite.robot.auto.paths.*;

public class ShootIntakeController extends BaseAutonController {
    private double mPathDistance = 0d;
    private SimpleSequence mSimpleSequence = new SimpleSequence();

    public ShootIntakeController() {
        setActivePath(new Yoink());
    }
    @Override
    protected void updateImpl(double pNow) {
        super.updateImpl(pNow);
        int pathIndex = BobUtils.getIndexForCumulativeTime(mActivePath, pNow, mPathStartTime);
        if (pathIndex != -1 ){
            mPathDistance = mActivePath.getPath()[pathIndex][7];
            SmartDashboard.putNumber("PATH DISTANCE", mPathDistance);
        }
        mSimpleSequence.updateSequence(pNow, mPathDistance);
    }

}
