package weblogic.management.deploy.internal;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class DeployerRuntimeExtendedLogger {
   private static final String LOCALIZER_CLASS = "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(DeployerRuntimeExtendedLogger.class.getName());
   }

   public static String logNonExistPartition(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2164000", 16, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164000";
   }

   public static Loggable logNonExistPartitionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2164000", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStartStopOnTemplateNotSupported() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2164001", 16, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164001";
   }

   public static Loggable logStartStopOnTemplateNotSupportedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2164001", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonExistPartitionResourceGroup(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2164002", 16, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164002";
   }

   public static Loggable logNonExistPartitionResourceGroupLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2164002", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonExistResourceGroupTemplate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2164003", 16, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164003";
   }

   public static Loggable logNonExistResourceGroupTemplateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2164003", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotSpecifyPartitionWithoutResourceGroup(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2164004", 16, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164004";
   }

   public static Loggable logCannotSpecifyPartitionWithoutResourceGroupLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2164004", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotSpecifyTemplateWithGroup() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2164005", 16, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164005";
   }

   public static Loggable logCannotSpecifyTemplateWithGroupLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2164005", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonExistDomainResourceGroup(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2164007", 16, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164007";
   }

   public static Loggable logNonExistDomainResourceGroupLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2164007", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPlanAlreadyReverted() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2164008", 8, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164008";
   }

   public static Loggable logPlanAlreadyRevertedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2164008", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotSpecifyTemplateWithPartition() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2164009", 16, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164009";
   }

   public static Loggable logCannotSpecifyTemplateWithPartitionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2164009", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotSpecifyResourceGroupOption() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2164011", 16, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164011";
   }

   public static Loggable logCannotSpecifyResourceGroupOptionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2164011", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullRGApp(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2164012", 16, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164012";
   }

   public static Loggable logNullRGAppLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2164012", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullRGAppNonDynamic(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2164013", 16, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164013";
   }

   public static Loggable logNullRGAppNonDynamicLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2164013", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeploymentTaskCanceled(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2164014", 16, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164014";
   }

   public static Loggable logDeploymentTaskCanceledLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2164014", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logVersionRequiredForUndeploymentFromRGT() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2164015", 8, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164015";
   }

   public static Loggable logVersionRequiredForUndeploymentFromRGTLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2164015", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotStartAppWhenPartitionOrRGShutdown(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2164016", 16, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164016";
   }

   public static Loggable logCannotStartAppWhenPartitionOrRGShutdownLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2164016", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logImplicitlyRetiringOnStartup(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2164017", 64, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164017";
   }

   public static Loggable logImplicitlyRetiringOnStartupLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2164017", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMoreThanOneNonRetiringVersionsFoundOnStartup(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2164018", 16, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164018";
   }

   public static Loggable logMoreThanOneNonRetiringVersionsFoundOnStartupLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2164018", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMoreThanOneRetiringVersionsFoundOnStartup(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2164019", 16, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164019";
   }

   public static Loggable logMoreThanOneRetiringVersionsFoundOnStartupLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2164019", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExistingChange(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2164020", 16, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164020";
   }

   public static Loggable logExistingChangeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2164020", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppMarkedForFutureRetirement(String arg0, long arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2164021", 64, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164021";
   }

   public static Loggable logAppMarkedForFutureRetirementLoggable(String arg0, long arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2164021", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeExtendedLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logModuleRemovedFromAppRuntimeStateDuringServerStartup(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2164022", 64, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164022";
   }

   public static String logFailedToDeployNewVersion(String arg0, String arg1, String arg2, String arg3, Exception arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("2164023", 8, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164023";
   }

   public static String logExceptionInRetirementOfOldVersion(String arg0, String arg1, String arg2, Exception arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2164024", 64, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164024";
   }

   public static String failedToReadDeploymentConfigOverrides(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2164025", 8, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164025";
   }

   public static String initiatingDeployOfNewVersion(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2164026", 64, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164026";
   }

   public static String completedDeployOfNewVersion(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2164027", 64, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164027";
   }

   public static String schedulingRetirement(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2164028", 64, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164028";
   }

   public static String initiatingRetirementOfOldVersion(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2164029", 64, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164029";
   }

   public static String completedRetirementOfOldVersion(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2164030", 64, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164030";
   }

   public static String ignoringModifiedDCO(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2164031", 64, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164031";
   }

   public static String cancelMSIDWork(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2164032", 64, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164032";
   }

   public static String invalidRetireTimeout(String arg0, String arg1, String arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2164033", 64, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164033";
   }

   public static String blockedMSIDAppProcessing(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2164034", 64, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164034";
   }

   public static String unblockedMSIDAppProcessing(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2164035", 64, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164035";
   }

   public static String logNullDeploymentMBeanLenient(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2164036", 16, args, DeployerRuntimeExtendedLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeExtendedLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2164036";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.deploy.internal.DeployerRuntimeExtendedLogLocalizer", DeployerRuntimeExtendedLogger.class.getClassLoader());
      private MessageLogger messageLogger = DeployerRuntimeExtendedLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = DeployerRuntimeExtendedLogger.findMessageLogger();
      }
   }
}
