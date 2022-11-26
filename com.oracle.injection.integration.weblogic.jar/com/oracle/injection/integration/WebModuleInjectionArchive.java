package com.oracle.injection.integration;

import com.oracle.injection.InjectionException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.SplitDirectoryInfo;
import weblogic.j2ee.descriptor.FilterBean;
import weblogic.j2ee.descriptor.ListenerBean;
import weblogic.j2ee.descriptor.ServletBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.servlet.internal.WebAppHelper;
import weblogic.servlet.internal.WebAppModule;
import weblogic.servlet.internal.WebBaseModuleExtensionContext;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

class WebModuleInjectionArchive extends ModuleInjectionArchive {
   private static Logger logger = Logger.getLogger(WebModuleInjectionArchive.class.getName());
   private List beanClassNames;
   private Collection embeddedInjectionArchives;
   private Module extensibleModule;
   private ApplicationContextInternal applicationContextInternal;

   public WebModuleInjectionArchive(ModuleContext moduleContext, ModuleExtensionContext moduleExtensionContext, Module extensibleModule, ApplicationContextInternal appContext) throws IOException, InjectionException {
      super(moduleContext, moduleExtensionContext);
      this.extensibleModule = extensibleModule;
      this.applicationContextInternal = appContext;
      this.embeddedInjectionArchives = new ArrayList();
      this.processWebModule();
   }

   protected WebModuleInjectionArchive(ModuleContext moduleContext, ModuleExtensionContext moduleExtensionContext, Module extensibleModule) {
      super(moduleContext, moduleExtensionContext);
      this.embeddedInjectionArchives = new ArrayList();
      this.extensibleModule = extensibleModule;
      this.beanClassNames = new ArrayList();
   }

   private boolean isClassPathEntryForWebInfLib(ArrayList appPaths, String oneModulePath, ArrayList splitDirectories) {
      String adjustedModulePath = oneModulePath.replace(File.separator, "/");
      File file = new File(oneModulePath);
      return file.exists() && !appPaths.contains(oneModulePath) && !adjustedModulePath.contains("cache/EJBCompilerCache") && !adjustedModulePath.endsWith("/war/WEB-INF/classes") && !adjustedModulePath.endsWith("/war/WEB-INF/lib/_wl_cls_gen.jar") && !adjustedModulePath.endsWith(this.m_moduleContext.getURI() + "/" + "WEB-INF/classes") && !adjustedModulePath.endsWith(this.m_moduleContext.getURI() + ".war/" + "WEB-INF/classes") && !adjustedModulePath.endsWith(this.m_moduleContext.getURI() + "/" + "WEB-INF/lib/_wl_cls_gen.jar") && !adjustedModulePath.endsWith(this.m_moduleContext.getURI() + ".war/" + "WEB-INF/lib/_wl_cls_gen.jar") && !adjustedModulePath.endsWith(".ear") && adjustedModulePath.length() > 0 && !this.isPathInSplitDirs(splitDirectories, adjustedModulePath);
   }

   private void processWebModule() throws IOException, InjectionException {
      ArrayList splitDirectories = this.getSplitDirWebInfClassesDirs(this.m_moduleContext, this.applicationContextInternal);
      ArrayList appPaths = CDIUtils.getArchivePaths(this.applicationContextInternal.getAppClassLoaderClassPath());
      ArrayList modulePaths = CDIUtils.getArchivePaths(this.m_moduleContext.getClassLoaderClassPath());
      int count = 0;
      Iterator var5 = modulePaths.iterator();

      while(var5.hasNext()) {
         String onePath = (String)var5.next();
         if (this.isClassPathEntryForWebInfLib(appPaths, onePath, splitDirectories)) {
            this.createDependentInjectionArchive(onePath, count);
            ++count;
         }
      }

      VirtualJarFile virtualJarFile = this.m_moduleContext.getVirtualJarFile();
      this.beanClassNames = this.extractWebInfClassNames(splitDirectories, false, virtualJarFile);
   }

   private boolean isPathInSplitDirs(ArrayList splitDirs, String adjustedPath) {
      if (!adjustedPath.endsWith("/")) {
         adjustedPath = adjustedPath + "/";
      }

      Iterator var3 = splitDirs.iterator();

      String adjustedDir;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         String oneDir = (String)var3.next();
         adjustedDir = oneDir.replace(File.separator, "/");
         if (!adjustedDir.endsWith("/")) {
            adjustedDir = adjustedDir + "/";
         }
      } while(!adjustedDir.equals(adjustedPath));

