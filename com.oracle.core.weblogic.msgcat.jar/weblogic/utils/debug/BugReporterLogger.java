package weblogic.utils.debug;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class BugReporterLogger {
   private static final String LOCALIZER_CLASS = "weblogic.utils.debug.BugReporterLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(BugReporterLogger.class.getName());
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.utils.debug.BugReporterLogLocalizer", BugReporterLogger.class.getClassLoader());
      private MessageLogger messageLogger = BugReporterLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = BugReporterLogger.findMessageLogger();
      }
   }
}
