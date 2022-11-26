package weblogic.messaging.interception;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class MILogger {
   private static final String LOCALIZER_CLASS = "weblogic.messaging.interception.MILogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(MILogger.class.getName());
   }

   public static String logStartMessageInterceptionService() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("400000", 64, args, MILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "400000";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.messaging.interception.MILogLocalizer", MILogger.class.getClassLoader());
      private MessageLogger messageLogger = MILogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = MILogger.findMessageLogger();
      }
   }
}
