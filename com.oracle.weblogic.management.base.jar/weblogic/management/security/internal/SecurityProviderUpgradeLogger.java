package weblogic.management.security.internal;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class SecurityProviderUpgradeLogger {
   private static final String LOCALIZER_CLASS = "weblogic.management.security.internal.SecurityProviderUpgradeLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(SecurityProviderUpgradeLogger.class.getName());
   }

   public static String logIncorrectArgs() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("400200", 64, args, SecurityProviderUpgradeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityProviderUpgradeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "400200";
   }

   public static String logMigratingOldProvidersFrom1Arg(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("400201", 64, args, SecurityProviderUpgradeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityProviderUpgradeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "400201";
   }

   public static String logNoJarsUpgraded() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("400204", 64, args, SecurityProviderUpgradeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityProviderUpgradeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "400204";
   }

   public static String logCopyProviderTo(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("400205", 64, args, SecurityProviderUpgradeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityProviderUpgradeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "400205";
   }

   public static String logCompletedJars(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("400206", 64, args, SecurityProviderUpgradeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityProviderUpgradeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "400206";
   }

   public static String logSkippedCount(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("400207", 64, args, SecurityProviderUpgradeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityProviderUpgradeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "400207";
   }

   public static String logNoMDF(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("400208", 64, args, SecurityProviderUpgradeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityProviderUpgradeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "400208";
   }

   public static String logNewFromOld(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("400209", 64, args, SecurityProviderUpgradeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityProviderUpgradeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "400209";
   }

   public static String logInvalidMDF(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("400210", 64, args, SecurityProviderUpgradeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityProviderUpgradeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "400210";
   }

   public static String logSkippingJar(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("400211", 64, args, SecurityProviderUpgradeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityProviderUpgradeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "400211";
   }

   public static String logNowProcessing(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("400212", 64, args, SecurityProviderUpgradeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityProviderUpgradeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "400212";
   }

   public static String logCannotConvert(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("400213", 64, args, SecurityProviderUpgradeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityProviderUpgradeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "400213";
   }

   public static String logRunningFirstPhase(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("400214", 64, args, SecurityProviderUpgradeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityProviderUpgradeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "400214";
   }

   public static String logRunningSecondPhase(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("400215", 64, args, SecurityProviderUpgradeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityProviderUpgradeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "400215";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.security.internal.SecurityProviderUpgradeLogLocalizer", SecurityProviderUpgradeLogger.class.getClassLoader());
      private MessageLogger messageLogger = SecurityProviderUpgradeLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = SecurityProviderUpgradeLogger.findMessageLogger();
      }
   }
}
