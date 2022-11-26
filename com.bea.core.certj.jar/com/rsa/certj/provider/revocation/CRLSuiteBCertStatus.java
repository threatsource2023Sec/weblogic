package com.rsa.certj.provider.revocation;

import com.rsa.certj.CertJ;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.Provider;
import com.rsa.certj.ProviderImplementation;
import com.rsa.certj.ProviderManagementException;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.spi.revocation.CertStatusException;
import com.rsa.certj.x.i;

public final class CRLSuiteBCertStatus extends Provider {
   public CRLSuiteBCertStatus(String var1) throws InvalidParameterException {
      super(2, var1);
   }

   public ProviderImplementation instantiate(CertJ var1) throws ProviderManagementException {
      try {
         return new a(var1, this.getName());
      } catch (InvalidParameterException var3) {
         throw new ProviderManagementException("CRLSuiteBCertStatus.instantiate.", var3);
      }
   }

   private final class a extends CRLStatusCommon {
      private a(CertJ var2, String var3) throws InvalidParameterException {
         super(var2, var3);
      }

      public String toString() {
         return "CRL SuiteB Certificate Status provider named: " + super.getName();
      }

      /** @deprecated */
      protected boolean checkCompliance(X509CRL var1) throws CertStatusException {
         return i.a(var1);
      }

      // $FF: synthetic method
      a(CertJ var2, String var3, Object var4) throws InvalidParameterException {
         this(var2, var3);
      }
   }
}
