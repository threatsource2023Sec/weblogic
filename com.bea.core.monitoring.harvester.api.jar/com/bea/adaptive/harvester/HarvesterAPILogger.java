package com.bea.adaptive.harvester;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class HarvesterAPILogger {
   private static final String LOCALIZER_CLASS = "com.bea.adaptive.harvester.HarvesterAPILogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(HarvesterAPILogger.class.getName());
   }

   public static String logBundleActivated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2011000", 64, args, HarvesterAPILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HarvesterAPILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2011000";
   }

   public static String logBundleDeactivated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2011099", 64, args, HarvesterAPILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HarvesterAPILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2011099";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "com.bea.adaptive.harvester.HarvesterAPILogLocalizer", HarvesterAPILogger.class.getClassLoader());
      private MessageLogger messageLogger = HarvesterAPILogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = HarvesterAPILogger.findMessageLogger();
      }
   }
}
