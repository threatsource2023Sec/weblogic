package com.bea.common.security.store.data;

import javax.jdo.PersistenceManager;
import kodo.jdo.FatalInternalException;
import kodo.jdo.KodoJDOHelper;
import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.util.IntId;
import org.apache.openjpa.util.InternalException;

public class SchemaVersion implements PersistenceCapable, javax.jdo.spi.PersistenceCapable {
   private int currentVersion;
   private static int pcInheritedFieldCount;
   private static String[] pcFieldNames = new String[]{"currentVersion"};
   private static Class[] pcFieldTypes;
   private static byte[] pcFieldFlags;
   private static Class pcPCSuperclass;
   protected transient StateManager pcStateManager;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$SchemaVersion;

   public int getCurrentVersion() {
      return pcGetcurrentVersion(this);
   }

   public void setCurrentVersion(int currentVersion) {
      pcSetcurrentVersion(this, currentVersion);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcFieldTypes = new Class[]{Integer.TYPE};
      pcFieldFlags = new byte[]{26};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$SchemaVersion != null ? class$Lcom$bea$common$security$store$data$SchemaVersion : (class$Lcom$bea$common$security$store$data$SchemaVersion = class$("com.bea.common.security.store.data.SchemaVersion")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "SchemaVersion", new SchemaVersion());
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
      this.currentVersion = 0;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      SchemaVersion var4 = new SchemaVersion();
      if (var3) {
         var4.pcClearFields();
      }

      var4.pcStateManager = var1;
      var4.pcCopyKeyFieldsFromObjectId(var2);
      return var4;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      SchemaVersion var3 = new SchemaVersion();
      if (var2) {
         var3.pcClearFields();
      }

      var3.pcStateManager = var1;
      return var3;
   }

   protected static int pcGetManagedFieldCount() {
      return 1;
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         throw new IllegalArgumentException();
      } else {
         switch (var2) {
            case 0:
               this.currentVersion = this.pcStateManager.replaceIntField(this, var1);
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
               this.pcStateManager.providedIntField(this, var1, this.currentVersion);
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

   protected void pcCopyField(SchemaVersion var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         throw new IllegalArgumentException();
      } else {
         switch (var3) {
            case 0:
               this.currentVersion = var1.currentVersion;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      SchemaVersion var3 = (SchemaVersion)var1;
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
      return this.pcStateManager == null ? null : this.pcStateManager.getVersion();
   }

   public synchronized void pcReplaceStateManager(StateManager var1) throws SecurityException {
      if (this.pcStateManager != null) {
         this.pcStateManager = this.pcStateManager.replaceStateManager(var1);
      } else {
         this.pcStateManager = var1;
      }
   }

   public void pcCopyKeyFieldsToObjectId(FieldSupplier var1, Object var2) {
      throw new InternalException();
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      throw new InternalException();
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      IntId var3 = (IntId)var2;
      var1.storeIntField(0 + pcInheritedFieldCount, var3.getId());
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      IntId var2 = (IntId)var1;
      this.currentVersion = var2.getId();
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new IntId(class$Lcom$bea$common$security$store$data$SchemaVersion != null ? class$Lcom$bea$common$security$store$data$SchemaVersion : (class$Lcom$bea$common$security$store$data$SchemaVersion = class$("com.bea.common.security.store.data.SchemaVersion")), (String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new IntId(class$Lcom$bea$common$security$store$data$SchemaVersion != null ? class$Lcom$bea$common$security$store$data$SchemaVersion : (class$Lcom$bea$common$security$store$data$SchemaVersion = class$("com.bea.common.security.store.data.SchemaVersion")), this.currentVersion);
   }

   private static final int pcGetcurrentVersion(SchemaVersion var0) {
      if (var0.pcStateManager == null) {
         return var0.currentVersion;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.currentVersion;
      }
   }

   private static final void pcSetcurrentVersion(SchemaVersion var0, int var1) {
      if (var0.pcStateManager == null) {
         var0.currentVersion = var1;
      } else {
         var0.pcStateManager.settingIntField(var0, pcInheritedFieldCount + 0, var0.currentVersion, var1, 0);
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
