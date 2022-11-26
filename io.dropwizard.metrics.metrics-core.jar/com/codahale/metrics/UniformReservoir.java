package com.codahale.metrics;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

public class UniformReservoir implements Reservoir {
   private static final int DEFAULT_SIZE = 1028;
   private final AtomicLong count;
   private final AtomicLongArray values;

   public UniformReservoir() {
      this(1028);
   }

   public UniformReservoir(int size) {
      this.count = new AtomicLong();
      this.values = new AtomicLongArray(size);

      for(int i = 0; i < this.values.length(); ++i) {
         this.values.set(i, 0L);
      }

      this.count.set(0L);
   }

   public int size() {
      long c = this.count.get();
      return c > (long)this.values.length() ? this.values.length() : (int)c;
   }

   public void update(long value) {
      long c = this.count.incrementAndGet();
      if (c <= (long)this.values.length()) {
         this.values.set((int)c - 1, value);
      } else {
         long r = ThreadLocalRandom.current().nextLong(c);
         if (r < (long)this.values.length()) {
            this.values.set((int)r, value);
         }
      }

   }

   public Snapshot getSnapshot() {
      int s = this.size();
      long[] copy = new long[s];

      for(int i = 0; i < s; ++i) {
         copy[i] = this.values.get(i);
      }

      return new UniformSnapshot(copy);
   }
}
