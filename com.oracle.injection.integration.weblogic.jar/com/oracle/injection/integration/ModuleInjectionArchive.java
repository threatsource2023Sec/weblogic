package com.oracle.injection.integration;

import com.oracle.injection.InjectionArchive;
import com.oracle.injection.InjectionArchiveType;
import com.oracle.injection.spi.WebModuleIntegrationService;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.naming.Environment;
import weblogic.ejb.spi.BeanInfo;
import weblogic.ejb.spi.DeploymentInfo;
import weblogic.ejb.spi.MessageDrivenBeanInfo;
import weblogic.ejb.spi.SessionBeanInfo;
import weblogic.j2ee.descriptor.wl.PojoEnvironmentBean;
import weblogic.utils.classloaders.Source;

class ModuleInjectionArchive implements InjectionArchive {
   private static Logger m_logger = Logger.getLogger(ModuleInjectionArchive.class.getName());
   protected ModuleContext m_moduleContext;
   protected ModuleExtensionContext m_moduleExtensionContext;
   private Collection beanClassNames;
   private final int[] beanClassNamesLock = new int[0];
   private List listOfEjbDescriptors = null;

   ModuleInjectionArchive() {
   }

   ModuleInjectionArchive(ModuleContext moduleContext, ModuleExtensionContext moduleExtensionContext) {
      if (moduleContext == null) {
         throw new IllegalArgumentException("ModuleContext argument cannot be null");
      } else if (moduleExtensionContext == null) {
         throw new IllegalArgumentException("ModuleExtensionContext argument cannot be null");
      } else {
         this.m_moduleContext = moduleContext;
         this.m_moduleExtensionContext = moduleExtensionContext;
         this.loadBeanClassNames();
      }
   }

   protected void loadBeanClassNames() {
      synchronized(this.beanClassNamesLock) {
         this.beanClassNames = new ArrayList();
         Collection moduleExtensionContextBeanClasses = this.m_moduleExtensionContext.getBeanClassNames();
         if (moduleExtensionContextBeanClasses != null && !moduleExtensionContextBeanClasses.isEmpty()) {
            this.beanClassNames.addAll(moduleExtensionContextBeanClasses);
         }

      }
   }

   public ClassLoader getClassLoader() {
      return this.m_moduleContext.getClassLoader();
   }

   public synchronized Collection getEjbDescriptors() {
      if (this.listOfEjbDescriptors == null) {
         DeploymentInfo deploymentInfo = (DeploymentInfo)this.m_moduleContext.getRegistry().get(DeploymentInfo.class.getName());
         if (deploymentInfo != null) {
            this.listOfEjbDescriptors = new ArrayList();
            Iterator var2 = deploymentInfo.getBeanInfos().iterator();

            while(var2.hasNext()) {
               BeanInfo beanInfo = (BeanInfo)var2.next();
               EjbDescriptorAdapter adapter;
               if (beanInfo instanceof SessionBeanInfo) {
                  SessionBeanInfo sessionBeanInfo = (SessionBeanInfo)beanInfo;
                  adapter = new EjbDescriptorAdapter(sessionBeanInfo, this.m_moduleContext.getId());
                  if (this.getBeanClassNames().contains(adapter.getNonGeneratedEjbBeanClass().getName())) {
                     this.listOfEjbDescriptors.add(adapter);
                  }
               } else if (beanInfo instanceof MessageDrivenBeanInfo) {
                  MessageDrivenBeanInfo mdbi = (MessageDrivenBeanInfo)beanInfo;
                  adapter = new EjbDescriptorAdapter(mdbi, this.m_moduleContext.getId());
                  if (this.getBeanClassNames().contains(adapter.getNonGeneratedEjbBeanClass().getName())) {
                     this.listOfEjbDescriptors.add(adapter);
                  }
               }
            }
         } else {
            this.listOfEjbDescriptors = Collections.emptyList();
         }
      }

      return this.listOfEjbDescriptors;
   }

   public URL getURL() {
      try {
         URI warURI = new URI("file", this.m_moduleContext.getURI(), (String)null);
         return warURI.toURL();
      } catch (URISyntaxException var2) {
         m_logger.log(Level.WARNING, "Exception occurred while trying to create URL", var2);
      } catch (MalformedURLException var3) {
         m_logger.log(Level.WARNING, "Exception occurred while trying to create URL", var3);
      }

      return null;
   }

   protected void setBeanClassNames(Collection beanClassNames) {
      this.beanClassNames = beanClassNames;
   }

   public Collection getBeanClassNames() {
      synchronized(this.beanClassNamesLock) {
         if (this.beanClassNames.isEmpty()) {
            this.loadBeanClassNames();
         }

         return this.beanClassNames;
      }
   }

   public URL getResource(String resourceName) {
      List listOfSources = this.m_moduleExtensionContext.getSources(resourceName);
      return !listOfSources.isEmpty() ? ((Source)listOfSources.get(0)).getURL() : null;
   }

   public Object getCustomContext() {
      return this.m_moduleExtensionContext instanceof WebModuleIntegrationService ? ((WebModuleIntegrationService)this.m_moduleExtensionContext).getServletContext() : null;
   }

   public InjectionArchiveType getArchiveType() {
      return InjectionArchiveType.WAR;
   }

   public String getArchiveName() {
      return this.m_moduleContext.getId();
   }

   public String getClassPathArchiveName() {
      return this.m_moduleContext.getVirtualJarFile().getName();
   }

   public Collection getEmbeddedArchives() {
      return null;
   }

   public Collection getComponentClassNamesForProcessInjectionTarget() {
      ArrayList classes = new ArrayList();
      DeploymentInfo deploymentInfo = (DeploymentInfo)this.m_moduleContext.getRegistry().get(DeploymentInfo.class.getName());
      if (deploymentInfo != null) {
         Iterator var3 = deploymentInfo.getBeanInfos().iterator();

         while(var3.hasNext()) {
            BeanInfo beanInfo = (BeanInfo)var3.next();
            if (beanInfo instanceof MessageDrivenBeanInfo) {
               String beanClassName = beanInfo.getBeanClass().getName();
               if (this.getBeanClassNames().contains(beanClassName)) {
                  classes.add(beanClassName);
               }
            }
         }
      }

      return classes;
   }

   public ModuleExtensionContext getModuleExtensionContext() {
      return this.m_moduleExtensionContext;
   }

   public PojoEnvironmentBean getPojoEnvironmentBean() {
      return this.m_moduleExtensionContext.getPojoEnvironmentBean();
   }

   public Context getRootContext(String componentName) {
      if (this.getArchiveType().equals(InjectionArchiveType.RAR)) {
         return null;
      } else {
         Environment environment = this.m_moduleExtensionContext.getEnvironment(componentName);
         if (environment != null) {
            return environment.getRootContext();
         } else {
            Collection environments = this.m_moduleExtensionContext.getEnvironments();
            return environments != null && environments.size() > 0 ? ((Environment)environments.iterator().next()).getRootContext() : null;
         }
      }
   }
}
