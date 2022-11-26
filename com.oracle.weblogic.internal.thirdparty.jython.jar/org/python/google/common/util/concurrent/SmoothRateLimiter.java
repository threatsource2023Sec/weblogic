package org.python.google.common.util.concurrent;

import java.util.concurrent.TimeUnit;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.math.LongMath;

@GwtIncompatible
abstract class SmoothRateLimiter extends RateLimiter {
   double storedPermits;
   double maxPermits;
   double stableIntervalMicros;
   private long nextFreeTicketMicros;

   private SmoothRateLimiter(RateLimiter.SleepingStopwatch stopwatch) {
      super(stopwatch);
      this.nextFreeTicketMicros = 0L;
   }

   final void doSetRate(double permitsPerSecond, long nowMicros) {
      this.resync(nowMicros);
      double stableIntervalMicros = (double)TimeUnit.SECONDS.toMicros(1L) / permitsPerSecond;
      this.stableIntervalMicros = stableIntervalMicros;
      this.doSetRate(permitsPerSecond, stableIntervalMicros);
   }

   abstract void doSetRate(double var1, double var3);

   final double doGetRate() {
      return (double)TimeUnit.SECONDS.toMicros(1L) / this.stableIntervalMicros;
   }

   final long queryEarliestAvailable(long nowMicros) {
      return this.nextFreeTicketMicros;
   }

   final long reserveEarliestAvailable(int requiredPermits, long nowMicros) {
      this.resync(nowMicros);
      long returnValue = this.nextFreeTicketMicros;
      double storedPermitsToSpend = Math.min((double)requiredPermits, this.storedPermits);
      double freshPermits = (double)requiredPermits - storedPermitsToSpend;
      long waitMicros = this.storedPermitsToWaitTime(this.storedPermits, storedPermitsToSpend) + (long)(freshPermits * this.stableIntervalMicros);
      this.nextFreeTicketMicros = LongMath.saturatedAdd(this.nextFreeTicketMicros, waitMicros);
      this.storedPermits -= storedPermitsToSpend;
      return returnValue;
   }

   abstract long storedPermitsToWaitTime(double var1, double var3);

   abstract double coolDownIntervalMicros();

   void resync(long nowMicros) {
      if (nowMicros > this.nextFreeTicketMicros) {
         double newPermits = (double)(nowMicros - this.nextFreeTicketMicros) / this.coolDownIntervalMicros();
         this.storedPermits = Math.min(this.maxPermits, this.storedPermits + newPermits);
         this.nextFreeTicketMicros = nowMicros;
      }

   }

   // $FF: synthetic method
   SmoothRateLimiter(RateLimiter.SleepingStopwatch x0, Object x1) {
      this(x0);
   }

   static final class SmoothBursty extends SmoothRateLimiter {
      final double maxBurstSeconds;

      SmoothBursty(RateLimiter.SleepingStopwatch stopwatch, double maxBurstSeconds) {
         super(stopwatch, null);
         this.maxBurstSeconds = maxBurstSeconds;
      }

      void doSetRate(double permitsPerSecond, double stableIntervalMicros) {
         double oldMaxPermits = this.maxPermits;
         this.maxPermits = this.maxBurstSeconds * permitsPerSecond;
         if (oldMaxPermits == Double.POSITIVE_INFINITY) {
            this.storedPermits = this.maxPermits;
         } else {
            this.storedPermits = oldMaxPermits == 0.0 ? 0.0 : this.storedPermits * this.maxPermits / oldMaxPermits;
         }

      }

      long storedPermitsToWaitTime(double storedPermits, double permitsToTake) {
         return 0L;
      }

      double coolDownIntervalMicros() {
         return this.stableIntervalMicros;
      }
   }

   static final class SmoothWarmingUp extends SmoothRateLimiter {
      private final long warmupPeriodMicros;
      private double slope;
      private double thresholdPermits;
      private double coldFactor;

      SmoothWarmingUp(RateLimiter.SleepingStopwatch stopwatch, long warmupPeriod, TimeUnit timeUnit, double coldFactor) {
         super(stopwatch, null);
         this.warmupPeriodMicros = timeUnit.toMicros(warmupPeriod);
         this.coldFactor = coldFactor;
      }

      void doSetRate(double permitsPerSecond, double stableIntervalMicros) {
         double oldMaxPermits = this.maxPermits;
         double coldIntervalMicros = stableIntervalMicros * this.coldFactor;
         this.thresholdPermits = 0.5 * (double)this.warmupPeriodMicros / stableIntervalMicros;
         this.maxPermits = this.thresholdPermits + 2.0 * (double)this.warmupPeriodMicros / (stableIntervalMicros + coldIntervalMicros);
         this.slope = (coldIntervalMicros - stableIntervalMicros) / (this.maxPermits - this.thresholdPermits);
         if (oldMaxPermits == Double.POSITIVE_INFINITY) {
            this.storedPermits = 0.0;
         } else {
            this.storedPermits = oldMaxPermits == 0.0 ? this.maxPermits : this.storedPermits * this.maxPermits / oldMaxPermits;
         }

      }

      long storedPermitsToWaitTime(double storedPermits, double permitsToTake) {
         double availablePermitsAboveThreshold = storedPermits - this.thresholdPermits;
         long micros = 0L;
         if (availablePermitsAboveThreshold > 0.0) {
            double permitsAboveThresholdToTake = Math.min(availablePermitsAboveThreshold, permitsToTake);
            double length = this.permitsToTime(availablePermitsAboveThreshold) + this.permitsToTime(availablePermitsAboveThreshold - permitsAboveThresholdToTake);
            micros = (long)(permitsAboveThresholdToTake * length / 2.0);
            permitsToTake -= permitsAboveThresholdToTake;
         }

         micros = (long)((double)micros + this.stableIntervalMicros * permitsToTake);
         return micros;
      }

      private double permitsToTime(double permits) {
         return this.stableIntervalMicros + permits * this.slope;
      }

      double coolDownIntervalMicros() {
         return (double)this.warmupPeriodMicros / this.maxPermits;
      }
   }
}
