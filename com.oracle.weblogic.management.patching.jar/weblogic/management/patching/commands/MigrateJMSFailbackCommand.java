package weblogic.management.patching.commands;

import weblogic.management.patching.model.JMSInfo;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;

public class MigrateJMSFailbackCommand extends MigrationBase implements CommandRevertInterface {
   private static final long serialVersionUID = 8262100465793902765L;
   @SharedState
   JMSInfo jmsInfo;
   @SharedState
   String destination;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      boolean result = this.migrateJMS(this.jmsInfo, this.destination, true);
      if (result) {
         PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      } else {
         PatchingLogger.logFailedStepNoError(workflowId, className, logTarget);
      }

      return result;
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      boolean result = this.migrateJMS(this.jmsInfo, this.destination, true);
      if (result) {
         PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      } else {
         PatchingLogger.logFailedRevertStepNoError(workflowId, className, logTarget);
      }

      return result;
   }
}
