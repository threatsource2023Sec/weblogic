package weblogic.nodemanager.server;

import java.io.IOException;
import java.util.Properties;
import weblogic.nodemanager.common.StateInfo;

public interface ServerMonitorI {
   Thread start(Properties var1) throws IOException;

   Thread start(String var1) throws IOException;

   boolean kill(Properties var1) throws InterruptedException, IOException;

   void printThreadDump(Properties var1) throws InterruptedException, IOException;

   void softRestart(Properties var1) throws IOException;

   boolean isRunningState();

   boolean isStarted();

   boolean isFinished();

   boolean isStartupAborted();

   boolean isCleanupAfterCrashNeeded() throws IOException;

   StateInfo getCurrentStateInfo();

   void initState(StateInfo var1) throws IOException;

   void cleanup();

   void waitForStarted() throws IOException;
}
