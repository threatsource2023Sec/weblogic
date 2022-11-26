package com.bea.core.repackaged.springframework.aop.support.annotation;

import com.bea.core.repackaged.springframework.aop.ClassFilter;
import com.bea.core.repackaged.springframework.aop.MethodMatcher;
import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class AnnotationMatchingPointcut implements Pointcut {
   private final ClassFilter classFilter;
   private final MethodMatcher methodMatcher;

   public AnnotationMatchingPointcut(Class classAnnotationType) {
      this(classAnnotationType, false);
   }

   public AnnotationMatchingPointcut(Class classAnnotationType, boolean checkInherited) {
      this.classFilter = new AnnotationClassFilter(classAnnotationType, checkInherited);
      this.methodMatcher = MethodMatcher.TRUE;
   }

   public AnnotationMatchingPointcut(@Nullable Class classAnnotationType, @Nullable Class methodAnnotationType) {
      this(classAnnotationType, methodAnnotationType, false);
   }

   public AnnotationMatchingPointcut(@Nullable Class classAnnotationType, @Nullable Class methodAnnotationType, boolean checkInherited) {
      Assert.isTrue(classAnnotationType != null || methodAnnotationType != null, "Either Class annotation type or Method annotation type needs to be specified (or both)");
      if (classAnnotationType != null) {
         this.classFilter = new AnnotationClassFilter(classAnnotationType, checkInherited);
      } else {
         this.classFilter = ClassFilter.TRUE;
      }

      if (methodAnnotationType != null) {
         this.methodMatcher = new AnnotationMethodMatcher(methodAnnotationType, checkInherited);
      } else {
         this.methodMatcher = MethodMatcher.TRUE;
      }

   }

   public ClassFilter getClassFilter() {
      return this.classFilter;
   }

   public MethodMatcher getMethodMatcher() {
      return this.methodMatcher;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof AnnotationMatchingPointcut)) {
         return false;
      } else {
         AnnotationMatchingPointcut otherPointcut = (AnnotationMatchingPointcut)other;
         return this.classFilter.equals(otherPointcut.classFilter) && this.methodMatcher.equals(otherPointcut.methodMatcher);
      }
   }

   public int hashCode() {
      return this.classFilter.hashCode() * 37 + this.methodMatcher.hashCode();
   }

   public String toString() {
      return "AnnotationMatchingPointcut: " + this.classFilter + ", " + this.methodMatcher;
   }

   public static AnnotationMatchingPointcut forClassAnnotation(Class annotationType) {
      Assert.notNull(annotationType, (String)"Annotation type must not be null");
      return new AnnotationMatchingPointcut(annotationType);
   }

   public static AnnotationMatchingPointcut forMethodAnnotation(Class annotationType) {
      Assert.notNull(annotationType, (String)"Annotation type must not be null");
      return new AnnotationMatchingPointcut((Class)null, annotationType);
   }
}
