package weblogic.j2ee;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class J2EEDeployerLogger {
   private static final String LOCALIZER_CLASS = "weblogic.j2ee.J2EEDeployerLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(J2EEDeployerLogger.class.getName());
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.j2ee.J2EEDeployerLogLocalizer", J2EEDeployerLogger.class.getClassLoader());
      private MessageLogger messageLogger = J2EEDeployerLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = J2EEDeployerLogger.findMessageLogger();
      }
   }
}
