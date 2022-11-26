package org.python.bouncycastle.asn1.cmc;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERSequence;

public class TaggedCertificationRequest extends ASN1Object {
   private final BodyPartID bodyPartID;
   private final CertificationRequest certificationRequest;

   public TaggedCertificationRequest(BodyPartID var1, CertificationRequest var2) {
      this.bodyPartID = var1;
      this.certificationRequest = var2;
   }

   private TaggedCertificationRequest(ASN1Sequence var1) {
      if (var1.size() != 2) {
         throw new IllegalArgumentException("incorrect sequence size");
      } else {
         this.bodyPartID = BodyPartID.getInstance(var1.getObjectAt(0));
         this.certificationRequest = CertificationRequest.getInstance(var1.getObjectAt(1));
      }
   }

   public static TaggedCertificationRequest getInstance(Object var0) {
      if (var0 instanceof TaggedCertificationRequest) {
         return (TaggedCertificationRequest)var0;
      } else {
         return var0 != null ? new TaggedCertificationRequest(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public static TaggedCertificationRequest getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.bodyPartID);
      var1.add(this.certificationRequest);
      return new DERSequence(var1);
   }
}
