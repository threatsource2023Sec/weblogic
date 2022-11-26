package weblogic.application.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.zip.ZipEntry;
import javax.enterprise.deploy.shared.ModuleType;
import javax.management.MBeanServer;
import javax.naming.Context;
import javax.security.auth.login.LoginException;
import javax.security.jacc.PolicyConfiguration;
import javax.xml.stream.XMLStreamException;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.AppDeploymentExtension;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.ApplicationFileManager;
import weblogic.application.ApplicationLifecycleListener;
import weblogic.application.DescriptorUpdater;
import weblogic.application.Module;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleException;
import weblogic.application.ModuleManager;
import weblogic.application.PermissionsDescriptorLoader;
import weblogic.application.PojoAnnotationSupportingModule;
import weblogic.application.Registry;
import weblogic.application.RegistryImpl;
import weblogic.application.SecurityRole;
import weblogic.application.SplitDirectoryInfo;
import weblogic.application.UpdateListener;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.internal.library.LibraryManagerAggregate;
import weblogic.application.io.AA;
import weblogic.application.io.Ear;
import weblogic.application.library.LibraryContext;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryProvider;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.application.naming.Environment;
import weblogic.application.utils.AppSupportDeclarations;
import weblogic.application.utils.ApplicationRuntimeStateManager;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.application.utils.ClassLoaderUtils;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.LibraryUtils;
import weblogic.application.utils.ManagementUtils;
import weblogic.application.utils.PathUtils;
import weblogic.application.utils.XMLWriter;
import weblogic.application.utils.annotation.AnnotationMappingsImpl;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.PermissionsBean;
import weblogic.j2ee.descriptor.wl.CdiDescriptorBean;
import weblogic.j2ee.descriptor.wl.ClassLoadingBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.PojoEnvironmentBean;
import weblogic.j2ee.descriptor.wl.PreferApplicationPackagesBean;
import weblogic.j2ee.descriptor.wl.PreferApplicationResourcesBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.CdiContainerMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.LibraryRuntimeMBean;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.PlatformConstants;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.AugmentableClassLoaderManager;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.work.WorkManagerCollection;
import weblogic.work.concurrent.runtime.ConcurrentManagedObjectCollection;

public class ApplicationContextImpl implements ApplicationContextInternal, DescriptorUpdater, LibraryContext, FlowContext, UpdateListener.Registration {
   private AppDeploymentMBean duMBean;
   private final SystemResourceMBean srMBean;
   private final String appId;
   private Context rootContext;
   private Context envContext;
   private GenericClassLoader appClassLoader;
   private ApplicationLifecycleListener[] listeners;
   private ApplicationBean appDD;
   private WeblogicApplicationBean wlappDD;
   private WeblogicExtensionBean extDD;
   private final ModuleManager moduleManager;
   private Module[] stoppingModules;
   private boolean isStoppedModuleIdsComputed;
   private String[] stoppedModuleIds;
   private File[] paths;
   private File descriptorCacheDir;
   private String securityRealmName;
   private ApplicationRuntimeMBeanImpl appRuntimeMBean;
   private ApplicationFileManager afm;
   private Map ejbCacheMap;
   private Map ejbQueryCacheMap;
   private Map applicationParameters;
   private Map factoryMap;
   private WorkManagerCollection workManagerCollection;
   private ConcurrentManagedObjectCollection concurrentManagedObjectCollection;
   private Ear ear;
   private final LibraryManagerAggregate libAggr;
   private final List updateListeners;
   private ApplicationDescriptor appDesc;
   private final Collection policyConfigurations;
   private SplitDirectoryInfo splitInfo;
   private final boolean isInternalApp;
   private DomainMBean proposedDomain;
   private AuthenticatedSubject deploymentInitiator;
   private Map appRoleMappings;
   private Map appListenerIdentityMappings;
   private boolean isStaticDeployment;
   private boolean isAdminState;
   private boolean isAdminModeSpecified;
   private boolean isSpecifiedTargetsOnly;
   private boolean requiresRestart;
   private boolean isSplitDir;
   private int deploymentOperation;
   private AppDDHolder appDDHolder;
   private Map clToSchemaTypeLoader;
   private Map contextRootOverrideMap;
   private Map userObjects;
   private String[] partialRedeployURIs;
   private final Registry applicationRegistry;
   private final Map appExtensions;
   private final Set appPreProcessorExtensions;
   private final Set appPostProcessorExtensions;
   private ApplicationVersionLifecycleNotifier versionLifecycleNotifier;
   private boolean isActive;
   private ApplicationArchive application;
   private Map moduleURItoIdMap;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static String serverName = null;
   private boolean appEnvContextCopy;
   private AnnotationMappingsImpl annotationMappings;
   private Boolean annotationProcessingComplete;
   private Object annotationProcessingLock;
   private final ComponentInvocationContext invocationContext;
   private final String partitionName;
   private final String applicationIdWithoutPartition;
   private boolean shareabilityEnabled;
   private MultiClassFinder allAppFindersFromLibraries;
   private MultiClassFinder appInstanceFindersFromLibraries;
   private MultiClassFinder sharedAppFindersFromLibraries;
   private boolean wasSharedAppClassLoaderCreated;
   private Environment environment;
   private PojoEnvironmentBean bean;
   private PermissionsDescriptorLoader permissionsDescriptor;
   private Class[] annotationsOfInterest;
   private boolean annotationsOfInterestGathered;
   private List libraryClassInfoFinders;
   private String classpathForAppAnnotationScanning;
   private File cacheDir;
   private File generatedOutputDir;
   private boolean wlDirectoriesSet;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final ComponentInvocationContext NULL_CIC = ComponentInvocationContextManager.getInstance().createComponentInvocationContext((String)null);
   private static final ApplicationContextInternal.SecurityProvider securityProvider = new WLSSecurityProvider();
   private ModuleAttributes emptyAttributes;
   private File applicationRootFile;
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");

   ApplicationContextImpl(String appName) {
      this((AppDeploymentMBean)null, (SystemResourceMBean)null, appName, (File)((File)null), false);
   }

   ApplicationContextImpl(AppDeploymentMBean duMBean, ApplicationArchive app) {
      this(duMBean, (SystemResourceMBean)null, duMBean.getApplicationIdentifier(), (ApplicationArchive)app, duMBean.isInternalApp());
   }

   ApplicationContextImpl(SystemResourceMBean srMBean, ApplicationArchive app) {
      this((AppDeploymentMBean)null, srMBean, srMBean.getName(), (ApplicationArchive)app, false);
   }

   ApplicationContextImpl(AppDeploymentMBean duMBean, ApplicationArchive applicationRootFile, ClassLoader loader) {
      this(duMBean, (SystemResourceMBean)null, duMBean.getApplicationIdentifier(), (ApplicationArchive)applicationRootFile, duMBean.isInternalApp(), true, loader);
   }

   public ApplicationContextImpl(String appId, ApplicationArchive app, ClassLoader parentClassLoader) {
      this((AppDeploymentMBean)null, (SystemResourceMBean)null, appId, (ApplicationArchive)app, false, false, parentClassLoader);
   }

