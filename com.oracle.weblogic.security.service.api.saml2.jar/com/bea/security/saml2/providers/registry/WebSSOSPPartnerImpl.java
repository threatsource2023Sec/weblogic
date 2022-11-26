package com.bea.security.saml2.providers.registry;

import java.io.Serializable;
import java.security.cert.X509Certificate;
import java.util.List;

public class WebSSOSPPartnerImpl implements WebSSOSPPartner, Serializable {
   private static final long serialVersionUID = -3900627128562860967L;
   private String name;
   private IndexedEndpoint[] artifactResolutionService;
   private IndexedEndpoint[] assertionConsumerService;
   private String[] audienceURIs;
   private X509Certificate ssoSigningCert;
   private String clientPassword;
   private String clientPasswordEncrypted;
   private boolean plainPasswordChanged = false;
   private String clientUsername;
   private String contactPersonCompany;
   private String contactPersonEmailAddress;
   private String contactPersonGivenName;
   private String contactPersonSurName;
   private String contactPersonTelephoneNumber;
   private String contactPersonType;
   private String description;
   private boolean enabled = true;
   private String entityID;
   private String errorURL;
   private boolean wantAssertionsSigned = true;
   private boolean generateAttributes = true;
   private boolean includeOneTimeUseCondition = false;
   private boolean keyinfoIncluded = false;
   private String organizationName;
   private String organizationURL;
   private String serviceProviderNameMapperClassname;
   private int timeToLive;
   private int timeToLiveOffset;
   private X509Certificate transportLayerClientCert;
   private boolean wantAuthnRequestsSigned = false;
   private String artifactPOSTForm;
   private boolean isWantArtifactRequestSigned = false;
   private String postPOSTForm;
   private boolean isArtifactUsePost = false;
   private String originalName = null;
   private X509Certificate assertionEncryptionCertificate;
   private List encryptionAlgorithms;

   public IndexedEndpoint[] getArtifactResolutionService() {
      return this.artifactResolutionService;
   }

   public void setArtifactResolutionService(IndexedEndpoint[] artifactResolutionService) {
      this.artifactResolutionService = artifactResolutionService;
   }

   public IndexedEndpoint[] getAssertionConsumerService() {
      return this.assertionConsumerService;
   }

   public void setAssertionConsumerService(IndexedEndpoint[] assertionConsumerService) {
      this.assertionConsumerService = assertionConsumerService;
   }

   public String[] getAudienceURIs() {
      return this.audienceURIs;
   }

   public void setAudienceURIs(String[] audienceURIs) {
      this.audienceURIs = audienceURIs;
   }

   public X509Certificate getSSOSigningCert() {
      return this.ssoSigningCert;
   }

   public void setSSOSigningCert(X509Certificate ssoSigningCert) {
      this.ssoSigningCert = ssoSigningCert;
   }

   public String getClientPassword() {
      return this.clientPassword;
   }

   public void setClientPassword(String clientPassword) {
      this.clientPassword = clientPassword;
      this.plainPasswordChanged = true;
   }

   public String getClientUsername() {
      return this.clientUsername;
   }

   public void setClientUsername(String clientUsername) {
      this.clientUsername = clientUsername;
   }

   public String getContactPersonCompany() {
      return this.contactPersonCompany;
   }

   public void setContactPersonCompany(String contactPersonCompany) {
      this.contactPersonCompany = contactPersonCompany;
   }

   public String getContactPersonEmailAddress() {
      return this.contactPersonEmailAddress;
   }

   public void setContactPersonEmailAddress(String contactPersonEmailAddress) {
      this.contactPersonEmailAddress = contactPersonEmailAddress;
   }

   public String getContactPersonGivenName() {
      return this.contactPersonGivenName;
   }

   public void setContactPersonGivenName(String contactPersonGivenName) {
      this.contactPersonGivenName = contactPersonGivenName;
   }

   public String getContactPersonSurName() {
      return this.contactPersonSurName;
   }

   public void setContactPersonSurName(String contactPersonSurName) {
      this.contactPersonSurName = contactPersonSurName;
   }

   public String getContactPersonTelephoneNumber() {
      return this.contactPersonTelephoneNumber;
   }

   public void setContactPersonTelephoneNumber(String contactPersonTelephoneNumber) {
      this.contactPersonTelephoneNumber = contactPersonTelephoneNumber;
   }

   public String getContactPersonType() {
      return this.contactPersonType;
   }

