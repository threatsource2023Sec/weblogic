package org.python.bouncycastle.jcajce.provider.symmetric;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.IvParameterSpec;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.bc.BCObjectIdentifiers;
import org.python.bouncycastle.asn1.cms.CCMParameters;
import org.python.bouncycastle.asn1.cms.GCMParameters;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.BufferedBlockCipher;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.Mac;
import org.python.bouncycastle.crypto.engines.AESEngine;
import org.python.bouncycastle.crypto.engines.AESWrapEngine;
import org.python.bouncycastle.crypto.engines.AESWrapPadEngine;
import org.python.bouncycastle.crypto.engines.RFC3211WrapEngine;
import org.python.bouncycastle.crypto.engines.RFC5649WrapEngine;
import org.python.bouncycastle.crypto.generators.Poly1305KeyGenerator;
import org.python.bouncycastle.crypto.macs.CMac;
import org.python.bouncycastle.crypto.macs.GMac;
import org.python.bouncycastle.crypto.modes.AEADBlockCipher;
import org.python.bouncycastle.crypto.modes.CBCBlockCipher;
import org.python.bouncycastle.crypto.modes.CCMBlockCipher;
import org.python.bouncycastle.crypto.modes.CFBBlockCipher;
import org.python.bouncycastle.crypto.modes.GCMBlockCipher;
import org.python.bouncycastle.crypto.modes.OFBBlockCipher;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.python.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;
import org.python.bouncycastle.jcajce.spec.AEADParameterSpec;

public final class AES {
   private static final Class gcmSpecClass = lookup("javax.crypto.spec.GCMParameterSpec");
   private static final Map generalAesAttributes = new HashMap();

   private AES() {
   }

   private static Class lookup(String var0) {
      try {
         Class var1 = AES.class.getClassLoader().loadClass(var0);
         return var1;
      } catch (Exception var2) {
         return null;
      }
   }

   static {
      generalAesAttributes.put("SupportedKeyClasses", "javax.crypto.SecretKey");
      generalAesAttributes.put("SupportedKeyFormats", "RAW");
   }

   public static class AESCCMMAC extends BaseMac {
      public AESCCMMAC() {
         super(new CCMMac());
      }

      private static class CCMMac implements Mac {
         private final CCMBlockCipher ccm;
         private int macLength;

         private CCMMac() {
            this.ccm = new CCMBlockCipher(new AESEngine());
            this.macLength = 8;
         }

         public void init(CipherParameters var1) throws IllegalArgumentException {
            this.ccm.init(true, var1);
            this.macLength = this.ccm.getMac().length;
         }

         public String getAlgorithmName() {
            return this.ccm.getAlgorithmName() + "Mac";
         }

         public int getMacSize() {
            return this.macLength;
         }

         public void update(byte var1) throws IllegalStateException {
            this.ccm.processAADByte(var1);
         }

         public void update(byte[] var1, int var2, int var3) throws DataLengthException, IllegalStateException {
            this.ccm.processAADBytes(var1, var2, var3);
         }

         public int doFinal(byte[] var1, int var2) throws DataLengthException, IllegalStateException {
            try {
               return this.ccm.doFinal(var1, 0);
            } catch (InvalidCipherTextException var4) {
               throw new IllegalStateException("exception on doFinal(): " + var4.toString());
            }
         }

         public void reset() {
            this.ccm.reset();
         }

         // $FF: synthetic method
         CCMMac(Object var1) {
            this();
         }
      }
   }

   public static class AESCMAC extends BaseMac {
      public AESCMAC() {
         super(new CMac(new AESEngine()));
      }
   }

   public static class AESGMAC extends BaseMac {
      public AESGMAC() {
         super(new GMac(new GCMBlockCipher(new AESEngine())));
      }
   }

