package org.python.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.digests.SHA256Digest;
import org.python.bouncycastle.crypto.params.CramerShoupParameters;
import org.python.bouncycastle.crypto.params.DHParameters;
import org.python.bouncycastle.util.BigIntegers;

public class CramerShoupParametersGenerator {
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private int size;
   private int certainty;
   private SecureRandom random;

   public void init(int var1, int var2, SecureRandom var3) {
      this.size = var1;
      this.certainty = var2;
      this.random = var3;
   }

   public CramerShoupParameters generateParameters() {
      BigInteger[] var1 = CramerShoupParametersGenerator.ParametersHelper.generateSafePrimes(this.size, this.certainty, this.random);
      BigInteger var2 = var1[1];
      BigInteger var3 = CramerShoupParametersGenerator.ParametersHelper.selectGenerator(var2, this.random);

      BigInteger var4;
      for(var4 = CramerShoupParametersGenerator.ParametersHelper.selectGenerator(var2, this.random); var3.equals(var4); var4 = CramerShoupParametersGenerator.ParametersHelper.selectGenerator(var2, this.random)) {
      }

      return new CramerShoupParameters(var2, var3, var4, new SHA256Digest());
   }

   public CramerShoupParameters generateParameters(DHParameters var1) {
      BigInteger var2 = var1.getP();
      BigInteger var3 = var1.getG();

      BigInteger var4;
      for(var4 = CramerShoupParametersGenerator.ParametersHelper.selectGenerator(var2, this.random); var3.equals(var4); var4 = CramerShoupParametersGenerator.ParametersHelper.selectGenerator(var2, this.random)) {
      }

      return new CramerShoupParameters(var2, var3, var4, new SHA256Digest());
   }

   private static class ParametersHelper {
      private static final BigInteger TWO = BigInteger.valueOf(2L);

      static BigInteger[] generateSafePrimes(int var0, int var1, SecureRandom var2) {
         int var3 = var0 - 1;

         BigInteger var4;
         BigInteger var5;
         do {
            do {
               var4 = new BigInteger(var3, 2, var2);
               var5 = var4.shiftLeft(1).add(CramerShoupParametersGenerator.ONE);
            } while(!var5.isProbablePrime(var1));
         } while(var1 > 2 && !var4.isProbablePrime(var1));

         return new BigInteger[]{var5, var4};
      }

      static BigInteger selectGenerator(BigInteger var0, SecureRandom var1) {
         BigInteger var2 = var0.subtract(TWO);

         BigInteger var4;
         do {
            BigInteger var3 = BigIntegers.createRandomInRange(TWO, var2, var1);
            var4 = var3.modPow(TWO, var0);
         } while(var4.equals(CramerShoupParametersGenerator.ONE));

         return var4;
      }
   }
}
