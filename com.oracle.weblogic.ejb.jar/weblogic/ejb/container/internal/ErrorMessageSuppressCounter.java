package weblogic.ejb.container.internal;

public class ErrorMessageSuppressCounter {
   private Throwable thr = null;
   private long lastOccurrencetimestamp = 0L;
   private long suppressedCount = 0L;

   public ErrorMessageSuppressCounter(Throwable t, long suppressedcnt, long lasttimestamp) {
      this.thr = t;
      this.suppressedCount = suppressedcnt;
      this.lastOccurrencetimestamp = lasttimestamp;
   }

   public long getSuppressedCount() {
      return this.suppressedCount;
   }

   public long getLastOccurranceTimestamp() {
      return this.lastOccurrencetimestamp;
   }

   public Throwable getThrowable() {
      return this.thr;
   }
}
