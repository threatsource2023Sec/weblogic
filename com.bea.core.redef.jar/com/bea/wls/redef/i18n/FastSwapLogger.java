package com.bea.wls.redef.i18n;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class FastSwapLogger {
   private static final String LOCALIZER_CLASS = "com.bea.wls.redef.l10n.FastSwapLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(FastSwapLogger.class.getName());
   }

   public static String logEnableFastSwap(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2154000", 64, args, FastSwapLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      FastSwapLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2154000";
   }

   public static String logFastSwapBegin(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2154001", 64, args, FastSwapLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      FastSwapLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2154001";
   }

   public static String logFastSwapEnd(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2154002", 64, args, FastSwapLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      FastSwapLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2154002";
   }

   public static String logFastSwapFailure(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2154003", 8, args, FastSwapLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      FastSwapLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2154003";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "com.bea.wls.redef.l10n.FastSwapLogLocalizer", FastSwapLogger.class.getClassLoader());
      private MessageLogger messageLogger = FastSwapLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = FastSwapLogger.findMessageLogger();
      }
   }
}
