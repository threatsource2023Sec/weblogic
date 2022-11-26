package weblogic.ejb.container.internal;

import weblogic.ejb.container.EJBLogger;
import weblogic.utils.StackTraceUtilsClient;

public class ErrorMessageLoggingSuppressor {
   private static final int SUPPRESS_THRESHOLD_COUNT = 3;
   private static final long INITIAL_SUPPRESS_INTERVAL = 60000L;
   private static final long MAX_SUPPRESS_INTERVAL = 216000000L;
   private long initialSuppressInterval = 60000L;
   private int suppressThresholdCount = 3;
   private long suppressInterval;
   private Throwable lastThrowable;
   private long lastThrowableLogTimestamp;
   private long lastThrowableConsecutiveCount;
   private long lastThrowableCurrentSuppressCount;

   public ErrorMessageLoggingSuppressor() {
      this.suppressInterval = this.initialSuppressInterval;
      this.lastThrowable = null;
      this.lastThrowableLogTimestamp = 0L;
      this.lastThrowableConsecutiveCount = 0L;
      this.lastThrowableCurrentSuppressCount = 0L;
   }

   public ErrorMessageLoggingSuppressor(int suppressThresholdCount, int initialSuppressInterval) {
      this.suppressInterval = this.initialSuppressInterval;
      this.lastThrowable = null;
      this.lastThrowableLogTimestamp = 0L;
      this.lastThrowableConsecutiveCount = 0L;
      this.lastThrowableCurrentSuppressCount = 0L;
      this.suppressThresholdCount = suppressThresholdCount;
      this.initialSuppressInterval = (long)initialSuppressInterval;
      this.suppressInterval = (long)initialSuppressInterval;
   }

   synchronized ErrorMessageSuppressCounter checkShouldLog(Throwable e) {
      if (this.lastThrowable == null) {
         this.lastThrowable = e;
         this.lastThrowableConsecutiveCount = 1L;
         this.lastThrowableCurrentSuppressCount = 0L;
         this.lastThrowableLogTimestamp = System.currentTimeMillis();
         return new ErrorMessageSuppressCounter(e, 0L, this.lastThrowableLogTimestamp);
      } else if (e.getMessage() == null && this.lastThrowable.getMessage() != null || e.getMessage() != null && !e.getMessage().equals(this.lastThrowable.getMessage())) {
         this.lastThrowable = e;
         this.lastThrowableConsecutiveCount = 1L;
         this.lastThrowableCurrentSuppressCount = 0L;
         this.lastThrowableLogTimestamp = System.currentTimeMillis();
         return new ErrorMessageSuppressCounter(e, 0L, this.lastThrowableLogTimestamp);
      } else {
         ++this.lastThrowableConsecutiveCount;
         if (this.lastThrowableConsecutiveCount <= 3L) {
            this.lastThrowableLogTimestamp = System.currentTimeMillis();
            this.lastThrowableCurrentSuppressCount = 0L;
            return new ErrorMessageSuppressCounter(e, 0L, this.lastThrowableLogTimestamp);
         } else {
            long currtime = System.currentTimeMillis();
            if (currtime - this.lastThrowableLogTimestamp < this.suppressInterval) {
               this.lastThrowableLogTimestamp = System.currentTimeMillis();
               ++this.lastThrowableCurrentSuppressCount;
               return null;
            } else {
               this.suppressInterval *= 2L;
               if (this.suppressInterval > 216000000L) {
                  this.suppressInterval = 216000000L;
               }

               long count = this.lastThrowableCurrentSuppressCount;
               this.lastThrowableCurrentSuppressCount = 0L;
               this.lastThrowableLogTimestamp = System.currentTimeMillis();
               return new ErrorMessageSuppressCounter(e, count, this.lastThrowableLogTimestamp);
            }
         }
      }
   }

   void clear() {
      Throwable thr = this.lastThrowable;
      long suppressedcnt = this.lastThrowableCurrentSuppressCount;
      long timestamp = this.lastThrowableLogTimestamp;
      this.lastThrowable = null;
      this.lastThrowableConsecutiveCount = 0L;
      this.lastThrowableCurrentSuppressCount = 0L;
      this.lastThrowableLogTimestamp = 0L;
      this.suppressInterval = this.initialSuppressInterval;
      if (thr != null && suppressedcnt != 0L) {
         EJBLogger.logJMSExceptionProcessingMDBSuppressed(suppressedcnt, String.valueOf(timestamp), JMSConnectionPoller.getAllExceptionText(thr), StackTraceUtilsClient.throwable2StackTrace(thr));
      }
   }
}
