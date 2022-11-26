package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.util.encoders.Hex;

public class SecT131R1Curve extends ECCurve.AbstractF2m {
   private static final int SecT131R1_DEFAULT_COORDS = 6;
   protected SecT131R1Point infinity = new SecT131R1Point(this, (ECFieldElement)null, (ECFieldElement)null);

   public SecT131R1Curve() {
      super(131, 2, 3, 8);
      this.a = this.fromBigInteger(new BigInteger(1, Hex.decode("07A11B09A76B562144418FF3FF8C2570B8")));
      this.b = this.fromBigInteger(new BigInteger(1, Hex.decode("0217C05610884B63B9C6C7291678F9D341")));
      this.order = new BigInteger(1, Hex.decode("0400000000000000023123953A9464B54D"));
      this.cofactor = BigInteger.valueOf(2L);
      this.coord = 6;
   }

   protected ECCurve cloneCurve() {
      return new SecT131R1Curve();
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
      return 131;
   }

   public ECFieldElement fromBigInteger(BigInteger var1) {
      return new SecT131FieldElement(var1);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, boolean var3) {
      return new SecT131R1Point(this, var1, var2, var3);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, ECFieldElement[] var3, boolean var4) {
      return new SecT131R1Point(this, var1, var2, var3, var4);
   }

   public ECPoint getInfinity() {
      return this.infinity;
   }

   public boolean isKoblitz() {
      return false;
   }

   public int getM() {
      return 131;
   }

   public boolean isTrinomial() {
      return false;
   }

   public int getK1() {
      return 2;
   }

   public int getK2() {
      return 3;
   }

   public int getK3() {
      return 8;
   }
}
