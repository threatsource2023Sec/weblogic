package weblogic.deploy.internal.targetserver.operations;

import weblogic.deploy.internal.InternalDeploymentData;
import weblogic.deploy.internal.targetserver.DeployHelper;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class DeactivateOperation extends AbstractOperation {
   protected int cbTag;

   public DeactivateOperation(long requestId, String taskId, InternalDeploymentData internalDeploymentData, BasicDeploymentMBean basicDeploymentMBean, DomainMBean proposedDomain, AuthenticatedSubject initiator, boolean requiresRestart) throws DeploymentException {
      super(requestId, taskId, internalDeploymentData, basicDeploymentMBean, proposedDomain, initiator, requiresRestart);
      this.operation = 3;
      this.controlOperation = true;
   }

   protected void doCommit() throws DeploymentException {
      try {
         this.deactivate();
         this.complete(3, (Exception)null);
      } catch (ManagementException var2) {
         throw DeployHelper.convertThrowable(var2);
      }
   }

   protected void deactivate() throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug(" Deactivating application = " + this.getApplication().getName());
      }

      this.appcontainer = this.getApplication().findDeployment();
      if (this.appcontainer != null) {
      }

      if (this.appcontainer != null) {
         int state = this.getState(this.appcontainer);
         if (this.isGracefulProductionToAdmin()) {
            if (state == 3) {
               this.gracefulProductionToAdmin(this.appcontainer);
            }
         } else if (state == 3 || state == 2 && this.getApplication().hasPendingGraceful()) {
            this.forceProductionToAdmin(this.appcontainer);
         }

         if (this.getState(this.appcontainer) == 2) {
            this.appcontainer.deactivate(this.deploymentContext);
         }
      }

   }

   protected boolean isDeploymentRequestValidForCurrentServer() {
      return this.isTargetListContainsCurrentServer();
   }
}
