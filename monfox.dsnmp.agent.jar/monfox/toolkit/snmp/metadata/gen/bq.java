package monfox.toolkit.snmp.metadata.gen;

import java.util.Properties;

class bq extends Properties {
   public String toString() {
      int var9 = Message.d;

      try {
         byte[] var1 = j.getASCIIBytes(new String(a("l7`V\u0018l7`")));
         String[] var2 = new String[]{a("U\u000bVf#J\u0007p{=P\u0010Pp"), a("U\u000bVf#J\u0007QS?V\u0006@`9J"), a("U\u000bVf#J\u0007az=\\"), a("U\u000bVf#J\u0007Q@8J\u0016Zn(K"), a("U\u000bVf#J\u0007QG(O\u0007Yl=\\\u0010F"), a("U\u000bVf#J\u0007QQ8W\u0016\\n(J")};
         int var3 = 0;
         int var4 = 0;

         label67:
         do {
            int var10000 = var4;
            int var10001 = var2.length;

            label64:
            while(true) {
               if (var10000 >= var10001) {
                  break label67;
               }

               String var5 = this.getProperty(var2[var4]).trim();
               byte[] var6 = j.getASCIIBytes(var5);
               int var7 = 0;

               while(true) {
                  if (var7 >= var6.length) {
                     break label64;
                  }

                  var10000 = var6[var7];
                  var10001 = 32;
                  if (var9 != 0) {
                     break;
                  }

                  label59: {
                     if (var10000 > 32) {
                        int var8 = 0;

                        while(var8 < 8) {
                           var1[(var3 + var8) % 8] = (byte)(var1[(var3 + 1 + var8) % 8] ^ var6[var7] + var8);
                           ++var8;
                           if (var9 != 0) {
                              break label59;
                           }

                           if (var9 != 0) {
                              break;
                           }
                        }

                        ++var3;
                     }

                     ++var7;
                  }

                  if (var9 != 0) {
                     break label64;
                  }
               }
            }

            ++var4;
         } while(var9 == 0);

         StringBuffer var11 = new StringBuffer();
         int var12 = 0;

         StringBuffer var13;
         while(true) {
            if (var12 < var1.length) {
               var11.append(a((byte)(var1[var12] >> 3)));
               var13 = var11.append(a(var1[var12]));
               if (var9 != 0) {
                  break;
               }

               ++var12;
               if (var9 == 0) {
                  continue;
               }
            }

            var13 = var11;
            break;
         }

         return var13.toString();
      } catch (Exception var10) {
         return null;
      }
   }

   private static char a(byte var0) {
      var0 = (byte)(var0 & 31);
      return var0 < 10 ? (char)(var0 + 48) : (char)(var0 - 10 + 65);
   }

   public String getType() {
      int var8 = Message.d;

      try {
         String var1 = this.getProperty(a("U\u000bVf#J\u0007~f4")).trim();
         String var2 = this.getProperty(a("U\u000bVf#J\u0007p{=P\u0010Pp")).trim();
         long var3 = Long.parseLong(var2.substring(0, var2.indexOf("/")));
         String var5 = this.getProperty(a("U\u000bVf#J\u0007QS?V\u0006@`9J")).trim();
         String var6 = this.getProperty(a("U\u000bVf#J\u0007az=\\")).trim();
         String var7 = this.toString();
         if (!var1.equals(var7)) {
            c.a(c.i());
            if (var8 == 0) {
               throw new RuntimeException();
            }
         }

         if (var5.lastIndexOf(c.b()) < 0 && !var5.equalsIgnoreCase(a("x.y"))) {
            c.a(c.h());
            if (var8 == 0) {
               throw new RuntimeException();
            }
         }

         if (var3 > 0L && var3 < c.c()) {
            c.a(c.f());
            if (var8 == 0) {
               throw new RuntimeException();
            }
         }

         return var6;
      } catch (Exception var9) {
         c.a(c.i());
         throw new RuntimeException();
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
               var10003 = 57;
               break;
            case 1:
               var10003 = 98;
               break;
            case 2:
               var10003 = 53;
               break;
            case 3:
               var10003 = 3;
               break;
            default:
               var10003 = 77;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
