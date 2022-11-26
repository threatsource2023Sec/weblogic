package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;

public class PartitionTargetedRedeployOnRevertCommand extends TargetedRedeployBase implements CommandRevertInterface {
   private static final long serialVersionUID = 3456645575094615967L;
   @SharedState
   protected String partitionName;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logSkippedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      String key = this.partitionName + "_" + this.serverName + "_" + this.applicationName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      if ((Boolean)this.isTargetedRedeployDone.get(key)) {
         this.partitionTargetedRedeploy(this.partitionName);
         PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      } else {
         PatchingLogger.logSkippedStep(workflowId, className, logTarget);
      }

      return true;
   }
}
