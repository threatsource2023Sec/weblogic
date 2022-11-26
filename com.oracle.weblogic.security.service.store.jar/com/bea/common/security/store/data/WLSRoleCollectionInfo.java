package com.bea.common.security.store.data;

import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;

public class WLSRoleCollectionInfo extends DomainRealmScope implements PersistenceCapable {
   private String wlsCollectionName;
   private String wlsCollectionVersion;
   private String wlsCollectionTimestamp;
   private byte[] wlsXmlFragment;
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
   static Class class$L$B;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$WLSRoleCollectionInfo;

   public WLSRoleCollectionInfo() {
   }

   public WLSRoleCollectionInfo(String domainName, String realmName, String wlsCollectionName) {
      super(domainName, realmName);
      pcSetwlsCollectionName(this, wlsCollectionName);
   }

   public String toString() {
      return "wlsCollectionName=" + ApplicationIdUtil.encode(pcGetwlsCollectionName(this)) + ',' + super.toString();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof WLSRoleCollectionInfo)) {
         return false;
      } else {
         WLSRoleCollectionInfo o = (WLSRoleCollectionInfo)other;
         return pcGetwlsCollectionName(this) == pcGetwlsCollectionName(o) || pcGetwlsCollectionName(this) != null && pcGetwlsCollectionName(this).equals(pcGetwlsCollectionName(o));
      }
   }

   public int hashCode() {
      return (pcGetwlsCollectionName(this) != null ? pcGetwlsCollectionName(this).hashCode() : 0) ^ super.hashCode();
   }

   public String getWlsCollectionName() {
      return pcGetwlsCollectionName(this);
   }

   public void setWlsCollectionName(String wlsCollectionName) {
      pcSetwlsCollectionName(this, wlsCollectionName);
   }

   public String getWlsCollectionTimestamp() {
      return pcGetwlsCollectionTimestamp(this);
   }

   public void setWlsCollectionTimestamp(String wlsCollectionTimestamp) {
      pcSetwlsCollectionTimestamp(this, wlsCollectionTimestamp);
   }

   public String getWlsCollectionVersion() {
      return pcGetwlsCollectionVersion(this);
   }

   public void setWlsCollectionVersion(String wlsCollectionVersion) {
      pcSetwlsCollectionVersion(this, wlsCollectionVersion);
   }

   public byte[] getWlsXmlFragment() {
      return pcGetwlsXmlFragment(this);
   }

   public void setWlsXmlFragment(byte[] wlsXmlFragment) {
      pcSetwlsXmlFragment(this, wlsXmlFragment);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$DomainRealmScope != null ? class$Lcom$bea$common$security$store$data$DomainRealmScope : (class$Lcom$bea$common$security$store$data$DomainRealmScope = class$("com.bea.common.security.store.data.DomainRealmScope"));
      pcFieldNames = new String[]{"wlsCollectionName", "wlsCollectionTimestamp", "wlsCollectionVersion", "wlsXmlFragment"};
      pcFieldTypes = new Class[]{class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$L$B != null ? class$L$B : (class$L$B = class$("[B"))};
      pcFieldFlags = new byte[]{26, 26, 26, 21};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$WLSRoleCollectionInfo != null ? class$Lcom$bea$common$security$store$data$WLSRoleCollectionInfo : (class$Lcom$bea$common$security$store$data$WLSRoleCollectionInfo = class$("com.bea.common.security.store.data.WLSRoleCollectionInfo")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "WLSRoleCollectionInfo", new WLSRoleCollectionInfo());
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
      this.wlsCollectionName = null;
      this.wlsCollectionTimestamp = null;
      this.wlsCollectionVersion = null;
      this.wlsXmlFragment = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      WLSRoleCollectionInfo var4 = new WLSRoleCollectionInfo();
      if (var3) {
         var4.pcClearFields();
      }

      var4.pcStateManager = var1;
      var4.pcCopyKeyFieldsFromObjectId(var2);
      return var4;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      WLSRoleCollectionInfo var3 = new WLSRoleCollectionInfo();
      if (var2) {
         var3.pcClearFields();
      }

      var3.pcStateManager = var1;
      return var3;
   }

   protected static int pcGetManagedFieldCount() {
      return 4 + DomainRealmScope.pcGetManagedFieldCount();
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcReplaceField(var1);
      } else {
         switch (var2) {
            case 0:
               this.wlsCollectionName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 1:
               this.wlsCollectionTimestamp = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 2:
               this.wlsCollectionVersion = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 3:
               this.wlsXmlFragment = (byte[])this.pcStateManager.replaceObjectField(this, var1);
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
               this.pcStateManager.providedStringField(this, var1, this.wlsCollectionName);
               return;
            case 1:
               this.pcStateManager.providedStringField(this, var1, this.wlsCollectionTimestamp);
               return;
            case 2:
               this.pcStateManager.providedStringField(this, var1, this.wlsCollectionVersion);
               return;
            case 3:
               this.pcStateManager.providedObjectField(this, var1, this.wlsXmlFragment);
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

   protected void pcCopyField(WLSRoleCollectionInfo var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.wlsCollectionName = var1.wlsCollectionName;
               return;
            case 1:
               this.wlsCollectionTimestamp = var1.wlsCollectionTimestamp;
               return;
            case 2:
               this.wlsCollectionVersion = var1.wlsCollectionVersion;
               return;
            case 3:
               this.wlsXmlFragment = var1.wlsXmlFragment;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      WLSRoleCollectionInfo var3 = (WLSRoleCollectionInfo)var1;
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
      WLSRoleCollectionInfoId var3 = (WLSRoleCollectionInfoId)var2;
      int var4 = pcInheritedFieldCount;
      var3.wlsCollectionName = var1.fetchStringField(0 + var4);
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      WLSRoleCollectionInfoId var2 = (WLSRoleCollectionInfoId)var1;
      var2.wlsCollectionName = this.wlsCollectionName;
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      WLSRoleCollectionInfoId var3 = (WLSRoleCollectionInfoId)var2;
      var1.storeStringField(0 + pcInheritedFieldCount, var3.wlsCollectionName);
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      WLSRoleCollectionInfoId var2 = (WLSRoleCollectionInfoId)var1;
      this.wlsCollectionName = var2.wlsCollectionName;
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new WLSRoleCollectionInfoId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new WLSRoleCollectionInfoId();
   }

   private static final String pcGetwlsCollectionName(WLSRoleCollectionInfo var0) {
      if (var0.pcStateManager == null) {
         return var0.wlsCollectionName;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.wlsCollectionName;
      }
   }

   private static final void pcSetwlsCollectionName(WLSRoleCollectionInfo var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.wlsCollectionName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 0, var0.wlsCollectionName, var1, 0);
      }
   }

   private static final String pcGetwlsCollectionTimestamp(WLSRoleCollectionInfo var0) {
      if (var0.pcStateManager == null) {
         return var0.wlsCollectionTimestamp;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.wlsCollectionTimestamp;
      }
   }

   private static final void pcSetwlsCollectionTimestamp(WLSRoleCollectionInfo var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.wlsCollectionTimestamp = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 1, var0.wlsCollectionTimestamp, var1, 0);
      }
   }

   private static final String pcGetwlsCollectionVersion(WLSRoleCollectionInfo var0) {
      if (var0.pcStateManager == null) {
         return var0.wlsCollectionVersion;
      } else {
         int var1 = pcInheritedFieldCount + 2;
         var0.pcStateManager.accessingField(var1);
         return var0.wlsCollectionVersion;
      }
   }

   private static final void pcSetwlsCollectionVersion(WLSRoleCollectionInfo var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.wlsCollectionVersion = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 2, var0.wlsCollectionVersion, var1, 0);
      }
   }

   private static final byte[] pcGetwlsXmlFragment(WLSRoleCollectionInfo var0) {
      if (var0.pcStateManager == null) {
         return var0.wlsXmlFragment;
      } else {
         int var1 = pcInheritedFieldCount + 3;
         var0.pcStateManager.accessingField(var1);
         return var0.wlsXmlFragment;
      }
   }

   private static final void pcSetwlsXmlFragment(WLSRoleCollectionInfo var0, byte[] var1) {
      if (var0.pcStateManager == null) {
         var0.wlsXmlFragment = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 3, var0.wlsXmlFragment, var1, 0);
      }
   }
}
