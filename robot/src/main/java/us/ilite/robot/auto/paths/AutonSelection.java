package us.ilite.robot.auto.paths;

import com.team319.trajectory.Path;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import us.ilite.LazyReference;
import us.ilite.robot.controller.*;

import java.lang.reflect.InvocationTargetException;

public class AutonSelection {
    public static ShuffleboardTab mAutonConfiguration = Shuffleboard.getTab("Auton Configuration");
    public static int mDelaySeconds;
    private SendableChooser<AUTON_CONTROLLERS> mSendableAutonControllers = new SendableChooser<>();

    private enum AUTON_CONTROLLERS {
        AUTON_CALIBRATION("Auton Calibration", new LazyReference<>(()->{
            return new AutonCalibration();
        })),
        LINE_AUTON_CONTROLLER("Line Auton Controller",new LazyReference<>(()->{
            return new LineAutonController();
        })),
        SHOOT_INTAKE_CONTROLLER("Shoot Auton Controller",new LazyReference<>(()->{
            return new ShootIntakeController();
        }));

        private final String displayedName;
        private final LazyReference<BaseAutonController> autonController;
        private AUTON_CONTROLLERS(String displayedName, LazyReference<BaseAutonController> autonController) {
            this.displayedName = displayedName;
            this.autonController = autonController;
        }

        public BaseAutonController getAutonController() {
            return autonController.getOrCompute();
        }
    }

    public AutonSelection() {
       mDelaySeconds = ((Double) (mAutonConfiguration.add("Path Delay Seconds", 0)
               .withPosition(2, 0)
               .withSize(2, 1)
               .getEntry()
               .getDouble(0.0)))
               .intValue();

        mSendableAutonControllers.setDefaultOption("Default - Auton Calibration", AUTON_CONTROLLERS.AUTON_CALIBRATION);

        for(AUTON_CONTROLLERS auton_controllers : AUTON_CONTROLLERS.values()) {
            mSendableAutonControllers.addOption(auton_controllers.displayedName ,auton_controllers);
        }

        mAutonConfiguration.add("Choose Auton Controller", mSendableAutonControllers)
            .withPosition(0, 0)
            .withSize(2, 1);
    }

    public BaseAutonController getSelectedAutonController() {

        return mSendableAutonControllers.getSelected().autonController.getOrCompute();
    }
}
