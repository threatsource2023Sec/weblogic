package weblogic.management.patching.commands;

public class ClusterUpdateFailoverGroupOnExecCommand extends ClusterUpdateFailoverGroupBase {
   private static final long serialVersionUID = -236509215534610883L;

   public boolean execute() throws CommandException {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.clusterName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.updateFailoverGroups();
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }
}
