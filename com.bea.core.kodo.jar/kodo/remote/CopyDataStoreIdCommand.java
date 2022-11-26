package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.StoreManager;
import org.apache.openjpa.meta.ClassMetaData;

class CopyDataStoreIdCommand extends BrokerCommand {
   private Object _oid;
   private Object _copy;

   CopyDataStoreIdCommand() {
      super((byte)5);
      this._oid = null;
      this._copy = null;
   }

   public CopyDataStoreIdCommand(Object oid) {
      this();
      this._oid = oid;
   }

   public Object getObjectId() {
      return this._oid;
   }

   public Object getCopy() {
      return this._copy;
   }

   public void execute(Broker broker) {
      StoreManager store = broker.getStoreManager();
      ClassMetaData meta = getMetaData(broker, store.getManagedType(this._oid));
      this._copy = store.copyDataStoreId(this._oid, meta);
   }

   protected void read(ObjectInput in) throws Exception {
      this._oid = in.readObject();
   }

   protected void write(ObjectOutput out) throws Exception {
      out.writeObject(this._oid);
   }

   protected void readResponse(ObjectInput in) throws Exception {
      this._copy = in.readObject();
   }

   protected void writeResponse(ObjectOutput out) throws Exception {
      out.writeObject(this._copy);
   }
}
