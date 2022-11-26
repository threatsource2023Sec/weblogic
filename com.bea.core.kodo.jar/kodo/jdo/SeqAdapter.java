package kodo.jdo;

import javax.jdo.datastore.Sequence;
import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.meta.ClassMetaData;

public class SeqAdapter implements Seq {
   private final Sequence _seq;

   public SeqAdapter(Sequence seq) {
      this._seq = seq;
   }

   public Sequence getDelegate() {
      return this._seq;
   }

   public void setType(int type) {
   }

   public Object next(StoreContext ctx, ClassMetaData cls) {
      return this._seq.next();
   }

   public Object current(StoreContext ctx, ClassMetaData cls) {
      return this._seq.current();
   }

   public void allocate(int additional, StoreContext ctx, ClassMetaData cls) {
      this._seq.allocate(additional);
   }

   public void close() {
   }
}
