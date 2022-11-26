package com.bea.security.saml2.providers.registry;

import java.io.Serializable;

public class WSSSPPartnerImpl implements WSSSPPartner, Serializable {
   private String name;
   private String[] audienceURIs;
   private String confirmationMethod = "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches";
   private String description;
   private boolean enabled = true;
   private boolean wantAssertionsSigned = true;
   private boolean generateAttributes = true;
   private boolean includeOneTimeUseCondition = false;
   private boolean keyinfoIncluded = false;
   private String serviceProviderNameMapperClassname;
   private int timeToLive;
   private int timeToLiveOffset;
   private String originalName = null;

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
