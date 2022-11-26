package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.xml.ResourceEntityResolver;
import com.bea.core.repackaged.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.IOException;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableConfigApplicationContext {
   private boolean validating = true;

   public AbstractXmlApplicationContext() {
   }

   public AbstractXmlApplicationContext(@Nullable ApplicationContext parent) {
      super(parent);
   }

   public void setValidating(boolean validating) {
      this.validating = validating;
   }

   protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
      XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
      beanDefinitionReader.setEnvironment(this.getEnvironment());
      beanDefinitionReader.setResourceLoader(this);
      beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));
      this.initBeanDefinitionReader(beanDefinitionReader);
      this.loadBeanDefinitions(beanDefinitionReader);
   }

   protected void initBeanDefinitionReader(XmlBeanDefinitionReader reader) {
      reader.setValidating(this.validating);
   }

   protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException, IOException {
      Resource[] configResources = this.getConfigResources();
      if (configResources != null) {
         reader.loadBeanDefinitions((Resource[])configResources);
      }

      String[] configLocations = this.getConfigLocations();
      if (configLocations != null) {
         reader.loadBeanDefinitions((String[])configLocations);
      }

   }

   @Nullable
   protected Resource[] getConfigResources() {
      return null;
   }
}
