package com.sun.faces.spi;

import com.sun.faces.util.FacesLogger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;

public class ConfigurationResourceProviderFactory {
   private static final Logger LOGGER;

   public static ConfigurationResourceProvider[] createProviders(ProviderType providerType) {
      String[] serviceEntries = ServiceFactoryUtils.getServiceEntries(providerType.servicesKey);
      List providers = new ArrayList();
      if (serviceEntries.length > 0) {
         String[] var3 = serviceEntries;
         int var4 = serviceEntries.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String serviceEntry = var3[var5];

            try {
               ConfigurationResourceProvider provider = (ConfigurationResourceProvider)ServiceFactoryUtils.getProviderFromEntry(serviceEntry, (Class[])null, (Object[])null);
               if (provider != null) {
                  if (ConfigurationResourceProviderFactory.ProviderType.FacesConfig == providerType) {
                     if (!(provider instanceof FacesConfigResourceProvider)) {
                        throw new IllegalStateException("Expected ConfigurationResourceProvider type to be an instance of FacesConfigResourceProvider");
                     }
                  } else if (!(provider instanceof FaceletConfigResourceProvider)) {
                     throw new IllegalStateException("Expected ConfigurationResourceProvider type to be an instance of FaceletConfigResourceProvider");
                  }

                  providers.add(provider);
               }
            } catch (ClassCastException var8) {
            } catch (FacesException var9) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, var9.toString(), var9);
               }
            }
         }
      } else {
         ServiceLoader serviceLoader;
         switch (providerType) {
            case FacesConfig:
               serviceLoader = ServiceLoader.load(FacesConfigResourceProvider.class);
               break;
            case FaceletConfig:
               serviceLoader = ServiceLoader.load(FaceletConfigResourceProvider.class);
               break;
            default:
               throw new UnsupportedOperationException(providerType.servicesKey + " cannot be loaded via ServiceLoader API.");
         }

         Iterator iterator = serviceLoader.iterator();

         while(iterator.hasNext()) {
            providers.add((ConfigurationResourceProvider)iterator.next());
         }
      }

      return (ConfigurationResourceProvider[])providers.toArray(new ConfigurationResourceProvider[providers.size()]);
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }

   public static enum ProviderType {
      FacesConfig("com.sun.faces.spi.FacesConfigResourceProvider"),
      FaceletConfig("com.sun.faces.spi.FaceletConfigResourceProvider");

      String servicesKey;

      private ProviderType(String servicesKey) {
         this.servicesKey = servicesKey;
      }
   }
}
