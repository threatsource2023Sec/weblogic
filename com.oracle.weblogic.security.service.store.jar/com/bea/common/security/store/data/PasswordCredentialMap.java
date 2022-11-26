package com.bea.common.security.store.data;

import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;

public class PasswordCredentialMap extends CredentialMap implements PersistenceCapable {
   private String principalName;
   private String principalPassword;
   private static int pcInheritedFieldCount = CredentialMap.pcGetManagedFieldCount();
   private static String[] pcFieldNames;
   private static Class[] pcFieldTypes;
   private static byte[] pcFieldFlags;
   private static Class pcPCSuperclass;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$CredentialMap;
   // $FF: synthetic field
   static Class class$Ljava$lang$String;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$PasswordCredentialMap;

   public PasswordCredentialMap() {
   }

   public PasswordCredentialMap(String domainName, String realmName, String cn, String principalName, String principalPassword) {
      super(domainName, realmName, cn);
      pcSetprincipalName(this, principalName);
      pcSetprincipalPassword(this, principalPassword);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof PasswordCredentialMap) ? false : super.equals(other);
      }
   }

   public String getPrincipalName() {
      return pcGetprincipalName(this);
   }

   public void setPrincipalName(String principalName) {
      pcSetprincipalName(this, principalName);
   }

   public String getPrincipalPassword() {
      return pcGetprincipalPassword(this);
   }

   public void setPrincipalPassword(String principalPassword) {
      pcSetprincipalPassword(this, principalPassword);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$CredentialMap != null ? class$Lcom$bea$common$security$store$data$CredentialMap : (class$Lcom$bea$common$security$store$data$CredentialMap = class$("com.bea.common.security.store.data.CredentialMap"));
      pcFieldNames = new String[]{"principalName", "principalPassword"};
      pcFieldTypes = new Class[]{class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String"))};
      pcFieldFlags = new byte[]{26, 26};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$PasswordCredentialMap != null ? class$Lcom$bea$common$security$store$data$PasswordCredentialMap : (class$Lcom$bea$common$security$store$data$PasswordCredentialMap = class$("com.bea.common.security.store.data.PasswordCredentialMap")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "PasswordCredentialMap", new PasswordCredentialMap());
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
      this.principalName = null;
      this.principalPassword = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      PasswordCredentialMap var4 = new PasswordCredentialMap();
      if (var3) {
         var4.pcClearFields();
      }

      var4.pcStateManager = var1;
      var4.pcCopyKeyFieldsFromObjectId(var2);
      return var4;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      PasswordCredentialMap var3 = new PasswordCredentialMap();
      if (var2) {
         var3.pcClearFields();
      }

      var3.pcStateManager = var1;
      return var3;
   }

   protected static int pcGetManagedFieldCount() {
      return 2 + CredentialMap.pcGetManagedFieldCount();
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcReplaceField(var1);
      } else {
         switch (var2) {
            case 0:
               this.principalName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 1:
               this.principalPassword = (String)this.pcStateManager.replaceStringField(this, var1);
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
               this.pcStateManager.providedStringField(this, var1, this.principalName);
               return;
            case 1:
               this.pcStateManager.providedStringField(this, var1, this.principalPassword);
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

   protected void pcCopyField(PasswordCredentialMap var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.principalName = var1.principalName;
               return;
            case 1:
               this.principalPassword = var1.principalPassword;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      PasswordCredentialMap var3 = (PasswordCredentialMap)var1;
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
      PasswordCredentialMapId var3 = (PasswordCredentialMapId)var2;
      int var4 = pcInheritedFieldCount;
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      PasswordCredentialMapId var2 = (PasswordCredentialMapId)var1;
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      PasswordCredentialMapId var3 = (PasswordCredentialMapId)var2;
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      PasswordCredentialMapId var2 = (PasswordCredentialMapId)var1;
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new PasswordCredentialMapId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new PasswordCredentialMapId();
   }

   private static final String pcGetprincipalName(PasswordCredentialMap var0) {
      if (var0.pcStateManager == null) {
         return var0.principalName;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.principalName;
      }
   }

   private static final void pcSetprincipalName(PasswordCredentialMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.principalName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 0, var0.principalName, var1, 0);
      }
   }

   private static final String pcGetprincipalPassword(PasswordCredentialMap var0) {
      if (var0.pcStateManager == null) {
         return var0.principalPassword;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.principalPassword;
      }
   }

   private static final void pcSetprincipalPassword(PasswordCredentialMap var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.principalPassword = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 1, var0.principalPassword, var1, 0);
      }
   }
}
