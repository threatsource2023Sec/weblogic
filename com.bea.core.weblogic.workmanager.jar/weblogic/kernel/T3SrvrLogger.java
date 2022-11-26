package weblogic.kernel;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18n.logging.MessageResetScheduler;
import weblogic.i18ntools.L10nLookup;

public class T3SrvrLogger {
   private static final String LOCALIZER_CLASS = "weblogic.kernel.T3SrvrLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(T3SrvrLogger.class.getName());
   }

   public static String logStartupBuildName(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000214", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000214";
   }

   public static String logLoadedLicense(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000215", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000215";
   }

   public static String logNoShutdownNullUser() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000220", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000220";
   }

   public static String logNoShutdownNamelessUser() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000221", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000221";
   }

   public static String logNoLockServerNullUser() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000222", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000222";
   }

   public static String logNoLockServerNamelessUser() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000223", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000223";
   }

   public static String logNoUnlockServerNullUser() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000224", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000224";
   }

   public static String logNoUnlockServerNamelessUser() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000225", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000225";
   }

   public static String logUnlockServerRequested(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000226", 2, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000226";
   }

   public static String logUnlockServerHappened() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000227", 2, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000227";
   }

   public static String logLockServerRequested(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000228", 2, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000228";
   }

   public static String logLockServerHappened() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000229", 2, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000229";
   }

   public static String logWaitingForShutdown(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000231", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000231";
   }

   public static String logNotWaitingForShutdown() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000232", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000232";
   }

   public static String logKernelShutdown() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000236", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000236";
   }

   public static String logShutdownCompleted() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000238", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000238";
   }

   public static String logNoCancelShutdownNullUser() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000240", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000240";
   }

   public static String logNoCancelShutdownNamelessUser() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000241", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000241";
   }

   public static String logNoCancelShutdownAlreadyNotShutting() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000242", 2, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000242";
   }

   public static String logNoCancelShutdownTooLate() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000243", 2, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000243";
   }

   public static String logCancelShutdownInitiated() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000244", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000244";
   }

   public static String logCancelShutdownHappened() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000246", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000246";
   }

   public static String logLocalizerProblem(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000248", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000248";
   }

   public static String logShutdownFromCommandLineOnly() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000249", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000249";
   }

   public static String logSwitchedToGroup(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000251", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000251";
   }

   public static String logCantSwitchToGroup(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000252", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000252";
   }

   public static String logSwitchedToUser(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000253", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000253";
   }

   public static String logCantSwitchToUser(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000254", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000254";
   }

   public static String logInvokingClass(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000256", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000256";
   }

   public static String logReadCommandIOException(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000257", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000257";
   }

   public static String logReadWhichCommand(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000258", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000258";
   }

   public static String logConsoleProfilingEnabled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000259", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000259";
   }

   public static String logConsoleProfilingDisabled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000260", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000260";
   }

   public static String logConsoleShutSecurityException(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000261", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000261";
   }

   public static String logConsoleNoSuchCommand(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000262", 8, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000262";
   }

   public static String logConsoleGCBefore(long arg0, long arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000263", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000263";
   }

   public static String logConsoleGCAfter(long arg0, long arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000264", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000264";
   }

   public static String logAttemptUnbindUnboundClientContext(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000265", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000265";
   }

   public static String logCCHasPendingExecuteRequests(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000266", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000266";
   }

   public static String logCCHasNegativeWorkQueueDepth(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000267", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000267";
   }

   public static String logFailedSendingUnsolicitedMessage(String arg0, Exception arg1) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("000268")) {
         return "000268";
      } else {
         Object[] args = new Object[]{arg0, arg1};
         CatalogMessage catalogMessage = new CatalogMessage("000268", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(false);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("000268", 5000L);
         return "000268";
      }
   }

   public static void resetlogFailedSendingUnsolicitedMessage() {
      MessageResetScheduler.getInstance().resetLogMessage("000268");
   }

   public static String logConnectionUnexpectedlyLostHardDisconnect(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000269", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000269";
   }

   public static String logTimingOutClientContextOnIdle(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000270", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000270";
   }

   public static String logIgnoringCCDeathRequest(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000271", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000271";
   }

   public static String logSchedulingClientContextDeath(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000272", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000272";
   }

   public static String logRemovingClientContextHardDisconnect(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000273", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000273";
   }

   public static String logRemovingClientContextSoftDisconnect(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000274", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000274";
   }

   public static String logSoftDisconnectPendingMins(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000275", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000275";
   }

   public static String logHardDisconnectPendingMins(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000276", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000276";
   }

   public static String logIdleDisconnectPendingMins(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000277", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000277";
   }

   public static String logSendObjectMarshalFailedIO(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000283", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000283";
   }

   public static String logSendObjectMarshalFailedRTE(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000284", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000284";
   }

   public static String logFailInvokeStartupClass(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000286", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000286";
   }

   public static String logInvokingStartup(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000287", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000287";
   }

   public static String logStartupClassReports(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000288", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000288";
   }

   public static String logFailInvokeShutdownClass(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000289", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000289";
   }

   public static String logInvokingShutdown(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000290", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000290";
   }

   public static String logShutdownClassReports(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000291", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000291";
   }

   public static String logInconsistentSecurityConfig(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000297", 8, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000297";
   }

   public static String logCertificateExpiresSoon(long arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000298", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000298";
   }

   public static String logNoCertificatesSpecified() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000306", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000306";
   }

   public static String logExportableKeyMaxLifespan(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000307", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000307";
   }

   public static String logExecutionClassNoRetrieveT3Exec(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000314", 2, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000314";
   }

   public static String logFailureOfT3ExecutableLazy(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000315", 2, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000315";
   }

   public static String logEnableWatchDogNotPermitted() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000316", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000316";
   }

   public static String logDisableWatchDogNotPermitted() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000317", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000317";
   }

   public static String logErrorCreatingExecuteQueueRuntime(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000320", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000320";
   }

   public static String logStartedAdminServerProduction(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000329", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000329";
   }

   public static String logStartedManagedServerProduction(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000330", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000330";
   }

   public static String logStartedAdminServerDevelopment(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000331", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000331";
   }

   public static String logStartedManagedServerDevelopment(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000332", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000332";
   }

   public static String logWarnQueueCapacityGreaterThanThreshold(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000333", 16, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000333";
   }

   public static String logWarnPossibleStuckThread(String arg0, long arg1, String arg2, long arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("000337", 8, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Low");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000337";
   }

   public static String logInfoUnstuckThread(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000339", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Low");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000339";
   }

   public static String logNotInitialized(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000342", 1, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000342";
   }

   public static String logWarnRegisterHealthMonitor(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000343", 16, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000343";
   }

   public static String logWarnUnregisterHealthMonitor(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000344", 16, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000344";
   }

   public static String logJreLacksJep290(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000345", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000345";
   }

   public static String logStartedIndependentManagedServerDevMode(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000357", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000357";
   }

   public static String logStartedIndependentManagedServerProdMode(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000358", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000358";
   }

   public static String logServerStarted1(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000360", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000360";
   }

   public static String logServerSubsystemFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000362", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000362";
   }

   public static String logServerStateChange(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000365", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000365";
   }

   public static String logFailedToShutdownServer(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000366", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000366";
   }

   public static String logErrorWhileServerShutdown(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000371", 8, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000371";
   }

   public static String logPendingWorkInQueues(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000374", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000374";
   }

   public static String logServerStarting(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000377", 64, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000377";
   }

   public static String logShutdownTimedOut(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000378", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000378";
   }

   public static String logDebugSLC(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000380", 128, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000380";
   }

   public static String logServiceFailure(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000381", 8, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000381";
   }

   public static String logSuspendingOnFailure() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000382", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000382";
   }

   public static String logShuttingDownOnFailure() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000383", 8, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000383";
   }

   public static String logConfigFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000384", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000384";
   }

   public static String logServerHealthFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000385", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000385";
   }

   public static String logServerSubsystemFailedWithTrace(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000386", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000386";
   }

   public static String logShutdownHookCalled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000388", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000388";
   }

   public static String logDeadlockedThreads(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000394", 4, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000394";
   }

   public static String logDomainLibPath(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000395", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000395";
   }

   public static String logOperationRequested(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000396", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000396";
   }

   public static String logDebugSetUID(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000397", 128, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000397";
   }

   public static String logSecureModeEnabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000398", 32, args, T3SrvrLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3SrvrLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000398";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.kernel.T3SrvrLogLocalizer", T3SrvrLogger.class.getClassLoader());
      private MessageLogger messageLogger = T3SrvrLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = T3SrvrLogger.findMessageLogger();
      }
   }
}
