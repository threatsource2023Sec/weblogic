package weblogic.iiop;

import java.io.IOException;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18n.logging.MessageResetScheduler;
import weblogic.i18ntools.L10nLookup;

public class IIOPLogger {
   private static final String LOCALIZER_CLASS = "weblogic.iiop.IIOPLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(IIOPLogger.class.getName());
   }

   public static String logGarbageMessage() {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("002001")) {
         return "002001";
      } else {
         Object[] args = new Object[0];
         CatalogMessage catalogMessage = new CatalogMessage("002001", 64, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(false);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("002001", 5000L);
         return "002001";
      }
   }

   public static void resetlogGarbageMessage() {
      MessageResetScheduler.getInstance().resetLogMessage("002001");
   }

   public static String logMethodParseFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002002", 64, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002002";
   }

   public static String logSendFailure(IOException arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002003", 8, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002003";
   }

   public static String logOutOfMemory(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002005", 4, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002005";
   }

   public static String logScavengeCreateFailure(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002006", 8, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002006";
   }

   public static String logExceptionSending(IOException arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002024", 128, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002024";
   }

   public static String logExceptionReceiving(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002025", 128, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002025";
   }

   public static String logFailedToExport(String arg0, IOException arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002008", 64, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002008";
   }

   public static String logMarshalExceptionFailure(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002011", 8, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002011";
   }

   public static String logSendExceptionFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002012", 8, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002012";
   }

   public static String logSendExceptionCompletelyFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002013", 8, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002013";
   }

   public static String logEnabled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002014", 64, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002014";
   }

   public static String logUtilClassNotInstalled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002015", 8, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002015";
   }

   public static String logPROClassNotInstalled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002016", 8, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002016";
   }

   public static String logLocateRequest(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002017", 64, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002017";
   }

   public static String logGIOPVersion(byte arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002018", 64, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002018";
   }

   public static String logLocationForwardPolicy(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002019", 64, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002019";
   }

   public static String logSecurityService(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002021", 64, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002021";
   }

   public static String logSecurityServiceFailed(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002022", 16, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002022";
   }

   public static String logBadRuntime(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002023", 16, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002023";
   }

   public static String logJTAEnabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002026", 64, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002026";
   }

   public static String logCompleteMarshalExceptionFailure(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002027", 8, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002027";
   }

   public static String logOTSError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002028", 16, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002028";
   }

   public static String logDebugOTS(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002029", 128, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002029";
   }

   public static String logDebugTransport(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002030", 128, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002030";
   }

   public static String logDebugMarshal(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002031", 128, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002031";
   }

   public static String logDebugMarshalError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002032", 128, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002032";
   }

   public static String logCodeSet(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("002033", 64, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002033";
   }

   public static String logDebugNaming(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002034", 128, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002034";
   }

   public static String logDebugSecurity(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002035", 128, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002035";
   }

   public static String logDebugReplacer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002036", 128, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002036";
   }

   public static String logCosNamingService(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002037", 64, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002037";
   }

   public static String logCosNamingServiceFailed(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002038", 16, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002038";
   }

   public static String logReplacerFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002039", 16, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002039";
   }

   public static String logConnectionRejected() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002041", 16, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002041";
   }

   public static String logDebugConnection(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002042", 128, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002042";
   }

   public static String logNamingException(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002043", 16, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002043";
   }

   public static String logClassNotFound(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("002044", 8, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002044";
   }

   public static String logRemoteAnonymousRMIAccesssNotAllowed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002045", 8, args, IIOPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      IIOPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002045";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.iiop.IIOPLogLocalizer", IIOPLogger.class.getClassLoader());
      private MessageLogger messageLogger = IIOPLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = IIOPLogger.findMessageLogger();
      }
   }
}
