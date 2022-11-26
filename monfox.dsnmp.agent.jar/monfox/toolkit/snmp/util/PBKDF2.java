package monfox.toolkit.snmp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PBKDF2 {
   MessageDigest a;

   public PBKDF2(String var1) throws NoSuchAlgorithmException {
      this.a = MessageDigest.getInstance(var1);
   }

   public byte[] passwd2key(byte[] var1, byte[] var2, int var3, int var4) {
      int var15 = WorkItem.d;
      synchronized(this) {
         int var6 = this.a.getDigestLength();
         int var7 = var4 / var6;
         int var8 = var4 % var6;
         if (var8 > 0) {
            ++var7;
         }

         int var9 = 0;
         byte[] var10 = new byte[var4];
         int var11 = 1;

         label46:
         while(true) {
            int var10000 = var11;
            int var10001 = var7;

            byte[] var17;
            label43:
            while(var10000 <= var10001) {
               var17 = this.a(var1, var2, var3, var11);
               if (var15 != 0) {
                  return var17;
               }

               byte[] var12 = var17;
               int var13 = 0;

               while(var13 < var12.length) {
                  var10000 = var9;
                  var10001 = var10.length;
                  if (var15 != 0) {
                     continue label43;
                  }

                  if (var9 >= var10001) {
                     break;
                  }

                  var10[var9++] = var12[var13++];
                  if (var15 != 0) {
                     break;
                  }
               }

               ++var11;
               if (var15 == 0) {
                  continue label46;
               }
               break;
            }

            var17 = var10;
            return var17;
         }
      }
   }

   private byte[] a(byte[] var1, byte[] var2, int var3, int var4) {
      int var10 = WorkItem.d;
      byte[] var5 = new byte[this.a.getDigestLength()];
      int var6 = 0;

      byte[] var10000;
      while(true) {
         if (var6 < var5.length) {
            var10000 = var5;
            if (var10 != 0) {
               break;
            }

            var5[var6] = 0;
            ++var6;
            if (var10 == 0) {
               continue;
            }
         }

         var10000 = new byte[var2.length + 4];
         break;
      }

      byte[] var11 = var10000;
      System.arraycopy(var2, 0, var11, 0, var2.length);
      int var7 = var2.length;
      var11[var7++] = (byte)(var4 >>> 24);
      var11[var7++] = (byte)(var4 >>> 16);
      var11[var7++] = (byte)(var4 >>> 8);
      var11[var7++] = (byte)var4;
      int var8 = 0;

      label35:
      while(true) {
         if (var8 >= var3) {
            var10000 = var5;
            break;
         }

         var10000 = this.a(var1, var11);
         if (var10 != 0) {
            break;
         }

         var11 = var10000;
         int var9 = 0;

         while(var9 < var5.length) {
            var5[var9] ^= var11[var9];
            ++var9;
            if (var10 != 0) {
               continue label35;
            }

            if (var10 != 0) {
               break;
            }
         }

         ++var8;
         if (var10 != 0) {
         }
      }

      return var10000;
   }

   private byte[] a(byte[] var1, byte[] var2) {
      int var8 = WorkItem.d;
      if (var1.length > 64) {
         this.a.update(var1);
         var1 = this.a.digest();
      }

      byte[] var3 = new byte[64];
      byte[] var4 = new byte[64];
      int var5 = 0;

      label39: {
         label38:
         while(true) {
            int var10000;
            byte var10001;
            if (var5 < var1.length) {
               var10000 = var5;
               var10001 = 64;
               if (var8 == 0) {
                  if (var5 < 64) {
                     var3[var5] = (byte)(var1[var5] ^ 54);
                     var4[var5] = (byte)(var1[var5] ^ 92);
                     ++var5;
                     if (var8 == 0) {
                        continue;
                     }
                  }

                  var10000 = var5;
                  var10001 = 64;
               }
            } else {
               var10000 = var5;
               var10001 = 64;
            }

            while(true) {
               if (var10000 >= var10001) {
                  break label38;
               }

               var3[var5] = 54;
               var4[var5] = 92;
               ++var5;
               if (var8 != 0) {
                  break label39;
               }

               if (var8 != 0) {
                  break label38;
               }

               var10000 = var5;
               var10001 = 64;
            }
         }

         this.a.reset();
         this.a.update(var3);
         this.a.update(var2, 0, var2.length);
      }

      byte[] var6 = this.a.digest();
      this.a.reset();
      this.a.update(var4);
      this.a.update(var6);
      byte[] var7 = this.a.digest();
      return var7;
   }
}
