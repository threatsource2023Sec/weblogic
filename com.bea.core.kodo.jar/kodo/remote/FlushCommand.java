package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.FindCallbacks;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.ObjectNotFoundException;
import org.apache.openjpa.util.OptimisticException;

class FlushCommand extends KodoCommand {
   private RemotePCData[] _inserts;
   private RemotePCData[] _updates;
   private Object[] _deletes;
   private Object[] _failed;
   private Object[] _versions;

   FlushCommand() {
      super((byte)8);
      this._inserts = null;
      this._updates = null;
      this._deletes = null;
      this._failed = null;
      this._versions = null;
   }

   public FlushCommand(RemotePCData[] inserts, RemotePCData[] updates, Object[] deletes) {
      this();
      this._inserts = inserts;
      this._updates = updates;
      this._deletes = deletes;
   }

   public RemotePCData[] getInsertPCDatas() {
      return this._inserts;
   }

   public RemotePCData[] getUpdatePCDatas() {
      return this._updates;
   }

   public Object[] getDeleteObjectIds() {
      return this._deletes;
   }

   public Object[] getFailedIds() {
      return this._failed;
   }

   public Object[] getUpdateVersions() {
      return this._versions;
   }

   public void execute(KodoContextFactory context) {
      Broker broker = (Broker)context.getContext(this.getClientId());
      OpenJPAStateManager[] ins = this._inserts == null ? null : new OpenJPAStateManager[this._inserts.length];
      OpenJPAStateManager[] ups = this._updates == null ? null : new OpenJPAStateManager[this._updates.length];
      PersistenceCapable pc;
      if (this._inserts != null) {
         for(int i = 0; i < this._inserts.length; ++i) {
            if (this._inserts[i].isNewInstance()) {
               ClassMetaData meta = broker.getConfiguration().getMetaDataRepositoryInstance().getMetaData(this._inserts[i].getType(), broker.getClassLoader(), true);
               if (meta.getIdentityType() == 2) {
                  pc = PCRegistry.newInstance(this._inserts[i].getType(), (StateManager)null, this._inserts[i].getId(), false);
               } else {
                  pc = PCRegistry.newInstance(this._inserts[i].getType(), (StateManager)null, false);
               }

               ins[i] = broker.persist(pc, this._inserts[i].getId(), ServerOpCallbacks.getInstance());
            }
         }
      }

      pc = null;
      Collection failed = this.update(broker, ins, this._inserts, pc);
      failed = this.update(broker, ups, this._updates, failed);
      failed = this.delete(broker, failed);

      try {
         broker.flush();
      } catch (OptimisticException var10) {
         failed = this.addFailedId(var10, failed, broker);
      }

      if (ins != null) {
         RemotePCDataGenerator gen = context.getPCDataGenerator();

         for(int i = 0; i < ins.length; ++i) {
            this._inserts[i] = null;
            if (ins[i] != null) {
               Object oid = ins[i].getObjectId();
               ClassMetaData meta = ins[i].getMetaData();
               if (gen == null) {
                  this._inserts[i] = new RemotePCDataImpl(oid, meta);
               } else {
                  this._inserts[i] = (RemotePCData)gen.generatePCData(oid, meta);
               }

               this._inserts[i].setNewInstance(true);
               this.store(ins[i], this._inserts[i]);
            }
         }
      }

      if (ups != null) {
         this._versions = new Object[ups.length];

         for(int i = 0; i < ups.length; ++i) {
            if (ups[i] != null) {
               this._versions[i] = ups[i].getVersion();
            }
         }
      }

      if (failed != null) {
         this._failed = failed.toArray();
      }

   }

   private void store(OpenJPAStateManager sm, RemotePCData pcdata) {
      FieldMetaData[] fmds = sm.getMetaData().getFields();
      BitSet assigned = new BitSet(fmds.length);

      for(int i = 0; i < fmds.length; ++i) {
         if (fmds[i].getValueStrategy() != 0) {
            assigned.set(i);
         }
      }

      pcdata.store(sm, assigned);
   }

   private Collection update(Broker broker, OpenJPAStateManager[] sms, RemotePCData[] datas, Collection failed) {
      if (datas == null) {
         return failed;
      } else {
         FetchConfiguration fetch = broker.getFetchConfiguration();

         for(int i = 0; i < datas.length; ++i) {
            if (sms[i] == null) {
               sms[i] = getStateManager(broker, (Object)null, datas[i].getId(), false);
            }

            if (sms[i] == null) {
               failed = this.addFailedId(datas[i].getId(), failed, broker);
            } else {
               datas[i].load(sms[i], fetch, (Object)null);
            }
         }

         return failed;
      }
   }

   private Collection delete(Broker broker, Collection failed) {
      if (this._deletes == null) {
         return failed;
      } else {
         for(int i = 0; i < this._deletes.length; ++i) {
            try {
               broker.delete(broker.find(this._deletes[i], false, (FindCallbacks)null), ServerOpCallbacks.getInstance());
            } catch (ObjectNotFoundException var5) {
               failed = this.addFailedId(this._deletes[i], failed, broker);
            }
         }

         return failed;
      }
   }

   private Collection addFailedId(OptimisticException oe, Collection failed, Broker b) {
      if (oe.getFailedObject() != null) {
         failed = this.addFailedId(oe.getFailedObject(), failed, b);
      }

      Throwable[] t = oe.getNestedThrowables();
      if (t != null) {
         for(int i = 0; i < t.length; ++i) {
            if (t[i] instanceof OptimisticException) {
               failed = this.addFailedId((OptimisticException)t[i], failed, b);
            }
         }
      }

      return failed;
   }

   private Collection addFailedId(Object obj, Collection failed, Broker b) {
      if (ImplHelper.isManageable(obj)) {
         obj = b.getObjectId(obj);
      }

      if (obj != null) {
         if (failed == null) {
            failed = new ArrayList();
         }

         ((Collection)failed).add(obj);
      }

      return (Collection)failed;
   }

   protected void read(ObjectInput in) throws Exception {
      this._inserts = (RemotePCData[])((RemotePCData[])in.readObject());
      this._updates = (RemotePCData[])((RemotePCData[])in.readObject());
      this._deletes = (Object[])((Object[])in.readObject());
   }

   protected void write(ObjectOutput out) throws Exception {
      out.writeObject(this._inserts);
      out.writeObject(this._updates);
      out.writeObject(this._deletes);
   }

   protected void readResponse(ObjectInput in) throws Exception {
      this._failed = (Object[])((Object[])in.readObject());
      this._inserts = (RemotePCData[])((RemotePCData[])in.readObject());
      this._versions = (Object[])((Object[])in.readObject());
   }

   protected void writeResponse(ObjectOutput out) throws Exception {
      out.writeObject(this._failed);
      out.writeObject(this._inserts);
      out.writeObject(this._versions);
   }
}
