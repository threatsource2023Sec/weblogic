package com.codahale.metrics;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class Timer implements Metered, Sampling {
   private final Meter meter;
   private final Histogram histogram;
   private final Clock clock;

   public Timer() {
      this(new ExponentiallyDecayingReservoir());
   }

   public Timer(Reservoir reservoir) {
      this(reservoir, Clock.defaultClock());
   }

   public Timer(Reservoir reservoir, Clock clock) {
      this.meter = new Meter(clock);
      this.clock = clock;
      this.histogram = new Histogram(reservoir);
   }

   public void update(long duration, TimeUnit unit) {
      this.update(unit.toNanos(duration));
   }

   public Object time(Callable event) throws Exception {
      long startTime = this.clock.getTick();

      Object var4;
      try {
         var4 = event.call();
      } finally {
         this.update(this.clock.getTick() - startTime);
      }

      return var4;
   }

   public Object timeSupplier(Supplier event) {
      long startTime = this.clock.getTick();

      Object var4;
      try {
         var4 = event.get();
      } finally {
         this.update(this.clock.getTick() - startTime);
      }

      return var4;
   }

   public void time(Runnable event) {
      long startTime = this.clock.getTick();

      try {
         event.run();
      } finally {
         this.update(this.clock.getTick() - startTime);
      }

   }

   public Context time() {
      return new Context(this, this.clock);
   }

   public long getCount() {
      return this.histogram.getCount();
   }

   public double getFifteenMinuteRate() {
      return this.meter.getFifteenMinuteRate();
   }

   public double getFiveMinuteRate() {
      return this.meter.getFiveMinuteRate();
   }

   public double getMeanRate() {
      return this.meter.getMeanRate();
   }

   public double getOneMinuteRate() {
      return this.meter.getOneMinuteRate();
   }

   public Snapshot getSnapshot() {
      return this.histogram.getSnapshot();
   }

   private void update(long duration) {
      if (duration >= 0L) {
         this.histogram.update(duration);
         this.meter.mark();
      }

   }

   public static class Context implements AutoCloseable {
      private final Timer timer;
      private final Clock clock;
      private final long startTime;

      private Context(Timer timer, Clock clock) {
         this.timer = timer;
         this.clock = clock;
         this.startTime = clock.getTick();
      }

      public long stop() {
         long elapsed = this.clock.getTick() - this.startTime;
         this.timer.update(elapsed, TimeUnit.NANOSECONDS);
         return elapsed;
      }

      public void close() {
         this.stop();
      }

      // $FF: synthetic method
      Context(Timer x0, Clock x1, Object x2) {
         this(x0, x1);
      }
   }
}
