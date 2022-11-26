package weblogic.health;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class HealthLogger {
   private static final String LOCALIZER_CLASS = "weblogic.health.HealthLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(HealthLogger.class.getName());
   }

   public static String logDebugMsg(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("310000", 128, args, HealthLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HealthLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "310000";
   }

   public static String logErrorSubsystemFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("310001", 8, args, HealthLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HealthLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "310001";
   }

   public static Loggable logErrorSubsystemFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("310001", 8, args, "weblogic.health.HealthLogLocalizer", HealthLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HealthLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFreeMemoryChanged(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("310002", 64, args, HealthLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HealthLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "310002";
   }

   public static String logOOMEImminent(long arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("310003", 16, args, HealthLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HealthLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "310003";
   }

   public static String logErrorSubsystemFailedWithReason(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("310006", 4, args, HealthLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HealthLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "310006";
   }

   public static Loggable logErrorSubsystemFailedWithReasonLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("310006", 4, args, "weblogic.health.HealthLogLocalizer", HealthLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HealthLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoRegisteredSubsystem(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("310007", 16, args, HealthLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HealthLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "310007";
   }

   public static String logNonCriticalSubsystemFailedWithReason(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("310008", 4, args, HealthLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HealthLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "310008";
   }

   public static String logErrorServiceGroupRestartInPlaceFailedWithReason(String arg0, String arg1, int arg2, int arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("310009", 4, args, HealthLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HealthLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "310009";
   }

   public static Loggable logErrorServiceGroupRestartInPlaceFailedWithReasonLoggable(String arg0, String arg1, int arg2, int arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("310009", 4, args, "weblogic.health.HealthLogLocalizer", HealthLogger.MessageLoggerInitializer.INSTANCE.messageLogger, HealthLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInfoServiceGroupRestartInPlaceStarted(String arg0, String arg1, int arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("310010", 64, args, HealthLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HealthLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "310010";
   }

   public static String logInfoServiceGroupRestartInPlaceFinished(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("310011", 64, args, HealthLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HealthLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "310011";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.health.HealthLogLocalizer", HealthLogger.class.getClassLoader());
      private MessageLogger messageLogger = HealthLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = HealthLogger.findMessageLogger();
      }
   }
}
