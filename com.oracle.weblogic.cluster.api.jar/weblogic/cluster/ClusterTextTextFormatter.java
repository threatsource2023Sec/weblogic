package weblogic.cluster;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class ClusterTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public ClusterTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.cluster.ClusterTextTextLocalizer", ClusterTextTextFormatter.class.getClassLoader());
   }

   public ClusterTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.cluster.ClusterTextTextLocalizer", ClusterTextTextFormatter.class.getClassLoader());
   }

   public static ClusterTextTextFormatter getInstance() {
      return new ClusterTextTextFormatter();
   }

   public static ClusterTextTextFormatter getInstance(Locale l) {
      return new ClusterTextTextFormatter(l);
   }

   public String startingClusterService() {
      String id = "startingClusterService";
      String subsystem = "Cluster";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String displayMulticastMonitorMessage(String arg0) {
      String id = "displayMulticastMonitorMessage";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotChangeClusterWhileServerReferredToInMigratableTarget(String arg0, String arg1) {
      String id = "CannotChangeClusterWhileServerReferredToInMigratableTarget";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotDeleteServerException(String arg0) {
      String id = "CannotDeleteServerException";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotDeleteClusterException(String arg0) {
      String id = "CannotDeleteClusterException";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotDeleteMigratableTargetException(String arg0) {
      String id = "CannotDeleteMigratableTargetException";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetInvViolation_1A(String arg0) {
      String id = "MigratableTargetInvViolation_1A";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetInvViolation_1B(String arg0) {
      String id = "MigratableTargetInvViolation_1B";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetInvViolation_1C(String arg0) {
      String id = "MigratableTargetInvViolation_1C";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetInvViolation_1D(String arg0) {
      String id = "MigratableTargetInvViolation_1D";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetInvViolation_2(String arg0) {
      String id = "MigratableTargetInvViolation_2";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetInvViolation_3(String arg0) {
      String id = "MigratableTargetInvViolation_3";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetInvViolation_4(String arg0) {
      String id = "MigratableTargetInvViolation_4";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetInvViolation_5(String arg0) {
      String id = "MigratableTargetInvViolation_5";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetSubSystemName() {
      String id = "MigratableTargetSubSystemName";
      String subsystem = "Cluster";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotSetConstrainedCandidateServersException(String arg0) {
      String id = "CannotSetConstrainedCandidateServersException";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotSetUserPreferredServerException(String arg0) {
      String id = "CannotSetUserPreferredServerException";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotRemoveUserPreferredServerException(String arg0) {
      String id = "CannotRemoveUserPreferredServerException";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotSetClusterException(String arg0) {
      String id = "CannotSetClusterException";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAutomaticModeNotSupportedException(String arg0) {
      String id = "AutomaticModeNotSupportedException";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskStatusInProgress() {
      String id = "MigrationTaskStatusInProgress";
      String subsystem = "Cluster";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskStatusDone() {
      String id = "MigrationTaskStatusDone";
      String subsystem = "Cluster";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskStatusFailed() {
      String id = "MigrationTaskStatusFailed";
      String subsystem = "Cluster";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskStatusCanceled() {
      String id = "MigrationTaskStatusCanceled";
      String subsystem = "Cluster";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskStatusQIsTheSourceServerDown() {
      String id = "MigrationTaskStatusQIsTheSourceServerDown";
      String subsystem = "Cluster";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskStatusQIsTheDestinationServerDown() {
      String id = "MigrationTaskStatusQIsTheDestinationServerDown";
      String subsystem = "Cluster";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskTitle(String arg0, String arg1, String arg2) {
      String id = "MigrationTaskTitle";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskCannotCancelHere() {
      String id = "MigrationTaskCannotCancelHere";
      String subsystem = "Cluster";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskErrorCandidateServerMustNotBeEmpty() {
      String id = "MigrationTaskErrorCandidateServerMustNotBeEmpty";
      String subsystem = "Cluster";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskErrorDestinationMustNotBeCurrentlyActiveServer() {
      String id = "MigrationTaskErrorDestinationMustNotBeCurrentlyActiveServer";
      String subsystem = "Cluster";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskErrorDestinationMustBeMemberOfCandidiateServers() {
      String id = "MigrationTaskErrorDestinationMustBeMemberOfCandidiateServers";
      String subsystem = "Cluster";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskErrorUnableToDetermineListenAddressFromConfig(String arg0) {
      String id = "MigrationTaskErrorUnableToDetermineListenAddressFromConfig";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskLoglineJTAMigrationStarted(String arg0, String arg1) {
      String id = "MigrationTaskLoglineJTAMigrationStarted";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskLoglineMigrationStarted(String arg0, String arg1) {
      String id = "MigrationTaskLoglineMigrationStarted";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationErrorDestinationNotAmongCandidateServers(String arg0, String arg1) {
      String id = "MigrationErrorDestinationNotAmongCandidateServers";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationStarted(String arg0, String arg1, String arg2) {
      String id = "MigrationStarted";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationSucceeded(String arg0) {
      String id = "MigrationSucceeded";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationFailed(String arg0, String arg1) {
      String id = "MigrationFailed";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationUnknownDestinationServer(String arg0) {
      String id = "MigrationUnknownDestinationServer";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationUnknownMigratableTarget(String arg0) {
      String id = "MigrationUnknownMigratableTarget";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationJTAPrefix() {
      String id = "MigrationJTAPrefix";
      String subsystem = "Cluster";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskUserStopDestinationNotReachable(String arg0) {
      String id = "MigrationTaskUserStopDestinationNotReachable";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String IncorrectMigratableServerName(String arg0) {
      String id = "IncorrectMigratableServerName";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MigratableServerIsNotInCluster(String arg0) {
      String id = "MigratableServerIsNotInCluster";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotEnableAutoMigrationWithoutLeasing(String arg0) {
      String id = "CannotEnableAutoMigrationWithoutLeasing";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotEnableAutoMigrationWithoutLeasing2() {
      String id = "CannotEnableAutoMigrationWithoutLeasing2";
      String subsystem = "Cluster";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNodemanagerRequiredOnCandidateServers(String arg0) {
      String id = "NodemanagerRequiredOnCandidateServers";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalMigrationPolicy(String arg0) {
      String id = "IllegalMigrationPolicy";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalAttemptToSetPostScriptFailure(String arg0) {
      String id = "IllegalAttemptToSetPostScriptFailure";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalAttemptToSetRestartOnFailure(String arg0) {
      String id = "IllegalAttemptToSetRestartOnFailure";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalAttemptToSetSecondsBetweenRestarts(String arg0) {
      String id = "IllegalAttemptToSetSecondsBetweenRestarts";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalAttemptToSetNumberOfRestartAttempts(String arg0) {
      String id = "IllegalAttemptToSetNumberOfRestartAttempts";
      String subsystem = "Cluster";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
