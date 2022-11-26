package com.bea.common.security.store.data;

import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;

public class WLSCertRegEntry extends RegistryScope implements PersistenceCapable {
   private String cn;
   private String wlsCertRegSubjectDN;
   private String wlsCertRegIssuerDN;
   private String wlsCertRegSerialNumber;
   private String wlsCertRegSubjectKeyIdentifier;
   private byte[] userCertificate;
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
   static Class class$L$B;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$WLSCertRegEntry;

   public WLSCertRegEntry() {
   }

   public WLSCertRegEntry(String domainName, String realmName, String registryName, String cn, String wlsCertRegSubjectDN, String wlsCertRegIssuerDN, String wlsCertRegSerialNumber, byte[] userCertificate) {
      super(domainName, realmName, registryName);
      pcSetcn(this, cn);
      pcSetwlsCertRegSubjectDN(this, wlsCertRegSubjectDN);
      pcSetwlsCertRegIssuerDN(this, wlsCertRegIssuerDN);
      pcSetwlsCertRegSerialNumber(this, wlsCertRegSerialNumber);
      pcSetuserCertificate(this, userCertificate);
   }

   public String toString() {
      return "cn=" + ApplicationIdUtil.encode(pcGetcn(this)) + ',' + super.toString();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof WLSCertRegEntry)) {
         return false;
      } else {
         WLSCertRegEntry o = (WLSCertRegEntry)other;
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

   public byte[] getUserCertificate() {
      return pcGetuserCertificate(this);
   }

   public void setUserCertificate(byte[] userCertificate) {
      pcSetuserCertificate(this, userCertificate);
   }

   public String getWlsCertRegIssuerDN() {
      return pcGetwlsCertRegIssuerDN(this);
   }

   public void setWlsCertRegIssuerDN(String wlsCertRegIssuerDN) {
      pcSetwlsCertRegIssuerDN(this, wlsCertRegIssuerDN);
   }

   public String getWlsCertRegSerialNumber() {
      return pcGetwlsCertRegSerialNumber(this);
   }

   public void setWlsCertRegSerialNumber(String wlsCertRegSerialNumber) {
      pcSetwlsCertRegSerialNumber(this, wlsCertRegSerialNumber);
   }

   public String getWlsCertRegSubjectDN() {
      return pcGetwlsCertRegSubjectDN(this);
   }

   public void setWlsCertRegSubjectDN(String wlsCertRegSubjectDN) {
      pcSetwlsCertRegSubjectDN(this, wlsCertRegSubjectDN);
   }

   public String getWlsCertRegSubjectKeyIdentifier() {
      return pcGetwlsCertRegSubjectKeyIdentifier(this);
   }

   public void setWlsCertRegSubjectKeyIdentifier(String wlsCertRegSubjectKeyIdentifier) {
      pcSetwlsCertRegSubjectKeyIdentifier(this, wlsCertRegSubjectKeyIdentifier);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$RegistryScope != null ? class$Lcom$bea$common$security$store$data$RegistryScope : (class$Lcom$bea$common$security$store$data$RegistryScope = class$("com.bea.common.security.store.data.RegistryScope"));
      pcFieldNames = new String[]{"cn", "userCertificate", "wlsCertRegIssuerDN", "wlsCertRegSerialNumber", "wlsCertRegSubjectDN", "wlsCertRegSubjectKeyIdentifier"};
      pcFieldTypes = new Class[]{class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$L$B != null ? class$L$B : (class$L$B = class$("[B")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String"))};
      pcFieldFlags = new byte[]{26, 26, 26, 26, 26, 26};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$WLSCertRegEntry != null ? class$Lcom$bea$common$security$store$data$WLSCertRegEntry : (class$Lcom$bea$common$security$store$data$WLSCertRegEntry = class$("com.bea.common.security.store.data.WLSCertRegEntry")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "WLSCertRegEntry", new WLSCertRegEntry());
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
      this.userCertificate = null;
      this.wlsCertRegIssuerDN = null;
      this.wlsCertRegSerialNumber = null;
      this.wlsCertRegSubjectDN = null;
      this.wlsCertRegSubjectKeyIdentifier = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      WLSCertRegEntry var4 = new WLSCertRegEntry();
      if (var3) {
         var4.pcClearFields();
      }

      var4.pcStateManager = var1;
      var4.pcCopyKeyFieldsFromObjectId(var2);
      return var4;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      WLSCertRegEntry var3 = new WLSCertRegEntry();
      if (var2) {
         var3.pcClearFields();
      }

      var3.pcStateManager = var1;
      return var3;
   }

   protected static int pcGetManagedFieldCount() {
      return 6 + RegistryScope.pcGetManagedFieldCount();
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
               this.userCertificate = (byte[])this.pcStateManager.replaceObjectField(this, var1);
               return;
            case 2:
               this.wlsCertRegIssuerDN = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 3:
               this.wlsCertRegSerialNumber = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 4:
               this.wlsCertRegSubjectDN = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 5:
               this.wlsCertRegSubjectKeyIdentifier = (String)this.pcStateManager.replaceStringField(this, var1);
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
               this.pcStateManager.providedObjectField(this, var1, this.userCertificate);
               return;
            case 2:
               this.pcStateManager.providedStringField(this, var1, this.wlsCertRegIssuerDN);
               return;
            case 3:
               this.pcStateManager.providedStringField(this, var1, this.wlsCertRegSerialNumber);
               return;
            case 4:
               this.pcStateManager.providedStringField(this, var1, this.wlsCertRegSubjectDN);
               return;
            case 5:
               this.pcStateManager.providedStringField(this, var1, this.wlsCertRegSubjectKeyIdentifier);
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

   protected void pcCopyField(WLSCertRegEntry var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.cn = var1.cn;
               return;
            case 1:
               this.userCertificate = var1.userCertificate;
               return;
            case 2:
               this.wlsCertRegIssuerDN = var1.wlsCertRegIssuerDN;
               return;
            case 3:
               this.wlsCertRegSerialNumber = var1.wlsCertRegSerialNumber;
               return;
            case 4:
               this.wlsCertRegSubjectDN = var1.wlsCertRegSubjectDN;
               return;
            case 5:
               this.wlsCertRegSubjectKeyIdentifier = var1.wlsCertRegSubjectKeyIdentifier;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      WLSCertRegEntry var3 = (WLSCertRegEntry)var1;
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
      WLSCertRegEntryId var3 = (WLSCertRegEntryId)var2;
      int var4 = pcInheritedFieldCount;
      var3.cn = var1.fetchStringField(0 + var4);
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      WLSCertRegEntryId var2 = (WLSCertRegEntryId)var1;
      var2.cn = this.cn;
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      WLSCertRegEntryId var3 = (WLSCertRegEntryId)var2;
      var1.storeStringField(0 + pcInheritedFieldCount, var3.cn);
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      WLSCertRegEntryId var2 = (WLSCertRegEntryId)var1;
      this.cn = var2.cn;
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new WLSCertRegEntryId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new WLSCertRegEntryId();
   }

   private static final String pcGetcn(WLSCertRegEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.cn;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.cn;
      }
   }

   private static final void pcSetcn(WLSCertRegEntry var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.cn = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 0, var0.cn, var1, 0);
      }
   }

   private static final byte[] pcGetuserCertificate(WLSCertRegEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.userCertificate;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.userCertificate;
      }
   }

   private static final void pcSetuserCertificate(WLSCertRegEntry var0, byte[] var1) {
      if (var0.pcStateManager == null) {
         var0.userCertificate = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 1, var0.userCertificate, var1, 0);
      }
   }

   private static final String pcGetwlsCertRegIssuerDN(WLSCertRegEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.wlsCertRegIssuerDN;
      } else {
         int var1 = pcInheritedFieldCount + 2;
         var0.pcStateManager.accessingField(var1);
         return var0.wlsCertRegIssuerDN;
      }
   }

   private static final void pcSetwlsCertRegIssuerDN(WLSCertRegEntry var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.wlsCertRegIssuerDN = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 2, var0.wlsCertRegIssuerDN, var1, 0);
      }
   }

   private static final String pcGetwlsCertRegSerialNumber(WLSCertRegEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.wlsCertRegSerialNumber;
      } else {
         int var1 = pcInheritedFieldCount + 3;
         var0.pcStateManager.accessingField(var1);
         return var0.wlsCertRegSerialNumber;
      }
   }

   private static final void pcSetwlsCertRegSerialNumber(WLSCertRegEntry var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.wlsCertRegSerialNumber = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 3, var0.wlsCertRegSerialNumber, var1, 0);
      }
   }

   private static final String pcGetwlsCertRegSubjectDN(WLSCertRegEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.wlsCertRegSubjectDN;
      } else {
         int var1 = pcInheritedFieldCount + 4;
         var0.pcStateManager.accessingField(var1);
         return var0.wlsCertRegSubjectDN;
      }
   }

   private static final void pcSetwlsCertRegSubjectDN(WLSCertRegEntry var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.wlsCertRegSubjectDN = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 4, var0.wlsCertRegSubjectDN, var1, 0);
      }
   }

   private static final String pcGetwlsCertRegSubjectKeyIdentifier(WLSCertRegEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.wlsCertRegSubjectKeyIdentifier;
      } else {
         int var1 = pcInheritedFieldCount + 5;
         var0.pcStateManager.accessingField(var1);
         return var0.wlsCertRegSubjectKeyIdentifier;
      }
   }

   private static final void pcSetwlsCertRegSubjectKeyIdentifier(WLSCertRegEntry var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.wlsCertRegSubjectKeyIdentifier = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 5, var0.wlsCertRegSubjectKeyIdentifier, var1, 0);
      }
   }
}
