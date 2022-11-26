package weblogic.management.eventbus;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class InternalEventBusLogger {
   private static final String LOCALIZER_CLASS = "weblogic.management.eventbus.InternalEventBusLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(InternalEventBusLogger.class.getName());
   }

   public static String logErrorProcessingEvent(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2159000", 16, args, InternalEventBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      InternalEventBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2159000";
   }

   public static String logErrorInitializingEventBus(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2159001", 8, args, InternalEventBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      InternalEventBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2159001";
   }

   public static String logErrorLoadingListenerClass(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2159002", 16, args, InternalEventBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      InternalEventBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2159002";
   }

   public static String logErrorInstantiatingListenerInstance(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2159003", 16, args, InternalEventBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      InternalEventBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2159003";
   }

   public static String logErrorRegisteringResourceListeners(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2159004", 16, args, InternalEventBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      InternalEventBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2159004";
   }

   public static String logErrorProcessingInternalEvent(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2159005", 16, args, InternalEventBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      InternalEventBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2159005";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.eventbus.InternalEventBusLogLocalizer", InternalEventBusLogger.class.getClassLoader());
      private MessageLogger messageLogger = InternalEventBusLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = InternalEventBusLogger.findMessageLogger();
      }
   }
}
