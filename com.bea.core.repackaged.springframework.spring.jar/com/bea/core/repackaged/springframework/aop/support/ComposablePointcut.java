package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.springframework.aop.ClassFilter;
import com.bea.core.repackaged.springframework.aop.MethodMatcher;
import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;

public class ComposablePointcut implements Pointcut, Serializable {
   private static final long serialVersionUID = -2743223737633663832L;
   private ClassFilter classFilter;
   private MethodMatcher methodMatcher;

   public ComposablePointcut() {
      this.classFilter = ClassFilter.TRUE;
      this.methodMatcher = MethodMatcher.TRUE;
   }

   public ComposablePointcut(Pointcut pointcut) {
      Assert.notNull(pointcut, (String)"Pointcut must not be null");
      this.classFilter = pointcut.getClassFilter();
      this.methodMatcher = pointcut.getMethodMatcher();
   }

   public ComposablePointcut(ClassFilter classFilter) {
      Assert.notNull(classFilter, (String)"ClassFilter must not be null");
      this.classFilter = classFilter;
      this.methodMatcher = MethodMatcher.TRUE;
   }

   public ComposablePointcut(MethodMatcher methodMatcher) {
      Assert.notNull(methodMatcher, (String)"MethodMatcher must not be null");
      this.classFilter = ClassFilter.TRUE;
      this.methodMatcher = methodMatcher;
   }

   public ComposablePointcut(ClassFilter classFilter, MethodMatcher methodMatcher) {
      Assert.notNull(classFilter, (String)"ClassFilter must not be null");
      Assert.notNull(methodMatcher, (String)"MethodMatcher must not be null");
      this.classFilter = classFilter;
      this.methodMatcher = methodMatcher;
   }

   public ComposablePointcut union(ClassFilter other) {
      this.classFilter = ClassFilters.union(this.classFilter, other);
      return this;
   }

   public ComposablePointcut intersection(ClassFilter other) {
      this.classFilter = ClassFilters.intersection(this.classFilter, other);
      return this;
   }

   public ComposablePointcut union(MethodMatcher other) {
      this.methodMatcher = MethodMatchers.union(this.methodMatcher, other);
      return this;
   }

   public ComposablePointcut intersection(MethodMatcher other) {
      this.methodMatcher = MethodMatchers.intersection(this.methodMatcher, other);
      return this;
   }

   public ComposablePointcut union(Pointcut other) {
      this.methodMatcher = MethodMatchers.union(this.methodMatcher, this.classFilter, other.getMethodMatcher(), other.getClassFilter());
      this.classFilter = ClassFilters.union(this.classFilter, other.getClassFilter());
      return this;
   }

   public ComposablePointcut intersection(Pointcut other) {
      this.classFilter = ClassFilters.intersection(this.classFilter, other.getClassFilter());
      this.methodMatcher = MethodMatchers.intersection(this.methodMatcher, other.getMethodMatcher());
      return this;
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
      } else if (!(other instanceof ComposablePointcut)) {
         return false;
      } else {
         ComposablePointcut otherPointcut = (ComposablePointcut)other;
         return this.classFilter.equals(otherPointcut.classFilter) && this.methodMatcher.equals(otherPointcut.methodMatcher);
      }
   }

   public int hashCode() {
      return this.classFilter.hashCode() * 37 + this.methodMatcher.hashCode();
   }

   public String toString() {
      return this.getClass().getName() + ": " + this.classFilter + ", " + this.methodMatcher;
   }
}
