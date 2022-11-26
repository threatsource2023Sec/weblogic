package monfox.toolkit.snmp.mgr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

abstract class b {
   // $FF: synthetic field
   static Class a;

   public static b getInstance() {
      try {
         String var0 = monfox.toolkit.snmp.mgr.a.a();
         InputStream var1 = null;

         try {
            var1 = getInputStream(System.getProperty(var0 + b("\u0001' \u0002\u0013A8,"), var0 + b("\u0001' \u0002\u0013A8,")));
         } catch (IOException var5) {
            var1 = getInputStream(System.getProperty(b("B$'\u0007\u0019We%\b\u0015J%:\u0004"), b("B$'\u0007\u0019We%\b\u0015J%:\u0004")));
         }

         v var2 = new v();
         d.loadASCII(var2, var1);
         String var3 = var2.getType();
         Properties var4 = System.getProperties();
         if (var3.equalsIgnoreCase(b("j=(\r\u0003N? \u000e\u0018")) && var4.get(monfox.toolkit.snmp.mgr.a.a() + b("\u0001.?\u0000\u001aZ*=\b\u0019A")) == null) {
            var4.put(monfox.toolkit.snmp.mgr.a.a() + b("\u0001.?\u0000\u001aZ*=\b\u0019A"), b("[9<\u0004"));
            monfox.toolkit.snmp.mgr.a.a(monfox.toolkit.snmp.mgr.a.e());
         }
      } catch (IOException var6) {
         monfox.toolkit.snmp.mgr.a.a(monfox.toolkit.snmp.mgr.a.g());
         monfox.toolkit.snmp.mgr.a.d();
      } catch (Exception var7) {
         monfox.toolkit.snmp.mgr.a.d();
      }

      return new c();
   }

   public static InputStream getInputStream(String var0) throws FileNotFoundException {
      return getInputStream(var0, (Class)null);
   }

   public static InputStream getInputStream(String var0, Class var1) throws FileNotFoundException {
      boolean var4 = SnmpSession.B;

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
               var10 = (a == null ? (a = a(b("B$'\u0007\u0019We=\u000e\u0019C  \u0015X\\%$\u0011XB,;O\u0014"))) : a).getResourceAsStream(var0);
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
            var3 = (a == null ? (a = a(b("B$'\u0007\u0019We=\u000e\u0019C  \u0015X\\%$\u0011XB,;O\u0014"))) : a).getResourceAsStream(var11);
            if (var3 != null) {
               return var3;
            }

            if (!var4) {
               throw new FileNotFoundException(b("f%?\u0000\u001aF/i\u0013\u0013\\$<\u0013\u0015Jk'\u0000\u001bJkn") + var0 + b("\u0001k\u0004\u0014\u0005[k+\u0004VNk\u001c3:\u0003k/\b\u001aJk&\u0013VL'(\u0012\u0005_*=\tVL$$\u0011\u0019A.'\u0015X"));
            }
         }

         var3 = var1.getResourceAsStream(var11);
         if (var3 != null) {
            return var3;
         }
      } catch (Exception var7) {
      }

      throw new FileNotFoundException(b("f%?\u0000\u001aF/i\u0013\u0013\\$<\u0013\u0015Jk'\u0000\u001bJkn") + var0 + b("\u0001k\u0004\u0014\u0005[k+\u0004VNk\u001c3:\u0003k/\b\u001aJk&\u0013VL'(\u0012\u0005_*=\tVL$$\u0011\u0019A.'\u0015X"));
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
               var10003 = 47;
               break;
            case 1:
               var10003 = 75;
               break;
            case 2:
               var10003 = 73;
               break;
            case 3:
               var10003 = 97;
               break;
            default:
               var10003 = 118;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
