package org.python.bouncycastle.jcajce.provider.symmetric.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import org.python.bouncycastle.asn1.cms.GCMParameters;
import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.BufferedBlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.OutputLengthException;
import org.python.bouncycastle.crypto.modes.AEADBlockCipher;
import org.python.bouncycastle.crypto.modes.CBCBlockCipher;
import org.python.bouncycastle.crypto.modes.CCMBlockCipher;
import org.python.bouncycastle.crypto.modes.CFBBlockCipher;
import org.python.bouncycastle.crypto.modes.CTSBlockCipher;
import org.python.bouncycastle.crypto.modes.EAXBlockCipher;
import org.python.bouncycastle.crypto.modes.GCFBBlockCipher;
import org.python.bouncycastle.crypto.modes.GCMBlockCipher;
import org.python.bouncycastle.crypto.modes.GOFBBlockCipher;
import org.python.bouncycastle.crypto.modes.OCBBlockCipher;
import org.python.bouncycastle.crypto.modes.OFBBlockCipher;
import org.python.bouncycastle.crypto.modes.OpenPGPCFBBlockCipher;
import org.python.bouncycastle.crypto.modes.PGPCFBBlockCipher;
import org.python.bouncycastle.crypto.modes.SICBlockCipher;
import org.python.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.python.bouncycastle.crypto.paddings.ISO10126d2Padding;
import org.python.bouncycastle.crypto.paddings.ISO7816d4Padding;
import org.python.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.python.bouncycastle.crypto.paddings.TBCPadding;
import org.python.bouncycastle.crypto.paddings.X923Padding;
import org.python.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.python.bouncycastle.crypto.params.AEADParameters;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.crypto.params.ParametersWithSBox;
import org.python.bouncycastle.crypto.params.RC2Parameters;
import org.python.bouncycastle.crypto.params.RC5Parameters;
import org.python.bouncycastle.jcajce.PBKDF1Key;
import org.python.bouncycastle.jcajce.PBKDF1KeyWithParameters;
import org.python.bouncycastle.jcajce.PKCS12Key;
import org.python.bouncycastle.jcajce.PKCS12KeyWithParameters;
import org.python.bouncycastle.jcajce.spec.AEADParameterSpec;
import org.python.bouncycastle.jcajce.spec.GOST28147ParameterSpec;
import org.python.bouncycastle.jcajce.spec.RepeatedSecretKeySpec;
import org.python.bouncycastle.util.Strings;

public class BaseBlockCipher extends BaseWrapCipher implements PBE {
   private static final Class gcmSpecClass = lookup("javax.crypto.spec.GCMParameterSpec");
   private Class[] availableSpecs;
   private BlockCipher baseEngine;
   private BlockCipherProvider engineProvider;
   private GenericBlockCipher cipher;
   private ParametersWithIV ivParam;
   private AEADParameters aeadParams;
   private int keySizeInBits;
   private int scheme;
   private int digest;
   private int ivLength;
   private boolean padded;
   private boolean fixedIv;
   private PBEParameterSpec pbeSpec;
   private String pbeAlgorithm;
   private String modeName;

   private static Class lookup(String var0) {
      try {
         Class var1 = BaseBlockCipher.class.getClassLoader().loadClass(var0);
         return var1;
      } catch (Exception var2) {
         return null;
      }
   }