   private ApplicationContextImpl(AppDeploymentMBean duMBean, SystemResourceMBean srMBean, String appId, ApplicationArchive app, boolean isInternalApp) {
      this(duMBean, srMBean, appId, (ApplicationArchive)app, isInternalApp, true, (ClassLoader)null);
   }

   private ApplicationContextImpl(AppDeploymentMBean duMBean, SystemResourceMBean srMBean, String appId, ApplicationArchive app, boolean isInternalApp, boolean initWorkManager, ClassLoader appParentClassLoader) {
      this.listeners = new ApplicationLifecycleListener[0];
      this.moduleManager = new ModuleManager();
      this.stoppingModules = new Module[0];
      this.isStoppedModuleIdsComputed = false;
      this.stoppedModuleIds = null;
      this.paths = new File[0];
      this.ejbCacheMap = Collections.EMPTY_MAP;
      this.ejbQueryCacheMap = Collections.EMPTY_MAP;
      this.applicationParameters = Collections.EMPTY_MAP;
      this.factoryMap = Collections.EMPTY_MAP;
      this.libAggr = new LibraryManagerAggregate();
      this.updateListeners = new ArrayList();
      this.appDesc = null;
      this.policyConfigurations = new ArrayList();
      this.appRoleMappings = null;
      this.appListenerIdentityMappings = null;
      this.isStaticDeployment = false;
      this.isAdminState = false;
      this.isAdminModeSpecified = false;
      this.isSpecifiedTargetsOnly = false;
      this.requiresRestart = false;
      this.isSplitDir = false;
      this.appDDHolder = null;
      this.clToSchemaTypeLoader = Collections.synchronizedMap(new WeakHashMap());
      this.userObjects = Collections.EMPTY_MAP;
      this.applicationRegistry = new RegistryImpl();
      this.isActive = false;
      this.appEnvContextCopy = false;
      this.annotationProcessingComplete = false;
      this.annotationProcessingLock = new Object();
      this.shareabilityEnabled = false;
      this.allAppFindersFromLibraries = new MultiClassFinder();
      this.appInstanceFindersFromLibraries = new MultiClassFinder();
      this.sharedAppFindersFromLibraries = new MultiClassFinder();
      this.wasSharedAppClassLoaderCreated = false;
      this.annotationsOfInterest = null;
      this.annotationsOfInterestGathered = false;
      this.libraryClassInfoFinders = null;
      this.classpathForAppAnnotationScanning = null;
      this.cacheDir = null;
      this.generatedOutputDir = null;
      this.wlDirectoriesSet = false;
      this.emptyAttributes = new ModuleAttributes((Module)null, (ApplicationContextInternal)null) {
         public boolean isConcurrent() {
            return false;
         }
      };
      this.applicationRootFile = null;
      this.duMBean = duMBean;
      this.srMBean = srMBean;
      this.appId = appId;
      this.application = app;
      this.isInternalApp = isInternalApp;
      if (initWorkManager) {
         this.workManagerCollection = new WorkManagerCollection(appId, isInternalApp);
         this.concurrentManagedObjectCollection = new ConcurrentManagedObjectCollection(appId);
      }

      if (this.duMBean != null) {
         this.partitionName = ApplicationVersionUtils.getPartitionName(this.duMBean.getName());
         this.applicationIdWithoutPartition = ApplicationVersionUtils.getNonPartitionName(this.duMBean.getName());
         this.invocationContext = this.createInvocationContext(this.duMBean.getName(), this.partitionName);
      } else if (this.srMBean != null) {
         this.partitionName = ApplicationVersionUtils.getPartitionName(this.srMBean.getName());
         this.applicationIdWithoutPartition = ApplicationVersionUtils.getNonPartitionName(this.srMBean.getName());
         this.invocationContext = this.createInvocationContext(this.srMBean.getName(), this.partitionName);
      } else if (this.appId != null) {
         this.partitionName = ApplicationVersionUtils.getPartitionName(this.appId);
         this.applicationIdWithoutPartition = ApplicationVersionUtils.getNonPartitionName(this.appId);
         this.invocationContext = this.createInvocationContext(this.appId, this.partitionName);
      } else {
         this.partitionName = null;
         this.applicationIdWithoutPartition = null;
         this.invocationContext = NULL_CIC;
      }

      this.libAggr.setPartitionName(this.partitionName);
      if (appParentClassLoader == null) {
         if (this.partitionName == null) {
            appParentClassLoader = AugmentableClassLoaderManager.getAugmentableSystemClassLoader();
         } else {
            appParentClassLoader = ClassLoaders.instance.getOrCreatePartitionClassLoader(this.partitionName);
         }
      }

      this.appClassLoader = new GenericClassLoader(new MultiClassFinder(), (ClassLoader)appParentClassLoader);
      this.appClassLoader.setAnnotation(new Annotation(appId));
      this.appExtensions = new HashMap();
      this.appPreProcessorExtensions = new HashSet();
      this.appPostProcessorExtensions = new HashSet();
      this.securityRealmName = SecurityServiceManager.getRealmName(this.partitionName != null && !"DOMAIN".equals(this.partitionName) ? this.partitionName : null, this.getProposedDomain());
   }

   public ApplicationMBean getApplicationMBean() {
      return this.duMBean == null ? null : this.duMBean.getAppMBean();
   }

   public BasicDeploymentMBean getBasicDeploymentMBean() {
      return (BasicDeploymentMBean)(this.duMBean != null ? this.duMBean : this.srMBean);
   }

   public AppDeploymentMBean getAppDeploymentMBean() {
      return this.duMBean;
   }

   public void setUpdatedAppDeploymentMBean(AppDeploymentMBean appMBean) {
      if (this.duMBean != null && appMBean != null) {
         this.duMBean = appMBean;
      }

   }

   public SystemResourceMBean getSystemResourceMBean() {
      return this.srMBean;
   }

   public DomainMBean getProposedDomain() {
      return this.proposedDomain;
   }

   public void setProposedDomain(DomainMBean proposedDomain) {
      this.proposedDomain = proposedDomain;
      if (proposedDomain != null && this.securityRealmName == null && this.partitionName != null && !this.partitionName.equals("DOMAIN")) {
         this.securityRealmName = SecurityServiceManager.getRealmName(this.partitionName, this.getProposedDomain());
      }

   }

   public DomainMBean getEffectiveDomain() {
      return this.proposedDomain != null ? this.proposedDomain : ManagementService.getRuntimeAccess(KERNEL_ID).getDomain();
   }

   public AuthenticatedSubject getDeploymentInitiator() {
      return this.deploymentInitiator;
   }

   public void setDeploymentInitiator(AuthenticatedSubject initiator) {
      this.deploymentInitiator = initiator;
   }

   public boolean requiresRestart() {
      return this.requiresRestart;
   }

   public void setRequiresRestart(boolean restart) {
      this.requiresRestart = restart;
   }

   public Context getEnvContext() {
      return this.envContext;
   }

   public void setEnvContext(Context envContext) {
      this.envContext = envContext;
   }

   public Context getRootContext() {
      return this.rootContext;
   }

