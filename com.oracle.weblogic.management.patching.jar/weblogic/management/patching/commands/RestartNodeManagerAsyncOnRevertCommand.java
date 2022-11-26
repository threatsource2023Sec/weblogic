package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;

public class RestartNodeManagerAsyncOnRevertCommand extends AbstractRestartNodeManagerCommand implements CommandRevertInterface {
   private static final long serialVersionUID = 8153057045715580902L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logSkippedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
      boolean success = true;
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      this.restartAsync(className);
      PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      return true;
   }
}
