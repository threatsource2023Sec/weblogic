package org.opensaml.security.x509.tls.impl;

import javax.annotation.Nullable;
import org.opensaml.security.trust.TrustEngine;
import org.opensaml.security.x509.tls.CertificateNameOptions;
import org.opensaml.security.x509.tls.ClientTLSValidationConfiguration;

public class BasicClientTLSValidationConfiguration implements ClientTLSValidationConfiguration {
   @Nullable
   private TrustEngine x509TrustEngine;
   @Nullable
   private CertificateNameOptions certificateNameOptions;

   @Nullable
   public TrustEngine getX509TrustEngine() {
      return this.x509TrustEngine;
   }

   public void setX509TrustEngine(@Nullable TrustEngine engine) {
      this.x509TrustEngine = engine;
   }

   @Nullable
   public CertificateNameOptions getCertificateNameOptions() {
      return this.certificateNameOptions;
   }

   public void setCertificateNameOptions(@Nullable CertificateNameOptions options) {
      this.certificateNameOptions = options;
   }
}
