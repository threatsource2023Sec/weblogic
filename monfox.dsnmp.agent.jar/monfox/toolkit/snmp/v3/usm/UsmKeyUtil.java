package monfox.toolkit.snmp.v3.usm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import monfox.log.Logger;

public class UsmKeyUtil {
   private static Logger a = Logger.getInstance(a("l\f\u001eLI"), a("~l\u007fTJe"), a("},=J|Q\n$hu"));

   public static byte[] generateKey(byte[] var0, int var1) throws NoSuchAlgorithmException {
      boolean var8 = USMLocalizedUserData.k;
      int var2 = var0.length;
      int var3 = 1048576;
      int var4 = var3 / var2;
      int var5 = var3 % var2;
      MessageDigest var6 = MessageDigest.getInstance(var1 == 0 ? a("e\u001be") : a("{\u0017\u0011"));
      int var7 = 0;

      while(true) {
         if (var7 < var4) {
            var6.update(var0);
            ++var7;
            if (var8) {
               break;
            }

            if (!var8) {
               continue;
            }
         }

         var6.update(var0, 0, var5);
         break;
      }

      byte[] var9 = var6.digest();
      return var9;
   }

   public static byte[] localizeAuthKey(byte[] var0, byte[] var1, int var2) throws NoSuchAlgorithmException {
      return a(var0, var1, var2, -1);
   }

   public static byte[] localizePrivKey(byte[] var0, byte[] var1, int var2, int var3) throws NoSuchAlgorithmException {
      return a(var0, var1, var2, var3);
   }

   private static byte[] a(byte[] var0, byte[] var1, int var2, int var3) throws NoSuchAlgorithmException {
      boolean var13 = USMLocalizedUserData.k;
      MessageDigest var4 = MessageDigest.getInstance(var2 == 0 ? a("e\u001be") : a("{\u0017\u0011"));
      var4.reset();
      var4.update(var0);
      var4.update(var1);
      var4.update(var0);
      byte[] var5 = var4.digest();
      byte[] var6 = var5;
      byte[] var8;
      byte[] var10;
      if (var3 == 14832) {
         MessageDigest var14 = MessageDigest.getInstance(var2 == 0 ? a("e\u001be") : a("{\u0017\u0011"));
         var8 = generateKey(var5, var2);
         var14.reset();
         var14.update(var8);
         var14.update(var1);
         var14.update(var8);
         byte[] var15 = var14.digest();
         var10 = new byte[var5.length + var15.length];
         System.arraycopy(var5, 0, var10, 0, var5.length);
         System.arraycopy(var15, 0, var10, var5.length, var15.length);
         var6 = new byte[32];
         System.arraycopy(var10, 0, var6, 0, 32);
         return var6;
      } else {
         byte var7 = -1;
         switch (var3) {
            case 2:
               var7 = 16;
               if (!var13) {
                  break;
               }
            case 14832:
               var7 = 32;
               if (!var13) {
                  break;
               }
            case 4:
               var7 = 16;
               if (!var13) {
                  break;
               }
            case 5:
               var7 = 24;
               if (!var13) {
                  break;
               }
            case 6:
               var7 = 32;
         }

         if (var7 > var5.length) {
            var8 = new byte[var7];
            System.arraycopy(var5, 0, var8, 0, var5.length);
            int var9 = var5.length;

            while(true) {
               label53:
               while(var9 < var8.length) {
                  var4.reset();
                  var4.update(var8, 0, var9);
                  var10 = var4.digest();
                  int var11 = var8.length - var9;
                  if (var13) {
                     return var6;
                  }

                  if (var11 > var6.length) {
                     var11 = var6.length;
                  }

                  int var12 = 0;

                  while(var12 < var11) {
                     var8[var12 + var9] = var10[var12];
                     ++var12;
                     if (var13 && var13) {
                        continue label53;
                     }
                  }

                  var9 += var11;
                  if (var13) {
                  }
               }

               var6 = var8;
               break;
            }
         }

         return var6;
      }
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 40;
               break;
            case 1:
               var10003 = 95;
               break;
            case 2:
               var10003 = 80;
               break;
            case 3:
               var10003 = 1;
               break;
            default:
               var10003 = 25;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
