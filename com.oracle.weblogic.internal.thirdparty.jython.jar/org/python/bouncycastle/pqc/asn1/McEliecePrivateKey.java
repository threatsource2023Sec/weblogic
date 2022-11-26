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
import org.python.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.python.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.python.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public class McEliecePrivateKey extends ASN1Object {
   private int n;
   private int k;
   private byte[] encField;
   private byte[] encGp;
   private byte[] encSInv;
   private byte[] encP1;
   private byte[] encP2;

   public McEliecePrivateKey(int var1, int var2, GF2mField var3, PolynomialGF2mSmallM var4, Permutation var5, Permutation var6, GF2Matrix var7) {
      this.n = var1;
      this.k = var2;
      this.encField = var3.getEncoded();
      this.encGp = var4.getEncoded();
      this.encSInv = var7.getEncoded();
      this.encP1 = var5.getEncoded();
      this.encP2 = var6.getEncoded();
   }

   public static McEliecePrivateKey getInstance(Object var0) {
      if (var0 instanceof McEliecePrivateKey) {
         return (McEliecePrivateKey)var0;
      } else {
         return var0 != null ? new McEliecePrivateKey(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   private McEliecePrivateKey(ASN1Sequence var1) {
      BigInteger var2 = ((ASN1Integer)var1.getObjectAt(0)).getValue();
      this.n = var2.intValue();
      BigInteger var3 = ((ASN1Integer)var1.getObjectAt(1)).getValue();
      this.k = var3.intValue();
      this.encField = ((ASN1OctetString)var1.getObjectAt(2)).getOctets();
      this.encGp = ((ASN1OctetString)var1.getObjectAt(3)).getOctets();
      this.encP1 = ((ASN1OctetString)var1.getObjectAt(4)).getOctets();
      this.encP2 = ((ASN1OctetString)var1.getObjectAt(5)).getOctets();
      this.encSInv = ((ASN1OctetString)var1.getObjectAt(6)).getOctets();
   }

   public int getN() {
      return this.n;
   }

   public int getK() {
      return this.k;
   }

   public GF2mField getField() {
      return new GF2mField(this.encField);
   }

   public PolynomialGF2mSmallM getGoppaPoly() {
      return new PolynomialGF2mSmallM(this.getField(), this.encGp);
   }

   public GF2Matrix getSInv() {
      return new GF2Matrix(this.encSInv);
   }

   public Permutation getP1() {
      return new Permutation(this.encP1);
   }

   public Permutation getP2() {
      return new Permutation(this.encP2);
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(new ASN1Integer((long)this.n));
      var1.add(new ASN1Integer((long)this.k));
      var1.add(new DEROctetString(this.encField));
      var1.add(new DEROctetString(this.encGp));
      var1.add(new DEROctetString(this.encP1));
      var1.add(new DEROctetString(this.encP2));
      var1.add(new DEROctetString(this.encSInv));
      return new DERSequence(var1);
   }
}
