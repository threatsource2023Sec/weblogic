package com.oracle.injection.integration;

import com.oracle.injection.BeanManager;
import com.oracle.injection.InjectionArchive;
import com.oracle.injection.InjectionContainer;
import com.oracle.injection.InjectionDeployment;
import com.oracle.injection.InjectionException;
import com.oracle.injection.spi.WebModuleIntegrationService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.decorator.Decorator;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.NormalScope;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.inject.Stereotype;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.interceptor.Interceptor;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.TransactionScoped;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.naming.ModuleRegistry;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.application.utils.annotation.ClassInfoFinder.Target;
import weblogic.j2ee.descriptor.wl.CdiDescriptorBean;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.CdiContainerMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.transaction.TransactionHelper;
import weblogic.utils.XXEUtils;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class CDIUtils {
   private static Logger m_logger = Logger.getLogger(CDIUtils.class.getName());
   public static final String CDI_BEAN_MANAGER_BINDING_NAME = "BeanManager";
   public static final String CDI_BEAN_DISCOVERY_MODE_KEY = "com.oracle.injection.integration.BeanDiscoveryMode";
   public static final String DISABLE_IMPLICIT_CDI_PROPERTY = "disable-implicit-bean-discovery";
   private static final List cdiScopeAnnotations = new ArrayList();
   private static final List cdiScopeAnnotationClasses = new ArrayList();
   protected static final String[] cdiEnablingAnnotationsArray;
   static final List cdiEnablingAnnotationClasses;
   static final List cdiEnablingAnnotations;
   private static final AuthenticatedSubject kernelIdentity;

   public static boolean isImplicitBeanDiscoveryEnabled(ApplicationContextInternal applicationContextInternal) {
      if (applicationContextInternal != null && applicationContextInternal.getCdiPolicy().equals("Disabled")) {
         return false;
      } else {
         String propVal = System.getProperty("disable-implicit-bean-discovery");
         if (propVal != null && Boolean.valueOf(propVal)) {
            return false;
         } else if (applicationContextInternal == null) {
            SecurityServiceManager.checkKernelPermission();
            RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelIdentity);
            if (runtimeAccess != null) {
               DomainMBean domainMBean = runtimeAccess.getDomain();
               if (domainMBean != null) {
                  return domainMBean.getCdiContainer().isImplicitBeanDiscoveryEnabled();
               }
            }

            return true;
         } else {
            return computeImplicitBeanDiscoveryEnabledForApp(applicationContextInternal.getEffectiveDomain().getCdiContainer(), applicationContextInternal.getCdiDescriptorBean());
         }
      }
   }

   private static boolean computeImplicitBeanDiscoveryEnabledForApp(CdiContainerMBean cdiContainerMBean, CdiDescriptorBean cdiDescriptorBean) {
      if (cdiDescriptorBean != null && cdiDescriptorBean.isImplicitBeanDiscoveryEnabledSet()) {
         return cdiDescriptorBean.isImplicitBeanDiscoveryEnabled();
      } else if (cdiContainerMBean.isImplicitBeanDiscoveryEnabledSet()) {
         return cdiContainerMBean.isImplicitBeanDiscoveryEnabled();
      } else {
         return cdiDescriptorBean != null ? cdiDescriptorBean.isImplicitBeanDiscoveryEnabled() : cdiContainerMBean.isImplicitBeanDiscoveryEnabled();
      }
   }

   public static void bindBeanManager(Context componentContext, InjectionArchive injectionArchive, InjectionDeployment injectionDeployment) throws NamingException, DeploymentException {
      if (componentContext != null && injectionDeployment != null) {
         BeanManager beanManager = injectionDeployment.getBeanManager(injectionArchive.getArchiveName());
         if (beanManager == null) {
            throw new DeploymentException("BeanManager is null for archive name = " + injectionArchive.getArchiveName());
         }

         Object obj = beanManager.getInternalBeanManager();
         componentContext.bind("BeanManager", obj);
         if (injectionArchive instanceof ModuleInjectionArchive) {
            ModuleExtensionContext extCtx = ((ModuleInjectionArchive)injectionArchive).getModuleExtensionContext();
            if (extCtx instanceof WebModuleIntegrationService && ((WebModuleIntegrationService)extCtx).isWebAppDestroyed()) {
               ((WebModuleIntegrationService)extCtx).setBeanManager(obj);
            }
         }
      }

   }

   public static BeanDiscoveryMode getBeanDiscoveryMode(URL beansXmlUrl) {
      BeanDiscoveryMode result = BeanDiscoveryMode.ANNOTATED;
      if (beansXmlUrl != null) {
         try {
            result = getBeanDiscoveryMode(beansXmlUrl.openStream());
         } catch (FileNotFoundException var3) {
         } catch (IOException var4) {
            var4.printStackTrace();
            result = BeanDiscoveryMode.ALL;
         }
      }

      return result;
   }

   public static List getBeanClassNamesFromArchive(String archivePath) {
      List archiveClassNames = null;

      try {
         File archiveFile = new File(archivePath);
         if (!archiveFile.isDirectory()) {
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(archiveFile));
            archiveClassNames = BeanLoaderUtils.getBeanClassNamesFromJar(zipInputStream);
         } else {
            archiveClassNames = getBeanClassNamesFromDirectory(archiveFile);
         }
      } catch (IOException var4) {
         m_logger.finer("Error getting class names from " + archivePath + " : " + var4.getMessage());
      }

      return archiveClassNames;
   }

   static List getBeanClassNamesFromDirectory(File libDir) throws IOException {
      if (libDir != null && libDir.exists()) {
         VirtualJarFile virtualJarFile = null;

         ArrayList var11;
         try {
            virtualJarFile = VirtualJarFactory.createVirtualJar(libDir);
            ZipEntry beansEntry = virtualJarFile.getEntry("META-INF/beans.xml");
            List beanClassNames = null;
            if (beansEntry != null) {
               BeanDiscoveryMode bdMode = getBeanDiscoveryMode(virtualJarFile, beansEntry);
               if (BeanDiscoveryMode.ALL.equals(bdMode)) {
                  Iterator entryIt = virtualJarFile.entries();

                  while(entryIt.hasNext()) {
                     ZipEntry entry = (ZipEntry)entryIt.next();
                     if (BeanLoaderUtils.isClassName(entry.getName())) {
                        String name = BeanLoaderUtils.getLoadableClassName(entry.getName(), 0);
                        if (beanClassNames == null) {
                           beanClassNames = new ArrayList();
                        }

                        beanClassNames.add(name);
                     }
                  }
               }
            }

            var11 = beanClassNames;
         } finally {
            if (virtualJarFile != null) {
               virtualJarFile.close();
            }

         }

         return var11;
      } else {
         return null;
      }
   }

   public static Source getWebInfClassesBeansXml(ModuleExtensionContext moduleExtensionContext) {
      List sources = moduleExtensionContext.getSources("WEB-INF/beans.xml");
      if (sources.size() == 0) {
         sources = moduleExtensionContext.getSources("WEB-INF/classes/META-INF/beans.xml");
      }

      return sources.size() > 0 ? (Source)sources.get(0) : null;
   }

   public static boolean isWebModuleCDIEnabled(ModuleContext moduleContext, ModuleExtensionContext moduleExtensionContext, ApplicationContextInternal appCtx) throws InjectionException {
      if (appCtx != null && appCtx.getCdiPolicy().equals("Disabled")) {
         return false;
      } else {
         GenericClassLoader classLoader = moduleContext.getClassLoader();
         ClassInfoFinder finder = moduleExtensionContext.getClassInfoFinder();
         boolean result = false;
         Source beansXmlSource = getWebInfClassesBeansXml(moduleExtensionContext);
         if (beansXmlSource != null) {
            BeanDiscoveryMode bdm = getBeanDiscoveryMode(beansXmlSource);
            if (BeanDiscoveryMode.ALL.equals(bdm)) {
               result = true;
            } else if (BeanDiscoveryMode.ANNOTATED.equals(bdm)) {
               ArrayList webInfClassesGenJarUris = new ArrayList();
               addSourcesToPaths(moduleExtensionContext, "WEB-INF/classes/", webInfClassesGenJarUris);
               addSourcesToPaths(moduleExtensionContext, "WEB-INF/lib/_wl_cls_gen.jar", webInfClassesGenJarUris);
               result = hasCDIEnablingAnnotations((Collection)webInfClassesGenJarUris, finder, classLoader);
            }
         } else if (isImplicitBeanDiscoveryEnabled(appCtx) && moduleExtensionContext.getSources("WEB-INF/classes/META-INF/services/javax.enterprise.inject.spi.Extension").size() == 0) {
            Collection webInfClassesUris = getWebInfClassesUris(moduleExtensionContext);
            if (webInfClassesUris.size() > 0) {
               result = hasCDIEnablingAnnotations((Collection)webInfClassesUris, finder, classLoader);
            }
         }

         if (!result) {
            VirtualJarFile virtualJar = moduleContext.getVirtualJarFile();
            result = areAnyEmbeddedJarsCdiEnabled(moduleExtensionContext, virtualJar, classLoader, "WEB-INF/lib/", appCtx);
         }

         return result;
      }
   }

   public static URL encodeURL(URL url) {
      try {
         return new URL(url.toString().replaceAll(" ", "%20"));
      } catch (MalformedURLException var2) {
         return url;
      }
   }

   private static boolean areAnyEmbeddedJarsCdiEnabled(ModuleExtensionContext moduleExtensionContext, VirtualJarFile moduleVirtualjarFile, GenericClassLoader classLoader, String dirToCheckForJars, ApplicationContextInternal appCtx) throws InjectionException {
      boolean result = false;
      ClassInfoFinder finder = moduleExtensionContext.getClassInfoFinder();

      try {
         Iterator entries = moduleVirtualjarFile.getEntries(dirToCheckForJars);

         while(!result && entries.hasNext()) {
            ZipEntry oneEntry = (ZipEntry)entries.next();
            if (oneEntry.getName().endsWith(".jar")) {
               List sources = moduleExtensionContext.getSources(oneEntry.getName());
               Iterator var10 = sources.iterator();

               while(var10.hasNext()) {
                  Source oneSource = (Source)var10.next();
                  VirtualJarFile virtualJarFile = null;

                  try {
                     URI jarFileCodeSourceUri = encodeURL(oneSource.getURL()).toURI();
                     File jarFile = new File(oneSource.getURL().getPath());
                     virtualJarFile = VirtualJarFactory.createVirtualJar(jarFile);
                     result = isVirtualJarCdiEnabled(virtualJarFile, finder, classLoader, jarFileCodeSourceUri, appCtx);
                     if (result) {
                        break;
                     }
                  } catch (URISyntaxException var19) {
                     throw new InjectionException("Exception creating URI for: " + oneEntry.getName() + ".  Exception: " + var19.getMessage(), var19);
                  } finally {
                     if (virtualJarFile != null) {
                        virtualJarFile.close();
                     }

                  }
               }
            }
         }

         return result;
      } catch (IOException var21) {
         throw new InjectionException("Exception getting entries for " + dirToCheckForJars + ".  Exception: " + var21.getMessage(), var21);
      }
   }

   public static void addSourcesToPaths(ModuleExtensionContext moduleExtensionContext, String sourceName, Collection paths) throws InjectionException {
      List sources = moduleExtensionContext.getSources(sourceName);
      Iterator var4 = sources.iterator();

      while(var4.hasNext()) {
         Source oneSource = (Source)var4.next();

         try {
            URI uri = encodeURL(oneSource.getURL()).toURI();
            if (!paths.contains(uri)) {
               paths.add(uri);
            }
         } catch (URISyntaxException var7) {
            throw new InjectionException("Exception creating URI for: " + oneSource.getURL() + ".  Exception: " + var7.getMessage(), var7);
         }
      }

   }

   private static void addSources(ArrayList existingSources, ModuleExtensionContext moduleExtensionContext, String sourceLocation) {
      List sources = moduleExtensionContext.getSources(sourceLocation);
      Iterator var4 = sources.iterator();

      while(var4.hasNext()) {
         Source oneSource = (Source)var4.next();
         if (!existingSources.contains(oneSource)) {
            existingSources.add(oneSource);
         }
      }

   }

   private static Collection getWebInfClassesUris(ModuleExtensionContext moduleExtensionContext) throws InjectionException {
      ArrayList collectedSources = new ArrayList();
      addSources(collectedSources, moduleExtensionContext, "WEB-INF/lib/_wl_cls_gen.jar");
      addSources(collectedSources, moduleExtensionContext, "WEB-INF/classes/");
      ArrayList uris = new ArrayList();
      Iterator var3 = collectedSources.iterator();

      while(var3.hasNext()) {
         Source oneSource = (Source)var3.next();

         try {
            URI sourceUri = encodeURL(oneSource.getURL()).toURI();
            uris.add(sourceUri);
         } catch (URISyntaxException var6) {
            throw new InjectionException("Exception creating URI for " + oneSource.getURL() + ".  Exception: " + var6.getMessage(), var6);
         }
      }

      return uris;
   }

   public static boolean isVirtualJarCdiEnabled(VirtualJarFile virtualJar, ClassInfoFinder finder, ClassLoader classLoader, ApplicationContextInternal appCtx) {
      return isVirtualJarCdiEnabled(virtualJar, finder, classLoader, (URI)null, appCtx);
   }

   private static boolean isVirtualJarCdiEnabled(VirtualJarFile virtualJar, ClassInfoFinder finder, ClassLoader classLoader, URI codeSourceUri, ApplicationContextInternal appCtx) {
      if (appCtx != null && appCtx.getCdiPolicy().equals("Disabled")) {
         return false;
      } else {
         BeanDiscoveryMode beanDiscoveryMode = getBeanDiscoveryMode(virtualJar, "META-INF/beans.xml", appCtx);
         if (beanDiscoveryMode.equals(BeanDiscoveryMode.NONE)) {
            return false;
         } else if (beanDiscoveryMode.equals(BeanDiscoveryMode.ALL)) {
            return true;
         } else if (isArchiveExtensionWithNoBeansXml(virtualJar, "META-INF/beans.xml")) {
            return false;
         } else {
            ArrayList uris = new ArrayList();
            if (codeSourceUri != null) {
               uris.add(codeSourceUri);
            }

            return hasCDIEnablingAnnotations((Collection)uris, finder, classLoader);
         }
      }
   }

   protected static boolean isRarModuleCDIEnabled(ModuleContext moduleContext, ModuleExtensionContext moduleExtensionContext, ApplicationContextInternal appCtx) throws InjectionException {
      if (appCtx != null && appCtx.getCdiPolicy().equals("Disabled")) {
         return false;
      } else {
         GenericClassLoader classLoader = moduleContext.getClassLoader();
         ClassInfoFinder finder = moduleExtensionContext.getClassInfoFinder();
         VirtualJarFile moduleContextVirtualJarFile = moduleContext.getVirtualJarFile();
         Source rootSource = (Source)moduleExtensionContext.getSources("/").get(0);

         boolean result;
         try {
            URI rootCodeSourceUri = encodeURL(rootSource.getCodeSourceURL()).toURI();
            result = isVirtualJarCdiEnabled(moduleContextVirtualJarFile, finder, classLoader, rootCodeSourceUri, appCtx);
         } catch (URISyntaxException var9) {
            throw new InjectionException("Exception creating URI for " + rootSource.getURL() + ".  Exception: " + var9.getMessage(), var9);
         }

         if (!result) {
            result = areAnyEmbeddedJarsCdiEnabled(moduleExtensionContext, moduleContextVirtualJarFile, classLoader, "/", appCtx);
         }

         return result;
      }
   }

   public static boolean isModuleCdiEnabled(ModuleContext moduleContext, ModuleExtensionContext moduleExtensionContext, ApplicationContextInternal appCtx) throws InjectionException {
      if (appCtx != null && appCtx.getCdiPolicy().equals("Disabled")) {
         return false;
      } else {
         ClassInfoFinder finder = moduleExtensionContext.getClassInfoFinder();
         ModuleRegistry moduleRegistry = moduleContext.getRegistry();
         if (moduleRegistry != null && moduleRegistry.get(InjectionContainer.class.getName()) != null) {
            return true;
         } else {
            String moduleType = moduleContext.getType();
            boolean enabled = false;
            if (moduleType.equals(ModuleType.WAR.toString())) {
               enabled = isWebModuleCDIEnabled(moduleContext, moduleExtensionContext, appCtx);
            } else if (moduleType.equals(ModuleType.EJB.toString())) {
               VirtualJarFile virtualJar = moduleContext.getVirtualJarFile();
               GenericClassLoader classLoader = moduleContext.getClassLoader();
               enabled = isVirtualJarCdiEnabled(virtualJar, finder, classLoader, appCtx);
               if (enabled) {
                  BeanDiscoveryMode mode = BeanDiscoveryMode.ANNOTATED;
                  if (moduleRegistry != null) {
                     ZipEntry beansXml = virtualJar.getEntry("META-INF/beans.xml");
                     if (beansXml != null) {
                        mode = getBeanDiscoveryMode(virtualJar, beansXml);
                     }

                     moduleRegistry.put("com.oracle.injection.integration.BeanDiscoveryMode", mode);
                  }
               }
            } else if (moduleType.equals(ModuleType.RAR.toString())) {
               enabled = isRarModuleCDIEnabled(moduleContext, moduleExtensionContext, appCtx);
            }

            if (enabled && moduleRegistry != null && appCtx != null) {
               InjectionContainer injectionContainer = getInjectionContainer(appCtx);
               moduleRegistry.put(InjectionContainer.class.getName(), injectionContainer);
            }

            return enabled;
         }
      }
   }

   public static BeanDiscoveryMode getBeanDiscoveryMode(ModuleContext moduleContext, ModuleExtensionContext moduleExtensionContext, ApplicationContextInternal appCtx) throws InjectionException {
      if (moduleContext != null) {
         String moduleType = moduleContext.getType();
         if (!moduleType.equals(ModuleType.WAR.toString())) {
            VirtualJarFile virtualJar = moduleContext.getVirtualJarFile();
            return getBeanDiscoveryMode(virtualJar, "META-INF/beans.xml", appCtx);
         }

         if (moduleExtensionContext != null) {
            boolean hasEJBinLib = false;

            try {
               Iterator entries = moduleContext.getVirtualJarFile().getEntries("WEB-INF/lib/");

               while(entries.hasNext()) {
                  ZipEntry oneEntry = (ZipEntry)entries.next();
                  String oneEntryName = oneEntry.getName();
                  String appName = moduleContext.getApplicationName();
                  if (oneEntry.getName().endsWith(".jar") && oneEntryName.contains(appName)) {
                     hasEJBinLib = true;
                  }
               }
            } catch (IOException var9) {
               throw new InjectionException("Exception getting entries for WEB-INF/lib/.  Exception: " + var9.getMessage(), var9);
            }

            Source beansXmlSource = getWebInfClassesBeansXml(moduleExtensionContext);
            if (beansXmlSource != null && !hasEJBinLib) {
               return getBeanDiscoveryMode(beansXmlSource);
            }
         }
      }

      return null;
   }

   public static boolean isArchiveExtensionWithNoBeansXml(VirtualJarFile virtualJar, String beansXmlLocation) {
      ZipEntry beansXml = virtualJar.getEntry(beansXmlLocation);
      return beansXml == null && virtualJar.getEntry("META-INF/services/javax.enterprise.inject.spi.Extension") != null;
   }

   public static BeanDiscoveryMode getBeanDiscoveryMode(VirtualJarFile virtualJarFile, String beansXmlLocation, ApplicationContextInternal appCtx) {
      ZipEntry beansXml = virtualJarFile.getEntry(beansXmlLocation);
      if (beansXml != null) {
         return getBeanDiscoveryMode(virtualJarFile, beansXml);
      } else {
         return isImplicitBeanDiscoveryEnabled(appCtx) ? BeanDiscoveryMode.ANNOTATED : BeanDiscoveryMode.NONE;
      }
   }

   public static InjectionContainer getInjectionContainer(ApplicationContextInternal appCtx) throws InjectionException {
      synchronized(appCtx) {
         InjectionContainer injectionContainer = (InjectionContainer)appCtx.getUserObject(InjectionContainer.class.getName());
         if (injectionContainer == null) {
            injectionContainer = createEmptyInjectionContainer(appCtx);
         }

         return injectionContainer;
      }
   }

   static InjectionContainer createEmptyInjectionContainer(ApplicationContextInternal appCtx) throws InjectionException {
      DefaultInjectionContainerFactory injectionContainerFactory = new DefaultInjectionContainerFactory();
      InjectionContainer injectionContainer = injectionContainerFactory.createInjectionContainer();
      ModuleContainerIntegrationService containerIntegrationService = new ModuleContainerIntegrationService(TransactionHelper.getTransactionHelper());
      appCtx.putUserObject(WeblogicContainerIntegrationService.class.getName(), containerIntegrationService);
      injectionContainer.setIntegrationService(containerIntegrationService);
      appCtx.putUserObject(InjectionContainer.class.getName(), injectionContainer);
      return injectionContainer;
   }

   public static boolean hasCDIEnablingAnnotations(String path, ClassInfoFinder finder, ClassLoader cl) {
      boolean result = false;

      try {
         Set paths = new HashSet();
         paths.add(new URI(path.replace(File.separatorChar, '/')));
         result = hasCDIEnablingAnnotations((Collection)paths, finder, cl);
      } catch (URISyntaxException var5) {
         m_logger.warning("Exception creating URI for path " + path + ".  Exception: " + var5.getMessage());
      }

      return result;
   }

   public static boolean hasCDIEnablingAnnotations(Collection paths, ClassInfoFinder finder, ClassLoader cl) {
      boolean result = false;
      if (finder != null) {
         result = finder.hasAnnotatedClasses(cdiEnablingAnnotationsArray, paths != null && !paths.isEmpty() ? new ClassAnnotationPathFilter(paths) : null, true, cl);
      }

      return result;
   }

   public static BeanDiscoveryMode getBeanDiscoveryMode(Source beansXMLSource) {
      BeanDiscoveryMode mode = BeanDiscoveryMode.ANNOTATED;

      try {
         mode = getBeanDiscoveryMode(beansXMLSource.getInputStream());
      } catch (IOException var3) {
      }

      return mode;
   }

   public static BeanDiscoveryMode getBeanDiscoveryMode(ModuleRegistry modReg) {
      BeanDiscoveryMode mode = null;
      if (modReg != null) {
         mode = (BeanDiscoveryMode)modReg.get("com.oracle.injection.integration.BeanDiscoveryMode");
      }

      return mode;
   }

   private static BeanDiscoveryMode getBeanDiscoveryMode(VirtualJarFile archive, ZipEntry beansXMLEntry) {
      BeanDiscoveryMode mode = BeanDiscoveryMode.ANNOTATED;

      try {
         mode = getBeanDiscoveryMode(archive.getInputStream(beansXMLEntry));
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      return mode;
   }

   public static BeanDiscoveryMode getBeanDiscoveryMode(InputStream beansXml) {
      BeanDiscoveryMode mode = BeanDiscoveryMode.ALL;
      if (beansXml != null) {
         try {
            BeanDiscoveryModeSAXHandler modeHandler = new BeanDiscoveryModeSAXHandler();
            SAXParserFactory saxParserFactory = XXEUtils.createSAXParserFactoryInstance();
            saxParserFactory.setNamespaceAware(true);
            SAXParser parser = saxParserFactory.newSAXParser();
            parser.parse(new InputSource(beansXml), modeHandler);
            String modeStr = modeHandler.getMode();
            if (modeStr != null && !modeStr.isEmpty()) {
               mode = BeanDiscoveryMode.valueOf(modeStr.toUpperCase());
            }
         } catch (SAXParseException var6) {
            m_logger.finest("Empty or invalid beans.xml");
         } catch (IllegalArgumentException var7) {
            m_logger.warning("Invalid bean-discovery-mode value");
         } catch (Exception var8) {
            var8.printStackTrace();
         }
      }

      return mode;
   }

   public static boolean archiveAlreadyDefined(String candidate, Collection injectionArchives) {
      boolean inUse = false;
      if (candidate != null && !candidate.isEmpty()) {
         Iterator var3 = injectionArchives.iterator();

         while(var3.hasNext()) {
            InjectionArchive archive = (InjectionArchive)var3.next();
            String archiveName = archive.getArchiveName();
            if (candidate.equals(archiveName)) {
               inUse = true;
               break;
            }
         }
      }

      return inUse;
   }

   public static Collection getCDIAnnotatedClassNames(URI path, ClassInfoFinder finder, ClassLoader cl) {
      Collection paths = new HashSet(1);
      paths.add(path);
      return getCDIAnnotatedClassNames((Collection)paths, finder, cl);
   }

   public static Collection getCDIAnnotatedClassNames(Collection paths, ClassInfoFinder finder, ClassLoader cl) {
      List annotatedClassNames = new ArrayList();
      Map annotationInfo = getCDIAnnotationInfo(paths, finder, cl);
      if (annotationInfo != null) {
         Iterator var5 = annotationInfo.keySet().iterator();

         while(var5.hasNext()) {
            URI codeSourceURI = (URI)var5.next();
            annotatedClassNames.addAll((Collection)annotationInfo.get(codeSourceURI));
         }
      }

      return annotatedClassNames;
   }

   public static Map getCDIAnnotationInfo(Collection paths, ClassInfoFinder finder, ClassLoader cl) {
      Map classAnnotationInfo = null;
      if (finder != null) {
         ClassAnnotationPathFilter filter = paths != null && !paths.isEmpty() ? new ClassAnnotationPathFilter(paths) : null;
         Map annotationInfo = finder.getAnnotatedClassesByTargetsAndSources(cdiEnablingAnnotationsArray, filter, true, cl);
         classAnnotationInfo = (Map)annotationInfo.get(Target.CLASS);
      }

      if (classAnnotationInfo == null) {
         classAnnotationInfo = Collections.emptyMap();
      }

      return classAnnotationInfo;
   }

   public static String getEjbRefName(AnnotatedMember annotatedElement) {
      EJB ejbAnnotation = (EJB)annotatedElement.getAnnotation(EJB.class);
      if (ejbAnnotation == null) {
         return null;
      } else {
         String retVal = ejbAnnotation.name();
         if (retVal.length() > 0) {
            return retVal;
         } else {
            String referenceClassName = getReferenceClassName(annotatedElement);
            return referenceClassName != null ? referenceClassName + "/" + annotatedElement.getJavaMember().getName() : null;
         }
      }
   }

   public static String getReferenceClassName(AnnotatedMember annotatedMember) {
      if (annotatedMember instanceof AnnotatedField) {
         return ((AnnotatedField)annotatedMember).getJavaMember().getType().getName();
      } else {
         if (annotatedMember instanceof AnnotatedMethod) {
            Method method = ((AnnotatedMethod)annotatedMember).getJavaMember();
            Class[] paramTypes = method.getParameterTypes();
            if (paramTypes.length == 1) {
               return paramTypes[0].getName();
            }
         }

         return null;
      }
   }

   public static void getManagedClassNamesAndEmbeddedJars(VirtualJarFile virtualJarFile, String relativeArchiveName, ModuleContext moduleContext, ModuleExtensionContext moduleExtensionContext, ApplicationContextInternal appCtx, Collection classNames, Collection embeddedJarNames) throws InjectionException {
      BeanDiscoveryMode beanDiscoveryMode = getBeanDiscoveryMode(virtualJarFile, "META-INF/beans.xml", appCtx);
      if (BeanDiscoveryMode.ANNOTATED.equals(beanDiscoveryMode)) {
         Collection paths = new HashSet();
         addSourcesToPaths(moduleExtensionContext, relativeArchiveName, paths);
         if (paths.size() > 0) {
            Collection annotatedClassNames = getCDIAnnotatedClassNames((Collection)paths, moduleExtensionContext.getClassInfoFinder(), moduleContext.getClassLoader());
            classNames.addAll(annotatedClassNames);
         }
      }

      Iterator iterator = virtualJarFile.entries();

      while(true) {
         while(iterator.hasNext()) {
            ZipEntry zipEntry = (ZipEntry)iterator.next();
            String entryName = zipEntry.getName();
            if (BeanLoaderUtils.isClassName(entryName) && beanDiscoveryMode.equals(BeanDiscoveryMode.ALL)) {
               classNames.add(BeanLoaderUtils.getLoadableClassName(entryName, 0));
            } else if (entryName.endsWith(".jar") && embeddedJarNames != null) {
               embeddedJarNames.add(entryName);
            }
         }

         return;
      }
   }

   public static ArrayList getArchivePaths(String classPath) {
      ArrayList paths = new ArrayList();
      if (classPath != null) {
         m_logger.log(Level.FINE, "CDIUtils Archive ClassPath: " + classPath);
         Collections.addAll(paths, classPath.split(File.pathSeparator));
         Collections.sort(paths);
         m_logger.log(Level.FINE, "CDIUtils Archive ClassPath after split: " + paths);
      }

      return paths;
   }

   static {
      cdiScopeAnnotationClasses.add(ApplicationScoped.class);
      cdiScopeAnnotationClasses.add(SessionScoped.class);
      cdiScopeAnnotationClasses.add(ConversationScoped.class);
      cdiScopeAnnotationClasses.add(RequestScoped.class);
      cdiScopeAnnotationClasses.add(TransactionScoped.class);
      cdiScopeAnnotationClasses.add(Interceptor.class);
      cdiScopeAnnotationClasses.add(Decorator.class);
      cdiScopeAnnotationClasses.add(Stereotype.class);
      cdiScopeAnnotationClasses.add(NormalScope.class);
      cdiScopeAnnotationClasses.add(Dependent.class);
      cdiScopeAnnotations.add(ApplicationScoped.class.getName());
      cdiScopeAnnotations.add(SessionScoped.class.getName());
      cdiScopeAnnotations.add(ConversationScoped.class.getName());
      cdiScopeAnnotations.add(RequestScoped.class.getName());
      cdiScopeAnnotations.add(TransactionScoped.class.getName());
      cdiScopeAnnotations.add(Interceptor.class.getName());
      cdiScopeAnnotations.add(Decorator.class.getName());
      cdiScopeAnnotations.add(Stereotype.class.getName());
      cdiScopeAnnotations.add(NormalScope.class.getName());
      cdiScopeAnnotations.add(Dependent.class.getName());
      cdiEnablingAnnotations = new ArrayList();
      cdiEnablingAnnotationClasses = new ArrayList();
      cdiEnablingAnnotations.addAll(cdiScopeAnnotations);
      cdiEnablingAnnotationClasses.addAll(cdiScopeAnnotationClasses);
      cdiEnablingAnnotationClasses.add(Stateful.class);
      cdiEnablingAnnotationClasses.add(Stateless.class);
      cdiEnablingAnnotationClasses.add(Singleton.class);
      cdiEnablingAnnotations.add(Stateful.class.getName());
      cdiEnablingAnnotations.add(Stateless.class.getName());
      cdiEnablingAnnotations.add(Singleton.class.getName());
      cdiEnablingAnnotationsArray = (String[])cdiEnablingAnnotations.toArray(new String[cdiEnablingAnnotations.size()]);
      kernelIdentity = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   static class BeanDiscoveryModeSAXHandler extends DefaultHandler {
      private String mode;

      public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
         if ("beans".equals(qName)) {
            for(int i = 0; i < attributes.getLength(); ++i) {
               if ("bean-discovery-mode".equals(attributes.getLocalName(i))) {
                  this.mode = attributes.getValue(i);
                  break;
               }
            }
         }

      }

      String getMode() {
         return this.mode;
      }
   }
}
