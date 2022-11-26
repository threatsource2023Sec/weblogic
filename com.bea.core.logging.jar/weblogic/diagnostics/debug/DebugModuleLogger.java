package weblogic.diagnostics.debug;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class DebugModuleLogger {
   private static final String LOCALIZER_CLASS = "weblogic.diagnostics.debug.DebugModuleLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(DebugModuleLogger.class.getName());
   }

   public static String logInvalidDebugScopeName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320450", 8, args, DebugModuleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DebugModuleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320450";
   }

   public static String logErrorConfiguringDebugScopes(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320451", 8, args, DebugModuleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DebugModuleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320451";
   }

   public static String logErrorAddingPropertyChangeListener(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320452", 8, args, DebugModuleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DebugModuleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320452";
   }

   public static String logErrorRemovingPropertyChangeListener(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320453", 8, args, DebugModuleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DebugModuleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320453";
   }

   public static String logErrorInitializingDebugCategories(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320454", 8, args, DebugModuleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DebugModuleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320454";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.diagnostics.debug.DebugModuleLogLocalizer", DebugModuleLogger.class.getClassLoader());
      private MessageLogger messageLogger = DebugModuleLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = DebugModuleLogger.findMessageLogger();
      }
   }
}
