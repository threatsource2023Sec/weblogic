package com.bea.core.repackaged.springframework.beans.factory;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;

public class UnsatisfiedDependencyException extends BeanCreationException {
   @Nullable
   private final InjectionPoint injectionPoint;

   public UnsatisfiedDependencyException(@Nullable String resourceDescription, @Nullable String beanName, String propertyName, String msg) {
      super(resourceDescription, beanName, "Unsatisfied dependency expressed through bean property '" + propertyName + "'" + (StringUtils.hasLength(msg) ? ": " + msg : ""));
      this.injectionPoint = null;
   }

   public UnsatisfiedDependencyException(@Nullable String resourceDescription, @Nullable String beanName, String propertyName, BeansException ex) {
      this(resourceDescription, beanName, propertyName, "");
      this.initCause(ex);
   }

   public UnsatisfiedDependencyException(@Nullable String resourceDescription, @Nullable String beanName, @Nullable InjectionPoint injectionPoint, String msg) {
      super(resourceDescription, beanName, "Unsatisfied dependency expressed through " + injectionPoint + (StringUtils.hasLength(msg) ? ": " + msg : ""));
      this.injectionPoint = injectionPoint;
   }

   public UnsatisfiedDependencyException(@Nullable String resourceDescription, @Nullable String beanName, @Nullable InjectionPoint injectionPoint, BeansException ex) {
      this(resourceDescription, beanName, injectionPoint, "");
      this.initCause(ex);
   }

   @Nullable
   public InjectionPoint getInjectionPoint() {
      return this.injectionPoint;
   }
}
