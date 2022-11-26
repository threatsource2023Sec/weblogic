package weblogic.security.pk;

import com.bea.common.security.ApiLogger;

public class X509ThumbprintSelector implements CertPathSelector {
   private String x509Thumbprint;

   public X509ThumbprintSelector(String thumbprint) {
      if (thumbprint != null && thumbprint.length() >= 1) {
         this.x509Thumbprint = thumbprint;
      } else {
         throw new IllegalArgumentException(ApiLogger.getIllegalArgumentSpecified("X509ThumbprintSelector", "X.509 certificate thumbprint", "null or empty"));
      }
   }

   public String getX509Thumbprint() {
      return this.x509Thumbprint;
   }

   public String toString() {
      return "X509ThumbprintSelector, x509Thumbprint=" + this.x509Thumbprint;
   }
}
