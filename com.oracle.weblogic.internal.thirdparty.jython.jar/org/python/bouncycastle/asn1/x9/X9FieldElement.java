package org.python.bouncycastle.asn1.x9;

import java.math.BigInteger;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.math.ec.ECFieldElement;

public class X9FieldElement extends ASN1Object {
   protected ECFieldElement f;
   private static X9IntegerConverter converter = new X9IntegerConverter();

   public X9FieldElement(ECFieldElement var1) {
      this.f = var1;
   }

   public X9FieldElement(BigInteger var1, ASN1OctetString var2) {
      this(new ECFieldElement.Fp(var1, new BigInteger(1, var2.getOctets())));
   }

   public X9FieldElement(int var1, int var2, int var3, int var4, ASN1OctetString var5) {
      this(new ECFieldElement.F2m(var1, var2, var3, var4, new BigInteger(1, var5.getOctets())));
   }

   public ECFieldElement getValue() {
      return this.f;
   }

   public ASN1Primitive toASN1Primitive() {
      int var1 = converter.getByteLength(this.f);
      byte[] var2 = converter.integerToBytes(this.f.toBigInteger(), var1);
      return new DEROctetString(var2);
   }
}
