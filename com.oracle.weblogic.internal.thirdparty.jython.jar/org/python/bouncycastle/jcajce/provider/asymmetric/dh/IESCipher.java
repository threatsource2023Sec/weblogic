package org.python.bouncycastle.jcajce.provider.asymmetric.dh;

import java.io.ByteArrayOutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.interfaces.DHKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.KeyEncoder;
import org.python.bouncycastle.crypto.KeyParser;
import org.python.bouncycastle.crypto.agreement.DHBasicAgreement;
import org.python.bouncycastle.crypto.engines.AESEngine;
import org.python.bouncycastle.crypto.engines.DESedeEngine;
import org.python.bouncycastle.crypto.engines.IESEngine;
import org.python.bouncycastle.crypto.generators.DHKeyPairGenerator;
import org.python.bouncycastle.crypto.generators.EphemeralKeyPairGenerator;
import org.python.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.python.bouncycastle.crypto.macs.HMac;
import org.python.bouncycastle.crypto.modes.CBCBlockCipher;
import org.python.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.DHKeyGenerationParameters;
import org.python.bouncycastle.crypto.params.DHKeyParameters;
import org.python.bouncycastle.crypto.params.DHParameters;
import org.python.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.python.bouncycastle.crypto.params.IESWithCipherParameters;
import org.python.bouncycastle.crypto.params.ParametersWithIV;
import org.python.bouncycastle.crypto.parsers.DHIESPublicKeyParser;
import org.python.bouncycastle.crypto.util.DigestFactory;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.DHUtil;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.IESUtil;
import org.python.bouncycastle.jcajce.provider.util.BadBlockException;
import org.python.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;
import org.python.bouncycastle.jce.interfaces.IESKey;
import org.python.bouncycastle.jce.spec.IESParameterSpec;
import org.python.bouncycastle.util.BigIntegers;
import org.python.bouncycastle.util.Strings;

public class IESCipher extends CipherSpi {
   private final JcaJceHelper helper = new BCJcaJceHelper();
   private final int ivLength;
   private IESEngine engine;
   private int state = -1;
   private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
   private AlgorithmParameters engineParam = null;
   private IESParameterSpec engineSpec = null;
   private AsymmetricKeyParameter key;
   private SecureRandom random;
   private boolean dhaesMode = false;
   private AsymmetricKeyParameter otherKeyParameter = null;

   public IESCipher(IESEngine var1) {
      this.engine = var1;
      this.ivLength = 0;
   }

   public IESCipher(IESEngine var1, int var2) {
      this.engine = var1;
      this.ivLength = var2;
   }

   public int engineGetBlockSize() {
      return this.engine.getCipher() != null ? this.engine.getCipher().getBlockSize() : 0;
   }

   public int engineGetKeySize(Key var1) {
      if (var1 instanceof DHKey) {
         return ((DHKey)var1).getParams().getP().bitLength();
      } else {
         throw new IllegalArgumentException("not a DH key");
      }
   }

   public byte[] engineGetIV() {
      return this.engineSpec != null ? this.engineSpec.getNonce() : null;
   }

   public AlgorithmParameters engineGetParameters() {
      if (this.engineParam == null && this.engineSpec != null) {
         try {
            this.engineParam = this.helper.createAlgorithmParameters("IES");
            this.engineParam.init(this.engineSpec);
         } catch (Exception var2) {
            throw new RuntimeException(var2.toString());
         }
      }

      return this.engineParam;
   }

   public void engineSetMode(String var1) throws NoSuchAlgorithmException {
      String var2 = Strings.toUpperCase(var1);
      if (var2.equals("NONE")) {
         this.dhaesMode = false;
      } else {
         if (!var2.equals("DHAES")) {
            throw new IllegalArgumentException("can't support mode " + var1);
         }

         this.dhaesMode = true;
      }

   }

