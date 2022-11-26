package org.python.bouncycastle.asn1.pkcs;

import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;

public class CertBag extends ASN1Object {
   private ASN1ObjectIdentifier certId;
   private ASN1Encodable certValue;

   private CertBag(ASN1Sequence var1) {
      this.certId = (ASN1ObjectIdentifier)var1.getObjectAt(0);
      this.certValue = ((DERTaggedObject)var1.getObjectAt(1)).getObject();
   }

   public static CertBag getInstance(Object var0) {
      if (var0 instanceof CertBag) {
         return (CertBag)var0;
      } else {
         return var0 != null ? new CertBag(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public CertBag(ASN1ObjectIdentifier var1, ASN1Encodable var2) {
      this.certId = var1;
      this.certValue = var2;
   }

   public ASN1ObjectIdentifier getCertId() {
      return this.certId;
   }

   public ASN1Encodable getCertValue() {
      return this.certValue;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.certId);
      var1.add(new DERTaggedObject(0, this.certValue));
      return new DERSequence(var1);
   }
}
