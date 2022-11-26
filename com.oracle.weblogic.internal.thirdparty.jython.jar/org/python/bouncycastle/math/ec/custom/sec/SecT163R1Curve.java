package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.util.encoders.Hex;

public class SecT163R1Curve extends ECCurve.AbstractF2m {
   private static final int SecT163R1_DEFAULT_COORDS = 6;
   protected SecT163R1Point infinity = new SecT163R1Point(this, (ECFieldElement)null, (ECFieldElement)null);

   public SecT163R1Curve() {
      super(163, 3, 6, 7);
      this.a = this.fromBigInteger(new BigInteger(1, Hex.decode("07B6882CAAEFA84F9554FF8428BD88E246D2782AE2")));
      this.b = this.fromBigInteger(new BigInteger(1, Hex.decode("0713612DCDDCB40AAB946BDA29CA91F73AF958AFD9")));
      this.order = new BigInteger(1, Hex.decode("03FFFFFFFFFFFFFFFFFFFF48AAB689C29CA710279B"));
      this.cofactor = BigInteger.valueOf(2L);
      this.coord = 6;
   }

   protected ECCurve cloneCurve() {
      return new SecT163R1Curve();
   }

   public boolean supportsCoordinateSystem(int var1) {
      switch (var1) {
         case 6:
            return true;
         default:
            return false;
      }
   }

   public int getFieldSize() {
      return 163;
   }

   public ECFieldElement fromBigInteger(BigInteger var1) {
      return new SecT163FieldElement(var1);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, boolean var3) {
      return new SecT163R1Point(this, var1, var2, var3);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, ECFieldElement[] var3, boolean var4) {
      return new SecT163R1Point(this, var1, var2, var3, var4);
   }

   public ECPoint getInfinity() {
      return this.infinity;
   }

   public boolean isKoblitz() {
      return false;
   }

   public int getM() {
      return 163;
   }

   public boolean isTrinomial() {
      return false;
   }

   public int getK1() {
      return 3;
   }

   public int getK2() {
      return 6;
   }

   public int getK3() {
      return 7;
   }
}