   public int engineGetOutputSize(int var1) {
      if (this.key == null) {
         throw new IllegalStateException("cipher not initialised");
      } else {
         int var2 = this.engine.getMac().getMacSize();
         int var3;
         if (this.otherKeyParameter == null) {
            var3 = 1 + 2 * (((DHKeyParameters)this.key).getParameters().getP().bitLength() + 7) / 8;
         } else {
            var3 = 0;
         }

         int var4;
         if (this.engine.getCipher() == null) {
            var4 = var1;
         } else if (this.state != 1 && this.state != 3) {
            if (this.state != 2 && this.state != 4) {
               throw new IllegalStateException("cipher not initialised");
            }

            var4 = this.engine.getCipher().getOutputSize(var1 - var2 - var3);
         } else {
            var4 = this.engine.getCipher().getOutputSize(var1);
         }

         if (this.state != 1 && this.state != 3) {
            if (this.state != 2 && this.state != 4) {
               throw new IllegalStateException("IESCipher not initialised");
            } else {
               return this.buffer.size() - var2 - var3 + var4;
            }
         } else {
            return this.buffer.size() + var2 + var3 + var4;
         }
      }
   }

   public void engineSetPadding(String var1) throws NoSuchPaddingException {
      String var2 = Strings.toUpperCase(var1);
      if (!var2.equals("NOPADDING") && !var2.equals("PKCS5PADDING") && !var2.equals("PKCS7PADDING")) {
         throw new NoSuchPaddingException("padding not available with IESCipher");
      }
   }

   public void engineInit(int var1, Key var2, AlgorithmParameters var3, SecureRandom var4) throws InvalidKeyException, InvalidAlgorithmParameterException {
      AlgorithmParameterSpec var5 = null;
      if (var3 != null) {
         try {
            var5 = var3.getParameterSpec(IESParameterSpec.class);
         } catch (Exception var7) {
            throw new InvalidAlgorithmParameterException("cannot recognise parameters: " + var7.toString());
         }
      }

      this.engineParam = var3;
      this.engineInit(var1, var2, var5, var4);
   }

   public void engineInit(int var1, Key var2, AlgorithmParameterSpec var3, SecureRandom var4) throws InvalidAlgorithmParameterException, InvalidKeyException {
      byte[] var5;
      if (var3 == null) {
         var5 = null;
         if (this.ivLength != 0 && var1 == 1) {
            var5 = new byte[this.ivLength];
            var4.nextBytes(var5);
         }

         this.engineSpec = IESUtil.guessParameterSpec(this.engine.getCipher(), var5);
      } else {
         if (!(var3 instanceof IESParameterSpec)) {
            throw new InvalidAlgorithmParameterException("must be passed IES parameters");
         }

         this.engineSpec = (IESParameterSpec)var3;
      }

      var5 = this.engineSpec.getNonce();
      if (this.ivLength != 0 && (var5 == null || var5.length != this.ivLength)) {
         throw new InvalidAlgorithmParameterException("NONCE in IES Parameters needs to be " + this.ivLength + " bytes long");
      } else {
         IESKey var6;
         if (var1 != 1 && var1 != 3) {
            if (var1 != 2 && var1 != 4) {
               throw new InvalidKeyException("must be passed EC key");
            }

            if (var2 instanceof DHPrivateKey) {
               this.key = DHUtil.generatePrivateKeyParameter((PrivateKey)var2);
            } else {
               if (!(var2 instanceof IESKey)) {
                  throw new InvalidKeyException("must be passed recipient's private DH key for decryption");
               }

               var6 = (IESKey)var2;
               this.otherKeyParameter = DHUtil.generatePublicKeyParameter(var6.getPublic());
               this.key = DHUtil.generatePrivateKeyParameter(var6.getPrivate());
            }
         } else if (var2 instanceof DHPublicKey) {
            this.key = DHUtil.generatePublicKeyParameter((PublicKey)var2);
         } else {
            if (!(var2 instanceof IESKey)) {
               throw new InvalidKeyException("must be passed recipient's public DH key for encryption");
            }

            var6 = (IESKey)var2;
            this.key = DHUtil.generatePublicKeyParameter(var6.getPublic());
            this.otherKeyParameter = DHUtil.generatePrivateKeyParameter(var6.getPrivate());
         }

         this.random = var4;
         this.state = var1;
         this.buffer.reset();
      }
   }

   public void engineInit(int var1, Key var2, SecureRandom var3) throws InvalidKeyException {
      try {
         this.engineInit(var1, var2, (AlgorithmParameterSpec)null, var3);
      } catch (InvalidAlgorithmParameterException var5) {
         throw new IllegalArgumentException("cannot handle supplied parameter spec: " + var5.getMessage());
      }
   }