   public void setRootContext(Context rootContext) {
      this.rootContext = rootContext;
   }

   public GenericClassLoader getAppClassLoader() {
      return this.appClassLoader;
   }

   public void resetAppClassLoader(GenericClassLoader loader) {
      this.appClassLoader = loader;
      Thread.currentThread().setContextClassLoader(this.appClassLoader);
   }

   public ApplicationLifecycleListener[] getApplicationListeners() {
      return this.listeners;
   }

   public void setApplicationListeners(ApplicationLifecycleListener[] listeners) {
      if (listeners != null && listeners.length != 0) {
         if (this.listeners.length == 0) {
            this.listeners = listeners;
         } else {
            this.addListeners(listeners);
         }

      }
   }

   public void addApplicationListener(ApplicationLifecycleListener listener) {
      this.setApplicationListeners(new ApplicationLifecycleListener[]{listener});
   }

   private void addListeners(ApplicationLifecycleListener[] newListeners) {
      List l = new ArrayList(this.listeners.length + newListeners.length);
      l.addAll(Arrays.asList(this.listeners));
      l.addAll(Arrays.asList(newListeners));
      this.listeners = (ApplicationLifecycleListener[])((ApplicationLifecycleListener[])l.toArray(new ApplicationLifecycleListener[l.size()]));
   }

   public ApplicationVersionLifecycleNotifier getApplicationVersionNotifier() {
      return this.versionLifecycleNotifier;
   }

   public void setApplicationVersionNotifier(ApplicationVersionLifecycleNotifier notifier) {
      this.versionLifecycleNotifier = notifier;
   }

   public void setApplicationDescriptor(ApplicationDescriptor appDesc) throws IOException, XMLStreamException {
      this.appDesc = appDesc;
      this.appDD = appDesc.getApplicationDescriptor();
      this.wlappDD = appDesc.getWeblogicApplicationDescriptor();
      this.extDD = appDesc.getWeblogicExtensionDescriptor();
   }

   public ApplicationDescriptor getApplicationDescriptor() {
      return this.appDesc;
   }

   public WorkManagerCollection getWorkManagerCollection() {
      return this.workManagerCollection;
   }

   public ConcurrentManagedObjectCollection getConcurrentManagedObjectCollection() {
      return this.concurrentManagedObjectCollection;
   }

   public ApplicationBean getApplicationDD() {
      return this.appDD;
   }

   public WeblogicApplicationBean getWLApplicationDD() {
      return this.wlappDD;
   }

   public WeblogicExtensionBean getWLExtensionDD() {
      return this.extDD;
   }

   public void setEar(Ear ear) {
      this.ear = ear;
   }

   public Ear getEar() {
      return this.ear;
   }

   public boolean isEar() {
      return this.ear != null;
   }

   public File[] getApplicationPaths() {
      return this.paths;
   }

   public void setApplicationPaths(File[] paths) {
      this.paths = paths;
   }

   public String getApplicationFileName() {
      if (!this.hasApplicationArchive()) {
         return this.applicationRootFile == null ? null : this.applicationRootFile.getName();
      } else if (this.application != null) {
         String name = this.application.getName();
         int index = name.indexOf(File.pathSeparator);
         return index < 0 ? name : name.substring(index + 1);
      } else {
         return null;
      }
   }

   public String getStagingPath() {
      return !this.hasApplicationArchive() ? this.applicationRootFile.getPath() : this.application.getName();
   }

   public String getOutputPath() {
      if (this.afm == null) {
         throw new AssertionError("getOutputPath called too early!");
      } else {
         return this.afm.getOutputPath().getPath();
      }
   }

   public String getApplicationSecurityRealmName() {
      return this.securityRealmName;
   }

   public void setApplicationSecurityRealmName(String securityRealmName) {
      this.securityRealmName = securityRealmName;
   }

   public String getApplicationName() {
      return ApplicationVersionUtils.getApplicationName(this.appId);
   }

   public String getApplicationId() {
      return this.appId;
   }

   public ApplicationRuntimeMBeanImpl getRuntime() {
      return this.appRuntimeMBean;
   }

   public void setRuntime(ApplicationRuntimeMBeanImpl appRuntimeMBean) {
      this.appRuntimeMBean = appRuntimeMBean;
   }

   public ApplicationFileManager getApplicationFileManager() {
      return this.afm;
   }

   public void setApplicationFileManager(ApplicationFileManager afm) {
      this.afm = afm;
   }

   public SplitDirectoryInfo getSplitDirectoryInfo() {
      return this.splitInfo;
   }

   public void setSplitDirectoryInfo(SplitDirectoryInfo splitInfo) {
      this.splitInfo = splitInfo;
   }

   public Map getEJBCacheMap() {
      return this.ejbCacheMap;
   }

   public void setEJBCacheMap(Map ejbCacheMap) {
      if (ejbCacheMap == null) {
         this.ejbCacheMap = Collections.EMPTY_MAP;
      } else {
         this.ejbCacheMap = ejbCacheMap;
      }

   }

   public Map getEJBQueryCacheMap() {
      return this.ejbQueryCacheMap;
   }

   public void setEJBQueryCacheMap(Map ejbQueryCacheMap) {
      if (ejbQueryCacheMap == null) {
         this.ejbQueryCacheMap = Collections.EMPTY_MAP;
      } else {
         this.ejbQueryCacheMap = ejbQueryCacheMap;
      }

   }

   public Map getApplicationParameters() {
      return this.applicationParameters;
   }

   public void setApplicationParameters(Map applicationParameters) {
      if (applicationParameters == null) {
         this.applicationParameters = Collections.EMPTY_MAP;
      } else {
         this.applicationParameters = applicationParameters;
      }

   }

   public String getApplicationParameter(String key) {
      return (String)this.applicationParameters.get(key);
   }

   public InputStream getElement(String path) throws IOException {
      if (!this.hasApplicationArchive()) {
         VirtualJarFile vjf = this.afm.getVirtualJarFile();
         ZipEntry ze = vjf.getEntry(path);
         return ze == null ? null : vjf.getInputStream(ze);
      } else {
         return this.application.getEntry(path).getInputStream();
      }
   }

   public ModuleManager getModuleManager() {
      return this.moduleManager;
   }

   public Module[] getApplicationModules() {
      return this.moduleManager.getModules();
   }

   public Module[] findModules(String... types) {
      return (Module[])this.moduleManager.findModulesWithTypes(types).toArray(new Module[0]);
   }

   public void setApplicationModules(Module[] modules) {
      this.moduleManager.setModules(modules, this);
   }

   public Module[] getStartingModules() {
      return this.getModuleManager().getAdditionalModules();
   }

   public void setStartingModules(Module[] modules) {
      this.getModuleManager().setAdditionalModules(modules, this);
   }

   public void mergeStartingModules() {
      this.getModuleManager().merge();
   }

   public Module[] getStoppingModules() {
      return this.stoppingModules;
   }

   public void setStoppingModules(Module[] modules) {
      this.stoppingModules = modules;
   }

   public Map getCustomModuleFactories() {
      return this.factoryMap;
   }

