package com.oracle.injection.integration;

import com.oracle.injection.InjectionArchiveType;
import com.oracle.injection.InjectionException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;
import weblogic.connector.deploy.ConnectorModuleExtensionContext;
import weblogic.utils.classloaders.Source;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class RarModuleInjectionArchive extends ModuleInjectionArchive {
   private ConnectorModuleExtensionContext connectorModuleExtensionContext;
   private List beanClassNames;
   private Collection embeddedInjectionArchives;
   private String archivePath;

   public RarModuleInjectionArchive(ModuleContext moduleContext, ModuleExtensionContext moduleExtensionContext) throws InjectionException {
      super(moduleContext, moduleExtensionContext);
      if (this.m_moduleExtensionContext instanceof ConnectorModuleExtensionContext) {
         this.connectorModuleExtensionContext = (ConnectorModuleExtensionContext)this.m_moduleExtensionContext;
         this.processModule();
         this.archivePath = this.extractArchivePath();
      } else {
         throw new InjectionException("The module extension was not an instance of ConnectorModuleExtensionContext.");
      }
   }

   public ClassLoader getClassLoader() {
      return this.connectorModuleExtensionContext.getConnectorClassLoader();
   }

   public InjectionArchiveType getArchiveType() {
      return InjectionArchiveType.RAR;
   }

   private VirtualJarFile getVirtualJarForEmbeddedJar(String embeddedjarName) throws InjectionException {
      List sources = this.m_moduleExtensionContext.getSources(embeddedjarName);
      Iterator var3 = sources.iterator();

      Source oneSource;
      do {
         if (!var3.hasNext()) {
            throw new InjectionException("Could not find a Source for embedded jar name: " + embeddedjarName);
         }

         oneSource = (Source)var3.next();
      } while(oneSource.getCodeSourceURL().getPath().endsWith(".rar"));

      try {
         return VirtualJarFactory.createVirtualJar(new File(oneSource.getURL().getPath()));
      } catch (IOException var6) {
         throw new InjectionException("Exception creating virtual jar file for " + embeddedjarName + " Exception: " + var6.getMessage(), var6);
      }
   }

   protected void processModule() throws InjectionException {
      this.beanClassNames = new ArrayList();
      this.embeddedInjectionArchives = new ArrayList();
      List managedClassNames = new ArrayList();
      List embeddedJarNames = new ArrayList();
      this.getManagedClassNamesAndEmbeddedJars(managedClassNames, embeddedJarNames);
      this.beanClassNames.addAll(this.connectorModuleExtensionContext.excludeConnectorClassNames(managedClassNames));
      Iterator var3 = embeddedJarNames.iterator();

      while(var3.hasNext()) {
         String oneEmbddedJarName = (String)var3.next();
         VirtualJarFile embeddedVirtualJarFile = this.getVirtualJarForEmbeddedJar(oneEmbddedJarName);
         RarModuleEmbeddedJarInjectionArchive moduleEmbeddedJarInjectionArchive = new RarModuleEmbeddedJarInjectionArchive(this.connectorModuleExtensionContext.getApplicationContext(), this.m_moduleContext, this.m_moduleExtensionContext, InjectionArchiveType.RAR, embeddedVirtualJarFile, oneEmbddedJarName, this.connectorModuleExtensionContext.getConnectorClassLoader());
         if (moduleEmbeddedJarInjectionArchive.getBeanClassNames().size() > 0) {
            this.embeddedInjectionArchives.add(moduleEmbeddedJarInjectionArchive);
         }
      }

   }

   protected void getManagedClassNamesAndEmbeddedJars(Collection managedClassNames, Collection embeddedJarNames) throws InjectionException {
      VirtualJarFile virtualJarFile = this.m_moduleContext.getVirtualJarFile();
      CDIUtils.getManagedClassNamesAndEmbeddedJars(virtualJarFile, "", this.m_moduleContext, this.m_moduleExtensionContext, this.connectorModuleExtensionContext.getApplicationContext(), managedClassNames, embeddedJarNames);
   }

   public Collection getEmbeddedArchives() {
      return this.embeddedInjectionArchives;
   }

   protected void loadBeanClassNames() {
   }

   public Collection getBeanClassNames() {
      return this.beanClassNames;
   }

   private String extractArchivePath() {
      List sources = this.m_moduleExtensionContext.getSources("");
      if (sources.size() > 0) {
         String path = ((Source)sources.get(0)).getCodeSourceURL().getPath();
         if (path.endsWith("/")) {
            int ndx = path.lastIndexOf("/");
            path = path.substring(0, ndx);
         }

         return path;
      } else {
         return super.getClassPathArchiveName();
      }
   }

   public String getClassPathArchiveName() {
      return this.archivePath;
   }
}
