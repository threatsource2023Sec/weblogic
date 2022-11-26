package org.hibernate.validator.cdi.internal.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedCallable;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.util.AnnotationLiteral;

public abstract class ValidationEnabledAnnotatedCallable implements AnnotatedCallable {
   private final AnnotatedCallable wrappedCallable;
   private final AnnotationLiteral methodValidationAnnotation;

   public ValidationEnabledAnnotatedCallable(AnnotatedCallable callable) {
      this.wrappedCallable = callable;
      this.methodValidationAnnotation = new AnnotationLiteral() {
      };
   }

   public boolean isStatic() {
      return this.wrappedCallable.isStatic();
   }

   public AnnotatedType getDeclaringType() {
      return this.wrappedCallable.getDeclaringType();
   }

   public List getParameters() {
      return this.wrappedCallable.getParameters();
   }

   public Type getBaseType() {
      return this.wrappedCallable.getBaseType();
   }

   public Set getTypeClosure() {
      return this.wrappedCallable.getTypeClosure();
   }

   public Annotation getAnnotation(Class annotationType) {
      if (MethodValidated.class.equals(annotationType)) {
         Annotation annotation = this.methodValidationAnnotation;
         return annotation;
      } else {
         return this.wrappedCallable.getAnnotation(annotationType);
      }
   }

   public Set getAnnotations() {
      Set annotations = new HashSet(this.wrappedCallable.getAnnotations());
      annotations.add(this.methodValidationAnnotation);
      return annotations;
   }

   public boolean isAnnotationPresent(Class annotationType) {
      return MethodValidated.class.equals(annotationType) ? true : this.wrappedCallable.isAnnotationPresent(annotationType);
   }

   AnnotatedCallable getWrappedCallable() {
      return this.wrappedCallable;
   }
}
