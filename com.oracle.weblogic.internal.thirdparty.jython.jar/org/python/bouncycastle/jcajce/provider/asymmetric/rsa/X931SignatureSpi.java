package org.python.bouncycastle.jcajce.provider.asymmetric.rsa;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import org.python.bouncycastle.crypto.AsymmetricBlockCipher;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.digests.RIPEMD128Digest;
import org.python.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.python.bouncycastle.crypto.digests.WhirlpoolDigest;
import org.python.bouncycastle.crypto.engines.RSABlindedEngine;
import org.python.bouncycastle.crypto.params.RSAKeyParameters;
import org.python.bouncycastle.crypto.signers.X931Signer;
import org.python.bouncycastle.crypto.util.DigestFactory;

public class X931SignatureSpi extends SignatureSpi {
   private X931Signer signer;

   protected X931SignatureSpi(Digest var1, AsymmetricBlockCipher var2) {
      this.signer = new X931Signer(var2, var1);
   }

   protected void engineInitVerify(PublicKey var1) throws InvalidKeyException {
      RSAKeyParameters var2 = RSAUtil.generatePublicKeyParameter((RSAPublicKey)var1);
      this.signer.init(false, var2);
   }

   protected void engineInitSign(PrivateKey var1) throws InvalidKeyException {
      RSAKeyParameters var2 = RSAUtil.generatePrivateKeyParameter((RSAPrivateKey)var1);
      this.signer.init(true, var2);
   }

   protected void engineUpdate(byte var1) throws SignatureException {
      this.signer.update(var1);
   }

   protected void engineUpdate(byte[] var1, int var2, int var3) throws SignatureException {
      this.signer.update(var1, var2, var3);
   }

   protected byte[] engineSign() throws SignatureException {
      try {
         byte[] var1 = this.signer.generateSignature();
         return var1;
      } catch (Exception var2) {
         throw new SignatureException(var2.toString());
      }
   }

   protected boolean engineVerify(byte[] var1) throws SignatureException {
      boolean var2 = this.signer.verifySignature(var1);
      return var2;
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

   public static class RIPEMD128WithRSAEncryption extends X931SignatureSpi {
      public RIPEMD128WithRSAEncryption() {
         super(new RIPEMD128Digest(), new RSABlindedEngine());
      }
   }

   public static class RIPEMD160WithRSAEncryption extends X931SignatureSpi {
      public RIPEMD160WithRSAEncryption() {
         super(new RIPEMD160Digest(), new RSABlindedEngine());
      }
   }

   public static class SHA1WithRSAEncryption extends X931SignatureSpi {
      public SHA1WithRSAEncryption() {
         super(DigestFactory.createSHA1(), new RSABlindedEngine());
      }
   }

   public static class SHA224WithRSAEncryption extends X931SignatureSpi {
      public SHA224WithRSAEncryption() {
         super(DigestFactory.createSHA224(), new RSABlindedEngine());
      }
   }

   public static class SHA256WithRSAEncryption extends X931SignatureSpi {
      public SHA256WithRSAEncryption() {
         super(DigestFactory.createSHA256(), new RSABlindedEngine());
      }
   }

   public static class SHA384WithRSAEncryption extends X931SignatureSpi {
      public SHA384WithRSAEncryption() {
         super(DigestFactory.createSHA384(), new RSABlindedEngine());
      }
   }

   public static class SHA512WithRSAEncryption extends X931SignatureSpi {
      public SHA512WithRSAEncryption() {
         super(DigestFactory.createSHA512(), new RSABlindedEngine());
      }
   }

   public static class SHA512_224WithRSAEncryption extends X931SignatureSpi {
      public SHA512_224WithRSAEncryption() {
         super(DigestFactory.createSHA512_224(), new RSABlindedEngine());
      }
   }

   public static class SHA512_256WithRSAEncryption extends X931SignatureSpi {
      public SHA512_256WithRSAEncryption() {
         super(DigestFactory.createSHA512_256(), new RSABlindedEngine());
      }
   }

   public static class WhirlpoolWithRSAEncryption extends X931SignatureSpi {
      public WhirlpoolWithRSAEncryption() {
         super(new WhirlpoolDigest(), new RSABlindedEngine());
      }
   }
}
