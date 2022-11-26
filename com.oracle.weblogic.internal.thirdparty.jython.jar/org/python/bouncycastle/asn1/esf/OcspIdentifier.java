package org.python.bouncycastle.asn1.esf;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1GeneralizedTime;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.ocsp.ResponderID;

public class OcspIdentifier extends ASN1Object {
   private ResponderID ocspResponderID;
   private ASN1GeneralizedTime producedAt;

   public static OcspIdentifier getInstance(Object var0) {
      if (var0 instanceof OcspIdentifier) {
         return (OcspIdentifier)var0;
      } else {
         return var0 != null ? new OcspIdentifier(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   private OcspIdentifier(ASN1Sequence var1) {
      if (var1.size() != 2) {
         throw new IllegalArgumentException("Bad sequence size: " + var1.size());
      } else {
         this.ocspResponderID = ResponderID.getInstance(var1.getObjectAt(0));
         this.producedAt = (ASN1GeneralizedTime)var1.getObjectAt(1);
      }
   }

   public OcspIdentifier(ResponderID var1, ASN1GeneralizedTime var2) {
      this.ocspResponderID = var1;
      this.producedAt = var2;
   }

   public ResponderID getOcspResponderID() {
      return this.ocspResponderID;
   }

   public ASN1GeneralizedTime getProducedAt() {
      return this.producedAt;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.ocspResponderID);
      var1.add(this.producedAt);
      return new DERSequence(var1);
   }
}
