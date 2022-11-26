package weblogic.j2eeclient;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;
import javax.enterprise.deploy.shared.ModuleType;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.AppEnvSharingModule;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationFileManager;
import weblogic.application.ConcurrentModule;
import weblogic.application.Extensible;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleException;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.PojoAnnotationSupportingModule;
import weblogic.application.UpdateListener;
import weblogic.application.internal.BaseJ2EEModule;
import weblogic.application.metadatacache.Cache;
import weblogic.application.naming.BindingsFactory;
import weblogic.application.naming.EnvUtils;
import weblogic.application.naming.Environment;
import weblogic.application.naming.EnvironmentException;
import weblogic.application.naming.PersistenceUnitRegistry;
import weblogic.application.naming.PersistenceUnitRegistryProvider;
import weblogic.application.utils.AppFileOverrideUtils;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.application.utils.annotation.ClassInfoFinderFactory;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.ApplicationClientBean;
import weblogic.j2ee.descriptor.wl.CdiDescriptorBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.MessageDestinationDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationClientBean;
import weblogic.jndi.internal.ApplicationNamingNode;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.jars.VirtualJarFile;

public final class AppClientModule extends BaseJ2EEModule implements ConcurrentModule, AppEnvSharingModule, Extensible, PojoAnnotationSupportingModule {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");
   private final String uri;
   private MultiClassFinder classFinder;
   private String mainClass = null;
   private GenericClassLoader classLoader = null;
   private ApplicationClientBean dd = null;
   private WeblogicApplicationClientBean wldd = null;
   private AppClientComponentRuntimeMBeanImpl rtmb;
   private AppClientPersistenceUnitRegistry persistenceUnitRegistry = null;
   private Environment envBuilder = null;
   private PersistenceUnitRegistryProvider purProvider = null;
   private ModuleExtensionContext extCtx;
   private ClassInfoFinder classInfoFinder;

   public AppClientModule(String uri) {
      this.uri = uri;
   }

   public String getId() {
      return this.uri;
   }

   public String getType() {
      return WebLogicModuleType.MODULETYPE_CAR;
   }

   public weblogic.descriptor.Descriptor getStandardDescriptor() {
      return this.dd != null ? ((DescriptorBean)this.dd).getDescriptor() : null;
   }

   public ModuleExtensionContext getModuleExtensionContext() {
      return this.extCtx;
   }

   public DescriptorBean[] getDescriptors() {
      return this.wldd == null ? new DescriptorBean[]{(DescriptorBean)this.dd} : new DescriptorBean[]{(DescriptorBean)this.dd, (DescriptorBean)this.wldd};
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return new ComponentRuntimeMBean[]{this.rtmb};
   }

   public void initUsingLoader(ApplicationContext ac, GenericClassLoader gcl, UpdateListener.Registration reg) throws ModuleException {
      this.init(ac, gcl, false);
   }

   public GenericClassLoader init(ApplicationContext appCtx, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.init(appCtx, parent, true);
      return this.classLoader;
   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
      try {
         if (this.rtmb != null) {
            this.rtmb.unregister();
         }

      } catch (ManagementException var3) {
         throw new ModuleException(var3);
      }
   }

   public void prepare() throws ModuleException {
      try {
         if (null != this.envBuilder) {
            this.envBuilder.contributeClientEnvEntries(this.dd, this.wldd);
            this.envBuilder.validateEnvEntries(this.classLoader);
            this.registerMessageDestinations();
         }

      } catch (Exception var2) {
         throw new ModuleException(var2);
      }
   }

   private void registerMessageDestinations() throws EnvironmentException {
      MessageDestinationDescriptorBean[] descs = this.wldd == null ? new MessageDestinationDescriptorBean[0] : this.wldd.getMessageDestinationDescriptors();
      EnvUtils.registerMessageDestinations(this.dd.getMessageDestinations(), descs, this.envBuilder.getApplicationName(), this.envBuilder.getModuleId());
   }

   public void activate() throws ModuleException {
      try {
         if (null != this.envBuilder) {
            this.envBuilder.bindEnvEntriesFromDDs(this.classLoader, this.purProvider, (ServletContext)null);
         }

      } catch (Exception var2) {
         throw new ModuleException(var2);
      }
   }

   public void deactivate() throws ModuleException {
      try {
         if (null != this.envBuilder) {
            this.envBuilder.unbindEnvEntries();
         }

      } catch (Exception var2) {
         throw new ModuleException(var2);
      }
   }

