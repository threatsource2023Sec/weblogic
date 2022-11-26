package com.bea.common.security.store.data;

import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;

public class UserPasswordCredential extends Credential implements PersistenceCapable {
   private String principalName;
   private byte[] principalPassword;
   private static int pcInheritedFieldCount = Credential.pcGetManagedFieldCount();
   private static String[] pcFieldNames;
   private static Class[] pcFieldTypes;
   private static byte[] pcFieldFlags;
   private static Class pcPCSuperclass;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$Credential;
   // $FF: synthetic field
   static Class class$Ljava$lang$String;
   // $FF: synthetic field
   static Class class$L$B;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$UserPasswordCredential;

   public UserPasswordCredential() {
   }

   public UserPasswordCredential(String domainName, String realmName, String credentialName, String principalName, byte[] principalPassword) {
      super(domainName, realmName, credentialName);
      pcSetprincipalName(this, principalName);
      pcSetprincipalPassword(this, principalPassword);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof UserPasswordCredential) ? false : super.equals(other);
      }
   }

   public String getPrincipalName() {
      return pcGetprincipalName(this);
   }

   public void setPrincipalName(String principalName) {
      pcSetprincipalName(this, principalName);
   }

   public byte[] getPrincipalPassword() {
      return pcGetprincipalPassword(this);
   }

   public void setPrincipalPassword(byte[] principalPassword) {
      pcSetprincipalPassword(this, principalPassword);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$Credential != null ? class$Lcom$bea$common$security$store$data$Credential : (class$Lcom$bea$common$security$store$data$Credential = class$("com.bea.common.security.store.data.Credential"));
      pcFieldNames = new String[]{"principalName", "principalPassword"};
      pcFieldTypes = new Class[]{class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$L$B != null ? class$L$B : (class$L$B = class$("[B"))};
      pcFieldFlags = new byte[]{26, 21};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$UserPasswordCredential != null ? class$Lcom$bea$common$security$store$data$UserPasswordCredential : (class$Lcom$bea$common$security$store$data$UserPasswordCredential = class$("com.bea.common.security.store.data.UserPasswordCredential")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "UserPasswordCredential", new UserPasswordCredential());
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
      UserPasswordCredential var4 = new UserPasswordCredential();
      if (var3) {
         var4.pcClearFields();
      }

      var4.pcStateManager = var1;
      var4.pcCopyKeyFieldsFromObjectId(var2);
      return var4;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      UserPasswordCredential var3 = new UserPasswordCredential();
      if (var2) {
         var3.pcClearFields();
      }

      var3.pcStateManager = var1;
      return var3;
   }

   protected static int pcGetManagedFieldCount() {
      return 2 + Credential.pcGetManagedFieldCount();
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
               this.principalPassword = (byte[])this.pcStateManager.replaceObjectField(this, var1);
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
               this.pcStateManager.providedObjectField(this, var1, this.principalPassword);
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

   protected void pcCopyField(UserPasswordCredential var1, int var2) {
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
      UserPasswordCredential var3 = (UserPasswordCredential)var1;
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
      UserPasswordCredentialId var3 = (UserPasswordCredentialId)var2;
      int var4 = pcInheritedFieldCount;
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      UserPasswordCredentialId var2 = (UserPasswordCredentialId)var1;
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      UserPasswordCredentialId var3 = (UserPasswordCredentialId)var2;
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      UserPasswordCredentialId var2 = (UserPasswordCredentialId)var1;
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new UserPasswordCredentialId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new UserPasswordCredentialId();
   }

   private static final String pcGetprincipalName(UserPasswordCredential var0) {
      if (var0.pcStateManager == null) {
         return var0.principalName;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.principalName;
      }
   }

   private static final void pcSetprincipalName(UserPasswordCredential var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.principalName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 0, var0.principalName, var1, 0);
      }
   }

   private static final byte[] pcGetprincipalPassword(UserPasswordCredential var0) {
      if (var0.pcStateManager == null) {
         return var0.principalPassword;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.principalPassword;
      }
   }

   private static final void pcSetprincipalPassword(UserPasswordCredential var0, byte[] var1) {
      if (var0.pcStateManager == null) {
         var0.principalPassword = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 1, var0.principalPassword, var1, 0);
      }
   }
}
