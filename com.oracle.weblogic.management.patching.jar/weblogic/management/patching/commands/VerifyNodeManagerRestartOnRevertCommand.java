package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;

public class VerifyNodeManagerRestartOnRevertCommand extends AbstractRestartNodeManagerCommand implements CommandRevertInterface {
   private static final long serialVersionUID = -8943812526218872246L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logSkippedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      this.verifyNM(className);
      PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      return true;
   }
}
