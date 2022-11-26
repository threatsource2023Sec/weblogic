package weblogic.deployment.jms;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class JMSPoolLogger {
   private static final String LOCALIZER_CLASS = "weblogic.deployment.jms.JMSPoolLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(JMSPoolLogger.class.getName());
   }

   public static String logInvalidExternalVersion(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("169800", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169800";
   }

   public static Loggable logInvalidExternalVersionLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("169800", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidJ2EEMethod(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("169801", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169801";
   }

   public static Loggable logInvalidJ2EEMethodLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("169801", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSConnectionBadAppAuth() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("169802", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169802";
   }

   public static Loggable logJMSConnectionBadAppAuthLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("169802", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSConnectionFactoryLookupFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("169803", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169803";
   }

   public static Loggable logJMSConnectionFactoryLookupFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("169803", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSConnectionFactoryWrongType() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("169804", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169804";
   }

   public static Loggable logJMSConnectionFactoryWrongTypeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("169804", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSConnectionFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("169805", 64, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169805";
   }

   public static String logJMSDestinationWrongType() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("169806", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169806";
   }

   public static Loggable logJMSDestinationWrongTypeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("169806", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSInitialConnectionFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("169807", 16, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169807";
   }

   public static String logJMSInitialConnectionFailedEJB(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("169808", 16, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169808";
   }

   public static String logJMSJTARegistrationError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("169809", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169809";
   }

   public static Loggable logJMSJTARegistrationErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("169809", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSJTAResolutionError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("169810", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169810";
   }

   public static Loggable logJMSJTAResolutionErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("169810", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSObjectClosed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("169811", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169811";
   }

   public static Loggable logJMSObjectClosedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("169811", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSSessionAlreadyEnlisted() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("169812", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169812";
   }

   public static Loggable logJMSSessionAlreadyEnlistedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("169812", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSSessionPoolCloseError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("169813", 16, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169813";
   }

   public static String logJMSSessionPoolDisabled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("169814", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169814";
   }

   public static Loggable logJMSSessionPoolDisabledLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("169814", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSSessionPoolPropertyMissing(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("169815", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169815";
   }

   public static Loggable logJMSSessionPoolPropertyMissingLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("169815", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSSessionPoolShutDown() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("169816", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169816";
   }

   public static Loggable logJMSSessionPoolShutDownLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("169816", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoXAOnJMSResource(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("169817", 16, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169817";
   }

   public static String logNotAConnectionFactory(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("169818", 16, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169818";
   }

   public static Loggable logNotAConnectionFactoryLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("169818", 16, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logResourcePoolError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("169819", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169819";
   }

   public static Loggable logResourcePoolErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("169819", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTemporaryDestinationUnrecognized() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("169820", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169820";
   }

   public static Loggable logTemporaryDestinationUnrecognizedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("169820", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWrappedClassError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("169821", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169821";
   }

   public static Loggable logWrappedClassErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("169821", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logXANotAvailable() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("169822", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169822";
   }

   public static Loggable logXANotAvailableLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("169822", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSUnableToCreateQueueConnection() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("169823", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169823";
   }

   public static Loggable logJMSUnableToCreateQueueConnectionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("169823", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSUnableToCreateTopicConnection() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("169824", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169824";
   }

   public static Loggable logJMSUnableToCreateTopicConnectionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("169824", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSContextBadAppAuth() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("169825", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169825";
   }

   public static Loggable logJMSContextBadAppAuthLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("169825", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotAConnectionFactoryForContext(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("169826", 16, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169826";
   }

   public static Loggable logNotAConnectionFactoryForContextLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("169826", 16, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLookupDefaultJMSConnectionFactory(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("169827", 64, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169827";
   }

   public static Loggable logLookupDefaultJMSConnectionFactoryLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("169827", 64, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidSessionMode(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("169828", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169828";
   }

   public static Loggable logInvalidSessionModeLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("169828", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCalledMissingJMS20Method(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("169829", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169829";
   }

   public static Loggable logCalledMissingJMS20MethodLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("169829", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSConnectionFactoryUnmappedResRefMode(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("169830", 64, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169830";
   }

   public static String logInvalidConnectionFactoryUnmappedResRefMode(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("169831", 16, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169831";
   }

   public static String logUnsupportedOBSEnabledCF(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("169832", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169832";
   }

   public static Loggable logUnsupportedOBSEnabledCFLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("169832", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedConnectionFactoryReferencedByForeignSever() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("169833", 8, args, JMSPoolLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "169833";
   }

   public static Loggable logUnsupportedConnectionFactoryReferencedByForeignSeverLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("169833", 8, args, "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSPoolLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.deployment.jms.JMSPoolLogLocalizer", JMSPoolLogger.class.getClassLoader());
      private MessageLogger messageLogger = JMSPoolLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = JMSPoolLogger.findMessageLogger();
      }
   }
}
