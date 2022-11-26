package com.codahale.metrics;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class SlidingTimeWindowReservoir implements Reservoir {
   private static final int COLLISION_BUFFER = 256;
   private static final int TRIM_THRESHOLD = 256;
   private static final long CLEAR_BUFFER;
   private final Clock clock;
   private final ConcurrentSkipListMap measurements;
   private final long window;
   private final AtomicLong lastTick;
   private final AtomicLong count;

   public SlidingTimeWindowReservoir(long window, TimeUnit windowUnit) {
      this(window, windowUnit, Clock.defaultClock());
   }

   public SlidingTimeWindowReservoir(long window, TimeUnit windowUnit, Clock clock) {
      this.clock = clock;
      this.measurements = new ConcurrentSkipListMap();
      this.window = windowUnit.toNanos(window) * 256L;
      this.lastTick = new AtomicLong(clock.getTick() * 256L);
      this.count = new AtomicLong();
   }

   public int size() {
      this.trim();
      return this.measurements.size();
   }

   public void update(long value) {
      if (this.count.incrementAndGet() % 256L == 0L) {
         this.trim();
      }

      this.measurements.put(this.getTick(), value);
   }

   public Snapshot getSnapshot() {
      this.trim();
      return new UniformSnapshot(this.measurements.values());
   }

   private long getTick() {
      long oldTick;
      long newTick;
      do {
         oldTick = this.lastTick.get();
         long tick = this.clock.getTick() * 256L;
         newTick = tick - oldTick > 0L ? tick : oldTick + 1L;
      } while(!this.lastTick.compareAndSet(oldTick, newTick));

      return newTick;
   }

   private void trim() {
      long now = this.getTick();
      long windowStart = now - this.window;
      long windowEnd = now + CLEAR_BUFFER;
      if (windowStart < windowEnd) {
         this.measurements.headMap(windowStart).clear();
         this.measurements.tailMap(windowEnd).clear();
      } else {
         this.measurements.subMap(windowEnd, windowStart).clear();
      }

   }

   static {
      CLEAR_BUFFER = TimeUnit.HOURS.toNanos(1L) * 256L;
   }
}
