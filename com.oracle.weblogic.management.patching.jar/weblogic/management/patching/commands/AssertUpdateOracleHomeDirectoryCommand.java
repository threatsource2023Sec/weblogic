package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;

public class AssertUpdateOracleHomeDirectoryCommand extends UpdateOracleHomeDirectoryBaseCommand implements CommandRevertInterface {
   private static final long serialVersionUID = -2572807670485188761L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.assertOracleHomeDirectorySwitch();
      this.directorySwitchPerformed.setValue(true);
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      boolean isRollback = true;
      this.prepareUpdateOracleHomeDirectory(isRollback);
      PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      return true;
   }
}
