package weblogic.cacheprovider;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class CacheProviderLogger {
   private static final String LOCALIZER_CLASS = "weblogic.cacheprovider.CacheProviderLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(CacheProviderLogger.class.getName());
   }

   public static String logMultipleCoherenceClusterSystemResourceMBeanTargetted(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2156300", 32, args, CacheProviderLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CacheProviderLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156300";
   }

   public static String logFailedToUnprepare(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156301", 8, args, CacheProviderLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CacheProviderLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156301";
   }

   public static String logMoreThanOneManagementProxy(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2156302", 8, args, CacheProviderLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CacheProviderLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156302";
   }

   public static Loggable logMoreThanOneManagementProxyLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2156302", 8, args, "weblogic.cacheprovider.CacheProviderLogLocalizer", CacheProviderLogger.MessageLoggerInitializer.INSTANCE.messageLogger, CacheProviderLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToInvoke(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2156303", 16, args, CacheProviderLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CacheProviderLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156303";
   }

   public static Loggable logFailedToInvokeLoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2156303", 16, args, "weblogic.cacheprovider.CacheProviderLogLocalizer", CacheProviderLogger.MessageLoggerInitializer.INSTANCE.messageLogger, CacheProviderLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServerNotAvailable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2156304", 16, args, CacheProviderLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CacheProviderLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156304";
   }

   public static Loggable logServerNotAvailableLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2156304", 16, args, "weblogic.cacheprovider.CacheProviderLogLocalizer", CacheProviderLogger.MessageLoggerInitializer.INSTANCE.messageLogger, CacheProviderLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.cacheprovider.CacheProviderLogLocalizer", CacheProviderLogger.class.getClassLoader());
      private MessageLogger messageLogger = CacheProviderLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = CacheProviderLogger.findMessageLogger();
      }
   }
}
