package weblogic.entitlement;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class EntitlementLogger {
   private static final String LOCALIZER_CLASS = "weblogic.entitlement.EntitlementLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(EntitlementLogger.class.getName());
   }

   public static String logInvalidPropertyValue(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("099500", 2, args, EntitlementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EntitlementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "099500";
   }

   public static String logPolicyEvaluationFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("099501", 32, args, EntitlementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EntitlementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "099501";
   }

   public static String logRetrievedInvalidPredicate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("099502", 4, args, EntitlementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EntitlementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "099502";
   }

   public static String logRoleUnregisteredPredicate(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("099503", 32, args, EntitlementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EntitlementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "099503";
   }

   public static String logResourceUnregisteredPredicate(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("099504", 32, args, EntitlementLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EntitlementLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "099504";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.entitlement.EntitlementLogLocalizer", EntitlementLogger.class.getClassLoader());
      private MessageLogger messageLogger = EntitlementLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = EntitlementLogger.findMessageLogger();
      }
   }
}
