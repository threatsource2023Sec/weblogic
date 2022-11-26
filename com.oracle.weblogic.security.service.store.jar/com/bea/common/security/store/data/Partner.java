package com.bea.common.security.store.data;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.openjpa.enhance.FieldConsumer;
import org.apache.openjpa.enhance.FieldSupplier;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.Reflection;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.util.UserException;

public abstract class Partner extends DomainRealmScope implements PersistenceCapable {
   public static final String SAML2_WEBSSO_SERVICE = "WEBSSOSERVICE";
   public static final String SAML2_ARTIFACT_RESOLUTION_SERVICE = "ARTIFACTRESOLUTIONSERVICE";
   public static final String SAML2_ASSERTION_CONSUMER_SERVICE = "ASSERTIONCONSUMERSERVICE";
   private String description;
   private String name;
   private String issuerURI;
   private boolean enabled;
   private String confirmationMethod;
   private String contactPersonCompany;
   private String contactPersonEmailAddress;
   private String contactPersonGivenName;
   private String contactPersonSurName;
   private String contactPersonTelephoneNumber;
   private String contactPersonType;
   private String entityId;
   private String errorURL;
   private String organizationName;
   private String organizationURL;
   private boolean artifactBindingUsePOST;
   private String artifactBindingPOSTForm;
   private boolean wantArtifactRequestSigned;
   private String postBindingPOSTForm;
   private String partnerType;
   private String clientPasswordEncrypt;
   private String clientUserName;
   private byte[] transportLayerClientCert;
   private byte[] signingCert;
   private boolean clientPasswordSet;
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
   static Class class$Lcom$bea$common$security$store$data$Partner;
   // $FF: synthetic field
   static Class class$Lcom$bea$common$security$store$data$PartnerId;

   public String getClientPasswordEncrypt() {
      return pcGetclientPasswordEncrypt(this);
   }

   public void setClientPasswordEncrypt(String clientPasswordEncrypt) {
      pcSetclientPasswordEncrypt(this, clientPasswordEncrypt);
   }

   public boolean isClientPasswordSet() {
      return pcGetclientPasswordSet(this);
   }

   public void setClientPasswordSet(boolean clientPasswordSet) {
      pcSetclientPasswordSet(this, clientPasswordSet);
   }

   public String getClientUserName() {
      return pcGetclientUserName(this);
   }

   public void setClientUserName(String clientUserName) {
      pcSetclientUserName(this, clientUserName);
   }

   public String getConfirmationMethod() {
      return pcGetconfirmationMethod(this);
   }

   public void setConfirmationMethod(String confirmationMethod) {
      pcSetconfirmationMethod(this, confirmationMethod);
   }

   public String getContactPersonCompany() {
      return pcGetcontactPersonCompany(this);
   }

   public void setContactPersonCompany(String contactPersonCompany) {
      pcSetcontactPersonCompany(this, contactPersonCompany);
   }

   public String getContactPersonEmailAddress() {
      return pcGetcontactPersonEmailAddress(this);
   }

   public void setContactPersonEmailAddress(String contactPersonEmailAddress) {
      pcSetcontactPersonEmailAddress(this, contactPersonEmailAddress);
   }

   public String getContactPersonGivenName() {
      return pcGetcontactPersonGivenName(this);
   }

   public void setContactPersonGivenName(String contactPersonGivenName) {
      pcSetcontactPersonGivenName(this, contactPersonGivenName);
   }

   public String getContactPersonSurName() {
      return pcGetcontactPersonSurName(this);
   }

   public void setContactPersonSurName(String contactPersonSurName) {
      pcSetcontactPersonSurName(this, contactPersonSurName);
   }

   public String getContactPersonTelephoneNumber() {
      return pcGetcontactPersonTelephoneNumber(this);
   }

   public void setContactPersonTelephoneNumber(String contactPersonTelephoneNumber) {
      pcSetcontactPersonTelephoneNumber(this, contactPersonTelephoneNumber);
   }

   public String getContactPersonType() {
      return pcGetcontactPersonType(this);
   }