   protected BaseBlockCipher(BlockCipher var1) {
      this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, IvParameterSpec.class, PBEParameterSpec.class, GOST28147ParameterSpec.class};
      this.scheme = -1;
      this.ivLength = 0;
      this.fixedIv = true;
      this.pbeSpec = null;
      this.pbeAlgorithm = null;
      this.modeName = null;
      this.baseEngine = var1;
      this.cipher = new BufferedGenericBlockCipher(var1);
   }

   protected BaseBlockCipher(BlockCipher var1, int var2, int var3, int var4, int var5) {
      this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, IvParameterSpec.class, PBEParameterSpec.class, GOST28147ParameterSpec.class};
      this.scheme = -1;
      this.ivLength = 0;
      this.fixedIv = true;
      this.pbeSpec = null;
      this.pbeAlgorithm = null;
      this.modeName = null;
      this.baseEngine = var1;
      this.scheme = var2;
      this.digest = var3;
      this.keySizeInBits = var4;
      this.ivLength = var5;
      this.cipher = new BufferedGenericBlockCipher(var1);
   }

   protected BaseBlockCipher(BlockCipherProvider var1) {
      this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, IvParameterSpec.class, PBEParameterSpec.class, GOST28147ParameterSpec.class};
      this.scheme = -1;
      this.ivLength = 0;
      this.fixedIv = true;
      this.pbeSpec = null;
      this.pbeAlgorithm = null;
      this.modeName = null;
      this.baseEngine = var1.get();
      this.engineProvider = var1;
      this.cipher = new BufferedGenericBlockCipher(var1.get());
   }

   protected BaseBlockCipher(AEADBlockCipher var1) {
      this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, IvParameterSpec.class, PBEParameterSpec.class, GOST28147ParameterSpec.class};
      this.scheme = -1;
      this.ivLength = 0;
      this.fixedIv = true;
      this.pbeSpec = null;
      this.pbeAlgorithm = null;
      this.modeName = null;
      this.baseEngine = var1.getUnderlyingCipher();
      this.ivLength = this.baseEngine.getBlockSize();
      this.cipher = new AEADGenericBlockCipher(var1);
   }

   protected BaseBlockCipher(AEADBlockCipher var1, boolean var2, int var3) {
      this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, IvParameterSpec.class, PBEParameterSpec.class, GOST28147ParameterSpec.class};
      this.scheme = -1;
      this.ivLength = 0;
      this.fixedIv = true;
      this.pbeSpec = null;
      this.pbeAlgorithm = null;
      this.modeName = null;
      this.baseEngine = var1.getUnderlyingCipher();
      this.fixedIv = var2;
      this.ivLength = var3;
      this.cipher = new AEADGenericBlockCipher(var1);
   }

   protected BaseBlockCipher(BlockCipher var1, int var2) {
      this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, IvParameterSpec.class, PBEParameterSpec.class, GOST28147ParameterSpec.class};
      this.scheme = -1;
      this.ivLength = 0;
      this.fixedIv = true;
      this.pbeSpec = null;
      this.pbeAlgorithm = null;
      this.modeName = null;
      this.baseEngine = var1;
      this.cipher = new BufferedGenericBlockCipher(var1);
      this.ivLength = var2 / 8;
   }

   protected BaseBlockCipher(BufferedBlockCipher var1, int var2) {
      this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, IvParameterSpec.class, PBEParameterSpec.class, GOST28147ParameterSpec.class};
      this.scheme = -1;
      this.ivLength = 0;
      this.fixedIv = true;
      this.pbeSpec = null;
      this.pbeAlgorithm = null;
      this.modeName = null;
      this.baseEngine = var1.getUnderlyingCipher();
      this.cipher = new BufferedGenericBlockCipher(var1);
      this.ivLength = var2 / 8;
   }

   protected int engineGetBlockSize() {
      return this.baseEngine.getBlockSize();
   }

   protected byte[] engineGetIV() {
      if (this.aeadParams != null) {
         return this.aeadParams.getNonce();
      } else {
         return this.ivParam != null ? this.ivParam.getIV() : null;
      }
   }

   protected int engineGetKeySize(Key var1) {
      return var1.getEncoded().length * 8;
   }

   protected int engineGetOutputSize(int var1) {
      return this.cipher.getOutputSize(var1);
   }

   protected AlgorithmParameters engineGetParameters() {
      if (this.engineParams == null) {
         if (this.pbeSpec != null) {
            try {
               this.engineParams = this.createParametersInstance(this.pbeAlgorithm);
               this.engineParams.init(this.pbeSpec);
            } catch (Exception var5) {
               return null;
            }
         } else if (this.aeadParams != null) {
            try {
               this.engineParams = this.createParametersInstance("GCM");
               this.engineParams.init((new GCMParameters(this.aeadParams.getNonce(), this.aeadParams.getMacSize() / 8)).getEncoded());
            } catch (Exception var4) {
               throw new RuntimeException(var4.toString());
            }
         } else if (this.ivParam != null) {
            String var1 = this.cipher.getUnderlyingCipher().getAlgorithmName();
            if (var1.indexOf(47) >= 0) {
               var1 = var1.substring(0, var1.indexOf(47));
            }

            try {
               this.engineParams = this.createParametersInstance(var1);
               this.engineParams.init(this.ivParam.getIV());
            } catch (Exception var3) {
               throw new RuntimeException(var3.toString());
            }
         }
      }

      return this.engineParams;
   }

   protected void engineSetMode(String var1) throws NoSuchAlgorithmException {
      this.modeName = Strings.toUpperCase(var1);
      if (this.modeName.equals("ECB")) {
         this.ivLength = 0;
         this.cipher = new BufferedGenericBlockCipher(this.baseEngine);
      } else if (this.modeName.equals("CBC")) {
         this.ivLength = this.baseEngine.getBlockSize();
         this.cipher = new BufferedGenericBlockCipher(new CBCBlockCipher(this.baseEngine));
      } else {
         int var2;
         if (this.modeName.startsWith("OFB")) {
            this.ivLength = this.baseEngine.getBlockSize();
            if (this.modeName.length() != 3) {
               var2 = Integer.parseInt(this.modeName.substring(3));
               this.cipher = new BufferedGenericBlockCipher(new OFBBlockCipher(this.baseEngine, var2));
            } else {
               this.cipher = new BufferedGenericBlockCipher(new OFBBlockCipher(this.baseEngine, 8 * this.baseEngine.getBlockSize()));
            }
         } else if (this.modeName.startsWith("CFB")) {
            this.ivLength = this.baseEngine.getBlockSize();
            if (this.modeName.length() != 3) {
               var2 = Integer.parseInt(this.modeName.substring(3));
               this.cipher = new BufferedGenericBlockCipher(new CFBBlockCipher(this.baseEngine, var2));
            } else {
               this.cipher = new BufferedGenericBlockCipher(new CFBBlockCipher(this.baseEngine, 8 * this.baseEngine.getBlockSize()));
            }
         } else if (this.modeName.startsWith("PGP")) {
            boolean var3 = this.modeName.equalsIgnoreCase("PGPCFBwithIV");
            this.ivLength = this.baseEngine.getBlockSize();
            this.cipher = new BufferedGenericBlockCipher(new PGPCFBBlockCipher(this.baseEngine, var3));
         } else if (this.modeName.equalsIgnoreCase("OpenPGPCFB")) {
            this.ivLength = 0;
            this.cipher = new BufferedGenericBlockCipher(new OpenPGPCFBBlockCipher(this.baseEngine));
         } else if (this.modeName.startsWith("SIC")) {
            this.ivLength = this.baseEngine.getBlockSize();
            if (this.ivLength < 16) {
               throw new IllegalArgumentException("Warning: SIC-Mode can become a twotime-pad if the blocksize of the cipher is too small. Use a cipher with a block size of at least 128 bits (e.g. AES)");
            }

            this.fixedIv = false;
            this.cipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new SICBlockCipher(this.baseEngine)));
         } else if (this.modeName.startsWith("CTR")) {
            this.ivLength = this.baseEngine.getBlockSize();
            this.fixedIv = false;
            this.cipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new SICBlockCipher(this.baseEngine)));
         } else if (this.modeName.startsWith("GOFB")) {
            this.ivLength = this.baseEngine.getBlockSize();
            this.cipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new GOFBBlockCipher(this.baseEngine)));
         } else if (this.modeName.startsWith("GCFB")) {
            this.ivLength = this.baseEngine.getBlockSize();
            this.cipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new GCFBBlockCipher(this.baseEngine)));
         } else if (this.modeName.startsWith("CTS")) {
            this.ivLength = this.baseEngine.getBlockSize();
            this.cipher = new BufferedGenericBlockCipher(new CTSBlockCipher(new CBCBlockCipher(this.baseEngine)));
         } else if (this.modeName.startsWith("CCM")) {
            this.ivLength = 13;
            this.cipher = new AEADGenericBlockCipher(new CCMBlockCipher(this.baseEngine));
         } else if (this.modeName.startsWith("OCB")) {
            if (this.engineProvider == null) {
               throw new NoSuchAlgorithmException("can't support mode " + var1);
            }

            this.ivLength = 15;
            this.cipher = new AEADGenericBlockCipher(new OCBBlockCipher(this.baseEngine, this.engineProvider.get()));
         } else if (this.modeName.startsWith("EAX")) {
            this.ivLength = this.baseEngine.getBlockSize();
            this.cipher = new AEADGenericBlockCipher(new EAXBlockCipher(this.baseEngine));
         } else {
            if (!this.modeName.startsWith("GCM")) {
               throw new NoSuchAlgorithmException("can't support mode " + var1);
            }

            this.ivLength = this.baseEngine.getBlockSize();
            this.cipher = new AEADGenericBlockCipher(new GCMBlockCipher(this.baseEngine));
         }
      }

   }

   protected void engineSetPadding(String var1) throws NoSuchPaddingException {
      String var2 = Strings.toUpperCase(var1);
      if (var2.equals("NOPADDING")) {
         if (this.cipher.wrapOnNoPadding()) {
            this.cipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(this.cipher.getUnderlyingCipher()));
         }
      } else if (var2.equals("WITHCTS")) {
         this.cipher = new BufferedGenericBlockCipher(new CTSBlockCipher(this.cipher.getUnderlyingCipher()));
      } else {
         this.padded = true;
         if (this.isAEADModeName(this.modeName)) {
            throw new NoSuchPaddingException("Only NoPadding can be used with AEAD modes.");
         }

         if (!var2.equals("PKCS5PADDING") && !var2.equals("PKCS7PADDING")) {
            if (var2.equals("ZEROBYTEPADDING")) {
               this.cipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new ZeroBytePadding());
            } else if (!var2.equals("ISO10126PADDING") && !var2.equals("ISO10126-2PADDING")) {
               if (!var2.equals("X9.23PADDING") && !var2.equals("X923PADDING")) {
                  if (!var2.equals("ISO7816-4PADDING") && !var2.equals("ISO9797-1PADDING")) {
                     if (!var2.equals("TBCPADDING")) {
                        throw new NoSuchPaddingException("Padding " + var1 + " unknown.");
                     }

                     this.cipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new TBCPadding());
                  } else {
                     this.cipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new ISO7816d4Padding());
                  }
               } else {
                  this.cipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new X923Padding());
               }
            } else {
               this.cipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new ISO10126d2Padding());
            }
         } else {
            this.cipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher());
         }
      }

   }

   protected void engineInit(int var1, Key var2, AlgorithmParameterSpec var3, SecureRandom var4) throws InvalidKeyException, InvalidAlgorithmParameterException {
      this.pbeSpec = null;
      this.pbeAlgorithm = null;
      this.engineParams = null;
      this.aeadParams = null;
      if (!(var2 instanceof SecretKey)) {
         throw new InvalidKeyException("Key for algorithm " + (var2 != null ? var2.getAlgorithm() : null) + " not suitable for symmetric enryption.");
      } else if (var3 == null && this.baseEngine.getAlgorithmName().startsWith("RC5-64")) {
         throw new InvalidAlgorithmParameterException("RC5 requires an RC5ParametersSpec to be passed in.");
      } else {
         Object var7;
         if (this.scheme != 2 && !(var2 instanceof PKCS12Key)) {
            if (var2 instanceof PBKDF1Key) {
               PBKDF1Key var12 = (PBKDF1Key)var2;
               if (var3 instanceof PBEParameterSpec) {
                  this.pbeSpec = (PBEParameterSpec)var3;
               }

               if (var12 instanceof PBKDF1KeyWithParameters && this.pbeSpec == null) {
                  this.pbeSpec = new PBEParameterSpec(((PBKDF1KeyWithParameters)var12).getSalt(), ((PBKDF1KeyWithParameters)var12).getIterationCount());
               }

               var7 = PBE.Util.makePBEParameters(var12.getEncoded(), 0, this.digest, this.keySizeInBits, this.ivLength * 8, this.pbeSpec, this.cipher.getAlgorithmName());
               if (var7 instanceof ParametersWithIV) {
                  this.ivParam = (ParametersWithIV)var7;
               }
            } else if (var2 instanceof BCPBEKey) {
               BCPBEKey var13 = (BCPBEKey)var2;
               if (var13.getOID() != null) {
                  this.pbeAlgorithm = var13.getOID().getId();
               } else {
                  this.pbeAlgorithm = var13.getAlgorithm();
               }

               if (var13.getParam() != null) {
                  var7 = this.adjustParameters(var3, var13.getParam());
               } else {
                  if (!(var3 instanceof PBEParameterSpec)) {
                     throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
                  }

                  this.pbeSpec = (PBEParameterSpec)var3;
                  var7 = PBE.Util.makePBEParameters(var13, var3, this.cipher.getUnderlyingCipher().getAlgorithmName());
               }

               if (var7 instanceof ParametersWithIV) {
                  this.ivParam = (ParametersWithIV)var7;
               }
            } else if (var2 instanceof PBEKey) {
               PBEKey var14 = (PBEKey)var2;
               this.pbeSpec = (PBEParameterSpec)var3;
               if (var14 instanceof PKCS12KeyWithParameters && this.pbeSpec == null) {
                  this.pbeSpec = new PBEParameterSpec(var14.getSalt(), var14.getIterationCount());
               }

               var7 = PBE.Util.makePBEParameters(var14.getEncoded(), this.scheme, this.digest, this.keySizeInBits, this.ivLength * 8, this.pbeSpec, this.cipher.getAlgorithmName());
               if (var7 instanceof ParametersWithIV) {
                  this.ivParam = (ParametersWithIV)var7;
               }
            } else if (!(var2 instanceof RepeatedSecretKeySpec)) {
               if (this.scheme == 0 || this.scheme == 4 || this.scheme == 1 || this.scheme == 5) {
                  throw new InvalidKeyException("Algorithm requires a PBE key");
               }

               var7 = new KeyParameter(var2.getEncoded());
            } else {
               var7 = null;
            }
         } else {
            SecretKey var5;
            try {
               var5 = (SecretKey)var2;
            } catch (Exception var11) {
               throw new InvalidKeyException("PKCS12 requires a SecretKey/PBEKey");
            }

            if (var3 instanceof PBEParameterSpec) {
               this.pbeSpec = (PBEParameterSpec)var3;
            }

            if (var5 instanceof PBEKey && this.pbeSpec == null) {
               PBEKey var6 = (PBEKey)var5;
               if (var6.getSalt() == null) {
                  throw new InvalidAlgorithmParameterException("PBEKey requires parameters to specify salt");
               }

               this.pbeSpec = new PBEParameterSpec(var6.getSalt(), var6.getIterationCount());
            }

            if (this.pbeSpec == null && !(var5 instanceof PBEKey)) {
               throw new InvalidKeyException("Algorithm requires a PBE key");
            }

            if (var2 instanceof BCPBEKey) {
               CipherParameters var15 = ((BCPBEKey)var2).getParam();
               if (var15 instanceof ParametersWithIV) {
                  var7 = var15;
               } else {
                  if (var15 != null) {
                     throw new InvalidKeyException("Algorithm requires a PBE key suitable for PKCS12");
                  }

                  var7 = PBE.Util.makePBEParameters(var5.getEncoded(), 2, this.digest, this.keySizeInBits, this.ivLength * 8, this.pbeSpec, this.cipher.getAlgorithmName());
               }
            } else {
               var7 = PBE.Util.makePBEParameters(var5.getEncoded(), 2, this.digest, this.keySizeInBits, this.ivLength * 8, this.pbeSpec, this.cipher.getAlgorithmName());
            }

            if (var7 instanceof ParametersWithIV) {
               this.ivParam = (ParametersWithIV)var7;
            }
         }

         if (var3 instanceof AEADParameterSpec) {
            if (!this.isAEADModeName(this.modeName) && !(this.cipher instanceof AEADGenericBlockCipher)) {
               throw new InvalidAlgorithmParameterException("AEADParameterSpec can only be used with AEAD modes.");
            }

            AEADParameterSpec var16 = (AEADParameterSpec)var3;
            KeyParameter var18;
            if (var7 instanceof ParametersWithIV) {
               var18 = (KeyParameter)((ParametersWithIV)var7).getParameters();
            } else {
               var18 = (KeyParameter)var7;
            }

            var7 = this.aeadParams = new AEADParameters(var18, var16.getMacSizeInBits(), var16.getNonce(), var16.getAssociatedData());
         } else if (var3 instanceof IvParameterSpec) {
            if (this.ivLength != 0) {
               IvParameterSpec var17 = (IvParameterSpec)var3;
               if (var17.getIV().length != this.ivLength && !(this.cipher instanceof AEADGenericBlockCipher) && this.fixedIv) {
                  throw new InvalidAlgorithmParameterException("IV must be " + this.ivLength + " bytes long.");
               }

               if (var7 instanceof ParametersWithIV) {
                  var7 = new ParametersWithIV(((ParametersWithIV)var7).getParameters(), var17.getIV());
               } else {
                  var7 = new ParametersWithIV((CipherParameters)var7, var17.getIV());
               }

               this.ivParam = (ParametersWithIV)var7;
            } else if (this.modeName != null && this.modeName.equals("ECB")) {
               throw new InvalidAlgorithmParameterException("ECB mode does not use an IV");
            }
         } else if (var3 instanceof GOST28147ParameterSpec) {
            GOST28147ParameterSpec var19 = (GOST28147ParameterSpec)var3;
            var7 = new ParametersWithSBox(new KeyParameter(var2.getEncoded()), ((GOST28147ParameterSpec)var3).getSbox());
            if (var19.getIV() != null && this.ivLength != 0) {
               if (var7 instanceof ParametersWithIV) {
                  var7 = new ParametersWithIV(((ParametersWithIV)var7).getParameters(), var19.getIV());
               } else {
                  var7 = new ParametersWithIV((CipherParameters)var7, var19.getIV());
               }

               this.ivParam = (ParametersWithIV)var7;
            }
         } else if (var3 instanceof RC2ParameterSpec) {
            RC2ParameterSpec var20 = (RC2ParameterSpec)var3;
            var7 = new RC2Parameters(var2.getEncoded(), ((RC2ParameterSpec)var3).getEffectiveKeyBits());
            if (var20.getIV() != null && this.ivLength != 0) {
               if (var7 instanceof ParametersWithIV) {
                  var7 = new ParametersWithIV(((ParametersWithIV)var7).getParameters(), var20.getIV());
               } else {
                  var7 = new ParametersWithIV((CipherParameters)var7, var20.getIV());
               }

               this.ivParam = (ParametersWithIV)var7;
            }
         } else if (var3 instanceof RC5ParameterSpec) {
            RC5ParameterSpec var21 = (RC5ParameterSpec)var3;
            var7 = new RC5Parameters(var2.getEncoded(), ((RC5ParameterSpec)var3).getRounds());
            if (!this.baseEngine.getAlgorithmName().startsWith("RC5")) {
               throw new InvalidAlgorithmParameterException("RC5 parameters passed to a cipher that is not RC5.");
            }

            if (this.baseEngine.getAlgorithmName().equals("RC5-32")) {
               if (var21.getWordSize() != 32) {
                  throw new InvalidAlgorithmParameterException("RC5 already set up for a word size of 32 not " + var21.getWordSize() + ".");
               }
            } else if (this.baseEngine.getAlgorithmName().equals("RC5-64") && var21.getWordSize() != 64) {
               throw new InvalidAlgorithmParameterException("RC5 already set up for a word size of 64 not " + var21.getWordSize() + ".");
            }

            if (var21.getIV() != null && this.ivLength != 0) {
               if (var7 instanceof ParametersWithIV) {
                  var7 = new ParametersWithIV(((ParametersWithIV)var7).getParameters(), var21.getIV());
               } else {
                  var7 = new ParametersWithIV((CipherParameters)var7, var21.getIV());
               }

               this.ivParam = (ParametersWithIV)var7;
            }
         } else if (gcmSpecClass != null && gcmSpecClass.isInstance(var3)) {
            if (!this.isAEADModeName(this.modeName) && !(this.cipher instanceof AEADGenericBlockCipher)) {
               throw new InvalidAlgorithmParameterException("GCMParameterSpec can only be used with AEAD modes.");
            }

            try {
               Method var24 = gcmSpecClass.getDeclaredMethod("getTLen");
               Method var22 = gcmSpecClass.getDeclaredMethod("getIV");
               KeyParameter var8;
               if (var7 instanceof ParametersWithIV) {
                  var8 = (KeyParameter)((ParametersWithIV)var7).getParameters();
               } else {
                  var8 = (KeyParameter)var7;
               }

               var7 = this.aeadParams = new AEADParameters(var8, (Integer)var24.invoke(var3), (byte[])((byte[])var22.invoke(var3)));
            } catch (Exception var10) {
               throw new InvalidAlgorithmParameterException("Cannot process GCMParameterSpec.");
            }
         } else if (var3 != null && !(var3 instanceof PBEParameterSpec)) {
            throw new InvalidAlgorithmParameterException("unknown parameter type.");
         }

         if (this.ivLength != 0 && !(var7 instanceof ParametersWithIV) && !(var7 instanceof AEADParameters)) {
            SecureRandom var25 = var4;
            if (var4 == null) {
               var25 = new SecureRandom();
            }

            if (var1 != 1 && var1 != 3) {
               if (this.cipher.getUnderlyingCipher().getAlgorithmName().indexOf("PGPCFB") < 0) {
                  throw new InvalidAlgorithmParameterException("no IV set when one expected");
               }
            } else {
               byte[] var23 = new byte[this.ivLength];
               var25.nextBytes(var23);
               var7 = new ParametersWithIV((CipherParameters)var7, var23);
               this.ivParam = (ParametersWithIV)var7;
            }
         }

         if (var4 != null && this.padded) {
            var7 = new ParametersWithRandom((CipherParameters)var7, var4);
         }

         try {
            switch (var1) {
               case 1:
               case 3:
                  this.cipher.init(true, (CipherParameters)var7);
                  break;
               case 2:
               case 4:
                  this.cipher.init(false, (CipherParameters)var7);
                  break;
               default:
                  throw new InvalidParameterException("unknown opmode " + var1 + " passed");
            }

            if (this.cipher instanceof AEADGenericBlockCipher && this.aeadParams == null) {
               AEADBlockCipher var26 = ((AEADGenericBlockCipher)this.cipher).cipher;
               this.aeadParams = new AEADParameters((KeyParameter)this.ivParam.getParameters(), var26.getMac().length * 8, this.ivParam.getIV());
            }

         } catch (Exception var9) {
            throw new InvalidKeyOrParametersException(var9.getMessage(), var9);
         }
      }
   }

   private CipherParameters adjustParameters(AlgorithmParameterSpec var1, CipherParameters var2) {
      IvParameterSpec var4;
      GOST28147ParameterSpec var5;
      if (var2 instanceof ParametersWithIV) {
         CipherParameters var3 = ((ParametersWithIV)var2).getParameters();
         if (var1 instanceof IvParameterSpec) {
            var4 = (IvParameterSpec)var1;
            this.ivParam = new ParametersWithIV(var3, var4.getIV());
            var2 = this.ivParam;
         } else if (var1 instanceof GOST28147ParameterSpec) {
            var5 = (GOST28147ParameterSpec)var1;
            var2 = new ParametersWithSBox((CipherParameters)var2, var5.getSbox());
            if (var5.getIV() != null && this.ivLength != 0) {
               this.ivParam = new ParametersWithIV(var3, var5.getIV());
               var2 = this.ivParam;
            }
         }
      } else if (var1 instanceof IvParameterSpec) {
         var4 = (IvParameterSpec)var1;
         this.ivParam = new ParametersWithIV((CipherParameters)var2, var4.getIV());
         var2 = this.ivParam;
      } else if (var1 instanceof GOST28147ParameterSpec) {
         var5 = (GOST28147ParameterSpec)var1;
         var2 = new ParametersWithSBox((CipherParameters)var2, var5.getSbox());
         if (var5.getIV() != null && this.ivLength != 0) {
            var2 = new ParametersWithIV((CipherParameters)var2, var5.getIV());
         }
      }

      return (CipherParameters)var2;
   }

   protected void engineInit(int var1, Key var2, AlgorithmParameters var3, SecureRandom var4) throws InvalidKeyException, InvalidAlgorithmParameterException {
      AlgorithmParameterSpec var5 = null;
      if (var3 != null) {
         for(int var6 = 0; var6 != this.availableSpecs.length; ++var6) {
            if (this.availableSpecs[var6] != null) {
               try {
                  var5 = var3.getParameterSpec(this.availableSpecs[var6]);
                  break;
               } catch (Exception var8) {
               }
            }
         }

         if (var5 == null) {
            throw new InvalidAlgorithmParameterException("can't handle parameter " + var3.toString());
         }
      }

      this.engineInit(var1, var2, var5, var4);
      this.engineParams = var3;
   }

   protected void engineInit(int var1, Key var2, SecureRandom var3) throws InvalidKeyException {
      try {
         this.engineInit(var1, var2, (AlgorithmParameterSpec)null, var3);
      } catch (InvalidAlgorithmParameterException var5) {
         throw new InvalidKeyException(var5.getMessage());
      }
   }

   protected void engineUpdateAAD(byte[] var1, int var2, int var3) {
      this.cipher.updateAAD(var1, var2, var3);
   }

   protected void engineUpdateAAD(ByteBuffer var1) {
      int var2 = var1.arrayOffset() + var1.position();
      int var3 = var1.limit() - var1.position();
      this.engineUpdateAAD(var1.array(), var2, var3);
   }

   protected byte[] engineUpdate(byte[] var1, int var2, int var3) {
      int var4 = this.cipher.getUpdateOutputSize(var3);
      if (var4 > 0) {
         byte[] var5 = new byte[var4];
         int var6 = this.cipher.processBytes(var1, var2, var3, var5, 0);
         if (var6 == 0) {
            return null;
         } else if (var6 != var5.length) {
            byte[] var7 = new byte[var6];
            System.arraycopy(var5, 0, var7, 0, var6);
            return var7;
         } else {
            return var5;
         }
      } else {
         this.cipher.processBytes(var1, var2, var3, (byte[])null, 0);
         return null;
      }
   }

   protected int engineUpdate(byte[] var1, int var2, int var3, byte[] var4, int var5) throws ShortBufferException {
      if (var5 + this.cipher.getUpdateOutputSize(var3) > var4.length) {
         throw new ShortBufferException("output buffer too short for input.");
      } else {
         try {
            return this.cipher.processBytes(var1, var2, var3, var4, var5);
         } catch (DataLengthException var7) {
            throw new IllegalStateException(var7.toString());
         }
      }
   }

   protected byte[] engineDoFinal(byte[] var1, int var2, int var3) throws IllegalBlockSizeException, BadPaddingException {
      int var4 = 0;
      byte[] var5 = new byte[this.engineGetOutputSize(var3)];
      if (var3 != 0) {
         var4 = this.cipher.processBytes(var1, var2, var3, var5, 0);
      }

      try {
         var4 += this.cipher.doFinal(var5, var4);
      } catch (DataLengthException var7) {
         throw new IllegalBlockSizeException(var7.getMessage());
      }

      if (var4 == var5.length) {
         return var5;
      } else {
         byte[] var6 = new byte[var4];
         System.arraycopy(var5, 0, var6, 0, var4);
         return var6;
      }
   }

   protected int engineDoFinal(byte[] var1, int var2, int var3, byte[] var4, int var5) throws IllegalBlockSizeException, BadPaddingException, ShortBufferException {
      int var6 = 0;
      if (var5 + this.engineGetOutputSize(var3) > var4.length) {
         throw new ShortBufferException("output buffer too short for input.");
      } else {
         try {
            if (var3 != 0) {
               var6 = this.cipher.processBytes(var1, var2, var3, var4, var5);
            }

            return var6 + this.cipher.doFinal(var4, var5 + var6);
         } catch (OutputLengthException var8) {
            throw new IllegalBlockSizeException(var8.getMessage());
         } catch (DataLengthException var9) {
            throw new IllegalBlockSizeException(var9.getMessage());
         }
      }
   }

   private boolean isAEADModeName(String var1) {
      return "CCM".equals(var1) || "EAX".equals(var1) || "GCM".equals(var1) || "OCB".equals(var1);
   }

   private static class AEADGenericBlockCipher implements GenericBlockCipher {
      private static final Constructor aeadBadTagConstructor;
      private AEADBlockCipher cipher;

      private static Constructor findExceptionConstructor(Class var0) {
         try {
            return var0.getConstructor(String.class);
         } catch (Exception var2) {
            return null;
         }
      }

      AEADGenericBlockCipher(AEADBlockCipher var1) {
         this.cipher = var1;
      }

      public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
         this.cipher.init(var1, var2);
      }

      public String getAlgorithmName() {
         return this.cipher.getUnderlyingCipher().getAlgorithmName();
      }

      public boolean wrapOnNoPadding() {
         return false;
      }

      public BlockCipher getUnderlyingCipher() {
         return this.cipher.getUnderlyingCipher();
      }

      public int getOutputSize(int var1) {
         return this.cipher.getOutputSize(var1);
      }

      public int getUpdateOutputSize(int var1) {
         return this.cipher.getUpdateOutputSize(var1);
      }

      public void updateAAD(byte[] var1, int var2, int var3) {
         this.cipher.processAADBytes(var1, var2, var3);
      }

      public int processByte(byte var1, byte[] var2, int var3) throws DataLengthException {
         return this.cipher.processByte(var1, var2, var3);
      }

      public int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException {
         return this.cipher.processBytes(var1, var2, var3, var4, var5);
      }

      public int doFinal(byte[] var1, int var2) throws IllegalStateException, BadPaddingException {
         try {
            return this.cipher.doFinal(var1, var2);
         } catch (InvalidCipherTextException var7) {
            InvalidCipherTextException var3 = var7;
            if (aeadBadTagConstructor != null) {
               BadPaddingException var4 = null;

               try {
                  var4 = (BadPaddingException)aeadBadTagConstructor.newInstance(var3.getMessage());
               } catch (Exception var6) {
               }

               if (var4 != null) {
                  throw var4;
               }
            }

            throw new BadPaddingException(var7.getMessage());
         }
      }

      static {
         Class var0 = BaseBlockCipher.lookup("javax.crypto.AEADBadTagException");
         if (var0 != null) {
            aeadBadTagConstructor = findExceptionConstructor(var0);
         } else {
            aeadBadTagConstructor = null;
         }

      }
   }

   private static class BufferedGenericBlockCipher implements GenericBlockCipher {
      private BufferedBlockCipher cipher;

      BufferedGenericBlockCipher(BufferedBlockCipher var1) {
         this.cipher = var1;
      }

      BufferedGenericBlockCipher(BlockCipher var1) {
         this.cipher = new PaddedBufferedBlockCipher(var1);
      }

      BufferedGenericBlockCipher(BlockCipher var1, BlockCipherPadding var2) {
         this.cipher = new PaddedBufferedBlockCipher(var1, var2);
      }

      public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
         this.cipher.init(var1, var2);
      }

      public boolean wrapOnNoPadding() {
         return !(this.cipher instanceof CTSBlockCipher);
      }

      public String getAlgorithmName() {
         return this.cipher.getUnderlyingCipher().getAlgorithmName();
      }

      public BlockCipher getUnderlyingCipher() {
         return this.cipher.getUnderlyingCipher();
      }

      public int getOutputSize(int var1) {
         return this.cipher.getOutputSize(var1);
      }

      public int getUpdateOutputSize(int var1) {
         return this.cipher.getUpdateOutputSize(var1);
      }

      public void updateAAD(byte[] var1, int var2, int var3) {
         throw new UnsupportedOperationException("AAD is not supported in the current mode.");
      }

      public int processByte(byte var1, byte[] var2, int var3) throws DataLengthException {
         return this.cipher.processByte(var1, var2, var3);
      }

      public int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException {
         return this.cipher.processBytes(var1, var2, var3, var4, var5);
      }

      public int doFinal(byte[] var1, int var2) throws IllegalStateException, BadPaddingException {
         try {
            return this.cipher.doFinal(var1, var2);
         } catch (InvalidCipherTextException var4) {
            throw new BadPaddingException(var4.getMessage());
         }
      }
   }

   private interface GenericBlockCipher {
      void init(boolean var1, CipherParameters var2) throws IllegalArgumentException;

      boolean wrapOnNoPadding();

      String getAlgorithmName();

      BlockCipher getUnderlyingCipher();

      int getOutputSize(int var1);

      int getUpdateOutputSize(int var1);

      void updateAAD(byte[] var1, int var2, int var3);

      int processByte(byte var1, byte[] var2, int var3) throws DataLengthException;

      int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException;

      int doFinal(byte[] var1, int var2) throws IllegalStateException, BadPaddingException;
   }

   private static class InvalidKeyOrParametersException extends InvalidKeyException {
      private final Throwable cause;

      InvalidKeyOrParametersException(String var1, Throwable var2) {
         super(var1);
         this.cause = var2;
      }

      public Throwable getCause() {
         return this.cause;
      }
   }
}
