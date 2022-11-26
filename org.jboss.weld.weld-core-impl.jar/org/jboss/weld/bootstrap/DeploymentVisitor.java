package org.jboss.weld.bootstrap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.jboss.weld.bootstrap.api.Environment;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.util.collections.WeldCollections;

public class DeploymentVisitor {
   private final BeanManagerImpl deploymentManager;
   private final Environment environment;
   private final Deployment deployment;
   private final BeanDeploymentArchiveMapping bdaMapping;
   private final Collection contexts;

   public DeploymentVisitor(BeanManagerImpl deploymentManager, Environment environment, Deployment deployment, Collection contexts, BeanDeploymentArchiveMapping bdaMapping) {
      this.deploymentManager = deploymentManager;
      this.environment = environment;
      this.deployment = deployment;
      this.contexts = contexts;
      this.bdaMapping = bdaMapping;
   }

   public void visit() {
      Set seenBeanDeploymentArchives = new HashSet();
      Iterator var2 = this.deployment.getBeanDeploymentArchives().iterator();

      while(var2.hasNext()) {
         BeanDeploymentArchive archive = (BeanDeploymentArchive)var2.next();
         if (!seenBeanDeploymentArchives.contains(archive)) {
            this.visit(archive, seenBeanDeploymentArchives);
         }
      }

      if (this.bdaMapping.isNonuniqueIdentifierDetected()) {
         throw BootstrapLogger.LOG.nonuniqueBeanDeploymentIdentifier(WeldCollections.toMultiRowString(this.bdaMapping.getBeanDeployments()));
      }
   }

   private BeanDeployment visit(BeanDeploymentArchive bda, Set seenBeanDeploymentArchives) {
      this.copyService(bda, ResourceLoader.class);
      WeldStartup.verifyServices(bda.getServices(), this.environment.getRequiredBeanDeploymentArchiveServices(), bda.getId());
      if (bda.getId() == null) {
         throw BootstrapLogger.LOG.deploymentArchiveNull(bda);
      } else {
         BeanDeployment parent = this.bdaMapping.getBeanDeployment(bda);
         if (parent == null) {
            parent = new BeanDeployment(bda, this.deploymentManager, this.deployment.getServices(), this.contexts);
            this.bdaMapping.put(bda, parent);
         }

         seenBeanDeploymentArchives.add(bda);

         BeanDeployment child;
         for(Iterator var4 = bda.getBeanDeploymentArchives().iterator(); var4.hasNext(); parent.getBeanManager().addAccessibleBeanManager(child.getBeanManager())) {
            BeanDeploymentArchive archive = (BeanDeploymentArchive)var4.next();
            if (!seenBeanDeploymentArchives.contains(archive)) {
               child = this.visit(archive, seenBeanDeploymentArchives);
            } else {
               child = this.bdaMapping.getBeanDeployment(archive);
            }
         }

         return parent;
      }
   }

   private void copyService(BeanDeploymentArchive archive, Class serviceClass) {
      ServiceRegistry registry = archive.getServices();
      if (!registry.contains(serviceClass)) {
         Service service = this.deployment.getServices().get(serviceClass);
         if (service != null) {
            registry.add(serviceClass, service);
         }
      }

   }
}
