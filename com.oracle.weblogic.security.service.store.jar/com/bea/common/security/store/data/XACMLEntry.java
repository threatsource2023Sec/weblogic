package com.bea.common.security.store.data;

import java.util.Collection;
import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.Reflection;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.util.UserException;

public abstract class XACMLEntry extends XACMLTypeScope implements PersistenceCapable {
   private Collection xacmlResourceScope;
   private String cn;
   private String xacmlVersion;
   private byte[] xacmlDocument;
   private String xacmlStatus;
   private String wlsCreatorInfo;
   private String wlsCollectionName;
   private byte[] wlsXmlFragment;
   private static int pcInheritedFieldCount = XACMLTypeScope.pcGetManagedFieldCount();
   private static String[] pcFieldNames;
   private static Class[] pcFieldTypes;
   private static byte[] pcFieldFlags;
   private static Class pcPCSuperclass;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$XACMLTypeScope;
   // $FF: synthetic field
   static Class class$Ljava$lang$String;
   // $FF: synthetic field
   static Class class$L$B;
   // $FF: synthetic field
   static Class class$Ljava$util$Collection;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$XACMLEntry;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$XACMLEntryId;

   public XACMLEntry() {
   }

   public XACMLEntry(String domainName, String realmName, String typeName, String cn, String xacmlVersion, byte[] xacmlDocument, String xacmlStatus) {
      super(domainName, realmName, typeName);
      pcSetcn(this, cn);
      pcSetxacmlVersion(this, xacmlVersion);
      pcSetxacmlDocument(this, xacmlDocument);
      pcSetxacmlStatus(this, xacmlStatus);
   }

