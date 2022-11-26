package weblogic.management.patching.commands;

import weblogic.management.workflow.command.CommandInterface;

public class WaitForCoherenceServicesHAStatusExecCommand extends WaitForCoherenceServicesHAStatusBaseCommand implements CommandInterface {
   private static final long serialVersionUID = 7437998567366634751L;

   public boolean execute() throws Exception {
      if (this.startTime.getValue() == null) {
         this.startTime.setValue(Long.toString(System.currentTimeMillis()));
      }

      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      this.debug("The targeted HA Status is " + this.haStatusTarget);
      PatchingLogger.logExecutingStep(workflowId, className, this.serverName);
      boolean result = this.waitForHAStatus();
      if (result) {
         PatchingLogger.logCompletedStep(workflowId, className, this.serverName);
         return true;
      } else {
         PatchingLogger.logFailedStepNoError(workflowId, className, this.serverName);
         throw new CommandException(PatchingMessageTextFormatter.getInstance().timoutOnCoherenceServiceHaStatusWait());
      }
   }
}
