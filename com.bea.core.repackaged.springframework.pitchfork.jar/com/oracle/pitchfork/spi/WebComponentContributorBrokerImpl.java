package com.oracle.pitchfork.spi;

import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.oracle.pitchfork.inject.DeploymentUnitMetadata;
import com.oracle.pitchfork.inject.Jsr250Metadata;
import com.oracle.pitchfork.interfaces.WebComponentContributorBroker;
import com.oracle.pitchfork.interfaces.inject.ComponentContributor;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;

class WebComponentContributorBrokerImpl extends BaseComponentBrokerImpl implements WebComponentContributorBroker {
   public void initialize(ClassLoader cl, String springConfigResource, String compFactoryName, boolean usesSpringExtensionModel, ComponentContributor cc) {
      super.initialize(cl, springConfigResource, compFactoryName, usesSpringExtensionModel, cc, (DeploymentUnitMetadata)null);
   }

   public Object getBean(String ejbName, Class beanClass, boolean createProxy) {
      throw new UnsupportedOperationException();
   }

   public Object getNewInstance(String clzName) throws InstantiationException {
      if (this.usesSpringExtensionModel) {
         try {
            return this.springBeanFactory.getBean(clzName);
         } catch (BeanCreationException var4) {
            InstantiationException ie = new InstantiationException(var4.getMessage());
            ie.initCause(var4);
            throw ie;
         }
      } else {
         return null;
      }
   }

   public Jsr250MetadataI createJsr250Metadata(DeploymentUnitMetadataI metadata, String componentName, Class componentClass) {
      return new Jsr250Metadata(metadata, componentName, componentClass);
   }
}
