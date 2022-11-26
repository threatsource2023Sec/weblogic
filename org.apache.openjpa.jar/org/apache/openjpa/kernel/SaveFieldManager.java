package org.apache.openjpa.kernel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.ProxyManager;

public class SaveFieldManager extends ClearFieldManager implements Serializable {
   private final StateManagerImpl _sm;
   private final BitSet _unloaded;
   private BitSet _saved = null;
   private int[] _copyField = null;
   private transient PersistenceCapable _state = null;
   private Object _field = null;

   SaveFieldManager(StateManagerImpl sm, PersistenceCapable pc, BitSet dirty) {
      this._sm = sm;
      this._state = pc;
      FieldMetaData[] fields = this._sm.getMetaData().getFields();
      if (!this._sm.isNew() && this._sm.isPersistent() && dirty != null) {
         this._unloaded = (BitSet)dirty.clone();

         for(int i = 0; i < fields.length; ++i) {
            int var10000 = fields[i].getManagement();
            FieldMetaData var10001 = fields[i];
            if (var10000 != 3) {
               this._unloaded.clear(i);
            }
         }
      } else {
         this._unloaded = new BitSet(fields.length);
      }

   }

   public PersistenceCapable getState() {
      return this._state;
   }

   public BitSet getUnloaded() {
      return this._unloaded;
   }

   public boolean saveField(int field) {
      if (this._sm.getLoaded() != null && !this._sm.getLoaded().get(field)) {
         this._unloaded.set(field);
         return false;
      } else if (this._saved != null && this._saved.get(field)) {
         return false;
      } else {
         FieldMetaData fmd = this._sm.getMetaData().getField(field);
         boolean mutable = false;
         switch (fmd.getDeclaredTypeCode()) {
            case 8:
            case 11:
            case 12:
            case 13:
            case 14:
               mutable = true;
            case 9:
            case 10:
         }

         if (this._sm.getBroker().getInverseManager() == null || fmd.getInverseMetaDatas().length == 0) {
            int restore = this._sm.getBroker().getRestoreState();
            if (restore == 0 || mutable && restore == 1) {
               this._unloaded.set(field);
               return false;
            }
         }

         if (this._state == null) {
            this._state = this._sm.getPersistenceCapable().pcNewInstance(this._sm, true);
         }

         if (this._saved == null) {
            this._saved = new BitSet(this._sm.getMetaData().getFields().length);
         }

         this._saved.set(field);
         if (mutable) {
            return true;
         } else {
            if (this._copyField == null) {
               this._copyField = new int[1];
            }

            this._copyField[0] = field;
            this.getState().pcCopyFields(this._sm.getPersistenceCapable(), this._copyField);
            return false;
         }
      }
   }

   public boolean restoreField(int field) {
      if (this._unloaded.get(field)) {
         return true;
      } else if (this._saved != null && this._saved.get(field)) {
         if (this._copyField == null) {
            this._copyField = new int[1];
         }

         this._copyField[0] = field;
         this._sm.getPersistenceCapable().pcCopyFields(this.getState(), this._copyField);
         return false;
      } else {
         return false;
      }
   }

   public boolean isFieldEqual(int field, Object current) {
      if (this._saved != null && this._saved.get(field)) {
         if (!(this.getState().pcGetStateManager() instanceof StateManagerImpl)) {
            return false;
         } else {
            StateManagerImpl sm = (StateManagerImpl)this.getState().pcGetStateManager();
            SingleFieldManager single = new SingleFieldManager(sm, sm.getBroker());
            sm.provideField(this.getState(), single, field);
            Object old = single.fetchObjectField(field);
            return current == old || current != null && current.equals(old);
         }
      } else {
         return false;
      }
   }

   public Object fetchObjectField(int field) {
      Object val = this._field;
      this._field = null;
      return val;
   }

   public void storeObjectField(int field, Object curVal) {
      ProxyManager proxy = this._sm.getBroker().getConfiguration().getProxyManagerInstance();
      FieldMetaData fmd = this._sm.getMetaData().getField(field);
      switch (fmd.getDeclaredTypeCode()) {
         case 8:
            this._field = proxy.copyCustom(curVal);
            if (this._field == null) {
               this._field = curVal;
            }
            break;
         case 9:
         case 10:
         default:
            this._field = curVal;
            break;
         case 11:
            this._field = proxy.copyArray(curVal);
            break;
         case 12:
            this._field = proxy.copyCollection((Collection)curVal);
            break;
         case 13:
            this._field = proxy.copyMap((Map)curVal);
            break;
         case 14:
            this._field = proxy.copyDate((Date)curVal);
      }

      if (curVal != null && this._field == null) {
         this._unloaded.set(field);
         this._saved.clear(field);
      }

   }

   private void writeObject(ObjectOutputStream oos) throws IOException {
      oos.defaultWriteObject();
      this._sm.writePC(oos, this._state);
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      ois.defaultReadObject();
      this._state = this._sm.readPC(ois);
   }
}
