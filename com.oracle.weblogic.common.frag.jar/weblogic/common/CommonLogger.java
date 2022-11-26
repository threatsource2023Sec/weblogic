package weblogic.common;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class CommonLogger {
   private static final String LOCALIZER_CLASS = "weblogic.common.CommonLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(CommonLogger.class.getName());
   }

   public static String logCallbackFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000600", 8, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000600";
   }

   public static String logEnabled(long arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000601", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000601";
   }

   public static String logEnableFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000602", 8, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000602";
   }

   public static String logDisabled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000603", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000603";
   }

   public static String logDisableFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000604", 8, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000604";
   }

   public static String logLost() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000605", 8, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000605";
   }

   public static String logNoEcho(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000606", 8, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000606";
   }

   public static String logEcho() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000607", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000607";
   }

   public static String logTick(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000608", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000608";
   }

   public static String logErrorWhileServerShutdown(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000609", 8, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000609";
   }

   public static String logUnexpectedProblem(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000610", 8, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000610";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.common.CommonLogLocalizer", CommonLogger.class.getClassLoader());
      private MessageLogger messageLogger = CommonLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = CommonLogger.findMessageLogger();
      }
   }
}
