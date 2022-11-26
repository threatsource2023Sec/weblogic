package org.apache.openjpa.kernel;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.Exceptions;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.Proxy;
import org.apache.openjpa.util.UnsupportedException;

public class DetachedStateManager extends AttachStrategy implements OpenJPAStateManager, Serializable {
   private static final long serialVersionUID = 4112223665584731100L;
   private static final Localizer _loc = Localizer.forPackage(DetachedStateManager.class);
   private final PersistenceCapable _pc;
   private final boolean _embedded;
   private final boolean _access;
   private final BitSet _loaded;
   private final BitSet _dirty;
   private final Object _oid;
   private final Object _version;
   private final ReentrantLock _lock;

   public DetachedStateManager(PersistenceCapable pc, OpenJPAStateManager sm, BitSet load, boolean access, boolean multithreaded) {
      this._pc = pc;
      this._embedded = sm.isEmbedded();
      this._loaded = load;
      this._access = access;
      if (!sm.isFlushed()) {
         this._dirty = (BitSet)sm.getDirty().clone();
      } else {
         this._dirty = new BitSet(this._loaded.length());
      }

      this._oid = sm.fetchObjectId();
      this._version = sm.getVersion();
      if (multithreaded) {
         this._lock = new ReentrantLock();
      } else {
         this._lock = null;
      }

   }

