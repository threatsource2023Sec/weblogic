package weblogic.messaging;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class MessagingLogger {
   private static final String LOCALIZER_CLASS = "weblogic.messaging.MessagingLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(MessagingLogger.class.getName());
   }

   public static String logUnsupportedClassVersion(int arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("282000", 8, args, MessagingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "282000";
   }

   public static Loggable logUnsupportedClassVersionLoggable(int arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("282000", 8, args, "weblogic.messaging.MessagingLogLocalizer", MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MessagingLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStartingKernelRecovery(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("282001", 64, args, MessagingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "282001";
   }

   public static String logCompletedKernelRecovery(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("282002", 64, args, MessagingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "282002";
   }

   public static String logMessageBufferSize(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("282003", 64, args, MessagingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "282003";
   }

   public static String logFatalRedirectionError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("282004", 8, args, MessagingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "282004";
   }

   public static String logRedirectionError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("282005", 16, args, MessagingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "282005";
   }

   public static String logDeleteError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("282006", 16, args, MessagingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "282006";
   }

   public static String logSendPrepareError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("282007", 8, args, MessagingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "282007";
   }

   public static String logSendCommitAfterDeactivate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("282008", 8, args, MessagingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "282008";
   }

   public static Loggable logSendCommitAfterDeactivateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("282008", 8, args, "weblogic.messaging.MessagingLogLocalizer", MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MessagingLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReceiveCommitAfterDeactivate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("282009", 8, args, MessagingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "282009";
   }

   public static Loggable logReceiveCommitAfterDeactivateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("282009", 8, args, "weblogic.messaging.MessagingLogLocalizer", MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MessagingLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSequenceCommitAfterDeactivate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("282010", 8, args, MessagingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "282010";
   }

   public static String logDelayedAvailable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("282011", 16, args, MessagingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "282011";
   }

   public static String logPagingIOFailure(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("282012", 16, args, MessagingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "282012";
   }

   public static String logUnexpectedNullMessage(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("282013", 16, args, MessagingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "282013";
   }

   public static String logIgnore2PCRecord(String arg0, long arg1, String arg2, String arg3, long arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("282014", 16, args, MessagingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "282014";
   }

   public static String logErrorWritingToStore(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("282015", 8, args, MessagingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "282015";
   }

   public static String logWarningSAFStoreUpgradeConflict(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("282016", 16, args, MessagingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MessagingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "282016";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.messaging.MessagingLogLocalizer", MessagingLogger.class.getClassLoader());
      private MessageLogger messageLogger = MessagingLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = MessagingLogger.findMessageLogger();
      }
   }
}
