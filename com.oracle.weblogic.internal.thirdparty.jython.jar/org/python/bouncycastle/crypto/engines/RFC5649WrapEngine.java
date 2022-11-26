package org.python.bouncycastle.crypto.engines;

import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.Wrapper;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.Pack;

public class RFC5649WrapEngine implements Wrapper {
   private BlockCipher engine;
   private KeyParameter param;
   private boolean forWrapping;
   private byte[] highOrderIV = new byte[]{-90, 89, 89, -90};
   private byte[] preIV;
   private byte[] extractedAIV;

   public RFC5649WrapEngine(BlockCipher var1) {
      this.preIV = this.highOrderIV;
      this.extractedAIV = null;
      this.engine = var1;
   }

   public void init(boolean var1, CipherParameters var2) {
      this.forWrapping = var1;
      if (var2 instanceof ParametersWithRandom) {
         var2 = ((ParametersWithRandom)var2).getParameters();
      }

      if (var2 instanceof KeyParameter) {
         this.param = (KeyParameter)var2;
         this.preIV = this.highOrderIV;
      } else if (var2 instanceof ParametersWithIV) {
         this.preIV = ((ParametersWithIV)var2).getIV();
         this.param = (KeyParameter)((ParametersWithIV)var2).getParameters();
         if (this.preIV.length != 4) {
            throw new IllegalArgumentException("IV length not equal to 4");
         }
      }

   }

   public String getAlgorithmName() {
      return this.engine.getAlgorithmName();
   }

   private byte[] padPlaintext(byte[] var1) {
      int var2 = var1.length;
      int var3 = (8 - var2 % 8) % 8;
      byte[] var4 = new byte[var2 + var3];
      System.arraycopy(var1, 0, var4, 0, var2);
      if (var3 != 0) {
         byte[] var5 = new byte[var3];
         System.arraycopy(var5, 0, var4, var2, var3);
      }

      return var4;
   }

   public byte[] wrap(byte[] var1, int var2, int var3) {
      if (!this.forWrapping) {
         throw new IllegalStateException("not set for wrapping");
      } else {
         byte[] var4 = new byte[8];
         byte[] var5 = Pack.intToBigEndian(var3);
         System.arraycopy(this.preIV, 0, var4, 0, this.preIV.length);
         System.arraycopy(var5, 0, var4, this.preIV.length, var5.length);
         byte[] var6 = new byte[var3];
         System.arraycopy(var1, var2, var6, 0, var3);
         byte[] var7 = this.padPlaintext(var6);
         if (var7.length != 8) {
            RFC3394WrapEngine var10 = new RFC3394WrapEngine(this.engine);
            ParametersWithIV var11 = new ParametersWithIV(this.param, var4);
            var10.init(true, var11);
            return var10.wrap(var7, 0, var7.length);
         } else {
            byte[] var8 = new byte[var7.length + var4.length];
            System.arraycopy(var4, 0, var8, 0, var4.length);
            System.arraycopy(var7, 0, var8, var4.length, var7.length);
            this.engine.init(true, this.param);

            for(int var9 = 0; var9 < var8.length; var9 += this.engine.getBlockSize()) {
               this.engine.processBlock(var8, var9, var8, var9);
            }

            return var8;
         }
      }
   }

   public byte[] unwrap(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      if (this.forWrapping) {
         throw new IllegalStateException("not set for unwrapping");
      } else {
         int var4 = var3 / 8;
         if (var4 * 8 != var3) {
            throw new InvalidCipherTextException("unwrap data must be a multiple of 8 bytes");
         } else if (var4 == 1) {
            throw new InvalidCipherTextException("unwrap data must be at least 16 bytes");
         } else {
            byte[] var5 = new byte[var3];
            System.arraycopy(var1, var2, var5, 0, var3);
            byte[] var6 = new byte[var3];
            byte[] var8;
            if (var4 == 2) {
               this.engine.init(false, this.param);

               for(int var7 = 0; var7 < var5.length; var7 += this.engine.getBlockSize()) {
                  this.engine.processBlock(var5, var7, var6, var7);
               }

               this.extractedAIV = new byte[8];
               System.arraycopy(var6, 0, this.extractedAIV, 0, this.extractedAIV.length);
               var8 = new byte[var6.length - this.extractedAIV.length];
               System.arraycopy(var6, this.extractedAIV.length, var8, 0, var8.length);
            } else {
               var6 = this.rfc3394UnwrapNoIvCheck(var1, var2, var3);
               var8 = var6;
            }

            byte[] var18 = new byte[4];
            byte[] var9 = new byte[4];
            System.arraycopy(this.extractedAIV, 0, var18, 0, var18.length);
            System.arraycopy(this.extractedAIV, var18.length, var9, 0, var9.length);
            int var10 = Pack.bigEndianToInt(var9, 0);
            boolean var11 = true;
            if (!Arrays.constantTimeAreEqual(var18, this.preIV)) {
               var11 = false;
            }

            int var12 = var8.length;
            int var13 = var12 - 8;
            if (var10 <= var13) {
               var11 = false;
            }

            if (var10 > var12) {
               var11 = false;
            }

            int var14 = var12 - var10;
            if (var14 >= var8.length) {
               var11 = false;
               var14 = var8.length;
            }

            byte[] var15 = new byte[var14];
            byte[] var16 = new byte[var14];
            System.arraycopy(var8, var8.length - var14, var16, 0, var14);
            if (!Arrays.constantTimeAreEqual(var16, var15)) {
               var11 = false;
            }

            if (!var11) {
               throw new InvalidCipherTextException("checksum failed");
            } else {
               byte[] var17 = new byte[var10];
               System.arraycopy(var8, 0, var17, 0, var17.length);
               return var17;
            }
         }
      }
   }

   private byte[] rfc3394UnwrapNoIvCheck(byte[] var1, int var2, int var3) {
      byte[] var4 = new byte[8];
      byte[] var5 = new byte[var3 - var4.length];
      byte[] var6 = new byte[var4.length];
      byte[] var7 = new byte[8 + var4.length];
      System.arraycopy(var1, var2, var6, 0, var4.length);
      System.arraycopy(var1, var2 + var4.length, var5, 0, var3 - var4.length);
      this.engine.init(false, this.param);
      int var8 = var3 / 8;
      --var8;

      for(int var9 = 5; var9 >= 0; --var9) {
         for(int var10 = var8; var10 >= 1; --var10) {
            System.arraycopy(var6, 0, var7, 0, var4.length);
            System.arraycopy(var5, 8 * (var10 - 1), var7, var4.length, 8);
            int var11 = var8 * var9 + var10;

            for(int var12 = 1; var11 != 0; ++var12) {
               byte var13 = (byte)var11;
               var7[var4.length - var12] ^= var13;
               var11 >>>= 8;
            }

            this.engine.processBlock(var7, 0, var7, 0);
            System.arraycopy(var7, 0, var6, 0, 8);
            System.arraycopy(var7, 8, var5, 8 * (var10 - 1), 8);
         }
      }

      this.extractedAIV = var6;
      return var5;
   }
}
