package us.ilite.robot.controller;

import us.ilite.common.types.drive.EDriveData;

public class InitiationController extends BaseAutonController{
    public void updateImpl() {
        db.drivetrain.set(EDriveData.R_DESIRED_POS, 5.0);
        db.drivetrain.set(EDriveData.L_DESIRED_POS, 5.0);
    }
}
