package netscape.ldap.client;

import java.io.UnsupportedEncodingException;
import java.util.Vector;
import netscape.ldap.ber.stream.BEROctetString;

public class JDAPFilterOpers {
   private static final String escapeKey = "\\";
   private static final boolean m_debug = false;

   static BEROctetString getOctetString(String var0) {
      if (var0.indexOf("\\") >= 0) {
         byte[] var1 = getByteValues(var0);
         return new BEROctetString(var1);
      } else {
         return new BEROctetString(var0);
      }
   }

   public static String convertLDAPv2Escape(String var0) {
      if (var0.indexOf(92) < 0) {
         return var0;
      } else {
         StringBuffer var1 = new StringBuffer();
         boolean var2 = false;
         int var3 = 0;
         int var4 = var0.length();

         int var7;
         while(var3 < var4 && (var7 = var0.indexOf(92, var3)) >= 0) {
            var1.append(var0.substring(var3, var7 + 1));

            try {
               char var5 = var0.charAt(var7 + 1);
               if (var5 >= ' ' && var5 < 127 && !isHexDigit(var5)) {
                  var1.append(Integer.toHexString(var5));
               } else {
                  var1.append(var5);
               }

               var3 = var7 + 2;
            } catch (IndexOutOfBoundsException var6) {
               throw new IllegalArgumentException("Bad search filter");
            }
         }

         if (var3 < var4) {
            var1.append(var0.substring(var3));
         }

         return var1.toString();
      }
   }

   private static boolean isHexDigit(char var0) {
      return var0 >= '0' && var0 <= '9' || var0 >= 'a' && var0 <= 'f' || var0 >= 'A' && var0 <= 'F';
   }

   static byte[] getByteValues(String var0) {
      boolean var1 = false;
      Vector var2 = new Vector();
      String var3 = new String(var0);

      int var4;
      int var12;
      byte[] var16;
      for(var4 = 0; (var12 = var3.indexOf("\\")) >= 0; var3 = var3.substring(var12 + 3)) {
         String var5 = var3.substring(0, var12);

         try {
            byte[] var6 = var5.getBytes("UTF8");
            var4 += var6.length;
            var2.addElement(var6);
         } catch (UnsupportedEncodingException var11) {
            printDebug(var11.toString());
            return null;
         }

         Integer var14 = null;

         try {
            String var7 = "0x" + var3.substring(var12 + 1, var12 + 3);
            var14 = Integer.decode(var7);
         } catch (IndexOutOfBoundsException var9) {
            printDebug(var9.toString());
            throw new IllegalArgumentException("Bad search filter");
         } catch (NumberFormatException var10) {
            printDebug(var10.toString());
            throw new IllegalArgumentException("Bad search filter");
         }

         var16 = new byte[]{(byte)var14};
         var4 += var16.length;
         var2.addElement(var16);
      }

      byte[] var13;
      if (var3.length() > 0) {
         try {
            var13 = var3.getBytes("UTF8");
            var4 += var13.length;
            var2.addElement(var13);
         } catch (UnsupportedEncodingException var8) {
            printDebug(var8.toString());
            return null;
         }
      }

      var13 = new byte[var4];
      var12 = 0;

      for(int var15 = 0; var15 < var2.size(); ++var15) {
         var16 = (byte[])((byte[])var2.elementAt(var15));
         System.arraycopy(var16, 0, var13, var12, var16.length);
         var12 += var16.length;
      }

      return var13;
   }

   private static void printDebug(String var0) {
   }
}
