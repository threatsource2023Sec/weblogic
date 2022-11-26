package weblogic.management.patching.commands;

public class TargetedRedeployOnExecCommand extends TargetedRedeployBase {
   private static final long serialVersionUID = -8765558273149239185L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      String key = this.serverName + "_" + this.applicationName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.targetedRedeploy();
      this.isTargetedRedeployDone.put(key, true);
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }
}
