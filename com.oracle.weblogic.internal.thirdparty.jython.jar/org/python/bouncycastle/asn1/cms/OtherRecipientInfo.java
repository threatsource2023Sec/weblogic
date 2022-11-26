package org.python.bouncycastle.asn1.cms;

import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERSequence;

public class OtherRecipientInfo extends ASN1Object {
   private ASN1ObjectIdentifier oriType;
   private ASN1Encodable oriValue;

   public OtherRecipientInfo(ASN1ObjectIdentifier var1, ASN1Encodable var2) {
      this.oriType = var1;
      this.oriValue = var2;
   }

   /** @deprecated */
   public OtherRecipientInfo(ASN1Sequence var1) {
      this.oriType = ASN1ObjectIdentifier.getInstance(var1.getObjectAt(0));
      this.oriValue = var1.getObjectAt(1);
   }

   public static OtherRecipientInfo getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public static OtherRecipientInfo getInstance(Object var0) {
      if (var0 instanceof OtherRecipientInfo) {
         return (OtherRecipientInfo)var0;
      } else {
         return var0 != null ? new OtherRecipientInfo(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public ASN1ObjectIdentifier getType() {
      return this.oriType;
   }

   public ASN1Encodable getValue() {
      return this.oriValue;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.oriType);
      var1.add(this.oriValue);
      return new DERSequence(var1);
   }
}
