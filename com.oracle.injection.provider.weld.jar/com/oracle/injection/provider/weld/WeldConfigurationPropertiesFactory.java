package com.oracle.injection.provider.weld;

import org.jboss.weld.configuration.spi.ExternalConfiguration;
import org.jboss.weld.configuration.spi.helpers.ExternalConfigurationBuilder;

public class WeldConfigurationPropertiesFactory {
   public ExternalConfiguration createExternalConfiguration() {
      ExternalConfigurationBuilder externalConfigurationBuilder = new ExternalConfigurationBuilder();
      externalConfigurationBuilder.add("org.jboss.weld.bootstrap.concurrentDeployment", false);
      externalConfigurationBuilder.add("org.jboss.weld.bootstrap.preloaderThreadPoolSize", 0);
      externalConfigurationBuilder.add("org.jboss.weld.nonPortableMode", false);
      return externalConfigurationBuilder.build();
   }
}
