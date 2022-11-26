package org.python.bouncycastle.pqc.crypto.gmss.util;

import org.python.bouncycastle.crypto.Digest;

public class WinternitzOTSignature {
   private Digest messDigestOTS;
   private int mdsize;
   private int keysize;
   private byte[][] privateKeyOTS;
   private int w;
   private GMSSRandom gmssRandom;
   private int messagesize;
   private int checksumsize;

   public WinternitzOTSignature(byte[] var1, Digest var2, int var3) {
      this.w = var3;
      this.messDigestOTS = var2;
      this.gmssRandom = new GMSSRandom(this.messDigestOTS);
      this.mdsize = this.messDigestOTS.getDigestSize();
      int var4 = this.mdsize << 3;
      this.messagesize = (int)Math.ceil((double)var4 / (double)var3);
      this.checksumsize = this.getLog((this.messagesize << var3) + 1);
      this.keysize = this.messagesize + (int)Math.ceil((double)this.checksumsize / (double)var3);
      this.privateKeyOTS = new byte[this.keysize][this.mdsize];
      byte[] var5 = new byte[this.mdsize];
      System.arraycopy(var1, 0, var5, 0, var5.length);

      for(int var6 = 0; var6 < this.keysize; ++var6) {
         this.privateKeyOTS[var6] = this.gmssRandom.nextSeed(var5);
      }

   }

   public byte[][] getPrivateKey() {
      return this.privateKeyOTS;
   }

   public byte[] getPublicKey() {
      byte[] var1 = new byte[this.keysize * this.mdsize];
      byte[] var2 = new byte[this.mdsize];
      int var3 = 1 << this.w;

      for(int var4 = 0; var4 < this.keysize; ++var4) {
         this.messDigestOTS.update(this.privateKeyOTS[var4], 0, this.privateKeyOTS[var4].length);
         var2 = new byte[this.messDigestOTS.getDigestSize()];
         this.messDigestOTS.doFinal(var2, 0);

         for(int var5 = 2; var5 < var3; ++var5) {
            this.messDigestOTS.update(var2, 0, var2.length);
            var2 = new byte[this.messDigestOTS.getDigestSize()];
            this.messDigestOTS.doFinal(var2, 0);
         }

         System.arraycopy(var2, 0, var1, this.mdsize * var4, this.mdsize);
      }

      this.messDigestOTS.update(var1, 0, var1.length);
      byte[] var6 = new byte[this.messDigestOTS.getDigestSize()];
      this.messDigestOTS.doFinal(var6, 0);
      return var6;
   }

