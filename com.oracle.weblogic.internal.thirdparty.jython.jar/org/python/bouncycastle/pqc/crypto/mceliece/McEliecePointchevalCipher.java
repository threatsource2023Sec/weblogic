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

public class McEliecePointchevalCipher implements MessageEncryptor {
   public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.2.2";
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

   protected int decryptOutputSize(int var1) {
      return 0;
   }

   protected int encryptOutputSize(int var1) {
      return 0;
   }

   public void initCipherEncrypt(McElieceCCA2PublicKeyParameters var1) {
      this.sr = this.sr != null ? this.sr : new SecureRandom();
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
         int var2 = this.k >> 3;
         byte[] var3 = new byte[var2];
         this.sr.nextBytes(var3);
         GF2Vector var4 = new GF2Vector(this.k, this.sr);
         byte[] var5 = var4.getEncoded();
         byte[] var6 = ByteUtils.concatenate(var1, var3);
         this.messDigest.update(var6, 0, var6.length);
         byte[] var7 = new byte[this.messDigest.getDigestSize()];
         this.messDigest.doFinal(var7, 0);
         GF2Vector var8 = Conversions.encode(this.n, this.t, var7);
         byte[] var9 = McElieceCCA2Primitives.encryptionPrimitive((McElieceCCA2PublicKeyParameters)this.key, var4, var8).getEncoded();
         DigestRandomGenerator var10 = new DigestRandomGenerator(new SHA1Digest());
         var10.addSeedMaterial(var5);
         byte[] var11 = new byte[var1.length + var2];
         var10.nextBytes(var11);

         int var12;
         for(var12 = 0; var12 < var1.length; ++var12) {
            var11[var12] ^= var1[var12];
         }

         for(var12 = 0; var12 < var2; ++var12) {
            var11[var1.length + var12] ^= var3[var12];
         }

         return ByteUtils.concatenate(var9, var11);
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

         this.messDigest.update(var12, 0, var12.length);
         byte[] var16 = new byte[this.messDigest.getDigestSize()];
         this.messDigest.doFinal(var16, 0);
         var7 = Conversions.encode(this.n, this.t, var16);
         if (!var7.equals(var10)) {
            throw new InvalidCipherTextException("Bad Padding: Invalid ciphertext.");
         } else {
            int var14 = this.k >> 3;
            byte[][] var15 = ByteUtils.split(var12, var3 - var14);
            return var15[0];
         }
      }
   }
}
