package weblogic.diagnostics.watch.i18n;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class DiagnosticsWatchLogger {
   private static final String LOCALIZER_CLASS = "weblogic.diagnostics.watch.l10n.DiagnosticsWatchLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(DiagnosticsWatchLogger.class.getName());
   }

   public static String logInvalidActionTypeForScope(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320200", 8, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320200";
   }

   public static String logUnexpectedExceptionForAction(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320201", 8, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320201";
   }

   public static String logActionConfigMapPropertyNotValid(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320202", 8, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320202";
   }

   public static String logActionTimerExpired(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320203", 8, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320203";
   }

   public static String logActionExecutionStarted(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320204", 64, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320204";
   }

   public static String logDomainRuntimeSourceIOException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320205", 64, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320205";
   }

   public static String logScriptActionFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320206", 8, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320206";
   }

   public static String logScriptActionInterrupted(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320207", 8, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320207";
   }

   public static String logInvalidScriptActionConfigType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320208", 8, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320208";
   }

   public static String logScriptActionPathEmptyOrDoesNotExist(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320209", 8, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320209";
   }

   public static String logScriptActionExecutionComplete(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320210", 64, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320210";
   }

   public static String logScriptActionExecutionStart(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320211", 64, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320211";
   }

   public static String logScriptActionCancelled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320212", 16, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320212";
   }

   public static String logErrorSendingRestNotification(String arg0, String arg1, int arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("320213", 8, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320213";
   }

   public static String logScaleDownActionNoConfigPresent() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320214", 8, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320214";
   }

   public static String logScaleDownActionStarted(String arg0, String arg1, int arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("320215", 64, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320215";
   }

   public static String logScaleDownTaskComplete(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320216", 64, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320216";
   }

   public static String logScaleDownActionInvalidConfigBeanType(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320217", 8, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320217";
   }

   public static String logScaleUpActionNoConfigPresent() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320218", 8, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320218";
   }

   public static String logScaleUpActionInvalidConfigBeanType(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320219", 8, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320219";
   }

   public static String logScaleUpActionStarted(String arg0, String arg1, int arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("320220", 64, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320220";
   }

   public static String logScaleUpTaskComplete(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320221", 64, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320221";
   }

   public static String logNotificationsAlreadyInProgress(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320222", 32, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320222";
   }

   public static String logThreadDumpActionAlreadyInProgress(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320223", 32, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320223";
   }

   public static String logCreatedThreadDump(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("320224", 64, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320224";
   }

   public static String logFailedToWriteThreadDump(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320225", 8, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320225";
   }

   public static String logWaitForNextThreadDump(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320226", 64, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320226";
   }

   public static String logHeapDumpAlreadyInProgress() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320227", 16, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320227";
   }

   public static String logHeapDumpCaptureInitiated(String arg0, boolean arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320228", 64, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320228";
   }

   public static String logHeapDumpActionAvailabilityCheckException(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320229", 8, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320229";
   }

   public static String logHeapDumpCaptureComplete(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("320230", 64, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320230";
   }

   public static String logHeapDumpActionUnavailable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320231", 8, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320231";
   }

   public static String logHeapDumpActionPlatformMBeanServerUnavailable() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320232", 16, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320232";
   }

   public static String logHeapDumpMethodUnavailable() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320233", 16, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320233";
   }

   public static String logHotSpotDiagnosticMXBeanUnavailable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320234", 16, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320234";
   }

   public static String logUnexpectedErrorGeneratingHeapDump(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320235", 8, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320235";
   }

   public static String logThreadDumpActionCanceled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320236", 64, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320236";
   }

   public static String logDumpFileRemoved(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320237", 64, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320237";
   }

   public static String logScaleDownActionClusterAtMinimum(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320238", 16, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320238";
   }

   public static String logScaleUpActionClusterAtMaximum(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320239", 16, args, DiagnosticsWatchLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DiagnosticsWatchLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320239";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.diagnostics.watch.l10n.DiagnosticsWatchLogLocalizer", DiagnosticsWatchLogger.class.getClassLoader());
      private MessageLogger messageLogger = DiagnosticsWatchLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = DiagnosticsWatchLogger.findMessageLogger();
      }
   }
}
