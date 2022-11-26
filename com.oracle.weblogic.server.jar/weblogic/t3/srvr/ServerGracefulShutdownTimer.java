package weblogic.t3.srvr;

import java.util.Timer;
import java.util.TimerTask;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.server.ServerLifecycleException;

final class ServerGracefulShutdownTimer extends Thread {
   private static final DebugLogger debugSUT = DebugLogger.getDebugLogger("DebugServerShutdownTimer");
   public static final int NON_RESTARTABLE_EXIT_CODE = -1;
   private static ServerGracefulShutdownTimer THE_ONE;
   private static Timer gracefulShutdownTimer = null;
   private static long timeout;
   private static int timeoutNumOfThreadDump;
   private static int timeoutThreadDumpInterval;

   private ServerGracefulShutdownTimer() {
   }

   static synchronized void startTimeBomb(long timebomb, int numOfThreadDump, int threadDumpInterval) {
      timeout = timebomb;
      timeoutNumOfThreadDump = numOfThreadDump;
      timeoutThreadDumpInterval = threadDumpInterval;
      THE_ONE = new ServerGracefulShutdownTimer();
      THE_ONE.setDaemon(true);
      THE_ONE.start();
   }

   public static void cancelGracefulShutdownTimeBomb() {
      if (gracefulShutdownTimer != null) {
         gracefulShutdownTimer.cancel();
      }

   }

   public void run() {
      if (timeout > 0L) {
         gracefulShutdownTimer = new Timer();
         gracefulShutdownTimer.schedule(new ShutdownTimer(timeoutNumOfThreadDump), timeout, (long)timeoutThreadDumpInterval);
         if (debugSUT.isDebugEnabled()) {
            debugSUT.debug("graceful shutdown timer is scheduled. timeout:" + timeout + ", num of diag dumps:" + timeoutNumOfThreadDump + ", diag dump interval:" + timeoutThreadDumpInterval);
         }
      }

   }

   public class ShutdownTimer extends TimerTask {
      private int numOfDiagDumps = 0;

      public ShutdownTimer(int numOfDiagDumps) {
         this.numOfDiagDumps = numOfDiagDumps;
      }

      public void run() {
         if (ServerGracefulShutdownTimer.debugSUT.isDebugEnabled()) {
            ServerGracefulShutdownTimer.debugSUT.debug("graceful shutdown timer is kicked. num of diag dumps:" + this.numOfDiagDumps);
         }

         if (this.numOfDiagDumps <= 0) {
            this.cancel();

            try {
               T3Srvr.getT3Srvr().forceShutdown();
            } catch (ServerLifecycleException var2) {
            }
         } else {
            SrvrUtilities.logThreadDump();
            --this.numOfDiagDumps;
         }

      }
   }
}
