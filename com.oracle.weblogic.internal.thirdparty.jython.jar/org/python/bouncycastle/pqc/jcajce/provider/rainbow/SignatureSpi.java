package org.python.bouncycastle.pqc.jcajce.provider.rainbow;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.digests.SHA224Digest;
import org.python.bouncycastle.crypto.digests.SHA256Digest;
import org.python.bouncycastle.crypto.digests.SHA384Digest;
import org.python.bouncycastle.crypto.digests.SHA512Digest;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.pqc.crypto.rainbow.RainbowSigner;

public class SignatureSpi extends java.security.SignatureSpi {
   private Digest digest;
   private RainbowSigner signer;
   private SecureRandom random;

   protected SignatureSpi(Digest var1, RainbowSigner var2) {
      this.digest = var1;
      this.signer = var2;
   }

   protected void engineInitVerify(PublicKey var1) throws InvalidKeyException {
      AsymmetricKeyParameter var2 = RainbowKeysToParams.generatePublicKeyParameter(var1);
      this.digest.reset();
      this.signer.init(false, var2);
   }

   protected void engineInitSign(PrivateKey var1, SecureRandom var2) throws InvalidKeyException {
      this.random = var2;
      this.engineInitSign(var1);
   }

   protected void engineInitSign(PrivateKey var1) throws InvalidKeyException {
      Object var2 = RainbowKeysToParams.generatePrivateKeyParameter(var1);
      if (this.random != null) {
         var2 = new ParametersWithRandom((CipherParameters)var2, this.random);
      }

      this.digest.reset();
      this.signer.init(true, (CipherParameters)var2);
   }

   protected void engineUpdate(byte var1) throws SignatureException {
      this.digest.update(var1);
   }

   protected void engineUpdate(byte[] var1, int var2, int var3) throws SignatureException {
      this.digest.update(var1, var2, var3);
   }

   protected byte[] engineSign() throws SignatureException {
      byte[] var1 = new byte[this.digest.getDigestSize()];
      this.digest.doFinal(var1, 0);

      try {
         byte[] var2 = this.signer.generateSignature(var1);
         return var2;
      } catch (Exception var3) {
         throw new SignatureException(var3.toString());
      }
   }

   protected boolean engineVerify(byte[] var1) throws SignatureException {
      byte[] var2 = new byte[this.digest.getDigestSize()];
      this.digest.doFinal(var2, 0);
      return this.signer.verifySignature(var2, var1);
   }

   protected void engineSetParameter(AlgorithmParameterSpec var1) {
      throw new UnsupportedOperationException("engineSetParameter unsupported");
   }

   /** @deprecated */
   protected void engineSetParameter(String var1, Object var2) {
      throw new UnsupportedOperationException("engineSetParameter unsupported");
   }

   /** @deprecated */
   protected Object engineGetParameter(String var1) {
      throw new UnsupportedOperationException("engineSetParameter unsupported");
   }

   public static class withSha224 extends SignatureSpi {
      public withSha224() {
         super(new SHA224Digest(), new RainbowSigner());
      }
   }

   public static class withSha256 extends SignatureSpi {
      public withSha256() {
         super(new SHA256Digest(), new RainbowSigner());
      }
   }

   public static class withSha384 extends SignatureSpi {
      public withSha384() {
         super(new SHA384Digest(), new RainbowSigner());
      }
   }

   public static class withSha512 extends SignatureSpi {
      public withSha512() {
         super(new SHA512Digest(), new RainbowSigner());
      }
   }
}
