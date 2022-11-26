package com.bea.common.security.store.data;

import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.Reflection;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.util.UserException;

public abstract class CredentialMap extends DomainRealmScope implements PersistenceCapable {
   private String cn;
   private String resourceName;
   private String applicationName;
   private String moduleName;
   private String wlsCreatorInfo;
   private String wlsCollectionName;
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
   static Class class$Lcom$bea$common$security$store$data$CredentialMap;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$CredentialMapId;

   public CredentialMap() {
   }

   public CredentialMap(String domainName, String realmName, String cn) {
      super(domainName, realmName);
      pcSetcn(this, cn);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof CredentialMap)) {
         return false;
      } else {
         CredentialMap o = (CredentialMap)other;
         return pcGetcn(this) == pcGetcn(o) || pcGetcn(this) != null && pcGetcn(this).equals(pcGetcn(o));
      }
   }

   public int hashCode() {
      return (pcGetcn(this) != null ? pcGetcn(this).hashCode() : 0) ^ super.hashCode();
   }

   public String toString() {
      return "cn=" + ApplicationIdUtil.encode(pcGetcn(this)) + ',' + super.toString();
   }

   public String getApplicationName() {
      return pcGetapplicationName(this);
   }

   public void setApplicationName(String applicationName) {
      pcSetapplicationName(this, applicationName);
   }

   public String getCn() {
      return pcGetcn(this);
   }

   public void setCn(String cn) {
      pcSetcn(this, cn);
   }

   public String getModuleName() {
      return pcGetmoduleName(this);
   }

   public void setModuleName(String moduleName) {
      pcSetmoduleName(this, moduleName);
   }

   public String getWlsCollectionName() {
      return pcGetwlsCollectionName(this);
   }

   public void setWlsCollectionName(String wlsCollectionName) {
      pcSetwlsCollectionName(this, wlsCollectionName);
   }

   public String getWlsCreatorInfo() {
      return pcGetwlsCreatorInfo(this);
   }

   public void setWlsCreatorInfo(String wlsCreatorInfo) {
      pcSetwlsCreatorInfo(this, wlsCreatorInfo);
   }

   public String getResourceName() {
      return pcGetresourceName(this);
   }

   public void setResourceName(String resourceName) {
      pcSetresourceName(this, resourceName);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$DomainRealmScope != null ? class$Lcom$bea$common$security$store$data$DomainRealmScope : (class$Lcom$bea$common$security$store$data$DomainRealmScope = class$("com.bea.common.security.store.data.DomainRealmScope"));
      pcFieldNames = new String[]{"applicationName", "cn", "moduleName", "resourceName", "wlsCollectionName", "wlsCreatorInfo"};
      pcFieldTypes = new Class[]{class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String"))};
      pcFieldFlags = new byte[]{26, 26, 26, 26, 26, 26};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$CredentialMap != null ? class$Lcom$bea$common$security$store$data$CredentialMap : (class$Lcom$bea$common$security$store$data$CredentialMap = class$("com.bea.common.security.store.data.CredentialMap")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "CredentialMap", (PersistenceCapable)null);
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
      this.applicationName = null;
      this.cn = null;
      this.moduleName = null;
      this.resourceName = null;
      this.wlsCollectionName = null;
      this.wlsCreatorInfo = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      throw new UserException();
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      throw new UserException();
   }

   protected static int pcGetManagedFieldCount() {
      return 6 + DomainRealmScope.pcGetManagedFieldCount();
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcReplaceField(var1);
      } else {
         switch (var2) {
            case 0:
               this.applicationName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 1:
               this.cn = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 2:
               this.moduleName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 3:
               this.resourceName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 4:
               this.wlsCollectionName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 5:
               this.wlsCreatorInfo = (String)this.pcStateManager.replaceStringField(this, var1);
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
               this.pcStateManager.providedStringField(this, var1, this.applicationName);
               return;
            case 1:
               this.pcStateManager.providedStringField(this, var1, this.cn);
               return;
            case 2:
               this.pcStateManager.providedStringField(this, var1, this.moduleName);
               return;
            case 3:
               this.pcStateManager.providedStringField(this, var1, this.resourceName);
               return;
            case 4:
               this.pcStateManager.providedStringField(this, var1, this.wlsCollectionName);
               return;
            case 5:
               this.pcStateManager.providedStringField(this, var1, this.wlsCreatorInfo);
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

   protected void pcCopyField(CredentialMap var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.applicationName = var1.applicationName;
               return;
            case 1:
               this.cn = var1.cn;
               return;
            case 2:
               this.moduleName = var1.moduleName;
               return;
            case 3:
               this.resourceName = var1.resourceName;
               return;
            case 4:
               this.wlsCollectionName = var1.wlsCollectionName;
               return;
            case 5:
               this.wlsCreatorInfo = var1.wlsCreatorInfo;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      CredentialMap var3 = (CredentialMap)var1;
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
      CredentialMapId var3 = (CredentialMapId)var2;
      int var4 = pcInheritedFieldCount;
      Reflection.set(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$CredentialMapId != null ? class$Lcom$bea$common$security$store$data$CredentialMapId : (class$Lcom$bea$common$security$store$data$CredentialMapId = class$("com.bea.common.security.store.data.CredentialMapId")), "cn", true), var1.fetchStringField(1 + var4));
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      CredentialMapId var2 = (CredentialMapId)var1;
      Reflection.set(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$CredentialMapId != null ? class$Lcom$bea$common$security$store$data$CredentialMapId : (class$Lcom$bea$common$security$store$data$CredentialMapId = class$("com.bea.common.security.store.data.CredentialMapId")), "cn", true), this.cn);
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      CredentialMapId var3 = (CredentialMapId)var2;
      var1.storeStringField(1 + pcInheritedFieldCount, (String)Reflection.get(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$CredentialMapId != null ? class$Lcom$bea$common$security$store$data$CredentialMapId : (class$Lcom$bea$common$security$store$data$CredentialMapId = class$("com.bea.common.security.store.data.CredentialMapId")), "cn", true)));
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      CredentialMapId var2 = (CredentialMapId)var1;
      this.cn = (String)Reflection.get(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$CredentialMapId != null ? class$Lcom$bea$common$security$store$data$CredentialMapId : (class$Lcom$bea$common$security$store$data$CredentialMapId = class$("com.bea.common.security.store.data.CredentialMapId")), "cn", true));
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new CredentialMapId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new CredentialMapId();
   }

   private static final String pcGetapplicationName(CredentialMap var0) {
      if (var0.pcStateManager == null) {
         return var0.applicationName;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.applicationName;
      }
   }

   private static final void pcSetapplicationName(CredentialMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.applicationName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 0, var0.applicationName, var1, 0);
      }
   }

   private static final String pcGetcn(CredentialMap var0) {
      if (var0.pcStateManager == null) {
         return var0.cn;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.cn;
      }
   }

   private static final void pcSetcn(CredentialMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.cn = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 1, var0.cn, var1, 0);
      }
   }

   private static final String pcGetmoduleName(CredentialMap var0) {
      if (var0.pcStateManager == null) {
         return var0.moduleName;
      } else {
         int var1 = pcInheritedFieldCount + 2;
         var0.pcStateManager.accessingField(var1);
         return var0.moduleName;
      }
   }

   private static final void pcSetmoduleName(CredentialMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.moduleName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 2, var0.moduleName, var1, 0);
      }
   }

   private static final String pcGetresourceName(CredentialMap var0) {
      if (var0.pcStateManager == null) {
         return var0.resourceName;
      } else {
         int var1 = pcInheritedFieldCount + 3;
         var0.pcStateManager.accessingField(var1);
         return var0.resourceName;
      }
   }

   private static final void pcSetresourceName(CredentialMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.resourceName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 3, var0.resourceName, var1, 0);
      }
   }

   private static final String pcGetwlsCollectionName(CredentialMap var0) {
      if (var0.pcStateManager == null) {
         return var0.wlsCollectionName;
      } else {
         int var1 = pcInheritedFieldCount + 4;
         var0.pcStateManager.accessingField(var1);
         return var0.wlsCollectionName;
      }
   }

   private static final void pcSetwlsCollectionName(CredentialMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.wlsCollectionName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 4, var0.wlsCollectionName, var1, 0);
      }
   }

   private static final String pcGetwlsCreatorInfo(CredentialMap var0) {
      if (var0.pcStateManager == null) {
         return var0.wlsCreatorInfo;
      } else {
         int var1 = pcInheritedFieldCount + 5;
         var0.pcStateManager.accessingField(var1);
         return var0.wlsCreatorInfo;
      }
   }

   private static final void pcSetwlsCreatorInfo(CredentialMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.wlsCreatorInfo = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 5, var0.wlsCreatorInfo, var1, 0);
      }
   }
}
