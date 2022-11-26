package weblogic.cache.configuration;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class BEACacheLogger {
   private static final String LOCALIZER_CLASS = "weblogic.cache.configuration.BEACacheLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(BEACacheLogger.class.getName());
   }

   public static String logUnableToFireListeners(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2145000", 16, args, BEACacheLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BEACacheLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2145000";
   }

   public static String logUnableToConfigureWritePolicyWithoutStore(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2145001", 8, args, BEACacheLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BEACacheLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2145001";
   }

   public static Loggable logUnableToConfigureWritePolicyWithoutStoreLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2145001", 8, args, "weblogic.cache.configuration.BEACacheLogLocalizer", BEACacheLogger.MessageLoggerInitializer.INSTANCE.messageLogger, BEACacheLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.cache.configuration.BEACacheLogLocalizer", BEACacheLogger.class.getClassLoader());
      private MessageLogger messageLogger = BEACacheLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = BEACacheLogger.findMessageLogger();
      }
   }
}
