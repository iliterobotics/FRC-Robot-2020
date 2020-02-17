package us.ilite.robot;
import com.flybotix.hfr.codex.CodexMetadata;
import com.flybotix.hfr.codex.RobotCodex;
import com.flybotix.hfr.util.log.ILog;
import org.junit.Test;
import us.ilite.CriticalTest;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@Category(CriticalTest.class)

public class CSVLoggerTest {

    /**
     * The purpose of this test is to ensure that the exception is put in an
     * apporpriate logger and not just printed to the screen
     */
    @Test
    public void testlogFromCodexToCSVLog_IOException() throws IOException {
        ILog mockedLog = mock(ILog.class);
        CSVWriter mockedWriter = mock(CSVWriter.class);
        CodexMetadata mockedMeta = mock(CodexMetadata.class);

        when(mockedMeta.gid()).thenReturn(123);

        when(mockedWriter.getMetaDataOfAssociatedCodex()).thenReturn(mockedMeta);
        doThrow(new IOException()).when(mockedWriter).log("HELLO");

        CSVLogger testedLogger =
                new CSVLogger(Arrays.asList(mockedWriter), false, false);
        testedLogger.mLogger = mockedLog;

        Log mockedLogEvent  = mock(Log.class);
        when(mockedLogEvent.getmLogData()).thenReturn("HELLO");
        when(mockedLogEvent.getmGlobalId()).thenReturn(123);
        testedLogger.logFromCodexToCSVLog(mockedLogEvent);

        verify(mockedLog, times(1)).debug(eq("Exception thrown while trying to log"), any(IOException.class));

    }
}