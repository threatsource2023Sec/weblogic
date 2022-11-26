package org.jboss.weld.util.annotated;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import javax.enterprise.inject.spi.Annotated;

public abstract class ForwardingAnnotated implements Annotated {
   protected abstract Annotated delegate();

   public Annotation getAnnotation(Class annotationType) {
      return this.delegate().getAnnotation(annotationType);
   }

   public Set getAnnotations() {
      return this.delegate().getAnnotations();
   }

   public boolean isAnnotationPresent(Class annotationType) {
      return this.delegate().isAnnotationPresent(annotationType);
   }

   public Type getBaseType() {
      return this.delegate().getBaseType();
   }

   public Set getTypeClosure() {
      return this.delegate().getTypeClosure();
   }

   public boolean equals(Object obj) {
      return this.delegate().equals(obj);
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public String toString() {
      return this.delegate().toString();
   }
}
