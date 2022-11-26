package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;

public class ClusterUpdateFailoverGroupOnRevertCommand extends ClusterUpdateFailoverGroupBase implements CommandRevertInterface {
   private static final long serialVersionUID = -6574581074170438793L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.clusterName;
      PatchingLogger.logSkippedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws CommandException {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.clusterName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      this.revertFailoverGroups();
      PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      return true;
   }
}
