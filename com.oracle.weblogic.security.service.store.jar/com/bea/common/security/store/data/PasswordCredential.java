package com.bea.common.security.store.data;

import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;

public class PasswordCredential extends Credential implements PersistenceCapable {
   private byte[] principalPassword;
   private static int pcInheritedFieldCount = Credential.pcGetManagedFieldCount();
   private static String[] pcFieldNames;
   private static Class[] pcFieldTypes;
   private static byte[] pcFieldFlags;
   private static Class pcPCSuperclass;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$Credential;
   // $FF: synthetic field
   static Class class$L$B;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$PasswordCredential;

   public PasswordCredential() {
   }

   public PasswordCredential(String domainName, String realmName, String credentialName, byte[] principalPassword) {
      super(domainName, realmName, credentialName);
      pcSetprincipalPassword(this, principalPassword);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof PasswordCredential) ? false : super.equals(other);
      }
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
      pcFieldNames = new String[]{"principalPassword"};
      pcFieldTypes = new Class[]{class$L$B != null ? class$L$B : (class$L$B = class$("[B"))};
      pcFieldFlags = new byte[]{21};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$PasswordCredential != null ? class$Lcom$bea$common$security$store$data$PasswordCredential : (class$Lcom$bea$common$security$store$data$PasswordCredential = class$("com.bea.common.security.store.data.PasswordCredential")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "PasswordCredential", new PasswordCredential());
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
      this.principalPassword = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      PasswordCredential var4 = new PasswordCredential();
      if (var3) {
         var4.pcClearFields();
      }

      var4.pcStateManager = var1;
      var4.pcCopyKeyFieldsFromObjectId(var2);
      return var4;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      PasswordCredential var3 = new PasswordCredential();
      if (var2) {
         var3.pcClearFields();
      }

      var3.pcStateManager = var1;
      return var3;
   }

   protected static int pcGetManagedFieldCount() {
      return 1 + Credential.pcGetManagedFieldCount();
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcReplaceField(var1);
      } else {
         switch (var2) {
            case 0:
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

   protected void pcCopyField(PasswordCredential var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.principalPassword = var1.principalPassword;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      PasswordCredential var3 = (PasswordCredential)var1;
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
      PasswordCredentialId var3 = (PasswordCredentialId)var2;
      int var4 = pcInheritedFieldCount;
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      PasswordCredentialId var2 = (PasswordCredentialId)var1;
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      PasswordCredentialId var3 = (PasswordCredentialId)var2;
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      PasswordCredentialId var2 = (PasswordCredentialId)var1;
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new PasswordCredentialId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new PasswordCredentialId();
   }

   private static final byte[] pcGetprincipalPassword(PasswordCredential var0) {
      if (var0.pcStateManager == null) {
         return var0.principalPassword;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.principalPassword;
      }
   }

   private static final void pcSetprincipalPassword(PasswordCredential var0, byte[] var1) {
      if (var0.pcStateManager == null) {
         var0.principalPassword = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 0, var0.principalPassword, var1, 0);
      }
   }
}
