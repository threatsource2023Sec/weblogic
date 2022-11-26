package org.opensaml.xmlsec;

import javax.annotation.Nullable;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;

public interface SignatureValidationConfiguration extends WhitelistBlacklistConfiguration {
   @Nullable
   SignatureTrustEngine getSignatureTrustEngine();
}
