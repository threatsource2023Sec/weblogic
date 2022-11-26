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
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.ProxyManager;

class SavepointFieldManager extends ClearFieldManager implements Serializable {
   private static final Localizer _loc = Localizer.forPackage(SavepointFieldManager.class);
   private final StateManagerImpl _sm;
   private final BitSet _loaded;
   private final BitSet _dirty;
   private final BitSet _flush;
   private final PCState _state;
   private transient PersistenceCapable _copy;
   private final Object _version;
   private final Object _loadVersion;
   private Object _field = null;
   private int[] _copyField = null;
   private BitSet _mutable;

   public SavepointFieldManager(StateManagerImpl sm, boolean copy) {
      this._sm = sm;
      this._state = this._sm.getPCState();
      this._dirty = (BitSet)this._sm.getDirty().clone();
      this._flush = (BitSet)this._sm.getFlushed().clone();
      this._loaded = (BitSet)this._sm.getLoaded().clone();
      FieldMetaData[] fields = this._sm.getMetaData().getFields();

      for(int i = 0; i < this._loaded.length(); ++i) {
         if (this._loaded.get(i)) {
            if (!copy && fields[i].getManagement() != 1) {
               this._loaded.clear(i);
            } else {
               if (this._copy == null) {
                  this._copy = this._sm.getPersistenceCapable().pcNewInstance(this._sm, true);
               }

               this.storeField(fields[i]);
            }
         }
      }

      this._sm.proxyFields(false, false);
      this._version = this._sm.getVersion();
      this._loadVersion = this._sm.getLoadVersion();
   }

   public StateManagerImpl getStateManager() {
      return this._sm;
   }

   public Object getVersion() {
      return this._version;
   }

   public Object getLoadVersion() {
      return this._loadVersion;
   }

   public PersistenceCapable getCopy() {
      return this._copy;
   }

   public PCState getPCState() {
      return this._state;
   }

   public BitSet getLoaded() {
      return this._loaded;
   }

   public BitSet getDirty() {
      return this._dirty;
   }

   public BitSet getFlushed() {
      return this._flush;
   }

   public void storeField(FieldMetaData field) {
      switch (field.getDeclaredTypeCode()) {
         case 8:
         case 11:
         case 12:
         case 13:
         case 14:
            if (this._mutable == null) {
               this._mutable = new BitSet(this._sm.getMetaData().getFields().length);
            }

            this._mutable.set(field.getIndex());
         case 9:
         case 10:
      }

      if (this._mutable != null && this._mutable.get(field.getIndex())) {
         this._sm.provideField(this._sm.getPersistenceCapable(), this, field.getIndex());
         this._sm.replaceField(this._copy, this, field.getIndex());
      } else {
         if (this._copyField == null) {
            this._copyField = new int[1];
         }

         this._copyField[0] = field.getIndex();
         this._copy.pcCopyFields(this._sm.getPersistenceCapable(), this._copyField);
      }

   }

   public boolean restoreField(int field) {
      if (!this._loaded.get(field)) {
         return false;
      } else if (this._mutable != null && this._mutable.get(field)) {
         return true;
      } else {
         if (this._copyField == null) {
            this._copyField = new int[1];
         }

         this._copyField[0] = field;
         this._sm.getPersistenceCapable().pcCopyFields(this._copy, this._copyField);
         return false;
      }
   }

   public Object fetchObjectField(int field) {
      Object val = this._field;
      this._field = null;
      return val;
   }

   public void storeObjectField(int field, Object curVal) {
      ProxyManager proxy = this._sm.getContext().getConfiguration().getProxyManagerInstance();
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
         throw new InternalException(_loc.get("no-savepoint-copy", (Object)fmd));
      }
   }

   private void writeObject(ObjectOutputStream oos) throws IOException {
      oos.defaultWriteObject();
      this._sm.writePC(oos, this._copy);
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      ois.defaultReadObject();
      this._copy = this._sm.readPC(ois);
   }
}
