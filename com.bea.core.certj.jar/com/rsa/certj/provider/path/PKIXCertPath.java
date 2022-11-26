package com.rsa.certj.provider.path;

import com.rsa.certj.CertJ;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.Provider;
import com.rsa.certj.ProviderImplementation;
import com.rsa.certj.ProviderManagementException;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.jsafe.JSAFE_PublicKey;

/** @deprecated */
public final class PKIXCertPath extends Provider {
   /** @deprecated */
   public PKIXCertPath(String var1) throws InvalidParameterException {
      super(3, var1);
   }

   /** @deprecated */
   public ProviderImplementation instantiate(CertJ var1) throws ProviderManagementException {
      try {
         return new a(var1, this.getName());
      } catch (InvalidParameterException var3) {
         throw new ProviderManagementException(var3);
      }
   }

   private final class a extends PKIXCertPathCommon {
      private a(CertJ var2, String var3) throws InvalidParameterException {
         super(var2, var3);
      }

      public String toString() {
         return "PKIX Certification Path provider named: " + super.getName();
      }

      /** @deprecated */
      protected boolean checkCompliance(X509Certificate var1, JSAFE_PublicKey var2) {
         return true;
      }

      /** @deprecated */
      protected String getCRLComplianceFailedMessage() {
         return ".";
      }

      // $FF: synthetic method
      a(CertJ var2, String var3, Object var4) throws InvalidParameterException {
         this(var2, var3);
      }
   }
}
