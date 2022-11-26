package weblogic.cluster.messaging;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class UnicastLogger {
   private static final String LOCALIZER_CLASS = "weblogic.cluster.messaging.UnicastLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(UnicastLogger.class.getName());
   }

   public static String logUnicastBootStrapRejected(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("004501", 16, args, UnicastLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      UnicastLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "004501";
   }

   public static String logUnicastBootStrapException(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("004502", 16, args, UnicastLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      UnicastLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "004502";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.cluster.messaging.UnicastLogLocalizer", UnicastLogger.class.getClassLoader());
      private MessageLogger messageLogger = UnicastLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = UnicastLogger.findMessageLogger();
      }
   }
}
