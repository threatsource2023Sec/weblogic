package weblogic.connector.deploy;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.xml.stream.XMLStreamException;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationFileManager;
import weblogic.application.DeployableObjectInfo;
import weblogic.application.Extensible;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.SplitDirectoryInfo;
import weblogic.application.UpdateListener;
import weblogic.application.internal.BaseJ2EEModule;
import weblogic.application.library.IllegalSpecVersionTypeException;
import weblogic.application.utils.AppFileOverrideUtils;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.common.LinkrefManager;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.common.Utils;
import weblogic.connector.configuration.AdditionalAnnotatedClassesProvider;
import weblogic.connector.configuration.DDUtil;
import weblogic.connector.exception.RAConfigurationException;
import weblogic.connector.exception.RAException;
import weblogic.connector.external.AdapterListener;
import weblogic.connector.external.ConnectorUtils;
import weblogic.connector.external.RAComplianceException;
import weblogic.connector.external.RAInfo;
import weblogic.connector.external.RAValidationInfo;
import weblogic.connector.external.impl.RAInfoImpl;
import weblogic.connector.lifecycle.BootstrapContext;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.PermissionsBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ConnectorComponentMBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.work.ShutdownCallback;

public final class ConnectorModule extends BaseJ2EEModule implements Extensible, Module, UpdateListener, DeployableObjectInfo {
   private final String uri;
   private RAInfo raInfo;
   private RAInfo newRAInfo;
   private ConnectorModuleChangePackage pendingChanges = null;
   String componentName = null;
   String componentURI = null;
   private ApplicationContextInternal appCtx;
   private ApplicationFileManager appFileManager;
   private AdditionalAnnotatedClassesProvider aacProvider;
   private boolean isEmbededInEar;
   private GenericClassLoader classLoader;
   private List classFinders;
   private boolean classFindersClosed;
   private boolean usingSubClassLoader;
   private String altDD = null;
   private RAInstanceManager raIM;
   private String moduleName;
   private String appId;
   private Vector vectSubContexts = new Vector();
   private RarArchive explodedRar;
   private AuthenticatedSubject kernelId;
   private ConnectorModuleExtensionContext moduleExtensionContext;

   private ConnectorModule(String uri, boolean isEmbededInEar) {
      this.uri = uri;
      this.isEmbededInEar = isEmbededInEar;
      this.classFinders = new ArrayList(2);
   }

   public static ConnectorModule createStandaloneConnectorModule(String uri) {
      Debug.deployment("Creating ConnectorModule Standalone with uri = " + uri);
      ConnectorModule module = new ConnectorModule(uri, false);
      return module;
   }

   public static ConnectorModule createEmbededConnectorModule(String uri) {
      Debug.deployment("Creating ConnectorModule Embeded inside EAR with uri = " + uri);
      ConnectorModule module = new ConnectorModule(uri, true);
      return module;
   }

   public final void prepare() throws ModuleException {
      boolean hadErrors = false;
      this.debugModule("is in NEW state. Calling prepare.");
      Utils.startManagement();

      try {
         String activeVersionId = ApplicationUtils.getActiveVersionId(this.appCtx.getApplicationName());
         this.raIM = new RAInstanceManager(this.raInfo, this.classLoader, this.componentName, this.uri, this.explodedRar, this.appCtx, this.uri, activeVersionId, this.getKernelId(), this.classFinders);
         this.moduleExtensionContext.setRAInstanceManager(this.raIM);
         this.raIM.prepare();
         this.debugModule("has been PREPARED.");
      } catch (RAException var8) {
         hadErrors = true;
         this.debugModule(" failed be PREPARED due to error " + var8, var8);
         throw new ModuleException(var8.toString(), var8);
      } catch (RAComplianceException var9) {
         hadErrors = true;
         this.debugModule(" failed be PREPARED due to error " + var9, var9);
         throw new ModuleException(var9.toString(), var9);
      } catch (Throwable var10) {
         hadErrors = true;
         this.debugModule(" failed be PREPARED due to error " + var10, var10);
         throw new ModuleException(var10.toString(), var10);
      } finally {
         if (hadErrors) {
            if (this.raIM != null) {
               this.raIM.cleanupWorkManagerRuntime();
            }

            this.cleanModuleExtensionContext();
         }

         Utils.stopManagement();
      }

   }