   public void setContactPersonType(String contactPersonType) {
      this.contactPersonType = contactPersonType;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public String getEntityID() {
      return this.entityID;
   }

   public void setEntityID(String entityID) {
      this.entityID = entityID;
   }

   public String getErrorURL() {
      return this.errorURL;
   }

   public void setErrorURL(String errorURL) {
      this.errorURL = errorURL;
   }

   public boolean isWantAssertionsSigned() {
      return this.wantAssertionsSigned;
   }

   public void setWantAssertionsSigned(boolean wantAssertionsSigned) {
      this.wantAssertionsSigned = wantAssertionsSigned;
   }

   public boolean isGenerateAttributes() {
      return this.generateAttributes;
   }

   public void setGenerateAttributes(boolean generateAttributes) {
      this.generateAttributes = generateAttributes;
   }

   public boolean isIncludeOneTimeUseCondition() {
      return this.includeOneTimeUseCondition;
   }

   public void setIncludeOneTimeUseCondition(boolean includeOneTimeUseCondition) {
      this.includeOneTimeUseCondition = includeOneTimeUseCondition;
   }

   public boolean isKeyinfoIncluded() {
      return this.keyinfoIncluded;
   }

   public void setKeyinfoIncluded(boolean keyinfoIncluded) {
      this.keyinfoIncluded = keyinfoIncluded;
   }

   public String getOrganizationName() {
      return this.organizationName;
   }

   public void setOrganizationName(String organizationName) {
      this.organizationName = organizationName;
   }

   public String getOrganizationURL() {
      return this.organizationURL;
   }

   public void setOrganizationURL(String organizationURL) {
      this.organizationURL = organizationURL;
   }

   public String getServiceProviderNameMapperClassname() {
      return this.serviceProviderNameMapperClassname;
   }

   public void setServiceProviderNameMapperClassname(String serviceProviderNameMapperClassname) {
      this.serviceProviderNameMapperClassname = serviceProviderNameMapperClassname;
   }

   public int getTimeToLive() {
      return this.timeToLive;
   }

   public void setTimeToLive(int timeToLive) {
      this.timeToLive = timeToLive;
   }

   public int getTimeToLiveOffset() {
      return this.timeToLiveOffset;
   }

   public void setTimeToLiveOffset(int timeToLiveOffset) {
      this.timeToLiveOffset = timeToLiveOffset;
   }

   public X509Certificate getTransportLayerClientCert() {
      return this.transportLayerClientCert;
   }

   public void setTransportLayerClientCert(X509Certificate transportLayerClientCert) {
      this.transportLayerClientCert = transportLayerClientCert;
   }

   public boolean isWantAuthnRequestsSigned() {
      return this.wantAuthnRequestsSigned;
   }

   public void setWantAuthnRequestsSigned(boolean wantAuthnRequestsSigned) {
      this.wantAuthnRequestsSigned = wantAuthnRequestsSigned;
   }

   public boolean isNameModified() {
      return this.originalName != null && !this.originalName.equals(this.name);
   }

   public void setName(String name) {
      this.name = name;
      if (this.originalName == null) {
         this.originalName = name;
      }

   }

   public String getName() {
      return this.name;
   }

   public String getClientPasswordEncrypted() {
      return this.clientPasswordEncrypted;
   }

   public boolean isClientPasswordSet() {
      if (this.clientPassword != null && this.clientPassword.trim().length() > 0) {
         return true;
      } else {
         return this.clientPasswordEncrypted != null && this.clientPasswordEncrypted.trim().length() > 0;
      }
   }

   public String getArtifactBindingPostForm() {
      return this.artifactPOSTForm;
   }

   public void setArtifactBindingPostForm(String postForm) {
      this.artifactPOSTForm = postForm;
   }

   public String getPostBindingPostForm() {
      return this.postPOSTForm;
   }

   public void setPostBindingPostForm(String postForm) {
      this.postPOSTForm = postForm;
   }

   public X509Certificate getAssertionEncryptionCert() {
      return this.assertionEncryptionCertificate;
   }

   public void setAssertionEncryptionCert(X509Certificate assertionEncryptionCert) {
      this.assertionEncryptionCertificate = assertionEncryptionCert;
   }

   public List getEncryptionAlgorithms() {
      return this.encryptionAlgorithms;
   }

   public void setEncryptionAlgorithms(List encryptionAlgorithms) {
      this.encryptionAlgorithms = encryptionAlgorithms;
   }

   public boolean isWantArtifactRequestSigned() {
      return this.isWantArtifactRequestSigned;
   }

   public void setWantArtifactRequestSigned(boolean wantSigned) {
      this.isWantArtifactRequestSigned = wantSigned;
   }

   public boolean isArtifactBindingUsePOSTMethod() {
      return this.isArtifactUsePost;
   }

   public void setArtifactBindingUsePOSTMethod(boolean isArtifactUsePost) {
      this.isArtifactUsePost = isArtifactUsePost;
   }

   public void setClientPasswordEncrypted(String clientPasswordEncrypted) {
      this.clientPasswordEncrypted = clientPasswordEncrypted;
      this.plainPasswordChanged = false;
   }

   public boolean isPlainPasswordChanged() {
      return this.plainPasswordChanged;
   }
}
