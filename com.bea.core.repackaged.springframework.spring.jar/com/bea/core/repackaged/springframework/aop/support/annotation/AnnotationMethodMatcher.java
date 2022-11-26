package com.bea.core.repackaged.springframework.aop.support.annotation;

import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.aop.support.StaticMethodMatcher;
import com.bea.core.repackaged.springframework.core.annotation.AnnotatedElementUtils;
import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AnnotationMethodMatcher extends StaticMethodMatcher {
   private final Class annotationType;
   private final boolean checkInherited;

   public AnnotationMethodMatcher(Class annotationType) {
      this(annotationType, false);
   }

   public AnnotationMethodMatcher(Class annotationType, boolean checkInherited) {
      Assert.notNull(annotationType, (String)"Annotation type must not be null");
      this.annotationType = annotationType;
      this.checkInherited = checkInherited;
   }

   public boolean matches(Method method, Class targetClass) {
      if (this.matchesMethod(method)) {
         return true;
      } else if (Proxy.isProxyClass(targetClass)) {
         return false;
      } else {
         Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
         return specificMethod != method && this.matchesMethod(specificMethod);
      }
   }

   private boolean matchesMethod(Method method) {
      return this.checkInherited ? AnnotatedElementUtils.hasAnnotation(method, this.annotationType) : method.isAnnotationPresent(this.annotationType);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof AnnotationMethodMatcher)) {
         return false;
      } else {
         AnnotationMethodMatcher otherMm = (AnnotationMethodMatcher)other;
         return this.annotationType.equals(otherMm.annotationType) && this.checkInherited == otherMm.checkInherited;
      }
   }

   public int hashCode() {
      return this.annotationType.hashCode();
   }

   public String toString() {
      return this.getClass().getName() + ": " + this.annotationType;
   }
}
