package com.rsa.certj.cert.extensions;

import com.rsa.certj.cert.NameException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.StringTokenizer;

/** @deprecated */
final class NameUtils {
   private NameUtils() {
   }

   /** @deprecated */
   public static void validateURLName(String var0) throws NameException {
      if (var0.length() > 6 && var0.substring(0, 6).equalsIgnoreCase("mailto")) {
         try {
            URI var1 = new URI(var0);
            if (!var1.isAbsolute()) {
               throw new NameException("Invalid URL");
            }
         } catch (URISyntaxException var3) {
            throw new NameException(var3);
         }
      } else {
         try {
            new URL(var0);
         } catch (MalformedURLException var2) {
            throw new NameException(var2);
         }
      }

   }

   /** @deprecated */
   public static void validateRFC822Name(String var0) throws NameException {
      if (var0 == null) {
         throw new NameException("Name is null!");
      } else {
         int var1 = var0.indexOf(64);
         if (var1 == -1) {
            throw new NameException("An RFC822 name must contain the @ symbol.");
         } else if (var0.indexOf(64, var1 + 1) != -1) {
            throw new NameException("An RFC822 name can contain only one @ symbol.");
         } else {
            b(var0.substring(0, var1));
            a(var0.substring(var1 + 1));
         }
      }
   }

   private static void a(String var0) throws NameException {
      if (var0.length() == 0) {
         throw new NameException("Illegal empty domain name!");
      } else {
         a var3 = new a(var0);

         while(true) {
            while(var3.b()) {
               String var4 = var3.a();
               if (var4.startsWith("[")) {
                  String[] var5 = new String[4];
                  var5[0] = var4.substring(1);

                  for(int var6 = 1; var6 < var5.length; ++var6) {
                     if (!var3.b()) {
                        throw new NameException("Illegal domain name: " + var0);
                     }

                     var5[var6] = var3.a();
                  }

                  if (!var5[3].endsWith("]")) {
                     throw new NameException("Illegal domain name: " + var0);
                  }

                  String var7 = var5[3];
                  var5[3] = var7.substring(0, var7.length() - 1);
                  a(var5);
               } else {
                  d(var4);
               }
            }

            return;
         }
      }
   }

   private static void a(String[] var0) throws NameException {
      for(int var3 = 0; var3 < var0.length; ++var3) {
         int var4 = Integer.parseInt(var0[var3]);
         if (var4 < 0 || var4 > 255) {
            throw new NameException("Invalid domain literal part: " + var0[var3]);
         }
      }

   }

   private static void b(String var0) throws NameException {
      if (var0.length() == 0) {
         throw new NameException("Empty local part is not allowed in an RFC822 name");
      } else {
         String var2;
         for(a var1 = new a(var0); var1.b(); c(var2)) {
            var2 = var1.a();
            if (var2.startsWith("\"")) {
               while(var1.b() && !var2.endsWith("\"")) {
                  var2 = var2 + var1.a();
               }
            }
         }

      }
   }

   private static void c(String var0) throws NameException {
      if (var0.length() == 0) {
         throw new NameException("Invalid word in local part of RFC822 name: " + var0);
      } else {
         if (var0.startsWith("\"")) {
            e(var0);
         } else {
            d(var0);
         }

      }
   }

   private static void d(String var0) throws NameException {
      if (var0.length() == 0) {
         throw new NameException("An atom must be at least 1 character long!");
      } else {
         for(int var4 = 0; var4 < var0.length(); ++var4) {
            char var5 = var0.charAt(var4);
            if (var5 < ' ' || var5 == 127) {
               throw new NameException("Illegal character in atom " + var0);
            }

            if (" ()<>@,;:\\/\".[]".indexOf(var5) != -1) {
               throw new NameException("Atom " + var0 + " contains the special character " + var5);
            }
         }

      }
   }

   private static void e(String var0) throws NameException {
      int var1 = var0.length() - 1;
      if (var0.length() >= 2 && var0.charAt(0) == '"' && var0.charAt(var1) == '"') {
         for(int var2 = 1; var2 < var1; ++var2) {
            char var3 = var0.charAt(var2);
            if (var3 == '\\') {
               ++var2;
               if (var2 == var1) {
                  throw new NameException("\\ escapes the closing quote in " + var0);
               }
            } else {
               if (var3 == '"') {
                  throw new NameException("Illegal character " + var3 + " in " + var0);
               }

               if (var3 == '\r') {
                  ++var2;
                  var3 = var0.charAt(var2);
                  if (var3 != '\n') {
                     throw new NameException("Illegal CR character in " + var0);
                  }

                  ++var2;
                  var3 = var0.charAt(var2);
                  if (var3 != ' ' && var3 != '\t') {
                     throw new NameException("CRLF sequence in " + var0 + " must be followed by a space or a tab.");
                  }
               }
            }
         }

      } else {
         throw new NameException("Illegal quoted string: " + var0);
      }
   }

   /** @deprecated */
   public static void validateDnsName(String var0) throws NameException {
      if (var0 == null) {
         throw new NameException("Name is null!");
      } else if (!var0.equals(" ")) {
         a var1 = new a(var0);

         while(var1.b()) {
            String var2 = var1.a();
            f(var2);
         }

      }
   }

   private static boolean a(char var0) {
      return var0 >= 'a' && var0 <= 'z' || var0 >= 'A' && var0 <= 'Z';
   }

   private static boolean b(char var0) {
      return var0 >= '0' && var0 <= '9';
   }

   private static void f(String var0) throws NameException {
      if (var0.length() == 0) {
         throw new NameException("A subdomain cannot be an empty string!");
      } else if (!a(var0.charAt(0))) {
         throw new NameException("A subdomain must start with a letter!");
      } else if (var0.charAt(var0.length() - 1) == '-') {
         throw new NameException("The last character in a subdomain cannot be a hyphen!");
      } else {
         for(int var1 = 0; var1 < var0.length(); ++var1) {
            if (!c(var0.charAt(var1))) {
               throw new NameException("Invalid character in domain name: " + var0.charAt(var1));
            }
         }

      }
   }

   private static boolean c(char var0) {
      return var0 == '-' || a(var0) || b(var0);
   }

   private static class a {
      private static final String a = ".";
      private static final String b = "";
      private StringTokenizer c;
      private boolean d;

      a(String var1) {
         this.c = new StringTokenizer(var1, ".", true);
         this.d = this.c.hasMoreTokens();
      }

      public String a() {
         if (this.d && !this.c.hasMoreTokens()) {
            this.d = false;
            return "";
         } else {
            String var1 = this.c.nextToken();
            if (".".equals(var1)) {
               return "";
            } else {
               if (!this.c.hasMoreTokens()) {
                  this.d = false;
               } else {
                  this.c.nextToken();
               }

               return var1;
            }
         }
      }

      public boolean b() {
         return this.d;
      }
   }
}
