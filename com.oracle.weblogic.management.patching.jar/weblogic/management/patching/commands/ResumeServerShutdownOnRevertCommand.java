package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;

public class ResumeServerShutdownOnRevertCommand extends ServerLifeCycleBase implements CommandRevertInterface {
   private static final long serialVersionUID = -7883161533186191903L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.resumeServer();
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      this.shutdownServer();
      PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      return true;
   }
}
