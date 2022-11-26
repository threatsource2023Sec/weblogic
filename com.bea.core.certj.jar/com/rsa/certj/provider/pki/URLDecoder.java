package com.rsa.certj.provider.pki;

import com.rsa.certj.spi.pki.PKIException;

/** @deprecated */
public final class URLDecoder {
   private URLDecoder() {
   }

   /** @deprecated */
   public static String decode(String var0) throws PKIException {
      StringBuffer var1 = new StringBuffer();
      int var2 = 0;

      while(var2 < var0.length()) {
         char var3 = var0.charAt(var2);
         switch (var3) {
            case '%':
               try {
                  var1.append((char)Integer.parseInt(var0.substring(var2 + 1, var2 + 3), 16));
               } catch (NumberFormatException var5) {
                  throw new PKIException("URLDecoder.decode: illegal character after %[" + var0.substring(var2 + 1, var2 + 3) + "].", var5);
               }

               var2 += 3;
               break;
            case '+':
               var1.append(' ');
               ++var2;
               break;
            default:
               var1.append(var3);
               ++var2;
         }
      }

      return var1.toString();
   }
}
