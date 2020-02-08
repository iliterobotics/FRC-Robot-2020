package us.ilite.robot.modules;

import com.flybotix.hfr.codex.Codex;
import com.flybotix.hfr.codex.RobotCodex;
import com.flybotix.hfr.util.lang.EnumUtils;
import com.flybotix.hfr.util.log.ILog;
import com.flybotix.hfr.util.log.Logger;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import us.ilite.common.types.ELimelightData;
import us.ilite.common.types.EMatchMode;
import us.ilite.common.types.input.ELogitech310;
import us.ilite.robot.Robot;
import java.util.List;

public class OperatorInput extends Module {
    protected static final double
            DRIVER_SUB_WARP_AXIS_THRESHOLD = 0.5;
    private ILog mLog = Logger.createLog(OperatorInput.class);

    private Joystick mDriverJoystick;
    private Joystick mOperatorJoystick;
    private ShuffleboardTab drivetrainTab;
//    private NetworkTableEntry operatorThrottleEntry, aButtonEntry, bButtonEntry, xButtonEntry,
//            yButtonEntry, lButtonEntry, rButtonEntry, backEntry, startEntry, rightJoystickButtonEntry,
//            leftJoystickButtonEntry, dpadRawEntry, dpadUpEntry, dpadDownEntry, dpadLeftEntry, dpadRightEntry,
//            leftXAxisEntry, leftYAxisEntry, rightXAxisEntry, rightYAxisEntry, combinedTriggerAxisEntry,
//            leftTriggerAxisEntry, rightTriggerAxisEntry, rumbleEntry;
    private NetworkTableEntry matchTimeEntry, driverThrottleEntry, limelightStatusEntry, driverHangBtnEntry,
        operatorHangBtnEntry, intakeStatusEntry;
    protected Codex<Double, ELogitech310> mDriverInputCodex, mOperatorInputCodex;
    private EMatchMode mMode = EMatchMode.DISABLED;


    public OperatorInput() {
        mDriverJoystick = new Joystick(0);
        mOperatorJoystick = new Joystick(1);
        drivetrainTab = Shuffleboard.getTab("drivetrain");
//        operatorThrottleEntry = drivetrainTab.add("Operator Throttle", 0).getEntry();
//        aButtonEntry = drivetrainTab.add("A Button", 0).getEntry();
//        bButtonEntry = drivetrainTab.add("B Button", 0).getEntry();
//        xButtonEntry = drivetrainTab.add("X Button", 0).getEntry();
//        yButtonEntry = drivetrainTab.add("Y Button", 0).getEntry();
//        lButtonEntry = drivetrainTab.add("L Button", 0).getEntry();
//        rButtonEntry = drivetrainTab.add("R Button", 0).getEntry();
//        backEntry = drivetrainTab.add("Back", 0).getEntry();
//        startEntry = drivetrainTab.add("Start", 0).getEntry();
//        rightJoystickButtonEntry = drivetrainTab.add("Right Joystick Button", 0).getEntry();
//        leftJoystickButtonEntry = drivetrainTab.add("Left Joystick Button", 0).getEntry();
//        dpadRawEntry = drivetrainTab.add("DPad Raw", 0).getEntry();
//        dpadUpEntry = drivetrainTab.add("DPad Up", 0).getEntry();
//        dpadDownEntry = drivetrainTab.add("DPad Down", 0).getEntry();
//        dpadLeftEntry = drivetrainTab.add("DPad Left", 0).getEntry();
//        dpadRightEntry = drivetrainTab.add("DPad Right", 0).getEntry();
//        leftXAxisEntry = drivetrainTab.add("Left X Axis", 0).getEntry();
//        leftYAxisEntry = drivetrainTab.add("Left Y Axis", 0).getEntry();
//        rightXAxisEntry = drivetrainTab.add("Right X Axis", 0).getEntry();
//        rightYAxisEntry = drivetrainTab.add("Right Y Axis", 0).getEntry();
//        combinedTriggerAxisEntry = drivetrainTab.add("Combined Trigger Axis", 0).getEntry();
//        leftTriggerAxisEntry = drivetrainTab.add("Left Trigger Axis", 0).getEntry();
//        rightTriggerAxisEntry = drivetrainTab.add("Right Trigger Axis", 0).getEntry();
//        rumbleEntry = drivetrainTab.add("Rumble", 0).getEntry();

        matchTimeEntry = drivetrainTab.add("Match Time", 0).withSize(2, 1).withPosition(0, 0).getEntry();
        driverThrottleEntry = drivetrainTab.add("Driver Throttle", 0).withWidget(BuiltInWidgets.kGraph).getEntry();
        limelightStatusEntry = drivetrainTab.add("Is Target Locked", 0).getEntry();
        driverHangBtnEntry = drivetrainTab.add("Hanger Lock - Driver", 0).getEntry();
        operatorHangBtnEntry = drivetrainTab.add("Hanger Lock - Operator", 0).getEntry();
        intakeStatusEntry = drivetrainTab.add("Intake Status", 0).getEntry();
    }

