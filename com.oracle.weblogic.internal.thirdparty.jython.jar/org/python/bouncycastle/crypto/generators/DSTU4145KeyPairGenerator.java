package org.python.bouncycastle.crypto.generators;

import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;

public class DSTU4145KeyPairGenerator extends ECKeyPairGenerator {
   public AsymmetricCipherKeyPair generateKeyPair() {
      AsymmetricCipherKeyPair var1 = super.generateKeyPair();
      ECPublicKeyParameters var2 = (ECPublicKeyParameters)var1.getPublic();
      ECPrivateKeyParameters var3 = (ECPrivateKeyParameters)var1.getPrivate();
      var2 = new ECPublicKeyParameters(var2.getQ().negate(), var2.getParameters());
      return new AsymmetricCipherKeyPair(var2, var3);
   }
}
