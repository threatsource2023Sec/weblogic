package com.bea.core.repackaged.springframework.beans.factory;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface BeanFactory {
   String FACTORY_BEAN_PREFIX = "&";

   Object getBean(String var1) throws BeansException;

   Object getBean(String var1, Class var2) throws BeansException;

   Object getBean(String var1, Object... var2) throws BeansException;

   Object getBean(Class var1) throws BeansException;

   Object getBean(Class var1, Object... var2) throws BeansException;

   ObjectProvider getBeanProvider(Class var1);

   ObjectProvider getBeanProvider(ResolvableType var1);

   boolean containsBean(String var1);

   boolean isSingleton(String var1) throws NoSuchBeanDefinitionException;

   boolean isPrototype(String var1) throws NoSuchBeanDefinitionException;

   boolean isTypeMatch(String var1, ResolvableType var2) throws NoSuchBeanDefinitionException;

   boolean isTypeMatch(String var1, Class var2) throws NoSuchBeanDefinitionException;

   @Nullable
   Class getType(String var1) throws NoSuchBeanDefinitionException;

   String[] getAliases(String var1);
}
