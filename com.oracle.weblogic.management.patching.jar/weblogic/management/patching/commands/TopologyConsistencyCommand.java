package weblogic.management.patching.commands;

import weblogic.management.patching.TopologyInspector;
import weblogic.management.patching.model.DomainModel;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;

public class TopologyConsistencyCommand extends AbstractCommand {
   private static final long serialVersionUID = -8828158528938429981L;
   @SharedState
   private transient DomainModel domainModel;
   @SharedState
   private transient String nodeName;

   public boolean execute() throws Exception {
      String logTarget = this.nodeName;
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);

      try {
         DomainModel originalModel = this.domainModel;
         DomainModel currentModel = TopologyInspector.generateDomainModel();
         DomainModel.compareOriginalTopologyWithCurrentTopology(originalModel, currentModel);
         boolean result = true;
         PatchingLogger.logCompletedStep(workflowId, className, logTarget);
         return result;
      } catch (Exception var7) {
         PatchingLogger.logFailedStepNoError(workflowId, className, logTarget);
         throw var7;
      }
   }
}
