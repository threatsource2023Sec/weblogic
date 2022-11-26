package weblogic.t3.srvr;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class PartitionLifecycleLogger {
   private static final String LOCALIZER_CLASS = "weblogic.t3.srvr.PartitionLifecycleLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(PartitionLifecycleLogger.class.getName());
   }

   public static String logPartitionOperationException(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192300", 8, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192300";
   }

   public static String logResourceGroupOperationException(String arg0, String arg1, String arg2, Exception arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2192301", 8, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192301";
   }

   public static String logTargetNotReachableException(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192302", 8, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192302";
   }

   public static String logPartitionOperationInitiated(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192303", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192303";
   }

   public static Loggable logPartitionOperationInitiatedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192303", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionOperationComplete(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192304", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192304";
   }

   public static Loggable logPartitionOperationCompleteLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192304", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionOperationNoTarget(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192305", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192305";
   }

   public static Loggable logPartitionOperationNoTargetLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192305", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logResourceGroupOperationInitiated(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192306", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192306";
   }

   public static Loggable logResourceGroupOperationInitiatedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192306", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logResourceGroupOperationComplete(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192307", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192307";
   }

   public static Loggable logResourceGroupOperationCompleteLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192307", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logResourceGroupOperationNoTarget(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192308", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192308";
   }

   public static Loggable logResourceGroupOperationNoTargetLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192308", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionAlreadyInState(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192309", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192309";
   }

   public static Loggable logPartitionAlreadyInStateLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192309", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logResourceGroupAlreadyInState(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192310", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192310";
   }

   public static Loggable logResourceGroupAlreadyInStateLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192310", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionOpInProgress(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192311", 16, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192311";
   }

   public static Loggable logPartitionOpInProgressLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192311", 16, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logResourceGroupOpInProgress(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192312", 16, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192312";
   }

   public static Loggable logResourceGroupOpInProgressLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192312", 16, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionOpIncompatible(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192313", 16, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192313";
   }

   public static Loggable logPartitionOpIncompatibleLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192313", 16, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logResourceGroupOpIncompatible(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2192314", 16, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192314";
   }

   public static Loggable logResourceGroupOpIncompatibleLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("2192314", 16, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionInterceptorException(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192315", 8, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192315";
   }

   public static Loggable logPartitionInterceptorExceptionLoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192315", 8, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionOperationAutoHalt(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192316", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192316";
   }

   public static Loggable logPartitionOperationAutoHaltLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192316", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionOperationSkipBoot(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192317", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192317";
   }

   public static Loggable logPartitionOperationSkipBootLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192317", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGracefulPartitionOperationTimedOut(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192318", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192318";
   }

   public static Loggable logGracefulPartitionOperationTimedOutLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192318", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String initiateOperationOnServers(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192319", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192319";
   }

   public static Loggable initiateOperationOnServersLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192319", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String successfulSubTask(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2192320", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192320";
   }

   public static Loggable successfulSubTaskLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("2192320", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String failedSubTask(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2192321", 8, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192321";
   }

   public static Loggable failedSubTaskLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("2192321", 8, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String successfulTask(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2192322", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192322";
   }

   public static Loggable successfulTaskLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("2192322", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String failedTask(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192323", 8, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192323";
   }

   public static Loggable failedTaskLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192323", 8, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionAlreadyExpectedState(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192324", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192324";
   }

   public static Loggable logPartitionAlreadyExpectedStateLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192324", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRGAlreadyExpectedState(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192325", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192325";
   }

   public static Loggable logRGAlreadyExpectedStateLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192325", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String initiateRGOperationOnServers(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192326", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192326";
   }

   public static Loggable initiateRGOperationOnServersLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192326", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String successfulRGSubTask(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2192327", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192327";
   }

   public static Loggable successfulRGSubTaskLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("2192327", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String failedRGSubTask(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2192328", 8, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192328";
   }

   public static Loggable failedRGSubTaskLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("2192328", 8, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String successfulRGTask(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2192329", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192329";
   }

   public static Loggable successfulRGTaskLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("2192329", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String failedRGTask(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192330", 8, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192330";
   }

   public static Loggable failedRGTaskLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192330", 8, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String forceRestartHaltedPartitionButNonNullRuntimeMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192331", 16, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192331";
   }

   public static Loggable forceRestartHaltedPartitionButNonNullRuntimeMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192331", 16, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String partitionStateNotAllowed(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2192332", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192332";
   }

   public static Loggable partitionStateNotAllowedLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("2192332", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionOperationShutdownToHaltedFlagIsTrue(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192333", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192333";
   }

   public static Loggable logPartitionOperationShutdownToHaltedFlagIsTrueLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192333", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String fixingInconsistentStateSubState(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192334", 16, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192334";
   }

   public static String logStartPartitionConvertedToResume(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192335", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192335";
   }

   public static Loggable logStartPartitionConvertedToResumeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192335", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStartPartitionResourceGroupConvertedToResume(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192336", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192336";
   }

   public static Loggable logStartPartitionResourceGroupConvertedToResumeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192336", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStartDomainResourceGroupConvertedToResume(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192337", 32, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192337";
   }

   public static Loggable logStartDomainResourceGroupConvertedToResumeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192337", 32, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionStartFailedDuringServerStartup(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192338", 8, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192338";
   }

   public static Loggable logPartitionStartFailedDuringServerStartupLoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192338", 8, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionShutdownFailedDuringServerShutdown(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2192339", 8, args, PartitionLifecycleLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192339";
   }

   public static Loggable logPartitionShutdownFailedDuringServerShutdownLoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2192339", 8, args, "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PartitionLifecycleLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.t3.srvr.PartitionLifecycleLogLocalizer", PartitionLifecycleLogger.class.getClassLoader());
      private MessageLogger messageLogger = PartitionLifecycleLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = PartitionLifecycleLogger.findMessageLogger();
      }
   }
}
