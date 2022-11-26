package com.bea.common.security.store.data;

import java.util.Collection;
import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;

public class BEASAMLAssertingParty extends BEASAMLPartner implements PersistenceCapable {
   private String beaSAMLProfile;
   private String beaSAMLTargetURL;
   private String beaSAMLIssuerURI;
   private String beaSAMLSourceId;
   private String beaSAMLIntersiteTransferURL;
   private Collection beaSAMLIntersiteTransferParams;
   private String beaSAMLAssertionRetrievalURL;
   private String beaSAMLAuthUsername;
   private String beaSAMLAuthPassword;
   private Collection beaSAMLRedirectURIs;
   private Collection beaSAMLAudienceURI;
   private String beaSAMLSignedAssertions;
   private String beaSAMLAssertionSigningCertAlias;
   private String beaSAMLProtocolSigningCertAlias;
   private String beaSAMLNameMapperClass;
   private String beaSAMLGroupsAttributeEnabled;
   private String beaSAMLVirtualUserEnabled;
   private String beaSAMLPersistentARSConnEnabled;
   private static int pcInheritedFieldCount = BEASAMLPartner.pcGetManagedFieldCount();
   private static String[] pcFieldNames;
   private static Class[] pcFieldTypes;
   private static byte[] pcFieldFlags;
   private static Class pcPCSuperclass;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$BEASAMLPartner;
   // $FF: synthetic field
   static Class class$Ljava$lang$String;
   // $FF: synthetic field
   static Class class$Ljava$util$Collection;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$BEASAMLAssertingParty;

   public BEASAMLAssertingParty() {
   }

