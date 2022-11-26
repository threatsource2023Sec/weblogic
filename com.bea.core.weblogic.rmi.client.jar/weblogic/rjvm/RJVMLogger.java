package weblogic.rjvm;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18n.logging.MessageResetScheduler;
import weblogic.i18ntools.L10nLookup;

public class RJVMLogger {
   private static final String LOCALIZER_CLASS = "weblogic.rjvm.RJVMLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(RJVMLogger.class.getName());
   }

   public static String logDebug(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000500", 128, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000500";
   }

   public static String logTargetUnreach() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000501", 64, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000501";
   }

   public static String logTargetGone() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000502", 64, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000502";
   }

   public static String logUnmarshal(Exception arg0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("000503")) {
         return "000503";
      } else {
         Object[] args = new Object[]{arg0};
         CatalogMessage catalogMessage = new CatalogMessage("000503", 8, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(true);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("000503", 5000L);
         return "000503";
      }
   }

   public static void resetlogUnmarshal() {
      MessageResetScheduler.getInstance().resetLogMessage("000503");
   }

   public static String logUnmarshal2(Exception arg0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("000504")) {
         return "000504";
      } else {
         Object[] args = new Object[]{arg0};
         CatalogMessage catalogMessage = new CatalogMessage("000504", 8, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(true);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("000504", 5000L);
         return "000504";
      }
   }

   public static void resetlogUnmarshal2() {
      MessageResetScheduler.getInstance().resetLogMessage("000504");
   }

   public static String logBadInterval() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000505", 8, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000505";
   }

   public static String logClose(String arg0, String arg1) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("000506")) {
         return "000506";
      } else {
         Object[] args = new Object[]{arg0, arg1};
         CatalogMessage catalogMessage = new CatalogMessage("000506", 8, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(true);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("000506", 5000L);
         return "000506";
      }
   }

   public static void resetlogClose() {
      MessageResetScheduler.getInstance().resetLogMessage("000506");
   }

   public static String logCloseError(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000507", 16, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000507";
   }

   public static String logBadInstall(int arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000508", 8, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000508";
   }

   public static String logFinderInit() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000509", 8, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000509";
   }

   public static String logUnsolResponse(int arg0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("000510")) {
         return "000510";
      } else {
         Object[] args = new Object[]{arg0};
         CatalogMessage catalogMessage = new CatalogMessage("000510", 16, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(true);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("000510", 5000L);
         return "000510";
      }
   }

   public static void resetlogUnsolResponse() {
      MessageResetScheduler.getInstance().resetLogMessage("000510");
   }

   public static String logUnsolResponseError(int arg0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("000511")) {
         return "000511";
      } else {
         Object[] args = new Object[]{arg0};
         CatalogMessage catalogMessage = new CatalogMessage("000511", 16, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(true);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("000511", 5000L);
         return "000511";
      }
   }

   public static void resetlogUnsolResponseError() {
      MessageResetScheduler.getInstance().resetLogMessage("000511");
   }

   public static String logHBPeriod(long arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000512", 64, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000512";
   }

   public static String logHBTrigger(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000513", 64, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000513";
   }

   public static String logOpenFailed(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000514", 8, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000514";
   }

   public static String logExecuteFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000515", 8, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000515";
   }

   public static String logDebug2(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000517", 128, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000517";
   }

   public static String logDebug3(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000518", 128, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000518";
   }

   public static String logConnectingFailureWarning(String arg0, String arg1, int arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("000519", 16, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000519";
   }

   public static String logChannelConfiguration(String arg0, String arg1, String arg2, boolean arg3, boolean arg4, boolean arg5, boolean arg6, boolean arg7) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7};
      CatalogMessage catalogMessage = new CatalogMessage("000570", 64, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000570";
   }

   public static String logChannelSettings(String arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8};
      CatalogMessage catalogMessage = new CatalogMessage("000571", 128, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000571";
   }

   public static String logBadNAT(String arg0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("000572")) {
         return "000572";
      } else {
         Object[] args = new Object[]{arg0};
         CatalogMessage catalogMessage = new CatalogMessage("000572", 8, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(false);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("000572", 5000L);
         return "000572";
      }
   }

   public static void resetlogBadNAT() {
      MessageResetScheduler.getInstance().resetLogMessage("000572");
   }

   public static String logRequestTimeout(int arg0, String arg1) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("000573")) {
         return "000573";
      } else {
         Object[] args = new Object[]{arg0, arg1};
         CatalogMessage catalogMessage = new CatalogMessage("000573", 16, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(true);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("000573", 5000L);
         return "000573";
      }
   }

   public static void resetlogRequestTimeout() {
      MessageResetScheduler.getInstance().resetLogMessage("000573");
   }

   public static String logBootstrapException(String arg0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("000574")) {
         return "000574";
      } else {
         Object[] args = new Object[]{arg0};
         CatalogMessage catalogMessage = new CatalogMessage("000574", 8, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
         catalogMessage.setStackTraceEnabled(true);
         catalogMessage.setDiagnosticVolume("Off");
         catalogMessage.setExcludePartition(true);
         RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
         MessageResetScheduler.getInstance().scheduleMessageReset("000574", 5000L);
         return "000574";
      }
   }

   public static void resetlogBootstrapException() {
      MessageResetScheduler.getInstance().resetLogMessage("000574");
   }

   public static String logUnableGetWebRjvmSupportHK2(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000577", 16, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000577";
   }

   public static String logUnableGetWebRjvmSupportReflect(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000578", 8, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000578";
   }

   public static String logFailedGetClientCertificate(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000579", 8, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000579";
   }

   public static String logFailedSendMsgWarning(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000581", 16, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000581";
   }

   public static String logRemoteAnonymousRMIT3AccessNotAllowed(byte arg0, int arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("000582", 8, args, RJVMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RJVMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000582";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.rjvm.RJVMLogLocalizer", RJVMLogger.class.getClassLoader());
      private MessageLogger messageLogger = RJVMLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = RJVMLogger.findMessageLogger();
      }
   }
}
