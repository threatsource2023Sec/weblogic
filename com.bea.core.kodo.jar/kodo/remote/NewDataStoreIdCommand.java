package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.meta.ClassMetaData;

class NewDataStoreIdCommand extends BrokerCommand {
   private Object _val;
   private Class _class;
   private Object _oid;

   NewDataStoreIdCommand() {
      super((byte)11);
      this._val = null;
      this._class = null;
      this._oid = null;
   }

   public NewDataStoreIdCommand(Object val, Class cls) {
      this();
      this._val = val;
      this._class = cls;
   }

   public Object getObjectIdValue() {
      return this._val;
   }

   public Class getPCType() {
      return this._class;
   }

   public Object getObjectId() {
      return this._oid;
   }

   public void execute(Broker broker) {
      ClassMetaData meta = getMetaData(broker, this._class);
      this._oid = broker.getStoreManager().newDataStoreId(this._val, meta);
   }

   protected void read(ObjectInput in) throws Exception {
      this._val = in.readObject();
      this._class = (Class)in.readObject();
   }

   protected void write(ObjectOutput out) throws Exception {
      out.writeObject(this._val);
      out.writeObject(this._class);
   }

   protected void readResponse(ObjectInput in) throws Exception {
      this._oid = in.readObject();
   }

   protected void writeResponse(ObjectOutput out) throws Exception {
      out.writeObject(this._oid);
   }
}
