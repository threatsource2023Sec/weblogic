package org.hibernate.validator.resourceloading;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.logging.Messages;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.hibernate.validator.internal.util.privilegedactions.GetResources;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.jboss.logging.Logger;

public class PlatformResourceBundleLocator implements ResourceBundleLocator {
   private static final Logger log = Logger.getLogger(PlatformResourceBundleLocator.class.getName());
   private static final boolean RESOURCE_BUNDLE_CONTROL_INSTANTIABLE = determineAvailabilityOfResourceBundleControl();
   private final String bundleName;
   private final ClassLoader classLoader;
   private final boolean aggregate;

   public PlatformResourceBundleLocator(String bundleName) {
      this(bundleName, (ClassLoader)null);
   }

   public PlatformResourceBundleLocator(String bundleName, ClassLoader classLoader) {
      this(bundleName, classLoader, false);
   }

   public PlatformResourceBundleLocator(String bundleName, ClassLoader classLoader, boolean aggregate) {
      Contracts.assertNotNull(bundleName, "bundleName");
      this.bundleName = bundleName;
      this.classLoader = classLoader;
      this.aggregate = aggregate && RESOURCE_BUNDLE_CONTROL_INSTANTIABLE;
   }

   public ResourceBundle getResourceBundle(Locale locale) {
      ResourceBundle rb = null;
      if (this.classLoader != null) {
         rb = this.loadBundle(this.classLoader, locale, this.bundleName + " not found by user-provided classloader");
      }

      ClassLoader classLoader;
      if (rb == null) {
         classLoader = (ClassLoader)run(GetClassLoader.fromContext());
         if (classLoader != null) {
            rb = this.loadBundle(classLoader, locale, this.bundleName + " not found by thread context classloader");
         }
      }

      if (rb == null) {
         classLoader = (ClassLoader)run(GetClassLoader.fromClass(PlatformResourceBundleLocator.class));
         rb = this.loadBundle(classLoader, locale, this.bundleName + " not found by validator classloader");
      }

      if (rb != null) {
         log.debugf("%s found.", this.bundleName);
      } else {
         log.debugf("%s not found.", this.bundleName);
      }

      return rb;
   }

   private ResourceBundle loadBundle(ClassLoader classLoader, Locale locale, String message) {
      ResourceBundle rb = null;

      try {
         if (this.aggregate) {
            rb = ResourceBundle.getBundle(this.bundleName, locale, classLoader, PlatformResourceBundleLocator.AggregateResourceBundle.CONTROL);
         } else {
            rb = ResourceBundle.getBundle(this.bundleName, locale, classLoader);
         }
      } catch (MissingResourceException var6) {
         log.trace(message);
      }

      return rb;
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }

   private static boolean determineAvailabilityOfResourceBundleControl() {
      try {
         ResourceBundle.Control dummyControl = PlatformResourceBundleLocator.AggregateResourceBundle.CONTROL;
         if (dummyControl == null) {
            return false;
         } else {
            Method getModule = (Method)run(GetMethod.action(Class.class, "getModule"));
            if (getModule == null) {
               return true;
            } else {
               Object module = getModule.invoke(PlatformResourceBundleLocator.class);
               Method isNamedMethod = (Method)run(GetMethod.action(module.getClass(), "isNamed"));
               boolean isNamed = (Boolean)isNamedMethod.invoke(module);
               return !isNamed;
            }
         }
      } catch (Throwable var5) {
         log.info(Messages.MESSAGES.unableToUseResourceBundleAggregation());
         return false;
      }
   }

   private static class AggregateResourceBundleControl extends ResourceBundle.Control {
      private AggregateResourceBundleControl() {
      }

      public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
         if (!"java.properties".equals(format)) {
            return super.newBundle(baseName, locale, format, loader, reload);
         } else {
            String resourceName = this.toBundleName(baseName, locale) + ".properties";
            Properties properties = this.load(resourceName, loader);
            return properties.size() == 0 ? null : new AggregateResourceBundle(properties);
         }
      }

      private Properties load(String resourceName, ClassLoader loader) throws IOException {
         Properties aggregatedProperties = new Properties();
         Enumeration urls = (Enumeration)PlatformResourceBundleLocator.run(GetResources.action(loader, resourceName));

         while(urls.hasMoreElements()) {
            URL url = (URL)urls.nextElement();
            Properties properties = new Properties();
            properties.load(url.openStream());
            aggregatedProperties.putAll(properties);
         }

         return aggregatedProperties;
      }

      // $FF: synthetic method
      AggregateResourceBundleControl(Object x0) {
         this();
      }
   }

   private static class AggregateResourceBundle extends ResourceBundle {
      protected static final ResourceBundle.Control CONTROL = new AggregateResourceBundleControl();
      private final Properties properties;

      protected AggregateResourceBundle(Properties properties) {
         this.properties = properties;
      }

      protected Object handleGetObject(String key) {
         return this.properties.get(key);
      }

      public Enumeration getKeys() {
         Set keySet = CollectionHelper.newHashSet();
         keySet.addAll(this.properties.stringPropertyNames());
         if (this.parent != null) {
            keySet.addAll(Collections.list(this.parent.getKeys()));
         }

         return Collections.enumeration(keySet);
      }
   }
}
