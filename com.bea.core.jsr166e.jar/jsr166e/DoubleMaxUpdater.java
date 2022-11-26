package jsr166e;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DoubleMaxUpdater extends Striped64 implements Serializable {
   private static final long serialVersionUID = 7249069246863182397L;
   private static final long MIN_AS_LONG = -4503599627370496L;

   final long fn(long v, long x) {
      return Double.longBitsToDouble(v) > Double.longBitsToDouble(x) ? v : x;
   }

   public DoubleMaxUpdater() {
      this.base = -4503599627370496L;
   }

   public void update(double x) {
      long lx = Double.doubleToRawLongBits(x);
      Striped64.Cell[] as;
      long b;
      if ((as = this.cells) != null || Double.longBitsToDouble(b = this.base) < x && !this.casBase(b, lx)) {
         boolean uncontended = true;
         long v;
         int[] hc;
         Striped64.Cell a;
         int n;
         if ((hc = (int[])threadHashCode.get()) == null || as == null || (n = as.length) < 1 || (a = as[n - 1 & hc[0]]) == null || Double.longBitsToDouble(v = a.value) < x && !(uncontended = a.cas(v, lx))) {
            this.retryUpdate(lx, hc, uncontended);
         }
      }

   }

   public double max() {
      Striped64.Cell[] as = this.cells;
      double max = Double.longBitsToDouble(this.base);
      if (as != null) {
         int n = as.length;

         for(int i = 0; i < n; ++i) {
            Striped64.Cell a = as[i];
            double v;
            if (a != null && (v = Double.longBitsToDouble(a.value)) > max) {
               max = v;
            }
         }
      }

      return max;
   }

   public void reset() {
      this.internalReset(-4503599627370496L);
   }

   public double maxThenReset() {
      Striped64.Cell[] as = this.cells;
      double max = Double.longBitsToDouble(this.base);
      this.base = -4503599627370496L;
      if (as != null) {
         int n = as.length;

         for(int i = 0; i < n; ++i) {
            Striped64.Cell a = as[i];
            if (a != null) {
               double v = Double.longBitsToDouble(a.value);
               a.value = -4503599627370496L;
               if (v > max) {
                  max = v;
               }
            }
         }
      }

      return max;
   }

   public String toString() {
      return Double.toString(this.max());
   }

   public double doubleValue() {
      return this.max();
   }

   public long longValue() {
      return (long)this.max();
   }

   public int intValue() {
      return (int)this.max();
   }

   public float floatValue() {
      return (float)this.max();
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();
      s.writeDouble(this.max());
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.busy = 0;
      this.cells = null;
      this.base = Double.doubleToRawLongBits(s.readDouble());
   }
}
