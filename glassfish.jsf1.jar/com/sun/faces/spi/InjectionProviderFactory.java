package com.sun.faces.spi;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import com.sun.faces.vendor.WebContainerInjectionProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;

public class InjectionProviderFactory {
   private static final InjectionProvider NOOP_PROVIDER = new NoopInjectionProvider();
   private static final InjectionProvider GENERIC_WEB_PROVIDER = new WebContainerInjectionProvider();
   private static final String INJECTION_SERVICE = "META-INF/services/com.sun.faces.spi.injectionprovider";
   private static final String INJECTION_PROVIDER_PROPERTY = "com.sun.faces.InjectionProvider";
   private static final Logger LOGGER;
   private static final String[] EMPTY_ARRAY;

   public static InjectionProvider createInstance(ExternalContext extContext) {
      String providerClass = findProviderClass(extContext);
      InjectionProvider provider = getProviderInstance(providerClass, extContext);
      if (!NoopInjectionProvider.class.equals(provider.getClass()) && !WebContainerInjectionProvider.class.equals(provider.getClass())) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "jsf.spi.injection.provider_configured", new Object[]{provider.getClass().getName()});
         }

         return provider;
      } else if (WebContainerInjectionProvider.class.equals(provider.getClass())) {
         if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.info("jsf.core.injection.provider_generic_web_configured");
         }

         return provider;
      } else {
         if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "jsf.spi.injection.no_injection");
         }

         return provider;
      }
   }

   private static InjectionProvider getProviderInstance(String className, ExternalContext extContext) {
      InjectionProvider provider = NOOP_PROVIDER;
      if (className != null) {
         try {
            Class clazz = Util.loadClass(className, InjectionProviderFactory.class);
            if (implementsInjectionProvider(clazz)) {
               try {
                  Constructor ctor = clazz.getConstructor(ServletContext.class);
                  return (InjectionProvider)ctor.newInstance((ServletContext)extContext.getContext());
               } catch (NoSuchMethodException var6) {
                  return (InjectionProvider)clazz.newInstance();
               } catch (InvocationTargetException var7) {
                  if (LOGGER.isLoggable(Level.SEVERE)) {
                     LOGGER.log(Level.SEVERE, "jsf.spi.injection.provider_cannot_instantiate", new Object[]{className});
                     LOGGER.log(Level.SEVERE, "", var7);
                  }
               }
            } else if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "jsf.spi.injection.provider_not_implemented", new Object[]{className});
            }
         } catch (ClassNotFoundException var8) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "jsf.spi.injection.provider_not_found", new Object[]{className});
            }
         } catch (InstantiationException var9) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "jsf.spi.injection.provider_cannot_instantiate", new Object[]{className});
               LOGGER.log(Level.SEVERE, "", var9);
            }
         } catch (IllegalAccessException var10) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "jsf.spi.injection.provider_cannot_instantiate", new Object[]{className});
               LOGGER.log(Level.SEVERE, "", var10);
            }
         }
      }

      if (NOOP_PROVIDER.equals(provider)) {
         try {
            if (Util.loadClass("javax.annotation.PostConstruct", (Object)null) != null && Util.loadClass("javax.annotation.PreDestroy", (Object)null) != null) {
               provider = GENERIC_WEB_PROVIDER;
            }
         } catch (Exception var5) {
            provider = NOOP_PROVIDER;
         }
      }

      return provider;
   }

   private static boolean implementsInjectionProvider(Class clazz) {
      return InjectionProvider.class.isAssignableFrom(clazz);
   }

   private static boolean extendsDiscoverableInjectionProvider(Class clazz) {
      return DiscoverableInjectionProvider.class.isAssignableFrom(clazz);
   }

   private static String findProviderClass(ExternalContext extContext) {
      WebConfiguration webConfig = WebConfiguration.getInstance(extContext);
      String provider = webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.InjectionProviderClass);
      if (provider != null) {
         return provider;
      } else {
         provider = System.getProperty("com.sun.faces.InjectionProvider");
         if (provider != null) {
            return provider;
         } else {
            String[] serviceEntries = getServiceEntries();
            if (serviceEntries.length <= 0) {
               return provider;
            } else {
               for(int i = 0; i < serviceEntries.length; ++i) {
                  provider = getProviderFromEntry(serviceEntries[i]);
                  if (provider != null) {
                     break;
                  }
               }

               return provider;
            }
         }
      }
   }

   private static String getProviderFromEntry(String entry) {
      if (entry == null) {
         return null;
      } else {
         String[] parts = Util.split(entry, ":");
         if (parts.length != 2) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "jsf.spi.injection.invalid_service_entry", new Object[]{entry});
            }

            return null;
         } else {
            try {
               Class clazz = Util.loadClass(parts[0], (Object)null);
               if (extendsDiscoverableInjectionProvider(clazz)) {
                  return DiscoverableInjectionProvider.isInjectionFeatureAvailable(parts[1]) ? parts[0] : null;
               } else {
                  if (LOGGER.isLoggable(Level.SEVERE)) {
                     LOGGER.log(Level.SEVERE, "jsf.spi.injection.provider.entry_not_discoverable", new Object[]{parts[0]});
                  }

                  return null;
               }
            } catch (ClassNotFoundException var3) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "jsf.spi.injection.provider_not_found", new Object[]{parts[0]});
               }

               return null;
            }
         }
      }
   }

   private static String[] getServiceEntries() {
      List results = null;
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      if (loader == null) {
         return EMPTY_ARRAY;
      } else {
         Enumeration urls = null;

         try {
            urls = loader.getResources("META-INF/services/com.sun.faces.spi.injectionprovider");
         } catch (IOException var25) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, var25.toString(), var25);
            }
         }

         if (urls != null) {
            InputStream input = null;
            BufferedReader reader = null;

            while(urls.hasMoreElements()) {
               try {
                  if (results == null) {
                     results = new ArrayList();
                  }

                  URL url = (URL)urls.nextElement();
                  URLConnection conn = url.openConnection();
                  conn.setUseCaches(false);
                  input = conn.getInputStream();
                  if (input != null) {
                     try {
                        reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
                     } catch (Exception var22) {
                        reader = new BufferedReader(new InputStreamReader(input));
                     }

                     for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                        results.add(line.trim());
                     }
                  }
               } catch (Exception var23) {
                  if (LOGGER.isLoggable(Level.SEVERE)) {
                     LOGGER.log(Level.SEVERE, "jsf.spi.injection.provider.cannot_read_service", var23);
                  }
               } finally {
                  if (input != null) {
                     try {
                        input.close();
                     } catch (Exception var21) {
                     }
                  }

                  if (reader != null) {
                     try {
                        reader.close();
                     } catch (Exception var20) {
                     }
                  }

               }
            }
         }

         return results != null && !results.isEmpty() ? (String[])results.toArray(new String[results.size()]) : EMPTY_ARRAY;
      }
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
      EMPTY_ARRAY = new String[0];
   }

   private static final class NoopInjectionProvider implements InjectionProvider {
      private NoopInjectionProvider() {
      }

      public void inject(Object managedBean) {
      }

      public void invokePreDestroy(Object managedBean) {
      }

      public void invokePostConstruct(Object managedBean) throws InjectionProviderException {
      }

      // $FF: synthetic method
      NoopInjectionProvider(Object x0) {
         this();
      }
   }
}
