package org.jboss.weld.bootstrap;

import java.util.Collection;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.DeploymentStructures;

public final class BeanDeploymentFinder {
   private final BeanDeploymentArchiveMapping bdaMapping;
   private final Deployment deployment;
   private final Collection contexts;
   private final BeanManagerImpl deploymentManager;

   public BeanDeploymentFinder(BeanDeploymentArchiveMapping bdaMapping, Deployment deployment, Collection contexts, BeanManagerImpl deploymentManager) {
      this.bdaMapping = bdaMapping;
      this.deployment = deployment;
      this.contexts = contexts;
      this.deploymentManager = deploymentManager;
   }

   public BeanDeployment getOrCreateBeanDeployment(Class clazz) {
      return DeploymentStructures.getOrCreateBeanDeployment(this.deployment, this.deploymentManager, this.bdaMapping, this.contexts, clazz);
   }
}
