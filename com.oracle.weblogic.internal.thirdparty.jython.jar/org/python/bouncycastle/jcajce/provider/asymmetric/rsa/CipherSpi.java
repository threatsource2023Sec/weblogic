package org.python.bouncycastle.jcajce.provider.asymmetric.rsa;

import java.io.ByteArrayOutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.PSource.PSpecified;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.crypto.AsymmetricBlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.encodings.ISO9796d1Encoding;
import org.python.bouncycastle.crypto.encodings.OAEPEncoding;
import org.python.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.python.bouncycastle.crypto.engines.RSABlindedEngine;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi;
import org.python.bouncycastle.jcajce.provider.util.BadBlockException;
import org.python.bouncycastle.jcajce.provider.util.DigestFactory;
import org.python.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;
import org.python.bouncycastle.util.Strings;

public class CipherSpi extends BaseCipherSpi {
   private final JcaJceHelper helper = new BCJcaJceHelper();
   private AsymmetricBlockCipher cipher;
   private AlgorithmParameterSpec paramSpec;
   private AlgorithmParameters engineParams;
   private boolean publicKeyOnly = false;
   private boolean privateKeyOnly = false;
   private ByteArrayOutputStream bOut = new ByteArrayOutputStream();

   public CipherSpi(AsymmetricBlockCipher var1) {
      this.cipher = var1;
   }

   public CipherSpi(OAEPParameterSpec var1) {
      try {
         this.initFromSpec(var1);
      } catch (NoSuchPaddingException var3) {
         throw new IllegalArgumentException(var3.getMessage());
      }
   }

   public CipherSpi(boolean var1, boolean var2, AsymmetricBlockCipher var3) {
      this.publicKeyOnly = var1;
      this.privateKeyOnly = var2;
      this.cipher = var3;
   }

   private void initFromSpec(OAEPParameterSpec var1) throws NoSuchPaddingException {
      MGF1ParameterSpec var2 = (MGF1ParameterSpec)var1.getMGFParameters();
      Digest var3 = DigestFactory.getDigest(var2.getDigestAlgorithm());
      if (var3 == null) {
         throw new NoSuchPaddingException("no match on OAEP constructor for digest algorithm: " + var2.getDigestAlgorithm());
      } else {
         this.cipher = new OAEPEncoding(new RSABlindedEngine(), var3, ((PSource.PSpecified)var1.getPSource()).getValue());
         this.paramSpec = var1;
      }
   }

   protected int engineGetBlockSize() {
      try {
         return this.cipher.getInputBlockSize();
      } catch (NullPointerException var2) {
         throw new IllegalStateException("RSA Cipher not initialised");
      }
   }

   protected int engineGetKeySize(Key var1) {
      if (var1 instanceof RSAPrivateKey) {
         RSAPrivateKey var3 = (RSAPrivateKey)var1;
         return var3.getModulus().bitLength();
      } else if (var1 instanceof RSAPublicKey) {
         RSAPublicKey var2 = (RSAPublicKey)var1;
         return var2.getModulus().bitLength();
      } else {
         throw new IllegalArgumentException("not an RSA key!");
      }
   }

   protected int engineGetOutputSize(int var1) {
      try {
         return this.cipher.getOutputBlockSize();
      } catch (NullPointerException var3) {
         throw new IllegalStateException("RSA Cipher not initialised");
      }
   }

   protected AlgorithmParameters engineGetParameters() {
      if (this.engineParams == null && this.paramSpec != null) {
         try {
            this.engineParams = this.helper.createAlgorithmParameters("OAEP");
            this.engineParams.init(this.paramSpec);
         } catch (Exception var2) {
            throw new RuntimeException(var2.toString());
         }
      }

      return this.engineParams;
   }

