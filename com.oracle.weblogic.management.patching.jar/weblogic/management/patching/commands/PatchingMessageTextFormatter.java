package weblogic.management.patching.commands;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class PatchingMessageTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public PatchingMessageTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.patching.commands.PatchingMessageTextLocalizer", PatchingMessageTextFormatter.class.getClassLoader());
   }

   public PatchingMessageTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.management.patching.commands.PatchingMessageTextLocalizer", PatchingMessageTextFormatter.class.getClassLoader());
   }

   public static PatchingMessageTextFormatter getInstance() {
      return new PatchingMessageTextFormatter();
   }

   public static PatchingMessageTextFormatter getInstance(Locale l) {
      return new PatchingMessageTextFormatter(l);
   }

   public String getRequireAdminServerToFindObj(String arg0) {
      String id = "getRequireAdminServerToFindObj";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidMachine(String arg0) {
      String id = "getInvalidMachine";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToStartServer(String arg0, Throwable arg1) {
      String id = "getFailedToStartServer";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStateInvalid(String arg0, String arg1, String arg2, String arg3) {
      String id = "getStateInvalid";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToShutdownServer(String arg0, Throwable arg1) {
      String id = "getFailedToShutdownServer";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToCreateServerMap() {
      String id = "getFailedToCreateServerMap";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotHAClusterError(String arg0) {
      String id = "getNotHAClusterError";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNMVersionFailed(String arg0) {
      String id = "getNMVersionFailed";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNMConnectionError(String arg0) {
      String id = "getNMConnectionError";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMinimumNMsCheckFailure() {
      String id = "getMinimumNMsCheckFailure";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoNMForServer(String arg0) {
      String id = "getNoNMForServer";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStandAloneServerError(String arg0) {
      String id = "getStandAloneServerError";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidServer(String arg0) {
      String id = "getInvalidServer";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationRedeployTimeout(String arg0, String arg1, long arg2, String arg3) {
      String id = "getApplicationRedeployTimeout";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerShutdownStateTimeout(String arg0, String arg1, long arg2) {
      String id = "getServerShutdownStateTimeout";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerAdminStateTimeout(String arg0, String arg1, long arg2) {
      String id = "getServerAdminStateTimeout";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerLifeCycleTaskTimeout(String arg0, String arg1, long arg2, String arg3) {
      String id = "getServerLifeCycleTaskTimeout";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationNameNotSpecified() {
      String id = "getApplicationNameNotSpecified";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNodeManagerRequiredAdmin() {
      String id = "getNodeManagerRequiredAdmin";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationPatchedLocationNotSpecified(String arg0) {
      String id = "getApplicationPatchedLocationNotSpecified";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidApplicationName(String arg0) {
      String id = "getInvalidApplicationName";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationBackupNotSpecified(String arg0) {
      String id = "getApplicationBackupNotSpecified";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckPathsFailure() {
      String id = "getCheckPathsFailure";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMissingOracleHomeArg(String arg0, String arg1, String arg2) {
      String id = "missingOracleHomeArg";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidArgWithSpace(String arg0, String arg1) {
      String id = "InvalidArgWithSpace";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidTarget(String arg0) {
      String id = "invalidTarget";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String duplicateTargetName(String arg0, String arg1, String arg2) {
      String id = "duplicateTargetName";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidTargetList(String arg0) {
      String id = "invalidTargetList";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String alreadyActiveWorkflow(String arg0) {
      String id = "alreadyActiveWorkflow";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String untargetedServer(String arg0, String arg1, String arg2, String arg3) {
      String id = "untargetedServer";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String domainContainsCluster(String arg0, String arg1) {
      String id = "domainContainsCluster";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String domainContainsNode(String arg0, String arg1) {
      String id = "domainContainsNode";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nodeContainsServerGroup(String arg0, String arg1) {
      String id = "nodeContainsServerGroup";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nullDestination() {
      String id = "nullDestination";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToMigrateTarget(String arg0, String arg1, String arg2) {
      String id = "getFailedToMigrateTarget";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String clusterContainsUntargetedServers(String arg0, int arg1) {
      String id = "clusterContainsUntargetedServers";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nodeContainsUntargetedServers(String arg0) {
      String id = "nodeContainsUntargetedServers";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationFailed(String arg0, Throwable arg1) {
      String id = "getMigrationFailed";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJTSMigrationFailed(String arg0) {
      String id = "getJTSMigrationFailed";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJTSMigrationError(Throwable arg0) {
      String id = "getJTSMigrationError";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String propsFileDoesNotExist(String arg0, String arg1) {
      String id = "propsFileDoesNotExist";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cannotReadFile(String arg0, String arg1) {
      String id = "cannotReadFile";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String emptyProps(String arg0) {
      String id = "emptyProps";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cannotLoadProps(String arg0, String arg1, Throwable arg2) {
      String id = "cannotLoadProps";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cannotLoadExtensionProps(String arg0, String arg1) {
      String id = "cannotLoadExtensionProps";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cannotLoadExtensionConfiguration(String arg0, String arg1) {
      String id = "cannotLoadExtensionConfiguration";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badFormat(String arg0) {
      String id = "badFormat";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badFormatInFile(String arg0, String arg1) {
      String id = "badFormatInFile";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unknownOption(String arg0) {
      String id = "unknownOption";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConflictingStageModes(String arg0, String arg1) {
      String id = "getConflictingStageModes";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String singletonsExist() {
      String id = "singletonsExist";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidMachineName(String arg0) {
      String id = "invalidMachineName";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidMigrationInfo(String arg0) {
      String id = "invalidMigrationInfo";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAutoMigrationError(String arg0) {
      String id = "getAutoMigrationError";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerMigrationError(Throwable arg0) {
      String id = "getServerMigrationError";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMinimumClusterSizeNotMet(String arg0, int arg1, int arg2) {
      String id = "getMinimumClusterSizeNotMet";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidJTAOption() {
      String id = "invalidJTAOption";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWSMJNDIFailed(String arg0) {
      String id = "getWSMJNDIFailed";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTopologyChangeDetectedAtDomainLevel() {
      String id = "getTopologyChangeDetectedAtDomainLevel";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUpdateOracleHomeFailure(String arg0) {
      String id = "getUpdateOracleHomeFailure";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getScriptExecutorExtensionFailure(String arg0) {
      String id = "getScriptExecutorExtensionFailure";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNodeManagerError(String arg0) {
      String id = "getNodeManagerError";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUpdateFailoverGroupsError(String arg0, String arg1) {
      String id = "getUpdateFailoverGroupsError";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExecScriptFailure(String arg0) {
      String id = "getExecScriptFailure";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUpdateApplicationFailure(String arg0) {
      String id = "getUpdateApplicationFailure";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCleanupExtensionArtifactsFailure(String arg0) {
      String id = "getCleanupExtensionArtifactsFailure";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRestartNodeManagerFailure(String arg0, String arg1) {
      String id = "getRestartNodeManagerFailure";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectNodeManagerFailure(String arg0, long arg1) {
      String id = "getConnectNodeManagerFailure";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNullServerRuntimeMBeanError(String arg0) {
      String id = "getNullServerRuntimeMBeanError";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerLifecycleOperationError(String arg0, String arg1) {
      String id = "getServerLifecycleOperationError";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationRedeployFailureMessages(String arg0, String arg1, String arg2) {
      String id = "getApplicationRedeployFailureMessages";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationRedeployToPartitionFailureMessages(String arg0, String arg1, String arg2, String arg3) {
      String id = "getApplicationRedeployToPartitionFailureMessages";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationRedeployFailure(String arg0, String arg1, String arg2) {
      String id = "getApplicationRedeployFailure";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTopologyChangeDetectedAtClusterLevel() {
      String id = "getTopologyChangeDetectedAtClusterLevel";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTopologyChangeDetectedAtNodeLevel() {
      String id = "getTopologyChangeDetectedAtNodeLevel";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTopologyChangeDetectedAtServerGroupLevel() {
      String id = "getTopologyChangeDetectedAtServerGroupLevel";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTopologyChangeDetectedAtServerLevel() {
      String id = "getTopologyChangeDetectedAtServerLevel";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String incompatibleSessionsRequireShutdownTimeout() {
      String id = "incompatibleSessionsRequireShutdownTimeout";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailureValidatingJavaHome(String arg0) {
      String id = "getFailureValidatingJavaHome";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailureValidatingOracleHome(String arg0) {
      String id = "getFailureValidatingOracleHome";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSingleNullFailoverGroup(String arg0, String arg1) {
      String id = "getSingleNullFailoverGroup";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailureValidatingStageModePaths(String arg0, String arg1) {
      String id = "getFailureValidatingStageModePaths";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailureValidatingNoStageModePaths(String arg0, String arg1) {
      String id = "getFailureValidatingNoStageModePaths";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailureValidatingExternalStageModePaths(String arg0, String arg1, String arg2) {
      String id = "getFailureValidatingExternalStageModePaths";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getQuiesceFailed(String arg0, String arg1) {
      String id = "getQuiesceFailed";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String undefinedParameter(String arg0) {
      String id = "undefinedParameter";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cannotLoadAppsProps(String arg0) {
      String id = "cannotLoadAppsProps";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidJTAMigration(String arg0, String arg1) {
      String id = "invalidJTAMigration";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidRolloutTargetType(String arg0) {
      String id = "invalidRolloutTargetType";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidRollbackValue(String arg0) {
      String id = "invalidRollbackValue";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidApplicationsRollback() {
      String id = "invalidApplicationsRollback";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidMachineMBeans(String arg0) {
      String id = "invalidMachineMBeans";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidMigrationToSameMachine() {
      String id = "invalidMigrationToSameMachine";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerNotConfigured(String arg0, String arg1) {
      String id = "getServerNotConfigured";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationString() {
      String id = "getMigrationString";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationString() {
      String id = "getApplicationString";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExtensionString() {
      String id = "getExtensionString";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String missingTarget() {
      String id = "missingTarget";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFileAlreadyExists(String arg0, Throwable arg1) {
      String id = "getFileAlreadyExists";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTmpUpdateScriptNotSet() {
      String id = "getTmpUpdateScriptNotSet";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String failedToStartRG(String arg0, String arg1) {
      String id = "failedToStartRG";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidInitialState(String arg0, String arg1, String arg2) {
      String id = "invalidInitialState";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String rgShutdownError(String arg0, Throwable arg1) {
      String id = "rgShutdownError";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String failedToShutdownRG(String arg0, String arg1) {
      String id = "failedToShutdownRG";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String failedToGetRGState(String arg0, String arg1) {
      String id = "failedToGetRGState";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidState(String arg0, String arg1, String arg2) {
      String id = "invalidState";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String rgStartError(String arg0, Throwable arg1) {
      String id = "rgStartError";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unableToGetMBean(String arg0) {
      String id = "unableToGetMBean";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResourceGroupLifeCycleTaskTimeout(String arg0, String arg1, long arg2, String arg3) {
      String id = "getResourceGroupLifeCycleTaskTimeout";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClusterConditionNotMet(String arg0) {
      String id = "getClusterConditionNotMet";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMinimumServerConditionNotMet(String arg0, int arg1, int arg2) {
      String id = "getMinimumServerConditionNotMet";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHAGlobalRGRequirementNotMet(String arg0, int arg1, int arg2) {
      String id = "getHAGlobalRGRequirementNotMet";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHAPartitionRequirementNotMet(String arg0, int arg1) {
      String id = "getHAPartitionRequirementNotMet";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHAResourceGroupRequirementNotMet(String arg0, int arg1) {
      String id = "getHAResourceGroupRequirementNotMet";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidOperation(String arg0, String arg1) {
      String id = "invalidOperation";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidRolloutType() {
      String id = "invalidRolloutType";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidRollout() {
      String id = "invalidRollout";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String failedToStartPartition(String arg0, String arg1, Throwable arg2) {
      String id = "failedToStartPartition";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String partitionShutdownError(String arg0, Throwable arg1) {
      String id = "partitionShutdownError";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String failedToShutdownPartition(String arg0, String arg1, Throwable arg2) {
      String id = "failedToShutdownPartition";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String failedToGetPartitionState(String arg0, String arg1) {
      String id = "failedToGetPartitionState";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidPartitionState(String arg0, String arg1, String arg2, String arg3) {
      String id = "invalidPartitionState";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String partitionStartError(String arg0, Throwable arg1) {
      String id = "partitionStartError";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unableToGetPartitionMBean(String arg0) {
      String id = "unableToGetPartitionMBean";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPartitionLifeCycleTaskTimeout(String arg0, String arg1, long arg2, String arg3) {
      String id = "getPartitionLifeCycleTaskTimeout";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidMigrationScenarioSrcDstMachineMatch(String arg0) {
      String id = "invalidMigrationScenarioSrcDstMachineMatch";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String createWorkflowFailed(String arg0, Throwable arg1) {
      String id = "createWorkflowFailed";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String artifactNotFound(String arg0, String arg1) {
      String id = "artifactNotFound";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cannotSupplyBothPartitionAndRGT(String arg0, String arg1) {
      String id = "cannotSupplyBothPartitionAndRGT";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String partitionsNotTargeted(String arg0) {
      String id = "partitionsNotTargeted";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String appsNotFoundInScope(String arg0, String arg1, String arg2) {
      String id = "appsNotFoundInScope";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidScope(String arg0, String arg1, String arg2) {
      String id = "invalidScope";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidArgument(String arg0, String arg1) {
      String id = "invalidArgument";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String timoutOnCoherenceServiceHaStatusWait() {
      String id = "timoutOnCoherenceServiceHaStatusWait";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidCoherenceServiceHAStatus() {
      String id = "invalidCoherenceServiceHAStatus";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidCoherenceServiceHAStatusWaitTimeout() {
      String id = "invalidCoherenceServiceHAStatusWaitTimeout";
      String subsystem = "Patching";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cannotEnforceNodeSafeHAStatus(String arg0) {
      String id = "cannotEnforceNodeSafeHAStatus";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cannotEnforceMachineSafeHAStatus(String arg0) {
      String id = "cannotEnforceMachineSafeHAStatus";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedZDTAgentExecution(String arg0, String arg1, String arg2) {
      String id = "getFailedZDTAgentExecution";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWriteContentsToFileError(String arg0, String arg1, String arg2) {
      String id = "getWriteContentsToFileError";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeleteFileError(String arg0, String arg1) {
      String id = "getDeleteFileError";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidExtensionName(String arg0, String arg1) {
      String id = "invalidExtensionName";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExtensionJarNotFound(String arg0, String arg1) {
      String id = "getExtensionJarNotFound";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cannotReadJarFile(String arg0) {
      String id = "cannotReadJarFile";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidJarFile(String arg0) {
      String id = "invalidJarFile";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidExtensionPoint(String arg0) {
      String id = "invalidExtensionPoint";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unsupportedExtensionClass(String arg0) {
      String id = "unsupportedExtensionClass";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String scriptNotFound(String arg0, String arg1, String arg2) {
      String id = "scriptNotFound";
      String subsystem = "Patching";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
