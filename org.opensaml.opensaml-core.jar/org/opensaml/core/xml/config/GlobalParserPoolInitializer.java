package org.opensaml.core.xml.config;

import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.xml.BasicParserPool;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.Initializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalParserPoolInitializer implements Initializer {
   private Logger log = LoggerFactory.getLogger(GlobalParserPoolInitializer.class);

   public void init() throws InitializationException {
      BasicParserPool pp = new BasicParserPool();
      pp.setMaxPoolSize(50);

      try {
         pp.initialize();
      } catch (ComponentInitializationException var5) {
         throw new InitializationException("Error initializing parser pool", var5);
      }

      XMLObjectProviderRegistry registry = null;
      Class var3 = ConfigurationService.class;
      synchronized(ConfigurationService.class) {
         registry = (XMLObjectProviderRegistry)ConfigurationService.get(XMLObjectProviderRegistry.class);
         if (registry == null) {
            this.log.debug("XMLObjectProviderRegistry did not exist in ConfigurationService, will be created");
            registry = new XMLObjectProviderRegistry();
            ConfigurationService.register(XMLObjectProviderRegistry.class, registry);
         }
      }

      registry.setParserPool(pp);
   }
}
