package com.bea.core.repackaged.springframework.context;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.core.env.ConfigurableEnvironment;
import com.bea.core.repackaged.springframework.core.io.ProtocolResolver;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.Closeable;

public interface ConfigurableApplicationContext extends ApplicationContext, Lifecycle, Closeable {
   String CONFIG_LOCATION_DELIMITERS = ",; \t\n";
   String CONVERSION_SERVICE_BEAN_NAME = "conversionService";
   String LOAD_TIME_WEAVER_BEAN_NAME = "loadTimeWeaver";
   String ENVIRONMENT_BEAN_NAME = "environment";
   String SYSTEM_PROPERTIES_BEAN_NAME = "systemProperties";
   String SYSTEM_ENVIRONMENT_BEAN_NAME = "systemEnvironment";

   void setId(String var1);

   void setParent(@Nullable ApplicationContext var1);

   void setEnvironment(ConfigurableEnvironment var1);

   ConfigurableEnvironment getEnvironment();

   void addBeanFactoryPostProcessor(BeanFactoryPostProcessor var1);

   void addApplicationListener(ApplicationListener var1);

   void addProtocolResolver(ProtocolResolver var1);

   void refresh() throws BeansException, IllegalStateException;

   void registerShutdownHook();

   void close();

   boolean isActive();

   ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
}
