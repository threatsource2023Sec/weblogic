package org.python.google.common.base;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.VisibleForTesting;

@GwtCompatible
public final class Suppliers {
   private Suppliers() {
   }

   public static Supplier compose(Function function, Supplier supplier) {
      Preconditions.checkNotNull(function);
      Preconditions.checkNotNull(supplier);
      return new SupplierComposition(function, supplier);
   }

   public static Supplier memoize(Supplier delegate) {
      if (!(delegate instanceof NonSerializableMemoizingSupplier) && !(delegate instanceof MemoizingSupplier)) {
         return (Supplier)(delegate instanceof Serializable ? new MemoizingSupplier(delegate) : new NonSerializableMemoizingSupplier(delegate));
      } else {
         return delegate;
      }
   }

   public static Supplier memoizeWithExpiration(Supplier delegate, long duration, TimeUnit unit) {
      return new ExpiringMemoizingSupplier(delegate, duration, unit);
   }

   public static Supplier ofInstance(@Nullable Object instance) {
      return new SupplierOfInstance(instance);
   }

   public static Supplier synchronizedSupplier(Supplier delegate) {
      return new ThreadSafeSupplier((Supplier)Preconditions.checkNotNull(delegate));
   }

   public static Function supplierFunction() {
      SupplierFunction sf = Suppliers.SupplierFunctionImpl.INSTANCE;
      return sf;
   }

   private static enum SupplierFunctionImpl implements SupplierFunction {
      INSTANCE;

      public Object apply(Supplier input) {
         return input.get();
      }

      public String toString() {
         return "Suppliers.supplierFunction()";
      }
   }

   private interface SupplierFunction extends Function {
   }

   private static class ThreadSafeSupplier implements Supplier, Serializable {
      final Supplier delegate;
      private static final long serialVersionUID = 0L;

      ThreadSafeSupplier(Supplier delegate) {
         this.delegate = delegate;
      }

      public Object get() {
         synchronized(this.delegate) {
            return this.delegate.get();
         }
      }

      public String toString() {
         return "Suppliers.synchronizedSupplier(" + this.delegate + ")";
      }
   }

   private static class SupplierOfInstance implements Supplier, Serializable {
      final Object instance;
      private static final long serialVersionUID = 0L;

      SupplierOfInstance(@Nullable Object instance) {
         this.instance = instance;
      }

      public Object get() {
         return this.instance;
      }

      public boolean equals(@Nullable Object obj) {
         if (obj instanceof SupplierOfInstance) {
            SupplierOfInstance that = (SupplierOfInstance)obj;
            return Objects.equal(this.instance, that.instance);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Objects.hashCode(this.instance);
      }

      public String toString() {
         return "Suppliers.ofInstance(" + this.instance + ")";
      }
   }

   @VisibleForTesting
   static class ExpiringMemoizingSupplier implements Supplier, Serializable {
      final Supplier delegate;
      final long durationNanos;
      transient volatile Object value;
      transient volatile long expirationNanos;
      private static final long serialVersionUID = 0L;

      ExpiringMemoizingSupplier(Supplier delegate, long duration, TimeUnit unit) {
         this.delegate = (Supplier)Preconditions.checkNotNull(delegate);
         this.durationNanos = unit.toNanos(duration);
         Preconditions.checkArgument(duration > 0L);
      }

      public Object get() {
         long nanos = this.expirationNanos;
         long now = Platform.systemNanoTime();
         if (nanos == 0L || now - nanos >= 0L) {
            synchronized(this) {
               if (nanos == this.expirationNanos) {
                  Object t = this.delegate.get();
                  this.value = t;
                  nanos = now + this.durationNanos;
                  this.expirationNanos = nanos == 0L ? 1L : nanos;
                  return t;
               }
            }
         }

         return this.value;
      }

      public String toString() {
         return "Suppliers.memoizeWithExpiration(" + this.delegate + ", " + this.durationNanos + ", NANOS)";
      }
   }

   @VisibleForTesting
   static class NonSerializableMemoizingSupplier implements Supplier {
      volatile Supplier delegate;
      volatile boolean initialized;
      Object value;

      NonSerializableMemoizingSupplier(Supplier delegate) {
         this.delegate = (Supplier)Preconditions.checkNotNull(delegate);
      }

      public Object get() {
         if (!this.initialized) {
            synchronized(this) {
               if (!this.initialized) {
                  Object t = this.delegate.get();
                  this.value = t;
                  this.initialized = true;
                  this.delegate = null;
                  return t;
               }
            }
         }

         return this.value;
      }

      public String toString() {
         return "Suppliers.memoize(" + this.delegate + ")";
      }
   }

   @VisibleForTesting
   static class MemoizingSupplier implements Supplier, Serializable {
      final Supplier delegate;
      transient volatile boolean initialized;
      transient Object value;
      private static final long serialVersionUID = 0L;

      MemoizingSupplier(Supplier delegate) {
         this.delegate = (Supplier)Preconditions.checkNotNull(delegate);
      }

      public Object get() {
         if (!this.initialized) {
            synchronized(this) {
               if (!this.initialized) {
                  Object t = this.delegate.get();
                  this.value = t;
                  this.initialized = true;
                  return t;
               }
            }
         }

         return this.value;
      }

      public String toString() {
         return "Suppliers.memoize(" + this.delegate + ")";
      }
   }

   private static class SupplierComposition implements Supplier, Serializable {
      final Function function;
      final Supplier supplier;
      private static final long serialVersionUID = 0L;

      SupplierComposition(Function function, Supplier supplier) {
         this.function = function;
         this.supplier = supplier;
      }

      public Object get() {
         return this.function.apply(this.supplier.get());
      }

      public boolean equals(@Nullable Object obj) {
         if (!(obj instanceof SupplierComposition)) {
            return false;
         } else {
            SupplierComposition that = (SupplierComposition)obj;
            return this.function.equals(that.function) && this.supplier.equals(that.supplier);
         }
      }

      public int hashCode() {
         return Objects.hashCode(this.function, this.supplier);
      }

      public String toString() {
         return "Suppliers.compose(" + this.function + ", " + this.supplier + ")";
      }
   }
}
