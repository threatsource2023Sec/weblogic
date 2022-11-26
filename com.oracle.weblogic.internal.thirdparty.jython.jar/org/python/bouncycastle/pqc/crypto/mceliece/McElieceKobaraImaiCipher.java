package org.python.bouncycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.digests.SHA1Digest;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.crypto.prng.DigestRandomGenerator;
import org.python.bouncycastle.pqc.crypto.MessageEncryptor;
import org.python.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.python.bouncycastle.pqc.math.linearalgebra.GF2Vector;
import org.python.bouncycastle.pqc.math.linearalgebra.IntegerFunctions;

public class McElieceKobaraImaiCipher implements MessageEncryptor {
   public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.2.3";
   private static final String DEFAULT_PRNG_NAME = "SHA1PRNG";
   public static final byte[] PUBLIC_CONSTANT = "a predetermined public constant".getBytes();
   private Digest messDigest;
   private SecureRandom sr;
   McElieceCCA2KeyParameters key;
   private int n;
   private int k;
   private int t;
   private boolean forEncryption;

   public void init(boolean var1, CipherParameters var2) {
      this.forEncryption = var1;
      if (var1) {
         if (var2 instanceof ParametersWithRandom) {
            ParametersWithRandom var3 = (ParametersWithRandom)var2;
            this.sr = var3.getRandom();
            this.key = (McElieceCCA2PublicKeyParameters)var3.getParameters();
            this.initCipherEncrypt((McElieceCCA2PublicKeyParameters)this.key);
         } else {
            this.sr = new SecureRandom();
            this.key = (McElieceCCA2PublicKeyParameters)var2;
            this.initCipherEncrypt((McElieceCCA2PublicKeyParameters)this.key);
         }
      } else {
         this.key = (McElieceCCA2PrivateKeyParameters)var2;
         this.initCipherDecrypt((McElieceCCA2PrivateKeyParameters)this.key);
      }

   }

   public int getKeySize(McElieceCCA2KeyParameters var1) {
      if (var1 instanceof McElieceCCA2PublicKeyParameters) {
         return ((McElieceCCA2PublicKeyParameters)var1).getN();
      } else if (var1 instanceof McElieceCCA2PrivateKeyParameters) {
         return ((McElieceCCA2PrivateKeyParameters)var1).getN();
      } else {
         throw new IllegalArgumentException("unsupported type");
      }
   }

   private void initCipherEncrypt(McElieceCCA2PublicKeyParameters var1) {
      this.messDigest = Utils.getDigest(var1.getDigest());
      this.n = var1.getN();
      this.k = var1.getK();
      this.t = var1.getT();
   }

   public void initCipherDecrypt(McElieceCCA2PrivateKeyParameters var1) {
      this.messDigest = Utils.getDigest(var1.getDigest());
      this.n = var1.getN();
      this.k = var1.getK();
      this.t = var1.getT();
   }

