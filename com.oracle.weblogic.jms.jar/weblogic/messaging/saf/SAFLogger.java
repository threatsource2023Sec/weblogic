package weblogic.messaging.saf;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class SAFLogger {
   private static final String LOCALIZER_CLASS = "weblogic.messaging.saf.SAFLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(SAFLogger.class.getName());
   }

   public static String logSAFStarted() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("281002", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281002";
   }

   public static String logSAFInitialized() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("281003", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281003";
   }

   public static String logSAFSuspended() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("281004", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281004";
   }

   public static String logSAFShutdown() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("281005", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281005";
   }

   public static String logExpiredMessage(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281006", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281006";
   }

   public static String logSAFAgentPrepared(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281007", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281007";
   }

   public static String logSAFAgentActivated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281008", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281008";
   }

   public static String logSAFAgentDeactivated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281009", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281009";
   }

   public static String logSAFAgentUnprepared(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281010", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281010";
   }

   public static String logErrorPrepareSAFAgent(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("281011", 8, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281011";
   }

   public static String logErrorStartSAFAgent(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("281012", 8, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281012";
   }

   public static String logBytesThresholdHighAgent(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281016", 2, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281016";
   }

   public static String logBytesThresholdLowAgent(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281017", 2, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281017";
   }

   public static String logMessagesThresholdHighAgent(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281018", 2, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281018";
   }

   public static String logMessagesThresholdLowAgent(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281019", 2, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281019";
   }

   public static String logSAFConnected(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281020", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281020";
   }

   public static String logSAFDisconnected(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("281021", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281021";
   }

   public static String logErrorResumeAgent(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("281022", 8, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281022";
   }

   public static String logIncomingPauseOfSAFAgent(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281025", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281025";
   }

   public static String logIncomingResumeOfSAFAgent(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281026", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281026";
   }

   public static String logForwardingPauseOfSAFAgent(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281027", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281027";
   }

   public static String logForwardingResumeOfSAFAgent(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281028", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281028";
   }

   public static String logIncomingPauseOfRemoteEndpoint(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281029", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281029";
   }

   public static String logIncomingResumeOfRemoteEndpoint(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281030", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281030";
   }

   public static String logForwardingPauseOfRemoteEndpoint(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281031", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281031";
   }

   public static String logForwardingResumeOfRemoteEndpoint(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("281032", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281032";
   }

   public static String logInvalidExactlyOnceLBPolicyProperty(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("281033", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281033";
   }

   public static String logEffectiveExactlyOnceLBPolicyProperty(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("281034", 64, args, SAFLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SAFLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "281034";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.messaging.saf.SAFLogLocalizer", SAFLogger.class.getClassLoader());
      private MessageLogger messageLogger = SAFLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = SAFLogger.findMessageLogger();
      }
   }
}
