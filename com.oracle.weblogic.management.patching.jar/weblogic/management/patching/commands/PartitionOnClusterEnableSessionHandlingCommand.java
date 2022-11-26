package weblogic.management.patching.commands;

public class PartitionOnClusterEnableSessionHandlingCommand extends PartitionOnClusterSessionHandlingBase {
   private static final long serialVersionUID = -1204005647858525496L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.clusterName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.applySessionHandlingSetting(true, this.castFailoverGroups(this.failoverGroups));
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.clusterName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      this.applySessionHandlingSetting(false, this.castFailoverGroups(this.origFailoverGroups));
      PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      return true;
   }
}
