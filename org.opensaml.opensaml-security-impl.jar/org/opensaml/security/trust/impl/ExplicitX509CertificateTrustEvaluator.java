package org.opensaml.security.trust.impl;

import java.security.cert.X509Certificate;
import java.util.Iterator;
import javax.annotation.Nonnull;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.X509Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplicitX509CertificateTrustEvaluator {
   private final Logger log = LoggerFactory.getLogger(ExplicitX509CertificateTrustEvaluator.class);

   public boolean validate(@Nonnull X509Certificate untrustedCertificate, @Nonnull X509Certificate trustedCertificate) {
      return untrustedCertificate.equals(trustedCertificate);
   }

   public boolean validate(@Nonnull X509Certificate untrustedCertificate, @Nonnull Iterable trustedCertificates) {
      Iterator var3 = trustedCertificates.iterator();

      X509Certificate trustedCertificate;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         trustedCertificate = (X509Certificate)var3.next();
      } while(!untrustedCertificate.equals(trustedCertificate));

      return true;
   }

   public boolean validate(@Nonnull X509Credential untrustedCredential, @Nonnull X509Credential trustedCredential) {
      X509Certificate untrustedCertificate = untrustedCredential.getEntityCertificate();
      X509Certificate trustedCertificate = trustedCredential.getEntityCertificate();
      if (untrustedCertificate == null) {
         this.log.debug("Untrusted credential contained no entity certificate, unable to evaluate");
         return false;
      } else if (trustedCertificate == null) {
         this.log.debug("Trusted credential contained no entity certificate, unable to evaluate");
         return false;
      } else if (this.validate(untrustedCertificate, trustedCertificate)) {
         this.log.debug("Successfully validated untrusted credential against trusted certificate");
         return true;
      } else {
         this.log.debug("Failed to validate untrusted credential against trusted certificate");
         return false;
      }
   }

   public boolean validate(@Nonnull X509Credential untrustedCredential, @Nonnull Iterable trustedCredentials) {
      Iterator var3 = trustedCredentials.iterator();

      while(var3.hasNext()) {
         Credential trustedCredential = (Credential)var3.next();
         if (!(trustedCredential instanceof X509Credential)) {
            this.log.debug("Skipping evaluation against trusted, non-X509Credential");
         } else {
            X509Credential trustedX509Credential = (X509Credential)trustedCredential;
            if (this.validate(untrustedCredential, trustedX509Credential)) {
               return true;
            }
         }
      }

      return false;
   }
}
