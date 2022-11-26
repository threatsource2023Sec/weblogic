package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;

public class StartServerCommand extends ServerCommandBase implements CommandRevertInterface {
   private static final long serialVersionUID = -4400203351543322846L;
   @SharedState
   public transient boolean waitForAllSessionsOnRevert = false;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      boolean result = this.startServer();
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
      this.waitForAllSessions = this.waitForAllSessionsOnRevert;
      boolean result = this.shutdownServer();
      if (result) {
         PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      } else {
         PatchingLogger.logFailedRevertStepNoError(workflowId, className, logTarget);
      }

      return result;
   }
}
