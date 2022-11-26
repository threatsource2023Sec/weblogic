package org.jboss.weld.bootstrap.events.configurator;

import java.util.List;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.configurator.AnnotatedMethodConfigurator;
import org.jboss.weld.util.reflection.Reflections;

public class AnnotatedMethodConfiguratorImpl extends AnnotatedCallableConfigurator implements AnnotatedMethodConfigurator {
   static AnnotatedMethodConfiguratorImpl from(AnnotatedMethod annotatedMethod) {
      return new AnnotatedMethodConfiguratorImpl(annotatedMethod);
   }

   private AnnotatedMethodConfiguratorImpl(AnnotatedMethod annotatedMethod) {
      super(annotatedMethod);
   }

   public List params() {
      return (List)Reflections.cast(this.params);
   }

   protected AnnotatedMethodConfiguratorImpl self() {
      return this;
   }
}
