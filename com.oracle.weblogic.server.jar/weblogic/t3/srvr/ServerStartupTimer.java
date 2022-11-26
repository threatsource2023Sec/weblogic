package weblogic.t3.srvr;

import java.util.Timer;
import java.util.TimerTask;
import weblogic.diagnostics.debug.DebugLogger;

final class ServerStartupTimer extends Thread {
   private static final DebugLogger debugSUT = DebugLogger.getDebugLogger("DebugServerStartupTimer");
   public static final int NON_RESTARTABLE_EXIT_CODE = -1;
   public static final int MINIMUM_INTERVAL = 10000;
   public static final int DEFAULT_INTERVAL = 60000;
   private static ServerStartupTimer THE_ONE;
   private static Timer startupTimer = null;
   private static long timeout;
   private static int numOfDiagDumps = 0;
   private static int diagDumpInterval = 0;

   private ServerStartupTimer() {
   }

   static synchronized void startTimeBomb(long timebomb, int numOfDiagDumps, int diagDumpInterval) {
      timeout = timebomb;
      ServerStartupTimer.numOfDiagDumps = numOfDiagDumps;
      ServerStartupTimer.diagDumpInterval = diagDumpInterval;
      if (debugSUT.isDebugEnabled()) {
         debugSUT.debug("startup timer startTimeBomb is called. timeout:" + timeout + ", diag dump interval:" + diagDumpInterval + ", num of diag dumps:" + numOfDiagDumps);
      }

      cancelStartupTimeBomb();
      THE_ONE = new ServerStartupTimer();
      THE_ONE.setDaemon(true);
      THE_ONE.start();
   }

   public static void cancelStartupTimeBomb() {
      if (startupTimer != null) {
         startupTimer.cancel();
      }

   }

   public void run() {
      if (timeout > 0L) {
         startupTimer = new Timer();
         startupTimer.schedule(new StartupTimer(numOfDiagDumps), timeout, (long)diagDumpInterval);
         if (debugSUT.isDebugEnabled()) {
            debugSUT.debug("startup timer is scheduled. timeout:" + timeout + ", diag dump interval:" + diagDumpInterval + ", num of diag dumps:" + numOfDiagDumps);
         }
      }

   }

   private static void debug(String line) {
      System.out.println("[ServerStartupTimer] " + line);
   }

   public class StartupTimer extends TimerTask {
      private int numOfDiagDumps = 0;

      public StartupTimer(int numOfDiagDumps) {
         this.numOfDiagDumps = numOfDiagDumps;
      }

      public void run() {
         if (this.numOfDiagDumps <= 0) {
            T3Srvr.getT3Srvr().setFailedState(T3Srvr.STARTUP_TIMED_OUT, false);
            SrvrUtilities.logThreadDump();
            this.cancel();
         } else {
            SrvrUtilities.logThreadDump();
            --this.numOfDiagDumps;
         }

      }
   }
}
