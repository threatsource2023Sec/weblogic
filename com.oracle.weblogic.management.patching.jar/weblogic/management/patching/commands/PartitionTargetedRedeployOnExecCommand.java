package weblogic.management.patching.commands;

import weblogic.management.workflow.command.SharedState;

public class PartitionTargetedRedeployOnExecCommand extends TargetedRedeployBase {
   private static final long serialVersionUID = 72714490056682205L;
   @SharedState
   protected String partitionName;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      String key = this.partitionName + "_" + this.serverName + "_" + this.applicationName;
      this.partitionTargetedRedeploy(this.partitionName);
      this.isTargetedRedeployDone.put(key, true);
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }
}
