package org.python.google.common.cache;

import java.util.concurrent.atomic.AtomicLong;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Supplier;

@GwtCompatible(
   emulated = true
)
final class LongAddables {
   private static final Supplier SUPPLIER;

   public static LongAddable create() {
      return (LongAddable)SUPPLIER.get();
   }

   static {
      Supplier supplier;
      try {
         new LongAdder();
         supplier = new Supplier() {
            public LongAddable get() {
               return new LongAdder();
            }
         };
      } catch (Throwable var2) {
         supplier = new Supplier() {
            public LongAddable get() {
               return new PureJavaLongAddable();
            }
         };
      }

      SUPPLIER = supplier;
   }

   private static final class PureJavaLongAddable extends AtomicLong implements LongAddable {
      private PureJavaLongAddable() {
      }

      public void increment() {
         this.getAndIncrement();
      }

      public void add(long x) {
         this.getAndAdd(x);
      }

      public long sum() {
         return this.get();
      }

      // $FF: synthetic method
      PureJavaLongAddable(Object x0) {
         this();
      }
   }
}
