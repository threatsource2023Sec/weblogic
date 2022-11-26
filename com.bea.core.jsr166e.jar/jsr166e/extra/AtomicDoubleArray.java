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

public class AtomicDoubleArray implements Serializable {
   private static final long serialVersionUID = -2308431214976778248L;
   private final transient long[] array;
   private static final Unsafe unsafe = getUnsafe();
   private static final long arrayOffset;
   private static final int base;
   private static final int shift;

   private long checkedByteOffset(int i) {
      if (i >= 0 && i < this.array.length) {
         return byteOffset(i);
      } else {
         throw new IndexOutOfBoundsException("index " + i);
      }
   }

   private static long byteOffset(int i) {
      return ((long)i << shift) + (long)base;
   }

   public AtomicDoubleArray(int length) {
      this.array = new long[length];
   }

   public AtomicDoubleArray(double[] array) {
      int len = array.length;
      long[] a = new long[len];

      for(int i = 0; i < len; ++i) {
         a[i] = Double.doubleToRawLongBits(array[i]);
      }

      this.array = a;
   }

   public final int length() {
      return this.array.length;
   }

   public final double get(int i) {
      return Double.longBitsToDouble(this.getRaw(this.checkedByteOffset(i)));
   }

   private long getRaw(long offset) {
      return unsafe.getLongVolatile(this.array, offset);
   }

   public final void set(int i, double newValue) {
      long next = Double.doubleToRawLongBits(newValue);
      unsafe.putLongVolatile(this.array, this.checkedByteOffset(i), next);
   }

   public final void lazySet(int i, double newValue) {
      long next = Double.doubleToRawLongBits(newValue);
      unsafe.putOrderedLong(this.array, this.checkedByteOffset(i), next);
   }

   public final double getAndSet(int i, double newValue) {
      long next = Double.doubleToRawLongBits(newValue);
      long offset = this.checkedByteOffset(i);

      long current;
      do {
         current = this.getRaw(offset);
      } while(!this.compareAndSetRaw(offset, current, next));

      return Double.longBitsToDouble(current);
   }

   public final boolean compareAndSet(int i, double expect, double update) {
      return this.compareAndSetRaw(this.checkedByteOffset(i), Double.doubleToRawLongBits(expect), Double.doubleToRawLongBits(update));
   }

   private boolean compareAndSetRaw(long offset, long expect, long update) {
      return unsafe.compareAndSwapLong(this.array, offset, expect, update);
   }

   public final boolean weakCompareAndSet(int i, double expect, double update) {
      return this.compareAndSet(i, expect, update);
   }

   public final double getAndAdd(int i, double delta) {
      long offset = this.checkedByteOffset(i);

      long current;
      double currentVal;
      long next;
      do {
         current = this.getRaw(offset);
         currentVal = Double.longBitsToDouble(current);
         double nextVal = currentVal + delta;
         next = Double.doubleToRawLongBits(nextVal);
      } while(!this.compareAndSetRaw(offset, current, next));

      return currentVal;
   }

   public double addAndGet(int i, double delta) {
      long offset = this.checkedByteOffset(i);

      long current;
      double nextVal;
      long next;
      do {
         current = this.getRaw(offset);
         double currentVal = Double.longBitsToDouble(current);
         nextVal = currentVal + delta;
         next = Double.doubleToRawLongBits(nextVal);
      } while(!this.compareAndSetRaw(offset, current, next));

      return nextVal;
   }

   public String toString() {
      int iMax = this.array.length - 1;
      if (iMax == -1) {
         return "[]";
      } else {
         StringBuilder b = new StringBuilder(19 * (iMax + 1));
         b.append('[');
         int i = 0;

         while(true) {
            b.append(Double.longBitsToDouble(this.getRaw(byteOffset(i))));
            if (i == iMax) {
               return b.append(']').toString();
            }

            b.append(',').append(' ');
            ++i;
         }
      }
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();
      int length = this.length();
      s.writeInt(length);

      for(int i = 0; i < length; ++i) {
         s.writeDouble(this.get(i));
      }

   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      int length = s.readInt();
      unsafe.putObjectVolatile(this, arrayOffset, new long[length]);

      for(int i = 0; i < length; ++i) {
         this.set(i, s.readDouble());
      }

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
      base = unsafe.arrayBaseOffset(long[].class);

      try {
         Class k = AtomicDoubleArray.class;
         arrayOffset = unsafe.objectFieldOffset(k.getDeclaredField("array"));
         int scale = unsafe.arrayIndexScale(long[].class);
         if ((scale & scale - 1) != 0) {
            throw new Error("data type scale not a power of two");
         } else {
            shift = 31 - Integer.numberOfLeadingZeros(scale);
         }
      } catch (Exception var2) {
         throw new Error(var2);
      }
   }
}
