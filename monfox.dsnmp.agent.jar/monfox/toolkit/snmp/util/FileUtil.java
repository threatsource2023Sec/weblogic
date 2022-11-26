package monfox.toolkit.snmp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileUtil {
   // $FF: synthetic field
   static Class a;

   public static InputStream getInputStream(String var0) throws IOException {
      Object var1 = null;
      if (var1 == null) {
         try {
            URL var2 = new URL(var0);
            var1 = var2.openStream();
         } catch (IOException var4) {
         }
      }

      if (var1 == null) {
         try {
            var1 = new FileInputStream(var0);
         } catch (IOException var3) {
         }
      }

      if (var1 == null) {
         var1 = (a == null ? (a = a(b("gT4g\u007fr\u0015.n\u007ffP3u>yU7q>\u007fO3m>LR6dE~R6"))) : a).getResourceAsStream(var0);
      }

      if (var1 == null) {
         throw new IOException(b("CU,`|c_zGyf^v!EXwv!\u007fx\u001b\bdceN(bu*\u001c") + var0 + "'");
      } else {
         return (InputStream)var1;
      }
   }

   // $FF: synthetic method
   static Class a(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw (new NoClassDefFoundError()).initCause(var2);
      }
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 10;
               break;
            case 1:
               var10003 = 59;
               break;
            case 2:
               var10003 = 90;
               break;
            case 3:
               var10003 = 1;
               break;
            default:
               var10003 = 16;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
