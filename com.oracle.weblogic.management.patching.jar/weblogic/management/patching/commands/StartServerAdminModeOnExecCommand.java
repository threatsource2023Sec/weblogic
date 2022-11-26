package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRetryInterface;
import weblogic.management.workflow.command.SharedState;

public class StartServerAdminModeOnExecCommand extends ServerLifeCycleBase implements CommandRetryInterface {
   private static final long serialVersionUID = -5018932549071349165L;
   @SharedState
   public transient String currentMachine;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.startServerAdminMode(this.currentMachine);
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean retry() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);

      try {
         this.retryStartOnFailure(this.currentMachine);
         this.startServerAdminMode(this.currentMachine);
      } catch (Exception var5) {
         this.restoreServerStateForStartCommand(this.currentMachine);
         throw var5;
      }

      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }
}
