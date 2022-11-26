package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomScopeConfigurer implements BeanFactoryPostProcessor, BeanClassLoaderAware, Ordered {
   @Nullable
   private Map scopes;
   private int order = Integer.MAX_VALUE;
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

   public void setScopes(Map scopes) {
      this.scopes = scopes;
   }

   public void addScope(String scopeName, Scope scope) {
      if (this.scopes == null) {
         this.scopes = new LinkedHashMap(1);
      }

      this.scopes.put(scopeName, scope);
   }

   public void setOrder(int order) {
      this.order = order;
   }

   public int getOrder() {
      return this.order;
   }

   public void setBeanClassLoader(@Nullable ClassLoader beanClassLoader) {
      this.beanClassLoader = beanClassLoader;
   }

   public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
      if (this.scopes != null) {
         this.scopes.forEach((scopeKey, value) -> {
            if (value instanceof Scope) {
               beanFactory.registerScope(scopeKey, (Scope)value);
            } else {
               Class scopeClass;
               if (value instanceof Class) {
                  scopeClass = (Class)value;
                  Assert.isAssignable(Scope.class, scopeClass, "Invalid scope class");
                  beanFactory.registerScope(scopeKey, (Scope)BeanUtils.instantiateClass(scopeClass));
               } else {
                  if (!(value instanceof String)) {
                     throw new IllegalArgumentException("Mapped value [" + value + "] for scope key [" + scopeKey + "] is not an instance of required type [" + Scope.class.getName() + "] or a corresponding Class or String value indicating a Scope implementation");
                  }

                  scopeClass = ClassUtils.resolveClassName((String)value, this.beanClassLoader);
                  Assert.isAssignable(Scope.class, scopeClass, "Invalid scope class");
                  beanFactory.registerScope(scopeKey, (Scope)BeanUtils.instantiateClass(scopeClass));
               }
            }

         });
      }

   }
}
