package org.opensaml.core.metrics.impl;

import com.codahale.metrics.MetricRegistry;
import javax.annotation.Nonnull;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.Initializer;
import org.opensaml.core.metrics.FilteredMetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetricRegistryInitializer implements Initializer {
   @Nonnull
   private Logger log = LoggerFactory.getLogger(MetricRegistryInitializer.class);

   public void init() throws InitializationException {
      Class var1 = ConfigurationService.class;
      synchronized(ConfigurationService.class) {
         MetricRegistry registry = (MetricRegistry)ConfigurationService.get(MetricRegistry.class);
         if (registry == null) {
            this.log.debug("MetricRegistry did not exist in ConfigurationService, a disabled one will be created");
            MetricRegistry registry = new FilteredMetricRegistry();
            ConfigurationService.register(MetricRegistry.class, registry);
         }

      }
   }
}
