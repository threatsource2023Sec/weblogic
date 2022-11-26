package weblogic.wtc;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class WTCLogger {
   private static final String LOCALIZER_CLASS = "weblogic.wtc.WTCLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(WTCLogger.class.getName());
   }

   public static String logInfoStartConfigParse() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180000", 64, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180000";
   }

   public static Loggable logInfoStartConfigParseLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180000", 64, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInfoDoneConfigParse() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180001", 64, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180001";
   }

   public static Loggable logInfoDoneConfigParseLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180001", 64, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIAEcreateSubCntxt(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180002", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180002";
   }

   public static Loggable logIAEcreateSubCntxtLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180002", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNEcreateSubCntxt(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180003", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180003";
   }

   public static Loggable logNEcreateSubCntxtLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180003", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNABEtuxConnFactory(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180004", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180004";
   }

   public static Loggable logNABEtuxConnFactoryLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180004", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIAEtuxConnFactory(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180005", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180005";
   }

   public static Loggable logIAEtuxConnFactoryLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180005", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNEtuxConnFactory(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180006", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180006";
   }

   public static Loggable logNEtuxConnFactoryLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180006", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSEgetTranMgr(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180007", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180007";
   }

   public static Loggable logSEgetTranMgrLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180007", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorTranId(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180008", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180008";
   }

   public static Loggable logErrorTranIdLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180008", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorExecMBeanDef(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180009", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180009";
   }

   public static Loggable logErrorExecMBeanDefLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180009", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUEconstructTDMLocalTD(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180010", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180010";
   }

   public static Loggable logUEconstructTDMLocalTDLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180010", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUEconstructTDMRemoteTD(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180011", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180011";
   }

   public static Loggable logUEconstructTDMRemoteTDLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180011", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorBadTDMRemoteLTD(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180012", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180012";
   }

   public static Loggable logErrorBadTDMRemoteLTDLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180012", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorBadTDMExportLTD(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180013", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180013";
   }

   public static Loggable logErrorBadTDMExportLTDLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180013", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUEconstructTDMExport(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180014", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180014";
   }

   public static Loggable logUEconstructTDMExportLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180014", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorBadTDMImportLTD(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180015", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180015";
   }

   public static Loggable logErrorBadTDMImportLTDLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180015", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorBadTDMImportRTD(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180016", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180016";
   }

   public static Loggable logErrorBadTDMImportRTDLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180016", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUEconstructTDMImport(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180017", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180017";
   }

   public static Loggable logUEconstructTDMImportLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180017", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorPasswordInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180018", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180018";
   }

   public static Loggable logErrorPasswordInfoLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180018", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorBadTDMPasswdLTD(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180019", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180019";
   }

   public static Loggable logErrorBadTDMPasswdLTDLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180019", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorBadTDMPasswdRTD(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180020", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180020";
   }

   public static Loggable logErrorBadTDMPasswdRTDLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180020", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUEconstructTDMPasswd(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180021", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180021";
   }

   public static Loggable logUEconstructTDMPasswdLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180021", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUEconstructTDMResourcesFT(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180022", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180022";
   }

   public static Loggable logUEconstructTDMResourcesFTLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180022", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logXAEcommitXid(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180023", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180023";
   }

   public static Loggable logXAEcommitXidLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180023", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTPEsendTran(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180024", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180024";
   }

   public static Loggable logTPEsendTranLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180024", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorBadFmlFldType(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180025", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180025";
   }

   public static Loggable logErrorBadFmlFldTypeLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180025", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFEbadFMLinData(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180026", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180026";
   }

   public static Loggable logFEbadFMLinDataLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180026", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUEbadFMLinData(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180027", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180027";
   }

   public static Loggable logUEbadFMLinDataLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180027", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorBadFml32FldType(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180028", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180028";
   }

   public static Loggable logErrorBadFml32FldTypeLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180028", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFEbadFML32inData(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180029", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180029";
   }

   public static Loggable logFEbadFML32inDataLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180029", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUEbadFML32inData(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180030", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180030";
   }

   public static Loggable logUEbadFML32inDataLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180030", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNullXmlArg() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180031", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180031";
   }

   public static Loggable logErrorNullXmlArgLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180031", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorBadFldTblsArg() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180032", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180032";
   }

   public static Loggable logErrorBadFldTblsArgLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180032", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNullFMLarg() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180033", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180033";
   }

   public static Loggable logErrorNullFMLargLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180033", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNoXmlDocRoot(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180034", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180034";
   }

   public static Loggable logErrorNoXmlDocRootLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180034", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNullFML32arg() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180035", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180035";
   }

   public static Loggable logErrorNullFML32argLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180035", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSEbadXml2Parser(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180036", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180036";
   }

   public static Loggable logSEbadXml2ParserLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180036", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIOEbadXml2Parser(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180037", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180037";
   }

   public static Loggable logIOEbadXml2ParserLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180037", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUEbadXml2Parser(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180038", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180038";
   }

   public static Loggable logUEbadXml2ParserLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180038", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNullDocFromParser(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180039", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180039";
   }

   public static Loggable logErrorNullDocFromParserLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180039", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNoTopElemFromStr(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180040", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180040";
   }

   public static Loggable logErrorNoTopElemFromStrLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180040", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNullTopElemName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180041", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180041";
   }

   public static Loggable logErrorNullTopElemNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180041", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNoFMLdata() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180042", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180042";
   }

   public static Loggable logErrorNoFMLdataLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180042", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNullXmlElemName() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180043", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180043";
   }

   public static Loggable logErrorNullXmlElemNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180043", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNullXmlElemValue(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180044", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180044";
   }

   public static Loggable logErrorNullXmlElemValueLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180044", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNoFML32data() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180045", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180045";
   }

   public static Loggable logErrorNoFML32dataLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180045", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDebugMsg(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180046", 128, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180046";
   }

   public static Loggable logDebugMsgLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180046", 128, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logtBNOredirects() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180047", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180047";
   }

   public static Loggable logtBNOredirectsLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180047", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logtBNOTuxedoConnectionFactory() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180048", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180048";
   }

   public static Loggable logtBNOTuxedoConnectionFactoryLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180048", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logtBNOWLXToptionAvailable() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180049", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180049";
   }

   public static Loggable logtBNOWLXToptionAvailableLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180049", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logtBInternalTranslationFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180050", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180050";
   }

   public static Loggable logtBInternalTranslationFailedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180050", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTTEstdSchedule(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180052", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180052";
   }

   public static Loggable logTTEstdScheduleLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180052", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDom80SendTokenCreation(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180053", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180053";
   }

   public static Loggable logErrorDom80SendTokenCreationLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180053", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDom80RecvTokenRead(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180054", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180054";
   }

   public static Loggable logErrorDom80RecvTokenReadLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180054", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorTokenError(int arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("180055", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180055";
   }

   public static Loggable logErrorTokenErrorLoggable(int arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("180055", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUEgssCryptoError1(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180056", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180056";
   }

   public static Loggable logUEgssCryptoError1Loggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180056", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorGssInvRetChallenge() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180057", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180057";
   }

   public static Loggable logErrorGssInvRetChallengeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180057", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIOEgssIOerror(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180058", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180058";
   }

   public static Loggable logIOEgssIOerrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180058", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUEgssCryptoError2(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180059", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180059";
   }

   public static Loggable logUEgssCryptoError2Loggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180059", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvHandlerAddrLength(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180063", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180063";
   }

   public static Loggable logInvHandlerAddrLengthLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180063", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorWSRPCRQdescrim(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180067", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180067";
   }

   public static Loggable logErrorWSRPCRQdescrimLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180067", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorWSRPCRQtype(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180068", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180068";
   }

   public static Loggable logErrorWSRPCRQtypeLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180068", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIOEbadTypedBuffer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180069", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180069";
   }

   public static Loggable logIOEbadTypedBufferLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180069", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarnFML32badType(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180070", 16, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180070";
   }

   public static Loggable logWarnFML32badTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180070", 16, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorFML32badField(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180071", 16, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180071";
   }

   public static Loggable logErrorFML32badFieldLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180071", 16, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDebugTDumpByteStart(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180072", 128, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180072";
   }

   public static Loggable logDebugTDumpByteStartLoggable(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180072", 128, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDebugTDumpByteEnd() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180073", 128, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180073";
   }

   public static Loggable logDebugTDumpByteEndLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180073", 128, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorTpinitBuffer() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180074", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180074";
   }

   public static Loggable logErrorTpinitBufferLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180074", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorRecvSize(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180075", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180075";
   }

   public static Loggable logErrorRecvSizeLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180075", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIOEbadUsrPasswd(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180076", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180076";
   }

   public static Loggable logIOEbadUsrPasswdLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180076", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIOEbadDomSocketClose(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180077", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180077";
   }

   public static Loggable logIOEbadDomSocketCloseLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180077", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarnOWSAREPLY() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180078", 16, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180078";
   }

   public static Loggable logWarnOWSAREPLYLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180078", 16, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorReadTfmh() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180079", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180079";
   }

   public static Loggable logErrorReadTfmhLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180079", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNullTmmsgWs() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180080", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180080";
   }

   public static Loggable logErrorNullTmmsgWsLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180080", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIOEbadRsessionClose(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180081", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180081";
   }

   public static Loggable logIOEbadRsessionCloseLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180081", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIOEbadWscSocketClose(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180082", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180082";
   }

   public static Loggable logIOEbadWscSocketCloseLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180082", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorUnknownTcmType(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180083", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180083";
   }

   public static Loggable logErrorUnknownTcmTypeLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180083", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIOEbadTCMwrite(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180084", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180084";
   }

   public static Loggable logIOEbadTCMwriteLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180084", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIOEbadUnsolAck(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180085", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180085";
   }

   public static Loggable logIOEbadUnsolAckLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180085", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInfoRemoteDomainConnected(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180086", 64, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180086";
   }

   public static Loggable logInfoRemoteDomainConnectedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180086", 64, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInfoConnectedToRemoteDomain(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180087", 64, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180087";
   }

   public static Loggable logInfoConnectedToRemoteDomainLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180087", 64, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDom65SendPreAcall(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180088", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180088";
   }

   public static Loggable logErrorDom65SendPreAcallLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180088", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDom65RecvPreAcall(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180089", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180089";
   }

   public static Loggable logErrorDom65RecvPreAcallLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180089", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDomainSecurityFailedRemote(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180090", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180090";
   }

   public static Loggable logErrorDomainSecurityFailedRemoteLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180090", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDomainSecurityFailedLocal(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180091", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180091";
   }

   public static Loggable logErrorDomainSecurityFailedLocalLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180091", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorChallengeFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180092", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180092";
   }

   public static Loggable logErrorChallengeFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180092", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNEConfigInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180093", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180093";
   }

   public static Loggable logNEConfigInfoLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180093", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logtBJMStargetNamefailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180094", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180094";
   }

   public static Loggable logtBJMStargetNamefailedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180094", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logtBJMSsourceNamefailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180095", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180095";
   }

   public static Loggable logtBJMSsourceNamefailedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180095", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logtBJMSerrorDestinationfailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180096", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180096";
   }

   public static Loggable logtBJMSerrorDestinationfailedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180096", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logtBsent2errorDestination() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180097", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180097";
   }

   public static Loggable logtBsent2errorDestinationLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180097", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logtBsent2errorDestinationfailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180098", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180098";
   }

   public static Loggable logtBsent2errorDestinationfailedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180098", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMinEncryptBitsGreaterThanMaxEncryptBits(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180099", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180099";
   }

   public static Loggable logMinEncryptBitsGreaterThanMaxEncryptBitsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180099", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTPEConfigError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180100", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180100";
   }

   public static Loggable logTPEConfigErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180100", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDuplicatedLocalDomain(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180102", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180102";
   }

   public static Loggable logErrorDuplicatedLocalDomainLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180102", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDuplicatedRemoteDomain(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180103", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180103";
   }

   public static Loggable logErrorDuplicatedRemoteDomainLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180103", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logtBunsupportedJMSmsgtype() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180104", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180104";
   }

   public static Loggable logtBunsupportedJMSmsgtypeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180104", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDroppedMessage() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180105", 16, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180105";
   }

   public static Loggable logDroppedMessageLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180105", 16, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNoTransactionManager() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180106", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180106";
   }

   public static Loggable logErrorNoTransactionManagerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180106", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningXaRecoverFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180107", 16, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180107";
   }

   public static Loggable logWarningXaRecoverFailedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180107", 16, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningRecoverRollbackFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180108", 16, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180108";
   }

   public static Loggable logWarningRecoverRollbackFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180108", 16, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logtBSlashQFML2XMLFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180109", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180109";
   }

   public static Loggable logtBSlashQFML2XMLFailedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180109", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logtBSlashQFML322XMLFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180110", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180110";
   }

   public static Loggable logtBSlashQFML322XMLFailedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180110", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logtBInternalFML2XMLFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180111", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180111";
   }

   public static Loggable logtBInternalFML2XMLFailedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180111", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logtBInternalFML322XMLFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180112", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180112";
   }

   public static Loggable logtBInternalFML322XMLFailedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180112", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDumpOneLine(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180113", 128, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180113";
   }

   public static Loggable logDumpOneLineLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180113", 128, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logtBparsefailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180115", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180115";
   }

   public static Loggable logtBparsefailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180115", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorBadGetFldTblsType(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180116", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180116";
   }

   public static Loggable logErrorBadGetFldTblsTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180116", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorUnsupportedEncoding(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180117", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180117";
   }

   public static Loggable logErrorUnsupportedEncodingLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180117", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorFileDoesNotExist(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180118", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180118";
   }

   public static Loggable logErrorFileDoesNotExistLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180118", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNotAFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180119", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180119";
   }

   public static Loggable logErrorNotAFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180119", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorFileNotReadable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180120", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180120";
   }

   public static Loggable logErrorFileNotReadableLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180120", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorFileNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180121", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180121";
   }

   public static Loggable logErrorFileNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180121", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorFileSecurity(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180122", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180122";
   }

   public static Loggable logErrorFileSecurityLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180122", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorFileIOError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180123", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180123";
   }

   public static Loggable logErrorFileIOErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180123", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorBadNumberFormat(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180124", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180124";
   }

   public static Loggable logErrorBadNumberFormatLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180124", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCustomAppKeyClassNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180132", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180132";
   }

   public static Loggable logErrorCustomAppKeyClassNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180132", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorAppKeyInitFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180133", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180133";
   }

   public static Loggable logErrorAppKeyInitFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180133", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCreateAppKeyClassInstanceFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180134", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180134";
   }

   public static Loggable logErrorCreateAppKeyClassInstanceFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180134", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUndefinedMBeanAttr(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180136", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180136";
   }

   public static Loggable logUndefinedMBeanAttrLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180136", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidMBeanAttr(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180137", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180137";
   }

   public static Loggable logInvalidMBeanAttrLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180137", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDupImpSvc(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180138", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180138";
   }

   public static Loggable logErrorDupImpSvcLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180138", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUndefinedMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180139", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180139";
   }

   public static Loggable logUndefinedMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180139", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDebugSecurity(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180140", 128, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180140";
   }

   public static Loggable logDebugSecurityLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180140", 128, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInvalidMagicNumber() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180141", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180141";
   }

   public static Loggable logErrorInvalidMagicNumberLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180141", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorLocalTDomInUse(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180142", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180142";
   }

   public static Loggable logErrorLocalTDomInUseLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180142", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNoSuchLocalDomain(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180143", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180143";
   }

   public static Loggable logErrorNoSuchLocalDomainLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180143", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDuplicateRemoteTDom(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180144", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180144";
   }

   public static Loggable logErrorDuplicateRemoteTDomLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180144", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorRemoteTDomInUse(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180145", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180145";
   }

   public static Loggable logErrorRemoteTDomInUseLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180145", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNoSuchRemoteDomain(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180146", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180146";
   }

   public static Loggable logErrorNoSuchRemoteDomainLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180146", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNoSuchImport(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180147", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180147";
   }

   public static Loggable logErrorNoSuchImportLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180147", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNoSuchExport(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180148", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180148";
   }

   public static Loggable logErrorNoSuchExportLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180148", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNoSuchPassword(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180149", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180149";
   }

   public static Loggable logErrorNoSuchPasswordLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180149", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorIncomingOnly(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180150", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180150";
   }

   public static Loggable logErrorIncomingOnlyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180150", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorResourceInUse(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180151", 1, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180151";
   }

   public static Loggable logErrorResourceInUseLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180151", 1, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNotificationRegistration() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180152", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180152";
   }

   public static Loggable logErrorNotificationRegistrationLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180152", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarnNoValidHostAddress(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180153", 16, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180153";
   }

   public static Loggable logWarnNoValidHostAddressLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180153", 16, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarnNoValidListeningAddress(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180154", 16, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180154";
   }

   public static Loggable logWarnNoValidListeningAddressLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180154", 16, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarnNoMoreValidRemoteAddress(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180155", 16, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180155";
   }

   public static Loggable logWarnNoMoreValidRemoteAddressLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180155", 16, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInfoTryNextAddress(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180156", 64, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180156";
   }

   public static Loggable logInfoTryNextAddressLoggable(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180156", 64, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarnNoMoreAddressToTry(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180157", 16, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180157";
   }

   public static Loggable logWarnNoMoreAddressToTryLoggable(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180157", 16, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarnNoMoreValidListeningAddress(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180158", 16, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180158";
   }

   public static Loggable logWarnNoMoreValidListeningAddressLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180158", 16, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInfoTryNextListeningAddress(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("180159", 64, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180159";
   }

   public static Loggable logInfoTryNextListeningAddressLoggable(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("180159", 64, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarnNoMoreListeningAddressToTry(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("180160", 16, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180160";
   }

   public static Loggable logWarnNoMoreListeningAddressToTryLoggable(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("180160", 16, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorFail2FindImportedQSpace(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180161", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180161";
   }

   public static Loggable logErrorFail2FindImportedQSpaceLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180161", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorTbNoSuchImport(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180162", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180162";
   }

   public static Loggable logErrorTbNoSuchImportLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180162", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorTbUnsupportedBufferType(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180163", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180163";
   }

   public static Loggable logErrorTbUnsupportedBufferTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180163", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorTbJmsSendFailure() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180164", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180164";
   }

   public static Loggable logErrorTbJmsSendFailureLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180164", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInfoDisconnectNoKeepAliveAck(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180165", 64, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180165";
   }

   public static Loggable logInfoDisconnectNoKeepAliveAckLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180165", 64, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarnDisableKeepAlive(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180166", 16, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180166";
   }

   public static Loggable logWarnDisableKeepAliveLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180166", 16, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorUndefinedTDomainSession(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("180167", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180167";
   }

   public static Loggable logErrorUndefinedTDomainSessionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("180167", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorTDomainPassword(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180168", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180168";
   }

   public static Loggable logErrorTDomainPasswordLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180168", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorXAEnd(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180169", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180169";
   }

   public static Loggable logErrorXAEndLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180169", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logViewToXMLException(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180170", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180170";
   }

   public static Loggable logViewToXMLExceptionLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180170", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarnNoNullCiphersAllowed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180171", 16, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180171";
   }

   public static Loggable logWarnNoNullCiphersAllowedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180171", 16, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorNoNullCiphersAllowed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180172", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180172";
   }

   public static Loggable logErrorNoNullCiphersAllowedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180172", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInvalidPrivateKeyInfo(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("180173", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180173";
   }

   public static Loggable logErrorInvalidPrivateKeyInfoLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("180173", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInvalidPrivateKeyStoreInfo(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180174", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180174";
   }

   public static Loggable logErrorInvalidPrivateKeyStoreInfoLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180174", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInvalidTrustCertStoreInfo(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180175", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180175";
   }

   public static Loggable logErrorInvalidTrustCertStoreInfoLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180175", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInvalidTrustCertificate(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("180176", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180176";
   }

   public static Loggable logErrorInvalidTrustCertificateLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("180176", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInvalidServerTrustCertificate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180177", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180177";
   }

   public static Loggable logErrorInvalidServerTrustCertificateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180177", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInfoLLEEncryptBitsDowngrade(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180178", 64, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180178";
   }

   public static Loggable logInfoLLEEncryptBitsDowngradeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180178", 64, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarnIgnoreSSLwithSDP(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("180179", 16, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180179";
   }

   public static Loggable logWarnIgnoreSSLwithSDPLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("180179", 16, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorSdpClassNotFound() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("180180", 8, args, WTCLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "180180";
   }

   public static Loggable logErrorSdpClassNotFoundLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("180180", 8, args, "weblogic.wtc.WTCLogLocalizer", WTCLogger.MessageLoggerInitializer.INSTANCE.messageLogger, WTCLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.wtc.WTCLogLocalizer", WTCLogger.class.getClassLoader());
      private MessageLogger messageLogger = WTCLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = WTCLogger.findMessageLogger();
      }
   }
}
