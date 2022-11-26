package weblogic.elasticity;

import java.util.List;
import java.util.Map;
import weblogic.elasticity.i18n.ElasticityLogger;
import weblogic.management.workflow.command.SharedState;

public class ScaleUpWork extends AbstractScalingWork {
   @SharedState(
      name = "InterceptorSharedDataConstants_workflow_shared_data_map_key"
   )
   private Map sharedMap;
   private ScalingOperationStatus operationStatus;

   public boolean execute() throws Exception {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("ScaleUpWork:: inside execute()...");
      }

      String workflowId = this.getContext().getWorkflowId();
      int startedCount = 0;
      this.operationStatus = (ScalingOperationStatus)this.sharedMap.get("InterceptorSharedDataConstants_elasticity_scaling_operation_status_key");
      if (this.operationStatus == null) {
         ElasticityLogger.logErrorOperationStatusNull(workflowId);
         return false;
      } else {
         String clusterName = this.operationStatus.getClusterName();
         List candidateClusterMembers = this.operationStatus.getCandidateMemberNames();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Executing ScaleUpWork:: (clusterName = " + clusterName + ")  Candidate Member Names...: " + this.operationStatus.getCandidateMemberNames());
         }

         if (candidateClusterMembers.size() > 0) {
            int additionalServersNeeded = this.operationStatus.getAdditionalServersNeeded();
            int serversAdded = 0;
            if (additionalServersNeeded > 0) {
               serversAdded = this.getWlsDynamicClusterScalingService().updateMaxServerCount(clusterName, additionalServersNeeded);
            }

            int maxScaleCount = this.operationStatus.getAllowedScalingSize();
            if (serversAdded < additionalServersNeeded) {
               maxScaleCount -= additionalServersNeeded - serversAdded;
               ElasticityLogger.logScaleUpRequestAdjusted(workflowId, clusterName, this.operationStatus.getAllowedScalingSize(), maxScaleCount);
            }

            if (maxScaleCount > 0) {
               startedCount = this.performScaling(clusterName, maxScaleCount, true, candidateClusterMembers, this.operationStatus.getScaledMemberNames());
            }
         }

         ElasticityLogger.logScaleUpWorkCompleted(workflowId, clusterName, startedCount);
         return startedCount > 0;
      }
   }
}
