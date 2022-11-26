package jsr166e.extra;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import sun.misc.Unsafe;

public class AtomicDouble extends Number implements Serializable {
   private static final long serialVersionUID = -8405198993435143622L;
   private transient volatile long value;
   private static final Unsafe unsafe = getUnsafe();
   private static final long valueOffset;

   public AtomicDouble(double initialValue) {
      this.value = Double.doubleToRawLongBits(initialValue);
   }

   public AtomicDouble() {
   }

   public final double get() {
      return Double.longBitsToDouble(this.value);
   }

   public final void set(double newValue) {
      long next = Double.doubleToRawLongBits(newValue);
      this.value = next;
   }

   public final void lazySet(double newValue) {
      long next = Double.doubleToRawLongBits(newValue);
      unsafe.putOrderedLong(this, valueOffset, next);
   }

   public final double getAndSet(double newValue) {
      long next = Double.doubleToRawLongBits(newValue);

      long current;
      do {
         current = this.value;
      } while(!unsafe.compareAndSwapLong(this, valueOffset, current, next));

      return Double.longBitsToDouble(current);
   }

   public final boolean compareAndSet(double expect, double update) {
      return unsafe.compareAndSwapLong(this, valueOffset, Double.doubleToRawLongBits(expect), Double.doubleToRawLongBits(update));
   }

   public final boolean weakCompareAndSet(double expect, double update) {
      return this.compareAndSet(expect, update);
   }

   public final double getAndAdd(double delta) {
      long current;
      double currentVal;
      long next;
      do {
         current = this.value;
         currentVal = Double.longBitsToDouble(current);
         double nextVal = currentVal + delta;
         next = Double.doubleToRawLongBits(nextVal);
      } while(!unsafe.compareAndSwapLong(this, valueOffset, current, next));

      return currentVal;
   }

   public final double addAndGet(double delta) {
      long current;
      double nextVal;
      long next;
      do {
         current = this.value;
         double currentVal = Double.longBitsToDouble(current);
         nextVal = currentVal + delta;
         next = Double.doubleToRawLongBits(nextVal);
      } while(!unsafe.compareAndSwapLong(this, valueOffset, current, next));

      return nextVal;
   }

   public String toString() {
      return Double.toString(this.get());
   }

   public int intValue() {
      return (int)this.get();
   }

   public long longValue() {
      return (long)this.get();
   }

   public float floatValue() {
      return (float)this.get();
   }

   public double doubleValue() {
      return this.get();
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();
      s.writeDouble(this.get());
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.set(s.readDouble());
   }

   private static Unsafe getUnsafe() {
      try {
         return Unsafe.getUnsafe();
      } catch (SecurityException var2) {
         try {
            return (Unsafe)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Unsafe run() throws Exception {
                  Class k = Unsafe.class;
                  Field[] arr$ = k.getDeclaredFields();
                  int len$ = arr$.length;

                  for(int i$ = 0; i$ < len$; ++i$) {
                     Field f = arr$[i$];
                     f.setAccessible(true);
                     Object x = f.get((Object)null);
                     if (k.isInstance(x)) {
                        return (Unsafe)k.cast(x);
                     }
                  }

                  throw new NoSuchFieldError("the Unsafe");
               }
            });
         } catch (PrivilegedActionException var1) {
            throw new RuntimeException("Could not initialize intrinsics", var1.getCause());
         }
      }
   }

   static {
      try {
         valueOffset = unsafe.objectFieldOffset(AtomicDouble.class.getDeclaredField("value"));
      } catch (Exception var1) {
         throw new Error(var1);
      }
   }
}
