package org.apache.openjpa.kernel;

import java.io.ObjectOutput;
import java.util.BitSet;
import org.apache.openjpa.enhance.FieldManager;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.UnsupportedException;
import serp.util.Numbers;

public class DetachedValueStateManager extends TransferFieldManager implements OpenJPAStateManager {
   private static final Localizer _loc = Localizer.forPackage(DetachedValueStateManager.class);
   private PersistenceCapable _pc;
   private StoreContext _ctx;
   private ClassMetaData _meta;

   public DetachedValueStateManager(Object pc, StoreContext ctx) {
      this(ImplHelper.toPersistenceCapable(pc, ctx.getConfiguration()), ctx.getConfiguration().getMetaDataRepositoryInstance().getMetaData(ImplHelper.getManagedInstance(pc).getClass(), ctx.getClassLoader(), true), ctx);
   }

   public DetachedValueStateManager(PersistenceCapable pc, ClassMetaData meta, StoreContext ctx) {
      this._pc = ImplHelper.toPersistenceCapable(pc, ctx.getConfiguration());
      this._meta = meta;
      this._ctx = ctx;
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
      return this._meta;
   }

   public OpenJPAStateManager getOwner() {
      return null;
   }

   public int getOwnerIndex() {
      throw new UnsupportedOperationException();
   }

   public boolean isEmbedded() {
      return false;
   }

   public boolean isFlushed() {
      return false;
   }

   public boolean isFlushedDirty() {
      return false;
   }

   public boolean isProvisional() {
      return false;
   }

   public BitSet getLoaded() {
      throw new UnsupportedOperationException();
   }

   public BitSet getDirty() {
      throw new UnsupportedOperationException();
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
      return this._ctx;
   }

   public PCState getPCState() {
      throw new UnsupportedOperationException();
   }

   public Object getId() {
      return this.getObjectId();
   }

   public Object getObjectId() {
      throw new UnsupportedOperationException();
   }

   public void setObjectId(Object oid) {
      throw new UnsupportedOperationException();
   }

   public boolean assignObjectId(boolean flush) {
      throw new UnsupportedOperationException();
   }

   public Object getLock() {
      throw new UnsupportedOperationException();
   }

   public void setLock(Object lock) {
      throw new UnsupportedOperationException();
   }

   public Object getVersion() {
      throw new UnsupportedOperationException();
   }

   public void setVersion(Object version) {
      throw new UnsupportedOperationException();
   }

   public void setNextVersion(Object version) {
      throw new UnsupportedOperationException();
   }

   public boolean isVersionUpdateRequired() {
      throw new UnsupportedOperationException();
   }

   public boolean isVersionCheckRequired() {
      throw new UnsupportedOperationException();
   }

   public Object getImplData() {
      throw new UnsupportedOperationException();
   }

   public Object setImplData(Object data, boolean cacheable) {
      throw new UnsupportedOperationException();
   }

