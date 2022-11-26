package com.oracle.injection.provider.weld;

import com.oracle.injection.InjectionArchive;
import com.oracle.injection.InjectionArchiveType;
import com.oracle.injection.ejb.EjbDescriptor;
import com.oracle.injection.spi.ContainerIntegrationService;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.weld.bootstrap.api.Bootstrap;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.bootstrap.spi.BeansXml;
import org.jboss.weld.bootstrap.spi.EEModuleDescriptor;
import org.jboss.weld.injection.spi.InjectionServices;
import org.jboss.weld.resources.spi.ResourceLoader;

class BasicBeanDeploymentArchive implements WlsBeanDeploymentArchive {
   static final Logger logger = Logger.getLogger(BasicBeanDeploymentArchive.class.getName());
   private final Bootstrap m_weldBootstrap;
   private final InjectionArchive injectionArchive;
   private final ServiceRegistry m_serviceRegistry;
   private final Collection m_beansXMLFiles;
   private final String m_archiveID;
   private final List m_accessibleBeanArchives = new ArrayList();
   private Collection ejbDescriptors;

   public BasicBeanDeploymentArchive(Bootstrap weldBootstrap, InjectionArchive injectionArchive, ContainerIntegrationService containerIntegrationService, EEModuleDescriptor eeModuleDescriptor) {
      this.injectionArchive = injectionArchive;
      this.m_weldBootstrap = weldBootstrap;
      this.m_serviceRegistry = new SimpleServiceRegistry();
      this.m_archiveID = generateArchiveID(injectionArchive);
      this.assignEjbDescriptors(injectionArchive);
      URL urlToBeansXML;
      if (injectionArchive.getArchiveType() == InjectionArchiveType.WAR) {
         urlToBeansXML = injectionArchive.getResource("WEB-INF/beans.xml");
         if (urlToBeansXML == null) {
            urlToBeansXML = injectionArchive.getResource("WEB-INF/classes/META-INF/beans.xml");
         }
      } else {
         urlToBeansXML = injectionArchive.getResource("META-INF/beans.xml");
      }

      this.m_beansXMLFiles = Collections.singletonList(urlToBeansXML);
      this.m_serviceRegistry.add(ResourceLoader.class, new BasicResourceLoader(injectionArchive));
      this.m_serviceRegistry.add(InjectionServices.class, new WeldInjectionServicesAdapter(containerIntegrationService, injectionArchive));
      if (eeModuleDescriptor != null) {
         this.m_serviceRegistry.add(EEModuleDescriptor.class, eeModuleDescriptor);
      }

      if (logger.isLoggable(Level.FINE)) {
         logger.log(Level.FINE, "Creating Bean Deployment Archive for " + injectionArchive.getClassPathArchiveName() + ".  The classes in this archive will be CDI-enabled.");
      }

   }

   private void assignEjbDescriptors(InjectionArchive injectionArchive) {
      this.ejbDescriptors = new ArrayList();
      if (injectionArchive.getEjbDescriptors() != null) {
         Iterator var2 = injectionArchive.getEjbDescriptors().iterator();

         while(var2.hasNext()) {
            EjbDescriptor descriptor = (EjbDescriptor)var2.next();
            this.ejbDescriptors.add(new WeldEjbDescriptorAdapter(descriptor));
         }
      }

   }

   public Collection getBeanClasses() {
      return this.injectionArchive.getBeanClassNames();
   }

   public Collection getBeanDeploymentArchives() {
      return this.m_accessibleBeanArchives;
   }

   public BeansXml getBeansXml() {
      BeansXml beansXml = null;
      if (!this.m_beansXMLFiles.isEmpty()) {
         URL beansXMLFile = (URL)this.m_beansXMLFiles.iterator().next();
         if (beansXMLFile != null) {
            beansXml = this.m_weldBootstrap.parse(this.m_beansXMLFiles);
         }
      }

      return beansXml;
   }

   public Collection getEjbs() {
      return this.ejbDescriptors;
   }

   public String getId() {
      return this.m_archiveID;
   }

   public ServiceRegistry getServices() {
      return this.m_serviceRegistry;
   }

   void addAccessibleBeanArchive(BeanDeploymentArchive beanArchive) {
      this.m_accessibleBeanArchives.add(beanArchive);
   }

   static String generateArchiveID(InjectionArchive injectionArchive) {
      return injectionArchive.getArchiveName() + "_" + injectionArchive.getArchiveType();
   }

   public ClassLoader getBdaClassLoader() {
      return this.injectionArchive.getClassLoader();
   }

   public Collection getComponentClassesForProcessInjectionTarget() {
      return this.injectionArchive.getComponentClassNamesForProcessInjectionTarget();
   }
}
