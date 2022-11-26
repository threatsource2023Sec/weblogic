package weblogic.kernel;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class KernelLogger {
   private static final String LOCALIZER_CLASS = "weblogic.kernel.KernelLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(KernelLogger.class.getName());
   }

   public static String logStopped(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000800", 4, args, KernelLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      KernelLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000800";
   }

   public static String logExecuteCancelled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000801", 64, args, KernelLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      KernelLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000801";
   }

   public static String logExecuteFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000802", 8, args, KernelLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      KernelLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000802";
   }

   public static String logNoConstructorWithStringParam(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("000803", 8, args, KernelLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      KernelLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000803";
   }

   public static String logErrorInitialingFromSystemProperties(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("000804", 8, args, KernelLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      KernelLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000804";
   }

   public static String logInitializingKernelDelegator() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000805", 32, args, KernelLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      KernelLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000805";
   }

   public static String logDebugQueueThrottle(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("000807", 64, args, KernelLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      KernelLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000807";
   }

   public static String logWarnSwitchToWorkManagerAPI() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000808", 16, args, KernelLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      KernelLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000808";
   }

   public static String logDebugSwitchToWorkManagerAPI(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000809", 128, args, KernelLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      KernelLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000809";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.kernel.KernelLogLocalizer", KernelLogger.class.getClassLoader());
      private MessageLogger messageLogger = KernelLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = KernelLogger.findMessageLogger();
      }
   }
}
