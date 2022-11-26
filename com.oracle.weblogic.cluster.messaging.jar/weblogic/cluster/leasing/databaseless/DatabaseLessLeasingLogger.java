package weblogic.cluster.leasing.databaseless;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class DatabaseLessLeasingLogger {
   private static final String LOCALIZER_CLASS = "weblogic.cluster.leasing.databaseless.DatabaseLessLeasingLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(DatabaseLessLeasingLogger.class.getName());
   }

   public static String logServerNotStartedByNodeManager() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000001", 4, args, DatabaseLessLeasingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DatabaseLessLeasingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000001";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.cluster.leasing.databaseless.DatabaseLessLeasingLogLocalizer", DatabaseLessLeasingLogger.class.getClassLoader());
      private MessageLogger messageLogger = DatabaseLessLeasingLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = DatabaseLessLeasingLogger.findMessageLogger();
      }
   }
}