   public BEASAMLAssertingParty(String domainName, String realmName, String registryName, String cn, String beaSAMLPartnerEnabled, String beaSAMLProfile) {
      super(domainName, realmName, registryName, cn, beaSAMLPartnerEnabled);
      pcSetbeaSAMLProfile(this, beaSAMLProfile);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof BEASAMLAssertingParty) ? false : super.equals(other);
      }
   }

   public String getBeaSAMLAssertionRetrievalURL() {
      return pcGetbeaSAMLAssertionRetrievalURL(this);
   }

   public void setBeaSAMLAssertionRetrievalURL(String beaSAMLAssertionRetrievalURL) {
      pcSetbeaSAMLAssertionRetrievalURL(this, beaSAMLAssertionRetrievalURL);
   }

   public String getBeaSAMLAssertionSigningCertAlias() {
      return pcGetbeaSAMLAssertionSigningCertAlias(this);
   }

   public void setBeaSAMLAssertionSigningCertAlias(String beaSAMLAssertionSigningCertAlias) {
      pcSetbeaSAMLAssertionSigningCertAlias(this, beaSAMLAssertionSigningCertAlias);
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

   public String getBeaSAMLAuthUsername() {
      return pcGetbeaSAMLAuthUsername(this);
   }

   public void setBeaSAMLAuthUsername(String beaSAMLAuthUsername) {
      pcSetbeaSAMLAuthUsername(this, beaSAMLAuthUsername);
   }

   public String getBeaSAMLGroupsAttributeEnabled() {
      return pcGetbeaSAMLGroupsAttributeEnabled(this);
   }

   public void setBeaSAMLGroupsAttributeEnabled(String beaSAMLGroupsAttributeEnabled) {
      pcSetbeaSAMLGroupsAttributeEnabled(this, beaSAMLGroupsAttributeEnabled);
   }

   public Collection getBeaSAMLIntersiteTransferParams() {
      return pcGetbeaSAMLIntersiteTransferParams(this);
   }

   public void setBeaSAMLIntersiteTransferParams(Collection beaSAMLIntersiteTransferParams) {
      pcSetbeaSAMLIntersiteTransferParams(this, beaSAMLIntersiteTransferParams);
   }

   public String getBeaSAMLIntersiteTransferURL() {
      return pcGetbeaSAMLIntersiteTransferURL(this);
   }

   public void setBeaSAMLIntersiteTransferURL(String beaSAMLIntersiteTransferURL) {
      pcSetbeaSAMLIntersiteTransferURL(this, beaSAMLIntersiteTransferURL);
   }

   public String getBeaSAMLIssuerURI() {
      return pcGetbeaSAMLIssuerURI(this);
   }

   public void setBeaSAMLIssuerURI(String beaSAMLIssuerURI) {
      pcSetbeaSAMLIssuerURI(this, beaSAMLIssuerURI);
   }

   public String getBeaSAMLNameMapperClass() {
      return pcGetbeaSAMLNameMapperClass(this);
   }

   public void setBeaSAMLNameMapperClass(String beaSAMLNameMapperClass) {
      pcSetbeaSAMLNameMapperClass(this, beaSAMLNameMapperClass);
   }

   public String getBeaSAMLPersistentARSConnEnabled() {
      return pcGetbeaSAMLPersistentARSConnEnabled(this);
   }

   public void setBeaSAMLPersistentARSConnEnabled(String beaSAMLPersistentARSConnEnabled) {
      pcSetbeaSAMLPersistentARSConnEnabled(this, beaSAMLPersistentARSConnEnabled);
   }

   public String getBeaSAMLProfile() {
      return pcGetbeaSAMLProfile(this);
   }

   public void setBeaSAMLProfile(String beaSAMLProfile) {
      pcSetbeaSAMLProfile(this, beaSAMLProfile);
   }

   public String getBeaSAMLProtocolSigningCertAlias() {
      return pcGetbeaSAMLProtocolSigningCertAlias(this);
   }

   public void setBeaSAMLProtocolSigningCertAlias(String beaSAMLProtocolSigningCertAlias) {
      pcSetbeaSAMLProtocolSigningCertAlias(this, beaSAMLProtocolSigningCertAlias);
   }

   public Collection getBeaSAMLRedirectURIs() {
      return pcGetbeaSAMLRedirectURIs(this);
   }

   public void setBeaSAMLRedirectURIs(Collection beaSAMLRedirectURIs) {
      pcSetbeaSAMLRedirectURIs(this, beaSAMLRedirectURIs);
   }

   public String getBeaSAMLSignedAssertions() {
      return pcGetbeaSAMLSignedAssertions(this);
   }

   public void setBeaSAMLSignedAssertions(String beaSAMLSignedAssertions) {
      pcSetbeaSAMLSignedAssertions(this, beaSAMLSignedAssertions);
   }

   public String getBeaSAMLSourceId() {
      return pcGetbeaSAMLSourceId(this);
   }

   public void setBeaSAMLSourceId(String beaSAMLSourceId) {
      pcSetbeaSAMLSourceId(this, beaSAMLSourceId);
   }

   public String getBeaSAMLTargetURL() {
      return pcGetbeaSAMLTargetURL(this);
   }

   public void setBeaSAMLTargetURL(String beaSAMLTargetURL) {
      pcSetbeaSAMLTargetURL(this, beaSAMLTargetURL);
   }

   public String getBeaSAMLVirtualUserEnabled() {
      return pcGetbeaSAMLVirtualUserEnabled(this);
   }

   public void setBeaSAMLVirtualUserEnabled(String beaSAMLVirtualUserEnabled) {
      pcSetbeaSAMLVirtualUserEnabled(this, beaSAMLVirtualUserEnabled);
   }

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$BEASAMLPartner != null ? class$Lcom$bea$common$security$store$data$BEASAMLPartner : (class$Lcom$bea$common$security$store$data$BEASAMLPartner = class$("com.bea.common.security.store.data.BEASAMLPartner"));
      pcFieldNames = new String[]{"beaSAMLAssertionRetrievalURL", "beaSAMLAssertionSigningCertAlias", "beaSAMLAudienceURI", "beaSAMLAuthPassword", "beaSAMLAuthUsername", "beaSAMLGroupsAttributeEnabled", "beaSAMLIntersiteTransferParams", "beaSAMLIntersiteTransferURL", "beaSAMLIssuerURI", "beaSAMLNameMapperClass", "beaSAMLPersistentARSConnEnabled", "beaSAMLProfile", "beaSAMLProtocolSigningCertAlias", "beaSAMLRedirectURIs", "beaSAMLSignedAssertions", "beaSAMLSourceId", "beaSAMLTargetURL", "beaSAMLVirtualUserEnabled"};
      pcFieldTypes = new Class[]{class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$util$Collection != null ? class$Ljava$util$Collection : (class$Ljava$util$Collection = class$("java.util.Collection")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$util$Collection != null ? class$Ljava$util$Collection : (class$Ljava$util$Collection = class$("java.util.Collection")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$util$Collection != null ? class$Ljava$util$Collection : (class$Ljava$util$Collection = class$("java.util.Collection")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String"))};
      pcFieldFlags = new byte[]{26, 26, 10, 26, 26, 26, 10, 26, 26, 26, 26, 26, 26, 10, 26, 26, 26, 26};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$BEASAMLAssertingParty != null ? class$Lcom$bea$common$security$store$data$BEASAMLAssertingParty : (class$Lcom$bea$common$security$store$data$BEASAMLAssertingParty = class$("com.bea.common.security.store.data.BEASAMLAssertingParty")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "BEASAMLAssertingParty", new BEASAMLAssertingParty());
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
      this.beaSAMLAssertionRetrievalURL = null;
      this.beaSAMLAssertionSigningCertAlias = null;
      this.beaSAMLAudienceURI = null;
      this.beaSAMLAuthPassword = null;
      this.beaSAMLAuthUsername = null;
      this.beaSAMLGroupsAttributeEnabled = null;
      this.beaSAMLIntersiteTransferParams = null;
      this.beaSAMLIntersiteTransferURL = null;
      this.beaSAMLIssuerURI = null;
      this.beaSAMLNameMapperClass = null;
      this.beaSAMLPersistentARSConnEnabled = null;
      this.beaSAMLProfile = null;
      this.beaSAMLProtocolSigningCertAlias = null;
      this.beaSAMLRedirectURIs = null;
      this.beaSAMLSignedAssertions = null;
      this.beaSAMLSourceId = null;
      this.beaSAMLTargetURL = null;
      this.beaSAMLVirtualUserEnabled = null;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      BEASAMLAssertingParty var4 = new BEASAMLAssertingParty();
      if (var3) {
         var4.pcClearFields();
      }

      var4.pcStateManager = var1;
      var4.pcCopyKeyFieldsFromObjectId(var2);
      return var4;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      BEASAMLAssertingParty var3 = new BEASAMLAssertingParty();
      if (var2) {
         var3.pcClearFields();
      }

      var3.pcStateManager = var1;
      return var3;
   }

   protected static int pcGetManagedFieldCount() {
      return 18 + BEASAMLPartner.pcGetManagedFieldCount();
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcReplaceField(var1);
      } else {
         switch (var2) {
            case 0:
               this.beaSAMLAssertionRetrievalURL = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 1:
               this.beaSAMLAssertionSigningCertAlias = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 2:
               this.beaSAMLAudienceURI = (Collection)this.pcStateManager.replaceObjectField(this, var1);
               return;
            case 3:
               this.beaSAMLAuthPassword = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 4:
               this.beaSAMLAuthUsername = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 5:
               this.beaSAMLGroupsAttributeEnabled = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 6:
               this.beaSAMLIntersiteTransferParams = (Collection)this.pcStateManager.replaceObjectField(this, var1);
               return;
            case 7:
               this.beaSAMLIntersiteTransferURL = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 8:
               this.beaSAMLIssuerURI = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 9:
               this.beaSAMLNameMapperClass = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 10:
               this.beaSAMLPersistentARSConnEnabled = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 11:
               this.beaSAMLProfile = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 12:
               this.beaSAMLProtocolSigningCertAlias = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 13:
               this.beaSAMLRedirectURIs = (Collection)this.pcStateManager.replaceObjectField(this, var1);
               return;
            case 14:
               this.beaSAMLSignedAssertions = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 15:
               this.beaSAMLSourceId = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 16:
               this.beaSAMLTargetURL = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 17:
               this.beaSAMLVirtualUserEnabled = (String)this.pcStateManager.replaceStringField(this, var1);
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
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLAssertionRetrievalURL);
               return;
            case 1:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLAssertionSigningCertAlias);
               return;
            case 2:
               this.pcStateManager.providedObjectField(this, var1, this.beaSAMLAudienceURI);
               return;
            case 3:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLAuthPassword);
               return;
            case 4:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLAuthUsername);
               return;
            case 5:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLGroupsAttributeEnabled);
               return;
            case 6:
               this.pcStateManager.providedObjectField(this, var1, this.beaSAMLIntersiteTransferParams);
               return;
            case 7:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLIntersiteTransferURL);
               return;
            case 8:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLIssuerURI);
               return;
            case 9:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLNameMapperClass);
               return;
            case 10:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLPersistentARSConnEnabled);
               return;
            case 11:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLProfile);
               return;
            case 12:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLProtocolSigningCertAlias);
               return;
            case 13:
               this.pcStateManager.providedObjectField(this, var1, this.beaSAMLRedirectURIs);
               return;
            case 14:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLSignedAssertions);
               return;
            case 15:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLSourceId);
               return;
            case 16:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLTargetURL);
               return;
            case 17:
               this.pcStateManager.providedStringField(this, var1, this.beaSAMLVirtualUserEnabled);
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

   protected void pcCopyField(BEASAMLAssertingParty var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.beaSAMLAssertionRetrievalURL = var1.beaSAMLAssertionRetrievalURL;
               return;
            case 1:
               this.beaSAMLAssertionSigningCertAlias = var1.beaSAMLAssertionSigningCertAlias;
               return;
            case 2:
               this.beaSAMLAudienceURI = var1.beaSAMLAudienceURI;
               return;
            case 3:
               this.beaSAMLAuthPassword = var1.beaSAMLAuthPassword;
               return;
            case 4:
               this.beaSAMLAuthUsername = var1.beaSAMLAuthUsername;
               return;
            case 5:
               this.beaSAMLGroupsAttributeEnabled = var1.beaSAMLGroupsAttributeEnabled;
               return;
            case 6:
               this.beaSAMLIntersiteTransferParams = var1.beaSAMLIntersiteTransferParams;
               return;
            case 7:
               this.beaSAMLIntersiteTransferURL = var1.beaSAMLIntersiteTransferURL;
               return;
            case 8:
               this.beaSAMLIssuerURI = var1.beaSAMLIssuerURI;
               return;
            case 9:
               this.beaSAMLNameMapperClass = var1.beaSAMLNameMapperClass;
               return;
            case 10:
               this.beaSAMLPersistentARSConnEnabled = var1.beaSAMLPersistentARSConnEnabled;
               return;
            case 11:
               this.beaSAMLProfile = var1.beaSAMLProfile;
               return;
            case 12:
               this.beaSAMLProtocolSigningCertAlias = var1.beaSAMLProtocolSigningCertAlias;
               return;
            case 13:
               this.beaSAMLRedirectURIs = var1.beaSAMLRedirectURIs;
               return;
            case 14:
               this.beaSAMLSignedAssertions = var1.beaSAMLSignedAssertions;
               return;
            case 15:
               this.beaSAMLSourceId = var1.beaSAMLSourceId;
               return;
            case 16:
               this.beaSAMLTargetURL = var1.beaSAMLTargetURL;
               return;
            case 17:
               this.beaSAMLVirtualUserEnabled = var1.beaSAMLVirtualUserEnabled;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      BEASAMLAssertingParty var3 = (BEASAMLAssertingParty)var1;
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
      BEASAMLAssertingPartyId var3 = (BEASAMLAssertingPartyId)var2;
      int var4 = pcInheritedFieldCount;
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      BEASAMLAssertingPartyId var2 = (BEASAMLAssertingPartyId)var1;
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      BEASAMLAssertingPartyId var3 = (BEASAMLAssertingPartyId)var2;
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      BEASAMLAssertingPartyId var2 = (BEASAMLAssertingPartyId)var1;
   }

   public Object pcNewObjectIdInstance(Object var1) {
      return new BEASAMLAssertingPartyId((String)var1);
   }

   public Object pcNewObjectIdInstance() {
      return new BEASAMLAssertingPartyId();
   }

   private static final String pcGetbeaSAMLAssertionRetrievalURL(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLAssertionRetrievalURL;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLAssertionRetrievalURL;
      }
   }

   private static final void pcSetbeaSAMLAssertionRetrievalURL(BEASAMLAssertingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLAssertionRetrievalURL = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 0, var0.beaSAMLAssertionRetrievalURL, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLAssertionSigningCertAlias(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLAssertionSigningCertAlias;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLAssertionSigningCertAlias;
      }
   }

   private static final void pcSetbeaSAMLAssertionSigningCertAlias(BEASAMLAssertingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLAssertionSigningCertAlias = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 1, var0.beaSAMLAssertionSigningCertAlias, var1, 0);
      }
   }

   private static final Collection pcGetbeaSAMLAudienceURI(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLAudienceURI;
      } else {
         int var1 = pcInheritedFieldCount + 2;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLAudienceURI;
      }
   }

   private static final void pcSetbeaSAMLAudienceURI(BEASAMLAssertingParty var0, Collection var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLAudienceURI = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 2, var0.beaSAMLAudienceURI, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLAuthPassword(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLAuthPassword;
      } else {
         int var1 = pcInheritedFieldCount + 3;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLAuthPassword;
      }
   }

   private static final void pcSetbeaSAMLAuthPassword(BEASAMLAssertingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLAuthPassword = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 3, var0.beaSAMLAuthPassword, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLAuthUsername(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLAuthUsername;
      } else {
         int var1 = pcInheritedFieldCount + 4;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLAuthUsername;
      }
   }

   private static final void pcSetbeaSAMLAuthUsername(BEASAMLAssertingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLAuthUsername = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 4, var0.beaSAMLAuthUsername, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLGroupsAttributeEnabled(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLGroupsAttributeEnabled;
      } else {
         int var1 = pcInheritedFieldCount + 5;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLGroupsAttributeEnabled;
      }
   }

   private static final void pcSetbeaSAMLGroupsAttributeEnabled(BEASAMLAssertingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLGroupsAttributeEnabled = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 5, var0.beaSAMLGroupsAttributeEnabled, var1, 0);
      }
   }

   private static final Collection pcGetbeaSAMLIntersiteTransferParams(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLIntersiteTransferParams;
      } else {
         int var1 = pcInheritedFieldCount + 6;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLIntersiteTransferParams;
      }
   }

   private static final void pcSetbeaSAMLIntersiteTransferParams(BEASAMLAssertingParty var0, Collection var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLIntersiteTransferParams = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 6, var0.beaSAMLIntersiteTransferParams, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLIntersiteTransferURL(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLIntersiteTransferURL;
      } else {
         int var1 = pcInheritedFieldCount + 7;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLIntersiteTransferURL;
      }
   }

   private static final void pcSetbeaSAMLIntersiteTransferURL(BEASAMLAssertingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLIntersiteTransferURL = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 7, var0.beaSAMLIntersiteTransferURL, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLIssuerURI(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLIssuerURI;
      } else {
         int var1 = pcInheritedFieldCount + 8;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLIssuerURI;
      }
   }

   private static final void pcSetbeaSAMLIssuerURI(BEASAMLAssertingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLIssuerURI = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 8, var0.beaSAMLIssuerURI, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLNameMapperClass(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLNameMapperClass;
      } else {
         int var1 = pcInheritedFieldCount + 9;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLNameMapperClass;
      }
   }

   private static final void pcSetbeaSAMLNameMapperClass(BEASAMLAssertingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLNameMapperClass = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 9, var0.beaSAMLNameMapperClass, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLPersistentARSConnEnabled(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLPersistentARSConnEnabled;
      } else {
         int var1 = pcInheritedFieldCount + 10;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLPersistentARSConnEnabled;
      }
   }

   private static final void pcSetbeaSAMLPersistentARSConnEnabled(BEASAMLAssertingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLPersistentARSConnEnabled = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 10, var0.beaSAMLPersistentARSConnEnabled, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLProfile(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLProfile;
      } else {
         int var1 = pcInheritedFieldCount + 11;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLProfile;
      }
   }

   private static final void pcSetbeaSAMLProfile(BEASAMLAssertingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLProfile = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 11, var0.beaSAMLProfile, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLProtocolSigningCertAlias(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLProtocolSigningCertAlias;
      } else {
         int var1 = pcInheritedFieldCount + 12;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLProtocolSigningCertAlias;
      }
   }

   private static final void pcSetbeaSAMLProtocolSigningCertAlias(BEASAMLAssertingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLProtocolSigningCertAlias = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 12, var0.beaSAMLProtocolSigningCertAlias, var1, 0);
      }
   }

   private static final Collection pcGetbeaSAMLRedirectURIs(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLRedirectURIs;
      } else {
         int var1 = pcInheritedFieldCount + 13;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLRedirectURIs;
      }
   }

   private static final void pcSetbeaSAMLRedirectURIs(BEASAMLAssertingParty var0, Collection var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLRedirectURIs = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 13, var0.beaSAMLRedirectURIs, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLSignedAssertions(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLSignedAssertions;
      } else {
         int var1 = pcInheritedFieldCount + 14;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLSignedAssertions;
      }
   }

   private static final void pcSetbeaSAMLSignedAssertions(BEASAMLAssertingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLSignedAssertions = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 14, var0.beaSAMLSignedAssertions, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLSourceId(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLSourceId;
      } else {
         int var1 = pcInheritedFieldCount + 15;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLSourceId;
      }
   }

   private static final void pcSetbeaSAMLSourceId(BEASAMLAssertingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLSourceId = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 15, var0.beaSAMLSourceId, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLTargetURL(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLTargetURL;
      } else {
         int var1 = pcInheritedFieldCount + 16;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLTargetURL;
      }
   }

   private static final void pcSetbeaSAMLTargetURL(BEASAMLAssertingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLTargetURL = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 16, var0.beaSAMLTargetURL, var1, 0);
      }
   }

   private static final String pcGetbeaSAMLVirtualUserEnabled(BEASAMLAssertingParty var0) {
      if (var0.pcStateManager == null) {
         return var0.beaSAMLVirtualUserEnabled;
      } else {
         int var1 = pcInheritedFieldCount + 17;
         var0.pcStateManager.accessingField(var1);
         return var0.beaSAMLVirtualUserEnabled;
      }
   }

   private static final void pcSetbeaSAMLVirtualUserEnabled(BEASAMLAssertingParty var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.beaSAMLVirtualUserEnabled = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 17, var0.beaSAMLVirtualUserEnabled, var1, 0);
      }
   }
}
