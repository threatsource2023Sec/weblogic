package weblogic.j2eeclient;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class AppClientLogger {
   private static final String LOCALIZER_CLASS = "weblogic.j2eeclient.AppClientLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(AppClientLogger.class.getName());
   }

   public static String logNoMainClassDefined4JavaModule(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162200", 64, args, AppClientLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      AppClientLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162200";
   }

   public static String userTransactionEntryNotFound(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162206", 16, args, AppClientLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      AppClientLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162206";
   }

   public static Loggable userTransactionEntryNotFoundLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162206", 16, args, "weblogic.j2eeclient.AppClientLogLocalizer", AppClientLogger.MessageLoggerInitializer.INSTANCE.messageLogger, AppClientLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String persistentInitAvailableInScope(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162210", 64, args, AppClientLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      AppClientLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162210";
   }

   public static Loggable persistentInitAvailableInScopeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162210", 64, args, "weblogic.j2eeclient.AppClientLogLocalizer", AppClientLogger.MessageLoggerInitializer.INSTANCE.messageLogger, AppClientLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String missingMainAttribute(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162224", 64, args, AppClientLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      AppClientLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162224";
   }

   public static Loggable missingMainAttributeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2162224", 64, args, "weblogic.j2eeclient.AppClientLogLocalizer", AppClientLogger.MessageLoggerInitializer.INSTANCE.messageLogger, AppClientLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String defaultJMSConnectionFactoryBindFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162231", 16, args, AppClientLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      AppClientLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162231";
   }

   public static Loggable defaultJMSConnectionFactoryBindFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162231", 16, args, "weblogic.j2eeclient.AppClientLogLocalizer", AppClientLogger.MessageLoggerInitializer.INSTANCE.messageLogger, AppClientLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.j2eeclient.AppClientLogLocalizer", AppClientLogger.class.getClassLoader());
      private MessageLogger messageLogger = AppClientLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = AppClientLogger.findMessageLogger();
      }
   }
}
