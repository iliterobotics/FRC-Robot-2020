package us.ilite.robot.modules;

import com.flybotix.hfr.util.log.ILog;
import com.flybotix.hfr.util.log.Logger;
import edu.wpi.first.wpilibj.Joystick;
import us.ilite.common.types.EMatchMode;
import us.ilite.common.types.input.ELogitech310;

public class OperatorInput extends Module {
    protected static final double
            DRIVER_SUB_WARP_AXIS_THRESHOLD = 0.5;
    private ILog mLog = Logger.createLog(OperatorInput.class);

    private Joystick mDriverJoystick;
    private Joystick mOperatorJoystick;
    private EMatchMode mMode = EMatchMode.DISABLED;

    public OperatorInput() {
        mDriverJoystick = new Joystick(0);
        mOperatorJoystick = new Joystick(1);
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
        ELogitech310.map(db.driverinput, mDriverJoystick);
        ELogitech310.map(db.operatorinput, mOperatorJoystick);
    }

    @Override
    public void setOutputs(double pNow) {

    }

}
