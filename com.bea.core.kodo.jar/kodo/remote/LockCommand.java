package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.FindCallbacks;
import org.apache.openjpa.kernel.LockManager;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.util.ObjectNotFoundException;

class LockCommand extends BrokerCommand {
   private Object[] _oids;
   private int _level;
   private int[] _levels;
   private int _timeout;

   LockCommand() {
      super((byte)20);
      this._oids = null;
      this._level = 0;
      this._levels = null;
      this._timeout = 0;
   }

   public LockCommand(Object oid, int level, int timeout) {
      this();
      this._oids = new Object[]{oid};
      this._level = level;
      this._timeout = timeout;
   }

   public LockCommand(Object[] oids, int level, int timeout) {
      this();
      this._oids = oids;
      this._level = level;
      this._timeout = timeout;
   }

   public Object[] getObjectIds() {
      return this._oids;
   }

   public int getLockLevel() {
      return this._level;
   }

   public int getTimeout() {
      return this._timeout;
   }

   public int[] getLockLevels() {
      return this._levels;
   }

   public void execute(Broker broker) {
      LockManager lm = broker.getLockManager();
      this._levels = new int[this._oids.length];

      for(int i = 0; i < this._oids.length; ++i) {
         Object pc = broker.find(this._oids[i], true, (FindCallbacks)null);
         if (pc == null) {
            throw new ObjectNotFoundException(this._oids[i]);
         }

         broker.lock(pc, this._level, this._timeout, ServerOpCallbacks.getInstance());
         OpenJPAStateManager sm = broker.getStateManager(pc);
         this._levels[i] = lm.getLockLevel(sm);
      }

   }

   protected void read(ObjectInput in) throws Exception {
      this._oids = (Object[])((Object[])in.readObject());
      this._level = in.readInt();
      this._timeout = in.readInt();
   }

   protected void write(ObjectOutput out) throws Exception {
      out.writeObject(this._oids);
      out.writeInt(this._level);
      out.writeInt(this._timeout);
   }

   protected void readResponse(ObjectInput in) throws Exception {
      this._levels = (int[])((int[])in.readObject());
   }

   protected void writeResponse(ObjectOutput out) throws Exception {
      out.writeObject(this._levels);
   }
}
