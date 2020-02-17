package us.ilite.robot;

import com.flybotix.hfr.codex.RobotCodex;
import com.flybotix.hfr.util.log.ILog;
import com.flybotix.hfr.util.log.Logger;
import us.ilite.common.config.Settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CSVLogger {
    public static LinkedBlockingDeque<Log> kCSVLoggerQueue = new LinkedBlockingDeque<>();
    private final Collection<CSVWriter> mCSVWriters;
    ILog mLogger = Logger.createLog(this.getClass());
    private static final ScheduledExecutorService mExService =
            Executors.newSingleThreadScheduledExecutor((run)->new Thread(run, "My timer thread"));
    private final ScheduledFuture<?> scheduledFuture;
    private boolean mIsAcceptingToQueue;

    public CSVLogger( ) {
        this(Robot.DATA.mLoggedCodexes, true, true);
    }

    CSVLogger(RobotCodex [] codexes, boolean logHeader, boolean startTimer) {
        this(Arrays.stream(codexes).map(CSVWriter::new).collect(Collectors.toList()), logHeader, startTimer);

    }
    CSVLogger(Collection<CSVWriter>writers, boolean logHeader, boolean startTimer) {
        mCSVWriters = writers;
        mIsAcceptingToQueue = false;
        if(logHeader) {
            logFromCodexToCSVHeader();
        }

        ScheduledFuture<?> scheduled = null;
        if(startTimer) {
            scheduled = mExService.scheduleAtFixedRate(this::run, Settings.kSecondsToUpdateCSVLogger, Settings.kSecondsToUpdateCSVLogger, TimeUnit.SECONDS);
        }
        scheduledFuture = scheduled;
    }

    void run() {
        if ( !kCSVLoggerQueue.isEmpty() ) {
            try {
                ArrayList<Log> kTempCSVLogs = new ArrayList<>();
                kCSVLoggerQueue.drainTo(kTempCSVLogs);
                for ( Log log : kTempCSVLogs ) {
                    logFromCodexToCSVLog( log );
                }
            } catch (Exception e) {
                mLogger.debug("Caught an unexpected exception in the run method: " , e);
            }
        }
    }

    public void logFromCodexToCSVHeader() {
        mCSVWriters.forEach(c -> c.writeHeader());
    }
    
    public void logFromCodexToCSVLog( Log pLog ) {
        for ( CSVWriter c : mCSVWriters ) {
            if ( c.getMetaDataOfAssociatedCodex().gid() == pLog.getmGlobalId() ) {
                try {
                    c.log( pLog.getmLogData() );
                } catch (IOException e) {
                    mLogger.debug("Exception thrown while trying to log", e);
                }
                break;
            }
        }
    }

    /**
     * Opens the queue
     */
    public void start() {
        mIsAcceptingToQueue = true;
    }

    /**
     * Closes the queue
     */
    public void stop() {
        mIsAcceptingToQueue = false;
    }

    public void addToQueue( Log pLog ) {
        if ( mIsAcceptingToQueue ) {
            kCSVLoggerQueue.add( pLog );
        }
    }

    public void closeWriters() {
        for ( CSVWriter cw : mCSVWriters ) {
            cw.close();
        }
    }

}