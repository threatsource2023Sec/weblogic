package org.python.bouncycastle.jcajce.provider.asymmetric.elgamal;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.interfaces.DHKey;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.PSource.PSpecified;
import org.python.bouncycastle.crypto.AsymmetricBlockCipher;
import org.python.bouncycastle.crypto.BufferedAsymmetricBlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.encodings.ISO9796d1Encoding;
import org.python.bouncycastle.crypto.encodings.OAEPEncoding;
import org.python.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.python.bouncycastle.crypto.engines.ElGamalEngine;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi;
import org.python.bouncycastle.jcajce.provider.util.BadBlockException;
import org.python.bouncycastle.jcajce.provider.util.DigestFactory;
import org.python.bouncycastle.jce.interfaces.ElGamalKey;
import org.python.bouncycastle.jce.interfaces.ElGamalPrivateKey;
import org.python.bouncycastle.jce.interfaces.ElGamalPublicKey;
import org.python.bouncycastle.util.Strings;

public class CipherSpi extends BaseCipherSpi {
   private BufferedAsymmetricBlockCipher cipher;
   private AlgorithmParameterSpec paramSpec;
   private AlgorithmParameters engineParams;

   public CipherSpi(AsymmetricBlockCipher var1) {
      this.cipher = new BufferedAsymmetricBlockCipher(var1);
   }

   private void initFromSpec(OAEPParameterSpec var1) throws NoSuchPaddingException {
      MGF1ParameterSpec var2 = (MGF1ParameterSpec)var1.getMGFParameters();
      Digest var3 = DigestFactory.getDigest(var2.getDigestAlgorithm());
      if (var3 == null) {
         throw new NoSuchPaddingException("no match on OAEP constructor for digest algorithm: " + var2.getDigestAlgorithm());
      } else {
         this.cipher = new BufferedAsymmetricBlockCipher(new OAEPEncoding(new ElGamalEngine(), var3, ((PSource.PSpecified)var1.getPSource()).getValue()));
         this.paramSpec = var1;
      }
   }

   protected int engineGetBlockSize() {
      return this.cipher.getInputBlockSize();
   }

   protected int engineGetKeySize(Key var1) {
      if (var1 instanceof ElGamalKey) {
         ElGamalKey var3 = (ElGamalKey)var1;
         return var3.getParameters().getP().bitLength();
      } else if (var1 instanceof DHKey) {
         DHKey var2 = (DHKey)var1;
         return var2.getParams().getP().bitLength();
      } else {
         throw new IllegalArgumentException("not an ElGamal key!");
      }
   }

   protected int engineGetOutputSize(int var1) {
      return this.cipher.getOutputBlockSize();
   }

   protected AlgorithmParameters engineGetParameters() {
      if (this.engineParams == null && this.paramSpec != null) {
         try {
            this.engineParams = this.createParametersInstance("OAEP");
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
         throw new NoSuchAlgorithmException("can't support mode " + var1);
      }
   }

   protected void engineSetPadding(String var1) throws NoSuchPaddingException {
      String var2 = Strings.toUpperCase(var1);
      if (var2.equals("NOPADDING")) {
         this.cipher = new BufferedAsymmetricBlockCipher(new ElGamalEngine());
      } else if (var2.equals("PKCS1PADDING")) {
         this.cipher = new BufferedAsymmetricBlockCipher(new PKCS1Encoding(new ElGamalEngine()));
      } else if (var2.equals("ISO9796-1PADDING")) {
         this.cipher = new BufferedAsymmetricBlockCipher(new ISO9796d1Encoding(new ElGamalEngine()));
      } else if (var2.equals("OAEPPADDING")) {
         this.initFromSpec(OAEPParameterSpec.DEFAULT);
      } else if (var2.equals("OAEPWITHMD5ANDMGF1PADDING")) {
         this.initFromSpec(new OAEPParameterSpec("MD5", "MGF1", new MGF1ParameterSpec("MD5"), PSpecified.DEFAULT));
      } else if (var2.equals("OAEPWITHSHA1ANDMGF1PADDING")) {
         this.initFromSpec(OAEPParameterSpec.DEFAULT);
      } else if (var2.equals("OAEPWITHSHA224ANDMGF1PADDING")) {
         this.initFromSpec(new OAEPParameterSpec("SHA-224", "MGF1", new MGF1ParameterSpec("SHA-224"), PSpecified.DEFAULT));
      } else if (var2.equals("OAEPWITHSHA256ANDMGF1PADDING")) {
         this.initFromSpec(new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSpecified.DEFAULT));
      } else if (var2.equals("OAEPWITHSHA384ANDMGF1PADDING")) {
         this.initFromSpec(new OAEPParameterSpec("SHA-384", "MGF1", MGF1ParameterSpec.SHA384, PSpecified.DEFAULT));
      } else {
         if (!var2.equals("OAEPWITHSHA512ANDMGF1PADDING")) {
            throw new NoSuchPaddingException(var1 + " unavailable with ElGamal.");
         }

         this.initFromSpec(new OAEPParameterSpec("SHA-512", "MGF1", MGF1ParameterSpec.SHA512, PSpecified.DEFAULT));
      }

   }

