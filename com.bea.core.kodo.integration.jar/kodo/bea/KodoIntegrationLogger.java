package kodo.bea;

import java.net.URL;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class KodoIntegrationLogger {
   private static final String LOCALIZER_CLASS = "kodo.bea.KodoIntegrationLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(KodoIntegrationLogger.class.getName());
   }

   public static String logExceptionLoadingMessageDictionary(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2004000", 16, args, KodoIntegrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      KodoIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2004000";
   }

   public static String logNoMessageDictionary(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2004001", 16, args, KodoIntegrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      KodoIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2004001";
   }

   public static String logMessageWithoutDictionary(Object arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2004002", 512, args, KodoIntegrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      KodoIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2004002";
   }

   public static String logDuplicateKey(String arg0, String arg1, String arg2, URL arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2004003", 16, args, KodoIntegrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      KodoIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2004003";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "kodo.bea.KodoIntegrationLogLocalizer", KodoIntegrationLogger.class.getClassLoader());
      private MessageLogger messageLogger = KodoIntegrationLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = KodoIntegrationLogger.findMessageLogger();
      }
   }
}
