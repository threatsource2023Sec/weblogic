package org.opensaml.core.xml.config;

import java.io.InputStream;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.Initializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXMLObjectProviderInitializer implements Initializer {
   private final Logger log = LoggerFactory.getLogger(AbstractXMLObjectProviderInitializer.class);

   public void init() throws InitializationException {
      try {
         XMLConfigurator configurator = new XMLConfigurator();
         String[] var2 = this.getConfigResources();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String resource = var2[var4];
            if (resource.startsWith("/")) {
               resource = resource.substring(1);
            }

            this.log.debug("Loading XMLObject provider configuration from resource '{}'", resource);
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
            if (is == null) {
               throw new XMLConfigurationException("Resource not found");
            }

            configurator.load(is);
         }

      } catch (XMLConfigurationException var7) {
         this.log.error("Problem loading configuration resource", var7);
         throw new InitializationException("Problem loading configuration resource", var7);
      }
   }

   protected abstract String[] getConfigResources();
}
