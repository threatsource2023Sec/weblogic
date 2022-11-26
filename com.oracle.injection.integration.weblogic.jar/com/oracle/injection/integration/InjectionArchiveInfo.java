package com.oracle.injection.integration;

import com.oracle.injection.InjectionException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class InjectionArchiveInfo {
   private static Logger logger = Logger.getLogger(InjectionArchiveInfo.class.getName());
   private ArrayList beanClassNames = new ArrayList();
   private URL beansXmlUrl;

   public InjectionArchiveInfo(String archivePath, ApplicationContextInternal appCtx, ClassInfoFinder classInfoFinder, ClassLoader classLoader) throws InjectionException {
      this.processArchive(archivePath, appCtx, classInfoFinder, classLoader);
   }

   private void processArchive(String archivePath, ApplicationContextInternal appCtx, ClassInfoFinder classInfoFinder, ClassLoader classLoader) throws InjectionException {
      File archiveFile = new File(archivePath);

      VirtualJarFile virtualJarFile;
      try {
         virtualJarFile = VirtualJarFactory.createVirtualJar(archiveFile);
      } catch (IOException var18) {
         String msg = "IOException occurred while trying to create virtual jar for " + archiveFile.getAbsolutePath();
         logger.log(Level.WARNING, msg, var18);
         throw new InjectionException(msg, var18);
      }

      try {
         if (CDIUtils.isArchiveExtensionWithNoBeansXml(virtualJarFile, "META-INF/beans.xml")) {
            return;
         }

         BeanDiscoveryMode discoveryMode = BeanDiscoveryMode.ANNOTATED;
         this.beansXmlUrl = virtualJarFile.getResource("META-INF/beans.xml");
         if (this.beansXmlUrl != null) {
            discoveryMode = CDIUtils.getBeanDiscoveryMode(this.beansXmlUrl);
         } else if (!CDIUtils.isImplicitBeanDiscoveryEnabled(appCtx)) {
            discoveryMode = BeanDiscoveryMode.NONE;
         }

         if (!BeanDiscoveryMode.NONE.equals(discoveryMode)) {
            if (BeanDiscoveryMode.ALL.equals(discoveryMode)) {
               this.beanClassNames = this.getClassNames(virtualJarFile);
            } else {
               Collection annotatedClassNames = CDIUtils.getCDIAnnotatedClassNames(archiveFile.getAbsoluteFile().toURI(), classInfoFinder, classLoader);
               this.beanClassNames.addAll(annotatedClassNames);
            }

            return;
         }
      } finally {
         try {
            virtualJarFile.close();
         } catch (IOException var17) {
         }

      }

   }

   private ArrayList getClassNames(VirtualJarFile virtualJarFile) {
      ArrayList beanClassNames = new ArrayList();
      Iterator iterator = virtualJarFile.entries();

      while(iterator.hasNext()) {
         ZipEntry zipEntry = (ZipEntry)iterator.next();
         String entryName = zipEntry.getName();
         if (BeanLoaderUtils.isClassName(entryName)) {
            String className = BeanLoaderUtils.getLoadableClassName(entryName, 0);
            beanClassNames.add(className);
         }
      }

      return beanClassNames;
   }

   public ArrayList getBeanClassNames() {
      return this.beanClassNames;
   }

   public URL getBeansXmlUrl() {
      return this.beansXmlUrl;
   }
}
