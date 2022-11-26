package org.python.bouncycastle.jcajce.provider.symmetric.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.PBEParametersGenerator;
import org.python.bouncycastle.crypto.digests.GOST3411Digest;
import org.python.bouncycastle.crypto.digests.MD2Digest;
import org.python.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.python.bouncycastle.crypto.digests.TigerDigest;
import org.python.bouncycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import org.python.bouncycastle.crypto.generators.PKCS12ParametersGenerator;
import org.python.bouncycastle.crypto.generators.PKCS5S1ParametersGenerator;
import org.python.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.python.bouncycastle.crypto.params.DESParameters;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;
import org.python.bouncycastle.crypto.util.DigestFactory;

public interface PBE {
   int MD5 = 0;
   int SHA1 = 1;
   int RIPEMD160 = 2;
   int TIGER = 3;
   int SHA256 = 4;
   int MD2 = 5;
   int GOST3411 = 6;
   int SHA224 = 7;
   int SHA384 = 8;
   int SHA512 = 9;
   int PKCS5S1 = 0;
   int PKCS5S2 = 1;
   int PKCS12 = 2;
   int OPENSSL = 3;
   int PKCS5S1_UTF8 = 4;
   int PKCS5S2_UTF8 = 5;

   public static class Util {
      private static PBEParametersGenerator makePBEGenerator(int var0, int var1) {
         Object var2;
         if (var0 != 0 && var0 != 4) {
            if (var0 != 1 && var0 != 5) {
               if (var0 == 2) {
                  switch (var1) {
                     case 0:
                        var2 = new PKCS12ParametersGenerator(DigestFactory.createMD5());
                        break;
                     case 1:
                        var2 = new PKCS12ParametersGenerator(DigestFactory.createSHA1());
                        break;
                     case 2:
                        var2 = new PKCS12ParametersGenerator(new RIPEMD160Digest());
                        break;
                     case 3:
                        var2 = new PKCS12ParametersGenerator(new TigerDigest());
                        break;
                     case 4:
                        var2 = new PKCS12ParametersGenerator(DigestFactory.createSHA256());
                        break;
                     case 5:
                        var2 = new PKCS12ParametersGenerator(new MD2Digest());
                        break;
                     case 6:
                        var2 = new PKCS12ParametersGenerator(new GOST3411Digest());
                        break;
                     case 7:
                        var2 = new PKCS12ParametersGenerator(DigestFactory.createSHA224());
                        break;
                     case 8:
                        var2 = new PKCS12ParametersGenerator(DigestFactory.createSHA384());
                        break;
                     case 9:
                        var2 = new PKCS12ParametersGenerator(DigestFactory.createSHA512());
                        break;
                     default:
                        throw new IllegalStateException("unknown digest scheme for PBE encryption.");
                  }
               } else {
                  var2 = new OpenSSLPBEParametersGenerator();
               }
            } else {
               switch (var1) {
                  case 0:
                     var2 = new PKCS5S2ParametersGenerator(DigestFactory.createMD5());
                     break;
                  case 1:
                     var2 = new PKCS5S2ParametersGenerator(DigestFactory.createSHA1());
                     break;
                  case 2:
                     var2 = new PKCS5S2ParametersGenerator(new RIPEMD160Digest());
                     break;
                  case 3:
                     var2 = new PKCS5S2ParametersGenerator(new TigerDigest());
                     break;
                  case 4:
                     var2 = new PKCS5S2ParametersGenerator(DigestFactory.createSHA256());
                     break;
                  case 5:
                     var2 = new PKCS5S2ParametersGenerator(new MD2Digest());
                     break;
                  case 6:
                     var2 = new PKCS5S2ParametersGenerator(new GOST3411Digest());
                     break;
                  case 7:
                     var2 = new PKCS5S2ParametersGenerator(DigestFactory.createSHA224());
                     break;
                  case 8:
                     var2 = new PKCS5S2ParametersGenerator(DigestFactory.createSHA384());
                     break;
                  case 9:
                     var2 = new PKCS5S2ParametersGenerator(DigestFactory.createSHA512());
                     break;
                  default:
                     throw new IllegalStateException("unknown digest scheme for PBE PKCS5S2 encryption.");
               }
            }
         } else {
            switch (var1) {
               case 0:
                  var2 = new PKCS5S1ParametersGenerator(DigestFactory.createMD5());
                  break;
               case 1:
                  var2 = new PKCS5S1ParametersGenerator(DigestFactory.createSHA1());
                  break;
               case 5:
                  var2 = new PKCS5S1ParametersGenerator(new MD2Digest());
                  break;
               default:
                  throw new IllegalStateException("PKCS5 scheme 1 only supports MD2, MD5 and SHA1.");
            }
         }

         return (PBEParametersGenerator)var2;
      }

