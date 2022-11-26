package weblogic.validation;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class ValidationLogger {
   private static final String LOCALIZER_CLASS = "weblogic.validation.ValidationLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(ValidationLogger.class.getName());
   }

   public static String warningUnableToParseDescriptor(URL arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2156400", 16, args, ValidationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ValidationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156400";
   }

   public static String errorUnableToProcessURL(URL arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156401", 8, args, ValidationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ValidationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156401";
   }

   public static String errorUnableToProcessURLWith(URL arg0, boolean arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2156402", 8, args, ValidationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ValidationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156402";
   }

   public static String errorUnableToProcessURLDueToException(URL arg0, IOException arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156403", 8, args, ValidationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ValidationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156403";
   }

   public static String errorUnableToFindValidationContext(RuntimeException arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2156404", 8, args, ValidationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ValidationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156404";
   }

   public static String errorUnableToReadSource(URL arg0, IOException arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156405", 8, args, ValidationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ValidationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156405";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.validation.ValidationLogLocalizer", ValidationLogger.class.getClassLoader());
      private MessageLogger messageLogger = ValidationLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = ValidationLogger.findMessageLogger();
      }
   }
}
