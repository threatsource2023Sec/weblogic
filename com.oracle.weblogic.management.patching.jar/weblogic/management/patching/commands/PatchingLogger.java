package weblogic.management.patching.commands;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class PatchingLogger {
   private static final String LOCALIZER_CLASS = "weblogic.management.patching.commands.PatchingLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(PatchingLogger.class.getName());
   }

   public static String logScriptOutput(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192100", 64, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192100";
   }

   public static String timeoutPollingReadyApp(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192101", 8, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192101";
   }

   public static Loggable timeoutPollingReadyAppLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192101", 8, args, "weblogic.management.patching.commands.PatchingLogLocalizer", PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PatchingLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFindURLError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192102", 8, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192102";
   }

   public static Loggable logFindURLErrorLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192102", 8, args, "weblogic.management.patching.commands.PatchingLogLocalizer", PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PatchingLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorOpeningURL(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192103", 8, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192103";
   }

   public static Loggable logErrorOpeningURLLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192103", 8, args, "weblogic.management.patching.commands.PatchingLogLocalizer", PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PatchingLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullServerRuntimeMBeanError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192104", 8, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192104";
   }

   public static Loggable logNullServerRuntimeMBeanErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192104", 8, args, "weblogic.management.patching.commands.PatchingLogLocalizer", PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PatchingLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServerLifecycleOperationError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192105", 8, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192105";
   }

   public static Loggable logServerLifecycleOperationErrorLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192105", 8, args, "weblogic.management.patching.commands.PatchingLogLocalizer", PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PatchingLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCanNotUregisterMBean(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192106", 8, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192106";
   }

   public static String logExecutingStep(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192107", 64, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192107";
   }

   public static String logRetryingStep(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192108", 64, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192108";
   }

   public static String logRevertingStep(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192109", 64, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192109";
   }

   public static String logCompletedStep(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192110", 64, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192110";
   }

   public static String logCompletedRevertStep(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192111", 64, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192111";
   }

   public static String logFailedStepNoError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192112", 64, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192112";
   }

   public static String logFailedStep(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2192113", 64, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192113";
   }

   public static String logFailedRevertStepNoError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192114", 64, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192114";
   }

   public static String logFailedRevertStep(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2192115", 64, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192115";
   }

   public static String logNoSessionHandlingApplied(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192116", 16, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192116";
   }

   public static String logApplicationRedeployFailure(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192117", 8, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192117";
   }

   public static String logSkippedStep(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192118", 64, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192118";
   }

   public static String logPathInputTranslated(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192119", 64, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192119";
   }

   public static String logPathVerificationError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192120", 8, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192120";
   }

   public static String logNMError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192121", 8, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192121";
   }

   public static String logNMWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192122", 16, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192122";
   }

   public static String logNMInfo(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192123", 64, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192123";
   }

   public static String logAgentInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192124", 64, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192124";
   }

   public static String logAgentWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192125", 16, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192125";
   }

   public static String logAgentError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192126", 8, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192126";
   }

   public static String logAgentErrorWithEx(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192127", 8, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192127";
   }

   public static String logApplicationRedeployToPartitionFailure(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2192128", 8, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192128";
   }

   public static String logCoherenceHaNodeSafeTwoMachineWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192129", 16, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192129";
   }

   public static String logElasticitySyncCounterLoadError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192130", 16, args, PatchingLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PatchingLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192130";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.patching.commands.PatchingLogLocalizer", PatchingLogger.class.getClassLoader());
      private MessageLogger messageLogger = PatchingLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = PatchingLogger.findMessageLogger();
      }
   }
}
