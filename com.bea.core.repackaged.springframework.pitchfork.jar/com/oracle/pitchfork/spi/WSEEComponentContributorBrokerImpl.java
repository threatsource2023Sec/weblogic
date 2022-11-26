package com.oracle.pitchfork.spi;

import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.bea.core.repackaged.springframework.context.support.AbstractApplicationContext;
import com.oracle.pitchfork.inject.Jsr250Metadata;
import com.oracle.pitchfork.interfaces.WSEEComponentContributorBroker;
import com.oracle.pitchfork.interfaces.inject.ComponentContributor;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import com.oracle.pitchfork.server.Bootstrap;

class WSEEComponentContributorBrokerImpl implements WSEEComponentContributorBroker {
   private AbstractApplicationContext springBeanFactory;

   public void init(ComponentContributor componentContributor) {
      this.springBeanFactory = (new Bootstrap()).deploy(componentContributor);
   }

   public Object getBean(String clzName) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
      try {
         return this.springBeanFactory.getBean(clzName);
      } catch (BeanCreationException var4) {
         InstantiationException ie = new InstantiationException(var4.getMessage());
         ie.initCause(var4);
         throw ie;
      }
   }

   public Jsr250MetadataI newJsr250Metadata(String componentName, Class componentClass, DeploymentUnitMetadataI dum) {
      return new Jsr250Metadata(dum, componentName, componentClass);
   }
}
