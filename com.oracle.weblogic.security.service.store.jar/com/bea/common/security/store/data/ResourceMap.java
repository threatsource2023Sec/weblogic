package com.bea.common.security.store.data;

import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;

public class ResourceMap extends DomainRealmScope implements PersistenceCapable {
   private String cn;
   private String principalName;
   private String resourceName;
   private String applicationName;
   private String moduleName;
   private PasswordCredentialMap credentialMapRef;
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
   static Class class$Lcom$bea$common$security$store$data$PasswordCredentialMap;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$ResourceMap;

   public ResourceMap() {
   }

   public ResourceMap(String domainName, String realmName, String cn, String principalName, PasswordCredentialMap credentialMapRef) {
      super(domainName, realmName);
      pcSetcn(this, cn);
      pcSetprincipalName(this, principalName);
      pcSetcredentialMapRef(this, credentialMapRef);
   }

   public String toString() {
      return "cn=" + ApplicationIdUtil.encode(pcGetcn(this)) + ',' + super.toString();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof ResourceMap)) {
         return false;
      } else {
         ResourceMap o = (ResourceMap)other;
         return pcGetcn(this) == pcGetcn(o) || pcGetcn(this) != null && pcGetcn(this).equals(pcGetcn(o));
      }
   }

   public int hashCode() {
      return (pcGetcn(this) != null ? pcGetcn(this).hashCode() : 0) ^ super.hashCode();
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

   public PasswordCredentialMap getCredentialMapRef() {
      return pcGetcredentialMapRef(this);
   }

   public void setCredentialMapRef(PasswordCredentialMap credentialMapRef) {
      pcSetcredentialMapRef(this, credentialMapRef);
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

   public String getPrincipalName() {
      return pcGetprincipalName(this);
   }

   public void setPrincipalName(String principalName) {
      pcSetprincipalName(this, principalName);
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
      pcFieldNames = new String[]{"applicationName", "cn", "credentialMapRef", "moduleName", "principalName", "resourceName", "wlsCollectionName", "wlsCreatorInfo"};
      pcFieldTypes = new Class[]{class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Lcom$bea$common$security$store$data$PasswordCredentialMap != null ? class$Lcom$bea$common$security$store$data$PasswordCredentialMap : (class$Lcom$bea$common$security$store$data$PasswordCredentialMap = class$("com.bea.common.security.store.data.PasswordCredentialMap")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String"))};
      pcFieldFlags = new byte[]{26, 26, 10, 26, 26, 26, 26, 26};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$ResourceMap != null ? class$Lcom$bea$common$security$store$data$ResourceMap : (class$Lcom$bea$common$security$store$data$ResourceMap = class$("com.bea.common.security.store.data.ResourceMap")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "ResourceMap", new ResourceMap());
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
      this.credentialMapRef = null;
      this.moduleName = null;
      this.principalName = null;
      this.resourceName = null;
      this.wlsCollectionName = null;
      this.wlsCreatorInfo = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      ResourceMap var4 = new ResourceMap();
      if (var3) {
         var4.pcClearFields();
      }

      var4.pcStateManager = var1;
      var4.pcCopyKeyFieldsFromObjectId(var2);
      return var4;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      ResourceMap var3 = new ResourceMap();
      if (var2) {
         var3.pcClearFields();
      }

      var3.pcStateManager = var1;
      return var3;
   }

   protected static int pcGetManagedFieldCount() {
      return 8 + DomainRealmScope.pcGetManagedFieldCount();
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
               this.credentialMapRef = (PasswordCredentialMap)this.pcStateManager.replaceObjectField(this, var1);
               return;
            case 3:
               this.moduleName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 4:
               this.principalName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 5:
               this.resourceName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 6:
               this.wlsCollectionName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 7:
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
               this.pcStateManager.providedObjectField(this, var1, this.credentialMapRef);
               return;
            case 3:
               this.pcStateManager.providedStringField(this, var1, this.moduleName);
               return;
            case 4:
               this.pcStateManager.providedStringField(this, var1, this.principalName);
               return;
            case 5:
               this.pcStateManager.providedStringField(this, var1, this.resourceName);
               return;
            case 6:
               this.pcStateManager.providedStringField(this, var1, this.wlsCollectionName);
               return;
            case 7:
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

   protected void pcCopyField(ResourceMap var1, int var2) {
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
               this.credentialMapRef = var1.credentialMapRef;
               return;
            case 3:
               this.moduleName = var1.moduleName;
               return;
            case 4:
               this.principalName = var1.principalName;
               return;
            case 5:
               this.resourceName = var1.resourceName;
               return;
            case 6:
               this.wlsCollectionName = var1.wlsCollectionName;
               return;
            case 7:
               this.wlsCreatorInfo = var1.wlsCreatorInfo;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      ResourceMap var3 = (ResourceMap)var1;
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
      ResourceMapId var3 = (ResourceMapId)var2;
      int var4 = pcInheritedFieldCount;
      var3.cn = var1.fetchStringField(1 + var4);
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      ResourceMapId var2 = (ResourceMapId)var1;
      var2.cn = this.cn;
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      ResourceMapId var3 = (ResourceMapId)var2;
      var1.storeStringField(1 + pcInheritedFieldCount, var3.cn);
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      ResourceMapId var2 = (ResourceMapId)var1;
      this.cn = var2.cn;
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new ResourceMapId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new ResourceMapId();
   }

   private static final String pcGetapplicationName(ResourceMap var0) {
      if (var0.pcStateManager == null) {
         return var0.applicationName;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.applicationName;
      }
   }

   private static final void pcSetapplicationName(ResourceMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.applicationName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 0, var0.applicationName, var1, 0);
      }
   }

   private static final String pcGetcn(ResourceMap var0) {
      if (var0.pcStateManager == null) {
         return var0.cn;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.cn;
      }
   }

   private static final void pcSetcn(ResourceMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.cn = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 1, var0.cn, var1, 0);
      }
   }

   private static final PasswordCredentialMap pcGetcredentialMapRef(ResourceMap var0) {
      if (var0.pcStateManager == null) {
         return var0.credentialMapRef;
      } else {
         int var1 = pcInheritedFieldCount + 2;
         var0.pcStateManager.accessingField(var1);
         return var0.credentialMapRef;
      }
   }

   private static final void pcSetcredentialMapRef(ResourceMap var0, PasswordCredentialMap var1) {
      if (var0.pcStateManager == null) {
         var0.credentialMapRef = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 2, var0.credentialMapRef, var1, 0);
      }
   }

   private static final String pcGetmoduleName(ResourceMap var0) {
      if (var0.pcStateManager == null) {
         return var0.moduleName;
      } else {
         int var1 = pcInheritedFieldCount + 3;
         var0.pcStateManager.accessingField(var1);
         return var0.moduleName;
      }
   }

   private static final void pcSetmoduleName(ResourceMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.moduleName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 3, var0.moduleName, var1, 0);
      }
   }

   private static final String pcGetprincipalName(ResourceMap var0) {
      if (var0.pcStateManager == null) {
         return var0.principalName;
      } else {
         int var1 = pcInheritedFieldCount + 4;
         var0.pcStateManager.accessingField(var1);
         return var0.principalName;
      }
   }

   private static final void pcSetprincipalName(ResourceMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.principalName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 4, var0.principalName, var1, 0);
      }
   }

   private static final String pcGetresourceName(ResourceMap var0) {
      if (var0.pcStateManager == null) {
         return var0.resourceName;
      } else {
         int var1 = pcInheritedFieldCount + 5;
         var0.pcStateManager.accessingField(var1);
         return var0.resourceName;
      }
   }

   private static final void pcSetresourceName(ResourceMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.resourceName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 5, var0.resourceName, var1, 0);
      }
   }

   private static final String pcGetwlsCollectionName(ResourceMap var0) {
      if (var0.pcStateManager == null) {
         return var0.wlsCollectionName;
      } else {
         int var1 = pcInheritedFieldCount + 6;
         var0.pcStateManager.accessingField(var1);
         return var0.wlsCollectionName;
      }
   }

   private static final void pcSetwlsCollectionName(ResourceMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.wlsCollectionName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 6, var0.wlsCollectionName, var1, 0);
      }
   }

   private static final String pcGetwlsCreatorInfo(ResourceMap var0) {
      if (var0.pcStateManager == null) {
         return var0.wlsCreatorInfo;
      } else {
         int var1 = pcInheritedFieldCount + 7;
         var0.pcStateManager.accessingField(var1);
         return var0.wlsCreatorInfo;
      }
   }

   private static final void pcSetwlsCreatorInfo(ResourceMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.wlsCreatorInfo = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 7, var0.wlsCreatorInfo, var1, 0);
      }
   }
}
