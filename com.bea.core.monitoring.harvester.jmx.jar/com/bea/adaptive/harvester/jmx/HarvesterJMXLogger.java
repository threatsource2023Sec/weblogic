package com.bea.adaptive.harvester.jmx;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class HarvesterJMXLogger {
   private static final String LOCALIZER_CLASS = "com.bea.adaptive.harvester.jmx.HarvesterJMXLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(HarvesterJMXLogger.class.getName());
   }

   public static String logBundleActivated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2011100", 64, args, HarvesterJMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HarvesterJMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2011100";
   }

   public static String logUnexpectedException(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2011101", 16, args, HarvesterJMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HarvesterJMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2011101";
   }

   public static String logUnexpectedCondition(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2011102", 16, args, HarvesterJMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HarvesterJMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2011102";
   }

   public static String logServicePrepared(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2011103", 64, args, HarvesterJMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HarvesterJMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2011103";
   }

   public static String logServiceActivated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2011104", 64, args, HarvesterJMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HarvesterJMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2011104";
   }

   public static String logServiceDeactivated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2011105", 64, args, HarvesterJMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HarvesterJMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2011105";
   }

   public static String logBundleDeactivated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2011199", 64, args, HarvesterJMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HarvesterJMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2011199";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "com.bea.adaptive.harvester.jmx.HarvesterJMXLogLocalizer", HarvesterJMXLogger.class.getClassLoader());
      private MessageLogger messageLogger = HarvesterJMXLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = HarvesterJMXLogger.findMessageLogger();
      }
   }
}
