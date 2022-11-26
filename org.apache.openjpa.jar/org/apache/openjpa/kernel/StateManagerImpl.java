package org.apache.openjpa.kernel;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.enhance.DynamicPersistenceCapable;
import org.apache.openjpa.enhance.FieldManager;
import org.apache.openjpa.enhance.ManagedInstanceProvider;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.RedefinitionHelper;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.event.LifecycleEventManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FetchGroup;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.ApplicationIds;
import org.apache.openjpa.util.Exceptions;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.InvalidStateException;
import org.apache.openjpa.util.ObjectNotFoundException;
import org.apache.openjpa.util.OpenJPAId;
import org.apache.openjpa.util.ProxyManager;
import org.apache.openjpa.util.RuntimeExceptionTranslator;
import org.apache.openjpa.util.UserException;
import serp.util.Numbers;

public class StateManagerImpl implements OpenJPAStateManager, Serializable {
   public static final int LOAD_FGS = 0;
   public static final int LOAD_ALL = 1;
   public static final int LOAD_SERIALIZE = 2;
   private static final int FLAG_SAVE = 2;
   private static final int FLAG_DEREF = 4;
   private static final int FLAG_LOADED = 8;
   private static final int FLAG_READ_LOCKED = 16;
   private static final int FLAG_WRITE_LOCKED = 32;
   private static final int FLAG_OID_ASSIGNED = 64;
   private static final int FLAG_LOADING = 128;
   private static final int FLAG_PRE_DELETING = 256;
   private static final int FLAG_FLUSHED = 512;
   private static final int FLAG_PRE_FLUSHED = 1024;
   private static final int FLAG_FLUSHED_DIRTY = 2048;
   private static final int FLAG_IMPL_CACHE = 4096;
   private static final int FLAG_INVERSES = 8192;
   private static final int FLAG_NO_UNPROXY = 16384;
   private static final int FLAG_VERSION_CHECK = 32768;
   private static final int FLAG_VERSION_UPDATE = 65536;
   private static final int FLAG_DETACHING = 131072;
   private static final Localizer _loc = Localizer.forPackage(StateManagerImpl.class);
   private transient PersistenceCapable _pc = null;
   private transient ClassMetaData _meta = null;
   private BitSet _loaded = null;
   private BitSet _dirty = null;
   private BitSet _flush = null;
   private int _flags = 0;
   private Object _id = null;
   private Object _oid = null;
   private transient BrokerImpl _broker;
   private PCState _state;
   private Object _version;
   private Object _loadVersion;
   private Object _lock;
   private int _readLockLevel;
   private int _writeLockLevel;
   private SingleFieldManager _single;
   private SaveFieldManager _saved;
   private FieldManager _fm;
   private Object _impl;
   private Object[] _fieldImpl;
   private StateManagerImpl _owner;
   private int _ownerIndex;

   protected StateManagerImpl(Object id, ClassMetaData meta, BrokerImpl broker) {
      this._state = PCState.TRANSIENT;
      this._version = null;
      this._loadVersion = null;
      this._lock = null;
      this._readLockLevel = -1;
      this._writeLockLevel = -1;
      this._single = null;
      this._saved = null;
      this._fm = null;
      this._impl = null;
      this._fieldImpl = null;
      this._owner = null;
      this._ownerIndex = -1;
      this._id = id;
      this._meta = meta;
      this._broker = broker;
      this._single = new SingleFieldManager(this, broker);
      if (this._meta.getIdentityType() == 0) {
         throw new UserException(_loc.get("meta-unknownid", (Object)this._meta));
      }
   }

   void setOwner(StateManagerImpl owner, ValueMetaData ownerMeta) {
      this._owner = owner;
      this._ownerIndex = ownerMeta.getFieldMetaData().getIndex();
   }

   boolean isLoading() {
      return (this._flags & 128) > 0;
   }

   void setLoading(boolean loading) {
      if (loading) {
         this._flags |= 128;
      } else {
         this._flags &= -129;
      }

   }

   private void setPCState(PCState state) {
      if (this._state != state) {
         this.lock();

         try {
            this._broker.getStoreManager().beforeStateChange(this, this._state, state);
            boolean wasDeleted = this._state.isDeleted();
            boolean wasDirty = this._state.isDirty();
            boolean wasPending = this._state.isPendingTransactional();
            this._state = state;
            if (this._state.isTransactional()) {
               this._broker.addToTransaction(this);
               if (this._state.isDeleted() == wasDeleted) {
                  if (this._state.isDirty() && !wasDirty) {
                     this._broker.setDirty(this, true);
                  }
               } else {
                  this._broker.setDirty(this, !wasDirty || this.isFlushed());
               }
            } else if (!wasPending && this._state.isPendingTransactional()) {
               this._broker.addToPendingTransaction(this);
            } else if (wasPending && !this._state.isPendingTransactional()) {
               this._broker.removeFromPendingTransaction(this);
            } else {
               this._broker.removeFromTransaction(this);
            }

            this._state.initialize(this);
            if (this._state.isDeleted() && !wasDeleted) {
               this.fireLifecycleEvent(8);
            }
         } finally {
            this.unlock();
         }

      }
   }

   public void initialize(Class cls, PCState state) {
      if (this._meta.getDescribedType() != cls) {
         ClassMetaData sub = this._meta.getRepository().getMetaData(cls, this._broker.getClassLoader(), true);
         if (this._oid != null) {
            if (this._meta.getIdentityType() == 1) {
               this._oid = this._broker.getStoreManager().copyDataStoreId(this._oid, sub);
            } else if (this._meta.isOpenJPAIdentity()) {
               this._oid = ApplicationIds.copy(this._oid, sub);
            } else if (sub.getObjectIdType() != this._meta.getObjectIdType()) {
               Object[] pkFields = ApplicationIds.toPKValues(this._oid, this._meta);
               this._oid = ApplicationIds.fromPKValues(pkFields, sub);
            }
         }

         this._meta = sub;
      }

      PersistenceCapable inst = PCRegistry.newInstance(cls, this, this._oid, true);
      if (inst == null) {
         if (Modifier.isAbstract(cls.getModifiers())) {
            throw new UserException(_loc.get("instantiate-abstract", cls.getName(), this._oid));
         } else {
            throw new InternalException();
         }
      } else {
         this.initialize(inst, state);
      }
   }

   protected void initialize(PersistenceCapable pc, PCState state) {
      if (pc == null) {
         throw new UserException(_loc.get("init-null-pc", (Object)this._meta));
      } else if (pc.pcGetStateManager() != null && pc.pcGetStateManager() != this) {
         throw (new UserException(_loc.get("init-sm-pc", (Object)Exceptions.toString((Object)pc)))).setFailedObject(pc);
      } else {
         pc.pcReplaceStateManager(this);
         FieldMetaData[] fmds = this._meta.getFields();
         this._loaded = new BitSet(fmds.length);
         this._flush = new BitSet(fmds.length);
         this._dirty = new BitSet(fmds.length);

         for(int i = 0; i < fmds.length; ++i) {
            label53: {
               if (!fmds[i].isPrimaryKey()) {
                  int var10000 = fmds[i].getManagement();
                  FieldMetaData var10001 = fmds[i];
                  if (var10000 == 3) {
                     break label53;
                  }
               }

               this._loaded.set(i);
            }

            if (this._broker.getInverseManager() != null && fmds[i].getInverseMetaDatas().length > 0) {
               this._flags |= 8192;
            }
         }

         pc.pcSetDetachedState((Object)null);
         this._pc = pc;
         if (this._oid instanceof OpenJPAId) {
            ((OpenJPAId)this._oid).setManagedInstanceType(this._meta.getDescribedType());
         }

         this.setPCState(state);
         if (this._oid == null || this._broker.getStateManagerImplById(this._oid, false) == null) {
            this._broker.setStateManager(this._id, this, 0);
         }

         if (state == PCState.PNEW) {
            this.fireLifecycleEvent(1);
         }

         if (!this.isIntercepting()) {
            this.saveFields(true);
            if (!this.isNew()) {
               RedefinitionHelper.assignLazyLoadProxies(this);
            }
         }

      }
   }

   public boolean isIntercepting() {
      if (this.getMetaData().isIntercepting()) {
         return true;
      } else {
         return this.getMetaData().getAccessType() != 2 && this._pc instanceof DynamicPersistenceCapable;
      }
   }

   private boolean fireLifecycleEvent(int type) {
      return this._broker.fireLifecycleEvent(this.getManagedInstance(), (Object)null, this._meta, type);
   }

   public void load(FetchConfiguration fetch) {
      this.load(fetch, 0, (BitSet)null, (Object)null, false);
   }

   protected boolean load(FetchConfiguration fetch, int loadMode, BitSet exclude, Object sdata, boolean forWrite) {
      if (!forWrite && (!this.isPersistent() || this.isNew() && !this.isFlushed() || this.isDeleted())) {
         return false;
      } else {
         BitSet fields = this.getUnloadedInternal(fetch, loadMode, exclude);
         boolean active = this._broker.isActive();
         if (!forWrite && fields != null) {
            this.beforeRead(-1);
         }

         int lockLevel = this.calculateLockLevel(active, forWrite, fetch);
         boolean ret = this.loadFields(fields, fetch, lockLevel, sdata);
         this.obtainLocks(active, forWrite, lockLevel, fetch, sdata);
         return ret;
      }
   }

   public Object getManagedInstance() {
      return this._pc instanceof ManagedInstanceProvider ? ((ManagedInstanceProvider)this._pc).getManagedInstance() : this._pc;
   }

   public PersistenceCapable getPersistenceCapable() {
      return this._pc;
   }

   public ClassMetaData getMetaData() {
      return this._meta;
   }

