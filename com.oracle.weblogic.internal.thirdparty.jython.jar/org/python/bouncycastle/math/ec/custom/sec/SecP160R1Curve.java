package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.util.encoders.Hex;

public class SecP160R1Curve extends ECCurve.AbstractFp {
   public static final BigInteger q = new BigInteger(1, Hex.decode("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7FFFFFFF"));
   private static final int SecP160R1_DEFAULT_COORDS = 2;
   protected SecP160R1Point infinity = new SecP160R1Point(this, (ECFieldElement)null, (ECFieldElement)null);

   public SecP160R1Curve() {
      super(q);
      this.a = this.fromBigInteger(new BigInteger(1, Hex.decode("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7FFFFFFC")));
      this.b = this.fromBigInteger(new BigInteger(1, Hex.decode("1C97BEFC54BD7A8B65ACF89F81D4D4ADC565FA45")));
      this.order = new BigInteger(1, Hex.decode("0100000000000000000001F4C8F927AED3CA752257"));
      this.cofactor = BigInteger.valueOf(1L);
      this.coord = 2;
   }

   protected ECCurve cloneCurve() {
      return new SecP160R1Curve();
   }

   public boolean supportsCoordinateSystem(int var1) {
      switch (var1) {
         case 2:
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
      return new SecP160R1FieldElement(var1);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, boolean var3) {
      return new SecP160R1Point(this, var1, var2, var3);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, ECFieldElement[] var3, boolean var4) {
      return new SecP160R1Point(this, var1, var2, var3, var4);
   }

   public ECPoint getInfinity() {
      return this.infinity;
   }
}
