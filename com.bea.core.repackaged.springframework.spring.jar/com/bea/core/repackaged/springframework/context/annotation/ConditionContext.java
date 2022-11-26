package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.core.env.Environment;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface ConditionContext {
   BeanDefinitionRegistry getRegistry();

   @Nullable
   ConfigurableListableBeanFactory getBeanFactory();

   Environment getEnvironment();

   ResourceLoader getResourceLoader();

   @Nullable
   ClassLoader getClassLoader();
}
