package monfox.toolkit.snmp;

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
         String var0 = monfox.toolkit.snmp.a.a();
         InputStream var1 = null;

         try {
            var1 = getInputStream(System.getProperty(var0 + b("W<w'c\u0017#{"), var0 + b("W<w'c\u0017#{")));
         } catch (IOException var5) {
            var1 = getInputStream(System.getProperty(b("\u0014?p\"i\u0001~r-e\u001c>m!"), b("\u0014?p\"i\u0001~r-e\u001c>m!")));
         }

         g var2 = new g();
         e.loadASCII(var2, var1);
         String var3 = var2.getType();
         Properties var4 = System.getProperties();
         if (var3.equalsIgnoreCase(b("<&\u007f(s\u0018$w+h")) && var4.get(monfox.toolkit.snmp.a.a() + b("W5h%j\f1j-i\u0017")) == null) {
            var4.put(monfox.toolkit.snmp.a.a() + b("W5h%j\f1j-i\u0017"), b("\r\"k!"));
            monfox.toolkit.snmp.a.a(monfox.toolkit.snmp.a.e());
         }
      } catch (IOException var6) {
         monfox.toolkit.snmp.a.a(monfox.toolkit.snmp.a.g());
         monfox.toolkit.snmp.a.d();
      } catch (Exception var7) {
         monfox.toolkit.snmp.a.d();
      }

      return new c();
   }

   public static InputStream getInputStream(String var0) throws FileNotFoundException {
      return getInputStream(var0, (Class)null);
   }

   public static InputStream getInputStream(String var0, Class var1) throws FileNotFoundException {
      boolean var4 = SnmpValue.b;

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
               var10 = (a == null ? (a = a(b("\u0014?p\"i\u0001~j+i\u0015;w0(\n>s4(\u001b"))) : a).getResourceAsStream(var0);
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
            var3 = (a == null ? (a = a(b("\u0014?p\"i\u0001~j+i\u0015;w0(\n>s4(\u001b"))) : a).getResourceAsStream(var11);
            if (var3 != null) {
               return var3;
            }

            if (!var4) {
               throw new FileNotFoundException(b("0>h%j\u00104>6c\n?k6e\u001cpp%k\u001cp9") + var0 + b("WpS1u\rp|!&\u0018pK\u0016JUpx-j\u001cpq6&\u001a<\u007f7u\t1j,&\u001a?s4i\u00175p0("));
            }
         }

         var3 = var1.getResourceAsStream(var11);
         if (var3 != null) {
            return var3;
         }
      } catch (Exception var7) {
      }

      throw new FileNotFoundException(b("0>h%j\u00104>6c\n?k6e\u001cpp%k\u001cp9") + var0 + b("WpS1u\rp|!&\u0018pK\u0016JUpx-j\u001cpq6&\u001a<\u007f7u\t1j,&\u001a?s4i\u00175p0("));
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
               var10003 = 121;
               break;
            case 1:
               var10003 = 80;
               break;
            case 2:
               var10003 = 30;
               break;
            case 3:
               var10003 = 68;
               break;
            default:
               var10003 = 6;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
