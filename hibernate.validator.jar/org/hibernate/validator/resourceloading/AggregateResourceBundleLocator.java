package org.hibernate.validator.resourceloading;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

public class AggregateResourceBundleLocator extends DelegatingResourceBundleLocator {
   private final List bundleNames;
   private final ClassLoader classLoader;

   public AggregateResourceBundleLocator(List bundleNames) {
      this(bundleNames, (ResourceBundleLocator)null);
   }

   public AggregateResourceBundleLocator(List bundleNames, ResourceBundleLocator delegate) {
      this(bundleNames, delegate, (ClassLoader)null);
   }

   public AggregateResourceBundleLocator(List bundleNames, ResourceBundleLocator delegate, ClassLoader classLoader) {
      super(delegate);
      Contracts.assertValueNotNull(bundleNames, "bundleNames");
      this.bundleNames = CollectionHelper.toImmutableList(bundleNames);
      this.classLoader = classLoader;
   }

   public ResourceBundle getResourceBundle(Locale locale) {
      List sourceBundles = new ArrayList();
      Iterator var3 = this.bundleNames.iterator();

      while(var3.hasNext()) {
         String bundleName = (String)var3.next();
         ResourceBundleLocator resourceBundleLocator = new PlatformResourceBundleLocator(bundleName, this.classLoader);
         ResourceBundle resourceBundle = resourceBundleLocator.getResourceBundle(locale);
         if (resourceBundle != null) {
            sourceBundles.add(resourceBundle);
         }
      }

      ResourceBundle bundleFromDelegate = super.getResourceBundle(locale);
      if (bundleFromDelegate != null) {
         sourceBundles.add(bundleFromDelegate);
      }

      return sourceBundles.isEmpty() ? null : new AggregateBundle(sourceBundles);
   }

   private static class IteratorEnumeration implements Enumeration {
      private final Iterator source;

      public IteratorEnumeration(Iterator source) {
         if (source == null) {
            throw new IllegalArgumentException("Source must not be null");
         } else {
            this.source = source;
         }
      }

      public boolean hasMoreElements() {
         return this.source.hasNext();
      }

      public Object nextElement() {
         return this.source.next();
      }
   }

   public static class AggregateBundle extends ResourceBundle {
      private final Map contents = new HashMap();

      public AggregateBundle(List bundles) {
         if (bundles != null) {
            Iterator var2 = bundles.iterator();

            while(var2.hasNext()) {
               ResourceBundle bundle = (ResourceBundle)var2.next();
               Enumeration keys = bundle.getKeys();

               while(keys.hasMoreElements()) {
                  String oneKey = (String)keys.nextElement();
                  if (!this.contents.containsKey(oneKey)) {
                     this.contents.put(oneKey, bundle.getObject(oneKey));
                  }
               }
            }
         }

      }

      public Enumeration getKeys() {
         return new IteratorEnumeration(this.contents.keySet().iterator());
      }

      protected Object handleGetObject(String key) {
         return this.contents.get(key);
      }
   }
}