   public void setContactPersonType(String contactPersonType) {
      pcSetcontactPersonType(this, contactPersonType);
   }

   public String getDescription() {
      return pcGetdescription(this);
   }

   public void setDescription(String description) {
      pcSetdescription(this, description);
   }

   public boolean isEnabled() {
      return pcGetenabled(this);
   }

   public void setEnabled(boolean enabled) {
      pcSetenabled(this, enabled);
   }

   public String getEntityId() {
      return pcGetentityId(this);
   }

   public void setEntityId(String entityId) {
      pcSetentityId(this, entityId);
   }

   public String getErrorURL() {
      return pcGeterrorURL(this);
   }

   public void setErrorURL(String errorURL) {
      pcSeterrorURL(this, errorURL);
   }

   public String getIssuerURI() {
      return pcGetissuerURI(this);
   }

   public void setIssuerURI(String issuerURI) {
      pcSetissuerURI(this, issuerURI);
   }

   public String getName() {
      return pcGetname(this);
   }

   public void setName(String name) {
      pcSetname(this, name);
   }

   public String getOrganizationName() {
      return pcGetorganizationName(this);
   }

   public void setOrganizationName(String organizationName) {
      pcSetorganizationName(this, organizationName);
   }

   public String getOrganizationURL() {
      return pcGetorganizationURL(this);
   }

   public void setOrganizationURL(String organizationURL) {
      pcSetorganizationURL(this, organizationURL);
   }

   public String getPartnerType() {
      return pcGetpartnerType(this);
   }

   public void setPartnerType(String partnerType) {
      pcSetpartnerType(this, partnerType);
   }

   public String getArtifactBindingPOSTForm() {
      return pcGetartifactBindingPOSTForm(this);
   }

   public void setArtifactBindingPOSTForm(String postForm) {
      pcSetartifactBindingPOSTForm(this, postForm);
   }

   public boolean isArtifactBindingUsePOST() {
      return pcGetartifactBindingUsePOST(this);
   }

   public void setArtifactBindingUsePOST(boolean usePOST) {
      pcSetartifactBindingUsePOST(this, usePOST);
   }

   public boolean isWantArtifactRequestSigned() {
      return pcGetwantArtifactRequestSigned(this);
   }

   public void setWantArtifactRequestSigned(boolean wantSigned) {
      pcSetwantArtifactRequestSigned(this, wantSigned);
   }

   public String getPOSTBindingPOSTForm() {
      return pcGetpostBindingPOSTForm(this);
   }

   public void setPOSTBindingPOSTForm(String postForm) {
      pcSetpostBindingPOSTForm(this, postForm);
   }

   public byte[] getSigningCert() {
      return pcGetsigningCert(this);
   }

   public void setSigningCert(byte[] signingCert) {
      pcSetsigningCert(this, signingCert);
   }

   public byte[] getTransportLayerClientCert() {
      return pcGettransportLayerClientCert(this);
   }

   public void setTransportLayerClientCert(byte[] transportLayerClientCert) {
      pcSettransportLayerClientCert(this, transportLayerClientCert);
   }

   public PartnerId getId() {
      return new IdPPartnerId(this.getDomainName(), this.getRealmName(), this.getName());
   }

   public Endpoint[] getArtifactResolutionServices() {
      return this.getTypedServices("ARTIFACTRESOLUTIONSERVICE");
   }

   protected Endpoint[] getTypedServices(String serviceType) {
      ArrayList list = new ArrayList();
      if (this.getServices() != null) {
         Iterator i = this.getServices().iterator();

         while(i.hasNext()) {
            Endpoint endpoint = (Endpoint)i.next();
            if (endpoint.getServiceType().equals(serviceType)) {
               list.add(endpoint);
            }
         }
      }

      return (Endpoint[])list.toArray(new Endpoint[list.size()]);
   }

   public void setSigningCertObject(X509Certificate cert) throws CertificateException {
      this.setSigningCert(this.serializeX509Certificate(cert));
   }

