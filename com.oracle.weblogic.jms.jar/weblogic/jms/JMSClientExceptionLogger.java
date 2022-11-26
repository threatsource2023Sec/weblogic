package weblogic.jms;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class JMSClientExceptionLogger {
   private static final String LOCALIZER_CLASS = "weblogic.jms.JMSClientExceptionLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(JMSClientExceptionLogger.class.getName());
   }

   public static String logInvalidTimeToDeliver() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055001", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055001";
   }

   public static Loggable logInvalidTimeToDeliverLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055001", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConvertBoolean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055002", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055002";
   }

   public static Loggable logConvertBooleanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055002", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullByte() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055003", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055003";
   }

   public static Loggable logNullByteLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055003", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConvertByte(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055004", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055004";
   }

   public static Loggable logConvertByteLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055004", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullShort() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055005", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055005";
   }

   public static Loggable logNullShortLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055005", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConvertShort(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055006", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055006";
   }

   public static Loggable logConvertShortLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055006", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSerializationError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055007", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055007";
   }

   public static Loggable logSerializationErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055007", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidRedeliveryLimit() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055008", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055008";
   }

   public static Loggable logInvalidRedeliveryLimitLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055008", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidSendTimeout() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055009", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055009";
   }

   public static Loggable logInvalidSendTimeoutLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055009", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorSendingMessage() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055014", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055014";
   }

   public static Loggable logErrorSendingMessageLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055014", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidDeliveryMode() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055015", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055015";
   }

   public static Loggable logInvalidDeliveryModeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055015", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidPriority() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055016", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055016";
   }

   public static Loggable logInvalidPriorityLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055016", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedSubscription() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055017", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055017";
   }

   public static Loggable logUnsupportedSubscriptionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055017", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoMulticastOnQueueSessions() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055018", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055018";
   }

   public static Loggable logNoMulticastOnQueueSessionsLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055018", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDuplicateSession() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055019", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055019";
   }

   public static Loggable logDuplicateSessionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055019", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullClientID() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055021", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055021";
   }

   public static Loggable logNullClientIDLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055021", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logZeroClientID() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055022", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055022";
   }

   public static Loggable logZeroClientIDLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055022", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidMessagesMaximum(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055023", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055023";
   }

   public static Loggable logInvalidMessagesMaximumLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055023", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoSuchMethod(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055024", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055024";
   }

   public static Loggable logNoSuchMethodLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055024", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidMessagesMaximumValue() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055025", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055025";
   }

   public static Loggable logInvalidMessagesMaximumValueLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055025", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidOverrunPolicy(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055026", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055026";
   }

   public static Loggable logInvalidOverrunPolicyLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055026", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidRedeliveryDelay() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055027", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055027";
   }

   public static Loggable logInvalidRedeliveryDelayLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055027", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoSubscriberName() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055028", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055028";
   }

   public static Loggable logNoSubscriberNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055028", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logZeroLengthSubscriberName() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055029", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055029";
   }

   public static Loggable logZeroLengthSubscriberNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055029", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidDistributedTopic() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055030", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055030";
   }

   public static Loggable logInvalidDistributedTopicLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055030", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidUnsubscribe() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055031", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055031";
   }

   public static Loggable logInvalidUnsubscribeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055031", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidConsumerCreation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055032", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055032";
   }

   public static Loggable logInvalidConsumerCreationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055032", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoMulticastForQueues() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055033", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055033";
   }

   public static Loggable logNoMulticastForQueuesLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055033", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTopicNoMulticast(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055034", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055034";
   }

   public static Loggable logTopicNoMulticastLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055034", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotOpenMulticastSocket(IOException arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055035", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055035";
   }

   public static Loggable logCannotOpenMulticastSocketLoggable(IOException arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055035", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotJoinMulticastGroup(String arg0, IOException arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("055036", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055036";
   }

   public static Loggable logCannotJoinMulticastGroupLoggable(String arg0, IOException arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("055036", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSubscriptionNameInUse(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055037", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055037";
   }

   public static Loggable logSubscriptionNameInUseLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055037", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidFrontEndResponse(Object arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055038", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055038";
   }

   public static Loggable logInvalidFrontEndResponseLoggable(Object arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055038", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSystemError(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055039", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055039";
   }

   public static Loggable logSystemErrorLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055039", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSubscriptionNameInUse2(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055040", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055040";
   }

   public static Loggable logSubscriptionNameInUse2Loggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055040", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotLeaveMulticastGroup(String arg0, IOException arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("055041", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055041";
   }

   public static Loggable logCannotLeaveMulticastGroupLoggable(String arg0, IOException arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("055041", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoSuchMethod2(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("055042", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055042";
   }

   public static Loggable logNoSuchMethod2Loggable(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("055042", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoSuchMethod3(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("055043", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055043";
   }

   public static Loggable logNoSuchMethod3Loggable(int arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("055043", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoSynchronousMulticastReceive() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055044", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055044";
   }

   public static Loggable logNoSynchronousMulticastReceiveLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055044", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTimeout(long arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055045", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055045";
   }

   public static Loggable logInvalidTimeoutLoggable(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055045", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logListenerExists() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055046", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055046";
   }

   public static Loggable logListenerExistsLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055046", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoSuchMethod4(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055047", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055047";
   }

   public static Loggable logNoSuchMethod4Loggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055047", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMulticastSelectors() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055048", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055048";
   }

   public static Loggable logMulticastSelectorsLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055048", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInternalError(IllegalAccessException arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055049", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055049";
   }

   public static Loggable logInternalErrorLoggable(IllegalAccessException arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055049", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInternalError2(NoSuchMethodException arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055050", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055050";
   }

   public static Loggable logInternalError2Loggable(NoSuchMethodException arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055050", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInternalError3(InstantiationException arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055051", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055051";
   }

   public static Loggable logInternalError3Loggable(InstantiationException arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055051", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidSelector(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055052", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055052";
   }

   public static Loggable logInvalidSelectorLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055052", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCreatingConnection(RemoteException arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055053", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055053";
   }

   public static Loggable logErrorCreatingConnectionLoggable(RemoteException arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055053", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorFindingDispatcher(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055054", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055054";
   }

   public static Loggable logErrorFindingDispatcherLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055054", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoTransaction() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055055", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055055";
   }

   public static Loggable logNoTransactionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055055", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCommittingSession() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055056", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055056";
   }

   public static Loggable logErrorCommittingSessionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055056", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoTransaction2() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055057", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055057";
   }

   public static Loggable logNoTransaction2Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055057", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorRollingBackSession() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055058", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055058";
   }

   public static Loggable logErrorRollingBackSessionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055058", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logOnlyFromServer() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055059", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055059";
   }

   public static Loggable logOnlyFromServerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055059", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoTransaction3() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055060", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055060";
   }

   public static Loggable logNoTransaction3Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055060", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoTransaction4() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055061", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055061";
   }

   public static Loggable logNoTransaction4Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055061", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTransacted() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055062", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055062";
   }

   public static Loggable logTransactedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055062", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionHasConsumers() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055063", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055063";
   }

   public static Loggable logSessionHasConsumersLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055063", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedTopicOperation() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055064", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055064";
   }

   public static Loggable logUnsupportedTopicOperationLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055064", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedTopicOperation2(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055065", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055065";
   }

   public static Loggable logUnsupportedTopicOperation2Loggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055065", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedTopicOperation3() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055066", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055066";
   }

   public static Loggable logUnsupportedTopicOperation3Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055066", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedTopicOperation4() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055067", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055067";
   }

   public static Loggable logUnsupportedTopicOperation4Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055067", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidSubscription() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055068", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055068";
   }

   public static Loggable logInvalidSubscriptionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055068", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedQueueOperation() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055069", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055069";
   }

   public static Loggable logUnsupportedQueueOperationLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055069", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedQueueOperation2() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055070", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055070";
   }

   public static Loggable logUnsupportedQueueOperation2Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055070", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedQueueOperation3() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055071", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055071";
   }

   public static Loggable logUnsupportedQueueOperation3Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055071", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidConnection() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055072", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055072";
   }

   public static Loggable logInvalidConnectionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055072", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDropNewer() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055073", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055073";
   }

   public static Loggable logDropNewerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055073", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDropOlder() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055074", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055074";
   }

   public static Loggable logDropOlderLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055074", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logClientThrowingException() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055075", 16, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055075";
   }

   public static Loggable logClientThrowingExceptionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055075", 16, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionIsClosed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055076", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055076";
   }

   public static Loggable logSessionIsClosedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055076", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotOverrideDestination() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055077", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055077";
   }

   public static Loggable logCannotOverrideDestinationLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055077", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotOverrideDestination2() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055078", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055078";
   }

   public static Loggable logCannotOverrideDestination2Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055078", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNeedDestination() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055079", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055079";
   }

   public static Loggable logNeedDestinationLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055079", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNeedDestination2() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055080", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055080";
   }

   public static Loggable logNeedDestination2Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055080", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedTopicOperation5() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055081", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055081";
   }

   public static Loggable logUnsupportedTopicOperation5Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055081", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logClosedConnection() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055082", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055082";
   }

   public static Loggable logClosedConnectionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055082", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logClientIDSet(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("055083", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055083";
   }

   public static Loggable logClientIDSetLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("055083", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConnectionConsumerOnClient() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055084", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055084";
   }

   public static Loggable logConnectionConsumerOnClientLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055084", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullDestination() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055085", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055085";
   }

   public static Loggable logNullDestinationLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055085", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logForeignDestination() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055086", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055086";
   }

   public static Loggable logForeignDestinationLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055086", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMessageListenerExists() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055087", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055087";
   }

   public static Loggable logMessageListenerExistsLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055087", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logClosedConsumer() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055088", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055088";
   }

   public static Loggable logClosedConsumerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055088", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullDestination2() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055089", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055089";
   }

   public static Loggable logNullDestination2Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055089", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logForeignDestination2() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055090", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055090";
   }

   public static Loggable logForeignDestination2Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055090", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMustBeAQueue(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055091", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055091";
   }

   public static Loggable logMustBeAQueueLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055091", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMustBeATopic(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055092", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055092";
   }

   public static Loggable logMustBeATopicLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055092", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorConvertingForeignMessage() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055093", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055093";
   }

   public static Loggable logErrorConvertingForeignMessageLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055093", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logClosedProducer() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055094", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055094";
   }

   public static Loggable logClosedProducerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055094", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logClosedBrowser() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055095", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055095";
   }

   public static Loggable logClosedBrowserLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055095", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullChar() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055096", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055096";
   }

   public static Loggable logNullCharLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055096", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConvertChar(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055097", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055097";
   }

   public static Loggable logConvertCharLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055097", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullInt() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055098", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055098";
   }

   public static Loggable logNullIntLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055098", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConvertInt(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055099", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055099";
   }

   public static Loggable logConvertIntLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055099", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullLong() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055100", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055100";
   }

   public static Loggable logNullLongLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055100", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConvertLong(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055101", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055101";
   }

   public static Loggable logConvertLongLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055101", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullFloat() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055102", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055102";
   }

   public static Loggable logNullFloatLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055102", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConvertFloat(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055103", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055103";
   }

   public static Loggable logConvertFloatLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055103", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullDouble() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055104", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055104";
   }

   public static Loggable logNullDoubleLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055104", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConvertDouble(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055105", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055105";
   }

   public static Loggable logConvertDoubleLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055105", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConvertByteArray() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055106", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055106";
   }

   public static Loggable logConvertByteArrayLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055106", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConvertToByteArray(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055107", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055107";
   }

   public static Loggable logConvertToByteArrayLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055107", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReadPastEnd() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055108", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055108";
   }

   public static Loggable logReadPastEndLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055108", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStreamReadError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055109", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055109";
   }

   public static Loggable logStreamReadErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055109", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStreamWriteError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055110", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055110";
   }

   public static Loggable logStreamWriteErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055110", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConversionError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("055111", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055111";
   }

   public static Loggable logConversionErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("055111", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStreamReadErrorIndex() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055112", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055112";
   }

   public static Loggable logStreamReadErrorIndexLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055112", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStreamReadErrorStore() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055113", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055113";
   }

   public static Loggable logStreamReadErrorStoreLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055113", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeserializeIO() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055114", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055114";
   }

   public static Loggable logDeserializeIOLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055114", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeserializeCNFE() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055115", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055115";
   }

   public static Loggable logDeserializeCNFELoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055115", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnknownMessageType(byte arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055116", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055116";
   }

   public static Loggable logUnknownMessageTypeLoggable(byte arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055116", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidDeliveryMode2(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055117", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055117";
   }

   public static Loggable logInvalidDeliveryMode2Loggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055117", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidRedeliveryLimit2() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055118", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055118";
   }

   public static Loggable logInvalidRedeliveryLimit2Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055118", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidPriority2(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055119", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055119";
   }

   public static Loggable logInvalidPriority2Loggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055119", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidPropertyName2(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055121", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055121";
   }

   public static Loggable logInvalidPropertyName2Loggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055121", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWriteInReadMode() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055122", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055122";
   }

   public static Loggable logWriteInReadModeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055122", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidPropertyValue(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055123", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055123";
   }

   public static Loggable logInvalidPropertyValueLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055123", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnknownStreamType() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055125", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055125";
   }

   public static Loggable logUnknownStreamTypeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055125", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCorruptedStream() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055126", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055126";
   }

   public static Loggable logCorruptedStreamLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055126", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logVersionError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055127", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055127";
   }

   public static Loggable logVersionErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055127", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReadInWriteMode() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055128", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055128";
   }

   public static Loggable logReadInWriteModeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055128", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWriteInReadMode2() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055129", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055129";
   }

   public static Loggable logWriteInReadMode2Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055129", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidDataType(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055130", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055130";
   }

   public static Loggable logInvalidDataTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055130", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055131", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055131";
   }

   public static Loggable logIllegalNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055131", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCopyError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055132", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055132";
   }

   public static Loggable logCopyErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055132", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeserializationError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055133", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055133";
   }

   public static Loggable logDeserializationErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055133", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedClassVersion(int arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("055134", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055134";
   }

   public static Loggable logUnsupportedClassVersionLoggable(int arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("055134", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSimpleObject(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055135", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055135";
   }

   public static Loggable logSimpleObjectLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055135", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnrecognizedClassCode(short arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055136", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055136";
   }

   public static Loggable logUnrecognizedClassCodeLoggable(short arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055136", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidResponse(int arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("055137", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055137";
   }

   public static Loggable logInvalidResponseLoggable(int arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("055137", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidPeer(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055138", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055138";
   }

   public static Loggable logInvalidPeerLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055138", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTemporaryDestination() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055139", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055139";
   }

   public static Loggable logInvalidTemporaryDestinationLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055139", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInternalMarshallingError(byte arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055140", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055140";
   }

   public static Loggable logInternalMarshallingErrorLoggable(byte arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055140", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDestinationNull() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055141", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055141";
   }

   public static Loggable logDestinationNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055141", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logForeignDestination3(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055142", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055142";
   }

   public static Loggable logForeignDestination3Loggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055142", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDestinationMustBeQueue(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055143", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055143";
   }

   public static Loggable logDestinationMustBeQueueLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055143", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDestinationMustBeTopic(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055144", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055144";
   }

   public static Loggable logDestinationMustBeTopicLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055144", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnknownStreamVersion(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055145", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055145";
   }

   public static Loggable logUnknownStreamVersionLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055145", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRawObjectError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055146", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055146";
   }

   public static Loggable logRawObjectErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055146", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotImplemented() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055147", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055147";
   }

   public static Loggable logNotImplementedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055147", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRawObjectError2() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055148", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055148";
   }

   public static Loggable logRawObjectError2Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055148", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReadPastEnd2(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055149", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055149";
   }

   public static Loggable logReadPastEnd2Loggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055149", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReadError(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055150", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055150";
   }

   public static Loggable logReadErrorLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055150", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNegativeLength(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055151", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055151";
   }

   public static Loggable logNegativeLengthLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055151", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTooMuchLength(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("055152", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055152";
   }

   public static Loggable logTooMuchLengthLoggable(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("055152", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWriteError(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055153", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055153";
   }

   public static Loggable logWriteErrorLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055153", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidObject(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055154", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055154";
   }

   public static Loggable logInvalidObjectLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055154", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotForwardable2() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055155", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055155";
   }

   public static Loggable logNotForwardable2Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055155", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIncompatibleVersion9(byte arg0, byte arg1, byte arg2, byte arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("055156", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055156";
   }

   public static Loggable logIncompatibleVersion9Loggable(byte arg0, byte arg1, byte arg2, byte arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("055156", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidCompressionThreshold() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055157", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055157";
   }

   public static Loggable logInvalidCompressionThresholdLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055157", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDecompressMessageBody() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055158", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055158";
   }

   public static Loggable logErrorDecompressMessageBodyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055158", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCompressionTag(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055159", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055159";
   }

   public static Loggable logErrorCompressionTagLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055159", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDeserializeMessageBody() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055160", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055160";
   }

   public static Loggable logErrorDeserializeMessageBodyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055160", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotForwardable3() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055161", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055161";
   }

   public static Loggable logNotForwardable3Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055161", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInteropTextMessage() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055162", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055162";
   }

   public static Loggable logErrorInteropTextMessageLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055162", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInteropXMLMessage() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055163", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055163";
   }

   public static Loggable logErrorInteropXMLMessageLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055163", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupported() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055164", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055164";
   }

   public static Loggable logUnsupportedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055164", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStackTrace(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055165", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055165";
   }

   public static Loggable logStackTraceLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055165", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMultiplePrefetchConsumerPerSession() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055167", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055167";
   }

   public static Loggable logMultiplePrefetchConsumerPerSessionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055167", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUserTXNotSupportPrefetchConsumerPerSession() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055168", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055168";
   }

   public static Loggable logUserTXNotSupportPrefetchConsumerPerSessionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055168", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLostServerConnection() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055169", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055169";
   }

   public static Loggable logLostServerConnectionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055169", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidStringProperty() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055170", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055170";
   }

   public static Loggable logInvalidStringPropertyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055170", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidUnrestrictedUnsubscribe(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("055171", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055171";
   }

   public static Loggable logInvalidUnrestrictedUnsubscribeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("055171", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidUnrestrictedUnsubscribe2(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("055172", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055172";
   }

   public static Loggable logInvalidUnrestrictedUnsubscribe2Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("055172", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReadPastEnd3(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055173", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055173";
   }

   public static Loggable logReadPastEnd3Loggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055173", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStreamReadError2(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055174", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055174";
   }

   public static Loggable logStreamReadError2Loggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055174", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStreamWriteError3(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055175", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055175";
   }

   public static Loggable logStreamWriteError3Loggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055175", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnexpectedTransaction() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055176", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055176";
   }

   public static Loggable logUnexpectedTransactionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055176", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidSessionMode(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055177", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055177";
   }

   public static Loggable logInvalidSessionModeLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055177", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMethodForbiddenInJavaEEWebEJB() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055178", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055178";
   }

   public static Loggable logMethodForbiddenInJavaEEWebEJBLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055178", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSContextIsClosed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055179", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055179";
   }

   public static Loggable logJMSContextIsClosedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055179", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSetClientIDCalledInInvalidState() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055180", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055180";
   }

   public static Loggable logSetClientIDCalledInInvalidStateLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055180", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidClientIDNull() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055181", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055181";
   }

   public static Loggable logInvalidClientIDNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055181", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidClientIDEmptyString() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055182", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055182";
   }

   public static Loggable logInvalidClientIDEmptyStringLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055182", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSConsumerIsClosed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055183", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055183";
   }

   public static Loggable logJMSConsumerIsClosedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055183", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMessageIsNull() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055184", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055184";
   }

   public static Loggable logMessageIsNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055184", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidJMSProducerDeliveryMode(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055185", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055185";
   }

   public static Loggable logInvalidJMSProducerDeliveryModeLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055185", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidJMSProducerPriority(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055186", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055186";
   }

   public static Loggable logInvalidJMSProducerPriorityLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055186", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSProducerPropertyNameNull() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055187", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055187";
   }

   public static Loggable logJMSProducerPropertyNameNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055187", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSProducerPropertyNameEmpty() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055188", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055188";
   }

   public static Loggable logJMSProducerPropertyNameEmptyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055188", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSProducerPropertyValueInvalid(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055189", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055189";
   }

   public static Loggable logJMSProducerPropertyValueInvalidLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055189", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSProducerPropertyNameReserved(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055190", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055190";
   }

   public static Loggable logJMSProducerPropertyNameReservedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055190", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSProducerPropertyNameHasBadChar(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055191", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055191";
   }

   public static Loggable logJMSProducerPropertyNameHasBadCharLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055191", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSProducerPropertyNameHasBadFirstChar(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055192", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055192";
   }

   public static Loggable logJMSProducerPropertyNameHasBadFirstCharLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055192", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMessageBodyCannotBeAssignedToSpecifiedType(Class arg0, Class arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("055193", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055193";
   }

   public static Loggable logMessageBodyCannotBeAssignedToSpecifiedTypeLoggable(Class arg0, Class arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("055193", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGetBodyDoesNotSupportStreamMessage() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055194", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055194";
   }

   public static Loggable logGetBodyDoesNotSupportStreamMessageLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055194", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedSharedDurableConnectionConsumer() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055195", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055195";
   }

   public static Loggable logUnsupportedSharedDurableConnectionConsumerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055195", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedSharedConnectionConsumer() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055196", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055196";
   }

   public static Loggable logUnsupportedSharedConnectionConsumerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055196", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidJMSDeliveryDelay() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055197", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055197";
   }

   public static Loggable logInvalidJMSDeliveryDelayLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055197", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidCloseFromListener(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("055198", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055198";
   }

   public static Loggable logInvalidCloseFromListenerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("055198", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoValidScopeForInjectedJMSContext() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055199", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055199";
   }

   public static Loggable logNoValidScopeForInjectedJMSContextLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055199", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAsyncForwardUnsupported() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055200", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055200";
   }

   public static Loggable logAsyncForwardUnsupportedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055200", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidPrioritySend(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055201", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055201";
   }

   public static Loggable logInvalidPrioritySendLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055201", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidDeliveryModeSend(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055202", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055202";
   }

   public static Loggable logInvalidDeliveryModeSendLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055202", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAsyncSentMessageConcurrentSend(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055203", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055203";
   }

   public static Loggable logAsyncSentMessageConcurrentSendLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055203", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedAsyncSendInXA() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055204", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055204";
   }

   public static Loggable logUnsupportedAsyncSendInXALoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055204", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoMessageBody() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055205", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055205";
   }

   public static Loggable logNoMessageBodyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055205", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCompletionListenerIsNull() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055206", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055206";
   }

   public static Loggable logCompletionListenerIsNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055206", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidSecurityPolicy(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("055207", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055207";
   }

   public static Loggable logInvalidSecurityPolicyLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("055207", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidLookupType(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055208", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055208";
   }

   public static Loggable logInvalidLookupTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055208", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidLookupForForeignServer() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("055209", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055209";
   }

   public static Loggable logInvalidLookupForForeignServerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("055209", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedAPI(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055210", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055210";
   }

   public static Loggable logUnsupportedAPILoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055210", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRestrictedAPI(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("055211", 8, args, JMSClientExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "055211";
   }

   public static Loggable logRestrictedAPILoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("055211", 8, args, "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSClientExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.jms.JMSClientExceptionLogLocalizer", JMSClientExceptionLogger.class.getClassLoader());
      private MessageLogger messageLogger = JMSClientExceptionLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = JMSClientExceptionLogger.findMessageLogger();
      }
   }
}
