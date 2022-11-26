package org.jboss.weld.bootstrap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.bootstrap.spi.Metadata;
import org.jboss.weld.resources.DefaultResourceLoader;
import org.jboss.weld.resources.WeldClassLoaderResourceLoader;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.util.ServiceLoader;
import org.jboss.weld.util.Services;

class AdditionalServiceLoader {
   private final Deployment deployment;

   AdditionalServiceLoader(Deployment deployment) {
      this.deployment = deployment;
   }

   void loadAdditionalServices(ServiceRegistry registry) {
      Iterator var2 = this.getResourceLoaders().iterator();

      while(var2.hasNext()) {
         ResourceLoader loader = (ResourceLoader)var2.next();
         Iterator var4 = ServiceLoader.load(Service.class, loader).iterator();

         while(var4.hasNext()) {
            Metadata metadata = (Metadata)var4.next();
            Service service = (Service)metadata.getValue();
            Iterator var7 = Services.identifyServiceInterfaces(service.getClass(), new HashSet()).iterator();

            while(var7.hasNext()) {
               Class serviceInterface = (Class)var7.next();
               Services.put(registry, serviceInterface, service);
            }
         }
      }

   }

   private Set getResourceLoaders() {
      Set resourceLoaders = new HashSet();
      Iterator var2 = this.deployment.getBeanDeploymentArchives().iterator();

      while(var2.hasNext()) {
         BeanDeploymentArchive archive = (BeanDeploymentArchive)var2.next();
         ResourceLoader resourceLoader = (ResourceLoader)archive.getServices().get(ResourceLoader.class);
         if (resourceLoader != null) {
            resourceLoaders.add(resourceLoader);
         }
      }

      resourceLoaders.add(WeldClassLoaderResourceLoader.INSTANCE);
      resourceLoaders.add(DefaultResourceLoader.INSTANCE);
      return resourceLoaders;
   }
}
