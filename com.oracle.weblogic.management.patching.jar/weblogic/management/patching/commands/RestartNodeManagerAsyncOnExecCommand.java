package weblogic.management.patching.commands;

public class RestartNodeManagerAsyncOnExecCommand extends AbstractRestartNodeManagerCommand {
   private static final long serialVersionUID = 1938360269498045380L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.restartAsync(className);
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }
}
