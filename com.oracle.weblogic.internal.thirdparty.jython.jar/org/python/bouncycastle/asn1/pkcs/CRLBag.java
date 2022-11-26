package org.python.bouncycastle.asn1.pkcs;

import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;

public class CRLBag extends ASN1Object {
   private ASN1ObjectIdentifier crlId;
   private ASN1Encodable crlValue;

   private CRLBag(ASN1Sequence var1) {
      this.crlId = (ASN1ObjectIdentifier)var1.getObjectAt(0);
      this.crlValue = ((ASN1TaggedObject)var1.getObjectAt(1)).getObject();
   }

   public static CRLBag getInstance(Object var0) {
      if (var0 instanceof CRLBag) {
         return (CRLBag)var0;
      } else {
         return var0 != null ? new CRLBag(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public CRLBag(ASN1ObjectIdentifier var1, ASN1Encodable var2) {
      this.crlId = var1;
      this.crlValue = var2;
   }

   public ASN1ObjectIdentifier getCrlId() {
      return this.crlId;
   }

   public ASN1Encodable getCrlValue() {
      return this.crlValue;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.crlId);
      var1.add(new DERTaggedObject(0, this.crlValue));
      return new DERSequence(var1);
   }
}
