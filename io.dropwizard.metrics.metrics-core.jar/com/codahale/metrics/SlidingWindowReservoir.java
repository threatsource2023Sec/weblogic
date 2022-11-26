package com.codahale.metrics;

public class SlidingWindowReservoir implements Reservoir {
   private final long[] measurements;
   private long count;

   public SlidingWindowReservoir(int size) {
      this.measurements = new long[size];
      this.count = 0L;
   }

   public synchronized int size() {
      return (int)Math.min(this.count, (long)this.measurements.length);
   }

   public synchronized void update(long value) {
      this.measurements[(int)(this.count++ % (long)this.measurements.length)] = value;
   }

   public Snapshot getSnapshot() {
      long[] values = new long[this.size()];

      for(int i = 0; i < values.length; ++i) {
         synchronized(this) {
            values[i] = this.measurements[i];
         }
      }

      return new UniformSnapshot(values);
   }
}
