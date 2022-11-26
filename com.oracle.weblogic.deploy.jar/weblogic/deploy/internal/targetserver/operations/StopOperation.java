package weblogic.deploy.internal.targetserver.operations;

import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.internal.InternalDeploymentData;
import weblogic.deploy.internal.TargetHelper;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.deploy.DeployerRuntimeTextTextFormatter;
import weblogic.management.deploy.internal.SlaveDeployerLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;

public final class StopOperation extends AbstractOperation {
   protected int cbTag;
   private String[] moduleIds = null;

   public StopOperation(long requestId, String taskId, InternalDeploymentData internalDeploymentData, BasicDeploymentMBean basicDeploymentMBean, DomainMBean proposedDomain, AuthenticatedSubject initiator, boolean requiresRestart) throws DeploymentException {
      super(requestId, taskId, internalDeploymentData, basicDeploymentMBean, proposedDomain, initiator, requiresRestart);
      this.operation = 8;
      DeploymentOptions options = this.deploymentData.getDeploymentOptions();
      if (options == null || !options.isDisableModuleLevelStartStop()) {
         this.moduleIds = TargetHelper.getModulesForTarget(this.deploymentData, proposedDomain);
      }

      this.controlOperation = true;
   }

   protected void doPrepare() throws DeploymentException {
      this.ensureAppContainerSet();
   }

   protected void doCommit() throws DeploymentException {
      boolean appShutdownOnStop = this.internalDeploymentData.getExternalDeploymentData().getDeploymentOptions().isAppShutdownOnStop();
      if (this.appcontainer != null) {
         if (this.getState(this.appcontainer) == 3 || this.getState(this.appcontainer) == 2 || this.getState(this.appcontainer) == 1) {
            if (this.moduleIds != null) {
               this.stop(this.appcontainer, this.moduleIds);
            } else {
               if (this.getState(this.appcontainer) == 3) {
                  if (this.isGracefulProductionToAdmin()) {
                     this.gracefulProductionToAdmin(this.appcontainer);
                  } else {
                     this.forceProductionToAdmin(this.appcontainer);
                  }
               }

               if (!this.isAdminMode() && !this.getApplication().isGracefulInterrupted()) {
                  if (this.getState(this.appcontainer) == 2) {
                     this.appcontainer.deactivate(this.deploymentContext);
                  }

                  if (appShutdownOnStop) {
                     if (this.isDebugEnabled()) {
                        this.debug("shutting down application " + this.mbean.getName());
                     }

                     this.appcontainer.unprepare(this.deploymentContext);
                     this.appcontainer.remove(this.deploymentContext);
                  }
               }
            }
         }
      } else {
         SlaveDeployerLogger.logNoDeployment(DeployerRuntimeTextTextFormatter.getInstance().messageStop(), this.mbean.getName());
      }

      this.complete(3, (Exception)null);
   }

   protected final boolean isDeploymentRequestValidForCurrentServer() {
      return this.isTargetListContainsCurrentServer();
   }
}
