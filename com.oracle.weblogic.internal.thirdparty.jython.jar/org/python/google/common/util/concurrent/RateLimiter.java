package org.python.google.common.util.concurrent;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.ThreadSafe;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Preconditions;
import org.python.google.common.base.Stopwatch;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@ThreadSafe
@Beta
@GwtIncompatible
public abstract class RateLimiter {
   private final SleepingStopwatch stopwatch;
   private volatile Object mutexDoNotUseDirectly;

   public static RateLimiter create(double permitsPerSecond) {
      return create(RateLimiter.SleepingStopwatch.createFromSystemTimer(), permitsPerSecond);
   }

   @VisibleForTesting
   static RateLimiter create(SleepingStopwatch stopwatch, double permitsPerSecond) {
      RateLimiter rateLimiter = new SmoothRateLimiter.SmoothBursty(stopwatch, 1.0);
      rateLimiter.setRate(permitsPerSecond);
      return rateLimiter;
   }

   public static RateLimiter create(double permitsPerSecond, long warmupPeriod, TimeUnit unit) {
      Preconditions.checkArgument(warmupPeriod >= 0L, "warmupPeriod must not be negative: %s", warmupPeriod);
      return create(RateLimiter.SleepingStopwatch.createFromSystemTimer(), permitsPerSecond, warmupPeriod, unit, 3.0);
   }

   @VisibleForTesting
   static RateLimiter create(SleepingStopwatch stopwatch, double permitsPerSecond, long warmupPeriod, TimeUnit unit, double coldFactor) {
      RateLimiter rateLimiter = new SmoothRateLimiter.SmoothWarmingUp(stopwatch, warmupPeriod, unit, coldFactor);
      rateLimiter.setRate(permitsPerSecond);
      return rateLimiter;
   }

   private Object mutex() {
      Object mutex = this.mutexDoNotUseDirectly;
      if (mutex == null) {
         synchronized(this) {
            mutex = this.mutexDoNotUseDirectly;
            if (mutex == null) {
               this.mutexDoNotUseDirectly = mutex = new Object();
            }
         }
      }

      return mutex;
   }

   RateLimiter(SleepingStopwatch stopwatch) {
      this.stopwatch = (SleepingStopwatch)Preconditions.checkNotNull(stopwatch);
   }

   public final void setRate(double permitsPerSecond) {
      Preconditions.checkArgument(permitsPerSecond > 0.0 && !Double.isNaN(permitsPerSecond), "rate must be positive");
      synchronized(this.mutex()) {
         this.doSetRate(permitsPerSecond, this.stopwatch.readMicros());
      }
   }

   abstract void doSetRate(double var1, long var3);

   public final double getRate() {
      synchronized(this.mutex()) {
         return this.doGetRate();
      }
   }

   abstract double doGetRate();

   @CanIgnoreReturnValue
   public double acquire() {
      return this.acquire(1);
   }

   @CanIgnoreReturnValue
   public double acquire(int permits) {
      long microsToWait = this.reserve(permits);
      this.stopwatch.sleepMicrosUninterruptibly(microsToWait);
      return 1.0 * (double)microsToWait / (double)TimeUnit.SECONDS.toMicros(1L);
   }

   final long reserve(int permits) {
      checkPermits(permits);
      synchronized(this.mutex()) {
         return this.reserveAndGetWaitLength(permits, this.stopwatch.readMicros());
      }
   }

   public boolean tryAcquire(long timeout, TimeUnit unit) {
      return this.tryAcquire(1, timeout, unit);
   }

   public boolean tryAcquire(int permits) {
      return this.tryAcquire(permits, 0L, TimeUnit.MICROSECONDS);
   }

   public boolean tryAcquire() {
      return this.tryAcquire(1, 0L, TimeUnit.MICROSECONDS);
   }

   public boolean tryAcquire(int permits, long timeout, TimeUnit unit) {
      long timeoutMicros = Math.max(unit.toMicros(timeout), 0L);
      checkPermits(permits);
      long microsToWait;
      synchronized(this.mutex()) {
         long nowMicros = this.stopwatch.readMicros();
         if (!this.canAcquire(nowMicros, timeoutMicros)) {
            return false;
         }

         microsToWait = this.reserveAndGetWaitLength(permits, nowMicros);
      }

      this.stopwatch.sleepMicrosUninterruptibly(microsToWait);
      return true;
   }

   private boolean canAcquire(long nowMicros, long timeoutMicros) {
      return this.queryEarliestAvailable(nowMicros) - timeoutMicros <= nowMicros;
   }

   final long reserveAndGetWaitLength(int permits, long nowMicros) {
      long momentAvailable = this.reserveEarliestAvailable(permits, nowMicros);
      return Math.max(momentAvailable - nowMicros, 0L);
   }

   abstract long queryEarliestAvailable(long var1);

   abstract long reserveEarliestAvailable(int var1, long var2);

   public String toString() {
      return String.format(Locale.ROOT, "RateLimiter[stableRate=%3.1fqps]", this.getRate());
   }

   private static void checkPermits(int permits) {
      Preconditions.checkArgument(permits > 0, "Requested permits (%s) must be positive", permits);
   }

   abstract static class SleepingStopwatch {
      protected SleepingStopwatch() {
      }

      protected abstract long readMicros();

      protected abstract void sleepMicrosUninterruptibly(long var1);

      public static final SleepingStopwatch createFromSystemTimer() {
         return new SleepingStopwatch() {
            final Stopwatch stopwatch = Stopwatch.createStarted();

            protected long readMicros() {
               return this.stopwatch.elapsed(TimeUnit.MICROSECONDS);
            }

            protected void sleepMicrosUninterruptibly(long micros) {
               if (micros > 0L) {
                  Uninterruptibles.sleepUninterruptibly(micros, TimeUnit.MICROSECONDS);
               }

            }
         };
      }
   }
}
