package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.BitSet;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.FindCallbacks;
import org.apache.openjpa.kernel.OpenJPAStateManager;

class ExistsCommand extends BrokerCommand {
   private Object _oid;
   private boolean _exists;

   ExistsCommand() {
      super((byte)7);
      this._oid = null;
      this._exists = false;
   }

   public ExistsCommand(Object oid) {
      this();
      this._oid = oid;
   }

   public Object getObjectId() {
      return this._oid;
   }

   public boolean exists() {
      return this._exists;
   }

   public void execute(Broker broker) {
      Object pc = broker.findCached(this._oid, (FindCallbacks)null);
      if (pc != null) {
         OpenJPAStateManager sm = broker.getStateManager(pc);
         if (sm.isTransactional()) {
            this._exists = true;
         } else {
            this._exists = broker.getStoreManager().exists(sm, (Object)null);
         }
      } else {
         this._exists = broker.find(this._oid, (FetchConfiguration)null, (BitSet)null, (Object)null, 0) != null;
      }

   }

   protected void read(ObjectInput in) throws Exception {
      this._oid = in.readObject();
   }

   protected void write(ObjectOutput out) throws Exception {
      out.writeObject(this._oid);
   }

   protected void readResponse(ObjectInput in) throws Exception {
      this._exists = in.readBoolean();
   }

   protected void writeResponse(ObjectOutput out) throws Exception {
      out.writeBoolean(this._exists);
   }
}
