package weblogic.t3.srvr;

public abstract class GracefulRequest implements Runnable {
   private Throwable throwable;
   private volatile boolean isCompleted = false;
   private Object syncObj = new Object();
   protected boolean ignoreSessions = false;
   protected Object operation;
   protected int timeout;
   protected boolean waitForAllSessions = false;
   protected String resourceGroupName;
   protected String partitionName;

   GracefulRequest(Object operation, boolean ignoreSessions, boolean waitForAllSessions, int timeout) {
      this.operation = operation;
      this.ignoreSessions = ignoreSessions;
      this.waitForAllSessions = waitForAllSessions;
      this.timeout = timeout;
   }

   GracefulRequest(Object operation, String partitionName, String resourceGroupName, boolean ignoreSessions, boolean waitForAllSessions, int timeout) {
      this.partitionName = partitionName;
      this.operation = operation;
      this.ignoreSessions = ignoreSessions;
      this.waitForAllSessions = waitForAllSessions;
      this.timeout = timeout;
      this.resourceGroupName = resourceGroupName;
   }

   public Throwable getException() {
      return this.throwable;
   }

   public boolean isCompleted() {
      return this.isCompleted;
   }

   protected abstract boolean isNextSuccessStateShutdown();

   protected abstract boolean isNextSuccessStateSuspend();

   protected abstract void execSuspend();

   protected abstract void execShutdown();

   public void run() {
      try {
         if (this.isNextSuccessStateShutdown()) {
            this.execShutdown();
         } else {
            if (!this.isNextSuccessStateSuspend()) {
               throw new IllegalArgumentException("Unexpected graceful operation " + this.operation.toString());
            }

            this.execSuspend();
         }
      } catch (Throwable var4) {
         this.throwable = var4;
      }

      this.isCompleted = true;
      synchronized(this.syncObj) {
         this.syncObj.notify();
      }
   }

   public void waitForCompletion(int timeout) {
      if (!this.isCompleted) {
         synchronized(this.syncObj) {
            if (!this.isCompleted) {
               try {
                  this.syncObj.wait((long)timeout);
               } catch (InterruptedException var5) {
               }
            }
         }
      }

   }
}
