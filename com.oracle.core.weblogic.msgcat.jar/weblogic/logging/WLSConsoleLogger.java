package weblogic.logging;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class WLSConsoleLogger {
   private static final String LOCALIZER_CLASS = "weblogic.logging.WLSConsoleLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(WLSConsoleLogger.class.getName());
   }

   public static String logConsoleDebug(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("240000", 128, args, WLSConsoleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WLSConsoleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "240000";
   }

   public static String logConsoleInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("240001", 64, args, WLSConsoleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WLSConsoleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "240001";
   }

   public static String logConsoleWarn(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("240002", 64, args, WLSConsoleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WLSConsoleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "240002";
   }

   public static String logConsoleError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("240003", 8, args, WLSConsoleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WLSConsoleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "240003";
   }

   public static String logConsoleFatal(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("240004", 4, args, WLSConsoleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WLSConsoleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "240004";
   }

   public static String logCSRF(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("240005", 4, args, WLSConsoleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WLSConsoleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "240005";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.logging.WLSConsoleLogLocalizer", WLSConsoleLogger.class.getClassLoader());
      private MessageLogger messageLogger = WLSConsoleLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = WLSConsoleLogger.findMessageLogger();
      }
   }
}