   protected void engineSetMode(String var1) throws NoSuchAlgorithmException {
      String var2 = Strings.toUpperCase(var1);
      if (!var2.equals("NONE") && !var2.equals("ECB")) {
         if (var2.equals("1")) {
            this.privateKeyOnly = true;
            this.publicKeyOnly = false;
         } else if (var2.equals("2")) {
            this.privateKeyOnly = false;
            this.publicKeyOnly = true;
         } else {
            throw new NoSuchAlgorithmException("can't support mode " + var1);
         }
      }
   }

   protected void engineSetPadding(String var1) throws NoSuchPaddingException {
      String var2 = Strings.toUpperCase(var1);
      if (var2.equals("NOPADDING")) {
         this.cipher = new RSABlindedEngine();
      } else if (var2.equals("PKCS1PADDING")) {
         this.cipher = new PKCS1Encoding(new RSABlindedEngine());
      } else if (var2.equals("ISO9796-1PADDING")) {
         this.cipher = new ISO9796d1Encoding(new RSABlindedEngine());
      } else if (var2.equals("OAEPWITHMD5ANDMGF1PADDING")) {
         this.initFromSpec(new OAEPParameterSpec("MD5", "MGF1", new MGF1ParameterSpec("MD5"), PSpecified.DEFAULT));
      } else if (var2.equals("OAEPPADDING")) {
         this.initFromSpec(OAEPParameterSpec.DEFAULT);
      } else if (!var2.equals("OAEPWITHSHA1ANDMGF1PADDING") && !var2.equals("OAEPWITHSHA-1ANDMGF1PADDING")) {
         if (!var2.equals("OAEPWITHSHA224ANDMGF1PADDING") && !var2.equals("OAEPWITHSHA-224ANDMGF1PADDING")) {
            if (!var2.equals("OAEPWITHSHA256ANDMGF1PADDING") && !var2.equals("OAEPWITHSHA-256ANDMGF1PADDING")) {
               if (!var2.equals("OAEPWITHSHA384ANDMGF1PADDING") && !var2.equals("OAEPWITHSHA-384ANDMGF1PADDING")) {
                  if (!var2.equals("OAEPWITHSHA512ANDMGF1PADDING") && !var2.equals("OAEPWITHSHA-512ANDMGF1PADDING")) {
                     if (var2.equals("OAEPWITHSHA3-224ANDMGF1PADDING")) {
                        this.initFromSpec(new OAEPParameterSpec("SHA3-224", "MGF1", new MGF1ParameterSpec("SHA3-224"), PSpecified.DEFAULT));
                     } else if (var2.equals("OAEPWITHSHA3-256ANDMGF1PADDING")) {
                        this.initFromSpec(new OAEPParameterSpec("SHA3-256", "MGF1", new MGF1ParameterSpec("SHA3-256"), PSpecified.DEFAULT));
                     } else if (var2.equals("OAEPWITHSHA3-384ANDMGF1PADDING")) {
                        this.initFromSpec(new OAEPParameterSpec("SHA3-384", "MGF1", new MGF1ParameterSpec("SHA3-384"), PSpecified.DEFAULT));
                     } else {
                        if (!var2.equals("OAEPWITHSHA3-512ANDMGF1PADDING")) {
                           throw new NoSuchPaddingException(var1 + " unavailable with RSA.");
                        }

                        this.initFromSpec(new OAEPParameterSpec("SHA3-512", "MGF1", new MGF1ParameterSpec("SHA3-512"), PSpecified.DEFAULT));
                     }
                  } else {
                     this.initFromSpec(new OAEPParameterSpec("SHA-512", "MGF1", MGF1ParameterSpec.SHA512, PSpecified.DEFAULT));
                  }
               } else {
                  this.initFromSpec(new OAEPParameterSpec("SHA-384", "MGF1", MGF1ParameterSpec.SHA384, PSpecified.DEFAULT));
               }
            } else {
               this.initFromSpec(new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSpecified.DEFAULT));
            }
         } else {
            this.initFromSpec(new OAEPParameterSpec("SHA-224", "MGF1", new MGF1ParameterSpec("SHA-224"), PSpecified.DEFAULT));
         }
      } else {
         this.initFromSpec(OAEPParameterSpec.DEFAULT);
      }

   }

   protected void engineInit(int var1, Key var2, AlgorithmParameterSpec var3, SecureRandom var4) throws InvalidKeyException, InvalidAlgorithmParameterException {
      if (var3 != null && !(var3 instanceof OAEPParameterSpec)) {
         throw new InvalidAlgorithmParameterException("unknown parameter type: " + var3.getClass().getName());
      } else {
         Object var5;
         if (var2 instanceof RSAPublicKey) {
            if (this.privateKeyOnly && var1 == 1) {
               throw new InvalidKeyException("mode 1 requires RSAPrivateKey");
            }

            var5 = RSAUtil.generatePublicKeyParameter((RSAPublicKey)var2);
         } else {
            if (!(var2 instanceof RSAPrivateKey)) {
               throw new InvalidKeyException("unknown key type passed to RSA");
            }

            if (this.publicKeyOnly && var1 == 1) {
               throw new InvalidKeyException("mode 2 requires RSAPublicKey");
            }

            var5 = RSAUtil.generatePrivateKeyParameter((RSAPrivateKey)var2);
         }

         if (var3 != null) {
            OAEPParameterSpec var6 = (OAEPParameterSpec)var3;
            this.paramSpec = var3;
            if (!var6.getMGFAlgorithm().equalsIgnoreCase("MGF1") && !var6.getMGFAlgorithm().equals(PKCSObjectIdentifiers.id_mgf1.getId())) {
               throw new InvalidAlgorithmParameterException("unknown mask generation function specified");
            }

            if (!(var6.getMGFParameters() instanceof MGF1ParameterSpec)) {
               throw new InvalidAlgorithmParameterException("unkown MGF parameters");
            }

            Digest var7 = DigestFactory.getDigest(var6.getDigestAlgorithm());
            if (var7 == null) {
               throw new InvalidAlgorithmParameterException("no match on digest algorithm: " + var6.getDigestAlgorithm());
            }

            MGF1ParameterSpec var8 = (MGF1ParameterSpec)var6.getMGFParameters();
            Digest var9 = DigestFactory.getDigest(var8.getDigestAlgorithm());
            if (var9 == null) {
               throw new InvalidAlgorithmParameterException("no match on MGF digest algorithm: " + var8.getDigestAlgorithm());
            }

            this.cipher = new OAEPEncoding(new RSABlindedEngine(), var7, var9, ((PSource.PSpecified)var6.getPSource()).getValue());
         }

         if (!(this.cipher instanceof RSABlindedEngine)) {
            if (var4 != null) {
               var5 = new ParametersWithRandom((CipherParameters)var5, var4);
            } else {
               var5 = new ParametersWithRandom((CipherParameters)var5, new SecureRandom());
            }
         }

         this.bOut.reset();
         switch (var1) {
            case 1:
            case 3:
               this.cipher.init(true, (CipherParameters)var5);
               break;
            case 2:
            case 4:
               this.cipher.init(false, (CipherParameters)var5);
               break;
            default:
               throw new InvalidParameterException("unknown opmode " + var1 + " passed to RSA");
         }

      }
   }

   protected void engineInit(int var1, Key var2, AlgorithmParameters var3, SecureRandom var4) throws InvalidKeyException, InvalidAlgorithmParameterException {
      AlgorithmParameterSpec var5 = null;
      if (var3 != null) {
         try {
            var5 = var3.getParameterSpec(OAEPParameterSpec.class);
         } catch (InvalidParameterSpecException var7) {
            throw new InvalidAlgorithmParameterException("cannot recognise parameters: " + var7.toString(), var7);
         }
      }

      this.engineParams = var3;
      this.engineInit(var1, var2, var5, var4);
   }

   protected void engineInit(int var1, Key var2, SecureRandom var3) throws InvalidKeyException {
      try {
         this.engineInit(var1, var2, (AlgorithmParameterSpec)null, var3);
      } catch (InvalidAlgorithmParameterException var5) {
         throw new InvalidKeyException("Eeeek! " + var5.toString(), var5);
      }
   }

   protected byte[] engineUpdate(byte[] var1, int var2, int var3) {
      this.bOut.write(var1, var2, var3);
      if (this.cipher instanceof RSABlindedEngine) {
         if (this.bOut.size() > this.cipher.getInputBlockSize() + 1) {
            throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
         }
      } else if (this.bOut.size() > this.cipher.getInputBlockSize()) {
         throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
      }

      return null;
   }

   protected int engineUpdate(byte[] var1, int var2, int var3, byte[] var4, int var5) {
      this.bOut.write(var1, var2, var3);
      if (this.cipher instanceof RSABlindedEngine) {
         if (this.bOut.size() > this.cipher.getInputBlockSize() + 1) {
            throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
         }
      } else if (this.bOut.size() > this.cipher.getInputBlockSize()) {
         throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
      }

      return 0;
   }

   protected byte[] engineDoFinal(byte[] var1, int var2, int var3) throws IllegalBlockSizeException, BadPaddingException {
      if (var1 != null) {
         this.bOut.write(var1, var2, var3);
      }

      if (this.cipher instanceof RSABlindedEngine) {
         if (this.bOut.size() > this.cipher.getInputBlockSize() + 1) {
            throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
         }
      } else if (this.bOut.size() > this.cipher.getInputBlockSize()) {
         throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
      }

      return this.getOutput();
   }

   protected int engineDoFinal(byte[] var1, int var2, int var3, byte[] var4, int var5) throws IllegalBlockSizeException, BadPaddingException {
      if (var1 != null) {
         this.bOut.write(var1, var2, var3);
      }

      if (this.cipher instanceof RSABlindedEngine) {
         if (this.bOut.size() > this.cipher.getInputBlockSize() + 1) {
            throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
         }
      } else if (this.bOut.size() > this.cipher.getInputBlockSize()) {
         throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
      }

      byte[] var6 = this.getOutput();

      for(int var7 = 0; var7 != var6.length; ++var7) {
         var4[var5 + var7] = var6[var7];
      }

      return var6.length;
   }

   private byte[] getOutput() throws BadPaddingException {
      byte[] var2;
      try {
         byte[] var1 = this.bOut.toByteArray();
         var2 = this.cipher.processBlock(var1, 0, var1.length);
      } catch (InvalidCipherTextException var7) {
         throw new BadBlockException("unable to decrypt block", var7);
      } catch (ArrayIndexOutOfBoundsException var8) {
         throw new BadBlockException("unable to decrypt block", var8);
      } finally {
         this.bOut.reset();
      }

      return var2;
   }

   public static class ISO9796d1Padding extends CipherSpi {
      public ISO9796d1Padding() {
         super((AsymmetricBlockCipher)(new ISO9796d1Encoding(new RSABlindedEngine())));
      }
   }

   public static class NoPadding extends CipherSpi {
      public NoPadding() {
         super((AsymmetricBlockCipher)(new RSABlindedEngine()));
      }
   }

   public static class OAEPPadding extends CipherSpi {
      public OAEPPadding() {
         super(OAEPParameterSpec.DEFAULT);
      }
   }

   public static class PKCS1v1_5Padding extends CipherSpi {
      public PKCS1v1_5Padding() {
         super((AsymmetricBlockCipher)(new PKCS1Encoding(new RSABlindedEngine())));
      }
   }

   public static class PKCS1v1_5Padding_PrivateOnly extends CipherSpi {
      public PKCS1v1_5Padding_PrivateOnly() {
         super(false, true, new PKCS1Encoding(new RSABlindedEngine()));
      }
   }

   public static class PKCS1v1_5Padding_PublicOnly extends CipherSpi {
      public PKCS1v1_5Padding_PublicOnly() {
         super(true, false, new PKCS1Encoding(new RSABlindedEngine()));
      }
   }
}
