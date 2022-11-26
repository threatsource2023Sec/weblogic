package com.rsa.certj.x;

import com.rsa.jsafe.CryptoJVersion;
import com.rsa.jsafe.crypto.CryptoJ;
import java.security.Security;

public final class b {
   private static final String a = "cryptoj.prod.common.fips140.configuration";
   private static boolean b;
   private static String c = "";
   private static final boolean d = g();
   private static final String e = "com.rsa.cryptoj.autogenserialnum";
   private static final boolean f;

   private static boolean g() {
      return CryptoJ.isFIPS140Compliant();
   }

   private b() {
   }

   /** @deprecated */
   public static boolean a() {
      if (!b) {
         return false;
      } else {
         throw new a(c);
      }
   }

   public static boolean b() {
      if (!b) {
         return d;
      } else {
         throw new a(c);
      }
   }

   public static boolean c() {
      return f;
   }

   public static boolean d() {
      if (!b) {
         return false;
      } else {
         throw new a(c);
      }
   }

   public static boolean e() {
      if (!b) {
         return false;
      } else {
         throw new a(c);
      }
   }

   public static boolean f() {
      if (!b) {
         return false;
      } else {
         throw new a(c);
      }
   }

   static String a(String var0) {
      String var1 = Security.getProperty(var0);
      if (var1 != null) {
         return var1;
      } else {
         var1 = System.getProperty(var0);
         return var1;
      }
   }

   static {
      String var0 = Security.getProperty("cryptoj.prod.common.fips140.configuration");
      if (var0 != null) {
         if (var0.equalsIgnoreCase("true") && !d) {
            c = c + "FIPS 140 mode is required but FIPS 140 compliant jar file not present\n";
            b = true;
         } else if (var0.equalsIgnoreCase("false") && d) {
            c = c + "FIPS 140 mode is not expected, but the jar file is FIPS 140 compliant\n";
            b = true;
         }
      }

      f = Boolean.parseBoolean(a("com.rsa.cryptoj.autogenserialnum"));
      var0 = CryptoJVersion.getVersionString();
      String var1 = com.rsa.jsafe.crypto.CryptoJVersion.getVersionString();
      if (!var0.equals(var1)) {
         c = c + "Detected differing JSAFE API and JCE API versions.\n JSAFE: " + var0 + "\n" + " JCE: " + var1 + "\n";
         b = true;
      }

   }
}
