package weblogic.security.pk;

import com.bea.common.security.SecurityLogger;
import java.security.cert.X509Certificate;

public final class EndCertificateSelector implements CertPathSelector {
   private X509Certificate endCertificate;

   public EndCertificateSelector(X509Certificate endCertificate) {
      if (endCertificate == null) {
         throw new IllegalArgumentException(SecurityLogger.getEndCertificateSelectorIllegalCertificate());
      } else {
         this.endCertificate = endCertificate;
      }
   }

   public X509Certificate getEndCertificate() {
      return this.endCertificate;
   }

   public String toString() {
      return "EndCertificateSelector, endCertificate=" + this.endCertificate;
   }
}
