package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.PropertyEditorRegistrar;
import com.bea.core.repackaged.springframework.beans.PropertyEditorRegistry;
import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.HierarchicalBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringValueResolver;
import java.security.AccessControlContext;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
   String SCOPE_SINGLETON = "singleton";
   String SCOPE_PROTOTYPE = "prototype";

   void setParentBeanFactory(BeanFactory var1) throws IllegalStateException;

   void setBeanClassLoader(@Nullable ClassLoader var1);

   @Nullable
   ClassLoader getBeanClassLoader();

   void setTempClassLoader(@Nullable ClassLoader var1);

   @Nullable
   ClassLoader getTempClassLoader();

   void setCacheBeanMetadata(boolean var1);

   boolean isCacheBeanMetadata();

   void setBeanExpressionResolver(@Nullable BeanExpressionResolver var1);

   @Nullable
   BeanExpressionResolver getBeanExpressionResolver();

   void setConversionService(@Nullable ConversionService var1);

   @Nullable
   ConversionService getConversionService();

   void addPropertyEditorRegistrar(PropertyEditorRegistrar var1);

   void registerCustomEditor(Class var1, Class var2);

   void copyRegisteredEditorsTo(PropertyEditorRegistry var1);

   void setTypeConverter(TypeConverter var1);

   TypeConverter getTypeConverter();

   void addEmbeddedValueResolver(StringValueResolver var1);

   boolean hasEmbeddedValueResolver();

   @Nullable
   String resolveEmbeddedValue(String var1);

   void addBeanPostProcessor(BeanPostProcessor var1);

   int getBeanPostProcessorCount();

   void registerScope(String var1, Scope var2);

   String[] getRegisteredScopeNames();

   @Nullable
   Scope getRegisteredScope(String var1);

   AccessControlContext getAccessControlContext();

   void copyConfigurationFrom(ConfigurableBeanFactory var1);

   void registerAlias(String var1, String var2) throws BeanDefinitionStoreException;

   void resolveAliases(StringValueResolver var1);

   BeanDefinition getMergedBeanDefinition(String var1) throws NoSuchBeanDefinitionException;

   boolean isFactoryBean(String var1) throws NoSuchBeanDefinitionException;

   void setCurrentlyInCreation(String var1, boolean var2);

   boolean isCurrentlyInCreation(String var1);

   void registerDependentBean(String var1, String var2);

   String[] getDependentBeans(String var1);

   String[] getDependenciesForBean(String var1);

   void destroyBean(String var1, Object var2);

   void destroyScopedBean(String var1);

   void destroySingletons();
}