   private void initNamingEnvironment(ApplicationContext ac) throws ModuleException {
      ApplicationContextInternal appCtx = (ApplicationContextInternal)ac;
      String appName = appCtx.getApplicationId();
      Context appRootContext = appCtx.getRootContext();

      Context clientRootContext;
      try {
         clientRootContext = (new ApplicationNamingNode()).getContext(appRootContext.getEnvironment());
      } catch (NamingException var8) {
         throw new AssertionError(var8);
      }

      try {
         this.envBuilder = BindingsFactory.getInstance().createClientEnvironment(clientRootContext, appName, this.uri, debugLogger, appRootContext);
         this.purProvider = new PersistenceUnitRegistryProvider() {
            public PersistenceUnitRegistry getPersistenceUnitRegistry() {
               return AppClientModule.this.persistenceUnitRegistry;
            }
         };
      } catch (NamingException var7) {
         throw new ModuleException(var7);
      }
   }

   public void start() {
   }

   public void unprepare() {
      if (null != this.envBuilder) {
         this.unregisterMessageDestinations();
         this.envBuilder.destroy();
      }

   }

   private void unregisterMessageDestinations() {
      MessageDestinationDescriptorBean[] descs = this.wldd == null ? new MessageDestinationDescriptorBean[0] : this.wldd.getMessageDestinationDescriptors();
      EnvUtils.unregisterMessageDestinations(this.dd.getMessageDestinations(), descs, this.envBuilder.getApplicationName(), this.envBuilder.getModuleId());
   }

   public void remove() {
   }

   public void adminToProduction() {
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) {
   }

   public void forceProductionToAdmin() {
   }

   public boolean isParallelEnabled() {
      return true;
   }

   private void init(ApplicationContext appCtx, GenericClassLoader parent, boolean createChild) throws ModuleException {
      this.initRuntimeMBean(appCtx);
      this.mainClass = this.findMainClass(appCtx);
      this.setupClassFinder(appCtx);
      this.setupClassLoader(appCtx, parent, createChild);
      if (null == this.mainClass) {
         AppClientLogger.logNoMainClassDefined4JavaModule(this.uri);
      } else {
         this.loadDescriptors(appCtx);
         this.initPersistenceUnitRegistry(appCtx);
         this.initNamingEnvironment(appCtx);
         this.extCtx = new ModuleExtensionContextImpl((ApplicationContextInternal)appCtx, ((ApplicationContextInternal)appCtx).getModuleContext(this.getId()), this, this.classLoader, this.classFinder, this.envBuilder, this.getClassInfoFinder((ApplicationContextInternal)appCtx));
      }
   }

   private ClassInfoFinder getClassInfoFinder(ApplicationContextInternal appCtx) {
      if (this.classInfoFinder == null) {
         try {
            ModuleContext mc = appCtx.getModuleContext(this.getId());
            this.classInfoFinder = ClassInfoFinderFactory.FACTORY.newInstance(ClassInfoFinderFactory.FACTORY.createParams(this.classFinder).setModuleType(ModuleType.CAR).enableCaching(Cache.AppMetadataCache, mc.getVirtualJarFile(), mc.getCacheDir()));
         } catch (AnnotationProcessingException var3) {
            var3.printStackTrace();
         }
      }

      return this.classInfoFinder;
   }

   private void setupClassLoader(ApplicationContext ac, GenericClassLoader parent, boolean shouldCreateChild) {
      if (shouldCreateChild) {
         this.classLoader = new GenericClassLoader(this.classFinder, parent);
         this.classLoader.setAnnotation(new Annotation(ac.getAppDeploymentMBean().getApplicationIdentifier(), this.getId()));
      } else {
         parent.addClassFinder(this.classFinder);
         this.classLoader = parent;
      }

   }

   private void loadDescriptors(ApplicationContext ac) throws ModuleException {
      File altDD = this.resolveAltDD((ApplicationContextInternal)ac, this.uri);
      DeploymentPlanBean plan = this.getDeploymentPlan(ac);
      File config = this.getLocalPlanDir(ac);
      VirtualJarFile vjf = null;

      try {
         ApplicationClientDescriptor parser;
         if (altDD != null) {
            parser = new ApplicationClientDescriptor(altDD, vjf, config, plan, this.uri);
         } else {
            vjf = this.getClientVirtualJarFile(ac);
            parser = new ApplicationClientDescriptor(vjf, config, plan, this.uri);
         }

         this.dd = ApplicationClientUtils.getAnnotationProcessedDescriptor(this.classLoader, parser, this.classLoader.loadClass(this.mainClass));
         this.wldd = parser.getWeblogicApplicationClientBean();
      } catch (Exception var11) {
         throw new ModuleException(var11);
      } finally {
         this.closeClientVirtualJarFile(vjf);
      }

   }

