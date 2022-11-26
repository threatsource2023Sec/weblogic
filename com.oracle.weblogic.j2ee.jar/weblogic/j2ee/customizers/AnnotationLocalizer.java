package weblogic.j2ee.customizers;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import weblogic.logging.NonCatalogLogger;

public final class AnnotationLocalizer {
   private static final String DESCRIPTION_KEY_SUFFIX = ".shortDescription";
   private static NonCatalogLogger _logger = new NonCatalogLogger(AnnotationLocalizer.class.getName());

   static String getShortDescription(String bundleName, String key) {
      assert bundleName != null;

      assert key != null;

      String shortDescription = null;
      Locale currentLocale = Locale.getDefault();
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      ResourceBundle bundle = null;

      try {
         bundle = ResourceBundle.getBundle(bundleName, currentLocale, loader);
      } catch (MissingResourceException var7) {
         bundle = null;
         _logger.debug("No resource bundle " + bundleName);
      }

      if (bundle != null) {
         shortDescription = bundle.getString(key + ".shortDescription");
         _logger.debug("Obtained description from resource bundle, " + shortDescription);
      }

      if (shortDescription == null) {
         shortDescription = key;
         _logger.debug("No description defined, using default, " + key);
      }

      return shortDescription;
   }
}
