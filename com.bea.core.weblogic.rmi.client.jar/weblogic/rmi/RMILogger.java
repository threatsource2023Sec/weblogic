package weblogic.rmi;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class RMILogger {
   private static final String LOCALIZER_CLASS = "weblogic.rmi.RMILogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(RMILogger.class.getName());
   }

   public static String logErrorDisp(Error arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("080001", 8, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080001";
   }

   public static String logUnexport(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("080002", 8, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080002";
   }

   public static String logRuntimeException(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("080003", 16, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080003";
   }

   public static String logError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("080004", 16, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080004";
   }

   public static String logException(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("080005", 16, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080005";
   }

   public static String logAssociateTX(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("080006", 16, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080006";
   }

   public static String logRunDisabled(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("080007", 8, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080007";
   }

   public static String logSendError(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("080008", 8, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080008";
   }

   public static String logErrorServer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("080009", 16, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080009";
   }

   public static String logEmptyWS(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("080011", 64, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080011";
   }

   public static String logNoWS(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("080012", 8, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080012";
   }

   public static String logNoRef(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("080013", 8, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080013";
   }

   public static String logDebug(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("080014", 128, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080014";
   }

   public static String logNotMarked(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("080015", 8, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080015";
   }

   public static String logNoConnection(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("080016", 16, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080016";
   }

   public static String logFailedRenew(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("080017", 16, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080017";
   }

   public static String logMarked(long arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("080019", 64, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080019";
   }

   public static String logSweepException(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("080020", 8, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080020";
   }

   public static String logSweepFreed(long arg0, long arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("080021", 64, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080021";
   }

   public static String logEnrollLostRef(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("080022", 64, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080022";
   }

   public static String logUnenrollLostRef(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("080023", 64, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080023";
   }

   public static String logRenewLease(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("080024", 64, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080024";
   }

   public static String logExportingRemoteObject(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("080026", 64, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080026";
   }

   public static String logHeartbeatPeerClosed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("080027", 64, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080027";
   }

   public static String logFailOverFailureTrace(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("080028", 64, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080028";
   }

   public static String logOneWayRequestCancelled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("080029", 8, args, RMILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RMILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "080029";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.rmi.RMILogLocalizer", RMILogger.class.getClassLoader());
      private MessageLogger messageLogger = RMILogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = RMILogger.findMessageLogger();
      }
   }
}
