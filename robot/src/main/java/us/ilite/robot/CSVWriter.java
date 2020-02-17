package us.ilite.robot;

import com.flybotix.hfr.codex.CodexMetadata;
import com.flybotix.hfr.codex.RobotCodex;
import com.flybotix.hfr.util.log.ILog;
import com.flybotix.hfr.util.log.Logger;
import edu.wpi.first.wpilibj.DriverStation;
import us.ilite.common.config.Settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

import static us.ilite.robot.CSVLogger.kCSVLoggerQueue;

public class CSVWriter {

    private static final String LOG_PATH_FORMAT = "/logs/%s/%s-%s-%s.csv";
    private static String eventName = DriverStation.getInstance().getEventName();
    private Optional<BufferedWriter> bw;

    private final ILog mLog = Logger.createLog(CSVWriter.class);
    private static int mLogFailures;

    private File file;
    private RobotCodex<?> mCodex;

    public CSVWriter(RobotCodex<?> pCodex) {

        mCodex = pCodex;

        mLogFailures = 0;
        file = file();
        if ( file != null ) {
            handleCreation( file );
        }

        bw = Optional.empty();
        try  {
            if ( file != null ) {
                bw = Optional.of( new BufferedWriter( new FileWriter( file ) ) );
            }
        } catch ( Exception e ) {
            mLog.debug("There was an error in the constructor", e);
        }

        if ( eventName.length() <= 0 ) {
            // event name format: MM-DD-YYYY_HH-MM-SS
            eventName =  new SimpleDateFormat("MM-dd-YYYY_HH-mm-ss").format(Calendar.getInstance().getTime());
        }
    }

    public void handleCreation(File pFile) {
        //Makes every folder before the file if the CSV's parent folder doesn't exist
        if(Files.notExists(pFile.toPath())) {
            mLog.debug( pFile.getAbsoluteFile().getParentFile().mkdirs() );
        }

        //Creates the .CSV if it doesn't exist
        if(!pFile.exists()) {
            try {
                pFile.createNewFile();
            } catch (IOException e) {
                mLog.debug("Error attempting to create file: " + pFile);
            }
        }
    }

    public void log( String s ) throws IOException {

            if ( bw.isPresent() ) {
                bw.get().append(s);
                bw.get().newLine();
            }
            else {
                if ( mLogFailures < Settings.kAcceptableLogFailures ) {
                    mLog.debug("Failure with logging codex: " + mCodex.meta().getEnum().getSimpleName() );
                    mLog.debug( "Could not find Path:  (Path to USB)  on roborio! Try plugging in the USB." );
                    mLogFailures++;

                        file = file();
                        if ( file != null ) {
                            bw = Optional.of( new BufferedWriter( new FileWriter( file ) ) );
                        }
                        else {
                            bw = Optional.empty();
                        }

                }
                else if ( mLogFailures == Settings.kAcceptableLogFailures ) {
                    mLog.debug("---------------------CSV LOGGING DISABLED----------------------");
                    Robot.mCSVLogger.closeWriters();
                    mLogFailures++;
                }
            }
    }

    public void close() {
        if ( bw.isPresent() ) {
            try {
                bw.get().flush();
                bw.get().close();
            }
            catch ( Exception e ) {
               mLog.debug("Error closing the bw", e);
            }

        }
    }

    public void writeHeader() {
        kCSVLoggerQueue.add( new Log( mCodex.getCSVHeader(), mCodex.meta().gid() ) );
    }

    public CodexMetadata<?> getMetaDataOfAssociatedCodex() {
        return mCodex.meta();
    }

    public File file() {

        //THIS CODE IS USED FOR A MULTI-PARTITION DRIVE AND FINDING IT'S LOCATION IN THE ROBORIO FILE DIRECTORIES
        String dir = "";
        char[] letters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        for ( char c : letters ) {
            if (Files.exists((new File(String.format("/%c/logs/here.txt", c)).toPath()))) {
                dir = "/" + c;
                break;
            }
        }
        File file = null;
        if (mCodex.meta().getEnum().getSimpleName().equals("ELogitech310")) {
            if (mCodex.meta().gid() == Robot.DATA.driverinput.meta().gid()) {
                file = new File(String.format(dir + LOG_PATH_FORMAT,
                        eventName,
                        "DriverInput",
                        DriverStation.getInstance().getMatchType().name(),
                        DriverStation.getInstance().getMatchNumber()
                ));
            } else {
                file = new File(String.format(dir + LOG_PATH_FORMAT,
                        eventName,
                        "OperatorInput",
                        DriverStation.getInstance().getMatchType().name(),
                        DriverStation.getInstance().getMatchNumber()
                ));
            }
        } else {
            file = new File(String.format(dir + LOG_PATH_FORMAT,
                    eventName,
                    mCodex.meta().getEnum().getSimpleName(),
                    DriverStation.getInstance().getMatchType().name(),
                    DriverStation.getInstance().getMatchNumber()
            ));
        }

        mLog.debug("Creating log file at ", file.toPath());

        return file;
    }
}