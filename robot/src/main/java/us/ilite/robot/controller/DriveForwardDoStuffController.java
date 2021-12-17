package us.ilite.robot.controller;

import us.ilite.common.types.EPowerCellData;
import us.ilite.common.types.drive.EDriveData;
import us.ilite.robot.Enums;

public class DriveForwardDoStuffController extends BaseAutonController {

    @Override
    public void updateImpl() {
//        if (db.powercell.isSet(EPowerCellData.EXIT_BEAM) == true) {
//            db.powercell.set(EPowerCellData.SET_H_pct, 0);
//            db.powercell.set(EPowerCellData.SET_V_pct, 0);
//        } else {
//            db.powercell.set(EPowerCellData.SET_V_pct, 0.1);
//            db.powercell.set(EPowerCellData.SET_H_pct, 0.1);
//        }
        reverseSerializer();
    }
}
