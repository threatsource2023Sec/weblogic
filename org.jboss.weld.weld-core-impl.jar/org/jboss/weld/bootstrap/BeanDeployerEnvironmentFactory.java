package org.jboss.weld.bootstrap;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.collections.SetMultimap;

public class BeanDeployerEnvironmentFactory {
   private BeanDeployerEnvironmentFactory() {
   }

   public static BeanDeployerEnvironment newEnvironment(BeanManagerImpl manager) {
      return new BeanDeployerEnvironment(manager);
   }

   public static BeanDeployerEnvironment newConcurrentEnvironment(BeanManagerImpl manager) {
      return new BeanDeployerEnvironment(Collections.newSetFromMap(new ConcurrentHashMap()), Collections.newSetFromMap(new ConcurrentHashMap()), SetMultimap.newConcurrentSetMultimap(), Collections.newSetFromMap(new ConcurrentHashMap()), SetMultimap.newConcurrentSetMultimap(), Collections.newSetFromMap(new ConcurrentHashMap()), Collections.newSetFromMap(new ConcurrentHashMap()), Collections.newSetFromMap(new ConcurrentHashMap()), Collections.newSetFromMap(new ConcurrentHashMap()), Collections.newSetFromMap(new ConcurrentHashMap()), Collections.newSetFromMap(new ConcurrentHashMap()), Collections.newSetFromMap(new ConcurrentHashMap()), manager);
   }
}
