package com.bea.security.saml2.providers.registry;

import java.io.Serializable;
import java.security.cert.X509Certificate;

public class WebSSOIdPPartnerImpl implements WebSSOIdPPartner, Serializable {
   private static final long serialVersionUID = -5078245771512082862L;
   private String name;
   private IndexedEndpoint[] artifactResolutionService;
   private X509Certificate ssoSigningCert;
   private String[] audienceURIs;
   private String clientPassword;
   private String clientPasswordEncrypted;
   private boolean plainPasswordChanged = false;
   private String clientUsername;
   private String contactPersonCompany;
   private String contactPersonEmailAddress;
   private String contactPersonSurName;
   private String contactPersonTelephoneNumber;
   private String contactPersonType;
   private String contactPersonGivenName;
   private String description;
   private boolean enabled = true;
   private String entityID;
   private String errorURL;
   private boolean wantAuthnRequestsSigned = false;
   private String identityProviderNameMapperClassname;
   private String issuerURI;
   private String organizationName;
   private String organizationURL;
   private boolean processAttributes = true;
   private String[] redirectURIs;
   private Endpoint[] singleSignOnService;
   private X509Certificate transportLayerClientCert;
   private boolean virtualUserEnabled = true;
   private boolean wantAssertionsSigned = false;
   private String artifactPOSTForm;
   private boolean isWantArtifactRequestSigned = false;
   private String postPOSTForm;
   private boolean isArtifactUsePost = false;
   private String originalName = null;

   public IndexedEndpoint[] getArtifactResolutionService() {
      return this.artifactResolutionService;
   }

   public void setArtifactResolutionService(IndexedEndpoint[] artifactResolutionService) {
      this.artifactResolutionService = artifactResolutionService;
   }

   public X509Certificate getSSOSigningCert() {
      return this.ssoSigningCert;
   }

   public void setSSOSigningCert(X509Certificate ssoSigningCert) {
      this.ssoSigningCert = ssoSigningCert;
   }

   public String[] getAudienceURIs() {
      return this.audienceURIs;
   }

   public void setAudienceURIs(String[] audienceURIs) {
      this.audienceURIs = audienceURIs;
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

   public boolean isWantAuthnRequestsSigned() {
      return this.wantAuthnRequestsSigned;
   }

   public void setWantAuthnRequestsSigned(boolean wantAuthnRequestsSigned) {
      this.wantAuthnRequestsSigned = wantAuthnRequestsSigned;
   }

   public String getIdentityProviderNameMapperClassname() {
      return this.identityProviderNameMapperClassname;
   }

   public void setIdentityProviderNameMapperClassname(String identityProviderNameMapperClassname) {
      this.identityProviderNameMapperClassname = identityProviderNameMapperClassname;
   }

   public String getIssuerURI() {
      return this.issuerURI;
   }

   public void setIssuerURI(String issuerURI) {
      this.issuerURI = issuerURI;
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

   public boolean isProcessAttributes() {
      return this.processAttributes;
   }

   public void setProcessAttributes(boolean processAttributes) {
      this.processAttributes = processAttributes;
   }

   public String[] getRedirectURIs() {
      return this.redirectURIs;
   }

   public void setRedirectURIs(String[] redirectURIs) {
      this.redirectURIs = redirectURIs;
   }

   public Endpoint[] getSingleSignOnService() {
      return this.singleSignOnService;
   }

   public void setSingleSignOnService(Endpoint[] singleSignOnService) {
      this.singleSignOnService = singleSignOnService;
   }

   public X509Certificate getTransportLayerClientCert() {
      return this.transportLayerClientCert;
   }

   public void setTransportLayerClientCert(X509Certificate transportLayerClientCert) {
      this.transportLayerClientCert = transportLayerClientCert;
   }

   public boolean isVirtualUserEnabled() {
      return this.virtualUserEnabled;
   }

   public void setVirtualUserEnabled(boolean virtualUserEnabled) {
      this.virtualUserEnabled = virtualUserEnabled;
   }

   public boolean isWantAssertionsSigned() {
      return this.wantAssertionsSigned;
   }

   public void setWantAssertionsSigned(boolean wantAssertionsSigned) {
      this.wantAssertionsSigned = wantAssertionsSigned;
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
