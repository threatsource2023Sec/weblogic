package weblogic.deploy.api.internal;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class SPIDeployerLogger {
   private static final String LOCALIZER_CLASS = "weblogic.deploy.api.internal.SPIDeployerLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(SPIDeployerLogger.class.getName());
   }

   public static String getDisplayName() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("260000", 64, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getInvalidURI(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260001", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getInvalidServerAuth(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260002", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String notConnected() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("260003", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String unsupportedLocale(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260004", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String unsupportedVersion(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260008", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String noClass(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260009", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String failedMBeanConnection(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("260010", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String noSuchTarget(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260012", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String nullTargetArray() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("260013", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String unsupported(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260015", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String noFile(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260016", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String noSuchApp(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260017", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String successfulTransition(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("260020", 64, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String failedTransition(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("260021", 64, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String appNotification(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("260022", 64, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String lostTask() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("260023", 16, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String reportErrorEvent(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("260024", 64, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String unknownError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260026", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String notRootTMID(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260027", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String nullTMID(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260028", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String diffTMID(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("260029", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String noAppForTMID(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260030", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String noop(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260031", 64, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String unsupportedModuleType(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260036", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String unexpectedDD(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260037", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getNoDelta(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260040", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String uploadFailure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260041", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String invalidClass(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260050", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String nullDCB() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("260052", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String badPlan(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260055", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String invalidMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260056", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String badDDBean(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260061", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logRestoreDCB(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("260067", 64, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260067";
   }

   public static String logRemoveDCB(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("260068", 64, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260068";
   }

   public static String logSaveDCB(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("260070", 64, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260070";
   }

   public static String logRestore(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("260071", 64, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260071";
   }

   public static String logSave(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("260072", 64, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260072";
   }

   public static String logBeanError(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("260073", 8, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260073";
   }

   public static String logNoDCB(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("260078", 16, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260078";
   }

   public static String invalidInstallDir(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260080", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logDDCreateError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("260081", 8, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260081";
   }

   public static String logNoCMPDD(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("260082", 16, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260082";
   }

   public static String logNoPlan(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("260083", 16, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260083";
   }

   public static String logNoSave(String arg0, boolean arg1, boolean arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("260085", 16, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260085";
   }

   public static String logSaveDD(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("260086", 64, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260086";
   }

   public static String getMissingExt(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("260087", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logMissingDD(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("260088", 8, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260088";
   }

   public static Loggable logMissingDDLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260088", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppReadError(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("260091", 8, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260091";
   }

   public static String logAddDS(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("260094", 64, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260094";
   }

   public static String getRenameError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260095", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getUnknownType(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260096", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String badRootBean(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260097", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String noTagRegistered(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260098", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String noTagFound(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("260099", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String invalidSecurityModel(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260100", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logNullParam(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("260101", 8, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260101";
   }

   public static Loggable logNullParamLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260101", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String invalidExport(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260102", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String noSuchBean(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260103", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String notChangable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260104", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String parseError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("260106", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String marshalError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260107", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String restoreError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260108", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String marshalPlanError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260109", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String createError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260110", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String connectionError() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("260111", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String nullTmids() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("260112", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String planIsDir(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260113", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String rootIsFile(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260114", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String noAppProvided() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("260115", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String mustInit() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("260116", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String invalidFactory(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260117", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String nullTarget() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("260118", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String notDir(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260119", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logInitOperation(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("260121", 64, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260121";
   }

   public static String logInitStreamOperation(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("260122", 64, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260122";
   }

   public static String noTargetInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260123", 64, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getConfiguredTargets() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("260124", 64, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getConfiguredTargetsLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("260124", 64, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConnectionError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("260125", 8, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260125";
   }

   public static String versionMismatchPlan(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260126", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logDTDDDUpdate(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("260128", 16, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260128";
   }

   public static Loggable logDTDDDUpdateLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("260128", 16, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDTDDDExport(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("260129", 16, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260129";
   }

   public static String logPollerError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("260130", 16, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260130";
   }

   public static String noURI() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("260131", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String notAChild(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260132", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getDDBeanCreateError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260133", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String nullAppName(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260134", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String restorePlanFailure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260137", 16, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String backupPlanError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260138", 16, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logRestorePlan(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("260139", 64, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260139";
   }

   public static String logUnableToRemoveDescriptorBean(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("260140", 64, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260140";
   }

   public static Loggable logUnableToRemoveDescriptorBeanLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260140", 64, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getReinitializeError() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("260141", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String unknownDD(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260142", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logParamCombination(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260143", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable logParamCombinationLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260143", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonExistPartition(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("260144", 16, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260144";
   }

   public static Loggable logNonExistPartitionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260144", 16, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonExistPartitionResourceGroup(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("260145", 16, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260145";
   }

   public static Loggable logNonExistPartitionResourceGroupLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260145", 16, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonExistResourceGroupTemplate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("260146", 16, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260146";
   }

   public static Loggable logNonExistResourceGroupTemplateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260146", 16, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotSpecifyPartitionWithoutResourceGroup(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("260147", 16, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260147";
   }

   public static Loggable logCannotSpecifyPartitionWithoutResourceGroupLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260147", 16, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotSpecifyTemplateWithGroup() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("260148", 16, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260148";
   }

   public static Loggable logCannotSpecifyTemplateWithGroupLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("260148", 16, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonExistDomainResourceGroup(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("260149", 16, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260149";
   }

   public static Loggable logNonExistDomainResourceGroupLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260149", 16, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotSpecifyTemplateWithPartitionAdmin() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("260150", 8, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260150";
   }

   public static Loggable logCannotSpecifyTemplateWithPartitionAdminLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("260150", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMismatchPartitionOptionWithPartitionAdmin(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("260151", 8, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260151";
   }

   public static Loggable logMismatchPartitionOptionWithPartitionAdminLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("260151", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConfigRootNotDirectory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("260152", 8, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260152";
   }

   public static Loggable logConfigRootNotDirectoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("260152", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConfigRootEmpty() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("260153", 8, args, SPIDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "260153";
   }

   public static Loggable logConfigRootEmptyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("260153", 8, args, "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SPIDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.deploy.api.internal.SPIDeployerLogLocalizer", SPIDeployerLogger.class.getClassLoader());
      private MessageLogger messageLogger = SPIDeployerLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = SPIDeployerLogger.findMessageLogger();
      }
   }
}
