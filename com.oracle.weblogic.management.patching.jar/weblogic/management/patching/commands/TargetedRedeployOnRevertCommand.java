package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;

public class TargetedRedeployOnRevertCommand extends TargetedRedeployBase implements CommandRevertInterface {
   private static final long serialVersionUID = 2010266658768374074L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logSkippedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      String key = this.serverName + "_" + this.applicationName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      if ((Boolean)this.isTargetedRedeployDone.get(key)) {
         this.targetedRedeploy();
         PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      } else {
         PatchingLogger.logSkippedStep(workflowId, className, logTarget);
      }

      return true;
   }
}
