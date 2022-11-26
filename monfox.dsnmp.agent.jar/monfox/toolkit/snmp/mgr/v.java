package monfox.toolkit.snmp.mgr;

import java.util.Properties;

class v extends Properties {
   public String toString() {
      boolean var9 = SnmpSession.B;

      try {
         byte[] var1 = d.getASCIIBytes(new String(a("c~\u00197?c~\u0019")));
         String[] var2 = new String[]{a("ZB/\u0007\u0004EN\t\u001a\u001a_Y)\u0011"), a("ZB/\u0007\u0004EN(2\u0018YO9\u0001\u001eE"), a("ZB/\u0007\u0004EN\u0018\u001b\u001aS"), a("ZB/\u0007\u0004EN(!\u001fE_#\u000f\u000fD"), a("ZB/\u0007\u0004EN(&\u000f@N \r\u001aSY?"), a("ZB/\u0007\u0004EN(0\u001fX_%\u000f\u000fE")};
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
               byte[] var6 = d.getASCIIBytes(var5);
               int var7 = 0;

               while(true) {
                  if (var7 >= var6.length) {
                     break label64;
                  }

                  var10000 = var6[var7];
                  var10001 = 32;
                  if (var9) {
                     break;
                  }

                  label59: {
                     if (var10000 > 32) {
                        int var8 = 0;

                        while(var8 < 8) {
                           var1[(var3 + var8) % 8] = (byte)(var1[(var3 + 1 + var8) % 8] ^ var6[var7] + var8);
                           ++var8;
                           if (var9) {
                              break label59;
                           }

                           if (var9) {
                              break;
                           }
                        }

                        ++var3;
                     }

                     ++var7;
                  }

                  if (var9) {
                     break label64;
                  }
               }
            }

            ++var4;
         } while(!var9);

         StringBuffer var11 = new StringBuffer();
         int var12 = 0;

         StringBuffer var13;
         while(true) {
            if (var12 < var1.length) {
               var11.append(a((byte)(var1[var12] >> 3)));
               var13 = var11.append(a(var1[var12]));
               if (var9) {
                  break;
               }

               ++var12;
               if (!var9) {
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
      boolean var8 = SnmpSession.B;

      try {
         String var1 = this.getProperty(a("ZB/\u0007\u0004EN\u0007\u0007\u0013")).trim();
         String var2 = this.getProperty(a("ZB/\u0007\u0004EN\t\u001a\u001a_Y)\u0011")).trim();
         long var3 = Long.parseLong(var2.substring(0, var2.indexOf("/")));
         String var5 = this.getProperty(a("ZB/\u0007\u0004EN(2\u0018YO9\u0001\u001eE")).trim();
         String var6 = this.getProperty(a("ZB/\u0007\u0004EN\u0018\u001b\u001aS")).trim();
         String var7 = this.toString();
         if (!var1.equals(var7)) {
            a.a(a.i());
            if (!var8) {
               throw new RuntimeException();
            }
         }

         if (var5.lastIndexOf(a.b()) < 0 && !var5.equalsIgnoreCase(a("wg\u0000"))) {
            a.a(a.h());
            if (!var8) {
               throw new RuntimeException();
            }
         }

         if (var3 > 0L && var3 < a.c()) {
            a.a(a.f());
            if (!var8) {
               throw new RuntimeException();
            }
         }

         return var6;
      } catch (Exception var9) {
         a.a(a.i());
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
               var10003 = 54;
               break;
            case 1:
               var10003 = 43;
               break;
            case 2:
               var10003 = 76;
               break;
            case 3:
               var10003 = 98;
               break;
            default:
               var10003 = 106;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
