package org.python.bouncycastle.jcajce.provider.asymmetric.rsa;

import java.io.ByteArrayOutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.crypto.AsymmetricBlockCipher;
import org.python.bouncycastle.crypto.CryptoException;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.engines.RSABlindedEngine;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.crypto.signers.PSSSigner;
import org.python.bouncycastle.jcajce.provider.util.DigestFactory;
import org.python.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;

public class PSSSignatureSpi extends SignatureSpi {
   private final JcaJceHelper helper;
   private AlgorithmParameters engineParams;
   private PSSParameterSpec paramSpec;
   private PSSParameterSpec originalSpec;
   private AsymmetricBlockCipher signer;
   private Digest contentDigest;
   private Digest mgfDigest;
   private int saltLength;
   private byte trailer;
   private boolean isRaw;
   private PSSSigner pss;

   private byte getTrailer(int var1) {
      if (var1 == 1) {
         return -68;
      } else {
         throw new IllegalArgumentException("unknown trailer field");
      }
   }

   private void setupContentDigest() {
      if (this.isRaw) {
         this.contentDigest = new NullPssDigest(this.mgfDigest);
      } else {
         this.contentDigest = this.mgfDigest;
      }

   }

   protected PSSSignatureSpi(AsymmetricBlockCipher var1, PSSParameterSpec var2) {
      this(var1, var2, false);
   }

   protected PSSSignatureSpi(AsymmetricBlockCipher var1, PSSParameterSpec var2, boolean var3) {
      this.helper = new BCJcaJceHelper();
      this.signer = var1;
      this.originalSpec = var2;
      if (var2 == null) {
         this.paramSpec = PSSParameterSpec.DEFAULT;
      } else {
         this.paramSpec = var2;
      }

      this.mgfDigest = DigestFactory.getDigest(this.paramSpec.getDigestAlgorithm());
      this.saltLength = this.paramSpec.getSaltLength();
      this.trailer = this.getTrailer(this.paramSpec.getTrailerField());
      this.isRaw = var3;
      this.setupContentDigest();
   }

   protected void engineInitVerify(PublicKey var1) throws InvalidKeyException {
      if (!(var1 instanceof RSAPublicKey)) {
         throw new InvalidKeyException("Supplied key is not a RSAPublicKey instance");
      } else {
         this.pss = new PSSSigner(this.signer, this.contentDigest, this.mgfDigest, this.saltLength, this.trailer);
         this.pss.init(false, RSAUtil.generatePublicKeyParameter((RSAPublicKey)var1));
      }
   }

   protected void engineInitSign(PrivateKey var1, SecureRandom var2) throws InvalidKeyException {
      if (!(var1 instanceof RSAPrivateKey)) {
         throw new InvalidKeyException("Supplied key is not a RSAPrivateKey instance");
      } else {
         this.pss = new PSSSigner(this.signer, this.contentDigest, this.mgfDigest, this.saltLength, this.trailer);
         this.pss.init(true, new ParametersWithRandom(RSAUtil.generatePrivateKeyParameter((RSAPrivateKey)var1), var2));
      }
   }

   protected void engineInitSign(PrivateKey var1) throws InvalidKeyException {
      if (!(var1 instanceof RSAPrivateKey)) {
         throw new InvalidKeyException("Supplied key is not a RSAPrivateKey instance");
      } else {
         this.pss = new PSSSigner(this.signer, this.contentDigest, this.mgfDigest, this.saltLength, this.trailer);
         this.pss.init(true, RSAUtil.generatePrivateKeyParameter((RSAPrivateKey)var1));
      }
   }

   protected void engineUpdate(byte var1) throws SignatureException {
      this.pss.update(var1);
   }

   protected void engineUpdate(byte[] var1, int var2, int var3) throws SignatureException {
      this.pss.update(var1, var2, var3);
   }

   protected byte[] engineSign() throws SignatureException {
      try {
         return this.pss.generateSignature();
      } catch (CryptoException var2) {
         throw new SignatureException(var2.getMessage());
      }
   }

   protected boolean engineVerify(byte[] var1) throws SignatureException {
      return this.pss.verifySignature(var1);
   }

   protected void engineSetParameter(AlgorithmParameterSpec var1) throws InvalidAlgorithmParameterException {
      if (var1 instanceof PSSParameterSpec) {
         PSSParameterSpec var2 = (PSSParameterSpec)var1;
         if (this.originalSpec != null && !DigestFactory.isSameDigest(this.originalSpec.getDigestAlgorithm(), var2.getDigestAlgorithm())) {
            throw new InvalidAlgorithmParameterException("parameter must be using " + this.originalSpec.getDigestAlgorithm());
         } else if (!var2.getMGFAlgorithm().equalsIgnoreCase("MGF1") && !var2.getMGFAlgorithm().equals(PKCSObjectIdentifiers.id_mgf1.getId())) {
            throw new InvalidAlgorithmParameterException("unknown mask generation function specified");
         } else if (!(var2.getMGFParameters() instanceof MGF1ParameterSpec)) {
            throw new InvalidAlgorithmParameterException("unknown MGF parameters");
         } else {
            MGF1ParameterSpec var3 = (MGF1ParameterSpec)var2.getMGFParameters();
            if (!DigestFactory.isSameDigest(var3.getDigestAlgorithm(), var2.getDigestAlgorithm())) {
               throw new InvalidAlgorithmParameterException("digest algorithm for MGF should be the same as for PSS parameters.");
            } else {
               Digest var4 = DigestFactory.getDigest(var3.getDigestAlgorithm());
               if (var4 == null) {
                  throw new InvalidAlgorithmParameterException("no match on MGF digest algorithm: " + var3.getDigestAlgorithm());
               } else {
                  this.engineParams = null;
                  this.paramSpec = var2;
                  this.mgfDigest = var4;
                  this.saltLength = this.paramSpec.getSaltLength();
                  this.trailer = this.getTrailer(this.paramSpec.getTrailerField());
                  this.setupContentDigest();
               }
            }
         }
      } else {
         throw new InvalidAlgorithmParameterException("Only PSSParameterSpec supported");
      }
   }

