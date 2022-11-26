package jsr166e;

import java.util.Random;

public class ThreadLocalRandom extends Random {
   private static final long multiplier = 25214903917L;
   private static final long addend = 11L;
   private static final long mask = 281474976710655L;
   private long rnd;
   boolean initialized = true;
   private long pad0;
   private long pad1;
   private long pad2;
   private long pad3;
   private long pad4;
   private long pad5;
   private long pad6;
   private long pad7;
   private static final ThreadLocal localRandom = new ThreadLocal() {
      protected ThreadLocalRandom initialValue() {
         return new ThreadLocalRandom();
      }
   };
   private static final long serialVersionUID = -5851777807851030925L;

   ThreadLocalRandom() {
   }

   public static ThreadLocalRandom current() {
      return (ThreadLocalRandom)localRandom.get();
   }

   public void setSeed(long seed) {
      if (this.initialized) {
         throw new UnsupportedOperationException();
      } else {
         this.rnd = (seed ^ 25214903917L) & 281474976710655L;
      }
   }

   protected int next(int bits) {
      this.rnd = this.rnd * 25214903917L + 11L & 281474976710655L;
      return (int)(this.rnd >>> 48 - bits);
   }

   public int nextInt(int least, int bound) {
      if (least >= bound) {
         throw new IllegalArgumentException();
      } else {
         return this.nextInt(bound - least) + least;
      }
   }

   public long nextLong(long n) {
      if (n <= 0L) {
         throw new IllegalArgumentException("n must be positive");
      } else {
         long offset;
         long nextn;
         for(offset = 0L; n >= 2147483647L; n = nextn) {
            int bits = this.next(2);
            long half = n >>> 1;
            nextn = (bits & 2) == 0 ? half : n - half;
            if ((bits & 1) == 0) {
               offset += n - nextn;
            }
         }

         return offset + (long)this.nextInt((int)n);
      }
   }

   public long nextLong(long least, long bound) {
      if (least >= bound) {
         throw new IllegalArgumentException();
      } else {
         return this.nextLong(bound - least) + least;
      }
   }

   public double nextDouble(double n) {
      if (n <= 0.0) {
         throw new IllegalArgumentException("n must be positive");
      } else {
         return this.nextDouble() * n;
      }
   }

   public double nextDouble(double least, double bound) {
      if (least >= bound) {
         throw new IllegalArgumentException();
      } else {
         return this.nextDouble() * (bound - least) + least;
      }
   }
}
