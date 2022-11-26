package weblogic.management.patching.commands;

public class VerifyNodeManagerRestartOnExecCommand extends AbstractRestartNodeManagerCommand {
   private static final long serialVersionUID = 3751847770890471788L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.verifyNM(className);
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }
}
