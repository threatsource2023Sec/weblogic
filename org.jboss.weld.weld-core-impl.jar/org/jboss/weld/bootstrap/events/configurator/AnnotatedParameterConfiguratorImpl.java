package org.jboss.weld.bootstrap.events.configurator;

import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.configurator.AnnotatedParameterConfigurator;

public class AnnotatedParameterConfiguratorImpl extends AnnotatedConfigurator implements AnnotatedParameterConfigurator {
   static AnnotatedParameterConfiguratorImpl from(AnnotatedParameter annotatedParam) {
      return new AnnotatedParameterConfiguratorImpl(annotatedParam);
   }

   private AnnotatedParameterConfiguratorImpl(AnnotatedParameter annotatedParam) {
      super(annotatedParam);
   }

   protected AnnotatedParameterConfiguratorImpl self() {
      return this;
   }
}
