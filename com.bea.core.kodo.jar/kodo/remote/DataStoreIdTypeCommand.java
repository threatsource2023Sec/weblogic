package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.meta.ClassMetaData;

class DataStoreIdTypeCommand extends BrokerCommand {
   private Class _pcClass;
   private Class _class;

   DataStoreIdTypeCommand() {
      super((byte)6);
      this._pcClass = null;
      this._class = null;
   }

   public DataStoreIdTypeCommand(Class pcClass) {
      this();
      this._pcClass = pcClass;
   }

   public Class getPCClass() {
      return this._pcClass;
   }

   public Class getDataStoreIdType() {
      return this._class;
   }

   public void execute(Broker broker) {
      ClassMetaData meta = getMetaData(broker, this._pcClass);
      this._class = broker.getStoreManager().getDataStoreIdType(meta);
   }

   protected void read(ObjectInput in) throws Exception {
      this._pcClass = (Class)in.readObject();
   }

   protected void write(ObjectOutput out) throws Exception {
      out.writeObject(this._pcClass);
   }

   protected void readResponse(ObjectInput in) throws Exception {
      this._class = (Class)in.readObject();
   }

   protected void writeResponse(ObjectOutput out) throws Exception {
      out.writeObject(this._class);
   }
}
