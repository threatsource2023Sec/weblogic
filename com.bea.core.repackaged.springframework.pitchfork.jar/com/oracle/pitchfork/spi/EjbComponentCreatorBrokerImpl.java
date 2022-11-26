package com.oracle.pitchfork.spi;

import com.oracle.pitchfork.inject.DeploymentUnitMetadata;
import com.oracle.pitchfork.intercept.InterceptionMetadata;
import com.oracle.pitchfork.interfaces.EjbComponentCreatorBroker;
import com.oracle.pitchfork.interfaces.TargetWrapper;
import com.oracle.pitchfork.interfaces.inject.ComponentContributor;
import java.util.HashMap;
import java.util.Map;

class EjbComponentCreatorBrokerImpl extends BaseComponentBrokerImpl implements EjbComponentCreatorBroker {
   public void initialize(ClassLoader cl, String springConfigResource, String compFactoryName, boolean usesSpringExtensionModel, ComponentContributor cc) {
      DeploymentUnitMetadata metadata = new DeploymentUnitMetadata();
      metadata.setInvokeLifecycleMethod(false);
      super.initialize(cl, springConfigResource, compFactoryName, usesSpringExtensionModel, cc, metadata);
   }

   public Object assembleEJB3Proxy(Object bean, String compName) {
      Object interceptorInstances;
      if (bean instanceof TargetWrapper) {
         interceptorInstances = ((TargetWrapper)bean).getInterceptionInstances();
      } else {
         interceptorInstances = new HashMap();
      }

      InterceptionMetadata im = (InterceptionMetadata)this.compContributor.getMetadata(compName);
      return im.createProxyIfNecessary(bean, (Map)interceptorInstances);
   }
}
