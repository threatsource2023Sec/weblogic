package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;

public class ShutdownServerCommand extends ServerCommandBase implements CommandRevertInterface {
   private static final long serialVersionUID = -380394846642059115L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      boolean result = this.shutdownServer();
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
      boolean result = this.startServer();
      if (result) {
         PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      } else {
         PatchingLogger.logFailedRevertStepNoError(workflowId, className, logTarget);
      }

      return result;
   }
}
