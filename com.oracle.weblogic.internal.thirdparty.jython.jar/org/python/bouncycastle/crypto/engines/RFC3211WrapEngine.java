package org.python.bouncycastle.crypto.engines;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.Wrapper;
import org.python.bouncycastle.crypto.modes.CBCBlockCipher;
import org.python.bouncycastle.crypto.params.ParametersWithIV;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;

public class RFC3211WrapEngine implements Wrapper {
   private CBCBlockCipher engine;
   private ParametersWithIV param;
   private boolean forWrapping;
   private SecureRandom rand;

   public RFC3211WrapEngine(BlockCipher var1) {
      this.engine = new CBCBlockCipher(var1);
   }

   public void init(boolean var1, CipherParameters var2) {
      this.forWrapping = var1;
      if (var2 instanceof ParametersWithRandom) {
         ParametersWithRandom var3 = (ParametersWithRandom)var2;
         this.rand = var3.getRandom();
         this.param = (ParametersWithIV)var3.getParameters();
      } else {
         if (var1) {
            this.rand = new SecureRandom();
         }

         this.param = (ParametersWithIV)var2;
      }

   }

   public String getAlgorithmName() {
      return this.engine.getUnderlyingCipher().getAlgorithmName() + "/RFC3211Wrap";
   }

   public byte[] wrap(byte[] var1, int var2, int var3) {
      if (!this.forWrapping) {
         throw new IllegalStateException("not set for wrapping");
      } else {
         this.engine.init(true, this.param);
         int var4 = this.engine.getBlockSize();
         byte[] var5;
         if (var3 + 4 < var4 * 2) {
            var5 = new byte[var4 * 2];
         } else {
            var5 = new byte[(var3 + 4) % var4 == 0 ? var3 + 4 : ((var3 + 4) / var4 + 1) * var4];
         }

         var5[0] = (byte)var3;
         var5[1] = (byte)(~var1[var2]);
         var5[2] = (byte)(~var1[var2 + 1]);
         var5[3] = (byte)(~var1[var2 + 2]);
         System.arraycopy(var1, var2, var5, 4, var3);
         byte[] var6 = new byte[var5.length - (var3 + 4)];
         this.rand.nextBytes(var6);
         System.arraycopy(var6, 0, var5, var3 + 4, var6.length);

         int var7;
         for(var7 = 0; var7 < var5.length; var7 += var4) {
            this.engine.processBlock(var5, var7, var5, var7);
         }

         for(var7 = 0; var7 < var5.length; var7 += var4) {
            this.engine.processBlock(var5, var7, var5, var7);
         }

         return var5;
      }
   }

   public byte[] unwrap(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      if (this.forWrapping) {
         throw new IllegalStateException("not set for unwrapping");
      } else {
         int var4 = this.engine.getBlockSize();
         if (var3 < 2 * var4) {
            throw new InvalidCipherTextException("input too short");
         } else {
            byte[] var5 = new byte[var3];
            byte[] var6 = new byte[var4];
            System.arraycopy(var1, var2, var5, 0, var3);
            System.arraycopy(var1, var2, var6, 0, var6.length);
            this.engine.init(false, new ParametersWithIV(this.param.getParameters(), var6));

            int var7;
            for(var7 = var4; var7 < var5.length; var7 += var4) {
               this.engine.processBlock(var5, var7, var5, var7);
            }

            System.arraycopy(var5, var5.length - var6.length, var6, 0, var6.length);
            this.engine.init(false, new ParametersWithIV(this.param.getParameters(), var6));
            this.engine.processBlock(var5, 0, var5, 0);
            this.engine.init(false, this.param);

            for(var7 = 0; var7 < var5.length; var7 += var4) {
               this.engine.processBlock(var5, var7, var5, var7);
            }

            if ((var5[0] & 255) > var5.length - 4) {
               throw new InvalidCipherTextException("wrapped key corrupted");
            } else {
               byte[] var11 = new byte[var5[0] & 255];
               System.arraycopy(var5, 4, var11, 0, var5[0]);
               int var8 = 0;

               for(int var9 = 0; var9 != 3; ++var9) {
                  byte var10 = (byte)(~var5[1 + var9]);
                  var8 |= var10 ^ var11[var9];
               }

               if (var8 != 0) {
                  throw new InvalidCipherTextException("wrapped key fails checksum");
               } else {
                  return var11;
               }
            }
         }
      }
   }
}
