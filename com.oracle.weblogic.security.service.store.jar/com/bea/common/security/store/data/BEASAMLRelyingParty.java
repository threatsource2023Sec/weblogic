package com.bea.common.security.store.data;

import java.util.Collection;
import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;

public class BEASAMLRelyingParty extends BEASAMLPartner implements PersistenceCapable {
   private String beaSAMLProfile;
   private String beaSAMLTargetURL;
   private String beaSAMLAssertionConsumerURL;
   private Collection beaSAMLAssertionConsumerParams;
   private String beaSAMLAuthUsername;
   private String beaSAMLAuthPassword;
   private String beaSAMLAuthSSLClientCertAlias;
   private String beaSAMLPostForm;
   private String beaSAMLTimeToLive;
   private String beaSAMLTimeToLiveOffset;
   private String beaSAMLDoNotCacheCondition;
   private Collection beaSAMLAudienceURI;
   private String beaSAMLSignedAssertions;
   private String beaSAMLKeyinfoIncluded;
   private String beaSAMLNameMapperClass;
   private String beaSAMLGroupsAttributeEnabled;
   private static int pcInheritedFieldCount = BEASAMLPartner.pcGetManagedFieldCount();
   private static String[] pcFieldNames;
   private static Class[] pcFieldTypes;
   private static byte[] pcFieldFlags;
   private static Class pcPCSuperclass;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$BEASAMLPartner;
   // $FF: synthetic field
   static Class class$Ljava$util$Collection;
   // $FF: synthetic field
   static Class class$Ljava$lang$String;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$BEASAMLRelyingParty;

   public BEASAMLRelyingParty() {
   }

