package org.python.bouncycastle.crypto.engines;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.Wrapper;
import org.python.bouncycastle.crypto.modes.CBCBlockCipher;
import org.python.bouncycastle.crypto.params.ParametersWithIV;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.crypto.util.DigestFactory;
import org.python.bouncycastle.util.Arrays;

public class RC2WrapEngine implements Wrapper {
   private CBCBlockCipher engine;
   private CipherParameters param;
   private ParametersWithIV paramPlusIV;
   private byte[] iv;
   private boolean forWrapping;
   private SecureRandom sr;
   private static final byte[] IV2 = new byte[]{74, -35, -94, 44, 121, -24, 33, 5};
   Digest sha1 = DigestFactory.createSHA1();
   byte[] digest = new byte[20];

   public void init(boolean var1, CipherParameters var2) {
      this.forWrapping = var1;
      this.engine = new CBCBlockCipher(new RC2Engine());
      if (var2 instanceof ParametersWithRandom) {
         ParametersWithRandom var3 = (ParametersWithRandom)var2;
         this.sr = var3.getRandom();
         var2 = var3.getParameters();
      } else {
         this.sr = new SecureRandom();
      }

      if (var2 instanceof ParametersWithIV) {
         this.paramPlusIV = (ParametersWithIV)var2;
         this.iv = this.paramPlusIV.getIV();
         this.param = this.paramPlusIV.getParameters();
         if (!this.forWrapping) {
            throw new IllegalArgumentException("You should not supply an IV for unwrapping");
         }

         if (this.iv == null || this.iv.length != 8) {
            throw new IllegalArgumentException("IV is not 8 octets");
         }
      } else {
         this.param = var2;
         if (this.forWrapping) {
            this.iv = new byte[8];
            this.sr.nextBytes(this.iv);
            this.paramPlusIV = new ParametersWithIV(this.param, this.iv);
         }
      }

   }

   public String getAlgorithmName() {
      return "RC2";
   }

   public byte[] wrap(byte[] var1, int var2, int var3) {
      if (!this.forWrapping) {
         throw new IllegalStateException("Not initialized for wrapping");
      } else {
         int var4 = var3 + 1;
         if (var4 % 8 != 0) {
            var4 += 8 - var4 % 8;
         }

         byte[] var5 = new byte[var4];
         var5[0] = (byte)var3;
         System.arraycopy(var1, var2, var5, 1, var3);
         byte[] var6 = new byte[var5.length - var3 - 1];
         if (var6.length > 0) {
            this.sr.nextBytes(var6);
            System.arraycopy(var6, 0, var5, var3 + 1, var6.length);
         }

         byte[] var7 = this.calculateCMSKeyChecksum(var5);
         byte[] var8 = new byte[var5.length + var7.length];
         System.arraycopy(var5, 0, var8, 0, var5.length);
         System.arraycopy(var7, 0, var8, var5.length, var7.length);
         byte[] var9 = new byte[var8.length];
         System.arraycopy(var8, 0, var9, 0, var8.length);
         int var10 = var8.length / this.engine.getBlockSize();
         int var11 = var8.length % this.engine.getBlockSize();
         if (var11 != 0) {
            throw new IllegalStateException("Not multiple of block length");
         } else {
            this.engine.init(true, this.paramPlusIV);

            for(int var12 = 0; var12 < var10; ++var12) {
               int var13 = var12 * this.engine.getBlockSize();
               this.engine.processBlock(var9, var13, var9, var13);
            }

            byte[] var17 = new byte[this.iv.length + var9.length];
            System.arraycopy(this.iv, 0, var17, 0, this.iv.length);
            System.arraycopy(var9, 0, var17, this.iv.length, var9.length);
            byte[] var18 = new byte[var17.length];

            for(int var14 = 0; var14 < var17.length; ++var14) {
               var18[var14] = var17[var17.length - (var14 + 1)];
            }

            ParametersWithIV var19 = new ParametersWithIV(this.param, IV2);
            this.engine.init(true, var19);

            for(int var15 = 0; var15 < var10 + 1; ++var15) {
               int var16 = var15 * this.engine.getBlockSize();
               this.engine.processBlock(var18, var16, var18, var16);
            }

            return var18;
         }
      }
   }

   public byte[] unwrap(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      if (this.forWrapping) {
         throw new IllegalStateException("Not set for unwrapping");
      } else if (var1 == null) {
         throw new InvalidCipherTextException("Null pointer as ciphertext");
      } else if (var3 % this.engine.getBlockSize() != 0) {
         throw new InvalidCipherTextException("Ciphertext not multiple of " + this.engine.getBlockSize());
      } else {
         ParametersWithIV var4 = new ParametersWithIV(this.param, IV2);
         this.engine.init(false, var4);
         byte[] var5 = new byte[var3];
         System.arraycopy(var1, var2, var5, 0, var3);

         int var7;
         for(int var6 = 0; var6 < var5.length / this.engine.getBlockSize(); ++var6) {
            var7 = var6 * this.engine.getBlockSize();
            this.engine.processBlock(var5, var7, var5, var7);
         }

         byte[] var12 = new byte[var5.length];

         for(var7 = 0; var7 < var5.length; ++var7) {
            var12[var7] = var5[var5.length - (var7 + 1)];
         }

         this.iv = new byte[8];
         byte[] var13 = new byte[var12.length - 8];
         System.arraycopy(var12, 0, this.iv, 0, 8);
         System.arraycopy(var12, 8, var13, 0, var12.length - 8);
         this.paramPlusIV = new ParametersWithIV(this.param, this.iv);
         this.engine.init(false, this.paramPlusIV);
         byte[] var8 = new byte[var13.length];
         System.arraycopy(var13, 0, var8, 0, var13.length);

         for(int var9 = 0; var9 < var8.length / this.engine.getBlockSize(); ++var9) {
            int var10 = var9 * this.engine.getBlockSize();
            this.engine.processBlock(var8, var10, var8, var10);
         }

         byte[] var14 = new byte[var8.length - 8];
         byte[] var15 = new byte[8];
         System.arraycopy(var8, 0, var14, 0, var8.length - 8);
         System.arraycopy(var8, var8.length - 8, var15, 0, 8);
         if (!this.checkCMSKeyChecksum(var14, var15)) {
            throw new InvalidCipherTextException("Checksum inside ciphertext is corrupted");
         } else if (var14.length - ((var14[0] & 255) + 1) > 7) {
            throw new InvalidCipherTextException("too many pad bytes (" + (var14.length - ((var14[0] & 255) + 1)) + ")");
         } else {
            byte[] var11 = new byte[var14[0]];
            System.arraycopy(var14, 1, var11, 0, var11.length);
            return var11;
         }
      }
   }

   private byte[] calculateCMSKeyChecksum(byte[] var1) {
      byte[] var2 = new byte[8];
      this.sha1.update(var1, 0, var1.length);
      this.sha1.doFinal(this.digest, 0);
      System.arraycopy(this.digest, 0, var2, 0, 8);
      return var2;
   }

   private boolean checkCMSKeyChecksum(byte[] var1, byte[] var2) {
      return Arrays.constantTimeAreEqual(this.calculateCMSKeyChecksum(var1), var2);
   }
}