   public OpenJPAStateManager getOwner() {
      return this._owner;
   }

   public int getOwnerIndex() {
      return this._ownerIndex;
   }

   public boolean isEmbedded() {
      return this._owner != null;
   }

   public boolean isFlushed() {
      return (this._flags & 512) > 0;
   }

   public boolean isFlushedDirty() {
      return (this._flags & 2048) > 0;
   }

   public BitSet getLoaded() {
      return this._loaded;
   }

   public BitSet getFlushed() {
      return this._flush;
   }

   public BitSet getDirty() {
      return this._dirty;
   }

   public BitSet getUnloaded(FetchConfiguration fetch) {
      BitSet fields = this.getUnloadedInternal(fetch, 0, (BitSet)null);
      return fields == null ? new BitSet(0) : fields;
   }

   private BitSet getUnloadedInternal(FetchConfiguration fetch, int mode, BitSet exclude) {
      if (exclude == StoreContext.EXCLUDE_ALL) {
         return null;
      } else {
         BitSet fields = null;
         FieldMetaData[] fmds = this._meta.getFields();

         for(int i = 0; i < fmds.length; ++i) {
            if (!this._loaded.get(i) && (exclude == null || !exclude.get(i))) {
               boolean load;
               switch (mode) {
                  case 0:
                     load = fetch == null || fetch.requiresFetch(fmds[i]) != 0;
                     break;
                  case 2:
                     load = !fmds[i].isTransient();
                     break;
                  default:
                     load = true;
               }

               if (load) {
                  if (fields == null) {
                     fields = new BitSet(fmds.length);
                  }

                  fields.set(i);
               }
            }
         }

         return fields;
      }
   }

   public StoreContext getContext() {
      return this._broker;
   }

   BrokerImpl getBroker() {
      return this._broker;
   }

   public Object getId() {
      return this._id;
   }

   public Object getObjectId() {
      StateManagerImpl sm;
      for(sm = this; sm.getOwner() != null; sm = (StateManagerImpl)sm.getOwner()) {
      }

      return sm._oid;
   }

   public void setObjectId(Object oid) {
      this._oid = oid;
      if (this._pc != null && oid instanceof OpenJPAId) {
         ((OpenJPAId)oid).setManagedInstanceType(this._meta.getDescribedType());
      }

   }

   public boolean assignObjectId(boolean flush) {
      this.lock();

      boolean var2;
      try {
         var2 = this.assignObjectId(flush, false);
      } finally {
         this.unlock();
      }

      return var2;
   }

   private boolean assignObjectId(boolean flush, boolean preFlushing) {
      if (this._oid == null && !this.isEmbedded() && this.isPersistent()) {
         if (this._broker.getStoreManager().assignObjectId(this, preFlushing)) {
            if (!preFlushing) {
               this.assertObjectIdAssigned(true);
            }
         } else {
            if (!flush) {
               return false;
            }

            this._broker.flush();
         }

         return true;
      } else {
         return true;
      }
   }

   private void assertObjectIdAssigned(boolean recache) {
      if (this.isNew() && !this.isDeleted() && !this.isProvisional() && (this._flags & 64) == 0) {
         if (this._oid == null) {
            if (this._meta.getIdentityType() == 1) {
               throw new InternalException(Exceptions.toString(this.getManagedInstance()));
            }

            this._oid = ApplicationIds.create(this._pc, this._meta);
         }

         Object orig = this._id;
         this._id = this._oid;
         if (recache) {
            try {
               this._broker.setStateManager(orig, this, 2);
            } catch (RuntimeException var4) {
               this._id = orig;
               this._oid = null;
               throw var4;
            }
         }

         this._flags |= 64;
      }
   }

