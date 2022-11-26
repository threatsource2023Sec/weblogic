package weblogic.management.internal;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class ConfigAuditorLogger {
   private static final String LOCALIZER_CLASS = "weblogic.management.internal.ConfigAuditorLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(ConfigAuditorLogger.class.getName());
   }

   public static String logInfoAuditCreateSuccess(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("159900", 64, args, ConfigAuditorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigAuditorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "159900";
   }

   public static String logInfoAuditCreateFailure(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("159901", 64, args, ConfigAuditorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigAuditorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "159901";
   }

   public static String logInfoAuditRemoveSuccess(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("159902", 64, args, ConfigAuditorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigAuditorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "159902";
   }

   public static String logInfoAuditRemoveFailure(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("159903", 64, args, ConfigAuditorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigAuditorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "159903";
   }

   public static String logInfoAuditModifySuccess(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("159904", 64, args, ConfigAuditorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigAuditorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "159904";
   }

   public static String logInfoAuditModifyFailure(String arg0, String arg1, String arg2, String arg3, String arg4, Exception arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("159905", 64, args, ConfigAuditorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigAuditorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "159905";
   }

   public static String logInfoAuditInvokeSuccess(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("159907", 64, args, ConfigAuditorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigAuditorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "159907";
   }

   public static String logInfoAuditInvokeFailure(String arg0, String arg1, String arg2, String arg3, Exception arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("159908", 64, args, ConfigAuditorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigAuditorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "159908";
   }

   public static String logInfoConfigurationAuditingEnabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("159909", 64, args, ConfigAuditorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigAuditorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "159909";
   }

   public static String logInfoConfigurationAuditingDisabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("159910", 64, args, ConfigAuditorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigAuditorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "159910";
   }

   public static String logInvalidNumberReplacingClearText(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("159911", 64, args, ConfigAuditorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigAuditorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "159911";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.internal.ConfigAuditorLogLocalizer", ConfigAuditorLogger.class.getClassLoader());
      private MessageLogger messageLogger = ConfigAuditorLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = ConfigAuditorLogger.findMessageLogger();
      }
   }
}
