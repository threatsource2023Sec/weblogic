package weblogic.osgi;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class OSGiLogger {
   private static final String LOCALIZER_CLASS = "weblogic.osgi.OSGiLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(OSGiLogger.class.getName());
   }

   public static String logCouldNotInstallFile(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2163000", 16, args, OSGiLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OSGiLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2163000";
   }

   public static String logCouldNotAdvertiseDataSource(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2163001", 16, args, OSGiLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OSGiLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2163001";
   }

   public static String logCouldNotDeployFromOSGiLib(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2163002", 8, args, OSGiLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OSGiLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2163002";
   }

   public static String logStartedServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2163003", 64, args, OSGiLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OSGiLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2163003";
   }

   public static String logCouldNotAdvertiseWorkManager(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2163004", 16, args, OSGiLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OSGiLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2163004";
   }

   public static String logConflictingFrameworkNames(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2163005", 8, args, OSGiLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OSGiLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2163005";
   }

   public static Loggable logConflictingFrameworkNamesLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2163005", 8, args, "weblogic.osgi.OSGiLogLocalizer", OSGiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OSGiLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConflictingBSNNames(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2163006", 8, args, OSGiLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OSGiLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2163006";
   }

   public static Loggable logConflictingBSNNamesLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2163006", 8, args, "weblogic.osgi.OSGiLogLocalizer", OSGiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OSGiLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.osgi.OSGiLogLocalizer", OSGiLogger.class.getClassLoader());
      private MessageLogger messageLogger = OSGiLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = OSGiLogger.findMessageLogger();
      }
   }
}
