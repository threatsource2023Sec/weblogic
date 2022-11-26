package org.python.bouncycastle.crypto.params;

import java.math.BigInteger;

public class DHPublicKeyParameters extends DHKeyParameters {
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private static final BigInteger TWO = BigInteger.valueOf(2L);
   private BigInteger y;

   public DHPublicKeyParameters(BigInteger var1, DHParameters var2) {
      super(false, var2);
      this.y = this.validate(var1, var2);
   }

   private BigInteger validate(BigInteger var1, DHParameters var2) {
      if (var1 == null) {
         throw new NullPointerException("y value cannot be null");
      } else if (var1.compareTo(TWO) >= 0 && var1.compareTo(var2.getP().subtract(TWO)) <= 0) {
         if (var2.getQ() != null) {
            if (ONE.equals(var1.modPow(var2.getQ(), var2.getP()))) {
               return var1;
            } else {
               throw new IllegalArgumentException("Y value does not appear to be in correct group");
            }
         } else {
            return var1;
         }
      } else {
         throw new IllegalArgumentException("invalid DH public key");
      }
   }

   public BigInteger getY() {
      return this.y;
   }

   public int hashCode() {
      return this.y.hashCode() ^ super.hashCode();
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof DHPublicKeyParameters)) {
         return false;
      } else {
         DHPublicKeyParameters var2 = (DHPublicKeyParameters)var1;
         return var2.getY().equals(this.y) && super.equals(var1);
      }
   }
}
