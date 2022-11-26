package org.python.bouncycastle.crypto.engines;

import java.math.BigInteger;
import org.python.bouncycastle.crypto.AsymmetricBlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.crypto.params.RSABlindingParameters;
import org.python.bouncycastle.crypto.params.RSAKeyParameters;

public class RSABlindingEngine implements AsymmetricBlockCipher {
   private RSACoreEngine core = new RSACoreEngine();
   private RSAKeyParameters key;
   private BigInteger blindingFactor;
   private boolean forEncryption;

   public void init(boolean var1, CipherParameters var2) {
      RSABlindingParameters var4;
      if (var2 instanceof ParametersWithRandom) {
         ParametersWithRandom var3 = (ParametersWithRandom)var2;
         var4 = (RSABlindingParameters)var3.getParameters();
      } else {
         var4 = (RSABlindingParameters)var2;
      }

      this.core.init(var1, var4.getPublicKey());
      this.forEncryption = var1;
      this.key = var4.getPublicKey();
      this.blindingFactor = var4.getBlindingFactor();
   }

   public int getInputBlockSize() {
      return this.core.getInputBlockSize();
   }

   public int getOutputBlockSize() {
      return this.core.getOutputBlockSize();
   }

   public byte[] processBlock(byte[] var1, int var2, int var3) {
      BigInteger var4 = this.core.convertInput(var1, var2, var3);
      if (this.forEncryption) {
         var4 = this.blindMessage(var4);
      } else {
         var4 = this.unblindMessage(var4);
      }

      return this.core.convertOutput(var4);
   }

   private BigInteger blindMessage(BigInteger var1) {
      BigInteger var2 = this.blindingFactor;
      var2 = var1.multiply(var2.modPow(this.key.getExponent(), this.key.getModulus()));
      var2 = var2.mod(this.key.getModulus());
      return var2;
   }

   private BigInteger unblindMessage(BigInteger var1) {
      BigInteger var2 = this.key.getModulus();
      BigInteger var4 = this.blindingFactor.modInverse(var2);
      BigInteger var3 = var1.multiply(var4);
      var3 = var3.mod(var2);
      return var3;
   }
}