   public final void unprepare() throws ModuleException {
      this.debugModule("is being rolled back: calling unprepare.");
      Utils.startManagement();

      try {
         this.debugModule("is being rolled back. Calling Deployer.rollback( connMBean )");
         if (this.raIM != null) {
            String msgId;
            try {
               this.raIM.rollback();
            } catch (Throwable var8) {
               this.debugModule(" failed be rolled back due to error " + var8, var8);
               msgId = Debug.getExceptionRollbackModuleFailed(var8.toString());
               throw new ModuleException(msgId, var8);
            }

            try {
               this.raIM.cleanupRuntime();
            } catch (ManagementException var7) {
               msgId = Debug.logFailedToUnregisterModuleRuntimeMBean(var7.toString());
               Debug.logStackTrace(msgId, var7);
               this.debugModule("Warning: couldn't unregister a runtime MBean for the module: " + var7.toString());
               throw new ModuleException(var7);
            }
         }
      } finally {
         Utils.stopManagement();
         this.cleanModuleExtensionContext();
      }

      this.debugModule("has been rolled back: UNPREPARED");
   }

   public final void activate() throws ModuleException {
      ClassLoader origContextCL = Thread.currentThread().getContextClassLoader();
      boolean hadErrors = false;
      this.debugModule("is being ACTIVATED: calling activate.");
      Utils.startManagement();

      try {
         Thread.currentThread().setContextClassLoader(this.classLoader);
         this.raIM.activate();
         this.debugModule("has been ACTIVATED");
      } catch (Throwable var11) {
         hadErrors = true;
         this.debugModule("failed be ACTIVATED due to error " + var11, var11);
         throw new ModuleException(var11.toString(), var11);
      } finally {
         if (hadErrors) {
            try {
               this.debugModule("will try deactivate after activate failure.");
               this.deactivate();
               this.debugModule("deactivated after activate failure.");
            } catch (Throwable var10) {
               this.debugModule("get deactivate errors after activate failure, ignore", var10);
            }
         }

         Utils.stopManagement();
         Thread.currentThread().setContextClassLoader(origContextCL);
      }

   }

   public final void start() {
      this.debugModule("ConnectorModule.start() called; nothing is done here.");
   }

   public final void deactivate() throws ModuleException {
      this.debugModule("is being DEACTIVATED: calling deactivate.");
      Utils.startManagement();

      try {
         this.raIM.deactivate();
         this.debugModule("has been DEACTIVATED");
      } catch (Throwable var5) {
         this.debugModule(" failed be DEACTIVATED due to error " + var5, var5);
         throw new ModuleException(var5.toString(), var5);
      } finally {
         Utils.stopManagement();
      }

   }

   public boolean acceptURI(String u) {
      if (this.appCtx.isEar() && u.equals(this.uri + "/" + "META-INF/weblogic-ra.xml")) {
         return true;
      } else {
         return !this.appCtx.isEar() && u.equals("META-INF/weblogic-ra.xml");
      }
   }

   public void prepareUpdate(String uri) throws ModuleException {
      try {
         File altDDFile = this.resolveAltDD(this.appCtx, uri);
         DeploymentPlanBean plan = this.appCtx.findDeploymentPlan();
         AppDeploymentMBean dmb = this.appCtx.getAppDeploymentMBean();
         this.newRAInfo = DDUtil.getRAInfo(this.explodedRar, altDDFile, (File)null, this.getModuleName(), dmb, plan, this.classLoader, this.isEmbededInEar, this.aacProvider, this.getPermissionsBean(), !this.usingSubClassLoader);
         ((RAInfoImpl)this.newRAInfo).copyBaseRA((RAInfoImpl)this.raInfo);
         RAValidationInfo raValidationInfo = ConnectorUtils.createRAComplianceChecker().validate(this.explodedRar.getOriginalRarFilename(), this.newRAInfo, this.classLoader);
         this.pendingChanges = DeployerUtil.enumerateChanges(this.raIM, this.raInfo, this.newRAInfo);
         this.preparePendingChanges();
      } catch (Throwable var6) {
         String exMsg = Debug.getExceptionPrepareUpdateFailed(uri, var6.toString());
         this.debugModule(exMsg, var6);
         throw new ModuleException(exMsg, var6);
      }
   }

   private PermissionsBean getPermissionsBean() throws RAConfigurationException, IOException, XMLStreamException {
      if (this.isEmbededInEar) {
         return this.appCtx.getPermissionsBean();
      } else {
         DeploymentPlanBean plan = this.appCtx.getAppDeploymentMBean().getDeploymentPlanDescriptor();
         AppDeploymentMBean dmb = this.appCtx.getAppDeploymentMBean();
         return DDUtil.getPermissionsBean(dmb, plan, this.explodedRar, this.getModuleName());
      }
   }

