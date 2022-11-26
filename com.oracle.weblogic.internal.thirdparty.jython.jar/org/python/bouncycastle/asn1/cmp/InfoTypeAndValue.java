package org.python.bouncycastle.asn1.cmp;

import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;

public class InfoTypeAndValue extends ASN1Object {
   private ASN1ObjectIdentifier infoType;
   private ASN1Encodable infoValue;

   private InfoTypeAndValue(ASN1Sequence var1) {
      this.infoType = ASN1ObjectIdentifier.getInstance(var1.getObjectAt(0));
      if (var1.size() > 1) {
         this.infoValue = var1.getObjectAt(1);
      }

   }

   public static InfoTypeAndValue getInstance(Object var0) {
      if (var0 instanceof InfoTypeAndValue) {
         return (InfoTypeAndValue)var0;
      } else {
         return var0 != null ? new InfoTypeAndValue(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public InfoTypeAndValue(ASN1ObjectIdentifier var1) {
      this.infoType = var1;
      this.infoValue = null;
   }

   public InfoTypeAndValue(ASN1ObjectIdentifier var1, ASN1Encodable var2) {
      this.infoType = var1;
      this.infoValue = var2;
   }

   public ASN1ObjectIdentifier getInfoType() {
      return this.infoType;
   }

   public ASN1Encodable getInfoValue() {
      return this.infoValue;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.infoType);
      if (this.infoValue != null) {
         var1.add(this.infoValue);
      }

      return new DERSequence(var1);
   }
}