   public byte[] engineUpdate(byte[] var1, int var2, int var3) {
      this.buffer.write(var1, var2, var3);
      return null;
   }

   public int engineUpdate(byte[] var1, int var2, int var3, byte[] var4, int var5) {
      this.buffer.write(var1, var2, var3);
      return 0;
   }

   public byte[] engineDoFinal(byte[] var1, int var2, int var3) throws IllegalBlockSizeException, BadPaddingException {
      if (var3 != 0) {
         this.buffer.write(var1, var2, var3);
      }

      byte[] var4 = this.buffer.toByteArray();
      this.buffer.reset();
      Object var5 = new IESWithCipherParameters(this.engineSpec.getDerivationV(), this.engineSpec.getEncodingV(), this.engineSpec.getMacKeySize(), this.engineSpec.getCipherKeySize());
      if (this.engineSpec.getNonce() != null) {
         var5 = new ParametersWithIV((CipherParameters)var5, this.engineSpec.getNonce());
      }

      DHParameters var6 = ((DHKeyParameters)this.key).getParameters();
      if (this.otherKeyParameter == null) {
         if (this.state != 1 && this.state != 3) {
            if (this.state != 2 && this.state != 4) {
               throw new IllegalStateException("IESCipher not initialised");
            } else {
               try {
                  this.engine.init(this.key, (CipherParameters)var5, (KeyParser)(new DHIESPublicKeyParser(((DHKeyParameters)this.key).getParameters())));
                  return this.engine.processBlock(var4, 0, var4.length);
               } catch (InvalidCipherTextException var10) {
                  throw new BadBlockException("unable to process block", var10);
               }
            }
         } else {
            DHKeyPairGenerator var7 = new DHKeyPairGenerator();
            var7.init(new DHKeyGenerationParameters(this.random, var6));
            EphemeralKeyPairGenerator var8 = new EphemeralKeyPairGenerator(var7, new KeyEncoder() {
               public byte[] getEncoded(AsymmetricKeyParameter var1) {
                  byte[] var2 = new byte[(((DHKeyParameters)var1).getParameters().getP().bitLength() + 7) / 8];
                  byte[] var3 = BigIntegers.asUnsignedByteArray(((DHPublicKeyParameters)var1).getY());
                  if (var3.length > var2.length) {
                     throw new IllegalArgumentException("Senders's public key longer than expected.");
                  } else {
                     System.arraycopy(var3, 0, var2, var2.length - var3.length, var3.length);
                     return var2;
                  }
               }
            });

            try {
               this.engine.init(this.key, (CipherParameters)var5, (EphemeralKeyPairGenerator)var8);
               return this.engine.processBlock(var4, 0, var4.length);
            } catch (Exception var11) {
               throw new BadBlockException("unable to process block", var11);
            }
         }
      } else {
         try {
            if (this.state != 1 && this.state != 3) {
               this.engine.init(false, this.key, this.otherKeyParameter, (CipherParameters)var5);
            } else {
               this.engine.init(true, this.otherKeyParameter, this.key, (CipherParameters)var5);
            }

            return this.engine.processBlock(var4, 0, var4.length);
         } catch (Exception var12) {
            throw new BadBlockException("unable to process block", var12);
         }
      }
   }

   public int engineDoFinal(byte[] var1, int var2, int var3, byte[] var4, int var5) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
      byte[] var6 = this.engineDoFinal(var1, var2, var3);
      System.arraycopy(var6, 0, var4, var5, var6.length);
      return var6.length;
   }

   public static class IES extends IESCipher {
      public IES() {
         super(new IESEngine(new DHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA1()), new HMac(DigestFactory.createSHA1())));
      }
   }

   public static class IESwithAESCBC extends IESCipher {
      public IESwithAESCBC() {
         super(new IESEngine(new DHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA1()), new HMac(DigestFactory.createSHA1()), new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()))), 16);
      }
   }

   public static class IESwithDESedeCBC extends IESCipher {
      public IESwithDESedeCBC() {
         super(new IESEngine(new DHBasicAgreement(), new KDF2BytesGenerator(DigestFactory.createSHA1()), new HMac(DigestFactory.createSHA1()), new PaddedBufferedBlockCipher(new CBCBlockCipher(new DESedeEngine()))), 8);
      }
   }
}
