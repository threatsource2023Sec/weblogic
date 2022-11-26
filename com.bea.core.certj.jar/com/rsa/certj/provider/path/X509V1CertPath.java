package com.rsa.certj.provider.path;

import com.rsa.certj.CertJ;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.Provider;
import com.rsa.certj.ProviderImplementation;
import com.rsa.certj.ProviderManagementException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.extensions.GeneralNames;
import com.rsa.certj.cert.extensions.GeneralSubtrees;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.spi.path.CertPathException;
import com.rsa.certj.spi.path.CertPathResult;
import com.rsa.certj.x.f;
import java.util.Vector;

/** @deprecated */
public final class X509V1CertPath extends Provider {
   /** @deprecated */
   public X509V1CertPath(String var1) throws InvalidParameterException {
      super(3, var1);
   }

   /** @deprecated */
   public ProviderImplementation instantiate(CertJ var1) throws ProviderManagementException {
      try {
         return new a(var1, this.getName());
      } catch (InvalidParameterException var3) {
         throw new ProviderManagementException("X509V1CertPath.instantiate: ", var3);
      }
   }

   private final class a extends CertPathCommon {
      private a(CertJ var2, String var3) throws InvalidParameterException {
         super(var2, var3);
      }

      protected void getNextCertCandidates(CertPathCtx var1, Object var2, Vector var3) throws CertPathException {
         X500Name var4;
         if (var2 instanceof X509Certificate) {
            X509Certificate var5 = (X509Certificate)var2;
            var4 = var5.getIssuerName();
         } else {
            if (!(var2 instanceof X509CRL)) {
               throw new CertPathException("X509V1CertPath$Implementation.getNextCertCandidates: does not support startObjects other than X509Certificate or X509CRL.");
            }

            X509CRL var6 = (X509CRL)var2;
            var4 = var6.getIssuerName();
         }

         this.findCertBySubject(var1, var4, var3);
      }

      protected boolean verifyPath(CertPathCtx var1, Vector var2, Vector var3, Vector var4, Vector var5, GeneralSubtrees var6, GeneralNames var7, CertPathResult var8) throws CertPathException {
         X509V1CertPathResult var9;
         if (var8 != null && var8 instanceof X509V1CertPathResult) {
            var9 = (X509V1CertPathResult)var8;
         } else {
            var9 = new X509V1CertPathResult();
         }

         Vector var10 = var3 == null ? null : new Vector();
         Vector var11 = var4 == null ? null : new Vector();
         X509Certificate var12 = null;
         int var13 = var2.size() - 1;
         f.a(DEBUG_ON, "Start X509v1 verifying path, using trusted root first.");

         do {
            if (var13 <= 0) {
               CertJUtils.mergeLists(var3, var10);
               CertJUtils.mergeLists(var4, var11);
               var9.setValidationResult(true);
               if (var12 != null) {
                  var9.a(var12);
               }

               return true;
            }

            --var13;
            var12 = (X509Certificate)var2.elementAt(var13);
            if (DEBUG_ON) {
               f.a("Verifying path with certificate " + var12.getSubjectName().toString());
            }
         } while(this.verifyRevocation(var1, var12, var10, var11));

         f.a(DEBUG_ON, "Certificate revocation check failed.");
         var9.setValidationResult(false);
         var9.a("Certificate with subject " + var12.getSubjectName().toString() + " is either revoked or the revocation could not be determined");
         return false;
      }

      public String toString() {
         return "X509V1 Certification Path provider named: " + super.getName();
      }

      /** @deprecated */
      protected CertPathResult createCertPathResult() {
         return new X509V1CertPathResult();
      }

      // $FF: synthetic method
      a(CertJ var2, String var3, Object var4) throws InvalidParameterException {
         this(var2, var3);
      }
   }
}
