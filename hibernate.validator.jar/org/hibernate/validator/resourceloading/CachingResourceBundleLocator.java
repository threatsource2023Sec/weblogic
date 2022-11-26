package org.hibernate.validator.resourceloading;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

public class CachingResourceBundleLocator extends DelegatingResourceBundleLocator {
   private final ConcurrentMap bundleCache = new ConcurrentHashMap();

   public CachingResourceBundleLocator(ResourceBundleLocator delegate) {
      super(delegate);
   }

   public ResourceBundle getResourceBundle(Locale locale) {
      ResourceBundle cachedResourceBundle = (ResourceBundle)this.bundleCache.get(locale);
      if (cachedResourceBundle == null) {
         ResourceBundle bundle = super.getResourceBundle(locale);
         if (bundle != null) {
            cachedResourceBundle = (ResourceBundle)this.bundleCache.putIfAbsent(locale, bundle);
            if (cachedResourceBundle == null) {
               return bundle;
            }
         }
      }

      return cachedResourceBundle;
   }
}
