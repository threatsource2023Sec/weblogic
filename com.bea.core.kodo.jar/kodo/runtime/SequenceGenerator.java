package kodo.runtime;

import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.meta.ClassMetaData;

/** @deprecated */
public class SequenceGenerator {
   private final Seq _seq;
   private final Broker _broker;
   private final ClassMetaData _cls;

   public SequenceGenerator(Seq seq, Broker broker, ClassMetaData cls) {
      this._seq = seq;
      this._broker = broker;
      this._cls = cls;
   }

   public Seq getDelegate() {
      return this._seq;
   }

   public Number getNext() {
      return (Number)this._seq.next(this._broker, this._cls);
   }

   public long next() {
      return this.getNext().longValue();
   }

   public boolean ensureCapacity(int count) {
      this._seq.allocate(count, this._broker, this._cls);
      return true;
   }
}
