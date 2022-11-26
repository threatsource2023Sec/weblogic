package jsr166e;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DoubleAdder extends Striped64 implements Serializable {
   private static final long serialVersionUID = 7249069246863182397L;

   final long fn(long v, long x) {
      return Double.doubleToRawLongBits(Double.longBitsToDouble(v) + Double.longBitsToDouble(x));
   }

   public void add(double x) {
      Striped64.Cell[] as;
      long b;
      if ((as = this.cells) != null || !this.casBase(b = this.base, Double.doubleToRawLongBits(Double.longBitsToDouble(b) + x))) {
         boolean uncontended = true;
         long v;
         int[] hc;
         Striped64.Cell a;
         int n;
         if ((hc = (int[])threadHashCode.get()) == null || as == null || (n = as.length) < 1 || (a = as[n - 1 & hc[0]]) == null || !(uncontended = a.cas(v = a.value, Double.doubleToRawLongBits(Double.longBitsToDouble(v) + x)))) {
            this.retryUpdate(Double.doubleToRawLongBits(x), hc, uncontended);
         }
      }

   }

   public double sum() {
      Striped64.Cell[] as = this.cells;
      double sum = Double.longBitsToDouble(this.base);
      if (as != null) {
         int n = as.length;

         for(int i = 0; i < n; ++i) {
            Striped64.Cell a = as[i];
            if (a != null) {
               sum += Double.longBitsToDouble(a.value);
            }
         }
      }

      return sum;
   }

   public void reset() {
      this.internalReset(0L);
   }

   public double sumThenReset() {
      Striped64.Cell[] as = this.cells;
      double sum = Double.longBitsToDouble(this.base);
      this.base = 0L;
      if (as != null) {
         int n = as.length;

         for(int i = 0; i < n; ++i) {
            Striped64.Cell a = as[i];
            if (a != null) {
               long v = a.value;
               a.value = 0L;
               sum += Double.longBitsToDouble(v);
            }
         }
      }

      return sum;
   }

   public String toString() {
      return Double.toString(this.sum());
   }

   public double doubleValue() {
      return this.sum();
   }

   public long longValue() {
      return (long)this.sum();
   }

   public int intValue() {
      return (int)this.sum();
   }

   public float floatValue() {
      return (float)this.sum();
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();
      s.writeDouble(this.sum());
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.busy = 0;
      this.cells = null;
      this.base = Double.doubleToRawLongBits(s.readDouble());
   }
}
