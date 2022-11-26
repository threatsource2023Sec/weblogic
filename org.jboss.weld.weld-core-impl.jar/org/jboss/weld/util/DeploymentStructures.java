package org.jboss.weld.util;

import java.util.Collection;
import org.jboss.weld.bootstrap.BeanDeployment;
import org.jboss.weld.bootstrap.BeanDeploymentArchiveMapping;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.logging.UtilLogger;
import org.jboss.weld.manager.BeanManagerImpl;

public class DeploymentStructures {
   private DeploymentStructures() {
   }

   public static BeanDeployment getOrCreateBeanDeployment(Deployment deployment, BeanManagerImpl deploymentManager, BeanDeploymentArchiveMapping bdaMapping, Collection contexts, Class clazz) {
      BeanDeploymentArchive beanDeploymentArchive = deployment.loadBeanDeploymentArchive(clazz);
      if (beanDeploymentArchive == null) {
         throw UtilLogger.LOG.unableToFindBeanDeploymentArchive(clazz);
      } else {
         BeanDeployment beanDeployment = bdaMapping.getBeanDeployment(beanDeploymentArchive);
         if (beanDeployment == null) {
            beanDeployment = new BeanDeployment(beanDeploymentArchive, deploymentManager, deployment.getServices(), contexts, true);
            bdaMapping.put(beanDeploymentArchive, beanDeployment);
         }

         return beanDeployment;
      }
   }
}