      return true;
   }

   private void createDependentInjectionArchive(String jarPath, int archiveNum) throws InjectionException {
      try {
         WebInfLibInjectionArchive webInfLibInjectionArchive;
         String libName;
         if (jarPath.replace(File.separator, "/").endsWith("WEB-INF/classes")) {
            libName = jarPath.substring(0, jarPath.length() - "WEB-INF/classes".length());
            File jarFile = new File(libName);
            VirtualJarFile virtualJarFile = VirtualJarFactory.createVirtualJar(jarFile);
            List classNames = this.extractWebInfClassNames(new ArrayList(), true, virtualJarFile);
            webInfLibInjectionArchive = new WebInfLibInjectionArchive(this.m_moduleContext, this.m_moduleExtensionContext, classNames, this.extensibleModule, jarFile.getName().replace(File.separator, "/") + archiveNum, this.getBeansXmlFromWar(virtualJarFile));
         } else {
            String adjustedJarPath = jarPath.replace(File.separator, "/");
            int index = adjustedJarPath.indexOf("WEB-INF/lib");
            if (index != -1) {
               libName = adjustedJarPath.substring(index);
            } else {
               libName = adjustedJarPath;
            }

            libName = libName + archiveNum;
            webInfLibInjectionArchive = new WebInfLibInjectionArchive(this.m_moduleContext, this.m_moduleExtensionContext, this.extensibleModule, libName, jarPath, this.applicationContextInternal);
         }

         if (webInfLibInjectionArchive.getBeanClassNames().size() > 0) {
            this.embeddedInjectionArchives.add(webInfLibInjectionArchive);
         }

      } catch (IOException var8) {
         throw new InjectionException("Exception creating injection archive for " + jarPath + ".  Exception: " + var8.getMessage(), var8);
      }
   }

   private List extractWebInfClassNames(ArrayList splitDirectoryPaths, boolean sharedLibrary, VirtualJarFile virtualJarFile) throws InjectionException, IOException {
      List listOfBeanClassNames = new ArrayList();
      URL beansXml = this.getBeansXmlFromWar(virtualJarFile);
      if (beansXml == null && virtualJarFile.getEntry("WEB-INF/classes/META-INF/services/javax.enterprise.inject.spi.Extension") != null) {
         return listOfBeanClassNames;
      } else {
         BeanDiscoveryMode beanDiscoveryMode = beansXml != null ? CDIUtils.getBeanDiscoveryMode(beansXml) : BeanDiscoveryMode.ANNOTATED;
         if (BeanDiscoveryMode.ANNOTATED.equals(beanDiscoveryMode)) {
            Collection paths = new HashSet();
            if (!sharedLibrary) {
               CDIUtils.addSourcesToPaths(this.m_moduleExtensionContext, "WEB-INF/classes/", paths);
               CDIUtils.addSourcesToPaths(this.m_moduleExtensionContext, "WEB-INF/lib/_wl_cls_gen.jar", paths);
            } else {
               try {
                  URL url = virtualJarFile.getResource("WEB-INF/classes/");
                  if (url != null) {
                     paths.add(url.toURI());
                  }
               } catch (URISyntaxException var14) {
                  throw new InjectionException("Exception creating URI for: " + virtualJarFile.getName() + "WEB-INF/classes/" + ".  Exception: " + var14.getMessage(), var14);
               }
            }

            if (!sharedLibrary) {
               Iterator var16 = splitDirectoryPaths.iterator();

               while(var16.hasNext()) {
                  String splitDir = (String)var16.next();

                  try {
                     paths.add(new URI("file:" + splitDir.replace(File.separatorChar, '/')));
                  } catch (URISyntaxException var13) {
                     throw new InjectionException("Exception creating URI for: " + splitDir + ".  Exception: " + var13.getMessage(), var13);
                  }
               }
            }

            if (paths.size() > 0) {
               Collection annotatedClassNames = CDIUtils.getCDIAnnotatedClassNames((Collection)paths, this.m_moduleExtensionContext.getClassInfoFinder(), this.m_moduleContext.getClassLoader());
               listOfBeanClassNames.addAll(annotatedClassNames);
            }
         } else if (BeanDiscoveryMode.ALL.equals(beanDiscoveryMode)) {
            if (!sharedLibrary) {
               this.addSplitDirWebInfClassNames(splitDirectoryPaths, listOfBeanClassNames);
            }

            Iterator iterator = virtualJarFile.entries();
            int webInfClassesLength = "WEB-INF/classes/".length();
            int slashWbInfClassesLength = "/WEB-INF/classes/".length();

            while(iterator.hasNext()) {
               ZipEntry zipEntry = (ZipEntry)iterator.next();
               String entryName = zipEntry.getName();
               if (BeanLoaderUtils.isClassName(entryName)) {
                  int webInfPrefixLen = 0;
                  if (entryName.startsWith("WEB-INF/classes/")) {
                     webInfPrefixLen = webInfClassesLength;
                  } else if (entryName.startsWith("/WEB-INF/classes/")) {
                     webInfPrefixLen = slashWbInfClassesLength;
                  }

                  if (webInfPrefixLen > 0) {
                     this.addClassNameToSet(listOfBeanClassNames, entryName, webInfPrefixLen);
                  }
               }
            }
         }

         return listOfBeanClassNames;
      }
   }

   private URL getBeansXmlFromWar(VirtualJarFile war) {
      URL url = war.getResource("WEB-INF/beans.xml");
      if (url == null) {
         url = war.getResource("/WEB-INF/beans.xml");
      }

      if (url == null) {
         url = war.getResource("WEB-INF/classes/META-INF/beans.xml");
      }

      if (url == null) {
         url = war.getResource("/WEB-INF/classes/META-INF/beans.xml");
      }

      return url;
   }

   private ArrayList getSplitDirWebInfClassesDirs(ModuleContext moduleContext, ApplicationContextInternal applicationContextInternal) {
      ArrayList splitDirectories = new ArrayList();
      SplitDirectoryInfo splitDirectoryInfo = applicationContextInternal.getSplitDirectoryInfo();
      if (splitDirectoryInfo != null) {
         String uri = moduleContext.getURI();
         Collections.addAll(splitDirectories, splitDirectoryInfo.getWebInfClasses(uri));
         Collections.addAll(splitDirectories, splitDirectoryInfo.getWebAppClasses(uri));
      }

      return splitDirectories;
   }

   private void addSplitDirWebInfClassNames(ArrayList splitDirectoryPaths, Collection beanClassNames) throws IOException {
      Iterator var3 = splitDirectoryPaths.iterator();

      while(var3.hasNext()) {
         String oneDir = (String)var3.next();

         try {
            VirtualJarFile virtualJarFile = VirtualJarFactory.createVirtualJar(new File(oneDir));
            this.getClassNamesFromVirtualJar(virtualJarFile, beanClassNames);
         } catch (IOException var6) {
            logger.log(Level.FINER, "Unbable to create virtual jar for directory: " + oneDir, var6);
            throw var6;
         }
      }

   }

   private void getClassNamesFromVirtualJar(VirtualJarFile virtualJarFile, Collection beanClassNames) {
      Iterator iterator = virtualJarFile.entries();

      while(iterator.hasNext()) {
         ZipEntry zipEntry = (ZipEntry)iterator.next();
         String entryName = zipEntry.getName();
         if (BeanLoaderUtils.isClassName(entryName)) {
            this.addClassNameToSet(beanClassNames, entryName, 0);
         }
      }

   }

   private void addClassNameToSet(Collection beanClassNames, String path, int prefix) {
      beanClassNames.add(BeanLoaderUtils.getLoadableClassName(path, prefix));
   }

   protected void loadBeanClassNames() {
   }

   public Collection getBeanClassNames() {
      return this.beanClassNames;
   }

   public Collection getEmbeddedArchives() {
      return this.embeddedInjectionArchives;
   }

   public Collection getComponentClassNamesForProcessInjectionTarget() {
      ArrayList classNames = new ArrayList();
      classNames.addAll(super.getComponentClassNamesForProcessInjectionTarget());
      if (this.m_moduleExtensionContext != null && this.m_moduleExtensionContext instanceof WebBaseModuleExtensionContext) {
         WebBaseModuleExtensionContext webBaseModuleExtensionContext = (WebBaseModuleExtensionContext)this.m_moduleExtensionContext;
         WebAppHelper webAppHelper = webBaseModuleExtensionContext.getWebAppHelper();
         this.addClassNames(classNames, webAppHelper.getTagHandlers(false));
         this.addClassNames(classNames, webAppHelper.getTagListeners(false));
      }

      if (this.extensibleModule instanceof WebAppModule) {
         WebAppModule webAppModule = (WebAppModule)this.extensibleModule;
         WebAppBean webAppBean = webAppModule.getWebAppBean();
         ServletBean[] servletBeans = webAppBean.getServlets();
         int var7;
         if (servletBeans != null) {
            ServletBean[] var5 = servletBeans;
            int var6 = servletBeans.length;

            for(var7 = 0; var7 < var6; ++var7) {
               ServletBean oneServletBean = var5[var7];
               this.addToClassNames(classNames, oneServletBean.getServletClass());
            }
         }

         ListenerBean[] listenerBeans = webAppBean.getListeners();
         int var17;
         if (listenerBeans != null) {
            ListenerBean[] var14 = listenerBeans;
            var7 = listenerBeans.length;

            for(var17 = 0; var17 < var7; ++var17) {
               ListenerBean oneListenerBean = var14[var17];
               this.addToClassNames(classNames, oneListenerBean.getListenerClass());
            }
         }

         FilterBean[] filterBeans = webAppBean.getFilters();
         if (filterBeans != null) {
            FilterBean[] var16 = filterBeans;
            var17 = filterBeans.length;

            for(int var18 = 0; var18 < var17; ++var18) {
               FilterBean oneFilterBean = var16[var18];
               this.addToClassNames(classNames, oneFilterBean.getFilterClass());
            }
         }
      }

      return classNames;
   }

   private void addToClassNames(ArrayList classNames, String className) {
      if (this.beanClassNames.contains(className)) {
         classNames.add(className);
      }

   }

   private void addClassNames(ArrayList classNames, Set classNamesToAdd) {
      if (classNamesToAdd != null) {
         Iterator var3 = classNamesToAdd.iterator();

         while(var3.hasNext()) {
            String oneClassName = (String)var3.next();
            this.addToClassNames(classNames, oneClassName);
         }
      }

   }
}
