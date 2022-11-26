package monfox.toolkit.snmp.util;

public class FormatUtil {
   private static String a = a("\u0005BwW7\u0005BwW7\u0005BwW7\u0005BwW7\u0005BwW7\u0005BwW7\u0005BwW7\u0005BwW7\u0005BwW7\u0005BwW7\u0005BwW7\u0005BwW7\u0005BwW7\u0005BwW7\u0005BwW7\u0005BwW7\u0005BwW7\u0005BwW7\u0005B");
   private static String b = a("\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\bOzZ:\b");

   public static String formatTable(Object[][] var0) {
      StringBuffer var1 = new StringBuffer();
      formatTable(var0, var1);
      return var1.toString();
   }

   public static void formatTable(Object[][] var0, StringBuffer var1) {
      int var9 = WorkItem.d;
      int var2 = var0.length;
      int var3 = var0[0].length;
      int[] var4 = new int[var3];
      int var5 = 0;

      int var6;
      while(var5 < var2) {
         var6 = 0;

         while(true) {
            if (var6 < var3) {
               Object var7 = var0[var5][var6];
               String var8 = null;
               if (var9 != 0) {
                  break;
               }

               if (var7 != null) {
                  var8 = var7.toString();
               } else {
                  var8 = a("K\u0017;\u001b");
               }

               var0[var5][var6] = var8;
               if (var4[var6] < var8.length()) {
                  var4[var6] = var8.length();
               }

               ++var6;
               if (var9 == 0) {
                  continue;
               }
            }

            ++var5;
            break;
         }

         if (var9 != 0) {
            break;
         }
      }

      String var10 = genDivider(var4, '+') + "\n";
      var1.append(var10);
      var6 = 0;

      while(true) {
         if (var6 < var2) {
            if (var9 != 0) {
               break;
            }

            int var11 = 0;

            int var10000;
            label52: {
               while(var11 < var3) {
                  var10000 = var6;
                  if (var9 != 0) {
                     break label52;
                  }

                  int var12 = var6 == 0 ? 99 : 108;
                  var1.append("|").append(pad((String)var0[var6][var11], var4[var11], (char)var12));
                  ++var11;
                  if (var9 != 0) {
                     break;
                  }
               }

               var1.append(a("Yh"));
               var10000 = var6;
            }

            if (var10000 == 0) {
               var1.append(var10);
            }

            ++var6;
            if (var9 == 0) {
               continue;
            }
         }

         var1.append(var10);
         break;
      }

   }

   public static String genDivider(int[] var0, char var1) {
      int var4 = WorkItem.d;
      StringBuffer var2 = new StringBuffer();
      var2.append(var1);
      int var3 = 0;

      StringBuffer var10000;
      while(true) {
         if (var3 < var0.length) {
            var10000 = var2.append(b.substring(0, var0[var3])).append(var1);
            if (var4 != 0) {
               break;
            }

            ++var3;
            if (var4 == 0) {
               continue;
            }
         }

         var10000 = var2;
         break;
      }

      return var10000.toString();
   }

   public static String pad(String var0, int var1, char var2) {
      int var7 = WorkItem.d;
      int var3 = var0.length();
      if (var3 > var1) {
         return var0;
      } else {
         String var4 = a.substring(0, var1 - var3);
         String var5;
         if (var2 == 'l') {
            var5 = var0 + var4;
            if (var7 == 0) {
               return var5;
            }
         }

         if (var2 == 'r') {
            var5 = var4 + var0;
            if (var7 == 0) {
               return var5;
            }
         }

         int var6 = var4.length() / 2;
         var5 = var4.substring(0, var6) + var0 + var4.substring(var6);
         return var5;
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
               var10003 = 37;
               break;
            case 1:
               var10003 = 98;
               break;
            case 2:
               var10003 = 87;
               break;
            case 3:
               var10003 = 119;
               break;
            default:
               var10003 = 23;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
