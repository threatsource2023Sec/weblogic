package org.jboss.weld.util.annotated;

import java.lang.reflect.Method;
import javax.enterprise.inject.spi.AnnotatedMethod;

public abstract class ForwardingAnnotatedMethod extends ForwardingAnnotatedCallable implements AnnotatedMethod {
   public Method getJavaMember() {
      return this.delegate().getJavaMember();
   }

   protected abstract AnnotatedMethod delegate();
}
