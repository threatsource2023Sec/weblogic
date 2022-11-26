package org.apache.openjpa.kernel;

import java.io.IOException;
import java.io.ObjectOutput;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.BitSet;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.Reflection;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.ImplHelper;
import serp.util.Numbers;

public class ObjectIdStateManager implements OpenJPAStateManager {
   private static final Byte ZERO_BYTE = new Byte((byte)0);
   private static final Character ZERO_CHAR = new Character('\u0000');
   private static final Double ZERO_DOUBLE = new Double(0.0);
   private static final Float ZERO_FLOAT = new Float(0.0F);
   private static final Short ZERO_SHORT = new Short((short)0);
   private Object _oid;
   private final OpenJPAStateManager _owner;
   private final ValueMetaData _vmd;

   public ObjectIdStateManager(Object oid, OpenJPAStateManager owner, ValueMetaData ownerVal) {
      this._oid = oid;
      this._owner = owner;
      this._vmd = ownerVal;
   }

   public Object getGenericContext() {
      return this._owner == null ? null : this._owner.getGenericContext();
   }

   public Object getPCPrimaryKey(Object oid, int field) {
      throw new UnsupportedOperationException();
   }

   public StateManager replaceStateManager(StateManager sm) {
      throw new UnsupportedOperationException();
   }

   public Object getVersion() {
      return null;
   }

   public void setVersion(Object version) {
      throw new UnsupportedOperationException();
   }

   public boolean isDirty() {
      return false;
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
      throw new UnsupportedOperationException();
   }

   public Object fetchObjectId() {
      return null;
   }

   public void accessingField(int idx) {
      throw new UnsupportedOperationException();
   }

   public boolean serializing() {
      throw new UnsupportedOperationException();
   }

   public boolean writeDetached(ObjectOutput out) throws IOException {
      throw new UnsupportedOperationException();
   }

