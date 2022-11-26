package org.python.bouncycastle.cert.cmp;

import org.python.bouncycastle.asn1.cmp.CertConfirmContent;
import org.python.bouncycastle.asn1.cmp.CertStatus;
import org.python.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.python.bouncycastle.operator.DigestAlgorithmIdentifierFinder;

public class CertificateConfirmationContent {
   private DigestAlgorithmIdentifierFinder digestAlgFinder;
   private CertConfirmContent content;

   public CertificateConfirmationContent(CertConfirmContent var1) {
      this(var1, new DefaultDigestAlgorithmIdentifierFinder());
   }

   public CertificateConfirmationContent(CertConfirmContent var1, DigestAlgorithmIdentifierFinder var2) {
      this.digestAlgFinder = var2;
      this.content = var1;
   }

   public CertConfirmContent toASN1Structure() {
      return this.content;
   }

   public CertificateStatus[] getStatusMessages() {
      CertStatus[] var1 = this.content.toCertStatusArray();
      CertificateStatus[] var2 = new CertificateStatus[var1.length];

      for(int var3 = 0; var3 != var2.length; ++var3) {
         var2[var3] = new CertificateStatus(this.digestAlgFinder, var1[var3]);
      }

      return var2;
   }
}
