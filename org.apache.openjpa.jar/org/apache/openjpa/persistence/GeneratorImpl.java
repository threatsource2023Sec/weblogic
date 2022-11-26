package org.apache.openjpa.persistence;

import org.apache.openjpa.kernel.DelegatingSeq;
import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.meta.ClassMetaData;

public class GeneratorImpl implements Generator {
   private final DelegatingSeq _seq;
   private final String _name;
   private final StoreContext _ctx;
   private final ClassMetaData _meta;

   public GeneratorImpl(Seq seq, String name, StoreContext ctx, ClassMetaData meta) {
      this._seq = new DelegatingSeq(seq, PersistenceExceptions.TRANSLATOR);
      this._name = name;
      this._ctx = ctx;
      this._meta = meta;
   }

   public Seq getDelegate() {
      return this._seq.getDelegate();
   }

   public String getName() {
      return this._name;
   }

   public Object next() {
      return this._seq.next(this._ctx, this._meta);
   }

   public Object current() {
      return this._seq.current(this._ctx, this._meta);
   }

   public void allocate(int additional) {
      this._seq.allocate(additional, this._ctx, this._meta);
   }

   public int hashCode() {
      return this._seq.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof GeneratorImpl) ? false : this._seq.equals(((GeneratorImpl)other)._seq);
      }
   }
}
