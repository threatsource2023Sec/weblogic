package org.python.bouncycastle.cert.path.validations;

import java.util.Collection;
import java.util.Iterator;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.cert.X509CRLHolder;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.cert.path.CertPathValidation;
import org.python.bouncycastle.cert.path.CertPathValidationContext;
import org.python.bouncycastle.cert.path.CertPathValidationException;
import org.python.bouncycastle.util.Memoable;
import org.python.bouncycastle.util.Selector;
import org.python.bouncycastle.util.Store;

public class CRLValidation implements CertPathValidation {
   private Store crls;
   private X500Name workingIssuerName;

   public CRLValidation(X500Name var1, Store var2) {
      this.workingIssuerName = var1;
      this.crls = var2;
   }

   public void validate(CertPathValidationContext var1, X509CertificateHolder var2) throws CertPathValidationException {
      Collection var3 = this.crls.getMatches(new Selector() {
         public boolean match(Object var1) {
            X509CRLHolder var2 = (X509CRLHolder)var1;
            return var2.getIssuer().equals(CRLValidation.this.workingIssuerName);
         }

         public Object clone() {
            return this;
         }
      });
      if (var3.isEmpty()) {
         throw new CertPathValidationException("CRL for " + this.workingIssuerName + " not found");
      } else {
         Iterator var4 = var3.iterator();

         X509CRLHolder var5;
         do {
            if (!var4.hasNext()) {
               this.workingIssuerName = var2.getSubject();
               return;
            }

            var5 = (X509CRLHolder)var4.next();
         } while(var5.getRevokedCertificate(var2.getSerialNumber()) == null);

         throw new CertPathValidationException("Certificate revoked");
      }
   }

   public Memoable copy() {
      return new CRLValidation(this.workingIssuerName, this.crls);
   }

   public void reset(Memoable var1) {
      CRLValidation var2 = (CRLValidation)var1;
      this.workingIssuerName = var2.workingIssuerName;
      this.crls = var2.crls;
   }
}
