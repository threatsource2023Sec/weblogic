package org.opensaml.xmlsec;

import org.opensaml.core.config.ConfigurationService;

public final class SecurityConfigurationSupport {
   private SecurityConfigurationSupport() {
   }

   public static DecryptionConfiguration getGlobalDecryptionConfiguration() {
      return (DecryptionConfiguration)ConfigurationService.get(DecryptionConfiguration.class);
   }

   public static EncryptionConfiguration getGlobalEncryptionConfiguration() {
      return (EncryptionConfiguration)ConfigurationService.get(EncryptionConfiguration.class);
   }

   public static SignatureSigningConfiguration getGlobalSignatureSigningConfiguration() {
      return (SignatureSigningConfiguration)ConfigurationService.get(SignatureSigningConfiguration.class);
   }

   public static SignatureValidationConfiguration getGlobalSignatureValidationConfiguration() {
      return (SignatureValidationConfiguration)ConfigurationService.get(SignatureValidationConfiguration.class);
   }
}
