package org.python.bouncycastle.jce;

import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import org.python.bouncycastle.math.ec.ECCurve;

public class ECPointUtil {
   public static ECPoint decodePoint(EllipticCurve var0, byte[] var1) {
      Object var2 = null;
      if (var0.getField() instanceof ECFieldFp) {
         var2 = new ECCurve.Fp(((ECFieldFp)var0.getField()).getP(), var0.getA(), var0.getB());
      } else {
         int[] var3 = ((ECFieldF2m)var0.getField()).getMidTermsOfReductionPolynomial();
         if (var3.length == 3) {
            var2 = new ECCurve.F2m(((ECFieldF2m)var0.getField()).getM(), var3[2], var3[1], var3[0], var0.getA(), var0.getB());
         } else {
            var2 = new ECCurve.F2m(((ECFieldF2m)var0.getField()).getM(), var3[0], var0.getA(), var0.getB());
         }
      }

      org.python.bouncycastle.math.ec.ECPoint var4 = ((ECCurve)var2).decodePoint(var1);
      return new ECPoint(var4.getAffineXCoord().toBigInteger(), var4.getAffineYCoord().toBigInteger());
   }
}
