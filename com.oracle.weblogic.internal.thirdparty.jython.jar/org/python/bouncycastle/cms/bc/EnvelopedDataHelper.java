package org.python.bouncycastle.cms.bc;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.python.bouncycastle.asn1.ASN1Null;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.kisa.KISAObjectIdentifiers;
import org.python.bouncycastle.asn1.misc.CAST5CBCParameters;
import org.python.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.python.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.python.bouncycastle.asn1.ntt.NTTObjectIdentifiers;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.RC2CBCParameter;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.cms.CMSAlgorithm;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.crypto.BufferedBlockCipher;
import org.python.bouncycastle.crypto.CipherKeyGenerator;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.ExtendedDigest;
import org.python.bouncycastle.crypto.KeyGenerationParameters;
import org.python.bouncycastle.crypto.Wrapper;
import org.python.bouncycastle.crypto.digests.SHA1Digest;
import org.python.bouncycastle.crypto.digests.SHA224Digest;
import org.python.bouncycastle.crypto.digests.SHA256Digest;
import org.python.bouncycastle.crypto.digests.SHA384Digest;
import org.python.bouncycastle.crypto.digests.SHA512Digest;
import org.python.bouncycastle.crypto.engines.AESEngine;
import org.python.bouncycastle.crypto.engines.CAST5Engine;
import org.python.bouncycastle.crypto.engines.DESEngine;
import org.python.bouncycastle.crypto.engines.DESedeEngine;
import org.python.bouncycastle.crypto.engines.RC2Engine;
import org.python.bouncycastle.crypto.engines.RC4Engine;
import org.python.bouncycastle.crypto.engines.RFC3211WrapEngine;
import org.python.bouncycastle.crypto.generators.DESKeyGenerator;
import org.python.bouncycastle.crypto.generators.DESedeKeyGenerator;
import org.python.bouncycastle.crypto.modes.CBCBlockCipher;
import org.python.bouncycastle.crypto.paddings.PKCS7Padding;
import org.python.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;
import org.python.bouncycastle.crypto.params.RC2Parameters;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.operator.bc.BcDigestProvider;

class EnvelopedDataHelper {
   protected static final Map BASE_CIPHER_NAMES = new HashMap();
   protected static final Map MAC_ALG_NAMES = new HashMap();
   private static final Map prfs = createTable();
   private static final short[] rc2Table;
   private static final short[] rc2Ekb;

