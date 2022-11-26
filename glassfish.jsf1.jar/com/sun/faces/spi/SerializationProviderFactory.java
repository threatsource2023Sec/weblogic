package com.sun.faces.spi;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.renderkit.ApplicationObjectInputStream;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;

public class SerializationProviderFactory {
   private static final SerializationProvider JAVA_PROVIDER = new JavaSerializationProvider();
   private static final String SERIALIZATION_PROVIDER_PROPERTY = "com.sun.faces.SerializationProvider";
   private static final Logger LOGGER;

   public static SerializationProvider createInstance(ExternalContext extContext) {
      String providerClass = findProviderClass(extContext);
      SerializationProvider provider = getProviderInstance(providerClass);
      if (provider.getClass() != JavaSerializationProvider.class && LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "jsf.spi.serialization.provider_configured", new Object[]{provider.getClass().getName()});
      }

      return provider;
   }

   private static SerializationProvider getProviderInstance(String className) {
      SerializationProvider provider = JAVA_PROVIDER;
      if (className != null) {
         try {
            Class clazz = Util.loadClass(className, SerializationProviderFactory.class);
            if (implementsSerializationProvider(clazz)) {
               provider = (SerializationProvider)clazz.newInstance();
            } else if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "jsf.spi.serialization.provider_not_implemented", new Object[]{className});
            }
         } catch (ClassNotFoundException var3) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "jsf.spi.serialization.provider_not_found", new Object[]{className});
            }
         } catch (InstantiationException var4) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "jsf.spi.serialization.provider_cannot_instantiate", new Object[]{className});
               LOGGER.log(Level.SEVERE, "", var4);
            }
         } catch (IllegalAccessException var5) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "jsf.spi.serialization.provider_cannot_instantiate", new Object[]{className});
               LOGGER.log(Level.SEVERE, "", var5);
            }
         }
      }

      return provider;
   }

   private static boolean implementsSerializationProvider(Class clazz) {
      return SerializationProvider.class.isAssignableFrom(clazz);
   }

   private static String findProviderClass(ExternalContext extContext) {
      WebConfiguration webConfig = WebConfiguration.getInstance(extContext);
      String provider = webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.SerializationProviderClass);
      return provider != null ? provider : System.getProperty("com.sun.faces.SerializationProvider");
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }

   private static final class JavaSerializationProvider implements SerializationProvider {
      private JavaSerializationProvider() {
      }

      public ObjectOutputStream createObjectOutputStream(OutputStream destination) throws IOException {
         return new ObjectOutputStream(destination);
      }

      public ObjectInputStream createObjectInputStream(InputStream source) throws IOException {
         return new ApplicationObjectInputStream(source);
      }

      // $FF: synthetic method
      JavaSerializationProvider(Object x0) {
         this();
      }
   }
}
