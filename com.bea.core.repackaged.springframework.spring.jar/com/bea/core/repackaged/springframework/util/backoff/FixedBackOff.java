package com.bea.core.repackaged.springframework.util.backoff;

public class FixedBackOff implements BackOff {
   public static final long DEFAULT_INTERVAL = 5000L;
   public static final long UNLIMITED_ATTEMPTS = Long.MAX_VALUE;
   private long interval = 5000L;
   private long maxAttempts = Long.MAX_VALUE;

   public FixedBackOff() {
   }

   public FixedBackOff(long interval, long maxAttempts) {
      this.interval = interval;
      this.maxAttempts = maxAttempts;
   }

   public void setInterval(long interval) {
      this.interval = interval;
   }

   public long getInterval() {
      return this.interval;
   }

   public void setMaxAttempts(long maxAttempts) {
      this.maxAttempts = maxAttempts;
   }

   public long getMaxAttempts() {
      return this.maxAttempts;
   }

   public BackOffExecution start() {
      return new FixedBackOffExecution();
   }

   private class FixedBackOffExecution implements BackOffExecution {
      private long currentAttempts;

      private FixedBackOffExecution() {
         this.currentAttempts = 0L;
      }

      public long nextBackOff() {
         ++this.currentAttempts;
         return this.currentAttempts <= FixedBackOff.this.getMaxAttempts() ? FixedBackOff.this.getInterval() : -1L;
      }

      public String toString() {
         StringBuilder sb = new StringBuilder("FixedBackOff{");
         sb.append("interval=").append(FixedBackOff.this.interval);
         String attemptValue = FixedBackOff.this.maxAttempts == Long.MAX_VALUE ? "unlimited" : String.valueOf(FixedBackOff.this.maxAttempts);
         sb.append(", currentAttempts=").append(this.currentAttempts);
         sb.append(", maxAttempts=").append(attemptValue);
         sb.append('}');
         return sb.toString();
      }

      // $FF: synthetic method
      FixedBackOffExecution(Object x1) {
         this();
      }
   }
}
