package weblogic.management.deploy.internal;

import java.io.File;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.api.tools.remote.SessionHelperManagerRuntime;
import weblogic.deploy.beans.factory.DeploymentBeanFactory;
import weblogic.deploy.beans.factory.internal.DeploymentBeanFactoryImpl;
import weblogic.deploy.internal.adminserver.DeploymentManager;
import weblogic.deploy.internal.diagnostics.DeploymentImageSource;
import weblogic.diagnostics.image.ImageManager;
import weblogic.logging.Loggable;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.deploy.ApplicationsDirPoller;
import weblogic.management.deploy.GenericAppPoller;
import weblogic.management.internal.SecurityHelper;
import weblogic.management.provider.DomainAccessSettable;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DeploymentManagerMBean;
import weblogic.management.runtime.SessionHelperManagerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.StringUtils;
import weblogic.utils.progress.ProgressTrackerRegistrar;
import weblogic.utils.progress.ProgressTrackerRegistrarFactory;
import weblogic.utils.progress.ProgressTrackerService;

@Service
@Named
@RunLevel(15)
public final class DeploymentServerService extends AbstractServerService {
   @Inject
   @Named("TransactionRecoveryService")
   private ServerService dependencyOnTransactionRecoveryService;
   @Inject
   @Optional
   @Named("WseeDeploymentService")
   private ServerService dependencyOnWseeDeploymentService;
   @Inject
   @Optional
   @Named("SAFServerService")
   private ServerService dependencyOnSAFServerService;
   @Inject
   @Named("DomainValidator")
   private ServerService domainValidator;
   @Inject
   @Named("ValidationService")
   private ServerService validationService;
   @Inject
   @Named("DomainPartitionService")
   private ServerService domainPartitionService;
   @Inject
   private ConfiguredDeployments configuredDeploymentsHandler;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ApplicationsDirPoller poller = null;
   private static DeployerRuntimeMBean deployerRuntime;
   private static DeploymentManagerMBean deploymentManager;
   private static DeploymentBeanFactory singleton;
   private static DeploymentManager adminServerDeploymentManager;
   private static weblogic.deploy.internal.targetserver.DeploymentManager targetServerDeploymentManager;
   private static boolean started = false;
   private static SessionHelperManagerRuntimeMBean sessionHelperManager = null;
   private static ProgressTrackerService depProgress = null;

   public static DeployerRuntimeMBean getDeployerRuntime() {
      SecurityHelper.assertIfNotKernel();
      return deployerRuntime;
   }

   public static DeploymentManagerMBean getDeploymentManager() {
      SecurityHelper.assertIfNotKernel();
      return deploymentManager;
   }

   public final void start() throws ServiceFailureException {
      ImageManager ims = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
      if (ims != null) {
         ims.registerImageSource("Deployment", new DeploymentImageSource());
      }

      this.resume();
      setStarted(true);
   }

   public final void halt() {
   }

   public final void stop() throws ServiceFailureException {
      try {
         ImageManager ims = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
         if (ims != null) {
            ims.unregisterImageSource("Deployment");
         }
      } catch (Exception var2) {
         throw new ServiceFailureException(var2);
      }

      this.halt();
      setStarted(false);
   }

   public static void shutdownHelper() throws ServiceFailureException {
      try {
         shutdownService();
      } catch (Exception var2) {
         var2.printStackTrace();
         Loggable l = DeploymentManagerLogger.logShutdownFailureLoggable();
         l.log();
      }

   }

   public static DeploymentBeanFactory getDeploymentBeanFactory() {
      if (singleton == null) {
         singleton = new DeploymentBeanFactoryImpl();
      }

      return singleton;
   }

   public static final void init() throws ServiceFailureException {
      try {
         registerDeploymentProgressMeter();
         RuntimeAccess runtime = ManagementService.getRuntimeAccess(kernelId);
         runtime.addAccessCallbackClass(ApplicationCompatibilityEditProcessor.class.getName());
         startAdminServerDeploymentService();
         startTargetServerDeploymentService();
      } catch (ManagementException var1) {
         shutdownService();
         throw new ServiceFailureException(var1);
      }
   }

   public static final void registerDeploymentProgressMeter() {
      ProgressTrackerRegistrar registrar = ProgressTrackerRegistrarFactory.getInstance().getProgressTrackerRegistrar();
      depProgress = registrar.registerProgressTrackerSubsystem("Deployments");
   }

   private void resume() throws ServiceFailureException {
      try {
         this.configuredDeploymentsHandler.deploy();
         RetirementManager.retireAppsOnStartup();
      } catch (ManagementException var3) {
         Loggable l = DeploymentManagerLogger.logResumeFailureLoggable();
         l.log();
         throw new ServiceFailureException(l.getMessage(), var3);
      }
   }

