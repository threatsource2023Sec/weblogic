package org.python.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.crypto.params.RSAKeyParameters;
import org.python.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;

public class RSABlindingFactorGenerator {
   private static BigInteger ZERO = BigInteger.valueOf(0L);
   private static BigInteger ONE = BigInteger.valueOf(1L);
   private RSAKeyParameters key;
   private SecureRandom random;

   public void init(CipherParameters var1) {
      if (var1 instanceof ParametersWithRandom) {
         ParametersWithRandom var2 = (ParametersWithRandom)var1;
         this.key = (RSAKeyParameters)var2.getParameters();
         this.random = var2.getRandom();
      } else {
         this.key = (RSAKeyParameters)var1;
         this.random = new SecureRandom();
      }

      if (this.key instanceof RSAPrivateCrtKeyParameters) {
         throw new IllegalArgumentException("generator requires RSA public key");
      }
   }

   public BigInteger generateBlindingFactor() {
      if (this.key == null) {
         throw new IllegalStateException("generator not initialised");
      } else {
         BigInteger var1 = this.key.getModulus();
         int var2 = var1.bitLength() - 1;

         BigInteger var3;
         BigInteger var4;
         do {
            do {
               var3 = new BigInteger(var2, this.random);
               var4 = var3.gcd(var1);
            } while(var3.equals(ZERO));
         } while(var3.equals(ONE) || !var4.equals(ONE));

         return var3;
      }
   }
}
