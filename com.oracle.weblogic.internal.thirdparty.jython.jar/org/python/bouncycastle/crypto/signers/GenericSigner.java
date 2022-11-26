package org.python.bouncycastle.crypto.signers;

import org.python.bouncycastle.crypto.AsymmetricBlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.CryptoException;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.Signer;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.util.Arrays;

public class GenericSigner implements Signer {
   private final AsymmetricBlockCipher engine;
   private final Digest digest;
   private boolean forSigning;

   public GenericSigner(AsymmetricBlockCipher var1, Digest var2) {
      this.engine = var1;
      this.digest = var2;
   }

   public void init(boolean var1, CipherParameters var2) {
      this.forSigning = var1;
      AsymmetricKeyParameter var3;
      if (var2 instanceof ParametersWithRandom) {
         var3 = (AsymmetricKeyParameter)((ParametersWithRandom)var2).getParameters();
      } else {
         var3 = (AsymmetricKeyParameter)var2;
      }

      if (var1 && !var3.isPrivate()) {
         throw new IllegalArgumentException("signing requires private key");
      } else if (!var1 && var3.isPrivate()) {
         throw new IllegalArgumentException("verification requires public key");
      } else {
         this.reset();
         this.engine.init(var1, var2);
      }
   }

   public void update(byte var1) {
      this.digest.update(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.digest.update(var1, var2, var3);
   }

   public byte[] generateSignature() throws CryptoException, DataLengthException {
      if (!this.forSigning) {
         throw new IllegalStateException("GenericSigner not initialised for signature generation.");
      } else {
         byte[] var1 = new byte[this.digest.getDigestSize()];
         this.digest.doFinal(var1, 0);
         return this.engine.processBlock(var1, 0, var1.length);
      }
   }

   public boolean verifySignature(byte[] var1) {
      if (this.forSigning) {
         throw new IllegalStateException("GenericSigner not initialised for verification");
      } else {
         byte[] var2 = new byte[this.digest.getDigestSize()];
         this.digest.doFinal(var2, 0);

         try {
            byte[] var3 = this.engine.processBlock(var1, 0, var1.length);
            if (var3.length < var2.length) {
               byte[] var4 = new byte[var2.length];
               System.arraycopy(var3, 0, var4, var4.length - var3.length, var3.length);
               var3 = var4;
            }

            return Arrays.constantTimeAreEqual(var3, var2);
         } catch (Exception var5) {
            return false;
         }
      }
   }

   public void reset() {
      this.digest.reset();
   }
}
