package com.codahale.metrics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class SlidingTimeWindowArrayReservoir implements Reservoir {
   private static final long COLLISION_BUFFER = 256L;
   private static final long TRIM_THRESHOLD = 256L;
   private static final long CLEAR_BUFFER;
   private final Clock clock;
   private final ChunkedAssociativeLongArray measurements;
   private final long window;
   private final AtomicLong lastTick;
   private final AtomicLong count;
   private final long startTick;

   public SlidingTimeWindowArrayReservoir(long window, TimeUnit windowUnit) {
      this(window, windowUnit, Clock.defaultClock());
   }

   public SlidingTimeWindowArrayReservoir(long window, TimeUnit windowUnit, Clock clock) {
      this.startTick = clock.getTick();
      this.clock = clock;
      this.measurements = new ChunkedAssociativeLongArray();
      this.window = windowUnit.toNanos(window) * 256L;
      this.lastTick = new AtomicLong((clock.getTick() - this.startTick) * 256L);
      this.count = new AtomicLong();
   }

   public int size() {
      this.trim();
      return this.measurements.size();
   }

   public void update(long value) {
      long newTick;
      do {
         if (this.count.incrementAndGet() % 256L == 0L) {
            this.trim();
         }

         long lastTick = this.lastTick.get();
         newTick = this.getTick();
         boolean longOverflow = newTick < lastTick;
         if (longOverflow) {
            this.measurements.clear();
         }
      } while(!this.measurements.put(newTick, value));

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
         long tick = (this.clock.getTick() - this.startTick) * 256L;
         newTick = tick - oldTick > 0L ? tick : oldTick + 1L;
      } while(!this.lastTick.compareAndSet(oldTick, newTick));

      return newTick;
   }

   void trim() {
      long now = this.getTick();
      long windowStart = now - this.window;
      long windowEnd = now + CLEAR_BUFFER;
      if (windowStart < windowEnd) {
         this.measurements.trim(windowStart, windowEnd);
      } else {
         this.measurements.clear();
      }

   }

   static {
      CLEAR_BUFFER = TimeUnit.HOURS.toNanos(1L) * 256L;
   }
}
