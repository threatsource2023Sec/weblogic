package org.opensaml.core.config.provider;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.annotation.Nonnull;
import org.opensaml.core.config.ConfigurationPropertiesSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClasspathConfigurationPropertiesSource implements ConfigurationPropertiesSource {
   @Nonnull
   private static final String RESOURCE_NAME = "opensaml-config.properties";
   @Nonnull
   private Logger log = LoggerFactory.getLogger(ClasspathConfigurationPropertiesSource.class);
   private Properties cachedProperties;

   public Properties getProperties() {
      synchronized(this) {
         if (this.cachedProperties == null) {
            try {
               InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("opensaml-config.properties");
               Throwable var3 = null;

               try {
                  if (is != null) {
                     Properties props = new Properties();
                     props.load(is);
                     this.cachedProperties = props;
                  }
               } catch (Throwable var15) {
                  var3 = var15;
                  throw var15;
               } finally {
                  if (is != null) {
                     if (var3 != null) {
                        try {
                           is.close();
                        } catch (Throwable var14) {
                           var3.addSuppressed(var14);
                        }
                     } else {
                        is.close();
                     }
                  }

               }
            } catch (IOException var17) {
               this.log.warn("Problem attempting to load configuration properties 'opensaml-config.properties' from classpath", var17);
            }
         }

         return this.cachedProperties;
      }
   }
}
