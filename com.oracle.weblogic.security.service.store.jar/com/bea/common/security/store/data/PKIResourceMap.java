package com.bea.common.security.store.data;

import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;

public class PKIResourceMap extends PKITypeScope implements PersistenceCapable {
   private String cn;
   private String resourceName;
   private String principalName;
   private String principalNameIsUser;
   private String credentialAction;
   private String keystoreAliasName;
   private String keystoreAliasPassword;
   private String wlsCreatorInfo;
   private String wlsCollectionName;
   private static int pcInheritedFieldCount = PKITypeScope.pcGetManagedFieldCount();
   private static String[] pcFieldNames;
   private static Class[] pcFieldTypes;
   private static byte[] pcFieldFlags;
   private static Class pcPCSuperclass;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$PKITypeScope;
   // $FF: synthetic field
   static Class class$Ljava$lang$String;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$PKIResourceMap;

   public PKIResourceMap() {
   }

   public PKIResourceMap(String domainName, String realmName, String typeName, String cn, String resourceName, String principalName, String principalNameIsUser, String keystoreAliasName) {
      super(domainName, realmName, typeName);
      pcSetcn(this, cn);
      pcSetresourceName(this, resourceName);
      pcSetprincipalName(this, principalName);
      pcSetprincipalNameIsUser(this, principalNameIsUser);
      pcSetkeystoreAliasName(this, keystoreAliasName);
   }

