package org.python.bouncycastle.pqc.jcajce.provider.mceliece;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.pqc.crypto.mceliece.McElieceCCA2KeyGenerationParameters;
import org.python.bouncycastle.pqc.crypto.mceliece.McElieceCCA2KeyPairGenerator;
import org.python.bouncycastle.pqc.crypto.mceliece.McElieceCCA2Parameters;
import org.python.bouncycastle.pqc.crypto.mceliece.McElieceCCA2PrivateKeyParameters;
import org.python.bouncycastle.pqc.crypto.mceliece.McElieceCCA2PublicKeyParameters;
import org.python.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;

public class McElieceCCA2KeyPairGeneratorSpi extends KeyPairGenerator {
   private McElieceCCA2KeyPairGenerator kpg;

   public McElieceCCA2KeyPairGeneratorSpi() {
      super("McEliece-CCA2");
   }

   public void initialize(AlgorithmParameterSpec var1) throws InvalidAlgorithmParameterException {
      this.kpg = new McElieceCCA2KeyPairGenerator();
      super.initialize(var1);
      McElieceCCA2KeyGenParameterSpec var2 = (McElieceCCA2KeyGenParameterSpec)var1;
      McElieceCCA2KeyGenerationParameters var3 = new McElieceCCA2KeyGenerationParameters(new SecureRandom(), new McElieceCCA2Parameters(var2.getM(), var2.getT(), var2.getDigest()));
      this.kpg.init(var3);
   }

   public void initialize(int var1, SecureRandom var2) {
      this.kpg = new McElieceCCA2KeyPairGenerator();
      McElieceCCA2KeyGenerationParameters var3 = new McElieceCCA2KeyGenerationParameters(var2, new McElieceCCA2Parameters());
      this.kpg.init(var3);
   }

   public KeyPair generateKeyPair() {
      AsymmetricCipherKeyPair var1 = this.kpg.generateKeyPair();
      McElieceCCA2PrivateKeyParameters var2 = (McElieceCCA2PrivateKeyParameters)var1.getPrivate();
      McElieceCCA2PublicKeyParameters var3 = (McElieceCCA2PublicKeyParameters)var1.getPublic();
      return new KeyPair(new BCMcElieceCCA2PublicKey(var3), new BCMcElieceCCA2PrivateKey(var2));
   }
}
