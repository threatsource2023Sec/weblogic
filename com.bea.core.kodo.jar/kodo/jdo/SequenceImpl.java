package kodo.jdo;

import javax.jdo.datastore.Sequence;
import org.apache.openjpa.kernel.DelegatingSeq;
import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.meta.ClassMetaData;

public class SequenceImpl implements Sequence {
   private final DelegatingSeq _seq;
   private final String _name;
   private final StoreContext _ctx;
   private final ClassMetaData _cls;

   public SequenceImpl(Seq seq, String name, StoreContext ctx, ClassMetaData cls) {
      this._seq = new DelegatingSeq(seq, JDOExceptions.TRANSLATOR);
      this._name = name;
      this._ctx = ctx;
      this._cls = cls;
   }

   public Seq getDelegate() {
      return this._seq.getDelegate();
   }

   public String getName() {
      return this._name;
   }

   public Object next() {
      return this._seq.next(this._ctx, this._cls);
   }

   public long nextValue() {
      return ((Number)this.next()).longValue();
   }

   public Object current() {
      return this._seq.current(this._ctx, this._cls);
   }

   public long currentValue() {
      return ((Number)this.current()).longValue();
   }

   public void allocate(int additional) {
      this._seq.allocate(additional, this._ctx, this._cls);
   }

   public int hashCode() {
      return this._seq.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof SequenceImpl) ? false : this._seq.equals(((SequenceImpl)other)._seq);
      }
   }
}