   public void proxyDetachedDeserialized(int idx) {
      throw new UnsupportedOperationException();
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

   public void providedBooleanField(PersistenceCapable pc, int idx, boolean cur) {
      throw new UnsupportedOperationException();
   }

   public void providedCharField(PersistenceCapable pc, int idx, char cur) {
      throw new UnsupportedOperationException();
   }

   public void providedByteField(PersistenceCapable pc, int idx, byte cur) {
      throw new UnsupportedOperationException();
   }

   public void providedShortField(PersistenceCapable pc, int idx, short cur) {
      throw new UnsupportedOperationException();
   }

   public void providedIntField(PersistenceCapable pc, int idx, int cur) {
      throw new UnsupportedOperationException();
   }

   public void providedLongField(PersistenceCapable pc, int idx, long cur) {
      throw new UnsupportedOperationException();
   }

   public void providedFloatField(PersistenceCapable pc, int idx, float cur) {
      throw new UnsupportedOperationException();
   }

   public void providedDoubleField(PersistenceCapable pc, int idx, double cur) {
      throw new UnsupportedOperationException();
   }

   public void providedStringField(PersistenceCapable pc, int idx, String cur) {
      throw new UnsupportedOperationException();
   }

   public void providedObjectField(PersistenceCapable pc, int idx, Object cur) {
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

   public void initialize(Class forType, PCState state) {
      throw new UnsupportedOperationException();
   }

   public void load(FetchConfiguration fetch) {
      throw new UnsupportedOperationException();
   }

   public Object getManagedInstance() {
      return this._oid;
   }

   public PersistenceCapable getPersistenceCapable() {
      return ImplHelper.toPersistenceCapable(this._oid, this._vmd.getRepository().getConfiguration());
   }

   public ClassMetaData getMetaData() {
      return this._vmd.getEmbeddedMetaData();
   }

   public OpenJPAStateManager getOwner() {
      return this._owner;
   }

   public int getOwnerIndex() {
      return this._vmd.getFieldMetaData().getIndex();
   }

   public boolean isEmbedded() {
      return true;
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
      Object val = this.getValue(field);
      if (val == null) {
         return true;
      } else {
         FieldMetaData fmd = this.getMetaData().getField(field);
         switch (fmd.getDeclaredTypeCode()) {
            case 0:
               return Boolean.FALSE.equals(val);
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
               return ((Number)val).intValue() == 0;
            case 2:
               return (Character)val == 0;
            case 8:
            default:
               return false;
            case 9:
               return "".equals(val);
         }
      }
   }

   public StoreContext getContext() {
      return this._owner == null ? null : this._owner.getContext();
   }

   public PCState getPCState() {
      throw new UnsupportedOperationException();
   }

   public Object getObjectId() {
      return null;
   }

   public void setObjectId(Object oid) {
      throw new UnsupportedOperationException();
   }

   public boolean assignObjectId(boolean flush) {
      throw new UnsupportedOperationException();
   }

   public Object getId() {
      return null;
   }

   public Object getLock() {
      return null;
   }

   public void setLock(Object lock) {
      throw new UnsupportedOperationException();
   }

   public void setNextVersion(Object version) {
      throw new UnsupportedOperationException();
   }

   public Object getImplData() {
      return null;
   }

   public Object setImplData(Object data, boolean cacheable) {
      throw new UnsupportedOperationException();
   }

   public boolean isImplDataCacheable() {
      return false;
   }

   public Object getImplData(int field) {
      return null;
   }

   public Object setImplData(int field, Object data) {
      throw new UnsupportedOperationException();
   }

   public boolean isImplDataCacheable(int field) {
      return false;
   }

   public Object getIntermediate(int field) {
      return null;
   }

   public void setIntermediate(int field, Object data) {
      throw new UnsupportedOperationException();
   }

   public void removed(int field, Object removed, boolean key) {
      throw new UnsupportedOperationException();
   }

   public boolean beforeRefresh(boolean all) {
      throw new UnsupportedOperationException();
   }

   public void dirty(int field) {
      throw new UnsupportedOperationException();
   }

   public void storeBoolean(int field, boolean extVal) {
      this.setValue(field, extVal ? Boolean.TRUE : Boolean.FALSE, true);
   }

   public void storeByte(int field, byte extVal) {
      this.setValue(field, new Byte(extVal), true);
   }

   public void storeChar(int field, char extVal) {
      this.setValue(field, new Character(extVal), true);
   }

   public void storeInt(int field, int extVal) {
      this.setValue(field, Numbers.valueOf(extVal), true);
   }

   public void storeShort(int field, short extVal) {
      this.setValue(field, new Short(extVal), true);
   }

   public void storeLong(int field, long extVal) {
      this.setValue(field, Numbers.valueOf(extVal), true);
   }

   public void storeFloat(int field, float extVal) {
      this.setValue(field, new Float(extVal), true);
   }

   public void storeDouble(int field, double extVal) {
      this.setValue(field, new Double(extVal), true);
   }

   public void storeString(int field, String extVal) {
      this.setValue(field, extVal, extVal != null);
   }

   public void storeObject(int field, Object extVal) {
      this.setValue(field, extVal, extVal != null);
   }

   public void store(int field, Object extVal) {
      boolean forceInst = true;
      if (extVal == null) {
         extVal = this.getDefaultValue(field);
         forceInst = false;
      }

      this.setValue(field, extVal, forceInst);
   }

   public void storeBooleanField(int field, boolean extVal) {
      this.storeBoolean(field, extVal);
   }

   public void storeByteField(int field, byte extVal) {
      this.storeByte(field, extVal);
   }

   public void storeCharField(int field, char extVal) {
      this.storeChar(field, extVal);
   }

   public void storeIntField(int field, int extVal) {
      this.storeInt(field, extVal);
   }

   public void storeShortField(int field, short extVal) {
      this.storeShort(field, extVal);
   }

   public void storeLongField(int field, long extVal) {
      this.storeLong(field, extVal);
   }

   public void storeFloatField(int field, float extVal) {
      this.storeFloat(field, extVal);
   }

   public void storeDoubleField(int field, double extVal) {
      this.storeDouble(field, extVal);
   }

   public void storeStringField(int field, String extVal) {
      this.storeString(field, extVal);
   }

   public void storeObjectField(int field, Object extVal) {
      this.storeObject(field, extVal);
   }

   public void storeField(int field, Object value) {
      this.store(field, value);
   }

   public boolean fetchBoolean(int field) {
      return (Boolean)this.getValue(field);
   }

   public byte fetchByte(int field) {
      return ((Number)this.getValue(field)).byteValue();
   }

   public char fetchChar(int field) {
      return (Character)this.getValue(field);
   }

   public short fetchShort(int field) {
      return ((Number)this.getValue(field)).shortValue();
   }

   public int fetchInt(int field) {
      return ((Number)this.getValue(field)).intValue();
   }

   public long fetchLong(int field) {
      return ((Number)this.getValue(field)).longValue();
   }

   public float fetchFloat(int field) {
      return ((Number)this.getValue(field)).floatValue();
   }

   public double fetchDouble(int field) {
      return ((Number)this.getValue(field)).doubleValue();
   }

   public String fetchString(int field) {
      return (String)this.getValue(field);
   }

   public Object fetchObject(int field) {
      return this.getValue(field);
   }

   public Object fetch(int field) {
      Object ret = this.getValue(field);
      if (ret == null) {
         ret = this.getDefaultValue(field);
      }

      return ret;
   }

   public boolean fetchBooleanField(int field) {
      return this.fetchBoolean(field);
   }

   public byte fetchByteField(int field) {
      return this.fetchByte(field);
   }

   public char fetchCharField(int field) {
      return this.fetchChar(field);
   }

   public short fetchShortField(int field) {
      return this.fetchShort(field);
   }

   public int fetchIntField(int field) {
      return this.fetchInt(field);
   }

   public long fetchLongField(int field) {
      return this.fetchLong(field);
   }

   public float fetchFloatField(int field) {
      return this.fetchFloat(field);
   }

   public double fetchDoubleField(int field) {
      return this.fetchDouble(field);
   }

   public String fetchStringField(int field) {
      return this.fetchString(field);
   }

   public Object fetchObjectField(int field) {
      return this.fetch(field);
   }

   public Object fetchField(int field, boolean transitions) {
      return this.fetch(field);
   }

   public Object fetchInitialField(int field) {
      throw new UnsupportedOperationException();
   }

   public void setRemote(int field, Object value) {
      this.store(field, value);
   }

   public void lock() {
   }

   public void unlock() {
   }

   private Object getDefaultValue(int field) {
      FieldMetaData fmd = this.getMetaData().getField(field);
      switch (fmd.getDeclaredTypeCode()) {
         case 0:
            return Boolean.FALSE;
         case 1:
            return ZERO_BYTE;
         case 2:
            return ZERO_CHAR;
         case 3:
            return ZERO_DOUBLE;
         case 4:
            return ZERO_FLOAT;
         case 5:
            return Numbers.valueOf(0);
         case 6:
            return Numbers.valueOf(0L);
         case 7:
            return ZERO_SHORT;
         default:
            return null;
      }
   }

   private Object getValue(int field) {
      if (this._oid == null) {
         return null;
      } else {
         FieldMetaData fmd = this.getMetaData().getField(field);
         if (fmd.getBackingMember() instanceof Field) {
            return Reflection.get(this._oid, (Field)fmd.getBackingMember());
         } else if (fmd.getBackingMember() instanceof Method) {
            return Reflection.get(this._oid, (Method)fmd.getBackingMember());
         } else {
            return fmd.getDefiningMetaData().getAccessType() == 2 ? Reflection.get(this._oid, Reflection.findField(this._oid.getClass(), fmd.getName(), true)) : Reflection.get(this._oid, Reflection.findGetter(this._oid.getClass(), fmd.getName(), true));
         }
      }
   }

   private void setValue(int field, Object val, boolean forceInst) {
      if (this._oid == null && forceInst) {
         try {
            this._oid = AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(this.getMetaData().getDescribedType()));
         } catch (Exception var5) {
            Exception e = var5;
            if (var5 instanceof PrivilegedActionException) {
               e = ((PrivilegedActionException)var5).getException();
            }

            throw new GeneralException(e);
         }
      } else if (this._oid == null) {
         return;
      }

      FieldMetaData fmd = this.getMetaData().getField(field);
      if (fmd.getBackingMember() instanceof Field) {
         Reflection.set(this._oid, (Field)fmd.getBackingMember(), val);
      } else if (fmd.getDefiningMetaData().getAccessType() == 2) {
         Reflection.set(this._oid, Reflection.findField(this._oid.getClass(), fmd.getName(), true), val);
      } else {
         Reflection.set(this._oid, Reflection.findSetter(this._oid.getClass(), fmd.getName(), fmd.getDeclaredType(), true), val);
      }

   }
}