   public X509Certificate getSigningCertObject() throws CertificateException {
      return this.deserializeX509Certificate(this.getSigningCert());
   }

   public void setTransportLayerClientCertObject(X509Certificate cert) throws CertificateException {
      this.setTransportLayerClientCert(this.serializeX509Certificate(cert));
   }

   public X509Certificate getTransportLayerClientCertObject() throws CertificateException {
      return this.deserializeX509Certificate(this.getTransportLayerClientCert());
   }

   protected byte[] serializeX509Certificate(X509Certificate cert) throws CertificateException {
      return cert == null ? null : cert.getEncoded();
   }

   protected X509Certificate deserializeX509Certificate(byte[] binary) throws CertificateException {
      if (binary == null) {
         return null;
      } else {
         CertificateFactory factory = CertificateFactory.getInstance("X.509");
         return (X509Certificate)factory.generateCertificate(new ByteArrayInputStream(binary));
      }
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof Partner)) {
         return false;
      } else {
         Partner p = (Partner)other;
         return pcGetname(this).equals(pcGetname(p));
      }
   }

   public String toString() {
      return "name=" + pcGetname(this) + "," + super.toString();
   }

   public abstract Collection getAudienceURIs();

   public abstract void setAudienceURIs(Collection var1);

   public abstract Collection getServices();

   public abstract void setServices(Collection var1);

   public int pcGetEnhancementContractVersion() {
      return 2;
   }

   static {
      pcPCSuperclass = class$Lcom$bea$common$security$store$data$DomainRealmScope != null ? class$Lcom$bea$common$security$store$data$DomainRealmScope : (class$Lcom$bea$common$security$store$data$DomainRealmScope = class$("com.bea.common.security.store.data.DomainRealmScope"));
      pcFieldNames = new String[]{"artifactBindingPOSTForm", "artifactBindingUsePOST", "clientPasswordEncrypt", "clientPasswordSet", "clientUserName", "confirmationMethod", "contactPersonCompany", "contactPersonEmailAddress", "contactPersonGivenName", "contactPersonSurName", "contactPersonTelephoneNumber", "contactPersonType", "description", "enabled", "entityId", "errorURL", "issuerURI", "name", "organizationName", "organizationURL", "partnerType", "postBindingPOSTForm", "signingCert", "transportLayerClientCert", "wantArtifactRequestSigned"};
      pcFieldTypes = new Class[]{class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), Boolean.TYPE, class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), Boolean.TYPE, class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), Boolean.TYPE, class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$Ljava$lang$String != null ? class$Ljava$lang$String : (class$Ljava$lang$String = class$("java.lang.String")), class$L$B != null ? class$L$B : (class$L$B = class$("[B")), class$L$B != null ? class$L$B : (class$L$B = class$("[B")), Boolean.TYPE};
      pcFieldFlags = new byte[]{26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26};
      PCRegistry.register(class$Lcom$bea$common$security$store$data$Partner != null ? class$Lcom$bea$common$security$store$data$Partner : (class$Lcom$bea$common$security$store$data$Partner = class$("com.bea.common.security.store.data.Partner")), pcFieldNames, pcFieldTypes, pcFieldFlags, pcPCSuperclass, "Partner", (PersistenceCapable)null);
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
      this.artifactBindingPOSTForm = null;
      this.artifactBindingUsePOST = false;
      this.clientPasswordEncrypt = null;
      this.clientPasswordSet = false;
      this.clientUserName = null;
      this.confirmationMethod = null;
      this.contactPersonCompany = null;
      this.contactPersonEmailAddress = null;
      this.contactPersonGivenName = null;
      this.contactPersonSurName = null;
      this.contactPersonTelephoneNumber = null;
      this.contactPersonType = null;
      this.description = null;
      this.enabled = false;
      this.entityId = null;
      this.errorURL = null;
      this.issuerURI = null;
      this.name = null;
      this.organizationName = null;
      this.organizationURL = null;
      this.partnerType = null;
      this.postBindingPOSTForm = null;
      this.signingCert = null;
      this.transportLayerClientCert = null;
      this.wantArtifactRequestSigned = false;
   }

   public PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3) {
      throw new UserException();
   }

   public PersistenceCapable pcNewInstance(StateManager var1, boolean var2) {
      throw new UserException();
   }

   protected static int pcGetManagedFieldCount() {
      return 25 + DomainRealmScope.pcGetManagedFieldCount();
   }

   public void pcReplaceField(int var1) {
      int var2 = var1 - pcInheritedFieldCount;
      if (var2 < 0) {
         super.pcReplaceField(var1);
      } else {
         switch (var2) {
            case 0:
               this.artifactBindingPOSTForm = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 1:
               this.artifactBindingUsePOST = this.pcStateManager.replaceBooleanField(this, var1);
               return;
            case 2:
               this.clientPasswordEncrypt = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 3:
               this.clientPasswordSet = this.pcStateManager.replaceBooleanField(this, var1);
               return;
            case 4:
               this.clientUserName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 5:
               this.confirmationMethod = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 6:
               this.contactPersonCompany = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 7:
               this.contactPersonEmailAddress = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 8:
               this.contactPersonGivenName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 9:
               this.contactPersonSurName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 10:
               this.contactPersonTelephoneNumber = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 11:
               this.contactPersonType = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 12:
               this.description = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 13:
               this.enabled = this.pcStateManager.replaceBooleanField(this, var1);
               return;
            case 14:
               this.entityId = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 15:
               this.errorURL = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 16:
               this.issuerURI = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 17:
               this.name = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 18:
               this.organizationName = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 19:
               this.organizationURL = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 20:
               this.partnerType = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 21:
               this.postBindingPOSTForm = (String)this.pcStateManager.replaceStringField(this, var1);
               return;
            case 22:
               this.signingCert = (byte[])this.pcStateManager.replaceObjectField(this, var1);
               return;
            case 23:
               this.transportLayerClientCert = (byte[])this.pcStateManager.replaceObjectField(this, var1);
               return;
            case 24:
               this.wantArtifactRequestSigned = this.pcStateManager.replaceBooleanField(this, var1);
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
               this.pcStateManager.providedStringField(this, var1, this.artifactBindingPOSTForm);
               return;
            case 1:
               this.pcStateManager.providedBooleanField(this, var1, this.artifactBindingUsePOST);
               return;
            case 2:
               this.pcStateManager.providedStringField(this, var1, this.clientPasswordEncrypt);
               return;
            case 3:
               this.pcStateManager.providedBooleanField(this, var1, this.clientPasswordSet);
               return;
            case 4:
               this.pcStateManager.providedStringField(this, var1, this.clientUserName);
               return;
            case 5:
               this.pcStateManager.providedStringField(this, var1, this.confirmationMethod);
               return;
            case 6:
               this.pcStateManager.providedStringField(this, var1, this.contactPersonCompany);
               return;
            case 7:
               this.pcStateManager.providedStringField(this, var1, this.contactPersonEmailAddress);
               return;
            case 8:
               this.pcStateManager.providedStringField(this, var1, this.contactPersonGivenName);
               return;
            case 9:
               this.pcStateManager.providedStringField(this, var1, this.contactPersonSurName);
               return;
            case 10:
               this.pcStateManager.providedStringField(this, var1, this.contactPersonTelephoneNumber);
               return;
            case 11:
               this.pcStateManager.providedStringField(this, var1, this.contactPersonType);
               return;
            case 12:
               this.pcStateManager.providedStringField(this, var1, this.description);
               return;
            case 13:
               this.pcStateManager.providedBooleanField(this, var1, this.enabled);
               return;
            case 14:
               this.pcStateManager.providedStringField(this, var1, this.entityId);
               return;
            case 15:
               this.pcStateManager.providedStringField(this, var1, this.errorURL);
               return;
            case 16:
               this.pcStateManager.providedStringField(this, var1, this.issuerURI);
               return;
            case 17:
               this.pcStateManager.providedStringField(this, var1, this.name);
               return;
            case 18:
               this.pcStateManager.providedStringField(this, var1, this.organizationName);
               return;
            case 19:
               this.pcStateManager.providedStringField(this, var1, this.organizationURL);
               return;
            case 20:
               this.pcStateManager.providedStringField(this, var1, this.partnerType);
               return;
            case 21:
               this.pcStateManager.providedStringField(this, var1, this.postBindingPOSTForm);
               return;
            case 22:
               this.pcStateManager.providedObjectField(this, var1, this.signingCert);
               return;
            case 23:
               this.pcStateManager.providedObjectField(this, var1, this.transportLayerClientCert);
               return;
            case 24:
               this.pcStateManager.providedBooleanField(this, var1, this.wantArtifactRequestSigned);
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

   protected void pcCopyField(Partner var1, int var2) {
      int var3 = var2 - pcInheritedFieldCount;
      if (var3 < 0) {
         super.pcCopyField(var1, var2);
      } else {
         switch (var3) {
            case 0:
               this.artifactBindingPOSTForm = var1.artifactBindingPOSTForm;
               return;
            case 1:
               this.artifactBindingUsePOST = var1.artifactBindingUsePOST;
               return;
            case 2:
               this.clientPasswordEncrypt = var1.clientPasswordEncrypt;
               return;
            case 3:
               this.clientPasswordSet = var1.clientPasswordSet;
               return;
            case 4:
               this.clientUserName = var1.clientUserName;
               return;
            case 5:
               this.confirmationMethod = var1.confirmationMethod;
               return;
            case 6:
               this.contactPersonCompany = var1.contactPersonCompany;
               return;
            case 7:
               this.contactPersonEmailAddress = var1.contactPersonEmailAddress;
               return;
            case 8:
               this.contactPersonGivenName = var1.contactPersonGivenName;
               return;
            case 9:
               this.contactPersonSurName = var1.contactPersonSurName;
               return;
            case 10:
               this.contactPersonTelephoneNumber = var1.contactPersonTelephoneNumber;
               return;
            case 11:
               this.contactPersonType = var1.contactPersonType;
               return;
            case 12:
               this.description = var1.description;
               return;
            case 13:
               this.enabled = var1.enabled;
               return;
            case 14:
               this.entityId = var1.entityId;
               return;
            case 15:
               this.errorURL = var1.errorURL;
               return;
            case 16:
               this.issuerURI = var1.issuerURI;
               return;
            case 17:
               this.name = var1.name;
               return;
            case 18:
               this.organizationName = var1.organizationName;
               return;
            case 19:
               this.organizationURL = var1.organizationURL;
               return;
            case 20:
               this.partnerType = var1.partnerType;
               return;
            case 21:
               this.postBindingPOSTForm = var1.postBindingPOSTForm;
               return;
            case 22:
               this.signingCert = var1.signingCert;
               return;
            case 23:
               this.transportLayerClientCert = var1.transportLayerClientCert;
               return;
            case 24:
               this.wantArtifactRequestSigned = var1.wantArtifactRequestSigned;
               return;
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public void pcCopyFields(Object var1, int[] var2) {
      Partner var3 = (Partner)var1;
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
      PartnerId var3 = (PartnerId)var2;
      int var4 = pcInheritedFieldCount;
      Reflection.set(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$PartnerId != null ? class$Lcom$bea$common$security$store$data$PartnerId : (class$Lcom$bea$common$security$store$data$PartnerId = class$("com.bea.common.security.store.data.PartnerId")), "name", true), var1.fetchStringField(17 + var4));
   }

   public void pcCopyKeyFieldsToObjectId(Object var1) {
      super.pcCopyKeyFieldsToObjectId(var1);
      PartnerId var2 = (PartnerId)var1;
      Reflection.set(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$PartnerId != null ? class$Lcom$bea$common$security$store$data$PartnerId : (class$Lcom$bea$common$security$store$data$PartnerId = class$("com.bea.common.security.store.data.PartnerId")), "name", true), this.name);
   }

   public void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2) {
      super.pcCopyKeyFieldsFromObjectId(var1, var2);
      PartnerId var3 = (PartnerId)var2;
      var1.storeStringField(17 + pcInheritedFieldCount, (String)Reflection.get(var3, Reflection.findField(class$Lcom$bea$common$security$store$data$PartnerId != null ? class$Lcom$bea$common$security$store$data$PartnerId : (class$Lcom$bea$common$security$store$data$PartnerId = class$("com.bea.common.security.store.data.PartnerId")), "name", true)));
   }

   public void pcCopyKeyFieldsFromObjectId(Object var1) {
      super.pcCopyKeyFieldsFromObjectId(var1);
      PartnerId var2 = (PartnerId)var1;
      this.name = (String)Reflection.get(var2, Reflection.findField(class$Lcom$bea$common$security$store$data$PartnerId != null ? class$Lcom$bea$common$security$store$data$PartnerId : (class$Lcom$bea$common$security$store$data$PartnerId = class$("com.bea.common.security.store.data.PartnerId")), "name", true));
   }

   public Object pcNewObjectIdInstance(Object var1) {
      throw new IllegalArgumentException("The id type \"class com.bea.common.security.store.data.PartnerId\" specfied by persistent type \"class com.bea.common.security.store.data.Partner\" does not have a public string or class + string constructor.");
   }

   public Object pcNewObjectIdInstance() {
      return new PartnerId();
   }

   private static final String pcGetartifactBindingPOSTForm(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.artifactBindingPOSTForm;
      } else {
         int var1 = pcInheritedFieldCount + 0;
         var0.pcStateManager.accessingField(var1);
         return var0.artifactBindingPOSTForm;
      }
   }

   private static final void pcSetartifactBindingPOSTForm(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.artifactBindingPOSTForm = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 0, var0.artifactBindingPOSTForm, var1, 0);
      }
   }

   private static final boolean pcGetartifactBindingUsePOST(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.artifactBindingUsePOST;
      } else {
         int var1 = pcInheritedFieldCount + 1;
         var0.pcStateManager.accessingField(var1);
         return var0.artifactBindingUsePOST;
      }
   }

   private static final void pcSetartifactBindingUsePOST(Partner var0, boolean var1) {
      if (var0.pcStateManager == null) {
         var0.artifactBindingUsePOST = var1;
      } else {
         var0.pcStateManager.settingBooleanField(var0, pcInheritedFieldCount + 1, var0.artifactBindingUsePOST, var1, 0);
      }
   }

   private static final String pcGetclientPasswordEncrypt(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.clientPasswordEncrypt;
      } else {
         int var1 = pcInheritedFieldCount + 2;
         var0.pcStateManager.accessingField(var1);
         return var0.clientPasswordEncrypt;
      }
   }

   private static final void pcSetclientPasswordEncrypt(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.clientPasswordEncrypt = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 2, var0.clientPasswordEncrypt, var1, 0);
      }
   }

   private static final boolean pcGetclientPasswordSet(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.clientPasswordSet;
      } else {
         int var1 = pcInheritedFieldCount + 3;
         var0.pcStateManager.accessingField(var1);
         return var0.clientPasswordSet;
      }
   }

   private static final void pcSetclientPasswordSet(Partner var0, boolean var1) {
      if (var0.pcStateManager == null) {
         var0.clientPasswordSet = var1;
      } else {
         var0.pcStateManager.settingBooleanField(var0, pcInheritedFieldCount + 3, var0.clientPasswordSet, var1, 0);
      }
   }

   private static final String pcGetclientUserName(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.clientUserName;
      } else {
         int var1 = pcInheritedFieldCount + 4;
         var0.pcStateManager.accessingField(var1);
         return var0.clientUserName;
      }
   }

   private static final void pcSetclientUserName(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.clientUserName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 4, var0.clientUserName, var1, 0);
      }
   }

   private static final String pcGetconfirmationMethod(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.confirmationMethod;
      } else {
         int var1 = pcInheritedFieldCount + 5;
         var0.pcStateManager.accessingField(var1);
         return var0.confirmationMethod;
      }
   }

   private static final void pcSetconfirmationMethod(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.confirmationMethod = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 5, var0.confirmationMethod, var1, 0);
      }
   }

   private static final String pcGetcontactPersonCompany(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.contactPersonCompany;
      } else {
         int var1 = pcInheritedFieldCount + 6;
         var0.pcStateManager.accessingField(var1);
         return var0.contactPersonCompany;
      }
   }

   private static final void pcSetcontactPersonCompany(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.contactPersonCompany = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 6, var0.contactPersonCompany, var1, 0);
      }
   }

   private static final String pcGetcontactPersonEmailAddress(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.contactPersonEmailAddress;
      } else {
         int var1 = pcInheritedFieldCount + 7;
         var0.pcStateManager.accessingField(var1);
         return var0.contactPersonEmailAddress;
      }
   }

   private static final void pcSetcontactPersonEmailAddress(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.contactPersonEmailAddress = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 7, var0.contactPersonEmailAddress, var1, 0);
      }
   }

   private static final String pcGetcontactPersonGivenName(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.contactPersonGivenName;
      } else {
         int var1 = pcInheritedFieldCount + 8;
         var0.pcStateManager.accessingField(var1);
         return var0.contactPersonGivenName;
      }
   }

   private static final void pcSetcontactPersonGivenName(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.contactPersonGivenName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 8, var0.contactPersonGivenName, var1, 0);
      }
   }

   private static final String pcGetcontactPersonSurName(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.contactPersonSurName;
      } else {
         int var1 = pcInheritedFieldCount + 9;
         var0.pcStateManager.accessingField(var1);
         return var0.contactPersonSurName;
      }
   }

   private static final void pcSetcontactPersonSurName(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.contactPersonSurName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 9, var0.contactPersonSurName, var1, 0);
      }
   }

   private static final String pcGetcontactPersonTelephoneNumber(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.contactPersonTelephoneNumber;
      } else {
         int var1 = pcInheritedFieldCount + 10;
         var0.pcStateManager.accessingField(var1);
         return var0.contactPersonTelephoneNumber;
      }
   }

   private static final void pcSetcontactPersonTelephoneNumber(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.contactPersonTelephoneNumber = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 10, var0.contactPersonTelephoneNumber, var1, 0);
      }
   }

   private static final String pcGetcontactPersonType(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.contactPersonType;
      } else {
         int var1 = pcInheritedFieldCount + 11;
         var0.pcStateManager.accessingField(var1);
         return var0.contactPersonType;
      }
   }

   private static final void pcSetcontactPersonType(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.contactPersonType = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 11, var0.contactPersonType, var1, 0);
      }
   }

   private static final String pcGetdescription(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.description;
      } else {
         int var1 = pcInheritedFieldCount + 12;
         var0.pcStateManager.accessingField(var1);
         return var0.description;
      }
   }

   private static final void pcSetdescription(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.description = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 12, var0.description, var1, 0);
      }
   }

   private static final boolean pcGetenabled(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.enabled;
      } else {
         int var1 = pcInheritedFieldCount + 13;
         var0.pcStateManager.accessingField(var1);
         return var0.enabled;
      }
   }

   private static final void pcSetenabled(Partner var0, boolean var1) {
      if (var0.pcStateManager == null) {
         var0.enabled = var1;
      } else {
         var0.pcStateManager.settingBooleanField(var0, pcInheritedFieldCount + 13, var0.enabled, var1, 0);
      }
   }

   private static final String pcGetentityId(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.entityId;
      } else {
         int var1 = pcInheritedFieldCount + 14;
         var0.pcStateManager.accessingField(var1);
         return var0.entityId;
      }
   }

   private static final void pcSetentityId(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.entityId = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 14, var0.entityId, var1, 0);
      }
   }

   private static final String pcGeterrorURL(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.errorURL;
      } else {
         int var1 = pcInheritedFieldCount + 15;
         var0.pcStateManager.accessingField(var1);
         return var0.errorURL;
      }
   }

   private static final void pcSeterrorURL(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.errorURL = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 15, var0.errorURL, var1, 0);
      }
   }

   private static final String pcGetissuerURI(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.issuerURI;
      } else {
         int var1 = pcInheritedFieldCount + 16;
         var0.pcStateManager.accessingField(var1);
         return var0.issuerURI;
      }
   }

   private static final void pcSetissuerURI(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.issuerURI = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 16, var0.issuerURI, var1, 0);
      }
   }

   private static final String pcGetname(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.name;
      } else {
         int var1 = pcInheritedFieldCount + 17;
         var0.pcStateManager.accessingField(var1);
         return var0.name;
      }
   }

   private static final void pcSetname(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.name = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 17, var0.name, var1, 0);
      }
   }

   private static final String pcGetorganizationName(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.organizationName;
      } else {
         int var1 = pcInheritedFieldCount + 18;
         var0.pcStateManager.accessingField(var1);
         return var0.organizationName;
      }
   }

   private static final void pcSetorganizationName(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.organizationName = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 18, var0.organizationName, var1, 0);
      }
   }

   private static final String pcGetorganizationURL(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.organizationURL;
      } else {
         int var1 = pcInheritedFieldCount + 19;
         var0.pcStateManager.accessingField(var1);
         return var0.organizationURL;
      }
   }

   private static final void pcSetorganizationURL(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.organizationURL = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 19, var0.organizationURL, var1, 0);
      }
   }

   private static final String pcGetpartnerType(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.partnerType;
      } else {
         int var1 = pcInheritedFieldCount + 20;
         var0.pcStateManager.accessingField(var1);
         return var0.partnerType;
      }
   }

   private static final void pcSetpartnerType(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.partnerType = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 20, var0.partnerType, var1, 0);
      }
   }

   private static final String pcGetpostBindingPOSTForm(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.postBindingPOSTForm;
      } else {
         int var1 = pcInheritedFieldCount + 21;
         var0.pcStateManager.accessingField(var1);
         return var0.postBindingPOSTForm;
      }
   }

   private static final void pcSetpostBindingPOSTForm(Partner var0, String var1) {
      if (var0.pcStateManager == null) {
         var0.postBindingPOSTForm = var1;
      } else {
         var0.pcStateManager.settingStringField(var0, pcInheritedFieldCount + 21, var0.postBindingPOSTForm, var1, 0);
      }
   }

   private static final byte[] pcGetsigningCert(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.signingCert;
      } else {
         int var1 = pcInheritedFieldCount + 22;
         var0.pcStateManager.accessingField(var1);
         return var0.signingCert;
      }
   }

   private static final void pcSetsigningCert(Partner var0, byte[] var1) {
      if (var0.pcStateManager == null) {
         var0.signingCert = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 22, var0.signingCert, var1, 0);
      }
   }

   private static final byte[] pcGettransportLayerClientCert(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.transportLayerClientCert;
      } else {
         int var1 = pcInheritedFieldCount + 23;
         var0.pcStateManager.accessingField(var1);
         return var0.transportLayerClientCert;
      }
   }

   private static final void pcSettransportLayerClientCert(Partner var0, byte[] var1) {
      if (var0.pcStateManager == null) {
         var0.transportLayerClientCert = var1;
      } else {
         var0.pcStateManager.settingObjectField(var0, pcInheritedFieldCount + 23, var0.transportLayerClientCert, var1, 0);
      }
   }

   private static final boolean pcGetwantArtifactRequestSigned(Partner var0) {
      if (var0.pcStateManager == null) {
         return var0.wantArtifactRequestSigned;
      } else {
         int var1 = pcInheritedFieldCount + 24;
         var0.pcStateManager.accessingField(var1);
         return var0.wantArtifactRequestSigned;
      }
   }

   private static final void pcSetwantArtifactRequestSigned(Partner var0, boolean var1) {
      if (var0.pcStateManager == null) {
         var0.wantArtifactRequestSigned = var1;
      } else {
         var0.pcStateManager.settingBooleanField(var0, pcInheritedFieldCount + 24, var0.wantArtifactRequestSigned, var1, 0);
      }
   }
}
