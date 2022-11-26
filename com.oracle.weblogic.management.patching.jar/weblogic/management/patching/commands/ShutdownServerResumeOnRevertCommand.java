package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRetryInterface;
import weblogic.management.workflow.command.CommandRevertInterface;

public class ShutdownServerResumeOnRevertCommand extends ServerLifeCycleBase implements CommandRevertInterface, CommandRetryInterface {
   private static final long serialVersionUID = -3478837519602517696L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.shutdownServer();
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      boolean isStrict = false;
      this.resumeServer(isStrict);
      PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      return true;
   }

   public boolean retry() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.shutdownServerWithFailureHandling();
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }
}
