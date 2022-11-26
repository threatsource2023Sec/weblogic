package weblogic.work;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class WorkManagerLogger {
   private static final String LOCALIZER_CLASS = "weblogic.work.WorkManagerLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(WorkManagerLogger.class.getName());
   }

   public static String logInitializingSelfTuning() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002900", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002900";
   }

   public static String logCreatingWorkManagerService(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("002901", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002901";
   }

   public static String logCreatingExecuteQueueFromMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002902", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002902";
   }

   public static String logCreatingServiceFromMBean(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002903", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002903";
   }

   public static String logRuntimeMBeanCreationError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002908", 4, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002908";
   }

   public static String logDebug(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002909", 128, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002909";
   }

   public static String logScheduleFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002911", 4, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002911";
   }

   public static String logOverloadAction(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("002912", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002912";
   }

   public static Loggable logOverloadActionLoggable(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("002912", 64, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLowMemory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002913", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002913";
   }

   public static Loggable logLowMemoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("002913", 64, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logShutdownCallbackFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002914", 8, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002914";
   }

   public static String logCancelBeforeEnqueue(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002916", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002916";
   }

   public static Loggable logCancelBeforeEnqueueLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("002916", 64, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCancelAfterEnqueue(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002917", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002917";
   }

   public static Loggable logCancelAfterEnqueueLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("002917", 64, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoSelfTuningForExecuteQueues() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002918", 16, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002918";
   }

   public static String logWorkManagerNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002919", 16, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002919";
   }

   public static String getWorkManagerDescription() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002920", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002920";
   }

   public static Loggable getWorkManagerDescriptionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("002920", 64, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getWorkManagerNameAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002921", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002921";
   }

   public static Loggable getWorkManagerNameAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("002921", 64, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getPendingRequestsAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002922", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002922";
   }

   public static Loggable getPendingRequestsAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("002922", 64, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCompletedRequestsAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002923", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002923";
   }

   public static String getMinThreadsConstraintDescription() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002924", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002924";
   }

   public static Loggable getMinThreadsConstraintDescriptionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("002924", 64, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMinThreadsConstraintCompletedRequestsAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002925", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002925";
   }

   public static String getMinThreadsConstraintPendingRequestsAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002926", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002926";
   }

   public static Loggable getMinThreadsConstraintPendingRequestsAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("002926", 64, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMinThreadsConstraintExecutingRequestsAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002927", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002927";
   }

   public static Loggable getMinThreadsConstraintExecutingRequestsAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("002927", 64, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMinThreadsConstraintOutOfOrderExecutionCountAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002928", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002928";
   }

   public static Loggable getMinThreadsConstraintOutOfOrderExecutionCountAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("002928", 64, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMinThreadsConstraintMustRunCountAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002929", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002929";
   }

   public static String getMinThreadsConstraintMaxWaitTimeAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002930", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002930";
   }

   public static Loggable getMinThreadsConstraintMaxWaitTimeAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("002930", 64, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMinThreadsConstraintCurrentWaitTimeAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002931", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002931";
   }

   public static Loggable getMinThreadsConstraintCurrentWaitTimeAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("002931", 64, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMaxThreadsConstraintDescription() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002932", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002932";
   }

   public static Loggable getMaxThreadsConstraintDescriptionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("002932", 64, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMaxThreadsConstraintExecutingRequestsAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002933", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002933";
   }

   public static Loggable getMaxThreadsConstraintExecutingRequestsAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("002933", 64, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMaxThreadsConstraintDeferredRequestsAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002934", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002934";
   }

   public static Loggable getMaxThreadsConstraintDeferredRequestsAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("002934", 64, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMissingContextCaseRequestClass(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002935", 16, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002935";
   }

   public static String logMaxThreadsConstraintReached(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002936", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002936";
   }

   public static String logMaxThreadsConstraintNoLongerReached(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002937", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002937";
   }

   public static String logGlobalInternalWorkManagerOverriden(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002938", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002938";
   }

   public static String logMaxThreadsConstraintReachedGathered(String arg0, int arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("002939", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002939";
   }

   public static String logWorkManagerShutdownActionTriggered(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002940", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002940";
   }

   public static String logRemovedStandbyThreads(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002941", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002941";
   }

   public static String logCMMLevelChanged(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002942", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002942";
   }

   public static String logMaxThreadsConstraintQueueFull(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("002943", 16, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002943";
   }

   public static Loggable logMaxThreadsConstraintQueueFullLoggable(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("002943", 16, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMaxThreadsConstraintQueueFullGathered(String arg0, String arg1, int arg2, int arg3, long arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("002944", 16, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002944";
   }

   public static String logThreadLocalCleanupDisabled(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002945", 16, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002945";
   }

   public static String logPartitionConfigNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002946", 16, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002946";
   }

   public static String logPartitionConfigAvailable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002947", 16, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002947";
   }

   public static String logLoadComponentsFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002948", 8, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002948";
   }

   public static String logPartitionNotDeployedToThisServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002949", 16, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002949";
   }

   public static String logPartitionMinThreadsConstraintCapInfo(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002950", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002950";
   }

   public static String logCreateThreadUnderGlobalContextFailed(Throwable arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002951", 16, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002951";
   }

   public static String logPartitionMaxThreadsConstraintReached(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002952", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002952";
   }

   public static String logPartitionMaxThreadsConstraintReachedGathered(String arg0, int arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("002953", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002953";
   }

   public static String logPartitionMaxThreadsConstraintQueueFull(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("002954", 16, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002954";
   }

   public static Loggable logPartitionMaxThreadsConstraintQueueFullLoggable(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("002954", 16, args, "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WorkManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionMaxThreadsConstraintQueueFullGathered(String arg0, String arg1, int arg2, int arg3, long arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("002955", 16, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002955";
   }

   public static String logSelfTuningStopped(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002956", 8, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002956";
   }

   public static String logFairShareValueTooHigh(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002957", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002957";
   }

   public static String logWorkManagerDumperStarted() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002958", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002958";
   }

   public static String logSelfTuningThreadCounts(int arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("002959", 64, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002959";
   }

   public static String logMarkCriticalStuckThresholdCount(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002960", 32, args, WorkManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WorkManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002960";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.work.WorkManagerLogLocalizer", WorkManagerLogger.class.getClassLoader());
      private MessageLogger messageLogger = WorkManagerLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = WorkManagerLogger.findMessageLogger();
      }
   }
}
