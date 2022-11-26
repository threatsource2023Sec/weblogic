package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;

public class StartServerAdminModeOnRevertCommand extends ServerLifeCycleBase implements CommandRevertInterface {
   private static final long serialVersionUID = 4349239090624915010L;
   @SharedState
   public transient String currentMachine;

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      boolean isStrict = false;
      this.startServerAdminMode(this.currentMachine, isStrict);
      PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      return true;
   }

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logSkippedStep(workflowId, className, logTarget);
      return true;
   }
}
