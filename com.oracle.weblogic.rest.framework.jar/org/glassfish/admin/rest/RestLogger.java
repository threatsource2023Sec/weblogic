package org.glassfish.admin.rest;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class RestLogger {
   private static final String LOCALIZER_CLASS = "org.glassfish.admin.rest.RestLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(RestLogger.class.getName());
   }

   public static String logGenericInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2159700", 64, args, RestLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RestLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2159700";
   }

   public static String logGenericWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2159701", 8, args, RestLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RestLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2159701";
   }

   public static String logGenericError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2159702", 8, args, RestLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RestLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2159702";
   }

   public static String logGenericFatal(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2159703", 4, args, RestLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      RestLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2159703";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "org.glassfish.admin.rest.RestLogLocalizer", RestLogger.class.getClassLoader());
      private MessageLogger messageLogger = RestLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = RestLogger.findMessageLogger();
      }
   }
}
