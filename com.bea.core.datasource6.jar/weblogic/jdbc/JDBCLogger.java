package weblogic.jdbc;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class JDBCLogger {
   private static final String LOCALIZER_CLASS = "weblogic.jdbc.JDBCLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(JDBCLogger.class.getName());
   }

   public static String logErrorMessage(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001035", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001035";
   }

   public static String logDebugMessage(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001036", 128, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001036";
   }

   public static String logGetJDNDIContextError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001058", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001058";
   }

   public static String logIgnoringTwoPhaseCommitPropWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001064", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001064";
   }

   public static String logDelayingBeforeConnection(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001066", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001066";
   }

   public static String logConnRefreshedInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001067", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001067";
   }

   public static String logConnCreatedInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001068", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001068";
   }

   public static String logXAConnCreatedInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001072", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001072";
   }

   public static String logXAConnRefreshedInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001073", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001073";
   }

   public static String logConnectionLeakWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001074", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001074";
   }

   public static String logReleaseOrphanedConnection(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001076", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001076";
   }

   public static String logMultyPoolCreatedInfo(String arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001083", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001083";
   }

   public static String logShutdownMultiPool(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001084", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001084";
   }

   public static String logShutdownDataSource(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001086", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001086";
   }

   public static String logStart(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001089", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001089";
   }

   public static String logReset(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001099", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001099";
   }

   public static String logShutdown(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001100", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001100";
   }

   public static String logDisable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001101", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001101";
   }

   public static String logEnable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001102", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001102";
   }

   public static String logExistingGlobalPool(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001104", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001104";
   }

   public static String logLocalDupPool(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001105", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001105";
   }

   public static String logGlobalDupJNDI(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001107", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001107";
   }

   public static String logBindLocalPool(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001108", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001108";
   }

   public static String logBindGlobalPoolRef(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001109", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001109";
   }

   public static String logWarnNoTestTable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001110", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001110";
   }

   public static String logTestVerifFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001111", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001111";
   }

   public static String logTestFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001112", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001112";
   }

   public static String logShrink(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001113", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001113";
   }

   public static String logShutdownSoft(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001114", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001114";
   }

   public static String logShutdownHard(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001115", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001115";
   }

   public static String logDisableFreezing(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001116", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001116";
   }

   public static String logDisableDropping(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001117", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001117";
   }

   public static String logMBeanRegFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001118", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001118";
   }

   public static String logMBeanDelFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001119", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001119";
   }

   public static String logCreatedDS(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001120", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001120";
   }

   public static String logCreatedTxDS(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001121", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001121";
   }

   public static String logDestroyedTxDS(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001122", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001122";
   }

   public static String logDestroyedDS(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001123", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001123";
   }

   public static String logCreatedCP(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001124", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001124";
   }

   public static String logCreatedMP(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001125", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001125";
   }

   public static String logDestroyedCP(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001126", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001126";
   }

   public static String logDestroyedMP(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001127", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001127";
   }

   public static String logConnClosedInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001128", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001128";
   }

   public static String logError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001129", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001129";
   }

   public static String logDisablingStmtCacheOCIXA(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001130", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001130";
   }

   public static String logStmtCloseFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001131", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001131";
   }

   public static String logStmtCacheEnabled(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001132", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001132";
   }

   public static String logStmtCacheDisabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001133", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001133";
   }

   public static String logClearStmtCache(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001134", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001134";
   }

   public static String logInit() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001135", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001135";
   }

   public static String logInitFailed2() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001136", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001136";
   }

   public static String logInitDone() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001137", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001137";
   }

   public static String logResume() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001138", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001138";
   }

   public static String logResumeFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001139", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001139";
   }

   public static String logResumeDone() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001140", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001140";
   }

   public static String logSuspend() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001141", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001141";
   }

   public static String logSuspendFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001142", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001142";
   }

   public static String logSuspendDone() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001143", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001143";
   }

   public static String logFSuspend() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001144", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001144";
   }

   public static String logFSuspendFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001145", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001145";
   }

   public static String logFSuspendDone() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001146", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001146";
   }

   public static String logShutdown2() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001147", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001147";
   }

   public static String logShutdownFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001148", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001148";
   }

   public static String logShutdownDone() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001149", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001149";
   }

   public static String logPoolStartupError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001150", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001150";
   }

   public static String logDSStartupError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001151", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001151";
   }

   public static String logCannotEnableStmtCacheOCIXA(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001152", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001152";
   }

   public static String logDisablingStmtCache(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001154", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001154";
   }

   public static String logStackTrace(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001155", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001155";
   }

   public static String logStackTraceId(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001156", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001156";
   }

   public static String logSuspending(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001157", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001157";
   }

   public static String logForceSuspending(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001158", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001158";
   }

   public static String logResuming(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001159", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001159";
   }

   public static String logForceDestroying(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001160", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001160";
   }

   public static String logDestroying(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001161", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001161";
   }

   public static String logForceShutting(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001162", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001162";
   }

   public static String logShutting(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001163", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001163";
   }

   public static String logConnInitFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001164", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001164";
   }

   public static String logInvalidCacheSize(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001165", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001165";
   }

   public static String logPoolActivateFailed(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("001166", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001166";
   }

   public static String logPoolDeactivateFailed(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("001167", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001167";
   }

   public static String logResumeOpInvalid() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001168", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001168";
   }

   public static String logPoolShutdownError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001169", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001169";
   }

   public static String logErrorLogInit(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001170", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001170";
   }

   public static String logCloseFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001171", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001171";
   }

   public static String logDSShutdownError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001172", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001172";
   }

   public static String logTestNameChange(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001173", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001173";
   }

   public static String logCreatingDS(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001174", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001174";
   }

   public static String logCreatingTxDS(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001175", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001175";
   }

   public static String logCreatingMP(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001176", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001176";
   }

   public static String logCreatingCP(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001177", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001177";
   }

   public static String logSetQueryTOFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001178", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001178";
   }

   public static String logFailoverCBLoadError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001250", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001250";
   }

   public static String logFailoverCBTypeError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001251", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001251";
   }

   public static String logMPMBeanRegFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001252", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001252";
   }

   public static String logDisableFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001254", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001254";
   }

   public static String logDisableFailed2(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001255", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001255";
   }

   public static String logMPFailoverFlagChg(String arg0, boolean arg1, boolean arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001256", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001256";
   }

   public static String logMPHealthChkFreqChg(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001257", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001257";
   }

   public static String logMPTimerSetupError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001258", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001258";
   }

   public static String logPoolReenableDisallowed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001259", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001259";
   }

   public static String logEnableFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001260", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001260";
   }

   public static String logEnableFailed2(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001261", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001261";
   }

   public static String logRegisteredCB(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001262", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001262";
   }

   public static String logCreatingASMP(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("001500", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001500";
   }

   public static String logCreatingMPAlg(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001501", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001501";
   }

   public static String logCreatingASCP(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("001503", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001503";
   }

   public static String logDestroyingASMP(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001504", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001504";
   }

   public static String logDestroyingMP(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001505", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001505";
   }

   public static String logDestroyingASCP(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001507", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001507";
   }

   public static String logDestroyingCP(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001508", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001508";
   }

   public static String logCreatingASDS(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("001510", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001510";
   }

   public static String logCreatedDS2(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001512", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001512";
   }

   public static String logDestroyingASDS(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001513", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001513";
   }

   public static String logDestroyingDS(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001514", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001514";
   }

   public static String logDestroyedDS2(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001515", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001515";
   }

   public static String logDatabaseInfo(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001516", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001516";
   }

   public static String logDriverInfo(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001517", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001517";
   }

   public static String logPoolUsageData(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001518", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001518";
   }

   public static String logProfileRecordId(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001519", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001519";
   }

   public static String logProfileRecordTimestamp(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001520", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001520";
   }

   public static String logProfileRecordUser(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001521", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001521";
   }

   public static String logPoolWaitData(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001522", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001522";
   }

   public static String logPoolLeakData(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001523", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001523";
   }

   public static String logPoolResvFailData(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001524", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001524";
   }

   public static String logStmtCacheEntryData(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001525", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001525";
   }

   public static String logInterceptorClassLoadFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001526", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001526";
   }

   public static String logInterceptorClassBadType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001527", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001527";
   }

   public static String logInterceptorClassLoaded(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001528", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001528";
   }

   public static String logCreatingLLRTable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001529", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001529";
   }

   public static String logLoadedLLRTable(String arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001530", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001530";
   }

   public static String logUnexpectedUpdateBeanType(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001531", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001531";
   }

   public static String logStmtUsageData(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001532", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001532";
   }

   public static String logProfileRecordPoolName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001533", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001533";
   }

   public static String logConnLastUsageData(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001534", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001534";
   }

   public static String logConnMTUsageData(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001535", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001535";
   }

   public static String logCreatingMDS(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001536", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001536";
   }

   public static String logCreatingASMDS(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("001537", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001537";
   }

   public static String logCreatedMDS(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001538", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001538";
   }

   public static String logDestroyingMDS(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001539", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001539";
   }

   public static String logDestroyingASMDS(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001540", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001540";
   }

   public static String logDestroyedMDS(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001541", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001541";
   }

   public static String logSetLoginTimeout(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001542", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001542";
   }

   public static String logUnexpectedBeanChangeType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001543", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001543";
   }

   public static String logUnexpectedBeanAddType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001544", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001544";
   }

   public static String logUnexpectedUpdateType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001545", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001545";
   }

   public static String logStarting(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001546", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001546";
   }

   public static String logInvalidTableReference(String arg0, String arg1, String arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("001547", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001547";
   }

   public static Loggable logInvalidTableReferenceLoggable(String arg0, String arg1, String arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("001547", 8, args, "weblogic.jdbc.JDBCLogLocalizer", JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JDBCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTableReference2(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001548", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001548";
   }

   public static Loggable logInvalidTableReference2Loggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("001548", 8, args, "weblogic.jdbc.JDBCLogLocalizer", JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JDBCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConvertWLDriverURL(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001549", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001549";
   }

   public static String logWLOracleDriverWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001550", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001550";
   }

   public static String logStandaloneMultiDataSourceMemberNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001551", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001551";
   }

   public static String logLLRWarningRemoteJDBCDisabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001552", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001552";
   }

   public static String logNoPasswordIndirectionCredentials(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001553", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001553";
   }

   public static String logConnUnwrapUsageData(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001554", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001554";
   }

   public static String logInvalidGridLinkMultiPoolMember(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001555", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001555";
   }

   public static String logRegisteringForFANEvents(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001556", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001556";
   }

   public static String logUnregisteringForFANEvents(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001557", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001557";
   }

   public static String logServiceDownEvent(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001558", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001558";
   }

   public static String logNodeDownEvent(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001559", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001559";
   }

   public static String logServiceInstanceUpEvent(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001560", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001560";
   }

   public static String logNodeUpEvent(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001561", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001561";
   }

   public static String logNotRegisteringForFANEvents(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001562", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001562";
   }

   public static String logNoExistsForADMDDLInfo(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001563", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001563";
   }

   public static String logLeavingExistsForADMDDLInfo(boolean arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001564", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001564";
   }

   public static String logLLRTablePropertyInfo(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001565", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001565";
   }

   public static String logCreateSQLADMDDLInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001566", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001566";
   }

   public static String logRTEADMDDLError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001567", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001567";
   }

   public static String logServiceUpEvent(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001568", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001568";
   }

   public static String logRACInstanceRuntimeCreationFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001569", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001569";
   }

   public static String logFANEnabledNotAllowed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001570", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001570";
   }

   public static String logConnectionLabelingCallbackClassLoadFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001571", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001571";
   }

   public static String logConnectionLabelingCallbackClassBadType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001572", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001572";
   }

   public static String logConnectionLabelingCallbackClassLoaded(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001573", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001573";
   }

   public static String logConnectionInitializationCallbackClassLoadFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001574", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001574";
   }

   public static String logConnectionInitializationCallbackClassBadType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001575", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001575";
   }

   public static String logConnectionInitializationCallbackClassLoaded(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001576", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001576";
   }

   public static String logExceptionFromConnectionHarvestingCallback(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001580", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001580";
   }

   public static String logInvalidApplicationScope(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001581", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001581";
   }

   public static String logDrcpError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001582", 8, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001582";
   }

   public static String logNoCache() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("001583", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001583";
   }

   public static String logDisablingPool(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001584", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001584";
   }

   public static String logReenablingPool(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001585", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001585";
   }

   public static String logEndRequestFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001586", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001586";
   }

   public static String logWarningTestOnReserveDisabledForMDSLLRRAC(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001587", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001587";
   }

   public static String logShrinkOnMemoryPressure(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001588", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001588";
   }

   public static String logSettingStatementCacheSizes(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001589", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001589";
   }

   public static String logSetStatementCacheSize(String arg0, int arg1, int arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("001590", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001590";
   }

   public static String logConfiguringPartitionDataSource(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("001591", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001591";
   }

   public static String logForcedCloseConnInactive(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001592", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001592";
   }

   public static String logForcedCloseConnHarvested(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001593", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001593";
   }

   public static String logForcedCloseConnClosed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001594", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001594";
   }

   public static String logForcedCloseConnInitfailure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001595", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001595";
   }

   public static String logConnLocalTxLeakData(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001596", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001596";
   }

   public static String logClosedObjectUsageData(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001597", 64, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001597";
   }

   public static String logONSFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001598", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001598";
   }

   public static String logAglUrl(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001599", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001599";
   }

   public static String logConnectionRepurposeError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("001600", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001600";
   }

   public static String logReplayRequiresBeginEndRequest(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("001601", 16, args, JDBCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JDBCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "001601";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.jdbc.JDBCLogLocalizer", JDBCLogger.class.getClassLoader());
      private MessageLogger messageLogger = JDBCLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = JDBCLogger.findMessageLogger();
      }
   }
}
