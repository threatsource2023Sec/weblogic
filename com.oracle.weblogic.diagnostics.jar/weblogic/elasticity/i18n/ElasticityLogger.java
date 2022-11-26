package weblogic.elasticity.i18n;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class ElasticityLogger {
   private static final String LOCALIZER_CLASS = "weblogic.elasticity.l10n.ElasticityLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(ElasticityLogger.class.getName());
   }

   public static String logClusterRunningAtMaxSize(String arg0, String arg1, int arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2162700", 16, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162700";
   }

   public static String logClusterHasSufficientAvailableServers(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162701", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162701";
   }

   public static String logProposedSizeExceedsLimit(String arg0, String arg1, int arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2162702", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162702";
   }

   public static String logAdditionalServersNeeded(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162703", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162703";
   }

   public static String logClusterRunningAtMinSize(String arg0, String arg1, int arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2162704", 16, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162704";
   }

   public static String logClusterScalingDown(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162705", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162705";
   }

   public static String logClusterPartiallyScalingDown(String arg0, String arg1, int arg2, int arg3, int arg4, int arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("2162706", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162706";
   }

   public static String logLCMEndPointNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162707", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162707";
   }

   public static String logScaleUpRequestAdjusted(String arg0, String arg1, int arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2162708", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162708";
   }

   public static String logScaleUpServerStartFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162709", 8, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162709";
   }

   public static String logScaleUpServerStarted(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162710", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162710";
   }

   public static String logScaleUpWorkCompleted(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162711", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162711";
   }

   public static String logErrorOperationStatusNull(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162712", 8, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162712";
   }

   public static String logMaximumDynamicClusterSizeReached(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162713", 16, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162713";
   }

   public static String logUpdatingMaxClusterSize(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162714", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162714";
   }

   public static String logErrorDuringForceShutdownEvent(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162715", 8, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162715";
   }

   public static String logServerLifecycleRuntimeNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162716", 8, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162716";
   }

   public static String logScaleDownTaskServerShutdownComplete(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162717", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162717";
   }

   public static String logErrorDuringScaleupEvent(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162718", 8, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162718";
   }

   public static String logUpdateMaxServerCountInProgress(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162719", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162719";
   }

   public static String logScaleDownServerShutdownFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162720", 8, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162720";
   }

   public static String logScaleDownWorkCompleted(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162721", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162721";
   }

   public static String logErrorScaleDownOperationStatusNull(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162722", 8, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162722";
   }

   public static String logScaleDownFailedSoTryingForceShutdown(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162723", 16, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162723";
   }

   public static String executingScaleUpPreProcessorScript(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2162724", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162724";
   }

   public static String executingScaleDownPreProcessorScript(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2162725", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162725";
   }

   public static String executingScaleUpPostProcessorScript(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2162726", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162726";
   }

   public static String executingScaleDownPostProcessorScript(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2162727", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162727";
   }

   public static String reportPreProcessorScaleUpScriptCompletionStatus(String arg0, String arg1, String arg2, String arg3, int arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("2162728", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162728";
   }

   public static String reportPreProcessorScaleDownScriptCompletionStatus(String arg0, String arg1, String arg2, String arg3, int arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("2162729", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162729";
   }

   public static String reportPostProcessorScaleUpScriptCompletionStatus(String arg0, String arg1, String arg2, String arg3, int arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("2162730", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162730";
   }

   public static String reportPostProcessorScaleDownScriptCompletionStatus(String arg0, String arg1, String arg2, String arg3, int arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("2162731", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162731";
   }

   public static String reportScriptInterceptorAddingPreProcessorScriptForScaleUp(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162732", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162732";
   }

   public static String reportScriptInterceptorAddingPreProcessorScriptForScaleDown(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162733", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162733";
   }

   public static String reportScriptInterceptorAddingPostProcessorScriptForScaleUp(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162734", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162734";
   }

   public static String reportScriptInterceptorAddingPostProcessorScriptForScaleDown(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162735", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162735";
   }

   public static String logScriptInterceptorTimeout(String arg0, String arg1, String arg2, String arg3, int arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("2162736", 8, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162736";
   }

   public static String logScriptInterceptorFailed(String arg0, String arg1, String arg2, String arg3, int arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("2162737", 8, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162737";
   }

   public static Loggable logScriptInterceptorFailedLoggable(String arg0, String arg1, String arg2, String arg3, int arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("2162737", 8, args, "weblogic.elasticity.l10n.ElasticityLogLocalizer", ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ElasticityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDuringCancellationOfScaleUpOperation(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2162738", 8, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162738";
   }

   public static String logErrorDuringCancellationOfScaleDownOperation(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2162739", 8, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162739";
   }

   public static String logNodemanagerForServerNotAvailable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162740", 8, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162740";
   }

   public static String logAbortScriptExceution(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162741", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162741";
   }

   public static String logFailedOperationWithLCM(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2162742", 8, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162742";
   }

   public static String logErrorDuringScaledownEvent(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162743", 8, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162743";
   }

   public static String logErrorUpdateClusterSize(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162744", 16, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162744";
   }

   public static String logConcurrentScalingActivity(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162745", 8, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162745";
   }

   public static String logWaitForCoolingOffPeriod(String arg0, String arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162746", 8, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162746";
   }

   public static String logScaleUpOperationStarted(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162747", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162747";
   }

   public static String logScaleDownOperationStarted(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162748", 64, args, ElasticityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ElasticityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162748";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.elasticity.l10n.ElasticityLogLocalizer", ElasticityLogger.class.getClassLoader());
      private MessageLogger messageLogger = ElasticityLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = ElasticityLogger.findMessageLogger();
      }
   }
}