   private boolean assignField(int field, boolean preFlushing) {
      Object sm;
      for(sm = this; ((OpenJPAStateManager)sm).isEmbedded(); sm = ((OpenJPAStateManager)sm).getOwner()) {
      }

      if (((OpenJPAStateManager)sm).isNew() && !((OpenJPAStateManager)sm).isFlushed() && !((OpenJPAStateManager)sm).isDeleted()) {
         FieldMetaData fmd = this._meta.getField(field);
         if (fmd.getDeclaredTypeCode() != 29) {
            if (fmd.getValueStrategy() == 0) {
               return false;
            } else if (!fmd.isValueGenerated() && !this.isDefaultValue(field)) {
               throw new InvalidStateException(_loc.get("existing-value-override-excep", (Object)fmd.getFullName(false)));
            } else if (fmd.isPrimaryKey() && !this.isEmbedded()) {
               return this.assignObjectId(!preFlushing, preFlushing);
            } else if (this._broker.getStoreManager().assignField(this, field, preFlushing)) {
               fmd.setValueGenerated(true);
               return true;
            } else {
               if (!preFlushing) {
                  this._broker.flush();
               }

               return !preFlushing;
            }
         } else if (this._oid == null && !this.isEmbedded() && this.isPersistent()) {
            FieldMetaData[] pks = fmd.getEmbeddedMetaData().getFields();
            OpenJPAStateManager oidsm = null;
            boolean assign = false;

            for(int i = 0; !assign && i < pks.length; ++i) {
               if (pks[i].getValueStrategy() != 0) {
                  if (oidsm == null) {
                     oidsm = new ObjectIdStateManager(this.fetchObjectField(field), this, fmd);
                  }

                  assign = oidsm.isDefaultValue(i);
               }
            }

            return assign && this.assignObjectId(!preFlushing, preFlushing);
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public Object getLock() {
      return this._lock;
   }

   public void setLock(Object lock) {
      this._lock = lock;
   }

   public Object getVersion() {
      return this._version;
   }

   public void setVersion(Object version) {
      this._loadVersion = version;
      this.assignVersionField(version);
   }

   Object getLoadVersion() {
      return this._loadVersion;
   }

   public void setNextVersion(Object version) {
      this.assignVersionField(version);
   }

   private void assignVersionField(Object version) {
      this._version = version;
      FieldMetaData vfield = this._meta.getVersionField();
      if (vfield != null) {
         this.store(vfield.getIndex(), JavaTypes.convert(version, vfield.getTypeCode()));
      }

   }

   public PCState getPCState() {
      return this._state;
   }

   public synchronized Object getImplData() {
      return this._impl;
   }

   public synchronized Object setImplData(Object data, boolean cacheable) {
      Object old = this._impl;
      this._impl = data;
      if (cacheable && data != null) {
         this._flags |= 4096;
      } else {
         this._flags &= -4097;
      }

      return old;
   }

   public boolean isImplDataCacheable() {
      return (this._flags & 4096) != 0;
   }

   public Object getImplData(int field) {
      return this.getExtraFieldData(field, true);
   }

   public Object setImplData(int field, Object data) {
      return this.setExtraFieldData(field, data, true);
   }

   public synchronized boolean isImplDataCacheable(int field) {
      if (this._fieldImpl != null && this._loaded.get(field)) {
         if (this._meta.getField(field).usesImplData() != null) {
            return false;
         } else {
            int idx = this._meta.getExtraFieldDataIndex(field);
            return idx != -1 && this._fieldImpl[idx] != null;
         }
      } else {
         return false;
      }
   }

   public Object getIntermediate(int field) {
      return this.getExtraFieldData(field, false);
   }

   public void setIntermediate(int field, Object data) {
      this.setExtraFieldData(field, data, false);
   }

   private synchronized Object getExtraFieldData(int field, boolean isLoaded) {
      if (this._fieldImpl != null && this._loaded.get(field) == isLoaded) {
         int idx = this._meta.getExtraFieldDataIndex(field);
         return idx == -1 ? null : this._fieldImpl[idx];
      } else {
         return null;
      }
   }

   private synchronized Object setExtraFieldData(int field, Object data, boolean loaded) {
      int idx = this._meta.getExtraFieldDataIndex(field);
      if (idx == -1) {
         throw new InternalException(String.valueOf(this._meta.getField(field)));
      } else {
         Object old = this._fieldImpl == null ? null : this._fieldImpl[idx];
         if (data != null) {
            if (this._loaded.get(field) != loaded) {
               throw new InternalException(String.valueOf(this._meta.getField(field)));
            }

            if (this._fieldImpl == null) {
               this._fieldImpl = new Object[this._meta.getExtraFieldDataLength()];
            }

            this._fieldImpl[idx] = data;
         } else if (this._fieldImpl != null && this._loaded.get(field) == loaded) {
            this._fieldImpl[idx] = null;
         }

         return old;
      }
   }

   public Object fetch(int field) {
      Object val = this.fetchField(field, false);
      return this._meta.getField(field).getExternalValue(val, this._broker);
   }

   public Object fetchField(int field, boolean transitions) {
      FieldMetaData fmd = this._meta.getField(field);
      if (fmd == null) {
         throw (new UserException(_loc.get("no-field", String.valueOf(field), this.getManagedInstance().getClass()))).setFailedObject(this.getManagedInstance());
      } else {
         if (!fmd.isPrimaryKey() && transitions) {
            this.accessingField(field);
         }

         switch (fmd.getDeclaredTypeCode()) {
            case 0:
               return this.fetchBooleanField(field) ? Boolean.TRUE : Boolean.FALSE;
            case 1:
               return new Byte(this.fetchByteField(field));
            case 2:
               return new Character(this.fetchCharField(field));
            case 3:
               return new Double(this.fetchDoubleField(field));
            case 4:
               return new Float(this.fetchFloatField(field));
            case 5:
               return Numbers.valueOf(this.fetchIntField(field));
            case 6:
               return Numbers.valueOf(this.fetchLongField(field));
            case 7:
               return new Short(this.fetchShortField(field));
            case 8:
               return this.fetchObjectField(field);
            case 9:
               return this.fetchStringField(field);
            default:
               return this.fetchObjectField(field);
         }
      }
   }

   public void store(int field, Object val) {
      val = this._meta.getField(field).getFieldValue(val, this._broker);
      this.storeField(field, val);
   }

   public void storeField(int field, Object val) {
      this.storeField(field, val, this);
   }

   public void dirtyCheck() {
      if (this.needsDirtyCheck()) {
         SaveFieldManager saved = this.getSaveFieldManager();
         if (saved == null) {
            throw new InternalException(_loc.get("no-saved-fields", (Object)this.getMetaData().getDescribedType().getName()));
         } else {
            FieldMetaData[] fmds = this.getMetaData().getFields();

            for(int i = 0; i < fmds.length; ++i) {
               if (!fmds[i].isPrimaryKey() && !fmds[i].isVersion() && this._loaded.get(i) && !saved.isFieldEqual(i, this.fetch(i))) {
                  this.dirty(i);
               }
            }

         }
      }
   }

   private boolean needsDirtyCheck() {
      if (this.isIntercepting()) {
         return false;
      } else if (this.isDeleted()) {
         return false;
      } else {
         return !this.isNew() || this.isFlushed();
      }
   }

   public Object fetchInitialField(int field) {
      FieldMetaData fmd = this._meta.getField(field);
      if (this._broker.getRestoreState() == 0 && ((this._flags & 8192) == 0 || fmd.getInverseMetaDatas().length == 0)) {
         throw new InvalidStateException(_loc.get("restore-unset"));
      } else {
         switch (fmd.getDeclaredTypeCode()) {
            case 8:
            case 11:
            case 12:
            case 13:
            case 14:
            case 28:
               if (this._broker.getRestoreState() != 2 && ((this._flags & 8192) == 0 || fmd.getInverseMetaDatas().length == 0)) {
                  throw new InvalidStateException(_loc.get("mutable-restore-unset"));
               }
         }

         this.lock();

         Object var3;
         try {
            if (this._saved != null && this._loaded.get(field) && this._dirty.get(field)) {
               if (this._saved.getUnloaded().get(field)) {
                  throw new InvalidStateException(_loc.get("initial-unloaded", (Object)fmd));
               }

               this.provideField(this._saved.getState(), this._single, field);
               var3 = fetchField(this._single, fmd);
               return var3;
            }

            var3 = this.fetchField(field, false);
         } finally {
            this.unlock();
         }

         return var3;
      }
   }

   private static Object fetchField(FieldManager fm, FieldMetaData fmd) {
      int field = fmd.getIndex();
      switch (fmd.getDeclaredTypeCode()) {
         case 0:
            return fm.fetchBooleanField(field) ? Boolean.TRUE : Boolean.FALSE;
         case 1:
            return new Byte(fm.fetchByteField(field));
         case 2:
            return new Character(fm.fetchCharField(field));
         case 3:
            return new Double(fm.fetchDoubleField(field));
         case 4:
            return new Float(fm.fetchFloatField(field));
         case 5:
            return Numbers.valueOf(fm.fetchIntField(field));
         case 6:
            return Numbers.valueOf(fm.fetchLongField(field));
         case 7:
            return new Short(fm.fetchShortField(field));
         case 8:
         default:
            return fm.fetchObjectField(field);
         case 9:
            return fm.fetchStringField(field);
      }
   }

   public void setRemote(int field, Object value) {
      this.lock();

      try {
         Boolean stat = this.dirty(field, Boolean.FALSE, false);
         this.storeField(field, value, this._single);
         this.replaceField(this._pc, this._single, field);
         this.postDirty(stat);
      } finally {
         this.unlock();
      }

   }

   void beforeRead(int field) {
      if (field == -1 || !this._meta.getField(field).isPrimaryKey()) {
         if (this._broker.isActive() && !this._broker.isTransactionEnding()) {
            if (this._broker.getOptimistic()) {
               this.setPCState(this._state.beforeOptimisticRead(this, field));
            } else {
               this.setPCState(this._state.beforeRead(this, field));
            }
         } else {
            if (!this._broker.getNontransactionalRead()) {
               throw (new InvalidStateException(_loc.get("non-trans-read"))).setFailedObject(this.getManagedInstance());
            }

            this.setPCState(this._state.beforeNontransactionalRead(this, field));
         }

      }
   }

   void beforeFlush(int reason, OpCallbacks call) {
      this._state.beforeFlush(this, reason == 3, call);
   }

   void afterFlush(int reason) {
      if (this.isPersistent()) {
         if (reason != 2 && reason != 3) {
            boolean wasNew = this.isNew();
            boolean wasFlushed = this.isFlushed();
            boolean wasDeleted = this.isDeleted();
            this._flush.or(this._dirty);
            this.setPCState(this._state.flush(this));
            this._flags |= 512;
            this._flags &= -2049;
            this._flags &= -32769;
            this._flags &= -65537;
            if (reason == 0) {
               this.assertObjectIdAssigned(true);
            }

            if ((this._flags & 1024) > 0) {
               this.fireLifecycleEvent(4);
            }

            if (wasNew && !wasFlushed) {
               this.fireLifecycleEvent(18);
            } else if (wasDeleted) {
               this.fireLifecycleEvent(19);
            } else {
               this.fireLifecycleEvent(21);
            }
         } else if (reason == 2) {
            this.assignVersionField(this._loadVersion);
            if (this.isNew() && (this._flags & 64) == 0) {
               this._oid = null;
            }
         }

         this._flags &= -1025;
      }
   }

   void commit() {
      this.releaseLocks();
      this.setVersion(this._version);
      this._flags &= -513;
      this._flags &= -2049;
      Object orig = this._id;
      this.assertObjectIdAssigned(false);
      boolean wasNew = this.isNew() && !this.isDeleted() && !this.isProvisional();
      if (this._broker.getRetainState()) {
         this.setPCState(this._state.commitRetain(this));
      } else {
         this.setPCState(this._state.commit(this));
      }

      if (wasNew) {
         this._broker.setStateManager(orig, this, 3);
      }

   }

   void rollback() {
      this.releaseLocks();
      this._flags &= -513;
      this._flags &= -2049;
      this.afterFlush(2);
      if (this._broker.getRestoreState() != 0) {
         this.setPCState(this._state.rollbackRestore(this));
      } else {
         this.setPCState(this._state.rollback(this));
      }

   }

   void rollbackToSavepoint(SavepointFieldManager savepoint) {
      this._state = savepoint.getPCState();
      BitSet loaded = savepoint.getLoaded();
      int i = 0;

      for(int len = loaded.length(); i < len; ++i) {
         if (loaded.get(i) && savepoint.restoreField(i)) {
            this.provideField(savepoint.getCopy(), savepoint, i);
            this.replaceField(this._pc, savepoint, i);
         }
      }

      this._loaded = loaded;
      this._dirty = savepoint.getDirty();
      this._flush = savepoint.getFlushed();
      this._version = savepoint.getVersion();
      this._loadVersion = savepoint.getLoadVersion();
   }

   void persist() {
      this.setPCState(this._state.persist(this));
   }

   void delete() {
      this.setPCState(this._state.delete(this));
   }

   void nontransactional() {
      this.setPCState(this._state.nontransactional(this));
   }

   void transactional() {
      this.setPCState(this._state.transactional(this));
   }

   void nonprovisional(boolean logical, OpCallbacks call) {
      this.setPCState(this._state.nonprovisional(this, logical, call));
   }

   void release(boolean unproxy) {
      this.release(unproxy, false);
   }

   void release(boolean unproxy, boolean force) {
      if (!unproxy) {
         this._flags |= 16384;
      }

      try {
         if (force) {
            this.setPCState(PCState.TRANSIENT);
         } else {
            this.setPCState(this._state.release(this));
         }
      } finally {
         this._flags &= -16385;
      }

   }

   void evict() {
      this.setPCState(this._state.evict(this));
   }

   void gatherCascadeRefresh(OpCallbacks call) {
      FieldMetaData[] fmds = this._meta.getFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (this._loaded.get(i) && (fmds[i].getCascadeRefresh() == 1 || fmds[i].getKey().getCascadeRefresh() == 1 || fmds[i].getElement().getCascadeRefresh() == 1)) {
            this._single.storeObjectField(i, this.fetchField(i, false));
            this._single.gatherCascadeRefresh(call);
            this._single.clear();
         }
      }

   }

   public boolean beforeRefresh(boolean refreshAll) {
      if (this.isPersistent() && (!this.isNew() || this.isFlushed())) {
         this.lock();

         boolean var2;
         try {
            if (this.isDirty()) {
               this.clearFields();
               var2 = true;
               return var2;
            }

            if (this._loaded.length() > 0 && (refreshAll || this.isEmbedded() || !this.syncVersion((Object)null))) {
               Object version = this._version;
               this.clearFields();
               if (!refreshAll && !this.isEmbedded()) {
                  this.setVersion(version);
               }

               boolean var3 = true;
               return var3;
            }

            var2 = false;
         } finally {
            this.unlock();
         }

         return var2;
      } else {
         return false;
      }
   }

   void afterRefresh() {
      this.lock();

      try {
         if (!this._broker.isActive()) {
            this.setPCState(this._state.afterNontransactionalRefresh());
         } else if (this._broker.getOptimistic()) {
            this.setPCState(this._state.afterOptimisticRefresh());
         } else {
            this.setPCState(this._state.afterRefresh());
         }
      } finally {
         this.unlock();
      }

   }

   void setDereferencedDependent(boolean deref, boolean notify) {
      if (!deref && (this._flags & 4) > 0) {
         if (notify) {
            this._broker.removeDereferencedDependent(this);
         }

         this._flags &= -5;
      } else if (deref && (this._flags & 4) == 0) {
         this._flags |= 4;
         if (notify) {
            this._broker.addDereferencedDependent(this);
         }
      }

   }

   void readLocked(int readLockLevel, int writeLockLevel) {
      if (readLockLevel != 0) {
         this.transactional();
      }

      this._readLockLevel = readLockLevel;
      this._writeLockLevel = writeLockLevel;
      this._flags |= 16;
      this._flags &= -33;
   }

   private int calculateLockLevel(boolean active, boolean forWrite, FetchConfiguration fetch) {
      if (!active) {
         return 0;
      } else {
         if (fetch == null) {
            fetch = this._broker.getFetchConfiguration();
         }

         if (this._readLockLevel == -1) {
            this._readLockLevel = fetch.getReadLockLevel();
         }

         if (this._writeLockLevel == -1) {
            this._writeLockLevel = fetch.getWriteLockLevel();
         }

         return forWrite ? this._writeLockLevel : this._readLockLevel;
      }
   }

   private void obtainLocks(boolean active, boolean forWrite, int lockLevel, FetchConfiguration fetch, Object sdata) {
      if (active) {
         int flag = forWrite ? 32 : 16;
         if ((this._flags & flag) == 0) {
            if (lockLevel != 0) {
               this.transactional();
            }

            if (fetch == null) {
               fetch = this._broker.getFetchConfiguration();
            }

            this._broker.getLockManager().lock(this, lockLevel, fetch.getLockTimeout(), sdata);
            this._flags |= 16;
            this._flags |= flag;
         }

      }
   }

   private void releaseLocks() {
      if (this._lock != null) {
         this._broker.getLockManager().release(this);
      }

      this._readLockLevel = -1;
      this._writeLockLevel = -1;
      this._flags &= -17;
      this._flags &= -33;
   }

   public boolean serializing() {
      if (this._broker.isSerializing()) {
         return false;
      } else {
         try {
            if (this._meta.isDetachable()) {
               return DetachManager.preSerialize(this);
            } else {
               this.load(this._broker.getFetchConfiguration(), 2, (BitSet)null, (Object)null, false);
               return false;
            }
         } catch (RuntimeException var2) {
            throw this.translate(var2);
         }
      }
   }

   public boolean writeDetached(ObjectOutput out) throws IOException {
      BitSet idxs = new BitSet(this._meta.getFields().length);
      this.lock();

      try {
         boolean detsm = DetachManager.writeDetachedState(this, out, idxs);
         if (detsm) {
            this._flags |= 131072;
         }

         FieldMetaData[] fmds = this._meta.getFields();

         for(int i = 0; i < fmds.length; ++i) {
            if (!fmds[i].isTransient()) {
               this.provideField(this._pc, this._single, i);
               this._single.serialize(out, !idxs.get(i));
               this._single.clear();
            }
         }

         boolean var12 = true;
         return var12;
      } catch (RuntimeException var10) {
         throw this.translate(var10);
      } finally {
         this._flags &= -131073;
         this.unlock();
      }
   }

   public void proxyDetachedDeserialized(int idx) {
      throw new InternalException();
   }

   public boolean isTransactional() {
      return this._state == PCState.TCLEAN || this._state.isTransactional();
   }

   public boolean isPendingTransactional() {
      return this._state.isPendingTransactional();
   }

   public boolean isProvisional() {
      return this._state.isProvisional();
   }

   public boolean isPersistent() {
      return this._state.isPersistent();
   }

   public boolean isNew() {
      return this._state.isNew();
   }

   public boolean isDeleted() {
      return this._state.isDeleted();
   }

   public boolean isDirty() {
      return this._state.isDirty();
   }

   public boolean isDetached() {
      return (this._flags & 131072) != 0;
   }

   public Object getGenericContext() {
      return this._broker;
   }

   public Object fetchObjectId() {
      try {
         this.assignObjectId(true);
         if (this._oid != null && this._broker.getConfiguration().getCompatibilityInstance().getCopyObjectIds()) {
            return this._meta.getIdentityType() == 1 ? this._broker.getStoreManager().copyDataStoreId(this._oid, this._meta) : ApplicationIds.copy(this._oid, this._meta);
         } else {
            return this._oid;
         }
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Object getPCPrimaryKey(Object oid, int field) {
      FieldMetaData fmd = this._meta.getField(field);
      Object pk = ApplicationIds.get(oid, fmd);
      if (pk == null) {
         return null;
      } else {
         ClassMetaData relmeta = fmd.getDeclaredTypeMetaData();
         if (relmeta.getIdentityType() == 1 && fmd.getObjectIdFieldTypeCode() == 6) {
            pk = this._broker.getStoreManager().newDataStoreId(pk, relmeta);
         } else if (relmeta.getIdentityType() == 2 && fmd.getObjectIdFieldType() != relmeta.getObjectIdType()) {
            pk = ApplicationIds.fromPKValues(new Object[]{pk}, relmeta);
         }

         return this._broker.find(pk, false, (FindCallbacks)null);
      }
   }

   public byte replaceFlags() {
      return 1;
   }

   public StateManager replaceStateManager(StateManager sm) {
      return sm;
   }

   public void accessingField(int field) {
      try {
         this.beforeRead(field);
         this.beforeAccessField(field);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   protected void beforeAccessField(int field) {
      this.lock();

      try {
         boolean active = this._broker.isActive();
         int lockLevel = this.calculateLockLevel(active, false, (FetchConfiguration)null);
         if (!this._loaded.get(field)) {
            this.loadField(field, lockLevel, false, true);
         } else {
            this.assignField(field, false);
         }

         this.obtainLocks(active, false, lockLevel, (FetchConfiguration)null, (Object)null);
      } catch (RuntimeException var8) {
         throw this.translate(var8);
      } finally {
         this.unlock();
      }

   }

   public void dirty(String field) {
      FieldMetaData fmd = this._meta.getField(field);
      if (fmd == null) {
         throw this.translate((new UserException(_loc.get("no-field", field, ImplHelper.getManagedInstance(this._pc).getClass()))).setFailedObject(this.getManagedInstance()));
      } else {
         this.dirty(fmd.getIndex(), (Boolean)null, true);
      }
   }

   public void dirty(int field) {
      this.dirty(field, (Boolean)null, true);
   }

   private Boolean dirty(int field, Boolean mutate, boolean loadFetchGroup) {
      boolean locked = false;
      boolean newFlush = false;
      boolean clean = false;

      try {
         FieldMetaData fmd = this._meta.getField(field);
         if (!this.isNew() || this.isFlushed()) {
            if (fmd.getUpdateStrategy() == 2) {
               throw new InvalidStateException(_loc.get("update-restrict", (Object)fmd));
            }

            if (fmd.getUpdateStrategy() == 1) {
               Boolean var16 = Boolean.FALSE;
               return var16;
            }
         }

         if (this.isEmbedded()) {
            this._owner.dirty(this._ownerIndex, Boolean.TRUE, loadFetchGroup);
         }

         if (mutate == null) {
            switch (fmd.getDeclaredTypeCode()) {
               case 8:
               case 11:
               case 12:
               case 13:
               case 14:
               case 28:
                  mutate = Boolean.TRUE;
                  break;
               case 9:
               case 10:
               case 16:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
               case 22:
               case 23:
               case 24:
               case 25:
               case 26:
               case 27:
               default:
                  mutate = Boolean.FALSE;
                  break;
               case 15:
                  mutate = fmd.isEmbedded() ? Boolean.TRUE : Boolean.FALSE;
            }
         }

         boolean active = this._broker.isActive();
         clean = !this._state.isDirty();
         if (clean) {
            this.fireLifecycleEvent(9);
         }

         if (active) {
            if (this._broker.getOptimistic()) {
               this.setPCState(this._state.beforeOptimisticWrite(this, field, mutate));
            } else {
               this.setPCState(this._state.beforeWrite(this, field, mutate));
            }
         } else if (fmd.getManagement() == 3) {
            if (this.isPersistent() && !this._broker.getNontransactionalWrite()) {
               throw (new InvalidStateException(_loc.get("non-trans-write"))).setFailedObject(this.getManagedInstance());
            }

            this.setPCState(this._state.beforeNontransactionalWrite(this, field, mutate));
         }

         if ((this._flags & 512) != 0) {
            newFlush = (this._flags & 2048) == 0;
            this._flags |= 2048;
         }

         this.lock();
         locked = true;
         this._flush.clear(field);
         this._broker.setDirty(this, newFlush && !clean);
         this.saveField(field);
         int lockLevel = this.calculateLockLevel(active, true, (FetchConfiguration)null);
         if (!this._dirty.get(field)) {
            this.setLoaded(field, true);
            this._dirty.set(field);
            if (loadFetchGroup && this.isPersistent() && fmd.getManagement() == 3) {
               this.loadField(field, lockLevel, true, true);
            }
         }

         this.obtainLocks(active, true, lockLevel, (FetchConfiguration)null, (Object)null);
      } catch (RuntimeException var14) {
         throw this.translate(var14);
      } finally {
         if (locked) {
            this.unlock();
         }

      }

      if (clean) {
         return Boolean.TRUE;
      } else if (newFlush) {
         this.fireLifecycleEvent(11);
         return null;
      } else {
         return Boolean.FALSE;
      }
   }

   private void postDirty(Boolean status) {
      if (Boolean.TRUE.equals(status)) {
         this.fireLifecycleEvent(10);
      } else if (status == null) {
         this.fireLifecycleEvent(12);
      }

   }

   public void removed(int field, Object removed, boolean key) {
      if (removed != null) {
         try {
            FieldMetaData fmd = this._meta.getField(field);
            ValueMetaData vmd = key ? fmd.getKey() : fmd.getElement();
            if (vmd.isEmbeddedPC()) {
               this._single.delete(vmd, (Object)removed, (OpCallbacks)null);
            } else if (vmd.getCascadeDelete() == 2) {
               this._single.dereferenceDependent(removed);
            }

         } catch (RuntimeException var6) {
            throw this.translate(var6);
         }
      }
   }

   public Object newProxy(int field) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         return this.newFieldProxy(field);
      } else {
         switch (fmd.getTypeCode()) {
            case 12:
               return new ArrayList();
            case 13:
               return new HashMap();
            case 14:
               if (fmd.getDeclaredType() == Date.class) {
                  return new Date(System.currentTimeMillis());
               } else if (fmd.getDeclaredType() == Timestamp.class) {
                  return new Timestamp(System.currentTimeMillis());
               } else {
                  if (fmd.getDeclaredType() == Time.class) {
                     return new Time(System.currentTimeMillis());
                  }

                  return new java.util.Date();
               }
            case 28:
               return Calendar.getInstance();
            default:
               return null;
         }
      }
   }

   public Object newFieldProxy(int field) {
      FieldMetaData fmd = this._meta.getField(field);
      ProxyManager mgr = this._broker.getConfiguration().getProxyManagerInstance();
      Object init = fmd.getInitializer();
      switch (fmd.getDeclaredTypeCode()) {
         case 12:
            return mgr.newCollectionProxy(fmd.getProxyType(), fmd.getElement().getDeclaredType(), init instanceof Comparator ? (Comparator)init : null, this._broker.getConfiguration().getCompatibilityInstance().getAutoOff());
         case 13:
            return mgr.newMapProxy(fmd.getProxyType(), fmd.getKey().getDeclaredType(), fmd.getElement().getDeclaredType(), init instanceof Comparator ? (Comparator)init : null, this._broker.getConfiguration().getCompatibilityInstance().getAutoOff());
         case 14:
            return mgr.newDateProxy(fmd.getDeclaredType());
         case 28:
            return mgr.newCalendarProxy(fmd.getDeclaredType(), init instanceof TimeZone ? (TimeZone)init : null);
         default:
            return null;
      }
   }

   public boolean isDefaultValue(int field) {
      this.lock();

      boolean var3;
      try {
         this._single.clear();
         this.provideField(this._pc, this._single, field);
         boolean ret = this._single.isDefaultValue();
         this._single.clear();
         var3 = ret;
      } finally {
         this.unlock();
      }

      return var3;
   }

   public void settingBooleanField(PersistenceCapable pc, int field, boolean curVal, boolean newVal, int set) {
      if (set != 1) {
         if (newVal == curVal && this._loaded.get(field)) {
            return;
         }

         this.assertNoPrimaryKeyChange(field);
      }

      this.lock();

      try {
         Boolean stat = this.dirty(field, Boolean.FALSE, set == 0);
         this._single.storeBooleanField(field, newVal);
         this.replaceField(pc, this._single, field);
         this.postDirty(stat);
      } finally {
         this.unlock();
      }

   }

   public void settingByteField(PersistenceCapable pc, int field, byte curVal, byte newVal, int set) {
      if (set != 1) {
         if (newVal == curVal && this._loaded.get(field)) {
            return;
         }

         this.assertNoPrimaryKeyChange(field);
      }

      this.lock();

      try {
         Boolean stat = this.dirty(field, Boolean.FALSE, set == 0);
         this._single.storeByteField(field, newVal);
         this.replaceField(pc, this._single, field);
         this.postDirty(stat);
      } finally {
         this.unlock();
      }

   }

   public void settingCharField(PersistenceCapable pc, int field, char curVal, char newVal, int set) {
      if (set != 1) {
         if (newVal == curVal && this._loaded.get(field)) {
            return;
         }

         this.assertNoPrimaryKeyChange(field);
      }

      this.lock();

      try {
         Boolean stat = this.dirty(field, Boolean.FALSE, set == 0);
         this._single.storeCharField(field, newVal);
         this.replaceField(pc, this._single, field);
         this.postDirty(stat);
      } finally {
         this.unlock();
      }

   }

   public void settingDoubleField(PersistenceCapable pc, int field, double curVal, double newVal, int set) {
      if (set != 1) {
         if (newVal == curVal && this._loaded.get(field)) {
            return;
         }

         this.assertNoPrimaryKeyChange(field);
      }

      this.lock();

      try {
         Boolean stat = this.dirty(field, Boolean.FALSE, set == 0);
         this._single.storeDoubleField(field, newVal);
         this.replaceField(pc, this._single, field);
         this.postDirty(stat);
      } finally {
         this.unlock();
      }

   }

   public void settingFloatField(PersistenceCapable pc, int field, float curVal, float newVal, int set) {
      if (set != 1) {
         if (newVal == curVal && this._loaded.get(field)) {
            return;
         }

         this.assertNoPrimaryKeyChange(field);
      }

      this.lock();

      try {
         Boolean stat = this.dirty(field, Boolean.FALSE, set == 0);
         this._single.storeFloatField(field, newVal);
         this.replaceField(pc, this._single, field);
         this.postDirty(stat);
      } finally {
         this.unlock();
      }

   }

   public void settingIntField(PersistenceCapable pc, int field, int curVal, int newVal, int set) {
      if (set != 1) {
         if (newVal == curVal && this._loaded.get(field)) {
            return;
         }

         this.assertNoPrimaryKeyChange(field);
      }

      this.lock();

      try {
         Boolean stat = this.dirty(field, Boolean.FALSE, set == 0);
         this._single.storeIntField(field, newVal);
         this.replaceField(pc, this._single, field);
         this.postDirty(stat);
      } finally {
         this.unlock();
      }

   }

   public void settingLongField(PersistenceCapable pc, int field, long curVal, long newVal, int set) {
      if (set != 1) {
         if (newVal == curVal && this._loaded.get(field)) {
            return;
         }

         this.assertNoPrimaryKeyChange(field);
      }

      this.lock();

      try {
         Boolean stat = this.dirty(field, Boolean.FALSE, set == 0);
         this._single.storeLongField(field, newVal);
         this.replaceField(pc, this._single, field);
         this.postDirty(stat);
      } finally {
         this.unlock();
      }

   }

   public void settingObjectField(PersistenceCapable pc, int field, Object curVal, Object newVal, int set) {
      if (set != 1) {
         FieldMetaData fmd = this._meta.getField(field);
         if (this._loaded.get(field)) {
            if (newVal == curVal) {
               return;
            }

            switch (fmd.getDeclaredTypeCode()) {
               case 11:
               case 12:
               case 13:
               case 15:
               case 27:
                  break;
               default:
                  if (newVal != null && newVal.equals(curVal)) {
                     return;
                  }
            }
         } else if (fmd.getCascadeDelete() == 2 || fmd.getKey().getCascadeDelete() == 2 || fmd.getElement().getCascadeDelete() == 2) {
            curVal = this.fetchObjectField(field);
         }

         this.assertNoPrimaryKeyChange(field);
         if (fmd.getDeclaredTypeCode() == 29) {
            this.assertNotManagedObjectId(newVal);
         }
      }

      this.lock();

      try {
         Boolean stat = this.dirty(field, Boolean.FALSE, set == 0);
         if (set != 1) {
            this._single.storeObjectField(field, curVal);
            this._single.unproxy();
            this._single.dereferenceDependent();
            this._single.clear();
         }

         this._single.storeObjectField(field, newVal);
         this.replaceField(pc, this._single, field);
         this.postDirty(stat);
      } finally {
         this.unlock();
      }

   }

   public void settingShortField(PersistenceCapable pc, int field, short curVal, short newVal, int set) {
      if (set != 1) {
         if (newVal == curVal && this._loaded.get(field)) {
            return;
         }

         this.assertNoPrimaryKeyChange(field);
      }

      this.lock();

      try {
         Boolean stat = this.dirty(field, Boolean.FALSE, set == 0);
         this._single.storeShortField(field, newVal);
         this.replaceField(pc, this._single, field);
         this.postDirty(stat);
      } finally {
         this.unlock();
      }

   }

   public void settingStringField(PersistenceCapable pc, int field, String curVal, String newVal, int set) {
      if (set != 1) {
         if (StringUtils.equals(newVal, curVal) && this._loaded.get(field)) {
            return;
         }

         this.assertNoPrimaryKeyChange(field);
      }

      this.lock();

      try {
         Boolean stat = this.dirty(field, Boolean.FALSE, set == 0);
         this._single.storeStringField(field, newVal);
         this.replaceField(pc, this._single, field);
         this.postDirty(stat);
      } finally {
         this.unlock();
      }

   }

   private void assertNoPrimaryKeyChange(int field) {
      if (this._oid != null && this._meta.getField(field).isPrimaryKey()) {
         throw this.translate((new InvalidStateException(_loc.get("change-identity"))).setFailedObject(this.getManagedInstance()));
      }
   }

   void assertNotManagedObjectId(Object val) {
      if (val != null && ImplHelper.toPersistenceCapable(val, this.getContext().getConfiguration()).pcGetGenericContext() != null) {
         throw this.translate((new InvalidStateException(_loc.get("managed-oid", Exceptions.toString(val), Exceptions.toString(this.getManagedInstance())))).setFailedObject(this.getManagedInstance()));
      }
   }

   public void providedBooleanField(PersistenceCapable pc, int field, boolean curVal) {
      this._fm.storeBooleanField(field, curVal);
   }

   public void providedByteField(PersistenceCapable pc, int field, byte curVal) {
      this._fm.storeByteField(field, curVal);
   }

   public void providedCharField(PersistenceCapable pc, int field, char curVal) {
      this._fm.storeCharField(field, curVal);
   }

   public void providedDoubleField(PersistenceCapable pc, int field, double curVal) {
      this._fm.storeDoubleField(field, curVal);
   }

   public void providedFloatField(PersistenceCapable pc, int field, float curVal) {
      this._fm.storeFloatField(field, curVal);
   }

   public void providedIntField(PersistenceCapable pc, int field, int curVal) {
      this._fm.storeIntField(field, curVal);
   }

   public void providedLongField(PersistenceCapable pc, int field, long curVal) {
      this._fm.storeLongField(field, curVal);
   }

   public void providedObjectField(PersistenceCapable pc, int field, Object curVal) {
      this._fm.storeObjectField(field, curVal);
   }

   public void providedShortField(PersistenceCapable pc, int field, short curVal) {
      this._fm.storeShortField(field, curVal);
   }

   public void providedStringField(PersistenceCapable pc, int field, String curVal) {
      this._fm.storeStringField(field, curVal);
   }

   public boolean replaceBooleanField(PersistenceCapable pc, int field) {
      return this._fm.fetchBooleanField(field);
   }

   public byte replaceByteField(PersistenceCapable pc, int field) {
      return this._fm.fetchByteField(field);
   }

   public char replaceCharField(PersistenceCapable pc, int field) {
      return this._fm.fetchCharField(field);
   }

   public double replaceDoubleField(PersistenceCapable pc, int field) {
      return this._fm.fetchDoubleField(field);
   }

   public float replaceFloatField(PersistenceCapable pc, int field) {
      return this._fm.fetchFloatField(field);
   }

   public int replaceIntField(PersistenceCapable pc, int field) {
      return this._fm.fetchIntField(field);
   }

   public long replaceLongField(PersistenceCapable pc, int field) {
      return this._fm.fetchLongField(field);
   }

   public Object replaceObjectField(PersistenceCapable pc, int field) {
      return this._fm.fetchObjectField(field);
   }

   public short replaceShortField(PersistenceCapable pc, int field) {
      return this._fm.fetchShortField(field);
   }

   public String replaceStringField(PersistenceCapable pc, int field) {
      return this._fm.fetchStringField(field);
   }

   public boolean fetchBoolean(int field) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         return this.fetchBooleanField(field);
      } else {
         Object val = this.fetchField(field, false);
         return (Boolean)fmd.getExternalValue(val, this._broker);
      }
   }

   public boolean fetchBooleanField(int field) {
      this.lock();

      boolean var2;
      try {
         if (!this._loaded.get(field)) {
            this.loadField(field, 0, false, false);
         }

         this.provideField(this._pc, this._single, field);
         var2 = this._single.fetchBooleanField(field);
      } finally {
         this.unlock();
      }

      return var2;
   }

   public byte fetchByte(int field) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         return this.fetchByteField(field);
      } else {
         Object val = this.fetchField(field, false);
         return ((Number)fmd.getExternalValue(val, this._broker)).byteValue();
      }
   }

