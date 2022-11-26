package weblogic.coherence.container.server;

import com.tangosol.application.ContainerAdapter;
import com.tangosol.application.ContainerContext;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.CacheFactoryBuilder;
import com.tangosol.run.xml.XmlElement;
import com.tangosol.run.xml.XmlHelper;
import com.tangosol.run.xml.XmlValue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationFileManager;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.SplitDirectoryInfo;
import weblogic.application.UpdateListener;
import weblogic.application.internal.library.JarLibraryDefinition;
import weblogic.application.io.ClasspathInfo;
import weblogic.application.io.Ear;
import weblogic.application.io.ExplodedJar;
import weblogic.application.io.JarCopyFilter;
import weblogic.application.io.ClasspathInfo.ArchiveType;
import weblogic.application.library.IllegalSpecVersionTypeException;
import weblogic.application.library.Library;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryReference;
import weblogic.application.library.LibraryReferenceFactory;
import weblogic.application.utils.LibraryUtils;
import weblogic.application.utils.PathUtils;
import weblogic.cacheprovider.coherence.CoherenceClusterManager;
import weblogic.cacheprovider.coherence.DebugLogger;
import weblogic.coherence.app.descriptor.CoherenceAppDescriptorLoader;
import weblogic.coherence.app.descriptor.wl.WeblogicCohAppBean;
import weblogic.coherence.service.internal.CoherenceMessagesTextFormatter;
import weblogic.coherence.service.internal.WLSCacheFactoryBuilder;
import weblogic.coherence.service.internal.coherenceLogger;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.DescriptorBean;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.j2ee.descriptor.wl.LibraryRefBean;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.kernel.ThreadLocalInitialValue;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.CoherencePartitionCacheConfigMBean;
import weblogic.management.configuration.CoherencePartitionCachePropertyMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.classloaders.Source;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class CoherenceModule implements Module {
   private static final String MANIFEST_ENTRY = "META-INF/MANIFEST.MF";
   private static final String PARTITION_APP_SEPARATOR = "/";
   private GenericClassLoader appClassLoader;
   private VirtualJarFile virtualJar;
   private String uri;
   private String name;
   private ClassFinder classFinder;
   private ExplodedJar explodedJar;
   private boolean isDirectory;
   private boolean isEar;
   private ApplicationContextInternal context;
   private ContainerAdapter adapter;
   private static ConcurrentHashMap mapCtxContainers = new ConcurrentHashMap();
   private static ConcurrentHashMap mapGlobalContexts = new ConcurrentHashMap();
   private boolean isDebugEnabled = false;
   private XmlElement appDescriptor;
   private LibraryManager libraryManager;
   private static final String COHERENCE_MODULE_LIB = "/lib/";
   private static final CoherenceMessagesTextFormatter txtFormatter = CoherenceMessagesTextFormatter.getInstance();
   private static final AuditableThreadLocal MIC_CONTEXT_STACK = AuditableThreadLocalFactory.createThreadLocal(new ThreadLocalInitialValue(true) {
      protected Object initialValue() {
         return new ArrayDeque();
      }

      protected Object childValue(Object parentValue) {
         return ((ArrayDeque)parentValue).clone();
      }
   });
   public static final ClasspathInfo COH_CLASSPATH_INFO = new ClasspathInfo() {
      private final String[] COH_MODULE_CLASSES = new String[]{""};
      private final String[] COH_MODULE_LIB = new String[]{"lib"};

      public String[] getClasspathURIs() {
         return this.COH_MODULE_CLASSES;
      }

      public String[] getJarURIs() {
         return this.COH_MODULE_LIB;
      }

      public ClasspathInfo.ArchiveType getArchiveType() {
         return ArchiveType.OTHER;
      }
   };
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   CoherenceModule(String uri, String name, boolean isDirectory) {
      this.uri = uri;
      this.name = name;
      this.isDirectory = isDirectory;
      this.isDebugEnabled = DebugLogger.isDebugEnabled();
   }

   public String getId() {
      return this.uri;
   }

   public String getType() {
      return WebLogicModuleType.MODULETYPE_GAR;
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return new ComponentRuntimeMBean[0];
   }

   public DescriptorBean[] getDescriptors() {
      return new DescriptorBean[0];
   }

   public GenericClassLoader init(ApplicationContext context, GenericClassLoader loader, UpdateListener.Registration registration) throws ModuleException {
      this.appClassLoader = loader;
      this.context = (ApplicationContextInternal)context;
      this.isEar = this.context.isEar();
      Module[] modules = this.context.getApplicationModules();
      if (this.isEar) {
         Module[] var5 = modules;
         int var6 = modules.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Module module = var5[var7];
            if (module.getType() != null && module.getType().equals(WebLogicModuleType.MODULETYPE_GAR) && !module.getId().equals(this.uri)) {
               throw new ModuleException("only one " + WebLogicModuleType.MODULETYPE_GAR + " permitted within an EAR");
            }
         }
      }

      try {
         this.buildClassFinder();
      } catch (Exception var9) {
         if (var9 instanceof ModuleException) {
            throw (ModuleException)var9;
         }

         throw new ModuleException(var9);
      }

      loader.addClassFinder(this.classFinder);
      return loader;
   }

   public void initUsingLoader(ApplicationContext context, GenericClassLoader loader, UpdateListener.Registration registration) throws ModuleException {
      this.init(context, loader, registration);
   }

   public void prepare() throws ModuleException {
      URL urlDescriptor = this.virtualJar.getResource("META-INF/coherence-application.xml");
      if (urlDescriptor == null) {
         throw new ModuleException("GAR Module in application " + this.context.getApplicationId() + " does not have a valid descriptor: " + "META-INF/coherence-application.xml");
      } else {
         ContainerContext ctxContainer = null;

         try {
            this.appDescriptor = XmlHelper.loadXml(urlDescriptor);
            ComponentInvocationContextManager ctxMgr = ComponentInvocationContextManager.getInstance(kernelId);
            ComponentInvocationContext ctxCurrent = ctxMgr.getCurrentComponentInvocationContext();
            String partitionName = ctxCurrent.getPartitionName();
            this.validateAppName(ctxCurrent);
            if (ctxCurrent.isGlobalRuntime()) {
               this.ensureGlobalContext(this.name, ctxMgr);
               ctxContainer = this.getGlobalContext(this.name);
            } else {
               ctxContainer = new WLSContainerContext(ctxMgr, partitionName);
               this.addPartitionContextToMap(this.name, partitionName, (ContainerContext)ctxContainer);
               this.ensureGlobalContext(this.name, ctxMgr);
            }

            this.debugMessage("CoherenceModule: name=" + this.name + ", appDescriptor=" + this.appDescriptor);
            this.debugMessage("Domain Partition ContainerContext is: " + ctxContainer);
            this.debugMessage("Global ContainerContext is: " + this.getGlobalContext(this.name));
            this.adapter = new ContainerAdapter(this.appClassLoader, "", this.name, this.appDescriptor);
            this.adapter.setContainerContext((ContainerContext)ctxContainer);
            ServerMBean server = CoherenceClusterManager.getServerMBean();
            ClusterMBean cluster = server.getCluster();
            CoherenceClusterSystemResourceMBean cohSR = cluster != null && cluster.getCoherenceClusterSystemResource() != null ? cluster.getCoherenceClusterSystemResource() : server.getCoherenceClusterSystemResource();
            if (cohSR == null) {
               coherenceLogger.logMessageGARWithoutClusterDefinition(this.name);
            }

         } catch (Exception var9) {
            throw new ModuleException(var9);
         }
      }
   }

   public void activate() throws ModuleException {
      CacheFactoryBuilder cacheFactoryBuilder = CacheFactory.getCacheFactoryBuilder();
      ArrayList appClassLoaders = new ArrayList();

      try {
         this.preProcess(this.appDescriptor);
         if (this.isEar) {
            Module[] modules = this.context.getApplicationModules();
            String applicationId = this.context.getApplicationId();
            Module[] var5 = modules;
            int var6 = modules.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Module module = var5[var7];
               GenericClassLoader moduleClassLoader = ApplicationAccess.getApplicationAccess().findModuleLoader(applicationId, module.getId());
               appClassLoaders.add(moduleClassLoader);
            }
         } else {
            appClassLoaders.add(this.appClassLoader);
         }

         if (cacheFactoryBuilder instanceof WLSCacheFactoryBuilder) {
            ((WLSCacheFactoryBuilder)cacheFactoryBuilder).addToContainerClassLoaders(appClassLoaders);
         }

         this.adapter.activate();
         if (this.isEar && !appClassLoaders.isEmpty()) {
            this.adapter.associateWithClassLoader((ClassLoader[])appClassLoaders.toArray(new ClassLoader[appClassLoaders.size()]));
         }

      } catch (Exception var10) {
         if (cacheFactoryBuilder instanceof WLSCacheFactoryBuilder) {
            ((WLSCacheFactoryBuilder)cacheFactoryBuilder).removeFromContainerClassLoaders(appClassLoaders);
         }

         throw new ModuleException(var10.getMessage(), var10);
      }
   }

   public void deactivate() throws ModuleException {
      try {
         this.adapter.deactivate();
      } catch (Exception var2) {
         throw new ModuleException(var2.getMessage(), var2);
      }
   }

   public void unprepare() throws ModuleException {
      ComponentInvocationContextManager ctxMgr = ComponentInvocationContextManager.getInstance(kernelId);
      ComponentInvocationContext ctxCurrent = ctxMgr.getCurrentComponentInvocationContext();
      String partitionName = ctxCurrent.getPartitionName();
      if (!ctxCurrent.isGlobalRuntime()) {
         this.removePartitionContextFromMap(this.name, partitionName);
      }

   }

   public void start() throws ModuleException {
   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
      this.appClassLoader = null;
      if (this.explodedJar != null) {
         this.explodedJar.remove();
      }

      if (this.virtualJar != null) {
         try {
            this.virtualJar.close();
         } catch (IOException var3) {
            coherenceLogger.logWarnGARUndeployFile(var3);
         }
      }

      if (this.libraryManager != null) {
         this.libraryManager.removeReferences();
         this.libraryManager = null;
      }

   }

   public void remove() throws ModuleException {
   }

   public void adminToProduction() {
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier adminModeCompletionBarrier) throws ModuleException {
   }

   public void forceProductionToAdmin() throws ModuleException {
   }

   private void buildClassFinder() throws IOException, ModuleException {
      ApplicationFileManager appFileManager = this.context.getApplicationFileManager();
      String appId = this.context.getApplicationId();
      File extractDir = this.getExtractDir(appId, this.uri);
      MultiClassFinder mcs = null;
      if (this.isEar) {
         Ear ear = this.context.getEar();
         File[] files = ear.getModuleRoots(this.uri);
         if (files.length == 0) {
            throw new FileNotFoundException("Unable to find coherence archive with uri " + this.uri + " in ear at " + this.context.getStagingPath());
         }

         if (files[0].isDirectory()) {
            this.virtualJar = VirtualJarFactory.createVirtualJar(files);
            this.explodedJar = new ExplodedJar(this.uri, extractDir, this.virtualJar.getRootFiles(), COH_CLASSPATH_INFO, JarCopyFilter.NOCOPY_FILTER);
         } else {
            this.virtualJar = VirtualJarFactory.createVirtualJar(new JarFile(files[0]));
            this.explodedJar = new ExplodedJar(this.uri, extractDir, files[0], COH_CLASSPATH_INFO);
         }
      } else {
         this.virtualJar = appFileManager.getVirtualJarFile();
         if (this.isDirectory) {
            this.explodedJar = new ExplodedJar(this.uri, extractDir, this.virtualJar.getRootFiles(), COH_CLASSPATH_INFO, JarCopyFilter.NOCOPY_FILTER);
         } else {
            this.explodedJar = new ExplodedJar(this.uri, extractDir, this.virtualJar.getRootFiles()[0], COH_CLASSPATH_INFO);
         }
      }

      mcs = (MultiClassFinder)this.explodedJar.getClassFinder();
      if (mcs == null) {
         throw new ModuleException("Unable to get class finder from the coherence archive");
      } else {
         this.addManifestFinder(this.explodedJar.getDirs(), mcs);
         this.addLibrariesFinder(mcs);
         this.addSplitDirClassFinder(mcs);
         this.classFinder = mcs;
      }
   }

   private void addLibrariesFinder(MultiClassFinder mcs) throws ModuleException {
      WeblogicCohAppBean weblogicCohAppBean = null;

      try {
         Source source = this.explodedJar.getClassFinder().getSource("META-INF/weblogic-coh-app.xml");
         if (source != null) {
            weblogicCohAppBean = CoherenceAppDescriptorLoader.getLoader().createWeblogicCohAppBean(source.getURL());
         }
      } catch (Exception var13) {
         throw new ModuleException(var13);
      }

      if (weblogicCohAppBean != null) {
         LibraryRefBean[] libraryRefBeans = weblogicCohAppBean.getLibraryRefs();
         LibraryReference[] libRefs = null;

         try {
            libRefs = LibraryReferenceFactory.getAppLibReference(libraryRefBeans);
         } catch (IllegalSpecVersionTypeException var12) {
            throw new ModuleException(var12);
         }

         if (libRefs != null && libRefs.length > 0) {
            this.libraryManager = new LibraryManager(LibraryUtils.initAppReferencer(this.context), this.context.getPartitionName(), libRefs);
            Library[] var5 = this.libraryManager.getReferencedLibraries();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Library library = var5[var7];
               if (library instanceof JarLibraryDefinition) {
                  JarClassFinder jarClassFinder = null;

                  try {
                     jarClassFinder = new JarClassFinder(library.getLocation());
                  } catch (IOException var11) {
                     throw new ModuleException("Unable to create class finder", var11);
                  }

                  mcs.addFinder(jarClassFinder);
               }
            }

            this.libraryManager.addReferences();
         }
      }

   }

   private void addSplitDirClassFinder(MultiClassFinder mcs) {
      SplitDirectoryInfo splitInfo = this.context.getSplitDirectoryInfo();
      if (splitInfo != null) {
         Map uriLinks = splitInfo.getUriLinks();
         if (uriLinks != null) {
            String libClassPath = "";
            Iterator var5 = uriLinks.keySet().iterator();

            while(true) {
               String appFile;
               do {
                  do {
                     if (!var5.hasNext()) {
                        if (libClassPath != null && !libClassPath.equals("")) {
                           mcs.addFinder(new ClasspathClassFinder2(libClassPath));
                        }

                        return;
                     }

                     Object key = var5.next();
                     appFile = (String)key;
                  } while(!appFile.startsWith(this.uri + "/lib/"));
               } while(!(uriLinks.get(appFile) instanceof ArrayList));

               Iterator var8 = ((ArrayList)uriLinks.get(appFile)).iterator();

               while(var8.hasNext()) {
                  Object file = var8.next();
                  if (file instanceof File) {
                     libClassPath = libClassPath + ((File)file).getAbsolutePath() + File.pathSeparator;
                  }
               }
            }
         }
      }

   }

   private void addManifestFinder(File[] exDirs, MultiClassFinder mcs) {
      File[] var3 = exDirs;
      int var4 = exDirs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         File exDir = var3[var5];
         ClassFinder cf = null;

         try {
            cf = this.getManifestFinder(exDir, new HashSet());
         } catch (IOException var9) {
         }

         if (cf != null) {
            mcs.addFinder(cf);
         }
      }

   }

   private ClassFinder getManifestFinder(File dir, Set exclude) throws IOException {
      File manFile = new File(dir, "META-INF/MANIFEST.MF");
      if (!manFile.exists()) {
         return null;
      } else {
         InputStream manFileStream = new FileInputStream(manFile);
         String mftCP = null;

         try {
            Manifest manifest = new Manifest(manFileStream);
            mftCP = getManifestClassPath(manifest, dir.getPath());
         } finally {
            manFileStream.close();
         }

         return mftCP != null ? new ClasspathClassFinder2(mftCP, exclude) : null;
      }
   }

   private static String getManifestClassPath(Manifest mft, String fName) {
      if (mft != null) {
         Attributes mainAttributes = mft.getMainAttributes();
         if (mainAttributes != null) {
            StringBuffer ret = null;
            String cpAttr = (String)mainAttributes.get(Name.CLASS_PATH);
            if (cpAttr != null) {
               ret = convertManifestClassPath(fName, cpAttr);
            }

            if (ret != null) {
               return ret.toString();
            }
         }
      }

      return null;
   }

   private static StringBuffer convertManifestClassPath(String fileName, String mftCP) {
      StringBuffer ret = new StringBuffer();
      String relPath = "";
      if ((new File(fileName)).isDirectory()) {
         relPath = fileName;
         if (!fileName.endsWith(File.separator)) {
            relPath = fileName + File.separator;
         }
      } else {
         int lastSep = fileName.lastIndexOf(File.separatorChar);
         if (lastSep > -1) {
            relPath = fileName.substring(0, lastSep + 1);
         }
      }

      String[] cp = StringUtils.splitCompletely(mftCP, " " + File.pathSeparatorChar);

      for(int i = 0; i < cp.length; ++i) {
         ret.append(relPath);
         cp[i] = cp[i].replace('/', File.separatorChar);
         ret.append(cp[i]);
         if (i < cp.length - 1) {
            ret.append(File.pathSeparatorChar);
         }
      }

      return ret;
   }

   private File getExtractDir(String appName, String modName) throws ModuleException {
      File extractDir = PathUtils.getAppTempDir((String)null, appName, modName);
      boolean isWriteable = extractDir.exists() ? extractDir.canWrite() : extractDir.mkdirs();
      if (!isWriteable) {
         throw new ModuleException("Unable to generate temporary directory for module: " + extractDir.getAbsolutePath());
      } else {
         return extractDir;
      }
   }

   private void preProcess(XmlElement xml) throws NamingException {
      Context ctxJndi = CoherenceCacheConfigModule.getContext();
      if (ctxJndi != null) {
         String sPropertyAttribute = "override-property";
         XmlValue attr = xml.getAttribute(sPropertyAttribute);
         if (attr != null) {
            String sJndiName = attr.getString();

            try {
               String sValue = ctxJndi.lookup(sJndiName).toString();
               xml.setAttribute(sPropertyAttribute, (XmlValue)null);
               if (sValue != null) {
                  xml.setString(sValue);
               }
            } catch (Exception var7) {
               coherenceLogger.logWarnJNDIOverrideNotFound(sJndiName, xml.getName());
            }
         }

         Iterator iter = xml.getElementList().iterator();

         while(iter.hasNext()) {
            this.preProcess((XmlElement)iter.next());
         }

      }
   }

   private String createGlobalPartitionedAppName(String domainPartition, String applicationName) {
      return domainPartition + "/" + applicationName;
   }

   private String createPartitionedAppName(String domainPartition, String applicationName) {
      return domainPartition + "/" + applicationName;
   }

   private void ensureGlobalContext(String applicationName, ComponentInvocationContextManager ctxMgr) {
      String globalPartition = "DOMAIN";
      String key = this.createGlobalPartitionedAppName(globalPartition, applicationName);
      mapGlobalContexts.putIfAbsent(key, new WLSContainerContext(ctxMgr, globalPartition));
   }

   private void addPartitionContextToMap(String applicationName, String partitionName, ContainerContext context) {
      String key = this.createPartitionedAppName(partitionName, applicationName);
      mapCtxContainers.putIfAbsent(key, context);
   }

   private void removePartitionContextFromMap(String applicationName, String partitionName) {
      String key = this.createPartitionedAppName(partitionName, applicationName);
      mapCtxContainers.remove(key);
   }

   private ContainerContext getGlobalContext(String applicationName) {
      String globalPartition = "DOMAIN";
      return (ContainerContext)mapGlobalContexts.get(this.createGlobalPartitionedAppName(globalPartition, applicationName));
   }

   private ContainerContext getPartitionContext(String applicationName, String partition) {
      String key = this.createPartitionedAppName(partition, applicationName);
      return (ContainerContext)mapCtxContainers.get(key);
   }

   private void debugMessage(String message) {
      if (this.isDebugEnabled) {
         DebugLogger.debug(message);
      }

   }

   public void validateAppName(ComponentInvocationContext ctxCurrent) throws ModuleException {
      if (ctxCurrent.isGlobalRuntime()) {
         int beginIndex = this.name.indexOf(47);
         if (beginIndex > 0) {
            String beforeForwardSlash = this.name.substring(0, beginIndex);
            if (beforeForwardSlash != null) {
               PartitionMBean partitionBean = CoherenceClusterManager.getDomainMBean().lookupPartition(beforeForwardSlash);
               if (partitionBean != null) {
                  throw new ModuleException(txtFormatter.getCheckDeploymentNameNotContainPartitionPrefixMsg(this.name, partitionBean.getName()));
               }
            }
         }
      }

   }

   private class WLSContainerContext implements ContainerContext {
      private final String domainPartition;
      private final ComponentInvocationContextManager ctxMgr;
      private final ComponentInvocationContext ctxPartition;
      private PartitionMBean partitionMBean;
      private Set setSharedCaches;
      private Map mapPartitionCacheProperties;

      public WLSContainerContext(ComponentInvocationContextManager ctxMgr, String partition) {
         if (partition == null) {
            throw new IllegalArgumentException("Partition cannot be null");
         } else {
            this.domainPartition = partition;
            this.ctxMgr = ctxMgr;
            ComponentInvocationContext currentContext = ctxMgr.getCurrentComponentInvocationContext();
            this.ctxPartition = ctxMgr.createComponentInvocationContext(this.domainPartition, currentContext.getApplicationName(), currentContext.getApplicationVersion(), currentContext.getModuleName(), currentContext.getComponentName());
         }
      }

      public String getDomainPartition() {
         return this.domainPartition;
      }

      public boolean isGlobalDomainPartition() {
         return CoherenceModule.this.getGlobalContext(CoherenceModule.this.name).getDomainPartition().equals(this.getDomainPartition());
      }

      public ContainerContext getGlobalContext() {
         return CoherenceModule.this.getGlobalContext(CoherenceModule.this.name);
      }

      public ContainerContext getCurrentThreadContext() {
         ComponentInvocationContext ctxCurrent = this.ctxMgr.getCurrentComponentInvocationContext();
         return ctxCurrent == null ? null : (ctxCurrent.isGlobalRuntime() ? CoherenceModule.this.getGlobalContext(CoherenceModule.this.name) : CoherenceModule.this.getPartitionContext(CoherenceModule.this.name, ctxCurrent.getPartitionName()));
      }

      public void setCurrentThreadContext() {
         ManagedInvocationContext mic = this.ctxMgr.setCurrentComponentInvocationContext(this.ctxPartition);
         Deque mics = (Deque)CoherenceModule.MIC_CONTEXT_STACK.get();
         mics.push(mic);
      }

      public void resetCurrentThreadContext() {
         Deque mics = (Deque)CoherenceModule.MIC_CONTEXT_STACK.get();
         ManagedInvocationContext mic = (ManagedInvocationContext)mics.pop();
         mic.close();
      }

      public Object runInDomainPartitionContext(Callable action) {
         try {
            return ComponentInvocationContextManager.runAs(CoherenceModule.kernelId, this.ctxPartition, action);
         } catch (Exception var3) {
            throw new RuntimeException(var3);
         }
      }

      public boolean isCacheShared(String sCache) {
         if (this.isGlobalDomainPartition()) {
            return true;
         } else {
            this.initCacheProperties();
            return this.setSharedCaches.contains(sCache);
         }
      }

      public Object getCacheAttribute(String sCache, String sAttribute) {
         if (!this.isGlobalDomainPartition()) {
            this.initCacheProperties();
            Map mapCacheProperties = (Map)this.mapPartitionCacheProperties.get(sCache);
            if (mapCacheProperties != null) {
               return mapCacheProperties.get(sAttribute);
            }
         }

         return null;
      }

      public Set getSharedCaches() {
         this.initCacheProperties();
         return this.setSharedCaches;
      }

      public String toString() {
         StringBuilder sb = new StringBuilder("WLSContainerContext(domainPartition=");
         sb.append(this.domainPartition).append(", isGlobalDomainPartition=").append(this.isGlobalDomainPartition()).append(", partitionMBean =").append(this.partitionMBean == null ? null : this.partitionMBean.toString()).append(", sharedCaches=").append(this.setSharedCaches == null ? "null" : this.setSharedCaches).append(", cacheProperties=(");
         if (this.mapPartitionCacheProperties != null) {
            Iterator var2 = this.mapPartitionCacheProperties.entrySet().iterator();

            while(var2.hasNext()) {
               Map.Entry entry = (Map.Entry)var2.next();
               sb.append("\ncacheName=").append((String)entry.getKey()).append(", properties=").append(entry.getValue());
            }
         }

         sb.append("\n))");
         return sb.toString();
      }

      private synchronized void initCacheProperties() {
         if (!this.isGlobalDomainPartition() && this.partitionMBean == null) {
            this.partitionMBean = CoherenceClusterManager.getDomainMBean().lookupPartition(this.domainPartition);
            if (this.partitionMBean == null) {
               throw new IllegalArgumentException("Unable to get partitionMBean for domain " + this.domainPartition);
            } else {
               this.setSharedCaches = new HashSet();
               this.mapPartitionCacheProperties = new HashMap();
               if (this.partitionMBean != null) {
                  CoherencePartitionCacheConfigMBean[] var1 = this.partitionMBean.getCoherencePartitionCacheConfigs();
                  int var2 = var1.length;

                  for(int var3 = 0; var3 < var2; ++var3) {
                     CoherencePartitionCacheConfigMBean cacheConfig = var1[var3];
                     String cacheName = cacheConfig.getCacheName();
                     if (CoherenceModule.this.name.equals(cacheConfig.getApplicationName())) {
                        if (cacheConfig.isShared()) {
                           this.setSharedCaches.add(cacheName);
                        }

                        Map mapProperties = new HashMap();
                        CoherencePartitionCachePropertyMBean[] var7 = cacheConfig.getCoherencePartitionCacheProperties();
                        int var8 = var7.length;

                        for(int var9 = 0; var9 < var8; ++var9) {
                           CoherencePartitionCachePropertyMBean prop = var7[var9];
                           mapProperties.put(prop.getName(), prop.getValue());
                        }

                        if (!mapProperties.isEmpty()) {
                           this.mapPartitionCacheProperties.put(cacheName, mapProperties);
                        }
                     }
                  }
               }

            }
         }
      }
   }
}
