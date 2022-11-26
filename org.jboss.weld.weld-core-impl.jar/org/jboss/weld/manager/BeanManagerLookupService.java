package org.jboss.weld.manager;

import java.util.concurrent.ConcurrentMap;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.bootstrap.spi.CDI11Deployment;

public class BeanManagerLookupService implements Service {
   private final CDI11Deployment deployment;
   private final ConcurrentMap bdaToBeanManagerMap;

   public BeanManagerLookupService(CDI11Deployment deployment, ConcurrentMap bdaToBeanManagerMap) {
      this.deployment = deployment;
      this.bdaToBeanManagerMap = bdaToBeanManagerMap;
   }

   private BeanManagerImpl lookupBeanManager(Class javaClass) {
      if (this.deployment == null) {
         return null;
      } else {
         BeanDeploymentArchive archive = this.deployment.getBeanDeploymentArchive(javaClass);
         return archive == null ? null : (BeanManagerImpl)this.bdaToBeanManagerMap.get(archive);
      }
   }

   public static BeanManagerImpl lookupBeanManager(Class javaClass, BeanManagerImpl fallback) {
      BeanManagerLookupService lookup = (BeanManagerLookupService)fallback.getServices().get(BeanManagerLookupService.class);
      if (lookup == null) {
         return fallback;
      } else {
         BeanManagerImpl result = lookup.lookupBeanManager(javaClass);
         return result == null ? fallback : result;
      }
   }

   public void cleanup() {
   }
}
