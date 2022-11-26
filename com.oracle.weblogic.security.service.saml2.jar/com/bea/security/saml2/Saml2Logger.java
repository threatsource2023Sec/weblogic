package com.bea.security.saml2;

import com.bea.common.logger.spi.LoggableMessageSpi;
import com.bea.common.logger.spi.LoggerSpi;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.logging.Loggable;

public class Saml2Logger {
   private static final String LOCALIZER_CLASS = "com.bea.security.saml2.Saml2LogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(Saml2Logger.class.getName());
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

   public static String getSAML2ArtifactConstructFail(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("096500", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2ArtifactConstructFailLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("096500", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2SamlMessageTypeError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096501", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2SamlMessageTypeErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096501", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2SamlMessageIsNull() {
      Object[] args = new Object[0];
      return (new Loggable("096502", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2SamlMessageIsNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096502", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2ArtifactIsNull(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096503", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2ArtifactIsNullLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096503", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2CouldNotGetBindingHandler(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096504", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2CouldNotGetBindingHandlerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096504", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2CouldNotGetSamlResponse(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096505", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2CouldNotGetSamlResponseLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096505", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2SamlResponseError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return (new Loggable("096506", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2SamlResponseErrorLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("096506", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2CouldNotGetEndpoint(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return (new Loggable("096507", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2CouldNotGetEndpointLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("096507", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2NoSignKeyFor(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096508", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2NoSignKeyForLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096508", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2NoVerifyKeyFor(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096509", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2NoVerifyKeyForLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096509", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2NoSamlMsgInHttpreq(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096510", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2NoSamlMsgInHttpreqLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096510", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2SslServerCertChainNull() {
      Object[] args = new Object[0];
      return (new Loggable("096511", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2SslServerCertChainNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096511", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2SslServerCouldnotBeTrusted(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096512", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2SslServerCouldnotBeTrustedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096512", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2CouldNotGetPartner(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096513", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2CouldNotGetPartnerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096513", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2SslServerHasnotSslCert(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096514", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2SslServerHasnotSslCertLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096514", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2SslClientHasnotSslCertKey() {
      Object[] args = new Object[0];
      return (new Loggable("096515", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2SslClientHasnotSslCertKeyLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096515", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2CouldnotGetSigFromHttpreq(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096516", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2CouldnotGetSigFromHttpreqLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096516", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2CouldnotGetSigFromSamlmsg() {
      Object[] args = new Object[0];
      return (new Loggable("096517", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2CouldnotGetSigFromSamlmsgLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096517", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2VerifySignatureFail() {
      Object[] args = new Object[0];
      return (new Loggable("096518", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2VerifySignatureFailLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096518", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2CouldnotGetArtifactFromStore() {
      Object[] args = new Object[0];
      return (new Loggable("096519", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2CouldnotGetArtifactFromStoreLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096519", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2InvalidSAMLRequest(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096520", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2InvalidSAMLRequestLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096520", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2InvalidSAMLResponse(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096521", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2InvalidSAMLResponseLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096521", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2SigningErrors(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096522", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2SigningErrorsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096522", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2AuthenticationFailed() {
      Object[] args = new Object[0];
      return (new Loggable("096523", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2AuthenticationFailedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096523", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIAInvalidTokenObject() {
      Object[] args = new Object[0];
      return (new Loggable("096524", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIAInvalidTokenObjectLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096524", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIAAssertionNotSigned() {
      Object[] args = new Object[0];
      return (new Loggable("096525", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIAAssertionNotSignedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096525", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIANoSubject() {
      Object[] args = new Object[0];
      return (new Loggable("096526", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIANoSubjectLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096526", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIANoSubjectConfirmation() {
      Object[] args = new Object[0];
      return (new Loggable("096527", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIANoSubjectConfirmationLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096527", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIAUnsupportAssertionType(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096528", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIAUnsupportAssertionTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096528", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIANoCertificate() {
      Object[] args = new Object[0];
      return (new Loggable("096529", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIANoCertificateLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096529", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIANoPublicKey() {
      Object[] args = new Object[0];
      return (new Loggable("096530", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIANoPublicKeyLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096530", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIAInvalidSignature() {
      Object[] args = new Object[0];
      return (new Loggable("096531", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIAInvalidSignatureLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096531", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getInvalidVersion(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("096532", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidVersionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("096532", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIANotMatchedCertificate() {
      Object[] args = new Object[0];
      return (new Loggable("096533", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIANotMatchedCertificateLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096533", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIANoIssuer() {
      Object[] args = new Object[0];
      return (new Loggable("096534", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIANoIssuerLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096534", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getInvalidIssuer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("096535", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidIssuerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("096535", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getInvalidIssuerFormat(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096536", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidIssuerFormatLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096536", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIAInvaildNotBefore(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096537", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIAInvaildNotBeforeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096537", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIAInvaildNotOnOrAfter(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096538", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIAInvaildNotOnOrAfterLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096538", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIAInvaildAudience() {
      Object[] args = new Object[0];
      return (new Loggable("096539", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIAInvaildAudienceLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096539", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIAIDUsedAgain(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096540", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIAIDUsedAgainLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096540", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIAMustBearer() {
      Object[] args = new Object[0];
      return (new Loggable("096541", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIAMustBearerLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096541", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIANoNameID() {
      Object[] args = new Object[0];
      return (new Loggable("096542", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIANoNameIDLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096542", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIAInvalidPartner(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096543", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIAInvalidPartnerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096543", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoPartnerForRequest(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("096544", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoPartnerForRequestLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("096544", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoSSOServicesForPartner(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096545", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoSSOServicesForPartnerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096545", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoSignKeyFor(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096546", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoSignKeyForLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096546", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIllegalResponseCode(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096547", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalResponseCodeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096547", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoIdPForIssuerURI(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096548", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoIdPForIssuerURILoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096548", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoVerifyingCert(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("096549", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoVerifyingCertLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("096549", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getCreateSessionError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096550", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCreateSessionErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096550", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getBindingUnenabled(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096551", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getBindingUnenabledLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096551", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getDestinationNotMatch(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("096552", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDestinationNotMatchLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("096552", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoRequestFound(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096554", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoRequestFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096554", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getEmptyAttribute(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096555", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyAttributeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096555", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getAttributeShouldBeOmitted(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096556", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getAttributeShouldBeOmittedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096556", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIllegalConfirmationMethod(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("096557", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalConfirmationMethodLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("096557", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIllegalRecipient(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("096558", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalRecipientLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("096558", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIllegalInResponseTo(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("096559", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalInResponseToLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("096559", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getInvalidQualifiersInIssuer() {
      Object[] args = new Object[0];
      return (new Loggable("096560", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidQualifiersInIssuerLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096560", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIdPNotEnabled(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096561", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIdPNotEnabledLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096561", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2SendingError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096562", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2SendingErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096562", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2ReceivingError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096563", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2ReceivingErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096563", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2ArtifactATNFailed() {
      Object[] args = new Object[0];
      return (new Loggable("096565", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2ArtifactATNFailedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096565", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIdpDisabled() {
      Object[] args = new Object[0];
      return (new Loggable("096566", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIdpDisabledLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096566", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoSPPartnerWithIssuerURI(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096567", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoSPPartnerWithIssuerURILoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096567", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSPPartnerIsDisabled(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096568", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSPPartnerIsDisabledLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096568", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getCantAuthnUserInPassiveMode() {
      Object[] args = new Object[0];
      return (new Loggable("096569", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCantAuthnUserInPassiveModeLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096569", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getUnauthenticatedUserAccessingLoginReturn() {
      Object[] args = new Object[0];
      return (new Loggable("096570", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnauthenticatedUserAccessingLoginReturnLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096570", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoAuthnRequestInSession() {
      Object[] args = new Object[0];
      return (new Loggable("096571", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoAuthnRequestInSessionLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096571", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getUnauthenticatedUserAccessingIdpInitiator() {
      Object[] args = new Object[0];
      return (new Loggable("096572", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnauthenticatedUserAccessingIdpInitiatorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096572", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoRequiredParamForIdpInitator(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096573", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoRequiredParamForIdpInitatorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096573", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getCantFindPartnerFromName(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096574", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCantFindPartnerFromNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096574", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getRelayStateTooLong() {
      Object[] args = new Object[0];
      return (new Loggable("096575", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getRelayStateTooLongLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096575", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getReceivedNonAuthnRequestDoc(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096576", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getReceivedNonAuthnRequestDocLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096576", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getFailedToReceiveDocument(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096577", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getFailedToReceiveDocumentLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096577", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getCantGenerateAssertion() {
      Object[] args = new Object[0];
      return (new Loggable("096578", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCantGenerateAssertionLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096578", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getFailedToUnmashallAssertion() {
      Object[] args = new Object[0];
      return (new Loggable("096579", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getFailedToUnmashallAssertionLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096579", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoACSServiceInPartner(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096580", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoACSServiceInPartnerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096580", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getAuthnRequestDestinationNotMatch(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("096581", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getAuthnRequestDestinationNotMatchLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("096581", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSubjectConfirmationMustNotExist() {
      Object[] args = new Object[0];
      return (new Loggable("096582", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSubjectConfirmationMustNotExistLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096582", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getOnlySupportUnspecifiedNamedId() {
      Object[] args = new Object[0];
      return (new Loggable("096583", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getOnlySupportUnspecifiedNamedIdLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096583", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getRequestedAuthnContextNotSupported() {
      Object[] args = new Object[0];
      return (new Loggable("096584", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getRequestedAuthnContextNotSupportedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096584", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSPNameQualifierNotSupported() {
      Object[] args = new Object[0];
      return (new Loggable("096585", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSPNameQualifierNotSupportedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096585", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getFindSPPartnerByIssuerURIError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096586", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getFindSPPartnerByIssuerURIErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096586", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getFindSPPartnerByNameError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096587", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getFindSPPartnerByNameErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096587", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getFindIdPPartnerByNameError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096588", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getFindIdPPartnerByNameErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096588", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getFindPartnerByNameError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096589", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getFindPartnerByNameErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096589", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIANoEntityIdForCheckAudience() {
      Object[] args = new Object[0];
      return (new Loggable("096590", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIANoEntityIdForCheckAudienceLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096590", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoPartnerByName(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("096591", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoPartnerByNameLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("096591", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getPartnerIsNotEnabledInRegistry(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096592", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPartnerIsNotEnabledInRegistryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096592", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIAEmptyAudience() {
      Object[] args = new Object[0];
      return (new Loggable("096593", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIAEmptyAudienceLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096593", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getCMEmptyEntityID() {
      Object[] args = new Object[0];
      return (new Loggable("096594", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCMEmptyEntityIDLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096594", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getEmptyConfirmationMethod() {
      Object[] args = new Object[0];
      return (new Loggable("096595", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyConfirmationMethodLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096595", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getEmptyAssertionSigningCert() {
      Object[] args = new Object[0];
      return (new Loggable("096596", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyAssertionSigningCertLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096596", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getEmptyArtifactPOSTForm() {
      Object[] args = new Object[0];
      return (new Loggable("096597", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyArtifactPOSTFormLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096597", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getEmptySSOSigningCert() {
      Object[] args = new Object[0];
      return (new Loggable("096598", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptySSOSigningCertLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096598", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getEmptyIssuerURI() {
      Object[] args = new Object[0];
      return (new Loggable("096599", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyIssuerURILoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096599", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoSAML2EntityConfig() {
      Object[] args = new Object[0];
      return (new Loggable("096600", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoSAML2EntityConfigLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096600", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoSAML2PublishedSiteURLConfig() {
      Object[] args = new Object[0];
      return (new Loggable("096601", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoSAML2PublishedSiteURLConfigLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096601", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoSAML2SSOSigningKeyAliasConfig() {
      Object[] args = new Object[0];
      return (new Loggable("096602", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoSAML2SSOSigningKeyAliasConfigLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096602", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoSAML2SSOSigningKeyPassPhraseConfig() {
      Object[] args = new Object[0];
      return (new Loggable("096603", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoSAML2SSOSigningKeyPassPhraseConfigLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096603", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoSAML2LoginReturnQueryParameterConfig() {
      Object[] args = new Object[0];
      return (new Loggable("096604", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoSAML2LoginReturnQueryParameterConfigLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096604", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getEmptyPartnerEntityId() {
      Object[] args = new Object[0];
      return (new Loggable("096605", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyPartnerEntityIdLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096605", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getEmptyPartnerName() {
      Object[] args = new Object[0];
      return (new Loggable("096606", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyPartnerNameLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096606", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getEmptySingleSignService() {
      Object[] args = new Object[0];
      return (new Loggable("096607", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptySingleSignServiceLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096607", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getEmptyAssertionConsumerServices() {
      Object[] args = new Object[0];
      return (new Loggable("096608", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyAssertionConsumerServicesLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096608", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIllegalPublishedSiteURL(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096609", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalPublishedSiteURLLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096609", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getCannotResolveIssuerURI() {
      Object[] args = new Object[0];
      return (new Loggable("096610", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCannotResolveIssuerURILoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096610", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getCannotGetKeyInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096611", 16, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCannotGetKeyInfoLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096611", 16, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getDecryptPasswordError(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096612", 16, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDecryptPasswordErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096612", 16, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getClearingCache(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096613", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getClearingCacheLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096613", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getCacheCleared(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096614", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCacheClearedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096614", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getExpiredItem(long arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("096615", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getExpiredItemLoggable(long arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("096615", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNegativeMaxReturn() {
      Object[] args = new Object[0];
      return (new Loggable("096616", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNegativeMaxReturnLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096616", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getIllegalContactPersonType() {
      Object[] args = new Object[0];
      return (new Loggable("096617", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalContactPersonTypeLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096617", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getInvalidKeystoreConfiguration() {
      Object[] args = new Object[0];
      return (new Loggable("096618", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidKeystoreConfigurationLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096618", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getEmptyKeyName() {
      Object[] args = new Object[0];
      return (new Loggable("096619", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getEmptyKeyNameLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096619", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getInvalidKeyInfo() {
      Object[] args = new Object[0];
      return (new Loggable("096620", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidKeyInfoLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096620", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String logCanNotInitialization(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("096621", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "096621";
   }

   public static LoggableMessageSpi logCanNotInitializationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096621", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getCantForceAuthnAndInPassiveBothTrue() {
      Object[] args = new Object[0];
      return (new Loggable("096622", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCantForceAuthnAndInPassiveBothTrueLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096622", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2AlreadyExpiredItem(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096623", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2AlreadyExpiredItemLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096623", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getParameterMustNotBeNull(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("096624", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getParameterMustNotBeNullLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("096624", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getMaxCacheSizeOrTimeoutMustNotBeNegative() {
      Object[] args = new Object[0];
      return (new Loggable("096625", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMaxCacheSizeOrTimeoutMustNotBeNegativeLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096625", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getCanNotChangePartnerName() {
      Object[] args = new Object[0];
      return (new Loggable("096626", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCanNotChangePartnerNameLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096626", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoLoginURLIsConfigured() {
      Object[] args = new Object[0];
      return (new Loggable("096627", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoLoginURLIsConfiguredLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096627", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2InvalidNameMapperClassName(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096628", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2InvalidNameMapperClassNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096628", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getInvalidConfirmationMethod(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096629", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidConfirmationMethodLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096629", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSignWithExpiredCert(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096630", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSignWithExpiredCertLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096630", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSignWithNotYetValidCert(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096631", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSignWithNotYetValidCertLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096631", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String logUnsupportedBindingType(LoggerSpi logger, String arg0) {
      Object[] args = new Object[]{arg0};
      LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("096632", 16, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      logMessageUsingLoggerSpi(logger, catalogMessage);
      return "096632";
   }

   public static LoggableMessageSpi logUnsupportedBindingTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096632", 16, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getMetadataNotEntityDescriptor() {
      Object[] args = new Object[0];
      return (new Loggable("096633", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMetadataNotEntityDescriptorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("096633", 64, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getNoSignCertificateFor(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096634", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoSignCertificateForLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096634", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSAML2EncryptionErrors(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096635", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAML2EncryptionErrorsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096635", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getBindingNotSupportedForAuthnResponse(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096636", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getBindingNotSupportedForAuthnResponseLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096636", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getSPACSBindingNotSupportedForAuthnResponse(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("096637", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSPACSBindingNotSupportedForAuthnResponseLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("096637", 8, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
   }

   public static String getTargetRedirectHostNotAllowed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("096638", 16, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.MessageLoggerInitializer.INSTANCE.messageLogger, Saml2Logger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getTargetRedirectHostNotAllowedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("096638", 16, args, "com.bea.security.saml2.Saml2LogLocalizer", Saml2Logger.class.getClassLoader());
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
      private MessageLogger messageLogger = Saml2Logger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = Saml2Logger.findMessageLogger();
      }
   }
}
