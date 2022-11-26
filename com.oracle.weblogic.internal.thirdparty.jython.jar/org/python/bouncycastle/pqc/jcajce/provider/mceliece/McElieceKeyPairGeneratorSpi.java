package org.python.bouncycastle.pqc.jcajce.provider.mceliece;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.pqc.crypto.mceliece.McElieceKeyGenerationParameters;
import org.python.bouncycastle.pqc.crypto.mceliece.McElieceKeyPairGenerator;
import org.python.bouncycastle.pqc.crypto.mceliece.McElieceParameters;
import org.python.bouncycastle.pqc.crypto.mceliece.McEliecePrivateKeyParameters;
import org.python.bouncycastle.pqc.crypto.mceliece.McEliecePublicKeyParameters;
import org.python.bouncycastle.pqc.jcajce.spec.McElieceKeyGenParameterSpec;

public class McElieceKeyPairGeneratorSpi extends KeyPairGenerator {
   McElieceKeyPairGenerator kpg;

   public McElieceKeyPairGeneratorSpi() {
      super("McEliece");
   }

   public void initialize(AlgorithmParameterSpec var1) throws InvalidAlgorithmParameterException {
      this.kpg = new McElieceKeyPairGenerator();
      super.initialize(var1);
      McElieceKeyGenParameterSpec var2 = (McElieceKeyGenParameterSpec)var1;
      McElieceKeyGenerationParameters var3 = new McElieceKeyGenerationParameters(new SecureRandom(), new McElieceParameters(var2.getM(), var2.getT()));
      this.kpg.init(var3);
   }

   public void initialize(int var1, SecureRandom var2) {
      McElieceKeyGenParameterSpec var3 = new McElieceKeyGenParameterSpec();

      try {
         this.initialize(var3);
      } catch (InvalidAlgorithmParameterException var5) {
      }

   }

   public KeyPair generateKeyPair() {
      AsymmetricCipherKeyPair var1 = this.kpg.generateKeyPair();
      McEliecePrivateKeyParameters var2 = (McEliecePrivateKeyParameters)var1.getPrivate();
      McEliecePublicKeyParameters var3 = (McEliecePublicKeyParameters)var1.getPublic();
      return new KeyPair(new BCMcEliecePublicKey(var3), new BCMcEliecePrivateKey(var2));
   }
}
