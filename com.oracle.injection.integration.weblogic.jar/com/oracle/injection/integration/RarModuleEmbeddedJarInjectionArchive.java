package com.oracle.injection.integration;

import com.oracle.injection.InjectionArchiveType;
import com.oracle.injection.InjectionException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;
import weblogic.connector.deploy.ConnectorModuleExtensionContext;
import weblogic.utils.jars.VirtualJarFile;

public class RarModuleEmbeddedJarInjectionArchive extends ModuleInjectionArchive {
   private static Logger logger = Logger.getLogger(RarModuleEmbeddedJarInjectionArchive.class.getName());
   private VirtualJarFile virtualJarFile;
   private InjectionArchiveType injectionArchiveType;
   private ApplicationContextInternal appCtx;
   private String relativeEmbeddedjarName;
   private ClassLoader archiveClassLoader;

   public RarModuleEmbeddedJarInjectionArchive(ApplicationContextInternal appCtx, ModuleContext moduleContext, ModuleExtensionContext moduleExtensionContext, InjectionArchiveType injectionArchiveType, VirtualJarFile virtualJarFile, String relativeEmbeddedJarName, ClassLoader archiveClassLoader) throws InjectionException {
      super(moduleContext, moduleExtensionContext);
      this.virtualJarFile = virtualJarFile;
      this.injectionArchiveType = injectionArchiveType;
      this.appCtx = appCtx;
      this.relativeEmbeddedjarName = relativeEmbeddedJarName;
      this.archiveClassLoader = archiveClassLoader;
      this.processEmbeddedJar();
   }

   protected void loadBeanClassNames() {
   }

   public ClassLoader getClassLoader() {
      return this.archiveClassLoader;
   }

   protected void processEmbeddedJar() throws InjectionException {
      List beanNames = new ArrayList();
      List managedClassNames = this.getManagedClassNames();
      if (this.injectionArchiveType.equals(InjectionArchiveType.RAR)) {
         if (!(this.m_moduleExtensionContext instanceof ConnectorModuleExtensionContext)) {
            throw new InjectionException("The module extension was not an instance of ConnectorModuleExtensionContext.");
         }

         ConnectorModuleExtensionContext connectorModuleExtensionContext = (ConnectorModuleExtensionContext)this.m_moduleExtensionContext;
         beanNames.addAll(connectorModuleExtensionContext.excludeConnectorClassNames(managedClassNames));
      } else {
         beanNames.addAll(managedClassNames);
      }

      this.setBeanClassNames(beanNames);
   }

   protected List getManagedClassNames() throws InjectionException {
      List managedClassNames = new ArrayList();
      CDIUtils.getManagedClassNamesAndEmbeddedJars(this.virtualJarFile, this.relativeEmbeddedjarName, this.m_moduleContext, this.m_moduleExtensionContext, this.appCtx, managedClassNames, (Collection)null);
      return managedClassNames;
   }

   public InjectionArchiveType getArchiveType() {
      return this.injectionArchiveType;
   }

   public URL getResource(String resourceName) {
      return this.virtualJarFile.getResource(resourceName);
   }

   public String getArchiveName() {
      return this.m_moduleContext.getApplicationName() + super.getArchiveName() + this.encodeVirtualJarFileName();
   }

   public String getClassPathArchiveName() {
      return this.virtualJarFile.getName();
   }

   public URL getURL() {
      try {
         URI uri = new URI("file", this.encodeVirtualJarFileName(), (String)null);
         return uri.toURL();
      } catch (MalformedURLException | URISyntaxException var2) {
         logger.log(Level.WARNING, "Exception occurred while trying to create URL for embedded injection archive.", var2);
         return null;
      }
   }

   private String encodeVirtualJarFileName() {
      return this.virtualJarFile.getName().replace(File.separatorChar, '/').replace(" ", "%20");
   }
}
