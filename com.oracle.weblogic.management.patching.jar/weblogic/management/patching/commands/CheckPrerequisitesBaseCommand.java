package weblogic.management.patching.commands;

import java.io.IOException;
import java.security.AccessController;
import java.util.Map;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.patching.MessageCallback;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.patching.ZDTInvoker;
import weblogic.management.patching.model.DomainModel;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;
import weblogic.nodemanager.mbean.NodeManagerRuntime;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public abstract class CheckPrerequisitesBaseCommand extends AbstractCommand {
   private static final long serialVersionUID = 8191539045653806980L;
   public static final String ACTION_PARAM = "ACTION";
   public static final String ACTION_CHECKREQ = "checkreq";
   public static final String PATCHED_PARAM = "PATCHED";
   public static final String BACKUP_PARAM = "BACKUP_DIR";
   public static final String NEW_JAVA_HOME_PARAM = "NEW_JAVA_HOME";
   public static final String VERBOSE_PARAM = "VERBOSE";
   public static final String DOMAIN_MODEL_KEY = "domainModel";
   public static final String LOG_TARGET = "logTarget";
   public static final String WORKFLOW_ID_PARAM = "WORKFLOW_ID";
   @SharedState
   public transient DomainModel domainModel;
   @SharedState
   public transient String logTarget;
   @SharedState
   public transient Boolean useNM = true;
   protected static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public boolean execute() throws Exception {
      String workflowId = this.getContext().getWorkflowId();
      String className = this.getClass().getName();
      String logTarget = "All";
      PatchingLogger.logExecutingStep(workflowId, className, logTarget);
      boolean result = false;
      if (this.domainModel == null) {
         PatchingLogger.logFailedStepNoError(workflowId, className, logTarget);
         throw new CommandException(PatchingMessageTextFormatter.getInstance().getFailedToCreateServerMap());
      } else {
         result = true;
         PatchingLogger.logCompletedStep(workflowId, className, logTarget);
         return result;
      }
   }

   public void verifyConnection(String machineName) throws CommandException {
      try {
         if (machineName != null) {
            MachineMBean machine = (new MachineBasedUtils()).getMachineMBean(machineName);
            NodeManagerRuntime nmr = NodeManagerRuntime.getInstance(machine);
            String nmVersion = nmr.getVersion();
            if (nmVersion == null || nmVersion.length() == 0) {
               throw new CommandException(PatchingMessageTextFormatter.getInstance().getNMVersionFailed(machineName));
            }

            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("CheckPrerequisitesBaseCommand Got connected to node manager and got the version " + nmVersion);
            }
         }

      } catch (IOException var5) {
         throw new CommandException(PatchingMessageTextFormatter.getInstance().getNMConnectionError(machineName), var5);
      }
   }

   public boolean executePatchingScript(String machineName, String agentName, Map scriptEnv) throws CommandException {
      long defaultNoTimeout = 0L;
      return this.executePatchingScript(machineName, agentName, scriptEnv, defaultNoTimeout);
   }

   public boolean executePatchingScript(String machineName, String agentName, Map scriptEnv, long timeoutMillis) throws CommandException {
      String workflowId = this.getContext().getWorkflowId();
      String className = this.getClass().getName();
      scriptEnv.put("WORKFLOW_ID", workflowId + "_" + className);
      return !"?ORPHANED?".equals(machineName) ? ZDTInvoker.invokeAgent(Boolean.TRUE, machineName, agentName, scriptEnv, timeoutMillis, new MessageCallback() {
         public String getErrorMessage(String lastTwoLines) {
            return PatchingMessageTextFormatter.getInstance().getCheckPathsFailure();
         }
      }) : ZDTInvoker.invokeAgent(Boolean.FALSE, machineName, agentName, scriptEnv, timeoutMillis, new MessageCallback() {
         public String getErrorMessage(String lastTwoLines) {
            return PatchingMessageTextFormatter.getInstance().getCheckPathsFailure();
         }
      });
   }

   protected boolean isWindowsDrive(String path) {
      return path.contains(":\\") || path.contains(":/");
   }

   protected boolean validatePathWithNodeManager(String machineName, String scriptName, Map scriptEnv) throws CommandException {
      boolean result = false;
      if (machineName != null && !machineName.isEmpty()) {
         if (scriptName != null && !scriptName.isEmpty()) {
            if (scriptEnv != null && !scriptEnv.isEmpty()) {
               result = this.executePatchingScript(machineName, scriptName, scriptEnv);
               return result;
            } else {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("validatePathWithNodeManager called with null scriptEnv");
               }

               return result;
            }
         } else {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("validatePathWithNodeManager called with null scriptName");
            }

            return result;
         }
      } else {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("validatePathWithNodeManager called with null machineName");
         }

         return result;
      }
   }
}
