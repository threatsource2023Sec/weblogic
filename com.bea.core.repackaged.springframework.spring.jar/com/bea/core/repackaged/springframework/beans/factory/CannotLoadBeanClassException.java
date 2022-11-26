package com.bea.core.repackaged.springframework.beans.factory;

import com.bea.core.repackaged.springframework.beans.FatalBeanException;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class CannotLoadBeanClassException extends FatalBeanException {
   @Nullable
   private final String resourceDescription;
   private final String beanName;
   @Nullable
   private final String beanClassName;

   public CannotLoadBeanClassException(@Nullable String resourceDescription, String beanName, @Nullable String beanClassName, ClassNotFoundException cause) {
      super("Cannot find class [" + beanClassName + "] for bean with name '" + beanName + "'" + (resourceDescription != null ? " defined in " + resourceDescription : ""), cause);
      this.resourceDescription = resourceDescription;
      this.beanName = beanName;
      this.beanClassName = beanClassName;
   }

   public CannotLoadBeanClassException(@Nullable String resourceDescription, String beanName, @Nullable String beanClassName, LinkageError cause) {
      super("Error loading class [" + beanClassName + "] for bean with name '" + beanName + "'" + (resourceDescription != null ? " defined in " + resourceDescription : "") + ": problem with class file or dependent class", cause);
      this.resourceDescription = resourceDescription;
      this.beanName = beanName;
      this.beanClassName = beanClassName;
   }

   @Nullable
   public String getResourceDescription() {
      return this.resourceDescription;
   }

   public String getBeanName() {
      return this.beanName;
   }

   @Nullable
   public String getBeanClassName() {
      return this.beanClassName;
   }
}
