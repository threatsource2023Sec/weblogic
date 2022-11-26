package weblogic.cache;

import java.io.IOException;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class CacheLogger {
   private static final String LOCALIZER_CLASS = "weblogic.cache.CacheLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(CacheLogger.class.getName());
   }

   public static String logMessageException(String arg0, IOException arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003000", 16, args, CacheLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CacheLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003000";
   }

   public static String logLeaseException(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003001", 16, args, CacheLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CacheLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003001";
   }

   public static String logReplicationException(IOException arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003002", 16, args, CacheLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CacheLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003002";
   }

   public static String logDebug(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003003", 128, args, CacheLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CacheLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003003";
   }

   public static String logWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003004", 16, args, CacheLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CacheLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003004";
   }

   public static String logNotCachingTheResponse(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003005", 16, args, CacheLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CacheLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003005";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.cache.CacheLogLocalizer", CacheLogger.class.getClassLoader());
      private MessageLogger messageLogger = CacheLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = CacheLogger.findMessageLogger();
      }
   }
}
