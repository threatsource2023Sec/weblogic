package weblogic.common.resourcepool;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class CommonLogger {
   private static final String LOCALIZER_CLASS = "weblogic.common.resourcepool.CommonLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(CommonLogger.class.getName());
   }

   public static String logMaxUnavlReached(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000611", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000611";
   }

   public static String logWarnTestingAllAvl(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000612", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000612";
   }

   public static String logTestOnCreateEnabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000613", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000613";
   }

   public static String logTestOnCreateDisabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000614", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000614";
   }

   public static String logTestOnReserveDisabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000615", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000615";
   }

   public static String logTestOnReserveEnabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000616", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000616";
   }

   public static String logTestOnReleaseEnabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000617", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000617";
   }

   public static String logTestOnReleaseDisabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000618", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000618";
   }

   public static String logErrForcedRelease(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000619", 8, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000619";
   }

   public static String logForcedRelease(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000620", 16, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000620";
   }

   public static String logUnexpectedProblem(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000621", 8, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000621";
   }

   public static String logWarnShutdownRelease(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000622", 16, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000622";
   }

   public static String logAdjustedCapacityIncrement(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000623", 16, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000623";
   }

   public static String logAdjustedTestSeconds(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000624", 16, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000624";
   }

   public static String logNoTest(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000625", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000625";
   }

   public static String logTest(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000626", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000626";
   }

   public static String logAdjustedMakeCount(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000627", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000627";
   }

   public static String logResourcesMade(String arg0, int arg1, int arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("000628", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000628";
   }

   public static String logWarnReclaimIncomplete(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000629", 16, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000629";
   }

   public static String logPoolRetryFailure(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000630", 16, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000630";
   }

   public static String logWarnUnknownResRelease(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000631", 16, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000631";
   }

   public static String logShuttingDownIgnoringInUse(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000632", 16, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000632";
   }

   public static String logSuspendingPoolDueToFailures(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000633", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000633";
   }

   public static String logHangDetected(String arg0, String arg1, long arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("000634", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000634";
   }

   public static String logDisablingGroupDueToFailures(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000635", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000635";
   }

   public static String logEnablingGroupDueToSuccess(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000636", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000636";
   }

   public static String logResumingPoolDueToSuccess(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000637", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000637";
   }

   public static String logSuspendingPoolByExternalCommand(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000638", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000638";
   }

   public static String logForceSuspendingPoolByExternalCommand(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000639", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000639";
   }

   public static String logResumingPoolByExternalCommand(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000640", 64, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000640";
   }

   public static String logDebugMsg(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000641", 128, args, CommonLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      CommonLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000641";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.common.resourcepool.CommonLogLocalizer", CommonLogger.class.getClassLoader());
      private MessageLogger messageLogger = CommonLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = CommonLogger.findMessageLogger();
      }
   }
}
