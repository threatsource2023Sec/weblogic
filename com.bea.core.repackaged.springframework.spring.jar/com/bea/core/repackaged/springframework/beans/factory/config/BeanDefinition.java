package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeanMetadataElement;
import com.bea.core.repackaged.springframework.beans.MutablePropertyValues;
import com.bea.core.repackaged.springframework.core.AttributeAccessor;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface BeanDefinition extends AttributeAccessor, BeanMetadataElement {
   String SCOPE_SINGLETON = "singleton";
   String SCOPE_PROTOTYPE = "prototype";
   int ROLE_APPLICATION = 0;
   int ROLE_SUPPORT = 1;
   int ROLE_INFRASTRUCTURE = 2;

   void setParentName(@Nullable String var1);

   @Nullable
   String getParentName();

   void setBeanClassName(@Nullable String var1);

   @Nullable
   String getBeanClassName();

   void setScope(@Nullable String var1);

   @Nullable
   String getScope();

   void setLazyInit(boolean var1);

   boolean isLazyInit();

   void setDependsOn(@Nullable String... var1);

   @Nullable
   String[] getDependsOn();

   void setAutowireCandidate(boolean var1);

   boolean isAutowireCandidate();

   void setPrimary(boolean var1);

   boolean isPrimary();

   void setFactoryBeanName(@Nullable String var1);

   @Nullable
   String getFactoryBeanName();

   void setFactoryMethodName(@Nullable String var1);

   @Nullable
   String getFactoryMethodName();

   ConstructorArgumentValues getConstructorArgumentValues();

   default boolean hasConstructorArgumentValues() {
      return !this.getConstructorArgumentValues().isEmpty();
   }

   MutablePropertyValues getPropertyValues();

   default boolean hasPropertyValues() {
      return !this.getPropertyValues().isEmpty();
   }

   void setInitMethodName(@Nullable String var1);

   @Nullable
   String getInitMethodName();

   void setDestroyMethodName(@Nullable String var1);

   @Nullable
   String getDestroyMethodName();

   void setRole(int var1);

   int getRole();

   void setDescription(@Nullable String var1);

   @Nullable
   String getDescription();

   boolean isSingleton();

   boolean isPrototype();

   boolean isAbstract();

   @Nullable
   String getResourceDescription();

   @Nullable
   BeanDefinition getOriginatingBeanDefinition();
}
