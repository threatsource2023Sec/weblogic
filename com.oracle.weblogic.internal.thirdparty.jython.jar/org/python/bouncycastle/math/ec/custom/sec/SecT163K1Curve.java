package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECMultiplier;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.math.ec.WTauNafMultiplier;
import org.python.bouncycastle.util.encoders.Hex;

public class SecT163K1Curve extends ECCurve.AbstractF2m {
   private static final int SecT163K1_DEFAULT_COORDS = 6;
   protected SecT163K1Point infinity = new SecT163K1Point(this, (ECFieldElement)null, (ECFieldElement)null);

   public SecT163K1Curve() {
      super(163, 3, 6, 7);
      this.a = this.fromBigInteger(BigInteger.valueOf(1L));
      this.b = this.a;
      this.order = new BigInteger(1, Hex.decode("04000000000000000000020108A2E0CC0D99F8A5EF"));
      this.cofactor = BigInteger.valueOf(2L);
      this.coord = 6;
   }

   protected ECCurve cloneCurve() {
      return new SecT163K1Curve();
   }

   public boolean supportsCoordinateSystem(int var1) {
      switch (var1) {
         case 6:
            return true;
         default:
            return false;
      }
   }

   protected ECMultiplier createDefaultMultiplier() {
      return new WTauNafMultiplier();
   }

   public int getFieldSize() {
      return 163;
   }

   public ECFieldElement fromBigInteger(BigInteger var1) {
      return new SecT163FieldElement(var1);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, boolean var3) {
      return new SecT163K1Point(this, var1, var2, var3);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, ECFieldElement[] var3, boolean var4) {
      return new SecT163K1Point(this, var1, var2, var3, var4);
   }

   public ECPoint getInfinity() {
      return this.infinity;
   }

   public boolean isKoblitz() {
      return true;
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
