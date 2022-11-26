package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

class PersistCommand extends BrokerCommand {
   private static final Localizer _loc = Localizer.forPackage(PersistCommand.class);
   private Object _id;
   private Object _oid;
   private Class _class;

   PersistCommand() {
      super((byte)10);
      this._id = null;
      this._oid = null;
      this._class = null;
   }

   public PersistCommand(Object id, Object oid, Class cls) {
      this();
      this._id = id;
      this._oid = oid;
      this._class = cls;
   }

   public Class getPCType() {
      return this._class;
   }

   public Object getId() {
      return this._id;
   }

   public Object getObjectId() {
      return this._oid;
   }

   public void execute(Broker broker) {
      PersistenceCapable pc;
      if (this._oid != null) {
         pc = PCRegistry.newInstance(this._class, (StateManager)null, this._oid, false);
      } else {
         pc = PCRegistry.newInstance(this._class, (StateManager)null, false);
      }

      if (pc == null) {
         throw new UserException(_loc.get("cant-create", this._class));
      } else {
         OpenJPAStateManager sm = broker.persist(pc, this._id, ServerOpCallbacks.getInstance());
         sm.assignObjectId(false);
         this._oid = sm.getObjectId();
      }
   }

   protected void read(ObjectInput in) throws Exception {
      this._id = in.readObject();
      this._oid = in.readObject();
      this._class = (Class)in.readObject();
   }

   protected void write(ObjectOutput out) throws Exception {
      out.writeObject(this._id);
      out.writeObject(this._oid);
      out.writeObject(this._class);
   }

   protected void readResponse(ObjectInput in) throws Exception {
      this._oid = in.readObject();
   }

   protected void writeResponse(ObjectOutput out) throws Exception {
      out.writeObject(this._oid);
   }
}
