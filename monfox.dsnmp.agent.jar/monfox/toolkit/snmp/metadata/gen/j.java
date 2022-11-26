package monfox.toolkit.snmp.metadata.gen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

class j {
   private static final String[] a = new String[]{a("r>,mud$H"), a("r9G\u0001\u001e"), a("n>N\u0001\u001e\u001fX8\u0001\u0017")};

   public static byte[] getASCIIBytes(String var0) {
      int var3 = Message.d;
      int var1 = 0;

      byte[] var10000;
      while(true) {
         if (var1 < a.length) {
            label19: {
               try {
                  var10000 = var0.getBytes(a[var1]);
               } catch (UnsupportedEncodingException var4) {
                  ++var1;
                  if (var3 == 0) {
                     continue;
                  }
                  break label19;
               }

               if (var3 == 0) {
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
      int var4;
      label49: {
         label44: {
            var4 = Message.d;
            if (System.getProperty(a("J\u0002oJI_CgEJBCdBEH\thBA")) == null) {
               int var2 = 0;

               while(true) {
                  if (var2 < a.length) {
                     try {
                        load(var0, var1, a[var2]);
                        break label49;
                     } catch (UnsupportedEncodingException var6) {
                        ++var2;
                        if (var4 == 0) {
                           continue;
                        }
                     }
                  }

                  if (var4 == 0) {
                     break label44;
                  }
                  break;
               }
            }

            try {
               load(var0, var1, System.getProperty(a("J\u0002oJI_CgEJBCdBEH\thBA")));
               return;
            } catch (UnsupportedEncodingException var5) {
            }
         }

         var0.load(var1);
         return;
      }

      if (var4 != 0) {
         ;
      }
   }

   public static void load(Properties var0, InputStream var1, String var2) throws IOException, UnsupportedEncodingException {
      int var9 = Message.d;
      InputStreamReader var3 = new InputStreamReader(var1, var2);
      BufferedReader var4 = new BufferedReader(var3);

      while(var4.ready()) {
         String var5 = var4.readLine();
         if (var5 == null) {
            break;
         }

         var5 = var5.trim();
         if (!var5.startsWith("#") && !var5.startsWith(a("\bB"))) {
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
                  if (var9 == 0) {
                     break label43;
                  }
               }

               var5 = var5.trim();
               if (var5.length() > 0) {
                  var0.put(var5, "");
               }
            }
         }

         if (var9 != 0) {
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
               var10003 = 39;
               break;
            case 1:
               var10003 = 109;
               break;
            case 2:
               var10003 = 1;
               break;
            case 3:
               var10003 = 44;
               break;
            default:
               var10003 = 38;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
