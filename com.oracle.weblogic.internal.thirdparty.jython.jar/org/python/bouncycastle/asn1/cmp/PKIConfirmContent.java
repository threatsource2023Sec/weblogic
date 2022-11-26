package org.python.bouncycastle.asn1.cmp;

import org.python.bouncycastle.asn1.ASN1Null;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.DERNull;

public class PKIConfirmContent extends ASN1Object {
   private ASN1Null val;

   private PKIConfirmContent(ASN1Null var1) {
      this.val = var1;
   }

   public static PKIConfirmContent getInstance(Object var0) {
      if (var0 != null && !(var0 instanceof PKIConfirmContent)) {
         if (var0 instanceof ASN1Null) {
            return new PKIConfirmContent((ASN1Null)var0);
         } else {
            throw new IllegalArgumentException("Invalid object: " + var0.getClass().getName());
         }
      } else {
         return (PKIConfirmContent)var0;
      }
   }

   public PKIConfirmContent() {
      this.val = DERNull.INSTANCE;
   }

   public ASN1Primitive toASN1Primitive() {
      return this.val;
   }
}
