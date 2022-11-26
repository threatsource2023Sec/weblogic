package weblogic.management.patching.commands;

import java.io.IOException;
import java.io.Writer;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.patching.MessageCallback;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.patching.ZDTInvoker;
import weblogic.management.patching.agent.PlatformUtils;
import weblogic.management.provider.ManagementService;
import weblogic.management.workflow.MutableBoolean;
import weblogic.management.workflow.MutableString;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;
import weblogic.nodemanager.mbean.NodeManagerRuntime;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public abstract class UpdateOracleHomeDirectoryBaseCommand extends AbstractCommand {
   private static final long serialVersionUID = -7142655976276130306L;
   public static final String UNIX_ASSERT_SCRIPT_NAME = "UpdateOracleHome.sh";
   public static final String WIN_ASSERT_SCRIPT_NAME = "UpdateOracleHome.cmd";
   public static final String ACTION_PARAM = "ACTION";
   public static final String ACTION_PREPARE = "prepare";
   public static final String ACTION_VALIDATE = "validate";
   public static final String PATCHED_PARAM = "PATCHED";
   public static final String BACKUP_DIR_PARAM = "BACKUP_DIR";
   public static final String REVERT_PARAM = "REVERT_FROM_ERROR";
   public static final String NEW_JAVA_HOME_PARAM = "NEW_JAVA_HOME";
   public static final String VERBOSE_PARAM = "VERBOSE";
   public static final String WORKFLOW_ID_PARAM = "WORKFLOW_ID";
   public static final String AFTER_UPDATE_EXTENSION_SCRIPTS_PARAM = "AFTER_UPDATE_EXTENSIONS";
   public static final String BEFORE_UPDATE_EXTENSIONS_SCRIPTS_PARAM = "BEFORE_UPDATE_EXTENSIONS";
   @SharedState
   protected transient String machineName;
   @SharedState
   protected transient Boolean isAdminServer;
   @SharedState
   protected transient long timeoutMillis = 0L;
   @SharedState
   protected transient String newDirectory;
   @SharedState
   protected transient String newJavaHome;
   @SharedState
   protected transient MutableString serverActivationTime;
   @SharedState
   protected transient String backupDirectory;
   @SharedState
   protected transient String beforeUpdateExtensions;
   @SharedState
   protected transient String afterUpdateExtensions;
   @SharedState
   protected transient Properties scriptEnvProps;
   @SharedState
   protected transient Boolean useNM = true;
   @SharedState
   protected MutableBoolean directorySwitchPerformed;
   protected Map scriptEnv;
   protected Writer writer;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   protected boolean isWindowsDrive(String path) {
      return path.contains(":\\") || path.contains(":/");
   }

   protected void prepareUpdateOracleHomeDirectory() throws CommandException {
      boolean isRevert = false;
      this.prepareUpdateOracleHomeDirectory(isRevert);
   }

   protected void prepareUpdateOracleHomeDirectory(boolean isRevert) throws CommandException {
      String workflowId = this.getContext().getWorkflowId();
      String className = this.getClass().getName();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("prepareUpdateOracleHomeDirectory called, isRevert: " + isRevert);
      }

      this.scriptEnv = new HashMap();
      if (this.scriptEnvProps != null) {
         Iterator var4 = this.scriptEnvProps.entrySet().iterator();

         while(var4.hasNext()) {
            Map.Entry entry = (Map.Entry)var4.next();
            this.scriptEnv.put(entry.getKey().toString(), entry.getValue().toString());
         }
      }

      this.scriptEnv.put("ACTION", "prepare");
      this.scriptEnv.put("VERBOSE", String.valueOf(PatchingDebugLogger.isDebugEnabled()));
      this.scriptEnv.put("WORKFLOW_ID", workflowId + "_" + className);
      this.scriptEnv.put("BEFORE_UPDATE_EXTENSIONS", this.beforeUpdateExtensions);
      this.scriptEnv.put("AFTER_UPDATE_EXTENSIONS", this.afterUpdateExtensions);
      if (!isRevert) {
         this.scriptEnv.put("PATCHED", this.newDirectory);
         this.scriptEnv.put("BACKUP_DIR", this.backupDirectory);
         if (this.newJavaHome != null && !this.newJavaHome.isEmpty()) {
            this.scriptEnv.put("NEW_JAVA_HOME", this.newJavaHome);
         }
      } else {
         this.scriptEnv.put("REVERT_FROM_ERROR", "true");
         this.scriptEnv.put("PATCHED", this.backupDirectory);
         this.scriptEnv.put("BACKUP_DIR", this.newDirectory);
      }

      if (this.isAdminServer) {
         this.serverActivationTime.setValue("" + ServerUtils.getServerActivationTime());
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("prepareUpdateOracleHomeDirectory set adminServerActivationTime to " + this.serverActivationTime.getValue());
         }
      }

      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("prepareUpdateOracleHomeDirectory set variables, ACTION: prepare, PATCHED: " + (String)this.scriptEnv.get("PATCHED") + ", " + "BACKUP_DIR" + ": " + (String)this.scriptEnv.get("BACKUP_DIR") + ", " + "NEW_JAVA_HOME" + ": " + (String)this.scriptEnv.get("NEW_JAVA_HOME") + ", " + "REVERT_FROM_ERROR" + ": " + (isRevert ? "true" : "not set"));
      }

      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("prepareUpdateOracleHomeDirectory calling NodeManager script UpdateOracleHome" + PlatformUtils.getScriptExtension());
         Set keys = this.scriptEnv.keySet();
         if (keys != null) {
            Iterator var8 = keys.iterator();

            while(var8.hasNext()) {
               String key = (String)var8.next();
               PatchingDebugLogger.debug("ScriptEnv " + key + ": " + (String)this.scriptEnv.get(key));
            }
         }
      }

      ZDTInvoker.invokeAgent(this.useNM, this.machineName, "UpdateOracleHome", this.scriptEnv, this.timeoutMillis, new MessageCallback() {
         public String getErrorMessage(String lastTwoLines) {
            return PatchingMessageTextFormatter.getInstance().getUpdateOracleHomeFailure(lastTwoLines);
         }
      });
   }

   private void checkNMReconnect(final NodeManagerRuntime nmr) {
      long reconnectTimeout = 180000L;
      long sleepTime = 2000L;
      StatusPollerResult nmReconnectStatusPoller = new StatusPollerResult() {
         public IOException cache;

         public boolean checkStatus() {
            try {
               nmr.getVersion();
               return true;
            } catch (IOException var2) {
               this.cache = var2;
               return false;
            }
         }

         public String getPollingDescription() {
            return "NMReconnect";
         }

         public IOException getResult() {
            return this.cache;
         }
      };
      if ((new TimedStatusPoller()).pollStatusWithTimeout(reconnectTimeout, sleepTime, nmReconnectStatusPoller) && PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Checking for NMReconnect has exceeded timeout and cache is " + nmReconnectStatusPoller.getResult());
         PatchingDebugLogger.debug("NodeManager must have restarted, but we are temporarily unable to contact it.  Will proceed with next workflow operation");
      }

   }

   protected void assertOracleHomeDirectorySwitch() throws CommandException {
      boolean isRevert = false;
      String adminServerName = ManagementService.getRuntimeAccess(kernelId).getAdminServerName();
      MachineMBean machine = ServerUtils.getServer(adminServerName).getMachine();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Checking " + (machine == null ? "null" : machine.getName()) + " and machineName: " + this.machineName);
      }

      if (machine != null && machine.getName().equalsIgnoreCase(this.machineName)) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Making sure we can reconnect");
         }

         NodeManagerRuntime nmr = NodeManagerRuntime.getInstance(machine);
         this.checkNMReconnect(nmr);
      }

      this.assertOracleHomeDirectorySwitch(isRevert);
   }

   protected void assertOracleHomeDirectorySwitch(boolean isRevert) throws CommandException {
      String workflowId = this.getContext().getWorkflowId();
      String className = this.getClass().getName();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("assertOracleHomeDirectorySwitch called, isRevert: " + isRevert);
      }

      this.scriptEnv = new HashMap();
      if (this.scriptEnvProps != null) {
         Iterator var4 = this.scriptEnvProps.entrySet().iterator();

         while(var4.hasNext()) {
            Map.Entry entry = (Map.Entry)var4.next();
            this.scriptEnv.put(entry.getKey().toString(), entry.getValue().toString());
         }
      }

      this.scriptEnv.put("ACTION", "validate");
      this.scriptEnv.put("VERBOSE", String.valueOf(PatchingDebugLogger.isDebugEnabled()));
      this.scriptEnv.put("WORKFLOW_ID", workflowId + "_" + className);
      if (!isRevert) {
         this.scriptEnv.put("PATCHED", this.newDirectory);
         this.scriptEnv.put("BACKUP_DIR", this.backupDirectory);
      } else {
         this.scriptEnv.put("REVERT_FROM_ERROR", "true");
         this.scriptEnv.put("PATCHED", this.backupDirectory);
         this.scriptEnv.put("BACKUP_DIR", this.newDirectory);
      }

      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("assertOracleHomeDirectorySwitch set variables, ACTION: validate, PATCHED: " + (String)this.scriptEnv.get("PATCHED") + ", " + "BACKUP_DIR" + ": " + (String)this.scriptEnv.get("BACKUP_DIR") + ", " + "REVERT_FROM_ERROR" + ": " + (isRevert ? "true" : "not set"));
      }

      ZDTInvoker.invokeAgent(this.useNM, this.machineName, "UpdateOracleHome", this.scriptEnv, this.timeoutMillis, new MessageCallback() {
         public String getErrorMessage(String lastTwoLines) {
            return PatchingMessageTextFormatter.getInstance().getUpdateOracleHomeFailure(lastTwoLines);
         }
      });
   }
}
