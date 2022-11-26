package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;

public class PartitionOnClusterUpdateFailoverGroupCommand extends PartitionOnClusterUpdateFailoverGroupBase implements CommandRevertInterface {
   private static final long serialVersionUID = -3865498564193734506L;

   public boolean execute() throws CommandException {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.clusterName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.updateFailoverGroups();
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
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
