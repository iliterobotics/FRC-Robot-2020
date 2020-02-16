package us.ilite.robot.controller;

import com.team319.trajectory.Path;
import java.util.Map;

public class KateAutonController extends BaseAutonController {
    public Path mActivePath = null;
    public Map<String, Path> mAvailablePaths;

    public KateAutonController() {
        mAvailablePaths = getAvailablePaths("kate");
    }

    @Override
    public void updateImpl(double pNow) {

    }
}
