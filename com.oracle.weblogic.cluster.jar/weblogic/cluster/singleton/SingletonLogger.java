package weblogic.cluster.singleton;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class SingletonLogger {
   private static final String LOCALIZER_CLASS = "weblogic.cluster.singleton.SingletonLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(SingletonLogger.class.getName());
   }

   public static String logServerMigrationFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003501", 8, args, SingletonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SingletonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003501";
   }

   public static String logServerMigrationStarting(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("003502", 64, args, SingletonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SingletonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003502";
   }

   public static String logServerMigrationFinished(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("003503", 64, args, SingletonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SingletonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003503";
   }

   public static String logServerMigrationTargetUnreachable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("003504", 64, args, SingletonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SingletonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003504";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.cluster.singleton.SingletonLogLocalizer", SingletonLogger.class.getClassLoader());
      private MessageLogger messageLogger = SingletonLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = SingletonLogger.findMessageLogger();
      }
   }
}
