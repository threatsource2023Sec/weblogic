package org.python.bouncycastle.pqc.jcajce.provider.rainbow;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters;
import org.python.bouncycastle.pqc.crypto.rainbow.RainbowPublicKeyParameters;

public class RainbowKeysToParams {
   public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey var0) throws InvalidKeyException {
      if (var0 instanceof BCRainbowPublicKey) {
         BCRainbowPublicKey var1 = (BCRainbowPublicKey)var0;
         return new RainbowPublicKeyParameters(var1.getDocLength(), var1.getCoeffQuadratic(), var1.getCoeffSingular(), var1.getCoeffScalar());
      } else {
         throw new InvalidKeyException("can't identify Rainbow public key: " + var0.getClass().getName());
      }
   }

   public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey var0) throws InvalidKeyException {
      if (var0 instanceof BCRainbowPrivateKey) {
         BCRainbowPrivateKey var1 = (BCRainbowPrivateKey)var0;
         return new RainbowPrivateKeyParameters(var1.getInvA1(), var1.getB1(), var1.getInvA2(), var1.getB2(), var1.getVi(), var1.getLayers());
      } else {
         throw new InvalidKeyException("can't identify Rainbow private key.");
      }
   }
}
