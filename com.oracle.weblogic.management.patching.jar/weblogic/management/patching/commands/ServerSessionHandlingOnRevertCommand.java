package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;

public class ServerSessionHandlingOnRevertCommand extends ServerSessionHandlingBase implements CommandRevertInterface {
   private static final long serialVersionUID = -8547423692168049390L;

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      this.enableSessionHandling(this.castFailoverGroups(this.origFailoverGroups));
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
