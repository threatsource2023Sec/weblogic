package org.python.apache.xerces.impl.dv.xs;

import java.io.UnsupportedEncodingException;
import org.python.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.python.apache.xerces.impl.dv.ValidationContext;
import org.python.apache.xerces.util.URI;

public class AnyURIDV extends TypeValidator {
   private static final URI BASE_URI;
   private static boolean[] gNeedEscaping;
   private static char[] gAfterEscaping1;
   private static char[] gAfterEscaping2;
   private static char[] gHexChs;

   public short getAllowedFacets() {
      return 2079;
   }

   public Object getActualValue(String var1, ValidationContext var2) throws InvalidDatatypeValueException {
      try {
         if (var1.length() != 0) {
            String var3 = encode(var1);
            new URI(BASE_URI, var3);
         }

         return var1;
      } catch (URI.MalformedURIException var4) {
         throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{var1, "anyURI"});
      }
   }

   private static String encode(String var0) {
      int var1 = var0.length();
      StringBuffer var2 = new StringBuffer(var1 * 3);

      int var3;
      int var4;
      for(var3 = 0; var3 < var1; ++var3) {
         var4 = var0.charAt(var3);
         if (var4 >= 128) {
            break;
         }

         if (gNeedEscaping[var4]) {
            var2.append('%');
            var2.append(gAfterEscaping1[var4]);
            var2.append(gAfterEscaping2[var4]);
         } else {
            var2.append((char)var4);
         }
      }

      if (var3 < var1) {
         Object var5 = null;

         byte[] var9;
         try {
            var9 = var0.substring(var3).getBytes("UTF-8");
         } catch (UnsupportedEncodingException var8) {
            return var0;
         }

         var1 = var9.length;

         for(var3 = 0; var3 < var1; ++var3) {
            byte var7 = var9[var3];
            if (var7 < 0) {
               var4 = var7 + 256;
               var2.append('%');
               var2.append(gHexChs[var4 >> 4]);
               var2.append(gHexChs[var4 & 15]);
            } else if (gNeedEscaping[var7]) {
               var2.append('%');
               var2.append(gAfterEscaping1[var7]);
               var2.append(gAfterEscaping2[var7]);
            } else {
               var2.append((char)var7);
            }
         }
      }

      return var2.length() != var1 ? var2.toString() : var0;
   }

   static {
      URI var0 = null;

      try {
         var0 = new URI("abc://def.ghi.jkl");
      } catch (URI.MalformedURIException var5) {
      }

      BASE_URI = var0;
      gNeedEscaping = new boolean[128];
      gAfterEscaping1 = new char[128];
      gAfterEscaping2 = new char[128];
      gHexChs = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

      for(int var6 = 0; var6 <= 31; ++var6) {
         gNeedEscaping[var6] = true;
         gAfterEscaping1[var6] = gHexChs[var6 >> 4];
         gAfterEscaping2[var6] = gHexChs[var6 & 15];
      }

      gNeedEscaping[127] = true;
      gAfterEscaping1[127] = '7';
      gAfterEscaping2[127] = 'F';
      char[] var1 = new char[]{' ', '<', '>', '"', '{', '}', '|', '\\', '^', '~', '`'};
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var4 = var1[var3];
         gNeedEscaping[var4] = true;
         gAfterEscaping1[var4] = gHexChs[var4 >> 4];
         gAfterEscaping2[var4] = gHexChs[var4 & 15];
      }

   }
}
