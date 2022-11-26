package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECMultiplier;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.math.ec.WTauNafMultiplier;
import org.python.bouncycastle.util.encoders.Hex;

public class SecT239K1Curve extends ECCurve.AbstractF2m {
   private static final int SecT239K1_DEFAULT_COORDS = 6;
   protected SecT239K1Point infinity = new SecT239K1Point(this, (ECFieldElement)null, (ECFieldElement)null);

   public SecT239K1Curve() {
      super(239, 158, 0, 0);
      this.a = this.fromBigInteger(BigInteger.valueOf(0L));
      this.b = this.fromBigInteger(BigInteger.valueOf(1L));
      this.order = new BigInteger(1, Hex.decode("2000000000000000000000000000005A79FEC67CB6E91F1C1DA800E478A5"));
      this.cofactor = BigInteger.valueOf(4L);
      this.coord = 6;
   }

   protected ECCurve cloneCurve() {
      return new SecT239K1Curve();
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
      return 239;
   }

   public ECFieldElement fromBigInteger(BigInteger var1) {
      return new SecT239FieldElement(var1);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, boolean var3) {
      return new SecT239K1Point(this, var1, var2, var3);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, ECFieldElement[] var3, boolean var4) {
      return new SecT239K1Point(this, var1, var2, var3, var4);
   }

   public ECPoint getInfinity() {
      return this.infinity;
   }

   public boolean isKoblitz() {
      return true;
   }

   public int getM() {
      return 239;
   }

   public boolean isTrinomial() {
      return true;
   }

   public int getK1() {
      return 158;
   }

   public int getK2() {
      return 0;
   }

   public int getK3() {
      return 0;
   }
}
