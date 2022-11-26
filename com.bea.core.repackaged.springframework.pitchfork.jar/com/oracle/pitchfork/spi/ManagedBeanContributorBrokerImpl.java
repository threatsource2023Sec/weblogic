package com.oracle.pitchfork.spi;

import com.oracle.pitchfork.inject.DeploymentUnitMetadata;
import com.oracle.pitchfork.interfaces.ManagedBeanContributorBroker;
import com.oracle.pitchfork.interfaces.inject.ComponentContributor;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;

class ManagedBeanContributorBrokerImpl extends BaseComponentBrokerImpl implements ManagedBeanContributorBroker {
   public void initialize(ClassLoader cl, ComponentContributor cc) {
      DeploymentUnitMetadata metadata = new DeploymentUnitMetadata();
      metadata.setInvokeLifecycleMethod(false);
      super.initialize(cl, (String)null, (String)null, false, cc, metadata);
   }

   public Object createBeanInstance(String beanName, Class beanClass, boolean createProxy) throws IllegalAccessException, InstantiationException {
      return super.getBean(beanName, beanClass, createProxy);
   }

   public Jsr250MetadataI createJsr250Metadata(DeploymentUnitMetadataI metadata, String componentName, Class componentClass) {
      return new ManagedBeanProxyMetadata(metadata, componentName, componentClass, this.usesSpringExtensionModel);
   }
}
