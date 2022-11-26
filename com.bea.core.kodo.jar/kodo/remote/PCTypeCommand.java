package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.apache.openjpa.kernel.Broker;

class PCTypeCommand extends BrokerCommand {
   private Object _oid;
   private Class _class;

   PCTypeCommand() {
      super((byte)13);
      this._oid = null;
      this._class = null;
   }

   public PCTypeCommand(Object oid) {
      this();
      this._oid = oid;
   }

   public Object getObjectId() {
      return this._oid;
   }

   public Class getPCType() {
      return this._class;
   }

   public void execute(Broker broker) {
      this._class = broker.getStoreManager().getManagedType(this._oid);
   }

   protected void read(ObjectInput in) throws Exception {
      this._oid = in.readObject();
   }

   protected void write(ObjectOutput out) throws Exception {
      out.writeObject(this._oid);
   }

   protected void readResponse(ObjectInput in) throws Exception {
      this._class = (Class)in.readObject();
   }

   protected void writeResponse(ObjectOutput out) throws Exception {
      out.writeObject(this._class);
   }
}
