package com.codahale.metrics;

import java.util.concurrent.atomic.LongAdder;

public class Histogram implements Metric, Sampling, Counting {
   private final Reservoir reservoir;
   private final LongAdder count;

   public Histogram(Reservoir reservoir) {
      this.reservoir = reservoir;
      this.count = new LongAdder();
   }

   public void update(int value) {
      this.update((long)value);
   }

   public void update(long value) {
      this.count.increment();
      this.reservoir.update(value);
   }

   public long getCount() {
      return this.count.sum();
   }

   public Snapshot getSnapshot() {
      return this.reservoir.getSnapshot();
   }
}
