package org.python.bouncycastle.jcajce.provider.asymmetric.util;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.DHParameters;
import org.python.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPublicKey;

public class DHUtil {
   public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey var0) throws InvalidKeyException {
      if (var0 instanceof BCDHPublicKey) {
         return ((BCDHPublicKey)var0).engineGetKeyParameters();
      } else if (var0 instanceof DHPublicKey) {
         DHPublicKey var1 = (DHPublicKey)var0;
         return new DHPublicKeyParameters(var1.getY(), new DHParameters(var1.getParams().getP(), var1.getParams().getG(), (BigInteger)null, var1.getParams().getL()));
      } else {
         throw new InvalidKeyException("can't identify DH public key.");
      }
   }

   public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey var0) throws InvalidKeyException {
      if (var0 instanceof DHPrivateKey) {
         DHPrivateKey var1 = (DHPrivateKey)var0;
         return new DHPrivateKeyParameters(var1.getX(), new DHParameters(var1.getParams().getP(), var1.getParams().getG(), (BigInteger)null, var1.getParams().getL()));
      } else {
         throw new InvalidKeyException("can't identify DH private key.");
      }
   }
}
