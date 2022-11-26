package org.jboss.weld.util.annotated;

import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedType;

public abstract class ForwardingAnnotatedType extends ForwardingAnnotated implements AnnotatedType {
   public abstract AnnotatedType delegate();

   public Set getConstructors() {
      return this.delegate().getConstructors();
   }

   public Set getFields() {
      return this.delegate().getFields();
   }

   public Class getJavaClass() {
      return this.delegate().getJavaClass();
   }

   public Set getMethods() {
      return this.delegate().getMethods();
   }
}