   public String toString() {
      return "cn=" + ApplicationIdUtil.encode(pcGetcn(this)) + ',' + super.toString();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof PKIResourceMap)) {
         return false;
      } else {
         PKIResourceMap o = (PKIResourceMap)other;
         return pcGetcn(this) == pcGetcn(o) || pcGetcn(this) != null && pcGetcn(this).equals(pcGetcn(o));
      }
   }

   public int hashCode() {
      return (pcGetcn(this) != null ? pcGetcn(this).hashCode() : 0) ^ super.hashCode();
   }

   public String getCn() {
      return pcGetcn(this);
   }

   public void setCn(String cn) {
      pcSetcn(this, cn);
   }

   public String getCredentialAction() {
      return pcGetcredentialAction(this);
   }

   public void setCredentialAction(String credentialAction) {
      pcSetcredentialAction(this, credentialAction);
   }

   public String getKeystoreAliasName() {
      return pcGetkeystoreAliasName(this);
   }

   public void setKeystoreAliasName(String keystoreAliasName) {
      pcSetkeystoreAliasName(this, keystoreAliasName);
   }

   public String getKeystoreAliasPassword() {
      return pcGetkeystoreAliasPassword(this);
   }

   public void setKeystoreAliasPassword(String keystoreAliasPassword) {
      pcSetkeystoreAliasPassword(this, keystoreAliasPassword);
   }

   public String getPrincipalName() {
      return pcGetprincipalName(this);
   }

   public void setPrincipalName(String principalName) {
      pcSetprincipalName(this, principalName);
   }

   public String isPrincipalNameIsUser() {
      return pcGetprincipalNameIsUser(this);
   }

   public void setPrincipalNameIsUser(String principalNameIsUser) {
      pcSetprincipalNameIsUser(this, principalNameIsUser);
   }

   public String getResourceName() {
      return pcGetresourceName(this);
   }

   public void setResourceName(String resourceName) {
      pcSetresourceName(this, resourceName);
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

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$PKITypeScope != null ? class$Lcom$bea$common$security$store$data$PKITypeScope : (class$Lcom$bea$common$security$store$data$PKITypeScope = class$("com.bea.common.security.store.data.PKITypeScope"));
      pcFieldNames = new String[]{"cn", "credentialAction", "keystoreAliasName", "keystoreAliasPassword", "principalName", "principalNameIsUser", "resourceName", "wlsCollectionName", "wlsCreatorInfo"};
      pcFieldTypes = new Class[]{class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String"))};
      pcFieldFlags = new byte[]{26, 26, 26, 26, 26, 26, 26, 26, 26};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$PKIResourceMap != null ? class$Lcom$bea$common$security$store$data$PKIResourceMap : (class$Lcom$bea$common$security$store$data$PKIResourceMap = class$("com.bea.common.security.store.data.PKIResourceMap")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "PKIResourceMap", new PKIResourceMap());
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
      this.cn = null;
      this.credentialAction = null;
      this.keystoreAliasName = null;
      this.keystoreAliasPassword = null;
      this.principalName = null;
      this.principalNameIsUser = null;
      this.resourceName = null;
      this.wlsCollectionName = null;
      this.wlsCreatorInfo = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      PKIResourceMap var4 = new PKIResourceMap();
      if (var3) {
         var4.pcClearFields();
      }

      var4.pcStateManager = var1;
      var4.pcCopyKeyFieldsFromObjectId(var2);
      return var4;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      PKIResourceMap var3 = new PKIResourceMap();
      if (var2) {
         var3.pcClearFields();
      }

      var3.pcStateManager = var1;
      return var3;
   }

   protected static int pcGetManagedFieldCount() {
      return 9 + PKITypeScope.pcGetManagedFieldCount();
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcReplaceField(var1);
      } else {
         switch (var2) {
            case 0:
               this.cn = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 1:
               this.credentialAction = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 2:
               this.keystoreAliasName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 3:
               this.keystoreAliasPassword = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 4:
               this.principalName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 5:
               this.principalNameIsUser = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 6:
               this.resourceName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 7:
               this.wlsCollectionName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 8:
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
               this.pcStateManager.providedStringField(this, var1, this.cn);
               return;
            case 1:
               this.pcStateManager.providedStringField(this, var1, this.credentialAction);
               return;
            case 2:
               this.pcStateManager.providedStringField(this, var1, this.keystoreAliasName);
               return;
            case 3:
               this.pcStateManager.providedStringField(this, var1, this.keystoreAliasPassword);
               return;
            case 4:
               this.pcStateManager.providedStringField(this, var1, this.principalName);
               return;
            case 5:
               this.pcStateManager.providedStringField(this, var1, this.principalNameIsUser);
               return;
            case 6:
               this.pcStateManager.providedStringField(this, var1, this.resourceName);
               return;
            case 7:
               this.pcStateManager.providedStringField(this, var1, this.wlsCollectionName);
               return;
            case 8:
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

   protected void pcCopyField(PKIResourceMap var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.cn = var1.cn;
               return;
            case 1:
               this.credentialAction = var1.credentialAction;
               return;
            case 2:
               this.keystoreAliasName = var1.keystoreAliasName;
               return;
            case 3:
               this.keystoreAliasPassword = var1.keystoreAliasPassword;
               return;
            case 4:
               this.principalName = var1.principalName;
               return;
            case 5:
               this.principalNameIsUser = var1.principalNameIsUser;
               return;
            case 6:
               this.resourceName = var1.resourceName;
               return;
            case 7:
               this.wlsCollectionName = var1.wlsCollectionName;
               return;
            case 8:
               this.wlsCreatorInfo = var1.wlsCreatorInfo;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      PKIResourceMap var3 = (PKIResourceMap)var1;
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
      PKIResourceMapId var3 = (PKIResourceMapId)var2;
      int var4 = pcInheritedFieldCount;
      var3.cn = var1.fetchStringField(0 + var4);
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      PKIResourceMapId var2 = (PKIResourceMapId)var1;
      var2.cn = this.cn;
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      PKIResourceMapId var3 = (PKIResourceMapId)var2;
      var1.storeStringField(0 + pcInheritedFieldCount, var3.cn);
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      PKIResourceMapId var2 = (PKIResourceMapId)var1;
      this.cn = var2.cn;
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new PKIResourceMapId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new PKIResourceMapId();
   }

   private static final String pcGetcn(PKIResourceMap var0) {
      if (var0.pcStateManager == null) {
         return var0.cn;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.cn;
      }
   }

   private static final void pcSetcn(PKIResourceMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.cn = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 0, var0.cn, var1, 0);
      }
   }

   private static final String pcGetcredentialAction(PKIResourceMap var0) {
      if (var0.pcStateManager == null) {
         return var0.credentialAction;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.credentialAction;
      }
   }

   private static final void pcSetcredentialAction(PKIResourceMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.credentialAction = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 1, var0.credentialAction, var1, 0);
      }
   }

   private static final String pcGetkeystoreAliasName(PKIResourceMap var0) {
      if (var0.pcStateManager == null) {
         return var0.keystoreAliasName;
      } else {
         int var1 = pcInheritedFieldCount + 2;
         var0.pcStateManager.accessingField(var1);
         return var0.keystoreAliasName;
      }
   }

   private static final void pcSetkeystoreAliasName(PKIResourceMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.keystoreAliasName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 2, var0.keystoreAliasName, var1, 0);
      }
   }

   private static final String pcGetkeystoreAliasPassword(PKIResourceMap var0) {
      if (var0.pcStateManager == null) {
         return var0.keystoreAliasPassword;
      } else {
         int var1 = pcInheritedFieldCount + 3;
         var0.pcStateManager.accessingField(var1);
         return var0.keystoreAliasPassword;
      }
   }

   private static final void pcSetkeystoreAliasPassword(PKIResourceMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.keystoreAliasPassword = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 3, var0.keystoreAliasPassword, var1, 0);
      }
   }

   private static final String pcGetprincipalName(PKIResourceMap var0) {
      if (var0.pcStateManager == null) {
         return var0.principalName;
      } else {
         int var1 = pcInheritedFieldCount + 4;
         var0.pcStateManager.accessingField(var1);
         return var0.principalName;
      }
   }

   private static final void pcSetprincipalName(PKIResourceMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.principalName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 4, var0.principalName, var1, 0);
      }
   }

   private static final String pcGetprincipalNameIsUser(PKIResourceMap var0) {
      if (var0.pcStateManager == null) {
         return var0.principalNameIsUser;
      } else {
         int var1 = pcInheritedFieldCount + 5;
         var0.pcStateManager.accessingField(var1);
         return var0.principalNameIsUser;
      }
   }

   private static final void pcSetprincipalNameIsUser(PKIResourceMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.principalNameIsUser = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 5, var0.principalNameIsUser, var1, 0);
      }
   }

   private static final String pcGetresourceName(PKIResourceMap var0) {
      if (var0.pcStateManager == null) {
         return var0.resourceName;
      } else {
         int var1 = pcInheritedFieldCount + 6;
         var0.pcStateManager.accessingField(var1);
         return var0.resourceName;
      }
   }

   private static final void pcSetresourceName(PKIResourceMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.resourceName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 6, var0.resourceName, var1, 0);
      }
   }

   private static final String pcGetwlsCollectionName(PKIResourceMap var0) {
      if (var0.pcStateManager == null) {
         return var0.wlsCollectionName;
      } else {
         int var1 = pcInheritedFieldCount + 7;
         var0.pcStateManager.accessingField(var1);
         return var0.wlsCollectionName;
      }
   }

   private static final void pcSetwlsCollectionName(PKIResourceMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.wlsCollectionName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 7, var0.wlsCollectionName, var1, 0);
      }
   }

   private static final String pcGetwlsCreatorInfo(PKIResourceMap var0) {
      if (var0.pcStateManager == null) {
         return var0.wlsCreatorInfo;
      } else {
         int var1 = pcInheritedFieldCount + 8;
         var0.pcStateManager.accessingField(var1);
         return var0.wlsCreatorInfo;
      }
   }

   private static final void pcSetwlsCreatorInfo(PKIResourceMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.wlsCreatorInfo = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 8, var0.wlsCreatorInfo, var1, 0);
      }
   }
}
