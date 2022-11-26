package netscape.ldap;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Locale;
import java.util.PropertyResourceBundle;

class LDAPResourceBundle implements Serializable {
   static final long serialVersionUID = -5903986665461157980L;
   private static final boolean m_debug = false;
   private static final String m_suffix = ".props";
   private static final String m_locale_separator = "_";

   static PropertyResourceBundle getBundle(String var0) throws IOException {
      return getBundle(var0, Locale.getDefault());
   }

   static PropertyResourceBundle getBundle(String var0, Locale var1) throws IOException {
      String var2 = "_" + var1.toString();

      InputStream var3;
      int var4;
      for(var3 = null; (var3 = getStream(var0, var2)) == null; var2 = var2.substring(0, var4)) {
         var4 = var2.lastIndexOf("_");
         if (var4 == -1) {
            printDebug("File " + var0 + var2 + ".props" + " not found");
            return null;
         }
      }

      PropertyResourceBundle var5 = new PropertyResourceBundle(var3);
      return var5;
   }

   private static InputStream getStream(String var0, String var1) {
      String var2 = var0 + var1 + ".props";
      return ClassLoader.getSystemResourceAsStream(var2);
   }

   private static void printDebug(String var0) {
   }
}
