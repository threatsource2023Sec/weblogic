package weblogic.ejb.container;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class EJBLogger {
   private static final String LOCALIZER_CLASS = "weblogic.ejb.container.EJBLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(EJBLogger.class.getName());
   }

   public static String logExceptionDuringEJBActivate(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010000", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010000";
   }

   public static Loggable logExceptionDuringEJBActivateLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010000", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRedeployClasspathFailure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010001", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010001";
   }

   public static Loggable logRedeployClasspathFailureLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010001", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorUndeploying(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010002", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010002";
   }

   public static Loggable logErrorUndeployingLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010002", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionLoadingTimestamp(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010003", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010003";
   }

   public static Loggable logExceptionLoadingTimestampLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010003", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorSavingTimestamps(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010006", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010006";
   }

   public static Loggable logErrorSavingTimestampsLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010006", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLicenseValidation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010007", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010007";
   }

   public static Loggable logLicenseValidationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010007", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeploying(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010008", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010008";
   }

   public static Loggable logDeployingLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010008", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeployedWithJNDIName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010009", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010009";
   }

   public static Loggable logDeployedWithJNDINameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010009", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepLookingUpXn2(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010011", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010011";
   }

   public static Loggable logExcepLookingUpXn2Loggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010011", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepLookingUpXn3(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010012", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010012";
   }

   public static Loggable logExcepLookingUpXn3Loggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010012", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorOnRollback(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010014", 32, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010014";
   }

   public static Loggable logErrorOnRollbackLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010014", 32, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorMarkingRollback(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010015", 32, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010015";
   }

   public static Loggable logErrorMarkingRollbackLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010015", 32, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorMarkingForRollback(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010016", 32, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010016";
   }

   public static Loggable logErrorMarkingForRollbackLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010016", 32, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDuringRollback(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010017", 32, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010017";
   }

   public static Loggable logErrorDuringRollbackLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010017", 32, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorResumingTx(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010018", 32, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010018";
   }

   public static Loggable logErrorResumingTxLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010018", 32, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoClusterName() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010019", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010019";
   }

   public static Loggable logNoClusterNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010019", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorStartingJMSConnection(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010020", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010020";
   }

   public static Loggable logErrorStartingJMSConnectionLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010020", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logClustersNotHomogeneous(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010021", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010021";
   }

   public static Loggable logClustersNotHomogeneousLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010021", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorPassivating(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010022", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010022";
   }

   public static Loggable logErrorPassivatingLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010022", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDuringPassivation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010024", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010024";
   }

   public static Loggable logErrorDuringPassivationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010024", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDuringRollback1(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010025", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010025";
   }

   public static Loggable logErrorDuringRollback1Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010025", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDuringCommit(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010026", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010026";
   }

   public static Loggable logErrorDuringCommitLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010026", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDuringCommit2(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010029", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010029";
   }

   public static Loggable logErrorDuringCommit2Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010029", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIgnoreExcepOnRollback(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010030", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010030";
   }

   public static Loggable logIgnoreExcepOnRollbackLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010030", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepInMethod(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010031", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010031";
   }

   public static Loggable logExcepInMethodLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010031", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDuringActivate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010032", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010032";
   }

   public static Loggable logErrorDuringActivateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010032", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorFromLoad(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010033", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010033";
   }

   public static Loggable logErrorFromLoadLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010033", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorFromStore(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010034", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010034";
   }

   public static Loggable logErrorFromStoreLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010034", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepFromStore(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010036", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010036";
   }

   public static Loggable logExcepFromStoreLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010036", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepFromSuperLoad(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010038", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010038";
   }

   public static Loggable logExcepFromSuperLoadLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010038", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepInStore(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010039", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010039";
   }

   public static Loggable logExcepInStoreLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010039", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepInStore1(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010040", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010040";
   }

   public static Loggable logExcepInStore1Loggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010040", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepInActivate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010043", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010043";
   }

   public static Loggable logExcepInActivateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010043", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepResumingTx(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010044", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010044";
   }

   public static Loggable logExcepResumingTxLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010044", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepInAfterBegin(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010045", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010045";
   }

   public static Loggable logExcepInAfterBeginLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010045", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepInBeforeCompletion(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010046", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010046";
   }

   public static Loggable logExcepInBeforeCompletionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010046", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepDuringSetRollbackOnly(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010047", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010047";
   }

   public static Loggable logExcepDuringSetRollbackOnlyLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010047", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepInAfterCompletion(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010048", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010048";
   }

   public static Loggable logExcepInAfterCompletionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010048", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepInMethod1(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010049", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010049";
   }

   public static Loggable logExcepInMethod1Loggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010049", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMustCommit() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010050", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010050";
   }

   public static Loggable logMustCommitLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010050", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepDuringInvocFromHome(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010051", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010051";
   }

   public static Loggable logExcepDuringInvocFromHomeLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010051", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCreatingFreepool(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010052", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010052";
   }

   public static Loggable logErrorCreatingFreepoolLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010052", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJBClassFoundInClasspath(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010054", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010054";
   }

   public static Loggable logEJBClassFoundInClasspathLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010054", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotFindSpecifiedRDBMSDescriptorInJarFile(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010055", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010055";
   }

   public static Loggable logCouldNotFindSpecifiedRDBMSDescriptorInJarFileLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010055", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServiceNotInitialized() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010057", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010057";
   }

   public static Loggable logServiceNotInitializedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010057", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJBBeingRecompiledOnServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010058", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010058";
   }

   public static Loggable logEJBBeingRecompiledOnServerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010058", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionDuringROInvalidation(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010059", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010059";
   }

   public static Loggable logExceptionDuringROInvalidationLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010059", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBReConnectedToJMS(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010060", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010060";
   }

   public static Loggable logMDBReConnectedToJMSLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010060", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBUnableToConnectToJMS(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010061", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010061";
   }

   public static Loggable logMDBUnableToConnectToJMSLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010061", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepInOnMessageCallOnMDB(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010065", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010065";
   }

   public static Loggable logExcepInOnMessageCallOnMDBLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010065", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionDuringEJBUnsetEntityContext(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010066", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010066";
   }

   public static Loggable logExceptionDuringEJBUnsetEntityContextLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010066", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionDuringEJBRemove(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010067", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010067";
   }

   public static Loggable logExceptionDuringEJBRemoveLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010067", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorPoppingCallerPrincipal(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010071", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010071";
   }

   public static Loggable logErrorPoppingCallerPrincipalLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010071", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionDuringEJBModuleStart(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010072", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010072";
   }

   public static Loggable logExceptionDuringEJBModuleStartLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010072", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToAddToClientJarDueToClasspath(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010073", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010073";
   }

   public static Loggable logUnableToAddToClientJarDueToClasspathLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010073", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToCreateClientJarDueToClasspathIssues() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010074", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010074";
   }

   public static Loggable logUnableToCreateClientJarDueToClasspathIssuesLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010074", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSkippingClientJarCreationSinceNoRemoteEJBsFound() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010075", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010075";
   }

   public static Loggable logSkippingClientJarCreationSinceNoRemoteEJBsFoundLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010075", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logClientJarCreated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010076", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010076";
   }

   public static Loggable logClientJarCreatedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010076", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSExceptionReceivingForMDB(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010079", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010079";
   }

   public static Loggable logJMSExceptionReceivingForMDBLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010079", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSExceptionProcessingMDB(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010080", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010080";
   }

   public static Loggable logJMSExceptionProcessingMDBLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010080", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUsingSingleThreadForMDBTopic(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010081", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010081";
   }

   public static Loggable logUsingSingleThreadForMDBTopicLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010081", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToUndeploySecurityRole(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010082", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010082";
   }

   public static Loggable logFailedToUndeploySecurityRoleLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010082", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToUndeploySecurityPolicy(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010083", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010083";
   }

   public static Loggable logFailedToUndeploySecurityPolicyLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010083", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBsBeingSuspended() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010084", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010084";
   }

   public static Loggable logMDBsBeingSuspendedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010084", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBsDoneSuspending() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010085", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010085";
   }

   public static Loggable logMDBsDoneSuspendingLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010085", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorOnStartMDBs(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010086", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010086";
   }

   public static Loggable logErrorOnStartMDBsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010086", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJBBeingRecompiledOnServerKeepgenerated(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010087", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010087";
   }

   public static Loggable logEJBBeingRecompiledOnServerKeepgeneratedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010087", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoInMemoryReplicationLicense() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010088", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010088";
   }

   public static Loggable logNoInMemoryReplicationLicenseLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010088", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToCreateCopy(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010089", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010089";
   }

   public static Loggable logFailedToCreateCopyLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010089", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToUpdateSecondaryCopy(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010090", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010090";
   }

   public static Loggable logFailedToUpdateSecondaryCopyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010090", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailureInReplication(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010091", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010091";
   }

   public static Loggable logFailureInReplicationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010091", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToUpdateSecondaryDuringReplication(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010092", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010092";
   }

   public static Loggable logFailedToUpdateSecondaryDuringReplicationLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010092", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToUpdateSecondary(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010094", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010094";
   }

   public static Loggable logFailedToUpdateSecondaryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010094", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBReconnectInfo(String arg0, String arg1, int arg2, int arg3, long arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("010096", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010096";
   }

   public static Loggable logMDBReconnectInfoLoggable(String arg0, String arg1, int arg2, int arg3, long arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("010096", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDuringBeanInvocation(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010097", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010097";
   }

   public static Loggable logErrorDuringBeanInvocationLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010097", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorAboutDatabaseType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010098", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010098";
   }

   public static Loggable logErrorAboutDatabaseTypeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010098", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningFromEJBQLCompiler(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010099", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010099";
   }

   public static Loggable logWarningFromEJBQLCompilerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010099", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningOnSFSBInMemoryReplicationFeature(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010100", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010100";
   }

   public static Loggable logWarningOnSFSBInMemoryReplicationFeatureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010100", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMismatchBetweenBeanAndGeneratedCode(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010101", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010101";
   }

   public static Loggable logMismatchBetweenBeanAndGeneratedCodeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010101", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logerrorCreatingDefaultDBMSTable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010102", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010102";
   }

   public static Loggable logerrorCreatingDefaultDBMSTableLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010102", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logduplicateRelationshipRoleName(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010105", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010105";
   }

   public static Loggable logduplicateRelationshipRoleNameLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010105", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logunlockCouldNotFindPk(String arg0, Object arg1, Object arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010108", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010108";
   }

   public static Loggable logunlockCouldNotFindPkLoggable(String arg0, Object arg1, Object arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010108", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logduplicateEJBName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010110", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010110";
   }

   public static Loggable logduplicateEJBNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010110", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logproviderIsNotTransactedButMDBIsTransacted(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010112", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010112";
   }

   public static Loggable logproviderIsNotTransactedButMDBIsTransactedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010112", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logrelationshipCachingCannotEnableIfSelectTypeIsNotObject(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010113", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010113";
   }

   public static Loggable logrelationshipCachingCannotEnableIfSelectTypeIsNotObjectLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010113", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logdynamicQueriesNotEnabled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010114", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010114";
   }

   public static Loggable logdynamicQueriesNotEnabledLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010114", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String loginvalidResultTypeMapping(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010115", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010115";
   }

   public static Loggable loginvalidResultTypeMappingLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010115", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logaccessedCmrCollectionInDifferentTransaction(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010117", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010117";
   }

   public static Loggable logaccessedCmrCollectionInDifferentTransactionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010117", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logmissingEnterpriseBeanMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010122", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010122";
   }

   public static Loggable logmissingEnterpriseBeanMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010122", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logentityMBeanWrongVersion(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010123", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010123";
   }

   public static Loggable logentityMBeanWrongVersionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010123", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logmissingCallerPrincipal(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010124", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010124";
   }

   public static Loggable logmissingCallerPrincipalLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010124", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String lognonResultSetFinderHasIntegerOrderByOrGroupByArg(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010125", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010125";
   }

   public static Loggable lognonResultSetFinderHasIntegerOrderByOrGroupByArgLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010125", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logintegerOrderByOrGroupByArgExceedsSelectListSize(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010126", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010126";
   }

   public static Loggable logintegerOrderByOrGroupByArgExceedsSelectListSizeLoggable(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010126", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logcmpBeanMustHaveTXDataSourceSpecified(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010127", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010127";
   }

   public static Loggable logcmpBeanMustHaveTXDataSourceSpecifiedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010127", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logmissingCacheDefinition(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010128", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010128";
   }

   public static Loggable logmissingCacheDefinitionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010128", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String lognotAnExclusiveCache(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010129", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010129";
   }

   public static Loggable lognotAnExclusiveCacheLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010129", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String lognotAMultiVersionCache(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010130", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010130";
   }

   public static Loggable lognotAMultiVersionCacheLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010130", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logselectForUpdateNotSupported(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010132", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010132";
   }

   public static Loggable logselectForUpdateNotSupportedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010132", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logillegalAttemptToAssignRemovedBeanToCMRField(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010133", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010133";
   }

   public static Loggable logillegalAttemptToAssignRemovedBeanToCMRFieldLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010133", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logparamInteger() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010136", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010136";
   }

   public static Loggable logparamIntegerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010136", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logparamPositiveInteger() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010137", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010137";
   }

   public static Loggable logparamPositiveIntegerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010137", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String loginvalidFieldGroupName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010138", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010138";
   }

   public static Loggable loginvalidFieldGroupNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010138", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logduplicateKeyFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010139", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010139";
   }

   public static Loggable logduplicateKeyFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010139", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String lognoSuchEntityException(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010140", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010140";
   }

   public static Loggable lognoSuchEntityExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010140", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logerrorInsertingInJoinTable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("010141", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010141";
   }

   public static Loggable logerrorInsertingInJoinTableLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("010141", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logbeanDoesNotExist(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010142", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010142";
   }

   public static Loggable logbeanDoesNotExistLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010142", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logoptimisticUpdateFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010143", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010143";
   }

   public static Loggable logoptimisticUpdateFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010143", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logcannotCallSetOnPk() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010144", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010144";
   }

   public static Loggable logcannotCallSetOnPkLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010144", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logcannotCallSetOnCmpCmrField() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010145", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010145";
   }

   public static Loggable logcannotCallSetOnCmpCmrFieldLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010145", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logsetCheckForCmrFieldAsPk() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010146", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010146";
   }

   public static Loggable logsetCheckForCmrFieldAsPkLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010146", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logsetCheckForCmrFieldDuringEjbCreate() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010147", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010147";
   }

   public static Loggable logsetCheckForCmrFieldDuringEjbCreateLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010147", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logpkNotSet(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010148", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010148";
   }

   public static Loggable logpkNotSetLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010148", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String lognullAssignedToCmrField() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010149", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010149";
   }

   public static Loggable lognullAssignedToCmrFieldLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010149", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logillegalConcurrencyStrategy(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010152", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010152";
   }

   public static Loggable logillegalConcurrencyStrategyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010152", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logonlyRemoteCanInvokeGetEJBObject() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010153", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010153";
   }

   public static Loggable logonlyRemoteCanInvokeGetEJBObjectLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010153", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logonlyLocalCanInvokeGetEJBObject() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010154", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010154";
   }

   public static Loggable logonlyLocalCanInvokeGetEJBObjectLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010154", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logonlyCMTBeanCanInvokeGetRollbackOnly() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010155", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010155";
   }

   public static Loggable logonlyCMTBeanCanInvokeGetRollbackOnlyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010155", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logillegalCallToGetRollbackOnly() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010156", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010156";
   }

   public static Loggable logillegalCallToGetRollbackOnlyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010156", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logonlyCMTBeanCanInvokeSetRollbackOnly() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010157", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010157";
   }

   public static Loggable logonlyCMTBeanCanInvokeSetRollbackOnlyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010157", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logillegalCallToSetRollbackOnly() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010158", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010158";
   }

   public static Loggable logillegalCallToSetRollbackOnlyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010158", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logneedJNDINameForHomeHandles(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010159", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010159";
   }

   public static Loggable logneedJNDINameForHomeHandlesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010159", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logaccessDeniedOnEJBResource(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010160", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010160";
   }

   public static Loggable logaccessDeniedOnEJBResourceLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010160", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String loghandleNull() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010161", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010161";
   }

   public static Loggable loghandleNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010161", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbObjectNull() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010162", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010162";
   }

   public static Loggable logejbObjectNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010162", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String loghomeWasNull() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010163", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010163";
   }

   public static Loggable loghomeWasNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010163", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbObjectNotFromThisHome() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010164", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010164";
   }

   public static Loggable logejbObjectNotFromThisHomeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010164", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logillegalAttemptToInvokeGetPrimaryKeyClass() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010165", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010165";
   }

   public static Loggable logillegalAttemptToInvokeGetPrimaryKeyClassLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010165", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logerrorStartingMDBTx(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010166", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010166";
   }

   public static Loggable logerrorStartingMDBTxLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010166", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logmdbCannotInvokeThisMethod(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010167", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010167";
   }

   public static Loggable logmdbCannotInvokeThisMethodLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010167", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logaccessException(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010168", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010168";
   }

   public static Loggable logaccessExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010168", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String loginsufficientPermission(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010169", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010169";
   }

   public static Loggable loginsufficientPermissionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010169", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logsessionBeanCannotCallGetPrimaryKey() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010171", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010171";
   }

   public static Loggable logsessionBeanCannotCallGetPrimaryKeyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010171", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String loginvalidRemoveCall() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010172", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010172";
   }

   public static Loggable loginvalidRemoveCallLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010172", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logcharEnvEntryHasLengthZero() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010173", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010173";
   }

   public static Loggable logcharEnvEntryHasLengthZeroLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010173", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String lognoJNDIForResourceRef(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010174", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010174";
   }

   public static Loggable lognoJNDIForResourceRefLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010174", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String lognoJNDIForResourceEnvRef(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010176", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010176";
   }

   public static Loggable lognoJNDIForResourceEnvRefLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010176", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logunableToCreateJMSConnectionFactory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010177", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010177";
   }

   public static Loggable logunableToCreateJMSConnectionFactoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010177", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String loglocalSessionBeanCannotCallGetPrimaryKey() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010179", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010179";
   }

   public static Loggable loglocalSessionBeanCannotCallGetPrimaryKeyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010179", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logillegalReentrantCall(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010180", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010180";
   }

   public static Loggable logillegalReentrantCallLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010180", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfindByPkReturnedNull(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010181", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010181";
   }

   public static Loggable logfindByPkReturnedNullLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010181", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderReturnedNull(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010182", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010182";
   }

   public static Loggable logfinderReturnedNullLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010182", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEnvironmentDeprecated() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010183", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010183";
   }

   public static Loggable logEnvironmentDeprecatedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010183", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String loginvalidMethodSignature(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010184", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010184";
   }

   public static Loggable loginvalidMethodSignatureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010184", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logbeanNotCreated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010185", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010185";
   }

   public static Loggable logbeanNotCreatedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010185", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logbeanNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010186", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010186";
   }

   public static Loggable logbeanNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010186", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDroppingDefaultDBMSTable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010188", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010188";
   }

   public static Loggable logErrorDroppingDefaultDBMSTableLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010188", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorAlteringDefaultDBMSTable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010189", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010189";
   }

   public static Loggable logErrorAlteringDefaultDBMSTableLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010189", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logcannotSpecifyBlobClobInOrderby(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010190", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010190";
   }

   public static Loggable logcannotSpecifyBlobClobInOrderbyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010190", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logalterTableNotSupported(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010191", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010191";
   }

   public static Loggable logalterTableNotSupportedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010191", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logillegalCallToEJBContextMethod(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010193", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010193";
   }

   public static Loggable logillegalCallToEJBContextMethodLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010193", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logbmtCanUseUserTransaction() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010194", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010194";
   }

   public static Loggable logbmtCanUseUserTransactionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010194", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String loginsufficientPermissionToUser(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010195", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010195";
   }

   public static Loggable loginsufficientPermissionToUserLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010195", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAnomalousRRBehaviorPossible(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010197", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010197";
   }

   public static Loggable logAnomalousRRBehaviorPossibleLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010197", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepionUninitializing(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010198", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010198";
   }

   public static Loggable logExcepionUninitializingLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010198", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRunAsPrincipalChosenFromSecurityRoleAssignment(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010199", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010199";
   }

   public static Loggable logRunAsPrincipalChosenFromSecurityRoleAssignmentLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010199", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionRecoveringJMSSession(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010201", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010201";
   }

   public static Loggable logExceptionRecoveringJMSSessionLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010201", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCallByReferenceNotEnabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010202", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010202";
   }

   public static Loggable logCallByReferenceNotEnabledLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010202", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJBModuleRolledBackToUpdateNonBeanClass(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010204", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010204";
   }

   public static Loggable logEJBModuleRolledBackToUpdateNonBeanClassLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010204", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJBModuleRolledBackSinceImplCLDisabled(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010205", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010205";
   }

   public static Loggable logEJBModuleRolledBackSinceImplCLDisabledLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010205", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJBModuleRolledBackSinceChangeIncompatible(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010206", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010206";
   }

   public static Loggable logEJBModuleRolledBackSinceChangeIncompatibleLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010206", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLicenseValidationError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010207", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010207";
   }

   public static Loggable logLicenseValidationErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010207", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDDLFileCreated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010208", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010208";
   }

   public static Loggable logDDLFileCreatedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010208", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToDeleteDDLFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010209", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010209";
   }

   public static Loggable logUnableToDeleteDDLFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010209", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToWriteToDDLFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010210", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010210";
   }

   public static Loggable logUnableToWriteToDDLFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010210", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logalterTableNotSupportedForPointbaseLoggable() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010211", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010211";
   }

   public static Loggable logalterTableNotSupportedForPointbaseLoggableLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010211", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJBUsesDefaultTXAttribute(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010212", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010212";
   }

   public static Loggable logEJBUsesDefaultTXAttributeLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010212", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTxRolledbackInfo(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010213", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010213";
   }

   public static Loggable logTxRolledbackInfoLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010213", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorStartingMDB(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010214", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010214";
   }

   public static Loggable logErrorStartingMDBLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010214", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBDurableSubscriptionDeletion(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010215", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010215";
   }

   public static Loggable logMDBDurableSubscriptionDeletionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010215", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBRedeliveryInfo(String arg0, int arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010216", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010216";
   }

   public static Loggable logMDBRedeliveryInfoLoggable(String arg0, int arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010216", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBUnableToConnectToJCA(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010221", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010221";
   }

   public static Loggable logMDBUnableToConnectToJCALoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010221", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoResultsForAggregateQuery(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010222", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010222";
   }

   public static Loggable logNoResultsForAggregateQueryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010222", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeployedMDB(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010223", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010223";
   }

   public static Loggable logDeployedMDBLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010223", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConfiguredEJBTimeoutDelayApplied(String arg0, int arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010224", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010224";
   }

   public static Loggable logConfiguredEJBTimeoutDelayAppliedLoggable(String arg0, int arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010224", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJBTimeoutDelayAutomaticallyApplied(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010225", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010225";
   }

   public static Loggable logEJBTimeoutDelayAutomaticallyAppliedLoggable(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010225", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logClusteredTimerFailedToLookupTimerHandler(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010226", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010226";
   }

   public static Loggable logClusteredTimerFailedToLookupTimerHandlerLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010226", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepDuringInvocFromHomeOrBusiness(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010227", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010227";
   }

   public static Loggable logExcepDuringInvocFromHomeOrBusinessLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010227", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToUpdateSecondaryFromBusiness(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010228", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010228";
   }

   public static Loggable logFailedToUpdateSecondaryFromBusinessLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010228", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logunableToInitializeInterfaceMethodInfo(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010229", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010229";
   }

   public static Loggable logunableToInitializeInterfaceMethodInfoLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010229", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMethodNameIsConflicteddUnderCaseInsensitiveComparison(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010230", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010230";
   }

   public static Loggable logMethodNameIsConflicteddUnderCaseInsensitiveComparisonLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010230", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String loglockRequestTimeOutNS(String arg0, Object arg1, Object arg2, long arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("010231", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010231";
   }

   public static Loggable loglockRequestTimeOutNSLoggable(String arg0, Object arg1, Object arg2, long arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("010231", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEnableBeanClassRedeployIsNotSupportedForEJB3(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010232", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010232";
   }

   public static Loggable logEnableBeanClassRedeployIsNotSupportedForEJB3Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010232", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMessagesMaximumIgnoredForNonWLJMS() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("010233", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010233";
   }

   public static Loggable logMessagesMaximumIgnoredForNonWLJMSLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("010233", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnbindFailureWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010234", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010234";
   }

   public static Loggable logUnbindFailureWarningLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010234", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBReConnectedToResourceAdapter(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010235", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010235";
   }

   public static Loggable logMDBReConnectedToResourceAdapterLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010235", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAttemptToSuspendNonConnectedMDB(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("010236", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010236";
   }

   public static Loggable logAttemptToSuspendNonConnectedMDBLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("010236", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUsingSingleThreadForNonXAMDBTopic(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010237", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010237";
   }

   public static Loggable logUsingSingleThreadForNonXAMDBTopicLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010237", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStartProcessAnnotaions(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("010238", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010238";
   }

   public static Loggable logStartProcessAnnotaionsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("010238", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJBContainerCreateBeanObject(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010239", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010239";
   }

   public static Loggable logEJBContainerCreateBeanObjectLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010239", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBSuspendedOnDeployment(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010240", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010240";
   }

   public static Loggable logMDBSuspendedOnDeploymentLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010240", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSuspendJCAMDBConnectionOnDeploymentNotSupported(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010241", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010241";
   }

   public static Loggable logSuspendJCAMDBConnectionOnDeploymentNotSupportedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010241", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInConnectWhileConnectionPollingCancelled(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("010242", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "010242";
   }

   public static Loggable logErrorInConnectWhileConnectionPollingCancelledLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("010242", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInstalledPersistFileNotExist(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011001", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011001";
   }

   public static Loggable logInstalledPersistFileNotExistLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011001", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInstalledPersistFileNotReadable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011002", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011002";
   }

   public static Loggable logInstalledPersistFileNotReadableLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011002", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInstalledPersistFileCouldNotOpen(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011003", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011003";
   }

   public static Loggable logInstalledPersistFileCouldNotOpenLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011003", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInstalledPersistErrorLoadingResource(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011004", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011004";
   }

   public static Loggable logInstalledPersistErrorLoadingResourceLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011004", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInstalledPersistNoXMLProcessor(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011005", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011005";
   }

   public static Loggable logInstalledPersistNoXMLProcessorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011005", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStatelessEOJNDIBindError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011006", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011006";
   }

   public static Loggable logStatelessEOJNDIBindErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011006", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPersistenceManagerSetupError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011007", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011007";
   }

   public static Loggable logPersistenceManagerSetupErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011007", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHomeJNDIRebindFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011008", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011008";
   }

   public static Loggable logHomeJNDIRebindFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011008", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedJNDIContextToJMSProvider() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011009", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011009";
   }

   public static Loggable logFailedJNDIContextToJMSProviderLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011009", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJmsDestinationNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011010", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011010";
   }

   public static Loggable logJmsDestinationNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011010", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJndiNameWasNotAJMSDestination(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011011", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011011";
   }

   public static Loggable logJndiNameWasNotAJMSDestinationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011011", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJmsConnectionFactoryNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011012", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011012";
   }

   public static Loggable logJmsConnectionFactoryNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011012", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJndiNameWasNotAJMSConnectionFactory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011013", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011013";
   }

   public static Loggable logJndiNameWasNotAJMSConnectionFactoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011013", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJmsExceptionWhileCreatingConnection() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011014", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011014";
   }

   public static Loggable logJmsExceptionWhileCreatingConnectionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011014", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoDestinationJNDINameSpecified() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011015", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011015";
   }

   public static Loggable logNoDestinationJNDINameSpecifiedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011015", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPersistenceTypeSetupError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011016", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011016";
   }

   public static Loggable logPersistenceTypeSetupErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011016", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPersistenceTypeSetupErrorWithFileName(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011017", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011017";
   }

   public static Loggable logPersistenceTypeSetupErrorWithFileNameLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011017", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRdbmsDescriptorNotFoundInJar(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011018", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011018";
   }

   public static Loggable logRdbmsDescriptorNotFoundInJarLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011018", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPersistenceTypeSetupErrorWithFileNameAndLineNumber(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("011019", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011019";
   }

   public static Loggable logPersistenceTypeSetupErrorWithFileNameAndLineNumberLoggable(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("011019", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCmpTableMissingColumns(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011020", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011020";
   }

   public static Loggable logCmpTableMissingColumnsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011020", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToCreateTempDir(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011022", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011022";
   }

   public static Loggable logUnableToCreateTempDirLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011022", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorReadingDD(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011023", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011023";
   }

   public static Loggable logErrorReadingDDLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011023", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logXmlParsingError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011024", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011024";
   }

   public static Loggable logXmlParsingErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011024", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logXmlProcessingError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011025", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011025";
   }

   public static Loggable logXmlProcessingErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011025", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailureWhileCreatingCompEnv() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011026", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011026";
   }

   public static Loggable logFailureWhileCreatingCompEnvLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011026", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToLoadJTSDriver() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011027", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011027";
   }

   public static Loggable logUnableToLoadJTSDriverLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011027", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDataSourceNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011028", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011028";
   }

   public static Loggable logDataSourceNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011028", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotGetConnectionFromDataSource(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011029", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011029";
   }

   public static Loggable logCouldNotGetConnectionFromDataSourceLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011029", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotGetConnectionFrom(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011030", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011030";
   }

   public static Loggable logCouldNotGetConnectionFromLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011030", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotInitializeFieldSQLTypeMap(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011032", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011032";
   }

   public static Loggable logCouldNotInitializeFieldSQLTypeMapLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011032", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExecGenKeyError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011033", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011033";
   }

   public static Loggable logExecGenKeyErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011033", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullInvalidateParameter() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011043", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011043";
   }

   public static Loggable logNullInvalidateParameterLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011043", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorWhileMulticastingInvalidation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011044", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011044";
   }

   public static Loggable logErrorWhileMulticastingInvalidationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011044", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToCreateRuntimeMBean() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011046", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011046";
   }

   public static Loggable logFailedToCreateRuntimeMBeanLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011046", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logContainerTransactionSetForBeanManagedEJB(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011047", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011047";
   }

   public static Loggable logContainerTransactionSetForBeanManagedEJBLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011047", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPersistenceUsesFinderExpressions() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011048", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011048";
   }

   public static Loggable logPersistenceUsesFinderExpressionsLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011048", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToFindBeanInRDBMSDescriptor(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011049", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011049";
   }

   public static Loggable logUnableToFindBeanInRDBMSDescriptorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011049", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToFindBeanInRDBMSDescriptor1(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011050", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011050";
   }

   public static Loggable logUnableToFindBeanInRDBMSDescriptor1Loggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011050", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderCollectionIsNull(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011051", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011051";
   }

   public static Loggable logFinderCollectionIsNullLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011051", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCmp20DDHasWrongDocumentType() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011052", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011052";
   }

   public static Loggable logCmp20DDHasWrongDocumentTypeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011052", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCmpBeanDescriptorIsNull(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011053", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011053";
   }

   public static Loggable logCmpBeanDescriptorIsNullLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011053", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDuplicateBeanOrRelation(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011054", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011054";
   }

   public static Loggable logDuplicateBeanOrRelationLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011054", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJNDINameAlreadyInUse(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011055", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011055";
   }

   public static Loggable logJNDINameAlreadyInUseLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011055", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTableCreatedByUser(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011057", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011057";
   }

   public static Loggable logTableCreatedByUserLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011057", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToCreateDDLFile(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011059", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011059";
   }

   public static Loggable logUnableToCreateDDLFileLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011059", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotInitializeFieldSQLTypeMapWithoutException() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011060", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011060";
   }

   public static Loggable logCouldNotInitializeFieldSQLTypeMapWithoutExceptionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011060", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStackTraceAndMessage(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011061", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011061";
   }

   public static Loggable logStackTraceAndMessageLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011061", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStackTrace(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011062", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011062";
   }

   public static Loggable logStackTraceLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011062", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSLSBMethodDidNotCompleteTX(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011063", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011063";
   }

   public static Loggable logSLSBMethodDidNotCompleteTXLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011063", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningUnusedFieldGroups(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011070", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011070";
   }

   public static Loggable logWarningUnusedFieldGroupsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011070", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningUnusedRelationshipCachings(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011071", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011071";
   }

   public static Loggable logWarningUnusedRelationshipCachingsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011071", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLocalHomeJNDIRebindFailed(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011072", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011072";
   }

   public static Loggable logLocalHomeJNDIRebindFailedLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011072", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorGettingTableInformation(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("011073", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011073";
   }

   public static Loggable logErrorGettingTableInformationLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("011073", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotCreateReadOnlyBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011074", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011074";
   }

   public static Loggable logCannotCreateReadOnlyBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011074", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotRemoveReadOnlyBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011075", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011075";
   }

   public static Loggable logCannotRemoveReadOnlyBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011075", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeploymentFailedTableDoesNotExist(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011076", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011076";
   }

   public static Loggable logDeploymentFailedTableDoesNotExistLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011076", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTableCannotBeCreatedInProductionMode() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011077", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011077";
   }

   public static Loggable logTableCannotBeCreatedInProductionModeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011077", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorStartingFreepoolTimer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011078", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011078";
   }

   public static Loggable logErrorStartingFreepoolTimerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011078", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorStoppingFreepoolTimer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011079", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011079";
   }

   public static Loggable logErrorStoppingFreepoolTimerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011079", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorStartingCacheTimer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011080", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011080";
   }

   public static Loggable logErrorStartingCacheTimerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011080", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorStoppingCacheTimer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011081", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011081";
   }

   public static Loggable logErrorStoppingCacheTimerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011081", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToResolveMDBMessageDestinationLink(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("011082", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011082";
   }

   public static Loggable logUnableToResolveMDBMessageDestinationLinkLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("011082", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStatefulSessionBeanAttemptToAccessTimerService() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011083", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011083";
   }

   public static Loggable logStatefulSessionBeanAttemptToAccessTimerServiceLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011083", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalAttemptToAccessTimerService() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011084", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011084";
   }

   public static Loggable logIllegalAttemptToAccessTimerServiceLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011084", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalAttemptToUseCancelledTimer() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011085", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011085";
   }

   public static Loggable logIllegalAttemptToUseCancelledTimerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011085", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExpiredTimerHandle() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011086", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011086";
   }

   public static Loggable logExpiredTimerHandleLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011086", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTimerHandleInvokedOutsideOriginalAppContext() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011087", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011087";
   }

   public static Loggable logTimerHandleInvokedOutsideOriginalAppContextLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011087", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionInvokingEJBTimeout(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011088", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011088";
   }

   public static Loggable logExceptionInvokingEJBTimeoutLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011088", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorRemovingTimer(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011089", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011089";
   }

   public static Loggable logErrorRemovingTimerLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011089", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionBeforeInvokingEJBTimeout(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011090", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011090";
   }

   public static Loggable logExceptionBeforeInvokingEJBTimeoutLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011090", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTableUsesTriggerCannotBeDroppedOrCreated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011091", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011091";
   }

   public static Loggable logTableUsesTriggerCannotBeDroppedOrCreatedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011091", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSequenceCannotBeAlteredInProductionMode(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011092", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011092";
   }

   public static Loggable logSequenceCannotBeAlteredInProductionModeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011092", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSequenceNotExist(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011093", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011093";
   }

   public static Loggable logSequenceNotExistLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011093", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSequenceIncrementMismatch(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011094", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011094";
   }

   public static Loggable logSequenceIncrementMismatchLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011094", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToCreateSequence(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("011095", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011095";
   }

   public static Loggable logFailedToCreateSequenceLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("011095", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSequenceSetupFailure(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("011096", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011096";
   }

   public static Loggable logSequenceSetupFailureLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("011096", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToAlterSequence(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("011097", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011097";
   }

   public static Loggable logFailedToAlterSequenceLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("011097", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoGeneratedPKReturned() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011098", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011098";
   }

   public static Loggable logNoGeneratedPKReturnedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011098", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMultiplGeneratedKeysReturned() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011099", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011099";
   }

   public static Loggable logMultiplGeneratedKeysReturnedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011099", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGenKeySequenceTableSetupFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011100", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011100";
   }

   public static Loggable logGenKeySequenceTableSetupFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011100", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGenKeySequenceTableEmpty(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011101", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011101";
   }

   public static Loggable logGenKeySequenceTableEmptyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011101", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGenKeySequenceTableNewTxFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011102", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011102";
   }

   public static Loggable logGenKeySequenceTableNewTxFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011102", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGenKeySequenceTableUpdateFailure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011103", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011103";
   }

   public static Loggable logGenKeySequenceTableUpdateFailureLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011103", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGenKeySequenceTableLocalCommitFailure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011104", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011104";
   }

   public static Loggable logGenKeySequenceTableLocalCommitFailureLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011104", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGenKeySequenceTableTxResumeFailure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011105", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011105";
   }

   public static Loggable logGenKeySequenceTableTxResumeFailureLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011105", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotCallSetOnDBMSDefaultFieldBeforeInsert() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011106", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011106";
   }

   public static Loggable logCannotCallSetOnDBMSDefaultFieldBeforeInsertLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011106", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotInvokeTimerObjectsFromEjbCreate() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011107", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011107";
   }

   public static Loggable logCannotInvokeTimerObjectsFromEjbCreateLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011107", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotInvokeTimerObjectsFromAfterCompletion() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011108", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011108";
   }

   public static Loggable logCannotInvokeTimerObjectsFromAfterCompletionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011108", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToRegisterPolicyContextHandlers(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011109", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011109";
   }

   public static Loggable logFailedToRegisterPolicyContextHandlersLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011109", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningSequenceIncrementLesserThanDBIncrement(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011110", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011110";
   }

   public static Loggable logWarningSequenceIncrementLesserThanDBIncrementLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011110", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorSequenceIncrementGreaterThanDBIncrement(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011111", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011111";
   }

   public static Loggable logErrorSequenceIncrementGreaterThanDBIncrementLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011111", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToFindPersistentStore(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("011112", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011112";
   }

   public static Loggable logUnableToFindPersistentStoreLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("011112", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoMdbDestinationConfigured(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011113", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011113";
   }

   public static Loggable logNoMdbDestinationConfiguredLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011113", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoPlanOverridesWithDTDDescriptors(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011114", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011114";
   }

   public static Loggable logNoPlanOverridesWithDTDDescriptorsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011114", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotCallSetForReadOnlyBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011115", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011115";
   }

   public static Loggable logCannotCallSetForReadOnlyBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011115", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPersistenceTypeSetupEjbqlParsingError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011116", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011116";
   }

   public static Loggable logPersistenceTypeSetupEjbqlParsingErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011116", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAttemptToBumpUpPrivilegesWithRunAs(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011117", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011117";
   }

   public static Loggable logAttemptToBumpUpPrivilegesWithRunAsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011117", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRunAsPrincipalNotFound(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("011118", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011118";
   }

   public static Loggable logRunAsPrincipalNotFoundLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("011118", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logoptimisticColumnIsNull(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("011119", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011119";
   }

   public static Loggable logoptimisticColumnIsNullLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("011119", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBatchingTurnedOff(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011120", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011120";
   }

   public static Loggable logBatchingTurnedOffLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011120", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPluginClassNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011121", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011121";
   }

   public static Loggable logPluginClassNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011121", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPluginClassInstantiationError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011122", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011122";
   }

   public static Loggable logPluginClassInstantiationErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011122", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPluginClassIllegalAccess(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011123", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011123";
   }

   public static Loggable logPluginClassIllegalAccessLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011123", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPluginClassNotImplment(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011124", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011124";
   }

   public static Loggable logPluginClassNotImplmentLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011124", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorRemovingEJBTimersFromStore(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011125", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011125";
   }

   public static Loggable logErrorRemovingEJBTimersFromStoreLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011125", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSuppressingEJBTimeoutErrors(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011126", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011126";
   }

   public static Loggable logSuppressingEJBTimeoutErrorsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011126", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJBTimerSerializationError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011127", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011127";
   }

   public static Loggable logEJBTimerSerializationErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011127", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInstantiatingBeanInstance(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011128", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011128";
   }

   public static Loggable logErrorInstantiatingBeanInstanceLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011128", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorBindingBusinessInterfaceToJNDI(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("011132", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011132";
   }

   public static Loggable logErrorBindingBusinessInterfaceToJNDILoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("011132", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBusinessJNDIRebindFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011133", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011133";
   }

   public static Loggable logBusinessJNDIRebindFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011133", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLocalBusinessJNDIRebindFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011134", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011134";
   }

   public static Loggable logLocalBusinessJNDIRebindFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011134", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String log2xClientViewNotSupportedForSingletons(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011135", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011135";
   }

   public static Loggable log2xClientViewNotSupportedForSingletonsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011135", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidWasCancelCalledInvocation() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011136", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011136";
   }

   public static Loggable logInvalidWasCancelCalledInvocationLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011136", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSingletonBMTMustCommit() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011137", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011137";
   }

   public static Loggable logSingletonBMTMustCommitLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011137", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionDuringAsyncInvocationExecution(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011139", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011139";
   }

   public static Loggable logExceptionDuringAsyncInvocationExecutionLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011139", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidLocalClientViewArgument(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011140", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011140";
   }

   public static Loggable logInvalidLocalClientViewArgumentLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011140", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInvokingAlreadyRemovedEJB() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011141", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011141";
   }

   public static Loggable logErrorInvokingAlreadyRemovedEJBLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011141", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCreatingEJBReference(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011142", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011142";
   }

   public static Loggable logErrorCreatingEJBReferenceLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011142", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorRemovingStatefulSessionBean(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011143", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011143";
   }

   public static Loggable logErrorRemovingStatefulSessionBeanLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011143", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInitializingSingleton(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011144", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011144";
   }

   public static Loggable logErrorInitializingSingletonLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011144", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String illegalSingletonLoopbackCall() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011145", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011145";
   }

   public static Loggable illegalSingletonLoopbackCallLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011145", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String singletonConcurrentAccessTimeout(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011146", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011146";
   }

   public static Loggable singletonConcurrentAccessTimeoutLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011146", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String singletonBeanInUse(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011147", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011147";
   }

   public static Loggable singletonBeanInUseLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011147", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String dependedOnSingletonFailedInit(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011148", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011148";
   }

   public static Loggable dependedOnSingletonFailedInitLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011148", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidMaximumNumberOfMessagesSpecified(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011149", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011149";
   }

   public static Loggable logInvalidMaximumNumberOfMessagesSpecifiedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011149", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTransRolledbackAsReleaseCalledBetweenBeforeAndAfterDelivery(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011150", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011150";
   }

   public static Loggable logTransRolledbackAsReleaseCalledBetweenBeforeAndAfterDeliveryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011150", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConflictingActivationConfigProperties(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("011151", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011151";
   }

   public static Loggable logConflictingActivationConfigPropertiesLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("011151", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConflictingResourceLinkConfiguration(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011152", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011152";
   }

   public static Loggable logConflictingResourceLinkConfigurationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011152", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPreferStandardPropertyOverWLSProperty(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("011153", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011153";
   }

   public static Loggable logPreferStandardPropertyOverWLSPropertyLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("011153", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalTopicMessagePartitionModeValue(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011154", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011154";
   }

   public static Loggable logIllegalTopicMessagePartitionModeValueLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011154", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAccessDeniedOnNonexistentMethodException() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011155", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011155";
   }

   public static Loggable logAccessDeniedOnNonexistentMethodExceptionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011155", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonNumericValueForACPException(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011156", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011156";
   }

   public static Loggable logNonNumericValueForACPExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011156", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepionPreparing(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011157", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011157";
   }

   public static Loggable logExcepionPreparingLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011157", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepionInitializing(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011158", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011158";
   }

   public static Loggable logExcepionInitializingLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011158", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExcepionStarting(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011159", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011159";
   }

   public static Loggable logExcepionStartingLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011159", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorAsyncSuspendOrResumeMDB(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("011160", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011160";
   }

   public static Loggable logErrorAsyncSuspendOrResumeMDBLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("011160", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCreatingJMSResource(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("011161", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011161";
   }

   public static Loggable logErrorCreatingJMSResourceLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("011161", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvokeEJBTimeoutCallbackOnUndeployedBeanInstance(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011162", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011162";
   }

   public static Loggable logInvokeEJBTimeoutCallbackOnUndeployedBeanInstanceLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011162", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMethodNotFoundInInterface(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("012000", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012000";
   }

   public static Loggable logMethodNotFoundInInterfaceLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("012000", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBadAutoKeyGeneratorName(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("012001", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012001";
   }

   public static Loggable logBadAutoKeyGeneratorNameLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("012001", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAutoKeyCannotBePartOfFK() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("012004", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012004";
   }

   public static Loggable logAutoKeyCannotBePartOfFKLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("012004", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToLoadClass(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("012005", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012005";
   }

   public static Loggable logUnableToLoadClassLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("012005", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFieldNotFoundInClass(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("012006", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012006";
   }

   public static Loggable logFieldNotFoundInClassLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("012006", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotGenerateFinder(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("012007", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012007";
   }

   public static Loggable logCouldNotGenerateFinderLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("012007", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullFinder(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("012008", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012008";
   }

   public static Loggable logNullFinderLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("012008", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoCMRFieldForRemoteRelationship(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("012009", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012009";
   }

   public static Loggable logNoCMRFieldForRemoteRelationshipLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("012009", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoRemoteHome(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("012012", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012012";
   }

   public static Loggable logNoRemoteHomeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("012012", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMethodHasWrongParamCount(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("012013", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012013";
   }

   public static Loggable logMethodHasWrongParamCountLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("012013", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRemoteFinderNameNull(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("012014", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012014";
   }

   public static Loggable logRemoteFinderNameNullLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("012014", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGotNullXForFinder(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("012015", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012015";
   }

   public static Loggable logGotNullXForFinderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("012015", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGotNullBeanFromBeanMap(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("012016", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012016";
   }

   public static Loggable logGotNullBeanFromBeanMapLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("012016", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorWhileGenerating(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("012017", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012017";
   }

   public static Loggable logErrorWhileGeneratingLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("012017", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotProduceProductionRule(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("012019", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012019";
   }

   public static Loggable logCouldNotProduceProductionRuleLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("012019", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotGetClassForParam(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("012020", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012020";
   }

   public static Loggable logCouldNotGetClassForParamLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("012020", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logpersistentTypeMissing(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("012021", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012021";
   }

   public static Loggable logpersistentTypeMissingLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("012021", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logmismatchBetweenEJBNames(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("012022", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012022";
   }

   public static Loggable logmismatchBetweenEJBNamesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("012022", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logmismatchBetweenslsbEJBNames(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("012023", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012023";
   }

   public static Loggable logmismatchBetweenslsbEJBNamesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("012023", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logmismatchBetweensfsbEJBNames(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("012024", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012024";
   }

   public static Loggable logmismatchBetweensfsbEJBNamesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("012024", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logmismatchBetweenmdbEJBNames(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("012025", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012025";
   }

   public static Loggable logmismatchBetweenmdbEJBNamesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("012025", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logincorrectXMLFileVersion(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("012029", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012029";
   }

   public static Loggable logincorrectXMLFileVersionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("012029", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logincorrectDocType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("012030", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012030";
   }

   public static Loggable logincorrectDocTypeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("012030", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSqlSelectDistinctDeprecated(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("012031", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012031";
   }

   public static Loggable logSqlSelectDistinctDeprecatedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("012031", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningWeblogicQueryHasNoMatchingEjbQuery(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("012032", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012032";
   }

   public static Loggable logWarningWeblogicQueryHasNoMatchingEjbQueryLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("012032", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJavaCompilerOutput(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("012033", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012033";
   }

   public static Loggable logJavaCompilerOutputLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("012033", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningParameterIsNotSerializable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("012035", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012035";
   }

   public static Loggable logWarningParameterIsNotSerializableLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("012035", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJavaCompilerErrorOutput(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("012036", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012036";
   }

   public static Loggable logJavaCompilerErrorOutputLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("012036", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMismatchBetweenSingletonEJBNames(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("012037", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "012037";
   }

   public static Loggable logMismatchBetweenSingletonEJBNamesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("012037", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderDoesNotReturnBean(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013000", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013000";
   }

   public static Loggable logFinderDoesNotReturnBeanLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013000", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderReturnsBeanOfWrongType(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("013001", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013001";
   }

   public static Loggable logFinderReturnsBeanOfWrongTypeLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("013001", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExpressionWrongNumberOfTerms(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013002", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013002";
   }

   public static Loggable logExpressionWrongNumberOfTermsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013002", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExpressionRequiresX(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013003", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013003";
   }

   public static Loggable logExpressionRequiresXLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013003", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderParamsMustBeGTOne(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013004", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013004";
   }

   public static Loggable logFinderParamsMustBeGTOneLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013004", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderParamMissing(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013005", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013005";
   }

   public static Loggable logFinderParamMissingLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013005", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderCouldNotGetClassForIdBean(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("013006", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013006";
   }

   public static Loggable logFinderCouldNotGetClassForIdBeanLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("013006", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderCouldNotGetXForY(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("013007", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013007";
   }

   public static Loggable logFinderCouldNotGetXForYLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("013007", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderSelectWrongBean(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("013008", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013008";
   }

   public static Loggable logFinderSelectWrongBeanLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("013008", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderSelectTargetNoJoinNode(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013009", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013009";
   }

   public static Loggable logFinderSelectTargetNoJoinNodeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013009", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSelectClauseRequired() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("013010", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013010";
   }

   public static Loggable logSelectClauseRequiredLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("013010", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFromClauseRequired() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("013011", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013011";
   }

   public static Loggable logFromClauseRequiredLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("013011", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderRVDCannotBePathExpression(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013012", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013012";
   }

   public static Loggable logFinderRVDCannotBePathExpressionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013012", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderCouldNotGetAbstractSchemaNameForRVD(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013013", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013013";
   }

   public static Loggable logFinderCouldNotGetAbstractSchemaNameForRVDLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013013", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderCouldNotGetAbstractSchemaNameForBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013014", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013014";
   }

   public static Loggable logFinderCouldNotGetAbstractSchemaNameForBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013014", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderCouldNotGetRDBMSBeanForAbstractSchemaName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013015", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013015";
   }

   public static Loggable logFinderCouldNotGetRDBMSBeanForAbstractSchemaNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013015", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderCouldNotGetLastJoinNode(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("013017", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013017";
   }

   public static Loggable logFinderCouldNotGetLastJoinNodeLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("013017", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderExpectedSingleFK(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("013018", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013018";
   }

   public static Loggable logFinderExpectedSingleFKLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("013018", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderNotNullOnWrongType(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("013019", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013019";
   }

   public static Loggable logFinderNotNullOnWrongTypeLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("013019", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderNotNullOnBadPath(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013020", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013020";
   }

   public static Loggable logFinderNotNullOnBadPathLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013020", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFinderCouldNotFindCMRPointingToBean(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013021", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013021";
   }

   public static Loggable logFinderCouldNotFindCMRPointingToBeanLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013021", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderFKColumnsMissing(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("013022", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013022";
   }

   public static Loggable logfinderFKColumnsMissingLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("013022", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderCMRFieldNotFK(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("013023", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013023";
   }

   public static Loggable logfinderCMRFieldNotFKLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("013023", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderCouldNotGetFKColumns(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("013024", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013024";
   }

   public static Loggable logfinderCouldNotGetFKColumnsLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("013024", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderArgMustBeCollectionValued(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013025", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013025";
   }

   public static Loggable logfinderArgMustBeCollectionValuedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013025", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderCouldNotGetJoinTable() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("013026", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013026";
   }

   public static Loggable logfinderCouldNotGetJoinTableLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("013026", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderCouldNotGetFKTable() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("013027", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013027";
   }

   public static Loggable logfinderCouldNotGetFKTableLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("013027", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderMemberLHSWrongType(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013029", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013029";
   }

   public static Loggable logfinderMemberLHSWrongTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013029", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderMemberRHSWrongType(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013031", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013031";
   }

   public static Loggable logfinderMemberRHSWrongTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013031", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderNoPKClassForField(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013032", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013032";
   }

   public static Loggable logfinderNoPKClassForFieldLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013032", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderMemberMismatch(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013033", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013033";
   }

   public static Loggable logfinderMemberMismatchLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013033", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderInvalidBooleanLiteral() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("013035", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013035";
   }

   public static Loggable logfinderInvalidBooleanLiteralLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("013035", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderCouldNotGetTableAndField(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013036", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013036";
   }

   public static Loggable logfinderCouldNotGetTableAndFieldLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013036", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderInvalidStringExpression() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("013037", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013037";
   }

   public static Loggable logfinderInvalidStringExpressionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("013037", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderInvalidArithExpression() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("013038", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013038";
   }

   public static Loggable logfinderInvalidArithExpressionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("013038", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderTerminalCMRNotRemote(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013039", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013039";
   }

   public static Loggable logfinderTerminalCMRNotRemoteLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013039", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderPathEndsInXNotY(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013040", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013040";
   }

   public static Loggable logfinderPathEndsInXNotYLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013040", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlArgNotACmpField(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013041", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013041";
   }

   public static Loggable logejbqlArgNotACmpFieldLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013041", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlIdNotFieldAndNotBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013042", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013042";
   }

   public static Loggable logejbqlIdNotFieldAndNotBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013042", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlNoTokenSpecial(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013043", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013043";
   }

   public static Loggable logejbqlNoTokenSpecialLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013043", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlCanOnlyTestBeanVsSameBeanType(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013045", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013045";
   }

   public static Loggable logejbqlCanOnlyTestBeanVsSameBeanTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013045", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlSubQuerySelectCanOnlyHaveOneItem() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("013046", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013046";
   }

   public static Loggable logejbqlSubQuerySelectCanOnlyHaveOneItemLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("013046", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlOrderByIsDifferent() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("013047", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013047";
   }

   public static Loggable logejbqlOrderByIsDifferentLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("013047", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlSubQueryBeansCannotTestVariables() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("013048", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013048";
   }

   public static Loggable logejbqlSubQueryBeansCannotTestVariablesLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("013048", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlWrongBeanTestedAgainstVariable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("013049", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013049";
   }

   public static Loggable logejbqlWrongBeanTestedAgainstVariableLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("013049", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlSubQueryMissingClause(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013050", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013050";
   }

   public static Loggable logejbqlSubQueryMissingClauseLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013050", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlMissingRangeVariable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013051", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013051";
   }

   public static Loggable logejbqlMissingRangeVariableLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013051", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlMissingRangeVariableDeclaration(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013052", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013052";
   }

   public static Loggable logejbqlMissingRangeVariableDeclarationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013052", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlArgMustBeIDorINT(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013053", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013053";
   }

   public static Loggable logejbqlArgMustBeIDorINTLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013053", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlClauseNotAllowedInResultSetQueriesReturningBeans(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013054", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013054";
   }

   public static Loggable logejbqlClauseNotAllowedInResultSetQueriesReturningBeansLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013054", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlResultSetFinderCannotSelectBeans(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013055", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013055";
   }

   public static Loggable logejbqlResultSetFinderCannotSelectBeansLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013055", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlSelectObjectMustBeRangeOrCollectionId(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013057", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013057";
   }

   public static Loggable logejbqlSelectObjectMustBeRangeOrCollectionIdLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013057", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlSelectObjectMustBeIdentificationVarNotCMPField(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013059", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013059";
   }

   public static Loggable logejbqlSelectObjectMustBeIdentificationVarNotCMPFieldLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013059", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlSELECTmustUseOBJECTargument(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013061", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013061";
   }

   public static Loggable logejbqlSELECTmustUseOBJECTargumentLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013061", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlSubQueryBeansCanOnlyHaveSimplePKs(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013062", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013062";
   }

   public static Loggable logejbqlSubQueryBeansCanOnlyHaveSimplePKsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013062", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotMemberOfLHSNotInSelect(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013064", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013064";
   }

   public static Loggable logNotMemberOfLHSNotInSelectLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013064", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotDoOuterJoinForUnspecifiedDB() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("013065", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013065";
   }

   public static Loggable logCannotDoOuterJoinForUnspecifiedDBLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("013065", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotDoOuterJoinForDB(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("013066", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013066";
   }

   public static Loggable logCannotDoOuterJoinForDBLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("013066", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotDoMultiOuterJoinForDB(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("013067", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013067";
   }

   public static Loggable logCannotDoMultiOuterJoinForDBLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("013067", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotDoNOuterJoinForDB(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013068", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013068";
   }

   public static Loggable logCannotDoNOuterJoinForDBLoggable(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013068", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logOracleCannotDoOuterJoinAndOR(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013069", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013069";
   }

   public static Loggable logOracleCannotDoOuterJoinAndORLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013069", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logmustUseTwoPhaseDeployment(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013070", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013070";
   }

   public static Loggable logmustUseTwoPhaseDeploymentLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013070", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderNotFound11Message(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("013071", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013071";
   }

   public static Loggable logfinderNotFound11MessageLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("013071", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logshouldNotDefineJoinTableForOneToMany(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013072", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013072";
   }

   public static Loggable logshouldNotDefineJoinTableForOneToManyLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013072", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logfinderReturnedMultipleValues(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013073", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013073";
   }

   public static Loggable logfinderReturnedMultipleValuesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013073", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logduplicateAsDefinition(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013074", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013074";
   }

   public static Loggable logduplicateAsDefinitionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013074", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logduplicateRangeVariableDefinition(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013075", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013075";
   }

   public static Loggable logduplicateRangeVariableDefinitionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013075", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String lograngeVariableNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013076", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013076";
   }

   public static Loggable lograngeVariableNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013076", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logduplicateCollectionMemberDefinition(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013077", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013077";
   }

   public static Loggable logduplicateCollectionMemberDefinitionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013077", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logcorrelationVarDefinedMultipleTimes(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013078", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013078";
   }

   public static Loggable logcorrelationVarDefinedMultipleTimesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013078", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logidNotDefinedInAsDeclaration(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013079", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013079";
   }

   public static Loggable logidNotDefinedInAsDeclarationLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013079", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logpathExpressionNotInContextOfQueryTree(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013080", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013080";
   }

   public static Loggable logpathExpressionNotInContextOfQueryTreeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013080", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logOrMayYieldEmptyCrossProduct(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013081", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013081";
   }

   public static Loggable logOrMayYieldEmptyCrossProductLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013081", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logISNULLArgMustBePathExpressionOrVariable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013082", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013082";
   }

   public static Loggable logISNULLArgMustBePathExpressionOrVariableLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013082", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logselectForUpdateSpecifiedWithOrderBy(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013084", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013084";
   }

   public static Loggable logselectForUpdateSpecifiedWithOrderByLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013084", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSelectMultipleFieldsButReturnCollection(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013085", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013085";
   }

   public static Loggable logSelectMultipleFieldsButReturnCollectionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013085", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLIKEmissingArgument() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("013086", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013086";
   }

   public static Loggable logLIKEmissingArgumentLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("013086", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidStartCharacterForEJBQLIdentifier(char arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013087", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013087";
   }

   public static Loggable logInvalidStartCharacterForEJBQLIdentifierLoggable(char arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013087", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidPartCharacterForEJBQLIdentifier(char arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013088", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013088";
   }

   public static Loggable logInvalidPartCharacterForEJBQLIdentifierLoggable(char arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013088", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJBQLCharAllowedForBackwardsCompatibility(char arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013089", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013089";
   }

   public static Loggable logEJBQLCharAllowedForBackwardsCompatibilityLoggable(char arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013089", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logejbqlSelectCaseMustBePathExpression(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013090", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013090";
   }

   public static Loggable logejbqlSelectCaseMustBePathExpressionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013090", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAggregateFunctionMustHaveCMPFieldArg(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("013091", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013091";
   }

   public static Loggable logAggregateFunctionMustHaveCMPFieldArgLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("013091", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEjbqlHasBeenRewritten(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013092", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013092";
   }

   public static Loggable logEjbqlHasBeenRewrittenLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013092", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJBQL_REWRITE_REASON_FACTOR_OUT_NOT_TEXT() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("013093", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013093";
   }

   public static Loggable logEJBQL_REWRITE_REASON_FACTOR_OUT_NOT_TEXTLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("013093", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMayNotComplyWithEJB21_11_2_7_1_mustReturnAnyNullBeans(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013094", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013094";
   }

   public static Loggable logMayNotComplyWithEJB21_11_2_7_1_mustReturnAnyNullBeansLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013094", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidRelationshipCachingName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013095", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013095";
   }

   public static Loggable logInvalidRelationshipCachingNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013095", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidEJBQLSELECTExpression(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("013096", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "013096";
   }

   public static Loggable logInvalidEJBQLSELECTExpressionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("013096", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalValueForTransactionIsolation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("014000", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014000";
   }

   public static Loggable logIllegalValueForTransactionIsolationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("014000", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIsolationLevelSetInRDBMSDescriptor(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("014001", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014001";
   }

   public static Loggable logIsolationLevelSetInRDBMSDescriptorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("014001", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningOptimisticBeanUsesIncludeUpdate(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("014003", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014003";
   }

   public static Loggable logWarningOptimisticBeanUsesIncludeUpdateLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("014003", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningBatchOperationOffForAutoKeyGen(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("014004", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014004";
   }

   public static Loggable logWarningBatchOperationOffForAutoKeyGenLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("014004", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBDispatchPolicyIgnored(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("014005", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014005";
   }

   public static Loggable logMDBDispatchPolicyIgnoredLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("014005", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBUnknownDispatchPolicy(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("014006", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014006";
   }

   public static Loggable logMDBUnknownDispatchPolicyLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("014006", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningOptimisticBlobBeanHasNoVersionTimestamp(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("014007", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014007";
   }

   public static Loggable logWarningOptimisticBlobBeanHasNoVersionTimestampLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("014007", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSqlShapeDoesNotExist(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("014008", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014008";
   }

   public static Loggable logSqlShapeDoesNotExistLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("014008", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSuspendMDB(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("014009", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014009";
   }

   public static Loggable logSuspendMDBLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("014009", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSuspendNonDurableSubscriber(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("014010", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014010";
   }

   public static Loggable logSuspendNonDurableSubscriberLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("014010", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningOptimisticBeanUsesUseSelectForUpdate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("014011", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014011";
   }

   public static Loggable logWarningOptimisticBeanUsesUseSelectForUpdateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("014011", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningOCBeanIsVerifyModAndNoClustInvalidate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("014012", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014012";
   }

   public static Loggable logWarningOCBeanIsVerifyModAndNoClustInvalidateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("014012", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningNonOCOrROBeanDisablesClustInvalidate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("014013", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014013";
   }

   public static Loggable logWarningNonOCOrROBeanDisablesClustInvalidateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("014013", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBUnknownDispatchPolicyWM(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("014014", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014014";
   }

   public static Loggable logMDBUnknownDispatchPolicyWMLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("014014", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningReadOnlyBeanUsesUseSelectForUpdate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("014016", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014016";
   }

   public static Loggable logWarningReadOnlyBeanUsesUseSelectForUpdateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("014016", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningExclusiveBeanUsesUseSelectForUpdate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("014017", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014017";
   }

   public static Loggable logWarningExclusiveBeanUsesUseSelectForUpdateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("014017", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningNonOptimisticBeanUsesVerifyColumns(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("014018", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014018";
   }

   public static Loggable logWarningNonOptimisticBeanUsesVerifyColumnsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("014018", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningNonOptimisticBeanUsesVerifyRows(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("014019", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014019";
   }

   public static Loggable logWarningNonOptimisticBeanUsesVerifyRowsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("014019", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningNonOptimisticBeanUsesOptimisticColumn(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("014020", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014020";
   }

   public static Loggable logWarningNonOptimisticBeanUsesOptimisticColumnLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("014020", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeployedWithEJBName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("014021", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014021";
   }

   public static Loggable logDeployedWithEJBNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("014021", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJNDINamesMap(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("014022", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "014022";
   }

   public static Loggable logJNDINamesMapLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("014022", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvokeSatefulCallbackError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011223", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011223";
   }

   public static Loggable logInvokeSatefulCallbackErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011223", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAlreadyBindInterfaceWithName(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011224", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011224";
   }

   public static Loggable logAlreadyBindInterfaceWithNameLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011224", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAnotherInterfaceBindWithName(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011225", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011225";
   }

   public static Loggable logAnotherInterfaceBindWithNameLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011225", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDuringFindCannotGetConnection(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("011226", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011226";
   }

   public static Loggable logDuringFindCannotGetConnectionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("011226", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionWhilePrepareingQuery(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("011227", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011227";
   }

   public static Loggable logExceptionWhilePrepareingQueryLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("011227", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorSetQueryParametor(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("011232", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011232";
   }

   public static Loggable logErrorSetQueryParametorLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("011232", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorExecuteQuery(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("011233", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011233";
   }

   public static Loggable logErrorExecuteQueryLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("011233", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorMapColumn(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("011234", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011234";
   }

   public static Loggable logErrorMapColumnLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("011234", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorMapRelatioship(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("011235", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011235";
   }

   public static Loggable logErrorMapRelatioshipLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("011235", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorExecuteFinder(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("011236", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011236";
   }

   public static Loggable logErrorExecuteFinderLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("011236", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoSqlShapeSpecified(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011237", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011237";
   }

   public static Loggable logNoSqlShapeSpecifiedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011237", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotSelectForAllPrimaryKey(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("011238", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011238";
   }

   public static Loggable logNotSelectForAllPrimaryKeyLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("011238", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logQueryCacheNotSupportReadWriteBean() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011239", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011239";
   }

   public static Loggable logQueryCacheNotSupportReadWriteBeanLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011239", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorObtainNativeQuery(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011240", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011240";
   }

   public static Loggable logErrorObtainNativeQueryLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011240", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonWeblogicEntityManagerExecuteQuery(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011241", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011241";
   }

   public static Loggable logNonWeblogicEntityManagerExecuteQueryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011241", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExtendedPersistenceContextClosed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011242", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011242";
   }

   public static Loggable logExtendedPersistenceContextClosedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011242", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalStateTransaction(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011243", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011243";
   }

   public static Loggable logIllegalStateTransactionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011243", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalCallEJBContextMethod() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011244", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011244";
   }

   public static Loggable logIllegalCallEJBContextMethodLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011244", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBeanIsNotEJB3Bean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011245", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011245";
   }

   public static Loggable logBeanIsNotEJB3BeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011245", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEjbBeanWithoutHomeInterface(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011246", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011246";
   }

   public static Loggable logEjbBeanWithoutHomeInterfaceLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011246", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEjbNoImplementBusinessInterface(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011247", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011247";
   }

   public static Loggable logEjbNoImplementBusinessInterfaceLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011247", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBeanNotInvokedThroughBusinessInterface() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011248", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011248";
   }

   public static Loggable logBeanNotInvokedThroughBusinessInterfaceLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011248", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBIllegalInvokeUserTransactionMethodInEjbCreateOrPostConstruct() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011249", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011249";
   }

   public static Loggable logMDBIllegalInvokeUserTransactionMethodInEjbCreateOrPostConstructLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011249", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBIllegalInvokeUserTransactionMethodInEjbRemoveOrPreDestroy() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011250", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011250";
   }

   public static Loggable logMDBIllegalInvokeUserTransactionMethodInEjbRemoveOrPreDestroyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011250", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBIllegalInvokeUserTransactionMethodInSetSessionContextOrDI() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011251", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011251";
   }

   public static Loggable logMDBIllegalInvokeUserTransactionMethodInSetSessionContextOrDILoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011251", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSFSBIllegalInvokeUserTransactionMethodInSetSessionContextOrDI() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011252", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011252";
   }

   public static Loggable logSFSBIllegalInvokeUserTransactionMethodInSetSessionContextOrDILoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011252", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSLSBIllegalInvokeUserTransactionMethodInEjbCreateOrPostConstruct() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011253", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011253";
   }

   public static Loggable logSLSBIllegalInvokeUserTransactionMethodInEjbCreateOrPostConstructLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011253", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSLSBIllegalInvokeUserTransactionMethodInEjbRemoveOrPreDestroy() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011254", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011254";
   }

   public static Loggable logSLSBIllegalInvokeUserTransactionMethodInEjbRemoveOrPreDestroyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011254", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSLSBIllegalInvokeUserTransactionMethodInSetSessionContextOrDI() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011255", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011255";
   }

   public static Loggable logSLSBIllegalInvokeUserTransactionMethodInSetSessionContextOrDILoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011255", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSingleExpirationTimerCannotBeCancelled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011256", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011256";
   }

   public static Loggable logSingleExpirationTimerCannotBeCancelledLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011256", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalInvokeTimerMethodInEJbRemoveOrPreDestroy() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011257", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011257";
   }

   public static Loggable logIllegalInvokeTimerMethodInEJbRemoveOrPreDestroyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011257", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalInvokeTimerMethodInEJbRAvitvateOrPostActivate() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011258", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011258";
   }

   public static Loggable logIllegalInvokeTimerMethodInEJbRAvitvateOrPostActivateLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011258", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalInvokeTimerMethodInEjbPassivateOrPrePassivate() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011259", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011259";
   }

   public static Loggable logIllegalInvokeTimerMethodInEjbPassivateOrPrePassivateLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011259", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalInvokeTimerMethodDuringDI() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011260", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011260";
   }

   public static Loggable logIllegalInvokeTimerMethodDuringDILoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011260", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvovationTimeout() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011261", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011261";
   }

   public static Loggable logInvovationTimeoutLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011261", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCacelTimer() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011262", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011262";
   }

   public static Loggable logErrorCacelTimerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011262", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEjBJarBeanNotSet() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011263", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011263";
   }

   public static Loggable logEjBJarBeanNotSetLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011263", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTxNerverMethodCalledWithnInTx(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011264", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011264";
   }

   public static Loggable logTxNerverMethodCalledWithnInTxLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011264", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionAferDelivery(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011265", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011265";
   }

   public static Loggable logExceptionAferDeliveryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011265", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExecuteGetDatabaseProductnameUseNonWeblogicEntityManager() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("011266", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011266";
   }

   public static Loggable logExecuteGetDatabaseProductnameUseNonWeblogicEntityManagerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("011266", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCallGetdatabaseProductName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011267", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011267";
   }

   public static Loggable logErrorCallGetdatabaseProductNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011267", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExecuteNativeQueryUseNonWeblogicEntitymanager(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011268", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011268";
   }

   public static Loggable logExecuteNativeQueryUseNonWeblogicEntitymanagerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011268", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEntityErrorObtainNativeQuery(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011269", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011269";
   }

   public static Loggable logEntityErrorObtainNativeQueryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011269", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorPrepareQuery(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011270", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011270";
   }

   public static Loggable logErrorPrepareQueryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011270", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalMakeReentrantCallSFSB(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011276", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011276";
   }

   public static Loggable logIllegalMakeReentrantCallSFSBLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011276", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalMakeReentrantCallSFSBFromHome(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("011277", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011277";
   }

   public static Loggable logIllegalMakeReentrantCallSFSBFromHomeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("011277", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalDependencyOfSingletonSessionBean(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011278", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011278";
   }

   public static Loggable logIllegalDependencyOfSingletonSessionBeanLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011278", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSingletonSessionBeanPreDestroyException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("011279", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "011279";
   }

   public static Loggable logSingletonSessionBeanPreDestroyExceptionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("011279", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorProcessingAnnotations(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015000", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015000";
   }

   public static Loggable logErrorProcessingAnnotationsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015000", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableLinkClass(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("015001", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015001";
   }

   public static Loggable logUnableLinkClassLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("015001", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableLoadClass(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("015002", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015002";
   }

   public static Loggable logUnableLoadClassLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("015002", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableCreateJar(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015003", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015003";
   }

   public static Loggable logUnableCreateJarLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015003", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionBeanWithSessionBeanParent(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015004", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015004";
   }

   public static Loggable logSessionBeanWithSessionBeanParentLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015004", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionBeanWithoutSetSessionType(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015005", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015005";
   }

   public static Loggable logSessionBeanWithoutSetSessionTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015005", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEjbCreateNotFoundForInitMethod(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015007", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015007";
   }

   public static Loggable logEjbCreateNotFoundForInitMethodLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015007", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoMatchCreateMethodForInitMethod(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015008", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015008";
   }

   public static Loggable logNoMatchCreateMethodForInitMethodLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015008", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotLoadInterceptorClass(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015009", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015009";
   }

   public static Loggable logCannotLoadInterceptorClassLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015009", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMutipleMehtodPermissionMethodForMethod(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015010", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015010";
   }

   public static Loggable logMutipleMehtodPermissionMethodForMethodLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015010", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMutipleMehtodPermissionMethodForClass(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015011", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015011";
   }

   public static Loggable logMutipleMehtodPermissionMethodForClassLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015011", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableLoadInterfaceClass(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015012", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015012";
   }

   public static Loggable logUnableLoadInterfaceClassLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015012", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBeanClassNotImplementInterfaceMethod(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015013", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015013";
   }

   public static Loggable logBeanClassNotImplementInterfaceMethodLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015013", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotFoundServiceEndPointClass(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015014", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015014";
   }

   public static Loggable logCannotFoundServiceEndPointClassLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015014", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBWithMDBParent(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015015", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015015";
   }

   public static Loggable logMDBWithMDBParentLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015015", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoMessageListenerSpecifiedForMDB(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015016", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015016";
   }

   public static Loggable logNoMessageListenerSpecifiedForMDBLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015016", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoSetBeanInterfaceForBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015017", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015017";
   }

   public static Loggable logNoSetBeanInterfaceForBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015017", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoSetBeanInterfaceForInterceptor(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015018", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015018";
   }

   public static Loggable logNoSetBeanInterfaceForInterceptorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015018", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAnnotationOnInvalidClass(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015022", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015022";
   }

   public static Loggable logAnnotationOnInvalidClassLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015022", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAnnotationOnInvalidMethod(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("015023", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015023";
   }

   public static Loggable logAnnotationOnInvalidMethodLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("015023", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJNDINameAnnotationOnLocalInterface(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015024", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015024";
   }

   public static Loggable logJNDINameAnnotationOnLocalInterfaceLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015024", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableLoadClassSpecifiedInDD(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015025", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015025";
   }

   public static Loggable logUnableLoadClassSpecifiedInDDLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015025", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRefrenceNameDuplicated(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("015026", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015026";
   }

   public static Loggable logRefrenceNameDuplicatedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("015026", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJndiNameWasNotAXAJMSConnectionFactory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015027", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015027";
   }

   public static Loggable logJndiNameWasNotAXAJMSConnectionFactoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015027", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalDistributedDestinationConnectionValue(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015028", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015028";
   }

   public static Loggable logIllegalDistributedDestinationConnectionValueLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015028", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalTopicMessagesDistributionModeValue(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015029", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015029";
   }

   public static Loggable logIllegalTopicMessagesDistributionModeValueLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015029", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalPermutationOnPDTAndComp(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015030", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015030";
   }

   public static Loggable logIllegalPermutationOnPDTAndCompLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015030", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidConfigurationForDistributionConnection(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015031", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015031";
   }

   public static Loggable logInvalidConfigurationForDistributionConnectionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015031", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidConfigurationForTopicMessagesDistributionMode(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015032", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015032";
   }

   public static Loggable logInvalidConfigurationForTopicMessagesDistributionModeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015032", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidConfigurationForPre1033(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015033", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015033";
   }

   public static Loggable logInvalidConfigurationForPre1033Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015033", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalSubscriptionOnDurRemoteRDT(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015034", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015034";
   }

   public static Loggable logIllegalSubscriptionOnDurRemoteRDTLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015034", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logOverridenLocalOnlyWithEveryMember(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015035", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015035";
   }

   public static Loggable logOverridenLocalOnlyWithEveryMemberLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015035", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logOverridenActivationConfigProperty(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("015036", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015036";
   }

   public static Loggable logOverridenActivationConfigPropertyLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("015036", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logComplianceWarning(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("015037", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015037";
   }

   public static Loggable logComplianceWarningLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("015037", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMayBeMissingBridgeMethod(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015038", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015038";
   }

   public static Loggable logMayBeMissingBridgeMethodLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015038", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInactiveMDBStartFail(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015039", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015039";
   }

   public static Loggable logInactiveMDBStartFailLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015039", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBInactive(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015040", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015040";
   }

   public static Loggable logMDBInactiveLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015040", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBActive(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015041", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015041";
   }

   public static Loggable logMDBActiveLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015041", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotFoundHomeForJndiName(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("015042", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015042";
   }

   public static Loggable logNotFoundHomeForJndiNameLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("015042", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConcurrentAccessTimeoutOnSFSBMethod() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("015043", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015043";
   }

   public static Loggable logConcurrentAccessTimeoutOnSFSBMethodLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("015043", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDuplicateJNDINameAnnotationEJB31(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("015044", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015044";
   }

   public static Loggable logDuplicateJNDINameAnnotationEJB31Loggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("015044", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoJNDINameOnMultiInterfaceImplEJB31(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015045", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015045";
   }

   public static Loggable logNoJNDINameOnMultiInterfaceImplEJB31Loggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015045", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logContextDataNotSupportedForEntityBean() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("015046", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015046";
   }

   public static Loggable logContextDataNotSupportedForEntityBeanLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("015046", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPortableJNDIBindFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015047", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015047";
   }

   public static Loggable logPortableJNDIBindFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015047", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTimeoutMethodNotConfigured() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("015048", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015048";
   }

   public static Loggable logTimeoutMethodNotConfiguredLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("015048", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLocalBusinessInterfaceExtendsRemote(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015049", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015049";
   }

   public static Loggable logLocalBusinessInterfaceExtendsRemoteLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015049", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMethodDidNotCompleteTX(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("015050", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015050";
   }

   public static Loggable logMethodDidNotCompleteTXLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("015050", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStatefulSessionBeanLifecycleCallbackException(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("015051", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015051";
   }

   public static Loggable logStatefulSessionBeanLifecycleCallbackExceptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("015051", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoLifecycleCallbackFoundinBeanClass(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("015052", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015052";
   }

   public static Loggable logNoLifecycleCallbackFoundinBeanClassLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("015052", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJndiNameWasPooledConnectionFactory(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015054", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015054";
   }

   public static Loggable logJndiNameWasPooledConnectionFactoryLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015054", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDuringFullStateReplication(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("015055", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015055";
   }

   public static Loggable logErrorDuringFullStateReplicationLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("015055", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnregisteredStatefulEJBReplicas(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015056", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015056";
   }

   public static Loggable logUnregisteredStatefulEJBReplicasLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015056", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSExceptionProcessingMDBWithSuppressCount(String arg0, String arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("015057", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015057";
   }

   public static Loggable logJMSExceptionProcessingMDBWithSuppressCountLoggable(String arg0, String arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("015057", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidOBSInitialContextFactory(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015058", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015058";
   }

   public static Loggable logInvalidOBSInitialContextFactoryLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015058", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedOBSEnabledCF(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015059", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015059";
   }

   public static Loggable logUnsupportedOBSEnabledCFLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015059", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBeanActivationException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015060", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015060";
   }

   public static Loggable logBeanActivationExceptionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015060", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReadReplicatedBeanStateException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015061", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015061";
   }

   public static Loggable logReadReplicatedBeanStateExceptionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015061", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCreateReplicatedBeanException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015062", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015062";
   }

   public static Loggable logCreateReplicatedBeanExceptionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015062", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSExceptionProcessingMDBSuppressed(long arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("015063", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015063";
   }

   public static Loggable logJMSExceptionProcessingMDBSuppressedLoggable(long arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("015063", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningReplicateOnShutdown(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("015064", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015064";
   }

   public static Loggable logWarningReplicateOnShutdownLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("015064", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCheckServerStateOnDeactivation(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015065", 8, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015065";
   }

   public static Loggable logErrorCheckServerStateOnDeactivationLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015065", 8, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningLocalCleanupReplicaOnPartitionShutdown(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("015066", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015066";
   }

   public static Loggable logWarningLocalCleanupReplicaOnPartitionShutdownLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("015066", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReplicateOnShutdown(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015067", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015067";
   }

   public static Loggable logReplicateOnShutdownLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015067", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLocalCleanupReplicaOnPartitionShutdown(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015068", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015068";
   }

   public static Loggable logLocalCleanupReplicaOnPartitionShutdownLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015068", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServerShuttingDownRejectEJBInvocation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015069", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015069";
   }

   public static Loggable logServerShuttingDownRejectEJBInvocationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015069", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionShuttingDownRejectEJBInvocation(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015070", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015070";
   }

   public static Loggable logPartitionShuttingDownRejectEJBInvocationLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015070", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotStartTransaction(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015071", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015071";
   }

   public static Loggable logCannotStartTransactionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015071", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBeanUndeployedRejectEJBInvocation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015072", 128, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015072";
   }

   public static Loggable logBeanUndeployedRejectEJBInvocationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015072", 128, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningNotFoundMDBActionConfigPropertyName(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015073", 16, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015073";
   }

   public static Loggable logWarningNotFoundMDBActionConfigPropertyNameLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015073", 16, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMDBSuspendedOnConnect(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015074", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015074";
   }

   public static Loggable logMDBSuspendedOnConnectLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015074", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJBDeploymentState(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015075", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015075";
   }

   public static Loggable logEJBDeploymentStateLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015075", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJBModuleCreated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("015076", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015076";
   }

   public static Loggable logEJBModuleCreatedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("015076", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logActivatedDescriptorUpdate(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015077", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015077";
   }

   public static Loggable logActivatedDescriptorUpdateLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015077", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBecomePrimary(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015078", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015078";
   }

   public static Loggable logBecomePrimaryLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015078", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBecomeSecondary(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015079", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015079";
   }

   public static Loggable logBecomeSecondaryLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015079", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBecomeSecondaryBI(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("015080", 64, args, EJBLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "015080";
   }

   public static Loggable logBecomeSecondaryBILoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("015080", 64, args, "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EJBLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.ejb.container.EJBLogLocalizer", EJBLogger.class.getClassLoader());
      private MessageLogger messageLogger = EJBLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = EJBLogger.findMessageLogger();
      }
   }
}
