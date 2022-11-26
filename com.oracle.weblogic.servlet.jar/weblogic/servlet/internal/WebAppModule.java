package weblogic.servlet.internal;

import com.bea.wls.redef.ClassRedefinerFactory;
import com.bea.wls.redef.RedefiningClassLoader;
import com.bea.wls.redef.runtime.ClassRedefinitionRuntimeImpl;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.jar.JarFile;
import javax.management.InvalidAttributeValueException;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.xml.stream.XMLStreamException;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.AppClassLoaderManager;
import weblogic.application.AppDeploymentExtension;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationFileManager;
import weblogic.application.ClassLoadingConfiguration;
import weblogic.application.ConcurrentModule;
import weblogic.application.DeployableObjectInfo;
import weblogic.application.MergedDescriptorModule;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleException;
import weblogic.application.ModuleExtension;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.ModuleLocationInfo;
import weblogic.application.ParentModule;
import weblogic.application.PermissionsDescriptorLoader;
import weblogic.application.PojoAnnotationSupportingModule;
import weblogic.application.SplitDirectoryInfo;
import weblogic.application.Type;
import weblogic.application.UpdateListener;
import weblogic.application.compiler.utils.ApplicationResourceFinder;
import weblogic.application.internal.ApplicationRuntimeMBeanImpl;
import weblogic.application.internal.BaseJ2EEModule;
import weblogic.application.io.DescriptorFinder;
import weblogic.application.io.ExplodedJar;
import weblogic.application.io.JarCopyFilter;
import weblogic.application.library.IllegalSpecVersionTypeException;
import weblogic.application.library.Library;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryReference;
import weblogic.application.library.LibraryReferenceFactory;
import weblogic.application.library.LibraryReferencer;
import weblogic.application.library.LibraryConstants.AutoReferrer;
import weblogic.application.naming.Environment;
import weblogic.application.naming.PersistenceUnitRegistryProvider;
import weblogic.application.ondemand.DeploymentProvider;
import weblogic.application.ondemand.DeploymentProviderManager;
import weblogic.application.utils.AppFileOverrideUtils;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.application.utils.CompositeWebAppFinder;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.PathUtils;
import weblogic.application.utils.TargetUtils;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.spi.EjbInWarIndicator;
import weblogic.ejb.spi.EjbInWarSupportingModule;
import weblogic.i18n.logging.Loggable;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.j2ee.descriptor.PermissionsBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.CdiDescriptorBean;
import weblogic.j2ee.descriptor.wl.ClassLoadingBean;
import weblogic.j2ee.descriptor.wl.ContainerDescriptorBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.LibraryRefBean;
import weblogic.j2ee.descriptor.wl.PojoEnvironmentBean;
import weblogic.j2ee.descriptor.wl.PreferApplicationPackagesBean;
import weblogic.j2ee.descriptor.wl.PreferApplicationResourcesBean;
import weblogic.j2ee.descriptor.wl.SecurityPermissionBean;
import weblogic.j2ee.descriptor.wl.SessionDescriptorBean;
import weblogic.j2ee.descriptor.wl.VirtualDirectoryMappingBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnvironmentBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.management.DeploymentException;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.configuration.WebAppComponentMBean;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.SecurityServiceException;
import weblogic.server.GlobalServiceLocator;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.dd.glassfish.GlassFishWebAppParser;
import weblogic.servlet.internal.fragment.CycleFoundInGraphException;
import weblogic.servlet.internal.fragment.WebFragmentManager;
import weblogic.servlet.internal.session.GracefulShutdownHelper;
import weblogic.servlet.internal.session.SessionConfigManager;
import weblogic.servlet.internal.tld.ExtensionTLD;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.servlet.utils.WarUtils;
import weblogic.servlet.utils.WebAppLibraryUtils;
import weblogic.utils.FileUtils;
import weblogic.utils.NestedException;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StringUtils;
import weblogic.utils.application.WarDetector;
import weblogic.utils.classloaders.ChangeAwareClassLoader;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClassLoaderUtils;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.classloaders.Source;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.http.HttpParsing;
import weblogic.utils.jars.JarFileUtils;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.work.ShutdownCallback;
import weblogic.work.WorkManagerCollection;
import weblogic.work.concurrent.runtime.ConcurrentManagedObjectCollection;
import weblogic.work.concurrent.runtime.RuntimeMBeanRegister;

public class WebAppModule extends BaseJ2EEModule implements PojoAnnotationSupportingModule, ModuleLocationInfo, ParentModule, UpdateListener, MergedDescriptorModule, DeployableObjectInfo, ConcurrentModule, ClassLoadingConfiguration, EjbInWarSupportingModule {
   public static final String META_INF = "META-INF";
   public static final String APP_XML = "META-INF/application.xml";
   public static final String INTERNAL_WEB_APP_CONTEXT_PATH = "/bea_wls_internal";
   public static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugWebAppModule");
   private final AppClassLoaderManager appClassLoaderManager = (AppClassLoaderManager)GlobalServiceLocator.getServiceLocator().getService(AppClassLoaderManager.class, new Annotation[0]);
   private static final WebServerRegistry registry = WebServerRegistry.getInstance();
   private static final String[] appDeploymentPreprocessorForReload = new String[]{"com.oracle.injection.integration.CDIAppDeploymentExtension"};
   private static final String[] appDeploymentPostprocessorForReload = new String[]{"com.oracle.injection.integration.CDIAppValidationExtension"};
   private final String moduleURI;
   private final Map allContexts = new HashMap();
   private String contextPath;
   private WebAppComponentMBean mbean;
   private WebAppContainerMBeanUpdateListener wacListener;
   protected WebAppBean webappBean;
   private WeblogicWebAppBean wlWebAppBean;
   private PermissionsBean permissionsBean;
   private AppDeploymentMBean dmb;
   private ApplicationFileManager appFileManager;
   private ApplicationContextInternal appCtx;
   private File altDDFile;
   private WebAppInternalParser webAppParser;
   private Collection moduleExtensions;
   private WebAppModuleExtensionContext moduleExtensionContext;
   private GenericClassLoader parentClassLoader;
   private GenericClassLoader webClassLoader;
   private GenericClassLoader temporaryClassLoader;
   private MultiClassFinder finder;
   private VirtualJarFile virtualJarFile;
   private boolean createdClassLoader;
   private boolean isSuspended = false;
   private boolean webappsDestroyed = false;
   private boolean updatingDescriptor = false;
   private File rootTempDir;
   private War war;
   private String warPath;
   private boolean isArchived;
   private String contextRootFromAppDD;
   private WebAppConfigManager configManager;
   private JSPManager jspManager;
   private SessionConfigManager sessionConfigManager;
   private WebFragmentManager webFragmentManager;
   private LibraryManager libraryManager;
   private boolean securityPermissionsRegistered;
   private boolean isJsfApplication;
   private boolean isJSR375Application;
   private boolean forcedContainerInitializersLookup = false;
   private PersistenceUnitRegistryProvider unitRegistryProvider;
   private static boolean mergeDescriptors = true;

   public WebAppModule(String uri, String contextRootFromAppDD) {
      this.moduleURI = uri;
      contextRootFromAppDD = EarUtils.fixAppContextRoot(contextRootFromAppDD);
      if (contextRootFromAppDD != null) {
         contextRootFromAppDD = WarDetector.instance.stem(contextRootFromAppDD);
      }

      this.contextRootFromAppDD = contextRootFromAppDD;
   }

   private GenericClassLoader init(ApplicationContext ac, GenericClassLoader parent, UpdateListener.Registration reg, boolean createSubLoader) throws ModuleException {
      this.appCtx = (ApplicationContextInternal)ac;
      this.appFileManager = this.appCtx.getApplicationFileManager();
      this.warPath = this.appFileManager.getSourcePath(this.moduleURI).getAbsolutePath();
      if (!this.appFileManager.getSourcePath(this.moduleURI).isDirectory() && WarDetector.instance.suffixed(this.warPath) || this.warPath.endsWith(".zip")) {
         this.isArchived = true;
      }

      this.initFinder();
      this.initLoader(parent, createSubLoader);
      this.initJNDIContext();
      this.initMBeans();
      this.configManager = new WebAppConfigManager(this, this.mbean);
      this.loadDescriptor();

      try {
         this.initWar();
         this.initContextPath();
         this.processWebAppLibraries();
         this.configManager.init();
         this.jspManager = new JSPManager(this.webappBean, this.wlWebAppBean, this.configManager.isRtexprvalueJspParamName(), this.configManager.isJSPCompilerBackwardsCompatible(), this.configManager.getRootTempDirPath(), this.configManager.getLogContext());
         this.cleanupLeftoverFiles();
         this.initSessionConfigManager();
         this.finder.addFinder(this.war.getClassFinder());
         this.reverseClassLoaderHirarchyIfNecessary();
         this.loadFastSwapClassloader();
         this.initFilterClassLoader();
         reg.addUpdateListener(this);
      } catch (ModuleException var6) {
         this.closeVirtualJarFile();
         throw var6;
      } catch (Exception var7) {
         this.closeVirtualJarFile();
         this.createModuleException(var7.getMessage(), var7);
      }

      return this.webClassLoader;
   }

   private void detectJsfApplication() {
      this.isJsfApplication = WarUtils.isJsfApplication(this.webappBean, this.wlWebAppBean, this.war);
   }

   private void detectJSR375Application() {
      this.isJSR375Application = WarUtils.isJSR375Application(this.webappBean, this.getTemporaryClassLoader(), this.war);
   }

   private void initSessionConfigManager() {
      SessionDescriptorBean sd = null;
      WeblogicApplicationBean wab = this.appCtx.getWLApplicationDD();
      if (wab != null && wab.isSet("SessionDescriptor")) {
         sd = wab.getSessionDescriptor();
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug(this.getLogContext() + ": shared session context enabled");
         }
      }

      if (sd == null && this.wlWebAppBean != null) {
         sd = (SessionDescriptorBean)DescriptorUtils.getFirstChildOrDefaultBean(this.wlWebAppBean, this.wlWebAppBean.getSessionDescriptors(), "SessionDescriptor");
      }

