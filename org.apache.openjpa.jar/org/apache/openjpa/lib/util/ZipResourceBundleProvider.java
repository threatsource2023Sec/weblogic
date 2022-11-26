package org.apache.openjpa.lib.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

class ZipResourceBundleProvider implements ResourceBundleProvider {
   public ResourceBundle findResource(String name, Locale locale, ClassLoader loader) {
      String rsrc = name.replace('.', '/') + ".properties";
      if (loader == null) {
         loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
      }

      InputStream in = loader.getResourceAsStream(rsrc);
      if (in == null) {
         return null;
      } else {
         ZipInputStream zip = new ZipInputStream(in);

         try {
            while(true) {
               ZipEntry ze = zip.getNextEntry();
               if (ze == null) {
                  return null;
               }

               if (rsrc.equals(ze.getName())) {
                  PropertyResourceBundle var8 = new PropertyResourceBundle(zip);
                  return var8;
               }

               zip.closeEntry();
            }
         } catch (Exception var19) {
            return null;
         } finally {
            try {
               zip.close();
            } catch (IOException var18) {
            }

         }
      }
   }
}
