package com.oracle.injection.provider.weld;

import com.oracle.injection.InjectionArchive;
import com.oracle.injection.InjectionArchiveType;
import com.oracle.injection.spi.ContainerIntegrationService;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.weld.bootstrap.spi.BeansXml;
import org.jboss.weld.injection.spi.InjectionServices;
import org.jboss.weld.resources.spi.ResourceLoader;
import weblogic.j2ee.descriptor.wl.PojoEnvironmentBean;

public class ExtensionBeanDeploymentArchive implements WlsBeanDeploymentArchive, InjectionArchive {
   static final Logger logger = Logger.getLogger(ExtensionBeanDeploymentArchive.class.getName());
   private List beanClasses = new ArrayList();
   private ClassLoader bdaClassLoader;
   private final String id;
   private final ServiceRegistry serviceRegistry;
   private final List accessibleBeanArchives = new ArrayList();
   private final String classPathArchiveName;

   public ExtensionBeanDeploymentArchive(String bdaId, Class extensionClass, ContainerIntegrationService integrationService, String classPathArchiveName) {
      this.bdaClassLoader = extensionClass.getClassLoader();
      this.id = "Extension_BDA_" + bdaId;
      this.serviceRegistry = new SimpleServiceRegistry();
      this.serviceRegistry.add(InjectionServices.class, new WeldInjectionServicesAdapter(integrationService, this));
      ClassLoader classLoader = extensionClass.getClassLoader();
      if (classLoader != null) {
         this.serviceRegistry.add(ResourceLoader.class, new BasicResourceLoader(classLoader));
      }

      this.classPathArchiveName = classPathArchiveName;
      logger.log(Level.FINE, "Creating Bean Deployment Archive for extension " + extensionClass.getName());
   }

   public Collection getBeanDeploymentArchives() {
      return this.accessibleBeanArchives;
   }

   public Collection getBeanClasses() {
      return this.beanClasses;
   }

   public BeansXml getBeansXml() {
      return BeansXml.EMPTY_BEANS_XML;
   }

   public Collection getEjbs() {
      return Collections.EMPTY_LIST;
   }

   public ServiceRegistry getServices() {
      return this.serviceRegistry;
   }

   public String getId() {
      return this.id;
   }

   public ClassLoader getBdaClassLoader() {
      return this.bdaClassLoader;
   }

   public Collection getComponentClassesForProcessInjectionTarget() {
      return Collections.EMPTY_SET;
   }

   public ClassLoader getClassLoader() {
      return this.getBdaClassLoader();
   }

   public Collection getEjbDescriptors() {
      return Collections.emptyList();
   }

   public Collection getBeanClassNames() {
      return this.getBeanClasses();
   }

   public URL getResource(String resourceName) {
      return null;
   }

   public Object getCustomContext() {
      return null;
   }

   public InjectionArchiveType getArchiveType() {
      return InjectionArchiveType.JAR;
   }

   public String getArchiveName() {
      return this.getId();
   }

   public String getClassPathArchiveName() {
      return this.classPathArchiveName;
   }

   public Collection getEmbeddedArchives() {
      return null;
   }

   public Collection getComponentClassNamesForProcessInjectionTarget() {
      return Collections.EMPTY_SET;
   }

   public PojoEnvironmentBean getPojoEnvironmentBean() {
      return null;
   }

   public Context getRootContext(String componentName) {
      return null;
   }
}