   private static DeploymentManager getAdminServerDeploymentManager() {
      if (adminServerDeploymentManager == null) {
         adminServerDeploymentManager = DeploymentManager.getInstance(kernelId);
      }

      return adminServerDeploymentManager;
   }

   private static weblogic.deploy.internal.targetserver.DeploymentManager getTargetServerDeploymentManager() {
      if (targetServerDeploymentManager == null) {
         targetServerDeploymentManager = weblogic.deploy.internal.targetserver.DeploymentManager.getInstance();
      }

      return targetServerDeploymentManager;
   }

   private static void shutdownService() {
      shutdownTargetServerDeploymentManager();
      shutdownAdminServerDeploymentManager();
   }

   private static void startAdminServerDeploymentService() throws ManagementException {
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         initializeDeployerRuntime();
         initializeDeploymentManager();
         initializeApplicationPoller();
         initializeAdminServerDeploymentManager();
         initializeSessionHelperManager();
         initializeAppRuntimeStateManager();
      }

   }

   private static void initializeAppRuntimeStateManager() throws ManagementException {
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         domain.getDescriptor().addUpdateListener(AppRuntimeStateManager.getManager());
      }

   }

   private static void initializeDeployerRuntime() throws ManagementException {
      try {
         deployerRuntime = new DeployerRuntimeImpl("DeployerRuntime");
         DomainAccessSettable domainAccessImpl = (DomainAccessSettable)ManagementService.getDomainAccess(kernelId);
         domainAccessImpl.setDeployerRuntime(deployerRuntime);
      } catch (ManagementException var2) {
         Loggable l = DeployerRuntimeLogger.logInitFailedLoggable(var2);
         l.log();
         throw new ManagementException(l.getMessage(), var2);
      }
   }

   private static void initializeDeploymentManager() throws ManagementException {
      try {
         deploymentManager = new DeploymentManagerImpl("DeploymentManager");
         DomainAccessSettable domainAccessImpl = (DomainAccessSettable)ManagementService.getDomainAccess(kernelId);
         domainAccessImpl.setDeploymentManager(deploymentManager);
      } catch (ManagementException var2) {
         Loggable l = DeploymentManagerLogger.logInitFailedLoggable(var2);
         l.log();
         throw new ManagementException(l.getMessage(), var2);
      }
   }

   private static void initializeApplicationPoller() {
      ApplicationsDirPoller.removeStagedFilesForAppsRemovedSinceLastShutdown();
   }

   private static void initializeAdminServerDeploymentManager() throws ManagementException {
      getAdminServerDeploymentManager();
      DeploymentManager.initialize();
   }

   private static void shutdownAdminServerDeploymentManager() {
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         DeploymentManager.shutdown();
      }

   }

   private static void startTargetServerDeploymentService() {
      initializeTargetServerDeploymentManager();
   }

   private static void initializeTargetServerDeploymentManager() {
      getTargetServerDeploymentManager().initialize();
   }

   private static void shutdownTargetServerDeploymentManager() {
      getTargetServerDeploymentManager().shutdown();
   }

   static void startAutoDeploymentPoller() {
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         int DEFAULT_INTERVAL = 3000;
         String autodeploydir = DomainDir.getAppPollerDir();
         if (!ManagementService.getRuntimeAccess(kernelId).getDomain().isProductionModeEnabled()) {
            long intervalAsLong = (new Integer(DEFAULT_INTERVAL)).longValue();
            poller = new ApplicationsDirPoller(new File(autodeploydir), false, intervalAsLong);
            poller.start();
            ManagementLogger.logPollerStarted();
         } else {
            ManagementLogger.logPollerNotStarted();
         }

      }
   }

   public static synchronized void setStarted(boolean isStarted) {
      started = isStarted;
      if (started && depProgress != null) {
         depProgress.finished();
      }

   }

   public static synchronized boolean isStarted() {
      return started;
   }

   public static GenericAppPoller getApplicationDirPoller() {
      return poller;
   }

   private static void initializeSessionHelperManager() throws ManagementException {
      try {
         sessionHelperManager = new SessionHelperManagerRuntime("SessionHelperManager");
         DomainAccessSettable domainAccessImpl = (DomainAccessSettable)ManagementService.getDomainAccess(kernelId);
         domainAccessImpl.setSessionHelperManager(sessionHelperManager);
      } catch (ManagementException var2) {
         Loggable l = DeployerRuntimeLogger.logInitFailedLoggable(var2);
         l.log();
         throw new ManagementException(l.getMessage(), var2);
      }
   }

   public SessionHelperManagerRuntimeMBean getSessionHelperManagerRuntime() {
      return sessionHelperManager;
   }

   static String normalizePartitionName(String partitionName) {
      return StringUtils.isEmptyString(partitionName) ? "DOMAIN" : partitionName;
   }
}
