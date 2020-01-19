package us.ilite.common;

import com.flybotix.hfr.codex.Codex;
import com.flybotix.hfr.codex.CodexSender;
import com.flybotix.hfr.io.MessageProtocols;
import com.flybotix.hfr.io.sender.ISendProtocol;
import com.flybotix.hfr.util.log.ILog;
import com.flybotix.hfr.util.log.Logger;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import us.ilite.common.config.Settings;
import us.ilite.common.io.CodexNetworkTables;
import us.ilite.common.io.CodexNetworkTablesParser;
import us.ilite.common.io.CodexCsvLogger;
import us.ilite.common.lib.util.SimpleNetworkTable;
import us.ilite.common.types.EFlywheelSubsystem;
import us.ilite.common.types.ETargetingData;
import us.ilite.common.types.drive.EDriveData;
import us.ilite.common.types.input.EDriverInputMode;
import us.ilite.common.types.input.ELogitech310;
import us.ilite.common.types.sensor.EGyro;
import us.ilite.common.types.sensor.EPowerDistPanel;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class Data {

    public CodexNetworkTables mCodexNT = CodexNetworkTables.getInstance();
    private final ILog mLogger = Logger.createLog(Data.class);
    
    //Add new codexes here as we need more

    public final Codex<Double, EGyro> imu = Codex.of.thisEnum(EGyro.class);
    public final Codex<Double, EDriveData> drivetrain = Codex.of.thisEnum(EDriveData.class);
    public final Codex<Double, ELogitech310> driverinput = Codex.of.thisEnum(ELogitech310.class);
    public final Codex<Double, ELogitech310> operatorinput = Codex.of.thisEnum(ELogitech310.class);
    public final Codex<Double, EPowerDistPanel> pdp = Codex.of.thisEnum(EPowerDistPanel.class);
    public Codex<Double, ETargetingData> limelight = Codex.of.thisEnum(ETargetingData.class);
    public Codex<Double, EFlywheelSubsystem> flywheel = Codex.of.thisEnum(EFlywheelSubsystem.class);


    public final Codex[] mAllCodexes = new Codex[] {
            imu, /*drivetrain,*/ driverinput, operatorinput, pdp, /*limelight,*/
    };

    public final Codex[] mLoggedCodexes = new Codex[] {
        imu, drivetrain, driverinput, /*operatorinput,*/  pdp, limelight
    };

    public final Codex[] mDisplayedCodexes = new Codex[] {
            imu, /*drivetrain,*/ driverinput, operatorinput, pdp
    };

    //Stores writers per codex needed for CSV logging
    private Map<String, Writer> mNetworkTableWriters = new HashMap<>();

    private List<CodexCsvLogger> mCodexCsvLoggers;

    /**
     * Create a Data object based on whether or not it is being used for logging
     * @param pLogging
     */
    public Data(boolean pLogging) {
        if(pLogging) {
            initParsers();
        }
    }

    public Data() {
        this(true);
    }

    private void initParsers() {

        mCodexCsvLoggers = new ArrayList<>();
    }

    public void logFromCodexToCSVHeader() {
        // Check that the USB drivetrain is still plugged in
//        if(Files.exists(new File(CodexCsvLogger.USB_DIR).toPath())) {
            mCodexCsvLoggers.forEach(c -> c.writeHeader());
//        }
    }
    public void logFromCodexToCSVLog() {
        // Check that the USB drivetrain is still plugged in
//        if(Files.exists(new File(CodexCsvLogger.USB_DIR).toPath())) {
            mCodexCsvLoggers.forEach(c -> c.writeLine());
//        }
    }

    /**
     * Closes all the writers in mNetworkTableWriters
     */
    public void closeWriters() {
        for (Writer writer : mNetworkTableWriters.values()) {
            try {
                writer.flush();
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        mCodexCsvLoggers.forEach(c -> c.closeWriter());
    }

    /**
     * Makes the log file if it doesn't already exist
     */
    public static void handleCreation(File pFile) {
        //Makes every folder before the file if the CSV's parent folder doesn't exist
        if(Files.notExists(pFile.toPath())) {
            pFile.getAbsoluteFile().getParentFile().mkdirs();
        }

        //Creates the .CSV if it doesn't exist
        if(!pFile.exists()) {
            try {
                pFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}