package monfox.toolkit.snmp.agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

class d {
   private static final String[] a = new String[]{a("B\u001f)r\u001aT\u0005M"), a("B\u0018B\u001eq"), a("^\u001fK\u001eq/y=\u001ex")};

   public static byte[] getASCIIBytes(String var0) {
      boolean var3 = SnmpMibNode.b;
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
            var4 = SnmpMibNode.b;
            if (System.getProperty(a("z#jU&obbZ%rba]*x(m].")) == null) {
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
               load(var0, var1, System.getProperty(a("z#jU&obbZ%rba]*x(m].")));
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
      boolean var9 = SnmpMibNode.b;
      InputStreamReader var3 = new InputStreamReader(var1, var2);
      BufferedReader var4 = new BufferedReader(var3);

      while(var4.ready()) {
         String var5 = var4.readLine();
         if (var5 == null) {
            break;
         }

         var5 = var5.trim();
         if (!var5.startsWith("#") && !var5.startsWith(a("8c"))) {
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
               var10003 = 23;
               break;
            case 1:
               var10003 = 76;
               break;
            case 2:
               var10003 = 4;
               break;
            case 3:
               var10003 = 51;
               break;
            default:
               var10003 = 73;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
