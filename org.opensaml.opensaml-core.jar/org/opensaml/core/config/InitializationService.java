package org.opensaml.core.config;

import java.util.Iterator;
import java.util.ServiceLoader;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitializationService {
   protected InitializationService() {
   }

   public static synchronized void initialize() throws InitializationException {
      Logger log = getLogger();
      log.info("Initializing OpenSAML using the Java Services API");
      ServiceLoader serviceLoader = getServiceLoader();
      Iterator iter = serviceLoader.iterator();

      while(iter.hasNext()) {
         Initializer initializer = (Initializer)iter.next();
         log.debug("Initializing module initializer implementation: {}", initializer.getClass().getName());

         try {
            initializer.init();
         } catch (InitializationException var5) {
            log.error("Error initializing module", var5);
            throw var5;
         }
      }

   }

   private static ServiceLoader getServiceLoader() {
      return ServiceLoader.load(Initializer.class);
   }

   @Nonnull
   private static Logger getLogger() {
      return LoggerFactory.getLogger(InitializationService.class);
   }
}
