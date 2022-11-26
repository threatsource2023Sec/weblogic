package weblogic.cluster;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class ClusterExtensionLogger {
   private static final String LOCALIZER_CLASS = "weblogic.cluster.ClusterExtensionLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(ClusterExtensionLogger.class.getName());
   }

   public static String logUpdatingNonDynamicPropertyOnAdminServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003101", 16, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003101";
   }

   public static String logPostDeactivationScriptFailure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003102", 8, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003102";
   }

   public static String logReleaseLeaseError(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003103", 8, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003103";
   }

   public static String logFailedToNotifyPostScriptFailureToStateManager(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("003104", 8, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003104";
   }

   public static String logAsyncReplicationRequestTimeout(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003105", 16, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003105";
   }

   public static String logUnexpectedExceptionDuringReplication(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003106", 16, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003106";
   }

   public static String logLostUnicastMessages(long arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003107", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003107";
   }

   public static String logUnicastReceiveError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003108", 8, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003108";
   }

   public static String logDataSourceForDatabaseLeasingNotSet(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003109", 8, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003109";
   }

   public static String logConnectionRejectedProtocol(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003110", 16, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003110";
   }

   public static String logNoChannelForReplicationCalls(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003111", 8, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003111";
   }

   public static String logUsingMultipleChannelsForReplication(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003112", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003112";
   }

   public static String logUsingOneWayRMIForReplication() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("003113", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003113";
   }

   public static String logIgnoringOneWayRMIWithoutMultipleChannels() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("003114", 32, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003114";
   }

   public static String logOutOfOrderUpdateOneWayRequest() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("003115", 8, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003115";
   }

   public static String logStartingMemberDeathDetector() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("003116", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003116";
   }

   public static String logStartingMemberDeathDetectorReceiver() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("003117", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003117";
   }

   public static String logServerWithNoMachineConfigured(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003118", 16, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003118";
   }

   public static String logRegisteredSingletonServiceNoActivation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003119", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003119";
   }

   public static String logFailedToResolveHostname(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003120", 16, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003120";
   }

   public static String logMissingListenAddress(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003121", 16, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003121";
   }

   public static String logFailedToExecutePingSQL(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003122", 8, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003122";
   }

   public static String logFailedLeaseIsFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003123", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003123";
   }

   public static String logExpiredLeaseIsFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003124", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003124";
   }

   public static String logNoLeaseIsFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003125", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003125";
   }

   public static String logStartAutoMigration(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003126", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003126";
   }

   public static String logRestartInPlaceStarted(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003127", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003127";
   }

   public static String logRestartInPlaceCompleted(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003128", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003128";
   }

   public static String logAutoMigrationStarted(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("003129", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003129";
   }

   public static String logAutoMigrationCompleted(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003130", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003130";
   }

   public static String logMigratableGroupFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("003131", 8, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003131";
   }

   public static String getJobSchedulerNotConfiguredForClusteredTimers() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("003132", 8, args, "weblogic.cluster.ClusterExtensionLogLocalizer", ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ClusterExtensionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getJobSchedulerNotConfiguredInPartition(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("003133", 8, args, "weblogic.cluster.ClusterExtensionLogLocalizer", ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ClusterExtensionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getClusteredTimersRequireCluster() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("003134", 8, args, "weblogic.cluster.ClusterExtensionLogLocalizer", ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ClusterExtensionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logJobSchedulerNotConfiguredInPartition() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("003135", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003135";
   }

   public static String logNoPartitionJobSchedulerInStandaloneServer() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("003136", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003136";
   }

   public static String logFailedToBindMulticastSocketToMulticastGroupAddress(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003137", 32, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003137";
   }

   public static String logFetchServerStateDumpComplete(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003138", 32, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003138";
   }

   public static String logAssertionError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003139", 16, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003139";
   }

   public static String logPartitionChangeEvent(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("003140", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003140";
   }

   public static String logReentrantThread(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003141", 16, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003141";
   }

   public static String logEnsureReplicatedException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003142", 16, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003142";
   }

   public static String logLocalCleanupException(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("003143", 16, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003143";
   }

   public static String logUnableToUpdateNonSerializableObject(Object arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003144", 8, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003144";
   }

   public static String logTimerMasterNotStarted(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003145", 16, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003145";
   }

   public static String logServerMissingListenAddress(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003146", 4, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003146";
   }

   public static String logErrorTransactionForClusteredTimers(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("003147", 8, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003147";
   }

   public static String logClusteredTimeoutDelayAutomaticallyApplied(String arg0, int arg1, int arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("003148", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003148";
   }

   public static String logFailedToFireRetryTimer(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003149", 16, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003149";
   }

   public static String logSingletonMasterLeaseAcquired(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003150", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003150";
   }

   public static String logSingletonMasterLeaseReleased(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003151", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003151";
   }

   public static String logSqlTimeoutError(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("003152", 32, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003152";
   }

   public static String logSqlTimeoutErrorWithRetry(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("003153", 32, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003153";
   }

   public static String logTableAlreadyExists(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003154", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003154";
   }

   public static String logTableCreateSuccess(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003155", 64, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003155";
   }

   public static String logTableCreateFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003156", 8, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003156";
   }

   public static String logUnknownDatabase(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("003157", 8, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003157";
   }

   public static String logDDLFileNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003158", 8, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003158";
   }

   public static String logDatabaseAccessError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003159", 8, args, ClusterExtensionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterExtensionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003159";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.cluster.ClusterExtensionLogLocalizer", ClusterExtensionLogger.class.getClassLoader());
      private MessageLogger messageLogger = ClusterExtensionLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = ClusterExtensionLogger.findMessageLogger();
      }
   }
}
