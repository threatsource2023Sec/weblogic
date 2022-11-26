package com.bea.security.saml2.providers.registry;

import java.io.Serializable;
import java.security.cert.X509Certificate;

public class WSSIdPPartnerImpl implements WSSIdPPartner, Serializable {
   private String name;
   private X509Certificate assertionSigningCert;
   private String[] audienceURIs;
   private String confirmationMethod = "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches";
   private String description;
   private boolean enabled = true;
   private String identityProviderNameMapperClassname;
   private String issuerURI;
   private boolean processAttributes = true;
   private boolean virtualUserEnabled = true;
   private boolean wantAssertionsSigned = false;
   private String originalName = null;

   public X509Certificate getAssertionSigningCert() {
      return this.assertionSigningCert;
   }

   public void setAssertionSigningCert(X509Certificate assertionSigningCert) {
      this.assertionSigningCert = assertionSigningCert;
   }

   public String[] getAudienceURIs() {
      return this.audienceURIs;
   }

   public void setAudienceURIs(String[] audienceURIs) {
      this.audienceURIs = audienceURIs;
   }

   public String getConfirmationMethod() {
      return this.confirmationMethod;
   }

   public void setConfirmationMethod(String confirmationMethod) {
      this.confirmationMethod = confirmationMethod;
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

   public boolean isProcessAttributes() {
      return this.processAttributes;
   }

   public void setProcessAttributes(boolean processAttributes) {
      this.processAttributes = processAttributes;
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
}
