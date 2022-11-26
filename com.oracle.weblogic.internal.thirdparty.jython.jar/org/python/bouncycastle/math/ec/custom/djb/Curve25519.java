package org.python.bouncycastle.math.ec.custom.djb;

import java.math.BigInteger;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.math.raw.Nat256;
import org.python.bouncycastle.util.encoders.Hex;

public class Curve25519 extends ECCurve.AbstractFp {
   public static final BigInteger q;
   private static final int Curve25519_DEFAULT_COORDS = 4;
   protected Curve25519Point infinity = new Curve25519Point(this, (ECFieldElement)null, (ECFieldElement)null);

   public Curve25519() {
      super(q);
      this.a = this.fromBigInteger(new BigInteger(1, Hex.decode("2AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA984914A144")));
      this.b = this.fromBigInteger(new BigInteger(1, Hex.decode("7B425ED097B425ED097B425ED097B425ED097B425ED097B4260B5E9C7710C864")));
      this.order = new BigInteger(1, Hex.decode("1000000000000000000000000000000014DEF9DEA2F79CD65812631A5CF5D3ED"));
      this.cofactor = BigInteger.valueOf(8L);
      this.coord = 4;
   }

   protected ECCurve cloneCurve() {
      return new Curve25519();
   }

   public boolean supportsCoordinateSystem(int var1) {
      switch (var1) {
         case 4:
            return true;
         default:
            return false;
      }
   }

   public BigInteger getQ() {
      return q;
   }

   public int getFieldSize() {
      return q.bitLength();
   }

   public ECFieldElement fromBigInteger(BigInteger var1) {
      return new Curve25519FieldElement(var1);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, boolean var3) {
      return new Curve25519Point(this, var1, var2, var3);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, ECFieldElement[] var3, boolean var4) {
      return new Curve25519Point(this, var1, var2, var3, var4);
   }

   public ECPoint getInfinity() {
      return this.infinity;
   }

   static {
      q = Nat256.toBigInteger(Curve25519Field.P);
   }
}
