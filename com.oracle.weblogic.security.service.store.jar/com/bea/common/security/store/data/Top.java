package com.bea.common.security.store.data;

import java.util.Date;
import javax.jdo.PersistenceManager;
import kodo.jdo.FatalInternalException;
import kodo.jdo.KodoJDOHelper;
import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.util.UserException;

public abstract class Top implements PersistenceCapable, javax.jdo.spi.PersistenceCapable {
   private Date createTimestamp;
   private Date modifyTimestamp;
   private static int pcInheritedFieldCount;
   private static String[] pcFieldNames = new String[]{"createTimestamp", "modifyTimestamp"};
   private static Class[] pcFieldTypes;
   private static byte[] pcFieldFlags;
   private static Class pcPCSuperclass;
   protected transient StateManager pcStateManager;
   // $FF: synthetic field
   static Class class$Ljava$util$Date;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$Top;

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return other instanceof Top;
      }
   }

   public int hashCode() {
      return 0;
   }

   public String toString() {
      return "";
   }

   public Date getCreateTimestamp() {
      return pcGetcreateTimestamp(this);
   }

   public Date getModifyTimestamp() {
      return pcGetmodifyTimestamp(this);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcFieldTypes = new Class[]{class$Ljava$util$Date != null ? class$Ljava$util$Date : (class$Ljava$util$Date = class$("java.util.Date")), class$Ljava$util$Date != null ? class$Ljava$util$Date : (class$Ljava$util$Date = class$("java.util.Date"))};
      pcFieldFlags = new byte[]{26, 21};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$Top != null ? class$Lcom$bea$common$security$store$data$Top : (class$Lcom$bea$common$security$store$data$Top = class$("com.bea.common.security.store.data.Top")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "Top", (PersistenceCapable)null);
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   protected void pcClearFields() {
      this.createTimestamp = null;
      this.modifyTimestamp = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      throw new UserException();
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      throw new UserException();
   }

   protected static int pcGetManagedFieldCount() {
      return 2;
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         throw new IllegalArgumentException();
      } else {
         switch (var2) {
            case 0:
               this.createTimestamp = (Date)this.pcStateManager.replaceObjectField(this, var1);
               return;
            case 1:
               this.modifyTimestamp = (Date)this.pcStateManager.replaceObjectField(this, var1);
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcReplaceFields(int[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         this.pcReplaceField(var1[var2]);
      }

   }

   public void pcProvideField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         throw new IllegalArgumentException();
      } else {
         switch (var2) {
            case 0:
               this.pcStateManager.providedObjectField(this, var1, this.createTimestamp);
               return;
            case 1:
               this.pcStateManager.providedObjectField(this, var1, this.modifyTimestamp);
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcProvideFields(int[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         this.pcProvideField(var1[var2]);
      }

   }

   protected void pcCopyField(Top var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         throw new IllegalArgumentException();
      } else {
         switch (var3) {
            case 0:
               this.createTimestamp = var1.createTimestamp;
               return;
            case 1:
               this.modifyTimestamp = var1.modifyTimestamp;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      Top var3 = (Top)var1;
      if (var3.pcStateManager != this.pcStateManager) {
         throw new IllegalArgumentException();
      } else if (this.pcStateManager == null) {
         throw new IllegalStateException();
      } else {
         for(int var4 = 0; var4 < var2.length; ++var4) {
            this.pcCopyField(var3, var2[var4]);
         }

      }
   }

   public Object pcGetGenericContext() {
      return this.pcStateManager == null ? null : this.pcStateManager.getGenericContext();
   }

   public Object pcFetchObjectId() {
      return this.pcStateManager == null ? null : this.pcStateManager.fetchObjectId();
   }

   public boolean pcIsDeleted() {
      return this.pcStateManager == null ? false : this.pcStateManager.isDeleted();
   }

   public boolean pcIsDirty() {
      return this.pcStateManager == null ? false : this.pcStateManager.isDirty();
   }

   public boolean pcIsNew() {
      return this.pcStateManager == null ? false : this.pcStateManager.isNew();
   }

   public boolean pcIsPersistent() {
      return this.pcStateManager == null ? false : this.pcStateManager.isPersistent();
   }

   public boolean pcIsTransactional() {
      return this.pcStateManager == null ? false : this.pcStateManager.isTransactional();
   }

   public boolean pcSerializing() {
      return this.pcStateManager == null ? false : this.pcStateManager.serializing();
   }

   public void pcDirty(String var1) {
      if (this.pcStateManager != null) {
         this.pcStateManager.dirty(var1);
      }
   }

   public StateManager pcGetStateManager() {
      return this.pcStateManager;
   }

   public Object pcGetVersion() {
      return this.pcStateManager == null ? this.modifyTimestamp : this.pcStateManager.getVersion();
   }

   public synchronized void pcReplaceStateManager(StateManager var1) throws SecurityException {
      if (this.pcStateManager != null) {
         this.pcStateManager = this.pcStateManager.replaceStateManager(var1);
      } else {
         this.pcStateManager = var1;
      }
   }

   public void pcCopyKeyFieldsToObjectId(FieldSupplier var1, Object var2) {
      TopId var3 = (TopId)var2;
      int var4 = pcInheritedFieldCount;
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      TopId var2 = (TopId)var1;
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      TopId var3 = (TopId)var2;
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      TopId var2 = (TopId)var1;
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new TopId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new TopId();
   }

   private static final Date pcGetcreateTimestamp(Top var0) {
      if (var0.pcStateManager == null) {
         return var0.createTimestamp;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.createTimestamp;
      }
   }

   private static final void pcSetcreateTimestamp(Top var0, Date var1) {
      if (var0.pcStateManager == null) {
         var0.createTimestamp = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 0, var0.createTimestamp, var1, 0);
      }
   }

   private static final Date pcGetmodifyTimestamp(Top var0) {
      if (var0.pcStateManager == null) {
         return var0.modifyTimestamp;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.modifyTimestamp;
      }
   }

   private static final void pcSetmodifyTimestamp(Top var0, Date var1) {
      if (var0.pcStateManager == null) {
         var0.modifyTimestamp = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 1, var0.modifyTimestamp, var1, 0);
      }
   }

   public Boolean pcIsDetached() {
      return Boolean.FALSE;
   }

   public Object pcGetDetachedState() {
      return null;
   }

   public void pcSetDetachedState(Object var1) {
   }

   public PersistenceManager jdoGetPersistenceManager() {
      return KodoJDOHelper.toPersistenceManager((Broker)this.pcGetGenericContext());
   }

   public void jdoReplaceStateManager(javax.jdo.spi.StateManager var1) {
      throw new FatalInternalException();
   }

   public void jdoProvideField(int var1) {
      throw new FatalInternalException();
   }

   public void jdoProvideFields(int[] var1) {
      throw new FatalInternalException();
   }

   public void jdoReplaceField(int var1) {
      throw new FatalInternalException();
   }

   public void jdoReplaceFields(int[] var1) {
      throw new FatalInternalException();
   }

   public void jdoReplaceFlags() {
      throw new FatalInternalException();
   }

   public void jdoCopyFields(Object var1, int[] var2) {
      throw new FatalInternalException();
   }

   public javax.jdo.spi.PersistenceCapable jdoNewInstance(javax.jdo.spi.StateManager var1) {
      throw new FatalInternalException();
   }

   public javax.jdo.spi.PersistenceCapable jdoNewInstance(javax.jdo.spi.StateManager var1, Object var2) {
      throw new FatalInternalException();
   }

   public Object jdoNewObjectIdInstance() {
      throw new FatalInternalException();
   }

   public Object jdoNewObjectIdInstance(Object var1) {
      throw new FatalInternalException();
   }

   public void jdoCopyKeyFieldsToObjectId(Object var1) {
      throw new FatalInternalException();
   }

   public void jdoCopyKeyFieldsToObjectId(javax.jdo.spi.PersistenceCapable.ObjectIdFieldSupplier var1, Object var2) {
      throw new FatalInternalException();
   }

   public void jdoCopyKeyFieldsFromObjectId(javax.jdo.spi.PersistenceCapable.ObjectIdFieldConsumer var1, Object var2) {
      throw new FatalInternalException();
   }

   public Object jdoGetVersion() {
      return this.pcGetVersion();
   }

   public boolean jdoIsDirty() {
      return this.pcIsDirty();
   }

   public boolean jdoIsTransactional() {
      return this.pcIsTransactional();
   }

   public boolean jdoIsPersistent() {
      return this.pcIsPersistent();
   }

   public boolean jdoIsNew() {
      return this.pcIsNew();
   }

   public boolean jdoIsDeleted() {
      return this.pcIsDeleted();
   }

   public boolean jdoIsDetached() {
      Boolean var1 = this.pcIsDetached();
      return var1 == null ? false : var1;
   }

   public void jdoMakeDirty(String var1) {
      this.pcDirty(var1);
   }

   public Object jdoGetObjectId() {
      return KodoJDOHelper.fromKodoObjectId(this.pcFetchObjectId());
   }

   public Object jdoGetTransactionalObjectId() {
      return this.jdoGetObjectId();
   }
}
