package org.cryptacular.adapter;

import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;

public final class Converter {
   private Converter() {
   }

   public static PrivateKey convertPrivateKey(AsymmetricKeyParameter bcKey) {
      if (!bcKey.isPrivate()) {
         throw new IllegalArgumentException("AsymmetricKeyParameter is not a private key: " + bcKey);
      } else {
         Object key;
         if (bcKey instanceof DSAPrivateKeyParameters) {
            key = new WrappedDSAPrivateKey((DSAPrivateKeyParameters)bcKey);
         } else if (bcKey instanceof ECPrivateKeyParameters) {
            key = new WrappedECPrivateKey((ECPrivateKeyParameters)bcKey);
         } else {
            if (!(bcKey instanceof RSAPrivateCrtKeyParameters)) {
               throw new IllegalArgumentException("Unsupported private key " + bcKey);
            }

            key = new WrappedRSAPrivateCrtKey((RSAPrivateCrtKeyParameters)bcKey);
         }

         return (PrivateKey)key;
      }
   }

   public static PublicKey convertPublicKey(AsymmetricKeyParameter bcKey) {
      if (bcKey.isPrivate()) {
         throw new IllegalArgumentException("AsymmetricKeyParameter is not a public key: " + bcKey);
      } else {
         Object key;
         if (bcKey instanceof DSAPublicKeyParameters) {
            key = new WrappedDSAPublicKey((DSAPublicKeyParameters)bcKey);
         } else if (bcKey instanceof ECPublicKeyParameters) {
            key = new WrappedECPublicKey((ECPublicKeyParameters)bcKey);
         } else {
            if (!(bcKey instanceof RSAKeyParameters)) {
               throw new IllegalArgumentException("Unsupported public key " + bcKey);
            }

            key = new WrappedRSAPublicKey((RSAKeyParameters)bcKey);
         }

         return (PublicKey)key;
      }
   }
}
