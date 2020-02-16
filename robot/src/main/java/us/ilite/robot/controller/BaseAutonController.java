package us.ilite.robot.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.team2363.commands.HelixFollower;
import com.team2363.commands.IliteHelixFollower;
import com.team2363.controller.PIDController;
import com.team319.trajectory.Path;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import javassist.tools.reflect.Reflection;
import org.reflections.Reflections;
import us.ilite.common.Distance;
import us.ilite.common.config.Settings;
import us.ilite.common.types.drive.EDriveData;
import us.ilite.common.types.sensor.EGyro;
import us.ilite.robot.Robot;
import us.ilite.robot.auto.paths.BobUtils;
import us.ilite.robot.modules.EDriveState;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BaseAutonController extends AbstractController {

    private Map<String, Path> mPaths = BobUtils.getAvailablePaths();
    private ShuffleboardTab mAutonConfiguration;
    private int mPathNumber;
    private double mPathDelay;

    protected Path mActivePath = null;
    private final Distance mPathTotalDistance;
    protected double mPathStartTime = 0d;
    protected double mDelayCycleCount;
    private HelixFollowerImpl mPathFollower = null;

    public BaseAutonController() {
         mAutonConfiguration = Shuffleboard.getTab("Auton Config");
        mAutonConfiguration.addPersistent("Path Selection", "Select paths by clicking on the 'Path Number' slider dot and using arrow keys").withPosition(0, 1).withSize(4, 1);
        mPathDelay = mAutonConfiguration.add("Path Delay Seconds", 0).getEntry().getDouble(0.0);
        mPathNumber = mAutonConfiguration.add("Path Number", 1)
                .withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(Map.of("min", 0, "max", 10, "block increment", 1))
                .getEntry()
                .getNumber(0.0)
                .intValue();
        int pathIndex = 0;
        for (Map.Entry<String, Path> entry : mPaths.entrySet()) {
            mAutonConfiguration.addPersistent(entry.getKey(), pathIndex).withSize(1, 1).withPosition(pathIndex, 2);
            pathIndex++;
        }

        // Set active path equal to the path of the index selected in shuffleboard.
        setActivePath(mPaths.get((String) mPaths.keySet().toArray()[mPathNumber]));
        mDelayCycleCount = mPathDelay / .02;
        mPathTotalDistance = BobUtils.getPathTotalDistance(mActivePath);
    }

    @Override
    protected void updateImpl(double pNow) {
        if (mDelayCycleCount == 0) {
            if(mPathStartTime == 0) {
                mPathStartTime = pNow;
            }
            if (mPathFollower != null && mPathFollower.isFinished()) {
                mPathFollower = null;
            }
            if(mPathFollower == null) {
                stopDrivetrain(pNow);
            } else {
                mPathFollower.execute(pNow);
            }

        } else {
            mDelayCycleCount--;
        }

    }

//    public Set<Class<? extends BaseAutonController>> getAvailableControllerClasses() {
//        Reflections reflections = new Reflections(Settings.CONTROLLER_PATH_PACKAGE);
//        if (reflections == null) {
//            return Collections.emptySet();
//        } else {
//            return reflections.getSubTypesOf(BaseAutonController.class);
//        }
//    }
//
//    public List<BaseAutonController> getAvailableAutonControllers() {
//        Set<Class<? extends BaseAutonController>> allClasses = getAvailableControllerClasses();
//        List<BaseAutonController> availableControllers = new ArrayList<>();
//        for(Class<?> c : allClasses) {
//
//        }
//        return availablePaths;
//    }
//
//    public Map<String, Path> getAvailablePaths(String baseName) {
//        Map<String, Path> mAvailablePaths = BobUtils.getAvailablePaths();
//        for (Map.Entry<String, Path> entry : mAvailablePaths.entrySet()) {
//            String key = entry.getKey();
//            if (!key.toLowerCase().contains(baseName.toLowerCase())) {
//                mAvailablePaths.remove(key);
//            }
//        }
//        return mAvailablePaths;
//    }

    public static final void e() {
        System.out.println("================================================");
    }

    protected void setActivePath(Path pPath) {
        mActivePath = pPath;
        mPathFollower = new HelixFollowerImpl(mActivePath);
        mPathFollower.initialize();
    }

    private class HelixFollowerImpl extends IliteHelixFollower {
        /**
         * Used as a multi-threaded caching buffer
         */
        private double mLastDistance = 0d;
        private double mLastHeading = 0d;
        private PIDController mDistanceController = new PIDController(
                1.5, 0.0, 0.0);
        private PIDController mHeadingController = new PIDController(
                0.001, 0.0, 0.0);
        private double mPathStartTime = 0.0;

        /**
         * This will import the path class based on the name of the path provided
         *
         * @param path the name of the path to run
         */
        public HelixFollowerImpl(Path path) {
            super(path);
        }

        @Override
        public void resetDistance() {
            mPathStartTime = Robot.CLOCK.getCurrentTime();
            mLastDistance = 0;
            db.drivetrain.set(EDriveData.STATE, EDriveState.RESET);
        }

        @Override
        public PIDController getHeadingController() {
            return mHeadingController;
        }

        @Override
        public PIDController getDistanceController() {
            return mDistanceController;
        }

        @Override
        public double getCurrentDistance() {
            // There is a small chance this fires after data.reset() but before the modules' readInput has run
//                mLastDistance = (db.drivetrain.get(EDriveData.L_ACTUAL_POS_FT) +
//                                db.drivetrain.get(EDriveData.R_ACTUAL_POS_FT)
//                ) / 2.0;
            return db.drivetrain.get(EDriveData.L_ACTUAL_POS_FT);
        }

        @Override
        public double getCurrentHeading() {
            // There is a small chance this fires after data.reset() but before the modules' readInput has run
            if (db.imu.isSet(EGyro.HEADING_DEGREES)) {
                mLastHeading = db.imu.get(EGyro.HEADING_DEGREES) * Math.PI / 180.0;
            }
            return mLastHeading;
        }

        @Override
        public void useOutputs(double left, double right) {
            db.drivetrain.set(EDriveData.STATE, EDriveState.PATH_FOLLOWING_HELIX);
            db.drivetrain.set(EDriveData.L_PATH_FT_s, left);
            db.drivetrain.set(EDriveData.R_PATH_FT_s, right);
            db.drivetrain.set(EDriveData.PATH_ERR_ft, mDistanceController.getError());
        }

        protected void moveToNextSegment(double pNow) {
            currentSegment = BobUtils.getIndexForCumulativeTime(mActivePath, pNow, mPathStartTime);
            if (currentSegment == -1) {
                isFinished = true;
            }
        }

        public void execute(double pNow) {
            super.execute();
            moveToNextSegment(pNow);
            super.calculateOutputs();
            if (isFinished()) {
                stopDrivetrain(0.0);
            }
        }
    }
}
