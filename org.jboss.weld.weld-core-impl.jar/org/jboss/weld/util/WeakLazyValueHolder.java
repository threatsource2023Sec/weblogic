package org.jboss.weld.util;

import java.lang.ref.WeakReference;
import java.util.function.Supplier;

public abstract class WeakLazyValueHolder implements ValueHolder {
   private volatile WeakReference reference;

   public static WeakLazyValueHolder forSupplier(final Supplier supplier) {
      return new WeakLazyValueHolder() {
         protected Object computeValue() {
            return supplier.get();
         }
      };
   }

   public Object get() {
      WeakReference reference = this.reference;
      Object value = null;
      if (reference != null) {
         value = reference.get();
      }

      if (value != null) {
         return value;
      } else {
         synchronized(this) {
            if (this.reference == reference) {
               Object newValue = this.computeValue();
               this.reference = new WeakReference(newValue);
            }

            return this.reference.get();
         }
      }
   }

   public Object getIfPresent() {
      WeakReference reference = this.reference;
      return reference != null ? reference.get() : null;
   }

   public void clear() {
      synchronized(this) {
         this.reference = null;
      }
   }

   protected abstract Object computeValue();
}
