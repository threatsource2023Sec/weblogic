package weblogic.management.deploy.internal;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class SlaveDeployerLogger {
   private static final String LOCALIZER_CLASS = "weblogic.i18ntools.SlaveDeployerLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(SlaveDeployerLogger.class.getName());
   }

   public static String logCommitUpdateFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149202", 8, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149202";
   }

   public static Loggable logCommitUpdateFailedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149202", 8, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIntialPrepareApplicationFailed(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149205", 8, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149205";
   }

   public static Loggable logIntialPrepareApplicationFailedLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149205", 8, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSlaveResumeStart() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("149209", 64, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149209";
   }

   public static String logStartupFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149229", 1, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149229";
   }

   public static Loggable logStartupFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149229", 1, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSetActivationStateFailed(String arg0, boolean arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149231", 8, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149231";
   }

   public static Loggable logSetActivationStateFailedLoggable(String arg0, boolean arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149231", 8, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnexpectedThrowable() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("149233", 8, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149233";
   }

   public static Loggable logUnexpectedThrowableLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149233", 8, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedDeployClusterAS(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149239", 16, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149239";
   }

   public static String logRetryingInternalAppDeployment(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149243", 16, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149243";
   }

   public static Loggable logRetryingInternalAppDeploymentLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149243", 16, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnknownPlan(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149245", 8, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149245";
   }

   public static Loggable logUnknownPlanLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149245", 8, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSecurityRealmDoesNotSupportAppVersioning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149246", 8, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149246";
   }

   public static Loggable logSecurityRealmDoesNotSupportAppVersioningLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149246", 8, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTransitionAppFromAdminToRunningFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149247", 8, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149247";
   }

   public static String logTransitionAppFromRunningToAdminFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149248", 8, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149248";
   }

   public static String logUnprepareFailed(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149250", 8, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149250";
   }

   public static String logOperationFailed(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149251", 16, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149251";
   }

   public static String logNoDeployment(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149252", 16, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149252";
   }

   public static String logFailedToFindDeployment(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149256", 8, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149256";
   }

   public static Loggable logFailedToFindDeploymentLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149256", 8, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidDistribute(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149257", 8, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149257";
   }

   public static Loggable logInvalidDistributeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149257", 8, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRemoveStagedFilesFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149258", 16, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149258";
   }

   public static Loggable logRemoveStagedFilesFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149258", 16, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStartupFailedTransitionToAdmin(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149259", 1, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149259";
   }

   public static String logAppStartupFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149260", 8, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149260";
   }

   public static String logInvalidStateForRedeploy(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149264", 8, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149264";
   }

   public static Loggable logInvalidStateForRedeployLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149264", 8, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTaskFailed(String arg0, String arg1, String arg2, Exception arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("149265", 8, args, SlaveDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149265";
   }

   public static Loggable logTaskFailedLoggable(String arg0, String arg1, String arg2, Exception arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("149265", 8, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String illegalStateForDeploy(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149266", 8, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logCancelFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149267", 8, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable logCancelFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149267", 8, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStaticDeploymentOfNonVersionAppCheck(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149268", 8, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable logStaticDeploymentOfNonVersionAppCheckLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149268", 8, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBothStaticFileRedeployAndModuleRedeploy() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149269", 8, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable logBothStaticFileRedeployAndModuleRedeployLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149269", 8, args, "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SlaveDeployerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.i18ntools.SlaveDeployerLogLocalizer", SlaveDeployerLogger.class.getClassLoader());
      private MessageLogger messageLogger = SlaveDeployerLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = SlaveDeployerLogger.findMessageLogger();
      }
   }
}
