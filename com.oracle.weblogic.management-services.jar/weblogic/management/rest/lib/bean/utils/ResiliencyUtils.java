package weblogic.management.rest.lib.bean.utils;

import org.glassfish.admin.rest.debug.DebugLogger;

public class ResiliencyUtils {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger(ResiliencyUtils.class);

   public static boolean doResilientWork(ResiliencyConfig rc, ResilientWorker worker) throws Exception {
      long startTime = System.currentTimeMillis();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("resilient work maxWait=" + rc.maxWait() + " connectionTimeout=" + rc.connectTimeout() + " readTimeout=" + rc.readTimeout() + " started=" + DebugLogger.formatDate(startTime) + " " + worker.getDescription());
      }

      long maxEndTime = startTime + (long)rc.maxWait();
      boolean first = true;

      long duration;
      while(first || System.currentTimeMillis() < maxEndTime) {
         first = false;
         duration = System.currentTimeMillis();
         if (worker.doWork(rc, startTime)) {
            if (DEBUG.isDebugEnabled()) {
               long duration = System.currentTimeMillis() - startTime;
               DEBUG.debug("resilient work completed maxWait=" + rc.maxWait() + " connectionTimeout=" + rc.connectTimeout() + " readTimeout=" + rc.readTimeout() + " started=" + DebugLogger.formatDate(startTime) + " duration=" + duration + " ms " + worker.getDescription());
            }

            return true;
         }

         sleepBeforeRetry(duration, rc, startTime, worker);
      }

      if (DEBUG.isDebugEnabled()) {
         duration = System.currentTimeMillis() - startTime;
         DEBUG.debug("resilient work timed out maxWait=" + rc.maxWait() + " connectionTimeout=" + rc.connectTimeout() + " readTimeout=" + rc.readTimeout() + " started=" + DebugLogger.formatDate(startTime) + " duration=" + duration + " ms " + worker.getDescription());
      }

      return false;
   }

   private static void sleepBeforeRetry(long startTimeOfLastRequest, ResiliencyConfig rc, long startTime, ResilientWorker worker) throws Exception {
      long retryInterval = (long)(rc.connectTimeout() + rc.readTimeout());
      long retryTime = startTimeOfLastRequest + retryInterval;
      long currentTime = System.currentTimeMillis();
      long sleepTime = retryTime - currentTime;
      if (sleepTime > 0L) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("resilient work sleeping before next retry " + sleepTime + " ms  " + DebugLogger.formatDate(startTime) + " " + worker.getDescription());
         }

         Thread.sleep(sleepTime);
      }

   }

   public interface ResilientWorker {
      boolean doWork(ResiliencyConfig var1, long var2) throws Exception;

      String getDescription();
   }

   public static class ResiliencyConfig {
      private int maxWait;
      private int connectTimeout;
      private int readTimeout;

      public int maxWait() {
         return this.maxWait;
      }

      public int connectTimeout() {
         return this.connectTimeout;
      }

      public int readTimeout() {
         return this.readTimeout;
      }

      public ResiliencyConfig maxWait(int val) {
         this.assertNotNegative("maxWait", val);
         this.maxWait = val;
         return this;
      }

      public ResiliencyConfig connectTimeout(int val) {
         this.assertNotNegative("connectTimeout", val);
         this.connectTimeout = val;
         return this;
      }

      public ResiliencyConfig readTimeout(int val) {
         this.assertNotNegative("readTimeout", val);
         this.readTimeout = val;
         return this;
      }

      private void assertNotNegative(String property, int val) {
         if (val < 0) {
            throw new AssertionError(property + " must be >= 0: " + val);
         }
      }
   }
}
