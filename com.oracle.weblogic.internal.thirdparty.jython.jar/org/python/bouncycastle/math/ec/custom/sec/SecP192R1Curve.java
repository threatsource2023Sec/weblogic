package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.util.encoders.Hex;

public class SecP192R1Curve extends ECCurve.AbstractFp {
   public static final BigInteger q = new BigInteger(1, Hex.decode("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF"));
   private static final int SecP192R1_DEFAULT_COORDS = 2;
   protected SecP192R1Point infinity = new SecP192R1Point(this, (ECFieldElement)null, (ECFieldElement)null);

   public SecP192R1Curve() {
      super(q);
      this.a = this.fromBigInteger(new BigInteger(1, Hex.decode("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFC")));
      this.b = this.fromBigInteger(new BigInteger(1, Hex.decode("64210519E59C80E70FA7E9AB72243049FEB8DEECC146B9B1")));
      this.order = new BigInteger(1, Hex.decode("FFFFFFFFFFFFFFFFFFFFFFFF99DEF836146BC9B1B4D22831"));
      this.cofactor = BigInteger.valueOf(1L);
      this.coord = 2;
   }

   protected ECCurve cloneCurve() {
      return new SecP192R1Curve();
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
      return new SecP192R1FieldElement(var1);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, boolean var3) {
      return new SecP192R1Point(this, var1, var2, var3);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, ECFieldElement[] var3, boolean var4) {
      return new SecP192R1Point(this, var1, var2, var3, var4);
   }

   public ECPoint getInfinity() {
      return this.infinity;
   }
}
