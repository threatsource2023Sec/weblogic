package org.opensaml.security.x509;

import javax.annotation.Nonnull;
import org.opensaml.security.trust.TrustEngine;

public interface PKIXTrustEngine extends TrustEngine {
   @Nonnull
   PKIXValidationInformationResolver getPKIXResolver();
}
