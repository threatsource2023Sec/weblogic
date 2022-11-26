package weblogic.work.concurrent.spi;

import weblogic.work.concurrent.ExecutorDaemonThread;
import weblogic.work.concurrent.ManagedThread;

public interface DaemonThreadManager {
   int getRunningThreadCount();

   long getCompletedThreads();

   long getRejectedThreads();

   int getLimit();

   boolean start();

   boolean stop();

   boolean isStarted();

   void shutdownThreadsSubmittedBy(String var1);

   ExecutorDaemonThread createAndStart(DaemonThreadTask var1) throws RejectException;

   ManagedThread create(Runnable var1, ContextProvider var2, int var3) throws RejectException;
}
