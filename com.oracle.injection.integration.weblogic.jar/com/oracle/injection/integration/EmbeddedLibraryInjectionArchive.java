package com.oracle.injection.integration;

import com.oracle.injection.InjectionArchive;
import com.oracle.injection.InjectionArchiveType;
import com.oracle.injection.InjectionException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import javax.naming.Context;
import weblogic.application.ApplicationContextInternal;
import weblogic.j2ee.descriptor.wl.PojoEnvironmentBean;

class EmbeddedLibraryInjectionArchive implements InjectionArchive {
   private ClassLoader classLoader;
   private Collection beanClassNames;
   private URL m_urlToBeansXml;
   private String m_archiveName;
   private ApplicationContextInternal appCtx;
   private String classPathArchiveName;

   EmbeddedLibraryInjectionArchive(ApplicationContextInternal appCtx) {
      this.appCtx = appCtx;
   }

   public EmbeddedLibraryInjectionArchive(ApplicationContextInternal appCtx, String archiveName, String classPathArchiveName) throws InjectionException {
      this.appCtx = appCtx;
      this.classLoader = appCtx.getAppClassLoader();
      this.m_archiveName = archiveName;
      this.classPathArchiveName = classPathArchiveName;
      InjectionArchiveInfo injectionArchiveInfo = new InjectionArchiveInfo(classPathArchiveName, appCtx, appCtx.getClassInfoFinder(), this.classLoader);
      this.beanClassNames = injectionArchiveInfo.getBeanClassNames();
      this.m_urlToBeansXml = injectionArchiveInfo.getBeansXmlUrl();
   }

   public ClassLoader getClassLoader() {
      return this.classLoader;
   }

   public Collection getEjbDescriptors() {
      return Collections.emptyList();
   }

   public Collection getBeanClassNames() {
      return this.beanClassNames;
   }

   public URL getResource(String resourceName) {
      return resourceName.equals("META-INF/beans.xml") ? this.m_urlToBeansXml : null;
   }

   public Object getCustomContext() {
      return null;
   }

   public InjectionArchiveType getArchiveType() {
      return InjectionArchiveType.JAR;
   }

   public String getArchiveName() {
      return this.m_archiveName;
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
      return this.appCtx.getPojoEnvironmentBean();
   }

   public Context getRootContext(String componentName) {
      return this.appCtx.getRootContext();
   }
}
