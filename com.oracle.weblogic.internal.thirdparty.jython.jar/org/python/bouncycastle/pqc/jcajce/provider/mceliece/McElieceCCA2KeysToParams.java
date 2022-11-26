package org.python.bouncycastle.pqc.jcajce.provider.mceliece;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class McElieceCCA2KeysToParams {
   public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey var0) throws InvalidKeyException {
      if (var0 instanceof BCMcElieceCCA2PublicKey) {
         BCMcElieceCCA2PublicKey var1 = (BCMcElieceCCA2PublicKey)var0;
         return var1.getKeyParams();
      } else {
         throw new InvalidKeyException("can't identify McElieceCCA2 public key: " + var0.getClass().getName());
      }
   }

   public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey var0) throws InvalidKeyException {
      if (var0 instanceof BCMcElieceCCA2PrivateKey) {
         BCMcElieceCCA2PrivateKey var1 = (BCMcElieceCCA2PrivateKey)var0;
         return var1.getKeyParams();
      } else {
         throw new InvalidKeyException("can't identify McElieceCCA2 private key.");
      }
   }
}