   public void setCustomModuleFactories(Map factoryMap) {
      this.factoryMap = factoryMap;
   }

   public void addUpdateListener(UpdateListener ul) {
      this.updateListeners.add(ul);
   }

   public void removeUpdateListener(UpdateListener ul) {
      this.updateListeners.remove(ul);
   }

   public List getUpdateListeners() {
      return this.updateListeners;
   }

   public void addLibraryManager(String moduleId, LibraryManager mgr) {
      this.libAggr.addLibraryManager(moduleId, mgr);
   }

   public void removeLibraryManager(String moduleId, LibraryManager mgr) {
      this.libAggr.removeLibraryManager(moduleId, mgr);
   }

   public LibraryManagerAggregate getLibraryManagerAggregate() {
      return this.libAggr;
   }

   public LibraryProvider getLibraryProvider(String moduleId) {
      return this.libAggr.getLibraryProvider(moduleId);
   }

   public void addJACCPolicyConfiguration(PolicyConfiguration pc) {
      this.policyConfigurations.add(pc);
   }

   public PolicyConfiguration[] getJACCPolicyConfigurations() {
      return (PolicyConfiguration[])((PolicyConfiguration[])this.policyConfigurations.toArray(new PolicyConfiguration[this.policyConfigurations.size()]));
   }

   public void addClassFinder(ClassFinder in) {
      this.allAppFindersFromLibraries.addFinder(in);
   }

   public void addInstanceAppLibClassFinder(ClassFinder finder) {
      this.appInstanceFindersFromLibraries.addFinder(finder);
   }

   public void addSharedAppLibClassFinder(ClassFinder finder) {
      this.sharedAppFindersFromLibraries.addFinder(finder);
   }

   public void registerLink(File f) throws IOException {
      this.registerLink(f.getName(), f);
   }

   public void registerLink(String uri, File f) throws IOException {
      EarUtils.linkURI(this.getEar(), this.getApplicationFileManager(), uri, f);
   }

   public void notifyDescriptorUpdate() throws LoggableLibraryProcessingException {
      LibraryUtils.resetAppDDs(this.appDesc, this);
   }

   public String getRefappName() {
      return this.getApplicationName();
   }

   public DeploymentPlanBean findDeploymentPlan() {
      if (this.srMBean != null) {
         return null;
      } else {
         AppDeploymentMBean appMBean;
         if (this.proposedDomain == null) {
            appMBean = this.duMBean;
         } else {
            appMBean = this.proposedDomain.lookupAppDeployment(this.getApplicationId());
         }

         return appMBean != null ? appMBean.getDeploymentPlanDescriptor() : null;
      }
   }

   public void writeDiagnosticImage(XMLWriter writer) {
      writer.addElement("name", this.getApplicationId());
      writer.addElement("internal", String.valueOf(this.isInternalApp));
      writer.addElement("paths");
      File[] paths = this.getApplicationPaths();

      for(int i = 0; i < paths.length; ++i) {
         writer.addElement("path", paths[i].getAbsolutePath());
      }

      writer.closeElement();
      writer.addElement("classpath", this.getAppClassLoader().getClassPath());
      writer.addElement("modules");
      Module[] modules = this.getApplicationModules();

      for(int i = 0; i < modules.length; ++i) {
         writer.addElement("module");
         writer.addElement("name", modules[i].getId());
         writer.addElement("type", modules[i].getType());
         writer.closeElement();
      }

      writer.closeElement();
      writer.addElement("libraries");
      this.libAggr.writeDiagnosticImage(writer);
      writer.closeElement();
   }

   public void setAppLevelRoleMappings(Map m) {
      if (this.appRoleMappings != null) {
         throw new AssertionError("Application Role mappings cannot be reset");
      } else {
         this.appRoleMappings = m;
      }
   }

   public void setAppListenerIdentityMappings(Map m) {
      if (this.appListenerIdentityMappings != null) {
         throw new AssertionError("ApplicationLifecycleListener to run-as principal mappings cannot be reset");
      } else {
         this.appListenerIdentityMappings = m;
      }
   }

   public String getAppListenerIdentity(ApplicationLifecycleListener listener) {
      return this.appListenerIdentityMappings == null ? null : (String)this.appListenerIdentityMappings.get(listener);
   }

   public SecurityRole getSecurityRole(String roleName) {
      return this.appRoleMappings == null ? null : (SecurityRole)this.appRoleMappings.get(roleName);
   }

   public File getDescriptorCacheDir() {
      return this.descriptorCacheDir;
   }

   public void setDescriptorCacheDir(File descriptorCacheDir) {
      this.descriptorCacheDir = descriptorCacheDir;
   }

   public boolean isActive() {
      return this.isActive;
   }

   public void setIsActive(boolean newIsActive) {
      this.isActive = newIsActive;
   }

   public boolean isStaticDeploymentOperation() {
      return this.isStaticDeployment;
   }

   public void setStaticDeploymentOperation(boolean isStaticDeployment) {
      this.isStaticDeployment = isStaticDeployment;
   }

   public void setAdminState(boolean b) {
      this.isAdminState = b;
   }

   public boolean isAdminState() {
      return this.isAdminState;
   }

   public void setAdminModeSpecified(boolean flag) {
      this.isAdminModeSpecified = flag;
   }

   public boolean isAdminModeSpecified() {
      return this.isAdminModeSpecified;
   }

   public boolean isInternalApp() {
      return this.isInternalApp;
   }

   public void setSplitDir() {
      this.isSplitDir = true;
   }

   public boolean isSplitDir() {
      return this.isSplitDir;
   }

   public boolean isRedeployOperation() {
      return this.deploymentOperation == 9;
   }

   public boolean isStopOperation() {
      return this.deploymentOperation == 8;
   }

   public int getDeploymentOperation() {
      return this.deploymentOperation;
   }

   public void setDeploymentOperation(int deploymentOperation) {
      this.deploymentOperation = deploymentOperation;
   }

   public void setAdditionalModuleUris(Map additionalModuleUris) {
      this.moduleManager.setAdditionalModuleUris(additionalModuleUris);
   }

   public void setPartialRedeployURIs(String[] uris) {
      this.partialRedeployURIs = uris;
   }

   public String[] getPartialRedeployURIs() {
      return this.partialRedeployURIs;
   }

   public AppDDHolder getProposedPartialRedeployDDs() {
      return this.appDDHolder;
   }

   public void setProposedPartialRedeployDDs(AppDDHolder appDDHolder) {
      this.appDDHolder = appDDHolder;
   }

   public Object getSchemaTypeLoader(ClassLoader cl) {
      return this.clToSchemaTypeLoader.get(cl);
   }

   public void setSchemaTypeLoader(ClassLoader cl, Object o) {
      this.clToSchemaTypeLoader.put(cl, o);
   }

   public void clear() {
      this.clToSchemaTypeLoader.clear();
   }

   public String getRefappUri() {
      return this.getEar().getURI();
   }

   public Map getContextRootOverrideMap() {
      return this.contextRootOverrideMap;
   }

   public void setContextRootOverrideMap(Map map) {
      this.contextRootOverrideMap = map;
   }