   public void activateUpdate(String uri) throws ModuleException {
      ClassLoader origContextCL = Thread.currentThread().getContextClassLoader();

      try {
         Thread.currentThread().setContextClassLoader(this.classLoader);
         this.activatePendingChanges();
      } catch (Throwable var8) {
         String msg = "failed to activate update";
         this.debugModule(msg, var8);
         throw new ModuleException(msg, var8);
      } finally {
         Thread.currentThread().setContextClassLoader(origContextCL);
      }

   }

   private void activatePendingChanges() throws RAException {
      if (this.pendingChanges != null) {
         this.raInfo = this.newRAInfo;
         this.newRAInfo = null;
         this.raIM.setRAInfo(this.getKernelId(), this.raInfo);
         this.pendingChanges.activate();
         this.pendingChanges = null;
      } else {
         this.debugModule("No pending changes for update invocation");
      }

   }

   private void preparePendingChanges() throws RAException {
      if (this.pendingChanges != null) {
         this.pendingChanges.prepare(this.newRAInfo);
      } else {
         this.debugModule("No pending changes for update invocation");
      }

   }

   public void rollbackUpdate(String uri) {
      this.newRAInfo = null;
      this.pendingChanges = null;
   }

   public void initUsingLoader(ApplicationContext ac, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.debugModule("calling initUsingLoader.");
      this.initAndSetClassLoader(ac, parent, reg, false);
      this.debugModule("has been INITED UsingLoader.");
   }

   public GenericClassLoader init(ApplicationContext ac, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.debugModule("calling init.");
      GenericClassLoader gcl = this.initAndSetClassLoader(ac, parent, reg, true);
      this.debugModule("has been INITED.");
      return gcl;
   }

   private GenericClassLoader initAndSetClassLoader(ApplicationContext ac, GenericClassLoader parent, UpdateListener.Registration reg, boolean useSubLoader) throws ModuleException {
      this.usingSubClassLoader = useSubLoader;
      Utils.startManagement();
      boolean hasErrors = false;

      try {
         this.appCtx = (ApplicationContextInternal)ac;
         ConnectorComponentMBean connMBean = (ConnectorComponentMBean)this.findComponentMBean(this.appCtx, this.uri, ConnectorComponentMBean.class);
         this.componentName = connMBean.getName();
         this.componentURI = connMBean.getURI();
         if (useSubLoader) {
            this.classLoader = new GenericClassLoader(parent);
            this.classLoader.setAnnotation(new Annotation(this.appCtx.getApplicationId(), this.componentName));
         } else {
            this.classLoader = parent;
         }

         reg.addUpdateListener(this);
         this.appFileManager = this.appCtx.getApplicationFileManager();
         this.aacProvider = new AppCtxBasedAdditionalAnnotatedClassesProvider(this.appCtx);
         this.initilizeRarArchive();
         this.raInfo = this.loadDescriptors();
         Debug.println((Object)this, (String)".initialize() Updating the classloader");
         GenericClassLoader classloader4ra = ClassLoaderUtil.getClassloader4RA(this.raInfo, this.appCtx, this.classLoader);
         ClassFinder appFileOverrideFinder = AppFileOverrideUtils.getFinderIfRequired(this.appCtx.getAppDeploymentMBean(), this.appCtx.getRuntime().isEAR() ? this.componentURI : null);
         if (appFileOverrideFinder != null) {
            this.debugModule("Application File Overrides enabled");
            classloader4ra.addClassFinderFirst(appFileOverrideFinder);
            this.debugModule("Application File Overrides applied to classloader:" + classloader4ra);
         }

         String linkRef = this.raInfo.getLinkref();
         if (linkRef != null && linkRef.length() > 0) {
            RAInstanceManager baseRA = LinkrefManager.getBaseRA(linkRef);
            if (baseRA != null) {
               Debug.println((Object)this, (String)"() Update the classloader with the base jar");
               DeployerUtil.updateClassFinder(classloader4ra, baseRA.getRarArchive(), this.classFinders);
            }
         } else {
            DeployerUtil.updateClassFinder(classloader4ra, this.explodedRar, this.classFinders);
         }

         if (appFileOverrideFinder != null) {
            this.classFinders.add(appFileOverrideFinder);
         }

         this.moduleExtensionContext = new ConnectorModuleExtensionContext(this.appCtx, this, this.classLoader, classloader4ra);
      } catch (ModuleException var17) {
         hasErrors = true;
         this.debugModule(" failed be INIT due to ModuleException " + var17, var17);
         throw var17;
      } catch (RuntimeException var18) {
         hasErrors = true;
         this.debugModule(" failed be INIT due to RuntimeException " + var18, var18);
         throw var18;
      } catch (RAException var19) {
         hasErrors = true;
         this.debugModule(" failed be INIT due to RAException " + var19, var19);
         throw new ModuleException(var19);
      } catch (Error var20) {
         hasErrors = true;
         this.debugModule(" failed be INIT due to Error " + var20, var20);
         throw var20;
      } finally {
         if (hasErrors) {
            this.closeClassFinders();
            if (this.explodedRar != null) {
               this.explodedRar.close();
            }
         }

         Utils.stopManagement();
      }

      return this.classLoader;
   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
      this.debugModule("calling destroy.");
      Utils.startManagement();

      try {
         this.closeClassFinders();
         if (this.usingSubClassLoader && this.classLoader != null) {
            this.classLoader.close();
         }

         this.explodedRar.close();
         this.raInfo = null;
         reg.removeUpdateListener(this);
         this.remove();
         this.debugModule("has been DESTROIED.");
      } finally {
         Utils.stopManagement();
      }

   }

