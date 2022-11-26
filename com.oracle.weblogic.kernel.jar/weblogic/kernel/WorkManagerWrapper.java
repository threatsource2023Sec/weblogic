package weblogic.kernel;

import weblogic.work.RequestManager;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public class WorkManagerWrapper extends ExecuteThreadManager {
   private boolean shutdownRequested = false;

   public WorkManagerWrapper(String policyName) {
      super(policyName);
   }

   public boolean isShutdownInProgress() {
      return this.shutdownRequested;
   }

   ExecuteThread[] getExecuteThreads() {
      return new ExecuteThread[0];
   }

   public int getExecuteQueueDepth() {
      return RequestManager.getInstance().getQueueDepth();
   }

   public int getExecuteQueueSize() {
      return 65000;
   }

   public int getExecuteQueueDepartures() {
      return (int)RequestManager.getInstance().getQueueDepartures();
   }

   public int getExecuteThreadCount() {
      return RequestManager.getInstance().getExecuteThreadCount();
   }

   public void setThreadCount(int count) throws SecurityException {
   }

   synchronized ExecuteThread[] getStuckExecuteThreads(long maxTime) {
      return new ExecuteThread[0];
   }

   void shutdown() throws SecurityException {
      this.shutdownRequested = true;
   }

   public int getIdleThreadCount() {
      return RequestManager.getInstance().getIdleThreadCount();
   }

   void registerIdle(ExecuteThread t) {
   }

   void execute(final ExecuteRequest r, boolean mayThrottle) {
      WorkManagerFactory.getInstance().getDefault().schedule(new WorkAdapter() {
         public void run() {
            try {
               r.execute((ExecuteThread)null);
            } catch (Exception var2) {
               throw new RuntimeException(var2);
            }
         }
      });
   }

   boolean executeIfIdle(final ExecuteRequest r) {
      return WorkManagerFactory.getInstance().getDefault().executeIfIdle(new WorkAdapter() {
         public void run() {
            try {
               r.execute((ExecuteThread)null);
            } catch (Exception var2) {
               throw new RuntimeException(var2);
            }
         }
      });
   }

   int getPendingTasksCount() {
      return 0;
   }
}
