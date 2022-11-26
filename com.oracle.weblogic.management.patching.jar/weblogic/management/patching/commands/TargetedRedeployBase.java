package weblogic.management.patching.commands;

import java.security.AccessController;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.AppDeploymentRuntimeMBean;
import weblogic.management.runtime.DeploymentManagerMBean;
import weblogic.management.runtime.DeploymentProgressObjectMBean;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StringUtils;

public abstract class TargetedRedeployBase extends AbstractCommand {
   private static final long serialVersionUID = 3375050629003254209L;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final long DEFAULT_REDEPLOY_TIMEOUT;
   private static final long DEFAULT_POLL_INTERVAL;
   private static String EDIT_SESSION_NAME_PREFIX;
   @SharedState
   protected String serverName;
   @SharedState
   protected String applicationName;
   @SharedState
   protected String deploymentPlan;
   @SharedState
   protected boolean removePlanOverride;
   @SharedState
   protected transient Map isTargetedRedeployDone;

   public void targetedRedeploy() throws CommandException {
      DeploymentManagerMBean deploymentManagerMBean = ManagementService.getDomainAccess(kernelId).getDeploymentManager();
      AppDeploymentRuntimeMBean appDeploymentRuntimeMBean = deploymentManagerMBean.lookupAppDeploymentRuntime(this.applicationName);
      Properties deploymentOptions = new Properties();
      deploymentOptions.setProperty("specifiedTargetsOnly", Boolean.TRUE.toString());
      String editSessionName = this.generateEditSessionName();
      if (editSessionName != null && !editSessionName.isEmpty()) {
         deploymentOptions.setProperty("editSession", editSessionName);
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TargetedRedeployBase.targetedRedeploy using session name " + editSessionName + ", for targeted redeploy of " + appDeploymentRuntimeMBean.getApplicationName() + " to server " + this.serverName);
         }
      } else if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("TargetedRedeployBase.targetedRedeploy using default global session , for targeted redeploy of " + appDeploymentRuntimeMBean.getApplicationName() + " to server " + this.serverName);
      }

      if (!StringUtils.isEmptyString(this.deploymentPlan) && PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("TargetedRedeployBase.targetedRedeploy passing deployment plan " + this.deploymentPlan + " to redeploy app " + this.applicationName);
      }

      if (this.removePlanOverride) {
         deploymentOptions.setProperty("removePlanOverride", Boolean.TRUE.toString());
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TargetedRedeployBase.targetedRedeploy passing removePlanOverride option to redeploy app " + this.applicationName);
         }
      }

      try {
         DeploymentProgressObjectMBean progress = appDeploymentRuntimeMBean.redeploy(new String[]{this.serverName}, StringUtils.isEmptyString(this.deploymentPlan) ? null : this.deploymentPlan, deploymentOptions);
         this.waitForCompletion(progress);
         StringBuffer sb = new StringBuffer();
         String[] var7 = progress.getMessages();
         int var8 = var7.length;

         int var9;
         for(var9 = 0; var9 < var8; ++var9) {
            String tmp = var7[var9];
            sb.append(tmp);
            sb.append("\n");
         }

         if (!"STATE_FAILED".equals(progress.getState())) {
            if ("STATE_COMPLETED".equals(progress.getState())) {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("Successful ApplicationRedeploy for " + this.applicationName + " on " + this.serverName + " with messages: " + sb.toString());
               }
            } else if ("STATE_DEFERRED".equals(progress.getState()) && PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Deferred ApplicationRedeploy for " + this.applicationName + " on " + this.serverName + " with messages: " + sb.toString());
            }

         } else {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("ApplicationRedeploy for " + this.applicationName + " on " + this.serverName + " failed with messages: " + sb.toString());
            }

            RuntimeException[] var13 = progress.getExceptions(this.serverName);
            var8 = var13.length;

            for(var9 = 0; var9 < var8; ++var9) {
               Exception e = var13[var9];
               PatchingLogger.logApplicationRedeployFailure(this.applicationName, this.serverName, e);
            }

            throw new CommandException(PatchingMessageTextFormatter.getInstance().getApplicationRedeployFailureMessages(this.applicationName, this.serverName, sb.toString()));
         }
      } catch (Exception var11) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("ApplicationRedeploy for " + this.applicationName + " on " + this.serverName + " failed ", var11);
         }

         CommandException ce = new CommandException(PatchingMessageTextFormatter.getInstance().getApplicationRedeployFailure(this.applicationName, this.serverName, var11.getClass() + ":" + var11.getMessage()));
         ce.initCause(var11);
         throw ce;
      }
   }

   public void partitionTargetedRedeploy(String partitionName) throws CommandException {
      DeploymentManagerMBean deploymentManagerMBean = ManagementService.getDomainAccess(kernelId).lookupDomainPartitionRuntime(partitionName).getDeploymentManager();
      AppDeploymentRuntimeMBean appDeploymentRuntimeMBean = deploymentManagerMBean.lookupAppDeploymentRuntime(this.applicationName);
      Properties deploymentOptions = new Properties();
      deploymentOptions.setProperty("specifiedTargetsOnly", Boolean.TRUE.toString());
      deploymentOptions.setProperty("partition", partitionName);
      String editSessionName = this.generateEditSessionName();
      if (editSessionName != null && !editSessionName.isEmpty()) {
         deploymentOptions.setProperty("editSession", editSessionName);
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TargetedRedeployBase.targetedRedeploy using session name " + editSessionName + ", for targeted redeploy of " + appDeploymentRuntimeMBean.getApplicationName() + "to partition " + partitionName + " to server " + this.serverName);
         }
      } else if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("TargetedRedeployBase.targetedRedeploy using default global session , for targeted redeploy of " + appDeploymentRuntimeMBean.getApplicationName() + "to partition " + partitionName + " to server " + this.serverName);
      }

      if (!StringUtils.isEmptyString(this.deploymentPlan) && PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("TargetedRedeployBase.targetedRedeploy passing deployment plan " + this.deploymentPlan + " to redeploy app " + this.applicationName);
      }

      if (this.removePlanOverride) {
         deploymentOptions.setProperty("removePlanOverride", Boolean.TRUE.toString());
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("TargetedRedeployBase.targetedRedeploy passing removePlanOverride option to redeploy app " + this.applicationName);
         }
      }

      try {
         DeploymentProgressObjectMBean progress = appDeploymentRuntimeMBean.redeploy(new String[]{this.serverName}, StringUtils.isEmptyString(this.deploymentPlan) ? null : this.deploymentPlan, deploymentOptions);
         this.waitForCompletion(progress);
         StringBuffer sb = new StringBuffer();
         String[] var8 = progress.getMessages();
         int var9 = var8.length;

         int var10;
         for(var10 = 0; var10 < var9; ++var10) {
            String tmp = var8[var10];
            sb.append(tmp);
            sb.append("\n");
         }

         if (!"STATE_FAILED".equals(progress.getState())) {
            if ("STATE_COMPLETED".equals(progress.getState())) {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("Successful ApplicationRedeploy for " + this.applicationName + "on " + partitionName + " on " + this.serverName + " with messages: " + sb.toString());
               }
            } else if ("STATE_DEFERRED".equals(progress.getState()) && PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Deferred ApplicationRedeploy for " + this.applicationName + "on " + partitionName + " on " + this.serverName + " with messages: " + sb.toString());
            }

         } else {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("ApplicationRedeploy for " + this.applicationName + "on " + partitionName + " on " + this.serverName + " failed with messages: " + sb.toString());
            }

            RuntimeException[] var14 = progress.getExceptions(this.serverName);
            var9 = var14.length;

            for(var10 = 0; var10 < var9; ++var10) {
               Exception e = var14[var10];
               PatchingLogger.logApplicationRedeployToPartitionFailure(this.applicationName, partitionName, this.serverName, e);
            }

            throw new CommandException(PatchingMessageTextFormatter.getInstance().getApplicationRedeployFailureMessages(this.applicationName, this.serverName, sb.toString()));
         }
      } catch (Exception var12) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("ApplicationRedeploy for " + this.applicationName + "on " + partitionName + " on " + this.serverName + " failed ", var12);
         }

         CommandException ce = new CommandException(PatchingMessageTextFormatter.getInstance().getApplicationRedeployToPartitionFailureMessages(this.applicationName, partitionName, this.serverName, var12.getClass() + ":" + var12.getMessage()));
         ce.initCause(var12);
         throw ce;
      }
   }

   protected String generateEditSessionName() {
      String editSessionName = EDIT_SESSION_NAME_PREFIX + "-" + this.getContext().getWorkflowId() + "-" + this.getClass().getName();
      return editSessionName;
   }

   private void waitForCompletion(final DeploymentProgressObjectMBean progress) throws CommandException {
      TimeoutUtils timeoutUtils = new TimeoutUtils();
      long timeout = timeoutUtils.convertTimeoutByPercentageFactor(DEFAULT_REDEPLOY_TIMEOUT);
      long pollInterval = timeoutUtils.convertIntervalByFactorIfLarger(DEFAULT_POLL_INTERVAL);
      StatusPoller deploymentProgressPoller = new StatusPoller() {
         public boolean checkStatus() {
            String state = progress.getState();
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Waiting on finished state( " + state + " ) for ApplicationRedeploy for " + TargetedRedeployBase.this.applicationName + " on " + TargetedRedeployBase.this.serverName);
            }

            return !state.equals("STATE_RUNNING") && !state.equals("STATE_INITIALIZED");
         }

         public String getPollingDescription() {
            return "ApplicationRedeployTask";
         }
      };
      if ((new TimedStatusPoller()).pollStatusWithTimeout(timeout, pollInterval, deploymentProgressPoller)) {
         throw new CommandException(PatchingMessageTextFormatter.getInstance().getApplicationRedeployTimeout(this.serverName, this.applicationName, timeout, progress.getState()));
      }
   }

   static {
      DEFAULT_REDEPLOY_TIMEOUT = TimeUnit.MILLISECONDS.convert(2L, TimeUnit.HOURS);
      DEFAULT_POLL_INTERVAL = TimeUnit.MILLISECONDS.convert(2L, TimeUnit.SECONDS);
      EDIT_SESSION_NAME_PREFIX = "com-oracle-weblogic-management-patching";
   }
}
