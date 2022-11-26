package weblogic.deploy.internal.targetserver.state;

import java.security.AccessController;
import java.util.Date;
import weblogic.application.ModuleListener;
import weblogic.application.ModuleListenerCtx;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.common.Debug;
import weblogic.deploy.compatibility.NotificationBroadcaster;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StringUtils;

public class ModuleTransitionTracker implements ModuleListener {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String serverName;
   private final AppDeploymentMBean mbean;
   private final String taskID;
   private final DeploymentState state;
   private long stateStartTime;

   ModuleTransitionTracker(AppDeploymentMBean mbean, String taskID, DeploymentState s) {
      this.mbean = mbean;
      this.state = s;
      if (mbean.getAppMBean() == null && this.isDebugEnabled()) {
         this.debug("Listener: appmbean is NULL for:" + this);
      }

      this.taskID = taskID;
   }

   public void beginTransition(ModuleListenerCtx ctx, ModuleListener.State currentState, ModuleListener.State newState) {
      if (ctx.getApplicationId().equals(this.mbean.getApplicationIdentifier())) {
         if (this.isDebugEnabled()) {
            this.debug("Listener: begin " + ctx + " " + currentState + ">" + newState);
         } else if (Debug.isDeploymentDebugConciseEnabled()) {
            Debug.deploymentDebugConcise("Module " + ctx.getModuleUri() + " of application " + ctx.getApplicationId() + " is transitioning from " + currentState + " to " + newState + " on server " + serverName);
         }

         long gentime = (new Date()).getTime();
         if (Debug.isDeploymentPerformanceDebugEnabled && (newState.toString().equals("STATE_PREPARED") || newState.toString().equals("STATE_ACTIVE"))) {
            this.stateStartTime = System.nanoTime();
         }

         TargetModuleState tms = this.state.addModuleTransition(ctx, currentState.toString(), newState.toString(), "begin", gentime);
         if (tms != null) {
            tms.setCurrentState(currentState.toString());
         }

         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         ApplicationMBean appmbean = runtimeAccess.getDomain().lookupApplication(this.mbean.getApplicationIdentifier());
         if (appmbean != null && NotificationBroadcaster.isRelevantToWLS81(currentState.toString(), newState.toString())) {
            appmbean.sendModuleNotification(serverName, ctx.getModuleUri(), "begin", currentState.toString(), newState.toString(), this.taskID, gentime);
         }

         DeployerRuntimeLogger.logStartTransition(this.getAppDisplayName(), this.getModuleDisplayName(ctx.getModuleUri()), currentState.toString(), newState.toString(), serverName);
      }
   }

   public void endTransition(ModuleListenerCtx ctx, ModuleListener.State currentState, ModuleListener.State newState) {
      if (ctx.getApplicationId().equals(this.mbean.getApplicationIdentifier())) {
         if (this.isDebugEnabled()) {
            this.debug("Listener: end " + ctx + " " + currentState + ">" + newState);
         } else if (Debug.isDeploymentDebugConciseEnabled()) {
            Debug.deploymentDebugConcise("Module " + ctx.getModuleUri() + " of application " + ctx.getApplicationId() + " successfully transitioned from " + currentState + " to " + newState + " on server " + serverName);
         }

         long gentime = (new Date()).getTime();
         TargetModuleState tms = this.state.addModuleTransition(ctx, currentState.toString(), newState.toString(), "end", gentime);
         if (tms != null) {
            tms.setCurrentState(newState.toString());
         } else if (this.isDebugEnabled()) {
            this.debug("Listener: module not locally targeted");
         }

         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         ApplicationMBean appmbean = runtimeAccess.getDomain().lookupApplication(this.mbean.getApplicationIdentifier());
         if (appmbean != null && NotificationBroadcaster.isRelevantToWLS81(currentState.toString(), newState.toString())) {
            appmbean.sendModuleNotification(serverName, ctx.getModuleUri(), "end", currentState.toString(), newState.toString(), this.taskID, gentime);
         }

         if (Debug.isDeploymentPerformanceDebugEnabled && (newState.toString().equals("STATE_PREPARED") || newState.toString().equals("STATE_ACTIVE"))) {
            Debug.logPerformanceData("Module " + this.getModuleDisplayName(ctx.getModuleUri()) + " from Application " + this.getAppDisplayName() + " transitioned to " + newState.toString() + " in " + StringUtils.formatCurrentTimeFromNanoToMillis(this.stateStartTime) + " ms.");
         }

         DeployerRuntimeLogger.logSuccessfulTransition(this.getAppDisplayName(), this.getModuleDisplayName(ctx.getModuleUri()), currentState.toString(), newState.toString(), serverName);
      }
   }

   public void failedTransition(ModuleListenerCtx ctx, ModuleListener.State currentState, ModuleListener.State newState) {
      if (ctx.getApplicationId().equals(this.mbean.getApplicationIdentifier())) {
         if (this.isDebugEnabled()) {
            this.debug("Listener: fail " + ctx + " " + currentState + ">" + newState);
         } else if (Debug.isDeploymentDebugConciseEnabled()) {
            Debug.deploymentDebugConcise("Module " + ctx.getModuleUri() + " of application " + ctx.getApplicationId() + " failed to transition from " + currentState + " to " + newState + " on server " + serverName);
         }

         long gentime = (new Date()).getTime();
         TargetModuleState tms = this.state.addModuleTransition(ctx, currentState.toString(), newState.toString(), "failed", gentime);
         if (tms != null) {
            tms.setCurrentState(newState.toString());
         }

         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         ApplicationMBean appmbean = runtimeAccess.getDomain().lookupApplication(this.mbean.getApplicationIdentifier());
         if (appmbean != null && NotificationBroadcaster.isRelevantToWLS81(currentState.toString(), newState.toString())) {
            appmbean.sendModuleNotification(serverName, ctx.getModuleUri(), "failed", currentState.toString(), newState.toString(), this.taskID, gentime);
         }

         DeployerRuntimeLogger.logFailedTransition(this.getAppDisplayName(), this.getModuleDisplayName(ctx.getModuleUri()), currentState.toString(), newState.toString(), serverName);
      }
   }

   private String getAppDisplayName() {
      return ApplicationVersionUtils.getDisplayName(this.mbean.getName());
   }

   private String getModuleDisplayName(String moduleName) {
      return ApplicationVersionUtils.getApplicationName(moduleName);
   }

   public String toString() {
      return "ModuleListener[appName= " + this.mbean.getName() + "]";
   }

   private void debug(String m) {
      Debug.deploymentDebug(m);
   }

   private boolean isDebugEnabled() {
      return Debug.isDeploymentDebugEnabled();
   }

   static {
      serverName = ManagementService.getRuntimeAccess(kernelId).getServer().getName();
   }
}
