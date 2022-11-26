package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECMultiplier;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.math.ec.WTauNafMultiplier;
import org.python.bouncycastle.util.encoders.Hex;

public class SecT571K1Curve extends ECCurve.AbstractF2m {
   private static final int SecT571K1_DEFAULT_COORDS = 6;
   protected SecT571K1Point infinity = new SecT571K1Point(this, (ECFieldElement)null, (ECFieldElement)null);

   public SecT571K1Curve() {
      super(571, 2, 5, 10);
      this.a = this.fromBigInteger(BigInteger.valueOf(0L));
      this.b = this.fromBigInteger(BigInteger.valueOf(1L));
      this.order = new BigInteger(1, Hex.decode("020000000000000000000000000000000000000000000000000000000000000000000000131850E1F19A63E4B391A8DB917F4138B630D84BE5D639381E91DEB45CFE778F637C1001"));
      this.cofactor = BigInteger.valueOf(4L);
      this.coord = 6;
   }

   protected ECCurve cloneCurve() {
      return new SecT571K1Curve();
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
      return 571;
   }

   public ECFieldElement fromBigInteger(BigInteger var1) {
      return new SecT571FieldElement(var1);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, boolean var3) {
      return new SecT571K1Point(this, var1, var2, var3);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, ECFieldElement[] var3, boolean var4) {
      return new SecT571K1Point(this, var1, var2, var3, var4);
   }

   public ECPoint getInfinity() {
      return this.infinity;
   }

   public boolean isKoblitz() {
      return true;
   }

   public int getM() {
      return 571;
   }

   public boolean isTrinomial() {
      return false;
   }

   public int getK1() {
      return 2;
   }

   public int getK2() {
      return 5;
   }

   public int getK3() {
      return 10;
   }
}
