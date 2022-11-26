package weblogic.security.pki.revocation.common;

import java.security.cert.X509Certificate;

abstract class AbstractRevocChecker {
   private final AbstractCertRevocContext context;

   AbstractRevocChecker(AbstractCertRevocContext context) {
      if (null == context) {
         throw new IllegalArgumentException("Non-null AbstractCertRevocContext expected.");
      } else {
         this.context = context;
      }
   }

   final AbstractCertRevocContext getContext() {
      return this.context;
   }

   abstract CertRevocStatus getCertRevocStatus(X509Certificate var1, X509Certificate var2);
}
