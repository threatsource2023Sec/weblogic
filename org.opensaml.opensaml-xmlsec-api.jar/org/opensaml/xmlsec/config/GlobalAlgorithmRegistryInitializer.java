package org.opensaml.xmlsec.config;

import java.util.Iterator;
import java.util.ServiceLoader;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.Initializer;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.AlgorithmRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalAlgorithmRegistryInitializer implements Initializer {
   private Logger log = LoggerFactory.getLogger(GlobalAlgorithmRegistryInitializer.class);

   public void init() throws InitializationException {
      AlgorithmRegistry algorithmRegistry = new AlgorithmRegistry();
      ServiceLoader descriptorsLoader = ServiceLoader.load(AlgorithmDescriptor.class);
      Iterator iter = descriptorsLoader.iterator();

      while(iter.hasNext()) {
         AlgorithmDescriptor descriptor = (AlgorithmDescriptor)iter.next();
         this.log.debug("Registering AlgorithmDescriptor of type '{}' with URI '{}': {}", new Object[]{descriptor.getType(), descriptor.getURI(), descriptor.getClass().getName()});
         algorithmRegistry.register(descriptor);
      }

      ConfigurationService.register(AlgorithmRegistry.class, algorithmRegistry);
   }
}
