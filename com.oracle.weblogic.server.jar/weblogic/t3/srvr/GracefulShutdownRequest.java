package weblogic.t3.srvr;

import weblogic.server.ServerLogger;
import weblogic.utils.StackTraceUtils;

class GracefulShutdownRequest implements Runnable {
   private Exception exception;
   private boolean isCompleted;
   private Object syncObj;
   private boolean ignoreSessions;
   private int destinationState;
   private boolean waitForAllSessions;

   GracefulShutdownRequest(boolean ignoreSessions) {
      this.isCompleted = false;
      this.syncObj = new Object();
      this.ignoreSessions = false;
      this.waitForAllSessions = false;
      this.ignoreSessions = ignoreSessions;
      this.destinationState = 0;
   }

   GracefulShutdownRequest(boolean ignoreSessions, boolean waitForAllSessions) {
      this(ignoreSessions);
      this.waitForAllSessions = waitForAllSessions;
   }

   GracefulShutdownRequest(boolean ignoreSessions, int destinationState) {
      this.isCompleted = false;
      this.syncObj = new Object();
      this.ignoreSessions = false;
      this.waitForAllSessions = false;
      this.ignoreSessions = ignoreSessions;
      this.destinationState = destinationState;
   }

   public Exception getException() {
      return this.exception;
   }

   public boolean isCompleted() {
      return this.isCompleted;
   }

   public void run() {
      try {
         if (this.destinationState == 17) {
            T3Srvr.getT3Srvr().gracefulSuspend(this.ignoreSessions);
         } else {
            T3Srvr.getT3Srvr().gracefulShutdown(this.ignoreSessions, this.waitForAllSessions);
         }
      } catch (RuntimeException var4) {
         ServerLogger.logServerRuntimeError(var4.toString());
         ServerLogger.logServerRuntimeError(StackTraceUtils.throwable2StackTrace(var4));
         this.exception = var4;
      } catch (Exception var5) {
         this.exception = var5;
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
