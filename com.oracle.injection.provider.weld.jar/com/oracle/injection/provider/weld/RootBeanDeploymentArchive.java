package com.oracle.injection.provider.weld;

import com.oracle.injection.InjectionArchive;
import com.oracle.injection.spi.ContainerIntegrationService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.bootstrap.spi.BeansXml;
import org.jboss.weld.bootstrap.spi.EEModuleDescriptor;
import org.jboss.weld.injection.spi.InjectionServices;

public class RootBeanDeploymentArchive implements WlsBeanDeploymentArchive {
   private BasicBeanDeploymentArchive moduleBda;
   private ClassLoader moduleClassLoader;
   private String id;
   private final List accessibleBeanArchives = new ArrayList();
   private final ServiceRegistry serviceRegistry = new SimpleServiceRegistry();

   public RootBeanDeploymentArchive(InjectionArchive moduleInjectionArchive, BasicBeanDeploymentArchive moduleBda, ContainerIntegrationService containerIntegrationService, EEModuleDescriptor eeModuleDescriptor) {
      this.moduleBda = moduleBda;
      this.moduleClassLoader = moduleInjectionArchive.getClassLoader();
      this.id = "root_" + moduleInjectionArchive.getArchiveName();
      this.serviceRegistry.add(InjectionServices.class, new WeldInjectionServicesAdapter(containerIntegrationService, moduleInjectionArchive));
      if (eeModuleDescriptor != null) {
         this.serviceRegistry.add(EEModuleDescriptor.class, eeModuleDescriptor);
      }

   }

   public Collection getBeanDeploymentArchives() {
      return this.accessibleBeanArchives;
   }

   public Collection getBeanClasses() {
      return Collections.emptyList();
   }

   public BeansXml getBeansXml() {
      return null;
   }

   public Collection getEjbs() {
      return Collections.emptyList();
   }

   public ServiceRegistry getServices() {
      return this.serviceRegistry;
   }

   public String getId() {
      return this.id;
   }

   public ClassLoader getModuleClassLoaderForBDA() {
      return this.moduleClassLoader;
   }

   public void addAccessibleBeanArchive(BeanDeploymentArchive beanArchive) {
      this.accessibleBeanArchives.add(beanArchive);
   }

   public ClassLoader getBdaClassLoader() {
      return this.moduleClassLoader;
   }

   public Collection getComponentClassesForProcessInjectionTarget() {
      return Collections.EMPTY_SET;
   }

   public BasicBeanDeploymentArchive getModuleBda() {
      return this.moduleBda;
   }
}
