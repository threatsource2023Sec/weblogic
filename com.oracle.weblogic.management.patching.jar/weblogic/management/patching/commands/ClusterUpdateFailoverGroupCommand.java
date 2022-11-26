package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.WorkflowContext;

public class ClusterUpdateFailoverGroupCommand extends ClusterUpdateFailoverGroupBase implements CommandRevertInterface {
   private static final long serialVersionUID = -7164478808744262240L;

   public void initialize(WorkflowContext workFlowContext) {
      super.initialize(workFlowContext);
   }

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
