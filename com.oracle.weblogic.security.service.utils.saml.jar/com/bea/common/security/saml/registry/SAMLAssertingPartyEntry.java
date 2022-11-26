package com.bea.common.security.saml.registry;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.saml.utils.SAMLSourceId;
import com.bea.common.security.saml.utils.SAMLUtil;
import weblogic.management.utils.InvalidParameterException;
import weblogic.security.providers.saml.registry.SAMLAssertingParty;

public class SAMLAssertingPartyEntry extends SAMLCommonPartnerEntry implements SAMLAssertingParty {
   private static final String AP_ID_PREFIX = "ap";
   private static final String AP_OBJECT_CLASS = "beaSAMLAssertingParty";
   private static final String AP_ATTR_ISSUER_URI = "beaSAMLIssuerURI";
   private static final String AP_ATTR_SOURCE_ID = "beaSAMLSourceId";
   private static final String AP_ATTR_ARS_URL = "beaSAMLAssertionRetrievalURL";
   private static final String AP_ATTR_ITS_URL = "beaSAMLIntersiteTransferURL";
   private static final String AP_ATTR_ITS_PARAMS = "beaSAMLIntersiteTransferParams";
   private static final String AP_ATTR_REDIRECT_URIS = "beaSAMLRedirectURIs";
   private static final String AP_ATTR_ASSN_SIGN_ALIAS = "beaSAMLAssertionSigningCertAlias";
   private static final String AP_ATTR_PRTCL_SIGN_ALIAS = "beaSAMLProtocolSigningCertAlias";
   private static final String AP_ATTR_VIRTUAL_USER_ENABLED = "beaSAMLVirtualUserEnabled";
   private static final String[] AP_ATTRIBUTES = new String[]{"beaSAMLIssuerURI", "beaSAMLSourceId", "beaSAMLAssertionRetrievalURL", "beaSAMLIntersiteTransferURL", "beaSAMLIntersiteTransferParams", "beaSAMLRedirectURIs", "beaSAMLAssertionSigningCertAlias", "beaSAMLProtocolSigningCertAlias", "beaSAMLVirtualUserEnabled"};
   private SAMLSourceId sourceId = null;

   protected SAMLAssertingPartyEntry(LoggerSpi logger, LegacyEncryptorSpi encryptionService) {
      super(logger, encryptionService);
   }

   protected static String getPartnerIdPrefix() {
      return "ap";
   }

   protected static String[] getLDAPObjectClasses() {
      return SAMLUtil.mergeArrays(getCommonLDAPObjectClasses(), new String[]{"beaSAMLAssertingParty"});
   }

   protected static String[] getLDAPAttributes() {
      return SAMLUtil.mergeArrays(getCommonLDAPAttributes(), AP_ATTRIBUTES);
   }

   protected static String getSearchFilter(String profile, String issuer, String target) {
      return null;
   }

   public String getIssuerURI() {
      return this.getAttribute("beaSAMLIssuerURI");
   }

   public void setIssuerURI(String issuerURI) {
      this.setAttribute("beaSAMLIssuerURI", issuerURI);
   }

   public String getAssertionRetrievalURL() {
      return this.getAttribute("beaSAMLAssertionRetrievalURL");
   }

   public void setAssertionRetrievalURL(String assertionRetrievalURL) {
      this.setAttribute("beaSAMLAssertionRetrievalURL", assertionRetrievalURL);
   }

   public String getSourceId() {
      return this.getAttribute("beaSAMLSourceId");
   }

   public void setSourceId(String sourceId) {
      this.setAttribute("beaSAMLSourceId", sourceId);
   }

   public String getIntersiteTransferURL() {
      return this.getAttribute("beaSAMLIntersiteTransferURL");
   }

   public void setIntersiteTransferURL(String intersiteTransferURL) {
      this.setAttribute("beaSAMLIntersiteTransferURL", intersiteTransferURL);
   }

   public String[] getIntersiteTransferParams() {
      return this.getMultiValuedAttribute("beaSAMLIntersiteTransferParams");
   }

   public void setIntersiteTransferParams(String[] intersiteTransferParams) {
      this.setMultiValuedAttribute("beaSAMLIntersiteTransferParams", intersiteTransferParams);
   }

   public String[] getRedirectURIs() {
      return this.getMultiValuedAttribute("beaSAMLRedirectURIs");
   }

   public void setRedirectURIs(String[] redirectURIs) {
      this.setMultiValuedAttribute("beaSAMLRedirectURIs", redirectURIs);
   }

   public String getAssertionSigningCertAlias() {
      return this.getAttribute("beaSAMLAssertionSigningCertAlias");
   }

