package org.python.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.util.encoders.Hex;

public class SecP256R1Curve extends ECCurve.AbstractFp {
   public static final BigInteger q = new BigInteger(1, Hex.decode("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF"));
   private static final int SecP256R1_DEFAULT_COORDS = 2;
   protected SecP256R1Point infinity = new SecP256R1Point(this, (ECFieldElement)null, (ECFieldElement)null);

   public SecP256R1Curve() {
      super(q);
      this.a = this.fromBigInteger(new BigInteger(1, Hex.decode("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC")));
      this.b = this.fromBigInteger(new BigInteger(1, Hex.decode("5AC635D8AA3A93E7B3EBBD55769886BC651D06B0CC53B0F63BCE3C3E27D2604B")));
      this.order = new BigInteger(1, Hex.decode("FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551"));
      this.cofactor = BigInteger.valueOf(1L);
      this.coord = 2;
   }

   protected ECCurve cloneCurve() {
      return new SecP256R1Curve();
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
      return new SecP256R1FieldElement(var1);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, boolean var3) {
      return new SecP256R1Point(this, var1, var2, var3);
   }

   protected ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, ECFieldElement[] var3, boolean var4) {
      return new SecP256R1Point(this, var1, var2, var3, var4);
   }

   public ECPoint getInfinity() {
      return this.infinity;
   }
}
