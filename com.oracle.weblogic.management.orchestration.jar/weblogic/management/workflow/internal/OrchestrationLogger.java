package weblogic.management.workflow.internal;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class OrchestrationLogger {
   private static final String LOCALIZER_CLASS = "weblogic.management.workflow.internal.OrchestrationLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(OrchestrationLogger.class.getName());
   }

   public static String logWorkunitFailNoException(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2192000", 8, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192000";
   }

   public static Loggable logWorkunitFailNoExceptionLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("2192000", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWorkunitFail(String arg0, String arg1, String arg2, String arg3, Throwable arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("2192001", 8, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192001";
   }

   public static Loggable logWorkunitFailLoggable(String arg0, String arg1, String arg2, String arg3, Throwable arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("2192001", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMissingWorkUnits(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192002", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCorruptedStorage(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192003", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getProgressRunning(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192004", 64, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getProgressFinished(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192005", 64, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getProgressReverting(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192006", 64, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getProgressFail(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192007", 64, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getProgressReverted(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192008", 64, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getProgressRevertFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192009", 64, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getProgressCanceled(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192010", 64, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCanNotCreateDirectoryForWF(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192011", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logFileDeleteFail(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192012", 16, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192012";
   }

   public static Loggable logFileDeleteFailLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192012", 16, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getWrongStorageStructure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192013", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCanNotMoveTmpToPermanentFile(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192014", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logRevertStarts(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192015", 64, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192015";
   }

   public static Loggable logRevertStartsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192015", 64, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getWfAlreadyCompleted(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192016", 16, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getWfAlreadyExecuted(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192017", 16, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCanNotDeleteFile(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192018", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCanNotCancel(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192019", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getHistoryMsgRecordOK(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("2192034", 64, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getHistoryMsgRecordError(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("2192035", 64, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getHistoryMsgRecordUnknownError(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("2192036", 64, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCommandFailedExceptionMessage(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192037", 16, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getWfWasNeverExecuted(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192038", 16, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getBlockedOrchestratioService() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2192039", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCannotDeleteRunningWf(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192040", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCannotExecute() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2192041", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCannotRevert() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2192042", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getUnresolvedContextInjection(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192043", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCommandFailedWithCauseExceptionMessage(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192044", 16, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logDebug(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192045", 16, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192045";
   }

   public static Loggable logDebugLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192045", 16, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWorkunitFailNoTrace(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("2192046", 8, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192046";
   }

   public static Loggable logWorkunitFailNoTraceLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("2192046", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWorkUnitStoreFail(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2192047", 8, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192047";
   }

   public static Loggable logWorkUnitStoreFailLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("2192047", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorLoadingProgressObject(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192048", 8, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192048";
   }

   public static Loggable logErrorLoadingProgressObjectLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192048", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInitializingProgressObject(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192049", 8, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192049";
   }

   public static Loggable logErrorInitializingProgressObjectLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192049", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInitializingWorkflowProgress(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192050", 8, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192050";
   }

   public static Loggable logErrorInitializingWorkflowProgressLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192050", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorStartingWorkflow(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192051", 8, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192051";
   }

   public static Loggable logErrorStartingWorkflowLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192051", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSetWorkflowTypeFail(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192052", 8, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192052";
   }

   public static Loggable logSetWorkflowTypeFailLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192052", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSetWorkflowTargetTypeFail(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192053", 8, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192053";
   }

   public static Loggable logSetWorkflowTargetTypeFailLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192053", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSetWorkflowTarget(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192054", 8, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192054";
   }

   public static Loggable logSetWorkflowTargetLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192054", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToDeleteWorkflowOnPartitionDelete(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192055", 8, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192055";
   }

   public static Loggable logFailedToDeleteWorkflowOnPartitionDeleteLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192055", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnabletoFindWorkflowProgress(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192056", 16, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192056";
   }

   public static Loggable logUnabletoFindWorkflowProgressLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192056", 16, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnabletoCleanupWorkflowFiles(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192057", 16, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192057";
   }

   public static Loggable logUnabletoCleanupWorkflowFilesLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192057", 16, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String foundNullStateForWorkflow(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192058", 16, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192058";
   }

   public static Loggable foundNullStateForWorkflowLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192058", 16, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCreatingWorkflowDirectory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192059", 8, args, OrchestrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192059";
   }

   public static Loggable logErrorCreatingWorkflowDirectoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192059", 8, args, "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, OrchestrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.workflow.internal.OrchestrationLogLocalizer", OrchestrationLogger.class.getClassLoader());
      private MessageLogger messageLogger = OrchestrationLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = OrchestrationLogger.findMessageLogger();
      }
   }
}
