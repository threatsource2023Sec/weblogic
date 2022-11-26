package monfox.toolkit.snmp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

class e {
   private static final String[] a = new String[]{a("+MX\u0000i=W<"), a("+J3l\u0002"), a("7M:l\u0002F+Ll\u000b")};

   public static byte[] getASCIIBytes(String var0) {
      boolean var3 = SnmpValue.b;
      int var1 = 0;

      byte[] var10000;
      while(true) {
         if (var1 < a.length) {
            label19: {
               try {
                  var10000 = var0.getBytes(a[var1]);
               } catch (UnsupportedEncodingException var4) {
                  ++var1;
                  if (!var3) {
                     continue;
                  }
                  break label19;
               }

               if (!var3) {
                  return var10000;
               }
               break;
            }
         }

         var10000 = var0.getBytes();
         break;
      }

      return var10000;
   }

   public static void loadASCII(Properties var0, InputStream var1) throws IOException {
      boolean var4;
      label49: {
         label44: {
            var4 = SnmpValue.b;
            if (System.getProperty(a("\u0013q\u001b'U\u00060\u0013(V\u001b0\u0010/Y\u0011z\u001c/]")) == null) {
               int var2 = 0;

               while(true) {
                  if (var2 < a.length) {
                     try {
                        load(var0, var1, a[var2]);
                        break label49;
                     } catch (UnsupportedEncodingException var6) {
                        ++var2;
                        if (!var4) {
                           continue;
                        }
                     }
                  }

                  if (!var4) {
                     break label44;
                  }
                  break;
               }
            }

            try {
               load(var0, var1, System.getProperty(a("\u0013q\u001b'U\u00060\u0013(V\u001b0\u0010/Y\u0011z\u001c/]")));
               return;
            } catch (UnsupportedEncodingException var5) {
            }
         }

         var0.load(var1);
         return;
      }

      if (var4) {
         ;
      }
   }

   public static void load(Properties var0, InputStream var1, String var2) throws IOException, UnsupportedEncodingException {
      boolean var9 = SnmpValue.b;
      InputStreamReader var3 = new InputStreamReader(var1, var2);
      BufferedReader var4 = new BufferedReader(var3);

      while(var4.ready()) {
         String var5 = var4.readLine();
         if (var5 == null) {
            break;
         }

         var5 = var5.trim();
         if (!var5.startsWith("#") && !var5.startsWith(a("Q1"))) {
            label43: {
               int var6 = var5.indexOf(61);
               if (var6 < 0) {
                  var6 = var5.indexOf(58);
               }

               if (var6 >= 0) {
                  String var7 = var5.substring(0, var6);
                  String var8 = "";
                  if (var6 + 1 < var5.length()) {
                     var8 = var5.substring(var6 + 1);
                  }

                  var0.put(var7, var8);
                  if (!var9) {
                     break label43;
                  }
               }

               var5 = var5.trim();
               if (var5.length() > 0) {
                  var0.put(var5, "");
               }
            }
         }

         if (var9) {
            break;
         }
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
               var10003 = 126;
               break;
            case 1:
               var10003 = 30;
               break;
            case 2:
               var10003 = 117;
               break;
            case 3:
               var10003 = 65;
               break;
            default:
               var10003 = 58;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
