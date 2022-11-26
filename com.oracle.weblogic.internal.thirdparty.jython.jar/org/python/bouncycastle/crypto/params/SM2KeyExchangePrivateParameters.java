package org.python.bouncycastle.crypto.params;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.math.ec.ECPoint;

public class SM2KeyExchangePrivateParameters implements CipherParameters {
   private final boolean initiator;
   private final ECPrivateKeyParameters staticPrivateKey;
   private final ECPoint staticPublicPoint;
   private final ECPrivateKeyParameters ephemeralPrivateKey;
   private final ECPoint ephemeralPublicPoint;

   public SM2KeyExchangePrivateParameters(boolean var1, ECPrivateKeyParameters var2, ECPrivateKeyParameters var3) {
      if (var2 == null) {
         throw new NullPointerException("staticPrivateKey cannot be null");
      } else if (var3 == null) {
         throw new NullPointerException("ephemeralPrivateKey cannot be null");
      } else {
         ECDomainParameters var4 = var2.getParameters();
         if (!var4.equals(var3.getParameters())) {
            throw new IllegalArgumentException("Static and ephemeral private keys have different domain parameters");
         } else {
            this.initiator = var1;
            this.staticPrivateKey = var2;
            this.staticPublicPoint = var4.getG().multiply(var2.getD()).normalize();
            this.ephemeralPrivateKey = var3;
            this.ephemeralPublicPoint = var4.getG().multiply(var3.getD()).normalize();
         }
      }
   }

   public boolean isInitiator() {
      return this.initiator;
   }

   public ECPrivateKeyParameters getStaticPrivateKey() {
      return this.staticPrivateKey;
   }

   public ECPoint getStaticPublicPoint() {
      return this.staticPublicPoint;
   }

   public ECPrivateKeyParameters getEphemeralPrivateKey() {
      return this.ephemeralPrivateKey;
   }

   public ECPoint getEphemeralPublicPoint() {
      return this.ephemeralPublicPoint;
   }
}
