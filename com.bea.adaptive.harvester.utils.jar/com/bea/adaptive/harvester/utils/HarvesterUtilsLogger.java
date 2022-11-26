package com.bea.adaptive.harvester.utils;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class HarvesterUtilsLogger {
   private static final String LOCALIZER_CLASS = "com.bea.adaptive.harvester.utils.HarvesterUtilsLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(HarvesterUtilsLogger.class.getName());
   }

   public static String logBundleActivated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2011300", 64, args, HarvesterUtilsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HarvesterUtilsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2011300";
   }

   public static String logBundleDeactivated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2011399", 64, args, HarvesterUtilsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      HarvesterUtilsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2011399";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "com.bea.adaptive.harvester.utils.HarvesterUtilsLogLocalizer", HarvesterUtilsLogger.class.getClassLoader());
      private MessageLogger messageLogger = HarvesterUtilsLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = HarvesterUtilsLogger.findMessageLogger();
      }
   }
}
