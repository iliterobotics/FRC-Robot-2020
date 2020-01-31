package us.ilite.robot.modules;

import com.flybotix.hfr.util.log.ILog;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import  org.mockito.Mock;

public class ModuleListTest {

    private ModuleList aList;

    @Before
    public void init() {
        aList = new ModuleList();
        aList.mLogger =mock(ILog.class);
    }

    @Test
    public void test_addModule_addNull() {
        aList.addModule(null);

        verify(aList.mLogger, times(1)).error(any());

        assertFalse(aList.mModules.contains(null));
    }
    @Test
    public void test_addModule_validModule() {
        Module fakeModule = mock(Module.class);

        aList.addModule(fakeModule);
        assertTrue(aList.mModules.contains(fakeModule));
        verify(aList.mLogger, never()).error(any());
        verify(aList.mLogger, times(1)).debug("You added a module!");
    }
}
