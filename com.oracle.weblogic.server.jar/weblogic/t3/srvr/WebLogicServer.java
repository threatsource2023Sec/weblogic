package weblogic.t3.srvr;

import org.jvnet.hk2.annotations.Contract;
import weblogic.common.T3ServicesDef;
import weblogic.logging.LogOutputStream;
import weblogic.server.ServerLifecycleException;
import weblogic.server.lifecycle.WebLogicServerRunState;

@Contract
public interface WebLogicServer extends WebLogicServerRunState {
   T3ServicesDef getT3Services();

   void setFailedState(Throwable var1, boolean var2);

   void setFailedState(Throwable var1, boolean var2, boolean var3);

   void setFailedStateFromCallback(Throwable var1, boolean var2);

   boolean isLifecycleExceptionThrown();

   void setLifecycleExceptionThrown(boolean var1);

   boolean isSvrStarting();

   boolean isStarted();

   void setSvrStarting(boolean var1);

   boolean isShuttingDown();

   boolean isShutdownDueToFailure();

   boolean setPreventShutdownHook();

   void setLockoutManager();

   ServerLockoutManager getLockoutManager();

   void forceShutdown() throws ServerLifecycleException;

   void gracefulShutdown(boolean var1) throws ServerLifecycleException;

   void gracefulShutdown(boolean var1, boolean var2) throws ServerLifecycleException;

   String cancelShutdown();

   void requestShutdownFromConsole() throws SecurityException;

   void forceSuspend() throws ServerLifecycleException;

   void gracefulSuspend(boolean var1) throws ServerLifecycleException;

   void resume() throws ServerLifecycleException;

   String getStartupMode() throws ServerLifecycleException;

   void setState(int var1);

   String getState();

   int getStableState();

   void setStarted(boolean var1);

   void logStartupStatistics();

   boolean isAbortStartupAfterAdminState();

   void abortStartupAfterAdminState() throws ServerLifecycleException;

   void setFallbackState(int var1);

   long getStartTime();

   void setStartupTime(long var1);

   long getStartupTime();

   void setShutdownWaitSecs(int var1);

   void initializeServerRuntime(ServerRuntime var1);

   ThreadGroup getStartupThreadGroup();

   LogOutputStream getLog();

   void failed(String var1);

   void failedForceShutdown(String var1);

   void exitImmediately(Throwable var1);

   void haltImmediately();
}
