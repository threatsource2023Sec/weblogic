package weblogic.management.deploy.internal;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class DeploymentManagerLogger {
   private static final String LOCALIZER_CLASS = "weblogic.i18ntools.DeploymentManagerLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(DeploymentManagerLogger.class.getName());
   }

   public static String logResumeFailure() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("149601", 8, args, DeploymentManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149601";
   }

   public static Loggable logResumeFailureLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149601", 8, args, "weblogic.i18ntools.DeploymentManagerLogLocalizer", DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logShutdownFailure() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("149603", 8, args, DeploymentManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149603";
   }

   public static Loggable logShutdownFailureLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149603", 8, args, "weblogic.i18ntools.DeploymentManagerLogLocalizer", DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConversionToAppMBeanFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149605", 8, args, DeploymentManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149605";
   }

   public static String logConfigureAppMBeanFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149606", 8, args, DeploymentManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149606";
   }

   public static Loggable logConfigureAppMBeanFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149606", 8, args, "weblogic.i18ntools.DeploymentManagerLogLocalizer", DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStatePersistenceFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149607", 8, args, DeploymentManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149607";
   }

   public static String logUnknownDeployable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149608", 8, args, "weblogic.i18ntools.DeploymentManagerLogLocalizer", DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logMBeanUnavailable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149609", 8, args, "weblogic.i18ntools.DeploymentManagerLogLocalizer", DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String storeCreateFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149610", 8, args, "weblogic.i18ntools.DeploymentManagerLogLocalizer", DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String cannotReadStore(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149611", 8, args, "weblogic.i18ntools.DeploymentManagerLogLocalizer", DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String cannotSaveStore(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149612", 8, args, "weblogic.i18ntools.DeploymentManagerLogLocalizer", DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String cannotDeleteStore(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149613", 8, args, "weblogic.i18ntools.DeploymentManagerLogLocalizer", DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logDisconnectListenerError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149614", 8, args, DeploymentManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149614";
   }

   public static String unrecognizedType(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149615", 8, args, "weblogic.i18ntools.DeploymentManagerLogLocalizer", DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logCriticalInternalAppNotDeployed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149616", 8, args, "weblogic.i18ntools.DeploymentManagerLogLocalizer", DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logNonCriticalInternalAppNotDeployed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149617", 16, args, DeploymentManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149617";
   }

   public static String logDeployFailedForInternalApp(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149618", 4, args, DeploymentManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149618";
   }

   public static String logDeleteFileFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149619", 8, args, DeploymentManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149619";
   }

   public static String logFailureOnConfigRecovery(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149621", 8, args, DeploymentManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149621";
   }

   public static Loggable logFailureOnConfigRecoveryLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149621", 8, args, "weblogic.i18ntools.DeploymentManagerLogLocalizer", DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotGetFileLock(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149622", 8, args, DeploymentManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149622";
   }

   public static String logInitFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149623", 1, args, DeploymentManagerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149623";
   }

   public static Loggable logInitFailedLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149623", 1, args, "weblogic.i18ntools.DeploymentManagerLogLocalizer", DeploymentManagerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DeploymentManagerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.i18ntools.DeploymentManagerLogLocalizer", DeploymentManagerLogger.class.getClassLoader());
      private MessageLogger messageLogger = DeploymentManagerLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = DeploymentManagerLogger.findMessageLogger();
      }
   }
}
