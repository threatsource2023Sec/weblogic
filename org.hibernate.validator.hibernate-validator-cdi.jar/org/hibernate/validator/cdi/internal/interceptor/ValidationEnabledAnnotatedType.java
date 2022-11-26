package org.hibernate.validator.cdi.internal.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;
import org.hibernate.validator.internal.util.CollectionHelper;

public class ValidationEnabledAnnotatedType implements AnnotatedType {
   private final AnnotatedType wrappedType;
   private final Set wrappedMethods;
   private final Set wrappedConstructors;

   public ValidationEnabledAnnotatedType(AnnotatedType type, Set constrainedCallables) {
      this.wrappedType = type;
      this.wrappedMethods = CollectionHelper.newHashSet();
      this.wrappedConstructors = CollectionHelper.newHashSet();
      this.buildWrappedCallable(constrainedCallables);
   }

   public Class getJavaClass() {
      return this.wrappedType.getJavaClass();
   }

   public Set getConstructors() {
      return this.wrappedConstructors;
   }

   public Set getMethods() {
      return this.wrappedMethods;
   }

   public Set getFields() {
      return this.wrappedType.getFields();
   }

   public Type getBaseType() {
      return this.wrappedType.getBaseType();
   }

   public Set getTypeClosure() {
      return this.wrappedType.getTypeClosure();
   }

   public Annotation getAnnotation(Class annotationType) {
      return this.wrappedType.getAnnotation(annotationType);
   }

   public Set getAnnotations() {
      return this.wrappedType.getAnnotations();
   }

   public boolean isAnnotationPresent(Class annotationType) {
      return this.wrappedType.isAnnotationPresent(annotationType);
   }

   private void buildWrappedCallable(Set constrainedCallables) {
      Iterator var2 = this.wrappedType.getConstructors().iterator();

      while(var2.hasNext()) {
         AnnotatedConstructor constructor = (AnnotatedConstructor)var2.next();
         if (constrainedCallables.contains(constructor)) {
            ValidationEnabledAnnotatedConstructor wrappedConstructor = new ValidationEnabledAnnotatedConstructor(constructor);
            this.wrappedConstructors.add(wrappedConstructor);
         } else {
            this.wrappedConstructors.add(constructor);
         }
      }

      var2 = this.wrappedType.getMethods().iterator();

      while(var2.hasNext()) {
         AnnotatedMethod method = (AnnotatedMethod)var2.next();
         if (constrainedCallables.contains(method)) {
            ValidationEnabledAnnotatedMethod wrappedMethod = this.wrap(method);
            this.wrappedMethods.add(wrappedMethod);
         } else {
            this.wrappedMethods.add(method);
         }
      }

   }

   private ValidationEnabledAnnotatedMethod wrap(AnnotatedMethod method) {
      return new ValidationEnabledAnnotatedMethod(method);
   }
}
