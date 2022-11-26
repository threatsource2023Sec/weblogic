package weblogic.management.patching;

import java.security.AccessController;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.ManagementException;
import weblogic.management.patching.commands.DomainModelIterator;
import weblogic.management.patching.commands.PatchingMessageTextFormatter;
import weblogic.management.patching.commands.ServerUtils;
import weblogic.management.patching.model.DomainModel;
import weblogic.management.workflow.WorkflowBuilder;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class RolloutUpdateWorkflowProvider {
   private static final String ROLLBACK_SUFFIX = "_Rollback";
   public static final String DOMAIN = "Domain";
   public static final String DOMAIN_ROLLBACK = "Domain_Rollback";
   public static final String CLUSTER = "Cluster";
   public static final String SERVER = "Server";
   public static final String PARTITION = "Partition";
   public static final boolean zdtMT = new Boolean(System.getProperty("weblogic.zdtMT", "true"));

   public WorkflowBuilder getRolloutUpdateWorkflow(String target, String patchedOracleHome, String backupOracleHome, String isRollbackStr, String javaHome, String applicationPropertiesLoc, String options) throws ManagementException {
      if (PatchingDebugLogger.isDebugEnabled()) {
         String debugString = "RolloutUpdateWorkflowProvider.getRolloutUpdateWorkflow called with params:  target = " + target + " patchedOracleHome = " + patchedOracleHome + " backupOracleHome = " + backupOracleHome + " isRollback = " + isRollbackStr + " javaHome = " + javaHome + " applicationProperties = " + applicationPropertiesLoc + " options = " + options;
         PatchingDebugLogger.debug(debugString);
      }

      RolloutPrimitiveFactory.getInstance().assertString("target", target);
      RolloutUpdateSettings rolloutUpdateSettings = new RolloutUpdateSettings(target, patchedOracleHome, backupOracleHome, isRollbackStr, javaHome, applicationPropertiesLoc, options);
      rolloutUpdateSettings.setRolloutTargetType(this.calculateRolloutType(target, rolloutUpdateSettings.isRollback()));
      rolloutUpdateSettings.validateSettings();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("rolloutUpdate rolloutUpdateSettings.isUpdateApplications: " + rolloutUpdateSettings.isUpdateApplications());
      }

      if (rolloutUpdateSettings.isUpdateApplications()) {
         try {
            List applicationPropertiesList = (new ApplicationPropertiesReader()).readApplicationProperties(applicationPropertiesLoc, target);
            rolloutUpdateSettings.setApplicationPropertyList(applicationPropertiesList);
         } catch (Exception var11) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Error loading application properties", var11);
            }

            String propType = PatchingMessageTextFormatter.getInstance().getApplicationString();
            throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().cannotLoadProps(propType, applicationPropertiesLoc, var11));
         }
      }

      DomainModel domainModel = this.generateDomainModel();
      domainModel.setTarget(rolloutUpdateSettings);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("RolloutUpdateWorkflowProvider.setExtensionManager creating the Extension Manager\n");
      }

      if (rolloutUpdateSettings.hasExtensions()) {
         rolloutUpdateSettings.setExtensionManager(ServerUtils.getServer(domainModel.getAdminServer().getServerName()).getRootDirectory());
         rolloutUpdateSettings.validateExtensions();
      }

      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("RolloutUpdateWorkflowProvider.getRolloutUpdateWorkflow built domain model \n" + domainModel.printContents());
         PatchingDebugLogger.debug("RolloutUpdateWorkflowProvider.getRolloutUpdateWorkflow printing targets only \n" + DomainModelIterator.printTargets(domainModel));
      }

      if (!this.isRolloutTypeMT(domainModel)) {
         return (new RolloutUpdatePrimitiveFactory()).getRolloutUpdatePrimitive(rolloutUpdateSettings, domainModel);
      } else {
         rolloutUpdateSettings.setMTRollout(true);
         if ((rolloutUpdateSettings.isRollingRestart() || rolloutUpdateSettings.isUpdateApplications()) && !rolloutUpdateSettings.isUpdateOracleHome() && !rolloutUpdateSettings.isUpdateJavaHome()) {
            return (new RolloutUpdateMTPrimitiveFactory()).getRolloutUpdatePrimitive(rolloutUpdateSettings, domainModel);
         } else {
            throw new ManagementException(PatchingMessageTextFormatter.getInstance().invalidRolloutType());
         }
      }
   }

   public WorkflowBuilder getRolloutOracleHomeWorkflow(String target, String rolloutOracleHome, String backupOracleHome, String isRollback, String options) throws ManagementException {
      boolean isRollbackBoolean = Boolean.parseBoolean(isRollback);
      String rolloutType = this.calculateRolloutType(target, isRollbackBoolean);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("PatchingPrimitiveFactory.getRolloutOracleHomeWorkflow using rolloutType: " + rolloutType);
      }

      String javaHome = null;
      String applicationProperties = null;
      return this.getRolloutUpdateWorkflow(target, rolloutOracleHome, backupOracleHome, isRollback, (String)javaHome, (String)applicationProperties, options);
   }

   public WorkflowBuilder getRolloutJavaHomeWorkflow(String target, String javaHome, String options) throws ManagementException {
      String rolloutType = this.calculateRolloutType(target);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("PatchingPrimitiveFactory.getRolloutJavaHomeWorkflow using rolloutType: " + rolloutType + ", and options: " + options);
      }

      String rolloutOracleHome = null;
      String backupOracleHome = null;
      String isRollback = null;
      String applicationProperties = null;
      return this.getRolloutUpdateWorkflow(target, (String)rolloutOracleHome, (String)backupOracleHome, (String)isRollback, javaHome, (String)applicationProperties, options);
   }

   public WorkflowBuilder getRolloutApplicationsWorkflow(final String target, final String applicationProperties, final String options) throws ManagementException {
      final String rolloutOracleHome = null;
      final String backupOracleHome = null;
      final String isRollback = null;
      final String javaHome = null;
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(kernelId).createComponentInvocationContext("DOMAIN");

      try {
         return (WorkflowBuilder)ComponentInvocationContextManager.runAs(kernelId, cic, new Callable() {
            public WorkflowBuilder call() throws Exception {
               return RolloutUpdateWorkflowProvider.this.getRolloutUpdateWorkflow(target, (String)rolloutOracleHome, (String)backupOracleHome, (String)isRollback, (String)javaHome, applicationProperties, options);
            }
         });
      } catch (ExecutionException var11) {
         throw new ManagementException(PatchingMessageTextFormatter.getInstance().createWorkflowFailed(target, var11));
      }
   }

   public WorkflowBuilder getRollingRestartWorkflow(final String target, final String options) throws ManagementException {
      String rolloutType = this.calculateRolloutType(target);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("PatchingPrimitiveFactory.getRollingRestartWorkflow using rolloutUpdatePrimitiveFactory rolloutType: " + rolloutType);
      }

      final String rolloutOracleHome = null;
      final String backupOracleHome = null;
      final String isRollback = null;
      final String javaHome = null;
      final String applicationProperties = null;
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(kernelId).createComponentInvocationContext("DOMAIN");

      try {
         return (WorkflowBuilder)ComponentInvocationContextManager.runAs(kernelId, cic, new Callable() {
            public WorkflowBuilder call() throws Exception {
               return RolloutUpdateWorkflowProvider.this.getRolloutUpdateWorkflow(target, (String)rolloutOracleHome, (String)backupOracleHome, (String)isRollback, (String)javaHome, (String)applicationProperties, options);
            }
         });
      } catch (ExecutionException var12) {
         throw new ManagementException(PatchingMessageTextFormatter.getInstance().createWorkflowFailed(target, var12));
      }
   }

   public String calculateRolloutType(String targets) throws ManagementException {
      boolean rollbackDefault = false;
      return this.calculateRolloutType(targets, rollbackDefault);
   }

   public String calculateRolloutType(String targets, boolean isRollback) throws ManagementException {
      String rolloutType = null;
      TopologyInspector.TargetType targetType = TopologyInspector.calculateTargetType(targets);
      if (targetType == TopologyInspector.TargetType.DOMAIN && isRollback) {
         rolloutType = "Domain_Rollback";
      } else if (targetType == TopologyInspector.TargetType.DOMAIN) {
         rolloutType = "Domain";
      } else if (targetType == TopologyInspector.TargetType.CLUSTER) {
         rolloutType = "Cluster";
      } else if (targetType == TopologyInspector.TargetType.SERVER) {
         rolloutType = "Server";
      } else if (targetType == TopologyInspector.TargetType.PARTITION) {
         rolloutType = "Partition";
      }

      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("RolloutPrimitiveFactory.calculateRolloutType returning: " + rolloutType);
      }

      return rolloutType;
   }

   protected DomainModel generateDomainModel() throws ManagementException {
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("PatchingPrimitiveFactory.getRolloutDirectoryPrimitive calling TopologyInspector to create domain model");
      }

      DomainModel domainModel = TopologyInspector.generateDomainModel();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("PatchingPrimitiveFactory.getRolloutDirectoryPrimitive TopologyInspector built domain model " + domainModel);
      }

      return domainModel;
   }

   protected boolean isRolloutTypeMT(DomainModel domainModel) throws ManagementException {
      return false;
   }
}