   private void initPersistenceUnitRegistry(ApplicationContext ac) throws ModuleException {
      DeploymentPlanBean plan = this.getDeploymentPlan(ac);
      File config = this.getLocalPlanDir(ac);
      File clientJarFile = this.getClientJarFile(ac);

      try {
         this.persistenceUnitRegistry = new AppClientPersistenceUnitRegistry(clientJarFile, this.classLoader, this.uri, config, plan);
      } catch (Exception var6) {
         throw new ModuleException(var6);
      }
   }

   private DeploymentPlanBean getDeploymentPlan(ApplicationContext ac) {
      AppDeploymentMBean deploy = ac.getAppDeploymentMBean();
      return deploy.getDeploymentPlanDescriptor();
   }

   private File getLocalPlanDir(ApplicationContext ac) {
      AppDeploymentMBean deploy = ac.getAppDeploymentMBean();
      return deploy.getPlanDir() != null ? new File(deploy.getLocalPlanDir()) : null;
   }

   private void setupClassFinder(ApplicationContext ac) throws ModuleException {
      this.classFinder = new MultiClassFinder();
      AppDeploymentMBean deploy = ac.getAppDeploymentMBean();
      ClassFinder overrideFinder = AppFileOverrideUtils.getFinderIfRequired(deploy, this.uri);
      if (overrideFinder != null) {
         this.classFinder.addFinderFirst(overrideFinder);
      }

      File clientJar = this.getClientJarFile(ac);

      try {
         this.classFinder.addFinder(new JarClassFinder(clientJar));
      } catch (IOException var6) {
         throw new ModuleException(var6);
      }
   }

   private File getClientJarFile(ApplicationContext ac) throws ModuleException {
      VirtualJarFile vjf = null;

      File var3;
      try {
         vjf = this.getClientVirtualJarFile(ac);
         var3 = vjf.getRootFiles()[0];
      } catch (IOException var7) {
         throw new ModuleException(var7);
      } finally {
         this.closeClientVirtualJarFile(vjf);
      }

      return var3;
   }

   private String findMainClass(ApplicationContext appCtx) throws ModuleException {
      VirtualJarFile vjf = null;

      String var4;
      try {
         vjf = this.getClientVirtualJarFile(appCtx);
         Manifest manifest = vjf.getManifest();
         if (manifest == null) {
            var4 = null;
            return var4;
         }

         var4 = (String)manifest.getMainAttributes().get(Name.MAIN_CLASS);
      } catch (IOException var8) {
         throw new ModuleException(var8);
      } finally {
         this.closeClientVirtualJarFile(vjf);
      }

      return var4;
   }

   private VirtualJarFile getClientVirtualJarFile(ApplicationContext appCtx) throws IOException {
      ApplicationFileManager fileManager = ((ApplicationContextInternal)appCtx).getApplicationFileManager();
      return fileManager.getVirtualJarFile(this.uri);
   }

   private void closeClientVirtualJarFile(VirtualJarFile vjf) {
      if (vjf != null) {
         try {
            vjf.close();
         } catch (IOException var3) {
         }
      }

   }

   private void initRuntimeMBean(ApplicationContext ac) throws ModuleException {
      ApplicationContextInternal appCtx = (ApplicationContextInternal)ac;
      String n = ManagementService.getRuntimeAccess(kernelId).getServerName() + "_" + appCtx.getApplicationId() + "_" + this.uri;

      try {
         this.rtmb = new AppClientComponentRuntimeMBeanImpl(n, this.uri, appCtx.getRuntime());
      } catch (ManagementException var5) {
         throw new ModuleException(var5);
      }
   }

   public boolean needsAppEnvContextCopy() {
      return this.mainClass != null;
   }

   protected String getMainClass() {
      return this.mainClass;
   }

   public CdiDescriptorBean getCdiDescriptorBean() {
      return this.wldd != null ? this.wldd.getCdiDescriptor() : null;
   }

   public boolean isMetadataComplete() {
      return this.dd == null ? false : this.dd.isMetadataComplete();
   }
}
