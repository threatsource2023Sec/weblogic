package com.bea.core.descriptor.wl;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class descriptorWlLogger {
   private static final String LOCALIZER_CLASS = "com.bea.core.descriptor.wl.descriptorWlLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(descriptorWlLogger.class.getName());
   }

   public static String logConfigRootNotDirectory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2156001", 16, args, descriptorWlLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      descriptorWlLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156001";
   }

   public static String logDurationNotValid(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2156002", 8, args, descriptorWlLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      descriptorWlLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156002";
   }

   public static Loggable logDurationNotValidLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2156002", 8, args, "com.bea.core.descriptor.wl.descriptorWlLogLocalizer", descriptorWlLogger.MessageLoggerInitializer.INSTANCE.messageLogger, descriptorWlLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLogicalStoreNameNotValid(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156003", 8, args, descriptorWlLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      descriptorWlLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156003";
   }

   public static Loggable logLogicalStoreNameNotValidLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2156003", 8, args, "com.bea.core.descriptor.wl.descriptorWlLogLocalizer", descriptorWlLogger.MessageLoggerInitializer.INSTANCE.messageLogger, descriptorWlLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJndiNameNotValid(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156004", 8, args, descriptorWlLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      descriptorWlLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156004";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "com.bea.core.descriptor.wl.descriptorWlLogLocalizer", descriptorWlLogger.class.getClassLoader());
      private MessageLogger messageLogger = descriptorWlLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = descriptorWlLogger.findMessageLogger();
      }
   }
}
