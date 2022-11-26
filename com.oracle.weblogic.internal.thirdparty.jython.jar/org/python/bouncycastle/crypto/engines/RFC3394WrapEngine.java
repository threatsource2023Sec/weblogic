package org.python.bouncycastle.crypto.engines;

import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.Wrapper;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.util.Arrays;

public class RFC3394WrapEngine implements Wrapper {
   private BlockCipher engine;
   private boolean wrapCipherMode;
   private KeyParameter param;
   private boolean forWrapping;
   private byte[] iv;

   public RFC3394WrapEngine(BlockCipher var1) {
      this(var1, false);
   }

   public RFC3394WrapEngine(BlockCipher var1, boolean var2) {
      this.iv = new byte[]{-90, -90, -90, -90, -90, -90, -90, -90};
      this.engine = var1;
      this.wrapCipherMode = !var2;
   }

   public void init(boolean var1, CipherParameters var2) {
      this.forWrapping = var1;
      if (var2 instanceof ParametersWithRandom) {
         var2 = ((ParametersWithRandom)var2).getParameters();
      }

      if (var2 instanceof KeyParameter) {
         this.param = (KeyParameter)var2;
      } else if (var2 instanceof ParametersWithIV) {
         this.iv = ((ParametersWithIV)var2).getIV();
         this.param = (KeyParameter)((ParametersWithIV)var2).getParameters();
         if (this.iv.length != 8) {
            throw new IllegalArgumentException("IV not equal to 8");
         }
      }

   }

   public String getAlgorithmName() {
      return this.engine.getAlgorithmName();
   }

   public byte[] wrap(byte[] var1, int var2, int var3) {
      if (!this.forWrapping) {
         throw new IllegalStateException("not set for wrapping");
      } else {
         int var4 = var3 / 8;
         if (var4 * 8 != var3) {
            throw new DataLengthException("wrap data must be a multiple of 8 bytes");
         } else {
            byte[] var5 = new byte[var3 + this.iv.length];
            byte[] var6 = new byte[8 + this.iv.length];
            System.arraycopy(this.iv, 0, var5, 0, this.iv.length);
            System.arraycopy(var1, var2, var5, this.iv.length, var3);
            this.engine.init(this.wrapCipherMode, this.param);

            for(int var7 = 0; var7 != 6; ++var7) {
               for(int var8 = 1; var8 <= var4; ++var8) {
                  System.arraycopy(var5, 0, var6, 0, this.iv.length);
                  System.arraycopy(var5, 8 * var8, var6, this.iv.length, 8);
                  this.engine.processBlock(var6, 0, var6, 0);
                  int var9 = var4 * var7 + var8;

                  for(int var10 = 1; var9 != 0; ++var10) {
                     byte var11 = (byte)var9;
                     int var10001 = this.iv.length - var10;
                     var6[var10001] ^= var11;
                     var9 >>>= 8;
                  }

                  System.arraycopy(var6, 0, var5, 0, 8);
                  System.arraycopy(var6, 8, var5, 8 * var8, 8);
               }
            }

            return var5;
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
         } else {
            byte[] var5 = new byte[var3 - this.iv.length];
            byte[] var6 = new byte[this.iv.length];
            byte[] var7 = new byte[8 + this.iv.length];
            System.arraycopy(var1, var2, var6, 0, this.iv.length);
            System.arraycopy(var1, var2 + this.iv.length, var5, 0, var3 - this.iv.length);
            this.engine.init(!this.wrapCipherMode, this.param);
            --var4;

            for(int var8 = 5; var8 >= 0; --var8) {
               for(int var9 = var4; var9 >= 1; --var9) {
                  System.arraycopy(var6, 0, var7, 0, this.iv.length);
                  System.arraycopy(var5, 8 * (var9 - 1), var7, this.iv.length, 8);
                  int var10 = var4 * var8 + var9;

                  for(int var11 = 1; var10 != 0; ++var11) {
                     byte var12 = (byte)var10;
                     int var10001 = this.iv.length - var11;
                     var7[var10001] ^= var12;
                     var10 >>>= 8;
                  }

                  this.engine.processBlock(var7, 0, var7, 0);
                  System.arraycopy(var7, 0, var6, 0, 8);
                  System.arraycopy(var7, 8, var5, 8 * (var9 - 1), 8);
               }
            }

            if (!Arrays.constantTimeAreEqual(var6, this.iv)) {
               throw new InvalidCipherTextException("checksum failed");
            } else {
               return var5;
            }
         }
      }
   }
}
