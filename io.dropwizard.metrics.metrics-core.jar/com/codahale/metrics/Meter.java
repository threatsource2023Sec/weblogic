package com.codahale.metrics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class Meter implements Metered {
   private static final long TICK_INTERVAL;
   private final EWMA m1Rate;
   private final EWMA m5Rate;
   private final EWMA m15Rate;
   private final LongAdder count;
   private final long startTime;
   private final AtomicLong lastTick;
   private final Clock clock;

   public Meter() {
      this(Clock.defaultClock());
   }

   public Meter(Clock clock) {
      this.m1Rate = EWMA.oneMinuteEWMA();
      this.m5Rate = EWMA.fiveMinuteEWMA();
      this.m15Rate = EWMA.fifteenMinuteEWMA();
      this.count = new LongAdder();
      this.clock = clock;
      this.startTime = this.clock.getTick();
      this.lastTick = new AtomicLong(this.startTime);
   }

   public void mark() {
      this.mark(1L);
   }

   public void mark(long n) {
      this.tickIfNecessary();
      this.count.add(n);
      this.m1Rate.update(n);
      this.m5Rate.update(n);
      this.m15Rate.update(n);
   }

   private void tickIfNecessary() {
      long oldTick = this.lastTick.get();
      long newTick = this.clock.getTick();
      long age = newTick - oldTick;
      if (age > TICK_INTERVAL) {
         long newIntervalStartTick = newTick - age % TICK_INTERVAL;
         if (this.lastTick.compareAndSet(oldTick, newIntervalStartTick)) {
            long requiredTicks = age / TICK_INTERVAL;

            for(long i = 0L; i < requiredTicks; ++i) {
               this.m1Rate.tick();
               this.m5Rate.tick();
               this.m15Rate.tick();
            }
         }
      }

   }

   public long getCount() {
      return this.count.sum();
   }

   public double getFifteenMinuteRate() {
      this.tickIfNecessary();
      return this.m15Rate.getRate(TimeUnit.SECONDS);
   }

   public double getFiveMinuteRate() {
      this.tickIfNecessary();
      return this.m5Rate.getRate(TimeUnit.SECONDS);
   }

   public double getMeanRate() {
      if (this.getCount() == 0L) {
         return 0.0;
      } else {
         double elapsed = (double)(this.clock.getTick() - this.startTime);
         return (double)this.getCount() / elapsed * (double)TimeUnit.SECONDS.toNanos(1L);
      }
   }

   public double getOneMinuteRate() {
      this.tickIfNecessary();
      return this.m1Rate.getRate(TimeUnit.SECONDS);
   }

   static {
      TICK_INTERVAL = TimeUnit.SECONDS.toNanos(5L);
   }
}
