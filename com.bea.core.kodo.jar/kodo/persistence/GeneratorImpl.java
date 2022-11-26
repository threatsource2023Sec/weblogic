package kodo.persistence;

import org.apache.openjpa.kernel.Seq;

/** @deprecated */
class GeneratorImpl implements Generator {
   private final org.apache.openjpa.persistence.GeneratorImpl _delegate;

   public GeneratorImpl(org.apache.openjpa.persistence.GeneratorImpl del) {
      this._delegate = del;
   }

   public Seq getDelegate() {
      return this._delegate.getDelegate();
   }

   public String getName() {
      return this._delegate.getName();
   }

   public Object next() {
      return this._delegate.next();
   }

   public Object current() {
      return this._delegate.current();
   }

   public void allocate(int additional) {
      this._delegate.allocate(additional);
   }

   public int hashCode() {
      return this._delegate.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof GeneratorImpl) ? false : this._delegate.equals(((GeneratorImpl)other)._delegate);
      }
   }
}
