package org.opensaml.xmlsec.impl;

import javax.annotation.Nullable;
import org.opensaml.xmlsec.SignatureValidationConfiguration;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;

public class BasicSignatureValidationConfiguration extends BasicWhitelistBlacklistConfiguration implements SignatureValidationConfiguration {
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