      this.sessionConfigManager = new SessionConfigManager(this, sd);
   }

   private void registerVirtualDirectoryMapping() {
      Map mappings = this.configManager.getVirtualDirectoryMappings();
      if (mappings != null && !mappings.isEmpty()) {
         Iterator var2 = mappings.keySet().iterator();

         while(var2.hasNext()) {
            String localPath = (String)var2.next();
            List patterns = (List)mappings.get(localPath);
            Iterator var5 = patterns.iterator();

            while(var5.hasNext()) {
               String pattern = (String)var5.next();
               this.war.addVirtualDirectory(localPath, pattern);
            }
         }

      }
   }

   private void registerExtensionTlds() {
      this.war.addExtensionTLD(new ExtensionTLD("org/apache/taglibs/standard/", this.webClassLoader));
      if (this.isJsfApplication() && WarUtils.shouldUseSystemJSF(this.webClassLoader)) {
         this.war.addExtensionTLD(new ExtensionTLD("com/sun/faces/", this.webClassLoader));
      }

   }

   protected String getModuleName() {
      ModuleContext mc = this.appCtx.getModuleContext(this.getId());
      return mc != null ? mc.getName() : this.getId();
   }

   private void initContextPath() throws ModuleException {
      this.contextPath = this.contextRootFromAppDD;
      String cpathFromWLDD = null;
      String moduleName = this.webappBean.getJavaEEModuleName();
      if (moduleName != null && !moduleName.isEmpty()) {
         this.contextPath = moduleName;
      }

      if (this.wlWebAppBean != null) {
         cpathFromWLDD = this.wlWebAppBean.getContextRoots().length > 0 ? this.wlWebAppBean.getContextRoots()[0] : null;
         if (this.contextPath != null && !this.isSynthesizedApplicationXml()) {
            if (this.wlWebAppBean.getContextRoots().length > 0) {
               HTTPLogger.logIgnoringWeblogicXMLContextRoot(this.getAppDisplayName(), this.getId(), this.contextPath, cpathFromWLDD);
            }
         } else if (cpathFromWLDD != null) {
            this.contextPath = cpathFromWLDD;
         }
      }

      if ((this.contextPath == null || this.isSynthesizedApplicationXml() && cpathFromWLDD == null) && this.configManager.getDefaultContextPath() != null) {
         this.contextPath = this.configManager.getDefaultContextPath();
      }

      if (ConsoleExtensionProcessor.getConsoleAppName().equals(this.getId())) {
         this.contextPath = HttpParsing.ensureStartingSlash(registry.getDomainMBean().getConsoleContextPath());
      }

      if (this.contextPath == null) {
         if (this.moduleURI.endsWith(".zip")) {
            this.contextPath = this.moduleURI.substring(0, this.moduleURI.indexOf(".zip"));
         } else {
            this.contextPath = WarDetector.instance.stem(this.moduleURI);
         }
      }

      this.contextPath = this.fixupContextPath(this.contextPath);
      this.contextPath = this.substituteAppName(this.contextPath);

      try {
         if (this.mbean != null) {
            this.mbean.setContextPath(this.contextPath);
         }
      } catch (DistributedManagementException var4) {
         HTTPLogger.logFailedToSetContextPath(this.getAppDisplayName(), this.getId(), this.contextPath, var4);
      } catch (InvalidAttributeValueException var5) {
         HTTPLogger.logFailedToSetContextPath(this.getAppDisplayName(), this.getId(), this.contextPath, var5);
      }

      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Context Path = " + this.contextPath);
      }

      if (this.appCtx.getApplicationDD() != null) {
         EarUtils.handleUnsetContextRoot(this.moduleURI, this.contextPath, this.appCtx.getApplicationDD(), this.wlWebAppBean, this.webappBean);
      }

   }

   private void initFilterClassLoader() throws ModuleException {
      try {
         if (WarUtils.configureFCL(this.getWlWebAppBean(), this.webClassLoader, !this.appCtx.isEar())) {
            HTTPLogger.logFilteringConfigurationIgnored(this.appCtx.getApplicationId(), this.getModuleURI());
         }

      } catch (ToolFailureException var2) {
         throw new ModuleException(var2);
      }
   }

   private void mergeLibraryDescriptors() throws ModuleException {
      if (this.getWlWebAppBean() != null || this.libraryManager.getAutoReferencedLibraries().length != 0) {
         try {
            this.mergeLibraryDescriptors("WEB-INF/web.xml");
            this.mergeLibraryDescriptors("WEB-INF/weblogic.xml");
         } catch (Exception var3) {
            Loggable l = HTTPLogger.logLibraryDescriptorMergeFailedLoggable(this.toString(), var3.getMessage(), var3);
            l.log();
            throw new ModuleException(l.getMessage(), var3);
         }
      }
   }

   private void mergeLibraryDescriptors(String uri) throws Exception {
      Source[] sources = this.war.getLibResourcesAsSources(uri);
      if (sources != null && sources.length > 0) {
         this.mergeLibraryDescriptors(sources, uri);
      }

   }

   protected void mergeLibraryDescriptors(Source[] sources, String uri) throws XMLStreamException, IOException {
      if (uri != null && this.webAppParser != null) {
         if (uri.equalsIgnoreCase("WEB-INF/web.xml")) {
            this.webappBean = (WebAppBean)this.webAppParser.mergeLibaryDescriptors(sources, uri);
            if (HTTPDebugLogger.isEnabled()) {
               this.dump((DescriptorBean)this.webappBean);
            }
         } else if (uri.equalsIgnoreCase("WEB-INF/weblogic.xml")) {
            this.wlWebAppBean = (WeblogicWebAppBean)this.webAppParser.mergeLibaryDescriptors(sources, uri);
            if (HTTPDebugLogger.isEnabled()) {
               this.dump((DescriptorBean)this.wlWebAppBean);
            }
         }

      }
   }

   private void processWebAppLibraries() throws ModuleException {
      this.mergeWebAppLibraries();
      if (mergeDescriptors) {
         this.mergeLibraryDescriptors();
      }

      this.mergeSharedLibrariesFilteringClassLoader();
   }

   private void mergeSharedLibrariesFilteringClassLoader() throws ModuleException {
      if (this.getWlWebAppBean() != null && this.getWlWebAppBean().getContainerDescriptors() != null && this.getWlWebAppBean().getContainerDescriptors().length != 0) {
         Set packageSet = new HashSet();
         Set resourceSet = new HashSet();
         ContainerDescriptorBean cBean = this.getWlWebAppBean().getContainerDescriptors()[0];
         PreferApplicationPackagesBean appPackages = cBean.getPreferApplicationPackages();
         PreferApplicationResourcesBean appResources = cBean.getPreferApplicationResources();
         if (appPackages != null && appPackages.getPackageNames() != null) {
            packageSet.addAll(Arrays.asList(appPackages.getPackageNames()));
         }

         if (appResources != null && appResources.getResourceNames() != null) {
            resourceSet.addAll(Arrays.asList(appResources.getResourceNames()));
         }

         Library[] lib = this.libraryManager.getReferencedLibraries();

         for(int i = 0; i < lib.length; ++i) {
            if (lib[i] instanceof WarLibraryDefinition) {
               WarLibraryDefinition warDefinition = (WarLibraryDefinition)lib[i];
               if (warDefinition.getPrefAppPackagesName() != null) {
                  packageSet.addAll(Arrays.asList(warDefinition.getPrefAppPackagesName()));
               }

               if (warDefinition.getPrefAppResourcesName() != null) {
                  resourceSet.addAll(Arrays.asList(warDefinition.getPrefAppResourcesName()));
               }
            }
         }

         String[] mergedResource;
         if (!packageSet.isEmpty() && appPackages != null) {
            mergedResource = (String[])((String[])packageSet.toArray(new String[0]));
            appPackages.setPackageNames(mergedResource);
         }

         if (!resourceSet.isEmpty() && appResources != null) {
            mergedResource = (String[])((String[])resourceSet.toArray(new String[0]));
            appResources.setResourceNames(mergedResource);
         }

      }
   }

   private void mergeWebAppLibraries() throws ModuleException {
      LibraryRefBean[] libs;
      if (this.getWlWebAppBean() == null) {
         libs = new LibraryRefBean[0];
      } else {
         libs = this.getWlWebAppBean().getLibraryRefs();
      }

      LibraryReference[] ref = null;

      try {
         ref = LibraryReferenceFactory.getWebLibReference(libs);
      } catch (IllegalSpecVersionTypeException var6) {
         throw new ModuleException(HTTPLogger.logIllegalWebLibSpecVersionRefLoggable(this.toString(), var6.getSpecVersion()).getMessage());
      }

      this.libraryManager = new LibraryManager(new LibraryReferencer(this.getModuleURI(), (RuntimeMBean)null, "Unresolved Webapp Library references for \"" + this.toString() + "\", defined in weblogic.xml"), this.appCtx.getPartitionName(), ref);
      this.appCtx.addLibraryManager(this.getId(), this.libraryManager);
      if (this.libraryManager.hasUnresolvedReferences()) {
         throw new ModuleException("Error: " + this.libraryManager.getUnresolvedReferencesError());
      } else {
         Library[] lib = this.libraryManager.getReferencedLibraries();

         try {
            for(int i = 0; i < lib.length; ++i) {
               this.war.addLibrary(lib[i]);
            }

            if (!this.isInternalApp() && !this.isInternalSAMLApp()) {
               this.libraryManager.lookupAndAddAutoReferences(Type.WAR, AutoReferrer.WebApp);
               Library[] autoreflib = this.libraryManager.getAutoReferencedLibraries();

               for(int i = 0; i < autoreflib.length; ++i) {
                  this.war.addLibrary(autoreflib[i]);
               }
            }
         } catch (IOException var7) {
            throw new ModuleException(var7);
         }

         this.libraryManager.addReferences();
      }
   }

   private void initLoader(GenericClassLoader parent, boolean createSubLoader) throws ModuleException {
      this.parentClassLoader = parent;
      this.createdClassLoader = createSubLoader;
      if (createSubLoader) {
         weblogic.utils.classloaders.Annotation annotation = new weblogic.utils.classloaders.Annotation(this.appCtx.getAppDeploymentMBean().getApplicationIdentifier(), this.normalizeId(this.getId(), this.moduleURI));
         this.webClassLoader = WarUtils.createChangeAwareClassLoader(this.finder, false, this.parentClassLoader, this.appCtx.isEar(), annotation);
      } else {
         this.webClassLoader = parent;
         this.webClassLoader.addClassFinder(this.finder);
      }

   }

   File getRootTempDir() {
      if (this.rootTempDir != null) {
         return this.rootTempDir;
      } else {
         String tempPath = this.getTempPath();
         this.rootTempDir = PathUtils.getAppTempDir(tempPath);
         if (!this.rootTempDir.exists() && !this.rootTempDir.mkdirs()) {
            HTTPLogger.logUnableToMakeDirectory(this.toString(), this.rootTempDir.getAbsolutePath());
         }

         return this.rootTempDir;
      }
   }

   String getTempPath() {
      HttpServer server = registry.getDefaultHttpServer();
      String appStr = ApplicationVersionUtils.replaceDelimiter(this.getApplicationId(), '_');
      String name = ApplicationVersionUtils.replaceDelimiter(this.getName(), '_');
      return PathUtils.generateTempPath(server.getName(), appStr, name);
   }

   public String getApplicationId() {
      String appId = this.appCtx.getApplicationId();
      return appId != null ? appId : this.getId();
   }

   public String getApplicationName() {
      return ApplicationVersionUtils.getNonVersionedName(this.getApplicationId());
   }

   public Collection getExtensions() {
      return this.moduleExtensions;
   }

   public ModuleExtensionContext getModuleExtensionContext() {
      if (this.isInternalApp()) {
         return null;
      } else {
         if (this.moduleExtensionContext == null) {
            this.moduleExtensionContext = WebAppModuleExtensionContext.getInstance(this, this.appCtx != null ? this.appCtx.getModuleContext(this.getId()) : null);
         }

         return this.moduleExtensionContext;
      }
   }

   public Descriptor getStandardDescriptor() {
      return ((DescriptorBean)this.getWebAppBean()).getDescriptor();
   }

   private void initMBeans() throws ModuleException {
      this.dmb = this.appCtx.getAppDeploymentMBean();
      ComponentMBean combean = this.findComponentMBeanInternal(this.appCtx, this.moduleURI, WebAppComponentMBean.class);
      if (combean instanceof WebAppComponentMBean) {
         this.mbean = (WebAppComponentMBean)combean;
      }

   }

   private void initFinder() throws ModuleException {
      this.finder = new MultiClassFinder();
      ClassFinder appFileOverrideFinder = AppFileOverrideUtils.getFinderIfRequired(this.appCtx.getAppDeploymentMBean(), this.moduleURI);
      if (appFileOverrideFinder != null) {
         this.finder.addFinderFirst(appFileOverrideFinder);
      }

   }

   private void initWebFragmentManager() throws ModuleException {
      try {
         this.webFragmentManager = new WebFragmentManager(this.war, this.webappBean);
      } catch (CycleFoundInGraphException var2) {
         HTTPLogger.logFragmentCircularReferencesFound();
         throw new ModuleException(var2);
      } catch (Exception var3) {
         throw new ModuleException(var3);
      }
   }

   public TargetMBean[] getTargets() {
      return this.dmb.getTargets();
   }

   private void initJNDIContext() throws ModuleException {
      Context envCtx = this.appCtx.getEnvContext();

      try {
         envCtx.lookup("/webapp");
      } catch (NameNotFoundException var5) {
         try {
            envCtx.createSubcontext("webapp");
         } catch (NamingException var4) {
            throw new ModuleException(var4);
         }
      } catch (NamingException var6) {
         throw new ModuleException(var6);
      }

   }

   public String getWLExtensionDirectory() {
      return "WEB-INF";
   }

   public void initUsingLoader(ApplicationContext ac, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.init(ac, parent, reg, false);
   }

   public GenericClassLoader init(ApplicationContext ac, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      return this.init(ac, parent, reg, true);
   }

   public String getModuleURI() {
      return this.moduleURI;
   }

   public String getId() {
      return this.contextRootFromAppDD != null ? this.contextRootFromAppDD : this.moduleURI;
   }

   public String getType() {
      return WebLogicModuleType.MODULETYPE_WAR;
   }

   public GenericClassLoader getClassLoader() {
      return this.webClassLoader;
   }

   protected GenericClassLoader getTemporaryClassLoader() {
      if (this.temporaryClassLoader != null) {
         return this.temporaryClassLoader;
      } else {
         ModuleContext modCtx = this.appCtx.getModuleContext(this.getId());
         if (modCtx != null) {
            this.temporaryClassLoader = modCtx.getTemporaryClassLoader();
         } else {
            this.temporaryClassLoader = ClassLoaderUtils.createTemporaryAppClassLoader(this.webClassLoader);
         }

         return this.temporaryClassLoader;
      }
   }

   protected void clearTemporaryClassLoader() {
      this.temporaryClassLoader = null;
      ModuleContext modCtx = this.appCtx.getModuleContext(this.getId());
      if (modCtx != null) {
         modCtx.removeTemporaryClassLoader();
      }

   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      LinkedList retList = new LinkedList();
      Iterator var2 = this.allContexts.values().iterator();

      while(var2.hasNext()) {
         WebAppServletContext ctx = (WebAppServletContext)var2.next();
         retList.add(ctx.getRuntimeMBean());
      }

      return (ComponentRuntimeMBean[])retList.toArray(new ComponentRuntimeMBean[retList.size()]);
   }

   public DescriptorBean[] getDescriptors() {
      if (this.webappBean != null && this.wlWebAppBean != null) {
         return new DescriptorBean[]{(DescriptorBean)this.webappBean, (DescriptorBean)this.wlWebAppBean};
      } else if (this.webappBean != null) {
         return new DescriptorBean[]{(DescriptorBean)this.webappBean};
      } else {
         return this.wlWebAppBean != null ? new DescriptorBean[]{(DescriptorBean)this.wlWebAppBean} : new DescriptorBean[0];
      }
   }

   public void prepare() throws ModuleException {
      this.moduleExtensions = this.appCtx.getModuleExtensions(this.getId());
      this.allContexts.clear();
      this.webappsDestroyed = false;
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(HTTPLogger.logPreparingLoggable(this.getAppDisplayName(), this.getId()).getMessage());
      }

      boolean success = false;

      try {
         WorkManagerCollection collection = this.appCtx.getWorkManagerCollection();
         if (this.wlWebAppBean != null && this.appCtx != null && collection.getWorkManagers(this.getId()).isEmpty()) {
            collection.populate(this.getId(), this.wlWebAppBean);
         }

         this.initWebFragmentManager();
         this.processAnnotations(false);
         this.detectJsfApplication();
         this.detectJSR375Application();
         this.registerExtensionTlds();
         this.configManager.prepare();
         this.registerVirtualDirectoryMapping();
         this.registerSecurityPermissions();
         if (this.isDeployedLocally()) {
            this.registerWebApp(registry.getDefaultHttpServer());
         }

         this.deployOnVirtualHosts(this.isInternalApp());
         this.deployOnPartition();
         this.addWorkManagerRuntimes(collection.getWorkManagers(this.getId()));
         ConcurrentManagedObjectCollection concurrentCollection = this.appCtx.getConcurrentManagedObjectCollection();
         if (this.wlWebAppBean != null && this.appCtx != null && !concurrentCollection.hasEntries(this.getId())) {
            List components = new ArrayList();
            Iterator var5 = this.allContexts.values().iterator();

            while(var5.hasNext()) {
               WebAppServletContext ctx = (WebAppServletContext)var5.next();
               components.add(ctx.getRuntimeMBean());
            }

            concurrentCollection.populate(this.getId(), RuntimeMBeanRegister.createWebRegister(components, this.wlWebAppBean));
         }

         success = true;
      } catch (DeploymentException var11) {
         throw new ModuleException(var11.getMessage(), var11);
      } catch (ManagementException var12) {
         throw new ModuleException(var12.getMessage(), var12);
      } finally {
         if (!success) {
            this.closeVirtualJarFile();
            this.destroyContexts();
         }

      }

   }

   public void activate() throws IllegalStateException, ModuleException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(HTTPLogger.logActivatingLoggable(this.getAppDisplayName(), this.getId()).getMessage());
      }

      if (this.webappsDestroyed) {
         this.prepare();
         if (this.moduleExtensionContext != null) {
            PojoEnvironmentBean envBean = this.moduleExtensionContext.getPojoEnvironmentBean();
            if (envBean != null) {
               Iterator var2 = this.moduleExtensionContext.getEnvironments().iterator();

               while(var2.hasNext()) {
                  Environment env = (Environment)var2.next();
                  env.contributeEnvEntries(envBean, (WeblogicEnvironmentBean)null, (AuthenticatedSubject)null);
               }
            }

            Object beanManager = this.moduleExtensionContext.getBeanManager();
            if (beanManager != null) {
               Iterator var8 = this.moduleExtensionContext.getEnvironments().iterator();

               while(var8.hasNext()) {
                  Environment env = (Environment)var8.next();

                  try {
                     env.getCompContext().bind("BeanManager", beanManager);
                  } catch (NamingException var6) {
                     var6.printStackTrace();
                  }
               }

               this.moduleExtensionContext.setBeanManager((Object)null);
            }
         }
      }

      this.activateContexts();
      this.registerBeanUpdateListeners();
      if (this.appCtx != null) {
         this.appCtx.getWorkManagerCollection().activateModuleEntries(this.getId());
      }

      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(HTTPLogger.logReadyLoggable(this.getAppDisplayName(), this.getId()).getMessage());
      }

   }

   public void start() throws ModuleException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(HTTPLogger.logStartingLoggable(this.getAppDisplayName(), this.getId()).getMessage());
      }

      if (this.moduleExtensionContext != null) {
         this.moduleExtensionContext.setWebAppDestroyed(false);
      }

      this.startContexts();
      this.war.cleanupClassInfos();
      this.clearTemporaryClassLoader();
      if (this.dmb != null && this.dmb.getOnDemandContextPaths() != null && this.dmb.getOnDemandContextPaths().length > 0) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("WebAppModule.activate: " + this.toString() + " unregister context paths ");
         }

         Iterator var1 = DeploymentProviderManager.instance.getProviders().iterator();

         while(var1.hasNext()) {
            DeploymentProvider provider = (DeploymentProvider)var1.next();
            provider.unclaim(this.dmb);
         }
      }

   }

   public void deactivate() throws IllegalStateException, ModuleException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(HTTPLogger.logDeactivatingLoggable(this.getAppDisplayName(), this.getId()).getMessage());
      }

      this.stopContexts();
      this.unregisterBeanUpdateListeners();
      this.destroyContexts();
      if (this.appCtx != null) {
         this.appCtx.getWorkManagerCollection().releaseModuleTasks(this.getModuleName());
      }

   }

   public boolean hasEjbInWar() {
      if (this.moduleExtensions != null) {
         Iterator var1 = this.moduleExtensions.iterator();

         while(var1.hasNext()) {
            ModuleExtension moduleExtension = (ModuleExtension)var1.next();
            if (moduleExtension instanceof EjbInWarIndicator) {
               return true;
            }
         }
      }

      return false;
   }

   public void unprepare() throws IllegalStateException, ModuleException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(HTTPLogger.logRollingBackLoggable(this.getAppDisplayName(), this.getId()).getMessage());
      }

      this.destroyContexts();
      if (this.appCtx != null) {
         this.appCtx.getWorkManagerCollection().removeModuleEntries(this.getId());
         this.appCtx.getConcurrentManagedObjectCollection().removeModuleEntries(this.getId());
      }

      if (this.finder != null) {
         this.finder.close();
      }

      this.closeVirtualJarFile();
      ResourceBundle.clearCache(this.webClassLoader);
      if (!WebAppConfigManager.isServerShutDown()) {
         this.cleanGeneratedJspClasses();
      }

   }

   public void remove() throws ModuleException, IllegalStateException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(HTTPLogger.logRemovingLoggable(this.getAppDisplayName(), this.moduleURI).getMessage());
      }

      if (this.appCtx != null) {
         this.appCtx.getWorkManagerCollection().removeModuleEntries(this.getId());
         this.appCtx.getConcurrentManagedObjectCollection().removeModuleEntries(this.getId());
      }

      this.war.remove();
      this.cleanupTempDirs();
      this.removeSavedSessionState();
      this.allContexts.clear();
   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
      this.destroyContexts();
      if (this.createdClassLoader) {
         if (this.webClassLoader != null) {
            this.webClassLoader.close();
         }

         this.createdClassLoader = false;
      }

      this.unregisterSecurityPermissionSpec();
      this.finder = null;
      this.webClassLoader = null;
      reg.removeUpdateListener(this);
      this.closeVirtualJarFile();
   }

   public boolean acceptURI(String uri) {
      return !uri.endsWith(".class") && !uri.endsWith(".jar") ? this.acceptModuleUri(this.appCtx, this.moduleURI, uri) : false;
   }

   protected boolean acceptModuleUri(ApplicationContextInternal appCtx, String moduleUri, String uri) {
      boolean accept = super.acceptModuleUri(appCtx, moduleUri, uri);
      if (this.wlWebAppBean == null) {
         return accept;
      } else {
         VirtualDirectoryMappingBean[] mappings = this.wlWebAppBean.getVirtualDirectoryMappings();
         if (mappings != null && mappings.length != 0) {
            if (!WebAppConfigManager.isAbsoluteFilePath(uri)) {
               uri = appCtx.getStagingPath() + WebAppConfigManager.FSC + uri;
            }

            return this.war.isVirtualMappingUri(uri) || accept;
         } else {
            return accept;
         }
      }
   }

   public void adminToProduction() {
      this.setSuspended(false);
      Iterator var1 = this.allContexts.values().iterator();

      while(var1.hasNext()) {
         WebAppServletContext context = (WebAppServletContext)var1.next();
         if (context != null) {
            context.setContextPhase(WebAppServletContext.ContextPhase.START);
         }
      }

   }

   public void forceProductionToAdmin() throws ModuleException {
      this.setSuspended(true);
      GracefulShutdownHelper.notifyGracefulProductionToAdmin(this.appCtx.getApplicationId(), this);
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws ModuleException {
      if (!registry.getManagementProvider().isServerSuspendingShuttingDown() && !this.isSuspended()) {
         if (!this.isPartitionShutdown()) {
            if (!this.isResourceGroupShutdown()) {
               boolean ignoreSessions = ApplicationVersionUtils.getIgnoreSessionsAppCtxParam(this.appCtx);
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("WebAppModule.gracefulProductionToAdmin: " + this.toString() + " received for " + this.moduleURI + ", ignoreSessions=" + ignoreSessions);
               }

               ShutdownCallback callback = barrier.registerWMShutdown();
               GracefulShutdownHelper.waitForPendingSessions(this.appCtx.getApplicationId(), this, ignoreSessions);
               this.setSuspended(true);
               callback.completed();
            }
         }
      }
   }

   private boolean isPartitionShutdown() {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      String partitionName = cic.getPartitionName();
      if (partitionName == null) {
         return false;
      } else {
         return WebAppPartitionManagerInterceptor.isPartitionSuspending() || WebAppPartitionManagerInterceptor.isPartitionShutdown();
      }
   }

   private boolean isResourceGroupShutdown() {
      return WebAppResourceGroupManagerInterceptor.isResourceGroupShutdown(this.appCtx) || WebAppResourceGroupManagerInterceptor.isResourceGroupSuspending(this.appCtx);
   }

   public void prepareUpdate(String uri) throws ModuleException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("WebAppModule: " + this.toString() + " received prepareUpdate for " + uri);
      }

      String unmangledUri = this.unmangle(this.appCtx, this.moduleURI, uri);
      if (this.isDescriptorUri(unmangledUri) && !this.updatingDescriptor) {
         this.prepareUpdateDescriptors();
         this.updatingDescriptor = true;
      }

      Iterator var3 = this.allContexts.values().iterator();

      while(var3.hasNext()) {
         WebAppServletContext ctx = (WebAppServletContext)var3.next();
         ctx.precompileJspsOnUpdate(unmangledUri);
      }

   }

   public void activateUpdate(String uri) throws ModuleException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("WebAppModule: " + this.toString() + " received activateUpdate for " + uri);
      }

      String unmangledUri = this.unmangle(this.appCtx, this.moduleURI, uri);
      if (this.isDescriptorUri(unmangledUri)) {
         if (this.updatingDescriptor) {
            this.activateUpdateDescriptors();
            this.updatingDescriptor = false;
         }
      }
   }

   private void activateUpdateDescriptors() throws ModuleException {
      Descriptor webAppDesc = this.webappBean == null ? null : ((DescriptorBean)this.webappBean).getDescriptor();
      Descriptor wlWebAppDesc = this.wlWebAppBean == null ? null : ((DescriptorBean)this.wlWebAppBean).getDescriptor();

      try {
         if (webAppDesc != null) {
            webAppDesc.activateUpdate();
         }

         if (wlWebAppDesc != null) {
            wlWebAppDesc.activateUpdate();
         }

      } catch (DescriptorUpdateFailedException var4) {
         throw new ModuleException(var4);
      }
   }

   public void rollbackUpdate(String uri) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("WebAppModule: " + this.toString() + " received rollbackUpdate for " + uri);
      }

      String unmangledUri = this.unmangle(this.appCtx, this.moduleURI, uri);
      if (this.isDescriptorUri(unmangledUri)) {
         this.rollbackUpdateDescriptors();
      }
   }

   private void rollbackUpdateDescriptors() {
      Descriptor webAppDesc = this.webappBean == null ? null : ((DescriptorBean)this.webappBean).getDescriptor();
      Descriptor wlWebAppDesc = this.wlWebAppBean == null ? null : ((DescriptorBean)this.wlWebAppBean).getDescriptor();
      if (webAppDesc != null) {
         webAppDesc.rollbackUpdate();
      }

      if (wlWebAppDesc != null) {
         wlWebAppDesc.rollbackUpdate();
      }

   }

   private UpdateListener.Registration getUpdateListener() {
      return this.appCtx instanceof UpdateListener.Registration ? (UpdateListener.Registration)this.appCtx : null;
   }

   public War getWarInstance() {
      return this.war;
   }

   public String toString() {
      return "WebAppModule(" + this.getAppDisplayName() + ":" + this.getId() + ")";
   }

   String getLogContext() {
      return this.toString();
   }

   boolean isServletReloadAllowed() {
      return this.webClassLoader instanceof ChangeAwareClassLoader;
   }

   public synchronized boolean isSuspended() {
      return this.isSuspended;
   }

   public synchronized void setSuspended(boolean b) {
      this.isSuspended = b;
   }

   public PersistenceUnitRegistryProvider getPersistenceUnitRegistryProvider() {
      return this.unitRegistryProvider;
   }

   void setPersistenceUnitRegistryProvider(PersistenceUnitRegistryProvider unitRegistryProvider) {
      this.unitRegistryProvider = unitRegistryProvider;
   }

   ApplicationContextInternal getApplicationContext() {
      return this.appCtx;
   }

   private void cleanupLeftoverFiles() {
      if (this.war.isArchiveReExtract()) {
         this.cleanGeneratedJspClasses();
      }

   }

   private void cleanGeneratedJspClasses() {
      String packagePrefix = this.jspManager.getJspcPkgPrefix();
      int firstDot = packagePrefix.indexOf(46);
      if (firstDot != -1) {
         packagePrefix = packagePrefix.substring(0, firstDot);
      }

      FileUtils.remove(new File(this.jspManager.getJSPWorkingDir(), packagePrefix));
   }

   private void registerSecurityPermissions() throws DeploymentException {
      if (System.getSecurityManager() != null) {
         try {
            this.securityPermissionsRegistered = registry.getSecurityProvider().registerSEPermissions(this.getWebDirsToGrantPermission(), EarUtils.getPermissions(this.permissionsBean), this.getSecurityPermissionSpecFromWLDD());
         } catch (SecurityServiceException var2) {
            throw new DeploymentException(var2);
         }
      }
   }

   private String getSecurityPermissionSpecFromWLDD() {
      SecurityPermissionBean secPerm = null;
      if (this.wlWebAppBean != null) {
         secPerm = (SecurityPermissionBean)DescriptorUtils.getFirstChildOrDefaultBean(this.wlWebAppBean, this.wlWebAppBean.getSecurityPermissions(), "SecurityPermission");
      }

      return secPerm != null ? secPerm.getSecurityPermissionSpec() : null;
   }

   private void unregisterSecurityPermissionSpec() {
      if (this.securityPermissionsRegistered) {
         String[] webAppPaths = this.getWebDirsToGrantPermission();
         registry.getSecurityProvider().removeJavaSecurityPolices(webAppPaths);
         this.securityPermissionsRegistered = false;
      }

   }

   private String[] getWebDirsToGrantPermission() {
      String[] webAppPaths = new String[2];
      String docRoot = this.configManager.getDocRoot();
      String workingDir = this.jspManager.getJSPWorkingDir();
      webAppPaths[0] = getCanonicalPath(docRoot).replace(WebAppConfigManager.FSC, '/');
      webAppPaths[1] = getCanonicalPath(workingDir).replace(WebAppConfigManager.FSC, '/');
      return webAppPaths;
   }

   private static String getCanonicalPath(String originalPath) {
      try {
         return (new File(originalPath)).getCanonicalPath();
      } catch (IOException var2) {
         return null;
      }
   }

   private void registerBeanUpdateListeners() {
      if (this.wlWebAppBean != null) {
         BeanUpdateListener listener = this.jspManager.getBeanUpdateListener();
         ((DescriptorBean)this.wlWebAppBean).addBeanUpdateListener(listener);
         DescriptorBean db;
         if (this.wlWebAppBean.getJspDescriptors() != null && this.wlWebAppBean.getJspDescriptors().length > 0) {
            db = (DescriptorBean)this.wlWebAppBean.getJspDescriptors()[0];
            db.addBeanUpdateListener(listener);
         }

         listener = this.configManager.getBeanUpdateListener();
         ((DescriptorBean)this.wlWebAppBean).addBeanUpdateListener(listener);
         if (this.wlWebAppBean.getContainerDescriptors() != null && this.wlWebAppBean.getContainerDescriptors().length > 0) {
            db = (DescriptorBean)this.wlWebAppBean.getContainerDescriptors()[0];
            db.addBeanUpdateListener(listener);
         }

         listener = this.sessionConfigManager.getBeanUpdateListener();
         ((DescriptorBean)this.wlWebAppBean).addBeanUpdateListener(listener);
         if (this.wlWebAppBean.getSessionDescriptors() != null && this.wlWebAppBean.getSessionDescriptors().length > 0) {
            db = (DescriptorBean)this.wlWebAppBean.getSessionDescriptors()[0];
            db.addBeanUpdateListener(listener);
         }

         this.wacListener = new WebAppContainerMBeanUpdateListener(this.allContexts.keySet());
         registry.getDomainMBean().getWebAppContainer().addBeanUpdateListener(this.wacListener);
      }
   }

   private void unregisterBeanUpdateListeners() {
      if (this.wlWebAppBean != null) {
         BeanUpdateListener listener = this.jspManager.getBeanUpdateListener();
         ((DescriptorBean)this.wlWebAppBean).removeBeanUpdateListener(listener);
         DescriptorBean db;
         if (this.wlWebAppBean.getJspDescriptors() != null && this.wlWebAppBean.getJspDescriptors().length > 0) {
            db = (DescriptorBean)this.wlWebAppBean.getJspDescriptors()[0];
            db.removeBeanUpdateListener(listener);
         }

         listener = this.configManager.getBeanUpdateListener();
         ((DescriptorBean)this.wlWebAppBean).removeBeanUpdateListener(listener);
         if (this.wlWebAppBean.getContainerDescriptors() != null && this.wlWebAppBean.getContainerDescriptors().length > 0) {
            db = (DescriptorBean)this.wlWebAppBean.getContainerDescriptors()[0];
            db.removeBeanUpdateListener(listener);
         }

         listener = this.sessionConfigManager.getBeanUpdateListener();
         ((DescriptorBean)this.wlWebAppBean).removeBeanUpdateListener(listener);
         if (this.wlWebAppBean.getSessionDescriptors() != null && this.wlWebAppBean.getSessionDescriptors().length > 0) {
            db = (DescriptorBean)this.wlWebAppBean.getSessionDescriptors()[0];
            db.removeBeanUpdateListener(listener);
         }

         registry.getDomainMBean().getWebAppContainer().removeBeanUpdateListener(this.wacListener);
      }
   }

   private void cleanupTempDirs() {
      File tmpDir = this.getRootTempDir();
      FileUtils.remove(tmpDir);
      File topLevelTempDir = tmpDir.getParentFile();
      if (topLevelTempDir != null) {
         FileUtils.remove(topLevelTempDir);
      }

   }

   private void deployOnVirtualHosts(boolean isInternal) throws ModuleException {
      VirtualHostMBean[] vhosts = this.resolveWebServers();
      if (vhosts != null) {
         for(int i = 0; i < vhosts.length; ++i) {
            HttpServer httpServer = WebAppConfigManager.httpServerManager.getHttpServer(vhosts[i].getName());
            if (httpServer == null && isInternal) {
               try {
                  httpServer = WebAppConfigManager.httpServerManager.initWebServer(vhosts[i]);
               } catch (DeploymentException var6) {
                  throw new ModuleException("Failed to initialize virtual host", var6);
               }
            }

            if (httpServer != null && httpServer != registry.getDefaultHttpServer()) {
               this.registerWebApp(httpServer);
            }
         }

      }
   }

   private void deployOnPartition() throws ModuleException {
      TargetMBean[] targets = this.findModuleTargets();
      if (targets != null && targets.length != 0) {
         try {
            TargetMBean[] var2 = targets;
            int var3 = targets.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               TargetMBean target = var2[var4];
               if (target instanceof VirtualTargetMBean) {
                  VirtualTargetMBean vtmb = (VirtualTargetMBean)target;
                  if (TargetUtils.isDeployedLocally(vtmb.getTargets())) {
                     HttpServer httpServer = WebAppConfigManager.httpServerManager.deployPartitionWebServer(vtmb);
                     if (httpServer != null) {
                        this.registerWebApp(httpServer);
                     }
                  }
               }
            }

         } catch (DeploymentException var8) {
            throw new ModuleException("Failed to deploy on partition", var8);
         }
      }
   }

   private void registerWebApp(HttpServer httpServer) throws ModuleException {
      this.validateContextPath(httpServer);
      this.allContexts.put(httpServer, httpServer.loadWebApp(this.mbean, this));
      if (httpServer.getUriPath() != null) {
         this.sessionConfigManager.setPartitionUriPath(httpServer.getUriPath());
      }

      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("registered " + this.contextPath + " with " + httpServer);
      }

   }

   private void removeSavedSessionState() {
      Iterator var1 = this.allContexts.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry entry = (Map.Entry)var1.next();
         HttpServer httpServer = (HttpServer)entry.getKey();
         WebAppServletContext wasc = (WebAppServletContext)entry.getValue();
         httpServer.getServletContextManager().removeSavedSessionState(wasc.getContextPath());
      }

   }

   private static String removeLeadingSlash(String s) {
      if (s == null) {
         return null;
      } else if (s.length() < 1) {
         return s;
      } else {
         return s.charAt(0) == '/' ? s.substring(1) : s;
      }
   }

   private boolean isDescriptorUri(String uri) {
      uri = removeLeadingSlash(uri);
      return "WEB-INF/web.xml".equals(uri) || "WEB-INF/weblogic.xml".equals(uri);
   }

   private void prepareUpdateDescriptors() throws ModuleException {
      try {
         this.virtualJarFile = this.createVirtualJarFile();
      } catch (IOException var18) {
         throw new ModuleException(var18.getMessage(), var18);
      }

      WebAppParser parser = this.getWebAppParser(this.virtualJarFile, this.appCtx.findDeploymentPlan());
      if (parser == null) {
         this.closeVirtualJarFile();
      } else {
         WebAppBean newWebAppBean = null;
         WeblogicWebAppBean newWlBean = null;

         try {
            weblogic.logging.Loggable l;
            try {
               newWebAppBean = parser.getWebAppBean();
               newWlBean = parser.getWeblogicWebAppBean();
            } catch (IOException var15) {
               l = HTTPLogger.logErrorReadingWebAppLoggable(this.toString(), this.getWarPath(), var15);
               l.log();
               this.createModuleException(l.getMessage(), var15);
            } catch (Exception var16) {
               l = HTTPLogger.logLoadErrorLoggable(this.toString(), this.getWarPath(), var16);
               l.log();
               this.createModuleException(l.getMessage(), var16);
            }
         } finally {
            this.closeVirtualJarFile();
         }

         try {
            this.processAnnotations(false, newWebAppBean, newWlBean);
         } catch (DeploymentException var14) {
            throw new ModuleException(var14.getMessage(), var14);
         }

         try {
            Descriptor preparedWeblogicWebAppDescriptor;
            if (newWebAppBean != null && this.webappBean != null) {
               preparedWeblogicWebAppDescriptor = ((DescriptorBean)this.webappBean).getDescriptor();
               preparedWeblogicWebAppDescriptor.prepareUpdate(((DescriptorBean)newWebAppBean).getDescriptor());
            }

            if (newWlBean != null && this.wlWebAppBean != null) {
               preparedWeblogicWebAppDescriptor = ((DescriptorBean)this.wlWebAppBean).getDescriptor();
               preparedWeblogicWebAppDescriptor.prepareUpdate(((DescriptorBean)newWlBean).getDescriptor());
            }

         } catch (DescriptorUpdateRejectedException var13) {
            throw new ModuleException(var13);
         }
      }
   }

   private void validateContextPath(HttpServer httpServer) throws ModuleException {
      String partitionContextPath = this.contextPath;
      String partitionUriPath = httpServer.getUriPath();
      if (partitionUriPath != null && !partitionUriPath.equals("/")) {
         partitionContextPath = partitionUriPath + this.contextPath;
      }

      WebAppServletContext existing = httpServer.getServletContextManager().getContextForContextPath(partitionContextPath, this.dmb.getVersionIdentifier());
      if (existing != null) {
         if (existing.getWebAppModule() != null) {
            throw new ModuleException("Context path '" + partitionContextPath + "' is already in use by the module: " + existing.getWebAppModule().getName() + " application: " + existing.getWebAppModule().getAppDisplayName());
         } else {
            throw new ModuleException("Context path '" + partitionContextPath + "' is already in use by the module: " + existing.getName());
         }
      }
   }

   private boolean isSynthesizedApplicationXml() throws ModuleException {
      VirtualJarFile vjf = null;

      boolean var2;
      try {
         if (this.appCtx != null && this.appCtx.getApplicationFileManager() != null) {
            vjf = this.appCtx.getApplicationFileManager().getVirtualJarFile();
            var2 = vjf.getEntry("META-INF/application.xml") == null;
            return var2;
         }

         var2 = true;
      } catch (IOException var12) {
         throw new ModuleException(var12.getMessage(), var12);
      } finally {
         if (vjf != null) {
            try {
               vjf.close();
            } catch (IOException var11) {
            }
         }

      }

      return var2;
   }

   private String fixupContextPath(String cp) {
      if (!cp.equals("/") && !cp.equals("")) {
         return cp.startsWith("/") ? cp : "/" + cp;
      } else {
         return "";
      }
   }

   private String substituteAppName(String cp) {
      return cp.indexOf("${APPNAME}") == -1 ? cp : StringUtils.replaceGlobal(cp, "${APPNAME}", ApplicationVersionUtils.getApplicationName(this.appCtx.getApplicationId()));
   }

   protected WebAppParser getWebAppParser(VirtualJarFile war, DeploymentPlanBean plan) throws ModuleException {
      this.altDDFile = this.resolveAltDD(this.appCtx, this.moduleURI);
      File config = null;
      if (this.dmb.getPlanDir() != null) {
         config = new File(this.dmb.getLocalPlanDir());
      }

      WebAppDescriptor desc = null;
      if (this.altDDFile != null) {
         desc = new WebAppDescriptor(this.altDDFile, war, config, plan, this.moduleURI, this.appCtx.getDescriptorCacheDir());
      } else {
         desc = new WebAppDescriptor(war, config, plan, this.moduleURI, this.appCtx.getDescriptorCacheDir());
      }

      if (this.appCtx != null && this.appCtx.getAppDeploymentMBean().isInternalApp() && registry.getDomainMBean().isInternalAppsDeployOnDemandEnabled()) {
         desc.setValidateSchema(false);
      }

      return desc;
   }

   protected PermissionsBean getAppSpecificPermissionsBean(VirtualJarFile war, DeploymentPlanBean plan) throws IOException, XMLStreamException {
      File config = null;
      if (this.dmb.getPlanDir() != null) {
         config = new File(this.dmb.getLocalPlanDir());
      }

      PermissionsDescriptorLoader desc = new PermissionsDescriptorLoader(war, config, plan, this.moduleURI, this.appCtx.getDescriptorCacheDir());
      return (PermissionsBean)desc.loadDescriptorBean();
   }

   private void loadDescriptor() throws ModuleException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(HTTPLogger.logLoadingDescriptorsLoggable(this.getAppDisplayName(), this.getId()).getMessage());
      }

      try {
         weblogic.logging.Loggable l;
         try {
            this.virtualJarFile = this.createVirtualJarFile();

            try {
               WebAppParser parser = this.getWebAppParser(this.virtualJarFile, this.dmb.getDeploymentPlanDescriptor());
               this.webappBean = parser.getWebAppBean();
               this.wlWebAppBean = parser.getWeblogicWebAppBean();
               if (this.wlWebAppBean == null) {
                  this.wlWebAppBean = GlassFishWebAppParser.getParser(this.virtualJarFile).getWeblogicWebAppBean();
               }

               if (this.appCtx != null && this.appCtx.isEar()) {
                  this.permissionsBean = this.appCtx.getPermissionsBean();
               } else {
                  this.permissionsBean = this.getAppSpecificPermissionsBean(this.virtualJarFile, this.dmb.getDeploymentPlanDescriptor());
               }

               if (parser instanceof WebAppInternalParser) {
                  this.webAppParser = (WebAppInternalParser)parser;
               }
            } catch (FileNotFoundException var10) {
            } catch (IOException var11) {
               throw new ModuleException(var11.getMessage(), var11);
            }
         } catch (FileNotFoundException var12) {
            l = HTTPLogger.logUnableToFindWebAppLoggable(this.toString(), this.getWarPath(), var12);
            l.log();
            this.createModuleException(l.getMessage(), var12);
         } catch (IOException var13) {
            l = HTTPLogger.logErrorReadingWebAppLoggable(this.toString(), this.getWarPath(), var13);
            l.log();
            this.createModuleException(l.getMessage(), var13);
         } catch (Exception var14) {
            l = HTTPLogger.logLoadErrorLoggable(this.toString(), this.getWarPath(), var14);
            l.log();
            this.createModuleException(l.getMessage(), var14);
         }
      } finally {
         this.closeVirtualJarFile();
      }

   }

   private void initWar() throws ModuleException {
      try {
         if (this.virtualJarFile == null) {
            this.virtualJarFile = this.createVirtualJarFile();
         }
      } catch (IOException var8) {
         throw new ModuleException(var8.getMessage(), var8);
      }

      SplitDirectoryInfo splitInfo = this.appCtx.getSplitDirectoryInfo();
      String[] splitClasses = splitInfo != null ? splitInfo.getWebAppClasses(this.getModuleURI()) : null;
      WarDefinition warDefinition = new WarDefinition(this.getId(), this.virtualJarFile, this.shouldUseOriginalJars(), splitClasses, this.getCacheDir());

      try {
         this.war = warDefinition.extract(this.getRootTempDir(), this.configManager);
      } catch (JarFileUtils.PathLengthException var6) {
         Loggable l = HTTPLogger.logExtractionPathTooLongLoggable(this.toString(), this.getWarPath(), var6);
         l.log();
         throw new ModuleException(l.getMessage(), var6);
      } catch (IOException var7) {
         throw new ModuleException(var7);
      }
   }

   private boolean shouldUseOriginalJars() {
      return ConsoleExtensionProcessor.support(this.getId()) || ManagementServicesExtensionProcessor.support(this.getId());
   }

   private VirtualJarFile createVirtualJarFile() throws ModuleException, IOException {
      String warPath = this.getWarPath();
      if (this.isArchived()) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug(HTTPLogger.logLoadingFromWARLoggable(this.toString(), this.getName(), warPath).getMessage());
         }

         return VirtualJarFactory.createVirtualJar(new JarFile(warPath));
      } else {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug(HTTPLogger.logLoadingFromDirLoggable(this.toString(), this.toString(), warPath).getMessage());
         }

         return this.appFileManager.getVirtualJarFile(this.moduleURI);
      }
   }

   private void closeVirtualJarFile() {
      if (this.virtualJarFile != null) {
         try {
            this.virtualJarFile.close();
         } catch (IOException var5) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug(var5.getMessage(), var5);
            }
         } finally {
            this.virtualJarFile = null;
         }
      }

   }

   protected boolean isDeployedLocally() {
      TargetMBean[] targets = this.findModuleTargets();
      if (targets != null && targets.length != 0) {
         for(int i = 0; i < targets.length; ++i) {
            if (!(targets[i] instanceof VirtualHostMBean) && !(targets[i] instanceof VirtualTargetMBean) && TargetUtils.findLocalServerTarget(new TargetMBean[]{targets[i]}) != null) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private TargetMBean[] findModuleTargets() {
      BasicDeploymentMBean proposed = null;
      if (this.appCtx.getProposedDomain() != null) {
         proposed = this.appCtx.getProposedDomain().lookupAppDeployment(this.appCtx.getApplicationId());
      }

      TargetMBean[] targets = TargetUtils.findModuleTargets(proposed, this.appCtx.getBasicDeploymentMBean(), this.appCtx, this.getId());
      if (targets == null || targets.length == 0) {
         targets = TargetUtils.findModuleTargets(proposed, this.appCtx.getBasicDeploymentMBean(), this.appCtx, this.getModuleURI());
      }

      return targets;
   }

   private VirtualHostMBean[] resolveWebServers() {
      TargetMBean[] targets = this.findModuleTargets();
      if (targets != null && targets.length != 0) {
         ArrayList vhList = new ArrayList();

         for(int i = 0; i < targets.length; ++i) {
            if (targets[i] instanceof VirtualHostMBean) {
               VirtualHostMBean vh = (VirtualHostMBean)targets[i];
               if (TargetUtils.isDeployedLocally(vh.getTargets())) {
                  vhList.add(vh);
               }
            }
         }

         return (VirtualHostMBean[])((VirtualHostMBean[])vhList.toArray(new VirtualHostMBean[vhList.size()]));
      } else {
         return new VirtualHostMBean[0];
      }
   }

   private void addWorkManagerRuntimes(List workManagers) throws ManagementException {
      ApplicationRuntimeMBean appRuntime = this.appCtx.getRuntime();
      Iterator var3 = this.allContexts.values().iterator();

      while(var3.hasNext()) {
         WebAppServletContext ctx = (WebAppServletContext)var3.next();
         ComponentRuntimeMBean parent = ctx.getRuntimeMBean();
         registry.getManagementProvider().addWorkManagerRuntimes(parent, appRuntime, workManagers);
      }

   }

   private void activateContexts() throws ModuleException {
      Iterator var1 = this.allContexts.values().iterator();

      while(var1.hasNext()) {
         WebAppServletContext ctx = (WebAppServletContext)var1.next();

         try {
            ctx.activate();
         } catch (DeploymentException var4) {
            throw new ModuleException(var4.getMessage(), var4);
         } catch (Throwable var5) {
            throw new ModuleException(var5.getMessage(), var5);
         }
      }

   }

   private void startContexts() throws ModuleException {
      Iterator var1 = this.allContexts.values().iterator();

      while(var1.hasNext()) {
         WebAppServletContext ctx = (WebAppServletContext)var1.next();

         try {
            ctx.start();
         } catch (DeploymentException var4) {
            throw new ModuleException(var4.getMessage(), var4);
         } catch (Throwable var5) {
            throw new ModuleException(var5.getMessage(), var5);
         }
      }

   }

   private void stopContexts() {
      Iterator var1 = this.allContexts.values().iterator();

      while(var1.hasNext()) {
         WebAppServletContext ctx = (WebAppServletContext)var1.next();
         ctx.stop();
      }

   }

   private void destroyContexts() {
      if (!this.webappsDestroyed) {
         this.webappsDestroyed = true;
         if (this.moduleExtensionContext != null) {
            this.moduleExtensionContext.setWebAppDestroyed(true);
         }

         if (this.libraryManager != null) {
            this.libraryManager.removeReferences();
            this.appCtx.removeLibraryManager(this.getId(), this.libraryManager);
            this.libraryManager = null;
         }

         Iterator var1 = this.allContexts.entrySet().iterator();

         while(var1.hasNext()) {
            Map.Entry entry = (Map.Entry)var1.next();
            HttpServer httpServer = (HttpServer)entry.getKey();
            WebAppServletContext ctx = (WebAppServletContext)entry.getValue();
            httpServer.unloadWebApp(ctx, this.dmb.getVersionIdentifier());
         }

      }
   }

   public String getWarPath() {
      return this.warPath;
   }

   public boolean isArchived() {
      return this.isArchived;
   }

   public WebAppBean getWebAppBean() {
      return this.webappBean;
   }

   public WeblogicWebAppBean getWlWebAppBean() {
      return this.wlWebAppBean;
   }

   ClassLoader bounceClassLoader() {
      try {
         GenericClassLoader gcl = this.webClassLoader;
         weblogic.utils.classloaders.Annotation a = gcl.getAnnotation();
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("reloading servlet classloader for " + this.toString());
         }

         this.preRefreshClassLoaderExtension();
         this.webClassLoader = WarUtils.createChangeAwareClassLoader(this.finder, false, gcl.getParent(), this.appCtx.isEar(), a);
         this.reverseClassLoaderHirarchyIfNecessary();
         this.appClassLoaderManager.addModuleLoader(this.webClassLoader, this.getId());
         this.postRefreshClassLoaderExtension();
      } catch (IllegalStateException var3) {
         HTTPLogger.logFailedToBounceClassLoader(this.getAppDisplayName(), this.getId(), var3);
      } catch (ModuleException var4) {
         HTTPLogger.logFailedToBounceClassLoader(this.getAppDisplayName(), this.getId(), var4);
      }

      return this.webClassLoader;
   }

   private void postRefreshClassLoaderExtension() throws ModuleException {
      if (this.moduleExtensions != null) {
         Iterator var1 = this.moduleExtensions.iterator();

         while(var1.hasNext()) {
            ModuleExtension extension = (ModuleExtension)var1.next();
            extension.postRefreshClassLoader();
         }
      }

   }

   private void preRefreshClassLoaderExtension() throws ModuleException {
      if (this.moduleExtensions != null) {
         Iterator var1 = this.moduleExtensions.iterator();

         while(var1.hasNext()) {
            ModuleExtension extension = (ModuleExtension)var1.next();
            extension.preRefreshClassLoader();
         }
      }

   }

   void deactivateModuleExtensionsForReload() {
      if (this.moduleExtensions != null) {
         ModuleExtension[] extensions = (ModuleExtension[])this.moduleExtensions.toArray(new ModuleExtension[0]);

         int i;
         ModuleExtension extension;
         for(i = extensions.length - 1; i >= 0; --i) {
            extension = extensions[i];

            try {
               extension.preDeactivate();
            } catch (ModuleException var6) {
               var6.printStackTrace();
            }
         }

         for(i = extensions.length - 1; i >= 0; --i) {
            extension = extensions[i];

            try {
               extension.postDeactivate();
            } catch (ModuleException var5) {
               var5.printStackTrace();
            }
         }

      }
   }

   void unprepareModuleExtensionsForReload() {
      if (this.moduleExtensions != null) {
         ModuleExtension[] extensions = (ModuleExtension[])this.moduleExtensions.toArray(new ModuleExtension[0]);

         int i;
         ModuleExtension extension;
         for(i = extensions.length - 1; i >= 0; --i) {
            extension = extensions[i];

            try {
               extension.preUnprepare(this.getUpdateListener());
            } catch (ModuleException var6) {
               var6.printStackTrace();
            }
         }

         for(i = extensions.length - 1; i >= 0; --i) {
            extension = extensions[i];

            try {
               extension.postUnprepare();
            } catch (ModuleException var5) {
               var5.printStackTrace();
            }
         }

      }
   }

   void prePrepareModuleExtensionsForReload() throws ModuleException {
      if (this.moduleExtensions != null) {
         Iterator var1 = this.moduleExtensions.iterator();

         while(var1.hasNext()) {
            ModuleExtension extension = (ModuleExtension)var1.next();
            extension.prePrepare();
         }

      }
   }

   void postPrepareModuleExtensionsForReload() throws ModuleException {
      if (this.moduleExtensions != null) {
         Iterator var1 = this.moduleExtensions.iterator();

         while(var1.hasNext()) {
            ModuleExtension extension = (ModuleExtension)var1.next();
            extension.postPrepare(this.getUpdateListener());
         }

      }
   }

   void preActivateModuleExtensionsForReload() throws ModuleException {
      if (this.moduleExtensions != null) {
         Iterator var1 = this.moduleExtensions.iterator();

         while(var1.hasNext()) {
            ModuleExtension extension = (ModuleExtension)var1.next();
            extension.preActivate();
         }

      }
   }

   void postActivateModuleExtensionsForReload() throws ModuleException {
      if (this.moduleExtensions != null) {
         Iterator var1 = this.moduleExtensions.iterator();

         while(var1.hasNext()) {
            ModuleExtension extension = (ModuleExtension)var1.next();
            extension.postActivate();
         }

      }
   }

   void startModuleExtensionsForReload() throws ModuleException {
      if (this.moduleExtensions != null) {
         Iterator var1 = this.moduleExtensions.iterator();

         while(var1.hasNext()) {
            ModuleExtension extension = (ModuleExtension)var1.next();
            extension.start();
         }

      }
   }

   void deactivateAppDeploymentExtensionsForReload() {
      String[] var1 = appDeploymentPreprocessorForReload;
      int var2 = var1.length;

      int var3;
      String extensionName;
      AppDeploymentExtension extension;
      for(var3 = 0; var3 < var2; ++var3) {
         extensionName = var1[var3];
         extension = this.appCtx.getAppDeploymentExtension(extensionName);
         if (extension != null) {
            try {
               extension.deactivate(this.appCtx);
            } catch (DeploymentException var8) {
               var8.printStackTrace();
            }
         }
      }

      var1 = appDeploymentPostprocessorForReload;
      var2 = var1.length;

      for(var3 = 0; var3 < var2; ++var3) {
         extensionName = var1[var3];
         extension = this.appCtx.getAppDeploymentExtension(extensionName);
         if (extension != null) {
            try {
               extension.deactivate(this.appCtx);
            } catch (DeploymentException var7) {
               var7.printStackTrace();
            }
         }
      }

   }

   void unprepareAppDeploymentExtensionsForReload() {
      String[] var1 = appDeploymentPreprocessorForReload;
      int var2 = var1.length;

      int var3;
      String extensionName;
      AppDeploymentExtension extension;
      for(var3 = 0; var3 < var2; ++var3) {
         extensionName = var1[var3];
         extension = this.appCtx.getAppDeploymentExtension(extensionName);
         if (extension != null) {
            try {
               extension.unprepare(this.appCtx);
            } catch (DeploymentException var8) {
               var8.printStackTrace();
            }
         }
      }

      var1 = appDeploymentPostprocessorForReload;
      var2 = var1.length;

      for(var3 = 0; var3 < var2; ++var3) {
         extensionName = var1[var3];
         extension = this.appCtx.getAppDeploymentExtension(extensionName);
         if (extension != null) {
            try {
               extension.unprepare(this.appCtx);
            } catch (DeploymentException var7) {
               var7.printStackTrace();
            }
         }
      }

   }

   void prepareAppDeploymentPreprocessorForReload() throws DeploymentException {
      String[] var1 = appDeploymentPreprocessorForReload;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String extensionName = var1[var3];
         AppDeploymentExtension extension = this.appCtx.getAppDeploymentExtension(extensionName);
         if (extension != null) {
            extension.prepare(this.appCtx);
         }
      }

   }

   void prepareAppDeploymentPostprocessorForReload() throws DeploymentException {
      String[] var1 = appDeploymentPostprocessorForReload;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String extensionName = var1[var3];
         AppDeploymentExtension extension = this.appCtx.getAppDeploymentExtension(extensionName);
         if (extension != null) {
            extension.prepare(this.appCtx);
         }
      }

   }

   void activateAppDeploymentPreprocessorForReload() throws DeploymentException {
      String[] var1 = appDeploymentPreprocessorForReload;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String extensionName = var1[var3];
         AppDeploymentExtension extension = this.appCtx.getAppDeploymentExtension(extensionName);
         if (extension != null) {
            extension.activate(this.appCtx);
         }
      }

   }

   void activateAppDeploymentPostprocessorForReload() throws DeploymentException {
      String[] var1 = appDeploymentPostprocessorForReload;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String extensionName = var1[var3];
         AppDeploymentExtension extension = this.appCtx.getAppDeploymentExtension(extensionName);
         if (extension != null) {
            extension.activate(this.appCtx);
         }
      }

   }

   private void reverseClassLoaderHirarchyIfNecessary() {
      if (this.webClassLoader instanceof ChangeAwareClassLoader) {
         ChangeAwareClassLoader c = (ChangeAwareClassLoader)this.webClassLoader;
         c.setChildFirst(this.configManager.isPreferWebInfClasses());
         WeblogicWebAppBean wlwab = this.getWlWebAppBean();
         if (wlwab != null) {
            ContainerDescriptorBean cd = (ContainerDescriptorBean)DescriptorUtils.getFirstChildOrDefaultBean(wlwab, wlwab.getContainerDescriptors(), "ContainerDescriptor");
            if (cd != null && cd.isPreferWebInfClasses() && this.appCtx != null && this.appCtx.getAppDeploymentMBean() != null) {
               try {
                  ClassFinder appFileOverrideFinder = AppFileOverrideUtils.getFinderIfRequired(this.appCtx.getAppDeploymentMBean(), this.appCtx.isEar() ? this.getModuleURI() : null);
                  if (appFileOverrideFinder != null) {
                     c.addClassFinderFirst(appFileOverrideFinder);
                  }
               } catch (ModuleException var5) {
               }
            }
         }
      }

   }

   void processAnnotations(boolean reload) throws DeploymentException {
      this.processAnnotations(reload, this.webappBean, this.wlWebAppBean);
   }

   void processAnnotations(boolean reload, WebAppBean webappBean, WeblogicWebAppBean wlWebAppBean) throws DeploymentException {
      if (!this.isInternalApp() && !this.isInternalSAMLApp() && !this.isInternalUtilitiesWebApp() && !this.isInternalUtilitiesWebSvcs() && !this.isInternalWSATApp()) {
         ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
         Thread.currentThread().setContextClassLoader(this.webClassLoader);
         AnnotationProcessingManager apm = new AnnotationProcessingManager(this.war, new WebAnnotationProcessor(), this.webFragmentManager);

         try {
            if (reload) {
               WebAppBean newBean = apm.processAnnotationsOnClone(this.webClassLoader, webappBean, wlWebAppBean);
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("------------------------------------------");
                  DescriptorUtils.writeAsXML((DescriptorBean)webappBean);
                  HTTPDebugLogger.debug("------------------------------------------");
                  DescriptorUtils.writeAsXML((DescriptorBean)newBean);
                  HTTPDebugLogger.debug("------------------------------------------");
               }

               DescriptorDiff diff = ((DescriptorBean)webappBean).getDescriptor().computeDiff(((DescriptorBean)newBean).getDescriptor());
               if (diff.size() > 0) {
                  HTTPLogger.logAnnotationsChanged(this.configManager.getDocRoot());
               }
            } else {
               ModuleContext mc = this.appCtx.getModuleContext(this.getId());
               if (mc != null) {
                  apm.beginRecording();
               }

               apm.processAnnotations(this.webClassLoader, webappBean, (WeblogicWebAppBean)wlWebAppBean);
               if (mc != null) {
                  mc.getRegistry().addAnnotationProcessedClasses(apm.endRecording());
               }
            }
         } catch (Exception var11) {
            Loggable l = HTTPLogger.logAnnotationProcessingFailedLoggable(this.configManager.getDocRoot(), var11.getMessage(), var11);
            l.log();
            throw new DeploymentException(l.getMessage(), var11);
         } finally {
            Thread.currentThread().setContextClassLoader(currentClassLoader);
         }

      }
   }

   public String getName() {
      return ApplicationVersionUtils.getNonVersionedName(this.getId());
   }

   private void createModuleException(String err, Exception ex) throws ModuleException {
      String message = err + PlatformConstants.EOL + (ex instanceof NestedException ? ex.toString() : ex.getMessage());
      throw new ModuleException(message, ex);
   }

   public String getAppDisplayName() {
      return ApplicationVersionUtils.getDisplayName(this.dmb);
   }

   public Collection getAllContexts() {
      return this.allContexts.values();
   }

   private String normalizeId(String id, String uri) {
      if (id != null && !id.trim().equals("") && !id.equals("/")) {
         if (id.startsWith("/")) {
            id = id.substring(1);
         }

         return id;
      } else {
         return uri;
      }
   }

   private void dump(DescriptorBean bean) {
      try {
         HTTPLogger.logDebug("dumping merged descriptor for " + this.moduleURI);
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         (new DescriptorManager()).writeDescriptorAsXML(bean.getDescriptor(), baos);
         (new DescriptorManager()).writeDescriptorAsXML(bean.getDescriptor(), System.out);
         String webDesc = baos.toString();
         HTTPLogger.logDebug("dumping merged descriptor for " + this.moduleURI);
         HTTPLogger.logDebug(webDesc);
      } catch (Exception var4) {
         System.out.println("unable to dump merged descriptor for " + this.moduleURI + var4.getMessage());
      }

   }

   public Map getDescriptorMappings() {
      if (this.wlWebAppBean == null) {
         return null;
      } else {
         LibraryRefBean[] libs = this.wlWebAppBean.getLibraryRefs();
         if (libs != null && libs.length != 0) {
            HashMap map = new HashMap(2);
            map.put("/WEB-INF/web.xml", (DescriptorBean)this.webappBean);
            if (this.wlWebAppBean != null) {
               map.put("/WEB-INF/weblogic.xml", (DescriptorBean)this.wlWebAppBean);
            }

            return map;
         } else {
            return null;
         }
      }
   }

   public void handleMergedFinder(ClassFinder mergedDescriptorFinder) {
      ClassFinder finder = this.war.getClassFinder();
      if (finder instanceof CompositeWebAppFinder) {
         CompositeWebAppFinder cwf = (CompositeWebAppFinder)finder;
         ClassFinder df = new DescriptorFinder(this.war.getURI(), mergedDescriptorFinder);
         cwf.addFinderFirst(df);
         cwf.addFinderFirst(mergedDescriptorFinder);
      }

   }

   public void loadFastSwapClassloader() throws ModuleException {
      if (this.createdClassLoader) {
         if (!registry.isProductionMode()) {
            if (this.configManager.isFastSwapEnabled()) {
               try {
                  this.webClassLoader = ClassRedefinerFactory.makeClassLoader(this.webClassLoader.getClassFinder(), this.webClassLoader.getParent());
                  this.webClassLoader.setAnnotation(new weblogic.utils.classloaders.Annotation(this.appCtx.getAppDeploymentMBean().getApplicationIdentifier(), this.normalizeId(this.getId(), this.moduleURI)));
               } catch (Exception var6) {
                  throw new ModuleException("Cannot initailize ClassRedefinerFactory", var6);
               }

               this.appClassLoaderManager.addModuleLoader(this.webClassLoader, this.getId());

               try {
                  RedefiningClassLoader rcl = (RedefiningClassLoader)this.webClassLoader;
                  ApplicationRuntimeMBeanImpl appRuntime = this.appCtx.getRuntime();
                  ClassRedefinitionRuntimeImpl rmb = (ClassRedefinitionRuntimeImpl)appRuntime.getClassRedefinitionRuntime();
                  if (rmb != null) {
                     rmb.registerClassLoader(rcl);
                  } else {
                     rcl.getRedefinitionRuntime().setRedefinitionTaskLimit(this.configManager.getFastSwapRedefinitionTaskLimit());
                     rmb = new ClassRedefinitionRuntimeImpl(appRuntime, this.webClassLoader);
                     appRuntime.setClassRedefinitionRuntime(rmb);
                  }
               } catch (ManagementException var4) {
                  throw new ModuleException(var4.getMessage(), var4);
               } catch (ClassCastException var5) {
                  throw new ModuleException(var5.getMessage(), var5);
               }
            }

         }
      }
   }

   boolean isInternalApp() {
      if (this.mbean == null) {
         return this.isInternalUtilitiesWebApp();
      } else {
         return this.isInternalUtilitiesWebApp() || this.mbean.getApplication().isInternalApp();
      }
   }

   boolean isInternalUtilitiesWebApp() {
      return "/bea_wls_internal".equals(this.contextPath);
   }

   boolean isInternalSAMLApp() {
      return registry.getSecurityProvider().isSamlApp(this.contextPath);
   }

   boolean isInternalWSATApp() {
      return "/wls-wsat".equals(this.contextPath);
   }

   boolean isInternalUtilitiesWebSvcs() {
      return this.contextPath.equals("/_async");
   }

   boolean isJsfApplication() {
      return this.isJsfApplication;
   }

   boolean isJSR375Application() {
      return this.isJSR375Application;
   }

   boolean forcedContainerInitializersLookup() {
      return this.forcedContainerInitializersLookup;
   }

   public void forceContainerInitializersLookup() {
      this.forcedContainerInitializersLookup = true;
   }

   WebAppConfigManager getWebAppConfigManager() {
      return this.configManager;
   }

   JSPManager getJspManager() {
      return this.jspManager;
   }

   SessionConfigManager getSessionConfigManager() {
      return this.sessionConfigManager;
   }

   WebFragmentManager getWebFragmentManager() {
      return this.webFragmentManager;
   }

   LibraryManager getLibraryManager() {
      return this.libraryManager;
   }

   public String getContextPath() {
      return this.contextPath;
   }

   public String getAltDD() {
      return this.altUri;
   }

   public void populateViewFinders(File baseDir, String viewAppName, boolean isArchived, VirtualJarFile moduleViewVjf, SplitDirectoryInfo viewSplitDirInfo, MultiClassFinder finder, MultiClassFinder resourceFinder) throws IOException, IllegalSpecVersionTypeException {
      CompositeWebAppFinder moduleClassFinder = new CompositeWebAppFinder();
      File moduleExtractDir = new File(baseDir, PathUtils.generateTempPath((String)null, viewAppName, this.getId()));
      ExplodedJar moduleWar = null;
      if (isArchived) {
         moduleWar = new ExplodedJar(this.getModuleURI(), moduleExtractDir, moduleViewVjf.getRootFiles()[0], War.WAR_CLASSPATH_INFO);
      } else {
         moduleWar = new ExplodedJar(this.getModuleURI(), moduleExtractDir, moduleViewVjf.getRootFiles(), War.WAR_CLASSPATH_INFO, JarCopyFilter.NOCOPY_FILTER);
      }

      moduleClassFinder.addFinder(moduleWar.getClassFinder());
      resourceFinder.addFinder(new ApplicationResourceFinder(this.getModuleURI(), moduleWar.getClassFinder()));
      if (viewSplitDirInfo != null) {
         String[] cp = viewSplitDirInfo.getWebAppClasses(this.getModuleURI());
         if (cp != null && cp.length > 0) {
            moduleClassFinder.addFinder(new ClasspathClassFinder2(StringUtils.join(cp, File.pathSeparator)));
         }
      }

      if (null != this.wlWebAppBean) {
         LibraryManager libraryManager = new LibraryManager(WebAppLibraryUtils.getLibraryReferencer(this.getModuleURI()), this.appCtx.getPartitionName());
         if (this.wlWebAppBean.getLibraryRefs() != null) {
            LibraryReference[] ref = LibraryReferenceFactory.getWebLibReference(this.wlWebAppBean.getLibraryRefs());
            if (ref != null) {
               libraryManager.lookup(ref);
            }
         }

         if (libraryManager.hasReferencedLibraries()) {
            War libraryWar = WebAppLibraryUtils.addWebAppLibraries(libraryManager, this.getModuleURI());
            moduleClassFinder.addLibraryFinder(libraryWar.getClassFinder());
            resourceFinder.addFinder(libraryWar.getResourceFinder("/"));
         }
      }

      finder.addFinder(moduleClassFinder);
   }

   public boolean isParallelEnabled() {
      return true;
   }

   public CdiDescriptorBean getCdiDescriptorBean() {
      return this.wlWebAppBean != null ? this.wlWebAppBean.getCdiDescriptor() : null;
   }

   public boolean isMetadataComplete() {
      return this.webappBean.isMetadataComplete();
   }

   public PreferApplicationPackagesBean getPreferApplicationPackages() {
      ContainerDescriptorBean cdb = this.getContainerDescriptorBean();
      return cdb != null ? cdb.getPreferApplicationPackages() : null;
   }

   public PreferApplicationResourcesBean getPreferApplicationResources() {
      ContainerDescriptorBean cdb = this.getContainerDescriptorBean();
      return cdb != null ? cdb.getPreferApplicationResources() : null;
   }

   public ClassLoadingBean getClassLoading() {
      ContainerDescriptorBean cdb = this.getContainerDescriptorBean();
      return cdb != null ? cdb.getClassLoading() : null;
   }

   private ContainerDescriptorBean getContainerDescriptorBean() {
      return this.wlWebAppBean != null && this.wlWebAppBean.getContainerDescriptors() != null && this.wlWebAppBean.getContainerDescriptors().length > 0 ? this.wlWebAppBean.getContainerDescriptors()[0] : null;
   }

   protected File getCacheDir() {
      if (this.appCtx != null) {
         ModuleContext mc = this.appCtx.getModuleContext(this.getId());
         if (mc != null) {
            return mc.getCacheDir();
         }
      }

      return null;
   }

   public void cleanEnvironmentEntries() {
      Iterator var1 = this.allContexts.values().iterator();

      while(var1.hasNext()) {
         WebAppServletContext webAppServletContext = (WebAppServletContext)var1.next();
         webAppServletContext.destroyCompEnv();
      }

   }

   static {
      String merge = System.getProperty("weblogic.http.descriptor.merge");
      if (merge != null && "false".equalsIgnoreCase(merge)) {
         mergeDescriptors = false;
      }

   }
}
