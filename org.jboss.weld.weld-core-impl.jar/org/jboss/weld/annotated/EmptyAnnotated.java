package org.jboss.weld.annotated;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;
import javax.enterprise.inject.spi.Annotated;

public class EmptyAnnotated implements Annotated {
   public static final EmptyAnnotated INSTANCE = new EmptyAnnotated();

   private EmptyAnnotated() {
   }

   public Type getBaseType() {
      return Object.class;
   }

   public Set getTypeClosure() {
      return Collections.singleton(Object.class);
   }

   public Annotation getAnnotation(Class annotationType) {
      return null;
   }

   public Set getAnnotations(Class annotationType) {
      return Collections.emptySet();
   }

   public Set getAnnotations() {
      return Collections.emptySet();
   }

   public boolean isAnnotationPresent(Class annotationType) {
      return false;
   }
}
