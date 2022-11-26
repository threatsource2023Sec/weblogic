package org.python.bouncycastle.pqc.crypto.gmss.util;

import org.python.bouncycastle.crypto.Digest;

public class WinternitzOTSVerify {
   private Digest messDigestOTS;
   private int w;

   public WinternitzOTSVerify(Digest var1, int var2) {
      this.w = var2;
      this.messDigestOTS = var1;
   }

   public int getSignatureLength() {
      int var1 = this.messDigestOTS.getDigestSize();
      int var2 = ((var1 << 3) + (this.w - 1)) / this.w;
      int var3 = this.getLog((var2 << this.w) + 1);
      var2 += (var3 + this.w - 1) / this.w;
      return var1 * var2;
   }

   public byte[] Verify(byte[] var1, byte[] var2) {
      int var3 = this.messDigestOTS.getDigestSize();
      byte[] var4 = new byte[var3];
      this.messDigestOTS.update(var1, 0, var1.length);
      var4 = new byte[this.messDigestOTS.getDigestSize()];
      this.messDigestOTS.doFinal(var4, 0);
      int var5 = ((var3 << 3) + (this.w - 1)) / this.w;
      int var6 = this.getLog((var5 << this.w) + 1);
      int var7 = var5 + (var6 + this.w - 1) / this.w;
      int var8 = var3 * var7;
      if (var8 != var2.length) {
         return null;
      } else {
         byte[] var9 = new byte[var8];
         int var10 = 0;
         int var11 = 0;
         int var12;
         int var13;
         byte[] var14;
         int var17;
         if (8 % this.w == 0) {
            var12 = 8 / this.w;
            var13 = (1 << this.w) - 1;
            var14 = new byte[var3];

            int var15;
            for(var15 = 0; var15 < var4.length; ++var15) {
               for(int var16 = 0; var16 < var12; ++var16) {
                  var17 = var4[var15] & var13;
                  var10 += var17;
                  System.arraycopy(var2, var11 * var3, var14, 0, var3);

                  while(var17 < var13) {
                     this.messDigestOTS.update(var14, 0, var14.length);
                     var14 = new byte[this.messDigestOTS.getDigestSize()];
                     this.messDigestOTS.doFinal(var14, 0);
                     ++var17;
                  }

                  System.arraycopy(var14, 0, var9, var11 * var3, var3);
                  var4[var15] = (byte)(var4[var15] >>> this.w);
                  ++var11;
               }
            }

            var10 = (var5 << this.w) - var10;

            for(var15 = 0; var15 < var6; var15 += this.w) {
               var17 = var10 & var13;
               System.arraycopy(var2, var11 * var3, var14, 0, var3);

               while(var17 < var13) {
                  this.messDigestOTS.update(var14, 0, var14.length);
                  var14 = new byte[this.messDigestOTS.getDigestSize()];
                  this.messDigestOTS.doFinal(var14, 0);
                  ++var17;
               }

               System.arraycopy(var14, 0, var9, var11 * var3, var3);
               var10 >>>= this.w;
               ++var11;
            }
         } else {
            long var20;
            int var22;
            if (this.w < 8) {
               var12 = var3 / this.w;
               var13 = (1 << this.w) - 1;
               var14 = new byte[var3];
               int var18 = 0;

               int var19;
               for(var19 = 0; var19 < var12; ++var19) {
                  var20 = 0L;

                  for(var22 = 0; var22 < this.w; ++var22) {
                     var20 ^= (long)((var4[var18] & 255) << (var22 << 3));
                     ++var18;
                  }

                  for(var22 = 0; var22 < 8; ++var22) {
                     var17 = (int)(var20 & (long)var13);
                     var10 += var17;
                     System.arraycopy(var2, var11 * var3, var14, 0, var3);

                     while(var17 < var13) {
                        this.messDigestOTS.update(var14, 0, var14.length);
                        var14 = new byte[this.messDigestOTS.getDigestSize()];
                        this.messDigestOTS.doFinal(var14, 0);
                        ++var17;
                     }

                     System.arraycopy(var14, 0, var9, var11 * var3, var3);
                     var20 >>>= this.w;
                     ++var11;
                  }
               }

               var12 = var3 % this.w;
               var20 = 0L;

               for(var19 = 0; var19 < var12; ++var19) {
                  var20 ^= (long)((var4[var18] & 255) << (var19 << 3));
                  ++var18;
               }

               var12 <<= 3;

               for(var19 = 0; var19 < var12; var19 += this.w) {
                  var17 = (int)(var20 & (long)var13);
                  var10 += var17;
                  System.arraycopy(var2, var11 * var3, var14, 0, var3);

                  while(var17 < var13) {
                     this.messDigestOTS.update(var14, 0, var14.length);
                     var14 = new byte[this.messDigestOTS.getDigestSize()];
                     this.messDigestOTS.doFinal(var14, 0);
                     ++var17;
                  }

                  System.arraycopy(var14, 0, var9, var11 * var3, var3);
                  var20 >>>= this.w;
                  ++var11;
               }

               var10 = (var5 << this.w) - var10;

               for(var19 = 0; var19 < var6; var19 += this.w) {
                  var17 = var10 & var13;
                  System.arraycopy(var2, var11 * var3, var14, 0, var3);

                  while(var17 < var13) {
                     this.messDigestOTS.update(var14, 0, var14.length);
                     var14 = new byte[this.messDigestOTS.getDigestSize()];
                     this.messDigestOTS.doFinal(var14, 0);
                     ++var17;
                  }

                  System.arraycopy(var14, 0, var9, var11 * var3, var3);
                  var10 >>>= this.w;
                  ++var11;
               }
            } else if (this.w < 57) {
               var12 = (var3 << 3) - this.w;
               var13 = (1 << this.w) - 1;
               var14 = new byte[var3];

               int var23;
               int var24;
               int var26;
               int var27;
               long var28;
               for(var22 = 0; var22 <= var12; ++var11) {
                  var23 = var22 >>> 3;
                  var24 = var22 % 8;
                  var22 += this.w;
                  int var25 = var22 + 7 >>> 3;
                  var20 = 0L;
                  var26 = 0;

                  for(var27 = var23; var27 < var25; ++var27) {
                     var20 ^= (long)((var4[var27] & 255) << (var26 << 3));
                     ++var26;
                  }

                  var20 >>>= var24;
                  var28 = var20 & (long)var13;
                  var10 = (int)((long)var10 + var28);
                  System.arraycopy(var2, var11 * var3, var14, 0, var3);

                  while(var28 < (long)var13) {
                     this.messDigestOTS.update(var14, 0, var14.length);
                     var14 = new byte[this.messDigestOTS.getDigestSize()];
                     this.messDigestOTS.doFinal(var14, 0);
                     ++var28;
                  }

                  System.arraycopy(var14, 0, var9, var11 * var3, var3);
               }

               var23 = var22 >>> 3;
               if (var23 < var3) {
                  var24 = var22 % 8;
                  var20 = 0L;
                  var26 = 0;

                  for(var27 = var23; var27 < var3; ++var27) {
                     var20 ^= (long)((var4[var27] & 255) << (var26 << 3));
                     ++var26;
                  }

                  var20 >>>= var24;
                  var28 = var20 & (long)var13;
                  var10 = (int)((long)var10 + var28);
                  System.arraycopy(var2, var11 * var3, var14, 0, var3);

                  while(var28 < (long)var13) {
                     this.messDigestOTS.update(var14, 0, var14.length);
                     var14 = new byte[this.messDigestOTS.getDigestSize()];
                     this.messDigestOTS.doFinal(var14, 0);
                     ++var28;
                  }

                  System.arraycopy(var14, 0, var9, var11 * var3, var3);
                  ++var11;
               }

               var10 = (var5 << this.w) - var10;

               for(var27 = 0; var27 < var6; var27 += this.w) {
                  var28 = (long)(var10 & var13);
                  System.arraycopy(var2, var11 * var3, var14, 0, var3);

                  while(var28 < (long)var13) {
                     this.messDigestOTS.update(var14, 0, var14.length);
                     var14 = new byte[this.messDigestOTS.getDigestSize()];
                     this.messDigestOTS.doFinal(var14, 0);
                     ++var28;
                  }

                  System.arraycopy(var14, 0, var9, var11 * var3, var3);
                  var10 >>>= this.w;
                  ++var11;
               }
            }
         }

         byte[] var30 = new byte[var3];
         this.messDigestOTS.update(var9, 0, var9.length);
         var30 = new byte[this.messDigestOTS.getDigestSize()];
         this.messDigestOTS.doFinal(var30, 0);
         return var30;
      }
   }

   public int getLog(int var1) {
      int var2 = 1;

      for(int var3 = 2; var3 < var1; ++var2) {
         var3 <<= 1;
      }

      return var2;
   }
}
