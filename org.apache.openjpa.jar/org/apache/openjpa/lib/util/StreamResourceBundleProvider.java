package org.apache.openjpa.lib.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

class StreamResourceBundleProvider implements ResourceBundleProvider {
   public ResourceBundle findResource(String name, Locale locale, ClassLoader loader) {
      String rsrc = name.replace('.', '/') + ".properties";
      if (loader == null) {
         loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
      }

      InputStream in = loader.getResourceAsStream(rsrc);
      if (in != null) {
         PropertyResourceBundle var6;
         try {
            var6 = new PropertyResourceBundle(in);
         } catch (Exception var16) {
            return null;
         } finally {
            try {
               in.close();
            } catch (IOException var15) {
            }

         }

         return var6;
      } else {
         return null;
      }
   }
}
