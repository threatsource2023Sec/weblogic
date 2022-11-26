package org.jboss.weld.util.annotated;

import java.lang.reflect.Constructor;
import javax.enterprise.inject.spi.AnnotatedConstructor;

public abstract class ForwardingAnnotatedConstructor extends ForwardingAnnotatedCallable implements AnnotatedConstructor {
   protected abstract AnnotatedConstructor delegate();

   public Constructor getJavaMember() {
      return this.delegate().getJavaMember();
   }
}
