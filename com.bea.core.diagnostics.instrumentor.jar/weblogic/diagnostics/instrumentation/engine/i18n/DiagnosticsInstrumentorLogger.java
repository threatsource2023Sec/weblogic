package weblogic.diagnostics.instrumentation.engine.i18n;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class DiagnosticsInstrumentorLogger {
   private static final String LOCALIZER_CLASS = "weblogic.diagnostics.instrumentation.engine.l18n.DiagnosticsInstrumentorLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(DiagnosticsInstrumentorLogger.class.getName());
   }

   public static String logEngineConfigSaveError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320800", 8, args, DiagnosticsInstrumentorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsInstrumentorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320800";
   }

   public static String logEngineConfigReadError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320801", 8, args, DiagnosticsInstrumentorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsInstrumentorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320801";
   }

   public static String logMissingEngineConfigNameError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320802", 8, args, DiagnosticsInstrumentorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsInstrumentorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320802";
   }

   public static String logDuplicateEngineConfigNameError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320803", 8, args, DiagnosticsInstrumentorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsInstrumentorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320803";
   }

   public static String logInvalidParentConfigError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320804", 8, args, DiagnosticsInstrumentorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsInstrumentorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320804";
   }

   public static String logMissingRootConfigError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320805", 8, args, DiagnosticsInstrumentorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsInstrumentorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320805";
   }

   public static String logCircularDepenencyConfigError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320806", 8, args, DiagnosticsInstrumentorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsInstrumentorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320806";
   }

   public static String logMissingParentConfigError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320807", 8, args, DiagnosticsInstrumentorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsInstrumentorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320807";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.diagnostics.instrumentation.engine.l18n.DiagnosticsInstrumentorLogLocalizer", DiagnosticsInstrumentorLogger.class.getClassLoader());
      private MessageLogger messageLogger = DiagnosticsInstrumentorLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = DiagnosticsInstrumentorLogger.findMessageLogger();
      }
   }
}
