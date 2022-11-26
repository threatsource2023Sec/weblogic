package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.OpenJPAStateManager;

class CompareVersionCommand extends BrokerCommand {
   private Object _oid;
   private Object _version1;
   private Object _version2;
   private int _comp;

   CompareVersionCommand() {
      super((byte)4);
      this._oid = null;
      this._version1 = null;
      this._version2 = null;
      this._comp = 0;
   }

   public CompareVersionCommand(Object oid, Object version1, Object version2) {
      this();
      this._oid = oid;
      this._version1 = version1;
      this._version2 = version2;
   }

   public Object getObjectId() {
      return this._oid;
   }

   public Object getVersion1() {
      return this._version1;
   }

   public Object getVersion2() {
      return this._version2;
   }

   public int compareVersion() {
      return this._comp;
   }

   public void execute(Broker broker) {
      OpenJPAStateManager sm = getStateManager(broker, (Object)null, this._oid, true);
      this._comp = broker.getStoreManager().compareVersion(sm, this._version1, this._version2);
   }

   protected void read(ObjectInput in) throws Exception {
      this._oid = in.readObject();
      this._version1 = in.readObject();
      this._version2 = in.readObject();
   }

   protected void write(ObjectOutput out) throws Exception {
      out.writeObject(this._oid);
      out.writeObject(this._version1);
      out.writeObject(this._version2);
   }

   protected void readResponse(ObjectInput in) throws Exception {
      this._comp = in.readInt();
   }

   protected void writeResponse(ObjectOutput out) throws Exception {
      out.writeInt(this._comp);
   }
}