    @Override
    public void modeInit(EMatchMode pMode, double pNow) {

        if(mDriverJoystick.getType() == null) {
            System.err.println("======= DRIVER JOYSTICK IS NOT PLUGGED IN =======");
        }
        if(mOperatorJoystick.getType() == null) {
            System.err.println("======= OPERATOR JOYSTICK IS NOT PLUGGED IN =======");
        }
        mMode = pMode;
    }

    @Override
    public void readInputs(double pNow) {
        ELogitech310.map(Robot.DATA.driverinput, mDriverJoystick);
        ELogitech310.map(Robot.DATA.operatorinput, mOperatorJoystick);
    }

    @Override
    public void setOutputs(double pNow) {
        driverThrottleEntry.setDouble(Robot.DATA.driverinput.get(ELogitech310.LEFT_Y_AXIS));
//        operatorThrottleEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.LEFT_Y_AXIS));
//        aButtonEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.A_BTN));
//        bButtonEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.B_BTN));
//        xButtonEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.X_BTN));
//        yButtonEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.Y_BTN));
//        lButtonEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.L_BTN));
//        rButtonEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.R_BTN));
//        backEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.BACK));
//        startEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.A_BTN));
//        rightJoystickButtonEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.RIGHT_JOYSTICK_BTN));
//        leftJoystickButtonEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.LEFT_JOYSTICK_BTN));
//        dpadRawEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.DPAD_RAW));
//        dpadUpEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.DPAD_UP));
//        dpadDownEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.DPAD_DOWN));
//        dpadLeftEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.DPAD_LEFT));
//        dpadRightEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.DPAD_RIGHT));
//        leftXAxisEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.LEFT_X_AXIS));
//        leftYAxisEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.LEFT_Y_AXIS));
//        rightXAxisEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.RIGHT_X_AXIS));
//        rightYAxisEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.RIGHT_Y_AXIS));
//        combinedTriggerAxisEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.COMBINED_TRIGGER_AXIS));
//        leftTriggerAxisEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.LEFT_TRIGGER_AXIS));
//        rightTriggerAxisEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.RIGHT_TRIGGER_AXIS));
//        rumbleEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.RUMBLE));

        //display current match time
        matchTimeEntry.setDouble(Robot.CLOCK.getCurrentTime());

        //display if limelight sees target and/or is locked
        if(Robot.DATA.limelight.get(ELimelightData.TV) == 1)
        {
            limelightStatusEntry.setString("Target in View");
        }
        else if(Robot.DATA.limelight.get(ELimelightData.TX) < 0.1)
        {
            limelightStatusEntry.setString("Target Locked");
        }
        else
        {
            limelightStatusEntry.setString("No Visible Target");
        }

        //is hanger locked (both driver and operator)
        operatorHangBtnEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.Y_BTN));
        driverHangBtnEntry.setDouble(Robot.DATA.driverinput.get(ELogitech310.BACK));

        //long/short mode (hood angle and flywheel speed)

        //intake in or out and current to tell what the operator's trying to do
        //intakeStatusEntry.setDouble(Robot.DATA.operatorinput.get())

        //ball count


        if (mMode == EMatchMode.TEST) {
            for(RobotCodex c : Robot.DATA.mLoggedCodexes) {
                String codex = c.meta().getEnum().getSimpleName();
                List<Enum<?>> enums = EnumUtils.getEnums(c.meta().getEnum(), true);
                for(int i = 0; i < enums.size(); i++) {
                    codex += ":" + enums.get(i).name();
                    SmartDashboard.putNumber(codex, (double)c.get(i));
                }
            }
        }
    }

}
