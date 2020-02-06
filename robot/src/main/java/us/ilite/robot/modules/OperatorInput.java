package us.ilite.robot.modules;

import com.flybotix.hfr.codex.Codex;
import com.flybotix.hfr.util.log.ILog;
import com.flybotix.hfr.util.log.Logger;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import us.ilite.common.types.EMatchMode;
import us.ilite.common.types.input.ELogitech310;
import us.ilite.robot.DriverInput;
import us.ilite.robot.Robot;

import java.util.TreeMap;

public class OperatorInput extends Module {
    protected static final double
            DRIVER_SUB_WARP_AXIS_THRESHOLD = 0.5;
    private ILog mLog = Logger.createLog(OperatorInput.class);


    private Joystick mDriverJoystick;
    private Joystick mOperatorJoystick;
    private ShuffleboardTab drivetrainTab;
    private NetworkTableEntry driverThrottleEntry;
    private TreeMap<String, NetworkTableEntry> entryTreeMap;



    protected Codex<Double, ELogitech310> mDriverInputCodex, mOperatorInputCodex;

    public OperatorInput() {
        mDriverJoystick = new Joystick(0);
        mOperatorJoystick = new Joystick(1);
        drivetrainTab = Shuffleboard.getTab("drivetrain");
        driverThrottleEntry = drivetrainTab.add("Driver Throttle", 0).getEntry();

        for (int i = 0; i < Robot.DATA.driverinput.length(); i++) {
            //put strings and corresponding network table entries in the map
        }
    }

    @Override
    public void modeInit(EMatchMode pMode, double pNow) {

    }

    @Override
    public void readInputs(double pNow) {
        ELogitech310.map(Robot.DATA.driverinput, mDriverJoystick);
//        ELogitech310.map(Robot.mData.operatorinput, mOperatorJoystick);
    }

    @Override
    public void setOutputs(double pNow) {
        System.out.println("-----------------" + Robot.DATA.driverinput.get(ELogitech310.LEFT_Y_AXIS));
        driverThrottleEntry.setDouble(Robot.DATA.driverinput.get(ELogitech310.LEFT_Y_AXIS));
    }


    @Override
    public void shutdown(double pNow) {

    }



}
