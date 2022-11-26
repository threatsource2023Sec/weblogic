package weblogic.nodemanager;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class NodeManagerLogger {
   private static final String LOCALIZER_CLASS = "weblogic.nodemanager.NodeManagerLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(NodeManagerLogger.class.getName());
   }

   public static String logNMNotRunning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("300037", 16, args, NodeManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      NodeManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "300037";
   }

   public static String logStateChangeNotificationFailureMsg(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("300040", 8, args, NodeManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      NodeManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "300040";
   }

   public static String logNativePidSupportUnavailable() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("300043", 16, args, NodeManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      NodeManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "300043";
   }

   public static String logErrorWritingPidFile(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("300044", 8, args, NodeManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      NodeManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "300044";
   }

   public static String logDebugMsgWithException(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("300047", 128, args, NodeManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      NodeManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "300047";
   }

   public static String logServerStartFailure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("300048", 8, args, NodeManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      NodeManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "300048";
   }

   public static String logErrorWritingURLFile(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("300049", 8, args, NodeManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      NodeManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "300049";
   }

   public static String logErrorUpdatingServerProps(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("300051", 8, args, NodeManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      NodeManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "300051";
   }

   public static String logNoIPFoundForMigratableServer() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("300053", 8, args, NodeManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      NodeManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "300053";
   }

   public static String logUnknownMigratableListenAddress(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("300054", 8, args, NodeManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      NodeManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "300054";
   }

   public static String illegalNullMachineMBean() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("300055", 8, args, NodeManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      NodeManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "300055";
   }

   public static String logErrorRotatingLogFiles(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("300056", 8, args, NodeManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      NodeManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "300056";
   }

   public static String logServerStartOnMachine(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("300057", 16, args, NodeManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      NodeManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "300057";
   }

   public static String logServerStartOnMachine1(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("300058", 16, args, NodeManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      NodeManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "300058";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.nodemanager.NodeManagerLogLocalizer", NodeManagerLogger.class.getClassLoader());
      private MessageLogger messageLogger = NodeManagerLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = NodeManagerLogger.findMessageLogger();
      }
   }
}
