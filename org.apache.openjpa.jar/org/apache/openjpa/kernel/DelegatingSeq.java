package org.apache.openjpa.kernel;

import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.RuntimeExceptionTranslator;

public class DelegatingSeq implements Seq {
   private final Seq _seq;
   private final DelegatingSeq _del;
   private final RuntimeExceptionTranslator _trans;

   public DelegatingSeq(Seq seq) {
      this(seq, (RuntimeExceptionTranslator)null);
   }

   public DelegatingSeq(Seq seq, RuntimeExceptionTranslator trans) {
      this._seq = seq;
      if (seq instanceof DelegatingSeq) {
         this._del = (DelegatingSeq)seq;
      } else {
         this._del = null;
      }

      this._trans = trans;
   }

   public Seq getDelegate() {
      return this._seq;
   }

   public Seq getInnermostDelegate() {
      return this._del == null ? this._seq : this._del.getInnermostDelegate();
   }

   public int hashCode() {
      return this.getInnermostDelegate().hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         if (other instanceof DelegatingSeq) {
            other = ((DelegatingSeq)other).getInnermostDelegate();
         }

         return this.getInnermostDelegate().equals(other);
      }
   }

   protected RuntimeException translate(RuntimeException re) {
      return this._trans == null ? re : this._trans.translate(re);
   }

   public void setType(int type) {
      try {
         this._seq.setType(type);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Object next(StoreContext ctx, ClassMetaData meta) {
      try {
         return this._seq.next(ctx, meta);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public Object current(StoreContext ctx, ClassMetaData meta) {
      try {
         return this._seq.current(ctx, meta);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void allocate(int additional, StoreContext ctx, ClassMetaData meta) {
      try {
         this._seq.allocate(additional, ctx, meta);
      } catch (RuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public void close() {
      try {
         this._seq.close();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }
}
