package javax.enterprise.inject.spi;

import java.util.Collections;
import java.util.Comparator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;
import javax.enterprise.inject.Instance;

public abstract class CDI implements Instance {
   private static final Object lock = new Object();
   protected static volatile Set discoveredProviders = null;
   protected static volatile CDIProvider configuredProvider = null;

   public static CDI current() {
      return getCDIProvider().getCDI();
   }

   private static CDIProvider getCDIProvider() {
      if (configuredProvider != null) {
         return configuredProvider;
      } else {
         if (discoveredProviders == null) {
            synchronized(lock) {
               if (discoveredProviders == null) {
                  findAllProviders();
               }
            }
         }

         configuredProvider = (CDIProvider)discoveredProviders.stream().filter((c) -> {
            return c.getCDI() != null;
         }).findFirst().orElseThrow(() -> {
            return new IllegalStateException("Unable to access CDI");
         });
         return configuredProvider;
      }
   }

   public static void setCDIProvider(CDIProvider provider) {
      if (provider != null) {
         configuredProvider = provider;
      } else {
         throw new IllegalStateException("CDIProvider must not be null");
      }
   }

   private static void findAllProviders() {
      Set providers = new TreeSet(Comparator.comparingInt(CDIProvider::getPriority).reversed());
      ServiceLoader providerLoader = SecurityActions.loadService(CDIProvider.class, CDI.class.getClassLoader());
      if (!providerLoader.iterator().hasNext()) {
         throw new IllegalStateException("Unable to locate CDIProvider");
      } else {
         try {
            providerLoader.forEach(providers::add);
         } catch (ServiceConfigurationError var3) {
            throw new IllegalStateException(var3);
         }

         discoveredProviders = Collections.unmodifiableSet(providers);
      }
   }

   public abstract BeanManager getBeanManager();
}
