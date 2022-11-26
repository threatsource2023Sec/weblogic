package org.python.bouncycastle.crypto.tls;

import org.python.bouncycastle.crypto.AsymmetricBlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.CryptoException;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.Signer;
import org.python.bouncycastle.crypto.digests.NullDigest;
import org.python.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.python.bouncycastle.crypto.engines.RSABlindedEngine;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.crypto.params.RSAKeyParameters;
import org.python.bouncycastle.crypto.signers.GenericSigner;
import org.python.bouncycastle.crypto.signers.RSADigestSigner;

public class TlsRSASigner extends AbstractTlsSigner {
   public byte[] generateRawSignature(SignatureAndHashAlgorithm var1, AsymmetricKeyParameter var2, byte[] var3) throws CryptoException {
      Signer var4 = this.makeSigner(var1, true, true, new ParametersWithRandom(var2, this.context.getSecureRandom()));
      var4.update(var3, 0, var3.length);
      return var4.generateSignature();
   }

   public boolean verifyRawSignature(SignatureAndHashAlgorithm var1, byte[] var2, AsymmetricKeyParameter var3, byte[] var4) throws CryptoException {
      Signer var5 = this.makeSigner(var1, true, false, var3);
      var5.update(var4, 0, var4.length);
      return var5.verifySignature(var2);
   }

   public Signer createSigner(SignatureAndHashAlgorithm var1, AsymmetricKeyParameter var2) {
      return this.makeSigner(var1, false, true, new ParametersWithRandom(var2, this.context.getSecureRandom()));
   }

   public Signer createVerifyer(SignatureAndHashAlgorithm var1, AsymmetricKeyParameter var2) {
      return this.makeSigner(var1, false, false, var2);
   }

   public boolean isValidPublicKey(AsymmetricKeyParameter var1) {
      return var1 instanceof RSAKeyParameters && !var1.isPrivate();
   }

   protected Signer makeSigner(SignatureAndHashAlgorithm var1, boolean var2, boolean var3, CipherParameters var4) {
      if (var1 != null != TlsUtils.isTLSv12(this.context)) {
         throw new IllegalStateException();
      } else if (var1 != null && var1.getSignature() != 1) {
         throw new IllegalStateException();
      } else {
         Object var5;
         if (var2) {
            var5 = new NullDigest();
         } else if (var1 == null) {
            var5 = new CombinedHash();
         } else {
            var5 = TlsUtils.createHash(var1.getHash());
         }

         Object var6;
         if (var1 != null) {
            var6 = new RSADigestSigner((Digest)var5, TlsUtils.getOIDForHashAlgorithm(var1.getHash()));
         } else {
            var6 = new GenericSigner(this.createRSAImpl(), (Digest)var5);
         }

         ((Signer)var6).init(var3, var4);
         return (Signer)var6;
      }
   }

   protected AsymmetricBlockCipher createRSAImpl() {
      return new PKCS1Encoding(new RSABlindedEngine());
   }
}
