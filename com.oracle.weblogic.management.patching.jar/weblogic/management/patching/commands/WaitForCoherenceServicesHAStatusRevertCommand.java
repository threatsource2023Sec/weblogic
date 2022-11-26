package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandRevertInterface;

public class WaitForCoherenceServicesHAStatusRevertCommand extends WaitForCoherenceServicesHAStatusBaseCommand implements CommandRevertInterface {
   private static final long serialVersionUID = -5889422497512858125L;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logSkippedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
      if (this.startTime.getValue() == null) {
         this.startTime.setValue(Long.toString(System.currentTimeMillis()));
      }

      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      this.debug("The targeted HA Status is " + this.haStatusTarget);
      PatchingLogger.logExecutingStep(workflowId, className, this.serverName);
      boolean result = this.waitForHAStatus();
      if (result) {
         PatchingLogger.logCompletedRevertStep(workflowId, className, this.serverName);
         return true;
      } else {
         PatchingLogger.logFailedStepNoError(workflowId, className, this.serverName);
         throw new CommandException(PatchingMessageTextFormatter.getInstance().timoutOnCoherenceServiceHaStatusWait());
      }
   }
}
