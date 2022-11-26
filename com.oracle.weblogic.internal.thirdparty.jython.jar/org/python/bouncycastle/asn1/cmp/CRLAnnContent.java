package org.python.bouncycastle.asn1.cmp;

import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.x509.CertificateList;

public class CRLAnnContent extends ASN1Object {
   private ASN1Sequence content;

   private CRLAnnContent(ASN1Sequence var1) {
      this.content = var1;
   }

   public static CRLAnnContent getInstance(Object var0) {
      if (var0 instanceof CRLAnnContent) {
         return (CRLAnnContent)var0;
      } else {
         return var0 != null ? new CRLAnnContent(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public CRLAnnContent(CertificateList var1) {
      this.content = new DERSequence(var1);
   }

   public CertificateList[] getCertificateLists() {
      CertificateList[] var1 = new CertificateList[this.content.size()];

      for(int var2 = 0; var2 != var1.length; ++var2) {
         var1[var2] = CertificateList.getInstance(this.content.getObjectAt(var2));
      }

      return var1;
   }

   public ASN1Primitive toASN1Primitive() {
      return this.content;
   }
}
