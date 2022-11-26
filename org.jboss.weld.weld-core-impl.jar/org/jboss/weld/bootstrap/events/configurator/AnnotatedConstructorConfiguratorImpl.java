package org.jboss.weld.bootstrap.events.configurator;

import java.util.List;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.configurator.AnnotatedConstructorConfigurator;
import org.jboss.weld.util.reflection.Reflections;

public class AnnotatedConstructorConfiguratorImpl extends AnnotatedCallableConfigurator implements AnnotatedConstructorConfigurator {
   static AnnotatedConstructorConfiguratorImpl from(AnnotatedConstructor annotatedConstructor) {
      return new AnnotatedConstructorConfiguratorImpl(annotatedConstructor);
   }

   private AnnotatedConstructorConfiguratorImpl(AnnotatedConstructor annotatedConstructor) {
      super(annotatedConstructor);
   }

   public List params() {
      return (List)Reflections.cast(this.params);
   }

   protected AnnotatedConstructorConfiguratorImpl self() {
      return this;
   }
}
