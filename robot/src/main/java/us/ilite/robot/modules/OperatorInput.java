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
import us.ilite.common.types.EPowerCellData;
import us.ilite.common.types.EShooterSystemData;
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
    private NetworkTableEntry matchTimeEntry, driverThrottleEntry, limelightStatusEntry, driverHangBtnEntry,
        operatorHangBtnEntry, intakeOutEntry, intakeInEntry, longShortMode,
            serializerCurrentEntry, ballCountEntry;
    protected Codex<Double, ELogitech310> mDriverInputCodex, mOperatorInputCodex;
    private EMatchMode mMode = EMatchMode.DISABLED;


    public OperatorInput() {
        mDriverJoystick = new Joystick(0);
        mOperatorJoystick = new Joystick(1);
        drivetrainTab = Shuffleboard.getTab("drivetrain");

        matchTimeEntry = drivetrainTab.add("Match Time", 0).withSize(2, 1).withPosition(0, 0).getEntry();
        driverThrottleEntry = drivetrainTab.add("Driver Throttle", 0).withWidget(BuiltInWidgets.kGraph).getEntry();
        limelightStatusEntry = drivetrainTab.add("Is Target Locked", "No Visible Target").getEntry();
        driverHangBtnEntry = drivetrainTab.add("Hanger Lock - Driver", 0).getEntry();
        operatorHangBtnEntry = drivetrainTab.add("Hanger Lock - Operator", 0).getEntry();
        intakeOutEntry = drivetrainTab.add("Intake Out", 0).getEntry();
        intakeInEntry = drivetrainTab.add("Intake In", 0).getEntry();
        longShortMode = drivetrainTab.add("Mode - Long/Short", " ").getEntry();
        serializerCurrentEntry = drivetrainTab.add("Serializer Current", 0).getEntry();
        ballCountEntry = drivetrainTab.add("Ball Count", 0).getEntry();
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
        //display current match time
        matchTimeEntry.setDouble(Robot.CLOCK.getCurrentTime());

        //display driver throttle
        driverThrottleEntry.setDouble(Robot.DATA.driverinput.get(ELogitech310.LEFT_Y_AXIS));

        //display if limelight sees target and/or is locked
        if(Math.abs(Robot.DATA.limelight.get(ELimelightData.TX)) < 2.0)
        {
            limelightStatusEntry.setString("Target Locked");
        }
        else if(Robot.DATA.limelight.isSet(ELimelightData.TV))
        {
            limelightStatusEntry.setString("Target in View");
        }
        else
        {
            limelightStatusEntry.setString("No Visible Target");
        }

        //is hanger locked (both driver and operator)
        operatorHangBtnEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.Y_BTN));
        driverHangBtnEntry.setDouble(Robot.DATA.driverinput.get(ELogitech310.BACK));

        //long/short shooting mode
        if(Robot.DATA.flywheel.get(EShooterSystemData.CURRENT_FLYWHEEL_VELOCITY) > 0.0) //arbitrary value for now, need clarification
        {
          longShortMode.setString("Long");
        }
        else
        {
          longShortMode.setString("Short");
        }

        //intake in or out and current to tell what the operator's trying to do
        intakeOutEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.X_BTN));
        intakeInEntry.setDouble(Robot.DATA.operatorinput.get(ELogitech310.A_BTN));
        serializerCurrentEntry.setDouble(Robot.DATA.powercell.get(EPowerCellData.SERIALIZER_CURRENT));

        //ball count - check how many times the secondary beam is broken
        ballCountEntry.setDouble(Robot.DATA.powercell.get(EPowerCellData.BALL_INTAKE_COUNT));

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
