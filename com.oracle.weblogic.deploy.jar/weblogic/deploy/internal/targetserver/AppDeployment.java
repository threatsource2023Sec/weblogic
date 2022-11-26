package weblogic.deploy.internal.targetserver;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import weblogic.application.Deployment;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.event.DeploymentEvent;
import weblogic.deploy.event.DeploymentEventManager;
import weblogic.deploy.event.DeploymentVetoException;
import weblogic.deploy.internal.DeploymentVersion;
import weblogic.deploy.internal.TargetHelper;
import weblogic.deploy.internal.targetserver.datamanagement.AppData;
import weblogic.deploy.internal.targetserver.datamanagement.Data;
import weblogic.deploy.internal.targetserver.operations.AbstractOperation;
import weblogic.deploy.internal.targetserver.state.DeploymentState;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.deploy.internal.AppRuntimeStateManager;
import weblogic.management.deploy.internal.ApplicationRuntimeState;
import weblogic.management.deploy.internal.MBeanConverter;
import weblogic.management.deploy.internal.SlaveDeployerLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public class AppDeployment extends BasicDeployment {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public AppDeployment(AppDeploymentMBean dmbean, ServerMBean server) {
      super(dmbean, server);
   }

   public AppDeploymentMBean getAppDeploymentMBean() {
      return (AppDeploymentMBean)this.getDeploymentMBean();
   }

   public void verifyAppVersionSecurity(AbstractOperation task) throws DeploymentException {
      if (isDebugEnabled()) {
         debug("BasicDeployment.verifyAppVersionSecurity(" + this.name + ")");
      }

      AppDeploymentMBean appDeploymentMBean = this.getAppDeploymentMBean();
      if (appDeploymentMBean != null && appDeploymentMBean.getVersionIdentifier() != null && !SecurityServiceManager.isApplicationVersioningSupported("weblogicDEFAULT")) {
         Loggable l = SlaveDeployerLogger.logSecurityRealmDoesNotSupportAppVersioningLoggable("weblogicDEFAULT", ApplicationVersionUtils.getDisplayName(appDeploymentMBean));
         l.log();
         throw new DeploymentException(l.getMessage());
      }
   }

   private void updateAggregateDeploymentVersion() {
      String appId = this.getAppDeploymentMBean().getName();
      ApplicationRuntimeState appRuntimeState = AppRuntimeStateManager.getManager().get(appId);
      if (appRuntimeState != null) {
         DeploymentVersion version = appRuntimeState.getDeploymentVersion();
         if (version != null) {
            DeploymentManager.getInstance().addOrUpdateTargetDeploymentVersion(appId, version);
         }
      }

   }

   public void prepare() throws IOException, DeploymentException {
      if (isDebugEnabled()) {
         debug("Preparing " + this.name);
      }

      AppDeploymentMBean appDeploymentMBean = this.getAppDeploymentMBean();
      if (!DeployHelper.isOkToTransition(appDeploymentMBean, this.server, "STATE_PREPARED")) {
         if (isDebugEnabled()) {
            debug("Not preparing because " + this.name + " is internal, is not targeted locally, or has a lower intended state than PREPARED");
         }

      } else {
         try {
            this.stageFilesForStatic();
            this.staticDeployValidationForNonVersion();
            MBeanConverter.setupNew81MBean(appDeploymentMBean);
            this.fireVetoableDeploymentEvent();

            weblogic.application.DeploymentManager.DeploymentCreator deploymentCreator;
            try {
               deploymentCreator = this.deploymentManager.getDeploymentCreator(appDeploymentMBean, (File)null);
            } catch (DeploymentException var20) {
               Throwable cause = var20.getCause();
               throw new DeploymentException(cause.getMessage(), new DeploymentVetoException(cause));
            }

            Deployment deployment = this.createDeployment(deploymentCreator, appDeploymentMBean, (DeploymentState)null);
            appDeploymentMBean.setDeploymentPlanDescriptor(this.parsePlan());
            DeploymentContextImpl deploymentContext;
            if (this.task == null) {
               deploymentContext = DeployHelper.createDeploymentContext(appDeploymentMBean);
               deploymentContext.setStaticDeploymentOperation(true);
            } else {
               deploymentContext = this.task.getDeploymentContext();
            }

            deploymentContext.setAdminModeTransition(true);
            if (this.isMSID) {
               deploymentContext.setDeploymentOperation(1);
            }

            this.startLifecycleStateManager();
            this.relayStagingState(this.getStagingState());
            this.updateAggregateDeploymentVersion();

            try {
               deployment.prepare(deploymentContext);
            } catch (Throwable var18) {
               this.failDeployment();
               throw var18;
            } finally {
               this.finishLifecycleStateManager();
            }

            this.fireDeployedDeploymentEvent();
         } catch (Throwable var21) {
            this.failDeployment();
            this.finishLifecycleStateManager();
            DeploymentException ex = DeployHelper.convertThrowable(var21);
            SlaveDeployerLogger.logIntialPrepareApplicationFailedLoggable(this.name, ex).log();
            throw ex;
         } finally {
            ;
         }
      }
   }

   private void staticDeployValidationForNonVersion() throws DeploymentException {
      AppDeploymentMBean deployment = this.getAppDeploymentMBean();
      String versionId = deployment.getVersionIdentifier();
      if (versionId == null || versionId.length() == 0) {
         String manifestVersion = ApplicationVersionUtils.getManifestVersion(this.getLocalAppFileOrDir().getAbsolutePath());
         if (manifestVersion != null && manifestVersion.length() != 0) {
            Loggable loggable = SlaveDeployerLogger.logStaticDeploymentOfNonVersionAppCheckLoggable(this.getName());
            throw new DeploymentException(loggable.getMessage());
         }
      }
   }

   private void failDeployment() {
      if (this.getState() == null) {
         this.setState(new DeploymentState(this.deploymentMBean.getName(), "__Lifecycle_taskid__", 0));
      }

      this.getState().setCurrentState("STATE_FAILED", true);
   }

   public DeploymentPlanBean parsePlan() throws DeploymentException {
      try {
         AppDeploymentMBean appDeploymentMBean = this.getAppDeploymentMBean();
         if (!appDeploymentMBean.isInternalApp()) {
            appDeploymentMBean.setDeploymentPlanDescriptor((DeploymentPlanBean)null);
         }

         return appDeploymentMBean.getDeploymentPlanDescriptor();
      } catch (IllegalArgumentException var2) {
         throw new DeploymentException(var2.toString(), var2);
      }
   }

   public File getFile(AppDeploymentMBean appDeploymentMBean) {
      return getFile(appDeploymentMBean, this.server);
   }

   public static File getFile(AppDeploymentMBean appDeploymentMBean, ServerMBean serverBean) {
      File theFile;
      if (DeployHelper.getStagingMode(serverBean.getName(), appDeploymentMBean).equals("nostage")) {
         String thePath = appDeploymentMBean.getAbsoluteSourcePath();
         theFile = new File(thePath);
      } else {
         theFile = new File(appDeploymentMBean.getLocalSourcePath());
      }

      return theFile;
   }

   public void removeDeployment() {
      AppDeploymentMBean appDeploymentMBean = this.getAppDeploymentMBean();
      if (appDeploymentMBean != null) {
         this.deploymentManager.removeDeployment(appDeploymentMBean);
         MBeanConverter.remove81MBean(appDeploymentMBean);
      }

   }

   public void updateDescriptorsPathInfo() {
      AppData localAppData = (AppData)this.getLocalData();
      localAppData.updateDescriptorsPathInfo(this.getAppDeploymentMBean());
   }

   private void fireDeployedDeploymentEvent() {
      AppDeploymentMBean appDeploymentMBean = this.getAppDeploymentMBean();
      DeploymentEventManager.sendDeploymentEvent(DeploymentEvent.create(this, DeploymentEvent.APP_DEPLOYED, appDeploymentMBean, true, (String[])null, TargetHelper.getTargetNames(appDeploymentMBean.getTargets())));
   }

   protected Data createLocalData() {
      AppDeploymentMBean mbean = this.getAppDeploymentMBean();
      String staging = DeployHelper.getStagingMode(this.server, mbean);
      String planStaging = mbean.getPlanStagingMode();
      return new AppData(this.server, mbean, this, staging, planStaging, mbean.getLocalSourcePath(), (String)null);
   }

   public String getRelativePlanPath() throws IOException {
      AppData localAppData = (AppData)this.getLocalData();
      return localAppData.getRelativePlanPath();
   }
}
