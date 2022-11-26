package weblogic.management;

import java.util.Locale;
import javax.management.ObjectName;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class ManagementLogger {
   private static final String LOCALIZER_CLASS = "weblogic.management.ManagementLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(ManagementLogger.class.getName());
   }

   public static String logDomainSaveFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("140008", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "140008";
   }

   public static String logFailedToFindConfig(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("140013", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "140013";
   }

   public static String logErrorInFileDistributionServlet(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141009", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141009";
   }

   public static String logBadRequestInFileDistributionServlet(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141010", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141010";
   }

   public static String logFileNotFoundProcessingFileRealmRequest(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141011", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141011";
   }

   public static String logClusterPropertyIgnoreBecauseNoClusterConfigured(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141021", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141021";
   }

   public static String logMBeanProxySetAttributeError(ObjectName arg0, String arg1, Object arg2, Object arg3, Throwable arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("141023", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141023";
   }

   public static String logNodeManagerError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141051", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141051";
   }

   public static String logPollerStarted() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141052", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141052";
   }

   public static String logPollerNotStarted() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141053", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141053";
   }

   public static String logNoAccess(ObjectName arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("141062", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141062";
   }

   public static String logNoMBeanAccess(ObjectName arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("141063", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141063";
   }

   public static String logFileNotFoundProcessingInitReplicaRequest(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141071", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141071";
   }

   public static String logMigrationTaskProgressInfo(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141073", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141073";
   }

   public static String logModuleNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141076", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141076";
   }

   public static String logParseDDFailed(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141077", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141077";
   }

   public static String logInitDDFailed(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("141078", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141078";
   }

   public static String logErrorProcessingInitReplicaRequest(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141081", 4, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141081";
   }

   public static String logErrorSettingAttribute(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141086", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141086";
   }

   public static String logUnrecognizedProperty(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141087", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141087";
   }

   public static String logBooting6xConfig() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141089", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141089";
   }

   public static String logNoAccessForSubjectRole(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141102", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141102";
   }

   public static Loggable logNoAccessForSubjectRoleLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141102", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStartupBuildName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141107", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141107";
   }

   public static String logInvalidSystemProperty(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141124", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141124";
   }

   public static String logConfigurationFileIsReadOnly(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141126", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141126";
   }

   public static String logNameAttributeIsReadOnly(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141133", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141133";
   }

   public static Loggable logNameAttributeIsReadOnlyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("141133", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorFDSMissingCredentials() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141145", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141145";
   }

   public static String logErrorFDSAuthenticationFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141146", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141146";
   }

   public static String logErrorFDSUnauthorizedUploadAttempt(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141147", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141147";
   }

   public static String logErrorFDSUnauthorizedDownloadAttempt(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141148", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141148";
   }

   public static String logErrorFDSAuthenticationFailedDueToDomainWideSecretMismatch(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141149", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141149";
   }

   public static String logErrorConnectingToAdminServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141151", 2, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141151";
   }

   public static String logJavaSystemProperties(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141187", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141187";
   }

   public static String logCommitConfigUpdateFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141190", 4, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141190";
   }

   public static Loggable logCommitConfigUpdateFailedLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("141190", 4, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPrepareConfigUpdateFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141191", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141191";
   }

   public static Loggable logPrepareConfigUpdateFailedLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("141191", 16, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReadEditLockFileFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141192", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141192";
   }

   public static String logWriteEditLockFileFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141193", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141193";
   }

   public static String logDeploymentRegistrationFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141194", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141194";
   }

   public static String logRegisterConfigUpdateFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141196", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141196";
   }

   public static Loggable logRegisterConfigUpdateFailedLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("141196", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeploymentFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141197", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141197";
   }

   public static String logUndeploymentFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141198", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141198";
   }

   public static String logCompatibilityInvokeModifiedConfig(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141199", 4, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141199";
   }

   public static String logDomainUpgrading(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141200", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141200";
   }

   public static String logCompatibilityWithPendingChanges() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141201", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141201";
   }

   public static Loggable logCompatibilityWithPendingChangesLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("141201", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinishAddFailed1(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141202", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141202";
   }

   public static Loggable logFinishAddFailed1Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141202", 16, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinishAddFailed2(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141203", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141203";
   }

   public static Loggable logFinishAddFailed2Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141203", 16, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinishAddFailed3(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141204", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141204";
   }

   public static Loggable logFinishAddFailed3Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141204", 16, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinishRemoveFailed1(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141205", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141205";
   }

   public static Loggable logFinishRemoveFailed1Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141205", 16, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinishRemoveFailed2(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141206", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141206";
   }

   public static Loggable logFinishRemoveFailed2Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141206", 16, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinishRemoveFailed3(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141207", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141207";
   }

   public static Loggable logFinishRemoveFailed3Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141207", 16, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPropertyChangeFailed1(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141208", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141208";
   }

   public static Loggable logPropertyChangeFailed1Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141208", 16, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPropertyChangeFailed2(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141209", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141209";
   }

   public static Loggable logPropertyChangeFailed2Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141209", 16, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPropertyChangeFailed3(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141210", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141210";
   }

   public static Loggable logPropertyChangeFailed3Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141210", 16, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPropertyInitializationFailed1(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141211", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141211";
   }

   public static Loggable logPropertyInitializationFailed1Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141211", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPropertyInitializationFailed2(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141212", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141212";
   }

   public static Loggable logPropertyInitializationFailed2Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141212", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPropertyInitializationFailed3(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141213", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141213";
   }

   public static Loggable logPropertyInitializationFailed3Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141213", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAddBeanFailed1(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141214", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141214";
   }

   public static Loggable logAddBeanFailed1Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141214", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAddBeanFailed2(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141215", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141215";
   }

   public static Loggable logAddBeanFailed2Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141215", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAddBeanFailed3(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141216", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141216";
   }

   public static Loggable logAddBeanFailed3Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141216", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRemoveBeanFailed1(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141217", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141217";
   }

   public static Loggable logRemoveBeanFailed1Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141217", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRemoveBeanFailed2(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141218", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141218";
   }

   public static Loggable logRemoveBeanFailed2Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141218", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRemoveBeanFailed3(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141219", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141219";
   }

   public static Loggable logRemoveBeanFailed3Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141219", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logOldSecurityProvidersFound() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141220", 4, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141220";
   }

   public static String logErrorUndoCompatibility(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141221", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141221";
   }

   public static String logServerNameDoesNotExist(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141223", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141223";
   }

   public static Loggable logServerNameDoesNotExistLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141223", 64, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logASNotReachable() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141224", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141224";
   }

   public static String logPendingDeleteFileFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141225", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141225";
   }

   public static String logInvalidPrepareCallback(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141226", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141226";
   }

   public static String logBackingUpConfiguration(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141227", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141227";
   }

   public static String logCouldNotBackupConfiguration(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141228", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141228";
   }

   public static String logUpgradeCancelledByUser() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141229", 32, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141229";
   }

   public static String logCouldNotFindSystemResource(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141230", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141230";
   }

   public static String logCouldNotRegisterMBeanForJSR77(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141231", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141231";
   }

   public static String logEditLockPropertyDecryptionFailure(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("141232", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141232";
   }

   public static String logEditLockDecryptionFailure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141233", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141233";
   }

   public static String logRollbackFailure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141234", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141234";
   }

   public static String logExtraneousConfigText(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141237", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141237";
   }

   public static String logServerNeedsReboot(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141238", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Low");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141238";
   }

   public static String logNonDynamicAttributeChange(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141239", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Low");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141239";
   }

   public static String logExpectedVersion9(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141240", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141240";
   }

   public static Loggable logExpectedVersion9Loggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("141240", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExpectedPreVersion9(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141241", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141241";
   }

   public static Loggable logExpectedPreVersion9Loggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("141241", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnexpectedConfigVersion(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141242", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141242";
   }

   public static Loggable logUnexpectedConfigVersionLoggable(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141242", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeprecatedConfigurationProperty(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("141243", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141243";
   }

   public static String logConfigurationValidationProblem(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141244", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141244";
   }

   public static String logConfigurationSchemaFailure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141245", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141245";
   }

   public static Loggable logConfigurationSchemaFailureLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141245", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConfigurationDirMissing(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141246", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141246";
   }

   public static Loggable logConfigurationDirMissingLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("141246", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConfigurationDirMissingNoAdmin(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141247", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141247";
   }

   public static Loggable logConfigurationDirMissingNoAdminLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("141247", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConfigFileMissing(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141248", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141248";
   }

   public static Loggable logConfigFileMissingLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141248", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConfigFileMissingNoAdmin(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141249", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141249";
   }

   public static Loggable logConfigFileMissingNoAdminLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141249", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAttributeNotValidBefore90(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141251", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141251";
   }

   public static String logDomainVersionNotSupported(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141252", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141252";
   }

   public static Loggable logDomainVersionNotSupportedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141252", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorLoadingConfigTranslator(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141253", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141253";
   }

   public static Loggable logErrorLoadingConfigTranslatorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141253", 16, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGeneratingDomainDirectory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141254", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141254";
   }

   public static String logDomainDirectoryGenerationComplete(long arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141255", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141255";
   }

   public static String logBeanUpdateRuntimeException(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141256", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141256";
   }

   public static String logExceptionDuringConfigTranslation(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141257", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141257";
   }

   public static String logExceptionCreatingObjectName(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141258", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141258";
   }

   public static String logSendNotificationFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141259", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141259";
   }

   public static String logRemoveNotificationFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141260", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141260";
   }

   public static String logUnregisterNotificationFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141261", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141261";
   }

   public static String logAddFilterFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141262", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141262";
   }

   public static String logExceptionInCustomizer(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141263", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141263";
   }

   public static String logExceptionInMBeanProxy(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141264", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141264";
   }

   public static String logCouldNotGetConfigFileLock() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141265", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141265";
   }

   public static String logConfigurationParseError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141266", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141266";
   }

   public static Loggable logConfigurationParseErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141266", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConfigurationParseError2(String arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("141267", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141267";
   }

   public static Loggable logConfigurationParseError2Loggable(String arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("141267", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConfigurationParseError3(String arg0, int arg1, int arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("141268", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141268";
   }

   public static Loggable logConfigurationParseError3Loggable(String arg0, int arg1, int arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("141268", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTemporaryBeanTreeNotGarbageCollected(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141269", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141269";
   }

   public static String logCouldNotGetConfigFileLockRetry(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141270", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141270";
   }

   public static String logSetAttributeRecordingIOException(ObjectName arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("141271", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141271";
   }

   public static String logInvokeRecordingIOException(ObjectName arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("141272", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141272";
   }

   public static String logUpgradeClassNotFound(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141273", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141273";
   }

   public static String logProductionModePropertyDiffersFromConfig() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141274", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141274";
   }

   public static String logDevelopmentModePropertyDiffersFromConfig() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141275", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141275";
   }

   public static String logExceptionGettingClassForInterface(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141276", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141276";
   }

   public static String logPlatformMBeanServerInitFailure() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141277", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141277";
   }

   public static String logJavaEntropyConfig(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141278", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141278";
   }

   public static String logJavaEntropyConfigIsBlocking() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141279", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141279";
   }

   public static String logJavaEntropyConfigIsNonBlocking() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141280", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141280";
   }

   public static String logDuplicateLocalJNDIName(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("141281", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141281";
   }

   public static String logServerNameNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141282", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141282";
   }

   public static String logCouldNotDetermineServerName() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141283", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141283";
   }

   public static String logDeprecatedMBeanFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141284", 4, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141284";
   }

   public static String logNetworkAccessPointPropertyIgnoredBecauseNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141285", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141285";
   }

   public static String logDynamicServerCreationFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141286", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141286";
   }

   public static String logNoMatchingMachines(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141287", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141287";
   }

   public static String logRetryFileLock() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141288", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141288";
   }

   public static String logConfigVersionNotSupported(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141289", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141289";
   }

   public static Loggable logConfigVersionNotSupportedLoggable(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141289", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPluginNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141290", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141290";
   }

   public static Loggable logPluginNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141290", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSystemComponentNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141291", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141291";
   }

   public static String logMachineNotSpecified(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141292", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141292";
   }

   public static String logBadCAMReplicaitonExclusiveFile(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141293", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141293";
   }

   public static String logBadCAMReplicaitonExclusiveFileContent(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141294", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141294";
   }

   public static String logNodeManagerUnreachable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141295", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141295";
   }

   public static String logNodeManagerUnreachableError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("141296", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141296";
   }

   public static String logCouldNotGetServerLockRetry(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141297", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141297";
   }

   public static String logCouldNotRegisterWithAdminServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141298", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141298";
   }

   public static String logMBeanServerInitException(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141299", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141299";
   }

   public static String logActivateTimedOut(long arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141300", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141300";
   }

   public static String logNoAccessAllowedInPartition(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("141301", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141301";
   }

   public static Loggable logNoAccessAllowedInPartitionLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("141301", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoAccessAllowedSubject(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("141302", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141302";
   }

   public static Loggable logNoAccessAllowedSubjectLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("141302", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotKernelUser(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141303", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141303";
   }

   public static Loggable logNotKernelUserLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("141303", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoAccessAllowedForSubjectInPartition(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("141304", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141304";
   }

   public static Loggable logNoAccessAllowedForSubjectInPartitionLoggable(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("141304", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorOnFireManagementReload(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141305", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141305";
   }

   public static Loggable logErrorOnFireManagementReloadLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("141305", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSMbeanValidationError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141306", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141306";
   }

   public static Loggable logJMSMbeanValidationErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("141306", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRetryRegisterDeploymentService(int arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("141307", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141307";
   }

   public static Loggable logRetryRegisterDeploymentServiceLoggable(int arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("141307", 64, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringNew() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("141308", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringNewLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("141308", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringInitialized() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("141309", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringInitializedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("141309", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringPrepared() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("141310", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringPreparedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("141310", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringActivated() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("141311", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringActivatedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("141311", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStringSuspended() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("141312", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getStringSuspendedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("141312", 8, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCanNotResolveInStartEdit(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141313", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141313";
   }

   public static Loggable logCanNotResolveInStartEditLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("141313", 16, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGlobalMBeansVisibleToPartitionsTrue() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141314", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141314";
   }

   public static Loggable logGlobalMBeansVisibleToPartitionsTrueLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("141314", 16, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCancelConfigUpdateFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141315", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141315";
   }

   public static Loggable logCancelConfigUpdateFailedLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("141315", 16, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConfigWriteRetry(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141316", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141316";
   }

   public static Loggable logConfigWriteRetryLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("141316", 64, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAutoHaltAfterConfigChange(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141317", 32, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141317";
   }

   public static Loggable logAutoHaltAfterConfigChangeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("141317", 32, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAutoBootAfterConfigChange(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141318", 32, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141318";
   }

   public static Loggable logAutoBootAfterConfigChangeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("141318", 32, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToInitJMXPolicies(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141319", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141319";
   }

   public static Loggable logFailedToInitJMXPoliciesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("141319", 16, args, "weblogic.management.ManagementLogLocalizer", ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ManagementLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMXInvocationTimedOutOnActivate(long arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141320", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141320";
   }

   public static String logPropertyOverride(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141321", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141321";
   }

   public static String logInvalidSituationalConfig(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141322", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141322";
   }

   public static String logInvalidSituationalConfigException(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141323", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141323";
   }

   public static String logRetryFileLock2(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141324", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141324";
   }

   public static String logExceptionAcquiringConfigLock(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141325", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141325";
   }

   public static String logExceptionReleasingConfigLock(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141326", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141326";
   }

   public static String logSituationalConfigRequired() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141327", 4, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141327";
   }

   public static String logNonDynamicSitConfigChange(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141328", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Low");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141328";
   }

   public static String logErrorLoadingSitConfigFiles(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141329", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141329";
   }

   public static String logLoadingSitConfigFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141330", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141330";
   }

   public static String logDetectedPendingChangesWhileCancelingEditLock() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("141331", 16, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141331";
   }

   public static String logPersistentStoreFailOverLimit(String arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("141332", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141332";
   }

   public static String logMessagingBridgeFailOverLimit(String arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("141333", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141333";
   }

   public static String logFailOverLimit(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("141334", 64, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141334";
   }

   public static String logFailServerInvalidSituationalConfig(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141335", 4, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141335";
   }

   public static String logLDAPAndXMLRequireSignedConnection(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("141336", 8, args, ManagementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ManagementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "141336";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.ManagementLogLocalizer", ManagementLogger.class.getClassLoader());
      private MessageLogger messageLogger = ManagementLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = ManagementLogger.findMessageLogger();
      }
   }
}
