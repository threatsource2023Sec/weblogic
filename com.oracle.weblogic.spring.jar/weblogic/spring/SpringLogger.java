package weblogic.spring;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class SpringLogger {
   private static final String LOCALIZER_CLASS = "weblogic.spring.SpringLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(SpringLogger.class.getName());
   }

   public static String logNotWebContext(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2160000", 16, args, SpringLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SpringLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2160000";
   }

   public static String logRuntimeMBeanNotFound() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2160001", 16, args, SpringLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SpringLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2160001";
   }

   public static String getUnregisteredScopeName(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2160002", 16, args, "weblogic.spring.SpringLogLocalizer", SpringLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SpringLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.spring.SpringLogLocalizer", SpringLogger.class.getClassLoader());
      private MessageLogger messageLogger = SpringLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = SpringLogger.findMessageLogger();
      }
   }
}
