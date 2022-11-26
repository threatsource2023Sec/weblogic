package weblogic.management.jmx;

import java.util.Locale;
import javax.management.ObjectName;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class JMXLogger {
   private static final String LOCALIZER_CLASS = "weblogic.management.jmx.JMXLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(JMXLogger.class.getName());
   }

   public static String logRegistrationFailed(ObjectName arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149500", 8, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149500";
   }

   public static String logRegistrationFailedForProperty(ObjectName arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149501", 8, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149501";
   }

   public static String logUnregisterFailed(ObjectName arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149502", 8, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149502";
   }

   public static String logUnregisterFailedForProperty(ObjectName arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149503", 8, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149503";
   }

   public static String logUnableToContactManagedServer(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149504", 16, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149504";
   }

   public static String logManagedServerNotAvailable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149505", 16, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149505";
   }

   public static String logEstablishedJMXConnectionWithManagedServer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149506", 16, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149506";
   }

   public static String logDisconnectedJMXConnectionWithManagedServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149507", 16, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149507";
   }

   public static String logExceptionEstablishingJMXConnectivity(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149508", 16, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149508";
   }

   public static String logUnableToEstablishJMXConnectivity(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149509", 16, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149509";
   }

   public static String logAdminstrationServerNotAvailable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149510", 16, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149510";
   }

   public static String logEstablishedJMXConnectivity(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149511", 16, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149511";
   }

   public static String logStartedJMXConnectorServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149512", 32, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149512";
   }

   public static String logStoppedJMXConnectorServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149513", 16, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149513";
   }

   public static String logErrorGeneratingAttributeChangeNotification(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149514", 128, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149514";
   }

   public static String logErrorDuringGetAttributes(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149515", 16, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149515";
   }

   public static String logErrorDuringSetAttributes(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149516", 16, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149516";
   }

   public static String logBeanUnregisterFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149517", 128, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149517";
   }

   public static String logWLSMBeanUnregisterFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149518", 16, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149518";
   }

   public static Loggable logWLSMBeanUnregisterFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149518", 16, args, "weblogic.management.jmx.JMXLogLocalizer", JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMXLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logManagedServerURLMalformed(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149519", 16, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149519";
   }

   public static String logMBeanRegistrationFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149520", 8, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149520";
   }

   public static Loggable logMBeanRegistrationFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149520", 8, args, "weblogic.management.jmx.JMXLogLocalizer", JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMXLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionDuringJMXConnectivity(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149521", 16, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149521";
   }

   public static String getMissingCredentialsError() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149522", 8, args, "weblogic.management.jmx.JMXLogLocalizer", JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMXLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getMissingRmiServerManagerError() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("149523", 8, args, "weblogic.management.jmx.JMXLogLocalizer", JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMXLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logStartedDwpJmxConnectorServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149524", 32, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149524";
   }

   public static String logStoppedDwpJmxConnectorServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149525", 32, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149525";
   }

   public static String logJRFMBeanAnnotatedAttributeFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149526", 8, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149526";
   }

   public static Loggable logJRFMBeanAnnotatedAttributeFailedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149526", 8, args, "weblogic.management.jmx.JMXLogLocalizer", JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMXLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJRFMBeanAnnotatedOperationFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149527", 8, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149527";
   }

   public static Loggable logJRFMBeanAnnotatedOperationFailedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149527", 8, args, "weblogic.management.jmx.JMXLogLocalizer", JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMXLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMBeanAnnotatedGetAttributeFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149528", 8, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149528";
   }

   public static Loggable logMBeanAnnotatedGetAttributeFailedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149528", 8, args, "weblogic.management.jmx.JMXLogLocalizer", JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMXLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMBeanAnnotatedSetAttributeFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149529", 8, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149529";
   }

   public static Loggable logMBeanAnnotatedSetAttributeFailedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149529", 8, args, "weblogic.management.jmx.JMXLogLocalizer", JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMXLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMBeanAnnotatedOperationFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149530", 8, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149530";
   }

   public static Loggable logMBeanAnnotatedOperationFailedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149530", 8, args, "weblogic.management.jmx.JMXLogLocalizer", JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMXLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMBeanAnnotatedFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149531", 8, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149531";
   }

   public static Loggable logMBeanAnnotatedFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("149531", 8, args, "weblogic.management.jmx.JMXLogLocalizer", JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMXLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDifferentMBeanPartitionValueFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("149532", 8, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149532";
   }

   public static Loggable logDifferentMBeanPartitionValueFailedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("149532", 8, args, "weblogic.management.jmx.JMXLogLocalizer", JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMXLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logParamsNotVisibleToPartition(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("149533", 8, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149533";
   }

   public static Loggable logParamsNotVisibleToPartitionLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("149533", 8, args, "weblogic.management.jmx.JMXLogLocalizer", JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMXLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorAllMBeansQueryNames(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149534", 8, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149534";
   }

   public static String logJMXResiliencyWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149535", 32, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149535";
   }

   public static String logNoSuchServerInConfiguration(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149536", 16, args, JMXLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMXLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149536";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.jmx.JMXLogLocalizer", JMXLogger.class.getClassLoader());
      private MessageLogger messageLogger = JMXLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = JMXLogger.findMessageLogger();
      }
   }
}
