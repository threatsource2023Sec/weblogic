package weblogic.application.config;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class CustomModuleLogger {
   private static final String LOCALIZER_CLASS = "weblogic.application.config.CustomModuleLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(CustomModuleLogger.class.getName());
   }

   public static String logNoConfigSupport(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("340450", 16, args, CustomModuleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CustomModuleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "340450";
   }

   public static String logConfigSupportUriMismatch(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("340451", 8, args, CustomModuleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CustomModuleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "340451";
   }

   public static String logPrepareDeploy(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("340452", 64, args, CustomModuleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CustomModuleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "340452";
   }

   public static String logPrepareUpdate(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("340454", 64, args, CustomModuleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CustomModuleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "340454";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.application.config.CustomModuleLogLocalizer", CustomModuleLogger.class.getClassLoader());
      private MessageLogger messageLogger = CustomModuleLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = CustomModuleLogger.findMessageLogger();
      }
   }
}
