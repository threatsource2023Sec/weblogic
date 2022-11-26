package weblogic.servlet;

import java.util.Locale;
import javax.management.ObjectName;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class HTTPLogger {
   private static final String LOCALIZER_CLASS = "weblogic.servlet.HTTPLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(HTTPLogger.class.getName());
   }

   public static String logUnableToDeserializeAttribute(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101002", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101002";
   }

   public static Loggable logUnableToDeserializeAttributeLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101002", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullDocRoot(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101003", 2, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101003";
   }

   public static Loggable logNullDocRootLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101003", 2, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsafePath(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101005", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101005";
   }

   public static Loggable logUnsafePathLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101005", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToGetStream(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101008", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101008";
   }

   public static Loggable logUnableToGetStreamLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101008", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRootCause(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101017", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101017";
   }

   public static Loggable logRootCauseLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101017", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIOException(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101019", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101019";
   }

   public static Loggable logIOExceptionLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101019", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logException(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101020", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101020";
   }

   public static Loggable logExceptionLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101020", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedErrorCode(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101024", 4, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101024";
   }

   public static Loggable logUnsupportedErrorCodeLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101024", 4, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullDocumentRoot(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101025", 2, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101025";
   }

   public static Loggable logNullDocumentRootLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101025", 2, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoDocRoot(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101027", 2, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101027";
   }

   public static Loggable logNoDocRootLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101027", 2, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToMakeDirectory(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101029", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101029";
   }

   public static Loggable logUnableToMakeDirectoryLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101029", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReloadCheckSecondsError(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101040", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101040";
   }

   public static Loggable logReloadCheckSecondsErrorLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101040", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCompareVersion(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101041", 128, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101041";
   }

   public static Loggable logCompareVersionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101041", 128, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101045", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101045";
   }

   public static Loggable logErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101045", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorWithThrowable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101046", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101046";
   }

   public static Loggable logErrorWithThrowableLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101046", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInfo(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101047", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101047";
   }

   public static Loggable logInfoLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101047", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStarted(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101051", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101051";
   }

   public static Loggable logStartedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101051", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInit(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101052", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101052";
   }

   public static Loggable logInitLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101052", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLoadingWebApp(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101053", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101053";
   }

   public static Loggable logLoadingWebAppLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101053", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSetContext(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101054", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101054";
   }

   public static Loggable logSetContextLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101054", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLoadingFromWAR(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101059", 128, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101059";
   }

   public static Loggable logLoadingFromWARLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101059", 128, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLoadingFromDir(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101060", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101060";
   }

   public static Loggable logLoadingFromDirLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101060", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToFindWebApp(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101061", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101061";
   }

   public static Loggable logUnableToFindWebAppLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101061", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorReadingWebApp(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101062", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101062";
   }

   public static Loggable logErrorReadingWebAppLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101062", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLoadError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101064", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101064";
   }

   public static Loggable logLoadErrorLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101064", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoJNDIContext(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101066", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101066";
   }

   public static Loggable logNoJNDIContextLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101066", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullURL(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101067", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101067";
   }

   public static Loggable logNullURLLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101067", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logURLParseError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101068", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101068";
   }

   public static Loggable logURLParseErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101068", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToBindURL(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101069", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101069";
   }

   public static Loggable logUnableToBindURLLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101069", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBoundURL(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101070", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101070";
   }

   public static Loggable logBoundURLLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101070", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logShutdown(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101075", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101075";
   }

   public static Loggable logShutdownLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101075", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConnectionFailure(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101083", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101083";
   }

   public static Loggable logConnectionFailureLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101083", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoHostInHeader() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101086", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101086";
   }

   public static Loggable logNoHostInHeaderLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101086", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHostNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101087", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101087";
   }

   public static Loggable logHostNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101087", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDispatchRequest(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101088", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101088";
   }

   public static Loggable logDispatchRequestLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101088", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDispatchError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101093", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101093";
   }

   public static Loggable logDispatchErrorLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101093", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPOSTTimeExceeded(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101095", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101095";
   }

   public static Loggable logPOSTTimeExceededLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101095", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPOSTSizeExceeded(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101096", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101096";
   }

   public static Loggable logPOSTSizeExceededLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101096", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBadCookieHeader(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101100", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101100";
   }

   public static Loggable logBadCookieHeaderLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101100", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionCreateError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101101", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101101";
   }

   public static Loggable logSessionCreateErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101101", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServletFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101104", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101104";
   }

   public static Loggable logServletFailedLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101104", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoLocation(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101105", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101105";
   }

   public static Loggable logNoLocationLoggable(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101105", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToServeErrorPage(String arg0, String arg1, int arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101106", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101106";
   }

   public static Loggable logUnableToServeErrorPageLoggable(String arg0, String arg1, int arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101106", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSendError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101107", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101107";
   }

   public static Loggable logSendErrorLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101107", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedEncoding(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101108", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101108";
   }

   public static Loggable logUnsupportedEncodingLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101108", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPermUnavailable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101122", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101122";
   }

   public static Loggable logPermUnavailableLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101122", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTimeUnavailable(String arg0, String arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101123", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101123";
   }

   public static Loggable logTimeUnavailableLoggable(String arg0, String arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101123", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInstantiateError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101125", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101125";
   }

   public static Loggable logInstantiateErrorLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101125", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCastingError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101126", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101126";
   }

   public static Loggable logCastingErrorLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101126", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHTTPInit() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101128", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101128";
   }

   public static Loggable logHTTPInitLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101128", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWebInit() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101129", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101129";
   }

   public static Loggable logWebInitLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101129", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInitWeb(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101133", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101133";
   }

   public static Loggable logInitWebLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101133", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDefaultName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101135", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101135";
   }

   public static Loggable logDefaultNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101135", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRegisterVirtualHost(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101136", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101136";
   }

   public static Loggable logRegisterVirtualHostLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101136", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidGetParameterInvocation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101138", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101138";
   }

   public static Loggable logInvalidGetParameterInvocationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101138", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCreatingServletStub(String arg0, String arg1, String arg2, Object arg3, Throwable arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("101140", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101140";
   }

   public static Loggable logErrorCreatingServletStubLoggable(String arg0, String arg1, String arg2, Object arg3, Throwable arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("101140", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorUnregisteringServletRuntime(ObjectName arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101142", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101142";
   }

   public static Loggable logErrorUnregisteringServletRuntimeLoggable(ObjectName arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101142", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorUnregisteringWebAppRuntime(ObjectName arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101143", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101143";
   }

   public static Loggable logErrorUnregisteringWebAppRuntimeLoggable(ObjectName arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101143", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoContext(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101147", 128, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101147";
   }

   public static Loggable logNoContextLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101147", 128, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalAccessOnInstantiate(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101159", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101159";
   }

   public static Loggable logIllegalAccessOnInstantiateLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101159", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMalformedWebDescriptor(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101160", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101160";
   }

   public static Loggable logMalformedWebDescriptorLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101160", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logListenerFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101162", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101162";
   }

   public static Loggable logListenerFailedLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101162", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotLoadListener(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101163", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101163";
   }

   public static Loggable logCouldNotLoadListenerLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101163", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotAListener(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101164", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101164";
   }

   public static Loggable logNotAListenerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101164", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotLoadFilter(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101165", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101165";
   }

   public static Loggable logCouldNotLoadFilterLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101165", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBadSecurityRoleInSRA(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101168", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101168";
   }

   public static Loggable logBadSecurityRoleInSRALoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101168", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServletNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101169", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101169";
   }

   public static Loggable logServletNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101169", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServletNotFoundForPattern(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101170", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101170";
   }

   public static Loggable logServletNotFoundForPatternLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101170", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoResourceRefs() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101171", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101171";
   }

   public static Loggable logNoResourceRefsLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101171", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoEjbRefs() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101172", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101172";
   }

   public static Loggable logNoEjbRefsLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101172", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logResourceRefNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101173", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101173";
   }

   public static Loggable logResourceRefNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101173", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEjbRefNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101174", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101174";
   }

   public static Loggable logEjbRefNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101174", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logZipCloseError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101175", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101175";
   }

   public static Loggable logZipCloseErrorLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101175", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logContextNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101176", 128, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101176";
   }

   public static Loggable logContextNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101176", 128, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBadSecurityRoleInAC(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101180", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101180";
   }

   public static Loggable logBadSecurityRoleInACLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101180", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidExceptionType(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101188", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101188";
   }

   public static Loggable logInvalidExceptionTypeLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101188", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotLoadUrlMatchMapClass(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101189", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101189";
   }

   public static Loggable logCouldNotLoadUrlMatchMapClassLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101189", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPreprocessorNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101194", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101194";
   }

   public static Loggable logPreprocessorNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101194", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logListenerParseException(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101196", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101196";
   }

   public static Loggable logListenerParseExceptionLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101196", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotDeployRole(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101198", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101198";
   }

   public static Loggable logCouldNotDeployRoleLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101198", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotDeployPolicy(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101199", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101199";
   }

   public static Loggable logCouldNotDeployPolicyLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101199", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotCachingTheResponse(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101200", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101200";
   }

   public static Loggable logNotCachingTheResponseLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101200", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLoadingDescriptors(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101201", 128, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101201";
   }

   public static Loggable logLoadingDescriptorsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101201", 128, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPreparing(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101202", 128, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101202";
   }

   public static Loggable logPreparingLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101202", 128, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRollingBack(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101205", 128, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101205";
   }

   public static Loggable logRollingBackLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101205", 128, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logActivating(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101206", 128, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101206";
   }

   public static Loggable logActivatingLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101206", 128, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeactivating(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101207", 128, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101207";
   }

   public static Loggable logDeactivatingLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101207", 128, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStarting(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101208", 128, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101208";
   }

   public static Loggable logStartingLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101208", 128, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReady(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101209", 128, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101209";
   }

   public static Loggable logReadyLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101209", 128, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPrecompilingJSPs(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101211", 128, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101211";
   }

   public static Loggable logPrecompilingJSPsLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101211", 128, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailureCompilingJSPs(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101212", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101212";
   }

   public static Loggable logFailureCompilingJSPsLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101212", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBindingResourceReference(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101213", 128, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101213";
   }

   public static Loggable logBindingResourceReferenceLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101213", 128, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIncludedFileNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101214", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101214";
   }

   public static Loggable logIncludedFileNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101214", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMalformedRequest(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101215", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101215";
   }

   public static Loggable logMalformedRequestLoggable(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101215", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServletFailedToPreloadOnStartup(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101216", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101216";
   }

   public static Loggable logServletFailedToPreloadOnStartupLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101216", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logContextAlreadyRegistered(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("101217", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101217";
   }

   public static Loggable logContextAlreadyRegisteredLoggable(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("101217", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNeedServletClassOrJspFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101218", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101218";
   }

   public static Loggable logNeedServletClassOrJspFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101218", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorSettingDocumentRoot(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101220", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101220";
   }

   public static Loggable logErrorSettingDocumentRootLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101220", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServletNameIsNull(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101221", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101221";
   }

   public static Loggable logServletNameIsNullLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101221", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServerSuspended(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101223", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101223";
   }

   public static Loggable logServerSuspendedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101223", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAuthFilterInvocationFailed(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101226", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101226";
   }

   public static Loggable logAuthFilterInvocationFailedLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101226", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToUndeploySecurityPolicy(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101228", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101228";
   }

   public static Loggable logFailedToUndeploySecurityPolicyLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101228", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToUndeploySecurityRole(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101229", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101229";
   }

   public static Loggable logFailedToUndeploySecurityRoleLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101229", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logELFLogNotFormattedProperly() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101231", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101231";
   }

   public static Loggable logELFLogNotFormattedProperlyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101231", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logELFReadHeadersException(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101232", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101232";
   }

   public static Loggable logELFReadHeadersExceptionLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101232", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logELFApplicationFieldFailure(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101234", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101234";
   }

   public static Loggable logELFApplicationFieldFailureLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101234", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logELFApplicationFieldFailureCCE(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101235", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101235";
   }

   public static Loggable logELFApplicationFieldFailureCCELoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101235", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logELFApplicationFieldFormatError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101236", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101236";
   }

   public static Loggable logELFApplicationFieldFormatErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101236", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHttpLoggingDisabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101237", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101237";
   }

   public static Loggable logHttpLoggingDisabledLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101237", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToRollLogFile(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101242", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101242";
   }

   public static Loggable logFailedToRollLogFileLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101242", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logOldPublicIDWarningWithContext(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101247", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101247";
   }

   public static Loggable logOldPublicIDWarningWithContextLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101247", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMalformedDescriptorCtx(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("101248", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101248";
   }

   public static Loggable logMalformedDescriptorCtxLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("101248", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServletClassNotFound(String arg0, String arg1, String arg2, String arg3, Throwable arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("101249", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101249";
   }

   public static Loggable logServletClassNotFoundLoggable(String arg0, String arg1, String arg2, String arg3, Throwable arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("101249", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServletClassDefNotFound(String arg0, String arg1, String arg2, String arg3, Throwable arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("101250", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101250";
   }

   public static Loggable logServletClassDefNotFoundLoggable(String arg0, String arg1, String arg2, String arg3, Throwable arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("101250", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServletUnsatisfiedLink(String arg0, String arg1, String arg2, String arg3, Throwable arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("101251", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101251";
   }

   public static Loggable logServletUnsatisfiedLinkLoggable(String arg0, String arg1, String arg2, String arg3, Throwable arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("101251", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServletVerifyError(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101252", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101252";
   }

   public static Loggable logServletVerifyErrorLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101252", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServletClassFormatError(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101253", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101253";
   }

   public static Loggable logServletClassFormatErrorLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101253", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServletLinkageError(String arg0, String arg1, String arg2, String arg3, Throwable arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("101254", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101254";
   }

   public static Loggable logServletLinkageErrorLoggable(String arg0, String arg1, String arg2, String arg3, Throwable arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("101254", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRunAsUserCouldNotBeResolved(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101256", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101256";
   }

   public static Loggable logRunAsUserCouldNotBeResolvedLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101256", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIgnoringClientCert(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101257", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101257";
   }

   public static Loggable logIgnoringClientCertLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101257", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidJspParamName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101258", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101258";
   }

   public static Loggable logInvalidJspParamNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101258", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEmptyJspParamName() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101260", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101260";
   }

   public static Loggable logEmptyJspParamNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101260", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidSessionParamName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101261", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101261";
   }

   public static Loggable logInvalidSessionParamNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101261", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeprecatedSessionParamName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101262", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101262";
   }

   public static Loggable logDeprecatedSessionParamNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101262", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEmptySessionParamName() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101263", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101263";
   }

   public static Loggable logEmptySessionParamNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101263", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertAuthenticationError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101264", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101264";
   }

   public static Loggable logCertAuthenticationErrorLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101264", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedWhileDestroyingFilter(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101267", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101267";
   }

   public static Loggable logFailedWhileDestroyingFilterLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101267", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServletFailedOnDestroy(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101268", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101268";
   }

   public static Loggable logServletFailedOnDestroyLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101268", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUrlPatternMissingFromWebResource(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101271", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101271";
   }

   public static Loggable logUrlPatternMissingFromWebResourceLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101271", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInitialSessionsDuringSuspend(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101275", 32, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101275";
   }

   public static Loggable logInitialSessionsDuringSuspendLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101275", 32, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionsDuringSuspend(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101276", 32, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101276";
   }

   public static Loggable logSessionsDuringSuspendLoggable(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101276", 32, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionListDuringSuspend(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101277", 32, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101277";
   }

   public static Loggable logSessionListDuringSuspendLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101277", 32, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPrepareToSuspendComplete() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101278", 32, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101278";
   }

   public static Loggable logPrepareToSuspendCompleteLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101278", 32, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRemoving(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101279", 128, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101279";
   }

   public static Loggable logRemovingLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101279", 128, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToCreateWebServerRuntimeMBean(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101280", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101280";
   }

   public static Loggable logFailedToCreateWebServerRuntimeMBeanLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101280", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidJspServlet(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101282", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101282";
   }

   public static Loggable logInvalidJspServletLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101282", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToLoadJspServletClass(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101283", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101283";
   }

   public static Loggable logUnableToLoadJspServletClassLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101283", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNPEDuringServletDestroy(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101287", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101287";
   }

   public static Loggable logNPEDuringServletDestroyLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101287", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToSetContextPath(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101288", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101288";
   }

   public static Loggable logFailedToSetContextPathLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101288", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToBounceClassLoader(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101291", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101291";
   }

   public static Loggable logFailedToBounceClassLoaderLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101291", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDescriptorValidationFailure(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101292", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101292";
   }

   public static Loggable logDescriptorValidationFailureLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101292", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServerVersionMismatchForJSPisStale(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101295", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101295";
   }

   public static Loggable logServerVersionMismatchForJSPisStaleLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101295", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToLoadDefaultCompilerClass(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101296", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101296";
   }

   public static Loggable logUnableToLoadDefaultCompilerClassLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101296", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidIndexDirectorySortBy(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101297", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101297";
   }

   public static Loggable logInvalidIndexDirectorySortByLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101297", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFoundStarJspUrlPattern(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101299", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101299";
   }

   public static Loggable logFoundStarJspUrlPatternLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101299", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logImplicitMappingForRunAsRole(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("101302", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101302";
   }

   public static Loggable logImplicitMappingForRunAsRoleLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("101302", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logImplicitMappingForRunAsRoleToSelf(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101303", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101303";
   }

   public static Loggable logImplicitMappingForRunAsRoleToSelfLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101303", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCreatingImplicitMapForRoles(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101304", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101304";
   }

   public static Loggable logCreatingImplicitMapForRolesLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101304", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBadErrorPage(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101305", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101305";
   }

   public static Loggable logBadErrorPageLoggable(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101305", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotResolveServletEntity(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101306", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101306";
   }

   public static Loggable logCouldNotResolveServletEntityLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101306", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorResolvingServletEntity(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101307", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101307";
   }

   public static Loggable logErrorResolvingServletEntityLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101307", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDebug(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101308", 128, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101308";
   }

   public static Loggable logDebugLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101308", 128, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToPerformBatchedLATUpdate(String arg0, String arg1, String arg2, int arg3, int arg4, Throwable arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("101310", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101310";
   }

   public static Loggable logFailedToPerformBatchedLATUpdateLoggable(String arg0, String arg1, String arg2, int arg3, int arg4, Throwable arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("101310", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDigestAuthNotSupported(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101317", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101317";
   }

   public static Loggable logDigestAuthNotSupportedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101317", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidAuthMethod(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101318", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101318";
   }

   public static Loggable logInvalidAuthMethodLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101318", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLoginOrErrorPageMissing(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101319", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101319";
   }

   public static Loggable logLoginOrErrorPageMissingLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101319", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInternalAppWrongPort(String arg0, int arg1, String arg2, String arg3, int arg4, String arg5, String arg6) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
      CatalogMessage catalogMessage = new CatalogMessage("101320", 128, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101320";
   }

   public static Loggable logInternalAppWrongPortLoggable(String arg0, int arg1, String arg2, String arg3, int arg4, String arg5, String arg6) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
      Loggable l = new Loggable("101320", 128, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMuxableSocketResetException(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101323", 128, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101323";
   }

   public static Loggable logMuxableSocketResetExceptionLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101323", 128, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidVirtualDirectoryPath(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101325", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101325";
   }

   public static Loggable logInvalidVirtualDirectoryPathLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101325", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUndefinedSecurityRole(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101326", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101326";
   }

   public static Loggable logUndefinedSecurityRoleLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101326", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMultipleOccurrencesNotAllowed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101327", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101327";
   }

   public static Loggable logMultipleOccurrencesNotAllowedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101327", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidFilterDispatcher(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101328", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101328";
   }

   public static Loggable logInvalidFilterDispatcherLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101328", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonVersionedContextAlreadyRegistered(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("101331", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101331";
   }

   public static Loggable logNonVersionedContextAlreadyRegisteredLoggable(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("101331", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToRegisterPolicyContextHandlers(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101332", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101332";
   }

   public static Loggable logFailedToRegisterPolicyContextHandlersLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101332", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSecurityException(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101337", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101337";
   }

   public static Loggable logSecurityExceptionLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101337", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeprecatedContextParamDefaultServlet() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101338", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101338";
   }

   public static Loggable logDeprecatedContextParamDefaultServletLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101338", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeprecatedContextParam(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101339", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101339";
   }

   public static Loggable logDeprecatedContextParamLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101339", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeprecatedContextParamClasspath() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101340", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101340";
   }

   public static Loggable logDeprecatedContextParamClasspathLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101340", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJSPClassUptodate(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101341", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101341";
   }

   public static Loggable logJSPClassUptodateLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101341", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJSPPrecompileErrors(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101342", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101342";
   }

   public static Loggable logJSPPrecompileErrorsLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101342", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPrecompilingStaleJsp(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101343", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101343";
   }

   public static Loggable logPrecompilingStaleJspLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101343", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPrecompilingJspNoClass(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101344", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101344";
   }

   public static Loggable logPrecompilingJspNoClassLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101344", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logELFFieldsChanged(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101345", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101345";
   }

   public static Loggable logELFFieldsChangedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101345", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logVirtualHostNameAlreadyUsed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101346", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101346";
   }

   public static Loggable logVirtualHostNameAlreadyUsedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101346", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logVirtualHostServerChannelNameAlreadyUsed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101347", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101347";
   }

   public static Loggable logVirtualHostServerChannelNameAlreadyUsedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101347", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logVirtualHostServerChannelUndefined(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101348", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101348";
   }

   public static Loggable logVirtualHostServerChannelUndefinedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101348", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToSaveWorkContexts(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101349", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101349";
   }

   public static Loggable logFailedToSaveWorkContextsLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101349", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionListDuringGracefulProductionToAdmin(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101350", 32, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101350";
   }

   public static Loggable logSessionListDuringGracefulProductionToAdminLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101350", 32, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInitialSessionsDuringGracefulProductionToAdmin(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101351", 32, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101351";
   }

   public static Loggable logInitialSessionsDuringGracefulProductionToAdminLoggable(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101351", 32, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGracefulProductionToAdminComplete(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101352", 32, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101352";
   }

   public static Loggable logGracefulProductionToAdminCompleteLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101352", 32, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionsDuringGracefulProductionToAdmin(String arg0, String arg1, int arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101354", 32, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101354";
   }

   public static Loggable logSessionsDuringGracefulProductionToAdminLoggable(String arg0, String arg1, int arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101354", 32, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWebServicesVersioningNotSupported(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101355", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101355";
   }

   public static Loggable logWebServicesVersioningNotSupportedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101355", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToLoadNativeIOLibrary(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101356", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101356";
   }

   public static Loggable logFailedToLoadNativeIOLibraryLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101356", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGracefulProductionToAdminInterrupted(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101357", 32, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101357";
   }

   public static Loggable logGracefulProductionToAdminInterruptedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101357", 32, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAsyncInitFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101359", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101359";
   }

   public static Loggable logAsyncInitFailedLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101359", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJSPisStale(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101360", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101360";
   }

   public static Loggable logJSPisStaleLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101360", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalWebLibSpecVersionRef(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101361", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101361";
   }

   public static Loggable logIllegalWebLibSpecVersionRefLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101361", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToDeserializeAttribute(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101362", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101362";
   }

   public static Loggable logFailedToDeserializeAttributeLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101362", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIgnoringWeblogicXMLContextRoot(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101363", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101363";
   }

   public static Loggable logIgnoringWeblogicXMLContextRootLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101363", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailureInCompilingJSP(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("101364", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101364";
   }

   public static Loggable logFailureInCompilingJSPLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("101364", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logClosingTimeoutSocket() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101366", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101366";
   }

   public static Loggable logClosingTimeoutSocketLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101366", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExtractionPathTooLong(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101367", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101367";
   }

   public static Loggable logExtractionPathTooLongLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101367", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeprecatedEncodingPropertyUsed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101369", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101369";
   }

   public static Loggable logDeprecatedEncodingPropertyUsedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101369", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLibraryDescriptorMergeFailed(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101370", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101370";
   }

   public static Loggable logLibraryDescriptorMergeFailedLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101370", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAnnotationProcessingFailed(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101371", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101371";
   }

   public static Loggable logAnnotationProcessingFailedLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101371", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDependencyInjectionFailed(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101372", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101372";
   }

   public static Loggable logDependencyInjectionFailedLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101372", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAnnotationsChanged(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101373", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101373";
   }

   public static Loggable logAnnotationsChangedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101373", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToLookupTransaction(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101374", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101374";
   }

   public static Loggable logFailedToLookupTransactionLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101374", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFacesConfigParseException(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101375", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101375";
   }

   public static Loggable logFacesConfigParseExceptionLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101375", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBeanELResolverPurgerException(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101376", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101376";
   }

   public static Loggable logBeanELResolverPurgerExceptionLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101376", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFilteringConfigurationIgnored(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101377", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101377";
   }

   public static Loggable logFilteringConfigurationIgnoredLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101377", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getAbstractMethodMsgForOpenJPA() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101378", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getAbstractMethodMsgForOpenJPALoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101378", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoSuchMethodMsgForOpenJPA() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101379", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoSuchMethodMsgForOpenJPALoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101379", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFragmentNamesConflict(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101380", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101380";
   }

   public static Loggable logFragmentNamesConflictLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101380", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalFragmentRelativeOrdering(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101381", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101381";
   }

   public static Loggable logIllegalFragmentRelativeOrderingLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101381", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalWLServletAnnotation() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101382", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101382";
   }

   public static Loggable logIllegalWLServletAnnotationLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101382", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalWLFilterAnnotation() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101383", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101383";
   }

   public static Loggable logIllegalWLFilterAnnotationLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101383", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWLAnnotationDeprecated(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101384", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101384";
   }

   public static Loggable logWLAnnotationDeprecatedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101384", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalWebListenerAnnotation() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101385", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101385";
   }

   public static Loggable logIllegalWebListenerAnnotationLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101385", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalServletSecurityAnnotation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101386", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101386";
   }

   public static Loggable logIllegalServletSecurityAnnotationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101386", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logContextAlreadyStart(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101387", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101387";
   }

   public static Loggable logContextAlreadyStartLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101387", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidServletContextListener() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101388", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101388";
   }

   public static Loggable logInvalidServletContextListenerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101388", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotAddServletContextListener() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101389", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101389";
   }

   public static Loggable logCannotAddServletContextListenerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101389", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExclusiveValueAndUrlPatternsInAnnotation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101390", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101390";
   }

   public static Loggable logExclusiveValueAndUrlPatternsInAnnotationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101390", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFragmentCircularReferencesFound() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101391", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101391";
   }

   public static Loggable logFragmentCircularReferencesFoundLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101391", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGlassfishDescriptorParsed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101392", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101392";
   }

   public static Loggable logGlassfishDescriptorParsedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101392", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGlassfishDescriptorIgnored(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101393", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101393";
   }

   public static Loggable logGlassfishDescriptorIgnoredLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101393", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServletRequestGetPartException(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101394", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101394";
   }

   public static Loggable logServletRequestGetPartExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101394", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeployApplicationAsWar(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101395", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101395";
   }

   public static Loggable logDeployApplicationAsWarLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101395", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMappingAttrsMustBePresentInWebServletAnnotation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101396", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101396";
   }

   public static Loggable logMappingAttrsMustBePresentInWebServletAnnotationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101396", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMappingAttrsMustBePresentInWebFilterAnnotation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101397", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101397";
   }

   public static Loggable logMappingAttrsMustBePresentInWebFilterAnnotationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101397", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRequestUpgradeError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101398", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101398";
   }

   public static Loggable logRequestUpgradeErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101398", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDigestAuthNotImplemented(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101399", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101399";
   }

   public static Loggable logDigestAuthNotImplementedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101399", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRemoveNonSerializableAttributeForReload(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101400", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101400";
   }

   public static Loggable logRemoveNonSerializableAttributeForReloadLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101400", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUrlPatternMappedToMultipleServlets(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101401", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101401";
   }

   public static Loggable logUrlPatternMappedToMultipleServletsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101401", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGetRequestOrResponseWhenStateIsCompletedOrDispatched() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101402", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101402";
   }

   public static Loggable logGetRequestOrResponseWhenStateIsCompletedOrDispatchedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101402", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUncoveredHttpMethods(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101403", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101403";
   }

   public static Loggable logUncoveredHttpMethodsLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101403", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUncoveredHttpOmittedMethods(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("101404", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101404";
   }

   public static Loggable logUncoveredHttpOmittedMethodsLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("101404", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPreliminaryServletStub(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101405", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101405";
   }

   public static Loggable logPreliminaryServletStubLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101405", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFilterNameIsNullOrEmptyString() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101406", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101406";
   }

   public static Loggable logFilterNameIsNullOrEmptyStringLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101406", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServletNameIsNullOrEmptyString() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101407", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101407";
   }

   public static Loggable logServletNameIsNullOrEmptyStringLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101407", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorRemoveWLDFDataAccessRuntimeMBean(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101408", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101408";
   }

   public static Loggable logErrorRemoveWLDFDataAccessRuntimeMBeanLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101408", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIgnoredSharedLibrary(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101409", 64, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101409";
   }

   public static Loggable logIgnoredSharedLibraryLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101409", 64, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoServletServletInSecureMode(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("101410", 16, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101410";
   }

   public static Loggable logNoServletServletInSecureModeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("101410", 16, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSendErrorResponseException(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("101411", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101411";
   }

   public static Loggable logSendErrorResponseExceptionLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("101411", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJspFileIsNullOrNotStartWithSlash() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("101412", 8, args, HTTPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "101412";
   }

   public static Loggable logJspFileIsNullOrNotStartWithSlashLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("101412", 8, args, "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.servlet.HTTPLogLocalizer", HTTPLogger.class.getClassLoader());
      private MessageLogger messageLogger = HTTPLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = HTTPLogger.findMessageLogger();
      }
   }
}
