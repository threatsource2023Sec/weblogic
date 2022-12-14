package org.apache.openjpa.meta;

import java.io.ObjectOutput;
import java.util.Calendar;
import java.util.SortedMap;
import java.util.SortedSet;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.util.InternalException;

class ProxySetupStateManager implements StateManager {
   private Object _object = null;

   public void setProxyData(PersistenceCapable pc, ClassMetaData meta) {
      FieldMetaData[] fmds = meta.getFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (fmds[i].getDefiningMetaData() == meta) {
            switch (fmds[i].getDeclaredTypeCode()) {
               case 12:
                  pc.pcProvideField(fmds[i].getIndex());
                  if (this._object != null) {
                     if (this._object.getClass() != fmds[i].getDeclaredType()) {
                        fmds[i].setProxyType(this._object.getClass());
                     }

                     if (this._object instanceof SortedSet) {
                        fmds[i].setInitializer(((SortedSet)this._object).comparator());
                     }
                  }
                  break;
               case 13:
                  pc.pcProvideField(fmds[i].getIndex());
                  if (this._object != null) {
                     if (this._object.getClass() != fmds[i].getDeclaredType()) {
                        fmds[i].setProxyType(this._object.getClass());
                     }

                     if (this._object instanceof SortedMap) {
                        fmds[i].setInitializer(((SortedMap)this._object).comparator());
                     }
                  }
                  break;
               case 28:
                  pc.pcProvideField(fmds[i].getIndex());
                  if (this._object != null) {
                     fmds[i].setInitializer(((Calendar)this._object).getTimeZone());
                  }
            }
         }
      }

   }

   public Object getPCPrimaryKey(Object oid, int field) {
      throw new UnsupportedOperationException();
   }

   public StateManager replaceStateManager(StateManager sm) {
      throw new InternalException();
   }

   public boolean isDirty() {
      throw new InternalException();
   }

   public boolean isTransactional() {
      throw new InternalException();
   }

   public boolean isPersistent() {
      throw new InternalException();
   }

   public boolean isNew() {
      throw new InternalException();
   }

   public boolean isDeleted() {
      throw new InternalException();
   }

   public boolean isDetached() {
      throw new InternalException();
   }

   public Object getGenericContext() {
      throw new InternalException();
   }

   public void dirty(String s) {
      throw new InternalException();
   }

   public Object fetchObjectId() {
      throw new InternalException();
   }

   public Object getVersion() {
      throw new InternalException();
   }

   public void accessingField(int i) {
      throw new InternalException();
   }

   public boolean serializing() {
      throw new InternalException();
   }

   public boolean writeDetached(ObjectOutput out) {
      throw new InternalException();
   }

   public void proxyDetachedDeserialized(int idx) {
      throw new InternalException();
   }

   public void settingBooleanField(PersistenceCapable pc, int i, boolean b, boolean b2, int set) {
      throw new InternalException();
   }

   public void settingCharField(PersistenceCapable pc, int i, char c, char c2, int set) {
      throw new InternalException();
   }

   public void settingByteField(PersistenceCapable pc, int i, byte b, byte b2, int set) {
      throw new InternalException();
   }

   public void settingShortField(PersistenceCapable pc, int i, short s, short s2, int set) {
      throw new InternalException();
   }

   public void settingIntField(PersistenceCapable pc, int i, int i2, int i3, int set) {
      throw new InternalException();
   }

   public void settingLongField(PersistenceCapable pc, int i, long l, long l2, int set) {
      throw new InternalException();
   }

   public void settingFloatField(PersistenceCapable pc, int i, float f, float f2, int set) {
      throw new InternalException();
   }

   public void settingDoubleField(PersistenceCapable pc, int i, double d, double d2, int set) {
      throw new InternalException();
   }

   public void settingStringField(PersistenceCapable pc, int i, String s, String s2, int set) {
      throw new InternalException();
   }

   public void settingObjectField(PersistenceCapable pc, int i, Object o, Object o2, int set) {
      throw new InternalException();
   }

   public void providedBooleanField(PersistenceCapable pc, int i, boolean b) {
      throw new InternalException();
   }

   public void providedCharField(PersistenceCapable pc, int i, char c) {
      throw new InternalException();
   }

   public void providedByteField(PersistenceCapable pc, int i, byte b) {
      throw new InternalException();
   }

   public void providedShortField(PersistenceCapable pc, int i, short s) {
      throw new InternalException();
   }

   public void providedIntField(PersistenceCapable pc, int i, int i2) {
      throw new InternalException();
   }

   public void providedLongField(PersistenceCapable pc, int i, long l) {
      throw new InternalException();
   }

   public void providedFloatField(PersistenceCapable pc, int i, float f) {
      throw new InternalException();
   }

   public void providedDoubleField(PersistenceCapable pc, int i, double d) {
      throw new InternalException();
   }

   public void providedStringField(PersistenceCapable pc, int i, String s) {
      throw new InternalException();
   }

   public void providedObjectField(PersistenceCapable pc, int i, Object o) {
      this._object = o;
   }

   public boolean replaceBooleanField(PersistenceCapable pc, int i) {
      throw new InternalException();
   }

   public char replaceCharField(PersistenceCapable pc, int i) {
      throw new InternalException();
   }

   public byte replaceByteField(PersistenceCapable pc, int i) {
      throw new InternalException();
   }

   public short replaceShortField(PersistenceCapable pc, int i) {
      throw new InternalException();
   }

   public int replaceIntField(PersistenceCapable pc, int i) {
      throw new InternalException();
   }

   public long replaceLongField(PersistenceCapable pc, int i) {
      throw new InternalException();
   }

   public float replaceFloatField(PersistenceCapable pc, int i) {
      throw new InternalException();
   }

   public double replaceDoubleField(PersistenceCapable pc, int i) {
      throw new InternalException();
   }

   public String replaceStringField(PersistenceCapable pc, int i) {
      throw new InternalException();
   }

   public Object replaceObjectField(PersistenceCapable pc, int i) {
      throw new InternalException();
   }
}
