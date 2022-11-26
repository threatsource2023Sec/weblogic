package weblogic.utils.concurrent.atomic;

public class WeakAtomicLong {
   private volatile int lowWord = 0;
   private int highWord = 0;

   public long incrementAndGet() {
      if (++this.lowWord > 0) {
         return ((long)this.highWord << 31) + ((long)this.lowWord & 4294967295L);
      } else {
         synchronized(this) {
            if (++this.lowWord > 0) {
               return this.get();
            } else if (++this.highWord < 0) {
               throw new ArithmeticException();
            } else {
               this.lowWord = 0;
               return this.get();
            }
         }
      }
   }

   public synchronized long get() {
      return ((long)this.highWord << 31) + ((long)this.lowWord & 4294967295L);
   }
}
