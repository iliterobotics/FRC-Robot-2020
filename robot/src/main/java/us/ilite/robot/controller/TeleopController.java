package us.ilite.robot.controller;

import com.flybotix.hfr.util.log.ILog;
import com.flybotix.hfr.util.log.Logger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import us.ilite.common.Field2020;
import us.ilite.common.config.InputMap;
import us.ilite.common.types.EHangerModuleData;
import us.ilite.common.types.ELimelightData;
import us.ilite.robot.Enums;
import us.ilite.robot.Robot;

import static us.ilite.common.types.EPowerCellData.*;
import static us.ilite.common.types.EPowerCellData.DESIRED_H_VELOCITY;
import static us.ilite.robot.Robot.DATA;

public class TeleopController extends BaseManualController { //copied from TestController, needs editing

    private ILog mLog = Logger.createLog(TeleopController.class);
    private static TeleopController INSTANCE;
    private Enums.FlywheelSpeeds currentState = Enums.FlywheelSpeeds.OFF;

    public static TeleopController getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TeleopController();
        }
        return INSTANCE;
    }

    private TeleopController() {
    }

    @Override
    protected void updateImpl(double pNow) {
        // ========================================
        // DO NOT COMMENT OUT THESE METHOD CALLS
        // ========================================
        //updateLimelightTargetLock(); //waiting for merge to master
        updateFlywheel(pNow);
        super.updateDrivetrain(pNow);
        updatePowerCells(pNow);
        //updateHanger(pNow); //not integrated yet
        //updateDJBooth(pNow); //not integrated yet
    }

    private void updateHanger(double pNow) {
        if (db.operatorinput.isSet(InputMap.OPERATOR.BEGIN_HANG)) {
            DATA.hanger.set(EHangerModuleData.DESIRED_POSITION, 17.0);
        } else {
            DATA.hanger.set(EHangerModuleData.DESIRED_POSITION, 0.0);
        }
    }

    private void updateFlywheel(double pNow) {
        if(db.operatorinput.isSet(InputMap.OPERATOR.FAR_MODE))
        {
            currentState = Enums.FlywheelSpeeds.FAR;
        }
        else if(db.operatorinput.isSet(InputMap.OPERATOR.NEAR_MODE))
        {
            currentState = Enums.FlywheelSpeeds.CLOSE;
        }

        if(db.operatorinput.isSet(InputMap.OPERATOR.AIM) || db.driverinput.isSet(InputMap.DRIVER.FIRE_POWER_CELLS))
        {
            super.setFlywheelClosedLoop(currentState);
        }
        else
        {
            super.setFlywheelClosedLoop(Enums.FlywheelSpeeds.OFF);
        }
    }

