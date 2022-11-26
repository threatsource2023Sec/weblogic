package org.python.bouncycastle.asn1.cmp;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;

public class ProtectedPart extends ASN1Object {
   private PKIHeader header;
   private PKIBody body;

   private ProtectedPart(ASN1Sequence var1) {
      this.header = PKIHeader.getInstance(var1.getObjectAt(0));
      this.body = PKIBody.getInstance(var1.getObjectAt(1));
   }

   public static ProtectedPart getInstance(Object var0) {
      if (var0 instanceof ProtectedPart) {
         return (ProtectedPart)var0;
      } else {
         return var0 != null ? new ProtectedPart(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public ProtectedPart(PKIHeader var1, PKIBody var2) {
      this.header = var1;
      this.body = var2;
   }

   public PKIHeader getHeader() {
      return this.header;
   }

   public PKIBody getBody() {
      return this.body;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.header);
      var1.add(this.body);
      return new DERSequence(var1);
   }
}
