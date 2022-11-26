package org.python.bouncycastle.pqc.jcajce.provider.mceliece;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.pqc.crypto.mceliece.McEliecePrivateKeyParameters;

public class McElieceKeysToParams {
   public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey var0) throws InvalidKeyException {
      if (var0 instanceof BCMcEliecePublicKey) {
         BCMcEliecePublicKey var1 = (BCMcEliecePublicKey)var0;
         return var1.getKeyParams();
      } else {
         throw new InvalidKeyException("can't identify McEliece public key: " + var0.getClass().getName());
      }
   }

   public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey var0) throws InvalidKeyException {
      if (var0 instanceof BCMcEliecePrivateKey) {
         BCMcEliecePrivateKey var1 = (BCMcEliecePrivateKey)var0;
         return new McEliecePrivateKeyParameters(var1.getN(), var1.getK(), var1.getField(), var1.getGoppaPoly(), var1.getP1(), var1.getP2(), var1.getSInv());
      } else {
         throw new InvalidKeyException("can't identify McEliece private key.");
      }
   }
}
