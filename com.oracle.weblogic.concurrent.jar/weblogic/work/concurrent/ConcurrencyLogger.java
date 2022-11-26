package weblogic.work.concurrent;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class ConcurrencyLogger {
   private static final String LOCALIZER_CLASS = "weblogic.work.concurrent.ConcurrencyLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(ConcurrencyLogger.class.getName());
   }

   public static String logCreatingMTF(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2162600", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162600";
   }

   public static Loggable logCreatingMTFLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("2162600", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCreatingContextService(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2162601", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162601";
   }

   public static Loggable logCreatingContextServiceLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("2162601", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTimerNameConflict(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162602", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162602";
   }

   public static Loggable logTimerNameConflictLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2162602", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWrongParaemter(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162603", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162603";
   }

   public static Loggable logWrongParaemterLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2162603", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRuntimeMBeanCreationError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162604", 4, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162604";
   }

   public static Loggable logRuntimeMBeanCreationErrorLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2162604", 4, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNewThreadRejected(String arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162605", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162605";
   }

   public static Loggable logNewThreadRejectedLoggable(String arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2162605", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLongRunningThreadRejected(String arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162606", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162606";
   }

   public static Loggable logLongRunningThreadRejectedLoggable(String arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2162606", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logOutofDateThreadLeft(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162607", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162607";
   }

   public static Loggable logOutofDateThreadLeftLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2162607", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTaskListenerFail(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162608", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162608";
   }

   public static Loggable logTaskListenerFailLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2162608", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTransactionFail(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162609", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162609";
   }

   public static Loggable logTransactionFailLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162609", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCreatingMES(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("2162610", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162610";
   }

   public static Loggable logCreatingMESLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("2162610", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCreatingMSES(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("2162611", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162611";
   }

   public static Loggable logCreatingMSESLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("2162611", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNewThreadStateError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162612", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162612";
   }

   public static Loggable logNewThreadStateErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162612", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logProxyStateError(String arg0, IllegalStateException arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162613", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162613";
   }

   public static Loggable logProxyStateErrorLoggable(String arg0, IllegalStateException arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2162613", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWorkManagerNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162614", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162614";
   }

   public static Loggable logWorkManagerNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2162614", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConcurrentObjectNotFound(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162615", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162615";
   }

   public static Loggable logConcurrentObjectNotFoundLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2162615", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDifferentCS() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2162616", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162616";
   }

   public static Loggable logDifferentCSLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2162616", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidProxy() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2162617", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162617";
   }

   public static Loggable logInvalidProxyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2162617", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullInstance() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2162618", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162618";
   }

   public static Loggable logNullInstanceLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2162618", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoInterface() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2162619", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162619";
   }

   public static Loggable logNoInterfaceLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2162619", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInterfacesNotImplemented() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2162620", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162620";
   }

   public static Loggable logInterfacesNotImplementedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2162620", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLifecycleNotsupported() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2162621", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162621";
   }

   public static Loggable logLifecycleNotsupportedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2162621", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEmptyTaskList() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2162622", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162622";
   }

   public static Loggable logEmptyTaskListLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2162622", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRejectForStop(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162623", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162623";
   }

   public static Loggable logRejectForStopLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162623", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCancelForStop(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162624", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162624";
   }

   public static Loggable logCancelForStopLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162624", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNewLongRunningThreadStateError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162625", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162625";
   }

   public static Loggable logNewLongRunningThreadStateErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162625", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUserCancelTask(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162626", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162626";
   }

   public static Loggable logUserCancelTaskLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162626", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeserializeErrorObject(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162627", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162627";
   }

   public static Loggable logDeserializeErrorObjectLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2162627", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSaveContextExp(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162628", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162628";
   }

   public static Loggable logSaveContextExpLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162628", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSetupContextExp(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162629", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162629";
   }

   public static Loggable logSetupContextExpLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162629", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logResetContextExp(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162630", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162630";
   }

   public static Loggable logResetContextExpLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162630", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConcurrentNotInitialized(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162631", 16, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162631";
   }

   public static Loggable logConcurrentNotInitializedLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162631", 16, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMTFRejectNewThread(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162632", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162632";
   }

   public static Loggable logMTFRejectNewThreadLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2162632", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeserializeErrorCache(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162633", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162633";
   }

   public static Loggable logDeserializeErrorCacheLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2162633", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDefaultConcurrentObjectOverrideNotAllowed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162634", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162634";
   }

   public static Loggable logDefaultConcurrentObjectOverrideNotAllowedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162634", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWrongConcurrentObjectJNDI(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162635", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162635";
   }

   public static Loggable logWrongConcurrentObjectJNDILoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2162635", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSkipClassloaderCheck(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162636", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162636";
   }

   public static Loggable logSkipClassloaderCheckLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2162636", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCMOsRuntimeMBeanCreationError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162637", 16, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162637";
   }

   public static Loggable logCMOsRuntimeMBeanCreationErrorLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2162637", 16, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCMOsRuntimeMBeanDestructionError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162638", 16, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162638";
   }

   public static Loggable logCMOsRuntimeMBeanDestructionErrorLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2162638", 16, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStateCheckerFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162639", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162639";
   }

   public static Loggable logStateCheckerFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162639", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionCMOCreationError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2162640", 16, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162640";
   }

   public static Loggable logPartitionCMOCreationErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2162640", 16, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionCMOFactoryUnbindError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162641", 16, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162641";
   }

   public static Loggable logPartitionCMOFactoryUnbindErrorLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2162641", 16, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRollbackTransaction(Object arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162642", 16, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162642";
   }

   public static Loggable logRollbackTransactionLoggable(Object arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2162642", 16, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTriggerFail(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162643", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162643";
   }

   public static Loggable logTriggerFailLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2162643", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMESReleaseTask(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162644", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162644";
   }

   public static Loggable logMESReleaseTaskLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2162644", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWMReleaseNonAdminTask(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2162645", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162645";
   }

   public static Loggable logWMReleaseNonAdminTaskLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("2162645", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logOutOfPartition(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162646", 16, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162646";
   }

   public static Loggable logOutOfPartitionLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2162646", 16, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logOutOfApplication(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162647", 16, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162647";
   }

   public static Loggable logOutOfApplicationLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2162647", 16, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSubmittingComponentNotStart(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162648", 16, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162648";
   }

   public static Loggable logSubmittingComponentNotStartLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2162648", 16, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCSNotStart(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162649", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162649";
   }

   public static Loggable logCSNotStartLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162649", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSubmittingCompStateCheckerFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162650", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162650";
   }

   public static Loggable logSubmittingCompStateCheckerFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162650", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSubmittingCompReleaseTask(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2162651", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162651";
   }

   public static Loggable logSubmittingCompReleaseTaskLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("2162651", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCancelForSubmittingCompStop(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162652", 64, args, ConcurrencyLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162652";
   }

   public static Loggable logCancelForSubmittingCompStopLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2162652", 64, args, "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConcurrencyLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.work.concurrent.ConcurrencyLogLocalizer", ConcurrencyLogger.class.getClassLoader());
      private MessageLogger messageLogger = ConcurrencyLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = ConcurrencyLogger.findMessageLogger();
      }
   }
}
