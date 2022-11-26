package com.bea.adaptive.mbean.typing;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class MBeanTypingUtilLogger {
   private static final String LOCALIZER_CLASS = "com.bea.adaptive.mbean.typing.MBeanTypingUtilLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(MBeanTypingUtilLogger.class.getName());
   }

   public static String logBundleActivated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2012300", 64, args, MBeanTypingUtilLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MBeanTypingUtilLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2012300";
   }

   public static String logProcessMBeans(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2012301", 64, args, MBeanTypingUtilLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MBeanTypingUtilLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2012301";
   }

   public static String logMBeanProcessingComplete(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2012302", 64, args, MBeanTypingUtilLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MBeanTypingUtilLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2012302";
   }

   public static String logMBeanRetrievalErrorFirst(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2012303", 8, args, MBeanTypingUtilLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MBeanTypingUtilLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2012303";
   }

   public static String logMBeanRetrievalErrorSecond(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2012304", 8, args, MBeanTypingUtilLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MBeanTypingUtilLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2012304";
   }

   public static String logConsecutiveMBeanRetrievalErrors(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2012305", 8, args, MBeanTypingUtilLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MBeanTypingUtilLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2012305";
   }

   public static String logErrorHandlingMBeanNotification(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2012306", 8, args, MBeanTypingUtilLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MBeanTypingUtilLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2012306";
   }

   public static String logErrorHandlingMBeanNotification_Warning(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2012307", 16, args, MBeanTypingUtilLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MBeanTypingUtilLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2012307";
   }

   public static String logErrorSchedulingWork(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2012308", 8, args, MBeanTypingUtilLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MBeanTypingUtilLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2012308";
   }

   public static String logMBeanServerCommunicationsError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2012309", 8, args, MBeanTypingUtilLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MBeanTypingUtilLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2012309";
   }

   public static String logBundleDeactivated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2012399", 64, args, MBeanTypingUtilLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MBeanTypingUtilLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2012399";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "com.bea.adaptive.mbean.typing.MBeanTypingUtilLogLocalizer", MBeanTypingUtilLogger.class.getClassLoader());
      private MessageLogger messageLogger = MBeanTypingUtilLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = MBeanTypingUtilLogger.findMessageLogger();
      }
   }
}
