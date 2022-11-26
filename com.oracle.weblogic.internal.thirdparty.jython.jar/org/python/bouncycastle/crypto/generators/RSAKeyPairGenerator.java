package org.python.bouncycastle.crypto.generators;

import java.math.BigInteger;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.python.bouncycastle.crypto.KeyGenerationParameters;
import org.python.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.python.bouncycastle.crypto.params.RSAKeyParameters;
import org.python.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.python.bouncycastle.math.Primes;
import org.python.bouncycastle.math.ec.WNafUtil;

public class RSAKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private RSAKeyGenerationParameters param;
   private int iterations;

   public void init(KeyGenerationParameters var1) {
      this.param = (RSAKeyGenerationParameters)var1;
      this.iterations = getNumberOfIterations(this.param.getStrength(), this.param.getCertainty());
   }

   public AsymmetricCipherKeyPair generateKeyPair() {
      AsymmetricCipherKeyPair var1 = null;
      boolean var2 = false;
      int var3 = this.param.getStrength();
      int var4 = (var3 + 1) / 2;
      int var5 = var3 - var4;
      int var6 = var3 / 2 - 100;
      if (var6 < var3 / 3) {
         var6 = var3 / 3;
      }

      int var7 = var3 >> 2;
      BigInteger var8 = BigInteger.valueOf(2L).pow(var3 / 2);
      BigInteger var9 = ONE.shiftLeft(var3 - 1);
      BigInteger var10 = ONE.shiftLeft(var6);

      while(!var2) {
         BigInteger var11 = this.param.getPublicExponent();
         BigInteger var12 = this.chooseRandomPrime(var4, var11, var9);

         while(true) {
            BigInteger var13;
            BigInteger var14;
            do {
               var13 = this.chooseRandomPrime(var5, var11, var9);
               var14 = var13.subtract(var12).abs();
            } while(var14.bitLength() < var6);

            if (var14.compareTo(var10) > 0) {
               BigInteger var15 = var12.multiply(var13);
               if (var15.bitLength() != var3) {
                  var12 = var12.max(var13);
               } else {
                  if (WNafUtil.getNafWeight(var15) >= var7) {
                     BigInteger var16;
                     if (var12.compareTo(var13) < 0) {
                        var16 = var12;
                        var12 = var13;
                        var13 = var16;
                     }

                     BigInteger var17 = var12.subtract(ONE);
                     BigInteger var18 = var13.subtract(ONE);
                     var16 = var17.gcd(var18);
                     BigInteger var19 = var17.divide(var16).multiply(var18);
                     BigInteger var20 = var11.modInverse(var19);
                     if (var20.compareTo(var8) > 0) {
                        var2 = true;
                        var14 = var20.remainder(var17);
                        BigInteger var21 = var20.remainder(var18);
                        BigInteger var22 = var13.modInverse(var12);
                        var1 = new AsymmetricCipherKeyPair(new RSAKeyParameters(false, var15, var11), new RSAPrivateCrtKeyParameters(var15, var11, var20, var12, var13, var14, var21, var22));
                     }
                     break;
                  }

                  var12 = this.chooseRandomPrime(var4, var11, var9);
               }
            }
         }
      }

      return var1;
   }

   protected BigInteger chooseRandomPrime(int var1, BigInteger var2, BigInteger var3) {
      for(int var4 = 0; var4 != 5 * var1; ++var4) {
         BigInteger var5 = new BigInteger(var1, 1, this.param.getRandom());
         if (!var5.mod(var2).equals(ONE) && var5.multiply(var5).compareTo(var3) >= 0 && this.isProbablePrime(var5) && var2.gcd(var5.subtract(ONE)).equals(ONE)) {
            return var5;
         }
      }

      throw new IllegalStateException("unable to generate prime number for RSA key");
   }

   protected boolean isProbablePrime(BigInteger var1) {
      return !Primes.hasAnySmallFactors(var1) && Primes.isMRProbablePrime(var1, this.param.getRandom(), this.iterations);
   }

   private static int getNumberOfIterations(int var0, int var1) {
      if (var0 >= 1536) {
         return var1 <= 100 ? 3 : (var1 <= 128 ? 4 : 4 + (var1 - 128 + 1) / 2);
      } else if (var0 >= 1024) {
         return var1 <= 100 ? 4 : (var1 <= 112 ? 5 : 5 + (var1 - 112 + 1) / 2);
      } else if (var0 >= 512) {
         return var1 <= 80 ? 5 : (var1 <= 100 ? 7 : 7 + (var1 - 100 + 1) / 2);
      } else {
         return var1 <= 80 ? 40 : 40 + (var1 - 80 + 1) / 2;
      }
   }
}
