package com.bea.common.security.internal.service;

import com.bea.common.logger.spi.LoggableMessageSpi;
import com.bea.common.logger.spi.LoggerSpi;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.logging.Loggable;

public class ServiceLogger {
   private static final String LOCALIZER_CLASS = "com.bea.common.security.internal.service.ServiceLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(ServiceLogger.class.getName());
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

   public static String getExpectedConfigurationNotSupplied(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("097500", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getExpectedConfigurationNotSuppliedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("097500", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getConfigurationMissingRequiredInfo(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return (new Loggable("097501", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getConfigurationMissingRequiredInfoLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("097501", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getIdentityServiceMaxIdentitiesInCacheInvalid(int arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097502", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIdentityServiceMaxIdentitiesInCacheInvalidLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097502", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getCertPathBuildReturnedNonX509CertPath() {
      Object[] args = new Object[0];
      return (new Loggable("097503", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCertPathBuildReturnedNonX509CertPathLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097503", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getAdjudicationServiceRequiresAdjudicatorV2ForMultipleAccessDecision() {
      Object[] args = new Object[0];
      return (new Loggable("097504", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getAdjudicationServiceRequiresAdjudicatorV2ForMultipleAccessDecisionLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097504", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getNoJAASProvidersErrorMessage(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097505", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoJAASProvidersErrorMessageLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097505", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getNullAccessDecision() {
      Object[] args = new Object[0];
      return (new Loggable("097506", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullAccessDecisionLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097506", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getHasChallengeIdentityNotCompleted(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097507", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getHasChallengeIdentityNotCompletedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097507", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getHasChallengeIdentityAlreadyCompleted(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097508", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getHasChallengeIdentityAlreadyCompletedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097508", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getNullObjectReturned(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("097509", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullObjectReturnedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("097509", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getNullObject(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097510", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullObjectLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097510", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getNotInstanceof(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097511", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNotInstanceofLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097511", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getMultipleChallengesNotSupported() {
      Object[] args = new Object[0];
      return (new Loggable("097512", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMultipleChallengesNotSupportedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097512", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getIncorrectCertPathType(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097513", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIncorrectCertPathTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097513", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getNonexclusiveToken(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("097514", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNonexclusiveTokenLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("097514", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getUnsupportedToken(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097515", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnsupportedTokenLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097515", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getNonX509CertPath() {
      Object[] args = new Object[0];
      return (new Loggable("097516", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNonX509CertPathLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097516", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getSelfSignedCertificateInChain(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097517", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSelfSignedCertificateInChainLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097517", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getIssuerDNMismatch(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("097518", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIssuerDNMismatchLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("097518", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getCertificateNotSignedByIssuer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("097519", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCertificateNotSignedByIssuerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("097519", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getCapacityGreaterThanMax(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("097520", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCapacityGreaterThanMaxLoggable(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("097520", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getMaxCapacityTooLarge(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("097521", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getMaxCapacityTooLargeLoggable(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("097521", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getIllegalInitialCapacity(int arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097522", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalInitialCapacityLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097522", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getIllegalLoadFactor() {
      Object[] args = new Object[0];
      return (new Loggable("097523", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalLoadFactorLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097523", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getJAASConfigurationNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097524", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getJAASConfigurationNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097524", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getNullJAASConfiguration() {
      Object[] args = new Object[0];
      return (new Loggable("097525", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullJAASConfigurationLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097525", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getDuplicateJAASConfiguration() {
      Object[] args = new Object[0];
      return (new Loggable("097526", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDuplicateJAASConfigurationLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097526", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getJAASConfigurationNotRegistered() {
      Object[] args = new Object[0];
      return (new Loggable("097527", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getJAASConfigurationNotRegisteredLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097527", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getNullArgumentSpecified(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097528", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullArgumentSpecifiedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097528", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getValidatorCollision(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("097529", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getValidatorCollisionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("097529", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getNoObjectsFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("097530", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoObjectsFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("097530", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getUnableToSignPricipal(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097531", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getUnableToSignPricipalLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097531", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getNullParameterSupplied(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097532", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullParameterSuppliedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097532", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getNoSecurityProviderClassName(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097533", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNoSecurityProviderClassNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097533", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getFailedToInstantiate(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097534", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getFailedToInstantiateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097534", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getStoreServiceRequired() {
      Object[] args = new Object[0];
      return (new Loggable("097535", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getStoreServiceRequiredLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097535", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getServiceNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("097536", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getServiceNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("097536", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getInvalidConfigurationSettingSupplied(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return (new Loggable("097537", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidConfigurationSettingSuppliedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      return new LoggableMessageSpiImpl("097537", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getKeystoreNotAccessible(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("097538", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getKeystoreNotAccessibleLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("097538", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getDBConnectionNotAvailable() {
      Object[] args = new Object[0];
      return (new Loggable("097539", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBConnectionNotAvailableLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097539", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getDBPoolPropertySkipped(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097540", 16, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getDBPoolPropertySkippedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097540", 16, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getPrerequisiteNotMet(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("097541", 16, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getPrerequisiteNotMetLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("097541", 16, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getCouldNotGetConnectionForName(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097542", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCouldNotGetConnectionForNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097542", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getStoreServicePropertiesIsNull() {
      Object[] args = new Object[0];
      return (new Loggable("097543", 16, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getStoreServicePropertiesIsNullLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097543", 16, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getStoreServicePropertiesHasNullField(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097544", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getStoreServicePropertiesHasNullFieldLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097544", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getInconsistentTokenState() {
      Object[] args = new Object[0];
      return (new Loggable("097545", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInconsistentTokenStateLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097545", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getLDAPIllegalTimestampLength(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097546", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getLDAPIllegalTimestampLengthLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097546", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getLDAPIllegalTimestampDigit(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097547", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getLDAPIllegalTimestampDigitLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097547", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getLDAPIllegalTimestampDesignator(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097548", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getLDAPIllegalTimestampDesignatorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097548", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getLDAPMissingTimestampDesignator(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097549", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getLDAPMissingTimestampDesignatorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097549", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getLDAPOutOfAgeMonth(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097550", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getLDAPOutOfAgeMonthLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097550", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getLDAPOutOfAgeDay(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097551", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getLDAPOutOfAgeDayLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097551", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getInvalidSPNEGOParseInfo() {
      Object[] args = new Object[0];
      return (new Loggable("097552", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getInvalidSPNEGOParseInfoLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097552", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getSAMLCouldNotGenerate(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return (new Loggable("097553", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLCouldNotGenerateLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      return new LoggableMessageSpiImpl("097553", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getIllegalQuery() {
      Object[] args = new Object[0];
      return (new Loggable("097554", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getIllegalQueryLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097554", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getStoreUnsupportedNestedContainer() {
      Object[] args = new Object[0];
      return (new Loggable("097555", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getStoreUnsupportedNestedContainerLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097555", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getNullOrEmptyPrimaryKey(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097556", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNullOrEmptyPrimaryKeyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097556", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getSAMLInvalidSigningKey(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097558", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLInvalidSigningKeyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097558", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getSAMLInvalidSSLKey(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097559", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLInvalidSSLKeyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097559", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getSAMLInvalidSourceSiteConfig(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097560", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLInvalidSourceSiteConfigLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097560", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getStoreServiceNotInitialized() {
      Object[] args = new Object[0];
      return (new Loggable("097561", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getStoreServiceNotInitializedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097561", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getSAMLInvalidPostFormConfig() {
      Object[] args = new Object[0];
      return (new Loggable("097562", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getSAMLInvalidPostFormConfigLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097562", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getCouldNotGetConnectionForSQLName(String arg0) {
      Object[] args = new Object[]{arg0};
      return (new Loggable("097563", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getCouldNotGetConnectionForSQLNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      return new LoggableMessageSpiImpl("097563", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
   }

   public static String getNamedJDBCServiceInitFailed() {
      Object[] args = new Object[0];
      return (new Loggable("097564", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServiceLogger.class.getClassLoader())).getMessage();
   }

   public static LoggableMessageSpi getNamedJDBCServiceInitFailedLoggable() {
      Object[] args = new Object[0];
      return new LoggableMessageSpiImpl("097564", 8, args, "com.bea.common.security.internal.service.ServiceLogLocalizer", ServiceLogger.class.getClassLoader());
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
      private MessageLogger messageLogger = ServiceLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = ServiceLogger.findMessageLogger();
      }
   }
}
