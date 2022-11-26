package weblogic.management.patching.commands;

import java.util.HashMap;
import java.util.Map;
import weblogic.management.patching.MessageCallback;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.patching.ZDTInvoker;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;

public class CleanupExtensionArtifactsCommand extends AbstractCommand {
   public static final String ACTION_PARAM = "ACTION";
   public static final String ACTION_CLEANUP = "cleanup";
   public static final String VERBOSE_PARAM = "VERBOSE";
   public static final String WORKFLOW_ID_PARAM = "WORKFLOW_ID";
   public static final String EXTENSION_JARS_PARAM = "EXTENSION_JARS";
   private static final long serialVersionUID = 5597187505104923455L;
   @SharedState
   protected transient long timeoutMillis = 0L;
   @SharedState
   protected transient String machineName;
   @SharedState
   protected transient String extensionJars;
   @SharedState
   protected transient Boolean useNM = true;

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      Map scriptEnv = new HashMap();
      scriptEnv.put("ACTION", "cleanup");
      scriptEnv.put("VERBOSE", String.valueOf(PatchingDebugLogger.isDebugEnabled()));
      scriptEnv.put("WORKFLOW_ID", workflowId + "_" + className);
      scriptEnv.put("EXTENSION_JARS", this.extensionJars);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("cleanupExtensionArtifactsCommand set variables, ACTION: cleanup, WORKFLOW_ID: " + scriptEnv.get("WORKFLOW_ID") + ", " + "EXTENSION_JARS" + ": " + scriptEnv.get("EXTENSION_JARS"));
      }

      ZDTInvoker.invokeAgent(this.useNM, this.machineName, "UpdateOracleHome", scriptEnv, this.timeoutMillis, new MessageCallback() {
         public String getErrorMessage(String lastTwoLines) {
            return PatchingMessageTextFormatter.getInstance().getCleanupExtensionArtifactsFailure(lastTwoLines);
         }
      });
      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }
}
