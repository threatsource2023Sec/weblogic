package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;

public class ShutdownPartitionCommand extends PartitionLifeCycleBase implements CommandRevertInterface {
   private static final long serialVersionUID = -2076928552782538057L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.shutdownPartition();
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.startPartition();
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }
}
