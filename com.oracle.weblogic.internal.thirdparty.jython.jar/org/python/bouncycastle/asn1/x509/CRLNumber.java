package org.python.bouncycastle.asn1.x509;

import java.math.BigInteger;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;

public class CRLNumber extends ASN1Object {
   private BigInteger number;

   public CRLNumber(BigInteger var1) {
      this.number = var1;
   }

   public BigInteger getCRLNumber() {
      return this.number;
   }

   public String toString() {
      return "CRLNumber: " + this.getCRLNumber();
   }

   public ASN1Primitive toASN1Primitive() {
      return new ASN1Integer(this.number);
   }

   public static CRLNumber getInstance(Object var0) {
      if (var0 instanceof CRLNumber) {
         return (CRLNumber)var0;
      } else {
         return var0 != null ? new CRLNumber(ASN1Integer.getInstance(var0).getValue()) : null;
      }
   }
}
