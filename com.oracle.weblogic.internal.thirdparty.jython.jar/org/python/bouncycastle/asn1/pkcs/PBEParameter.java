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

public class PBEParameter extends ASN1Object {
   ASN1Integer iterations;
   ASN1OctetString salt;

   public PBEParameter(byte[] var1, int var2) {
      if (var1.length != 8) {
         throw new IllegalArgumentException("salt length must be 8");
      } else {
         this.salt = new DEROctetString(var1);
         this.iterations = new ASN1Integer((long)var2);
      }
   }

   private PBEParameter(ASN1Sequence var1) {
      this.salt = (ASN1OctetString)var1.getObjectAt(0);
      this.iterations = (ASN1Integer)var1.getObjectAt(1);
   }

   public static PBEParameter getInstance(Object var0) {
      if (var0 instanceof PBEParameter) {
         return (PBEParameter)var0;
      } else {
         return var0 != null ? new PBEParameter(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public BigInteger getIterationCount() {
      return this.iterations.getValue();
   }

   public byte[] getSalt() {
      return this.salt.getOctets();
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.salt);
      var1.add(this.iterations);
      return new DERSequence(var1);
   }
}