      public static CipherParameters makePBEParameters(byte[] var0, int var1, int var2, int var3, int var4, AlgorithmParameterSpec var5, String var6) throws InvalidAlgorithmParameterException {
         if (var5 != null && var5 instanceof PBEParameterSpec) {
            PBEParameterSpec var7 = (PBEParameterSpec)var5;
            PBEParametersGenerator var8 = makePBEGenerator(var1, var2);
            var8.init(var0, var7.getSalt(), var7.getIterationCount());
            CipherParameters var10;
            if (var4 != 0) {
               var10 = var8.generateDerivedParameters(var3, var4);
            } else {
               var10 = var8.generateDerivedParameters(var3);
            }

            if (var6.startsWith("DES")) {
               KeyParameter var11;
               if (var10 instanceof ParametersWithIV) {
                  var11 = (KeyParameter)((ParametersWithIV)var10).getParameters();
                  DESParameters.setOddParity(var11.getKey());
               } else {
                  var11 = (KeyParameter)var10;
                  DESParameters.setOddParity(var11.getKey());
               }
            }

            return var10;
         } else {
            throw new InvalidAlgorithmParameterException("Need a PBEParameter spec with a PBE key.");
         }
      }

      public static CipherParameters makePBEParameters(BCPBEKey var0, AlgorithmParameterSpec var1, String var2) {
         if (var1 != null && var1 instanceof PBEParameterSpec) {
            PBEParameterSpec var3 = (PBEParameterSpec)var1;
            PBEParametersGenerator var4 = makePBEGenerator(var0.getType(), var0.getDigest());
            byte[] var5 = var0.getEncoded();
            if (var0.shouldTryWrongPKCS12()) {
               var5 = new byte[2];
            }

            var4.init(var5, var3.getSalt(), var3.getIterationCount());
            CipherParameters var6;
            if (var0.getIvSize() != 0) {
               var6 = var4.generateDerivedParameters(var0.getKeySize(), var0.getIvSize());
            } else {
               var6 = var4.generateDerivedParameters(var0.getKeySize());
            }

            if (var2.startsWith("DES")) {
               KeyParameter var7;
               if (var6 instanceof ParametersWithIV) {
                  var7 = (KeyParameter)((ParametersWithIV)var6).getParameters();
                  DESParameters.setOddParity(var7.getKey());
               } else {
                  var7 = (KeyParameter)var6;
                  DESParameters.setOddParity(var7.getKey());
               }
            }

            return var6;
         } else {
            throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
         }
      }

      public static CipherParameters makePBEMacParameters(BCPBEKey var0, AlgorithmParameterSpec var1) {
         if (var1 != null && var1 instanceof PBEParameterSpec) {
            PBEParameterSpec var2 = (PBEParameterSpec)var1;
            PBEParametersGenerator var3 = makePBEGenerator(var0.getType(), var0.getDigest());
            byte[] var4 = var0.getEncoded();
            var3.init(var4, var2.getSalt(), var2.getIterationCount());
            CipherParameters var5 = var3.generateDerivedMacParameters(var0.getKeySize());
            return var5;
         } else {
            throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
         }
      }

      public static CipherParameters makePBEMacParameters(PBEKeySpec var0, int var1, int var2, int var3) {
         PBEParametersGenerator var4 = makePBEGenerator(var1, var2);
         byte[] var5 = convertPassword(var1, var0);
         var4.init(var5, var0.getSalt(), var0.getIterationCount());
         CipherParameters var6 = var4.generateDerivedMacParameters(var3);

         for(int var7 = 0; var7 != var5.length; ++var7) {
            var5[var7] = 0;
         }

         return var6;
      }

      public static CipherParameters makePBEParameters(PBEKeySpec var0, int var1, int var2, int var3, int var4) {
         PBEParametersGenerator var5 = makePBEGenerator(var1, var2);
         byte[] var6 = convertPassword(var1, var0);
         var5.init(var6, var0.getSalt(), var0.getIterationCount());
         CipherParameters var7;
         if (var4 != 0) {
            var7 = var5.generateDerivedParameters(var3, var4);
         } else {
            var7 = var5.generateDerivedParameters(var3);
         }

         for(int var8 = 0; var8 != var6.length; ++var8) {
            var6[var8] = 0;
         }

         return var7;
      }

      public static CipherParameters makePBEMacParameters(SecretKey var0, int var1, int var2, int var3, PBEParameterSpec var4) {
         PBEParametersGenerator var5 = makePBEGenerator(var1, var2);
         byte[] var6 = var0.getEncoded();
         var5.init(var0.getEncoded(), var4.getSalt(), var4.getIterationCount());
         CipherParameters var7 = var5.generateDerivedMacParameters(var3);

         for(int var8 = 0; var8 != var6.length; ++var8) {
            var6[var8] = 0;
         }

         return var7;
      }

      private static byte[] convertPassword(int var0, PBEKeySpec var1) {
         byte[] var2;
         if (var0 == 2) {
            var2 = PBEParametersGenerator.PKCS12PasswordToBytes(var1.getPassword());
         } else if (var0 != 5 && var0 != 4) {
            var2 = PBEParametersGenerator.PKCS5PasswordToBytes(var1.getPassword());
         } else {
            var2 = PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(var1.getPassword());
         }

         return var2;
      }
   }
}
