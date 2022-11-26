package weblogic.management.patching;

import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.ManagementException;
import weblogic.management.patching.commands.PatchingMessageTextFormatter;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.workflow.WorkflowBuilder;
import weblogic.management.workflow.WorkflowLifecycleManager;
import weblogic.management.workflow.WorkflowProgress;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.annotation.AdminServer;

@Service
@AdminServer
public class RolloutService {
   private static final String SERVICE_NAME = "rollout";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   @Inject
   private WorkflowLifecycleManager workflowManager;
   private RolloutServiceFacadeMBean patchingServiceMBean;

   @PostConstruct
   void initMBeanFacade() {
      try {
         if (ManagementService.getDomainAccess(kernelId) != null) {
            DomainRuntimeMBean domainRuntimeMBean = ManagementService.getDomainAccess(kernelId).getDomainRuntime();
            if (domainRuntimeMBean != null) {
               this.patchingServiceMBean = new RolloutServiceFacadeMBean(this);
            }
         }

      } catch (ManagementException var2) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Got ManagementException while RolloutService.initMBeanFacade(): ", var2);
         }

         throw new RuntimeException(var2);
      } catch (Exception var3) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Got Exception while RolloutService.initMBeanFacade(): ", var3);
         }

         throw new RuntimeException(var3);
      }
   }

   public synchronized RolloutProgress rolloutUpdate(String target, String rolloutOracleHome, String backupOracleHome, String isRollback, String javaHome, String applicationProperties, String options) throws ManagementException {
      this.checkActiveWorkflows();
      WorkflowBuilder builder = (new RolloutUpdateWorkflowProvider()).getRolloutUpdateWorkflow(target, rolloutOracleHome, backupOracleHome, isRollback, javaHome, applicationProperties, options);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("RolloutService starting workflow of type: " + RolloutService.RolloutType.ROLLOUT_UPDATE);
      }

      RolloutProgressImpl progress = new RolloutProgressImpl();
      this.workflowManager.initWorkflow(builder, "rollout", progress);
      progress.setWorkflowType(RolloutService.RolloutType.ROLLOUT_UPDATE.toString());
      progress.setWorkflowTarget(target);
      this.workflowManager.executeWorkflow(progress);
      return progress;
   }

   public synchronized RolloutProgress rolloutOracleHome(String target, String rolloutOracleHome, String backupOracleHome, String isRollback, String options) throws ManagementException {
      this.checkActiveWorkflows();
      WorkflowBuilder builder = (new RolloutUpdateWorkflowProvider()).getRolloutOracleHomeWorkflow(target, rolloutOracleHome, backupOracleHome, isRollback, options);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("RolloutService starting workflow of type: " + RolloutService.RolloutType.ROLLOUT_ORACLE_HOME);
      }

      RolloutProgressImpl progress = new RolloutProgressImpl();
      this.workflowManager.initWorkflow(builder, "rollout", progress);
      progress.setWorkflowType(RolloutService.RolloutType.ROLLOUT_ORACLE_HOME.toString());
      progress.setWorkflowTarget(target);
      this.workflowManager.executeWorkflow(progress);
      return progress;
   }

   public synchronized RolloutProgress initializeRolloutOracleHome(String target, String rolloutOracleHome, String backupOracleHome, String isRollback, String options) throws ManagementException {
      this.checkActiveWorkflows();
      WorkflowBuilder builder = (new RolloutUpdateWorkflowProvider()).getRolloutOracleHomeWorkflow(target, rolloutOracleHome, backupOracleHome, isRollback, options);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("RolloutService starting workflow of type: " + RolloutService.RolloutType.ROLLOUT_ORACLE_HOME);
      }

      RolloutProgressImpl progress = new RolloutProgressImpl();
      this.workflowManager.initWorkflow(builder, "rollout", progress);
      progress.setWorkflowType(RolloutService.RolloutType.ROLLOUT_ORACLE_HOME.toString());
      progress.setWorkflowTarget(target);
      return progress;
   }

   public synchronized RolloutProgress rolloutJavaHome(String target, String javaHome, String options) throws ManagementException {
      this.checkActiveWorkflows();
      WorkflowBuilder builder = (new RolloutUpdateWorkflowProvider()).getRolloutJavaHomeWorkflow(target, javaHome, options);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("RolloutService starting workflow of type: " + RolloutService.RolloutType.ROLLOUT_JAVA_HOME);
      }

      RolloutProgressImpl progress = new RolloutProgressImpl();
      this.workflowManager.initWorkflow(builder, "rollout", progress);
      progress.setWorkflowType(RolloutService.RolloutType.ROLLOUT_JAVA_HOME.toString());
      progress.setWorkflowTarget(target);
      this.workflowManager.executeWorkflow(progress);
      return progress;
   }

   public synchronized RolloutProgress rolloutApplications(String target, String applicationProperties, String options) throws ManagementException {
      String partitionName = this.getPartitionInfo();
      this.checkActiveWorkflows();
      if (this.isPartitionContext() && !target.equals(partitionName)) {
         throw new ManagementException(PatchingMessageTextFormatter.getInstance().invalidOperation(target, partitionName));
      } else {
         WorkflowBuilder builder = (new RolloutUpdateWorkflowProvider()).getRolloutApplicationsWorkflow(target, applicationProperties, options);
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("RolloutService starting workflow of type: " + RolloutService.RolloutType.ROLLOUT_APPLICATIONS);
         }

         RolloutProgressImpl progress = new RolloutProgressImpl();
         this.workflowManager.initWorkflow(partitionName, builder, "rollout", progress);
         progress.setWorkflowType(RolloutService.RolloutType.ROLLOUT_APPLICATIONS.toString());
         progress.setWorkflowTarget(target);
         this.workflowManager.executeWorkflow(progress);
         return progress;
      }
   }

   public synchronized RolloutProgress rollingRestart(String target, String options) throws ManagementException {
      String partitionName = this.getPartitionInfo();
      this.checkActiveWorkflows();
      if (this.isPartitionContext() && !target.equals(partitionName)) {
         throw new ManagementException(PatchingMessageTextFormatter.getInstance().invalidOperation(target, partitionName));
      } else {
         WorkflowBuilder builder = (new RolloutUpdateWorkflowProvider()).getRollingRestartWorkflow(target, options);
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("RolloutService starting workflow of type: " + RolloutService.RolloutType.ROLLING_RESTART);
         }

         RolloutProgressImpl progress = new RolloutProgressImpl();
         this.workflowManager.initWorkflow(partitionName, builder, "rollout", progress);
         progress.setWorkflowType(RolloutService.RolloutType.ROLLING_RESTART.toString());
         progress.setWorkflowTarget(target);
         this.workflowManager.executeWorkflow(progress);
         return progress;
      }
   }

   public synchronized WorkflowProgress shutdownServer(String serverName, int sessionTimeout, boolean ignoreSessions, boolean waitForAllSessions) throws ManagementException {
      this.checkActiveWorkflows();
      WorkflowBuilder builder = RolloutPrimitiveFactory.getInstance().getShutdownServerPrimitive(serverName, sessionTimeout, ignoreSessions, waitForAllSessions, (ArrayList)null, (ArrayList)null);
      return this.workflowManager.startWorkflow(builder, "rollout");
   }

   public synchronized WorkflowProgress startServer(String serverName, int sessionTimeout, boolean ignoreSessions, boolean waitForAllSessions) throws ManagementException {
      this.checkActiveWorkflows();
      boolean waitForAllSessionsOnRevert = true;
      WorkflowBuilder builder = RolloutPrimitiveFactory.getInstance().getStartServerPrimitive(serverName, sessionTimeout, ignoreSessions, waitForAllSessionsOnRevert);
      return this.workflowManager.startWorkflow(builder, "rollout");
   }

   public synchronized WorkflowProgress restartNodeManager(String machineName, Boolean isAdminServer, long timeoutMillis) throws ManagementException {
      this.checkActiveWorkflows();
      WorkflowBuilder builder = RolloutPrimitiveFactory.getInstance().getRestartNodeManagerPrimitive(machineName, isAdminServer, timeoutMillis);
      return this.workflowManager.startWorkflow(builder, "rollout");
   }

   public synchronized WorkflowProgress execScript(String machineName, String scriptName, long timeoutMillis) throws ManagementException {
      this.checkActiveWorkflows();
      WorkflowBuilder builder = RolloutPrimitiveFactory.getInstance().getExecScriptPrimitive(machineName, scriptName, timeoutMillis);
      return this.workflowManager.startWorkflow(builder, "rollout");
   }

   public synchronized WorkflowProgress executeWorkflow(WorkflowProgress progress) {
      this.workflowManager.executeWorkflow(progress);
      return progress;
   }

   public synchronized WorkflowProgress revertWorkflow(WorkflowProgress progress) {
      this.workflowManager.revertWorkflow(progress);
      return progress;
   }

   public WorkflowProgress getWorkflowProgress(String id) {
      WorkflowProgress result = this.workflowManager.getWorkflowProgress(this.getPartitionInfo(), id);
      return result != null && "rollout".equals(result.getServiceName()) ? result : null;
   }

   public boolean canResume(WorkflowProgress progress) {
      return progress.canResume();
   }

   public List getCompleteWorkflows() {
      return this.workflowManager.getCompleteWorkflows(this.getPartitionInfo(), "rollout");
   }

   public WorkflowProgress lookupCompleteWorkflow(String workflowId) {
      return this.workflowManager.lookupCompleteWorkflow(this.getPartitionInfo(), "rollout", workflowId);
   }

   public List getActiveWorkflows() {
      return this.workflowManager.getActiveWorkflows(this.getPartitionInfo(), "rollout");
   }

   public WorkflowProgress lookupActiveWorkflow(String workflowId) {
      return this.workflowManager.lookupActiveWorkflow(this.getPartitionInfo(), "rollout", workflowId);
   }

   public List getInactiveWorkflows() {
      return this.workflowManager.getInactiveWorkflows(this.getPartitionInfo(), "rollout");
   }

   public WorkflowProgress lookupInactiveWorkflow(String workflowId) {
      return this.workflowManager.lookupInactiveWorkflow(this.getPartitionInfo(), "rollout", workflowId);
   }

   public List getAllWorkflows() {
      return this.workflowManager.getAllWorkflows(this.getPartitionInfo(), "rollout");
   }

   public WorkflowProgress lookupAllWorkflow(String workflowId) {
      return this.workflowManager.lookupAllWorkflow(this.getPartitionInfo(), "rollout", workflowId);
   }

   public synchronized void deleteWorkflow(String id) throws IOException {
      this.workflowManager.deleteWorkflow(this.getPartitionInfo(), id);
   }

   public synchronized List getStoppedWorkflows() {
      return this.workflowManager.getStoppedWorkflows(this.getPartitionInfo(), "rollout");
   }

   public WorkflowProgress lookupStoppedWorkflow(String workflowId) {
      return this.workflowManager.lookupStoppedWorkflow(this.getPartitionInfo(), "rollout", workflowId);
   }

   private void checkActiveWorkflows() throws ManagementException {
      String partitionName = this.getPartitionInfo();
      boolean competingWorkflowsRunning = false;
      String workflowId = null;
      List globalactiveWf;
      if (partitionName.equals("DOMAIN")) {
         globalactiveWf = this.workflowManager.getAllDomainAndPartitionWorkflows();
         Iterator var5 = globalactiveWf.iterator();

         while(var5.hasNext()) {
            WorkflowProgress workflowProgress = (WorkflowProgress)var5.next();
            if (workflowProgress.isActive()) {
               competingWorkflowsRunning = true;
               workflowId = workflowProgress.getWorkflowId();
               break;
            }
         }
      } else {
         globalactiveWf = this.workflowManager.getActiveWorkflows();
         if (globalactiveWf != null && !globalactiveWf.isEmpty()) {
            competingWorkflowsRunning = true;
            workflowId = ((WorkflowProgress)globalactiveWf.get(0)).getWorkflowId();
         }

         if (!competingWorkflowsRunning) {
            List activeWf = this.workflowManager.getActiveWorkflows(partitionName, "rollout");
            if (activeWf != null && !activeWf.isEmpty()) {
               competingWorkflowsRunning = true;
               workflowId = ((WorkflowProgress)activeWf.get(0)).getWorkflowId();
            }
         }
      }

      if (competingWorkflowsRunning) {
         throw new ManagementException(PatchingMessageTextFormatter.getInstance().alreadyActiveWorkflow(workflowId));
      }
   }

   private String getPartitionInfo() {
      return ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
   }

   private boolean isPartitionContext() {
      String partitionName = this.getPartitionInfo();
      return !"DOMAIN".equals(partitionName);
   }

   public static enum RolloutType {
      ROLLOUT_UPDATE("rolloutUpdate"),
      ROLLOUT_ORACLE_HOME("rolloutOracleHome"),
      ROLLOUT_JAVA_HOME("rolloutJavaHome"),
      ROLLOUT_APPLICATIONS("rolloutApplications"),
      ROLLING_RESTART("rollingRestart");

      String displayName;

      private RolloutType(String displayName) {
         this.displayName = displayName;
      }

      public String toString() {
         return this.displayName;
      }
   }
}
