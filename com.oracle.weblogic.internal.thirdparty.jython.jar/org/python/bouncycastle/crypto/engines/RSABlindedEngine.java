package org.python.bouncycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.AsymmetricBlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.crypto.params.RSAKeyParameters;
import org.python.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.python.bouncycastle.util.BigIntegers;

public class RSABlindedEngine implements AsymmetricBlockCipher {
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private RSACoreEngine core = new RSACoreEngine();
   private RSAKeyParameters key;
   private SecureRandom random;

   public void init(boolean var1, CipherParameters var2) {
      this.core.init(var1, var2);
      if (var2 instanceof ParametersWithRandom) {
         ParametersWithRandom var3 = (ParametersWithRandom)var2;
         this.key = (RSAKeyParameters)var3.getParameters();
         this.random = var3.getRandom();
      } else {
         this.key = (RSAKeyParameters)var2;
         this.random = new SecureRandom();
      }

   }

   public int getInputBlockSize() {
      return this.core.getInputBlockSize();
   }

   public int getOutputBlockSize() {
      return this.core.getOutputBlockSize();
   }

   public byte[] processBlock(byte[] var1, int var2, int var3) {
      if (this.key == null) {
         throw new IllegalStateException("RSA engine not initialised");
      } else {
         BigInteger var4 = this.core.convertInput(var1, var2, var3);
         BigInteger var12;
         if (this.key instanceof RSAPrivateCrtKeyParameters) {
            RSAPrivateCrtKeyParameters var5 = (RSAPrivateCrtKeyParameters)this.key;
            BigInteger var6 = var5.getPublicExponent();
            if (var6 != null) {
               BigInteger var7 = var5.getModulus();
               BigInteger var8 = BigIntegers.createRandomInRange(ONE, var7.subtract(ONE), this.random);
               BigInteger var9 = var8.modPow(var6, var7).multiply(var4).mod(var7);
               BigInteger var10 = this.core.processBlock(var9);
               BigInteger var11 = var8.modInverse(var7);
               var12 = var10.multiply(var11).mod(var7);
               if (!var4.equals(var12.modPow(var6, var7))) {
                  throw new IllegalStateException("RSA engine faulty decryption/signing detected");
               }
            } else {
               var12 = this.core.processBlock(var4);
            }
         } else {
            var12 = this.core.processBlock(var4);
         }

         return this.core.convertOutput(var12);
      }
   }
}