   public SubDeploymentMBean[] getLibrarySubDeployments() {
      return (new LibrarySubDeploymentFetcher()).getSubDeploymentMBeans();
   }

   public Object putUserObject(Object key, Object value) {
      if (this.userObjects == Collections.EMPTY_MAP) {
         this.userObjects = new HashMap();
      }

      return this.userObjects.put(key, value);
   }

   public Object getUserObject(Object key) {
      return this.userObjects.get(key);
   }

   public Object removeUserObject(Object key) {
      return this.userObjects.remove(key);
   }

   public String toString() {
      return this.appId;
   }

   public void addAppDeploymentExtension(AppDeploymentExtension ext, FlowContext.ExtensionType type) {
      this.appExtensions.put(ext.getName(), ext);
      if (type == FlowContext.ExtensionType.PRE) {
         this.appPreProcessorExtensions.add(ext);
      } else {
         this.appPostProcessorExtensions.add(ext);
      }

   }

   public Set getAppDeploymentExtensions(FlowContext.ExtensionType type) {
      return type == FlowContext.ExtensionType.PRE ? this.appPreProcessorExtensions : this.appPostProcessorExtensions;
   }

   public AppDeploymentExtension getAppDeploymentExtension(String extensionName) {
      return (AppDeploymentExtension)this.appExtensions.get(extensionName);
   }

   public void clearAppDeploymentExtensions() {
      this.appExtensions.clear();
      this.appPreProcessorExtensions.clear();
      this.appPostProcessorExtensions.clear();
   }

   public ModuleAttributes getModuleAttributes(String moduleId) {
      ModuleAttributes attribs = this.moduleManager.getAttributes(moduleId);
      return attribs == null ? this.emptyAttributes : attribs;
   }

   public void reset() throws IOException {
      this.duMBean = null;
      this.rootContext = null;
      this.envContext = null;
      this.appClassLoader = null;
      this.listeners = null;
      this.appDD = null;
      this.wlappDD = null;
      this.extDD = null;
      this.stoppingModules = null;
      this.paths = null;
      this.descriptorCacheDir = null;
      this.securityRealmName = null;
      this.appRuntimeMBean = null;
      this.afm = null;
      this.ejbCacheMap.clear();
      this.ejbQueryCacheMap.clear();
      this.applicationParameters.clear();
      this.factoryMap.clear();
      this.clearIfNotNull((Collection)this.workManagerCollection);
      if (this.concurrentManagedObjectCollection != null) {
         this.concurrentManagedObjectCollection.clear();
      }

      this.ear = null;
      this.updateListeners.clear();
      this.appDesc = null;
      this.policyConfigurations.clear();
      this.splitInfo = null;
      this.proposedDomain = null;
      this.deploymentInitiator = null;
      this.appRoleMappings = null;
      this.clearIfNotNull(this.appListenerIdentityMappings);
      this.appDDHolder = null;
      this.clToSchemaTypeLoader.clear();
      this.clearIfNotNull(this.contextRootOverrideMap);
      this.userObjects.clear();
      this.partialRedeployURIs = null;
      this.clearIfNotNull(this.appExtensions);
      this.clearIfNotNull((Collection)this.appPreProcessorExtensions);
      this.clearIfNotNull((Collection)this.appPostProcessorExtensions);
      this.appExtensions.clear();
      this.appPreProcessorExtensions.clear();
      this.appPostProcessorExtensions.clear();
      this.versionLifecycleNotifier = null;
      this.moduleManager.reset();
      if (this.application != null) {
         this.application.close();
      }

      this.applicationRegistry.clear();
   }

   private void clearIfNotNull(Map m) {
      if (m != null) {
         m.clear();
      }

   }

   private void clearIfNotNull(Collection c) {
      if (c != null) {
         c.clear();
      }

   }

   public ModuleContext getModuleContext(String id) {
      return this.getModuleAttributes(id).getModuleContext();
   }

   public Registry getApplicationRegistry() {
      return this.applicationRegistry;
   }

   private void processAnnotationMappings() throws AnnotationProcessingException {
      this.annotationMappings.loadAnnotatedClasses(this.findAnnotationsOfInterest());
   }

   private Class[] findAnnotationsOfInterest() {
      if (!this.annotationsOfInterestGathered) {
         this.annotationsOfInterestGathered = true;
         Module[] modules = this.getApplicationModules();
         Set allAnnotations = null;
         Set processedModuleTypes = new HashSet();
         if (modules != null) {
            Module[] var4 = modules;
            int var5 = modules.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Module module = var4[var6];
               ModuleType moduleType = WebLogicModuleType.getTypeFromString(module.getType());
               if (!processedModuleTypes.contains(moduleType)) {
                  processedModuleTypes.add(moduleType);
                  Class[] annotations = AppSupportDeclarations.instance.getAnnotations(moduleType);
                  if (annotations != null && annotations.length > 0) {
                     if (allAnnotations == null) {
                        allAnnotations = new HashSet();
                     }

                     allAnnotations.addAll(Arrays.asList(annotations));
                  }
               }
            }
         }

         if (allAnnotations != null) {
            this.annotationsOfInterest = (Class[])allAnnotations.toArray(new Class[0]);
         }
      }