//    public void updateLimelightTargetLock() {
//        if (DATA.driverinput.isSet(InputMap.DRIVER.DRIVER_LIMELIGHT_LOCK_TARGET)) {
//            if (DATA.selectedTarget.isSet(ELimelightData.TY)) {
//                SmartDashboard.putNumber("Distance to Target", DATA.limelight.get(ELimelightData.CALC_DIST_TO_TARGET));
//            }
//            DATA.limelight.set(ELimelightData.TARGET_ID, (double) Field2020.FieldElement.TARGET.id());
//        } else if (DATA.driverinput.isSet(InputMap.DRIVER.DRIVER_LIMELIGHT_LOCK_TARGET_ZOOM)) {
//            if (DATA.selectedTarget.isSet(ELimelightData.TY)) {
//                if (Math.abs(DATA.selectedTarget.get(ELimelightData.TX)) < mLimelightZoomThreshold) {
//                    DATA.limelight.set(ELimelightData.TARGET_ID, (double) Field2020.FieldElement.TARGET_ZOOM.id());
//                    System.out.println("ZOOMING");
//                } else {
//                    DATA.limelight.set(ELimelightData.TARGET_ID, (double) Field2020.FieldElement.TARGET.id());
//                }
//            } else {
//                DATA.selectedTarget.set(ELimelightData.TARGET_ID, (double) Field2020.FieldElement.TARGET.id());
//            }
//        } else if (DATA.driverinput.isSet(InputMap.DRIVER.DRIVER_LIMELIGHT_LOCK_BALL)) {
//            DATA.limelight.set(ELimelightData.TARGET_ID, (double) Field2020.FieldElement.BALL.id());
//        } else if (DATA.driverinput.isSet(InputMap.DRIVER.DRIVER_LIMELIGHT_LOCK_BALL_DUAL)) {
//            DATA.limelight.set(ELimelightData.TARGET_ID, (double) Field2020.FieldElement.BALL_DUAL.id());
//            DATA.limelight.set(ELimelightData.TARGET_ID, (double) Field2020.FieldElement.BALL_TRI.id());
//        } else {
//            DATA.limelight.set(ELimelightData.TARGET_ID, (double) Limelight.NONE.id());
//        }
//        if ((DATA.limelight.get(ELimelightData.TARGET_ID.ordinal()) != (mLastTrackingType))
//                && !(DATA.limelight.get(ELimelightData.TARGET_ID.ordinal()) == Limelight.NONE.id())) {
//            mLog.error("Requesting command start");
//            mLog.error("Stopping teleop command queue");
//        }
//        mLastTrackingType = DATA.limelight.get(ELimelightData.TARGET_ID.ordinal());
//    }

    protected void updatePowerCells(double pNow) {
        // Default to none
        db.powercell.set(INTAKE_STATE, Enums.EArmState.NONE);

        if (db.operatorinput.isSet(InputMap.OPERATOR.INTAKE_ACTIVATE)) {
            setIntakeArmEnabled(pNow, true);
            activateSerializer(pNow);

        } else if (db.operatorinput.isSet(InputMap.OPERATOR.INTAKE_REVERSE)) {
            db.powercell.set(INTAKE_STATE, Enums.EArmState.STOW);
            reverseSerializer(pNow);
        } else if (db.operatorinput.isSet(InputMap.OPERATOR.INTAKE_STOW)) {
            setIntakeArmEnabled(pNow, false);
            activateSerializer(pNow);
        } else {
            db.powercell.set(INTAKE_STATE, Enums.EArmState.NONE);
            db.powercell.set(DESIRED_INTAKE_VELOCITY_FT_S, 0d);
        }

        if ((db.driverinput.isSet(InputMap.DRIVER.FIRE_POWER_CELLS) && isFlywheelUpToSpeed() && isFeederUpToSpeed())) {
            db.powercell.set(DESIRED_V_VELOCITY, 0.6);
            db.powercell.set(DESIRED_H_VELOCITY, 0.5);
        }
    }

//    void updateDJBooth(double pNow) {
//        if (db.operatorinput.isSet(InputMap.OPERATOR.COLOR_POSITION)) {
//            db.color.set(EColorData.DESIRED_MOTOR_POWER, Enums.EColorWheelState.POSITION.getPower());
//            int i = (int) db.color.get(EColorData.SENSED_COLOR);
//            Enums.EColorMatch m = Enums.EColorMatch.values()[i];
//            Color DJ_COLOR = null;
//            switch (db.recieveColorFmsRelay()) {
//                case 'B':
//                    DJ_COLOR = Enums.EColorMatch.BLUE.color;
//                    break;
//                case 'G':
//                    DJ_COLOR = Enums.EColorMatch.GREEN.color;
//                    break;
//                case 'R':
//                    DJ_COLOR = Enums.EColorMatch.RED.color;
//                    break;
//                case 'Y':
//                    DJ_COLOR = Enums.EColorMatch.YELLOW.color;
//                    break;
//                default:
//                    DJ_COLOR = null;
//                    break;
//            }
//            if (m.color.equals(DJ_COLOR)) {
//                //TODO stop using the module for the desired power
//                db.color.set(EColorData.DESIRED_MOTOR_POWER, Enums.EColorWheelState.OFF.getPower());
//            } else {
//                db.color.set(EColorData.DESIRED_MOTOR_POWER, Enums.EColorWheelState.POSITION.getPower());
//                db.color.set(EColorData.COLOR_WHEEL_MOTOR_STATE, Enums.EColorWheelState.POSITION.ordinal());
//            }
//        }
//        else if ( db.operatorinput.isSet(InputMap.OPERATOR.COLOR_ROTATION)) {
//            db.color.set(EColorData.DESIRED_MOTOR_POWER, Enums.EColorWheelState.ROTATION.getPower());
//            if(db.color.get(EColorData.WHEEL_ROTATION_COUNT) >= DJSpinnerModule.TARGET_ROTATION_COUNT) {
//                db.color.set(EColorData.COLOR_WHEEL_MOTOR_STATE, Enums.EColorWheelState.OFF.ordinal());
//                db.color.set(EColorData.DESIRED_MOTOR_POWER, Enums.EColorWheelState.OFF.getPower());
//            } else {
//                db.color.set(EColorData.COLOR_WHEEL_MOTOR_STATE, Enums.EColorWheelState.ROTATION.ordinal());
//                db.color.set(EColorData.DESIRED_MOTOR_POWER, Enums.EColorWheelState.ROTATION.getPower());
//            }
//        }
//
//    }

}
