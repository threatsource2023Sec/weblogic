package kodo.remote;

import java.util.BitSet;
import org.apache.openjpa.kernel.AbstractPCData;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.LockManager;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCDataImpl;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.ValueMetaData;

public class RemotePCDataImpl extends PCDataImpl implements RemotePCData {
   private static final byte FLAG_REMOTE_FLUSH = 1;
   private static final byte FLAG_NEW = 2;
   private int _lockLevel = 0;
   private byte _flags = 0;

   public RemotePCDataImpl(Object oid, ClassMetaData meta) {
      super(oid, meta);
   }

   public boolean isNewInstance() {
      return (this._flags & 2) > 0;
   }

   public void setNewInstance(boolean newInstance) {
      if (newInstance) {
         this._flags = (byte)(this._flags | 2);
      } else {
         this._flags &= -3;
      }

   }

   public boolean isRemoteFlush() {
      return (this._flags & 1) > 0;
   }

   public void setRemoteFlush(boolean remote) {
      if (remote) {
         this._flags = (byte)(this._flags | 1);
      } else {
         this._flags &= -2;
      }

   }

   public int getLockLevel() {
      return this._lockLevel;
   }

   public void load(OpenJPAStateManager sm, FetchConfiguration fetch, Object context) {
      if (this.isRemoteFlush()) {
         this.loadVersion(sm);
         FieldMetaData[] fmds = sm.getMetaData().getFields();

         for(int i = 0; i < fmds.length; ++i) {
            if (this.isLoaded(i)) {
               Object val = this.toField(sm, fmds[i], this.getData(i), fetch, context);
               sm.setRemote(i, val);
            }
         }
      } else {
         this.loadLockLevel(sm);
         super.load(sm, fetch, context);
      }

   }

   public void load(OpenJPAStateManager sm, BitSet fields, FetchConfiguration fetch, Object context) {
      if (this.isRemoteFlush()) {
         this.load(sm, fetch, context);
      } else {
         this.loadLockLevel(sm);
         super.load(sm, fields, fetch, context);
      }

   }

   private void loadLockLevel(OpenJPAStateManager sm) {
      LockManager lm = sm.getContext().getLockManager();
      if (lm instanceof ClientLockManager) {
         ((ClientLockManager)lm).serverLocked(sm, this._lockLevel);
      }

   }

   protected void loadVersion(OpenJPAStateManager sm) {
      if (this.isNewInstance() && !this.isRemoteFlush()) {
         sm.setNextVersion(this.getVersion());
      } else if (this.isRemoteFlush()) {
         sm.setVersion(this.getVersion());
      } else {
         super.loadVersion(sm);
      }

   }

   protected void loadImplData(OpenJPAStateManager sm) {
   }

   protected void loadImplData(OpenJPAStateManager sm, FieldMetaData fmd) {
   }

   public Object toRelationField(OpenJPAStateManager sm, ValueMetaData vmd, Object data, FetchConfiguration fetch, Object context) {
      StoreContext ctx = sm.getContext();
      return ctx.find(data, fetch, (BitSet)null, context, this.isRemoteFlush() ? 16 : 0);
   }

   public AbstractPCData newEmbeddedPCData(OpenJPAStateManager sm) {
      RemotePCDataImpl pcdata = new RemotePCDataImpl(sm.getId(), sm.getMetaData());
      pcdata.setRemoteFlush(this.isRemoteFlush());
      pcdata.setNewInstance(sm.isNew() && sm.getObjectId() == null);
      return pcdata;
   }

   public void store(OpenJPAStateManager sm) {
      if (!this.isRemoteFlush()) {
         this.storeLockLevel(sm);
      }

      super.store(sm);
   }

   public void store(OpenJPAStateManager sm, BitSet fields) {
      if (!this.isRemoteFlush()) {
         this.storeLockLevel(sm);
      }

      super.store(sm, fields);
   }

   private void storeLockLevel(OpenJPAStateManager sm) {
      this._lockLevel = sm.getContext().getLockManager().getLockLevel(sm);
   }

   protected void storeImplData(OpenJPAStateManager sm) {
   }

   protected void storeImplData(OpenJPAStateManager sm, FieldMetaData fmd, boolean fieldLoaded) {
   }

   protected Object toRelationData(Object val, StoreContext ctx) {
      if (!this.isRemoteFlush()) {
         return ctx.getObjectId(val);
      } else {
         OpenJPAStateManager sm = ctx.getStateManager(val);
         if (sm == null) {
            return null;
         } else {
            return sm.getObjectId() == null ? sm.getId() : sm.getObjectId();
         }
      }
   }
}
