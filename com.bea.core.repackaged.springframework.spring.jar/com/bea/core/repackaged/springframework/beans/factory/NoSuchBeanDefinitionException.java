package com.bea.core.repackaged.springframework.beans.factory;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class NoSuchBeanDefinitionException extends BeansException {
   @Nullable
   private final String beanName;
   @Nullable
   private final ResolvableType resolvableType;

   public NoSuchBeanDefinitionException(String name) {
      super("No bean named '" + name + "' available");
      this.beanName = name;
      this.resolvableType = null;
   }

   public NoSuchBeanDefinitionException(String name, String message) {
      super("No bean named '" + name + "' available: " + message);
      this.beanName = name;
      this.resolvableType = null;
   }

   public NoSuchBeanDefinitionException(Class type) {
      this(ResolvableType.forClass(type));
   }

   public NoSuchBeanDefinitionException(Class type, String message) {
      this(ResolvableType.forClass(type), message);
   }

   public NoSuchBeanDefinitionException(ResolvableType type) {
      super("No qualifying bean of type '" + type + "' available");
      this.beanName = null;
      this.resolvableType = type;
   }

   public NoSuchBeanDefinitionException(ResolvableType type, String message) {
      super("No qualifying bean of type '" + type + "' available: " + message);
      this.beanName = null;
      this.resolvableType = type;
   }

   @Nullable
   public String getBeanName() {
      return this.beanName;
   }

   @Nullable
   public Class getBeanType() {
      return this.resolvableType != null ? this.resolvableType.resolve() : null;
   }

   @Nullable
   public ResolvableType getResolvableType() {
      return this.resolvableType;
   }

   public int getNumberOfBeansFound() {
      return 0;
   }
}
