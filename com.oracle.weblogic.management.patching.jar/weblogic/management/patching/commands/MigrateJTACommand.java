package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;

public class MigrateJTACommand extends MigrationBase implements CommandRevertInterface {
   private static final long serialVersionUID = 1775115982852655054L;
   @SharedState
   String destination;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      boolean result = this.migrateJTA(this.destination);
      if (result) {
         PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      } else {
         PatchingLogger.logFailedStepNoError(workflowId, className, logTarget);
      }

      return result;
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      boolean result = this.migrateJTA(this.serverName);
      if (result) {
         PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      } else {
         PatchingLogger.logFailedRevertStepNoError(workflowId, className, logTarget);
      }

      return result;
   }
}
