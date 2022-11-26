package org.apache.openjpa.enhance;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StateManagerImpl;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.ApplicationIds;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.ObjectId;

public class ReflectingPersistenceCapable implements PersistenceCapable, ManagedInstanceProvider, Serializable {
   private Object o;
   private StateManager sm;
   private transient PersistenceCapable pcSubclassInstance;
   private transient ClassMetaData meta;
   private boolean serializationUserVisible = true;

   public ReflectingPersistenceCapable(Object o, OpenJPAConfiguration conf) {
      this.o = o;
      Class type = o.getClass();
      this.pcSubclassInstance = PCRegistry.newInstance(type, (StateManager)null, false);
      this.meta = conf.getMetaDataRepositoryInstance().getMetaData((Class)type, (ClassLoader)null, true);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   public Object pcGetGenericContext() {
      return this.sm == null ? null : this.sm.getGenericContext();
   }

   public StateManager pcGetStateManager() {
      return this.sm;
   }

   public void pcReplaceStateManager(StateManager sm) {
      this.sm = sm;
      if (this.meta == null && sm instanceof OpenJPAStateManager) {
         this.meta = ((OpenJPAStateManager)sm).getMetaData();
      }

   }

   public void pcProvideField(int i) {
      Object value = this.getValue(i, this.o);
      switch (this.meta.getField(i).getTypeCode()) {
         case 0:
            this.sm.providedBooleanField(this, i, value == null ? false : (Boolean)value);
            break;
         case 1:
            this.sm.providedByteField(this, i, value == null ? 0 : (Byte)value);
            break;
         case 2:
            this.sm.providedCharField(this, i, value == null ? '\u0000' : (Character)value);
            break;
         case 3:
            this.sm.providedDoubleField(this, i, value == null ? 0.0 : (Double)value);
            break;
         case 4:
            this.sm.providedFloatField(this, i, value == null ? 0.0F : (Float)value);
            break;
         case 5:
            this.sm.providedIntField(this, i, value == null ? 0 : (Integer)value);
            break;
         case 6:
            this.sm.providedLongField(this, i, value == null ? 0L : (Long)value);
            break;
         case 7:
            this.sm.providedShortField(this, i, value == null ? 0 : (Short)value);
            break;
         case 8:
         default:
            this.sm.providedObjectField(this, i, value);
            break;
         case 9:
            this.sm.providedStringField(this, i, (String)value);
      }

   }

   public void pcProvideFields(int[] fieldIndices) {
      for(int i = 0; i < fieldIndices.length; ++i) {
         this.pcProvideField(fieldIndices[i]);
      }

   }

   public void pcReplaceField(int i) {
      switch (this.meta.getField(i).getTypeCode()) {
         case 0:
            this.setValue(i, this.o, this.sm.replaceBooleanField(this, i));
            break;
         case 1:
            this.setValue(i, this.o, new Byte(this.sm.replaceByteField(this, i)));
            break;
         case 2:
            this.setValue(i, this.o, new Character(this.sm.replaceCharField(this, i)));
            break;
         case 3:
            this.setValue(i, this.o, new Double(this.sm.replaceDoubleField(this, i)));
            break;
         case 4:
            this.setValue(i, this.o, new Float(this.sm.replaceFloatField(this, i)));
            break;
         case 5:
            this.setValue(i, this.o, new Integer(this.sm.replaceIntField(this, i)));
            break;
         case 6:
            this.setValue(i, this.o, new Long(this.sm.replaceLongField(this, i)));
            break;
         case 7:
            this.setValue(i, this.o, new Short(this.sm.replaceShortField(this, i)));
            break;
         case 8:
         default:
            this.setValue(i, this.o, this.sm.replaceObjectField(this, i));
            break;
         case 9:
            this.setValue(i, this.o, this.sm.replaceStringField(this, i));
      }

   }

   public void pcReplaceFields(int[] fieldIndices) {
      for(int i = 0; i < fieldIndices.length; ++i) {
         this.pcReplaceField(fieldIndices[i]);
      }

   }

   public void pcCopyField(Object fromObject, int i) {
      this.setValue(i, this.o, this.getValue(i, fromObject));
   }

   public void pcCopyFields(Object fromObject, int[] fieldIndices) {
      if (fromObject instanceof ReflectingPersistenceCapable) {
         fromObject = ((ReflectingPersistenceCapable)fromObject).getManagedInstance();
      }

      for(int i = 0; i < fieldIndices.length; ++i) {
         this.pcCopyField(fromObject, fieldIndices[i]);
      }

   }

   public void pcDirty(String fieldName) {
      if (this.sm != null) {
         this.sm.dirty(fieldName);
      }

   }

   public Object pcFetchObjectId() {
      return this.sm != null ? this.sm.fetchObjectId() : null;
   }

   public Object pcGetVersion() {
      return this.sm == null ? null : this.sm.getVersion();
   }

   public boolean pcIsDirty() {
      if (this.sm == null) {
         return false;
      } else {
         if (this.sm instanceof StateManagerImpl) {
            ((StateManagerImpl)this.sm).dirtyCheck();
         }

         return this.sm.isDirty();
      }
   }

   public boolean pcIsTransactional() {
      return this.sm == null ? false : this.sm.isTransactional();
   }

   public boolean pcIsPersistent() {
      return this.sm == null ? false : this.sm.isPersistent();
   }

   public boolean pcIsNew() {
      return this.sm == null ? false : this.sm.isNew();
   }

   public boolean pcIsDeleted() {
      return this.sm == null ? false : this.sm.isDeleted();
   }

   public Boolean pcIsDetached() {
      return this.sm != null ? this.sm.isDetached() : null;
   }

   public PersistenceCapable pcNewInstance(StateManager sm, boolean clear) {
      return this.pcSubclassInstance.pcNewInstance(sm, clear);
   }

   public PersistenceCapable pcNewInstance(StateManager sm, Object oid, boolean clear) {
      return this.pcSubclassInstance.pcNewInstance(sm, oid, clear);
   }

   public Object pcNewObjectIdInstance() {
      FieldMetaData[] pkFields = this.meta.getPrimaryKeyFields();
      Object[] pks = new Object[pkFields.length];

      for(int i = 0; i < pkFields.length; ++i) {
         pks[i] = this.getValue(pkFields[i].getIndex(), this.o);
      }

      return ApplicationIds.fromPKValues(pks, this.meta);
   }

   public Object pcNewObjectIdInstance(Object oid) {
      return this.pcSubclassInstance.pcNewObjectIdInstance(oid);
   }

   public void pcCopyKeyFieldsToObjectId(Object oid) {
      Object target;
      if (oid instanceof ObjectId) {
         target = ((ObjectId)oid).getId();
      } else {
         target = oid;
      }

      FieldMetaData[] pks = this.meta.getPrimaryKeyFields();

      for(int i = 0; i < pks.length; ++i) {
         Object val = this.getValue(pks[i].getIndex(), this.o);
         Field f = Reflection.findField(target.getClass(), pks[i].getName(), true);
         Reflection.set(target, f, val);
      }

   }

   public void pcCopyKeyFieldsToObjectId(FieldSupplier supplier, Object obj) {
      throw new InternalException();
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer consumer, Object obj) {
      throw new InternalException();
   }

   public Object pcGetDetachedState() {
      return null;
   }

   public void pcSetDetachedState(Object state) {
      if (state != null) {
         throw new UnsupportedOperationException();
      }
   }

   public void pcSetSerializationUserVisible(boolean userVisible) {
      this.serializationUserVisible = userVisible;
   }

   public boolean pcIsSerializationUserVisible() {
      return this.serializationUserVisible;
   }

   public Object getManagedInstance() {
      return this.o;
   }

   private Object getValue(int i, Object o) {
      Field field;
      if (this.meta.getAccessType() == 4) {
         field = Reflection.findField(this.meta.getDescribedType(), this.toFieldName(i), true);
         return Reflection.get(o, field);
      } else {
         field = (Field)this.meta.getField(i).getBackingMember();
         return Reflection.get(o, field);
      }
   }

   private String toFieldName(int i) {
      return this.pcSubclassInstance instanceof AttributeTranslator ? ((AttributeTranslator)this.pcSubclassInstance).pcAttributeIndexToFieldName(i) : this.meta.getField(i).getName();
   }

   private void setValue(int i, Object o, Object val) {
      Field field;
      if (this.meta.getAccessType() == 4) {
         if (!this.meta.isIntercepting()) {
            Method meth = Reflection.findSetter(this.meta.getDescribedType(), this.meta.getField(i).getName(), true);
            Reflection.set(o, meth, val);
         } else {
            field = Reflection.findField(this.meta.getDescribedType(), this.toFieldName(i), true);
            Reflection.set(o, field, val);
         }
      } else {
         field = (Field)this.meta.getField(i).getBackingMember();
         Reflection.set(o, field, val);
      }

   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.defaultWriteObject();
      out.writeObject(this.meta.getDescribedType());
   }

   private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
      in.defaultReadObject();
      Class type = (Class)in.readObject();
      this.pcSubclassInstance = PCRegistry.newInstance(type, (StateManager)null, false);
      ImplHelper.registerPersistenceCapable(this);
   }
}