   private static Map createTable() {
      HashMap var0 = new HashMap();
      var0.put(PKCSObjectIdentifiers.id_hmacWithSHA1, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new SHA1Digest();
         }
      });
      var0.put(PKCSObjectIdentifiers.id_hmacWithSHA224, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new SHA224Digest();
         }
      });
      var0.put(PKCSObjectIdentifiers.id_hmacWithSHA256, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new SHA256Digest();
         }
      });
      var0.put(PKCSObjectIdentifiers.id_hmacWithSHA384, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new SHA384Digest();
         }
      });
      var0.put(PKCSObjectIdentifiers.id_hmacWithSHA512, new BcDigestProvider() {
         public ExtendedDigest get(AlgorithmIdentifier var1) {
            return new SHA512Digest();
         }
      });
      return Collections.unmodifiableMap(var0);
   }

   String getBaseCipherName(ASN1ObjectIdentifier var1) {
      String var2 = (String)BASE_CIPHER_NAMES.get(var1);
      return var2 == null ? var1.getId() : var2;
   }

   static ExtendedDigest getPRF(AlgorithmIdentifier var0) throws OperatorCreationException {
      return ((BcDigestProvider)prfs.get(var0.getAlgorithm())).get((AlgorithmIdentifier)null);
   }

   static BufferedBlockCipher createCipher(ASN1ObjectIdentifier var0) throws CMSException {
      CBCBlockCipher var1;
      if (!NISTObjectIdentifiers.id_aes128_CBC.equals(var0) && !NISTObjectIdentifiers.id_aes192_CBC.equals(var0) && !NISTObjectIdentifiers.id_aes256_CBC.equals(var0)) {
         if (PKCSObjectIdentifiers.des_EDE3_CBC.equals(var0)) {
            var1 = new CBCBlockCipher(new DESedeEngine());
         } else if (OIWObjectIdentifiers.desCBC.equals(var0)) {
            var1 = new CBCBlockCipher(new DESEngine());
         } else if (PKCSObjectIdentifiers.RC2_CBC.equals(var0)) {
            var1 = new CBCBlockCipher(new RC2Engine());
         } else {
            if (!MiscObjectIdentifiers.cast5CBC.equals(var0)) {
               throw new CMSException("cannot recognise cipher: " + var0);
            }

            var1 = new CBCBlockCipher(new CAST5Engine());
         }
      } else {
         var1 = new CBCBlockCipher(new AESEngine());
      }

      return new PaddedBufferedBlockCipher(var1, new PKCS7Padding());
   }

   static Wrapper createRFC3211Wrapper(ASN1ObjectIdentifier var0) throws CMSException {
      if (!NISTObjectIdentifiers.id_aes128_CBC.equals(var0) && !NISTObjectIdentifiers.id_aes192_CBC.equals(var0) && !NISTObjectIdentifiers.id_aes256_CBC.equals(var0)) {
         if (PKCSObjectIdentifiers.des_EDE3_CBC.equals(var0)) {
            return new RFC3211WrapEngine(new DESedeEngine());
         } else if (OIWObjectIdentifiers.desCBC.equals(var0)) {
            return new RFC3211WrapEngine(new DESEngine());
         } else if (PKCSObjectIdentifiers.RC2_CBC.equals(var0)) {
            return new RFC3211WrapEngine(new RC2Engine());
         } else {
            throw new CMSException("cannot recognise wrapper: " + var0);
         }
      } else {
         return new RFC3211WrapEngine(new AESEngine());
      }
   }

   static Object createContentCipher(boolean var0, CipherParameters var1, AlgorithmIdentifier var2) throws CMSException {
      ASN1ObjectIdentifier var3 = var2.getAlgorithm();
      if (var3.equals(PKCSObjectIdentifiers.rc4)) {
         RC4Engine var7 = new RC4Engine();
         var7.init(var0, var1);
         return var7;
      } else {
         BufferedBlockCipher var4 = createCipher(var2.getAlgorithm());
         ASN1Primitive var5 = var2.getParameters().toASN1Primitive();
         if (var5 != null && !(var5 instanceof ASN1Null)) {
            if (!var3.equals(CMSAlgorithm.DES_EDE3_CBC) && !var3.equals(CMSAlgorithm.IDEA_CBC) && !var3.equals(CMSAlgorithm.AES128_CBC) && !var3.equals(CMSAlgorithm.AES192_CBC) && !var3.equals(CMSAlgorithm.AES256_CBC) && !var3.equals(CMSAlgorithm.CAMELLIA128_CBC) && !var3.equals(CMSAlgorithm.CAMELLIA192_CBC) && !var3.equals(CMSAlgorithm.CAMELLIA256_CBC) && !var3.equals(CMSAlgorithm.SEED_CBC) && !var3.equals(OIWObjectIdentifiers.desCBC)) {
               if (var3.equals(CMSAlgorithm.CAST5_CBC)) {
                  CAST5CBCParameters var6 = CAST5CBCParameters.getInstance(var5);
                  var4.init(var0, new ParametersWithIV(var1, var6.getIV()));
               } else {
                  if (!var3.equals(CMSAlgorithm.RC2_CBC)) {
                     throw new CMSException("cannot match parameters");
                  }

                  RC2CBCParameter var8 = RC2CBCParameter.getInstance(var5);
                  var4.init(var0, new ParametersWithIV(new RC2Parameters(((KeyParameter)var1).getKey(), rc2Ekb[var8.getRC2ParameterVersion().intValue()]), var8.getIV()));
               }
            } else {
               var4.init(var0, new ParametersWithIV(var1, ASN1OctetString.getInstance(var5).getOctets()));
            }
         } else if (!var3.equals(CMSAlgorithm.DES_EDE3_CBC) && !var3.equals(CMSAlgorithm.IDEA_CBC) && !var3.equals(CMSAlgorithm.CAST5_CBC)) {
            var4.init(var0, var1);
         } else {
            var4.init(var0, new ParametersWithIV(var1, new byte[8]));
         }

         return var4;
      }
   }

   AlgorithmIdentifier generateAlgorithmIdentifier(ASN1ObjectIdentifier var1, CipherParameters var2, SecureRandom var3) throws CMSException {
      byte[] var4;
      if (!var1.equals(CMSAlgorithm.AES128_CBC) && !var1.equals(CMSAlgorithm.AES192_CBC) && !var1.equals(CMSAlgorithm.AES256_CBC) && !var1.equals(CMSAlgorithm.CAMELLIA128_CBC) && !var1.equals(CMSAlgorithm.CAMELLIA192_CBC) && !var1.equals(CMSAlgorithm.CAMELLIA256_CBC) && !var1.equals(CMSAlgorithm.SEED_CBC)) {
         if (!var1.equals(CMSAlgorithm.DES_EDE3_CBC) && !var1.equals(CMSAlgorithm.IDEA_CBC) && !var1.equals(OIWObjectIdentifiers.desCBC)) {
            if (var1.equals(CMSAlgorithm.CAST5_CBC)) {
               var4 = new byte[8];
               var3.nextBytes(var4);
               CAST5CBCParameters var6 = new CAST5CBCParameters(var4, ((KeyParameter)var2).getKey().length * 8);
               return new AlgorithmIdentifier(var1, var6);
            } else if (var1.equals(PKCSObjectIdentifiers.rc4)) {
               return new AlgorithmIdentifier(var1, DERNull.INSTANCE);
            } else if (var1.equals(PKCSObjectIdentifiers.RC2_CBC)) {
               var4 = new byte[8];
               var3.nextBytes(var4);
               RC2CBCParameter var5 = new RC2CBCParameter(rc2Table[128], var4);
               return new AlgorithmIdentifier(var1, var5);
            } else {
               throw new CMSException("unable to match algorithm");
            }
         } else {
            var4 = new byte[8];
            var3.nextBytes(var4);
            return new AlgorithmIdentifier(var1, new DEROctetString(var4));
         }
      } else {
         var4 = new byte[16];
         var3.nextBytes(var4);
         return new AlgorithmIdentifier(var1, new DEROctetString(var4));
      }
   }

   CipherKeyGenerator createKeyGenerator(ASN1ObjectIdentifier var1, SecureRandom var2) throws CMSException {
      if (NISTObjectIdentifiers.id_aes128_CBC.equals(var1)) {
         return this.createCipherKeyGenerator(var2, 128);
      } else if (NISTObjectIdentifiers.id_aes192_CBC.equals(var1)) {
         return this.createCipherKeyGenerator(var2, 192);
      } else if (NISTObjectIdentifiers.id_aes256_CBC.equals(var1)) {
         return this.createCipherKeyGenerator(var2, 256);
      } else if (PKCSObjectIdentifiers.des_EDE3_CBC.equals(var1)) {
         DESedeKeyGenerator var4 = new DESedeKeyGenerator();
         var4.init(new KeyGenerationParameters(var2, 192));
         return var4;
      } else if (NTTObjectIdentifiers.id_camellia128_cbc.equals(var1)) {
         return this.createCipherKeyGenerator(var2, 128);
      } else if (NTTObjectIdentifiers.id_camellia192_cbc.equals(var1)) {
         return this.createCipherKeyGenerator(var2, 192);
      } else if (NTTObjectIdentifiers.id_camellia256_cbc.equals(var1)) {
         return this.createCipherKeyGenerator(var2, 256);
      } else if (KISAObjectIdentifiers.id_seedCBC.equals(var1)) {
         return this.createCipherKeyGenerator(var2, 128);
      } else if (CMSAlgorithm.CAST5_CBC.equals(var1)) {
         return this.createCipherKeyGenerator(var2, 128);
      } else if (OIWObjectIdentifiers.desCBC.equals(var1)) {
         DESKeyGenerator var3 = new DESKeyGenerator();
         var3.init(new KeyGenerationParameters(var2, 64));
         return var3;
      } else if (PKCSObjectIdentifiers.rc4.equals(var1)) {
         return this.createCipherKeyGenerator(var2, 128);
      } else if (PKCSObjectIdentifiers.RC2_CBC.equals(var1)) {
         return this.createCipherKeyGenerator(var2, 128);
      } else {
         throw new CMSException("cannot recognise cipher: " + var1);
      }
   }

   private CipherKeyGenerator createCipherKeyGenerator(SecureRandom var1, int var2) {
      CipherKeyGenerator var3 = new CipherKeyGenerator();
      var3.init(new KeyGenerationParameters(var1, var2));
      return var3;
   }

   static {
      BASE_CIPHER_NAMES.put(CMSAlgorithm.DES_EDE3_CBC, "DESEDE");
      BASE_CIPHER_NAMES.put(CMSAlgorithm.AES128_CBC, "AES");
      BASE_CIPHER_NAMES.put(CMSAlgorithm.AES192_CBC, "AES");
      BASE_CIPHER_NAMES.put(CMSAlgorithm.AES256_CBC, "AES");
      MAC_ALG_NAMES.put(CMSAlgorithm.DES_EDE3_CBC, "DESEDEMac");
      MAC_ALG_NAMES.put(CMSAlgorithm.AES128_CBC, "AESMac");
      MAC_ALG_NAMES.put(CMSAlgorithm.AES192_CBC, "AESMac");
      MAC_ALG_NAMES.put(CMSAlgorithm.AES256_CBC, "AESMac");
      MAC_ALG_NAMES.put(CMSAlgorithm.RC2_CBC, "RC2Mac");
      rc2Table = new short[]{189, 86, 234, 242, 162, 241, 172, 42, 176, 147, 209, 156, 27, 51, 253, 208, 48, 4, 182, 220, 125, 223, 50, 75, 247, 203, 69, 155, 49, 187, 33, 90, 65, 159, 225, 217, 74, 77, 158, 218, 160, 104, 44, 195, 39, 95, 128, 54, 62, 238, 251, 149, 26, 254, 206, 168, 52, 169, 19, 240, 166, 63, 216, 12, 120, 36, 175, 35, 82, 193, 103, 23, 245, 102, 144, 231, 232, 7, 184, 96, 72, 230, 30, 83, 243, 146, 164, 114, 140, 8, 21, 110, 134, 0, 132, 250, 244, 127, 138, 66, 25, 246, 219, 205, 20, 141, 80, 18, 186, 60, 6, 78, 236, 179, 53, 17, 161, 136, 142, 43, 148, 153, 183, 113, 116, 211, 228, 191, 58, 222, 150, 14, 188, 10, 237, 119, 252, 55, 107, 3, 121, 137, 98, 198, 215, 192, 210, 124, 106, 139, 34, 163, 91, 5, 93, 2, 117, 213, 97, 227, 24, 143, 85, 81, 173, 31, 11, 94, 133, 229, 194, 87, 99, 202, 61, 108, 180, 197, 204, 112, 178, 145, 89, 13, 71, 32, 200, 79, 88, 224, 1, 226, 22, 56, 196, 111, 59, 15, 101, 70, 190, 126, 45, 123, 130, 249, 64, 181, 29, 115, 248, 235, 38, 199, 135, 151, 37, 84, 177, 40, 170, 152, 157, 165, 100, 109, 122, 212, 16, 129, 68, 239, 73, 214, 174, 46, 221, 118, 92, 47, 167, 28, 201, 9, 105, 154, 131, 207, 41, 57, 185, 233, 76, 255, 67, 171};
      rc2Ekb = new short[]{93, 190, 155, 139, 17, 153, 110, 77, 89, 243, 133, 166, 63, 183, 131, 197, 228, 115, 107, 58, 104, 90, 192, 71, 160, 100, 52, 12, 241, 208, 82, 165, 185, 30, 150, 67, 65, 216, 212, 44, 219, 248, 7, 119, 42, 202, 235, 239, 16, 28, 22, 13, 56, 114, 47, 137, 193, 249, 128, 196, 109, 174, 48, 61, 206, 32, 99, 254, 230, 26, 199, 184, 80, 232, 36, 23, 252, 37, 111, 187, 106, 163, 68, 83, 217, 162, 1, 171, 188, 182, 31, 152, 238, 154, 167, 45, 79, 158, 142, 172, 224, 198, 73, 70, 41, 244, 148, 138, 175, 225, 91, 195, 179, 123, 87, 209, 124, 156, 237, 135, 64, 140, 226, 203, 147, 20, 201, 97, 46, 229, 204, 246, 94, 168, 92, 214, 117, 141, 98, 149, 88, 105, 118, 161, 74, 181, 85, 9, 120, 51, 130, 215, 221, 121, 245, 27, 11, 222, 38, 33, 40, 116, 4, 151, 86, 223, 60, 240, 55, 57, 220, 255, 6, 164, 234, 66, 8, 218, 180, 113, 176, 207, 18, 122, 78, 250, 108, 29, 132, 0, 200, 127, 145, 69, 170, 43, 194, 177, 143, 213, 186, 242, 173, 25, 178, 103, 54, 247, 15, 10, 146, 125, 227, 157, 233, 144, 62, 35, 39, 102, 19, 236, 129, 21, 189, 34, 191, 159, 126, 169, 81, 75, 76, 251, 2, 211, 112, 134, 49, 231, 59, 5, 3, 84, 96, 72, 101, 24, 210, 205, 95, 50, 136, 14, 53, 253};
   }
}
