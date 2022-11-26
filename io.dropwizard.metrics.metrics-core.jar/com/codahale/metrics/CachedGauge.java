package com.codahale.metrics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public abstract class CachedGauge implements Gauge {
   private final Clock clock;
   private final AtomicLong reloadAt;
   private final long timeoutNS;
   private volatile Object value;

   protected CachedGauge(long timeout, TimeUnit timeoutUnit) {
      this(Clock.defaultClock(), timeout, timeoutUnit);
   }

   protected CachedGauge(Clock clock, long timeout, TimeUnit timeoutUnit) {
      this.clock = clock;
      this.reloadAt = new AtomicLong(0L);
      this.timeoutNS = timeoutUnit.toNanos(timeout);
   }

   protected abstract Object loadValue();

   public Object getValue() {
      if (this.shouldLoad()) {
         this.value = this.loadValue();
      }

      return this.value;
   }

   private boolean shouldLoad() {
      long time;
      long current;
      do {
         time = this.clock.getTick();
         current = this.reloadAt.get();
         if (current > time) {
            return false;
         }
      } while(!this.reloadAt.compareAndSet(current, time + this.timeoutNS));

      return true;
   }
}
