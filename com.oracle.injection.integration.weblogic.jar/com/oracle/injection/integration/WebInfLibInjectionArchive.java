package com.oracle.injection.integration;

import com.oracle.injection.InjectionArchiveType;
import com.oracle.injection.InjectionException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;

public class WebInfLibInjectionArchive extends WebModuleInjectionArchive {
   private URL beansXmlUrl;
   private final String libName;
   private Collection beanClassNames;

   public WebInfLibInjectionArchive(ModuleContext moduleContext, ModuleExtensionContext moduleExtensionContext, Collection beanClassNames, Module extensibleModule, String libName, URL beansXmlUrl) {
      super(moduleContext, moduleExtensionContext, extensibleModule);
      this.libName = libName;
      this.beansXmlUrl = beansXmlUrl;
      this.beanClassNames = beanClassNames;
   }

   public WebInfLibInjectionArchive(ModuleContext moduleContext, ModuleExtensionContext moduleExtensionContext, Module extensibleModule, String libName, String archivePath, ApplicationContextInternal appCtx) throws InjectionException {
      super(moduleContext, moduleExtensionContext, extensibleModule);
      this.libName = libName;
      this.beanClassNames = new ArrayList();
      InjectionArchiveInfo injectionArchiveInfo = new InjectionArchiveInfo(archivePath, appCtx, moduleExtensionContext.getClassInfoFinder(), moduleContext.getClassLoader());
      this.beansXmlUrl = injectionArchiveInfo.getBeansXmlUrl();
      this.beanClassNames = injectionArchiveInfo.getBeanClassNames();
   }

   protected void loadBeanClassNames() {
   }

   public Collection getBeanClassNames() {
      return this.beanClassNames;
   }

   public URL getResource(String resourceName) {
      return resourceName.equals("META-INF/beans.xml") ? this.beansXmlUrl : null;
   }

   public String getArchiveName() {
      return this.m_moduleContext.getApplicationName() + super.getArchiveName() + this.libName;
   }

   public String getClassPathArchiveName() {
      return this.libName;
   }

   public InjectionArchiveType getArchiveType() {
      return InjectionArchiveType.JAR;
   }
}
