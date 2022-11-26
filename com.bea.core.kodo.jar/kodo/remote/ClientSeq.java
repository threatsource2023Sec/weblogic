package kodo.remote;

import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;

class ClientSeq implements Seq {
   private final String _name;
   private final ClassMetaData _cls;
   private final String _field;

   public ClientSeq(ClassMetaData meta) {
      this._cls = meta;
      this._name = null;
      this._field = null;
   }

   public ClientSeq(FieldMetaData fmd) {
      this._cls = fmd.getDeclaringMetaData();
      this._field = fmd.getName();
      this._name = null;
   }

   public ClientSeq(String name) {
      this._name = name;
      this._cls = null;
      this._field = null;
   }

   public void setType(int type) {
   }

   public Object next(StoreContext ctx, ClassMetaData cls) {
      if (cls == null) {
         cls = this._cls;
      }

      Class desc = cls == null ? null : cls.getDescribedType();
      ClientStoreManager store = (ClientStoreManager)ctx.getStoreManager().getInnermostDelegate();
      GetNextSequenceCommand cmd = new GetNextSequenceCommand(this._name, desc, this._field);
      store.send(cmd);
      return cmd.getNext();
   }

   public Object current(StoreContext ctx, ClassMetaData cls) {
      return null;
   }

   public void allocate(int additional, StoreContext ctx, ClassMetaData cls) {
   }

   public void close() {
   }
}