   public Class loadClass(String className) throws ClassNotFoundException {
      Utils.startManagement();

      Class var2;
      try {
         if (this.classLoader == null || className == null || className.equals("")) {
            throw new ClassNotFoundException(className);
         }

         var2 = this.raIM.getAdapterLayer().loadClass(this.classLoader, className, this.getKernelId());
      } finally {
         Utils.stopManagement();
      }

      return var2;
   }

   public void remove() throws ModuleException {
      this.debugModule("calling remove.");

      try {
         if (this.explodedRar != null) {
            this.explodedRar.remove();
         }
      } catch (RemoteRuntimeException var3) {
         String msgId = Debug.logFailedToFindModuleRuntimeMBean(var3.toString());
         Debug.logStackTrace(msgId, var3);
         this.debugModule("Warning: couldn't find a runtime MBean for the module: " + var3.toString());
      }

      this.debugModule("has been REMOVED.");
   }

   public void adminToProduction() {
      this.debugModule("calling adminToProduction.");
      this.debugModule("has been adminToProduction.");
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws ModuleException {
      this.debugModule("calling gracefulProductionToAdmin.");
      if (this.raIM.isWaitingStartVersioningComplete()) {
         ConnectorLogger.logWaitingComplete(this.raIM.toString());
         ShutdownCallback shutdownCallback = barrier.registerWMShutdown();
         this.addListenerAndSignalShutdown(new AdapterListenerImpl(shutdownCallback));
      }

      this.debugModule("has been gracefulProductionToAdmin.");
   }

   public void forceProductionToAdmin() {
      this.debugModule("calling forceProductionToAdmin.");
      this.debugModule("has been forceProductionToAdmin.");
   }

   private RAInfo loadDescriptors() throws ModuleException {
      RAInfo var4;
      try {
         Debug.enter(this, ".loadDescriptors()");
         this.debugModule("ConnectorModule.loadDescriptors() loading descriptors for module  of application " + ApplicationVersionUtils.getDisplayName(this.getAppId()));
         File altDDFile = this.resolveAltDD(this.appCtx, this.uri);
         DeploymentPlanBean plan = this.appCtx.getAppDeploymentMBean().getDeploymentPlanDescriptor();
         AppDeploymentMBean dmb = this.appCtx.getAppDeploymentMBean();

         try {
            this.raInfo = DDUtil.getRAInfo(this.explodedRar, altDDFile, (File)null, this.getModuleName(), dmb, plan, this.classLoader, this.isEmbededInEar, this.aacProvider, this.getPermissionsBean(), !this.usingSubClassLoader);
         } catch (Throwable var8) {
            this.debugModule("ConnectorModule.loadDescriptors() threw an exception: " + var8, var8);
            throw new ModuleException(var8.toString(), var8);
         }

         this.debugModule("ConnectorModule.loadDescriptors() succeeded");
         var4 = this.raInfo;
      } finally {
         Debug.exit(this, ".loadDescriptors() returning with raInfo = " + this.raInfo);
      }

      return var4;
   }

   public void setAltDD(String newValue) {
      this.altDD = newValue;
   }

   private void initilizeRarArchive() throws ModuleException {
      try {
         VirtualJarFile originaljarFile = this.appFileManager.getVirtualJarFile(this.componentURI);
         this.explodedRar = new RarArchive(this.getAppId(), this.appCtx, this.appCtx.getModuleContext(this.getId()), this.getModuleName(), this.isEmbededInEar, originaljarFile, false);
      } catch (IOException var3) {
         String exMsg = Debug.getExceptionCreateVJarFailed(this.componentURI, var3.toString());
         throw new ModuleException(exMsg, var3);
      }
   }

   public String getId() {
      return this.uri;
   }

   public String getType() {
      return WebLogicModuleType.MODULETYPE_RAR;
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return new ComponentRuntimeMBean[]{this.getRuntimeMBean()};
   }

   public String getAltDD() {
      return this.altDD;
   }

   public ComponentRuntimeMBean getRuntimeMBean() {
      return this.raIM != null ? this.raIM.getRuntime() : null;
   }

   private void addListenerAndSignalShutdown(AdapterListener adapterListener) throws ModuleException {
      BootstrapContext bootstrapContext = this.raIM.getBootstrapContext();
      if (bootstrapContext != null) {
         bootstrapContext.addListener(adapterListener);
         bootstrapContext.signalShutdown();
      }

   }

   public void removeListener(AdapterListener adapterListener) throws ModuleException {
      BootstrapContext bootstrapContext = this.raIM.getBootstrapContext();
      if (bootstrapContext != null) {
         bootstrapContext.removeListener(adapterListener);
      }

   }

   private String getModuleName() {
      if (this.moduleName != null) {
         return this.moduleName;
      } else if (this.componentName != null) {
         this.moduleName = this.componentName;
         return this.moduleName;
      } else {
         return this.uri;
      }
   }

   private String getAppId() {
      if (this.appId != null) {
         return this.appId;
      } else {
         if (this.appCtx != null) {
            this.appId = this.appCtx.getApplicationId();
         }

         return this.appId;
      }
   }

   private void debugModule(String msg) {
      if (Debug.isDeploymentEnabled()) {
         Debug.deployment("Module '" + this.getModuleName() + "' " + msg);
      }

   }

   private void debugModule(String msg, Throwable t) {
      if (Debug.isDeploymentEnabled()) {
         Debug.deployment("Module '" + this.getModuleName() + "' " + msg, t);
      }

   }

   public DescriptorBean[] getDescriptors() {
      if (this.raInfo == null) {
         return new DescriptorBean[0];
      } else {
         DescriptorBean[] descriptors = new DescriptorBean[]{(DescriptorBean)this.raInfo.getConnectorBean(), (DescriptorBean)this.raInfo.getWeblogicConnectorBean()};
         return descriptors;
      }
   }

   public void populateViewFinders(File baseDir, String viewAppName, boolean isArchived, VirtualJarFile moduleViewVjf, SplitDirectoryInfo viewSplitDirInfo, MultiClassFinder finder, MultiClassFinder resourceFinder) throws IOException, IllegalSpecVersionTypeException {
   }

   public ModuleExtensionContext getModuleExtensionContext() {
      return this.moduleExtensionContext;
   }

   public Descriptor getStandardDescriptor() {
      return ((DescriptorBean)this.raInfo.getConnectorBean()).getDescriptor();
   }

   public RarArchive getExplodedRar() {
      return this.explodedRar;
   }

   private AuthenticatedSubject getKernelId() {
      if (this.kernelId == null) {
         this.kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      }

      return this.kernelId;
   }

   private void cleanModuleExtensionContext() {
      if (this.moduleExtensionContext != null) {
         GenericClassLoader tempClassLoader = this.moduleExtensionContext.getTemporaryClassLoader();
         if (tempClassLoader != null) {
            tempClassLoader.close();
         }
      }

   }

   public List getClassFinders() {
      return this.classFinders;
   }

   public synchronized void closeClassFinders() {
      Debug.println((Object)this, (String)".closeClassFinders()");
      if (!this.classFindersClosed) {
         Iterator classFinderIterator = this.classFinders.iterator();

         while(classFinderIterator.hasNext()) {
            ClassFinder classFinder = (ClassFinder)classFinderIterator.next();

            try {
               Debug.println((Object)this, (String)(".closeClassFinders():  closing classfinder " + classFinder));
               classFinder.close();
            } catch (Throwable var4) {
               Debug.println((Object)this, (String)(".closeClassFinders(): ignore ex: " + var4 + "; while closing CF" + classFinder));
            }
         }

         this.classFindersClosed = true;
      }
   }
}
