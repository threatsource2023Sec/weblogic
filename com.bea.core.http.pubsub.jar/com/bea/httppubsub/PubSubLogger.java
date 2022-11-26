package com.bea.httppubsub;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class PubSubLogger {
   private static final String LOCALIZER_CLASS = "com.bea.httppubsub.PubSubLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(PubSubLogger.class.getName());
   }

   public static String logConfigurationValidationProblem(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150001", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150001";
   }

   public static String logNoPermissionCreateChannel(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150002", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150002";
   }

   public static Loggable logNoPermissionCreateChannelLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2150002", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoPermissionDeleteChannel(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150003", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150003";
   }

   public static String logInvalidPersistentClientTimeout(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150005", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150005";
   }

   public static Loggable logInvalidPersistentClientTimeoutLoggable(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2150005", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTransportType(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2150006", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150006";
   }

   public static Loggable logInvalidTransportTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2150006", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMismatchMessageFilterDefined(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2150007", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150007";
   }

   public static Loggable logMismatchMessageFilterDefinedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2150007", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoMessageFilterConfigured(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2150008", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150008";
   }

   public static Loggable logNoMessageFilterConfiguredLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2150008", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidMessageFilterConfiguration(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2150009", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150009";
   }

   public static Loggable logInvalidMessageFilterConfigurationLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2150009", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoSecurityConstraint(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150010", 128, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150010";
   }

   public static String logAlwaysSecurityConstraint(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150011", 64, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150011";
   }

   public static String logNotLogin(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150012", 64, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150012";
   }

   public static String logLoginDisallowed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2150013", 64, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150013";
   }

   public static String logLoginAllowed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2150014", 64, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150014";
   }

   public static String logClientHasNoPermissionSubscribe(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150015", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150015";
   }

   public static Loggable logClientHasNoPermissionSubscribeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2150015", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logClientHasNoPermissionPublish(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150016", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150016";
   }

   public static Loggable logClientHasNoPermissionPublishLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2150016", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailRegisterMBean(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150017", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150017";
   }

   public static String logFailUnregisterMBean(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150018", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150018";
   }

   public static String logInitServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2150019", 64, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150019";
   }

   public static String logDuplicateInitServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2150020", 16, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150020";
   }

   public static String logCannotInitServiceChannel(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150021", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150021";
   }

   public static Loggable logCannotInitServiceChannelLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2150021", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnknownMetaChannelFromBayeuxMessage(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2150024", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150024";
   }

   public static Loggable logUnknownMetaChannelFromBayeuxMessageLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2150024", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsetMBeanManagerFactory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2150025", 16, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150025";
   }

   public static String logCannotInitMBeanManagerFactory(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150026", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150026";
   }

   public static Loggable logCannotInitMBeanManagerFactoryLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2150026", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailRegisterWebPubSubRuntimeMBean(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150027", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150027";
   }

   public static String logFailUnregisterWebPubSubRuntimeMBean(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150028", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150028";
   }

   public static String logControllerServletInitFails(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150029", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150029";
   }

   public static Loggable logControllerServletInitFailsLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2150029", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotFetchJsonMessageFromRequest() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2150030", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150030";
   }

   public static Loggable logCannotFetchJsonMessageFromRequestLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2150030", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotLoadProperty(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150031", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150031";
   }

   public static Loggable logCannotLoadPropertyLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2150031", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotFindPersistentStore(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150032", 16, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150032";
   }

   public static String logInvalidClientTimeout(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2150033", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150033";
   }

   public static Loggable logInvalidClientTimeoutLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2150033", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidInterval(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2150034", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150034";
   }

   public static Loggable logInvalidIntervalLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2150034", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidMultiFrameInterval(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2150035", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150035";
   }

   public static Loggable logInvalidMultiFrameIntervalLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2150035", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidPersistentTimeout(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2150036", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150036";
   }

   public static Loggable logInvalidPersistentTimeoutLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2150036", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidPersistentDuration(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2150037", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150037";
   }

   public static Loggable logInvalidPersistentDurationLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2150037", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logChannelAutorizationManagerFactory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2150038", 64, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150038";
   }

   public static String logErrorInitChannelAuthorizationManager(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2150039", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150039";
   }

   public static String logDebug(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2150040", 128, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150040";
   }

   public static String logInvalidChannelFoundInBayeuxMessage(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2150041", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150041";
   }

   public static Loggable logInvalidChannelFoundInBayeuxMessageLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2150041", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logImplicitRoleDeployment(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2150042", 16, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150042";
   }

   public static String logSecurityAuthorizationDisabled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2150043", 16, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150043";
   }

   public static String logInvalidConnectionTimeout(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2150044", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150044";
   }

   public static Loggable logInvalidConnectionTimeoutLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2150044", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoChannelInBayeuxMessage() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2150045", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150045";
   }

   public static Loggable logNoChannelInBayeuxMessageLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2150045", 8, args, "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger, PubSubLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJsonMessageParseError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2150046", 8, args, PubSubLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      PubSubLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2150046";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "com.bea.httppubsub.PubSubLogLocalizer", PubSubLogger.class.getClassLoader());
      private MessageLogger messageLogger = PubSubLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = PubSubLogger.findMessageLogger();
      }
   }
}
