package monfox.toolkit.snmp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class License {
   // $FF: synthetic field
   static Class a;

   public static void main(String[] var0) {
      new d();
   }

   public static String getString() {
      Properties var0 = a(d("\fX:D&"));
      return var0 == null ? d("b!^\tvH\u0001~\u0003v&Dte?\u000bN:Z3Hb:O9\u001aF5]?\u0007Eth \tB8H4\u0004NtO9\u001a\u0011tm/\u0006J9@5;e\u0019yvB\u0001~#") : d("b!t\tv8Y;M#\u000b_te?\u000bN:Z3Hb:O9\u001aF5]?\u0007E^\tvH\u0006y\u0004{E\u0006y\u0004{E\u0006y\u0004{E\u0006y\u0004{E\u0006y\u0004{E\u0006y\u0004{E\u0006y\u0004{E\u0006y\u0004{E\u0006y\u0004{E\u0006^\tvH{&F2\u001dH ZvH\u0011t") + var0.getProperty(d("\u0004B7L8\u001bN0y$\u0007O!J\"\u001b"), d("W\u0014k")) + d("b\u000bt\t\u001a\u0001H1G%\r\u000bt\tlH") + var0.getProperty(d("\u0004B7L8\u001bN\u0000P&\r"), d("W\u0014k")) + d("b\u000bt\t\u0015\u001dX F;\rYt\tlH") + var0.getProperty(d("\u0004B7L8\u001bN0j#\u001b_;D3\u001a"), d("W\u0014k")) + d("b\u000bt\t\u0013\u0010[=[3\u001b\u000bt\tlH") + var0.getProperty(d("\u0004B7L8\u001bN\u0011Q&\u0001Y1Z"), d("W\u0014k")) + d("b\u000bt\t{E\u0006y\u0004{E\u0006y\u0004{E\u0006y\u0004{E\u0006y\u0004{E\u0006y\u0004{E\u0006y\u0004{E\u0006y\u0004{E\u0006y\u0004{E\u0006y\u0004{") + d("b!");
   }

   static Properties a(String var0) {
      try {
         InputStream var1 = null;

         try {
            var1 = b(System.getProperty(var0 + d("FG=J3\u0006X1"), var0 + d("FG=J3\u0006X1")));
         } catch (IOException var3) {
            var1 = b(System.getProperty(d("\u0005D:O9\u0010\u00058@5\rE'L"), d("\u0005D:O9\u0010\u00058@5\rE'L")));
         }

         Properties var2 = new Properties();
         var2.load(var1);
         return var2;
      } catch (IOException var4) {
      } catch (Exception var5) {
      }

      return null;
   }

   static InputStream b(String var0) throws FileNotFoundException {
      return a(var0, (Class)null);
   }

   static InputStream a(String var0, Class var1) throws FileNotFoundException {
      boolean var4 = SnmpValue.b;

      InputStream var3;
      try {
         URL var2 = new URL(var0);
         var3 = var2.openStream();
         if (var3 != null) {
            return var3;
         }
      } catch (IOException var6) {
      }

      try {
         FileInputStream var9 = new FileInputStream(var0);
         if (var9 != null) {
            return var9;
         }
      } catch (IOException var5) {
      }

      try {
         label74: {
            InputStream var10;
            if (var1 == null) {
               var10 = (a == null ? (a = c(d("\u0005D:O9\u0010\u0005 F9\u0004@=]x\u001bE9Yx$B7L8\u001bN"))) : a).getResourceAsStream(var0);
               if (var10 != null) {
                  return var10;
               }

               if (!var4) {
                  break label74;
               }
            }

            var10 = var1.getResourceAsStream(var0);
            if (var10 != null) {
               return var10;
            }
         }
      } catch (Exception var8) {
      }

      try {
         String var11 = "/" + var0;
         if (var1 == null) {
            var3 = (a == null ? (a = c(d("\u0005D:O9\u0010\u0005 F9\u0004@=]x\u001bE9Yx$B7L8\u001bN"))) : a).getResourceAsStream(var11);
            if (var3 != null) {
               return var3;
            }

            if (!var4) {
               throw new FileNotFoundException(d("!E\"H:\u0001Ot[3\u001bD![5\r\u000b:H;\r\u000bs") + var0 + d("F\u000b\u0019\\%\u001c\u000b6Lv\t\u000b\u0001{\u001aD\u000b2@:\r\u000b;[v\u000bG5Z%\u0018J Av\u000bD9Y9\u0006N:]x"));
            }
         }

         var3 = var1.getResourceAsStream(var11);
         if (var3 != null) {
            return var3;
         }
      } catch (Exception var7) {
      }

      throw new FileNotFoundException(d("!E\"H:\u0001Ot[3\u001bD![5\r\u000b:H;\r\u000bs") + var0 + d("F\u000b\u0019\\%\u001c\u000b6Lv\t\u000b\u0001{\u001aD\u000b2@:\r\u000b;[v\u000bG5Z%\u0018J Av\u000bD9Y9\u0006N:]x"));
   }

   // $FF: synthetic method
   static Class c(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw (new NoClassDefFoundError()).initCause(var2);
      }
   }

   private static String d(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 104;
               break;
            case 1:
               var10003 = 43;
               break;
            case 2:
               var10003 = 84;
               break;
            case 3:
               var10003 = 41;
               break;
            default:
               var10003 = 86;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
