package weblogic.elasticity;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import weblogic.elasticity.i18n.ElasticityLogger;
import weblogic.management.provider.DomainAccess;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleTaskRuntimeMBean;
import weblogic.management.workflow.command.SharedState;
import weblogic.server.GlobalServiceLocator;

public class ScaleDownWork extends AbstractScalingWork {
   @SharedState(
      name = "InterceptorSharedDataConstants_workflow_shared_data_map_key"
   )
   private Map sharedMap;
   private ScalingOperationStatus operationStatus;

   public boolean execute() throws Exception {
      int stoppedCount = 0;
      String workflowId = this.getContext().getWorkflowId();
      this.operationStatus = (ScalingOperationStatus)this.sharedMap.get("InterceptorSharedDataConstants_elasticity_scaling_operation_status_key");
      if (this.operationStatus == null) {
         ElasticityLogger.logErrorScaleDownOperationStatusNull(workflowId);
         return false;
      } else {
         String clusterName = this.operationStatus.getClusterName();
         List candidateClusterMembers = this.operationStatus.getCandidateMemberNames();
         int scaleDownAmount = this.operationStatus.getAllowedScalingSize();
         if (scaleDownAmount > 0) {
            stoppedCount = this.performScaling(clusterName, scaleDownAmount, false, candidateClusterMembers, this.operationStatus.getScaledMemberNames());
         }

         ElasticityLogger.logScaleDownWorkCompleted(workflowId, clusterName, stoppedCount);
         return stoppedCount > 0;
      }
   }

   protected boolean handleFailedScalingOperation(String clusterName, String serverName) {
      super.logOnFailedScaling(clusterName, serverName, false);
      String workflowId = this.getContext().getWorkflowId();
      ElasticityLogger.logScaleDownFailedSoTryingForceShutdown(workflowId, serverName, clusterName);
      ServerLifeCycleTaskRuntimeMBean forceShutdownTask = this.getWlsDynamicClusterScalingService().initiateForceShutdown(clusterName, serverName, this);

      while(forceShutdownTask.isRunning()) {
         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var11) {
         }
      }

      String finalStatus = forceShutdownTask.getStatus();
      boolean forceShutdownResult = "TASK COMPLETED".equals(finalStatus);
      if (!forceShutdownResult) {
         Exception shutdownError = forceShutdownTask.getError();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(workflowId + ": force shutdown status == " + finalStatus + ", result is " + forceShutdownResult + ", error: " + shutdownError);
            if (shutdownError != null) {
               shutdownError.printStackTrace();
            }
         }

         DomainAccess domainAccess = (DomainAccess)GlobalServiceLocator.getServiceLocator().getService(DomainAccess.class, new Annotation[0]);
         ServerLifeCycleRuntimeMBean slcRuntime = domainAccess.getDomainRuntime().lookupServerLifeCycleRuntime(serverName);
         if (slcRuntime != null) {
            String serverState = slcRuntime.getState();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(workflowId + ": final server state == " + serverState);
            }

            if ("SHUTDOWN".equals(serverState)) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(workflowId + ": server " + serverName + " is in SHUTDOWN state; server must have shutdown between the return from graceful shutdown and the issuing of the force-shutdown command, force-setting success for scale-down");
               }

               forceShutdownResult = true;
            }
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(workflowId + ": could not find ServerLifeCycleRuntime for server " + serverName);
         }
      }

      return forceShutdownResult;
   }
}
