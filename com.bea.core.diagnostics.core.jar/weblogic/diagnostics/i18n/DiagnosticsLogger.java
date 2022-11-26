package weblogic.diagnostics.i18n;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18n.logging.MessageResetScheduler;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class DiagnosticsLogger {
   private static final String LOCALIZER_CLASS = "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(DiagnosticsLogger.class.getName());
   }

   public static String logDiagnosticsInitializing(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320000", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320000";
   }

   public static Loggable logDiagnosticsInitializingLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("320000", 64, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServerDebugInitialized() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320001", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320001";
   }

   public static String logDiagnosticsStopping(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320002", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320002";
   }

   public static String logUnexpectedException(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320004", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320004";
   }

   public static String logUnknownMonitorTypeInScope(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320005", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320005";
   }

   public static String logErrorHarvesting(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320008", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320008";
   }

   public static String logHarvestingDelay(long arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320009", 16, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320009";
   }

   public static String logDiagnosticImageCreationError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320010", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320010";
   }

   public static String logDiagnosticImageDirectoryCreationError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320011", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320011";
   }

   public static String logDiagnosticImageDirectoryAccessError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320012", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320012";
   }

   public static String logDiagnosticImageAlreadyCaptured() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320013", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320013";
   }

   public static String logDiagnosticImageLockoutAbove(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320014", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320014";
   }

   public static String logDiagnosticImageLockoutBelow(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320015", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320015";
   }

   public static String logDiagnosticImageCaptureRequest(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320016", 32, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320016";
   }

   public static String logEngineConfigurationFileError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320018", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320018";
   }

   public static String logStdMonClassNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320019", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320019";
   }

   public static String logStdMonCodegenNotInstantiated(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320020", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320020";
   }

   public static String logStdMonCodegenNotAccessed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320021", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320021";
   }

   public static String logErrorCreatingSUID(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320022", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320022";
   }

   public static String logInvalidClassFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320023", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320023";
   }

   public static String logCouldNotInstrument(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320024", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320024";
   }

   public static String logMissingInputFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320025", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320025";
   }

   public static String logMonitorNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320026", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320026";
   }

   public static String logNonExistentActionType(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320030", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320030";
   }

   public static String logDuplicateMonitorInScope(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320031", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320031";
   }

   public static String logDuplicateActionInMonitor(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320032", 16, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320032";
   }

   public static String logIncompatibleAction(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320033", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320033";
   }

   public static String logInstrumentationConfigParseError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320034", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320034";
   }

   public static String logInstrumentationConfigReadError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320035", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320035";
   }

   public static String logInstrumentedMethodOevrflowError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320036", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320036";
   }

   public static String logInvalidClassBytes(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320037", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320037";
   }

   public static String logInstrumentationFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320038", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320038";
   }

   public static String logInvalidInclusionPatternError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320039", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320039";
   }

   public static String logInvalidExclusionPatternError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320040", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320040";
   }

   public static String logInvalidNotificationLockoutMinutes(String arg0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("320041")) {
         return "320041";
      } else {
         Object[] args = new Object[]{arg0};
         CatalogMessage catalogMessage = new CatalogMessage("320041", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(false);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("320041", 300000L);
         return "320041";
      }
   }

   public static void resetlogInvalidNotificationLockoutMinutes() {
      MessageResetScheduler.getInstance().resetLogMessage("320041");
   }

   public static String logInvalidNotificationImageLocation(String arg0, String arg1) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("320042")) {
         return "320042";
      } else {
         Object[] args = new Object[]{arg0, arg1};
         CatalogMessage catalogMessage = new CatalogMessage("320042", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(false);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("320042", 300000L);
         return "320042";
      }
   }

   public static void resetlogInvalidNotificationImageLocation() {
      MessageResetScheduler.getInstance().resetLogMessage("320042");
   }

   public static String logNotificationImageAlreadyCaptured(String arg0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("320043")) {
         return "320043";
      } else {
         Object[] args = new Object[]{arg0};
         CatalogMessage catalogMessage = new CatalogMessage("320043", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(false);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("320043", 300000L);
         return "320043";
      }
   }

   public static void resetlogNotificationImageAlreadyCaptured() {
      MessageResetScheduler.getInstance().resetLogMessage("320043");
   }

   public static String logErrorInNotification(Throwable arg0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("320044")) {
         return "320044";
      } else {
         Object[] args = new Object[]{arg0};
         CatalogMessage catalogMessage = new CatalogMessage("320044", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(true);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("320044", 300000L);
         return "320044";
      }
   }

   public static void resetlogErrorInNotification() {
      MessageResetScheduler.getInstance().resetLogMessage("320044");
   }

   public static String logMessagingExceptionInNotification(Throwable arg0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("320047")) {
         return "320047";
      } else {
         Object[] args = new Object[]{arg0};
         CatalogMessage catalogMessage = new CatalogMessage("320047", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(true);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("320047", 300000L);
         return "320047";
      }
   }

   public static void resetlogMessagingExceptionInNotification() {
      MessageResetScheduler.getInstance().resetLogMessage("320047");
   }

   public static String logJMSNotificationCreateMsgException(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320048", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320048";
   }

   public static String logJMSNotificationSendMsgException(Throwable arg0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("320049")) {
         return "320049";
      } else {
         Object[] args = new Object[]{arg0};
         CatalogMessage catalogMessage = new CatalogMessage("320049", 16, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(true);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("320049", 300000L);
         return "320049";
      }
   }

   public static void resetlogJMSNotificationSendMsgException() {
      MessageResetScheduler.getInstance().resetLogMessage("320049");
   }

   public static String logErrorInMailNotification(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320052", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320052";
   }

   public static String logSNMPAgentNotAvailable() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320053", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320053";
   }

   public static String logErrorInSNMPNotification(Throwable arg0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("320054")) {
         return "320054";
      } else {
         Object[] args = new Object[]{arg0};
         CatalogMessage catalogMessage = new CatalogMessage("320054", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(true);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("320054", 300000L);
         return "320054";
      }
   }

   public static void resetlogErrorInSNMPNotification() {
      MessageResetScheduler.getInstance().resetLogMessage("320054");
   }

   public static String logCreateLogIndexError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320055", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320055";
   }

   public static String logInvalidIndexFileMagicNumber(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320056", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320056";
   }

   public static String logInvalidIndexFileContents(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320057", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320057";
   }

   public static String logLogRecordParseError(String arg0, Throwable arg1) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("320058")) {
         return "320058";
      } else {
         Object[] args = new Object[]{arg0, arg1};
         CatalogMessage catalogMessage = new CatalogMessage("320058", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(true);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("320058", 30000L);
         return "320058";
      }
   }

   public static void resetlogLogRecordParseError() {
      MessageResetScheduler.getInstance().resetLogMessage("320058");
   }

   public static String logDuplicateWatch(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320063", 16, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320063";
   }

   public static String logCreateWatchError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320064", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320064";
   }

   public static Loggable logCreateWatchErrorLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("320064", 8, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDuplicateNotification(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320066", 16, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320066";
   }

   public static String logWatchEvaluatedToTrue(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("320068", 32, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Low");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320068";
   }

   public static String logInvalidNotification(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320069", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320069";
   }

   public static String logNotificationError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320070", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320070";
   }

   public static String logWatchNotificationError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320071", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320071";
   }

   public static String logRecordNotFoundError(long arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320073", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320073";
   }

   public static String logQueryExecutionError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320074", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320074";
   }

   public static String logRecordReadError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320075", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320075";
   }

   public static String logIndexInitializationError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320076", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320076";
   }

   public static String logInitializedAccessor() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320077", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320077";
   }

   public static String logAccessorInitializationError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320078", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320078";
   }

   public static String logWatchEvaluationFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320079", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320079";
   }

   public static String logUnknownLogType(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320080", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320080";
   }

   public static Loggable logUnknownLogTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("320080", 8, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMonitorConfigurationError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320082", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320082";
   }

   public static String logMissingLocationForCustomMonitor(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320083", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320083";
   }

   public static String logUserNotAuthorizedToViewLogs(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320084", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320084";
   }

   public static Loggable logUserNotAuthorizedToViewLogsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("320084", 8, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonSecureAttemptToAccessDiagnosticData() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320085", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320085";
   }

   public static Loggable logNonSecureAttemptToAccessDiagnosticDataLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("320085", 8, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidCharactersInMonitorType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320086", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320086";
   }

   public static String logRemovingCursorHandler(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320087", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320087";
   }

   public static String logCursorNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320088", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320088";
   }

   public static Loggable logCursorNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("320088", 8, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnknownActionTypeInActionGroup(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320091", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320091";
   }

   public static String logInvalidActionGroupUsage(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320092", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320092";
   }

   public static String logMonitorAttributeError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320093", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320093";
   }

   public static String logServerInstrumentationScopeInitializationError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320094", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320094";
   }

   public static String logCustomMonitorInServerScopeError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320096", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320096";
   }

   public static String logInvalidMonitorInServerScope(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320097", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320097";
   }

   public static String logInvalidMonitorInApplicationScope(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320098", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320098";
   }

   public static String logIncompleteJDBCArchiveConfiguration() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320100", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320100";
   }

   public static String logTargettingMultipleWLDFSystemResourcesToServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320101", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320101";
   }

   public static Loggable logTargettingMultipleWLDFSystemResourcesToServerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("320101", 8, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorExecutingDiagnosticQuery(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320102", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320102";
   }

   public static Loggable logErrorExecutingDiagnosticQueryLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("320102", 8, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCreatingDiagnosticDataRuntime(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320103", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320103";
   }

   public static Loggable logErrorCreatingDiagnosticDataRuntimeLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("320103", 8, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWatchErrorInvokingLog4j(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320104", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320104";
   }

   public static String logInvalidWatchRuleExpression(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320105", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320105";
   }

   public static Loggable logInvalidWatchRuleExpressionLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("320105", 8, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotificationNameExisting(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320106", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320106";
   }

   public static Loggable logNotificationNameExistingLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("320106", 8, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInsufficientTimeBetweenHarvesterCycles(long arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320111", 16, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320111";
   }

   public static String logTypeRemoval(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320113", 16, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320113";
   }

   public static String logConfigLoading() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320114", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320114";
   }

   public static String logHarvesterIsDisabled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320115", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320115";
   }

   public static String logHarvesterTypeIsDisabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320116", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320116";
   }

   public static String logDisablingHarvesterDueToLackOfActiveConfig() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320117", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320117";
   }

   public static String logHarvestTimerInitiated(long arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320118", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320118";
   }

   public static String logHarvestState(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320119", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320119";
   }

   public static String logAttributeNotHarvestable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320122", 16, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320122";
   }

   public static String logInstanceAddFailure(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320123", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320123";
   }

   public static String logInstanceDiscovered(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320124", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320124";
   }

   public static String logGenericHarvesterProblem(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320125", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320125";
   }

   public static String logConfigReloading() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320126", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320126";
   }

   public static String logDiagnosticImageSourceCreationException(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320127", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320127";
   }

   public static String logWLDFResourceBeanNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320128", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320128";
   }

   public static Loggable logWLDFResourceBeanNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("320128", 8, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInformInstrumentationScopeCreation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320129", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320129";
   }

   public static String logWarnInstrumentationScopeDisabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320130", 16, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320130";
   }

   public static String logWarnInstrumentationManagerDisabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320131", 16, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320131";
   }

   public static String logDiagnosticsClassRedefinition(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320132", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320132";
   }

   public static String logAccessorInstantiationError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320133", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320133";
   }

   public static String logErrorCreatingDomainLogHandler(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320134", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320134";
   }

   public static String logErrorInitializingJDBCArchive(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320135", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320135";
   }

   public static String logCouldNotInstrumentClass(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320136", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320136";
   }

   public static String logInvalidInclusionPatternInMonitorError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320137", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320137";
   }

   public static String logInvalidExclusionPatternInMonitorError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320138", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320138";
   }

   public static String logArchiveNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320139", 16, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320139";
   }

   public static String logScheduledDataRetirementBegin() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320140", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320140";
   }

   public static String logAgeBasedDataRetirementError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320141", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320141";
   }

   public static String logSizeBasedDataRetirementError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320142", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320142";
   }

   public static String logScheduledDataRetirementEnd(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320143", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320143";
   }

   public static String logDataRetirementOperationBegin(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320144", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320144";
   }

   public static String logDataRetirementOperationEnd(String arg0, String arg1, long arg2, long arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("320145", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320145";
   }

   public static String logDyeRegistrationFailureError(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320146", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320146";
   }

   public static String logInvalidInstrumentationLibraryError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320147", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320147";
   }

   public static String logMissingDiagnosticMonitor(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320148", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320148";
   }

   public static String logDomainRuntimeHarvesterUnavailable() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320149", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320149";
   }

   public static String logInvalidWatchVariableInstanceNameSpecification(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320151", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320151";
   }

   public static String logErrorDeleteingWatchedValues(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320152", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320152";
   }

   public static String logWarnHotswapUnavailable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320153", 16, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320153";
   }

   public static String logDiagnosticsClassRedefinitionFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320154", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320154";
   }

   public static String getInconsistentHandlingModifiersSpecified(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("320155", 8, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logCannotLoadRenderer(String arg0, Throwable arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320156", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320156";
   }

   public static String logErrorRegisteringLog4jDataGatheringAppender(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320157", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320157";
   }

   public static String logErrorCapturingFlightRecorderImage(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320158", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320158";
   }

   public static String logErrorInitializingFlightRecording(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320159", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320159";
   }

   public static String logCannotLoadTypeBasedRenderer(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320160", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320160";
   }

   public static String logLegacySpringInstrumentationUnknownMethod(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320161", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320161";
   }

   public static String logLegacySpringInstrumentationCalled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320162", 16, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320162";
   }

   public static String getWildcardedTypesNotAllowedToBeInSensitive() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("320163", 8, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCircularClassLoadStackmapComputeError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("320164", 8, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logWatchInstrumentationEventDispatchError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320165", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320165";
   }

   public static String logWatchLogEventDispatchError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320166", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320166";
   }

   public static String getExpectedMonitorsNotInstrumented(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("320167", 8, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logDisableInstrumentationWithShareableClassloader(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320168", 16, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320168";
   }

   public static String logActivatingDebugPatchAtSystem(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320169", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320169";
   }

   public static String logActivatingDebugPatchAtApplication(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320170", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320170";
   }

   public static String logActivatingDebugPatchAtModuleInApplication(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320171", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320171";
   }

   public static String logActivatingDebugPatchAtApplicationInPartition(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320172", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320172";
   }

   public static String logActivatingDebugPatchAtModuleInApplicationInPartition(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("320173", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320173";
   }

   public static String logActivatedDebugPatch(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320174", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320174";
   }

   public static String logDebugPatchActivationFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320175", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320175";
   }

   public static String logDeactivatingDebugPatchAtSystem(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320176", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320176";
   }

   public static String logDeactivatingDebugPatchAtApplication(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320177", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320177";
   }

   public static String logDeactivatingDebugPatchAtModuleInApplication(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320178", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320178";
   }

   public static String logDeactivatingDebugPatchAtApplicationInPartition(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320179", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320179";
   }

   public static String logDeactivatingDebugPatchAtModuleInApplicationInPartition(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("320180", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320180";
   }

   public static String logDeactivatedDebugPatch(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320181", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320181";
   }

   public static String logDebugPatchDeactivationFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320182", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320182";
   }

   public static String logUnknownClassesInDebugPatch(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320183", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320183";
   }

   public static Loggable logUnknownClassesInDebugPatchLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("320183", 8, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRetryStorefileOpen(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320184", 64, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320184";
   }

   public static String logFailedToOpenStorefile(String arg0, int arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320185", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320185";
   }

   public static String logUnableToFindPathToStoreDir(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320186", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320186";
   }

   public static String logClassInfoUnavailable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320187", 16, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320187";
   }

   public static Loggable logClassInfoUnavailableLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("320187", 16, args, "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DiagnosticsLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLongRIDValueCreated(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320188", 16, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Low");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320188";
   }

   public static String logAccessorFailedToAdaptToLogfileChange(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320189", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Low");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320189";
   }

   public static String logFailureToCloseDiagnosticStore(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320190", 8, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Low");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320190";
   }

   public static String logLongMaxRIDLengthValueExceeded(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320191", 16, args, DiagnosticsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Low");
      catalogMessage.setExcludePartition(true);
      DiagnosticsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320191";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.diagnostics.l10n.DiagnosticsLogLocalizer", DiagnosticsLogger.class.getClassLoader());
      private MessageLogger messageLogger = DiagnosticsLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = DiagnosticsLogger.findMessageLogger();
      }
   }
}
