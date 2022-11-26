package weblogic.kernel;

import java.rmi.RemoteException;
import weblogic.work.ExecuteThread;

public class QueueThrottleException extends RemoteException {
   private static final long serialVersionUID = 9045249449980529886L;
   private static final boolean DEBUG = true;

   public QueueThrottleException() {
      this.log((String)null, (Throwable)null);
   }

   public QueueThrottleException(String msg) {
      super(msg);
      this.log(msg, (Throwable)null);
   }

   public QueueThrottleException(String msg, Throwable throable) {
      super(msg, throable);
      this.log(msg, throable);
   }

   private void log(String msg, Throwable th) {
      if (KernelStatus.isServer()) {
         Thread thread = Thread.currentThread();
         String wmName = null;

         try {
            if (thread instanceof ExecuteThread) {
               wmName = ((ExecuteThread)thread).getWorkManager().getName();
            }
         } catch (NullPointerException var6) {
         }

         KernelLogger.logDebugQueueThrottle(wmName, thread.getName(), msg, th);
      }
   }
}
