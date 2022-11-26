package org.opensaml.xmlsec;

import javax.annotation.Nullable;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;

public class SignatureValidationParameters extends WhitelistBlacklistParameters {
   @Nullable
   private SignatureTrustEngine signatureTrustEngine;

   @Nullable
   public SignatureTrustEngine getSignatureTrustEngine() {
      return this.signatureTrustEngine;
   }

   public void setSignatureTrustEngine(@Nullable SignatureTrustEngine engine) {
      this.signatureTrustEngine = engine;
   }
}
