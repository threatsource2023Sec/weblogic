package org.hibernate.validator.resourceloading;

import java.util.Locale;
import java.util.ResourceBundle;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

public abstract class DelegatingResourceBundleLocator implements ResourceBundleLocator {
   private final ResourceBundleLocator delegate;

   public DelegatingResourceBundleLocator(ResourceBundleLocator delegate) {
      this.delegate = delegate;
   }

   public ResourceBundle getResourceBundle(Locale locale) {
      return this.delegate == null ? null : this.delegate.getResourceBundle(locale);
   }
}