   public Object attach(AttachManager manager, Object toAttach, ClassMetaData meta, PersistenceCapable into, OpenJPAStateManager owner, ValueMetaData ownerMeta, boolean explicit) {
      BrokerImpl broker = manager.getBroker();
      StateManagerImpl sm;
      if (this._embedded) {
         if (this._dirty.length() > 0) {
            owner.dirty(ownerMeta.getFieldMetaData().getIndex());
         }

         sm = (StateManagerImpl)broker.embed(this._pc, this._oid, owner, ownerMeta);
         ImplHelper.toPersistenceCapable(toAttach, broker.getConfiguration()).pcReplaceStateManager(this);
      } else {
         PCState state = this._dirty.length() > 0 ? PCState.PDIRTY : PCState.PCLEAN;
         sm = (StateManagerImpl)broker.copy(this, state);
      }

      PersistenceCapable pc = sm.getPersistenceCapable();
      manager.setAttachedCopy(toAttach, pc);
      manager.fireBeforeAttach(toAttach, meta);
      FieldMetaData[] fields = meta.getFields();
      int restore = broker.getRestoreState();
      if (this._dirty.length() > 0) {
         BitSet load = new BitSet(fields.length);

         for(int i = 0; i < fields.length; ++i) {
            if (this._dirty.get(i)) {
               switch (fields[i].getDeclaredTypeCode()) {
                  case 11:
                  case 12:
                     if (restore == 2 || fields[i].getElement().getCascadeDelete() == 2) {
                        load.set(i);
                     }
                     break;
                  case 13:
                     if (restore == 2 || fields[i].getElement().getCascadeDelete() == 2 || fields[i].getKey().getCascadeDelete() == 2) {
                        load.set(i);
                     }
                     break;
                  default:
                     if (restore != 0 || fields[i].getCascadeDelete() == 2) {
                        load.set(i);
                     }
               }
            }
         }

         FetchConfiguration fc = broker.getFetchConfiguration();
         sm.loadFields(load, fc, fc.getWriteLockLevel(), (Object)null);
      }

      Object origVersion = sm.getVersion();
      sm.setVersion(this._version);
      BitSet loaded = sm.getLoaded();
      int set = 2;

      for(int i = 0; i < fields.length; ++i) {
         if (this._loaded.get(i) && (this._dirty.get(i) || !loaded.get(i) || !ignoreLoaded(fields[i]))) {
            this.provideField(i);
            switch (fields[i].getDeclaredTypeCode()) {
               case 0:
                  if (!this._dirty.get(i)) {
                     sm.storeBooleanField(i, this.longval == 1L);
                     break;
                  }

                  sm.settingBooleanField(pc, i, loaded.get(i) && sm.fetchBooleanField(i), this.longval == 1L, set);
                  break;
               case 1:
                  if (this._dirty.get(i)) {
                     sm.settingByteField(pc, i, !loaded.get(i) ? 0 : sm.fetchByteField(i), (byte)((int)this.longval), set);
                  } else {
                     sm.storeByteField(i, (byte)((int)this.longval));
                  }
                  break;
               case 2:
                  if (this._dirty.get(i)) {
                     sm.settingCharField(pc, i, !loaded.get(i) ? '\u0000' : sm.fetchCharField(i), (char)((int)this.longval), set);
                  } else {
                     sm.storeCharField(i, (char)((int)this.longval));
                  }
                  break;
               case 3:
                  if (this._dirty.get(i)) {
                     sm.settingDoubleField(pc, i, !loaded.get(i) ? 0.0 : sm.fetchDoubleField(i), this.dblval, set);
                  } else {
                     sm.storeDoubleField(i, this.dblval);
                  }
                  break;
               case 4:
                  if (this._dirty.get(i)) {
                     sm.settingFloatField(pc, i, !loaded.get(i) ? 0.0F : sm.fetchFloatField(i), (float)this.dblval, set);
                  } else {
                     sm.storeFloatField(i, (float)this.dblval);
                  }
                  break;
               case 5:
                  if (this._dirty.get(i)) {
                     sm.settingIntField(pc, i, !loaded.get(i) ? 0 : sm.fetchIntField(i), (int)this.longval, set);
                  } else {
                     sm.storeIntField(i, (int)this.longval);
                  }
                  break;
               case 6:
                  if (this._dirty.get(i)) {
                     sm.settingLongField(pc, i, !loaded.get(i) ? 0L : sm.fetchLongField(i), this.longval, set);
                  } else {
                     sm.storeLongField(i, this.longval);
                  }
                  break;
               case 7:
                  if (this._dirty.get(i)) {
                     sm.settingShortField(pc, i, !loaded.get(i) ? 0 : sm.fetchShortField(i), (short)((int)this.longval), set);
                  } else {
                     sm.storeShortField(i, (short)((int)this.longval));
                  }
                  break;
               case 8:
               case 10:
               case 11:
               case 14:
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
               default:
                  if (this._dirty.get(i)) {
                     sm.settingObjectField(pc, i, !loaded.get(i) ? null : sm.fetchObjectField(i), this.objval, set);
                  } else {
                     sm.storeObjectField(i, this.objval);
                  }

                  this.objval = null;
                  break;
               case 9:
                  if (this._dirty.get(i)) {
                     sm.settingStringField(pc, i, !loaded.get(i) ? null : sm.fetchStringField(i), (String)this.objval, set);
                  } else {
                     sm.storeStringField(i, (String)this.objval);
                  }

                  this.objval = null;
                  break;
               case 12:
                  Collection coll = (Collection)this.objval;
                  this.objval = null;
                  if (coll != null) {
                     coll = this.attachCollection(manager, coll, sm, fields[i]);
                  }

                  if (this._dirty.get(i)) {
                     sm.settingObjectField(pc, i, !loaded.get(i) ? null : sm.fetchObjectField(i), coll, set);
                  } else {
                     sm.storeObjectField(i, coll);
                  }
                  break;
               case 13:
                  Map map = (Map)this.objval;
                  this.objval = null;
                  if (map != null) {
                     map = this.attachMap(manager, map, sm, fields[i]);
                  }

                  if (this._dirty.get(i)) {
                     sm.settingObjectField(pc, i, !loaded.get(i) ? null : sm.fetchObjectField(i), map, set);
                  } else {
                     sm.storeObjectField(i, map);
                  }
                  break;
               case 15:
               case 27:
                  if (fields[i].getCascadeAttach() == 0) {
                     this.objval = this.getReference(manager, this.objval, sm, fields[i]);
                  } else {
                     PersistenceCapable toPC = null;
                     if (this.objval != null && fields[i].isEmbeddedPC()) {
                        toPC = ImplHelper.toPersistenceCapable(this.objval, broker.getConfiguration());
                     }

                     this.objval = manager.attach(this.objval, toPC, sm, fields[i], false);
                  }

                  if (this._dirty.get(i)) {
                     sm.settingObjectField(pc, i, !loaded.get(i) ? null : sm.fetchObjectField(i), this.objval, set);
                  } else {
                     sm.storeObjectField(i, this.objval);
                  }

                  this.objval = null;
            }
         }
      }

      pc.pcReplaceStateManager(sm);
      if (!sm.isVersionCheckRequired() && broker.isActive() && this._version != origVersion && (origVersion == null || broker.getStoreManager().compareVersion(sm, this._version, origVersion) != 3)) {
         broker.transactional(sm.getManagedInstance(), false, manager.getBehavior());
      }

      return sm.getManagedInstance();
   }

   protected Object getDetachedObjectId(AttachManager manager, Object toAttach) {
      return this._oid;
   }

   void provideField(int field) {
      this._pc.pcProvideField(field);
   }

   protected void provideField(Object toAttach, StateManagerImpl sm, int field) {
      this.provideField(field);
   }

