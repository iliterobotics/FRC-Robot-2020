package us.ilite.robot.controller;

import us.ilite.common.types.EShooterSystemData;
import us.ilite.common.types.drive.EDriveData;
import us.ilite.robot.Enums;

public class InitiationController extends BaseAutonController{
    public void updateImpl() {
        db.drivetrain.set(EDriveData.R_DESIRED_POS, 5.0);
        db.drivetrain.set(EDriveData.L_DESIRED_POS, 5.0);
        db.flywheel.set(EShooterSystemData.SET_FEEDER_pct, 0.5);
        db.drivetrain.set(EDriveData.R_DESIRED_POS, -5.0);

    }
}
