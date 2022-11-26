package com.bea.core.repackaged.springframework.util.backoff;

import com.bea.core.repackaged.springframework.util.Assert;

public class ExponentialBackOff implements BackOff {
   public static final long DEFAULT_INITIAL_INTERVAL = 2000L;
   public static final double DEFAULT_MULTIPLIER = 1.5;
   public static final long DEFAULT_MAX_INTERVAL = 30000L;
   public static final long DEFAULT_MAX_ELAPSED_TIME = Long.MAX_VALUE;
   private long initialInterval = 2000L;
   private double multiplier = 1.5;
   private long maxInterval = 30000L;
   private long maxElapsedTime = Long.MAX_VALUE;

   public ExponentialBackOff() {
   }

   public ExponentialBackOff(long initialInterval, double multiplier) {
      this.checkMultiplier(multiplier);
      this.initialInterval = initialInterval;
      this.multiplier = multiplier;
   }

   public void setInitialInterval(long initialInterval) {
      this.initialInterval = initialInterval;
   }

   public long getInitialInterval() {
      return this.initialInterval;
   }

   public void setMultiplier(double multiplier) {
      this.checkMultiplier(multiplier);
      this.multiplier = multiplier;
   }

   public double getMultiplier() {
      return this.multiplier;
   }

   public void setMaxInterval(long maxInterval) {
      this.maxInterval = maxInterval;
   }

   public long getMaxInterval() {
      return this.maxInterval;
   }

   public void setMaxElapsedTime(long maxElapsedTime) {
      this.maxElapsedTime = maxElapsedTime;
   }

   public long getMaxElapsedTime() {
      return this.maxElapsedTime;
   }

   public BackOffExecution start() {
      return new ExponentialBackOffExecution();
   }

   private void checkMultiplier(double multiplier) {
      Assert.isTrue(multiplier >= 1.0, () -> {
         return "Invalid multiplier '" + multiplier + "'. Should be greater than or equal to 1. A multiplier of 1 is equivalent to a fixed interval.";
      });
   }

   private class ExponentialBackOffExecution implements BackOffExecution {
      private long currentInterval;
      private long currentElapsedTime;

      private ExponentialBackOffExecution() {
         this.currentInterval = -1L;
         this.currentElapsedTime = 0L;
      }

      public long nextBackOff() {
         if (this.currentElapsedTime >= ExponentialBackOff.this.maxElapsedTime) {
            return -1L;
         } else {
            long nextInterval = this.computeNextInterval();
            this.currentElapsedTime += nextInterval;
            return nextInterval;
         }
      }

      private long computeNextInterval() {
         long maxInterval = ExponentialBackOff.this.getMaxInterval();
         if (this.currentInterval >= maxInterval) {
            return maxInterval;
         } else {
            if (this.currentInterval < 0L) {
               long initialInterval = ExponentialBackOff.this.getInitialInterval();
               this.currentInterval = initialInterval < maxInterval ? initialInterval : maxInterval;
            } else {
               this.currentInterval = this.multiplyInterval(maxInterval);
            }

            return this.currentInterval;
         }
      }

      private long multiplyInterval(long maxInterval) {
         long i = this.currentInterval;
         i = (long)((double)i * ExponentialBackOff.this.getMultiplier());
         return i > maxInterval ? maxInterval : i;
      }

      public String toString() {
         StringBuilder sb = new StringBuilder("ExponentialBackOff{");
         sb.append("currentInterval=").append(this.currentInterval < 0L ? "n/a" : this.currentInterval + "ms");
         sb.append(", multiplier=").append(ExponentialBackOff.this.getMultiplier());
         sb.append('}');
         return sb.toString();
      }

      // $FF: synthetic method
      ExponentialBackOffExecution(Object x1) {
         this();
      }
   }
}
