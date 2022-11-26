package weblogic.management.patching.commands;

import java.util.Map;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;

public class WSMToAdminModeCommand extends MigrationBase implements CommandRevertInterface {
   private static final long serialVersionUID = 3044440504243204555L;
   @SharedState
   String destination;
   @SharedState
   public transient Map originalMachineName;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      String currentMachine = this.getCurrentMachine();
      this.originalMachineName.put(this.serverName, currentMachine);
      this.migrateServerToAdminMode(currentMachine, this.destination, false);
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.serverName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      this.migrateServerToAdminMode(this.destination, (String)this.originalMachineName.get(this.serverName), false);
      PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      return true;
   }
}
