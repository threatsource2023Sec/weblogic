package com.bea.common.engine.internal;

import com.bea.common.logger.spi.LoggableMessageSpi;
import com.bea.common.logger.spi.LoggerSpi;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.logging.Loggable;

public class EngineLogger {
   private static final String LOCALIZER_CLASS = "com.bea.common.engine.internal.EngineLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(EngineLogger.class.getName());
   }

   private static void logMessageUsingLoggerSpi(LoggerSpi logger, LoggableMessageSpiImpl message) {
      int severity = message.getSeverity();
      if (severity == 64) {
         logger.info(message);
      } else if (severity != 32 && severity != 16) {
         if (severity == 8) {
            logger.error(message);
         } else if (severity == 2 || severity == 4 || severity == 1) {
            logger.severe(message);
         }
      } else {
         logger.warn(message);
      }

   }

   public static String getServiceInfoNotFoundForShutDown(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097000", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getServiceInfoNotFoundForShutDownLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097000", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getConfigurationProblemsDetected() {
      Object[] args = new Object[0];
      return (new Loggable("097001", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getConfigurationProblemsDetectedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097001", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getConfigurationNotModifiedAfterStart() {
      Object[] args = new Object[0];
      return (new Loggable("097002", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getConfigurationNotModifiedAfterStartLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097002", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getEngineAlreadyStarted() {
      Object[] args = new Object[0];
      return (new Loggable("097004", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEngineAlreadyStartedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097004", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getServiceConfigNotFound() {
      Object[] args = new Object[0];
      return (new Loggable("097005", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getServiceConfigNotFoundLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097005", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getDuplicateServiceName(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097006", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDuplicateServiceNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097006", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getLoggerNotFound() {
      Object[] args = new Object[0];
      return (new Loggable("097007", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getLoggerNotFoundLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097007", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getNoAccessServiceEngineShutdown() {
      Object[] args = new Object[0];
      return (new Loggable("097008", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoAccessServiceEngineShutdownLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097008", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getEngineAlreadyShutdown() {
      Object[] args = new Object[0];
      return (new Loggable("097009", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEngineAlreadyShutdownLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097009", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getServiceNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097010", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getServiceNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097010", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getServiceNotExposed(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097011", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getServiceNotExposedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097011", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getServiceDoesNotImplementServiceLifecycle(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097012", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getServiceDoesNotImplementServiceLifecycleLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097012", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getNullServiceLoaded(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097013", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullServiceLoadedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097013", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getClassloaderNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097014", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getClassloaderNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097014", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getFailureLoadingService(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("097015", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getFailureLoadingServiceLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("097015", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getDependentOnNonExistentService(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("097016", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDependentOnNonExistentServiceLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("097016", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getServiceNameNotSpecified() {
      Object[] args = new Object[0];
      return (new Loggable("097017", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getServiceNameNotSpecifiedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097017", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getDependenciesStillExist(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("097018", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDependenciesStillExistLoggable(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("097018", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getNoDependencies(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097019", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoDependenciesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097019", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getInternalConsistencyFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097020", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInternalConsistencyFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097020", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getManageableServiceNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097021", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getManageableServiceNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097021", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   public static String getServiceNotManageable(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097022", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EngineLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getServiceNotManageableLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097022", 8, args, "com.bea.common.engine.internal.EngineLogLocalizer", EngineLogger.class.getClassLoader());
   }

   private static class LoggableMessageSpiImpl extends CatalogMessage implements LoggableMessageSpi {
      public LoggableMessageSpiImpl(String messageId, int severity, Object[] args, String resourceName, ClassLoader resourceClassLoader) {
         super(messageId, severity, args, resourceName, resourceClassLoader);
      }

      public String getPrefix() {
         return this.getMessageIdPrefix();
      }

      public String getFormattedMessageBody() {
         return this.getMessage();
      }
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private MessageLogger messageLogger = EngineLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = EngineLogger.findMessageLogger();
      }
   }
}
