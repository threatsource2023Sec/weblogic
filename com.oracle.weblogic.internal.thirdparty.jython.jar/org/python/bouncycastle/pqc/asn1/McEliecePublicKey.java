package org.python.bouncycastle.pqc.asn1;

import java.math.BigInteger;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.pqc.math.linearalgebra.GF2Matrix;

public class McEliecePublicKey extends ASN1Object {
   private final int n;
   private final int t;
   private final GF2Matrix g;

   public McEliecePublicKey(int var1, int var2, GF2Matrix var3) {
      this.n = var1;
      this.t = var2;
      this.g = new GF2Matrix(var3);
   }

   private McEliecePublicKey(ASN1Sequence var1) {
      BigInteger var2 = ((ASN1Integer)var1.getObjectAt(0)).getValue();
      this.n = var2.intValue();
      BigInteger var3 = ((ASN1Integer)var1.getObjectAt(1)).getValue();
      this.t = var3.intValue();
      this.g = new GF2Matrix(((ASN1OctetString)var1.getObjectAt(2)).getOctets());
   }

   public int getN() {
      return this.n;
   }

   public int getT() {
      return this.t;
   }

   public GF2Matrix getG() {
      return new GF2Matrix(this.g);
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(new ASN1Integer((long)this.n));
      var1.add(new ASN1Integer((long)this.t));
      var1.add(new DEROctetString(this.g.getEncoded()));
      return new DERSequence(var1);
   }

   public static McEliecePublicKey getInstance(Object var0) {
      if (var0 instanceof McEliecePublicKey) {
         return (McEliecePublicKey)var0;
      } else {
         return var0 != null ? new McEliecePublicKey(ASN1Sequence.getInstance(var0)) : null;
      }
   }
}