   public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for AES parameter generation.");
      }

      protected AlgorithmParameters engineGenerateParameters() {
         byte[] var1 = new byte[16];
         if (this.random == null) {
            this.random = new SecureRandom();
         }

         this.random.nextBytes(var1);

         try {
            AlgorithmParameters var2 = this.createParametersInstance("AES");
            var2.init(new IvParameterSpec(var1));
            return var2;
         } catch (Exception var4) {
            throw new RuntimeException(var4.getMessage());
         }
      }
   }

   public static class AlgParamGenCCM extends BaseAlgorithmParameterGenerator {
      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for AES parameter generation.");
      }

      protected AlgorithmParameters engineGenerateParameters() {
         byte[] var1 = new byte[12];
         if (this.random == null) {
            this.random = new SecureRandom();
         }

         this.random.nextBytes(var1);

         try {
            AlgorithmParameters var2 = this.createParametersInstance("CCM");
            var2.init((new CCMParameters(var1, 12)).getEncoded());
            return var2;
         } catch (Exception var4) {
            throw new RuntimeException(var4.getMessage());
         }
      }
   }

   public static class AlgParamGenGCM extends BaseAlgorithmParameterGenerator {
      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for AES parameter generation.");
      }

      protected AlgorithmParameters engineGenerateParameters() {
         byte[] var1 = new byte[12];
         if (this.random == null) {
            this.random = new SecureRandom();
         }

         this.random.nextBytes(var1);

         try {
            AlgorithmParameters var2 = this.createParametersInstance("GCM");
            var2.init((new GCMParameters(var1, 16)).getEncoded());
            return var2;
         } catch (Exception var4) {
            throw new RuntimeException(var4.getMessage());
         }
      }
   }

   public static class AlgParams extends IvAlgorithmParameters {
      protected String engineToString() {
         return "AES IV";
      }
   }

   public static class AlgParamsCCM extends BaseAlgorithmParameters {
      private CCMParameters ccmParams;

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if (GcmSpecUtil.isGcmSpec(var1)) {
            this.ccmParams = CCMParameters.getInstance(GcmSpecUtil.extractGcmParameters(var1));
         } else {
            if (!(var1 instanceof AEADParameterSpec)) {
               throw new InvalidParameterSpecException("AlgorithmParameterSpec class not recognized: " + var1.getClass().getName());
            }

            this.ccmParams = new CCMParameters(((AEADParameterSpec)var1).getNonce(), ((AEADParameterSpec)var1).getMacSizeInBits() / 8);
         }

      }

      protected void engineInit(byte[] var1) throws IOException {
         this.ccmParams = CCMParameters.getInstance(var1);
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if (!this.isASN1FormatString(var2)) {
            throw new IOException("unknown format specified");
         } else {
            this.ccmParams = CCMParameters.getInstance(var1);
         }
      }

      protected byte[] engineGetEncoded() throws IOException {
         return this.ccmParams.getEncoded();
      }

      protected byte[] engineGetEncoded(String var1) throws IOException {
         if (!this.isASN1FormatString(var1)) {
            throw new IOException("unknown format specified");
         } else {
            return this.ccmParams.getEncoded();
         }
      }

      protected String engineToString() {
         return "CCM";
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         if (var1 != AlgorithmParameterSpec.class && !GcmSpecUtil.isGcmSpec(var1)) {
            if (var1 == AEADParameterSpec.class) {
               return new AEADParameterSpec(this.ccmParams.getNonce(), this.ccmParams.getIcvLen() * 8);
            } else if (var1 == IvParameterSpec.class) {
               return new IvParameterSpec(this.ccmParams.getNonce());
            } else {
               throw new InvalidParameterSpecException("AlgorithmParameterSpec not recognized: " + var1.getName());
            }
         } else {
            return (AlgorithmParameterSpec)(GcmSpecUtil.gcmSpecExists() ? GcmSpecUtil.extractGcmSpec(this.ccmParams.toASN1Primitive()) : new AEADParameterSpec(this.ccmParams.getNonce(), this.ccmParams.getIcvLen() * 8));
         }
      }
   }

   public static class AlgParamsGCM extends BaseAlgorithmParameters {
      private GCMParameters gcmParams;

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if (GcmSpecUtil.isGcmSpec(var1)) {
            this.gcmParams = GcmSpecUtil.extractGcmParameters(var1);
         } else {
            if (!(var1 instanceof AEADParameterSpec)) {
               throw new InvalidParameterSpecException("AlgorithmParameterSpec class not recognized: " + var1.getClass().getName());
            }

            this.gcmParams = new GCMParameters(((AEADParameterSpec)var1).getNonce(), ((AEADParameterSpec)var1).getMacSizeInBits() / 8);
         }

      }

      protected void engineInit(byte[] var1) throws IOException {
         this.gcmParams = GCMParameters.getInstance(var1);
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if (!this.isASN1FormatString(var2)) {
            throw new IOException("unknown format specified");
         } else {
            this.gcmParams = GCMParameters.getInstance(var1);
         }
      }

      protected byte[] engineGetEncoded() throws IOException {
         return this.gcmParams.getEncoded();
      }

      protected byte[] engineGetEncoded(String var1) throws IOException {
         if (!this.isASN1FormatString(var1)) {
            throw new IOException("unknown format specified");
         } else {
            return this.gcmParams.getEncoded();
         }
      }

      protected String engineToString() {
         return "GCM";
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         if (var1 != AlgorithmParameterSpec.class && !GcmSpecUtil.isGcmSpec(var1)) {
            if (var1 == AEADParameterSpec.class) {
               return new AEADParameterSpec(this.gcmParams.getNonce(), this.gcmParams.getIcvLen() * 8);
            } else if (var1 == IvParameterSpec.class) {
               return new IvParameterSpec(this.gcmParams.getNonce());
            } else {
               throw new InvalidParameterSpecException("AlgorithmParameterSpec not recognized: " + var1.getName());
            }
         } else {
            return (AlgorithmParameterSpec)(GcmSpecUtil.gcmSpecExists() ? GcmSpecUtil.extractGcmSpec(this.gcmParams.toASN1Primitive()) : new AEADParameterSpec(this.gcmParams.getNonce(), this.gcmParams.getIcvLen() * 8));
         }
      }
   }

   public static class CBC extends BaseBlockCipher {
      public CBC() {
         super((BlockCipher)(new CBCBlockCipher(new AESEngine())), 128);
      }
   }

   public static class CCM extends BaseBlockCipher {
      public CCM() {
         super(new CCMBlockCipher(new AESEngine()), false, 16);
      }
   }

   public static class CFB extends BaseBlockCipher {
      public CFB() {
         super((BufferedBlockCipher)(new BufferedBlockCipher(new CFBBlockCipher(new AESEngine(), 128))), 128);
      }
   }

   public static class ECB extends BaseBlockCipher {
      public ECB() {
         super(new BlockCipherProvider() {
            public BlockCipher get() {
               return new AESEngine();
            }
         });
      }
   }

   public static class GCM extends BaseBlockCipher {
      public GCM() {
         super((AEADBlockCipher)(new GCMBlockCipher(new AESEngine())));
      }
   }

   public static class KeyFactory extends BaseSecretKeyFactory {
      public KeyFactory() {
         super("AES", (ASN1ObjectIdentifier)null);
      }
   }

   public static class KeyGen extends BaseKeyGenerator {
      public KeyGen() {
         this(192);
      }

      public KeyGen(int var1) {
         super("AES", var1, new CipherKeyGenerator());
      }
   }

   public static class KeyGen128 extends KeyGen {
      public KeyGen128() {
         super(128);
      }
   }

   public static class KeyGen192 extends KeyGen {
      public KeyGen192() {
         super(192);
      }
   }

   public static class KeyGen256 extends KeyGen {
      public KeyGen256() {
         super(256);
      }
   }

   public static class Mappings extends SymmetricAlgorithmProvider {
      private static final String PREFIX = AES.class.getName();
      private static final String wrongAES128 = "2.16.840.1.101.3.4.2";
      private static final String wrongAES192 = "2.16.840.1.101.3.4.22";
      private static final String wrongAES256 = "2.16.840.1.101.3.4.42";

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("AlgorithmParameters.AES", PREFIX + "$AlgParams");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.2", "AES");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.22", "AES");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.42", "AES");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + NISTObjectIdentifiers.id_aes128_CBC, "AES");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + NISTObjectIdentifiers.id_aes192_CBC, "AES");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + NISTObjectIdentifiers.id_aes256_CBC, "AES");
         var1.addAlgorithm("AlgorithmParameters.GCM", PREFIX + "$AlgParamsGCM");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + NISTObjectIdentifiers.id_aes128_GCM, "GCM");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + NISTObjectIdentifiers.id_aes192_GCM, "GCM");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + NISTObjectIdentifiers.id_aes256_GCM, "GCM");
         var1.addAlgorithm("AlgorithmParameters.CCM", PREFIX + "$AlgParamsCCM");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + NISTObjectIdentifiers.id_aes128_CCM, "CCM");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + NISTObjectIdentifiers.id_aes192_CCM, "CCM");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + NISTObjectIdentifiers.id_aes256_CCM, "CCM");
         var1.addAlgorithm("AlgorithmParameterGenerator.AES", PREFIX + "$AlgParamGen");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator.2.16.840.1.101.3.4.2", "AES");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator.2.16.840.1.101.3.4.22", "AES");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator.2.16.840.1.101.3.4.42", "AES");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + NISTObjectIdentifiers.id_aes128_CBC, "AES");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + NISTObjectIdentifiers.id_aes192_CBC, "AES");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + NISTObjectIdentifiers.id_aes256_CBC, "AES");
         var1.addAttributes("Cipher.AES", AES.generalAesAttributes);
         var1.addAlgorithm("Cipher.AES", PREFIX + "$ECB");
         var1.addAlgorithm("Alg.Alias.Cipher.2.16.840.1.101.3.4.2", "AES");
         var1.addAlgorithm("Alg.Alias.Cipher.2.16.840.1.101.3.4.22", "AES");
         var1.addAlgorithm("Alg.Alias.Cipher.2.16.840.1.101.3.4.42", "AES");
         var1.addAlgorithm("Cipher", NISTObjectIdentifiers.id_aes128_ECB, PREFIX + "$ECB");
         var1.addAlgorithm("Cipher", NISTObjectIdentifiers.id_aes192_ECB, PREFIX + "$ECB");
         var1.addAlgorithm("Cipher", NISTObjectIdentifiers.id_aes256_ECB, PREFIX + "$ECB");
         var1.addAlgorithm("Cipher", NISTObjectIdentifiers.id_aes128_CBC, PREFIX + "$CBC");
         var1.addAlgorithm("Cipher", NISTObjectIdentifiers.id_aes192_CBC, PREFIX + "$CBC");
         var1.addAlgorithm("Cipher", NISTObjectIdentifiers.id_aes256_CBC, PREFIX + "$CBC");
         var1.addAlgorithm("Cipher", NISTObjectIdentifiers.id_aes128_OFB, PREFIX + "$OFB");
         var1.addAlgorithm("Cipher", NISTObjectIdentifiers.id_aes192_OFB, PREFIX + "$OFB");
         var1.addAlgorithm("Cipher", NISTObjectIdentifiers.id_aes256_OFB, PREFIX + "$OFB");
         var1.addAlgorithm("Cipher", NISTObjectIdentifiers.id_aes128_CFB, PREFIX + "$CFB");
         var1.addAlgorithm("Cipher", NISTObjectIdentifiers.id_aes192_CFB, PREFIX + "$CFB");
         var1.addAlgorithm("Cipher", NISTObjectIdentifiers.id_aes256_CFB, PREFIX + "$CFB");
         var1.addAttributes("Cipher.AESWRAP", AES.generalAesAttributes);
         var1.addAlgorithm("Cipher.AESWRAP", PREFIX + "$Wrap");
         var1.addAlgorithm("Alg.Alias.Cipher", NISTObjectIdentifiers.id_aes128_wrap, "AESWRAP");
         var1.addAlgorithm("Alg.Alias.Cipher", NISTObjectIdentifiers.id_aes192_wrap, "AESWRAP");
         var1.addAlgorithm("Alg.Alias.Cipher", NISTObjectIdentifiers.id_aes256_wrap, "AESWRAP");
         var1.addAlgorithm("Alg.Alias.Cipher.AESKW", "AESWRAP");
         var1.addAttributes("Cipher.AESWRAPPAD", AES.generalAesAttributes);
         var1.addAlgorithm("Cipher.AESWRAPPAD", PREFIX + "$WrapPad");
         var1.addAlgorithm("Alg.Alias.Cipher", NISTObjectIdentifiers.id_aes128_wrap_pad, "AESWRAPPAD");
         var1.addAlgorithm("Alg.Alias.Cipher", NISTObjectIdentifiers.id_aes192_wrap_pad, "AESWRAPPAD");
         var1.addAlgorithm("Alg.Alias.Cipher", NISTObjectIdentifiers.id_aes256_wrap_pad, "AESWRAPPAD");
         var1.addAlgorithm("Alg.Alias.Cipher.AESKWP", "AESWRAPPAD");
         var1.addAlgorithm("Cipher.AESRFC3211WRAP", PREFIX + "$RFC3211Wrap");
         var1.addAlgorithm("Cipher.AESRFC5649WRAP", PREFIX + "$RFC5649Wrap");
         var1.addAlgorithm("AlgorithmParameterGenerator.CCM", PREFIX + "$AlgParamGenCCM");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + NISTObjectIdentifiers.id_aes128_CCM, "CCM");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + NISTObjectIdentifiers.id_aes192_CCM, "CCM");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + NISTObjectIdentifiers.id_aes256_CCM, "CCM");
         var1.addAttributes("Cipher.CCM", AES.generalAesAttributes);
         var1.addAlgorithm("Cipher.CCM", PREFIX + "$CCM");
         var1.addAlgorithm("Alg.Alias.Cipher", NISTObjectIdentifiers.id_aes128_CCM, "CCM");
         var1.addAlgorithm("Alg.Alias.Cipher", NISTObjectIdentifiers.id_aes192_CCM, "CCM");
         var1.addAlgorithm("Alg.Alias.Cipher", NISTObjectIdentifiers.id_aes256_CCM, "CCM");
         var1.addAlgorithm("AlgorithmParameterGenerator.GCM", PREFIX + "$AlgParamGenGCM");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + NISTObjectIdentifiers.id_aes128_GCM, "GCM");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + NISTObjectIdentifiers.id_aes192_GCM, "GCM");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + NISTObjectIdentifiers.id_aes256_GCM, "GCM");
         var1.addAttributes("Cipher.GCM", AES.generalAesAttributes);
         var1.addAlgorithm("Cipher.GCM", PREFIX + "$GCM");
         var1.addAlgorithm("Alg.Alias.Cipher", NISTObjectIdentifiers.id_aes128_GCM, "GCM");
         var1.addAlgorithm("Alg.Alias.Cipher", NISTObjectIdentifiers.id_aes192_GCM, "GCM");
         var1.addAlgorithm("Alg.Alias.Cipher", NISTObjectIdentifiers.id_aes256_GCM, "GCM");
         var1.addAlgorithm("KeyGenerator.AES", PREFIX + "$KeyGen");
         var1.addAlgorithm("KeyGenerator.2.16.840.1.101.3.4.2", PREFIX + "$KeyGen128");
         var1.addAlgorithm("KeyGenerator.2.16.840.1.101.3.4.22", PREFIX + "$KeyGen192");
         var1.addAlgorithm("KeyGenerator.2.16.840.1.101.3.4.42", PREFIX + "$KeyGen256");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes128_ECB, PREFIX + "$KeyGen128");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes128_CBC, PREFIX + "$KeyGen128");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes128_OFB, PREFIX + "$KeyGen128");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes128_CFB, PREFIX + "$KeyGen128");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes192_ECB, PREFIX + "$KeyGen192");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes192_CBC, PREFIX + "$KeyGen192");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes192_OFB, PREFIX + "$KeyGen192");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes192_CFB, PREFIX + "$KeyGen192");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes256_ECB, PREFIX + "$KeyGen256");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes256_CBC, PREFIX + "$KeyGen256");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes256_OFB, PREFIX + "$KeyGen256");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes256_CFB, PREFIX + "$KeyGen256");
         var1.addAlgorithm("KeyGenerator.AESWRAP", PREFIX + "$KeyGen");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes128_wrap, PREFIX + "$KeyGen128");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes192_wrap, PREFIX + "$KeyGen192");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes256_wrap, PREFIX + "$KeyGen256");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes128_GCM, PREFIX + "$KeyGen128");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes192_GCM, PREFIX + "$KeyGen192");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes256_GCM, PREFIX + "$KeyGen256");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes128_CCM, PREFIX + "$KeyGen128");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes192_CCM, PREFIX + "$KeyGen192");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes256_CCM, PREFIX + "$KeyGen256");
         var1.addAlgorithm("KeyGenerator.AESWRAPPAD", PREFIX + "$KeyGen");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes128_wrap_pad, PREFIX + "$KeyGen128");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes192_wrap_pad, PREFIX + "$KeyGen192");
         var1.addAlgorithm("KeyGenerator", NISTObjectIdentifiers.id_aes256_wrap_pad, PREFIX + "$KeyGen256");
         var1.addAlgorithm("Mac.AESCMAC", PREFIX + "$AESCMAC");
         var1.addAlgorithm("Mac.AESCCMMAC", PREFIX + "$AESCCMMAC");
         var1.addAlgorithm("Alg.Alias.Mac." + NISTObjectIdentifiers.id_aes128_CCM.getId(), "AESCCMMAC");
         var1.addAlgorithm("Alg.Alias.Mac." + NISTObjectIdentifiers.id_aes192_CCM.getId(), "AESCCMMAC");
         var1.addAlgorithm("Alg.Alias.Mac." + NISTObjectIdentifiers.id_aes256_CCM.getId(), "AESCCMMAC");
         var1.addAlgorithm("Alg.Alias.Cipher", BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes128_cbc, "PBEWITHSHAAND128BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher", BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes192_cbc, "PBEWITHSHAAND192BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher", BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes256_cbc, "PBEWITHSHAAND256BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher", BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes128_cbc, "PBEWITHSHA256AND128BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher", BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes192_cbc, "PBEWITHSHA256AND192BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher", BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes256_cbc, "PBEWITHSHA256AND256BITAES-CBC-BC");
         var1.addAlgorithm("Cipher.PBEWITHSHAAND128BITAES-CBC-BC", PREFIX + "$PBEWithSHA1AESCBC128");
         var1.addAlgorithm("Cipher.PBEWITHSHAAND192BITAES-CBC-BC", PREFIX + "$PBEWithSHA1AESCBC192");
         var1.addAlgorithm("Cipher.PBEWITHSHAAND256BITAES-CBC-BC", PREFIX + "$PBEWithSHA1AESCBC256");
         var1.addAlgorithm("Cipher.PBEWITHSHA256AND128BITAES-CBC-BC", PREFIX + "$PBEWithSHA256AESCBC128");
         var1.addAlgorithm("Cipher.PBEWITHSHA256AND192BITAES-CBC-BC", PREFIX + "$PBEWithSHA256AESCBC192");
         var1.addAlgorithm("Cipher.PBEWITHSHA256AND256BITAES-CBC-BC", PREFIX + "$PBEWithSHA256AESCBC256");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAAND128BITAES-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAAND192BITAES-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAAND256BITAES-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND128BITAES-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND192BITAES-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND256BITAES-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND128BITAES-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND192BITAES-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND256BITAES-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND128BITAES-CBC-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND192BITAES-CBC-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND256BITAES-CBC-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA256AND128BITAES-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA256AND192BITAES-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA256AND256BITAES-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND128BITAES-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND192BITAES-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND256BITAES-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
         var1.addAlgorithm("Cipher.PBEWITHMD5AND128BITAES-CBC-OPENSSL", PREFIX + "$PBEWithAESCBC");
         var1.addAlgorithm("Cipher.PBEWITHMD5AND192BITAES-CBC-OPENSSL", PREFIX + "$PBEWithAESCBC");
         var1.addAlgorithm("Cipher.PBEWITHMD5AND256BITAES-CBC-OPENSSL", PREFIX + "$PBEWithAESCBC");
         var1.addAlgorithm("SecretKeyFactory.AES", PREFIX + "$KeyFactory");
         var1.addAlgorithm("SecretKeyFactory", NISTObjectIdentifiers.aes, PREFIX + "$KeyFactory");
         var1.addAlgorithm("SecretKeyFactory.PBEWITHMD5AND128BITAES-CBC-OPENSSL", PREFIX + "$PBEWithMD5And128BitAESCBCOpenSSL");
         var1.addAlgorithm("SecretKeyFactory.PBEWITHMD5AND192BITAES-CBC-OPENSSL", PREFIX + "$PBEWithMD5And192BitAESCBCOpenSSL");
         var1.addAlgorithm("SecretKeyFactory.PBEWITHMD5AND256BITAES-CBC-OPENSSL", PREFIX + "$PBEWithMD5And256BitAESCBCOpenSSL");
         var1.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND128BITAES-CBC-BC", PREFIX + "$PBEWithSHAAnd128BitAESBC");
         var1.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND192BITAES-CBC-BC", PREFIX + "$PBEWithSHAAnd192BitAESBC");
         var1.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND256BITAES-CBC-BC", PREFIX + "$PBEWithSHAAnd256BitAESBC");
         var1.addAlgorithm("SecretKeyFactory.PBEWITHSHA256AND128BITAES-CBC-BC", PREFIX + "$PBEWithSHA256And128BitAESBC");
         var1.addAlgorithm("SecretKeyFactory.PBEWITHSHA256AND192BITAES-CBC-BC", PREFIX + "$PBEWithSHA256And192BitAESBC");
         var1.addAlgorithm("SecretKeyFactory.PBEWITHSHA256AND256BITAES-CBC-BC", PREFIX + "$PBEWithSHA256And256BitAESBC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND128BITAES-CBC-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND192BITAES-CBC-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND256BITAES-CBC-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND128BITAES-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND192BITAES-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND256BITAES-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory", BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes128_cbc, "PBEWITHSHAAND128BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory", BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes192_cbc, "PBEWITHSHAAND192BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory", BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes256_cbc, "PBEWITHSHAAND256BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory", BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes128_cbc, "PBEWITHSHA256AND128BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory", BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes192_cbc, "PBEWITHSHA256AND192BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory", BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes256_cbc, "PBEWITHSHA256AND256BITAES-CBC-BC");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND128BITAES-CBC-BC", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND192BITAES-CBC-BC", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND256BITAES-CBC-BC", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND128BITAES-CBC-BC", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND192BITAES-CBC-BC", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND256BITAES-CBC-BC", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND128BITAES-CBC-BC", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND192BITAES-CBC-BC", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND256BITAES-CBC-BC", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND128BITAES-CBC-BC", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND192BITAES-CBC-BC", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND256BITAES-CBC-BC", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND128BITAES-CBC-BC", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND192BITAES-CBC-BC", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND256BITAES-CBC-BC", "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes128_cbc.getId(), "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes192_cbc.getId(), "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes256_cbc.getId(), "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes128_cbc.getId(), "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes192_cbc.getId(), "PKCS12PBE");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes256_cbc.getId(), "PKCS12PBE");
         this.addGMacAlgorithm(var1, "AES", PREFIX + "$AESGMAC", PREFIX + "$KeyGen128");
         this.addPoly1305Algorithm(var1, "AES", PREFIX + "$Poly1305", PREFIX + "$Poly1305KeyGen");
      }
   }

   public static class OFB extends BaseBlockCipher {
      public OFB() {
         super((BufferedBlockCipher)(new BufferedBlockCipher(new OFBBlockCipher(new AESEngine(), 128))), 128);
      }
   }

   public static class PBEWithAESCBC extends BaseBlockCipher {
      public PBEWithAESCBC() {
         super((BlockCipher)(new CBCBlockCipher(new AESEngine())));
      }
   }

   public static class PBEWithMD5And128BitAESCBCOpenSSL extends PBESecretKeyFactory {
      public PBEWithMD5And128BitAESCBCOpenSSL() {
         super("PBEWithMD5And128BitAES-CBC-OpenSSL", (ASN1ObjectIdentifier)null, true, 3, 0, 128, 128);
      }
   }

   public static class PBEWithMD5And192BitAESCBCOpenSSL extends PBESecretKeyFactory {
      public PBEWithMD5And192BitAESCBCOpenSSL() {
         super("PBEWithMD5And192BitAES-CBC-OpenSSL", (ASN1ObjectIdentifier)null, true, 3, 0, 192, 128);
      }
   }

   public static class PBEWithMD5And256BitAESCBCOpenSSL extends PBESecretKeyFactory {
      public PBEWithMD5And256BitAESCBCOpenSSL() {
         super("PBEWithMD5And256BitAES-CBC-OpenSSL", (ASN1ObjectIdentifier)null, true, 3, 0, 256, 128);
      }
   }

   public static class PBEWithSHA1AESCBC128 extends BaseBlockCipher {
      public PBEWithSHA1AESCBC128() {
         super(new CBCBlockCipher(new AESEngine()), 2, 1, 128, 16);
      }
   }

   public static class PBEWithSHA1AESCBC192 extends BaseBlockCipher {
      public PBEWithSHA1AESCBC192() {
         super(new CBCBlockCipher(new AESEngine()), 2, 1, 192, 16);
      }
   }

   public static class PBEWithSHA1AESCBC256 extends BaseBlockCipher {
      public PBEWithSHA1AESCBC256() {
         super(new CBCBlockCipher(new AESEngine()), 2, 1, 256, 16);
      }
   }

   public static class PBEWithSHA256AESCBC128 extends BaseBlockCipher {
      public PBEWithSHA256AESCBC128() {
         super(new CBCBlockCipher(new AESEngine()), 2, 4, 128, 16);
      }
   }

   public static class PBEWithSHA256AESCBC192 extends BaseBlockCipher {
      public PBEWithSHA256AESCBC192() {
         super(new CBCBlockCipher(new AESEngine()), 2, 4, 192, 16);
      }
   }

   public static class PBEWithSHA256AESCBC256 extends BaseBlockCipher {
      public PBEWithSHA256AESCBC256() {
         super(new CBCBlockCipher(new AESEngine()), 2, 4, 256, 16);
      }
   }

   public static class PBEWithSHA256And128BitAESBC extends PBESecretKeyFactory {
      public PBEWithSHA256And128BitAESBC() {
         super("PBEWithSHA256And128BitAES-CBC-BC", (ASN1ObjectIdentifier)null, true, 2, 4, 128, 128);
      }
   }

   public static class PBEWithSHA256And192BitAESBC extends PBESecretKeyFactory {
      public PBEWithSHA256And192BitAESBC() {
         super("PBEWithSHA256And192BitAES-CBC-BC", (ASN1ObjectIdentifier)null, true, 2, 4, 192, 128);
      }
   }

   public static class PBEWithSHA256And256BitAESBC extends PBESecretKeyFactory {
      public PBEWithSHA256And256BitAESBC() {
         super("PBEWithSHA256And256BitAES-CBC-BC", (ASN1ObjectIdentifier)null, true, 2, 4, 256, 128);
      }
   }

   public static class PBEWithSHAAnd128BitAESBC extends PBESecretKeyFactory {
      public PBEWithSHAAnd128BitAESBC() {
         super("PBEWithSHA1And128BitAES-CBC-BC", (ASN1ObjectIdentifier)null, true, 2, 1, 128, 128);
      }
   }

   public static class PBEWithSHAAnd192BitAESBC extends PBESecretKeyFactory {
      public PBEWithSHAAnd192BitAESBC() {
         super("PBEWithSHA1And192BitAES-CBC-BC", (ASN1ObjectIdentifier)null, true, 2, 1, 192, 128);
      }
   }

   public static class PBEWithSHAAnd256BitAESBC extends PBESecretKeyFactory {
      public PBEWithSHAAnd256BitAESBC() {
         super("PBEWithSHA1And256BitAES-CBC-BC", (ASN1ObjectIdentifier)null, true, 2, 1, 256, 128);
      }
   }

   public static class Poly1305 extends BaseMac {
      public Poly1305() {
         super(new org.python.bouncycastle.crypto.macs.Poly1305(new AESEngine()));
      }
   }

   public static class Poly1305KeyGen extends BaseKeyGenerator {
      public Poly1305KeyGen() {
         super("Poly1305-AES", 256, new Poly1305KeyGenerator());
      }
   }

   public static class RFC3211Wrap extends BaseWrapCipher {
      public RFC3211Wrap() {
         super(new RFC3211WrapEngine(new AESEngine()), 16);
      }
   }

   public static class RFC5649Wrap extends BaseWrapCipher {
      public RFC5649Wrap() {
         super(new RFC5649WrapEngine(new AESEngine()));
      }
   }

   public static class Wrap extends BaseWrapCipher {
      public Wrap() {
         super(new AESWrapEngine());
      }
   }

   public static class WrapPad extends BaseWrapCipher {
      public WrapPad() {
         super(new AESWrapPadEngine());
      }
   }
}
