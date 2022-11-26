package org.python.apache.xerces.impl.xs;

import java.io.File;
import java.io.UnsupportedEncodingException;

final class FilePathToURI {
   private static boolean[] gNeedEscaping = new boolean[128];
   private static char[] gAfterEscaping1 = new char[128];
   private static char[] gAfterEscaping2 = new char[128];
   private static char[] gHexChs = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

   private FilePathToURI() {
   }

   public static String filepath2URI(String var0) {
      if (var0 == null) {
         return null;
      } else {
         char var1 = File.separatorChar;
         var0 = var0.replace(var1, '/');
         int var2 = var0.length();
         StringBuffer var3 = new StringBuffer(var2 * 3);
         var3.append("file://");
         int var4;
         if (var2 >= 2 && var0.charAt(1) == ':') {
            var4 = Character.toUpperCase(var0.charAt(0));
            if (var4 >= 65 && var4 <= 90) {
               var3.append('/');
            }
         }

         int var5;
         for(var5 = 0; var5 < var2; ++var5) {
            var4 = var0.charAt(var5);
            if (var4 >= 128) {
               break;
            }

            if (gNeedEscaping[var4]) {
               var3.append('%');
               var3.append(gAfterEscaping1[var4]);
               var3.append(gAfterEscaping2[var4]);
            } else {
               var3.append((char)var4);
            }
         }

         if (var5 < var2) {
            Object var6 = null;

            byte[] var10;
            try {
               var10 = var0.substring(var5).getBytes("UTF-8");
            } catch (UnsupportedEncodingException var9) {
               return var0;
            }

            var2 = var10.length;

            for(var5 = 0; var5 < var2; ++var5) {
               byte var8 = var10[var5];
               if (var8 < 0) {
                  var4 = var8 + 256;
                  var3.append('%');
                  var3.append(gHexChs[var4 >> 4]);
                  var3.append(gHexChs[var4 & 15]);
               } else if (gNeedEscaping[var8]) {
                  var3.append('%');
                  var3.append(gAfterEscaping1[var8]);
                  var3.append(gAfterEscaping2[var8]);
               } else {
                  var3.append((char)var8);
               }
            }
         }

         return var3.toString();
      }
   }

   static {
      for(int var0 = 0; var0 <= 31; ++var0) {
         gNeedEscaping[var0] = true;
         gAfterEscaping1[var0] = gHexChs[var0 >> 4];
         gAfterEscaping2[var0] = gHexChs[var0 & 15];
      }

      gNeedEscaping[127] = true;
      gAfterEscaping1[127] = '7';
      gAfterEscaping2[127] = 'F';
      char[] var1 = new char[]{' ', '<', '>', '#', '%', '"', '{', '}', '|', '\\', '^', '~', '[', ']', '`'};
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var4 = var1[var3];
         gNeedEscaping[var4] = true;
         gAfterEscaping1[var4] = gHexChs[var4 >> 4];
         gAfterEscaping2[var4] = gHexChs[var4 & 15];
      }

   }
}
