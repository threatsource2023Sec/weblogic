package com.bea.core.repackaged.springframework.aop.support.annotation;

import com.bea.core.repackaged.springframework.aop.ClassFilter;
import com.bea.core.repackaged.springframework.core.annotation.AnnotatedElementUtils;
import com.bea.core.repackaged.springframework.util.Assert;

public class AnnotationClassFilter implements ClassFilter {
   private final Class annotationType;
   private final boolean checkInherited;

   public AnnotationClassFilter(Class annotationType) {
      this(annotationType, false);
   }

   public AnnotationClassFilter(Class annotationType, boolean checkInherited) {
      Assert.notNull(annotationType, (String)"Annotation type must not be null");
      this.annotationType = annotationType;
      this.checkInherited = checkInherited;
   }

   public boolean matches(Class clazz) {
      return this.checkInherited ? AnnotatedElementUtils.hasAnnotation(clazz, this.annotationType) : clazz.isAnnotationPresent(this.annotationType);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof AnnotationClassFilter)) {
         return false;
      } else {
         AnnotationClassFilter otherCf = (AnnotationClassFilter)other;
         return this.annotationType.equals(otherCf.annotationType) && this.checkInherited == otherCf.checkInherited;
      }
   }

   public int hashCode() {
      return this.annotationType.hashCode();
   }

   public String toString() {
      return this.getClass().getName() + ": " + this.annotationType;
   }
}
