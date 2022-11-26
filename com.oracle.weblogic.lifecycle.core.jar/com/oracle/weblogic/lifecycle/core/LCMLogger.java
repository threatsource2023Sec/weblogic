package com.oracle.weblogic.lifecycle.core;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class LCMLogger {
   private static final String LOCALIZER_CLASS = "com.oracle.weblogic.lifecycle.core.LCMLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(LCMLogger.class.getName());
   }

   public static String logException(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2166001", 8, args, LCMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LCMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2166001";
   }

   public static String logExceptionLoadingPlugins(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2166002", 8, args, LCMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LCMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2166002";
   }

   public static String logExceptionLCMConfigFile(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2166003", 8, args, LCMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LCMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2166003";
   }

   public static String logExceptionConfigFileWatcher(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2166004", 8, args, LCMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LCMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2166004";
   }

   public static String logExceptionReloadingConfig(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2166005", 8, args, LCMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LCMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2166005";
   }

   public static String logConfigFileValidationException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2166006", 8, args, LCMLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      LCMLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2166006";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "com.oracle.weblogic.lifecycle.core.LCMLogLocalizer", LCMLogger.class.getClassLoader());
      private MessageLogger messageLogger = LCMLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = LCMLogger.findMessageLogger();
      }
   }
}
