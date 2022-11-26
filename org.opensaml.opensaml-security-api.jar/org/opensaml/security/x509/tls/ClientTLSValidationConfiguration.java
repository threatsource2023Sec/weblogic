package org.opensaml.security.x509.tls;

import javax.annotation.Nullable;
import org.opensaml.security.trust.TrustEngine;

public interface ClientTLSValidationConfiguration {
   @Nullable
   TrustEngine getX509TrustEngine();

   @Nullable
   CertificateNameOptions getCertificateNameOptions();
}
