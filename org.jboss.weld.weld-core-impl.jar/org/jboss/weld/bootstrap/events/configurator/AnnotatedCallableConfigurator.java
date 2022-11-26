package org.jboss.weld.bootstrap.events.configurator;

import java.util.List;
import javax.enterprise.inject.spi.AnnotatedCallable;
import org.jboss.weld.util.collections.ImmutableList;

abstract class AnnotatedCallableConfigurator extends AnnotatedConfigurator {
   protected final List params;

   AnnotatedCallableConfigurator(AnnotatedCallable annotatedCallable) {
      super(annotatedCallable);
      this.params = (List)annotatedCallable.getParameters().stream().map((p) -> {
         return AnnotatedParameterConfiguratorImpl.from(p);
      }).collect(ImmutableList.collector());
   }

   List getParams() {
      return this.params;
   }
}
