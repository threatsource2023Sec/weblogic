package weblogic.servlet.internal.session;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class HTTPSessionLogger {
   private static final String LOCALIZER_CLASS = "weblogic.servlet.internal.session.HTTPSessionLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(HTTPSessionLogger.class.getName());
   }

   public static String logDeprecatedCall(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100000", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100000";
   }

   public static Loggable logDeprecatedCallLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100000", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToRemoveSession(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100005", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100005";
   }

   public static Loggable logUnableToRemoveSessionLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100005", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPersistence(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100006", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100006";
   }

   public static Loggable logPersistenceLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100006", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeleteDirectory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100007", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100007";
   }

   public static Loggable logDeleteDirectoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100007", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToDelete(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100008", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100008";
   }

   public static Loggable logUnableToDeleteLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100008", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPickledSession(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100010", 128, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100010";
   }

   public static Loggable logPickledSessionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100010", 128, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorSavingSessionData(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100011", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100011";
   }

   public static Loggable logErrorSavingSessionDataLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100011", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotInvalidated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100013", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100013";
   }

   public static Loggable logNotInvalidatedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100013", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeletedFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100015", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100015";
   }

   public static Loggable logDeletedFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100015", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTestFailure(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100016", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100016";
   }

   public static Loggable logTestFailureLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100016", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorLoadingSessionData(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100018", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100018";
   }

   public static Loggable logErrorLoadingSessionDataLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100018", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionPath(String arg0, String arg1, boolean arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("100019", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100019";
   }

   public static Loggable logSessionPathLoggable(String arg0, String arg1, boolean arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("100019", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCookieFormatError(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100020", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100020";
   }

   public static Loggable logCookieFormatErrorLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100020", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidationInterval(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100022", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100022";
   }

   public static Loggable logInvalidationIntervalLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100022", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnexpectedTimeoutError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100025", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100025";
   }

   public static Loggable logUnexpectedTimeoutErrorLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100025", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnexpectedTimeoutErrorRaised(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100026", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100026";
   }

   public static Loggable logUnexpectedTimeoutErrorRaisedLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100026", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToDeserializeSessionData(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100028", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100028";
   }

   public static Loggable logUnableToDeserializeSessionDataLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100028", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionExpired(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100030", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100030";
   }

   public static Loggable logSessionExpiredLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100030", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorUnregisteringServletSessionRuntime(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100031", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100031";
   }

   public static Loggable logErrorUnregisteringServletSessionRuntimeLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100031", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCreatingServletSessionRuntimeMBean(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100032", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100032";
   }

   public static Loggable logErrorCreatingServletSessionRuntimeMBeanLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100032", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionNotAllowed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100033", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100033";
   }

   public static Loggable logSessionNotAllowedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100033", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTimerInvalidatedSession(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100035", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100035";
   }

   public static Loggable logTimerInvalidatedSessionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100035", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCreatingSessionContextOfType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100037", 128, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100037";
   }

   public static Loggable logCreatingSessionContextOfTypeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100037", 128, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnknownPeristentType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100038", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100038";
   }

   public static Loggable logUnknownPeristentTypeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100038", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logClusteringRequiredForReplication(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100039", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100039";
   }

   public static Loggable logClusteringRequiredForReplicationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100039", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGetAttributeEJBObject(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100040", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100040";
   }

   public static Loggable logGetAttributeEJBObjectLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100040", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSetAttributeEJBObject(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100041", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100041";
   }

   public static Loggable logSetAttributeEJBObjectLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100041", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorReconstructingEJBObject(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100042", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100042";
   }

   public static Loggable logErrorReconstructingEJBObjectLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100042", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorFindingHandle(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100043", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100043";
   }

   public static Loggable logErrorFindingHandleLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100043", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFoundWLCookie(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100044", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100044";
   }

   public static Loggable logFoundWLCookieLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100044", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMalformedWLCookie(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100045", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100045";
   }

   public static Loggable logMalformedWLCookieLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100045", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCreateNewSessionForPath(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100046", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100046";
   }

   public static Loggable logCreateNewSessionForPathLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100046", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPerformOperation(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("100047", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100047";
   }

   public static Loggable logPerformOperationLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("100047", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRetrievedROIDFromSecondary(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("100048", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100048";
   }

   public static Loggable logRetrievedROIDFromSecondaryLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("100048", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBecomePrimary(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100050", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100050";
   }

   public static Loggable logBecomePrimaryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100050", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBecomeSecondary(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100051", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100051";
   }

   public static Loggable logBecomeSecondaryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100051", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnregister(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100052", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100052";
   }

   public static Loggable logUnregisterLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100052", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToUpdateSecondary(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100053", 16, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100053";
   }

   public static Loggable logFailedToUpdateSecondaryLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100053", 16, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSecondaryNotFound(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100054", 16, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100054";
   }

   public static Loggable logSecondaryNotFoundLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100054", 16, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionIDContainsReservedKeyword(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100055", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100055";
   }

   public static Loggable logSessionIDContainsReservedKeywordLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100055", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionGotInvalidatedBeforeCreationCouldComplete(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100056", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100056";
   }

   public static Loggable logSessionGotInvalidatedBeforeCreationCouldCompleteLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100056", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logContextNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100057", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100057";
   }

   public static Loggable logContextNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100057", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPersistentStoreTypeNotReplicated(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100058", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100058";
   }

   public static Loggable logPersistentStoreTypeNotReplicatedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100058", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnexpectedErrorCleaningUpSessions(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100059", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100059";
   }

   public static Loggable logUnexpectedErrorCleaningUpSessionsLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100059", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnexpectedError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100060", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100060";
   }

   public static Loggable logUnexpectedErrorLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100060", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTransientMemoryAttributeError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("100061", 16, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100061";
   }

   public static Loggable logTransientMemoryAttributeErrorLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("100061", 16, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTransientReplicatedAttributeError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("100062", 16, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100062";
   }

   public static Loggable logTransientReplicatedAttributeErrorLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("100062", 16, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTransientFileAttributeError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("100063", 16, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100063";
   }

   public static Loggable logTransientFileAttributeErrorLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("100063", 16, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTransientJDBCAttributeError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("100064", 16, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100064";
   }

   public static Loggable logTransientJDBCAttributeErrorLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("100064", 16, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAttributeRemovalFailure(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("100065", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100065";
   }

   public static Loggable logAttributeRemovalFailureLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("100065", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionNotFound(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("100066", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100066";
   }

   public static Loggable logSessionNotFoundLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("100066", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSecondaryIDNotFound(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("100067", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100067";
   }

   public static Loggable logSecondaryIDNotFoundLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("100067", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToFindSecondaryInfo(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("100068", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100068";
   }

   public static Loggable logFailedToFindSecondaryInfoLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("100068", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGetAttributeEJBHome(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100069", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100069";
   }

   public static Loggable logGetAttributeEJBHomeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100069", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorReconstructingEJBHome(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100070", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100070";
   }

   public static Loggable logErrorReconstructingEJBHomeLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100070", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSetAttributeEJBHome(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100071", 64, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100071";
   }

   public static Loggable logSetAttributeEJBHomeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100071", 64, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorFindingHomeHandle(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100072", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100072";
   }

   public static Loggable logErrorFindingHomeHandleLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100072", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionSerializingAttributeWrapper(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100073", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100073";
   }

   public static Loggable logExceptionSerializingAttributeWrapperLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100073", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logObjectNotSerializable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100088", 128, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100088";
   }

   public static Loggable logObjectNotSerializableLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100088", 128, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionObjectSize(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100077", 128, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100077";
   }

   public static Loggable logSessionObjectSizeLoggable(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100077", 128, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionSize(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100078", 128, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100078";
   }

   public static Loggable logSessionSizeLoggable(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100078", 128, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWANSessionConfigurationError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("100079", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100079";
   }

   public static Loggable logWANSessionConfigurationErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("100079", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIncompatiblePersistentStore(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100081", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100081";
   }

   public static Loggable logIncompatiblePersistentStoreLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100081", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInsufficientConfiguration(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100082", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100082";
   }

   public static Loggable logInsufficientConfigurationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100082", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInhomogeneousDeploymentForApp(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("100083", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100083";
   }

   public static Loggable logInhomogeneousDeploymentForAppLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("100083", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInhomogeneousDeploymentForVHost(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("100084", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100084";
   }

   public static Loggable logInhomogeneousDeploymentForVHostLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("100084", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJDBCSessionConcurrentModification(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100087", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100087";
   }

   public static Loggable logJDBCSessionConcurrentModificationLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100087", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionAccessFromNonPrimaryNonSecondary(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("100089", 16, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100089";
   }

   public static Loggable logSessionAccessFromNonPrimaryNonSecondaryLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("100089", 16, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLATUpdateError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100090", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100090";
   }

   public static Loggable logLATUpdateErrorLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100090", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAttributeChanged(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("100091", 128, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100091";
   }

   public static Loggable logAttributeChangedLoggable(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("100091", 128, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDumpSession(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100092", 128, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100092";
   }

   public static Loggable logDumpSessionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100092", 128, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDebugSessionEvent(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("100093", 128, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100093";
   }

   public static Loggable logDebugSessionEventLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("100093", 128, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionAccessFromNonPrimary(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("100094", 16, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100094";
   }

   public static Loggable logSessionAccessFromNonPrimaryLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("100094", 16, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAsyncJDBCSessionDatabaseConfigError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("100095", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100095";
   }

   public static Loggable logAsyncJDBCSessionDatabaseConfigErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("100095", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAsyncJDBCSessionIgnorePersistentStorePool(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("100096", 16, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100096";
   }

   public static Loggable logAsyncJDBCSessionIgnorePersistentStorePoolLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("100096", 16, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionIDlengthTooShort(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100097", 16, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100097";
   }

   public static Loggable logSessionIDlengthTooShortLoggable(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100097", 16, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJDBCSessionDatabaseConfigError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("100098", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100098";
   }

   public static Loggable logJDBCSessionDatabaseConfigErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("100098", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorGettingReplicatedSession(String arg0, String arg1, String arg2, Exception arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("100099", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100099";
   }

   public static Loggable logErrorGettingReplicatedSessionLoggable(String arg0, String arg1, String arg2, Exception arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("100099", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReplicationServicesNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("100100", 8, args, HTTPSessionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "100100";
   }

   public static Loggable logReplicationServicesNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("100100", 8, args, "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HTTPSessionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.servlet.internal.session.HTTPSessionLogLocalizer", HTTPSessionLogger.class.getClassLoader());
      private MessageLogger messageLogger = HTTPSessionLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = HTTPSessionLogger.findMessageLogger();
      }
   }
}
