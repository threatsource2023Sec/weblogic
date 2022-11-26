package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;

public class EnableElasticityCommand extends ElasticityBaseCommand implements CommandRevertInterface {
   private static final long serialVersionUID = 4367923725631481983L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.clusterName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      boolean result = this.enableElasticity();
      if (result) {
         PatchingLogger.logCompletedStep(workflowId, className, logTarget);
         return true;
      } else {
         PatchingLogger.logFailedStepNoError(workflowId, className, logTarget);
         return false;
      }
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.clusterName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      boolean result = this.disableElasticity();
      if (result) {
         PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
         return true;
      } else {
         PatchingLogger.logFailedRevertStepNoError(workflowId, className, logTarget);
         return false;
      }
   }
}