   public void setAssertionSigningCertAlias(String assertionSigningCertAlias) {
      this.setAttribute("beaSAMLAssertionSigningCertAlias", assertionSigningCertAlias);
   }

   public String getProtocolSigningCertAlias() {
      return this.getAttribute("beaSAMLProtocolSigningCertAlias");
   }

   public void setProtocolSigningCertAlias(String protocolSigningCertAlias) {
      this.setAttribute("beaSAMLProtocolSigningCertAlias", protocolSigningCertAlias);
   }

   public boolean isVirtualUserEnabled() {
      return this.getBooleanAttribute("beaSAMLVirtualUserEnabled");
   }

   public void setVirtualUserEnabled(boolean virtualUserEnabled) {
      this.setBooleanAttribute("beaSAMLVirtualUserEnabled", virtualUserEnabled);
   }

   protected String getSourceIdHex() {
      return this.sourceId.getSourceIdHex();
   }

   protected byte[] getSourceIdBytes() {
      return this.sourceId.getSourceIdBytes();
   }

   public void validate() throws InvalidParameterException {
      super.validate();
      if (this.isEnabled()) {
         String issuer = this.getIssuerURI();
         if (!this.isValidURI(issuer)) {
            throw new InvalidParameterException("Missing/Invalid Issuer URI");
         } else {
            String itsURL;
            if (this.getProfileId() == 2) {
               itsURL = this.getAssertionRetrievalURL();
               if (itsURL == null) {
                  throw new InvalidParameterException("Missing Assertion Retrieval URL");
               }

               if (!this.isValidURL(itsURL)) {
                  throw new InvalidParameterException("Invalid Assertion Retrieval URL");
               }

               this.constructSourceId();
            }

            itsURL = this.getIntersiteTransferURL();
            if (itsURL != null) {
               if (!this.isValidURL(itsURL)) {
                  throw new InvalidParameterException("Invalid Intersite Transfer URL");
               }

               if (!this.isValidParameters(this.getIntersiteTransferParams())) {
                  throw new InvalidParameterException("Invalid Intersite Retrieval Parameters");
               }
            }

            String[] redirectURIs = this.getRedirectURIs();
            if (redirectURIs != null && redirectURIs.length > 0) {
               if (itsURL == null) {
                  throw new InvalidParameterException("Redirect URIs configured without an ITS URL");
               }

               for(int i = 0; i < redirectURIs.length; ++i) {
                  if (redirectURIs[i] != null && !this.isValidContextPath(redirectURIs[i])) {
                     throw new InvalidParameterException("Invalid redirect URI '" + redirectURIs[i] + "'");
                  }
               }

               this.setRedirectURIs(redirectURIs);
            }

            String alias;
            if (!this.isV1Config()) {
               if (this.getProfileId() == 1) {
                  alias = this.getProtocolSigningCertAlias();
                  if (alias == null) {
                     throw new InvalidParameterException("Missing Protocol Signing Certificate Alias");
                  }

                  if (!this.isValidCertAlias(alias)) {
                     throw new InvalidParameterException("Invalid Protocol Signing Certificate Alias '" + alias + "'");
                  }
               }

               if (this.isSignedAssertions()) {
                  alias = this.getAssertionSigningCertAlias();
                  if (alias == null) {
                     throw new InvalidParameterException("Missing Assertion Signing Certificate Alias");
                  }

                  if (!this.isValidCertAlias(alias)) {
                     throw new InvalidParameterException("Invalid Assertion Signing Certificate Alias '" + alias + "'");
                  }
               }
            }

            alias = this.getNameMapperClass();
            if (alias != null && !alias.equals("")) {
               try {
                  Class.forName(alias);
               } catch (ClassNotFoundException var6) {
                  throw new InvalidParameterException("Invalid Name Mapper Class '" + alias + "'");
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

   private void constructSourceId() throws InvalidParameterException {
      String sourceIdString = this.getSourceId();
      if (sourceIdString == null) {
         throw new InvalidParameterException("Missing Source ID");
      } else {
         try {
            if (this.isV1Config() && (sourceIdString.startsWith("http:") || sourceIdString.startsWith("https:"))) {
               this.sourceId = new SAMLSourceId(1, sourceIdString);
            } else if (sourceIdString.length() == 40) {
               this.sourceId = new SAMLSourceId(2, sourceIdString);
            } else {
               this.sourceId = new SAMLSourceId(3, sourceIdString);
            }

         } catch (IllegalArgumentException var3) {
            this.sourceId = null;
            throw new InvalidParameterException("Invalid Source ID");
         }
      }
   }
}
