package org.python.bouncycastle.jcajce.provider.symmetric;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.python.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.python.bouncycastle.jcajce.provider.symmetric.util.PBE;
import org.python.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import org.python.bouncycastle.jcajce.spec.PBKDF2KeySpec;

public class PBEPBKDF2 {
   private PBEPBKDF2() {
   }

   public static class AlgParams extends BaseAlgorithmParameters {
      PBKDF2Params params;

      protected byte[] engineGetEncoded() {
         try {
            return this.params.getEncoded("DER");
         } catch (IOException var2) {
            throw new RuntimeException("Oooops! " + var2.toString());
         }
      }

      protected byte[] engineGetEncoded(String var1) {
         return this.isASN1FormatString(var1) ? this.engineGetEncoded() : null;
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         if (var1 == PBEParameterSpec.class) {
            return new PBEParameterSpec(this.params.getSalt(), this.params.getIterationCount().intValue());
         } else {
            throw new InvalidParameterSpecException("unknown parameter spec passed to PBKDF2 PBE parameters object.");
         }
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if (!(var1 instanceof PBEParameterSpec)) {
            throw new InvalidParameterSpecException("PBEParameterSpec required to initialise a PBKDF2 PBE parameters algorithm parameters object");
         } else {
            PBEParameterSpec var2 = (PBEParameterSpec)var1;
            this.params = new PBKDF2Params(var2.getSalt(), var2.getIterationCount());
         }
      }

      protected void engineInit(byte[] var1) throws IOException {
         this.params = PBKDF2Params.getInstance(ASN1Primitive.fromByteArray(var1));
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if (this.isASN1FormatString(var2)) {
            this.engineInit(var1);
         } else {
            throw new IOException("Unknown parameters format in PBKDF2 parameters object");
         }
      }

      protected String engineToString() {
         return "PBKDF2 Parameters";
      }
   }

   public static class BasePBKDF2 extends BaseSecretKeyFactory {
      private int scheme;
      private int defaultDigest;

      public BasePBKDF2(String var1, int var2) {
         this(var1, var2, 1);
      }

      public BasePBKDF2(String var1, int var2, int var3) {
         super(var1, PKCSObjectIdentifiers.id_PBKDF2);
         this.scheme = var2;
         this.defaultDigest = var3;
      }

      protected SecretKey engineGenerateSecret(KeySpec var1) throws InvalidKeySpecException {
         if (var1 instanceof PBEKeySpec) {
            PBEKeySpec var2 = (PBEKeySpec)var1;
            if (var2.getSalt() == null) {
               throw new InvalidKeySpecException("missing required salt");
            } else if (var2.getIterationCount() <= 0) {
               throw new InvalidKeySpecException("positive iteration count required: " + var2.getIterationCount());
            } else if (var2.getKeyLength() <= 0) {
               throw new InvalidKeySpecException("positive key length required: " + var2.getKeyLength());
            } else if (var2.getPassword().length == 0) {
               throw new IllegalArgumentException("password empty");
            } else {
               int var4;
               int var5;
               if (var2 instanceof PBKDF2KeySpec) {
                  PBKDF2KeySpec var8 = (PBKDF2KeySpec)var2;
                  var4 = this.getDigestCode(var8.getPrf().getAlgorithm());
                  var5 = var2.getKeyLength();
                  byte var9 = -1;
                  CipherParameters var7 = PBE.Util.makePBEMacParameters(var2, this.scheme, var4, var5);
                  return new BCPBEKey(this.algName, this.algOid, this.scheme, var4, var5, var9, var2, var7);
               } else {
                  int var3 = this.defaultDigest;
                  var4 = var2.getKeyLength();
                  var5 = -1;
                  CipherParameters var6 = PBE.Util.makePBEMacParameters(var2, this.scheme, var3, var4);
                  return new BCPBEKey(this.algName, this.algOid, this.scheme, var3, var4, var5, var2, var6);
               }
            }
         } else {
            throw new InvalidKeySpecException("Invalid KeySpec");
         }
      }

      private int getDigestCode(ASN1ObjectIdentifier var1) throws InvalidKeySpecException {
         if (var1.equals(CryptoProObjectIdentifiers.gostR3411Hmac)) {
            return 6;
         } else if (var1.equals(PKCSObjectIdentifiers.id_hmacWithSHA1)) {
            return 1;
         } else if (var1.equals(PKCSObjectIdentifiers.id_hmacWithSHA256)) {
            return 4;
         } else if (var1.equals(PKCSObjectIdentifiers.id_hmacWithSHA224)) {
            return 7;
         } else if (var1.equals(PKCSObjectIdentifiers.id_hmacWithSHA384)) {
            return 8;
         } else if (var1.equals(PKCSObjectIdentifiers.id_hmacWithSHA512)) {
            return 9;
         } else {
            throw new InvalidKeySpecException("Invalid KeySpec: unknown PRF algorithm " + var1);
         }
      }
   }

   public static class Mappings extends AlgorithmProvider {
      private static final String PREFIX = PBEPBKDF2.class.getName();

      public void configure(ConfigurableProvider var1) {
         var1.addAlgorithm("AlgorithmParameters.PBKDF2", PREFIX + "$AlgParams");
         var1.addAlgorithm("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.id_PBKDF2, "PBKDF2");
         var1.addAlgorithm("SecretKeyFactory.PBKDF2", PREFIX + "$PBKDF2withUTF8");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2WITHHMACSHA1", "PBKDF2");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2WITHHMACSHA1ANDUTF8", "PBKDF2");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory." + PKCSObjectIdentifiers.id_PBKDF2, "PBKDF2");
         var1.addAlgorithm("SecretKeyFactory.PBKDF2WITHASCII", PREFIX + "$PBKDF2with8BIT");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2WITH8BIT", "PBKDF2WITHASCII");
         var1.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2WITHHMACSHA1AND8BIT", "PBKDF2WITHASCII");
         var1.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA224", PREFIX + "$PBKDF2withSHA224");
         var1.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA256", PREFIX + "$PBKDF2withSHA256");
         var1.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA384", PREFIX + "$PBKDF2withSHA384");
         var1.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA512", PREFIX + "$PBKDF2withSHA512");
      }
   }

   public static class PBKDF2with8BIT extends BasePBKDF2 {
      public PBKDF2with8BIT() {
         super("PBKDF2", 1);
      }
   }

   public static class PBKDF2withSHA224 extends BasePBKDF2 {
      public PBKDF2withSHA224() {
         super("PBKDF2", 5, 7);
      }
   }

   public static class PBKDF2withSHA256 extends BasePBKDF2 {
      public PBKDF2withSHA256() {
         super("PBKDF2", 5, 4);
      }
   }

   public static class PBKDF2withSHA384 extends BasePBKDF2 {
      public PBKDF2withSHA384() {
         super("PBKDF2", 5, 8);
      }
   }

   public static class PBKDF2withSHA512 extends BasePBKDF2 {
      public PBKDF2withSHA512() {
         super("PBKDF2", 5, 9);
      }
   }

   public static class PBKDF2withUTF8 extends BasePBKDF2 {
      public PBKDF2withUTF8() {
         super("PBKDF2", 5);
      }
   }
}
