package weblogic.connector;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class ConnectorLogger {
   private static final String LOCALIZER_CLASS = "weblogic.connector.ConnectorLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(ConnectorLogger.class.getName());
   }

   public static String logConnectorServiceInitializing() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("190000", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190000";
   }

   public static Loggable logConnectorServiceInitializingLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190000", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConnectorServiceInitialized() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("190001", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190001";
   }

   public static Loggable logConnectorServiceInitializedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190001", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMCFNotFoundForJNDIName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190004", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190004";
   }

   public static Loggable logMCFNotFoundForJNDINameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190004", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCreateCFforMCFError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190005", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190005";
   }

   public static Loggable logCreateCFforMCFErrorLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190005", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCloseConnectionError(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("190008", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190008";
   }

   public static Loggable logCloseConnectionErrorLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("190008", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoConnectionRequestInfo() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("190009", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190009";
   }

   public static Loggable logNoConnectionRequestInfoLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190009", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoResourcePrincipalFound() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("190010", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190010";
   }

   public static Loggable logNoResourcePrincipalFoundLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190010", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRequestedSecurityType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190012", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190012";
   }

   public static Loggable logRequestedSecurityTypeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190012", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logContextProcessingError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190013", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190013";
   }

   public static Loggable logContextProcessingErrorLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190013", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFindLogWriterError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190019", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190019";
   }

   public static Loggable logFindLogWriterErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190019", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSetLogWriterError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190020", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190020";
   }

   public static Loggable logSetLogWriterErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190020", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvokeMethodError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("190023", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190023";
   }

   public static Loggable logInvokeMethodErrorLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("190023", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCreateInitialConnectionsError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190024", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190024";
   }

   public static Loggable logCreateInitialConnectionsErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190024", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCreateManagedConnectionException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190032", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190032";
   }

   public static Loggable logCreateManagedConnectionExceptionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190032", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCreateManagedConnectionError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190033", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190033";
   }

   public static Loggable logCreateManagedConnectionErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190033", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConnectorServiceInitError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190049", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190049";
   }

   public static Loggable logConnectorServiceInitErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190049", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConnectorServiceShutdownError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190050", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190050";
   }

   public static Loggable logConnectorServiceShutdownErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190050", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnregisterCPRTMBeanError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190051", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190051";
   }

   public static Loggable logUnregisterCPRTMBeanErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190051", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInitCPRTMBeanError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190052", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190052";
   }

   public static Loggable logInitCPRTMBeanErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190052", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInitConnRTMBeanError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190053", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190053";
   }

   public static Loggable logInitConnRTMBeanErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190053", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnregisterConnRTMBeanError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190054", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190054";
   }

   public static Loggable logUnregisterConnRTMBeanErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190054", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRegisterXAResourceError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190056", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190056";
   }

   public static Loggable logRegisterXAResourceErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190056", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAccessDeniedWarning(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("190064", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190064";
   }

   public static Loggable logAccessDeniedWarningLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("190064", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCreateCFReturnedNull(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190075", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190075";
   }

   public static Loggable logCreateCFReturnedNullLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190075", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeprecationReplacedWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190079", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190079";
   }

   public static Loggable logDeprecationReplacedWarningLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190079", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeprecationNotUsedWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190080", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190080";
   }

   public static Loggable logDeprecationNotUsedWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190080", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logProxyTestStarted(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190081", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190081";
   }

   public static Loggable logProxyTestStartedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190081", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logProxyTestSuccess(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190082", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190082";
   }

   public static Loggable logProxyTestSuccessLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190082", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logProxyTestError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190084", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190084";
   }

   public static Loggable logProxyTestErrorLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190084", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRarMarkedForLateDeployment(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190085", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190085";
   }

   public static Loggable logRarMarkedForLateDeploymentLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190085", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJNDINameAlreadyExists(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190088", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190088";
   }

   public static Loggable logJNDINameAlreadyExistsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190088", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJarFileProcessingError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190089", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190089";
   }

   public static Loggable logJarFileProcessingErrorLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190089", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGetLocalTransactionError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190090", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190090";
   }

   public static Loggable logGetLocalTransactionErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190090", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGetXAResourceError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190091", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190091";
   }

   public static Loggable logGetXAResourceErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190091", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRegisterNonXAResourceError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190092", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190092";
   }

   public static Loggable logRegisterNonXAResourceErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190092", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logProxyTestFailureInfo(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190097", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190097";
   }

   public static Loggable logProxyTestFailureInfoLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190097", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReReleasingResource(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190098", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190098";
   }

   public static Loggable logReReleasingResourceLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190098", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConnectionAlreadyClosed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190099", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190099";
   }

   public static Loggable logConnectionAlreadyClosedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190099", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCloseNotFoundOnHandle(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190100", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190100";
   }

   public static Loggable logCloseNotFoundOnHandleLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190100", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeprecatedLinkref(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190101", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190101";
   }

   public static Loggable logDeprecatedLinkrefLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190101", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStackTrace(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("199999", 128, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199999";
   }

   public static Loggable logStackTraceLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199999", 128, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStackTraceString(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("199998", 128, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199998";
   }

   public static Loggable logStackTraceStringLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199998", 128, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDiagImageUnregisterFailure(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190102", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190102";
   }

   public static Loggable logDiagImageUnregisterFailureLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190102", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDiagImageRegisterFailure(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190103", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190103";
   }

   public static Loggable logDiagImageRegisterFailureLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190103", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConfigPropWarning(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("190104", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190104";
   }

   public static Loggable logConfigPropWarningLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("190104", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGetAnonymousSubjectFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("190105", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190105";
   }

   public static Loggable logGetAnonymousSubjectFailedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190105", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToFindModuleRuntimeMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190106", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190106";
   }

   public static Loggable logFailedToFindModuleRuntimeMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190106", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToUnregisterModuleRuntimeMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190107", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190107";
   }

   public static Loggable logFailedToUnregisterModuleRuntimeMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190107", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInitJndiSubcontextsFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190108", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190108";
   }

   public static Loggable logInitJndiSubcontextsFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190108", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExtractingNativeLib(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190109", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190109";
   }

   public static Loggable logExtractingNativeLibLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190109", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTimerWarning() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("190110", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190110";
   }

   public static Loggable logTimerWarningLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190110", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidDye(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190111", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190111";
   }

   public static Loggable logInvalidDyeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190111", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRegisterForXARecoveryFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190112", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190112";
   }

   public static Loggable logRegisterForXARecoveryFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190112", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnregisterForXARecoveryFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190113", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190113";
   }

   public static Loggable logUnregisterForXARecoveryFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190113", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToApplyPoolChanges(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190114", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190114";
   }

   public static Loggable logFailedToApplyPoolChangesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190114", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMCFNotImplementResourceAdapterAssociation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190115", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190115";
   }

   public static Loggable logMCFNotImplementResourceAdapterAssociationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190115", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidRecoveryEvent(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190116", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190116";
   }

   public static Loggable logInvalidRecoveryEventLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190116", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCleanupFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190117", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190117";
   }

   public static Loggable logCleanupFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190117", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConnectionError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190118", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190118";
   }

   public static Loggable logConnectionErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190118", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDestroyFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190119", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190119";
   }

   public static Loggable logDestroyFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190119", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullXAResource() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("190120", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190120";
   }

   public static Loggable logNullXAResourceLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190120", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDissociateHandlesFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190121", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190121";
   }

   public static Loggable logDissociateHandlesFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190121", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLazyEnlistNullMC() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("190122", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190122";
   }

   public static Loggable logLazyEnlistNullMCLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190122", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRequestedSharingScope(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190123", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190123";
   }

   public static Loggable logRequestedSharingScopeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190123", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToDeployLinkRef(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("190124", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190124";
   }

   public static Loggable logFailedToDeployLinkRefLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("190124", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAssertionError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190125", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190125";
   }

   public static Loggable logAssertionErrorLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190125", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringAnonymousUser() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190126", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringAnonymousUserLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190126", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringCloseCount() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190127", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringCloseCountLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190127", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringCreateCount() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190128", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringCreateCountLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190128", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringFreePoolSize() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190129", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringFreePoolSizeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190129", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringPoolSize() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190130", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringPoolSizeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190130", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringWaitingThreadCount() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190131", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringWaitingThreadCountLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190131", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringCreateCountDescription() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190133", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringCreateCountDescriptionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190133", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringFreePoolSizeDescription() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190134", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringFreePoolSizeDescriptionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190134", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringPoolSizeDescription() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190135", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringPoolSizeDescriptionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190135", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringWaitingThreadCountDescription() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190136", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringWaitingThreadCountDescriptionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190136", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringNever() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190137", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringNeverLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190137", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringUnavailable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190138", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringUnavailableLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190138", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringRunning() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190139", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringRunningLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190139", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringSuspended() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190140", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringSuspendedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190140", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringNew() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190141", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringNewLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190141", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringInitialized() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190142", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringInitializedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190142", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringPrepared() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190143", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringPreparedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190143", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringActivated() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190144", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringActivatedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190144", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringUnknown() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190145", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringUnknownLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190145", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSetLogWriterErrorWithCause(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("190146", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190146";
   }

   public static Loggable logSetLogWriterErrorWithCauseLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("190146", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPoolProfilingRecord(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("190147", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190147";
   }

   public static Loggable logPoolProfilingRecordLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("190147", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionInitialCapacityMustBePositive() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199000", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionInitialCapacityMustBePositiveLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199000", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPropertyVetoWarning(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("190148", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190148";
   }

   public static Loggable logPropertyVetoWarningLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("190148", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoAdapterJNDInameSetForInboundRA(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190149", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190149";
   }

   public static Loggable logNoAdapterJNDInameSetForInboundRALoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190149", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDiagnosticImageTimedOut() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("190150", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190150";
   }

   public static Loggable logDiagnosticImageTimedOutLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("190150", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBuildOutboundFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190151", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190151";
   }

   public static Loggable logBuildOutboundFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190151", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCreateInboundRuntimeMBeanFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190152", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190152";
   }

   public static Loggable logCreateInboundRuntimeMBeanFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190152", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToCloseLog(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190153", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190153";
   }

   public static Loggable logFailedToCloseLogLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190153", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToCreateLogStream(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190154", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190154";
   }

   public static Loggable logFailedToCreateLogStreamLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190154", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logComplianceWarnings(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190155", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190155";
   }

   public static Loggable logComplianceWarningsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190155", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoComplianceErrors(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190156", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190156";
   }

   public static Loggable logNoComplianceErrorsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190156", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNumComplianceErrorsAndWarnings(String arg0, int arg1, int arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("190157", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190157";
   }

   public static Loggable logNumComplianceErrorsAndWarningsLoggable(String arg0, int arg1, int arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("190157", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logComplianceIsLinkRef(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190158", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190158";
   }

   public static Loggable logComplianceIsLinkRefLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190158", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConnectionPoolReset(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190159", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190159";
   }

   public static Loggable logConnectionPoolResetLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190159", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotResetConnectionPoolInuse(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("190160", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190160";
   }

   public static Loggable logCannotResetConnectionPoolInuseLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("190160", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotExtractRARtoTempDir(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190161", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190161";
   }

   public static Loggable logCannotExtractRARtoTempDirLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190161", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotCreateTempDirDuringExtraction(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("190162", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "190162";
   }

   public static Loggable logCannotCreateTempDirDuringExtractionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("190162", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMaxCapacityZero() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199001", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMaxCapacityZeroLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199001", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMaxCapacityNegative() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199002", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMaxCapacityNegativeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199002", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMaxCapacityLessThanInitialCapacity(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199003", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMaxCapacityLessThanInitialCapacityLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199003", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMaxCapacityIncrementMustBePositive() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199004", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMaxCapacityIncrementMustBePositiveLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199004", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMaxCapacityTooHigh(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199005", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMaxCapacityTooHighLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199005", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionShrinkFrequencySecondsMustBePositive() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199006", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionShrinkFrequencySecondsMustBePositiveLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199006", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionInactiveConnectionTimeoutSecondsNegative() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199007", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionInactiveConnectionTimeoutSecondsNegativeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199007", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionNoDescriptorOrAltDD() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199010", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionNoDescriptorOrAltDDLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199010", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionNoDescriptor() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199011", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionNoDescriptorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199011", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMissingSchema() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199012", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMissingSchemaLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199012", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionNoComponents(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199013", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionNoComponentsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199013", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMoreThanOneComponent(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199014", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMoreThanOneComponentLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199014", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionRollbackModuleFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199015", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionRollbackModuleFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199015", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionCreateRuntimeMBeanForConnectorModuleFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199016", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionCreateRuntimeMBeanForConnectorModuleFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199016", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionCloseVJarFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199017", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionCloseVJarFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199017", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionCreateVJarFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199018", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionCreateVJarFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199018", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionInitializeJndiSubcontextsFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199019", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionInitializeJndiSubcontextsFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199019", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionPrepareUpdateFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199020", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionPrepareUpdateFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199020", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionJndiNameNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199021", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionJndiNameNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199021", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionNoInitialContextForJndi() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199022", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionNoInitialContextForJndiLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199022", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionResourceLinkNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199023", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionResourceLinkNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199023", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionNoInitialContextForResourceLink() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199024", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionNoInitialContextForResourceLinkLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199024", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionAlreadyDeployed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199028", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionAlreadyDeployedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199028", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionBindingFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199029", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionBindingFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199029", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionUnbindFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199031", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionUnbindFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199031", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionNoInitialContextForUnbind(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199032", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionNoInitialContextForUnbindLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199032", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionResourceLinkAlreadyBound(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199036", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionResourceLinkAlreadyBoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199036", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionAppScopedBindFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199037", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionAppScopedBindFailedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199037", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionUnbindAdminObjectFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199038", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionUnbindAdminObjectFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199038", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionGetConnectionFactoryFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199039", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionGetConnectionFactoryFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199039", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionRANewInstanceFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199041", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionRANewInstanceFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199041", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionImageSourceCreation(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199042", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionImageSourceCreationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199042", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionPrepareUninitializedRA() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199043", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionPrepareUninitializedRALoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199043", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionActivateUnpreparedRA(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199044", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionActivateUnpreparedRALoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199044", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionRollbackActivatedRA() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199045", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionRollbackActivatedRALoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199045", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionCreateNativeLib() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199046", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionCreateNativeLibLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199046", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionBadRAClassSpec(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199047", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionBadRAClassSpecLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199047", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionAdapterNotVersionable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199048", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionAdapterNotVersionableLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199048", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionPopulateWorkManager() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199049", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionPopulateWorkManagerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199049", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionStartRA(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199050", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionStartRALoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199050", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionCreateBootstrap(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199051", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionCreateBootstrapLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199051", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionVersionRA() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199052", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionVersionRALoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199052", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionWorkRuntimer() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199053", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionWorkRuntimerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199053", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionIntrospectProperties(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199054", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionIntrospectPropertiesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199054", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionSetterNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199055", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionSetterNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199055", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionInvokeSetter(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199056", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionInvokeSetterLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199056", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionBadPropertyType(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199065", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionBadPropertyTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199065", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionPropertyValueTypeMismatch(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("199066", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionPropertyValueTypeMismatchLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("199066", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionLoginException(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199067", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionLoginExceptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199067", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionRANotDeployed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199068", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionRANotDeployedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199068", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionInitializeActivationSpecFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199071", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionInitializeActivationSpecFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199071", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionInstantiateClassFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199072", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionInstantiateClassFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199072", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionBadValue(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199073", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionBadValueLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199073", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMissingRequiredProperty(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199074", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMissingRequiredPropertyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199074", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionRANotActive(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199075", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionRANotActiveLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199075", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionRANotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199076", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionRANotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199076", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionNoMessageListener(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199077", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionNoMessageListenerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199077", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionNoInboundRAElement() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199089", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionNoInboundRAElementLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199089", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionNoMessageAdapterElement() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199090", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionNoMessageAdapterElementLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199090", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionNoMessageListenerElement() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199091", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionNoMessageListenerElementLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199091", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionAssertionError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199092", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionAssertionErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199092", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionSetDyeBitsFailedDiagCtxNotEnabled() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199093", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionSetDyeBitsFailedDiagCtxNotEnabledLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199093", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionInvalidDyeValue(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199094", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionInvalidDyeValueLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199094", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionInvalidDye(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199095", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionInvalidDyeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199095", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionFailedToGetDiagCtx(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199096", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionFailedToGetDiagCtxLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199096", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionGetDyeBitsFailedDiagCtxNotEnabled() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199097", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionGetDyeBitsFailedDiagCtxNotEnabledLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199097", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionCannotDeleteConnection() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199098", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionCannotDeleteConnectionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199098", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionEnlistmentFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199099", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionEnlistmentFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199099", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMCGetLocalTransactionThrewNonResourceException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199100", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMCGetLocalTransactionThrewNonResourceExceptionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199100", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMCGetLocalTransactionReturnedNull(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199101", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMCGetLocalTransactionReturnedNullLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199101", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionRegisterNonXAFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199102", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionRegisterNonXAFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199102", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionCommitFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199103", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionCommitFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199103", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionRollbackFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199104", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionRollbackFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199104", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMCGetXAResourceReturnedNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199105", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMCGetXAResourceReturnedNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199105", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMCGetXAResourceThrewNonResourceException(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199106", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMCGetXAResourceThrewNonResourceExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199106", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMCFCreateManagedConnectionReturnedNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199107", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMCFCreateManagedConnectionReturnedNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199107", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionInitializeForRecoveryFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199108", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionInitializeForRecoveryFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199108", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionEnlistResourceIllegalType() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199109", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionEnlistResourceIllegalTypeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199109", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionRegisterResourceIllegalType(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199110", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionRegisterResourceIllegalTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199110", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionXAStartInLocalTxIllegal() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199111", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionXAStartInLocalTxIllegalLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199111", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionCreateMCFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199112", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionCreateMCFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199112", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionFailedMCSetup() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199113", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionFailedMCSetupLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199113", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionObjectIdNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199114", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionObjectIdNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199114", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMCGetConnectionReturnedNull(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199116", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMCGetConnectionReturnedNullLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199116", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionDuplicateHandle() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199117", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionDuplicateHandleLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199117", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionTestResourceException(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199118", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionTestResourceExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199118", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionTestNonResourceException(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199119", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionTestNonResourceExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199119", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMCFNotImplementValidatingMCF() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199120", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMCFNotImplementValidatingMCFLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199120", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getLazyEnlistNullMC(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199121", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getLazyEnlistNullMCLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199121", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionRAAccessDenied(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199122", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionRAAccessDeniedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199122", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionGetConnectionFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199123", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionGetConnectionFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199123", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionPoolDisabled(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199124", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionPoolDisabledLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199124", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMCFCreateCFReturnedNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199125", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMCFCreateCFReturnedNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199125", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionStackTrace() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199126", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionStackTraceLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199126", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionLocalTxNotSupported() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199127", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionLocalTxNotSupportedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199127", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionHandleNotSet() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199128", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionHandleNotSetLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199128", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionOutboundPrepareFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199129", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionOutboundPrepareFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199129", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionResumePoolFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199130", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionResumePoolFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199130", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionJndiBindFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199131", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionJndiBindFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199131", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionDeactivateException(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199132", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionDeactivateExceptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199132", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionShutdownException(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199133", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionShutdownExceptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199133", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionCFJndiNameDuplicate(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199134", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionCFJndiNameDuplicateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199134", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionCFResourceLinkDuplicate(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199135", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionCFResourceLinkDuplicateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199135", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionJndiVerifyFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199136", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionJndiVerifyFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199136", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMCFNoImplementResourceAdapterAssociation(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199137", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMCFNoImplementResourceAdapterAssociationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199137", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionSetRAClassFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199138", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionSetRAClassFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199138", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMCFUnexpectedException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199139", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMCFUnexpectedExceptionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199139", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMCFClassNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199140", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMCFClassNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199140", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionInstantiateMCFFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199141", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionInstantiateMCFFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199141", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionAccessMCFFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199142", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionAccessMCFFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199142", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionGetConnectionFactoryFailedInternalError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199143", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionGetConnectionFactoryFailedInternalErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199143", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionFailedAccessOutsideApp() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199144", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionFailedAccessOutsideAppLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199144", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionNotImplemented(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199145", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionNotImplementedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199145", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionMustBeLinkRef() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199146", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionMustBeLinkRefLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199146", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionNeedsRAXML() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199147", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionNeedsRAXMLLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199147", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionErrorCreatingNativeLibDir(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199148", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionErrorCreatingNativeLibDirLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199148", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionFileNotFoundForNativeLibDir(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199149", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionFileNotFoundForNativeLibDirLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199149", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionExceptionCreatingNativeLibDir(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199150", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionExceptionCreatingNativeLibDirLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199150", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionStartPoolFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199151", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionStartPoolFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199151", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionTestFrequencyNonZero() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199152", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionTestFrequencyNonZeroLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199152", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionInvalidTestingConfig() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199153", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionInvalidTestingConfigLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199153", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionWorkIsNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199154", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionWorkIsNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199154", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionDoWorkNotAccepted() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199155", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionDoWorkNotAcceptedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199155", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionWorkManagerSuspended() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199156", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionWorkManagerSuspendedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199156", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionSetExecutionContextFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199157", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionSetExecutionContextFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199157", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionInvalidGid(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199158", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionInvalidGidLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199158", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionGidNotRegistered(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199159", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionGidNotRegisteredLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199159", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionSecurityPrincipalMapNotSupported() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199160", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionSecurityPrincipalMapNotSupportedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199160", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionImportedTxAlreadyActive(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199161", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionImportedTxAlreadyActiveLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199161", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionBadMCFClassSpec(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199162", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionBadMCFClassSpecLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199162", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionActivatePoolFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199164", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionActivatePoolFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199164", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getTestConnectionsOnCreateTrue() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199165", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getTestConnectionsOnCreateTrueLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199165", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getTestConnectionsOnReleaseTrue() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199166", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getTestConnectionsOnReleaseTrueLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199166", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getTestConnectionsOnReserveTrue() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199167", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getTestConnectionsOnReserveTrueLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199167", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getFailedToForceLogRotation(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199168", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getFailedToForceLogRotationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199168", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getFailedToGetCF(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199169", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getFailedToGetCFLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199169", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getDeploySecurityBumpUpFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199170", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getDeploySecurityBumpUpFailedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199170", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSecurityPrincipalMapNotAllowed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("199171", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199171";
   }

   public static Loggable logSecurityPrincipalMapNotAllowedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199171", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logComplianceRAConfigurationException(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("199172", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199172";
   }

   public static Loggable logComplianceRAConfigurationExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199172", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logComplianceWLRAConfigurationException(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("199173", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199173";
   }

   public static Loggable logComplianceWLRAConfigurationExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199173", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionActivateSuspendedRA(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199174", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionActivateSuspendedRALoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199174", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAdapterShouldnotSendLocalTxEvent(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199175", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable logAdapterShouldnotSendLocalTxEventLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199175", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionAllocateConnectionOnStaleConnectionFactory(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199176", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionAllocateConnectionOnStaleConnectionFactoryLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199176", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionDeserializeConnectionManager() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199177", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionDeserializeConnectionManagerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199177", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeploySideBySide(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("199178", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199178";
   }

   public static Loggable logDeploySideBySideLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199178", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSkipSideBySide() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("199179", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199179";
   }

   public static Loggable logSkipSideBySideLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199179", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppNotSideBySide() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("199180", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199180";
   }

   public static Loggable logAppNotSideBySideLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199180", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWaitingComplete(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("199181", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199181";
   }

   public static Loggable logWaitingCompleteLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199181", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCompleteCalled(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("199182", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199182";
   }

   public static Loggable logCompleteCalledLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199182", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnknownHintWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("199183", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199183";
   }

   public static Loggable logUnknownHintWarningLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199183", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRuntimeTransactionSupportLevel(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("199184", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199184";
   }

   public static Loggable logRuntimeTransactionSupportLevelLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199184", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUsingDefaultValidationXml(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("199185", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199185";
   }

   public static Loggable logUsingDefaultValidationXmlLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199185", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUsingRarValidationXml(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("199186", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199186";
   }

   public static Loggable logUsingRarValidationXmlLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199186", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFindConstraintViolationErrors(String arg0, String arg1, int arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("199187", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199187";
   }

   public static Loggable logFindConstraintViolationErrorsLoggable(String arg0, String arg1, int arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("199187", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBeanValidationFailed(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("199188", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199188";
   }

   public static Loggable logBeanValidationFailedLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199188", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWorkRejectedDueToExceedLimit(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("199189", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199189";
   }

   public static Loggable logWorkRejectedDueToExceedLimitLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199189", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSetMaxConcurrentRequests(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("199190", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199190";
   }

   public static Loggable logSetMaxConcurrentRequestsLoggable(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199190", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logShareableRefToUnshareablePool(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("199191", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199191";
   }

   public static Loggable logShareableRefToUnshareablePoolLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199191", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnknownShareableRefToUnshareablePool(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("199192", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199192";
   }

   public static Loggable logUnknownShareableRefToUnshareablePoolLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199192", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMCDestroyedAlready(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("199193", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199193";
   }

   public static Loggable logMCDestroyedAlreadyLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("199193", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnexpectedExceptionDuringWorkEventNotification(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("199194", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199194";
   }

   public static Loggable logUnexpectedExceptionDuringWorkEventNotificationLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199194", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIgnoredErrorWhenProcessAnnotation(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("199195", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199195";
   }

   public static Loggable logIgnoredErrorWhenProcessAnnotationLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("199195", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConnectorAnnotationIgnored(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("199196", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199196";
   }

   public static Loggable logConnectorAnnotationIgnoredLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199196", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRemovingAdminObjectException(Object arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("199197", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199197";
   }

   public static Loggable logRemovingAdminObjectExceptionLoggable(Object arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199197", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String loginvokePreDestroy(String arg0, Object arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("199198", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199198";
   }

   public static Loggable loginvokePreDestroyLoggable(String arg0, Object arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199198", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionStartTimeout() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199199", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionStartTimeoutLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199199", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAnnotationScanWarnings(String arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("199200", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199200";
   }

   public static Loggable logAnnotationScanWarningsLoggable(String arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199200", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMergeFailedDueToAnnotationScanErrors(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("199201", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199201";
   }

   public static Loggable logMergeFailedDueToAnnotationScanErrorsLoggable(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199201", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIgnoredErrorWhenUnregisterRuntimeMBean(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("199202", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199202";
   }

   public static Loggable logIgnoredErrorWhenUnregisterRuntimeMBeanLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199202", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logComplianceNonCriticalErrors(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("199211", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199211";
   }

   public static Loggable logComplianceNonCriticalErrorsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199211", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPoolCreationError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("199212", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199212";
   }

   public static Loggable logPoolCreationErrorLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199212", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInternalDestroyFailedPoolError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("199213", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199213";
   }

   public static Loggable logInternalDestroyFailedPoolErrorLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199213", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalMethodCalledOnFailedPoolRuntimeMbean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("199214", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199214";
   }

   public static Loggable logIllegalMethodCalledOnFailedPoolRuntimeMbeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199214", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSetupFailedPool(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("199215", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199215";
   }

   public static Loggable logSetupFailedPoolLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199215", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIgnoredErrorOnPool(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("199216", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199216";
   }

   public static Loggable logIgnoredErrorOnPoolLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("199216", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotResetConnectionPoolFailedDuringValidation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("199217", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199217";
   }

   public static Loggable logCannotResetConnectionPoolFailedDuringValidationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199217", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotRDetermineConnectionPoolChange(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("199218", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199218";
   }

   public static Loggable logCannotRDetermineConnectionPoolChangeLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199218", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidShrinkThreshold(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199219", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable logInvalidShrinkThresholdLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199219", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidURKString(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199220", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidURKStringLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199220", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidDefiningAppInfoforURK(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199221", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidDefiningAppInfoforURKLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199221", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getWrongNameSpaceInJNDIName(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("199222", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getWrongNameSpaceInJNDINameLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("199222", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getAppInfoNSNotMatch(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("199223", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getAppInfoNSNotMatchLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("199223", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getRedundantJavaNameSpce(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199224", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getRedundantJavaNameSpceLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199224", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionNoMatchedConnectionDefinition(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("199300", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionNoMatchedConnectionDefinitionLoggable(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("199300", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionNoMatchedAdminObjectDefinition(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
      Loggable l = new Loggable("199301", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionNoMatchedAdminObjectDefinitionLoggable(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
      Loggable l = new Loggable("199301", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionAdminObjectInterfaceAmbiguously(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("199302", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionAdminObjectInterfaceAmbiguouslyLoggable(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("199302", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionAccessDenyOutsideApp(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("199303", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionAccessDenyOutsideAppLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("199303", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionExportObject(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("199304", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionExportObjectLoggable(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("199304", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionDuplicatedResourceDefinition(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("199305", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionDuplicatedResourceDefinitionLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("199305", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionAppDefinedResourceExist(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("199306", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionAppDefinedResourceExistLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("199306", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoSuchObjectException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("199307", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199307";
   }

   public static Loggable logNoSuchObjectExceptionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199307", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionRuntimeMBeanNotFoundException(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("199308", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199308";
   }

   public static Loggable logPartitionRuntimeMBeanNotFoundExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199308", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRegisterConnectorComponentRuntimeMbeanFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199309", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable logRegisterConnectorComponentRuntimeMbeanFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("199309", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionAppDefinedObjNotExist(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("199310", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionAppDefinedObjNotExistLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("199310", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionResourceAdapterNotFound(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("199311", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionResourceAdapterNotFoundLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("199311", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getDefinedPermissionNoAllowed() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199312", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getDefinedPermissionNoAllowedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("199312", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUseAppClassloader(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("199313", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199313";
   }

   public static Loggable logUseAppClassloaderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199313", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUseRAClassloader(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("199314", 64, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199314";
   }

   public static Loggable logUseRAClassloaderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199314", 64, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logClassloaderStructureIgnored(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("199315", 16, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199315";
   }

   public static Loggable logClassloaderStructureIgnoredLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199315", 16, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPoolDestroyError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("199316", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199316";
   }

   public static Loggable logPoolDestroyErrorLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199316", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAdminObjectCreationError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("199317", 8, args, ConnectorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "199317";
   }

   public static Loggable logAdminObjectCreationErrorLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("199317", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getJndiAccessOutsideServer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199318", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getJndiAccessOutsideServerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199318", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getAccessOutsidePartition(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199319", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getAccessOutsidePartitionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("199319", 8, args, "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConnectorLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.connector.ConnectorLogLocalizer", ConnectorLogger.class.getClassLoader());
      private MessageLogger messageLogger = ConnectorLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = ConnectorLogger.findMessageLogger();
      }
   }
}
