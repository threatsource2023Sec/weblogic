package weblogic.management.patching.commands;

import java.util.HashMap;
import java.util.Map;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.patching.model.Node;
import weblogic.management.workflow.command.SharedState;

public class CheckRolloutJavaHomePrerequisitesCommand extends CheckPrerequisitesBaseCommand {
   private static final long serialVersionUID = 4171780156463381062L;
   @SharedState
   public transient String newJavaHome;

   public boolean execute() throws Exception {
      boolean result = super.execute();
      if (!result) {
         return result;
      } else {
         String className = this.getClass().getName();
         String workflowId = this.getContext().getWorkflowId();
         PatchingLogger.logExecutingStep(workflowId, className, this.logTarget);

         try {
            this.validatePathExistsOnAffectedNodes();
            result = true;
            PatchingLogger.logCompletedStep(workflowId, className, this.logTarget);
            return result;
         } catch (Exception var5) {
            PatchingLogger.logFailedStepNoError(workflowId, className, this.logTarget);
            throw var5;
         }
      }
   }

   public void validatePathExistsOnAffectedNodes() throws CommandException {
      DomainModelIterator iterator = new DomainModelIterator(this.domainModel);

      String machineName;
      boolean success;
      do {
         if (!iterator.hasNextNode()) {
            return;
         }

         Node n = iterator.nextNode();
         machineName = n.getNodeName();
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("validatePathExistsOnAffectedNodes: Targeting machine: " + machineName);
         }

         success = this.validatePathWithNodeManager(machineName);
      } while(success);

      throw new CommandException(PatchingMessageTextFormatter.getInstance().getFailureValidatingJavaHome(machineName));
   }

   protected boolean validatePathWithNodeManager(String machineName) throws CommandException {
      boolean result = false;
      Map scriptEnv = new HashMap();
      scriptEnv.put("NEW_JAVA_HOME", this.newJavaHome);
      scriptEnv.put("ACTION", "checkreq");
      scriptEnv.put("VERBOSE", String.valueOf(PatchingDebugLogger.isDebugEnabled()));
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("CheckRolloutJavaHomePrerequisitesCommand validatePathWithNodeManager being called with NEW_JAVA_HOME: \"" + this.newJavaHome + "\", " + "ACTION" + ": \"" + "checkreq" + "\"");
      }

      result = this.validatePathWithNodeManager(machineName, "UpdateOracleHome", scriptEnv);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("CheckRolloutJavaHomePrerequisitesCommand validatePathWithNodeManager returning " + result);
      }

      return result;
   }
}
