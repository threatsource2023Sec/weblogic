package weblogic.deploy.service.internal;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class DeploymentServiceLogger {
   private static final String LOCALIZER_CLASS = "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(DeploymentServiceLogger.class.getName());
   }

   public static String logBadContentTypeServletRequest(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290001", 8, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290001";
   }

   public static Loggable logBadContentTypeServletRequestLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("290001", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionInServletRequest(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290003", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290003";
   }

   public static Loggable logExceptionInServletRequestLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("290003", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRequestWithNoAppName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("290006", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290006";
   }

   public static Loggable logRequestWithNoAppNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290006", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoUploadDirectory(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290007", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290007";
   }

   public static Loggable logNoUploadDirectoryLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("290007", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionOnExtract(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290008", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290008";
   }

   public static String logServletFailedToInit() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("290009", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290009";
   }

   public static Loggable logServletFailedToInitLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("290009", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServletInitFailedDueToPrivilegedActionViolation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("290010", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290010";
   }

   public static Loggable logServletInitFailedDueToPrivilegedActionViolationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290010", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnautherizedRequest(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290011", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290011";
   }

   public static Loggable logUnautherizedRequestLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("290011", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoUploadFileRequest() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("290012", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290012";
   }

   public static Loggable logNoUploadFileRequestLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("290012", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoUserNameOrPassword() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("290013", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290013";
   }

   public static Loggable logNoUserNameOrPasswordLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("290013", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidUserNameOrPassword() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("290014", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290014";
   }

   public static Loggable logInvalidUserNameOrPasswordLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("290014", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDomainWideSecretMismatch() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("290015", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290015";
   }

   public static Loggable logDomainWideSecretMismatchLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("290015", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAccessNotAllowed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("290016", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290016";
   }

   public static Loggable logAccessNotAllowedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290016", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppListenerException() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("290020", 8, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290020";
   }

   public static Loggable logAppListenerExceptionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("290020", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("290021", 8, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290021";
   }

   public static Loggable logNoFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290021", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStartControl() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("290022", 8, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290022";
   }

   public static Loggable logStartControlLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("290022", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCallbackAlreadyRegistered(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("290024", 8, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290024";
   }

   public static Loggable logCallbackAlreadyRegisteredLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290024", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidState() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("290025", 8, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290025";
   }

   public static Loggable logInvalidStateLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("290025", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAlreadyCancelled(long arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290026", 64, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290026";
   }

   public static Loggable logAlreadyCancelledLoggable(long arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("290026", 64, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTooLateToCancel(long arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290027", 64, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290027";
   }

   public static Loggable logTooLateToCancelLoggable(long arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("290027", 64, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCancelled(long arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("290028", 64, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290028";
   }

   public static Loggable logCancelledLoggable(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290028", 64, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRequestTimedOut(long arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("290029", 64, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290029";
   }

   public static Loggable logRequestTimedOutLoggable(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290029", 64, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoDataHandlerRegistered(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("290030", 8, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290030";
   }

   public static Loggable logNoDataHandlerRegisteredLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290030", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoTaskToCancel() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("290031", 8, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290031";
   }

   public static Loggable logNoTaskToCancelLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("290031", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDataHandlerExists(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("290032", 8, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290032";
   }

   public static Loggable logDataHandlerExistsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290032", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeferredDueToDisconnect(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290036", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290036";
   }

   public static Loggable logDeferredDueToDisconnectLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("290036", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String unrecognizedCallback() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("290039", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String cancelRejected(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290040", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String commitFailed(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290041", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String commitNoRequest() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("290042", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String optimisticConcurrencyErr(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290043", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String operationDelivery(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290044", 64, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String prepareOperation() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("290045", 64, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String commitOperation() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("290046", 64, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String cancelOperation() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("290047", 64, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String timedOut(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290048", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String noTargets(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290049", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String commitDeliveryFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290050", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String commitTimedOut(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290051", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String operationTimeout(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290052", 64, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String timedOutAdmin(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290053", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String deploymentCancelled(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290054", 64, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String timedOutDuring(long arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("290055", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String indeterminate() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("290056", 64, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String incompatibleModification() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("290057", 64, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String noRequestToCancel(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290058", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String unsupportedOperation(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290059", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String duplicateRegistration(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290060", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String unrecognizedTypes(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("290061", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logTransitioningServerToAdminState(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290062", 8, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290062";
   }

   public static String logCommitPendingRestart(long arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("290063", 64, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290063";
   }

   public static String logExceptionInServletRequestForDeploymentMsg(long arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("290064", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290064";
   }

   public static Loggable logExceptionInServletRequestForDeploymentMsgLoggable(long arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("290064", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionInServletRequestForDatatransferMsg(long arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("290065", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290065";
   }

   public static Loggable logExceptionInServletRequestForDatatransferMsgLoggable(long arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("290065", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionWhileGettingDataAsStream(long arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290066", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290066";
   }

   public static Loggable logExceptionWhileGettingDataAsStreamLoggable(long arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("290066", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String cancelledDueToClusterConstraints(long arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("290067", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logExceptionInServletRequestIntendedForAdminServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("290068", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290068";
   }

   public static Loggable logExceptionInServletRequestIntendedForAdminServerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290068", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String sendCommitFailMsgFailed(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290069", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logExceptionOnUpload(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290070", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290070";
   }

   public static String logFailedOnUploadingFile() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("290071", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290071";
   }

   public static Loggable logFailedOnUploadingFileLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("290071", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRequestWithInvalidAppName(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290073", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290073";
   }

   public static Loggable logRequestWithInvalidAppNameLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("290073", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFileUnavailable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("290074", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290074";
   }

   public static String errorSendMessageToAdminServer(String arg0, long arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("290075", 8, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290075";
   }

   public static String logMaxLengthExceedDetails(float arg0, float arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290076", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290076";
   }

   public static String logMaxLengthExceedHeadMsg() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("290077", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290077";
   }

   public static String logMaxLengthExceedFileList(String arg0, float arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290078", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290078";
   }

   public static String logRequestWithInvalidUploadPath(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290079", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290079";
   }

   public static Loggable logRequestWithInvalidUploadPathLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("290079", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRequestWithNoUploadPath(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("290080", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290080";
   }

   public static Loggable logRequestWithNoUploadPathLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290080", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRequestWithInvalidPartitionName(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290081", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290081";
   }

   public static Loggable logRequestWithInvalidPartitionNameLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("290081", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToOverwriteUserFile(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290082", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290082";
   }

   public static Loggable logUnableToOverwriteUserFileLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("290082", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUserFileAlreadyExists(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("290083", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290083";
   }

   public static Loggable logUserFileAlreadyExistsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("290083", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotCreateUploadDirectory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("290084", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290084";
   }

   public static Loggable logCannotCreateUploadDirectoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290084", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUploadDirectoryIsNotDirectory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("290085", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290085";
   }

   public static Loggable logUploadDirectoryIsNotDirectoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290085", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUploadDirectoryIsNotWritable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("290086", 16, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290086";
   }

   public static Loggable logUploadDirectoryIsNotWritableLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290086", 16, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWriteDataStreamException(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("290087", 8, args, DeploymentServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "290087";
   }

   public static Loggable logWriteDataStreamExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("290087", 8, args, "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentServiceLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.deploy.service.internal.DeploymentServiceLogLocalizer", DeploymentServiceLogger.class.getClassLoader());
      private MessageLogger messageLogger = DeploymentServiceLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = DeploymentServiceLogger.findMessageLogger();
      }
   }
}
