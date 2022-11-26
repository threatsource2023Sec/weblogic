package org.apache.openjpa.lib.util;

import java.util.Locale;
import java.util.ResourceBundle;

class SimpleResourceBundleProvider implements ResourceBundleProvider {
   public ResourceBundle findResource(String name, Locale locale, ClassLoader loader) {
      ResourceBundle bundle = null;
      if (loader != null) {
         try {
            bundle = ResourceBundle.getBundle(name, locale, loader);
         } catch (Throwable var7) {
         }
      }

      if (bundle == null) {
         try {
            bundle = ResourceBundle.getBundle(name, locale);
         } catch (Throwable var6) {
         }
      }

      return bundle;
   }
}