   public byte[] getSignature(byte[] var1) {
      byte[] var2 = new byte[this.keysize * this.mdsize];
      byte[] var3 = new byte[this.mdsize];
      int var4 = 0;
      int var5 = 0;
      boolean var6 = false;
      this.messDigestOTS.update(var1, 0, var1.length);
      var3 = new byte[this.messDigestOTS.getDigestSize()];
      this.messDigestOTS.doFinal(var3, 0);
      int var7;
      int var8;
      byte[] var9;
      int var24;
      if (8 % this.w == 0) {
         var7 = 8 / this.w;
         var8 = (1 << this.w) - 1;
         var9 = new byte[this.mdsize];

         int var10;
         for(var10 = 0; var10 < var3.length; ++var10) {
            for(int var11 = 0; var11 < var7; ++var11) {
               var24 = var3[var10] & var8;
               var5 += var24;
               System.arraycopy(this.privateKeyOTS[var4], 0, var9, 0, this.mdsize);

               while(var24 > 0) {
                  this.messDigestOTS.update(var9, 0, var9.length);
                  var9 = new byte[this.messDigestOTS.getDigestSize()];
                  this.messDigestOTS.doFinal(var9, 0);
                  --var24;
               }

               System.arraycopy(var9, 0, var2, var4 * this.mdsize, this.mdsize);
               var3[var10] = (byte)(var3[var10] >>> this.w);
               ++var4;
            }
         }

         var5 = (this.messagesize << this.w) - var5;

         for(var10 = 0; var10 < this.checksumsize; var10 += this.w) {
            var24 = var5 & var8;
            System.arraycopy(this.privateKeyOTS[var4], 0, var9, 0, this.mdsize);

            while(var24 > 0) {
               this.messDigestOTS.update(var9, 0, var9.length);
               var9 = new byte[this.messDigestOTS.getDigestSize()];
               this.messDigestOTS.doFinal(var9, 0);
               --var24;
            }

            System.arraycopy(var9, 0, var2, var4 * this.mdsize, this.mdsize);
            var5 >>>= this.w;
            ++var4;
         }
      } else {
         long var14;
         int var16;
         if (this.w < 8) {
            var7 = this.mdsize / this.w;
            var8 = (1 << this.w) - 1;
            var9 = new byte[this.mdsize];
            int var12 = 0;

            int var13;
            for(var13 = 0; var13 < var7; ++var13) {
               var14 = 0L;

               for(var16 = 0; var16 < this.w; ++var16) {
                  var14 ^= (long)((var3[var12] & 255) << (var16 << 3));
                  ++var12;
               }

               for(var16 = 0; var16 < 8; ++var16) {
                  var24 = (int)(var14 & (long)var8);
                  var5 += var24;
                  System.arraycopy(this.privateKeyOTS[var4], 0, var9, 0, this.mdsize);

                  while(var24 > 0) {
                     this.messDigestOTS.update(var9, 0, var9.length);
                     var9 = new byte[this.messDigestOTS.getDigestSize()];
                     this.messDigestOTS.doFinal(var9, 0);
                     --var24;
                  }

                  System.arraycopy(var9, 0, var2, var4 * this.mdsize, this.mdsize);
                  var14 >>>= this.w;
                  ++var4;
               }
            }

            var7 = this.mdsize % this.w;
            var14 = 0L;

            for(var13 = 0; var13 < var7; ++var13) {
               var14 ^= (long)((var3[var12] & 255) << (var13 << 3));
               ++var12;
            }

            var7 <<= 3;

            for(var13 = 0; var13 < var7; var13 += this.w) {
               var24 = (int)(var14 & (long)var8);
               var5 += var24;
               System.arraycopy(this.privateKeyOTS[var4], 0, var9, 0, this.mdsize);

               while(var24 > 0) {
                  this.messDigestOTS.update(var9, 0, var9.length);
                  var9 = new byte[this.messDigestOTS.getDigestSize()];
                  this.messDigestOTS.doFinal(var9, 0);
                  --var24;
               }

               System.arraycopy(var9, 0, var2, var4 * this.mdsize, this.mdsize);
               var14 >>>= this.w;
               ++var4;
            }

            var5 = (this.messagesize << this.w) - var5;

            for(var13 = 0; var13 < this.checksumsize; var13 += this.w) {
               var24 = var5 & var8;
               System.arraycopy(this.privateKeyOTS[var4], 0, var9, 0, this.mdsize);

               while(var24 > 0) {
                  this.messDigestOTS.update(var9, 0, var9.length);
                  var9 = new byte[this.messDigestOTS.getDigestSize()];
                  this.messDigestOTS.doFinal(var9, 0);
                  --var24;
               }

               System.arraycopy(var9, 0, var2, var4 * this.mdsize, this.mdsize);
               var5 >>>= this.w;
               ++var4;
            }
         } else if (this.w < 57) {
            var7 = (this.mdsize << 3) - this.w;
            var8 = (1 << this.w) - 1;
            var9 = new byte[this.mdsize];

            int var17;
            int var18;
            int var20;
            int var21;
            long var22;
            for(var16 = 0; var16 <= var7; ++var4) {
               var17 = var16 >>> 3;
               var18 = var16 % 8;
               var16 += this.w;
               int var19 = var16 + 7 >>> 3;
               var14 = 0L;
               var20 = 0;

               for(var21 = var17; var21 < var19; ++var21) {
                  var14 ^= (long)((var3[var21] & 255) << (var20 << 3));
                  ++var20;
               }

               var14 >>>= var18;
               var22 = var14 & (long)var8;
               var5 = (int)((long)var5 + var22);
               System.arraycopy(this.privateKeyOTS[var4], 0, var9, 0, this.mdsize);

               while(var22 > 0L) {
                  this.messDigestOTS.update(var9, 0, var9.length);
                  var9 = new byte[this.messDigestOTS.getDigestSize()];
                  this.messDigestOTS.doFinal(var9, 0);
                  --var22;
               }

               System.arraycopy(var9, 0, var2, var4 * this.mdsize, this.mdsize);
            }

            var17 = var16 >>> 3;
            if (var17 < this.mdsize) {
               var18 = var16 % 8;
               var14 = 0L;
               var20 = 0;

               for(var21 = var17; var21 < this.mdsize; ++var21) {
                  var14 ^= (long)((var3[var21] & 255) << (var20 << 3));
                  ++var20;
               }

               var14 >>>= var18;
               var22 = var14 & (long)var8;
               var5 = (int)((long)var5 + var22);
               System.arraycopy(this.privateKeyOTS[var4], 0, var9, 0, this.mdsize);

               while(var22 > 0L) {
                  this.messDigestOTS.update(var9, 0, var9.length);
                  var9 = new byte[this.messDigestOTS.getDigestSize()];
                  this.messDigestOTS.doFinal(var9, 0);
                  --var22;
               }

               System.arraycopy(var9, 0, var2, var4 * this.mdsize, this.mdsize);
               ++var4;
            }

            var5 = (this.messagesize << this.w) - var5;

            for(var21 = 0; var21 < this.checksumsize; var21 += this.w) {
               var22 = (long)(var5 & var8);
               System.arraycopy(this.privateKeyOTS[var4], 0, var9, 0, this.mdsize);

               while(var22 > 0L) {
                  this.messDigestOTS.update(var9, 0, var9.length);
                  var9 = new byte[this.messDigestOTS.getDigestSize()];
                  this.messDigestOTS.doFinal(var9, 0);
                  --var22;
               }

               System.arraycopy(var9, 0, var2, var4 * this.mdsize, this.mdsize);
               var5 >>>= this.w;
               ++var4;
            }
         }
      }

      return var2;
   }

   public int getLog(int var1) {
      int var2 = 1;

      for(int var3 = 2; var3 < var1; ++var2) {
         var3 <<= 1;
      }

      return var2;
   }
}