      return this.annotationsOfInterest;
   }

   public Set getAnnotatedClasses(Class... annos) throws AnnotationProcessingException {
      if (this.isEar()) {
         if (!this.annotationProcessingComplete) {
            synchronized(this.annotationProcessingLock) {
               if (!this.annotationProcessingComplete) {
                  this.processAnnotationMappings();
                  this.annotationProcessingComplete = true;
               }
            }
         }

         return this.annotationMappings.getAnnotatedClasses(annos);
      } else {
         return Collections.emptySet();
      }
   }

   public ApplicationArchive getApplicationArchive() {
      return this.application;
   }

   ApplicationContextImpl(AppDeploymentMBean duMBean, File applicationRootFile) {
      this(duMBean, (SystemResourceMBean)null, duMBean.getApplicationIdentifier(), (File)applicationRootFile, duMBean.isInternalApp());
   }

   ApplicationContextImpl(SystemResourceMBean srMBean, File applicationRootFile) {
      this((AppDeploymentMBean)null, srMBean, srMBean.getName(), (File)applicationRootFile, false);
   }

   public ApplicationContextImpl(String appId, File applicationRootFile, ClassLoader parentClassLoader) {
      this((AppDeploymentMBean)null, (SystemResourceMBean)null, appId, (File)applicationRootFile, false, false, parentClassLoader);
   }

   private ApplicationContextImpl(AppDeploymentMBean duMBean, SystemResourceMBean srMBean, String appId, File applicationRootFile, boolean isInternalApp) {
      this(duMBean, srMBean, appId, (File)applicationRootFile, isInternalApp, true, (ClassLoader)null);
   }

   private ApplicationContextImpl(AppDeploymentMBean duMBean, SystemResourceMBean srMBean, String appId, File applicationRootFile, boolean isInternalApp, boolean initWorkManager, ClassLoader appParentClassLoader) {
      this.listeners = new ApplicationLifecycleListener[0];
      this.moduleManager = new ModuleManager();
      this.stoppingModules = new Module[0];
      this.isStoppedModuleIdsComputed = false;
      this.stoppedModuleIds = null;
      this.paths = new File[0];
      this.ejbCacheMap = Collections.EMPTY_MAP;
      this.ejbQueryCacheMap = Collections.EMPTY_MAP;
      this.applicationParameters = Collections.EMPTY_MAP;
      this.factoryMap = Collections.EMPTY_MAP;
      this.libAggr = new LibraryManagerAggregate();
      this.updateListeners = new ArrayList();
      this.appDesc = null;
      this.policyConfigurations = new ArrayList();
      this.appRoleMappings = null;
      this.appListenerIdentityMappings = null;
      this.isStaticDeployment = false;
      this.isAdminState = false;
      this.isAdminModeSpecified = false;
      this.isSpecifiedTargetsOnly = false;
      this.requiresRestart = false;
      this.isSplitDir = false;
      this.appDDHolder = null;
      this.clToSchemaTypeLoader = Collections.synchronizedMap(new WeakHashMap());
      this.userObjects = Collections.EMPTY_MAP;
      this.applicationRegistry = new RegistryImpl();
      this.isActive = false;
      this.appEnvContextCopy = false;
      this.annotationProcessingComplete = false;
      this.annotationProcessingLock = new Object();
      this.shareabilityEnabled = false;
      this.allAppFindersFromLibraries = new MultiClassFinder();
      this.appInstanceFindersFromLibraries = new MultiClassFinder();
      this.sharedAppFindersFromLibraries = new MultiClassFinder();
      this.wasSharedAppClassLoaderCreated = false;
      this.annotationsOfInterest = null;
      this.annotationsOfInterestGathered = false;
      this.libraryClassInfoFinders = null;
      this.classpathForAppAnnotationScanning = null;
      this.cacheDir = null;
      this.generatedOutputDir = null;
      this.wlDirectoriesSet = false;
      this.emptyAttributes = new ModuleAttributes((Module)null, (ApplicationContextInternal)null) {
         public boolean isConcurrent() {
            return false;
         }
      };
      this.applicationRootFile = null;
      this.duMBean = duMBean;
      this.srMBean = srMBean;
      this.appId = appId;
      this.applicationRootFile = applicationRootFile;
      this.isInternalApp = isInternalApp;
      if (initWorkManager) {
         this.workManagerCollection = new WorkManagerCollection(appId, isInternalApp);
         this.concurrentManagedObjectCollection = new ConcurrentManagedObjectCollection(appId);
      }

      if (this.duMBean != null) {
         this.partitionName = ApplicationVersionUtils.getPartitionName(this.duMBean.getName());
         this.applicationIdWithoutPartition = ApplicationVersionUtils.getNonPartitionName(this.duMBean.getName());
         this.invocationContext = this.createInvocationContext(this.duMBean.getName(), this.partitionName);
      } else if (this.srMBean != null) {
         this.partitionName = ApplicationVersionUtils.getPartitionName(this.srMBean.getName());
         this.applicationIdWithoutPartition = ApplicationVersionUtils.getNonPartitionName(this.srMBean.getName());
         this.invocationContext = this.createInvocationContext(this.srMBean.getName(), this.partitionName);
      } else if (this.appId != null) {
         this.partitionName = ApplicationVersionUtils.getPartitionName(this.appId);
         this.applicationIdWithoutPartition = ApplicationVersionUtils.getNonPartitionName(this.appId);
         this.invocationContext = this.createInvocationContext(this.appId, this.partitionName);
      } else {
         this.partitionName = null;
         this.applicationIdWithoutPartition = null;
         this.invocationContext = NULL_CIC;
      }

      this.libAggr.setPartitionName(this.partitionName);
      if (appParentClassLoader == null) {
         if (this.partitionName == null) {
            appParentClassLoader = AugmentableClassLoaderManager.getAugmentableSystemClassLoader();
         } else {
            appParentClassLoader = ClassLoaders.instance.getOrCreatePartitionClassLoader(this.partitionName);
         }
      }

      this.appClassLoader = new GenericClassLoader(new MultiClassFinder(), (ClassLoader)appParentClassLoader);
      this.appClassLoader.setAnnotation(new Annotation(appId));
      this.appExtensions = new HashMap();
      this.appPreProcessorExtensions = new HashSet();
      this.appPostProcessorExtensions = new HashSet();
      this.securityRealmName = SecurityServiceManager.getRealmName(this.partitionName != null && !"DOMAIN".equals(this.partitionName) ? this.partitionName : null, this.getProposedDomain());
   }

   public void setupApplicationFileManager(File dir) throws IOException {
      ApplicationFileManager afm;
      if (!this.hasApplicationArchive()) {
         afm = ApplicationFileManager.newInstance(dir);
      } else {
         afm = ApplicationFileManager.newInstance(this.getApplicationArchive());
      }

      this.setApplicationFileManager(afm);
   }

   public boolean hasApplicationArchive() {
      assert this.application != null == AA.isOn : "Gating error, application archive: " + this.application;

      return this.application != null;
   }

   public Map getModuleURItoIdMap() {
      return this.moduleURItoIdMap;
   }

   public void setModuleURItoIdMap(Map m) {
      this.moduleURItoIdMap = m;
   }

   public String[] getStoppedModules() {
      if (!this.isStoppedModuleIdsComputed) {
         if (this.isStaticDeployment) {
            String serverName = ManagementService.getRuntimeAccess(KERNEL_ID).getServerName();
            this.stoppedModuleIds = ApplicationContextImpl.ApplicationRuntimeStateInitializer.appRTStateMgr.getStoppedModuleIds(this.appId, serverName);
         }

         this.isStoppedModuleIdsComputed = true;
      }

      return this.stoppedModuleIds;
   }

   public boolean needsAppEnvContextCopy() {
      return this.appEnvContextCopy;
   }

   public void setAppEnvContextCopy() {
      this.appEnvContextCopy = true;
   }

   public void setAnnotationMappings(AnnotationMappingsImpl annotationMappings) {
      this.annotationMappings = annotationMappings;
   }

   public Collection getModuleExtensions(String id) {
      return this.getModuleAttributes(id).getExtensions();
   }

   public ComponentInvocationContext getInvocationContext() {
      return this.invocationContext;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   private ComponentInvocationContext createInvocationContext(String appId, String partitionName) {
      return ComponentInvocationContextManager.getInstance().createComponentInvocationContext(partitionName, ApplicationVersionUtils.getApplicationName(appId), ApplicationVersionUtils.getVersionId(appId), (String)null, (String)null);
   }

   public ClassInfoFinder getClassInfoFinder() {
      return this.annotationMappings != null ? this.annotationMappings.getClassInfoFinder(this.findAnnotationsOfInterest()) : null;
   }

   public GenericClassLoader getPartitionClassLoader() {
      return ClassLoaders.instance.getPartitionClassLoader(this.partitionName);
   }

   public String getPartialApplicationId(boolean includePartitionName) {
      return includePartitionName ? this.appId : this.applicationIdWithoutPartition;
   }

   public void markShareability() {
      this.shareabilityEnabled = true;
   }

   public boolean checkShareability() {
      return this.shareabilityEnabled;
   }

   public void createdSharedAppClassLoader() {
      this.wasSharedAppClassLoaderCreated = true;
   }

   public boolean wasSharedAppClassLoaderCreated() {
      return this.wasSharedAppClassLoaderCreated;
   }

   public MultiClassFinder getAllAppFindersFromLibraries() {
      return this.allAppFindersFromLibraries;
   }

   public MultiClassFinder getInstanceAppClassFindersFromLibraries() {
      return this.appInstanceFindersFromLibraries;
   }

   public MultiClassFinder getSharedAppClassFindersFromLibraries() {
      return this.sharedAppFindersFromLibraries;
   }

   public Environment getEnvironment() {
      return this.environment;
   }

   public void setEnvironment(Environment env) {
      this.environment = env;
   }

   public boolean isPojoAnnotationEnabled() {
      return this.getCdiPolicy().equals("Disabled") ? false : Controls.pojoannotationprocessing.enabled;
   }

   public ResourceGroupMBean getResourceGroupMBean() {
      return ManagementUtils.getResourceGroupMBean((BasicDeploymentMBean)(this.duMBean != null ? this.duMBean : this.srMBean));
   }

   public boolean isDeployedThroughResourceGroupTemplate() {
      return ManagementUtils.isDeployedThroughResourceGroupTemplate((BasicDeploymentMBean)(this.duMBean != null ? this.duMBean : this.srMBean));
   }

   public PojoEnvironmentBean getPojoEnvironmentBean() {
      return this.bean;
   }

   public void setPojoEnvironmentBean(PojoEnvironmentBean bean) {
      this.bean = bean;
   }

   public void setPermissionsDescriptor(PermissionsDescriptorLoader permissionsDescriptor) {
      this.permissionsDescriptor = permissionsDescriptor;
   }

   public PermissionsBean getPermissionsBean() throws IOException, XMLStreamException {
      return this.permissionsDescriptor != null ? (PermissionsBean)this.permissionsDescriptor.loadDescriptorBean() : null;
   }

   public void freeDeploymentMemory() {
      if (debugLogger.isDebugEnabled()) {
         String msg = this.annotationMappings == null ? "No annotation scanning data to remove" : "Removing annotation scanning data";
         debugLogger.debug(msg + " for application " + this.getApplicationName());
      }

      if (this.annotationMappings != null) {
         this.annotationMappings.freeClassInfoFinder();
      }

   }

   public void addLibraryClassInfoFinderFirst(ClassInfoFinder classInfoFinder) {
      if (this.libraryClassInfoFinders == null) {
         this.libraryClassInfoFinders = new ArrayList(1);
      }

      this.libraryClassInfoFinders.add(0, classInfoFinder);
   }

   public List getLibraryClassInfoFinders() {
      return this.libraryClassInfoFinders;
   }

   public void addAppAnnotationScanningClassPathFirst(String classpath) {
      if (classpath != null && classpath.length() > 0) {
         if (this.classpathForAppAnnotationScanning == null) {
            this.classpathForAppAnnotationScanning = classpath;
         } else {
            this.classpathForAppAnnotationScanning = classpath + PlatformConstants.PATH_SEP + this.classpathForAppAnnotationScanning;
         }
      }

   }

   public String getAppAnnotationScanningClassPath() {
      return this.classpathForAppAnnotationScanning;
   }

   private void setupWLDirectories() {
      if (!this.wlDirectoriesSet) {
         ApplicationFileManager afm = this.getApplicationFileManager();
         if (afm == null) {
            throw new IllegalStateException("Application File Manager not setup yet, cannot setup WL Internal directories");
         }

         this.cacheDir = new File(afm.getOutputPath(), "META-INF/.WL_internal/cache/ear/");
         this.generatedOutputDir = new File(afm.getOutputPath(), "META-INF/.WL_internal/generated/ear/");
         this.wlDirectoriesSet = true;
      }

   }

   public File getGeneratedOutputDir() {
      this.setupWLDirectories();
      return this.generatedOutputDir;
   }

   public File getCacheDir() {
      this.setupWLDirectories();
      return this.cacheDir;
   }

   public ApplicationContextInternal.SecurityProvider getSecurityProvider() {
      SecurityServiceManager.checkKernelPermission();
      return securityProvider;
   }

   public CdiDescriptorBean getCdiDescriptorBean() {
      if (this.isEar()) {
         WeblogicApplicationBean wlDD = this.getWLApplicationDD();
         if (wlDD != null) {
            return wlDD.getCdiDescriptor();
         }
      } else {
         Module[] var7 = this.getApplicationModules();
         int var2 = var7.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Module m = var7[var3];
            ModuleAttributes attributes = this.getModuleAttributes(m.getId());
            PojoAnnotationSupportingModule pModule = (PojoAnnotationSupportingModule)attributes.getUnwrappedModule(PojoAnnotationSupportingModule.class);
            if (pModule != null) {
               return pModule.getCdiDescriptorBean();
            }
         }
      }

      return null;
   }

   public String getCdiPolicy() {
      CdiDescriptorBean cdiDescriptorBean = this.getCdiDescriptorBean();
      if (cdiDescriptorBean != null && cdiDescriptorBean.isPolicySet()) {
         return cdiDescriptorBean.getPolicy();
      } else {
         CdiContainerMBean cdiContainerMBean = this.getEffectiveDomain().getCdiContainer();
         if (cdiContainerMBean != null && cdiContainerMBean.isPolicySet()) {
            return cdiContainerMBean.getPolicy();
         } else if (cdiDescriptorBean != null) {
            return cdiDescriptorBean.getPolicy();
         } else {
            return cdiContainerMBean != null ? cdiContainerMBean.getPolicy() : "Enabled";
         }
      }
   }

   public boolean isSpecifiedTargetsOnly() {
      return this.isSpecifiedTargetsOnly;
   }

   public void setSpecifiedTargetsOnly(boolean sto) {
      this.isSpecifiedTargetsOnly = sto;
   }

   public boolean isDebugEnabled() {
      return debugLogger.isDebugEnabled();
   }

   public void debug(String message) {
      String filterAppId = (String)debugLogger.getDebugParameters().get("a");
      if (filterAppId == null || filterAppId.equals(this.appId)) {
         debugLogger.debug('[' + this.appId + ']' + ' ' + message);
      }

   }

   public void debug(String message, Throwable t) {
      String filterAppId = (String)debugLogger.getDebugParameters().get("a");
      if (filterAppId == null || filterAppId.equals(this.appId)) {
         debugLogger.debug('[' + this.appId + ']' + ' ' + message, t);
      }

   }

   public PreferApplicationPackagesBean getPreferApplicationPackages() {
      return this.wlappDD != null ? this.wlappDD.getPreferApplicationPackages() : null;
   }

   public PreferApplicationResourcesBean getPreferApplicationResources() {
      return this.wlappDD != null ? this.wlappDD.getPreferApplicationResources() : null;
   }

   public ClassLoadingBean getClassLoading() {
      return this.wlappDD != null ? this.wlappDD.getClassLoading() : null;
   }

   private static synchronized String getCurrentServerName() {
      if (serverName == null) {
         serverName = ManagementService.getRuntimeAccess(kernelId).getServerName();
      }

      return serverName;
   }

   public String[] getInternalSourceDirectories() {
      File extractDir = PathUtils.getAppTempDir(getCurrentServerName(), ApplicationVersionUtils.replaceDelimiter(this.getApplicationId(), '_'));
      return new String[]{extractDir.getParentFile().getAbsolutePath()};
   }

   public void possiblyFixAppRuntimeState(Module[] modules) throws DeploymentException {
      String[] moduleIds = new String[modules.length];

      for(int i = 0; i < modules.length; ++i) {
         moduleIds[i] = modules[i].getId();
      }

      try {
         String serverName = ManagementService.getRuntimeAccess(KERNEL_ID).getServerName();
         ApplicationContextImpl.ApplicationRuntimeStateInitializer.appRTStateMgr.possiblyFixAppRuntimeState(this.getAppDeploymentMBean(), serverName, moduleIds);
      } catch (ManagementException var4) {
         throw new DeploymentException(var4);
      }
   }

   public String getAppClassLoaderClassPath() {
      return ClassLoaderUtils.getClassLoaderClassPath(this.getAppClassLoader());
   }

   public void reloadConfiguration() throws ModuleException {
      SystemResourceMBean sysRes = this.getSystemResourceMBean();
      if (sysRes != null) {
         String uri = sysRes.getDescriptorFileName();
         if (uri != null) {
            Iterator it = this.getUpdateListeners().iterator();

            while(it.hasNext()) {
               UpdateListener ul = (UpdateListener)it.next();
               if (ul.acceptURI(uri)) {
                  ul.prepareUpdate(uri);
                  ul.activateUpdate(uri);
               }
            }

         }
      }
   }

   private static class WLSSecurityProvider implements ApplicationContextInternal.SecurityProvider {
      private static final AuthenticatedSubject kernelIdentity = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

      private WLSSecurityProvider() {
      }

      private AuthenticatedSubject getSubject(String realmName, String identity) throws LoginException {
         PrincipalAuthenticator pa = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(kernelIdentity, realmName != null ? realmName : "weblogicDEFAULT", ServiceType.AUTHENTICATION);
         return pa.impersonateIdentity(identity);
      }

      public boolean isIdentityValid(String realmName, String identity) {
         try {
            this.getSubject(realmName, identity);
            return true;
         } catch (LoginException var4) {
            return false;
         }
      }

      public boolean isIdentityAdministrator(String realmName, String identity) {
         try {
            return SubjectUtils.isUserAnAdministrator(this.getSubject(realmName, identity));
         } catch (LoginException var4) {
            return false;
         }
      }

      public boolean isUserAnAdministrator(AuthenticatedSubject subject) {
         return SubjectUtils.isUserAnAdministrator(subject);
      }

      public boolean isUserAnonymous(AuthenticatedSubject subject) {
         return SubjectUtils.isUserAnonymous(subject);
      }

      public boolean isAdminPrivilegeEscalation(AuthenticatedSubject currentSubject, String principalName, String realmName) {
         return SubjectUtils.isAdminPrivilegeEscalation(currentSubject, principalName, realmName);
      }

      public Object invokePrivilegedAction(String realmName, String identity, PrivilegedAction action) throws LoginException {
         PrincipalAuthenticator pa = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(kernelIdentity, realmName != null ? realmName : "weblogicDEFAULT", ServiceType.AUTHENTICATION);
         AuthenticatedSubject userIdentity = pa.impersonateIdentity(identity);
         return this.invokePrivilegedAction(userIdentity, action);
      }

      public Object invokePrivilegedAction(AuthenticatedSubject identity, PrivilegedAction action) {
         return SecurityServiceManager.runAsForUserCode(kernelIdentity, identity, action);
      }

      public Object invokePrivilegedAction(AuthenticatedSubject identity, PrivilegedExceptionAction action) throws PrivilegedActionException {
         return SecurityServiceManager.runAs(kernelIdentity, identity, action);
      }

      public Object invokePrivilegedActionAsAnonymous(PrivilegedExceptionAction action) throws PrivilegedActionException {
         return SecurityServiceManager.runAsForUserCode(kernelIdentity, SubjectUtils.getAnonymousSubject(), action);
      }

      public Object invokePrivilegedActionAsCurrent(PrivilegedAction action) {
         return this.invokePrivilegedAction(SecurityServiceManager.getCurrentSubject(kernelIdentity), action);
      }

      public boolean isJACCEnabled() {
         return SecurityServiceManager.isJACCEnabled();
      }

      public boolean isWLSRuntimeAccessInitialized() {
         return ManagementService.isRuntimeAccessInitialized();
      }

      public RuntimeAccess getWLSRuntimeAccess() {
         return ManagementService.getRuntimeAccess(kernelIdentity);
      }

      public MBeanServer getRuntimeMBeanServer() {
         return ManagementService.getRuntimeMBeanServer(kernelIdentity);
      }

      public MBeanServer getWLSDomainRuntimeMBeanServer() {
         return ManagementService.getDomainRuntimeMBeanServer(kernelIdentity);
      }

      public MBeanServer getWLSEditMBeanServer() {
         return ManagementService.getEditMBeanServer(kernelIdentity);
      }

      // $FF: synthetic method
      WLSSecurityProvider(Object x0) {
         this();
      }
   }

   private static class ApplicationRuntimeStateInitializer {
      private static final ApplicationRuntimeStateManager appRTStateMgr = (ApplicationRuntimeStateManager)GlobalServiceLocator.getServiceLocator().getService(ApplicationRuntimeStateManager.class, new java.lang.annotation.Annotation[0]);
   }

   private class LibrarySubDeploymentFetcher {
      private LibrarySubDeploymentFetcher() {
      }

      SubDeploymentMBean[] getSubDeploymentMBeans() {
         LibraryRuntimeMBean[] runtimes = ApplicationContextImpl.this.getRuntime().getLibraryRuntimes();
         if (runtimes != null && runtimes.length != 0) {
            DescriptorBean db = (DescriptorBean)ApplicationContextImpl.this.getBasicDeploymentMBean().getParent();
            DomainMBean domain = (DomainMBean)db.getDescriptor().getRootBean();
            List libSubDeployments = new ArrayList();

            for(int i = 0; i < runtimes.length; ++i) {
               String name = runtimes[i].getLibraryIdentifier();
               LibraryMBean app = domain.lookupLibrary(name);
               if (app != null) {
                  SubDeploymentMBean[] subs = app.getSubDeployments();
                  if (subs != null && subs.length > 0) {
                     libSubDeployments.addAll(Arrays.asList(subs));
                  }
               }
            }

            return (SubDeploymentMBean[])((SubDeploymentMBean[])libSubDeployments.toArray(new SubDeploymentMBean[libSubDeployments.size()]));
         } else {
            return null;
         }
      }

      // $FF: synthetic method
      LibrarySubDeploymentFetcher(Object x1) {
         this();
      }
   }
}
