package com.bea.logging;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class LoggingServiceLogger {
   private static final String LOCALIZER_CLASS = "com.bea.logging.LoggingServiceLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(LoggingServiceLogger.class.getName());
   }

   public static String logFileWillbeRotated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320400", 32, args, LoggingServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LoggingServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320400";
   }

   public static String logFileRotated(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320401", 32, args, LoggingServiceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LoggingServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320401";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "com.bea.logging.LoggingServiceLogLocalizer", LoggingServiceLogger.class.getClassLoader());
      private MessageLogger messageLogger = LoggingServiceLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = LoggingServiceLogger.findMessageLogger();
      }
   }
}
