package weblogic.kernel;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class T3Srvr2Logger {
   private static final String LOCALIZER_CLASS = "weblogic.kernel.T3Srvr2LogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(T3Srvr2Logger.class.getName());
   }

   public static String logCantParseRandomSeed(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003800", 16, args, T3Srvr2Logger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3Srvr2Logger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003800";
   }

   public static String logCantParseRandomSeedFile(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003801", 16, args, T3Srvr2Logger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3Srvr2Logger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003801";
   }

   public static String logStartupServicesRandomized(long arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003802", 64, args, T3Srvr2Logger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3Srvr2Logger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003802";
   }

   public static String logCantWriteRandomSeedFile(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003803", 16, args, T3Srvr2Logger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3Srvr2Logger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003803";
   }

   public static String logRandomizerFileNotReadable(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003804", 16, args, T3Srvr2Logger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3Srvr2Logger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003804";
   }

   public static String logRandomizerFileDataNotReadable(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003805", 16, args, T3Srvr2Logger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3Srvr2Logger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003805";
   }

   public static String logErrorRedirectingStream(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003806", 16, args, T3Srvr2Logger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3Srvr2Logger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003806";
   }

   public static String logFilterMode(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003807", 64, args, T3Srvr2Logger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3Srvr2Logger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003807";
   }

   public static String logFilterScope(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("003808", 64, args, T3Srvr2Logger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3Srvr2Logger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003808";
   }

   public static String logFilterNotEnabled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("003809", 64, args, T3Srvr2Logger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3Srvr2Logger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003809";
   }

   public static String logFilterLimit(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003810", 64, args, T3Srvr2Logger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3Srvr2Logger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003810";
   }

   public static String logFilterBlackListPackage(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003811", 64, args, T3Srvr2Logger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3Srvr2Logger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003811";
   }

   public static String logFilterBlackListClass(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003812", 64, args, T3Srvr2Logger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3Srvr2Logger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003812";
   }

   public static String logFilterWhiteList(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("003813", 64, args, T3Srvr2Logger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3Srvr2Logger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "003813";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.kernel.T3Srvr2LogLocalizer", T3Srvr2Logger.class.getClassLoader());
      private MessageLogger messageLogger = T3Srvr2Logger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = T3Srvr2Logger.findMessageLogger();
      }
   }
}
