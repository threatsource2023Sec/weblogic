package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECMultiplier;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.math.ec.WTauNafMultiplier;
import org.python.bouncycastle.util.encoders.Hex;

public class SecT409K1Curve extends ECCurve.AbstractF2m {
   private static final int SecT409K1_DEFAULT_COORDS = 6;
   protected SecT409K1Point infinity = new SecT409K1Point(this, (ECFieldElement)null, (ECFieldElement)null);

   public SecT409K1Curve() {
      super(409, 87, 0, 0);
      this.a = this.fromBigInteger(BigInteger.valueOf(0L));
      this.b = this.fromBigInteger(BigInteger.valueOf(1L));
      this.order = new BigInteger(1, Hex.decode("7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFE5F83B2D4EA20400EC4557D5ED3E3E7CA5B4B5C83B8E01E5FCF"));
      this.cofactor = BigInteger.valueOf(4L);
      this.coord = 6;
   }

   protected ECCurve cloneCurve() {
      return new SecT409K1Curve();
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
      return 409;
   }

   public ECFieldElement fromBigInteger(BigInteger var1) {
      return new SecT409FieldElement(var1);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, boolean var3) {
      return new SecT409K1Point(this, var1, var2, var3);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, ECFieldElement[] var3, boolean var4) {
      return new SecT409K1Point(this, var1, var2, var3, var4);
   }

   public ECPoint getInfinity() {
      return this.infinity;
   }

   public boolean isKoblitz() {
      return true;
   }

   public int getM() {
      return 409;
   }

   public boolean isTrinomial() {
      return true;
   }

   public int getK1() {
      return 87;
   }

   public int getK2() {
      return 0;
   }

   public int getK3() {
      return 0;
   }
}