   protected AlgorithmParameters engineGetParameters() {
      if (this.engineParams == null && this.paramSpec != null) {
         try {
            this.engineParams = this.helper.createAlgorithmParameters("PSS");
            this.engineParams.init(this.paramSpec);
         } catch (Exception var2) {
            throw new RuntimeException(var2.toString());
         }
      }

      return this.engineParams;
   }

   /** @deprecated */
   protected void engineSetParameter(String var1, Object var2) {
      throw new UnsupportedOperationException("engineSetParameter unsupported");
   }

   protected Object engineGetParameter(String var1) {
      throw new UnsupportedOperationException("engineGetParameter unsupported");
   }

   private class NullPssDigest implements Digest {
      private ByteArrayOutputStream bOut = new ByteArrayOutputStream();
      private Digest baseDigest;
      private boolean oddTime = true;

      public NullPssDigest(Digest var2) {
         this.baseDigest = var2;
      }

      public String getAlgorithmName() {
         return "NULL";
      }

      public int getDigestSize() {
         return this.baseDigest.getDigestSize();
      }

      public void update(byte var1) {
         this.bOut.write(var1);
      }

      public void update(byte[] var1, int var2, int var3) {
         this.bOut.write(var1, var2, var3);
      }

      public int doFinal(byte[] var1, int var2) {
         byte[] var3 = this.bOut.toByteArray();
         if (this.oddTime) {
            System.arraycopy(var3, 0, var1, var2, var3.length);
         } else {
            this.baseDigest.update(var3, 0, var3.length);
            this.baseDigest.doFinal(var1, var2);
         }

         this.reset();
         this.oddTime = !this.oddTime;
         return var3.length;
      }

      public void reset() {
         this.bOut.reset();
         this.baseDigest.reset();
      }

      public int getByteLength() {
         return 0;
      }
   }

   public static class PSSwithRSA extends PSSSignatureSpi {
      public PSSwithRSA() {
         super(new RSABlindedEngine(), (PSSParameterSpec)null);
      }
   }

   public static class SHA1withRSA extends PSSSignatureSpi {
      public SHA1withRSA() {
         super(new RSABlindedEngine(), PSSParameterSpec.DEFAULT);
      }
   }

   public static class SHA224withRSA extends PSSSignatureSpi {
      public SHA224withRSA() {
         super(new RSABlindedEngine(), new PSSParameterSpec("SHA-224", "MGF1", new MGF1ParameterSpec("SHA-224"), 28, 1));
      }
   }

   public static class SHA256withRSA extends PSSSignatureSpi {
      public SHA256withRSA() {
         super(new RSABlindedEngine(), new PSSParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-256"), 32, 1));
      }
   }

   public static class SHA384withRSA extends PSSSignatureSpi {
      public SHA384withRSA() {
         super(new RSABlindedEngine(), new PSSParameterSpec("SHA-384", "MGF1", new MGF1ParameterSpec("SHA-384"), 48, 1));
      }
   }

   public static class SHA3_224withRSA extends PSSSignatureSpi {
      public SHA3_224withRSA() {
         super(new RSABlindedEngine(), new PSSParameterSpec("SHA3-224", "MGF1", new MGF1ParameterSpec("SHA3-224"), 28, 1));
      }
   }

   public static class SHA3_256withRSA extends PSSSignatureSpi {
      public SHA3_256withRSA() {
         super(new RSABlindedEngine(), new PSSParameterSpec("SHA3-256", "MGF1", new MGF1ParameterSpec("SHA3-256"), 32, 1));
      }
   }

   public static class SHA3_384withRSA extends PSSSignatureSpi {
      public SHA3_384withRSA() {
         super(new RSABlindedEngine(), new PSSParameterSpec("SHA3-384", "MGF1", new MGF1ParameterSpec("SHA3-384"), 48, 1));
      }
   }

   public static class SHA3_512withRSA extends PSSSignatureSpi {
      public SHA3_512withRSA() {
         super(new RSABlindedEngine(), new PSSParameterSpec("SHA3-512", "MGF1", new MGF1ParameterSpec("SHA3-512"), 64, 1));
      }
   }

   public static class SHA512_224withRSA extends PSSSignatureSpi {
      public SHA512_224withRSA() {
         super(new RSABlindedEngine(), new PSSParameterSpec("SHA-512(224)", "MGF1", new MGF1ParameterSpec("SHA-512(224)"), 28, 1));
      }
   }

   public static class SHA512_256withRSA extends PSSSignatureSpi {
      public SHA512_256withRSA() {
         super(new RSABlindedEngine(), new PSSParameterSpec("SHA-512(256)", "MGF1", new MGF1ParameterSpec("SHA-512(256)"), 32, 1));
      }
   }

   public static class SHA512withRSA extends PSSSignatureSpi {
      public SHA512withRSA() {
         super(new RSABlindedEngine(), new PSSParameterSpec("SHA-512", "MGF1", new MGF1ParameterSpec("SHA-512"), 64, 1));
      }
   }

   public static class nonePSS extends PSSSignatureSpi {
      public nonePSS() {
         super(new RSABlindedEngine(), (PSSParameterSpec)null, true);
      }
   }
}