   public byte fetchByteField(int field) {
      this.lock();

      byte var2;
      try {
         if (!this._loaded.get(field)) {
            this.loadField(field, 0, false, false);
         }

         this.provideField(this._pc, this._single, field);
         var2 = this._single.fetchByteField(field);
      } finally {
         this.unlock();
      }

      return var2;
   }

   public char fetchChar(int field) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         return this.fetchCharField(field);
      } else {
         Object val = this.fetchField(field, false);
         return (Character)fmd.getExternalValue(val, this._broker);
      }
   }

   public char fetchCharField(int field) {
      this.lock();

      char var2;
      try {
         if (!this._loaded.get(field)) {
            this.loadField(field, 0, false, false);
         }

         this.provideField(this._pc, this._single, field);
         var2 = this._single.fetchCharField(field);
      } finally {
         this.unlock();
      }

      return var2;
   }

   public double fetchDouble(int field) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         return this.fetchDoubleField(field);
      } else {
         Object val = this.fetchField(field, false);
         return ((Number)fmd.getExternalValue(val, this._broker)).doubleValue();
      }
   }

   public double fetchDoubleField(int field) {
      this.lock();

      double var2;
      try {
         if (!this._loaded.get(field)) {
            this.loadField(field, 0, false, false);
         }

         this.provideField(this._pc, this._single, field);
         var2 = this._single.fetchDoubleField(field);
      } finally {
         this.unlock();
      }

      return var2;
   }

   public float fetchFloat(int field) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         return this.fetchFloatField(field);
      } else {
         Object val = this.fetchField(field, false);
         return ((Number)fmd.getExternalValue(val, this._broker)).floatValue();
      }
   }

   public float fetchFloatField(int field) {
      this.lock();

      float var2;
      try {
         if (!this._loaded.get(field)) {
            this.loadField(field, 0, false, false);
         }

         this.provideField(this._pc, this._single, field);
         var2 = this._single.fetchFloatField(field);
      } finally {
         this.unlock();
      }

      return var2;
   }

   public int fetchInt(int field) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         return this.fetchIntField(field);
      } else {
         Object val = this.fetchField(field, false);
         return ((Number)fmd.getExternalValue(val, this._broker)).intValue();
      }
   }

   public int fetchIntField(int field) {
      this.lock();

      int var2;
      try {
         if (!this._loaded.get(field)) {
            this.loadField(field, 0, false, false);
         }

         this.provideField(this._pc, this._single, field);
         var2 = this._single.fetchIntField(field);
      } finally {
         this.unlock();
      }

      return var2;
   }

   public long fetchLong(int field) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         return this.fetchLongField(field);
      } else {
         Object val = this.fetchField(field, false);
         return ((Number)fmd.getExternalValue(val, this._broker)).longValue();
      }
   }

   public long fetchLongField(int field) {
      this.lock();

      long var2;
      try {
         if (!this._loaded.get(field)) {
            this.loadField(field, 0, false, false);
         }

         this.provideField(this._pc, this._single, field);
         var2 = this._single.fetchLongField(field);
      } finally {
         this.unlock();
      }

      return var2;
   }

   public Object fetchObject(int field) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         return this.fetchObjectField(field);
      } else {
         Object val = this.fetchField(field, false);
         return fmd.getExternalValue(val, this._broker);
      }
   }

   public Object fetchObjectField(int field) {
      this.lock();

      Object var2;
      try {
         if (!this._loaded.get(field)) {
            this.loadField(field, 0, false, false);
         }

         this.provideField(this._pc, this._single, field);
         var2 = this._single.fetchObjectField(field);
      } finally {
         this.unlock();
      }

      return var2;
   }

   public short fetchShort(int field) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         return this.fetchShortField(field);
      } else {
         Object val = this.fetchField(field, false);
         return ((Number)fmd.getExternalValue(val, this._broker)).shortValue();
      }
   }

   public short fetchShortField(int field) {
      this.lock();

      short var2;
      try {
         if (!this._loaded.get(field)) {
            this.loadField(field, 0, false, false);
         }

         this.provideField(this._pc, this._single, field);
         var2 = this._single.fetchShortField(field);
      } finally {
         this.unlock();
      }

      return var2;
   }

   public String fetchString(int field) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         return this.fetchStringField(field);
      } else {
         Object val = this.fetchField(field, false);
         return (String)fmd.getExternalValue(val, this._broker);
      }
   }

   public String fetchStringField(int field) {
      this.lock();

      String var2;
      try {
         if (!this._loaded.get(field)) {
            this.loadField(field, 0, false, false);
         }

         this.provideField(this._pc, this._single, field);
         var2 = this._single.fetchStringField(field);
      } finally {
         this.unlock();
      }

      return var2;
   }

   public void storeBoolean(int field, boolean externalVal) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         this.storeBooleanField(field, externalVal);
      } else {
         Object val = externalVal ? Boolean.TRUE : Boolean.FALSE;
         this.storeField(field, fmd.getFieldValue(val, this._broker));
      }

   }

   public void storeBooleanField(int field, boolean curVal) {
      this.lock();

      try {
         this._single.storeBooleanField(field, curVal);
         this.replaceField(this._pc, this._single, field);
         this.setLoaded(field, true);
         this.postLoad(field, (FetchConfiguration)null);
      } finally {
         this.unlock();
      }

   }

   public void storeByte(int field, byte externalVal) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         this.storeByteField(field, externalVal);
      } else {
         this.storeField(field, fmd.getFieldValue(new Byte(externalVal), this._broker));
      }

   }

   public void storeByteField(int field, byte curVal) {
      this.lock();

      try {
         this._single.storeByteField(field, curVal);
         this.replaceField(this._pc, this._single, field);
         this.setLoaded(field, true);
         this.postLoad(field, (FetchConfiguration)null);
      } finally {
         this.unlock();
      }

   }

   public void storeChar(int field, char externalVal) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         this.storeCharField(field, externalVal);
      } else {
         this.storeField(field, fmd.getFieldValue(new Character(externalVal), this._broker));
      }

   }

   public void storeCharField(int field, char curVal) {
      this.lock();

      try {
         this._single.storeCharField(field, curVal);
         this.replaceField(this._pc, this._single, field);
         this.setLoaded(field, true);
         this.postLoad(field, (FetchConfiguration)null);
      } finally {
         this.unlock();
      }

   }

   public void storeDouble(int field, double externalVal) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         this.storeDoubleField(field, externalVal);
      } else {
         this.storeField(field, fmd.getFieldValue(new Double(externalVal), this._broker));
      }

   }

   public void storeDoubleField(int field, double curVal) {
      this.lock();

      try {
         this._single.storeDoubleField(field, curVal);
         this.replaceField(this._pc, this._single, field);
         this.setLoaded(field, true);
         this.postLoad(field, (FetchConfiguration)null);
      } finally {
         this.unlock();
      }

   }

   public void storeFloat(int field, float externalVal) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         this.storeFloatField(field, externalVal);
      } else {
         this.storeField(field, fmd.getFieldValue(new Float(externalVal), this._broker));
      }

   }

   public void storeFloatField(int field, float curVal) {
      this.lock();

      try {
         this._single.storeFloatField(field, curVal);
         this.replaceField(this._pc, this._single, field);
         this.setLoaded(field, true);
         this.postLoad(field, (FetchConfiguration)null);
      } finally {
         this.unlock();
      }

   }

   public void storeInt(int field, int externalVal) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         this.storeIntField(field, externalVal);
      } else {
         this.storeField(field, fmd.getFieldValue(Numbers.valueOf(externalVal), this._broker));
      }

   }

   public void storeIntField(int field, int curVal) {
      this.lock();

      try {
         this._single.storeIntField(field, curVal);
         this.replaceField(this._pc, this._single, field);
         this.setLoaded(field, true);
         this.postLoad(field, (FetchConfiguration)null);
      } finally {
         this.unlock();
      }

   }

   public void storeLong(int field, long externalVal) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         this.storeLongField(field, externalVal);
      } else {
         this.storeField(field, fmd.getFieldValue(Numbers.valueOf(externalVal), this._broker));
      }

   }

   public void storeLongField(int field, long curVal) {
      this.lock();

      try {
         this._single.storeLongField(field, curVal);
         this.replaceField(this._pc, this._single, field);
         this.setLoaded(field, true);
         this.postLoad(field, (FetchConfiguration)null);
      } finally {
         this.unlock();
      }

   }

   public void storeObject(int field, Object externalVal) {
      FieldMetaData fmd = this._meta.getField(field);
      externalVal = fmd.order(externalVal);
      if (!fmd.isExternalized()) {
         this.storeObjectField(field, externalVal);
      } else {
         this.storeField(field, fmd.getFieldValue(externalVal, this._broker));
      }

   }

   public void storeObjectField(int field, Object curVal) {
      this.lock();

      try {
         this._single.storeObjectField(field, curVal);
         this._single.proxy(true, false);
         this.replaceField(this._pc, this._single, field);
         this.setLoaded(field, true);
         this.postLoad(field, (FetchConfiguration)null);
      } finally {
         this.unlock();
      }

   }

   public void storeShort(int field, short externalVal) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         this.storeShortField(field, externalVal);
      } else {
         this.storeField(field, fmd.getFieldValue(new Short(externalVal), this._broker));
      }

   }

   public void storeShortField(int field, short curVal) {
      this.lock();

      try {
         this._single.storeShortField(field, curVal);
         this.replaceField(this._pc, this._single, field);
         this.setLoaded(field, true);
         this.postLoad(field, (FetchConfiguration)null);
      } finally {
         this.unlock();
      }

   }

   public void storeString(int field, String externalVal) {
      FieldMetaData fmd = this._meta.getField(field);
      if (!fmd.isExternalized()) {
         this.storeStringField(field, externalVal);
      } else {
         this.storeField(field, fmd.getFieldValue(externalVal, this._broker));
      }

   }

   public void storeStringField(int field, String curVal) {
      this.lock();

      try {
         this._single.storeStringField(field, curVal);
         this.replaceField(this._pc, this._single, field);
         this.setLoaded(field, true);
         this.postLoad(field, (FetchConfiguration)null);
      } finally {
         this.unlock();
      }

   }

   private void storeField(int field, Object val, FieldManager fm) {
      FieldMetaData fmd = this._meta.getField(field);
      if (fmd == null) {
         throw (new UserException(_loc.get("no-field-index", String.valueOf(field), this._meta.getDescribedType()))).setFailedObject(this.getManagedInstance());
      } else {
         switch (fmd.getDeclaredTypeCode()) {
            case 0:
               boolean bool = val != null && (Boolean)val;
               fm.storeBooleanField(field, bool);
               break;
            case 1:
               byte b = val == null ? 0 : ((Number)val).byteValue();
               fm.storeByteField(field, b);
               break;
            case 2:
               char c = val == null ? 0 : (Character)val;
               fm.storeCharField(field, c);
               break;
            case 3:
               double d = val == null ? 0.0 : ((Number)val).doubleValue();
               fm.storeDoubleField(field, d);
               break;
            case 4:
               float f = val == null ? 0.0F : ((Number)val).floatValue();
               fm.storeFloatField(field, f);
               break;
            case 5:
               int i = val == null ? 0 : ((Number)val).intValue();
               fm.storeIntField(field, i);
               break;
            case 6:
               long l = val == null ? 0L : ((Number)val).longValue();
               fm.storeLongField(field, l);
               break;
            case 7:
               short s = val == null ? 0 : ((Number)val).shortValue();
               fm.storeShortField(field, s);
               break;
            case 8:
            default:
               fm.storeObjectField(field, val);
               break;
            case 9:
               fm.storeStringField(field, (String)val);
         }

      }
   }

   void eraseFlush() {
      this._flags &= -513;
      this._flags &= -2049;
      int fmds = this._meta.getFields().length;

      for(int i = 0; i < fmds; ++i) {
         this._flush.clear(i);
      }

   }

   void setLoaded(boolean val) {
      FieldMetaData[] fmds = this._meta.getFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (!fmds[i].isPrimaryKey()) {
            int var10000 = fmds[i].getManagement();
            FieldMetaData var10001 = fmds[i];
            if (var10000 == 3) {
               this.setLoaded(i, val);
            }
         }
      }

      if (!val) {
         this._flags &= -9;
         this.setDirty(false);
      } else {
         this._flags |= 8;
      }

   }

   void setDirty(boolean val) {
      FieldMetaData[] fmds = this._meta.getFields();
      boolean update = !this.isNew() || this.isFlushed();

      for(int i = 0; i < fmds.length; ++i) {
         if (val && (!update || fmds[i].getUpdateStrategy() != 1)) {
            this._dirty.set(i);
         } else if (!val) {
            this._flush.clear(i);
            this._dirty.clear(i);
         }
      }

      if (val) {
         this._flags |= 8;
      }

   }

   void clearFields() {
      if (this.isIntercepting()) {
         this.fireLifecycleEvent(5);
         this.unproxyFields();
         this.lock();

         try {
            FieldMetaData[] fmds = this._meta.getFields();

            for(int i = 0; i < fmds.length; ++i) {
               if (!fmds[i].isPrimaryKey() && fmds[i].getManagement() == 3) {
                  this.replaceField(this._pc, ClearFieldManager.getInstance(), i);
               }
            }

            this.setLoaded(false);
            this._version = null;
            this._loadVersion = null;
            if (this._fieldImpl != null) {
               Arrays.fill(this._fieldImpl, (Object)null);
            }
         } finally {
            this.unlock();
         }

         this.fireLifecycleEvent(6);
      }
   }

   void saveFields(boolean immediate) {
      if (this._broker.getRestoreState() != 0 || (this._flags & 8192) != 0) {
         this._flags |= 2;
         if (immediate) {
            int i = 0;

            for(int len = this._loaded.length(); i < len; ++i) {
               this.saveField(i);
            }

            this._flags &= -3;
         }

      }
   }

   private void saveField(int field) {
      if ((this._flags & 2) != 0) {
         if (!this._loaded.get(field) && (this._flags & 8192) != 0 && this._meta.getField(field).getInverseMetaDatas().length > 0) {
            this.loadField(field, 0, false, false);
         }

         if (this._saved == null) {
            if (!this._loaded.get(field)) {
               return;
            }

            this._saved = new SaveFieldManager(this, (PersistenceCapable)null, this._dirty);
         }

         if (this._saved.saveField(field)) {
            this.provideField(this._pc, this._saved, field);
            this.replaceField(this._saved.getState(), this._saved, field);
         }

      }
   }

   void clearSavedFields() {
      if (this.isIntercepting()) {
         this._flags &= -3;
         this._saved = null;
      }

   }

   public SaveFieldManager getSaveFieldManager() {
      return this._saved;
   }

   void restoreFields() {
      this.lock();

      try {
         if (this._saved == null) {
            if ((this._flags & 2) == 0) {
               this.clearFields();
            } else {
               this._loaded.andNot(this._loaded);
            }
         } else if (this._broker.getRestoreState() != 0) {
            int i = 0;

            for(int len = this._loaded.length(); i < len; ++i) {
               if (this._loaded.get(i) && this._saved.restoreField(i)) {
                  this.replaceField(this._pc, this._saved, i);
               }
            }

            this._loaded.andNot(this._saved.getUnloaded());
         }
      } finally {
         this.unlock();
      }

   }

   void proxyFields(boolean reset, boolean replaceNull) {
      if (replaceNull) {
         replaceNull = !this._broker.getConfiguration().supportedOptions().contains("openjpa.option.NullContainer");
      }

      this.lock();

      try {
         int i = 0;

         for(int len = this._loaded.length(); i < len; ++i) {
            if (this._loaded.get(i)) {
               this.provideField(this._pc, this._single, i);
               if (this._single.proxy(reset, replaceNull)) {
                  this.replaceField(this._pc, this._single, i);
               } else {
                  this._single.clear();
               }
            }
         }
      } finally {
         this.unlock();
      }

   }

   void unproxyFields() {
      if ((this._flags & 16384) == 0) {
         this.lock();

         try {
            int i = 0;

            for(int len = this._loaded.length(); i < len; ++i) {
               this.provideField(this._pc, this._single, i);
               this._single.unproxy();
               this._single.releaseEmbedded();
               this._single.clear();
            }
         } finally {
            this.unlock();
         }

      }
   }

   void preFlush(boolean logical, OpCallbacks call) {
      if ((this._flags & 1024) == 0) {
         if (this.isPersistent()) {
            this.fireLifecycleEvent(3);
            if (this.isDeleted()) {
               this.fireLifecycleEvent(7);
            } else if (!this.isNew() || this.isFlushed()) {
               this.fireLifecycleEvent(20);
            }

            this._flags |= 1024;
         }

         this.lock();

         try {
            if (!logical) {
               this.assignObjectId(false, true);
            }

            int i = 0;

            for(int len = this._meta.getFields().length; i < len; ++i) {
               if ((logical || !this.assignField(i, true)) && !this._flush.get(i) && this._dirty.get(i)) {
                  this.provideField(this._pc, this._single, i);
                  if (this._single.preFlush(logical, call)) {
                     this.replaceField(this._pc, this._single, i);
                  } else {
                     this._single.clear();
                  }
               }
            }

            this.dirtyCheck();
         } finally {
            this.unlock();
         }

      }
   }

   void preDelete() {
      if ((this._flags & 256) == 0) {
         this._flags |= 256;

         try {
            this.fireLifecycleEvent(7);
         } finally {
            this._flags &= -257;
         }
      }

   }

   void cascadeDelete(OpCallbacks call) {
      FieldMetaData[] fmds = this._meta.getFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (fmds[i].getCascadeDelete() != 0 || fmds[i].getKey().getCascadeDelete() != 0 || fmds[i].getElement().getCascadeDelete() != 0) {
            this._single.storeObjectField(i, this.fetchField(i, false));
            this._single.delete(call);
            this._single.clear();
         }
      }

   }

   void cascadePersist(OpCallbacks call) {
      FieldMetaData[] fmds = this._meta.getFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (this._loaded.get(i) && (fmds[i].getCascadePersist() == 1 || fmds[i].getKey().getCascadePersist() == 1 || fmds[i].getElement().getCascadePersist() == 1)) {
            this._single.storeObjectField(i, this.fetchField(i, false));
            this._single.persist(call);
            this._single.clear();
         }
      }

   }

   boolean loadFields(BitSet fields, FetchConfiguration fetch, int lockLevel, Object sdata) {
      if (fields != null) {
         FieldMetaData vfield = this._meta.getVersionField();
         if (vfield != null) {
            fields.clear(vfield.getIndex());
         }
      }

      boolean ret = false;
      this.setLoading(true);

      try {
         int len = fields == null ? 0 : fields.length();
         if (len > 0) {
            if (fetch == null) {
               fetch = this._broker.getFetchConfiguration();
            }

            if (!this._broker.getStoreManager().load(this, fields, fetch, lockLevel, sdata)) {
               throw (new ObjectNotFoundException(_loc.get("del-instance", this._meta.getDescribedType(), this._oid))).setFailedObject(this.getManagedInstance());
            }

            ret = true;
         }

         if (this._loadVersion == null) {
            this.syncVersion(sdata);
            ret = ret || this._loadVersion != null;
         }
      } finally {
         this.setLoading(false);
      }

      this.postLoad(-1, fetch);
      return ret;
   }

   protected void loadField(int field, int lockLevel, boolean forWrite, boolean fgs) {
      FetchConfiguration fetch = this._broker.getFetchConfiguration();
      FieldMetaData fmd = this._meta.getField(field);
      BitSet fields = null;
      if (fgs && (this._flags & 8) == 0) {
         fields = this.getUnloadedInternal(fetch, 0, (BitSet)null);
      }

      String lfg = fmd.getLoadFetchGroup();
      boolean lfgAdded = false;
      if (lfg != null) {
         FieldMetaData[] fmds = this._meta.getFields();

         for(int i = 0; i < fmds.length; ++i) {
            if (!this._loaded.get(i) && (i == field || fmds[i].isInFetchGroup(lfg))) {
               if (fields == null) {
                  fields = new BitSet(fmds.length);
               }

               fields.set(i);
            }
         }

         if (!fetch.hasFetchGroup(lfg)) {
            fetch.addFetchGroup(lfg);
            lfgAdded = true;
         }
      } else if (fmd.isInDefaultFetchGroup() && fields == null) {
         fields = this.getUnloadedInternal(fetch, 0, (BitSet)null);
      } else if (!this._loaded.get(fmd.getIndex())) {
         if (fields == null) {
            fields = new BitSet();
         }

         fields.set(fmd.getIndex());
      }

      try {
         this.loadFields(fields, fetch, lockLevel, (Object)null);
      } finally {
         if (lfgAdded) {
            fetch.removeFetchGroup(lfg);
         }

      }

   }

   void provideField(PersistenceCapable pc, FieldManager store, int field) {
      FieldManager beforeFM = this._fm;
      this._fm = store;
      pc.pcProvideField(field);
      this._fm = beforeFM;
   }

   void replaceField(PersistenceCapable pc, FieldManager load, int field) {
      FieldManager beforeFM = this._fm;
      this._fm = load;
      pc.pcReplaceField(field);
      this._fm = beforeFM;
   }

   private void setLoaded(int field, boolean isLoaded) {
      if (this._loaded.get(field) != isLoaded) {
         if (this._fieldImpl != null) {
            int idx = this._meta.getExtraFieldDataIndex(field);
            if (idx != -1) {
               this._fieldImpl[idx] = null;
            }
         }

         if (isLoaded) {
            this._loaded.set(field);
         } else {
            this._loaded.clear(field);
         }

      }
   }

   private void postLoad(int field, FetchConfiguration fetch) {
      if ((this._flags & 8) == 0) {
         if (field == -1 || !this.isLoading()) {
            LifecycleEventManager mgr = this._broker.getLifecycleEventManager();
            if (mgr != null && mgr.hasLoadListeners(this.getManagedInstance(), this._meta)) {
               if (fetch == null) {
                  fetch = this._broker.getFetchConfiguration();
               }

               if (field != -1) {
                  FieldMetaData fmd = this._meta.getField(field);
                  if (fmd.isInDefaultFetchGroup() && fetch.hasFetchGroup("default") && this.postLoad("default", fetch)) {
                     return;
                  }

                  String[] fgs = fmd.getCustomFetchGroups();

                  for(int i = 0; i < fgs.length; ++i) {
                     if (fetch.hasFetchGroup(fgs[i]) && this.postLoad(fgs[i], fetch)) {
                        return;
                     }
                  }
               } else {
                  Iterator itr = fetch.getFetchGroups().iterator();

                  while(itr.hasNext()) {
                     if (this.postLoad((String)itr.next(), fetch)) {
                        return;
                     }
                  }
               }

            }
         }
      }
   }

   private boolean postLoad(String fgName, FetchConfiguration fetch) {
      FetchGroup fg = this._meta.getFetchGroup(fgName);
      if (fg != null && fg.isPostLoad()) {
         FieldMetaData[] fmds = this._meta.getFields();

         for(int i = 0; i < fmds.length; ++i) {
            if (!this._loaded.get(i) && fmds[i].isInFetchGroup(fgName)) {
               return false;
            }
         }

         this._flags |= 8;
         this._broker.fireLifecycleEvent(this.getManagedInstance(), fetch, this._meta, 2);
         return true;
      } else {
         return false;
      }
   }

   private boolean syncVersion(Object sdata) {
      return this._broker.getStoreManager().syncVersion(this, sdata);
   }

   public boolean isVersionCheckRequired() {
      if ((this._flags & '耀') != 0) {
         return true;
      } else {
         return !this._broker.getOptimistic() && !this._broker.getConfiguration().getCompatibilityInstance().getNonOptimisticVersionCheck() ? false : this._state.isVersionCheckRequired(this);
      }
   }

   void setCheckVersion(boolean versionCheck) {
      if (versionCheck) {
         this._flags |= 32768;
      } else {
         this._flags &= -32769;
      }

   }

   public boolean isVersionUpdateRequired() {
      return (this._flags & 65536) > 0;
   }

   void setUpdateVersion(boolean versionUpdate) {
      if (versionUpdate) {
         this._flags |= 65536;
      } else {
         this._flags &= -65537;
      }

   }

   protected RuntimeException translate(RuntimeException re) {
      RuntimeExceptionTranslator trans = this._broker.getInstanceExceptionTranslator();
      return trans == null ? re : trans.translate(re);
   }

   protected void lock() {
      this._broker.lock();
   }

   protected void unlock() {
      this._broker.unlock();
   }

   private void writeObject(ObjectOutputStream oos) throws IOException {
      oos.writeObject(this._broker);
      oos.defaultWriteObject();
      oos.writeObject(this._meta.getDescribedType());
      this.writePC(oos, this._pc);
   }

   void writePC(ObjectOutputStream oos, PersistenceCapable pc) throws IOException {
      if (!Serializable.class.isAssignableFrom(this._meta.getDescribedType())) {
         throw new NotSerializableException(this._meta.getDescribedType().getName());
      } else {
         oos.writeObject(pc);
      }
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      this._broker = (BrokerImpl)in.readObject();
      in.defaultReadObject();
      Class managedType = (Class)in.readObject();
      this._meta = this._broker.getConfiguration().getMetaDataRepositoryInstance().getMetaData((Class)managedType, (ClassLoader)null, true);
      this._pc = this.readPC(in);
   }

   PersistenceCapable readPC(ObjectInputStream in) throws ClassNotFoundException, IOException {
      Object o = in.readObject();
      if (o == null) {
         return null;
      } else {
         PersistenceCapable pc;
         if (!(o instanceof PersistenceCapable)) {
            pc = ImplHelper.toPersistenceCapable(o, this);
         } else {
            pc = (PersistenceCapable)o;
         }

         pc.pcReplaceStateManager(this);
         return pc;
      }
   }
}
