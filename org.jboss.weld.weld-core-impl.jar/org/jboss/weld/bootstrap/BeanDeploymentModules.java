package org.jboss.weld.bootstrap;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.spi.EEModuleDescriptor;
import org.jboss.weld.bootstrap.spi.EEModuleDescriptor.ModuleType;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.collections.WeldCollections;

public final class BeanDeploymentModules implements Service, Iterable {
   private final ConcurrentMap modules;
   private final BeanDeploymentModule defaultModule;

   BeanDeploymentModules(String contextId, ServiceRegistry services) {
      this.defaultModule = new BeanDeploymentModule(BeanDeploymentModules.class.getSimpleName() + ".DEFAULT", contextId, false, services);
      this.modules = new ConcurrentHashMap();
   }

   public BeanDeploymentModule getModule(BeanManagerImpl manager) {
      EEModuleDescriptor descriptor = (EEModuleDescriptor)manager.getServices().get(EEModuleDescriptor.class);
      if (descriptor == null) {
         this.defaultModule.addManager(manager);
         this.modules.putIfAbsent(this.defaultModule.getId(), this.defaultModule);
         return this.defaultModule;
      } else {
         BeanDeploymentModule module = (BeanDeploymentModule)this.modules.get(descriptor.getId());
         if (module == null) {
            module = new BeanDeploymentModule(descriptor.getId(), manager.getContextId(), descriptor.getType() == ModuleType.WEB, manager.getServices());
            module = (BeanDeploymentModule)WeldCollections.putIfAbsent(this.modules, descriptor.getId(), module);
         }

         module.addManager(manager);
         return module;
      }
   }

   public void processBeanDeployments(Iterable deployments) {
      Iterator var2 = deployments.iterator();

      while(var2.hasNext()) {
         BeanDeployment deployment = (BeanDeployment)var2.next();
         this.getModule(deployment.getBeanManager());
      }

   }

   public void cleanup() {
      this.modules.clear();
   }

   public Iterator iterator() {
      return this.modules.values().iterator();
   }

   public String toString() {
      return this.modules.values().toString();
   }
}
