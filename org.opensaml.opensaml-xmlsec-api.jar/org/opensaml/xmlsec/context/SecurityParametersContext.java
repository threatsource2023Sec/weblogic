package org.opensaml.xmlsec.context;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.xmlsec.DecryptionParameters;
import org.opensaml.xmlsec.EncryptionParameters;
import org.opensaml.xmlsec.SignatureSigningParameters;
import org.opensaml.xmlsec.SignatureValidationParameters;

public class SecurityParametersContext extends BaseContext {
   @Nullable
   private SignatureSigningParameters signatureSigningParameters;
   @Nullable
   private SignatureValidationParameters signatureValidationParameters;
   @Nullable
   private EncryptionParameters encryptionParameters;
   @Nullable
   private DecryptionParameters decryptionParameters;

   @Nullable
   public SignatureSigningParameters getSignatureSigningParameters() {
      return this.signatureSigningParameters;
   }

   @Nonnull
   public SecurityParametersContext setSignatureSigningParameters(@Nullable SignatureSigningParameters params) {
      this.signatureSigningParameters = params;
      return this;
   }

   @Nullable
   public SignatureValidationParameters getSignatureValidationParameters() {
      return this.signatureValidationParameters;
   }

   @Nonnull
   public SecurityParametersContext setSignatureValidationParameters(@Nullable SignatureValidationParameters params) {
      this.signatureValidationParameters = params;
      return this;
   }

   @Nullable
   public EncryptionParameters getEncryptionParameters() {
      return this.encryptionParameters;
   }

   @Nonnull
   public SecurityParametersContext setEncryptionParameters(@Nullable EncryptionParameters params) {
      this.encryptionParameters = params;
      return this;
   }

   @Nullable
   public DecryptionParameters getDecryptionParameters() {
      return this.decryptionParameters;
   }

   @Nonnull
   public SecurityParametersContext setDecryptionParameters(@Nullable DecryptionParameters params) {
      this.decryptionParameters = params;
      return this;
   }
}
