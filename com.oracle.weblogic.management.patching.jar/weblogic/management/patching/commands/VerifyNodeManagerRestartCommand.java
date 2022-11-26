package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;

public class VerifyNodeManagerRestartCommand extends AbstractRestartNodeManagerCommand implements CommandRevertInterface {
   private static final long serialVersionUID = 3461095287137835405L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.verifyNM(className);
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
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
