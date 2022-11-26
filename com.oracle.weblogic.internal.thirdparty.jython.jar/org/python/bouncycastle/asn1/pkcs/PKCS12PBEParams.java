package org.python.bouncycastle.asn1.pkcs;

import java.math.BigInteger;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;

public class PKCS12PBEParams extends ASN1Object {
   ASN1Integer iterations;
   ASN1OctetString iv;

   public PKCS12PBEParams(byte[] var1, int var2) {
      this.iv = new DEROctetString(var1);
      this.iterations = new ASN1Integer((long)var2);
   }

   private PKCS12PBEParams(ASN1Sequence var1) {
      this.iv = (ASN1OctetString)var1.getObjectAt(0);
      this.iterations = ASN1Integer.getInstance(var1.getObjectAt(1));
   }

   public static PKCS12PBEParams getInstance(Object var0) {
      if (var0 instanceof PKCS12PBEParams) {
         return (PKCS12PBEParams)var0;
      } else {
         return var0 != null ? new PKCS12PBEParams(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public BigInteger getIterations() {
      return this.iterations.getValue();
   }

   public byte[] getIV() {
      return this.iv.getOctets();
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.iv);
      var1.add(this.iterations);
      return new DERSequence(var1);
   }
}
