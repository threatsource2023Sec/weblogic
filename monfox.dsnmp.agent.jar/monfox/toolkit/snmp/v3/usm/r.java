package monfox.toolkit.snmp.v3.usm;

class r {
   private r() {
   }

   public static String tohex(byte[] var0) {
      boolean var7 = USMLocalizedUserData.k;
      StringBuffer var1 = new StringBuffer();
      StringBuffer var2 = new StringBuffer();
      var1.append(a("Z\u0010K8\u0006J"));
      int var5 = 0;
      int var4 = 0;

      int var10000;
      byte var10001;
      int var6;
      while(true) {
         if (var4 < var0.length) {
            var1.append(b(var0[var4] >>> 4 & 15)).append(b(var0[var4] & 15));
            var1.append(" ");
            ++var5;
            var10000 = var0[var4];
            var10001 = 29;
            if (var7) {
               break;
            }

            label51: {
               if (var10000 > 29) {
                  var6 = (char)var0[var4];
                  var2.append(String.valueOf((char)var6));
                  if (!var7) {
                     break label51;
                  }
               }

               var2.append(".");
            }

            label46: {
               if ((var4 + 1) % 16 == 0) {
                  var1.append(a("J\u0000[")).append(var2.toString()).append("\n");
                  if (var4 + 1 != var0.length) {
                     var1.append(a(var4 + 1)).append(a("P\u0000"));
                  }

                  var2 = new StringBuffer();
                  var5 = 0;
                  if (!var7) {
                     break label46;
                  }
               }

               if ((var4 + 1) % 4 == 0) {
                  var1.append(" ");
               }
            }

            ++var4;
            if (!var7) {
               continue;
            }
         }

         var10000 = var4;
         var10001 = 16;
         break;
      }

      if (var10000 % var10001 != 0) {
         var6 = 0;

         while(var6 < 16 - var5) {
            var1.append(a("J\u0000["));
            ++var6;
            if (var7) {
               return var1.toString();
            }

            if (var7) {
               break;
            }
         }

         var1.append(a("J\u0000[(")).append(var2.toString()).append("\n");
      }

      return var1.toString();
   }

   private static String a(int var0) {
      boolean var4 = USMLocalizedUserData.k;
      String var1 = String.valueOf(var0);
      int var2 = var1.length();
      int var3 = 0;

      String var10000;
      while(true) {
         if (var3 < var2) {
            var10000 = "0" + var1;
            if (var4) {
               break;
            }

            var1 = var10000;
            ++var3;
            if (!var4) {
               continue;
            }
         }

         var10000 = var1;
         break;
      }

      return var10000;
   }

   private static final char b(int var0) {
      if (var0 >= 10 && var0 <= 15) {
         return (char)(65 + (var0 - 10));
      } else {
         return var0 >= 0 && var0 <= 9 ? (char)(48 + var0) : '0';
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
               var10003 = 106;
               break;
            case 1:
               var10003 = 32;
               break;
            case 2:
               var10003 = 123;
               break;
            case 3:
               var10003 = 8;
               break;
            default:
               var10003 = 60;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
