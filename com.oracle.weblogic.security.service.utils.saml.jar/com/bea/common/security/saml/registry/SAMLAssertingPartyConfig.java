package com.bea.common.security.saml.registry;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import weblogic.management.utils.InvalidParameterException;

public class SAMLAssertingPartyConfig extends SAMLAssertingPartyEntry implements SAMLAssertingPartyRuntime {
   private static final long serialVersionUID = -1038058978469884779L;

   public SAMLAssertingPartyConfig(LoggerSpi logger, LegacyEncryptorSpi encryptionService) {
      super(logger, encryptionService);
   }

   public String getSourceIdHex() {
      return super.getSourceIdHex();
   }

   public byte[] getSourceIdBytes() {
      return super.getSourceIdBytes();
   }

   public boolean isTrustedSender() {
      return this.getBooleanAttribute("V1_COMPAT_TRUSTED_SENDER");
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
