package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.util.encoders.Hex;

public class SecT283R1Curve extends ECCurve.AbstractF2m {
   private static final int SecT283R1_DEFAULT_COORDS = 6;
   protected SecT283R1Point infinity = new SecT283R1Point(this, (ECFieldElement)null, (ECFieldElement)null);

   public SecT283R1Curve() {
      super(283, 5, 7, 12);
      this.a = this.fromBigInteger(BigInteger.valueOf(1L));
      this.b = this.fromBigInteger(new BigInteger(1, Hex.decode("027B680AC8B8596DA5A4AF8A19A0303FCA97FD7645309FA2A581485AF6263E313B79A2F5")));
      this.order = new BigInteger(1, Hex.decode("03FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEF90399660FC938A90165B042A7CEFADB307"));
      this.cofactor = BigInteger.valueOf(2L);
      this.coord = 6;
   }

   protected ECCurve cloneCurve() {
      return new SecT283R1Curve();
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
      return 283;
   }

   public ECFieldElement fromBigInteger(BigInteger var1) {
      return new SecT283FieldElement(var1);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, boolean var3) {
      return new SecT283R1Point(this, var1, var2, var3);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, ECFieldElement[] var3, boolean var4) {
      return new SecT283R1Point(this, var1, var2, var3, var4);
   }

   public ECPoint getInfinity() {
      return this.infinity;
   }

   public boolean isKoblitz() {
      return false;
   }

   public int getM() {
      return 283;
   }

   public boolean isTrinomial() {
      return false;
   }

   public int getK1() {
      return 5;
   }

   public int getK2() {
      return 7;
   }

   public int getK3() {
      return 12;
   }
}
