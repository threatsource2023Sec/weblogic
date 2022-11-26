package weblogic.diagnostics.i18n;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class DiagnosticsHarvesterLogger {
   private static final String LOCALIZER_CLASS = "weblogic.diagnostics.l10n.DiagnosticsHarvesterLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(DiagnosticsHarvesterLogger.class.getName());
   }

   public static String logServerRuntimeMBeanServerNotAvailable() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320300", 8, args, DiagnosticsHarvesterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsHarvesterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320300";
   }

   public static String logErrorActivatingWatchConfiguration(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320301", 8, args, DiagnosticsHarvesterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsHarvesterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320301";
   }

   public static String logUnservicableHarvestedTypeNamespaceError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320303", 16, args, DiagnosticsHarvesterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsHarvesterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320303";
   }

   public static String logValidationErrors(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320304", 16, args, DiagnosticsHarvesterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsHarvesterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320304";
   }

   public static String logInstanceNameInvalid(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320305", 8, args, DiagnosticsHarvesterLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsHarvesterLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320305";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.diagnostics.l10n.DiagnosticsHarvesterLogLocalizer", DiagnosticsHarvesterLogger.class.getClassLoader());
      private MessageLogger messageLogger = DiagnosticsHarvesterLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = DiagnosticsHarvesterLogger.findMessageLogger();
      }
   }
}