   public byte[] messageEncrypt(byte[] var1) {
      if (!this.forEncryption) {
         throw new IllegalStateException("cipher initialised for decryption");
      } else {
         int var2 = this.messDigest.getDigestSize();
         int var3 = this.k >> 3;
         int var4 = IntegerFunctions.binomial(this.n, this.t).bitLength() - 1 >> 3;
         int var5 = var3 + var4 - var2 - PUBLIC_CONSTANT.length;
         if (var1.length > var5) {
            var5 = var1.length;
         }

         int var6 = var5 + PUBLIC_CONSTANT.length;
         int var7 = var6 + var2 - var3 - var4;
         byte[] var8 = new byte[var6];
         System.arraycopy(var1, 0, var8, 0, var1.length);
         System.arraycopy(PUBLIC_CONSTANT, 0, var8, var5, PUBLIC_CONSTANT.length);
         byte[] var9 = new byte[var2];
         this.sr.nextBytes(var9);
         DigestRandomGenerator var10 = new DigestRandomGenerator(new SHA1Digest());
         var10.addSeedMaterial(var9);
         byte[] var11 = new byte[var6];
         var10.nextBytes(var11);

         for(int var12 = var6 - 1; var12 >= 0; --var12) {
            var11[var12] ^= var8[var12];
         }

         byte[] var20 = new byte[this.messDigest.getDigestSize()];
         this.messDigest.update(var11, 0, var11.length);
         this.messDigest.doFinal(var20, 0);

         for(int var13 = var2 - 1; var13 >= 0; --var13) {
            var20[var13] ^= var9[var13];
         }

         byte[] var21 = ByteUtils.concatenate(var20, var11);
         byte[] var14 = new byte[0];
         if (var7 > 0) {
            var14 = new byte[var7];
            System.arraycopy(var21, 0, var14, 0, var7);
         }

         byte[] var15 = new byte[var4];
         System.arraycopy(var21, var7, var15, 0, var4);
         byte[] var16 = new byte[var3];
         System.arraycopy(var21, var7 + var4, var16, 0, var3);
         GF2Vector var17 = GF2Vector.OS2VP(this.k, var16);
         GF2Vector var18 = Conversions.encode(this.n, this.t, var15);
         byte[] var19 = McElieceCCA2Primitives.encryptionPrimitive((McElieceCCA2PublicKeyParameters)this.key, var17, var18).getEncoded();
         return var7 > 0 ? ByteUtils.concatenate(var14, var19) : var19;
      }
   }

   public byte[] messageDecrypt(byte[] var1) throws InvalidCipherTextException {
      if (this.forEncryption) {
         throw new IllegalStateException("cipher initialised for decryption");
      } else {
         int var2 = this.n >> 3;
         if (var1.length < var2) {
            throw new InvalidCipherTextException("Bad Padding: Ciphertext too short.");
         } else {
            int var3 = this.messDigest.getDigestSize();
            int var4 = this.k >> 3;
            int var5 = var1.length - var2;
            byte[] var7;
            byte[] var8;
            if (var5 > 0) {
               byte[][] var6 = ByteUtils.split(var1, var5);
               var7 = var6[0];
               var8 = var6[1];
            } else {
               var7 = new byte[0];
               var8 = var1;
            }

            GF2Vector var24 = GF2Vector.OS2VP(this.n, var8);
            GF2Vector[] var9 = McElieceCCA2Primitives.decryptionPrimitive((McElieceCCA2PrivateKeyParameters)this.key, var24);
            byte[] var10 = var9[0].getEncoded();
            GF2Vector var11 = var9[1];
            if (var10.length > var4) {
               var10 = ByteUtils.subArray(var10, 0, var4);
            }

            byte[] var12 = Conversions.decode(this.n, this.t, var11);
            byte[] var13 = ByteUtils.concatenate(var7, var12);
            var13 = ByteUtils.concatenate(var13, var10);
            int var14 = var13.length - var3;
            byte[][] var15 = ByteUtils.split(var13, var3);
            byte[] var16 = var15[0];
            byte[] var17 = var15[1];
            byte[] var18 = new byte[this.messDigest.getDigestSize()];
            this.messDigest.update(var17, 0, var17.length);
            this.messDigest.doFinal(var18, 0);

            for(int var19 = var3 - 1; var19 >= 0; --var19) {
               var18[var19] ^= var16[var19];
            }

            DigestRandomGenerator var25 = new DigestRandomGenerator(new SHA1Digest());
            var25.addSeedMaterial(var18);
            byte[] var20 = new byte[var14];
            var25.nextBytes(var20);

            for(int var21 = var14 - 1; var21 >= 0; --var21) {
               var20[var21] ^= var17[var21];
            }

            if (var20.length < var14) {
               throw new InvalidCipherTextException("Bad Padding: invalid ciphertext");
            } else {
               byte[][] var26 = ByteUtils.split(var20, var14 - PUBLIC_CONSTANT.length);
               byte[] var22 = var26[0];
               byte[] var23 = var26[1];
               if (!ByteUtils.equals(var23, PUBLIC_CONSTANT)) {
                  throw new InvalidCipherTextException("Bad Padding: invalid ciphertext");
               } else {
                  return var22;
               }
            }
         }
      }
   }
}