   public boolean isImplDataCacheable() {
      throw new UnsupportedOperationException();
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

   public void setIntermediate(int field, Object value) {
      throw new UnsupportedOperationException();
   }

   void provideField(int field) {
      if (this._pc.pcGetStateManager() != null) {
         throw new InternalException(_loc.get("detach-val-mismatch", (Object)this._pc));
      } else {
         this._pc.pcReplaceStateManager(this);
         this._pc.pcProvideField(field);
         this._pc.pcReplaceStateManager((StateManager)null);
      }
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

   public double fetchDouble(int field) {
      throw new UnsupportedOperationException();
   }

   public float fetchFloat(int field) {
      throw new UnsupportedOperationException();
   }

   public int fetchInt(int field) {
      throw new UnsupportedOperationException();
   }

   public long fetchLong(int field) {
      throw new UnsupportedOperationException();
   }

   public Object fetchObject(int field) {
      throw new UnsupportedOperationException();
   }

   public short fetchShort(int field) {
      throw new UnsupportedOperationException();
   }

   public String fetchString(int field) {
      throw new UnsupportedOperationException();
   }

   public Object fetchFromDetachedSM(DetachedStateManager sm, int field) {
      sm.lock();
      sm.provideField(field);
      Object val = this.fetchField(sm, field);
      sm.clear();
      sm.unlock();
      return val;
   }

   public Object fetch(int field) {
      StateManager sm = this._pc.pcGetStateManager();
      if (sm != null) {
         if (sm instanceof DetachedStateManager) {
            return this.fetchFromDetachedSM((DetachedStateManager)sm, field);
         } else {
            throw new UnsupportedException(_loc.get("detach-val-badsm", (Object)this._pc));
         }
      } else {
         this.provideField(field);
         Object val = this.fetchField(field, false);
         this.clear();
         return this._meta.getField(field).getExternalValue(val, this._ctx.getBroker());
      }
   }

   public Object fetchField(int field, boolean transitions) {
      if (transitions) {
         throw new IllegalArgumentException();
      } else {
         return this.fetchField(this, field);
      }
   }

   private Object fetchField(FieldManager fm, int field) {
      FieldMetaData fmd = this._meta.getField(field);
      if (fmd == null) {
         throw new InternalException();
      } else {
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
               return fm.fetchObjectField(field);
            case 9:
               return fm.fetchStringField(field);
            default:
               return fm.fetchObjectField(field);
         }
      }
   }

   public Object fetchInitialField(int field) {
      throw new UnsupportedOperationException();
   }

   public void storeBoolean(int field, boolean externalVal) {
      throw new UnsupportedOperationException();
   }

   public void storeByte(int field, byte externalVal) {
      throw new UnsupportedOperationException();
   }

   public void storeChar(int field, char externalVal) {
      throw new UnsupportedOperationException();
   }

   public void storeDouble(int field, double externalVal) {
      throw new UnsupportedOperationException();
   }

   public void storeFloat(int field, float externalVal) {
      throw new UnsupportedOperationException();
   }

   public void storeInt(int field, int externalVal) {
      throw new UnsupportedOperationException();
   }

   public void storeLong(int field, long externalVal) {
      throw new UnsupportedOperationException();
   }

   public void storeObject(int field, Object externalVal) {
      throw new UnsupportedOperationException();
   }

   public void storeShort(int field, short externalVal) {
      throw new UnsupportedOperationException();
   }

   public void storeString(int field, String externalVal) {
      throw new UnsupportedOperationException();
   }

   public void store(int field, Object value) {
      throw new UnsupportedOperationException();
   }

   public void storeField(int field, Object value) {
      throw new UnsupportedOperationException();
   }

   public void dirty(int field) {
      throw new UnsupportedOperationException();
   }

   public void removed(int field, Object removed, boolean key) {
      throw new UnsupportedOperationException();
   }

   public boolean beforeRefresh(boolean refreshAll) {
      throw new UnsupportedOperationException();
   }

   public void setRemote(int field, Object value) {
      throw new UnsupportedOperationException();
   }

   public Object getGenericContext() {
      return this._ctx;
   }

   public Object getPCPrimaryKey(Object oid, int field) {
      throw new UnsupportedOperationException();
   }

   public StateManager replaceStateManager(StateManager sm) {
      return sm;
   }

   public boolean isDirty() {
      return true;
   }

   public boolean isTransactional() {
      return false;
   }

   public boolean isPersistent() {
      return true;
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

   public void dirty(String field) {
      throw new UnsupportedOperationException();
   }

   public Object fetchObjectId() {
      return this.getObjectId();
   }

   public boolean serializing() {
      throw new UnsupportedOperationException();
   }

   public boolean writeDetached(ObjectOutput out) {
      throw new UnsupportedOperationException();
   }

   public void proxyDetachedDeserialized(int idx) {
      throw new UnsupportedOperationException();
   }

   public void accessingField(int idx) {
      throw new UnsupportedOperationException();
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

   public void settingBooleanField(PersistenceCapable pc, int idx, boolean cur, boolean next, int set) {
      throw new UnsupportedOperationException();
   }

   public void settingCharField(PersistenceCapable pc, int idx, char cur, char next, int set) {
      throw new UnsupportedOperationException();
   }

   public void settingByteField(PersistenceCapable pc, int idx, byte cur, byte next, int set) {
      throw new UnsupportedOperationException();
   }

   public void settingShortField(PersistenceCapable pc, int idx, short cur, short next, int set) {
      throw new UnsupportedOperationException();
   }

   public void settingIntField(PersistenceCapable pc, int idx, int cur, int next, int set) {
      throw new UnsupportedOperationException();
   }

   public void settingLongField(PersistenceCapable pc, int idx, long cur, long next, int set) {
      throw new UnsupportedOperationException();
   }

   public void settingFloatField(PersistenceCapable pc, int idx, float cur, float next, int set) {
      throw new UnsupportedOperationException();
   }

   public void settingDoubleField(PersistenceCapable pc, int idx, double cur, double next, int set) {
      throw new UnsupportedOperationException();
   }

   public void settingStringField(PersistenceCapable pc, int idx, String cur, String next, int set) {
      throw new UnsupportedOperationException();
   }

   public void settingObjectField(PersistenceCapable pc, int idx, Object cur, Object next, int set) {
      throw new UnsupportedOperationException();
   }

   public boolean replaceBooleanField(PersistenceCapable pc, int idx) {
      throw new UnsupportedOperationException();
   }

   public char replaceCharField(PersistenceCapable pc, int idx) {
      throw new UnsupportedOperationException();
   }

   public byte replaceByteField(PersistenceCapable pc, int idx) {
      throw new UnsupportedOperationException();
   }

   public short replaceShortField(PersistenceCapable pc, int idx) {
      throw new UnsupportedOperationException();
   }

   public int replaceIntField(PersistenceCapable pc, int idx) {
      throw new UnsupportedOperationException();
   }

   public long replaceLongField(PersistenceCapable pc, int idx) {
      throw new UnsupportedOperationException();
   }

   public float replaceFloatField(PersistenceCapable pc, int idx) {
      throw new UnsupportedOperationException();
   }

   public double replaceDoubleField(PersistenceCapable pc, int idx) {
      throw new UnsupportedOperationException();
   }

   public String replaceStringField(PersistenceCapable pc, int idx) {
      throw new UnsupportedOperationException();
   }

   public Object replaceObjectField(PersistenceCapable pc, int idx) {
      throw new UnsupportedOperationException();
   }
}
