package com.rsa.certj.x;

import java.math.BigInteger;

public class e {
   public static String a(byte[] var0) {
      StringBuffer var1 = new StringBuffer();
      int var2 = var0.length;

      for(int var3 = 0; var2 > 0; ++var3) {
         int var4 = var0[var3] & 255;
         String var5 = Integer.toHexString(var4);
         if (var5.length() == 1) {
            var1 = var1.append("0");
         }

         var1 = var1.append(var5);
         --var2;
      }

      return var1.toString();
   }

   public static byte[] a(BigInteger var0) {
      if (var0 == null) {
         return null;
      } else {
         byte[] var1 = var0.toByteArray();
         if (var1[0] != 0) {
            return var1;
         } else if (var1.length == 1) {
            return var1;
         } else {
            byte[] var2 = new byte[var1.length - 1];
            System.arraycopy(var1, 1, var2, 0, var2.length);
            return var2;
         }
      }
   }
}
