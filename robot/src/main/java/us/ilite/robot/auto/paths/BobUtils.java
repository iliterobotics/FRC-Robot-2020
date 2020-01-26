package us.ilite.robot.auto.paths;

import com.team2363.commands.HelixFollower;
import com.team319.trajectory.Path;

import static com.team319.trajectory.Path.SegmentValue.*;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import static java.lang.Math.*;

import java.util.HashMap;
import java.util.Map;

public class BobUtils {

    static final double FEET_TO_METERS = 0.3048;
    static final double METERS_TO_FEET = 1/FEET_TO_METERS;

    private static final Map<String, Double> mMaxTimeCache = new HashMap<>();
    private HelixFollower hf;

    public static double getMeters(Path pPath, Path.SegmentValue pKey, int i) {
        return pPath.getValue(i, pKey) * FEET_TO_METERS;
    }

    /**
     * @param pPath
     * @param i
     * @return the calculated curvature in
     */
    public static double calculateCurvature(Path pPath, int i) {
        Trajectory t;
        // https://www.math24.net/curvature-radius/
        // https://math.dartmouth.edu/~m8f19/lectures/m8f19curvature.pdf
        // Ref edu.wpi.first.wpilibj.spline.Spline
        double dt = pPath.getValue(i, TIME_STAMP);
        double dx = (getMeters(pPath, X, i) - getMeters(pPath, X, i-1))/dt/dt;
        double ddx = (getMeters(pPath, X, i) - getMeters(pPath, X, i-2))/dt/dt;
        double dy = (getMeters(pPath, Y, i) - getMeters(pPath, Y, i-1))/dt/dt;
        double ddy = (getMeters(pPath, Y, i) - getMeters(pPath, Y, i-2))/dt/dt;

//        double result = abs(dx*ddy - dy*ddx) / pow(abs(pow(dx, 2.0) + pow(dy, 2.0)), 1.5);

        double result = (dx * ddy - ddx * dy) / ((dx * dx + dy * dy) * Math.hypot(dx, dy));
        if(result == Double.NaN) {
            result = 0d;
        }
        return result;
    }

    public static Trajectory.State sample(Path pPath, double pNow, double pPathStartTimestamp) {
        int i = getIndexForCumulativeTime(pPath, pNow, pPathStartTimestamp);
        if(i == -1) return null;

        double curve = 0d;
        if(i > 1) {
            curve = calculateCurvature(pPath, i);
        }


        return new Trajectory.State(
                pNow - pPathStartTimestamp,
                getMeters(pPath, CENTER_VELOCITY, i),
                getMeters(pPath, CENTER_ACCELERATION, i),
                new Pose2d(
                        getMeters(pPath, X, i),
                        getMeters(pPath, Y, i),
                        new Rotation2d(
                            pPath.getValue(i, HEADING)
                        )
                ),
                curve
        );
    }

    /**
     * Determines the index of a BobTrajectory path based upon the path's start time and the current timestamp. It is
     * best to use the FPGA timestamp when calling this method.
     *
     * @param pNow - The current cycle's time
     * @param pPathStartTimestamp - The timestamp of when the path was started
     * @return a 0-based index that determines the index of the input Path. Returns -1 if the time is greater than the path length.
     */
    public static int getIndexForCumulativeTime(Path pPath, double pNow, double pPathStartTimestamp) {
        double dt = pNow - pPathStartTimestamp;
        // Check path overrun
        if(
                pNow < pPathStartTimestamp ||
                dt > getPathTotalTime(pPath)
        ) {
            return -1;
        }
        return (int)(dt / 0.020);
    }

    public static double getPathMaxVelocity(Path pPath) {
        double velocity = 0;
        for (int i = 0; i < pPath.getSegmentCount(); i++) {
            velocity = max(velocity, pPath.getValue(i, CENTER_VELOCITY));
        }
        return velocity;
    }

    /**
     * Determines the path's maximum allotted time
     *  Caches the result for future use so this is only calculated once per path.
     * @param pPath
     * @return
     */
    public static double getPathTotalTime(Path pPath) {
        String key = pPath.getClass().getSimpleName();
        if(mMaxTimeCache.containsKey(key)) {
            return mMaxTimeCache.get(key);
        }
        double time = 0;
        for (int i = 0; i < pPath.getSegmentCount(); i++) {
            time += pPath.getValue(i, TIME_STAMP);
        }
        mMaxTimeCache.put(key, time);
        return time;
    }

    public static double getPathTotalDistanceFt(Path pPath) {
        double distance = 0;
        for (int i = 1; i < pPath.getSegmentCount(); i++) {
            distance += calculateDistance(
                    pPath.getValue(i-1, X),
                    pPath.getValue(i-1, Y),
                    pPath.getValue(i, X),
                    pPath.getValue(i, Y)
            );
        }
        return distance;
    }

    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        return sqrt(pow(x2-x1,2) + pow(y2-y1,2));
    }
}
