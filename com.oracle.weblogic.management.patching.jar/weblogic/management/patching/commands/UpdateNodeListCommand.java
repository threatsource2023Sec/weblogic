package weblogic.management.patching.commands;

import java.util.List;
import weblogic.management.patching.model.Node;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;

public class UpdateNodeListCommand extends AbstractCommand implements CommandRevertInterface {
   private static final long serialVersionUID = -1763214509212262773L;
   @SharedState
   public transient List updatedNodes;
   @SharedState
   public transient Node node;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = null;
      if (this.node != null) {
         logTarget = this.node.getNodeName();
      }

      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      boolean success = false;
      if (this.updatedNodes != null && this.node != null && this.updatedNodes.add(this.node.getMachineInfo())) {
         success = true;
         PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      } else {
         PatchingLogger.logFailedStepNoError(workflowId, className, logTarget);
      }

      return success;
   }

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = null;
      if (this.node != null) {
         logTarget = this.node.getNodeName();
      }

      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      boolean success = false;
      if (this.updatedNodes != null && this.node != null && this.updatedNodes.remove(this.node.getMachineInfo())) {
         success = true;
         PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      } else {
         PatchingLogger.logFailedRevertStepNoError(workflowId, className, logTarget);
      }

      return success;
   }
}
