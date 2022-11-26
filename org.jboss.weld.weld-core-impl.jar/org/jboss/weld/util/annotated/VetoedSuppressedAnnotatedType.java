package org.jboss.weld.util.annotated;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.Vetoed;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;

public class VetoedSuppressedAnnotatedType extends ForwardingAnnotatedType {
   private final AnnotatedType annotatedType;

   public static VetoedSuppressedAnnotatedType from(Class clazz, BeanManager beanManager) {
      return new VetoedSuppressedAnnotatedType(beanManager.createAnnotatedType(clazz));
   }

   public VetoedSuppressedAnnotatedType(AnnotatedType annotatedType) {
      this.annotatedType = annotatedType;
   }

   public Annotation getAnnotation(Class annotationType) {
      return annotationType == Vetoed.class ? null : this.annotatedType.getAnnotation(annotationType);
   }

   public Set getAnnotations() {
      Set annotations = new HashSet();
      Iterator var2 = this.annotatedType.getAnnotations().iterator();

      while(var2.hasNext()) {
         Annotation a = (Annotation)var2.next();
         if (a.annotationType() != Vetoed.class) {
            annotations.add(a);
         }
      }

      return annotations;
   }

   public boolean isAnnotationPresent(Class annotationType) {
      return annotationType == Vetoed.class ? false : this.annotatedType.isAnnotationPresent(annotationType);
   }

   public AnnotatedType delegate() {
      return this.annotatedType;
   }
}
