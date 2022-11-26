package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.util.encoders.Hex;

public class SecP521R1Curve extends ECCurve.AbstractFp {
   public static final BigInteger q = new BigInteger(1, Hex.decode("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"));
   private static final int SecP521R1_DEFAULT_COORDS = 2;
   protected SecP521R1Point infinity = new SecP521R1Point(this, (ECFieldElement)null, (ECFieldElement)null);

   public SecP521R1Curve() {
      super(q);
      this.a = this.fromBigInteger(new BigInteger(1, Hex.decode("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC")));
      this.b = this.fromBigInteger(new BigInteger(1, Hex.decode("0051953EB9618E1C9A1F929A21A0B68540EEA2DA725B99B315F3B8B489918EF109E156193951EC7E937B1652C0BD3BB1BF073573DF883D2C34F1EF451FD46B503F00")));
      this.order = new BigInteger(1, Hex.decode("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFA51868783BF2F966B7FCC0148F709A5D03BB5C9B8899C47AEBB6FB71E91386409"));
      this.cofactor = BigInteger.valueOf(1L);
      this.coord = 2;
   }

   protected ECCurve cloneCurve() {
      return new SecP521R1Curve();
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
      return new SecP521R1FieldElement(var1);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, boolean var3) {
      return new SecP521R1Point(this, var1, var2, var3);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, ECFieldElement[] var3, boolean var4) {
      return new SecP521R1Point(this, var1, var2, var3, var4);
   }

   public ECPoint getInfinity() {
      return this.infinity;
   }
}
