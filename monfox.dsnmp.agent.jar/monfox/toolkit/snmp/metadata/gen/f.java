package monfox.toolkit.snmp.metadata.gen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

abstract class f {
   // $FF: synthetic field
   static Class a;

   public static f getInstance() {
      try {
         String var0 = c.a();
         InputStream var1 = null;

         try {
            var1 = getInputStream(System.getProperty(var0 + b("MS\u001a\t$\rL\u0016"), var0 + b("MS\u001a\t$\rL\u0016")));
         } catch (IOException var5) {
            var1 = getInputStream(System.getProperty(b("\u000eP\u001d\f.\u001b\u0011\u001f\u0003\"\u0006Q\u0000\u000f"), b("\u000eP\u001d\f.\u001b\u0011\u001f\u0003\"\u0006Q\u0000\u000f")));
         }

         bq var2 = new bq();
         j.loadASCII(var2, var1);
         String var3 = var2.getType();
         Properties var4 = System.getProperties();
         if (var3.equalsIgnoreCase(b("&I\u0012\u00064\u0002K\u001a\u0005/")) && var4.get(c.a() + b("MZ\u0005\u000b-\u0016^\u0007\u0003.\r")) == null) {
            var4.put(c.a() + b("MZ\u0005\u000b-\u0016^\u0007\u0003.\r"), b("\u0017M\u0006\u000f"));
            c.a(c.e());
         }
      } catch (IOException var6) {
         c.a(c.g());
         c.d();
      } catch (Exception var7) {
         c.d();
      }

      return new g();
   }

   public static InputStream getInputStream(String var0) throws FileNotFoundException {
      return getInputStream(var0, (Class)null);
   }

   public static InputStream getInputStream(String var0, Class var1) throws FileNotFoundException {
      int var4 = Message.d;

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
               var10 = (a == null ? (a = a(b("\u000eP\u001d\f.\u001b\u0011\u0007\u0005.\u000fT\u001a\u001eo\u0010Q\u001e\u001ao\u000eZ\u0007\u000b%\u0002K\u0012D&\u0006Q]\f"))) : a).getResourceAsStream(var0);
               if (var10 != null) {
                  return var10;
               }

               if (var4 == 0) {
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
            var3 = (a == null ? (a = a(b("\u000eP\u001d\f.\u001b\u0011\u0007\u0005.\u000fT\u001a\u001eo\u0010Q\u001e\u001ao\u000eZ\u0007\u000b%\u0002K\u0012D&\u0006Q]\f"))) : a).getResourceAsStream(var11);
            if (var3 != null) {
               return var3;
            }

            if (var4 == 0) {
               throw new FileNotFoundException(b("*Q\u0005\u000b-\n[S\u0018$\u0010P\u0006\u0018\"\u0006\u001f\u001d\u000b,\u0006\u001fT") + var0 + b("M\u001f>\u001f2\u0017\u001f\u0011\u000fa\u0002\u001f&8\rO\u001f\u0015\u0003-\u0006\u001f\u001c\u0018a\u0000S\u0012\u00192\u0013^\u0007\u0002a\u0000P\u001e\u001a.\rZ\u001d\u001eo"));
            }
         }

         var3 = var1.getResourceAsStream(var11);
         if (var3 != null) {
            return var3;
         }
      } catch (Exception var7) {
      }

      throw new FileNotFoundException(b("*Q\u0005\u000b-\n[S\u0018$\u0010P\u0006\u0018\"\u0006\u001f\u001d\u000b,\u0006\u001fT") + var0 + b("M\u001f>\u001f2\u0017\u001f\u0011\u000fa\u0002\u001f&8\rO\u001f\u0015\u0003-\u0006\u001f\u001c\u0018a\u0000S\u0012\u00192\u0013^\u0007\u0002a\u0000P\u001e\u001a.\rZ\u001d\u001eo"));
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
               var10003 = 99;
               break;
            case 1:
               var10003 = 63;
               break;
            case 2:
               var10003 = 115;
               break;
            case 3:
               var10003 = 106;
               break;
            default:
               var10003 = 65;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
