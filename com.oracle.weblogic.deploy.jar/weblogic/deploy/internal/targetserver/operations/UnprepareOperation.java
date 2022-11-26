package weblogic.deploy.internal.targetserver.operations;

import weblogic.deploy.internal.InternalDeploymentData;
import weblogic.deploy.internal.TargetHelper;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.deploy.DeployerRuntimeTextTextFormatter;
import weblogic.management.deploy.internal.SlaveDeployerLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;

public final class UnprepareOperation extends AbstractOperation {
   protected int cbTag;
   private final String[] moduleIds;

   public UnprepareOperation(long requestId, String taskId, InternalDeploymentData internalDeploymentData, BasicDeploymentMBean basicDeploymentMBean, DomainMBean proposedDomain, AuthenticatedSubject initiator, boolean requiresRestart) throws DeploymentException {
      super(requestId, taskId, internalDeploymentData, basicDeploymentMBean, proposedDomain, initiator, requiresRestart);
      this.operation = 5;
      this.moduleIds = TargetHelper.getModulesForTarget(this.deploymentData, proposedDomain);
      this.controlOperation = true;
   }

   protected void doPrepare() throws DeploymentException {
      this.ensureAppContainerSet();
   }

   protected void doCommit() throws DeploymentException {
      if (this.appcontainer != null) {
         if (this.getState(this.appcontainer) >= 1) {
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

               if (!this.isAdminMode()) {
                  if (this.isAdminState(this.appcontainer)) {
                     this.silentDeactivate(this.appcontainer);
                  }

                  this.silentUnprepare(this.appcontainer);
               }
            }
         }
      } else {
         SlaveDeployerLogger.logNoDeployment(DeployerRuntimeTextTextFormatter.getInstance().messageStop(), this.mbean.getName());
      }

      this.complete(3, (Exception)null);
   }
}
