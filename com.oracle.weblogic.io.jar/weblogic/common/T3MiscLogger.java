package weblogic.common;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class T3MiscLogger {
   private static final String LOCALIZER_CLASS = "weblogic.common.T3MiscLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(T3MiscLogger.class.getName());
   }

   public static String logDebug(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000700", 128, args, T3MiscLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3MiscLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000700";
   }

   public static String logMount(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000701", 8, args, T3MiscLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3MiscLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000701";
   }

   public static String logUnmount(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000702", 8, args, T3MiscLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3MiscLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000702";
   }

   public static String logGetRoot(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000703", 8, args, T3MiscLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3MiscLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000703";
   }

   public static String logBadCreate(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000704", 8, args, T3MiscLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3MiscLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000704";
   }

   public static String logCreate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000705", 64, args, T3MiscLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3MiscLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000705";
   }

   public static String logFindRemote(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000706", 8, args, T3MiscLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3MiscLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000706";
   }

   public static String logOpenRemote(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000707", 8, args, T3MiscLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3MiscLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000707";
   }

   public static String logWriteTimed(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000708", 8, args, T3MiscLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3MiscLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000708";
   }

   public static String logFlushTimed(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000709", 8, args, T3MiscLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3MiscLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000709";
   }

   public static String logPastTime(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000710", 8, args, T3MiscLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3MiscLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000710";
   }

   public static String logThrowable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000711", 8, args, T3MiscLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3MiscLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000711";
   }

   public static String logExecution(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000712", 8, args, T3MiscLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3MiscLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000712";
   }

   public static String logCloseException(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000713", 8, args, T3MiscLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      T3MiscLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000713";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.common.T3MiscLogLocalizer", T3MiscLogger.class.getClassLoader());
      private MessageLogger messageLogger = T3MiscLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = T3MiscLogger.findMessageLogger();
      }
   }
}
