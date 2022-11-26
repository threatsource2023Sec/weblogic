package com.bea.common.security;

import com.bea.common.logger.spi.LoggableMessageSpi;
import com.bea.common.logger.spi.LoggerSpi;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.logging.Loggable;

public class ProvidersLogger {
   private static final String LOCALIZER_CLASS = "com.bea.common.security.ProvidersLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(ProvidersLogger.class.getName());
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

   public static String getNullParameterSuppliedToMethod(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("099000", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullParameterSuppliedToMethodLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("099000", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String logSAMLProviderUnexpectedContextElementType(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("099001", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "099001";
   }

   public static LoggableMessageSpi logSAMLProviderUnexpectedContextElementTypeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("099001", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String logSAMLProviderNullOrEmptyCertificateChain(LoggerSpi logger) {
      Object[] args = new Object[0];
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("099002", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "099002";
   }

   public static LoggableMessageSpi logSAMLProviderNullOrEmptyCertificateChainLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099002", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String logSAMLProviderListenerError(LoggerSpi logger, String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("099003", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "099003";
   }

   public static LoggableMessageSpi logSAMLProviderListenerErrorLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("099003", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String logSAMLAssertionCreateFail(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("099004", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "099004";
   }

   public static LoggableMessageSpi logSAMLAssertionCreateFailLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099004", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String logSAMLCouldNotMapConfirmMeth(LoggerSpi logger) {
      Object[] args = new Object[0];
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("099005", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "099005";
   }

   public static LoggableMessageSpi logSAMLCouldNotMapConfirmMethLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099005", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String logSAMLProvidereInvalidKeyConfiguration(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("099006", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "099006";
   }

   public static LoggableMessageSpi logSAMLProvidereInvalidKeyConfigurationLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("099006", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String logSAMLProvidereNotInit(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("099007", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "099007";
   }

   public static LoggableMessageSpi logSAMLProvidereNotInitLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("099007", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getStoreServicePropertiesIsNull() {
      Object[] args = new Object[0];
      return (new Loggable("099008", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getStoreServicePropertiesIsNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099008", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getStoreServicePropertiesHasNullField(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099009", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getStoreServicePropertiesHasNullFieldLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099009", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getUnableDeriveKeyWithNullField(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099010", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnableDeriveKeyWithNullFieldLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099010", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getUnablePasswordDigestWithNullField(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099011", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnablePasswordDigestWithNullFieldLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099011", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getUnablePasswordDigestUtf8Required(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099012", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnablePasswordDigestUtf8RequiredLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099012", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getLDAPX509IATokenNotCorrect(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099013", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getLDAPX509IATokenNotCorrectLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099013", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getNullHandlerInSAMLAssertion(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("099014", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullHandlerInSAMLAssertionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("099014", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLInvalidImpExpFormat() {
      Object[] args = new Object[0];
      return (new Loggable("099015", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLInvalidImpExpFormatLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099015", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLCouldNotGetPartner(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099016", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLCouldNotGetPartnerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099016", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLCouldNotAddPartnerWithoutId() {
      Object[] args = new Object[0];
      return (new Loggable("099017", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLCouldNotAddPartnerWithoutIdLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099017", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLCouldNotUpdateBusinessObject() {
      Object[] args = new Object[0];
      return (new Loggable("099018", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLCouldNotUpdateBusinessObjectLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099018", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLCouldNotParseAssertion(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099019", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLCouldNotParseAssertionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099019", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLAssertionCheckFail(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099020", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLAssertionCheckFailLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099020", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLAssertionNotSigned() {
      Object[] args = new Object[0];
      return (new Loggable("099021", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLAssertionNotSignedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099021", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLNoSigningCertAlias() {
      Object[] args = new Object[0];
      return (new Loggable("099022", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLNoSigningCertAliasLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099022", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLNoCertForAlias(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099023", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLNoCertForAliasLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099023", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLNoCertInSignature() {
      Object[] args = new Object[0];
      return (new Loggable("099024", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLNoCertInSignatureLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099024", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLCertificateNotMatch() {
      Object[] args = new Object[0];
      return (new Loggable("099025", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLCertificateNotMatchLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099025", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLCertificateNotTrusted() {
      Object[] args = new Object[0];
      return (new Loggable("099026", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLCertificateNotTrustedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099026", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLCouldNotCreateMapper(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099027", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLCouldNotCreateMapperLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099027", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLMappingNameError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099028", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLMappingNameErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099028", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLMappingGroupError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099029", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLMappingGroupErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099029", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLMappingNoName() {
      Object[] args = new Object[0];
      return (new Loggable("099030", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLMappingNoNameLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099030", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLAssertionInvalidBefore(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099031", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLAssertionInvalidBeforeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099031", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLAssertionInvalidOnOrAfter(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099032", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLAssertionInvalidOnOrAfterLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099032", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLNoMatchingAudience() {
      Object[] args = new Object[0];
      return (new Loggable("099033", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLNoMatchingAudienceLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099033", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLUnrecognizedAssertionCondition() {
      Object[] args = new Object[0];
      return (new Loggable("099034", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLUnrecognizedAssertionConditionLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099034", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLNoTargetInContextHandler() {
      Object[] args = new Object[0];
      return (new Loggable("099035", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLNoTargetInContextHandlerLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099035", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLCouldNotMapConfirmMethod(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099036", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLCouldNotMapConfirmMethodLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099036", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLCouldNotGetAssertParty() {
      Object[] args = new Object[0];
      return (new Loggable("099037", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLCouldNotGetAssertPartyLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099037", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLCouldNotVerifySignature(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099038", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLCouldNotVerifySignatureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099038", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLNoAssertPartyIssuerConfig() {
      Object[] args = new Object[0];
      return (new Loggable("099039", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLNoAssertPartyIssuerConfigLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099039", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLAssertPartyNotMatch() {
      Object[] args = new Object[0];
      return (new Loggable("099040", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLAssertPartyNotMatchLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099040", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLConfirmMethodNotMatch() {
      Object[] args = new Object[0];
      return (new Loggable("099041", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLConfirmMethodNotMatchLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099041", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getIllegalValue(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("099046", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalValueLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("099046", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getMissingParameter(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099047", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMissingParameterLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099047", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getIllegalValueForContext(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return (new Loggable("099048", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalValueForContextLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("099048", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLCouldNotLoadPartnerRegistryFile(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099049", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLCouldNotLoadPartnerRegistryFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099049", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLCouldNotGeneratePartnerRegistryFile(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099050", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLCouldNotGeneratePartnerRegistryFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099050", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLImportTerminateInFailMode(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099051", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLImportTerminateInFailModeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099051", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLImportFailForCouldNotLocateFromFile(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("099052", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLImportFailForCouldNotLocateFromFileLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("099052", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLImportFailForAlreadyExists(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("099053", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLImportFailForAlreadyExistsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("099053", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLImportFailForCouldNotLocateFromRegistry(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("099054", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLImportFailForCouldNotLocateFromRegistryLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("099054", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLCouldNotLocateFromRegistry(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("099055", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLCouldNotLocateFromRegistryLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("099055", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLCouldNotExportPartner(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099056", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLCouldNotExportPartnerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099056", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getCouldNotDecryptPassword(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099057", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCouldNotDecryptPasswordLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099057", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLNoSenderCert() {
      Object[] args = new Object[0];
      return (new Loggable("099058", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLNoSenderCertLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099058", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLNotFoundIn(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("099059", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLNotFoundInLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("099059", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getStoreServiceInvalidURL(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099060", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getStoreServiceInvalidURLLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099060", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getHashAlgorithmNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099061", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getHashAlgorithmNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099061", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getHashAlgorithmNotUsable(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099062", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getHashAlgorithmNotUsableLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099062", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getPlaintextPasswordUsageRejected() {
      Object[] args = new Object[0];
      return (new Loggable("099063", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPlaintextPasswordUsageRejectedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099063", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getUnableParseHashedPassword() {
      Object[] args = new Object[0];
      return (new Loggable("099064", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnableParseHashedPasswordLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099064", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getPasswordHashUtilityPrompt() {
      Object[] args = new Object[0];
      return (new Loggable("099065", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPasswordHashUtilityPromptLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099065", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getPasswordHashUtilityUsage() {
      Object[] args = new Object[0];
      return (new Loggable("099066", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPasswordHashUtilityUsageLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099066", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getPasswordLengthConfigError() {
      Object[] args = new Object[0];
      return (new Loggable("099067", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPasswordLengthConfigErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099067", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getEmptyUsernameError() {
      Object[] args = new Object[0];
      return (new Loggable("099068", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyUsernameErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099068", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getPasswordLengthLessError(int arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099069", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPasswordLengthLessErrorLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099069", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getPasswordLengthGreaterError(int arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099070", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPasswordLengthGreaterErrorLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099070", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getPasswordContainUsernameError() {
      Object[] args = new Object[0];
      return (new Loggable("099071", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPasswordContainUsernameErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099071", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getPasswordContainReverseUsernameError() {
      Object[] args = new Object[0];
      return (new Loggable("099072", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPasswordContainReverseUsernameErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099072", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getMaxInstanceOfCharError(int arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099073", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMaxInstanceOfCharErrorLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099073", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getMaxConsecutiveCharError(int arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099074", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMaxConsecutiveCharErrorLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099074", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getMinAlphaCharError(int arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099075", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMinAlphaCharErrorLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099075", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getMinNumericCharError(int arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099076", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMinNumericCharErrorLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099076", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getMinLowercaseCharError(int arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099077", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMinLowercaseCharErrorLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099077", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getMinUppercaseCharError(int arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099078", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMinUppercaseCharErrorLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099078", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getMinNonalphanumericCharError(int arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099079", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMinNonalphanumericCharErrorLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099079", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getEmptyPasswordError() {
      Object[] args = new Object[0];
      return (new Loggable("099080", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyPasswordErrorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099080", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getIllegalCmdline() {
      Object[] args = new Object[0];
      return (new Loggable("099081", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalCmdlineLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099081", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getCouldNotConfigAttrForMBean(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("099082", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCouldNotConfigAttrForMBeanLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("099082", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getWrongKeyFormatInPropFile(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099083", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getWrongKeyFormatInPropFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099083", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getCouldNotCreateMBean() {
      Object[] args = new Object[0];
      return (new Loggable("099084", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCouldNotCreateMBeanLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099084", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getUnknownMBeanResourceActionType(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099085", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnknownMBeanResourceActionTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099085", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getNullOrEmptyEncryptedValue() {
      Object[] args = new Object[0];
      return (new Loggable("099086", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullOrEmptyEncryptedValueLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099086", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getPasswordLengthConfigError1() {
      Object[] args = new Object[0];
      return (new Loggable("099087", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPasswordLengthConfigError1Loggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099087", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getPasswordLengthConfigError2() {
      Object[] args = new Object[0];
      return (new Loggable("099088", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPasswordLengthConfigError2Loggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099088", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String logNegotiateCredentialMapperGetCredentialsFailure(LoggerSpi logger, String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("099089", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "099089";
   }

   public static LoggableMessageSpi logNegotiateCredentialMapperGetCredentialsFailureLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      return new LoggableMessageSpiImpl("099089", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String logNegotiateCredentialMapperUnsupportedParameter(LoggerSpi logger, String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("099090", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "099090";
   }

   public static LoggableMessageSpi logNegotiateCredentialMapperUnsupportedParameterLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("099090", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getNegotiateCredentialMapperUnsupportedParameter() {
      Object[] args = new Object[0];
      return (new Loggable("099091", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNegotiateCredentialMapperUnsupportedParameterLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099091", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getTrustGroupNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099092", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getTrustGroupNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099092", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getTrustGroupAlreadyExists(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099093", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getTrustGroupAlreadyExistsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099093", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getEmptyOrNullTrustGroup() {
      Object[] args = new Object[0];
      return (new Loggable("099094", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyOrNullTrustGroupLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099094", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getEmptyOrNullTrustGroupWildcard() {
      Object[] args = new Object[0];
      return (new Loggable("099095", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyOrNullTrustGroupWildcardLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099095", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getInvalidGroupJKSKeyStoreEntryError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("099096", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidGroupJKSKeyStoreEntryErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("099096", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getGroupNotInImportFileError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("099097", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getGroupNotInImportFileErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("099097", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getCertificateAliasAlreadyExistsInTrustGroup(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("099098", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCertificateAliasAlreadyExistsInTrustGroupLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("099098", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getErrorsRegisterCertificate(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099099", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getErrorsRegisterCertificateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099099", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getEmptyOrNullTrustGroups() {
      Object[] args = new Object[0];
      return (new Loggable("099100", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyOrNullTrustGroupsLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099100", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getTrustGroupNameReserved(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099101", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getTrustGroupNameReservedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099101", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSubjectDNAlreadyExists(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return (new Loggable("099102", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSubjectDNAlreadyExistsLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("099102", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getIssuerDNAndSerialNumberAlreadyExists(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      return (new Loggable("099103", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIssuerDNAndSerialNumberAlreadyExistsLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      return new LoggableMessageSpiImpl("099103", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSubjectKeyIdentifierAlreadyExists(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return (new Loggable("099104", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSubjectKeyIdentifierAlreadyExistsLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("099104", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getFilePathNotExist(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099105", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getFilePathNotExistLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099105", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getEmptyOrNullPassword() {
      Object[] args = new Object[0];
      return (new Loggable("099106", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyOrNullPasswordLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099106", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getInvalidEncryptionAlgorithm(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099107", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidEncryptionAlgorithmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099107", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getInvalidPropertyFile(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099108", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidPropertyFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099108", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String logCannotGetCallbackHandler(LoggerSpi logger, String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("099109", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "099109";
   }

   public static LoggableMessageSpi logCannotGetCallbackHandlerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("099109", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSAMLInvalidNameMapperClassName(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099110", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLInvalidNameMapperClassNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099110", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String logKerberosCredentialMapperGetCredentialsFailure(LoggerSpi logger, String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("099111", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "099111";
   }

   public static LoggableMessageSpi logKerberosCredentialMapperGetCredentialsFailureLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      return new LoggableMessageSpiImpl("099111", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String logKerberosCredentialMapperUnsupportedParameter(LoggerSpi logger, String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("099112", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "099112";
   }

   public static LoggableMessageSpi logKerberosCredentialMapperUnsupportedParameterLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("099112", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getKerberosCredentialMapperUnsupportedParameter() {
      Object[] args = new Object[0];
      return (new Loggable("099113", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getKerberosCredentialMapperUnsupportedParameterLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099113", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getPasswordLengthConfigError3() {
      Object[] args = new Object[0];
      return (new Loggable("099114", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPasswordLengthConfigError3Loggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099114", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getPasswordLengthConfigError4() {
      Object[] args = new Object[0];
      return (new Loggable("099115", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPasswordLengthConfigError4Loggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("099115", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getMinNumericOrSpecialError(int arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099116", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMinNumericOrSpecialErrorLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099116", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String logLdapConnectionFailedWarning(LoggerSpi logger, String arg0, String arg1, String arg2, int arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("099117", 16, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "099117";
   }

   public static LoggableMessageSpi logLdapConnectionFailedWarningLoggable(String arg0, String arg1, String arg2, int arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      return new LoggableMessageSpiImpl("099117", 16, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
   }

   public static String getSpecialCharactersError(char arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("099118", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ProvidersLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSpecialCharactersErrorLoggable(char arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("099118", 8, args, "com.bea.common.security.ProvidersLogLocalizer", ProvidersLogger.class.getClassLoader());
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
      private MessageLogger messageLogger = ProvidersLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = ProvidersLogger.findMessageLogger();
      }
   }
}
