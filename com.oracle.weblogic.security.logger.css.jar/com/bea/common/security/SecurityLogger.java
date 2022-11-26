package com.bea.common.security;

import com.bea.common.logger.spi.LoggableMessageSpi;
import com.bea.common.logger.spi.LoggerSpi;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.logging.Loggable;

public class SecurityLogger {
   private static final String LOCALIZER_CLASS = "com.bea.common.security.SecurityLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(SecurityLogger.class.getName());
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

   public static String logAuditWriteEventError(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090058", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090058";
   }

   public static LoggableMessageSpi logAuditWriteEventErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090058", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logInvalidPrincipalError(LoggerSpi logger, String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090059", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090059";
   }

   public static LoggableMessageSpi logInvalidPrincipalErrorLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090059", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logAccessDecisionError(LoggerSpi logger, String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090060", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090060";
   }

   public static LoggableMessageSpi logAccessDecisionErrorLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090060", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logDeployableRoleProviderError(LoggerSpi logger, String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090063", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090063";
   }

   public static LoggableMessageSpi logDeployableRoleProviderErrorLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090063", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logDeployableAuthorizationProviderError(LoggerSpi logger, String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090064", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090064";
   }

   public static LoggableMessageSpi logDeployableAuthorizationProviderErrorLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090064", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logInitializingLDIFForProvider(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090074", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090074";
   }

   public static LoggableMessageSpi logInitializingLDIFForProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090074", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logLoadedLDIFForProvider(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090075", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090075";
   }

   public static LoggableMessageSpi logLoadedLDIFForProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090075", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logFailureLoadingLDIFForProvider(LoggerSpi logger, String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090076", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090076";
   }

   public static LoggableMessageSpi logFailureLoadingLDIFForProviderLoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("090076", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logLDIFEmptyForProvider(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090118", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090118";
   }

   public static LoggableMessageSpi logLDIFEmptyForProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090118", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logLDIFNotFoundForProvider(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090119", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090119";
   }

   public static LoggableMessageSpi logLDIFNotFoundForProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090119", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logMisconfiguredWLSProviderUpdateFile(LoggerSpi logger, String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090126", 4, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090126";
   }

   public static LoggableMessageSpi logMisconfiguredWLSProviderUpdateFileLoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("090126", 4, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logUnreadableWLSProviderUpdateFile(LoggerSpi logger, String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090127", 4, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090127";
   }

   public static LoggableMessageSpi logUnreadableWLSProviderUpdateFileLoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("090127", 4, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logUpdatingLDIFForProvider(LoggerSpi logger, String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090129", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090129";
   }

   public static LoggableMessageSpi logUpdatingLDIFForProviderLoggable(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("090129", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logFailureUpdatingLDIFVersion(LoggerSpi logger, String arg0, String arg1, int arg2, Exception arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090130", 4, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090130";
   }

   public static LoggableMessageSpi logFailureUpdatingLDIFVersionLoggable(String arg0, String arg1, int arg2, Exception arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      return new LoggableMessageSpiImpl("090130", 4, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logLoadKeyStoreKeyStoreException(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090131", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090131";
   }

   public static LoggableMessageSpi logLoadKeyStoreKeyStoreExceptionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090131", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logLoadKeyStoreFileNotFoundException(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090132", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090132";
   }

   public static LoggableMessageSpi logLoadKeyStoreFileNotFoundExceptionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090132", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logLoadKeyStoreIOException(LoggerSpi logger, String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090133", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090133";
   }

   public static LoggableMessageSpi logLoadKeyStoreIOExceptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("090133", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logLoadKeyStoreCertificateException(LoggerSpi logger, String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090134", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090134";
   }

   public static LoggableMessageSpi logLoadKeyStoreCertificateExceptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("090134", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logLoadKeyStoreNoSuchAlgorithmException(LoggerSpi logger, String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090135", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090135";
   }

   public static LoggableMessageSpi logLoadKeyStoreNoSuchAlgorithmExceptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("090135", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logFailureSavingLDIFForProvider(LoggerSpi logger, String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090161", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090161";
   }

   public static LoggableMessageSpi logFailureSavingLDIFForProviderLoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("090161", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSavedLDIFForProvider(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090162", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090162";
   }

   public static LoggableMessageSpi logSavedLDIFForProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090162", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logDuplicateLDAPEntryForProvider(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090163", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090163";
   }

   public static LoggableMessageSpi logDuplicateLDAPEntryForProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090163", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getAuditorProviderNotInitialized() {
      Object[] args = new Object[0];
      return (new Loggable("090234", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getAuditorProviderNotInitializedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090234", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidAuditSeverity() {
      Object[] args = new Object[0];
      return (new Loggable("090235", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidAuditSeverityLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090235", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDefAtnConstructorFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090236", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDefAtnConstructorFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090236", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getValidationOldPwFailed() {
      Object[] args = new Object[0];
      return (new Loggable("090237", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getValidationOldPwFailedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090237", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getIATypeCanNotBeNull() {
      Object[] args = new Object[0];
      return (new Loggable("090239", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIATypeCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090239", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getIATokenCanNotBeNull() {
      Object[] args = new Object[0];
      return (new Loggable("090240", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIATokenCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090240", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getIATypeNotConfigured(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090241", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIATypeNotConfiguredLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090241", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnrecognizedIACallback() {
      Object[] args = new Object[0];
      return (new Loggable("090248", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnrecognizedIACallbackLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090248", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCursorIsNull() {
      Object[] args = new Object[0];
      return (new Loggable("090249", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCursorIsNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090249", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCursorNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090250", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCursorNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090250", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getHaveCurrentError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090251", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getHaveCurrentErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090251", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getAdvanceError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090252", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getAdvanceErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090252", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCloseError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090253", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCloseErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090253", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNameCanNotBeNull() {
      Object[] args = new Object[0];
      return (new Loggable("090254", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNameCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090254", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUserOrGroupNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090255", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUserOrGroupNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090255", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getErrorSettingDesc(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090256", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getErrorSettingDescLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090256", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getGroupCanNotBeNull() {
      Object[] args = new Object[0];
      return (new Loggable("090257", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getGroupCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090257", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getMaxCanNotBeLessThanZero() {
      Object[] args = new Object[0];
      return (new Loggable("090258", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMaxCanNotBeLessThanZeroLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090258", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getGroupNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090259", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getGroupNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090259", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getErrorListingGroupMembers(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090260", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getErrorListingGroupMembersLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090260", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getWildcardCanNotBeNull() {
      Object[] args = new Object[0];
      return (new Loggable("090261", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getWildcardCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090261", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getErrorListingGroups(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090262", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getErrorListingGroupsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090262", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getParentGroupCanNotBeNull() {
      Object[] args = new Object[0];
      return (new Loggable("090263", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getParentGroupCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090263", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getMemberCanNotBeNull() {
      Object[] args = new Object[0];
      return (new Loggable("090264", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMemberCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090264", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getMemberCanNotBeParent() {
      Object[] args = new Object[0];
      return (new Loggable("090265", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMemberCanNotBeParentLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090265", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getGroupNameCanNotBeNull() {
      Object[] args = new Object[0];
      return (new Loggable("090266", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getGroupNameCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090266", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getGroupAlreadyExists(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090267", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getGroupAlreadyExistsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090267", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUserAlreadyExistsGroup(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090268", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUserAlreadyExistsGroupLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090268", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getErrorAddingGroup(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090269", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getErrorAddingGroupLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090269", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getGroupCanNotBeSameAsMember() {
      Object[] args = new Object[0];
      return (new Loggable("090270", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getGroupCanNotBeSameAsMemberLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090270", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getMemberNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090271", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMemberNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090271", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getErrorAddingMemberToGroup(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090272", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getErrorAddingMemberToGroupLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090272", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getMemberCanNotBeGroup() {
      Object[] args = new Object[0];
      return (new Loggable("090273", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMemberCanNotBeGroupLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090273", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getErrorRemovingMemberFromGroup(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090274", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getErrorRemovingMemberFromGroupLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090274", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getErrorRemovingGroup(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090275", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getErrorRemovingGroupLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090275", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getErrorRemovingGroupInvCursor(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090276", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getErrorRemovingGroupInvCursorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090276", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getErrorDestroyingProvider() {
      Object[] args = new Object[0];
      return (new Loggable("090277", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getErrorDestroyingProviderLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090277", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getErrorListingMemberGroups(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090278", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getErrorListingMemberGroupsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090278", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getErrorListingUsers(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090279", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getErrorListingUsersLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090279", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUserCanNotBeNull() {
      Object[] args = new Object[0];
      return (new Loggable("090280", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUserCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090280", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUserNameCanNotBeNull() {
      Object[] args = new Object[0];
      return (new Loggable("090281", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUserNameCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090281", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getPasswordCanNotBeNull() {
      Object[] args = new Object[0];
      return (new Loggable("090282", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPasswordCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090282", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUserAlreadyExists(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090283", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUserAlreadyExistsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090283", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getGroupAlreadyExistsUser(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090284", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getGroupAlreadyExistsUserLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090284", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getPasswordMinLength(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090285", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPasswordMinLengthLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090285", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getErrorAddingUser(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090286", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getErrorAddingUserLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090286", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUserNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090287", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUserNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090287", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidPasswordForUser(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090288", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidPasswordForUserLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090288", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getErrorChangingPassword(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090289", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getErrorChangingPasswordLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090289", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getErrorRemovingUser(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090290", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getErrorRemovingUserLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090290", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvSearchScope(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090291", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvSearchScopeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090291", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getErrorCreatingProvider() {
      Object[] args = new Object[0];
      return (new Loggable("090293", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getErrorCreatingProviderLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090293", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCouldNotGetConnection() {
      Object[] args = new Object[0];
      return (new Loggable("090294", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCouldNotGetConnectionLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090294", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCaughtUnexpectedExc() {
      Object[] args = new Object[0];
      return (new Loggable("090295", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCaughtUnexpectedExcLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090295", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidURL(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090296", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidURLLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090296", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNoCallbackHdlrSpecified() {
      Object[] args = new Object[0];
      return (new Loggable("090297", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoCallbackHdlrSpecifiedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090297", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNoDelegateSpecified() {
      Object[] args = new Object[0];
      return (new Loggable("090298", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoDelegateSpecifiedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090298", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUsernameNotSupplied() {
      Object[] args = new Object[0];
      return (new Loggable("090299", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUsernameNotSuppliedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090299", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUserDoesNotExist(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090300", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUserDoesNotExistLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090300", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getPasswordNotSupplied() {
      Object[] args = new Object[0];
      return (new Loggable("090301", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPasswordNotSuppliedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090301", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUserDenied(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090302", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUserDeniedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090302", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getAuthFailedLDAP(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090303", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getAuthFailedLDAPLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090303", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getAuthFailedExc(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090304", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getAuthFailedExcLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090304", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getAuthFailedNotFnd(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090305", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getAuthFailedNotFndLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090305", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getAuthFailedGroupsExc(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090306", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getAuthFailedGroupsExcLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090306", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDefAuthImplConstrFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090307", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDefAuthImplConstrFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090307", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getValidationOldPasswordFailed() {
      Object[] args = new Object[0];
      return (new Loggable("090308", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getValidationOldPasswordFailedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090308", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getFailedToCreateResource() {
      Object[] args = new Object[0];
      return (new Loggable("090310", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getFailedToCreateResourceLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090310", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getFailedToSetResource() {
      Object[] args = new Object[0];
      return (new Loggable("090311", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getFailedToSetResourceLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090311", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getPolicyRemovalError() {
      Object[] args = new Object[0];
      return (new Loggable("090313", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPolicyRemovalErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090313", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnableToConnectLDAP(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090314", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnableToConnectLDAPLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090314", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDefAuthImplInitFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090315", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDefAuthImplInitFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090315", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNullHelper() {
      Object[] args = new Object[0];
      return (new Loggable("090316", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullHelperLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090316", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getBugInPredicateArg() {
      Object[] args = new Object[0];
      return (new Loggable("090317", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getBugInPredicateArgLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090317", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDefRoleMapperInitFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090318", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDefRoleMapperInitFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090318", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnknownResourceType(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090319", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnknownResourceTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090319", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getFailedToCreateRole() {
      Object[] args = new Object[0];
      return (new Loggable("090320", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getFailedToCreateRoleLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090320", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getFailedToSetRoleExpr() {
      Object[] args = new Object[0];
      return (new Loggable("090321", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getFailedToSetRoleExprLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090321", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getRoleRemovalError() {
      Object[] args = new Object[0];
      return (new Loggable("090322", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getRoleRemovalErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090322", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDefCredMapperInitFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090324", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDefCredMapperInitFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090324", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getListCredentialsLDAPExc() {
      Object[] args = new Object[0];
      return (new Loggable("090326", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getListCredentialsLDAPExcLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090326", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNoSuchCredListCursorAvail() {
      Object[] args = new Object[0];
      return (new Loggable("090327", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoSuchCredListCursorAvailLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090327", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getListMappingsGotLDAPExc() {
      Object[] args = new Object[0];
      return (new Loggable("090329", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getListMappingsGotLDAPExcLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090329", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNoSuchResourceMapsCursor() {
      Object[] args = new Object[0];
      return (new Loggable("090330", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoSuchResourceMapsCursorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090330", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNoSuchResourceMapsCursorRemoteUser() {
      Object[] args = new Object[0];
      return (new Loggable("090331", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoSuchResourceMapsCursorRemoteUserLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090331", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getRemoteUserNameGotLDAPExc() {
      Object[] args = new Object[0];
      return (new Loggable("090332", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getRemoteUserNameGotLDAPExcLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090332", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getRemoteUserPasswordGotLDAPExc() {
      Object[] args = new Object[0];
      return (new Loggable("090333", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getRemoteUserPasswordGotLDAPExcLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090333", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getSetUserPasswordCredGotLDAPExc() {
      Object[] args = new Object[0];
      return (new Loggable("090334", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSetUserPasswordCredGotLDAPExcLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090334", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getSetUserPasswordCredMapGotLDAPExc() {
      Object[] args = new Object[0];
      return (new Loggable("090335", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSetUserPasswordCredMapGotLDAPExcLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090335", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getRemoveUserPasswordCredGotLDAPExc() {
      Object[] args = new Object[0];
      return (new Loggable("090336", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getRemoveUserPasswordCredGotLDAPExcLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090336", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getRemoveUserPwCredMapGotLDAPExc() {
      Object[] args = new Object[0];
      return (new Loggable("090337", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getRemoveUserPwCredMapGotLDAPExcLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090337", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCouldNotCreateAuditLogFileExc(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090339", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCouldNotCreateAuditLogFileExcLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090339", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvResourceInitNYI() {
      Object[] args = new Object[0];
      return (new Loggable("090367", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvResourceInitNYILoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090367", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNullTokenTypeParam() {
      Object[] args = new Object[0];
      return (new Loggable("090375", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullTokenTypeParamLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090375", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getIdentityAssertionFailedExc(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090377", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIdentityAssertionFailedExcLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090377", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getIAHdlrUnsupTokenType(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090380", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIAHdlrUnsupTokenTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090380", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getSecServiceNotYetInit(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090393", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSecServiceNotYetInitLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090393", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getLocationNullOrEmpty() {
      Object[] args = new Object[0];
      return (new Loggable("090442", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getLocationNullOrEmptyLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090442", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNullFile() {
      Object[] args = new Object[0];
      return (new Loggable("090443", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullFileLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090443", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidFlagValue(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090447", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidFlagValueLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090447", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidFormat(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090448", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidFormatLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090448", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidConstraints() {
      Object[] args = new Object[0];
      return (new Loggable("090449", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidConstraintsLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090449", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidNameSupplied() {
      Object[] args = new Object[0];
      return (new Loggable("090450", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidNameSuppliedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090450", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnableToReadFile(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090451", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnableToReadFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090451", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getImportFileError() {
      Object[] args = new Object[0];
      return (new Loggable("090453", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getImportFileErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090453", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getExportFileError() {
      Object[] args = new Object[0];
      return (new Loggable("090455", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getExportFileErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090455", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidFileFormat() {
      Object[] args = new Object[0];
      return (new Loggable("090469", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidFileFormatLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090469", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDefAuthImplSearchFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090473", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDefAuthImplSearchFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090473", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCreateTempFileFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090474", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCreateTempFileFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090474", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logStackTrace(LoggerSpi logger, Throwable arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090511", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090511";
   }

   public static LoggableMessageSpi logStackTraceLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090511", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logAuditProviderFailedToRegisterOnMBean(LoggerSpi logger) {
      Object[] args = new Object[0];
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090512", 4, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090512";
   }

   public static LoggableMessageSpi logAuditProviderFailedToRegisterOnMBeanLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090512", 4, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logLDAPPreviouslyInitialized(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090516", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090516";
   }

   public static LoggableMessageSpi logLDAPPreviouslyInitializedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090516", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNoCircularGroupMembership(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090517", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoCircularGroupMembershipLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090517", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDefRoleMapImplSearchFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090545", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDefRoleMapImplSearchFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090545", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logInvalidPropertyValue(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090553", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090553";
   }

   public static LoggableMessageSpi logInvalidPropertyValueLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090553", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logDefaultCredentialMapperGetCredentialsFailure(LoggerSpi logger, String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090555", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090555";
   }

   public static LoggableMessageSpi logDefaultCredentialMapperGetCredentialsFailureLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      return new LoggableMessageSpiImpl("090555", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getEndCertificateSelectorIllegalCertificate() {
      Object[] args = new Object[0];
      return (new Loggable("090583", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEndCertificateSelectorIllegalCertificateLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090583", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getIssuerDNSerialNumberSelectorIllegalIssuerDN() {
      Object[] args = new Object[0];
      return (new Loggable("090584", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIssuerDNSerialNumberSelectorIllegalIssuerDNLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090584", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getIssuerDNSerialNumberSelectorIllegalSerialNumber() {
      Object[] args = new Object[0];
      return (new Loggable("090585", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIssuerDNSerialNumberSelectorIllegalSerialNumberLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090585", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getSubjectDNSelectorIllegalSubjectDN() {
      Object[] args = new Object[0];
      return (new Loggable("090586", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSubjectDNSelectorIllegalSubjectDNLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090586", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getSubjectKeyIdentifierSelectorIllegalSubjectKeyIdentifier() {
      Object[] args = new Object[0];
      return (new Loggable("090587", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSubjectKeyIdentifierSelectorIllegalSubjectKeyIdentifierLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090587", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getWLSCertPathBuilderResultIllegalCertPath() {
      Object[] args = new Object[0];
      return (new Loggable("090588", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getWLSCertPathBuilderResultIllegalCertPathLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090588", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidRealmIllegalCertPathBuilderWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090593", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidRealmIllegalCertPathBuilderWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090593", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getIllegalCertPathBuilderParametersSpi(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090594", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalCertPathBuilderParametersSpiLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090594", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getIllegalCertPathValidatorParametersSpi(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090595", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalCertPathValidatorParametersSpiLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090595", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getWebLogicCertPathProviderIllegalCertPathSelector() {
      Object[] args = new Object[0];
      return (new Loggable("090596", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getWebLogicCertPathProviderIllegalCertPathSelectorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090596", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCertificateRegistryIllegalCertPathSelector() {
      Object[] args = new Object[0];
      return (new Loggable("090597", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCertificateRegistryIllegalCertPathSelectorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090597", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getWLCPPCannotCompleteChain(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090603", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getWLCPPCannotCompleteChainLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090603", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getWLCPPNoTrustedCA() {
      Object[] args = new Object[0];
      return (new Loggable("090604", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getWLCPPNoTrustedCALoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090604", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getWLCPPCertNotCurrentlyValid(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090605", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getWLCPPCertNotCurrentlyValidLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090605", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getWLCPPDNMismatch(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090606", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getWLCPPDNMismatchLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090606", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getWLCPPEncodingError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090607", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getWLCPPEncodingErrorLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090607", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getWLCPPInvalidKeyError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090608", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getWLCPPInvalidKeyErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090608", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getWLCPPNoSuchAlgorithmError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090609", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getWLCPPNoSuchAlgorithmErrorLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090609", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getWLCPPNoDefaultProviderError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090610", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getWLCPPNoDefaultProviderErrorLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090610", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getWLCPPInvalidSignatureError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090611", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getWLCPPInvalidSignatureErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090611", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getWLCPPUnsupportedCriticalExtensionError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090612", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getWLCPPUnsupportedCriticalExtensionErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090612", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getWLCPPBasicConstraintsViolatedError(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return (new Loggable("090613", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getWLCPPBasicConstraintsViolatedErrorLoggable(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("090613", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getWLCPPMissingBasicConstraintsError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090614", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getWLCPPMissingBasicConstraintsErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090614", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCertPathManagerNullTrustedCAError() {
      Object[] args = new Object[0];
      return (new Loggable("090615", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCertPathManagerNullTrustedCAErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090615", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCertPathBuilderProviderReturnedEmptyCertPathError() {
      Object[] args = new Object[0];
      return (new Loggable("090616", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCertPathBuilderProviderReturnedEmptyCertPathErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090616", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCertPathValidatorEmptyCertPathError() {
      Object[] args = new Object[0];
      return (new Loggable("090618", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCertPathValidatorEmptyCertPathErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090618", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getJDKCertPathBuilderNotFoundError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090620", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getJDKCertPathBuilderNotFoundErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090620", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getJDKCertPathValidatorNotFoundError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090621", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getJDKCertPathValidatorNotFoundErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090621", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getGetDefaultTrustedCAsError(Exception arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090623", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getGetDefaultTrustedCAsErrorLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090623", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getX509CreateCertPathError(Exception arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090624", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getX509CreateCertPathErrorLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090624", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDefAuthImplNoSearchResults() {
      Object[] args = new Object[0];
      return (new Loggable("090635", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDefAuthImplNoSearchResultsLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090635", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDefRoleMapImplNoSearchResults() {
      Object[] args = new Object[0];
      return (new Loggable("090636", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDefRoleMapImplNoSearchResultsLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090636", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNoRoleNameSupplied() {
      Object[] args = new Object[0];
      return (new Loggable("090637", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoRoleNameSuppliedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090637", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNoResourceData() {
      Object[] args = new Object[0];
      return (new Loggable("090638", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoResourceDataLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090638", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNoResourceType() {
      Object[] args = new Object[0];
      return (new Loggable("090639", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoResourceTypeLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090639", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNoResourceIdentifier() {
      Object[] args = new Object[0];
      return (new Loggable("090640", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoResourceIdentifierLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090640", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNoResourceKeysFound() {
      Object[] args = new Object[0];
      return (new Loggable("090641", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoResourceKeysFoundLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090641", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidDataTypeForResourceKey(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090642", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidDataTypeForResourceKeyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090642", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidResourceType(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090643", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidResourceTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090643", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getExpectedResourceType(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090644", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getExpectedResourceTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090644", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getExpectedResourceKey(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090645", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getExpectedResourceKeyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090645", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnknownResourceKey(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090646", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnknownResourceKeyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090646", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidApplicationName() {
      Object[] args = new Object[0];
      return (new Loggable("090647", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidApplicationNameLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090647", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidComponentName() {
      Object[] args = new Object[0];
      return (new Loggable("090648", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidComponentNameLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090648", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidApplicationSearchName() {
      Object[] args = new Object[0];
      return (new Loggable("090649", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidApplicationSearchNameLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090649", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidComponentSearchName() {
      Object[] args = new Object[0];
      return (new Loggable("090650", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidComponentSearchNameLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090650", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNoComponentType() {
      Object[] args = new Object[0];
      return (new Loggable("090651", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoComponentTypeLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090651", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidComponentType(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090652", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidComponentTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090652", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getEmptyArrayValueFound() {
      Object[] args = new Object[0];
      return (new Loggable("090653", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyArrayValueFoundLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090653", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnexpectedResourceIdData(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090654", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnexpectedResourceIdDataLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090654", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnexpectedResourceKeyArrayValue(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090655", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnexpectedResourceKeyArrayValueLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090655", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getEmptyResourceKeyString(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090656", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyResourceKeyStringLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090656", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getEmptyResourceKeyArrayString(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090657", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyResourceKeyArrayStringLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090657", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logDeprecatedDeployableAuthorizer(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090662", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090662";
   }

   public static LoggableMessageSpi logDeprecatedDeployableAuthorizerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090662", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logDeprecatedDeployableRoleMapper(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090663", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090663";
   }

   public static LoggableMessageSpi logDeprecatedDeployableRoleMapperLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090663", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDeployHandleNotSupplied() {
      Object[] args = new Object[0];
      return (new Loggable("090666", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDeployHandleNotSuppliedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090666", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getApplicationInformationNotSupplied() {
      Object[] args = new Object[0];
      return (new Loggable("090667", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getApplicationInformationNotSuppliedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090667", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnableToGetDigest(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090671", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnableToGetDigestLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090671", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCertificateRegistryBuildFailureUnregisteredCertificate(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090683", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCertificateRegistryBuildFailureUnregisteredCertificateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090683", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCertificateRegistryBuildFailureUnregisteredSubjectDN(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090684", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCertificateRegistryBuildFailureUnregisteredSubjectDNLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090684", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCertificateRegistryBuildFailureUnregisteredIssuerDNAndSerialNumber(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090685", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCertificateRegistryBuildFailureUnregisteredIssuerDNAndSerialNumberLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090685", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCertificateRegistryBuildFailureUnregisteredSubjectKeyIdentifier(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090686", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCertificateRegistryBuildFailureUnregisteredSubjectKeyIdentifierLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090686", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCertificateRegistryValidateFailureUnregisteredCertificate(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090687", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCertificateRegistryValidateFailureUnregisteredCertificateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090687", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getMaximumListersExceeded(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090688", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMaximumListersExceededLoggable(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090688", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getEmptyOrNullCertificateAlias() {
      Object[] args = new Object[0];
      return (new Loggable("090689", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyOrNullCertificateAliasLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090689", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getEmptyOrNullFileName() {
      Object[] args = new Object[0];
      return (new Loggable("090690", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyOrNullFileNameLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090690", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getEmptyOrNullCertificateAliasWildcard() {
      Object[] args = new Object[0];
      return (new Loggable("090691", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyOrNullCertificateAliasWildcardLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090691", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getMaximumToReturnCanNotBeLessThanZero() {
      Object[] args = new Object[0];
      return (new Loggable("090692", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMaximumToReturnCanNotBeLessThanZeroLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090692", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCertificateAliasNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090693", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCertificateAliasNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090693", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCertificateAliasAlreadyExists(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090694", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCertificateAliasAlreadyExistsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090694", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getSubjectDNAlreadyExists(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090695", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSubjectDNAlreadyExistsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090695", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getIssuerDNAndSerialNumberAlreadyExists(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return (new Loggable("090696", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIssuerDNAndSerialNumberAlreadyExistsLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("090696", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getSubjectKeyIdentifierAlreadyExists(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090697", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSubjectKeyIdentifierAlreadyExistsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090697", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getImportErrors() {
      Object[] args = new Object[0];
      return (new Loggable("090698", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getImportErrorsLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090698", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getExportErrors() {
      Object[] args = new Object[0];
      return (new Loggable("090699", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getExportErrorsLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090699", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnableToWriteFileError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090710", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnableToWriteFileErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090710", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnableToReadFileError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090711", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnableToReadFileErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090711", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnableToReadJKSKeyStoreError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090712", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnableToReadJKSKeyStoreErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090712", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnableToReadCertificateFromPEMorDERError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090713", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnableToReadCertificateFromPEMorDERErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090713", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getChallengeNotCompleted() {
      Object[] args = new Object[0];
      return (new Loggable("090724", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getChallengeNotCompletedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090724", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logCredMapperUnexpectedException(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090726", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090726";
   }

   public static LoggableMessageSpi logCredMapperUnexpectedExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090726", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logFailedToReadCredential(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090728", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090728";
   }

   public static LoggableMessageSpi logFailedToReadCredentialLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090728", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnableToRetrieveQuery() {
      Object[] args = new Object[0];
      return (new Loggable("090731", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnableToRetrieveQueryLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090731", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidDataSourceName(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090732", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidDataSourceNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090732", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSConnectionPoolNotUsable() {
      Object[] args = new Object[0];
      return (new Loggable("090734", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSConnectionPoolNotUsableLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090734", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSConnectionNotUsable() {
      Object[] args = new Object[0];
      return (new Loggable("090735", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSConnectionNotUsableLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090735", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSConfigurationValidationException(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090736", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSConfigurationValidationExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090736", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSConfigurationValidationConnectionException() {
      Object[] args = new Object[0];
      return (new Loggable("090737", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSConfigurationValidationConnectionExceptionLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090737", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getHashAlgorithmNotUsable(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090738", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getHashAlgorithmNotUsableLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090738", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getSQLValidationFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090739", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSQLValidationFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090739", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSPluginException(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090740", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSPluginExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090740", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSPluginSQLException(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090741", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSPluginSQLExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090741", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSPluginFailedToLoad() {
      Object[] args = new Object[0];
      return (new Loggable("090742", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSPluginFailedToLoadLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090742", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSPluginLoadException(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090743", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSPluginLoadExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090743", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSPluginLoadClassNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090744", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSPluginLoadClassNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090744", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSPluginLoadBadClass(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090745", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSPluginLoadBadClassLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090745", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSPluginLoadIllegalAccessClass(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090746", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSPluginLoadIllegalAccessClassLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090746", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSPluginInstantiateClass(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090747", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSPluginInstantiateClassLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090747", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSConnectionCloseFailed() {
      Object[] args = new Object[0];
      return (new Loggable("090748", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSConnectionCloseFailedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090748", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSFailedToRetrievePreparedStatement(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090751", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSFailedToRetrievePreparedStatementLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090751", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSUnknownPasswordType() {
      Object[] args = new Object[0];
      return (new Loggable("090752", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSUnknownPasswordTypeLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090752", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getHashAlgorithmNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090753", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getHashAlgorithmNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090753", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSLoginModuleNotInitialized() {
      Object[] args = new Object[0];
      return (new Loggable("090754", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSLoginModuleNotInitializedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090754", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSConnectionNotAvailableFromPool() {
      Object[] args = new Object[0];
      return (new Loggable("090755", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSConnectionNotAvailableFromPoolLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090755", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSUnableToQueryInformation(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090756", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSUnableToQueryInformationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090756", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSExceptionFormattingUser() {
      Object[] args = new Object[0];
      return (new Loggable("090757", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSExceptionFormattingUserLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090757", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSExceptionFormattingGroups() {
      Object[] args = new Object[0];
      return (new Loggable("090758", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSExceptionFormattingGroupsLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090758", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSSQLException(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090759", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSSQLExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090759", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSUserNotUnique(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090760", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSUserNotUniqueLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090760", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSAuthenticationFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090761", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSAuthenticationFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090761", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSLoginModuleException() {
      Object[] args = new Object[0];
      return (new Loggable("090762", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSLoginModuleExceptionLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090762", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getAppVersionCreatePolicyError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090765", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getAppVersionCreatePolicyErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090765", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getAppVersionCreateRoleError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090766", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getAppVersionCreateRoleErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090766", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getAppVersionCreateCredMapError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090767", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getAppVersionCreateCredMapErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090767", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCredMapRemovalError() {
      Object[] args = new Object[0];
      return (new Loggable("090768", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCredMapRemovalErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090768", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logTokenGroupsAttributeNotAccessable(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090769", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090769";
   }

   public static LoggableMessageSpi logTokenGroupsAttributeNotAccessableLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090769", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logInsufficientActiveTypesForNegotiation(LoggerSpi logger) {
      Object[] args = new Object[0];
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090771", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090771";
   }

   public static LoggableMessageSpi logInsufficientActiveTypesForNegotiationLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090771", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logUnableToGenerateSAMLAssertion(LoggerSpi logger, String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090776", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090776";
   }

   public static LoggableMessageSpi logUnableToGenerateSAMLAssertionLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090776", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logUnableToGenerateSignedSAMLAssertion(LoggerSpi logger, String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090777", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090777";
   }

   public static LoggableMessageSpi logUnableToGenerateSignedSAMLAssertionLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090777", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnexpectedCallbackFound(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090781", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnexpectedCallbackFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090781", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logDuplicateMembershipDetected(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090784", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090784";
   }

   public static LoggableMessageSpi logDuplicateMembershipDetectedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090784", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNoIdentityOrContinuation() {
      Object[] args = new Object[0];
      return (new Loggable("090785", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoIdentityOrContinuationLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090785", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnexpectedTokenFormatError() {
      Object[] args = new Object[0];
      return (new Loggable("090786", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnexpectedTokenFormatErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090786", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNullDataSourceName() {
      Object[] args = new Object[0];
      return (new Loggable("090787", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullDataSourceNameLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090787", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnableToLocateDataSourceConfig(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090788", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnableToLocateDataSourceConfigLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090788", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSUnexpectedAuthenticationException() {
      Object[] args = new Object[0];
      return (new Loggable("090789", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSUnexpectedAuthenticationExceptionLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090789", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSBinaryResultTypeNotAllowed(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090790", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSBinaryResultTypeNotAllowedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090790", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSNotExpectedResultType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090791", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSNotExpectedResultTypeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090791", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSParameterNotExpectedCharType(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090792", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSParameterNotExpectedCharTypeLoggable(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090792", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnableToAcceptKrbSecContext() {
      Object[] args = new Object[0];
      return (new Loggable("090793", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnableToAcceptKrbSecContextLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090793", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSNoHashAlgorithmSpecified() {
      Object[] args = new Object[0];
      return (new Loggable("090794", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSNoHashAlgorithmSpecifiedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090794", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSNoResultColumnFound(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090795", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSNoResultColumnFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090795", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSNumberOfParametersNotCorrect(int arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return (new Loggable("090796", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSNumberOfParametersNotCorrectLoggable(int arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("090796", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDBMSInvalidCharacterInput(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090798", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBMSInvalidCharacterInputLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090798", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getPKICredMapperInitFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090799", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPKICredMapperInitFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090799", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getValueNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090800", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getValueNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090800", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getValueNotFoundResource(String arg0, String arg1, boolean arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      return (new Loggable("090801", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getValueNotFoundResourceLoggable(String arg0, String arg1, boolean arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      return new LoggableMessageSpiImpl("090801", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getSetKeyPairMap() {
      Object[] args = new Object[0];
      return (new Loggable("090802", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSetKeyPairMapLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090802", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getSetPublicCertMap() {
      Object[] args = new Object[0];
      return (new Loggable("090803", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSetPublicCertMapLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090803", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logRemoveCredential(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090804", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090804";
   }

   public static LoggableMessageSpi logRemoveCredentialLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090804", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logFailedToReadCredentialResource(LoggerSpi logger, String arg0, String arg1, boolean arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090805", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090805";
   }

   public static LoggableMessageSpi logFailedToReadCredentialResourceLoggable(String arg0, String arg1, boolean arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      return new LoggableMessageSpiImpl("090805", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getWrongKeyStoreConfiguration(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090806", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getWrongKeyStoreConfigurationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090806", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getKeyStoreConfigurationIncorrect(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return (new Loggable("090807", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getKeyStoreConfigurationIncorrectLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("090807", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getKSAliasNotPresent(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090808", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getKSAliasNotPresentLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090808", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getKeyPairNotRetrieved(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090809", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getKeyPairNotRetrievedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090809", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getKSAliasNotCerEntry(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090810", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getKSAliasNotCerEntryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090810", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logLoadKeyStoreException(LoggerSpi logger, String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090812", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090812";
   }

   public static LoggableMessageSpi logLoadKeyStoreExceptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("090812", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNoSuchResourceMapsCursorResourceID() {
      Object[] args = new Object[0];
      return (new Loggable("090813", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoSuchResourceMapsCursorResourceIDLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090813", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getPKIKeystorePasswordNotCorrect() {
      Object[] args = new Object[0];
      return (new Loggable("090815", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPKIKeystorePasswordNotCorrectLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090815", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logErrorCreatingSecurityConfigurationRuntime(LoggerSpi logger, Throwable arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090816", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090816";
   }

   public static LoggableMessageSpi logErrorCreatingSecurityConfigurationRuntimeLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090816", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCannotActivateChangesNoDefaultRealmError() {
      Object[] args = new Object[0];
      return (new Loggable("090817", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCannotActivateChangesNoDefaultRealmErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090817", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnexpectedNullVariable(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090820", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnexpectedNullVariableLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090820", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCertPathBuilderProviderUnorderedCertPathError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090821", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCertPathBuilderProviderUnorderedCertPathErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090821", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logCouldNotGenerateSAMLAssertion(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090824", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090824";
   }

   public static LoggableMessageSpi logCouldNotGenerateSAMLAssertionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090824", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logLDIFEmptyForCredentialProvider(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090827", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090827";
   }

   public static LoggableMessageSpi logLDIFEmptyForCredentialProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090827", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getLDAPX509IATokenTypeCanNotBeNull() {
      Object[] args = new Object[0];
      return (new Loggable("090828", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getLDAPX509IATokenTypeCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090828", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getLDAPX509IATokenCanNotBeNull() {
      Object[] args = new Object[0];
      return (new Loggable("090829", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getLDAPX509IATokenCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090829", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getLDAPX509IATokenTypeInCorrect(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090830", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getLDAPX509IATokenTypeInCorrectLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090830", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getLDAPX509UnrecognizedIACallback() {
      Object[] args = new Object[0];
      return (new Loggable("090831", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getLDAPX509UnrecognizedIACallbackLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090831", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNoMatchingCertificatesinDLAPDirectory() {
      Object[] args = new Object[0];
      return (new Loggable("090832", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoMatchingCertificatesinDLAPDirectoryLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090832", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNoCertificatesinDLAPDirectory() {
      Object[] args = new Object[0];
      return (new Loggable("090833", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoCertificatesinDLAPDirectoryLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090833", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNoLDAPConnection() {
      Object[] args = new Object[0];
      return (new Loggable("090834", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoLDAPConnectionLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090834", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getReqParamNotSuppliedPKIMappingManagementOps() {
      Object[] args = new Object[0];
      return (new Loggable("090835", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getReqParamNotSuppliedPKIMappingManagementOpsLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090835", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logPKIProviderKeyStoreDoesntExist(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090836", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090836";
   }

   public static LoggableMessageSpi logPKIProviderKeyStoreDoesntExistLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090836", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getPKIKeystoreLocationNullOrEmpty() {
      Object[] args = new Object[0];
      return (new Loggable("090837", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPKIKeystoreLocationNullOrEmptyLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090837", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSAMLServletThrowable(LoggerSpi logger, String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090841", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090841";
   }

   public static LoggableMessageSpi logSAMLServletThrowableLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090841", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSAMLAuthFilterThrowable(LoggerSpi logger, Throwable arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090842", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090842";
   }

   public static LoggableMessageSpi logSAMLAuthFilterThrowableLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090842", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSAMLProviderNullArg(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090843", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090843";
   }

   public static LoggableMessageSpi logSAMLProviderNullArgLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090843", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSAMLProviderBadParamContext(LoggerSpi logger, String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090844", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090844";
   }

   public static LoggableMessageSpi logSAMLProviderBadParamContextLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      return new LoggableMessageSpiImpl("090844", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSAMLProviderMissingParamContext(LoggerSpi logger, String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090845", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090845";
   }

   public static LoggableMessageSpi logSAMLProviderMissingParamContextLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("090845", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSAMLProviderBadParam(LoggerSpi logger, String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090846", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090846";
   }

   public static LoggableMessageSpi logSAMLProviderBadParamLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("090846", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSAMLProviderMissingParam(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090847", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090847";
   }

   public static LoggableMessageSpi logSAMLProviderMissingParamLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090847", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSAMLProviderInitException(LoggerSpi logger, String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090848", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090848";
   }

   public static LoggableMessageSpi logSAMLProviderInitExceptionLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090848", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSAMLProviderInitXMLFail(LoggerSpi logger, String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090849", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090849";
   }

   public static LoggableMessageSpi logSAMLProviderInitXMLFailLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090849", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSAMLProviderInitialized(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090851", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090851";
   }

   public static LoggableMessageSpi logSAMLProviderInitializedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090851", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSAMLAssertionCacheInitFail(LoggerSpi logger, String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090853", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090853";
   }

   public static LoggableMessageSpi logSAMLAssertionCacheInitFailLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090853", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSAMLProviderNoCredentials(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090854", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090854";
   }

   public static LoggableMessageSpi logSAMLProviderNoCredentialsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090854", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSAMLProviderBadUpdate(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090856", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090856";
   }

   public static LoggableMessageSpi logSAMLProviderBadUpdateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090856", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSAMLProviderUpdateListenerFail(LoggerSpi logger, String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090857", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090857";
   }

   public static LoggableMessageSpi logSAMLProviderUpdateListenerFailLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090857", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSAMLCredentialMapperCacheException(LoggerSpi logger, Throwable arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090863", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090863";
   }

   public static LoggableMessageSpi logSAMLCredentialMapperCacheExceptionLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090863", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logPolicyConsumerProviderError(LoggerSpi logger, String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090869", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090869";
   }

   public static LoggableMessageSpi logPolicyConsumerProviderErrorLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090869", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logRoleConsumerProviderError(LoggerSpi logger, String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090871", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090871";
   }

   public static LoggableMessageSpi logRoleConsumerProviderErrorLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090871", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getServiceNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090872", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getServiceNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090872", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNotInstanceof(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090873", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNotInstanceofLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090873", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNullObjectReturned(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090874", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullObjectReturnedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090874", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNullParameterSupplied(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090875", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullParameterSuppliedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090875", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getConsumerNotConfigured(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090876", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getConsumerNotConfiguredLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090876", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnknownTokenType(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090878", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnknownTokenTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090878", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logWarningCertificateAlreadyExists(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090879", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090879";
   }

   public static LoggableMessageSpi logWarningCertificateAlreadyExistsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090879", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getManagementOperationUnsupported() {
      Object[] args = new Object[0];
      return (new Loggable("090881", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getManagementOperationUnsupportedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090881", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNullResourceName() {
      Object[] args = new Object[0];
      return (new Loggable("090883", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullResourceNameLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090883", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNullUserName() {
      Object[] args = new Object[0];
      return (new Loggable("090884", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullUserNameLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090884", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNullRemoteUserName() {
      Object[] args = new Object[0];
      return (new Loggable("090885", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullRemoteUserNameLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090885", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getNullIllegalReturnNum() {
      Object[] args = new Object[0];
      return (new Loggable("090886", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullIllegalReturnNumLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090886", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getMemberNotFoundInGroup(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090887", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMemberNotFoundInGroupLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090887", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logFailedUpdateGlobalPolicies(LoggerSpi logger, String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090897", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090897";
   }

   public static LoggableMessageSpi logFailedUpdateGlobalPoliciesLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090897", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUserAttributeNameCanNotBeNull() {
      Object[] args = new Object[0];
      return (new Loggable("090899", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUserAttributeNameCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090899", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUserAttributeNameNotSupported() {
      Object[] args = new Object[0];
      return (new Loggable("090900", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUserAttributeNameNotSupportedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090900", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidGroupName(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090901", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidGroupNameLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090901", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logCertificateChainNoV1CAFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090902", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi logCertificateChainNoV1CAFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090902", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logDisallowingCryptoJDefaultJCEVerification(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090905", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090905";
   }

   public static LoggableMessageSpi logDisallowingCryptoJDefaultJCEVerificationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090905", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logChangingCryptoJDefaultPRNG(LoggerSpi logger, String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090906", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090906";
   }

   public static LoggableMessageSpi logChangingCryptoJDefaultPRNGLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("090906", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidUserName(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090907", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidUserNameLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090907", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logRestartingRealm(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090936", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090936";
   }

   public static LoggableMessageSpi logRestartingRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090936", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logCompletedRestartingRealm(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090937", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090937";
   }

   public static LoggableMessageSpi logCompletedRestartingRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090937", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUserLoginFailureGeneral() {
      Object[] args = new Object[0];
      return (new Loggable("090938", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUserLoginFailureGeneralLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090938", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidJWTToken() {
      Object[] args = new Object[0];
      return (new Loggable("090939", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidJWTTokenLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090939", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getIAJWTTokenWrongType() {
      Object[] args = new Object[0];
      return (new Loggable("090940", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIAJWTTokenWrongTypeLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090940", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getIAJWTTokenFailedPassSignatureVerify() {
      Object[] args = new Object[0];
      return (new Loggable("090941", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIAJWTTokenFailedPassSignatureVerifyLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090941", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logPartitionsRequireNewRealm(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090942", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090942";
   }

   public static LoggableMessageSpi logPartitionsRequireNewRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090942", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getInvalidRealmProviderNotIDDAwareWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090943", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidRealmProviderNotIDDAwareWarningLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090943", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logNotIDDAwareProvider(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090944", 4, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090944";
   }

   public static LoggableMessageSpi logNotIDDAwareProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090944", 4, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logShutdownRetiredSecurityRealm(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090945", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090945";
   }

   public static LoggableMessageSpi logShutdownRetiredSecurityRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090945", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logPreInitializingUsingRealm(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090946", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090946";
   }

   public static LoggableMessageSpi logPreInitializingUsingRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090946", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logPostInitializingUsingRealm(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090947", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090947";
   }

   public static LoggableMessageSpi logPostInitializingUsingRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090947", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logStartingRealm(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090948", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090948";
   }

   public static LoggableMessageSpi logStartingRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090948", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logCompletedStartingRealm(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090949", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090949";
   }

   public static LoggableMessageSpi logCompletedStartingRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090949", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logShutdownRealmFailed(LoggerSpi logger, String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090950", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090950";
   }

   public static LoggableMessageSpi logShutdownRealmFailedLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090950", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getFailureWithRealm(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090951", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getFailureWithRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090951", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getIdentityDomainIgnored(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090952", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIdentityDomainIgnoredLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090952", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCannotActivateChangesNoAdminIDDSetError() {
      Object[] args = new Object[0];
      return (new Loggable("090953", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCannotActivateChangesNoAdminIDDSetErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090953", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCannotActivateChangesNoPartitionIDDSetError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090954", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCannotActivateChangesNoPartitionIDDSetErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090954", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCannotActivateChangesNoIDDConfiguredError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090955", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCannotActivateChangesNoIDDConfiguredErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090955", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getConflictingPermissionsDeclarationError() {
      Object[] args = new Object[0];
      return (new Loggable("090956", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getConflictingPermissionsDeclarationErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090956", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getProhibitedPermissionsError() {
      Object[] args = new Object[0];
      return (new Loggable("090957", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getProhibitedPermissionsErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090957", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getDeploymentDescriptorGrantDisabledError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090958", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDeploymentDescriptorGrantDisabledErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090958", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getPackagedPermissionsDisabledError() {
      Object[] args = new Object[0];
      return (new Loggable("090959", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPackagedPermissionsDisabledErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090959", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logNoSSLMBeanPossibleFailure(LoggerSpi logger) {
      Object[] args = new Object[0];
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090960", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090960";
   }

   public static LoggableMessageSpi logNoSSLMBeanPossibleFailureLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090960", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCannotActivateChangesRealmNameExistsError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090961", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCannotActivateChangesRealmNameExistsErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090961", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getCannotActivateChangesImproperlyConfiguredRealmError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090962", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCannotActivateChangesImproperlyConfiguredRealmErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090962", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logModifiedLDAPEntryForProvider(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090963", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090963";
   }

   public static LoggableMessageSpi logModifiedLDAPEntryForProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090963", 32, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logDatasourceConnectionError(LoggerSpi logger, String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090964", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090964";
   }

   public static LoggableMessageSpi logDatasourceConnectionErrorLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090964", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getUnableToCreatePolicyInstance(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090965", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnableToCreatePolicyInstanceLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090965", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logKernelPermissionFailure(LoggerSpi logger, Exception arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090966", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090966";
   }

   public static LoggableMessageSpi logKernelPermissionFailureLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090966", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getLDAPConnectionParamMissing(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090967", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getLDAPConnectionParamMissingLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090967", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getLDAPConnectionParamError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("090968", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getLDAPConnectionParamErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090968", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getOIDCIATokenTypeCanNotBeNull() {
      Object[] args = new Object[0];
      return (new Loggable("090969", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getOIDCIATokenTypeCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090969", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getOIDCIATokenCanNotBeNull() {
      Object[] args = new Object[0];
      return (new Loggable("090970", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getOIDCIATokenCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090970", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getOIDCIATokenTypeInCorrect(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090971", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getOIDCIATokenTypeInCorrectLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090971", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getResourceTypeAlreadyRegistered(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090972", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getResourceTypeAlreadyRegisteredLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090972", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logLdapRequestTimeout(LoggerSpi logger, int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090973", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090973";
   }

   public static LoggableMessageSpi logLdapRequestTimeoutLoggable(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090973", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String getIniFileNotExist(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("090974", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIniFileNotExistLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090974", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logConnectionNonceExpired(LoggerSpi logger) {
      Object[] args = new Object[0];
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090975", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090975";
   }

   public static LoggableMessageSpi logConnectionNonceExpiredLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090975", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logAuditingNotEnabledInSecureMode(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090976", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090976";
   }

   public static LoggableMessageSpi logAuditingNotEnabledInSecureModeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090976", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logAuditingLevelInappropriateInSecureMode(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090977", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090977";
   }

   public static LoggableMessageSpi logAuditingLevelInappropriateInSecureModeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090977", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logLockoutSettingNotSecureInSecureMode(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090978", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090978";
   }

   public static LoggableMessageSpi logLockoutSettingNotSecureInSecureModeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090978", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logUnEncryptedPasswdInCommandLine(LoggerSpi logger) {
      Object[] args = new Object[0];
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090979", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090979";
   }

   public static LoggableMessageSpi logUnEncryptedPasswdInCommandLineLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090979", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logNoPasswordValidatorInSecureMode(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090980", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090980";
   }

   public static LoggableMessageSpi logNoPasswordValidatorInSecureModeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090980", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSecurityManagerNotEnabledInSecureMode(LoggerSpi logger) {
      Object[] args = new Object[0];
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090981", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090981";
   }

   public static LoggableMessageSpi logSecurityManagerNotEnabledInSecureModeLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090981", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logUnixMachinePostBindNotEnabled(LoggerSpi logger, String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090982", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090982";
   }

   public static LoggableMessageSpi logUnixMachinePostBindNotEnabledLoggable(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("090982", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logAdministrationPortNotEnabledInSecureMode(LoggerSpi logger) {
      Object[] args = new Object[0];
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090983", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090983";
   }

   public static LoggableMessageSpi logAdministrationPortNotEnabledInSecureModeLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("090983", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logFileOwnerInsecureSecureMode(LoggerSpi logger, String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090984", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090984";
   }

   public static LoggableMessageSpi logFileOwnerInsecureSecureModeLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("090984", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logFilePermissionInsecureSecureMode(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090985", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090985";
   }

   public static LoggableMessageSpi logFilePermissionInsecureSecureModeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090985", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSecureModeRequiresNewRealm(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090986", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090986";
   }

   public static LoggableMessageSpi logSecureModeRequiresNewRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090986", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSSLv3EnabledBySysProp(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090987", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090987";
   }

   public static LoggableMessageSpi logSSLv3EnabledBySysPropLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090987", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logBasicConstraintsValidationEnabledBySysProp(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090988", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090988";
   }

   public static LoggableMessageSpi logBasicConstraintsValidationEnabledBySysPropLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090988", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logHostNameVerificationDisabledBySysProp(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090989", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090989";
   }

   public static LoggableMessageSpi logHostNameVerificationDisabledBySysPropLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090989", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logHostNameVerificationDisabledBySSLMBean(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090990", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090990";
   }

   public static LoggableMessageSpi logHostNameVerificationDisabledBySSLMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090990", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logHostNameVerificationDisabledByNetworkAccessPointMBean(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090991", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090991";
   }

   public static LoggableMessageSpi logHostNameVerificationDisabledByNetworkAccessPointMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090991", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSSLv3MinProtocolEnabledBySysProp(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090992", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090992";
   }

   public static LoggableMessageSpi logSSLv3MinProtocolEnabledBySysPropLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090992", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSSLv3MinProtocolEnabledBySSLMBean(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090993", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090993";
   }

   public static LoggableMessageSpi logSSLv3MinProtocolEnabledBySSLMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090993", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSSLv3MinProtocolEnabledByNetworkAccessPointMBean(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090994", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090994";
   }

   public static LoggableMessageSpi logSSLv3MinProtocolEnabledByNetworkAccessPointMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090994", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logNullCipherAllowedBySysProp(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090995", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090995";
   }

   public static LoggableMessageSpi logNullCipherAllowedBySysPropLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090995", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logNullCipherAllowedBySSLMBean(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090996", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090996";
   }

   public static LoggableMessageSpi logNullCipherAllowedBySSLMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090996", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logNullCipherAllowedByNetworkAccessPointMBean(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090997", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090997";
   }

   public static LoggableMessageSpi logNullCipherAllowedByNetworkAccessPointMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090997", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logAllowAnonymousCiphersBySysProp(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090998", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090998";
   }

   public static LoggableMessageSpi logAllowAnonymousCiphersBySysPropLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090998", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logTLSClientInitSecureRenegotiationBySSLMBean(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("090999", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "090999";
   }

   public static LoggableMessageSpi logTLSClientInitSecureRenegotiationBySSLMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("090999", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logTLSClientInitSecureRenegotiationByNetworkAccessPointMBean(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091000", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091000";
   }

   public static LoggableMessageSpi logTLSClientInitSecureRenegotiationByNetworkAccessPointMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("091000", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logWeakCipherSuitesBySSLMBean(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091001", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091001";
   }

   public static LoggableMessageSpi logWeakCipherSuitesBySSLMBeanLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("091001", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logWeakCipherSuitesByNetworkAccessPointMBean(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091002", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091002";
   }

   public static LoggableMessageSpi logWeakCipherSuitesByNetworkAccessPointMBeanLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("091002", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logAdminUserInsecureName(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091003", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091003";
   }

   public static LoggableMessageSpi logAdminUserInsecureNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("091003", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logSamplesInstalledInSecureMode(LoggerSpi logger) {
      Object[] args = new Object[0];
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091004", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091004";
   }

   public static LoggableMessageSpi logSamplesInstalledInSecureModeLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("091004", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logRetryBootAuthentication(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091005", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091005";
   }

   public static LoggableMessageSpi logRetryBootAuthenticationLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("091005", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logJdk8SslNotEndorsed(LoggerSpi logger) {
      Object[] args = new Object[0];
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091006", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091006";
   }

   public static LoggableMessageSpi logJdk8SslNotEndorsedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("091006", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logIDCSServer429ResponseCount(LoggerSpi logger, int arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091007", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091007";
   }

   public static LoggableMessageSpi logIDCSServer429ResponseCountLoggable(int arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("091007", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logIDCSServerTimeoutCount(LoggerSpi logger, int arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091008", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091008";
   }

   public static LoggableMessageSpi logIDCSServerTimeoutCountLoggable(int arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("091008", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logWlsSecurityContextInit(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091009", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091009";
   }

   public static LoggableMessageSpi logWlsSecurityContextInitLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("091009", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logWlsSecurityContextAuthenticateException(LoggerSpi logger, Exception arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091010", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091010";
   }

   public static LoggableMessageSpi logWlsSecurityContextAuthenticateExceptionLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("091010", 8, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logWlsSecurityContextInitAuditor(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091011", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091011";
   }

   public static LoggableMessageSpi logWlsSecurityContextInitAuditorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("091011", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logNullArgForCheckAccessToWebResource(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091012", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091012";
   }

   public static LoggableMessageSpi logNullArgForCheckAccessToWebResourceLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("091012", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logCurrentSubjectIsNull(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091013", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091013";
   }

   public static LoggableMessageSpi logCurrentSubjectIsNullLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("091013", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logIsCallerInRole(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091014", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091014";
   }

   public static LoggableMessageSpi logIsCallerInRoleLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("091014", 64, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logIDCSProviderRequestRetryCount(LoggerSpi logger, int arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091015", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091015";
   }

   public static LoggableMessageSpi logIDCSProviderRequestRetryCountLoggable(int arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("091015", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logIDCSProviderBackoffCount(LoggerSpi logger, int arg0, int arg1, int arg2, long arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091016", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091016";
   }

   public static LoggableMessageSpi logIDCSProviderBackoffCountLoggable(int arg0, int arg1, int arg2, long arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      return new LoggableMessageSpiImpl("091016", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
   }

   public static String logDeprecatedSecurityProvider(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("091017", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "091017";
   }

   public static LoggableMessageSpi logDeprecatedSecurityProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("091017", 16, args, "com.bea.common.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
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
      private MessageLogger messageLogger = SecurityLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = SecurityLogger.findMessageLogger();
      }
   }
}
