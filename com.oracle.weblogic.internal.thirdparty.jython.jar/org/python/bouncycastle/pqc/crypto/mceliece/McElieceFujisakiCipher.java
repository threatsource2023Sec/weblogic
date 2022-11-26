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

public class McElieceFujisakiCipher implements MessageEncryptor {
   public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.2.1";
   private static final String DEFAULT_PRNG_NAME = "SHA1PRNG";
   private Digest messDigest;
   private SecureRandom sr;
   private int n;
   private int k;
   private int t;
   McElieceCCA2KeyParameters key;
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

   public int getKeySize(McElieceCCA2KeyParameters var1) throws IllegalArgumentException {
      if (var1 instanceof McElieceCCA2PublicKeyParameters) {
         return ((McElieceCCA2PublicKeyParameters)var1).getN();
      } else if (var1 instanceof McElieceCCA2PrivateKeyParameters) {
         return ((McElieceCCA2PrivateKeyParameters)var1).getN();
      } else {
         throw new IllegalArgumentException("unsupported type");
      }
   }

   private void initCipherEncrypt(McElieceCCA2PublicKeyParameters var1) {
      this.sr = this.sr != null ? this.sr : new SecureRandom();
      this.messDigest = Utils.getDigest(var1.getDigest());
      this.n = var1.getN();
      this.k = var1.getK();
      this.t = var1.getT();
   }

   public void initCipherDecrypt(McElieceCCA2PrivateKeyParameters var1) {
      this.messDigest = Utils.getDigest(var1.getDigest());
      this.n = var1.getN();
      this.t = var1.getT();
   }

   public byte[] messageEncrypt(byte[] var1) {
      if (!this.forEncryption) {
         throw new IllegalStateException("cipher initialised for decryption");
      } else {
         GF2Vector var2 = new GF2Vector(this.k, this.sr);
         byte[] var3 = var2.getEncoded();
         byte[] var4 = ByteUtils.concatenate(var3, var1);
         this.messDigest.update(var4, 0, var4.length);
         byte[] var5 = new byte[this.messDigest.getDigestSize()];
         this.messDigest.doFinal(var5, 0);
         GF2Vector var6 = Conversions.encode(this.n, this.t, var5);
         byte[] var7 = McElieceCCA2Primitives.encryptionPrimitive((McElieceCCA2PublicKeyParameters)this.key, var2, var6).getEncoded();
         DigestRandomGenerator var8 = new DigestRandomGenerator(new SHA1Digest());
         var8.addSeedMaterial(var3);
         byte[] var9 = new byte[var1.length];
         var8.nextBytes(var9);

         for(int var10 = 0; var10 < var1.length; ++var10) {
            var9[var10] ^= var1[var10];
         }

         return ByteUtils.concatenate(var7, var9);
      }
   }

   public byte[] messageDecrypt(byte[] var1) throws InvalidCipherTextException {
      if (this.forEncryption) {
         throw new IllegalStateException("cipher initialised for decryption");
      } else {
         int var2 = this.n + 7 >> 3;
         int var3 = var1.length - var2;
         byte[][] var4 = ByteUtils.split(var1, var2);
         byte[] var5 = var4[0];
         byte[] var6 = var4[1];
         GF2Vector var7 = GF2Vector.OS2VP(this.n, var5);
         GF2Vector[] var8 = McElieceCCA2Primitives.decryptionPrimitive((McElieceCCA2PrivateKeyParameters)this.key, var7);
         byte[] var9 = var8[0].getEncoded();
         GF2Vector var10 = var8[1];
         DigestRandomGenerator var11 = new DigestRandomGenerator(new SHA1Digest());
         var11.addSeedMaterial(var9);
         byte[] var12 = new byte[var3];
         var11.nextBytes(var12);

         for(int var13 = 0; var13 < var3; ++var13) {
            var12[var13] ^= var6[var13];
         }

         byte[] var15 = ByteUtils.concatenate(var9, var12);
         byte[] var14 = new byte[this.messDigest.getDigestSize()];
         this.messDigest.update(var15, 0, var15.length);
         this.messDigest.doFinal(var14, 0);
         var7 = Conversions.encode(this.n, this.t, var14);
         if (!var7.equals(var10)) {
            throw new InvalidCipherTextException("Bad Padding: invalid ciphertext");
         } else {
            return var12;
         }
      }
   }
}
