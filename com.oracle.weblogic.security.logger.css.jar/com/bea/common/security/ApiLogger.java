package com.bea.common.security;

import com.bea.common.logger.spi.LoggableMessageSpi;
import com.bea.common.logger.spi.LoggerSpi;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.logging.Loggable;

public class ApiLogger {
   private static final String LOCALIZER_CLASS = "com.bea.common.security.ApiLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(ApiLogger.class.getName());
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

   public static String getDecimalIsTooShort() {
      Object[] args = new Object[0];
      return (new Loggable("098500", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDecimalIsTooShortLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("098500", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getDecimalIncorrectCharacters() {
      Object[] args = new Object[0];
      return (new Loggable("098501", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDecimalIncorrectCharactersLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("098501", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getHexBinaryBadLength() {
      Object[] args = new Object[0];
      return (new Loggable("098502", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getHexBinaryBadLengthLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("098502", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getInvalidHexDigit() {
      Object[] args = new Object[0];
      return (new Loggable("098503", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidHexDigitLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("098503", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getInvalidRFC822Name(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("098504", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidRFC822NameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("098504", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getIllegalX500Value(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("098505", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalX500ValueLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("098505", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getNotEnoughBASE64Bytes() {
      Object[] args = new Object[0];
      return (new Loggable("098506", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNotEnoughBASE64BytesLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("098506", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getIncorrectXPathVersion() {
      Object[] args = new Object[0];
      return (new Loggable("098507", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIncorrectXPathVersionLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("098507", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getNegativeMonthNonZeroYear() {
      Object[] args = new Object[0];
      return (new Loggable("098508", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNegativeMonthNonZeroYearLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("098508", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getMonthOrYearBothZero() {
      Object[] args = new Object[0];
      return (new Loggable("098509", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMonthOrYearBothZeroLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("098509", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getMethodInternalError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("098510", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMethodInternalErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("098510", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getIllegalTimeZoneComparison() {
      Object[] args = new Object[0];
      return (new Loggable("098511", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalTimeZoneComparisonLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("098511", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getAuditLogTriggerError() {
      Object[] args = new Object[0];
      return (new Loggable("098512", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getAuditLogTriggerErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("098512", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String logServiceInitializationException(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("098513", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "098513";
   }

   public static LoggableMessageSpi logServiceInitializationExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("098513", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getFailedToGenerateThumbprint(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("098514", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getFailedToGenerateThumbprintLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("098514", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getIllegalArgumentSpecified(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return (new Loggable("098515", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalArgumentSpecifiedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("098515", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getCertificateRegistryBuildFailureUnregisteredThumbprint(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("098516", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCertificateRegistryBuildFailureUnregisteredThumbprintLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("098516", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getFailedToGetSAMLAssertionInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("098517", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getFailedToGetSAMLAssertionInfoLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("098517", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getNotSupportAnonymous() {
      Object[] args = new Object[0];
      return (new Loggable("098518", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNotSupportAnonymousLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("098518", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getIllegalFileFormatForFileStore() {
      Object[] args = new Object[0];
      return (new Loggable("098519", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalFileFormatForFileStoreLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("098519", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getUnableToInstantiateCSSDelegate() {
      Object[] args = new Object[0];
      return (new Loggable("098520", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnableToInstantiateCSSDelegateLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("098520", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getInvalidPrincipalClassName(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("098521", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidPrincipalClassNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("098521", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getUnableToInstantiatePrincipal(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("098522", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnableToInstantiatePrincipalLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("098522", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
   }

   public static String getUnableToInstantiatePrincipalConfigurationDelegate(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("098523", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApiLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnableToInstantiatePrincipalConfigurationDelegateLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("098523", 8, args, "com.bea.common.security.ApiLogLocalizer", ApiLogger.class.getClassLoader());
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
      private MessageLogger messageLogger = ApiLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = ApiLogger.findMessageLogger();
      }
   }
}
