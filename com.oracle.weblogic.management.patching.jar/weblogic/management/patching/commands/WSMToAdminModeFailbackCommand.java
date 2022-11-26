package weblogic.management.patching.commands;

import java.util.Map;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;

public class WSMToAdminModeFailbackCommand extends MigrationBase implements CommandRevertInterface {
   private static final long serialVersionUID = -7868886796377406398L;
   @SharedState
   String destination;
   @SharedState
   public transient Map originalMachineName;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      this.migrateServerToAdminMode(this.destination, (String)this.originalMachineName.get(this.serverName), true);
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      this.migrateServerToAdminMode((String)this.originalMachineName.get(this.serverName), this.destination, true);
      PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      return true;
   }
}
