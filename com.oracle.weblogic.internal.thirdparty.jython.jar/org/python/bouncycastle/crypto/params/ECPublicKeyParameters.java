package org.python.bouncycastle.crypto.params;

import org.python.bouncycastle.math.ec.ECPoint;

public class ECPublicKeyParameters extends ECKeyParameters {
   private final ECPoint Q;

   public ECPublicKeyParameters(ECPoint var1, ECDomainParameters var2) {
      super(false, var2);
      this.Q = this.validate(var1);
   }

   private ECPoint validate(ECPoint var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("point has null value");
      } else if (var1.isInfinity()) {
         throw new IllegalArgumentException("point at infinity");
      } else {
         var1 = var1.normalize();
         if (!var1.isValid()) {
            throw new IllegalArgumentException("point not on curve");
         } else {
            return var1;
         }
      }
   }

   public ECPoint getQ() {
      return this.Q;
   }
}
