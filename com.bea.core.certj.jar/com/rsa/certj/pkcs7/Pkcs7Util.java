package com.rsa.certj.pkcs7;

import java.io.ByteArrayOutputStream;
import java.util.StringTokenizer;

final class Pkcs7Util {
   private Pkcs7Util() {
   }

   static byte[] a(String var0) throws PKCS7Exception {
      byte[] var1 = null;
      if (var0 == null) {
         throw new PKCS7Exception("OID is null");
      } else {
         if (var0.trim().length() > 1) {
            StringTokenizer var2 = new StringTokenizer(var0, ".");
            int var4 = 0;
            int var5 = 0;

            try {
               ByteArrayOutputStream var6;
               for(var6 = new ByteArrayOutputStream(); var2.hasMoreElements(); ++var4) {
                  Integer var3 = new Integer(var2.nextToken());
                  if (var4 == 0) {
                     if (var3 > 2) {
                        throw new PKCS7Exception("OID is invalid");
                     }

                     var5 = var3;
                  }

                  if (var4 == 1) {
                     if (var5 < 2 && var3 > 39) {
                        throw new PKCS7Exception("OID is invalid");
                     }

                     var3 = new Integer(var5 * 40 + var3);
                  }

                  if (var4 > 0) {
                     if ((long)var3 < 128L) {
                        var6.write(var3);
                     } else if ((long)var3 < 16384L) {
                        var6.write(var3 >>> 7 | 128);
                        var6.write(var3 & 127);
                     } else if ((long)var3 < 2097152L) {
                        var6.write(var3 >>> 14 | 128);
                        var6.write((var3 >>> 7 | 128) & 255);
                        var6.write(var3 & 127);
                     } else if ((long)var3 < 268435456L) {
                        var6.write(var3 >>> 21 | 128);
                        var6.write((var3 >>> 14 | 128) & 255);
                        var6.write((var3 >>> 7 | 128) & 255);
                        var6.write(var3 & 127);
                     } else {
                        var6.write(var3 >>> 28 | 128);
                        var6.write(var3 >>> 21 | 128);
                        var6.write((var3 >>> 14 | 128) & 255);
                        var6.write((var3 >>> 7 | 128) & 255);
                        var6.write(var3 & 127);
                     }
                  }
               }

               var6.close();
               var1 = var6.toByteArray();
            } catch (Exception var8) {
               throw new PKCS7Exception("OID processing failed - error was: ", var8);
            }
         }

         return var1;
      }
   }
}
