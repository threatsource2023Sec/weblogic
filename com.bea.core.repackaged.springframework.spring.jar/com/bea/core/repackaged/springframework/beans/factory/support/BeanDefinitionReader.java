package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface BeanDefinitionReader {
   BeanDefinitionRegistry getRegistry();

   @Nullable
   ResourceLoader getResourceLoader();

   @Nullable
   ClassLoader getBeanClassLoader();

   BeanNameGenerator getBeanNameGenerator();

   int loadBeanDefinitions(Resource var1) throws BeanDefinitionStoreException;

   int loadBeanDefinitions(Resource... var1) throws BeanDefinitionStoreException;

   int loadBeanDefinitions(String var1) throws BeanDefinitionStoreException;

   int loadBeanDefinitions(String... var1) throws BeanDefinitionStoreException;
}