   public BEASAMLRelyingParty(String domainName, String realmName, String registryName, String cn, String beaSAMLPartnerEnabled, String beaSAMLProfile) {
      super(domainName, realmName, registryName, cn, beaSAMLPartnerEnabled);
      pcSetbeaSAMLProfile(this, beaSAMLProfile);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof BEASAMLRelyingParty) ? false : super.equals(other);
      }
   }

   public Collection getBeaSAMLAssertionConsumerParams() {
      return pcGetbeaSAMLAssertionConsumerParams(this);
   }

   public void setBeaSAMLAssertionConsumerParams(Collection beaSAMLAssertionConsumerParams) {
      pcSetbeaSAMLAssertionConsumerParams(this, beaSAMLAssertionConsumerParams);
   }

   public String getBeaSAMLAssertionConsumerURL() {
      return pcGetbeaSAMLAssertionConsumerURL(this);
   }

   public void setBeaSAMLAssertionConsumerURL(String beaSAMLAssertionConsumerURL) {
      pcSetbeaSAMLAssertionConsumerURL(this, beaSAMLAssertionConsumerURL);
   }

   public Collection getBeaSAMLAudienceURI() {
      return pcGetbeaSAMLAudienceURI(this);
   }

   public void setBeaSAMLAudienceURI(Collection beaSAMLAudienceURI) {
      pcSetbeaSAMLAudienceURI(this, beaSAMLAudienceURI);
   }

   public String getBeaSAMLAuthPassword() {
      return pcGetbeaSAMLAuthPassword(this);
   }

   public void setBeaSAMLAuthPassword(String beaSAMLAuthPassword) {
      pcSetbeaSAMLAuthPassword(this, beaSAMLAuthPassword);
   }

   public String getBeaSAMLAuthSSLClientCertAlias() {
      return pcGetbeaSAMLAuthSSLClientCertAlias(this);
   }

   public void setBeaSAMLAuthSSLClientCertAlias(String beaSAMLAuthSSLClientCertAlias) {
      pcSetbeaSAMLAuthSSLClientCertAlias(this, beaSAMLAuthSSLClientCertAlias);
   }

   public String getBeaSAMLAuthUsername() {
      return pcGetbeaSAMLAuthUsername(this);
   }

   public void setBeaSAMLAuthUsername(String beaSAMLAuthUsername) {
      pcSetbeaSAMLAuthUsername(this, beaSAMLAuthUsername);
   }

   public String getBeaSAMLDoNotCacheCondition() {
      return pcGetbeaSAMLDoNotCacheCondition(this);
   }

   public void setBeaSAMLDoNotCacheCondition(String beaSAMLDoNotCacheCondition) {
      pcSetbeaSAMLDoNotCacheCondition(this, beaSAMLDoNotCacheCondition);
   }

   public String getBeaSAMLGroupsAttributeEnabled() {
      return pcGetbeaSAMLGroupsAttributeEnabled(this);
   }

   public void setBeaSAMLGroupsAttributeEnabled(String beaSAMLGroupsAttributeEnabled) {
      pcSetbeaSAMLGroupsAttributeEnabled(this, beaSAMLGroupsAttributeEnabled);
   }

   public String getBeaSAMLKeyinfoIncluded() {
      return pcGetbeaSAMLKeyinfoIncluded(this);
   }

   public void setBeaSAMLKeyinfoIncluded(String beaSAMLKeyinfoIncluded) {
      pcSetbeaSAMLKeyinfoIncluded(this, beaSAMLKeyinfoIncluded);
   }

   public String getBeaSAMLNameMapperClass() {
      return pcGetbeaSAMLNameMapperClass(this);
   }

   public void setBeaSAMLNameMapperClass(String beaSAMLNameMapperClass) {
      pcSetbeaSAMLNameMapperClass(this, beaSAMLNameMapperClass);
   }

   public String getBeaSAMLPostForm() {
      return pcGetbeaSAMLPostForm(this);
   }

   public void setBeaSAMLPostForm(String beaSAMLPostForm) {
      pcSetbeaSAMLPostForm(this, beaSAMLPostForm);
   }

   public String getBeaSAMLProfile() {
      return pcGetbeaSAMLProfile(this);
   }

   public void setBeaSAMLProfile(String beaSAMLProfile) {
      pcSetbeaSAMLProfile(this, beaSAMLProfile);
   }

   public String getBeaSAMLSignedAssertions() {
      return pcGetbeaSAMLSignedAssertions(this);
   }

   public void setBeaSAMLSignedAssertions(String beaSAMLSignedAssertions) {
      pcSetbeaSAMLSignedAssertions(this, beaSAMLSignedAssertions);
   }

   public String getBeaSAMLTargetURL() {
      return pcGetbeaSAMLTargetURL(this);
   }

   public void setBeaSAMLTargetURL(String beaSAMLTargetURL) {
      pcSetbeaSAMLTargetURL(this, beaSAMLTargetURL);
   }

   public String getBeaSAMLTimeToLive() {
      return pcGetbeaSAMLTimeToLive(this);
   }

   public void setBeaSAMLTimeToLive(String beaSAMLTimeToLive) {
      pcSetbeaSAMLTimeToLive(this, beaSAMLTimeToLive);
   }

   public String getBeaSAMLTimeToLiveOffset() {
      return pcGetbeaSAMLTimeToLiveOffset(this);
   }

   public void setBeaSAMLTimeToLiveOffset(String beaSAMLTimeToLiveOffset) {
      pcSetbeaSAMLTimeToLiveOffset(this, beaSAMLTimeToLiveOffset);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$BEASAMLPartner != null ? class$Lcom$bea$common$security$store$data$BEASAMLPartner : (class$Lcom$bea$common$security$store$data$BEASAMLPartner = class$("com.bea.common.security.store.data.BEASAMLPartner"));
      pcFieldNames = new String[]{"beaSAMLAssertionConsumerParams", "beaSAMLAssertionConsumerURL", "beaSAMLAudienceURI", "beaSAMLAuthPassword", "beaSAMLAuthSSLClientCertAlias", "beaSAMLAuthUsername", "beaSAMLDoNotCacheCondition", "beaSAMLGroupsAttributeEnabled", "beaSAMLKeyinfoIncluded", "beaSAMLNameMapperClass", "beaSAMLPostForm", "beaSAMLProfile", "beaSAMLSignedAssertions", "beaSAMLTargetURL", "beaSAMLTimeToLive", "beaSAMLTimeToLiveOffset"};
      pcFieldTypes = new Class[]{class$Ljava$util$Collection != null ? class$Ljava$util$Collection : (class$Ljava$util$Collection = class$("java.util.Collection")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$util$Collection != null ? class$Ljava$util$Collection : (class$Ljava$util$Collection = class$("java.util.Collection")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String"))};
      pcFieldFlags = new byte[]{10, 26, 10, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$BEASAMLRelyingParty != null ? class$Lcom$bea$common$security$store$data$BEASAMLRelyingParty : (class$Lcom$bea$common$security$store$data$BEASAMLRelyingParty = class$("com.bea.common.security.store.data.BEASAMLRelyingParty")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "BEASAMLRelyingParty", new BEASAMLRelyingParty());
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
      this.beaSAMLAssertionConsumerParams = null;
      this.beaSAMLAssertionConsumerURL = null;
      this.beaSAMLAudienceURI = null;
      this.beaSAMLAuthPassword = null;
      this.beaSAMLAuthSSLClientCertAlias = null;
      this.beaSAMLAuthUsername = null;
      this.beaSAMLDoNotCacheCondition = null;
      this.beaSAMLGroupsAttributeEnabled = null;
      this.beaSAMLKeyinfoIncluded = null;
      this.beaSAMLNameMapperClass = null;
      this.beaSAMLPostForm = null;
      this.beaSAMLProfile = null;
      this.beaSAMLSignedAssertions = null;
      this.beaSAMLTargetURL = null;
      this.beaSAMLTimeToLive = null;
      this.beaSAMLTimeToLiveOffset = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      BEASAMLRelyingParty var4 = new BEASAMLRelyingParty();
      if (var3) {
         var4.pcClearFields();
      }

      var4.pcStateManager = var1;
      var4.pcCopyKeyFieldsFromObjectId(var2);
      return var4;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      BEASAMLRelyingParty var3 = new BEASAMLRelyingParty();
      if (var2) {
         var3.pcClearFields();
      }

      var3.pcStateManager = var1;
      return var3;
   }

   protected static int pcGetManagedFieldCount() {
      return 16 + BEASAMLPartner.pcGetManagedFieldCount();
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcReplaceField(var1);
      } else {
         switch (var2) {
            case 0:
               this.beaSAMLAssertionConsumerParams = (Collection)this.pcStateManager.replaceObjectField(this, var1);
               return;
            case 1:
               this.beaSAMLAssertionConsumerURL = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 2:
               this.beaSAMLAudienceURI = (Collection)this.pcStateManager.replaceObjectField(this, var1);
               return;
            case 3:
               this.beaSAMLAuthPassword = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 4:
               this.beaSAMLAuthSSLClientCertAlias = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 5:
               this.beaSAMLAuthUsername = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 6:
               this.beaSAMLDoNotCacheCondition = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 7:
               this.beaSAMLGroupsAttributeEnabled = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 8:
               this.beaSAMLKeyinfoIncluded = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 9:
               this.beaSAMLNameMapperClass = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 10:
               this.beaSAMLPostForm = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 11:
               this.beaSAMLProfile = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 12:
               this.beaSAMLSignedAssertions = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 13:
               this.beaSAMLTargetURL = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 14:
               this.beaSAMLTimeToLive = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 15:
               this.beaSAMLTimeToLiveOffset = (String)this.pcStateManager.replaceStringField(this, var1);
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
               this.pcStateManager.providedObjectField(this, var1, this.beaSAMLAssertionConsumerParams);
               return;
            case 1:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLAssertionConsumerURL);
               return;
            case 2:
               this.pcStateManager.providedObjectField(this, var1, this.beaSAMLAudienceURI);
               return;
            case 3:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLAuthPassword);
               return;
            case 4:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLAuthSSLClientCertAlias);
               return;
            case 5:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLAuthUsername);
               return;
            case 6:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLDoNotCacheCondition);
               return;
            case 7:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLGroupsAttributeEnabled);
               return;
            case 8:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLKeyinfoIncluded);
               return;
            case 9:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLNameMapperClass);
               return;
            case 10:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLPostForm);
               return;
            case 11:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLProfile);
               return;
            case 12:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLSignedAssertions);
               return;
            case 13:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLTargetURL);
               return;
            case 14:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLTimeToLive);
               return;
            case 15:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLTimeToLiveOffset);
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

   protected void pcCopyField(BEASAMLRelyingParty var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.beaSAMLAssertionConsumerParams = var1.beaSAMLAssertionConsumerParams;
               return;
            case 1:
               this.beaSAMLAssertionConsumerURL = var1.beaSAMLAssertionConsumerURL;
               return;
            case 2:
               this.beaSAMLAudienceURI = var1.beaSAMLAudienceURI;
               return;
            case 3:
               this.beaSAMLAuthPassword = var1.beaSAMLAuthPassword;
               return;
            case 4:
               this.beaSAMLAuthSSLClientCertAlias = var1.beaSAMLAuthSSLClientCertAlias;
               return;
            case 5:
               this.beaSAMLAuthUsername = var1.beaSAMLAuthUsername;
               return;
            case 6:
               this.beaSAMLDoNotCacheCondition = var1.beaSAMLDoNotCacheCondition;
               return;
            case 7:
               this.beaSAMLGroupsAttributeEnabled = var1.beaSAMLGroupsAttributeEnabled;
               return;
            case 8:
               this.beaSAMLKeyinfoIncluded = var1.beaSAMLKeyinfoIncluded;
               return;
            case 9:
               this.beaSAMLNameMapperClass = var1.beaSAMLNameMapperClass;
               return;
            case 10:
               this.beaSAMLPostForm = var1.beaSAMLPostForm;
               return;
            case 11:
               this.beaSAMLProfile = var1.beaSAMLProfile;
               return;
            case 12:
               this.beaSAMLSignedAssertions = var1.beaSAMLSignedAssertions;
               return;
            case 13:
               this.beaSAMLTargetURL = var1.beaSAMLTargetURL;
               return;
            case 14:
               this.beaSAMLTimeToLive = var1.beaSAMLTimeToLive;
               return;
            case 15:
               this.beaSAMLTimeToLiveOffset = var1.beaSAMLTimeToLiveOffset;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      BEASAMLRelyingParty var3 = (BEASAMLRelyingParty)var1;
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
      BEASAMLRelyingPartyId var3 = (BEASAMLRelyingPartyId)var2;
      int var4 = pcInheritedFieldCount;
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      BEASAMLRelyingPartyId var2 = (BEASAMLRelyingPartyId)var1;
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      BEASAMLRelyingPartyId var3 = (BEASAMLRelyingPartyId)var2;
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      BEASAMLRelyingPartyId var2 = (BEASAMLRelyingPartyId)var1;
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new BEASAMLRelyingPartyId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new BEASAMLRelyingPartyId();
   }

   private static final Collection pcGetbeaSAMLAssertionConsumerParams(BEASAMLRelyingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLAssertionConsumerParams;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLAssertionConsumerParams;
      }
   }

   private static final void pcSetbeaSAMLAssertionConsumerParams(BEASAMLRelyingParty var0, Collection var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLAssertionConsumerParams = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 0, var0.beaSAMLAssertionConsumerParams, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLAssertionConsumerURL(BEASAMLRelyingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLAssertionConsumerURL;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLAssertionConsumerURL;
      }
   }

   private static final void pcSetbeaSAMLAssertionConsumerURL(BEASAMLRelyingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLAssertionConsumerURL = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 1, var0.beaSAMLAssertionConsumerURL, var1, 0);
      }
   }

   private static final Collection pcGetbeaSAMLAudienceURI(BEASAMLRelyingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLAudienceURI;
      } else {
         int var1 = pcInheritedFieldCount + 2;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLAudienceURI;
      }
   }

   private static final void pcSetbeaSAMLAudienceURI(BEASAMLRelyingParty var0, Collection var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLAudienceURI = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 2, var0.beaSAMLAudienceURI, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLAuthPassword(BEASAMLRelyingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLAuthPassword;
      } else {
         int var1 = pcInheritedFieldCount + 3;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLAuthPassword;
      }
   }

   private static final void pcSetbeaSAMLAuthPassword(BEASAMLRelyingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLAuthPassword = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 3, var0.beaSAMLAuthPassword, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLAuthSSLClientCertAlias(BEASAMLRelyingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLAuthSSLClientCertAlias;
      } else {
         int var1 = pcInheritedFieldCount + 4;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLAuthSSLClientCertAlias;
      }
   }

   private static final void pcSetbeaSAMLAuthSSLClientCertAlias(BEASAMLRelyingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLAuthSSLClientCertAlias = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 4, var0.beaSAMLAuthSSLClientCertAlias, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLAuthUsername(BEASAMLRelyingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLAuthUsername;
      } else {
         int var1 = pcInheritedFieldCount + 5;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLAuthUsername;
      }
   }

   private static final void pcSetbeaSAMLAuthUsername(BEASAMLRelyingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLAuthUsername = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 5, var0.beaSAMLAuthUsername, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLDoNotCacheCondition(BEASAMLRelyingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLDoNotCacheCondition;
      } else {
         int var1 = pcInheritedFieldCount + 6;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLDoNotCacheCondition;
      }
   }

   private static final void pcSetbeaSAMLDoNotCacheCondition(BEASAMLRelyingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLDoNotCacheCondition = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 6, var0.beaSAMLDoNotCacheCondition, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLGroupsAttributeEnabled(BEASAMLRelyingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLGroupsAttributeEnabled;
      } else {
         int var1 = pcInheritedFieldCount + 7;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLGroupsAttributeEnabled;
      }
   }

   private static final void pcSetbeaSAMLGroupsAttributeEnabled(BEASAMLRelyingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLGroupsAttributeEnabled = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 7, var0.beaSAMLGroupsAttributeEnabled, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLKeyinfoIncluded(BEASAMLRelyingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLKeyinfoIncluded;
      } else {
         int var1 = pcInheritedFieldCount + 8;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLKeyinfoIncluded;
      }
   }

   private static final void pcSetbeaSAMLKeyinfoIncluded(BEASAMLRelyingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLKeyinfoIncluded = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 8, var0.beaSAMLKeyinfoIncluded, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLNameMapperClass(BEASAMLRelyingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLNameMapperClass;
      } else {
         int var1 = pcInheritedFieldCount + 9;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLNameMapperClass;
      }
   }

   private static final void pcSetbeaSAMLNameMapperClass(BEASAMLRelyingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLNameMapperClass = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 9, var0.beaSAMLNameMapperClass, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLPostForm(BEASAMLRelyingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLPostForm;
      } else {
         int var1 = pcInheritedFieldCount + 10;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLPostForm;
      }
   }

   private static final void pcSetbeaSAMLPostForm(BEASAMLRelyingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLPostForm = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 10, var0.beaSAMLPostForm, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLProfile(BEASAMLRelyingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLProfile;
      } else {
         int var1 = pcInheritedFieldCount + 11;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLProfile;
      }
   }

   private static final void pcSetbeaSAMLProfile(BEASAMLRelyingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLProfile = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 11, var0.beaSAMLProfile, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLSignedAssertions(BEASAMLRelyingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLSignedAssertions;
      } else {
         int var1 = pcInheritedFieldCount + 12;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLSignedAssertions;
      }
   }

   private static final void pcSetbeaSAMLSignedAssertions(BEASAMLRelyingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLSignedAssertions = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 12, var0.beaSAMLSignedAssertions, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLTargetURL(BEASAMLRelyingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLTargetURL;
      } else {
         int var1 = pcInheritedFieldCount + 13;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLTargetURL;
      }
   }

   private static final void pcSetbeaSAMLTargetURL(BEASAMLRelyingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLTargetURL = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 13, var0.beaSAMLTargetURL, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLTimeToLive(BEASAMLRelyingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLTimeToLive;
      } else {
         int var1 = pcInheritedFieldCount + 14;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLTimeToLive;
      }
   }

   private static final void pcSetbeaSAMLTimeToLive(BEASAMLRelyingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLTimeToLive = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 14, var0.beaSAMLTimeToLive, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLTimeToLiveOffset(BEASAMLRelyingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLTimeToLiveOffset;
      } else {
         int var1 = pcInheritedFieldCount + 15;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLTimeToLiveOffset;
      }
   }

   private static final void pcSetbeaSAMLTimeToLiveOffset(BEASAMLRelyingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLTimeToLiveOffset = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 15, var0.beaSAMLTimeToLiveOffset, var1, 0);
      }
   }
}
