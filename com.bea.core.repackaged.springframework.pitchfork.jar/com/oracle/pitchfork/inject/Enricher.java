package com.oracle.pitchfork.inject;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.context.support.GenericApplicationContext;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.EnricherI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import java.util.LinkedList;
import java.util.List;

public class Enricher implements EnricherI {
   protected final Log log;
   private final List deployments;
   private final ApplicationContext deploymentContext;
   private final BeanDefinitionRegistry beanDefinitionRegistry;
   private DeploymentUnitMetadataI deploymentUnitMetadata;

   public Enricher(ApplicationContext deploymentContext, BeanDefinitionRegistry beanDefinitionRegistry) {
      this.log = LogFactory.getLog(this.getClass());
      this.deployments = new LinkedList();
      this.deploymentContext = deploymentContext;
      this.beanDefinitionRegistry = beanDefinitionRegistry;
      this.setDeploymentUnitMetadata(new DeploymentUnitMetadata());
   }

   public Enricher(GenericApplicationContext deploymentContext) {
      this(deploymentContext, deploymentContext);
   }

   public void setDeploymentUnitMetadata(DeploymentUnitMetadataI deploymentUnitMetadata) {
      this.deploymentUnitMetadata = deploymentUnitMetadata;
      ((DeploymentUnitMetadata)deploymentUnitMetadata).associate(this.deploymentContext, this.beanDefinitionRegistry);
   }

   public DeploymentUnitMetadataI getDeploymentUnitMetadata() {
      return this.deploymentUnitMetadata;
   }

   public void attach(Jsr250MetadataI jsr250) {
      this.attach(jsr250, true);
   }

   public void attach(Jsr250MetadataI jsr250, boolean isSingleton) {
      Object bd;
      if (this.deploymentContext.containsBean(jsr250.getComponentName())) {
         bd = (AbstractBeanDefinition)this.beanDefinitionRegistry.getBeanDefinition(jsr250.getComponentName());
         ((AbstractBeanDefinition)bd).setScope("singleton");
      } else {
         bd = new RootBeanDefinition(jsr250.getComponentClass());
         ((AbstractBeanDefinition)bd).setScope("prototype");
         this.beanDefinitionRegistry.registerBeanDefinition(jsr250.getComponentName(), (BeanDefinition)bd);
      }

      GenericApplicationContext componentContext = new PitchforkGenericApplicationContext(this.deploymentContext);
      componentContext.registerBeanDefinition(Jsr250MetadataBeanPostProcessor.class.getName(), new RootBeanDefinition(Jsr250MetadataBeanPostProcessor.class));
      ((Jsr250Metadata)jsr250).setBeanDefinition((AbstractBeanDefinition)bd);
      ((Jsr250Metadata)jsr250).setComponentContext(componentContext, componentContext);
      this.deployments.add(jsr250);
      if (isSingleton) {
         ((AbstractBeanDefinition)bd).setScope("singleton");
      } else {
         ((AbstractBeanDefinition)bd).setScope("prototype");
      }

   }

   public void startup() {
      this.deploymentUnitMetadata.startup();
   }

   public String[] getRegisteredBeanDefinitionNames() {
      return this.deploymentContext.getBeanDefinitionNames();
   }
}
