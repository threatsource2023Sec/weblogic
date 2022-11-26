package weblogic.management.deploy.internal;

import java.util.ArrayList;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class DeployerRuntimeLogger {
   private static final String LOCALIZER_CLASS = "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(DeployerRuntimeLogger.class.getName());
   }

   public static String logInitFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149000", 1, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149000";
   }

   public static Loggable logInitFailedLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149000", 1, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullApp(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149001", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149001";
   }

   public static Loggable logNullAppLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149001", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoSource(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149002", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149002";
   }

   public static Loggable logNoSourceLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149002", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidSource(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149003", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149003";
   }

   public static Loggable logInvalidSourceLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149003", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTaskFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149004", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149004";
   }

   public static String logUnconfigTargets(ArrayList arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149011", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149011";
   }

   public static Loggable logUnconfigTargetsLoggable(ArrayList arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149011", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoSuchModule(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149013", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149013";
   }

   public static Loggable logNoSuchModuleLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149013", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoSuchTarget(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149014", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149014";
   }

   public static Loggable logNoSuchTargetLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149014", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAddTarget(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149015", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149015";
   }

   public static Loggable logAddTargetLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149015", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTaskInUse(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149021", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149021";
   }

   public static Loggable logTaskInUseLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149021", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTargetForComponent(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149025", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149025";
   }

   public static Loggable logInvalidTargetForComponentLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149025", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDescription(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149026", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149026";
   }

   public static Loggable logDescriptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149026", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidApp(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149027", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149027";
   }

   public static Loggable logInvalidAppLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149027", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnknownAppType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149028", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149028";
   }

   public static Loggable logUnknownAppTypeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149028", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidStagingMode(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149031", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149031";
   }

   public static Loggable logInvalidStagingModeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149031", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAlreadyStarted() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("149032", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149032";
   }

   public static Loggable logAlreadyStartedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149032", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppNotification(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149033", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149033";
   }

   public static Loggable logAppNotificationLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149033", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionReceived(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149034", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149034";
   }

   public static Loggable logExceptionReceivedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149034", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logModuleMessage(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149035", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149035";
   }

   public static Loggable logModuleMessageLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149035", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStartedDeployment(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149038", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149038";
   }

   public static String logRejectStagingChange(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149040", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149040";
   }

   public static String logClusterMemberAlreadyTargeted(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149045", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149045";
   }

   public static Loggable logClusterMemberAlreadyTargetedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149045", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServerAlreadyTargetedByCluster(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149048", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149048";
   }

   public static Loggable logServerAlreadyTargetedByClusterLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149048", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHostTargetForNonWebApp(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149054", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149054";
   }

   public static Loggable logHostTargetForNonWebAppLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149054", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServerAlreadyTargetedByOtherClusterMember(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("149055", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149055";
   }

   public static Loggable logServerAlreadyTargetedByOtherClusterMemberLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("149055", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorStagingMode(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149058", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149058";
   }

   public static Loggable logErrorStagingModeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149058", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStartTransition(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("149059", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149059";
   }

   public static Loggable logStartTransitionLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("149059", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSuccessfulTransition(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("149060", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149060";
   }

   public static Loggable logSuccessfulTransitionLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("149060", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedTransition(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("149061", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149061";
   }

   public static Loggable logFailedTransitionLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("149061", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCannotCancelCompletedTask(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149062", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149062";
   }

   public static Loggable logErrorCannotCancelCompletedTaskLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149062", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoURI(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149066", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149066";
   }

   public static String logInternalError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149068", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149068";
   }

   public static String getOldActivate(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149069", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getOldActivateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149069", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoModules(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149073", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149073";
   }

   public static String logTaskSuccess(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149074", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149074";
   }

   public static String logTrace(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149078", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149078";
   }

   public static String logPartialRedeployOfArchive(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149080", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149080";
   }

   public static Loggable logPartialRedeployOfArchiveLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149080", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidAppVersion(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149081", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149081";
   }

   public static Loggable logInvalidAppVersionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149081", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidAppVersion2(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149082", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149082";
   }

   public static Loggable logInvalidAppVersion2Loggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149082", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logActivateWhileStopInProgress(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149083", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149083";
   }

   public static Loggable logActivateWhileStopInProgressLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149083", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logActiveAppVersionWarning(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149085", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149085";
   }

   public static String logRetireGracefully(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149086", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149086";
   }

   public static String logRetireTimeout(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149087", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149087";
   }

   public static String logRetirementCancelled(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149088", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149088";
   }

   public static String logActivateWhileRetireInProgress(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149089", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149089";
   }

   public static Loggable logActivateWhileRetireInProgressLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149089", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppVersionMismatch(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149091", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149091";
   }

   public static Loggable logAppVersionMismatchLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149091", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppVersionMismatch2(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149092", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149092";
   }

   public static Loggable logAppVersionMismatch2Loggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149092", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoActiveApp(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149093", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149093";
   }

   public static Loggable logNoActiveAppLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149093", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRetireNow(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149095", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149095";
   }

   public static String logVersionNotAllowedForDeprecatedOp(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149096", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149096";
   }

   public static Loggable logVersionNotAllowedForDeprecatedOpLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149096", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRetirementFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149097", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149097";
   }

   public static String logGracefulUndeployWhileRetireInProgress(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149098", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149098";
   }

   public static Loggable logGracefulUndeployWhileRetireInProgressLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149098", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSendDeploymentEventError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149099", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149099";
   }

   public static String logSendVetoableDeployEventError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149100", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149100";
   }

   public static String logInvalidTargetForPinnedAppUndeploy(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149101", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149101";
   }

   public static Loggable logInvalidTargetForPinnedAppUndeployLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149101", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logModuleTypeNotSupported(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149102", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149102";
   }

   public static Loggable logModuleTypeNotSupportedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149102", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionOccurred(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149103", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149103";
   }

   public static Loggable logExceptionOccurredLoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149103", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149104", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149104";
   }

   public static Loggable logNullInfoLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149104", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLibNameMismatch(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149105", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149105";
   }

   public static Loggable logLibNameMismatchLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149105", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoLibName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149106", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149106";
   }

   public static Loggable logNoLibNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149106", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLibVersionMismatch(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("149107", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149107";
   }

   public static Loggable logLibVersionMismatchLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("149107", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTaskFailedNoApp(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149108", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149108";
   }

   public static String logErrorPersistingActiveAppState(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149110", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149110";
   }

   public static String logRedeployWithSrcNotAllowedForNonVersion(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149111", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149111";
   }

   public static Loggable logRedeployWithSrcNotAllowedForNonVersionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149111", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRedeployWithSrcNotAllowedForSameVersion(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149112", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149112";
   }

   public static Loggable logRedeployWithSrcNotAllowedForSameVersionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149112", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMaxAppVersionsExceeded(String arg0, String arg1, int arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("149113", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149113";
   }

   public static Loggable logMaxAppVersionsExceededLoggable(String arg0, String arg1, int arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("149113", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logVersionIdLengthExceeded(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149114", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149114";
   }

   public static Loggable logVersionIdLengthExceededLoggable(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149114", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidVersionId(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149115", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149115";
   }

   public static Loggable logInvalidVersionIdLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149115", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToObtainConfig() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("149116", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149116";
   }

   public static Loggable logFailedToObtainConfigLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149116", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLibraryDescription(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149117", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149117";
   }

   public static Loggable logLibraryDescriptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149117", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppNotTargeted(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149118", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149118";
   }

   public static Loggable logAppNotTargetedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149118", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTargetsForAppVersion(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("149119", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149119";
   }

   public static Loggable logInvalidTargetsForAppVersionLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("149119", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPlanVersionNotAllowed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149120", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149120";
   }

   public static Loggable logPlanVersionNotAllowedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149120", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeploymentServiceNotStarted(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149121", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149121";
   }

   public static Loggable logDeploymentServiceNotStartedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149121", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoOpOnInternalApp(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149122", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149122";
   }

   public static Loggable logNoOpOnInternalAppLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149122", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDiffSecurityModelIgnoredForRedeploy(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149123", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149123";
   }

   public static String logTaskFailedWithError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149124", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149124";
   }

   public static String logRemoveRetiredAppVersion(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149125", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149125";
   }

   public static String logRetiringAppVersionNotRemoved(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149126", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149126";
   }

   public static String logTaskFailedNoAppWithError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149127", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149127";
   }

   public static String logRemoveAllRetiredAppVersion(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("149128", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149128";
   }

   public static String logRemoveAllRetiringAppVersion(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("149129", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149129";
   }

   public static String logRemoveAllActiveAppVersion(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("149130", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149130";
   }

   public static String logNonActiveApp(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149131", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149131";
   }

   public static Loggable logNonActiveAppLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149131", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoReTargetOnSplitDirApp(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149132", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149132";
   }

   public static Loggable logNoReTargetOnSplitDirAppLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149132", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoReTargetOnAutoDeployedApp(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149133", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149133";
   }

   public static Loggable logNoReTargetOnAutoDeployedAppLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149133", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartialRedeployOfVersionedArchive(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149134", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149134";
   }

   public static Loggable logPartialRedeployOfVersionedArchiveLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149134", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTargetForOperation(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149135", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149135";
   }

   public static Loggable logInvalidTargetForOperationLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149135", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String invalidDelta(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149137", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable invalidDeltaLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149137", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTargetSubset(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149138", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149138";
   }

   public static Loggable logInvalidTargetSubsetLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149138", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidIndividualTarget(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("149139", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149139";
   }

   public static Loggable logInvalidIndividualTargetLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("149139", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String pendingActivation() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149140", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable pendingActivationLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149140", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String appAlreadyExists(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149141", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String emptyCluster(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149142", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logAppSubModuleTargetErr() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149143", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String serverUnreachable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149145", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String altURLFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149146", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String adminUnreachable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149147", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String errorReceivingMessage() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149148", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String malformedURL(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149149", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String errorReadingInput() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149150", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String receiverNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149151", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String noDeploymentRequest() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149152", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String invalidPrepare(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149153", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String invalidHandleResponse(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149154", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String duplicateHandleResponse(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149155", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String illegalStateForStart(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149156", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String nothingToDoForTask(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149157", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String noAppFilesExist(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149158", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String nullStagingDirectory() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149159", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String nullName() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149160", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String operationRequiresTarget() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149161", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String operationRequiresPlan() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149162", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String nonExclusiveModeLock() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149163", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String exclusiveModeLock() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149164", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String invalidTarget() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149165", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String configLocked() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149166", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String nothingToCommit(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149167", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String requestCompletedOrCancelled(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149168", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String requiresRestart() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149169", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String mixedTargetError() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149170", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logTaskDeferred(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149171", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149171";
   }

   public static String logNonDynamicPropertyChange(long arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("149172", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149172";
   }

   public static String logPartialClusterTarget(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("149178", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149178";
   }

   public static String logErrorOnAbortEditSession(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149181", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149181";
   }

   public static Loggable logErrorOnAbortEditSessionLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149181", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String invalidAltDDDuringRedeploy() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149182", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String invalidRetireTimeout(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149183", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String invalidRedeployOnAutodeployedApp(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149184", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable invalidRedeployOnAutodeployedAppLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149184", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String invalidUndeployOnAutodeployedApp(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149185", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable invalidUndeployOnAutodeployedAppLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149185", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String invalidDistributeOnAutodeployedApp(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149186", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable invalidDistributeOnAutodeployedAppLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149186", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logApplicationUpgradeProblem(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149187", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149187";
   }

   public static String logNullAppNonDynamic(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149188", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149188";
   }

   public static Loggable logNullAppNonDynamicLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149188", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullDeploymentMBean(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149189", 16, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149189";
   }

   public static Loggable logNullDeploymentMBeanLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149189", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String expiredLockPendingChanges() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149190", 16, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logInitStatus(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149191", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable logInitStatusLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149191", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logProgressStatus(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149192", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable logProgressStatusLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149192", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedStatus(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149193", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable logFailedStatusLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149193", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSuccessStatus(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149194", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable logSuccessStatusLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149194", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnavailableStatus(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149195", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable logUnavailableStatusLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149195", 64, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTaskConflict(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149196", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149196";
   }

   public static Loggable logTaskConflictLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149196", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidAppState(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("149197", 8, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149197";
   }

   public static Loggable logInvalidAppStateLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("149197", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotRenameDirectory(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149198", 64, args, DeployerRuntimeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149198";
   }

   public static String logDisallowDDSecurityPermissions(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149199", 8, args, "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeployerRuntimeLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.deploy.internal.DeployerRuntimeLogLocalizer", DeployerRuntimeLogger.class.getClassLoader());
      private MessageLogger messageLogger = DeployerRuntimeLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = DeployerRuntimeLogger.findMessageLogger();
      }
   }
}
