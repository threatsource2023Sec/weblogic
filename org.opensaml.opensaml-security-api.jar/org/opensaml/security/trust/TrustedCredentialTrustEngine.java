package org.opensaml.security.trust;

import javax.annotation.Nonnull;
import org.opensaml.security.credential.CredentialResolver;

public interface TrustedCredentialTrustEngine extends TrustEngine {
   @Nonnull
   CredentialResolver getCredentialResolver();
}
