package com.oracle.pitchfork.inject;

import com.bea.core.repackaged.springframework.aop.aspectj.annotation.AspectJAdvisorFactory;
import com.bea.core.repackaged.springframework.aop.aspectj.annotation.ReflectiveAspectJAdvisorFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.context.support.AbstractApplicationContext;
import com.oracle.pitchfork.intercept.InterceptorMetadata;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import com.oracle.pitchfork.interfaces.intercept.InterceptorMetadataI;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DeploymentUnitMetadata implements DeploymentUnitMetadataI {
   private final AspectJAdvisorFactory aspectJAdvisorFactory = new ReflectiveAspectJAdvisorFactory();
   private final List defaultInterceptorMetadata = new LinkedList();
   private final List deployedComponents = new LinkedList();
   private ApplicationContext deploymentUnitContext;
   private BeanDefinitionRegistry beanDefinitionRegistry;
   private boolean invokeLifecycleMethod = true;
   private boolean limitToSpec;

   public void associate(ApplicationContext deploymentContext, BeanDefinitionRegistry bdr) {
      if (this.deploymentUnitContext != null) {
         throw new IllegalStateException("Cannot associate DeploymentUnitMetadata with two contexts");
      } else {
         this.deploymentUnitContext = deploymentContext;
         this.beanDefinitionRegistry = bdr;
      }
   }

   public AspectJAdvisorFactory getAspectJAdvisorFactory() {
      return this.aspectJAdvisorFactory;
   }

   public List getDefaultInterceptorMetadata() {
      return this.defaultInterceptorMetadata;
   }

   public void registerDefaultInterceptorMetadata(InterceptorMetadataI defaultIm) {
      this.defaultInterceptorMetadata.add(defaultIm);
   }

   public List getDeployedComponentMetadata() {
      return this.deployedComponents;
   }

   protected void registerDeployedComponentMetadata(Jsr250Metadata deployedComponentMetadata) {
      this.deployedComponents.add(deployedComponentMetadata);
   }

   public boolean isLimitToSpec() {
      return this.limitToSpec;
   }

   public void setLimitToSpec(boolean limitToSpec) {
      this.limitToSpec = limitToSpec;
   }

   public void startup() {
      Iterator var1 = this.defaultInterceptorMetadata.iterator();

      while(var1.hasNext()) {
         InterceptorMetadataI defaultIm = (InterceptorMetadataI)var1.next();
         RootBeanDefinition rbd = new RootBeanDefinition(defaultIm.getComponentClass());
         rbd.setScope("prototype");
         ((InterceptorMetadata)defaultIm).setBeanDefinition(rbd);
         ((InterceptorMetadata)defaultIm).setComponentContext(this.deploymentUnitContext, this.beanDefinitionRegistry);
      }

      if (this.deploymentUnitContext instanceof AbstractApplicationContext) {
         ((AbstractApplicationContext)this.deploymentUnitContext).refresh();
      }

      var1 = this.getDeployedComponentMetadata().iterator();

      while(var1.hasNext()) {
         Jsr250MetadataI child = (Jsr250MetadataI)var1.next();
         child.refresh();
      }

   }

   public boolean isInvokeLifecycleMethod() {
      return this.invokeLifecycleMethod;
   }

   public void setInvokeLifecycleMethod(boolean invokeLifecycleMethod) {
      this.invokeLifecycleMethod = invokeLifecycleMethod;
   }
}
