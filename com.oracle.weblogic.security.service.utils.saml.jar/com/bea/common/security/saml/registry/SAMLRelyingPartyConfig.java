package com.bea.common.security.saml.registry;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import weblogic.management.utils.InvalidParameterException;

public class SAMLRelyingPartyConfig extends SAMLRelyingPartyEntry implements SAMLRelyingPartyRuntime {
   private static final long serialVersionUID = 7560420135778879431L;

   public SAMLRelyingPartyConfig(LoggerSpi logger, LegacyEncryptorSpi encryptionService) {
      super(logger, encryptionService);
   }

   public int getProfileId() {
      return super.getProfileId();
   }

   public String getProfileConfMethodName() {
      return super.getProfileConfMethodName();
   }

   public String getProfileConfMethodURN() {
      return super.getProfileConfMethodURN();
   }

   public boolean isWildcardTarget() {
      return super.isWildcardTarget();
   }

   public boolean isDefaultTarget() {
      return super.isDefaultTarget();
   }

   public String getARSPassword() {
      return super.getARSPassword();
   }

   public String[] getAudienceURIs() {
      return super.getAudienceURIs();
   }

   public void validate() throws InvalidParameterException {
      super.validate();
      if (this.isEnabled()) {
         ;
      }
   }

   public void handleEncryption(boolean isInbound) {
      super.handleEncryption(isInbound);
   }

   public void construct() throws InvalidParameterException {
      this.validate();
      super.construct();
      if (this.isEnabled()) {
         this.logDebug("construct", "Constructed partner '" + this.getPartnerId() + "'");
      }
   }
}