   public XACMLEntry(String domainName, String realmName, String typeName, Collection xacmlResourceScope, String cn, String xacmlVersion, byte[] xacmlDocument, String xacmlStatus) {
      super(domainName, realmName, typeName);
      pcSetxacmlResourceScope(this, xacmlResourceScope);
      pcSetcn(this, cn);
      pcSetxacmlVersion(this, xacmlVersion);
      pcSetxacmlDocument(this, xacmlDocument);
      pcSetxacmlStatus(this, xacmlStatus);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof XACMLEntry)) {
         return false;
      } else {
         XACMLEntry o = (XACMLEntry)other;
         return pcGetcn(this) == pcGetcn(o) || pcGetcn(this) != null && pcGetcn(this).equals(pcGetcn(o)) && pcGetxacmlVersion(this) == pcGetxacmlVersion(o) || pcGetxacmlVersion(this) != null && pcGetxacmlVersion(this).equals(pcGetxacmlVersion(o));
      }
   }

   public int hashCode() {
      return (pcGetcn(this) != null ? pcGetcn(this).hashCode() : 0) ^ (pcGetxacmlVersion(this) != null ? pcGetxacmlVersion(this).hashCode() : 0) ^ super.hashCode();
   }

   public String toString() {
      return "cn=" + ApplicationIdUtil.encode(pcGetcn(this)) + ',' + "xacmlVersion" + '=' + ApplicationIdUtil.encode(pcGetxacmlVersion(this)) + ',' + super.toString();
   }

   public Collection getXacmlResourceScope() {
      return pcGetxacmlResourceScope(this);
   }

   public void setXacmlResourceScope(Collection xacmlResourceScope) {
      pcSetxacmlResourceScope(this, xacmlResourceScope);
   }

   public String getCn() {
      return pcGetcn(this);
   }

   public void setCn(String cn) {
      pcSetcn(this, cn);
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

   public byte[] getWlsXmlFragment() {
      return pcGetwlsXmlFragment(this);
   }

   public void setWlsXmlFragment(byte[] wlsXmlFragment) {
      pcSetwlsXmlFragment(this, wlsXmlFragment);
   }

   public byte[] getXacmlDocument() {
      return pcGetxacmlDocument(this);
   }

   public void setXacmlDocument(byte[] xacmlDocument) {
      pcSetxacmlDocument(this, xacmlDocument);
   }

   public String getXacmlStatus() {
      return pcGetxacmlStatus(this);
   }

   public void setXacmlStatus(String xacmlStatus) {
      pcSetxacmlStatus(this, xacmlStatus);
   }

   public String getXacmlVersion() {
      return pcGetxacmlVersion(this);
   }

   public void setXacmlVersion(String xacmlVersion) {
      pcSetxacmlVersion(this, xacmlVersion);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$XACMLTypeScope != null ? class$Lcom$bea$common$security$store$data$XACMLTypeScope : (class$Lcom$bea$common$security$store$data$XACMLTypeScope = class$("com.bea.common.security.store.data.XACMLTypeScope"));
      pcFieldNames = new String[]{"cn", "wlsCollectionName", "wlsCreatorInfo", "wlsXmlFragment", "xacmlDocument", "xacmlResourceScope", "xacmlStatus", "xacmlVersion"};
      pcFieldTypes = new Class[]{class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$L$B != null ? class$L$B : (class$L$B = class$("[B")), class$L$B != null ? class$L$B : (class$L$B = class$("[B")), class$Ljava$util$Collection != null ? class$Ljava$util$Collection : (class$Ljava$util$Collection = class$("java.util.Collection")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String"))};
      pcFieldFlags = new byte[]{26, 26, 26, 21, 26, 5, 26, 26};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$XACMLEntry != null ? class$Lcom$bea$common$security$store$data$XACMLEntry : (class$Lcom$bea$common$security$store$data$XACMLEntry = class$("com.bea.common.security.store.data.XACMLEntry")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "XACMLEntry", (PersistenceCapable)null);
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
      this.wlsCollectionName = null;
      this.wlsCreatorInfo = null;
      this.wlsXmlFragment = null;
      this.xacmlDocument = null;
      this.xacmlResourceScope = null;
      this.xacmlStatus = null;
      this.xacmlVersion = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      throw new UserException();
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      throw new UserException();
   }

   protected static int pcGetManagedFieldCount() {
      return 8 + XACMLTypeScope.pcGetManagedFieldCount();
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
               this.wlsCollectionName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 2:
               this.wlsCreatorInfo = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 3:
               this.wlsXmlFragment = (byte[])this.pcStateManager.replaceObjectField(this, var1);
               return;
            case 4:
               this.xacmlDocument = (byte[])this.pcStateManager.replaceObjectField(this, var1);
               return;
            case 5:
               this.xacmlResourceScope = (Collection)this.pcStateManager.replaceObjectField(this, var1);
               return;
            case 6:
               this.xacmlStatus = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 7:
               this.xacmlVersion = (String)this.pcStateManager.replaceStringField(this, var1);
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
               this.pcStateManager.providedStringField(this, var1, this.wlsCollectionName);
               return;
            case 2:
               this.pcStateManager.providedStringField(this, var1, this.wlsCreatorInfo);
               return;
            case 3:
               this.pcStateManager.providedObjectField(this, var1, this.wlsXmlFragment);
               return;
            case 4:
               this.pcStateManager.providedObjectField(this, var1, this.xacmlDocument);
               return;
            case 5:
               this.pcStateManager.providedObjectField(this, var1, this.xacmlResourceScope);
               return;
            case 6:
               this.pcStateManager.providedStringField(this, var1, this.xacmlStatus);
               return;
            case 7:
               this.pcStateManager.providedStringField(this, var1, this.xacmlVersion);
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

   protected void pcCopyField(XACMLEntry var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.cn = var1.cn;
               return;
            case 1:
               this.wlsCollectionName = var1.wlsCollectionName;
               return;
            case 2:
               this.wlsCreatorInfo = var1.wlsCreatorInfo;
               return;
            case 3:
               this.wlsXmlFragment = var1.wlsXmlFragment;
               return;
            case 4:
               this.xacmlDocument = var1.xacmlDocument;
               return;
            case 5:
               this.xacmlResourceScope = var1.xacmlResourceScope;
               return;
            case 6:
               this.xacmlStatus = var1.xacmlStatus;
               return;
            case 7:
               this.xacmlVersion = var1.xacmlVersion;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      XACMLEntry var3 = (XACMLEntry)var1;
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
      XACMLEntryId var3 = (XACMLEntryId)var2;
      int var4 = pcInheritedFieldCount;
      Reflection.set(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$XACMLEntryId != null ? class$Lcom$bea$common$security$store$data$XACMLEntryId : (class$Lcom$bea$common$security$store$data$XACMLEntryId = class$("com.bea.common.security.store.data.XACMLEntryId")), "cn", true), var1.fetchStringField(0 + var4));
      Reflection.set(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$XACMLEntryId != null ? class$Lcom$bea$common$security$store$data$XACMLEntryId : (class$Lcom$bea$common$security$store$data$XACMLEntryId = class$("com.bea.common.security.store.data.XACMLEntryId")), "xacmlVersion", true), var1.fetchStringField(7 + var4));
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      XACMLEntryId var2 = (XACMLEntryId)var1;
      Reflection.set(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$XACMLEntryId != null ? class$Lcom$bea$common$security$store$data$XACMLEntryId : (class$Lcom$bea$common$security$store$data$XACMLEntryId = class$("com.bea.common.security.store.data.XACMLEntryId")), "cn", true), this.cn);
      Reflection.set(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$XACMLEntryId != null ? class$Lcom$bea$common$security$store$data$XACMLEntryId : (class$Lcom$bea$common$security$store$data$XACMLEntryId = class$("com.bea.common.security.store.data.XACMLEntryId")), "xacmlVersion", true), this.xacmlVersion);
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      XACMLEntryId var3 = (XACMLEntryId)var2;
      var1.storeStringField(0 + pcInheritedFieldCount, (String)Reflection.get(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$XACMLEntryId != null ? class$Lcom$bea$common$security$store$data$XACMLEntryId : (class$Lcom$bea$common$security$store$data$XACMLEntryId = class$("com.bea.common.security.store.data.XACMLEntryId")), "cn", true)));
      var1.storeStringField(7 + pcInheritedFieldCount, (String)Reflection.get(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$XACMLEntryId != null ? class$Lcom$bea$common$security$store$data$XACMLEntryId : (class$Lcom$bea$common$security$store$data$XACMLEntryId = class$("com.bea.common.security.store.data.XACMLEntryId")), "xacmlVersion", true)));
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      XACMLEntryId var2 = (XACMLEntryId)var1;
      this.cn = (String)Reflection.get(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$XACMLEntryId != null ? class$Lcom$bea$common$security$store$data$XACMLEntryId : (class$Lcom$bea$common$security$store$data$XACMLEntryId = class$("com.bea.common.security.store.data.XACMLEntryId")), "cn", true));
      this.xacmlVersion = (String)Reflection.get(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$XACMLEntryId != null ? class$Lcom$bea$common$security$store$data$XACMLEntryId : (class$Lcom$bea$common$security$store$data$XACMLEntryId = class$("com.bea.common.security.store.data.XACMLEntryId")), "xacmlVersion", true));
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new XACMLEntryId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new XACMLEntryId();
   }

   private static final String pcGetcn(XACMLEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.cn;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.cn;
      }
   }

   private static final void pcSetcn(XACMLEntry var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.cn = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 0, var0.cn, var1, 0);
      }
   }

   private static final String pcGetwlsCollectionName(XACMLEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.wlsCollectionName;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.wlsCollectionName;
      }
   }

   private static final void pcSetwlsCollectionName(XACMLEntry var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.wlsCollectionName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 1, var0.wlsCollectionName, var1, 0);
      }
   }

   private static final String pcGetwlsCreatorInfo(XACMLEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.wlsCreatorInfo;
      } else {
         int var1 = pcInheritedFieldCount + 2;
         var0.pcStateManager.accessingField(var1);
         return var0.wlsCreatorInfo;
      }
   }

   private static final void pcSetwlsCreatorInfo(XACMLEntry var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.wlsCreatorInfo = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 2, var0.wlsCreatorInfo, var1, 0);
      }
   }

   private static final byte[] pcGetwlsXmlFragment(XACMLEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.wlsXmlFragment;
      } else {
         int var1 = pcInheritedFieldCount + 3;
         var0.pcStateManager.accessingField(var1);
         return var0.wlsXmlFragment;
      }
   }

   private static final void pcSetwlsXmlFragment(XACMLEntry var0, byte[] var1) {
      if (var0.pcStateManager == null) {
         var0.wlsXmlFragment = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 3, var0.wlsXmlFragment, var1, 0);
      }
   }

   private static final byte[] pcGetxacmlDocument(XACMLEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.xacmlDocument;
      } else {
         int var1 = pcInheritedFieldCount + 4;
         var0.pcStateManager.accessingField(var1);
         return var0.xacmlDocument;
      }
   }

   private static final void pcSetxacmlDocument(XACMLEntry var0, byte[] var1) {
      if (var0.pcStateManager == null) {
         var0.xacmlDocument = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 4, var0.xacmlDocument, var1, 0);
      }
   }

   private static final Collection pcGetxacmlResourceScope(XACMLEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.xacmlResourceScope;
      } else {
         int var1 = pcInheritedFieldCount + 5;
         var0.pcStateManager.accessingField(var1);
         return var0.xacmlResourceScope;
      }
   }

   private static final void pcSetxacmlResourceScope(XACMLEntry var0, Collection var1) {
      if (var0.pcStateManager == null) {
         var0.xacmlResourceScope = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 5, var0.xacmlResourceScope, var1, 0);
      }
   }

   private static final String pcGetxacmlStatus(XACMLEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.xacmlStatus;
      } else {
         int var1 = pcInheritedFieldCount + 6;
         var0.pcStateManager.accessingField(var1);
         return var0.xacmlStatus;
      }
   }

   private static final void pcSetxacmlStatus(XACMLEntry var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.xacmlStatus = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 6, var0.xacmlStatus, var1, 0);
      }
   }

   private static final String pcGetxacmlVersion(XACMLEntry var0) {
      if (var0.pcStateManager == null) {
         return var0.xacmlVersion;
      } else {
         int var1 = pcInheritedFieldCount + 7;
         var0.pcStateManager.accessingField(var1);
         return var0.xacmlVersion;
      }
   }

   private static final void pcSetxacmlVersion(XACMLEntry var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.xacmlVersion = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 7, var0.xacmlVersion, var1, 0);
      }
   }
}
