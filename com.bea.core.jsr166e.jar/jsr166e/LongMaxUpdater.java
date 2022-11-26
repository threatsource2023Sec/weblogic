package jsr166e;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class LongMaxUpdater extends Striped64 implements Serializable {
   private static final long serialVersionUID = 7249069246863182397L;

   final long fn(long v, long x) {
      return v > x ? v : x;
   }

   public LongMaxUpdater() {
      this.base = Long.MIN_VALUE;
   }

   public void update(long x) {
      Striped64.Cell[] as;
      long b;
      if ((as = this.cells) != null || (b = this.base) < x && !this.casBase(b, x)) {
         boolean uncontended = true;
         long v;
         int[] hc;
         Striped64.Cell a;
         int n;
         if ((hc = (int[])threadHashCode.get()) == null || as == null || (n = as.length) < 1 || (a = as[n - 1 & hc[0]]) == null || (v = a.value) < x && !(uncontended = a.cas(v, x))) {
            this.retryUpdate(x, hc, uncontended);
         }
      }

   }

   public long max() {
      Striped64.Cell[] as = this.cells;
      long max = this.base;
      if (as != null) {
         int n = as.length;

         for(int i = 0; i < n; ++i) {
            Striped64.Cell a = as[i];
            long v;
            if (a != null && (v = a.value) > max) {
               max = v;
            }
         }
      }

      return max;
   }

   public void reset() {
      this.internalReset(Long.MIN_VALUE);
   }

   public long maxThenReset() {
      Striped64.Cell[] as = this.cells;
      long max = this.base;
      this.base = Long.MIN_VALUE;
      if (as != null) {
         int n = as.length;

         for(int i = 0; i < n; ++i) {
            Striped64.Cell a = as[i];
            if (a != null) {
               long v = a.value;
               a.value = Long.MIN_VALUE;
               if (v > max) {
                  max = v;
               }
            }
         }
      }

      return max;
   }

   public String toString() {
      return Long.toString(this.max());
   }

   public long longValue() {
      return this.max();
   }

   public int intValue() {
      return (int)this.max();
   }

   public float floatValue() {
      return (float)this.max();
   }

   public double doubleValue() {
      return (double)this.max();
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();
      s.writeLong(this.max());
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.busy = 0;
      this.cells = null;
      this.base = s.readLong();
   }
}