   protected void engineInit(int var1, Key var2, AlgorithmParameterSpec var3, SecureRandom var4) throws InvalidKeyException {
      if (var3 == null) {
         Object var5;
         if (var2 instanceof ElGamalPublicKey) {
            var5 = ElGamalUtil.generatePublicKeyParameter((PublicKey)var2);
         } else {
            if (!(var2 instanceof ElGamalPrivateKey)) {
               throw new InvalidKeyException("unknown key type passed to ElGamal");
            }

            var5 = ElGamalUtil.generatePrivateKeyParameter((PrivateKey)var2);
         }

         if (var4 != null) {
            var5 = new ParametersWithRandom((CipherParameters)var5, var4);
         }

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
               throw new InvalidParameterException("unknown opmode " + var1 + " passed to ElGamal");
         }

      } else {
         throw new IllegalArgumentException("unknown parameter type.");
      }
   }

   protected void engineInit(int var1, Key var2, AlgorithmParameters var3, SecureRandom var4) throws InvalidKeyException, InvalidAlgorithmParameterException {
      throw new InvalidAlgorithmParameterException("can't handle parameters in ElGamal");
   }

   protected void engineInit(int var1, Key var2, SecureRandom var3) throws InvalidKeyException {
      this.engineInit(var1, var2, (AlgorithmParameterSpec)null, var3);
   }

   protected byte[] engineUpdate(byte[] var1, int var2, int var3) {
      this.cipher.processBytes(var1, var2, var3);
      return null;
   }

   protected int engineUpdate(byte[] var1, int var2, int var3, byte[] var4, int var5) {
      this.cipher.processBytes(var1, var2, var3);
      return 0;
   }

   protected byte[] engineDoFinal(byte[] var1, int var2, int var3) throws IllegalBlockSizeException, BadPaddingException {
      this.cipher.processBytes(var1, var2, var3);
      return this.getOutput();
   }

   protected int engineDoFinal(byte[] var1, int var2, int var3, byte[] var4, int var5) throws IllegalBlockSizeException, BadPaddingException {
      this.cipher.processBytes(var1, var2, var3);
      byte[] var6 = this.getOutput();

      for(int var7 = 0; var7 != var6.length; ++var7) {
         var4[var5 + var7] = var6[var7];
      }

      return var6.length;
   }

   private byte[] getOutput() throws BadPaddingException {
      try {
         return this.cipher.doFinal();
      } catch (final InvalidCipherTextException var2) {
         throw new BadPaddingException("unable to decrypt block") {
            public synchronized Throwable getCause() {
               return var2;
            }
         };
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new BadBlockException("unable to decrypt block", var3);
      }
   }

   public static class NoPadding extends CipherSpi {
      public NoPadding() {
         super(new ElGamalEngine());
      }
   }

   public static class PKCS1v1_5Padding extends CipherSpi {
      public PKCS1v1_5Padding() {
         super(new PKCS1Encoding(new ElGamalEngine()));
      }
   }
}
