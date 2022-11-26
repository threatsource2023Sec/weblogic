package monfox.toolkit.snmp.util;

public class ByteFormatter {
   private static char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

   public static String toString(byte[] var0) {
      return var0 == null ? a(";49\u0004") : toString(var0, 0, var0.length);
   }

   public static String toString(byte[] var0, int var1, int var2) {
      if (var0 == null) {
         return a(";49\u0004");
      } else {
         StringBuffer var3 = new StringBuffer();
         toString(var3, var0, var1, var2);
         return var3.toString();
      }
   }

   public static void toString(StringBuffer var0, byte[] var1, int var2, int var3) {
      int var8 = WorkItem.d;
      if (var1 == null) {
         var0.append(a(";49\u0004"));
      } else {
         int var4 = 0;

         do {
            int var10000 = var4;
            int var10001 = var3;

            label70:
            while(true) {
               if (var10000 >= var10001) {
                  return;
               }

               var0.append(FormatUtil.pad("" + var4, 4, 'r')).append(":");
               boolean var5 = true;
               int var6 = 0;

               label51: {
                  while(var6 < 16) {
                     var10000 = var6 % 4;
                     if (var8 != 0) {
                        break label51;
                     }

                     if (var10000 == 0) {
                        var0.append(" ");
                     }

                     label47: {
                        if (var4 + var6 < var3) {
                           var0.append(a[var1[var2 + var4 + var6] >> 4 & 15]);
                           var0.append(a[var1[var2 + var4 + var6] & 15]);
                           if (var8 == 0) {
                              break label47;
                           }
                        }

                        var0.append(a("ua"));
                     }

                     var0.append(" ");
                     ++var6;
                     if (var8 != 0) {
                        break;
                     }
                  }

                  var0.append(a("uau"));
                  var10000 = 0;
               }

               var6 = var10000;

               while(true) {
                  if (var6 >= 16) {
                     break label70;
                  }

                  var10000 = var4 + var6;
                  var10001 = var3;
                  if (var8 != 0) {
                     break;
                  }

                  if (var10000 >= var3) {
                     break label70;
                  }

                  label65: {
                     byte var7 = var1[var2 + var4 + var6];
                     if (var7 >= 32 && var7 <= 126) {
                        var0.append((char)var7);
                        if (var8 == 0) {
                           break label65;
                        }
                     }

                     var0.append(".");
                  }

                  ++var6;
                  if (var8 != 0) {
                     break label70;
                  }
               }
            }

            var0.append("\n");
            var4 += 16;
         } while(var8 == 0);

      }
   }

   public static String toHexString(byte[] var0) {
      return toHexString(var0, false);
   }

   public static String toHexString(byte[] var0, boolean var1) {
      int var4 = WorkItem.d;
      if (var0 == null) {
         return a(";49\u0004");
      } else {
         StringBuffer var2 = new StringBuffer();
         if (!var1) {
            var2.append('\'');
         }

         int var3 = 0;

         while(true) {
            if (var3 < var0.length) {
               var2.append(a[var0[var3] >> 4 & 15]);
               var2.append(a[var0[var3] & 15]);
               ++var3;
               if (var4 != 0) {
                  break;
               }

               if (var4 == 0) {
                  continue;
               }
            }

            if (!var1) {
               var2.append(a("r\t"));
            }
            break;
         }

         return var2.toString();
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
               var10003 = 85;
               break;
            case 1:
               var10003 = 65;
               break;
            case 2:
               var10003 = 85;
               break;
            case 3:
               var10003 = 104;
               break;
            default:
               var10003 = 49;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
