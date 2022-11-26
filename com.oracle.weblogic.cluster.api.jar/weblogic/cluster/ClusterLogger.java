package weblogic.cluster;

import java.io.IOException;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18n.logging.MessageResetScheduler;
import weblogic.i18ntools.L10nLookup;

public class ClusterLogger {
   private static final String LOCALIZER_CLASS = "weblogic.cluster.ClusterLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(ClusterLogger.class.getName());
   }

   public static String logCannotResolveClusterAddressWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000101", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000101";
   }

   public static String logJoinedCluster(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000102", 32, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000102";
   }

   public static String logLeavingCluster(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000103", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000103";
   }

   public static String logIncompatibleVersionsError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000104", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000104";
   }

   public static String logIncompatibleServerLeavingCluster() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000105", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000105";
   }

   public static String logOfferReplacementError(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000107", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000107";
   }

   public static String logRetractUnrecognizedOfferError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000108", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000108";
   }

   public static String logMulticastSendError(IOException arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000109", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000109";
   }

   public static String logMulticastReceiveError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000110", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000110";
   }

   public static String logAddingServer(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000111", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000111";
   }

   public static String logRemovingServerDueToTimeout(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000112", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000112";
   }

   public static String logRemovingServerDueToPeerGone(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000113", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000113";
   }

   public static String logNoClusterLicenseError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000114", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000114";
   }

   public static String logLostMulticastMessages(long arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000115", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000115";
   }

   public static String logFailedToJoinClusterError(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000116", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000116";
   }

   public static String logStaleReplicationRequest(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000117", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000117";
   }

   public static String logReplicationVersionMismatch(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000118", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000118";
   }

   public static String logMissingClusterMulticastAddressError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000119", 1, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000119";
   }

   public static String logErrorCreatingClusterRuntime(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000120", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000120";
   }

   public static String logMultipleDomainsCannotUseSameMulticastAddress(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000121", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000121";
   }

   public static String logMultipleClustersCannotUseSameMulticastAddress(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000122", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000122";
   }

   public static String logConflictStartNonClusterableObject(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000123", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000123";
   }

   public static String logConflictStartInCompatibleClusterableObject(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000124", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000124";
   }

   public static String logConflictStop(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000125", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000125";
   }

   public static String logUnableToUpdateNonSerializableObject(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000126", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000126";
   }

   public static String logNewServerJoinedCluster(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000127", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000127";
   }

   public static String logUpdatingServerInTheCluster(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000128", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000128";
   }

   public static String logRemovingServerFromCluster(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000129", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000129";
   }

   public static String logStartWarmup(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000133", 32, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000133";
   }

   public static String logMulticastSendErrorMsg(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000137", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000137";
   }

   public static String logListeningToCluster(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000138", 32, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000138";
   }

   public static String logMulticastAddressCollision(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000139", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000139";
   }

   public static String logFailedToDeserializeStateDump(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000140", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000140";
   }

   public static String logFailedWhileReceivingStateDump(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000141", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000141";
   }

   public static String logFetchClusterStateDump(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000142", 32, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000142";
   }

   public static String logFetchServerStateDump(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000143", 32, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000143";
   }

   public static String logServerSuspended(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000144", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000144";
   }

   public static String logMissingJDBCConfigurationForAutoMigration(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000145", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000145";
   }

   public static String logServerFailedtoRenewLease(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000147", 16, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000147";
   }

   public static String logMigratableServerNotTargetToAMachine(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000148", 16, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000148";
   }

   public static String logDatabaseUnreachable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000149", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000149";
   }

   public static String logDatabaseUnreachableForLeaseRenewal(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000150", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000150";
   }

   public static String logClusterMasterElected(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000151", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000151";
   }

   public static String logRevokeClusterMasterRole(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000152", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000152";
   }

   public static String logMisconfiguredMigratableCluster() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000153", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000153";
   }

   public static String logIncorrectRemoteClusterAddress(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000154", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000154";
   }

   public static String logInvalidConfiguredClusterAddress(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000155", 32, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000155";
   }

   public static String logFailureUpdatingServerInTheCluster(String arg0, IOException arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000156", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000156";
   }

   public static String logOutboundClusterServiceStopped() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000158", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000158";
   }

   public static String logMachineTimesOutOfSync(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000159", 16, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000159";
   }

   public static String logEnforceSecureRequest() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000160", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000160";
   }

   public static String logMessageDigestInvalid(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000161", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000161";
   }

   public static String logStartingReplicationService(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000162", 32, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000162";
   }

   public static String logStoppingReplicationService(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000163", 32, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000163";
   }

   public static String logFetchClusterStateDumpComplete(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000164", 32, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000164";
   }

   public static String logMultipleDomainsCannotUseSameMulticastAddress2(String arg0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("000165")) {
         return "000165";
      } else {
         Object[] args = new Object[]{arg0};
         CatalogMessage catalogMessage = new CatalogMessage("000165", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(true);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("000165", 120000L);
         return "000165";
      }
   }

   public static void resetlogMultipleDomainsCannotUseSameMulticastAddress2() {
      MessageResetScheduler.getInstance().resetLogMessage("000165");
   }

   public static String logMultipleClustersCannotUseSameMulticastAddress2(String arg0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("000166")) {
         return "000166";
      } else {
         Object[] args = new Object[]{arg0};
         CatalogMessage catalogMessage = new CatalogMessage("000166", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(true);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("000166", 120000L);
         return "000166";
      }
   }

   public static void resetlogMultipleClustersCannotUseSameMulticastAddress2() {
      MessageResetScheduler.getInstance().resetLogMessage("000166");
   }

   public static String logFailedToDeactivateMigratableServicesDuringRollback(Exception arg0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("000167")) {
         return "000167";
      } else {
         Object[] args = new Object[]{arg0};
         CatalogMessage catalogMessage = new CatalogMessage("000167", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(true);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("000167", 120000L);
         return "000167";
      }
   }

   public static void resetlogFailedToDeactivateMigratableServicesDuringRollback() {
      MessageResetScheduler.getInstance().resetLogMessage("000167");
   }

   public static String logFailedToAutomaticallyMigrateServers2(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000168", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000168";
   }

   public static String logMessageCannotReceiveOwnMessages(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000170", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000170";
   }

   public static String logUnableToLoadCustomQueryHelper(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000171", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000171";
   }

   public static String logFailedWhileReceivingStateDumpWithMessage(String arg0, Exception arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("000172", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000172";
   }

   public static String logMissingMachine(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000176", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000176";
   }

   public static String logScriptFailed(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000178", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000178";
   }

   public static String logLeasingError(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000179", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000179";
   }

   public static String logInvalidTimerState(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000180", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000180";
   }

   public static String logDebug(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000181", 128, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000181";
   }

   public static String logCreatedJob(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000182", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000182";
   }

   public static String logCancelledJob(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000183", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000183";
   }

   public static String logDelayedLeaseRenewal(long arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000184", 16, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000184";
   }

   public static String logLeaseRenewedAfterDelay() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000185", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000185";
   }

   public static String logExceptionWhileMigratingService(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000186", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000186";
   }

   public static String logRegisteredSingletonService(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000187", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000187";
   }

   public static String logUnregisteredSingletonService(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000188", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000188";
   }

   public static String logActivatedSingletonService(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000189", 32, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000189";
   }

   public static String logDeactivatedSingletonService(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000190", 32, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000190";
   }

   public static String logMonitoringMigratableServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000191", 64, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000191";
   }

   public static String logNoSuitableServerFoundForSingletonService(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000192", 16, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000192";
   }

   public static String logAttemptedJTAMigrationFromLivingServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000193", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000193";
   }

   public static String logErrorReportingMigrationRuntimeInfo(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000194", 16, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000194";
   }

   public static String logErrorUpdatingMigrationRuntimeInfo(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000195", 16, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000195";
   }

   public static String logUnknownMigrationDataType(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000196", 16, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000196";
   }

   public static String logUnicastEnabled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000197", 32, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000197";
   }

   public static String logWrongChannelForReplicationCalls(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000198", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000198";
   }

   public static String logWrongPriviledgesForReplicationCalls(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000199", 8, args, ClusterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClusterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000199";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.cluster.ClusterLogLocalizer", ClusterLogger.class.getClassLoader());
      private MessageLogger messageLogger = ClusterLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = ClusterLogger.findMessageLogger();
      }
   }
}
