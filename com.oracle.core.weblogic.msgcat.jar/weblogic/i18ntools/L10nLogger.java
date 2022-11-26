package weblogic.i18ntools;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;

public class L10nLogger {
   private static final String LOCALIZER_CLASS = "weblogic.i18ntools.L10nLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(L10nLogger.class.getName());
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.i18ntools.L10nLogLocalizer", L10nLogger.class.getClassLoader());
      private MessageLogger messageLogger = L10nLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = L10nLogger.findMessageLogger();
      }
   }
}
