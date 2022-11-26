package com.bea.common.security.store.data;

import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.Reflection;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.util.UserException;

public abstract class BEASAMLPartner extends RegistryScope implements PersistenceCapable {
   private String cn;
   private String beaSAMLPartnerEnabled;
   private String beaSAMLPartnerDescription;
   private static int pcInheritedFieldCount = RegistryScope.pcGetManagedFieldCount();
   private static String[] pcFieldNames;
   private static Class[] pcFieldTypes;
   private static byte[] pcFieldFlags;
   private static Class pcPCSuperclass;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$RegistryScope;
   // $FF: synthetic field
   static Class class$Ljava$lang$String;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$BEASAMLPartner;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$BEASAMLPartnerId;

   public BEASAMLPartner() {
   }

   public BEASAMLPartner(String domainName, String realmName, String registryName, String cn, String beaSAMLPartnerEnabled) {
      super(domainName, realmName, registryName);
      pcSetcn(this, cn);
      pcSetbeaSAMLPartnerEnabled(this, beaSAMLPartnerEnabled);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof BEASAMLPartner)) {
         return false;
      } else {
         BEASAMLPartner o = (BEASAMLPartner)other;
         return pcGetcn(this) == pcGetcn(o) || pcGetcn(this) != null && pcGetcn(this).equals(pcGetcn(o));
      }
   }

   public int hashCode() {
      return (pcGetcn(this) != null ? pcGetcn(this).hashCode() : 0) ^ super.hashCode();
   }

   public String toString() {
      return "cn=" + ApplicationIdUtil.encode(pcGetcn(this)) + ',' + super.toString();
   }

   public String getBeaSAMLPartnerDescription() {
      return pcGetbeaSAMLPartnerDescription(this);
   }

   public void setBeaSAMLPartnerDescription(String beaSAMLPartnerDescription) {
      pcSetbeaSAMLPartnerDescription(this, beaSAMLPartnerDescription);
   }

   public String getBeaSAMLPartnerEnabled() {
      return pcGetbeaSAMLPartnerEnabled(this);
   }

   public void setBeaSAMLPartnerEnabled(String beaSAMLPartnerEnabled) {
      pcSetbeaSAMLPartnerEnabled(this, beaSAMLPartnerEnabled);
   }

   public String getCn() {
      return pcGetcn(this);
   }

   public void setCn(String cn) {
      pcSetcn(this, cn);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$RegistryScope != null ? class$Lcom$bea$common$security$store$data$RegistryScope : (class$Lcom$bea$common$security$store$data$RegistryScope = class$("com.bea.common.security.store.data.RegistryScope"));
      pcFieldNames = new String[]{"beaSAMLPartnerDescription", "beaSAMLPartnerEnabled", "cn"};
      pcFieldTypes = new Class[]{class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String"))};
      pcFieldFlags = new byte[]{26, 26, 26};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$BEASAMLPartner != null ? class$Lcom$bea$common$security$store$data$BEASAMLPartner : (class$Lcom$bea$common$security$store$data$BEASAMLPartner = class$("com.bea.common.security.store.data.BEASAMLPartner")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "BEASAMLPartner", (PersistenceCapable)null);
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
      this.beaSAMLPartnerDescription = null;
      this.beaSAMLPartnerEnabled = null;
      this.cn = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      throw new UserException();
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      throw new UserException();
   }

   protected static int pcGetManagedFieldCount() {
      return 3 + RegistryScope.pcGetManagedFieldCount();
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcReplaceField(var1);
      } else {
         switch (var2) {
            case 0:
               this.beaSAMLPartnerDescription = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 1:
               this.beaSAMLPartnerEnabled = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 2:
               this.cn = (String)this.pcStateManager.replaceStringField(this, var1);
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
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLPartnerDescription);
               return;
            case 1:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLPartnerEnabled);
               return;
            case 2:
               this.pcStateManager.providedStringField(this, var1, this.cn);
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

   protected void pcCopyField(BEASAMLPartner var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.beaSAMLPartnerDescription = var1.beaSAMLPartnerDescription;
               return;
            case 1:
               this.beaSAMLPartnerEnabled = var1.beaSAMLPartnerEnabled;
               return;
            case 2:
               this.cn = var1.cn;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      BEASAMLPartner var3 = (BEASAMLPartner)var1;
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
      BEASAMLPartnerId var3 = (BEASAMLPartnerId)var2;
      int var4 = pcInheritedFieldCount;
      Reflection.set(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$BEASAMLPartnerId != null ? class$Lcom$bea$common$security$store$data$BEASAMLPartnerId : (class$Lcom$bea$common$security$store$data$BEASAMLPartnerId = class$("com.bea.common.security.store.data.BEASAMLPartnerId")), "cn", true), var1.fetchStringField(2 + var4));
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      BEASAMLPartnerId var2 = (BEASAMLPartnerId)var1;
      Reflection.set(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$BEASAMLPartnerId != null ? class$Lcom$bea$common$security$store$data$BEASAMLPartnerId : (class$Lcom$bea$common$security$store$data$BEASAMLPartnerId = class$("com.bea.common.security.store.data.BEASAMLPartnerId")), "cn", true), this.cn);
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      BEASAMLPartnerId var3 = (BEASAMLPartnerId)var2;
      var1.storeStringField(2 + pcInheritedFieldCount, (String)Reflection.get(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$BEASAMLPartnerId != null ? class$Lcom$bea$common$security$store$data$BEASAMLPartnerId : (class$Lcom$bea$common$security$store$data$BEASAMLPartnerId = class$("com.bea.common.security.store.data.BEASAMLPartnerId")), "cn", true)));
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      BEASAMLPartnerId var2 = (BEASAMLPartnerId)var1;
      this.cn = (String)Reflection.get(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$BEASAMLPartnerId != null ? class$Lcom$bea$common$security$store$data$BEASAMLPartnerId : (class$Lcom$bea$common$security$store$data$BEASAMLPartnerId = class$("com.bea.common.security.store.data.BEASAMLPartnerId")), "cn", true));
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new BEASAMLPartnerId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new BEASAMLPartnerId();
   }

   private static final String pcGetbeaSAMLPartnerDescription(BEASAMLPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLPartnerDescription;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLPartnerDescription;
      }
   }

   private static final void pcSetbeaSAMLPartnerDescription(BEASAMLPartner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLPartnerDescription = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 0, var0.beaSAMLPartnerDescription, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLPartnerEnabled(BEASAMLPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLPartnerEnabled;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLPartnerEnabled;
      }
   }

   private static final void pcSetbeaSAMLPartnerEnabled(BEASAMLPartner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLPartnerEnabled = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 1, var0.beaSAMLPartnerEnabled, var1, 0);
      }
   }

   private static final String pcGetcn(BEASAMLPartner var0) {
      if (var0.pcStateManager == null) {
         return var0.cn;
      } else {
         int var1 = pcInheritedFieldCount + 2;
         var0.pcStateManager.accessingField(var1);
         return var0.cn;
      }
   }

   private static final void pcSetcn(BEASAMLPartner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.cn = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 2, var0.cn, var1, 0);
      }
   }
}