   protected static boolean ignoreLoaded(FieldMetaData fmd) {
      switch (fmd.getTypeCode()) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 9:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
            return true;
         case 8:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         default:
            return false;
      }
   }

   public Object getGenericContext() {
      return null;
   }

   public Object getPCPrimaryKey(Object oid, int field) {
      throw new UnsupportedOperationException();
   }

   public StateManager replaceStateManager(StateManager sm) {
      return sm;
   }

   public Object getVersion() {
      return this._version;
   }

   public void setVersion(Object version) {
      throw new UnsupportedException();
   }

   public boolean isDirty() {
      return this._dirty.length() != 0;
   }

   public boolean isTransactional() {
      return false;
   }

   public boolean isPersistent() {
      return false;
   }

   public boolean isNew() {
      return false;
   }

   public boolean isDeleted() {
      return false;
   }

   public boolean isDetached() {
      return true;
   }

   public boolean isVersionUpdateRequired() {
      return false;
   }

   public boolean isVersionCheckRequired() {
      return false;
   }

   public void dirty(String field) {
      throw new UnsupportedException();
   }

   public Object fetchObjectId() {
      return this._oid;
   }

   public void accessingField(int idx) {
      if (!this._access && !this._loaded.get(idx)) {
         throw new IllegalStateException(_loc.get("unloaded-detached", (Object)Exceptions.toString((Object)this._pc)).getMessage());
      }
   }

   public boolean serializing() {
      return false;
   }

   public boolean writeDetached(ObjectOutput out) throws IOException {
      out.writeObject(this._pc.pcGetDetachedState());
      out.writeObject(this);
      return false;
   }

   public void proxyDetachedDeserialized(int idx) {
      this.lock();

      try {
         this._pc.pcProvideField(idx);
         if (this.objval instanceof Proxy) {
            ((Proxy)this.objval).setOwner(this, idx);
         }

         this.objval = null;
      } finally {
         this.unlock();
      }

   }

   public void settingBooleanField(PersistenceCapable pc, int idx, boolean cur, boolean next, int set) {
      this.accessingField(idx);
      if (cur != next && this._loaded.get(idx)) {
         this.lock();

         try {
            this._dirty.set(idx);
            this.longval = next ? 1L : 0L;
            pc.pcReplaceField(idx);
         } finally {
            this.unlock();
         }

      }
   }

   public void settingCharField(PersistenceCapable pc, int idx, char cur, char next, int set) {
      this.accessingField(idx);
      if (cur != next && this._loaded.get(idx)) {
         this.lock();

         try {
            this._dirty.set(idx);
            this.longval = (long)next;
            pc.pcReplaceField(idx);
         } finally {
            this.unlock();
         }

      }
   }

   public void settingByteField(PersistenceCapable pc, int idx, byte cur, byte next, int set) {
      this.accessingField(idx);
      if (cur != next && this._loaded.get(idx)) {
         this.lock();

         try {
            this._dirty.set(idx);
            this.longval = (long)next;
            pc.pcReplaceField(idx);
         } finally {
            this.unlock();
         }

      }
   }

   public void settingShortField(PersistenceCapable pc, int idx, short cur, short next, int set) {
      this.accessingField(idx);
      if (cur != next && this._loaded.get(idx)) {
         this.lock();

         try {
            this._dirty.set(idx);
            this.longval = (long)next;
            pc.pcReplaceField(idx);
         } finally {
            this.unlock();
         }

      }
   }

   public void settingIntField(PersistenceCapable pc, int idx, int cur, int next, int set) {
      this.accessingField(idx);
      if (cur != next && this._loaded.get(idx)) {
         this.lock();

         try {
            this._dirty.set(idx);
            this.longval = (long)next;
            pc.pcReplaceField(idx);
         } finally {
            this.unlock();
         }

      }
   }

   public void settingLongField(PersistenceCapable pc, int idx, long cur, long next, int set) {
      this.accessingField(idx);
      if (cur != next && this._loaded.get(idx)) {
         this.lock();

         try {
            this._dirty.set(idx);
            this.longval = next;
            pc.pcReplaceField(idx);
         } finally {
            this.unlock();
         }

      }
   }

   public void settingFloatField(PersistenceCapable pc, int idx, float cur, float next, int set) {
      this.accessingField(idx);
      if (cur != next && this._loaded.get(idx)) {
         this.lock();

         try {
            this._dirty.set(idx);
            this.dblval = (double)next;
            pc.pcReplaceField(idx);
         } finally {
            this.unlock();
         }

      }
   }

   public void settingDoubleField(PersistenceCapable pc, int idx, double cur, double next, int set) {
      this.accessingField(idx);
      if (cur != next && this._loaded.get(idx)) {
         this.lock();

         try {
            this._dirty.set(idx);
            this.dblval = next;
            pc.pcReplaceField(idx);
         } finally {
            this.unlock();
         }

      }
   }

   public void settingStringField(PersistenceCapable pc, int idx, String cur, String next, int set) {
      this.accessingField(idx);
      if (cur != next && (cur == null || !cur.equals(next)) && this._loaded.get(idx)) {
         this.lock();

         try {
            this._dirty.set(idx);
            this.objval = next;
            pc.pcReplaceField(idx);
         } finally {
            this.unlock();
            this.objval = null;
         }

      }
   }

   public void settingObjectField(PersistenceCapable pc, int idx, Object cur, Object next, int set) {
      this.accessingField(idx);
      if (cur != next && this._loaded.get(idx)) {
         this.lock();

         try {
            this._dirty.set(idx);
            this.objval = next;
            pc.pcReplaceField(idx);
         } finally {
            this.unlock();
            this.objval = null;
         }

      }
   }

   public void providedBooleanField(PersistenceCapable pc, int idx, boolean cur) {
      this.longval = cur ? 1L : 0L;
   }

   public void providedCharField(PersistenceCapable pc, int idx, char cur) {
      this.longval = (long)cur;
   }

   public void providedByteField(PersistenceCapable pc, int idx, byte cur) {
      this.longval = (long)cur;
   }

   public void providedShortField(PersistenceCapable pc, int idx, short cur) {
      this.longval = (long)cur;
   }

   public void providedIntField(PersistenceCapable pc, int idx, int cur) {
      this.longval = (long)cur;
   }

   public void providedLongField(PersistenceCapable pc, int idx, long cur) {
      this.longval = cur;
   }

   public void providedFloatField(PersistenceCapable pc, int idx, float cur) {
      this.dblval = (double)cur;
   }

   public void providedDoubleField(PersistenceCapable pc, int idx, double cur) {
      this.dblval = cur;
   }

   public void providedStringField(PersistenceCapable pc, int idx, String cur) {
      this.objval = cur;
   }

   public void providedObjectField(PersistenceCapable pc, int idx, Object cur) {
      this.objval = cur;
   }

   public boolean replaceBooleanField(PersistenceCapable pc, int idx) {
      return this.longval == 1L;
   }

   public char replaceCharField(PersistenceCapable pc, int idx) {
      return (char)((int)this.longval);
   }

   public byte replaceByteField(PersistenceCapable pc, int idx) {
      return (byte)((int)this.longval);
   }

   public short replaceShortField(PersistenceCapable pc, int idx) {
      return (short)((int)this.longval);
   }

   public int replaceIntField(PersistenceCapable pc, int idx) {
      return (int)this.longval;
   }

   public long replaceLongField(PersistenceCapable pc, int idx) {
      return this.longval;
   }

   public float replaceFloatField(PersistenceCapable pc, int idx) {
      return (float)this.dblval;
   }

   public double replaceDoubleField(PersistenceCapable pc, int idx) {
      return this.dblval;
   }

   public String replaceStringField(PersistenceCapable pc, int idx) {
      String str = (String)this.objval;
      this.objval = null;
      return str;
   }

   public Object replaceObjectField(PersistenceCapable pc, int idx) {
      Object ret = this.objval;
      this.objval = null;
      return ret;
   }

   public void initialize(Class forType, PCState state) {
      throw new UnsupportedOperationException();
   }

   public void load(FetchConfiguration fetch) {
      throw new UnsupportedOperationException();
   }

   public Object getManagedInstance() {
      return this._pc;
   }

   public PersistenceCapable getPersistenceCapable() {
      return this._pc;
   }

   public ClassMetaData getMetaData() {
      throw new UnsupportedOperationException();
   }

   public OpenJPAStateManager getOwner() {
      throw new UnsupportedOperationException();
   }

   public int getOwnerIndex() {
      throw new UnsupportedOperationException();
   }

   public boolean isEmbedded() {
      return this._embedded;
   }

   public boolean isFlushed() {
      throw new UnsupportedOperationException();
   }

   public boolean isFlushedDirty() {
      throw new UnsupportedOperationException();
   }

   public boolean isProvisional() {
      throw new UnsupportedOperationException();
   }

   public BitSet getLoaded() {
      return this._loaded;
   }

   public BitSet getDirty() {
      return this._dirty;
   }

   public BitSet getFlushed() {
      throw new UnsupportedOperationException();
   }

   public BitSet getUnloaded(FetchConfiguration fetch) {
      throw new UnsupportedOperationException();
   }

   public Object newProxy(int field) {
      throw new UnsupportedOperationException();
   }

   public Object newFieldProxy(int field) {
      throw new UnsupportedOperationException();
   }

   public boolean isDefaultValue(int field) {
      throw new UnsupportedOperationException();
   }

   public StoreContext getContext() {
      return null;
   }

   public PCState getPCState() {
      throw new UnsupportedOperationException();
   }

   public Object getObjectId() {
      return this._oid;
   }

   public void setObjectId(Object oid) {
      throw new UnsupportedOperationException();
   }

   public boolean assignObjectId(boolean flush) {
      return true;
   }

   public Object getId() {
      return this.getObjectId();
   }

   public Object getLock() {
      throw new UnsupportedOperationException();
   }

   public void setLock(Object lock) {
      throw new UnsupportedOperationException();
   }

   public void setNextVersion(Object version) {
      throw new UnsupportedOperationException();
   }

   public Object getImplData() {
      throw new UnsupportedOperationException();
   }

   public Object setImplData(Object data, boolean cacheable) {
      throw new UnsupportedOperationException();
   }

   public boolean isImplDataCacheable() {
      return false;
   }

   public Object getImplData(int field) {
      throw new UnsupportedOperationException();
   }

   public Object setImplData(int field, Object data) {
      throw new UnsupportedOperationException();
   }

   public boolean isImplDataCacheable(int field) {
      throw new UnsupportedOperationException();
   }

   public Object getIntermediate(int field) {
      throw new UnsupportedOperationException();
   }

   public void setIntermediate(int field, Object data) {
      throw new UnsupportedOperationException();
   }

   public void removed(int field, Object removed, boolean key) {
      this.dirty(field);
   }

   public boolean beforeRefresh(boolean all) {
      throw new UnsupportedOperationException();
   }

   public void dirty(int field) {
      this.lock();

      try {
         this._dirty.set(field);
      } finally {
         this.unlock();
      }

   }

   public void storeBoolean(int field, boolean extVal) {
      throw new UnsupportedOperationException();
   }

   public void storeByte(int field, byte extVal) {
      throw new UnsupportedOperationException();
   }

   public void storeChar(int field, char extVal) {
      throw new UnsupportedOperationException();
   }

   public void storeInt(int field, int extVal) {
      throw new UnsupportedOperationException();
   }

   public void storeShort(int field, short extVal) {
      throw new UnsupportedOperationException();
   }

   public void storeLong(int field, long extVal) {
      throw new UnsupportedOperationException();
   }

   public void storeFloat(int field, float extVal) {
      throw new UnsupportedOperationException();
   }

   public void storeDouble(int field, double extVal) {
      throw new UnsupportedOperationException();
   }

   public void storeString(int field, String extVal) {
      throw new UnsupportedOperationException();
   }

   public void storeObject(int field, Object extVal) {
      throw new UnsupportedOperationException();
   }

   public void store(int field, Object extVal) {
      throw new UnsupportedOperationException();
   }

   public void storeField(int field, Object value) {
      throw new UnsupportedOperationException();
   }

   public boolean fetchBoolean(int field) {
      throw new UnsupportedOperationException();
   }

   public byte fetchByte(int field) {
      throw new UnsupportedOperationException();
   }

   public char fetchChar(int field) {
      throw new UnsupportedOperationException();
   }

   public short fetchShort(int field) {
      throw new UnsupportedOperationException();
   }

   public int fetchInt(int field) {
      throw new UnsupportedOperationException();
   }

   public long fetchLong(int field) {
      throw new UnsupportedOperationException();
   }

   public float fetchFloat(int field) {
      throw new UnsupportedOperationException();
   }

   public double fetchDouble(int field) {
      throw new UnsupportedOperationException();
   }

   public String fetchString(int field) {
      throw new UnsupportedOperationException();
   }

   public Object fetchObject(int field) {
      throw new UnsupportedOperationException();
   }

   public Object fetch(int field) {
      throw new UnsupportedOperationException();
   }

   public Object fetchField(int field, boolean transitions) {
      throw new UnsupportedOperationException();
   }

   public Object fetchInitialField(int field) {
      throw new UnsupportedOperationException();
   }

   public void setRemote(int field, Object value) {
      throw new UnsupportedOperationException();
   }

   public void lock() {
      if (this._lock != null) {
         this._lock.lock();
      }

   }

   public void unlock() {
      if (this._lock != null) {
         this._lock.unlock();
      }

   }
}
