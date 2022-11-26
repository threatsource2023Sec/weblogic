package org.jboss.weld.util.annotated;

import javax.enterprise.inject.spi.AnnotatedCallable;
import javax.enterprise.inject.spi.AnnotatedParameter;

public abstract class ForwardingAnnotatedParameter extends ForwardingAnnotated implements AnnotatedParameter {
   protected abstract AnnotatedParameter delegate();

   public int getPosition() {
      return this.delegate().getPosition();
   }

   public AnnotatedCallable getDeclaringCallable() {
      return this.delegate().getDeclaringCallable();
   }
}
