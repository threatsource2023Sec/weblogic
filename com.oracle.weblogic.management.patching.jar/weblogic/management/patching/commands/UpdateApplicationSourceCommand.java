package weblogic.management.patching.commands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import weblogic.management.patching.MessageCallback;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.patching.ZDTInvoker;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;

public class UpdateApplicationSourceCommand extends AbstractCommand implements CommandRevertInterface {
   private static final long serialVersionUID = 6867305951211927933L;
   protected static final String PATCHING_SCRIPT_DIR = "patching";
   public static final String ACTION_PARAM = "ACTION";
   public static final String ACTION_UPDATE = "update";
   public static final String PATCHED_PARAM = "PATCHED";
   public static final String BACKUP_DIR_PARAM = "BACKUP_DIR";
   public static final String CURRENT_PARAM = "CURRENT";
   public static final String RESUME_PARAM = "RESUME";
   public static final String VERBOSE_PARAM = "VERBOSE";
   public static final String RESUME_VAL;
   public static final String WORKFLOW_ID_PARAM = "WORKFLOW_ID";
   public static final String AFTER_UPDATE_EXTENSION_SCRIPTS_PARAM = "AFTER_UPDATE_EXTENSIONS";
   public static final String BEFORE_UPDATE_EXTENSIONS_SCRIPTS_PARAM = "BEFORE_UPDATE_EXTENSIONS";
   @SharedState
   public transient String machineName;
   @SharedState
   public transient String currentSource;
   @SharedState
   public transient String patched;
   @SharedState
   public transient String backup;
   @SharedState
   protected transient long timeoutMillis = 0L;
   @SharedState
   public transient Boolean useNM = true;
   @SharedState
   public transient Map isAppSourceUpdated;
   @SharedState
   protected transient String beforeUpdateExtensions;
   @SharedState
   protected transient String afterUpdateExtensions;
   private String scriptName;
   private Map scriptEnv;
   private Boolean resume = false;
   protected Properties scriptEnvProps;

   public boolean revert() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logRevertingStep(workflowId, className, logTarget);
      String key = this.currentSource + "_" + this.machineName;
      if ((Boolean)this.isAppSourceUpdated.get(key)) {
         this.updateApplication(this.currentSource, this.backup, this.patched);
         this.isAppSourceUpdated.put(key, false);
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("UpdateApplicationSourceCommand.revert isAppSourceUpdated=false for app " + this.currentSource + " on machine: " + this.machineName);
         }
      }

      PatchingLogger.logCompletedRevertStep(workflowId, className, logTarget);
      return true;
   }

   public boolean resume() throws Exception {
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("UpdateApplicationSourceCommand.resume called for machine: " + this.machineName);
      }

      this.resume = true;
      return this.execute();
   }

   public boolean execute() throws Exception {
      String className = this.getClass().getName();
      String workflowId = this.getContext().getWorkflowId();
      String logTarget = this.machineName;
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      String key = this.currentSource + "_" + this.machineName;
      if (!this.isAppSourceUpdated.containsKey(key)) {
         this.isAppSourceUpdated.put(key, false);
      }

      if (!(Boolean)this.isAppSourceUpdated.get(key)) {
         this.updateApplication(this.currentSource, this.patched, this.backup);
         this.isAppSourceUpdated.put(key, true);
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("UpdateApplicationSourceCommand.execute isAppSourceUpdated=true for app " + this.currentSource + " on machine: " + this.machineName);
         }
      }

      PatchingLogger.logCompletedStep(workflowId, className, logTarget);
      return true;
   }

   protected void updateApplication(String current, String patched, String backup) throws CommandException {
      String workflowId = this.getContext().getWorkflowId();
      String className = this.getClass().getName();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("updateApplication called");
      }

      this.scriptEnv = new HashMap();
      if (this.scriptEnvProps != null) {
         Iterator var6 = this.scriptEnvProps.entrySet().iterator();

         while(var6.hasNext()) {
            Map.Entry entry = (Map.Entry)var6.next();
            this.scriptEnv.put(entry.getKey().toString(), entry.getValue().toString());
         }
      }

      this.scriptEnv.put("ACTION", "update");
      this.scriptEnv.put("CURRENT", current);
      this.scriptEnv.put("PATCHED", patched);
      this.scriptEnv.put("BACKUP_DIR", backup);
      this.scriptEnv.put("VERBOSE", String.valueOf(PatchingDebugLogger.isDebugEnabled()));
      this.scriptEnv.put("WORKFLOW_ID", workflowId + "_" + className);
      this.scriptEnv.put("BEFORE_UPDATE_EXTENSIONS", this.beforeUpdateExtensions);
      this.scriptEnv.put("AFTER_UPDATE_EXTENSIONS", this.afterUpdateExtensions);
      if (this.resume) {
         this.scriptEnv.put("RESUME", RESUME_VAL);
      }

      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("updateApplication set variables, ACTION: " + (String)this.scriptEnv.get("ACTION") + ", " + "CURRENT" + ": " + (String)this.scriptEnv.get("CURRENT") + ", " + "PATCHED" + ": " + (String)this.scriptEnv.get("PATCHED") + ", " + "BACKUP_DIR" + ": " + (String)this.scriptEnv.get("BACKUP_DIR"));
      }

      ZDTInvoker.invokeAgent(this.useNM, this.machineName, "UpdateApplication", this.scriptEnv, this.timeoutMillis, new MessageCallback() {
         public String getErrorMessage(String lastTwoLines) {
            return PatchingMessageTextFormatter.getInstance().getUpdateApplicationFailure(lastTwoLines);
         }
      });
   }

   static {
      RESUME_VAL = Boolean.FALSE.toString();
   }
}
