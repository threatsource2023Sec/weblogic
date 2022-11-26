package monfox.toolkit.snmp.agent;

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
         String var0 = monfox.toolkit.snmp.agent.a.a();
         InputStream var1 = null;

         try {
            var1 = getInputStream(System.getProperty(var0 + b("(\u0004P``h\u001b\\"), var0 + b("(\u0004P``h\u001b\\")));
         } catch (IOException var5) {
            var1 = getInputStream(System.getProperty(b("k\u0007Wej~FUjfc\u0006Jf"), b("k\u0007Wej~FUjfc\u0006Jf")));
         }

         k var2 = new k();
         d.loadASCII(var2, var1);
         String var3 = var2.getType();
         Properties var4 = System.getProperties();
         if (var3.equalsIgnoreCase(b("C\u001eXopg\u001cPlk")) && var4.get(monfox.toolkit.snmp.agent.a.a() + b("(\rObis\tMjjh")) == null) {
            var4.put(monfox.toolkit.snmp.agent.a.a() + b("(\rObis\tMjjh"), b("r\u001aLf"));
            monfox.toolkit.snmp.agent.a.a(monfox.toolkit.snmp.agent.a.e());
         }
      } catch (IOException var6) {
         monfox.toolkit.snmp.agent.a.a(monfox.toolkit.snmp.agent.a.g());
         monfox.toolkit.snmp.agent.a.d();
      } catch (Exception var7) {
         monfox.toolkit.snmp.agent.a.d();
      }

      return new c();
   }

   public static InputStream getInputStream(String var0) throws FileNotFoundException {
      return getInputStream(var0, (Class)null);
   }

   public static InputStream getInputStream(String var0, Class var1) throws FileNotFoundException {
      boolean var4 = SnmpMibNode.b;

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
               var10 = (a == null ? (a = a(b("k\u0007Wej~FMljj\u0003Pw+u\u0006Ts+g\u000f\\mq(\n"))) : a).getResourceAsStream(var0);
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
            var3 = (a == null ? (a = a(b("k\u0007Wej~FMljj\u0003Pw+u\u0006Ts+g\u000f\\mq(\n"))) : a).getResourceAsStream(var11);
            if (var3 != null) {
               return var3;
            }

            if (!var4) {
               throw new FileNotFoundException(b("O\u0006Obio\f\u0019q`u\u0007LqfcHWbhcH\u001e") + var0 + b("(HtvvrH[f%gHlQI*H_jicHVq%e\u0004Xpvv\tMk%e\u0007Tsjh\rWw+"));
            }
         }

         var3 = var1.getResourceAsStream(var11);
         if (var3 != null) {
            return var3;
         }
      } catch (Exception var7) {
      }

      throw new FileNotFoundException(b("O\u0006Obio\f\u0019q`u\u0007LqfcHWbhcH\u001e") + var0 + b("(HtvvrH[f%gHlQI*H_jicHVq%e\u0004Xpvv\tMk%e\u0007Tsjh\rWw+"));
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
               var10003 = 6;
               break;
            case 1:
               var10003 = 104;
               break;
            case 2:
               var10003 = 57;
               break;
            case 3:
               var10003 = 3;
               break;
            default:
               var10003 = 5;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
