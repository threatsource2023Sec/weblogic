package com.bea.httppubsub.jms;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class PubSubJmsLogger {
   private static final String LOCALIZER_CLASS = "com.bea.httppubsub.jms.PubSubJmsLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(PubSubJmsLogger.class.getName());
   }

   public static String logCannotCreateJmsProviderFactory(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2152001", 8, args, PubSubJmsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2152001";
   }

   public static String logCannotCreateJmsProviderFactoryForGivenClassName(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2152002", 8, args, PubSubJmsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2152002";
   }

   public static Loggable logCannotCreateJmsProviderFactoryForGivenClassNameLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2152002", 8, args, "com.bea.httppubsub.jms.PubSubJmsLogLocalizer", PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubJmsLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJmsProviderFactoryRegistered(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2152003", 64, args, PubSubJmsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2152003";
   }

   public static String logJmsConfigurations(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2152021", 64, args, PubSubJmsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2152021";
   }

   public static String logCannotRetrieveJmsResources(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2152022", 8, args, PubSubJmsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2152022";
   }

   public static Loggable logCannotRetrieveJmsResourcesLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2152022", 8, args, "com.bea.httppubsub.jms.PubSubJmsLogLocalizer", PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubJmsLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logChannelHandlerMapping(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2152041", 64, args, PubSubJmsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2152041";
   }

   public static String logNoChannelBeanConfigured() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2152042", 64, args, PubSubJmsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2152042";
   }

   public static String logJmsHandlerMappingCount(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2152043", 64, args, PubSubJmsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2152043";
   }

   public static String logJmsHandlerMapping(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2152044", 64, args, PubSubJmsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2152044";
   }

   public static String logCannotInitializeJmsChannel(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2152061", 8, args, PubSubJmsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2152061";
   }

   public static String logCannotPublishMessageToTopic(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2152081", 16, args, PubSubJmsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2152081";
   }

   public static String logCannotInitializeJmsPublishHandler(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2152082", 8, args, PubSubJmsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2152082";
   }

   public static Loggable logCannotInitializeJmsPublishHandlerLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2152082", 8, args, "com.bea.httppubsub.jms.PubSubJmsLogLocalizer", PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubJmsLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoPermissionPublish(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2152101", 16, args, PubSubJmsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2152101";
   }

   public static String logCannotInitializeJmstopicListener(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2152102", 8, args, PubSubJmsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2152102";
   }

   public static Loggable logCannotInitializeJmstopicListenerLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2152102", 8, args, "com.bea.httppubsub.jms.PubSubJmsLogLocalizer", PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubJmsLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorRetrieveMessageFromJmsTopic(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2152103", 16, args, PubSubJmsLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubJmsLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2152103";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "com.bea.httppubsub.jms.PubSubJmsLogLocalizer", PubSubJmsLogger.class.getClassLoader());
      private MessageLogger messageLogger = PubSubJmsLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = PubSubJmsLogger.findMessageLogger();
      }
   }
}
