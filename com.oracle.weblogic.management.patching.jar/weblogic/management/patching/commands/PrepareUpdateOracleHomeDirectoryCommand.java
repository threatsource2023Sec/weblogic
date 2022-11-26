package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;

public class PrepareUpdateOracleHomeDirectoryCommand extends UpdateOracleHomeDirectoryBaseCommand implements CommandRevertInterface {
   private static final long serialVersionUID = 1134449804412743711L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.prepareUpdateOracleHomeDirectory();
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
      boolean success = true;
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      if (this.directorySwitchPerformed.getValue()) {
         boolean isRollback = true;
         this.assertOracleHomeDirectorySwitch(isRollback);
      }

      PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      return true;
   }
}
