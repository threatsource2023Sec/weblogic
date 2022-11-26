package org.jboss.weld.bootstrap;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;

public class BeanDeploymentArchiveMapping {
   private final Map beanDeployments = new HashMap();
   private final ConcurrentMap beanManagers = new ConcurrentHashMap();

   public void put(BeanDeploymentArchive bda, BeanDeployment beanDeployment) {
      this.beanDeployments.put(bda, beanDeployment);
      this.beanManagers.put(bda, beanDeployment.getBeanManager());
   }

   public BeanDeployment getBeanDeployment(BeanDeploymentArchive bda) {
      return (BeanDeployment)this.beanDeployments.get(bda);
   }

   public Collection getBeanDeployments() {
      return this.beanDeployments.values();
   }

   public ConcurrentMap getBdaToBeanManagerMap() {
      return this.beanManagers;
   }

   boolean isNonuniqueIdentifierDetected() {
      Set beanDeploymentArchiveIds = new HashSet();
      Set beanManagerIds = new HashSet();
      Iterator var3 = this.beanDeployments.entrySet().iterator();

      Map.Entry entry;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         entry = (Map.Entry)var3.next();
      } while(beanDeploymentArchiveIds.add(((BeanDeploymentArchive)entry.getKey()).getId()) && beanManagerIds.add(((BeanDeployment)entry.getValue()).getBeanManager().getId()));

      return true;
   }
}
