package weblogic.management.patching.commands;

public class ResumeServerOnExecCommand extends ServerLifeCycleBase {
   private static final long serialVersionUID = 4932977076208632598L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.resumeServer();
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }
}
