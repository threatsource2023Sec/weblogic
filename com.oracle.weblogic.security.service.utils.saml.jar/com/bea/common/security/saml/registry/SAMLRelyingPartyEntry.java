package com.bea.common.security.saml.registry;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.saml.utils.SAMLUtil;
import weblogic.management.utils.InvalidParameterException;
import weblogic.security.providers.saml.registry.SAMLRelyingParty;

public class SAMLRelyingPartyEntry extends SAMLCommonPartnerEntry implements SAMLRelyingParty {
   private static final String RP_ID_PREFIX = "rp";
   private static final String RP_OBJECT_CLASS = "beaSAMLRelyingParty";
   private static final String RP_ATTR_ACS_URL = "beaSAMLAssertionConsumerURL";
   private static final String RP_ATTR_ACS_PARAMS = "beaSAMLAssertionConsumerParams";
   private static final String RP_ATTR_POST_FORM = "beaSAMLPostForm";
   private static final String RP_ATTR_AUTH_SSL_CLIENT_ALIAS = "beaSAMLAuthSSLClientCertAlias";
   private static final String RP_ATTR_TIME_TO_LIVE = "beaSAMLTimeToLive";
   private static final String RP_ATTR_TTL_OFFSET = "beaSAMLTimeToLiveOffset";
   private static final String RP_ATTR_DO_NOT_CACHE = "beaSAMLDoNotCacheCondition";
   private static final String RP_ATTR_KEYINFO_INCLUDED = "beaSAMLKeyinfoIncluded";
   private static final String[] RP_ATTRIBUTES = new String[]{"beaSAMLAssertionConsumerURL", "beaSAMLAssertionConsumerParams", "beaSAMLPostForm", "beaSAMLAuthSSLClientCertAlias", "beaSAMLTimeToLive", "beaSAMLTimeToLiveOffset", "beaSAMLDoNotCacheCondition", "beaSAMLKeyinfoIncluded"};

   protected SAMLRelyingPartyEntry(LoggerSpi logger, LegacyEncryptorSpi encryptionService) {
      super(logger, encryptionService);
      this.setKeyinfoIncluded(true);
   }

   protected static String getPartnerIdPrefix() {
      return "rp";
   }

   protected static String[] getLDAPObjectClasses() {
      return SAMLUtil.mergeArrays(getCommonLDAPObjectClasses(), new String[]{"beaSAMLRelyingParty"});
   }

   protected static String[] getLDAPAttributes() {
      return SAMLUtil.mergeArrays(getCommonLDAPAttributes(), RP_ATTRIBUTES);
   }

   protected static String getSearchFilter(String profile, String target) {
      return null;
   }

   public String getAssertionConsumerURL() {
      return this.getAttribute("beaSAMLAssertionConsumerURL");
   }

   public void setAssertionConsumerURL(String assertionConsumerURL) {
      this.setAttribute("beaSAMLAssertionConsumerURL", assertionConsumerURL);
   }

   public String[] getAssertionConsumerParams() {
      return this.getMultiValuedAttribute("beaSAMLAssertionConsumerParams");
   }

   public void setAssertionConsumerParams(String[] assertionConsumerParams) {
      this.setMultiValuedAttribute("beaSAMLAssertionConsumerParams", assertionConsumerParams);
   }

   public String getPostForm() {
      return this.getAttribute("beaSAMLPostForm");
   }

   public void setPostForm(String postForm) {
      this.setAttribute("beaSAMLPostForm", postForm);
   }

   public String getSSLClientCertAlias() {
      return this.getAttribute("beaSAMLAuthSSLClientCertAlias");
   }

   public void setSSLClientCertAlias(String sslClientCertAlias) {
      this.setAttribute("beaSAMLAuthSSLClientCertAlias", sslClientCertAlias);
   }

   public int getTimeToLive() {
      return this.getIntegerAttribute("beaSAMLTimeToLive");
   }

   public void setTimeToLive(int timeToLive) {
      this.setIntegerAttribute("beaSAMLTimeToLive", timeToLive);
   }

   public int getTimeToLiveOffset() {
      return this.getIntegerAttribute("beaSAMLTimeToLiveOffset");
   }

   public void setTimeToLiveOffset(int timeToLiveOffset) {
      this.setIntegerAttribute("beaSAMLTimeToLiveOffset", timeToLiveOffset);
   }

   public boolean isDoNotCacheCondition() {
      return this.getBooleanAttribute("beaSAMLDoNotCacheCondition");
   }

   public void setDoNotCacheCondition(boolean doNotCacheCondition) {
      this.setBooleanAttribute("beaSAMLDoNotCacheCondition", doNotCacheCondition);
   }

   public boolean isKeyinfoIncluded() {
      return this.getBooleanAttribute("beaSAMLKeyinfoIncluded");
   }

   public void setKeyinfoIncluded(boolean keyinfoIncluded) {
      this.setBooleanAttribute("beaSAMLKeyinfoIncluded", keyinfoIncluded);
   }

   public boolean isCredentialCacheEnabled() {
      return false;
   }

   public void setCredentialCacheEnabled(boolean enabled) {
   }

   public void validate() throws InvalidParameterException {
      super.validate();
      if (this.isEnabled()) {
         if (this.getProfileId() == 1 || this.getProfileId() == 2) {
            String acsURL = this.getAssertionConsumerURL();
            if (acsURL == null) {
               throw new InvalidParameterException("Missing Assertion Consumer URL");
            }

            if (!this.isValidURL(acsURL)) {
               throw new InvalidParameterException("Invalid Assertion Consumer URL");
            }

            if (!this.isValidParameters(this.getAssertionConsumerParams())) {
               throw new InvalidParameterException("Invalid Assertion Consumer Parameters");
            }
         }

         int timeToLive = this.getTimeToLive();
         if (timeToLive < 0) {
            throw new InvalidParameterException("Invalid TimeToLive (less than zero)");
         } else {
            String nameMapper = this.getNameMapperClass();
            if (nameMapper != null && !nameMapper.equals("")) {
               try {
                  Class.forName(nameMapper);
               } catch (ClassNotFoundException var4) {
                  throw new InvalidParameterException("Invalid Name Mapper Class '" + nameMapper + "'");
               }
            }

         }
      }
   }

   public void construct() throws InvalidParameterException {
      super.construct();
      if (this.isEnabled()) {
         ;
      }
   }
}
