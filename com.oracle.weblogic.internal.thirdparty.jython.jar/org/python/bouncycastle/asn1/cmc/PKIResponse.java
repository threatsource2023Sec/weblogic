package org.python.bouncycastle.asn1.cmc;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERSequence;

public class PKIResponse extends ASN1Object {
   private final ASN1Sequence controlSequence;
   private final ASN1Sequence cmsSequence;
   private final ASN1Sequence otherMsgSequence;

   private PKIResponse(ASN1Sequence var1) {
      if (var1.size() != 3) {
         throw new IllegalArgumentException("incorrect sequence size");
      } else {
         this.controlSequence = ASN1Sequence.getInstance(var1.getObjectAt(0));
         this.cmsSequence = ASN1Sequence.getInstance(var1.getObjectAt(1));
         this.otherMsgSequence = ASN1Sequence.getInstance(var1.getObjectAt(2));
      }
   }

   public static PKIResponse getInstance(Object var0) {
      if (var0 instanceof PKIResponse) {
         return (PKIResponse)var0;
      } else {
         return var0 != null ? new PKIResponse(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public static PKIResponse getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.controlSequence);
      var1.add(this.cmsSequence);
      var1.add(this.otherMsgSequence);
      return new DERSequence(var1);
   }

   public ASN1Sequence getControlSequence() {
      return this.controlSequence;
   }

   public ASN1Sequence getCmsSequence() {
      return this.cmsSequence;
   }

   public ASN1Sequence getOtherMsgSequence() {
      return this.otherMsgSequence;
   }
}
