package weblogic.logging;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18n.logging.MessageResetScheduler;
import weblogic.i18ntools.L10nLookup;

public class LogMgmtLogger {
   private static final String LOCALIZER_CLASS = "weblogic.logging.LogMgmtLogLocalizer";

   private static weblogic.i18n.logging.MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(LogMgmtLogger.class.getName());
   }

   public static String logCannotOpenDomainLogfile(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("170003", 4, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170003";
   }

   public static String logCannotGetDomainLogHandler(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("170011", 16, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170011";
   }

   public static String logServerLogFileOpened(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("170019", 32, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170019";
   }

   public static String logErrorOpeningLogFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("170020", 8, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170020";
   }

   public static String logErrorInitializingLog4jLogging(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("170022", 8, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170022";
   }

   public static String logDefaultServerLoggingInitialized() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("170023", 64, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170023";
   }

   public static String logLog4jServerLoggingInitialized() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("170024", 64, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170024";
   }

   public static String logInitializedDomainLogFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("170025", 64, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170025";
   }

   public static String logErrorInitializingServletContextLogger(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("170026", 8, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170026";
   }

   public static String logDomainLogHandlerInitialized() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("170027", 32, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170027";
   }

   public static String logDomainLogHandlerNotAvailableForTrap() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("170028", 8, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170028";
   }

   public static Loggable logDomainLogHandlerNotAvailableForTrapLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("170028", 8, args, "weblogic.logging.LogMgmtLogLocalizer", LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger, LogMgmtLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInitializingDataGatheringHandler(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("170029", 16, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170029";
   }

   public static String logErrorInitializingLog4jConfiguration(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("170030", 8, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170030";
   }

   public static String logInitializedServerLoggingBridge() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("170031", 64, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170031";
   }

   public static String logDetectedODLConfiguration() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("170032", 32, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170032";
   }

   public static String logErrorRegisteringServerLoggingBridge(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("170033", 8, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170033";
   }

   public static String logMessageThrottlingOn(int arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("170034", 16, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170034";
   }

   public static String logMessageThrottlingOff(int arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("170035", 32, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170035";
   }

   public static String logMessageMonitoringStarted(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("170036", 32, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170036";
   }

   public static String logMessageMonitoringStopped() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("170037", 32, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170037";
   }

   public static String logDumpThrottleData(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("170038", 64, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170038";
   }

   public static String logErrorReopeningServerLogFile(Exception arg0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("170039")) {
         return "170039";
      } else {
         Object[] args = new Object[]{arg0};
         CatalogMessage catalogMessage = new CatalogMessage("170039", 8, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(true);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("170039", 600000L);
         return "170039";
      }
   }

   public static void resetlogErrorReopeningServerLogFile() {
      MessageResetScheduler.getInstance().resetLogMessage("170039");
   }

   public static String logForcedLogRotation() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("170040", 8, args, LogMgmtLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LogMgmtLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "170040";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.logging.LogMgmtLogLocalizer", LogMgmtLogger.class.getClassLoader());
      private weblogic.i18n.logging.MessageLogger messageLogger = LogMgmtLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = LogMgmtLogger.findMessageLogger();
      }
   }
}
