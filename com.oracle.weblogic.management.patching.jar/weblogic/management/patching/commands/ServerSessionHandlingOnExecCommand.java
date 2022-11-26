package weblogic.management.patching.commands;

public class ServerSessionHandlingOnExecCommand extends ServerSessionHandlingBase {
   private static final long serialVersionUID = 7859403657020537281L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.enableSessionHandling(this.castFailoverGroups(this.failoverGroups));
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }
}
