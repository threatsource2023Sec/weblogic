package com.oracle.pitchfork.server;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.support.GenericBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.context.support.GenericApplicationContext;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.oracle.pitchfork.intercept.InterceptionMetadataBeanPostProcessor;

public class DefaultContainerBootstrap implements ContainerBootstrap {
   protected final Log log = LogFactory.getLog(this.getClass());

   public GenericApplicationContext bootstrap(ApplicationContext parent, String[] requiredLocations, String[] optionalLocations, ResourceLoader resourceLoader) {
      GenericApplicationContext deploymentContext = parent != null ? new GenericApplicationContext(parent) : new GenericApplicationContext();
      if (resourceLoader != null) {
         deploymentContext.setResourceLoader(resourceLoader);
      }

      GenericBeanDefinition bd = new GenericBeanDefinition();
      bd.setBeanClassName(InterceptionMetadataBeanPostProcessor.class.getName());
      deploymentContext.registerBeanDefinition(InterceptionMetadataBeanPostProcessor.class.getName(), bd);
      XmlBeanDefinitionReader childBdr = new XmlBeanDefinitionReader(deploymentContext);
      String[] var8 = requiredLocations;
      int var9 = requiredLocations.length;

      int var10;
      String optionalLocation;
      for(var10 = 0; var10 < var9; ++var10) {
         optionalLocation = var8[var10];
         childBdr.loadBeanDefinitions(optionalLocation);
      }

      var8 = optionalLocations;
      var9 = optionalLocations.length;

      for(var10 = 0; var10 < var9; ++var10) {
         optionalLocation = var8[var10];

         try {
            childBdr.loadBeanDefinitions(optionalLocation);
         } catch (BeanDefinitionStoreException var13) {
            this.log.info("Did not find optional Spring deployment descriptor: " + var13.getMessage());
         }
      }

      return deploymentContext;
   }
}
