package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.ListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Iterator;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
   void ignoreDependencyType(Class var1);

   void ignoreDependencyInterface(Class var1);

   void registerResolvableDependency(Class var1, @Nullable Object var2);

   boolean isAutowireCandidate(String var1, DependencyDescriptor var2) throws NoSuchBeanDefinitionException;

   BeanDefinition getBeanDefinition(String var1) throws NoSuchBeanDefinitionException;

   Iterator getBeanNamesIterator();

   void clearMetadataCache();

   void freezeConfiguration();

   boolean isConfigurationFrozen();

   void preInstantiateSingletons() throws BeansException;
}
