package weblogic.diagnostics.i18n;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class DiagnosticsRuntimeControlLogger {
   private static final String LOCALIZER_CLASS = "weblogic.diagnostics.l10n.DiagnosticsRuntimeControlLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(DiagnosticsRuntimeControlLogger.class.getName());
   }

   public static String logBuiltinResourceDescriptorNotFound(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320500", 8, args, DiagnosticsRuntimeControlLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsRuntimeControlLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320500";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.diagnostics.l10n.DiagnosticsRuntimeControlLogLocalizer", DiagnosticsRuntimeControlLogger.class.getClassLoader());
      private MessageLogger messageLogger = DiagnosticsRuntimeControlLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = DiagnosticsRuntimeControlLogger.findMessageLogger();
      }
   }
}
