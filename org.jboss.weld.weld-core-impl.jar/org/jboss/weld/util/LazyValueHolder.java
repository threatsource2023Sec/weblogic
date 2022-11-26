package org.jboss.weld.util;

import java.util.function.Supplier;

public abstract class LazyValueHolder implements ValueHolder {
   private transient volatile Object value;

   public static LazyValueHolder forSupplier(final Supplier supplier) {
      return new LazyValueHolder() {
         protected Object computeValue() {
            return supplier.get();
         }
      };
   }

   public Object get() {
      Object valueCopy = this.value;
      if (valueCopy != null) {
         return valueCopy;
      } else {
         synchronized(this) {
            if (this.value == null) {
               this.value = this.computeValue();
            }

            return this.value;
         }
      }
   }

   public Object getIfPresent() {
      return this.value;
   }

   public void clear() {
      synchronized(this) {
         this.value = null;
      }
   }

   public boolean isAvailable() {
      return this.value != null;
   }

   protected abstract Object computeValue();

   public abstract static class Serializable extends LazyValueHolder implements java.io.Serializable {
      private static final long serialVersionUID = 1L;
   }
}
