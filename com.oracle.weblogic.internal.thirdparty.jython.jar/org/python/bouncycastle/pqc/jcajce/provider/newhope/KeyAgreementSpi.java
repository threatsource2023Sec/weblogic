package org.python.bouncycastle.pqc.jcajce.provider.newhope;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.ShortBufferException;
import org.python.bouncycastle.crypto.DerivationFunction;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import org.python.bouncycastle.pqc.crypto.ExchangePair;
import org.python.bouncycastle.pqc.crypto.newhope.NHAgreement;
import org.python.bouncycastle.pqc.crypto.newhope.NHExchangePairGenerator;
import org.python.bouncycastle.pqc.crypto.newhope.NHPublicKeyParameters;
import org.python.bouncycastle.util.Arrays;

public class KeyAgreementSpi extends BaseAgreementSpi {
   private NHAgreement agreement;
   private BCNHPublicKey otherPartyKey;
   private NHExchangePairGenerator exchangePairGenerator;
   private byte[] shared;

   public KeyAgreementSpi() {
      super("NH", (DerivationFunction)null);
   }

   protected void engineInit(Key var1, SecureRandom var2) throws InvalidKeyException {
      if (var1 != null) {
         this.agreement = new NHAgreement();
         this.agreement.init(((BCNHPrivateKey)var1).getKeyParams());
      } else {
         this.exchangePairGenerator = new NHExchangePairGenerator(var2);
      }

   }

   protected void engineInit(Key var1, AlgorithmParameterSpec var2, SecureRandom var3) throws InvalidKeyException, InvalidAlgorithmParameterException {
      throw new InvalidAlgorithmParameterException("NewHope does not require parameters");
   }

   protected Key engineDoPhase(Key var1, boolean var2) throws InvalidKeyException, IllegalStateException {
      if (!var2) {
         throw new IllegalStateException("NewHope can only be between two parties.");
      } else {
         this.otherPartyKey = (BCNHPublicKey)var1;
         if (this.exchangePairGenerator != null) {
            ExchangePair var3 = this.exchangePairGenerator.generateExchange((AsymmetricKeyParameter)this.otherPartyKey.getKeyParams());
            this.shared = var3.getSharedValue();
            return new BCNHPublicKey((NHPublicKeyParameters)var3.getPublicKey());
         } else {
            this.shared = this.agreement.calculateAgreement(this.otherPartyKey.getKeyParams());
            return null;
         }
      }
   }

   protected byte[] engineGenerateSecret() throws IllegalStateException {
      byte[] var1 = Arrays.clone(this.shared);
      Arrays.fill((byte[])this.shared, (byte)0);
      return var1;
   }

   protected int engineGenerateSecret(byte[] var1, int var2) throws IllegalStateException, ShortBufferException {
      System.arraycopy(this.shared, 0, var1, var2, this.shared.length);
      Arrays.fill((byte[])this.shared, (byte)0);
      return this.shared.length;
   }

   protected byte[] calcSecret() {
      return this.engineGenerateSecret();
   }
}
