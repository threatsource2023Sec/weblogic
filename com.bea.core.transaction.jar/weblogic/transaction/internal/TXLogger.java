package weblogic.transaction.internal;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18n.logging.MessageResetScheduler;
import weblogic.i18ntools.L10nLookup;

public class TXLogger {
   private static final String LOCALIZER_CLASS = "weblogic.transaction.internal.TXLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(TXLogger.class.getName());
   }

   public static String logTLOGRecordClassNameError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110000", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110000";
   }

   public static String logTLOGRecordEncodingError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110001", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110001";
   }

   public static String logTLOGOnErrorException(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110003", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110003";
   }

   public static String logTLOGReadChecksumError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("110004", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110004";
   }

   public static String logTLOGRecordChecksumMismatch(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110005", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110005";
   }

   public static String logTLOGRecordClassInstantiationException(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110007", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110007";
   }

   public static String logTLOGReadExternalException(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110008", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110008";
   }

   public static String logTLOGOnRecoveryException(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110011", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110011";
   }

   public static String logTLOGWriteError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110013", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110013";
   }

   public static String logTLOGUnrecognizedHeaderVersionNumber() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("110015", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110015";
   }

   public static String logTLOGFileReadError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110020", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110020";
   }

   public static String logTLOGFileReadFormatError(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110021", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110021";
   }

   public static String logTLOGFileReadFormatException(int arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("110022", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110022";
   }

   public static String logTLOGRecordChecksumException(int arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110023", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110023";
   }

   public static String logTLOGMissing(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110026", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110026";
   }

   public static String logDebug(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110027", 128, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110027";
   }

   public static String logForgetNotCalledOnCommitHeur(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110028", 64, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110028";
   }

   public static String logForgetNotCalledOnRollbackHeur(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110029", 64, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110029";
   }

   public static String logResourceNotResponding(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110030", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110030";
   }

   public static String logTLOGRecoveredBackupHeader(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110033", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110033";
   }

   public static String logNo2PCLicense() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("110101", 64, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110101";
   }

   public static String log2PCAttemptWithoutLicense() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("110102", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110102";
   }

   public static String logUserNotAuthorizedForStartCommit(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110200", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110200";
   }

   public static String logUserNotAuthorizedForStartRollback(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110201", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110201";
   }

   public static String logUserNotAuthorizedForRollback(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110202", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110202";
   }

   public static String logResourceUnavailable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110204", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110204";
   }

   public static String logResourceNowAvailable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110207", 64, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110207";
   }

   public static String logRecoveryServiceUnregistrationFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110208", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110208";
   }

   public static String logRecoveryServiceRegistrationFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110209", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110209";
   }

   public static String logRecoveryServiceFailbackFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110210", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110210";
   }

   public static String logRecoveryServiceFailbackRetryFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110211", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110211";
   }

   public static String logMigrateRecoveryServiceWhileServerActive() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("110212", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110212";
   }

   public static String logRecoveryServiceActivationFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110213", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110213";
   }

   public static String logUnregisterResMBeanError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110400", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110400";
   }

   public static String logIgnoreAfterCompletionErr(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110401", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110401";
   }

   public static String logUnknownTxState(byte arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110402", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110402";
   }

   public static String logUnexpectedTimerException(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110403", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110403";
   }

   public static String logResourceNotAssigned(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110405", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110405";
   }

   public static String logErrorComputingHeuristicStatus(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110406", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110406";
   }

   public static String logUnknownGetStatusState(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110407", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110407";
   }

   public static String logAddDuplicateTxToMap() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("110408", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110408";
   }

   public static String logAdvertiseResourceError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110409", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110409";
   }

   public static String logLocalCoordinatorDescriptorError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110411", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110411";
   }

   public static String logHeuristicCompletion(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110412", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110412";
   }

   public static String logRetryCommitIllegalState(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110414", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110414";
   }

   public static String logRetryRollbackIllegalState(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110415", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110415";
   }

   public static String logTxPropertyTypeError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("110419", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110419";
   }

   public static String logTxLocalPropertyTypeError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("110420", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110420";
   }

   public static String logWakeupForCommittedTx(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110421", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110421";
   }

   public static String logWakeupStateErrorForceRB(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110422", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110422";
   }

   public static String logAbandoningTx(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110423", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110423";
   }

   public static String logBeginUnexpectedException(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110424", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110424";
   }

   public static String logResourceMBeanCreateFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110410", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110410";
   }

   public static String logAdvertiseTMError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110425", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110425";
   }

   public static String logAdvertiseUTError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110426", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110426";
   }

   public static String logAdvertiseCoordinatorError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110427", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110427";
   }

   public static String logExportCoordinatorObjIDError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110428", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110428";
   }

   public static String logAdvertiseSubCoordinatorError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110429", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110429";
   }

   public static String logDebugTrace(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110430", 128, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110430";
   }

   public static String logGetTransactionLogOwnershipError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("110433", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110433";
   }

   public static String logForceLocalRollbackInvoked(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110442", 64, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110442";
   }

   public static String logForceLocalCommitInvoked(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110443", 64, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110443";
   }

   public static String logForceGlobalRollbackInvoked(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110444", 64, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110444";
   }

   public static String logForceGlobalCommitInvoked(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110445", 64, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110445";
   }

   public static String logForceLocalRollbackNoTx(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110446", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110446";
   }

   public static String logForceLocalCommitNoTx(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110447", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110447";
   }

   public static String logForceGlobalRollbackNoTx(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110448", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110448";
   }

   public static String logForceGlobalCommitNoTx(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110449", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110449";
   }

   public static String logForceLocalRollbackFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110450", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110450";
   }

   public static String logForceGlobalRollbackFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110451", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110451";
   }

   public static String logForceLocalCommitFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110452", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110452";
   }

   public static String logForceGlobalCommitFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110453", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110453";
   }

   public static String logForceLocalRollbackInvalidState(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110454", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110454";
   }

   public static String logForceGlobalRollbackInvalidState(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110455", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110455";
   }

   public static String logForceLocalCommitInvalidState(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110456", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110456";
   }

   public static String logForceGlobalRollbackCoordinatorError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("110458", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110458";
   }

   public static String logForceGlobalCommitCoordinatorError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("110459", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110459";
   }

   public static String logForceGlobalRollbackCoordinatorVersion(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110460", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110460";
   }

   public static String logForceLocalCommitMarkedRollback(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110462", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110462";
   }

   public static String logForceCommitResourceBusy(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110464", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110464";
   }

   public static String logForceRollbackResourceBusy(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110465", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110465";
   }

   public static String logForceCommitResourceCommitted(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110466", 64, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110466";
   }

   public static String logForceRollbackResourceRolledBack(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110467", 64, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110467";
   }

   public static String logForceCommitResourceError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("110468", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110468";
   }

   public static String logForceRollbackResourceError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("110469", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110469";
   }

   public static String logUserNotAuthorizedForForceGlobalRollback(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110470", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110470";
   }

   public static String logUserNotAuthorizedForForceGlobalCommit(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110471", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110471";
   }

   public static String logUserNotAuthorizedForForceLocalRollback(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110472", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110472";
   }

   public static String logUserNotAuthorizedForForceLocalCommit(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110473", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110473";
   }

   public static String logAdvertiseNonXAResourceError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110474", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110474";
   }

   public static String logUserNotAuthorizedForNonXACommit(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110475", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110475";
   }

   public static String logPendingTxDuringShutdown() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("110476", 32, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110476";
   }

   public static String logBlockingUnregistrationTimedOut(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110477", 64, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110477";
   }

   public static String logUnresolvedLLROnePhaseCommit(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("110478", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110478";
   }

   public static String logUnresolvedLLRTwoPhaseCommit(String arg0, String arg1, String arg2, int arg3, Throwable arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("110479", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110479";
   }

   public static String logResolvedLLRTwoPhaseCommit(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("110480", 32, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110480";
   }

   public static String logFailedLLRBoot(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110482", 4, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110482";
   }

   public static String logHealthOK(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("110483", 64, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110483";
   }

   public static String logHealthWarning(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("110484", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110484";
   }

   public static String logHealthError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("110485", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110485";
   }

   public static String logResourceNotAssignedForCommitRetry(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("110486", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110486";
   }

   public static String logResourceNotAssignedForRollbackRetry(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("110487", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110487";
   }

   public static String logCheckpointedLLRResourcesNotAvailable(String arg0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("110488")) {
         return "110488";
      } else {
         Object[] args = new Object[]{arg0};
         CatalogMessage catalogMessage = new CatalogMessage("110488", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(true);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("110488", 30000L);
         return "110488";
      }
   }

   public static void resetlogCheckpointedLLRResourcesNotAvailable() {
      MessageResetScheduler.getInstance().resetLogMessage("110488");
   }

   public static String logUserNotAuthorizedForAckPrepare(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("114089", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "114089";
   }

   public static String logUserNotAuthorizedForCommit(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110490", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110490";
   }

   public static String logUserNotAuthorizedForAckPrePrepare(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110491", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110491";
   }

   public static String logUserNotAuthorizedForCheckStatus(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110492", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110492";
   }

   public static String logUserNotAuthorizedForAckCommit(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110493", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110493";
   }

   public static String logUserNotAuthorizedForNakCommit(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110494", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110494";
   }

   public static String logUserNotAuthorizedForAckRollback(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110495", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110495";
   }

   public static String logUserNotAuthorizedForNakRollback(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110496", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110496";
   }

   public static String logUserNotAuthorizedForGetSubCoordinatorInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110500", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110500";
   }

   public static String logUserNotAuthorizedForGetProperties(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110501", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110501";
   }

   public static String logUserNotAuthorizedForRecover(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110502", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110502";
   }

   public static String logMigratorNotAvailable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110503", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110503";
   }

   public static String logAdvertiseTSRError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("110504", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110504";
   }

   public static String logTxCompletionTimeoutSecondsError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("110505", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "110505";
   }

   public static String logStandaloneMode() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("111001", 64, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111001";
   }

   public static String logDistributedMode() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("111002", 64, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111002";
   }

   public static String logErrorCreatingTLOGStore(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("111003", 64, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111003";
   }

   public static String logDowngradeAdminURL(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("111004", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111004";
   }

   public static String logClearedBusyFlag(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("111005", 64, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111005";
   }

   public static String logDowngradeSSLURL(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("111006", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111006";
   }

   public static String logOnePhaseCommitResourceError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("111007", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111007";
   }

   public static String logFailedTLOGBoot(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("111008", 4, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111008";
   }

   public static String logFailedActivateDeployments(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("111009", 4, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111009";
   }

   public static String logFailedSetPrimaryStore(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("111010", 4, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111010";
   }

   public static String logFailedPrimaryStoreBoot(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("111011", 4, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111011";
   }

   public static String logFailedSetPrimaryStoreRetry(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("111012", 4, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111012";
   }

   public static String logJDBCStoreRecoveryInitException(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("111013", 4, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111013";
   }

   public static String logLookingForResourceDescriptorFailure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("111014", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111014";
   }

   public static String logTransactionTimedOut(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("111015", 64, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111015";
   }

   public static String logUnableToProcessDeterminer(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("111016", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111016";
   }

   public static String logNamingExceptionOnTMLookup(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("111017", 8, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111017";
   }

   public static String logNoComponentInvocation() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("111018", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111018";
   }

   public static String logPendingTxAfterInitiatedTxShutdown() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("111019", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111019";
   }

   public static String logCrossSiteRecoveryIssue(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("111020", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111020";
   }

   public static String logSecurityInteropModeCompatibilityDeprecated() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("111021", 16, args, TXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "111021";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.transaction.internal.TXLogLocalizer", TXLogger.class.getClassLoader());
      private MessageLogger messageLogger = TXLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = TXLogger.findMessageLogger();
      }
   }
}
