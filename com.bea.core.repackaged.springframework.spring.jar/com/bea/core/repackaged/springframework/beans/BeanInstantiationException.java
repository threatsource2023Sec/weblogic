package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class BeanInstantiationException extends FatalBeanException {
   private final Class beanClass;
   @Nullable
   private final Constructor constructor;
   @Nullable
   private final Method constructingMethod;

   public BeanInstantiationException(Class beanClass, String msg) {
      this((Class)beanClass, msg, (Throwable)null);
   }

   public BeanInstantiationException(Class beanClass, String msg, @Nullable Throwable cause) {
      super("Failed to instantiate [" + beanClass.getName() + "]: " + msg, cause);
      this.beanClass = beanClass;
      this.constructor = null;
      this.constructingMethod = null;
   }

   public BeanInstantiationException(Constructor constructor, String msg, @Nullable Throwable cause) {
      super("Failed to instantiate [" + constructor.getDeclaringClass().getName() + "]: " + msg, cause);
      this.beanClass = constructor.getDeclaringClass();
      this.constructor = constructor;
      this.constructingMethod = null;
   }

   public BeanInstantiationException(Method constructingMethod, String msg, @Nullable Throwable cause) {
      super("Failed to instantiate [" + constructingMethod.getReturnType().getName() + "]: " + msg, cause);
      this.beanClass = constructingMethod.getReturnType();
      this.constructor = null;
      this.constructingMethod = constructingMethod;
   }

   public Class getBeanClass() {
      return this.beanClass;
   }

   @Nullable
   public Constructor getConstructor() {
      return this.constructor;
   }

   @Nullable
   public Method getConstructingMethod() {
      return this.constructingMethod;
   }
}
