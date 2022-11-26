package org.jboss.weld.util.annotated;

import java.util.List;
import javax.enterprise.inject.spi.AnnotatedCallable;

public abstract class ForwardingAnnotatedCallable extends ForwardingAnnotatedMember implements AnnotatedCallable {
   public List getParameters() {
      return this.delegate().getParameters();
   }

   protected abstract AnnotatedCallable delegate();
}
