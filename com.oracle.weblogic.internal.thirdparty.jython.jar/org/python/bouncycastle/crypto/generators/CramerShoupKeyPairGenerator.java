package org.python.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.python.bouncycastle.crypto.KeyGenerationParameters;
import org.python.bouncycastle.crypto.params.CramerShoupKeyGenerationParameters;
import org.python.bouncycastle.crypto.params.CramerShoupParameters;
import org.python.bouncycastle.crypto.params.CramerShoupPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.CramerShoupPublicKeyParameters;
import org.python.bouncycastle.util.BigIntegers;

public class CramerShoupKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private CramerShoupKeyGenerationParameters param;

   public void init(KeyGenerationParameters var1) {
      this.param = (CramerShoupKeyGenerationParameters)var1;
   }

   public AsymmetricCipherKeyPair generateKeyPair() {
      CramerShoupParameters var1 = this.param.getParameters();
      CramerShoupPrivateKeyParameters var2 = this.generatePrivateKey(this.param.getRandom(), var1);
      CramerShoupPublicKeyParameters var3 = this.calculatePublicKey(var1, var2);
      var2.setPk(var3);
      return new AsymmetricCipherKeyPair(var3, var2);
   }

   private BigInteger generateRandomElement(BigInteger var1, SecureRandom var2) {
      return BigIntegers.createRandomInRange(ONE, var1.subtract(ONE), var2);
   }

   private CramerShoupPrivateKeyParameters generatePrivateKey(SecureRandom var1, CramerShoupParameters var2) {
      BigInteger var3 = var2.getP();
      CramerShoupPrivateKeyParameters var4 = new CramerShoupPrivateKeyParameters(var2, this.generateRandomElement(var3, var1), this.generateRandomElement(var3, var1), this.generateRandomElement(var3, var1), this.generateRandomElement(var3, var1), this.generateRandomElement(var3, var1));
      return var4;
   }

   private CramerShoupPublicKeyParameters calculatePublicKey(CramerShoupParameters var1, CramerShoupPrivateKeyParameters var2) {
      BigInteger var3 = var1.getG1();
      BigInteger var4 = var1.getG2();
      BigInteger var5 = var1.getP();
      BigInteger var6 = var3.modPow(var2.getX1(), var5).multiply(var4.modPow(var2.getX2(), var5));
      BigInteger var7 = var3.modPow(var2.getY1(), var5).multiply(var4.modPow(var2.getY2(), var5));
      BigInteger var8 = var3.modPow(var2.getZ(), var5);
      return new CramerShoupPublicKeyParameters(var1, var6, var7, var8);
   }
}
