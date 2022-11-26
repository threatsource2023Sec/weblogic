package weblogic.management.deploy.internal;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class MasterDeployerLogger {
   private static final String LOCALIZER_CLASS = "weblogic.i18ntools.MasterDeployerLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(MasterDeployerLogger.class.getName());
   }

   public static String logServers(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149319", 64, args, MasterDeployerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MasterDeployerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149319";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.i18ntools.MasterDeployerLogLocalizer", MasterDeployerLogger.class.getClassLoader());
      private MessageLogger messageLogger = MasterDeployerLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = MasterDeployerLogger.findMessageLogger();
      }
   }
}
