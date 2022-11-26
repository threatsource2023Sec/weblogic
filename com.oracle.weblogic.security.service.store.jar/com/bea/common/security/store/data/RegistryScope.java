package com.bea.common.security.store.data;

import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.Reflection;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.util.UserException;

public abstract class RegistryScope extends DomainRealmScope implements PersistenceCapable {
   private String registryName;
   private static int pcInheritedFieldCount = DomainRealmScope.pcGetManagedFieldCount();
   private static String[] pcFieldNames;
   private static Class[] pcFieldTypes;
   private static byte[] pcFieldFlags;
   private static Class pcPCSuperclass;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$DomainRealmScope;
   // $FF: synthetic field
   static Class class$Ljava$lang$String;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$RegistryScope;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$RegistryScopeId;

   public RegistryScope() {
   }

   public RegistryScope(String domainName, String realmName, String registryName) {
      super(domainName, realmName);
      pcSetregistryName(this, registryName);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof RegistryScope)) {
         return false;
      } else {
         RegistryScope o = (RegistryScope)other;
         return pcGetregistryName(this) == pcGetregistryName(o) || pcGetregistryName(this) != null && pcGetregistryName(this).equals(pcGetregistryName(o));
      }
   }

   public int hashCode() {
      return (pcGetregistryName(this) != null ? pcGetregistryName(this).hashCode() : 0) ^ super.hashCode();
   }

   public String toString() {
      return "registryName=" + ApplicationIdUtil.encode(pcGetregistryName(this)) + ',' + super.toString();
   }

   public String getRegistryName() {
      return pcGetregistryName(this);
   }

   public void setRegistryName(String registryName) {
      pcSetregistryName(this, registryName);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$DomainRealmScope != null ? class$Lcom$bea$common$security$store$data$DomainRealmScope : (class$Lcom$bea$common$security$store$data$DomainRealmScope = class$("com.bea.common.security.store.data.DomainRealmScope"));
      pcFieldNames = new String[]{"registryName"};
      pcFieldTypes = new Class[]{class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String"))};
      pcFieldFlags = new byte[]{26};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$RegistryScope != null ? class$Lcom$bea$common$security$store$data$RegistryScope : (class$Lcom$bea$common$security$store$data$RegistryScope = class$("com.bea.common.security.store.data.RegistryScope")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "RegistryScope", (PersistenceCapable)null);
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
      super.pcClearFields();
      this.registryName = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      throw new UserException();
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      throw new UserException();
   }

   protected static int pcGetManagedFieldCount() {
      return 1 + DomainRealmScope.pcGetManagedFieldCount();
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcReplaceField(var1);
      } else {
         switch (var2) {
            case 0:
               this.registryName = (String)this.pcStateManager.replaceStringField(this, var1);
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
         super.pcProvideField(var1);
      } else {
         switch (var2) {
            case 0:
               this.pcStateManager.providedStringField(this, var1, this.registryName);
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

   protected void pcCopyField(RegistryScope var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.registryName = var1.registryName;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      RegistryScope var3 = (RegistryScope)var1;
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

   public void pcCopyKeyFieldsToObjectId(FieldSupplier var1, Object var2) {
      super.pcCopyKeyFieldsToObjectId(var1, var2);
      RegistryScopeId var3 = (RegistryScopeId)var2;
      int var4 = pcInheritedFieldCount;
      Reflection.set(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$RegistryScopeId != null ? class$Lcom$bea$common$security$store$data$RegistryScopeId : (class$Lcom$bea$common$security$store$data$RegistryScopeId = class$("com.bea.common.security.store.data.RegistryScopeId")), "registryName", true), var1.fetchStringField(0 + var4));
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      RegistryScopeId var2 = (RegistryScopeId)var1;
      Reflection.set(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$RegistryScopeId != null ? class$Lcom$bea$common$security$store$data$RegistryScopeId : (class$Lcom$bea$common$security$store$data$RegistryScopeId = class$("com.bea.common.security.store.data.RegistryScopeId")), "registryName", true), this.registryName);
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      RegistryScopeId var3 = (RegistryScopeId)var2;
      var1.storeStringField(0 + pcInheritedFieldCount, (String)Reflection.get(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$RegistryScopeId != null ? class$Lcom$bea$common$security$store$data$RegistryScopeId : (class$Lcom$bea$common$security$store$data$RegistryScopeId = class$("com.bea.common.security.store.data.RegistryScopeId")), "registryName", true)));
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      RegistryScopeId var2 = (RegistryScopeId)var1;
      this.registryName = (String)Reflection.get(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$RegistryScopeId != null ? class$Lcom$bea$common$security$store$data$RegistryScopeId : (class$Lcom$bea$common$security$store$data$RegistryScopeId = class$("com.bea.common.security.store.data.RegistryScopeId")), "registryName", true));
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new RegistryScopeId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new RegistryScopeId();
   }

   private static final String pcGetregistryName(RegistryScope var0) {
      if (var0.pcStateManager == null) {
         return var0.registryName;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.registryName;
      }
   }

   private static final void pcSetregistryName(RegistryScope var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.registryName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 0, var0.registryName, var1, 0);
      }
   }
}
