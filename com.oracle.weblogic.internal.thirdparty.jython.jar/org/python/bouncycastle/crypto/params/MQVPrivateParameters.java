package org.python.bouncycastle.crypto.params;

import org.python.bouncycastle.crypto.CipherParameters;

public class MQVPrivateParameters implements CipherParameters {
   private ECPrivateKeyParameters staticPrivateKey;
   private ECPrivateKeyParameters ephemeralPrivateKey;
   private ECPublicKeyParameters ephemeralPublicKey;

   public MQVPrivateParameters(ECPrivateKeyParameters var1, ECPrivateKeyParameters var2) {
      this(var1, var2, (ECPublicKeyParameters)null);
   }

   public MQVPrivateParameters(ECPrivateKeyParameters var1, ECPrivateKeyParameters var2, ECPublicKeyParameters var3) {
      if (var1 == null) {
         throw new NullPointerException("staticPrivateKey cannot be null");
      } else if (var2 == null) {
         throw new NullPointerException("ephemeralPrivateKey cannot be null");
      } else {
         ECDomainParameters var4 = var1.getParameters();
         if (!var4.equals(var2.getParameters())) {
            throw new IllegalArgumentException("Static and ephemeral private keys have different domain parameters");
         } else {
            if (var3 == null) {
               var3 = new ECPublicKeyParameters(var4.getG().multiply(var2.getD()), var4);
            } else if (!var4.equals(var3.getParameters())) {
               throw new IllegalArgumentException("Ephemeral public key has different domain parameters");
            }

            this.staticPrivateKey = var1;
            this.ephemeralPrivateKey = var2;
            this.ephemeralPublicKey = var3;
         }
      }
   }

   public ECPrivateKeyParameters getStaticPrivateKey() {
      return this.staticPrivateKey;
   }

   public ECPrivateKeyParameters getEphemeralPrivateKey() {
      return this.ephemeralPrivateKey;
   }

   public ECPublicKeyParameters getEphemeralPublicKey() {
      return this.ephemeralPublicKey;
   }
}
