package org.python.bouncycastle.asn1.cmp;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1GeneralizedTime;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.crmf.CertId;
import org.python.bouncycastle.asn1.x509.Extensions;

public class RevAnnContent extends ASN1Object {
   private PKIStatus status;
   private CertId certId;
   private ASN1GeneralizedTime willBeRevokedAt;
   private ASN1GeneralizedTime badSinceDate;
   private Extensions crlDetails;

   private RevAnnContent(ASN1Sequence var1) {
      this.status = PKIStatus.getInstance(var1.getObjectAt(0));
      this.certId = CertId.getInstance(var1.getObjectAt(1));
      this.willBeRevokedAt = ASN1GeneralizedTime.getInstance(var1.getObjectAt(2));
      this.badSinceDate = ASN1GeneralizedTime.getInstance(var1.getObjectAt(3));
      if (var1.size() > 4) {
         this.crlDetails = Extensions.getInstance(var1.getObjectAt(4));
      }

   }

   public static RevAnnContent getInstance(Object var0) {
      if (var0 instanceof RevAnnContent) {
         return (RevAnnContent)var0;
      } else {
         return var0 != null ? new RevAnnContent(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public PKIStatus getStatus() {
      return this.status;
   }

   public CertId getCertId() {
      return this.certId;
   }

   public ASN1GeneralizedTime getWillBeRevokedAt() {
      return this.willBeRevokedAt;
   }

   public ASN1GeneralizedTime getBadSinceDate() {
      return this.badSinceDate;
   }

   public Extensions getCrlDetails() {
      return this.crlDetails;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.status);
      var1.add(this.certId);
      var1.add(this.willBeRevokedAt);
      var1.add(this.badSinceDate);
      if (this.crlDetails != null) {
         var1.add(this.crlDetails);
      }

      return new DERSequence(var1);
   }
}
