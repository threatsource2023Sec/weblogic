package org.jboss.weld.bootstrap.events.configurator;

import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.configurator.AnnotatedFieldConfigurator;

public class AnnotatedFieldConfiguratorImpl extends AnnotatedConfigurator implements AnnotatedFieldConfigurator {
   static AnnotatedFieldConfiguratorImpl from(AnnotatedField annotatedField) {
      return new AnnotatedFieldConfiguratorImpl(annotatedField);
   }

   private AnnotatedFieldConfiguratorImpl(AnnotatedField annotatedField) {
      super(annotatedField);
   }

   protected AnnotatedFieldConfiguratorImpl self() {
      return this;
   }
}
