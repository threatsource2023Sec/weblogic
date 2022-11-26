package org.python.bouncycastle.crypto.tls;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.CryptoException;
import org.python.bouncycastle.crypto.DSA;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.Signer;
import org.python.bouncycastle.crypto.digests.NullDigest;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.crypto.signers.DSADigestSigner;

public abstract class TlsDSASigner extends AbstractTlsSigner {
   public byte[] generateRawSignature(SignatureAndHashAlgorithm var1, AsymmetricKeyParameter var2, byte[] var3) throws CryptoException {
      Signer var4 = this.makeSigner(var1, true, true, new ParametersWithRandom(var2, this.context.getSecureRandom()));
      if (var1 == null) {
         var4.update(var3, 16, 20);
      } else {
         var4.update(var3, 0, var3.length);
      }

      return var4.generateSignature();
   }

   public boolean verifyRawSignature(SignatureAndHashAlgorithm var1, byte[] var2, AsymmetricKeyParameter var3, byte[] var4) throws CryptoException {
      Signer var5 = this.makeSigner(var1, true, false, var3);
      if (var1 == null) {
         var5.update(var4, 16, 20);
      } else {
         var5.update(var4, 0, var4.length);
      }

      return var5.verifySignature(var2);
   }

   public Signer createSigner(SignatureAndHashAlgorithm var1, AsymmetricKeyParameter var2) {
      return this.makeSigner(var1, false, true, var2);
   }

   public Signer createVerifyer(SignatureAndHashAlgorithm var1, AsymmetricKeyParameter var2) {
      return this.makeSigner(var1, false, false, var2);
   }

   protected CipherParameters makeInitParameters(boolean var1, CipherParameters var2) {
      return var2;
   }

   protected Signer makeSigner(SignatureAndHashAlgorithm var1, boolean var2, boolean var3, CipherParameters var4) {
      if (var1 != null != TlsUtils.isTLSv12(this.context)) {
         throw new IllegalStateException();
      } else if (var1 != null && var1.getSignature() != this.getSignatureAlgorithm()) {
         throw new IllegalStateException();
      } else {
         short var5 = var1 == null ? 2 : var1.getHash();
         Object var6 = var2 ? new NullDigest() : TlsUtils.createHash(var5);
         DSADigestSigner var7 = new DSADigestSigner(this.createDSAImpl(var5), (Digest)var6);
         var7.init(var3, this.makeInitParameters(var3, var4));
         return var7;
      }
   }

   protected abstract short getSignatureAlgorithm();

   protected abstract DSA createDSAImpl(short var1);
}
