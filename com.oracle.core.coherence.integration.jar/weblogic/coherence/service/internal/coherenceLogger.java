package weblogic.coherence.service.internal;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class coherenceLogger {
   private static final String LOCALIZER_CLASS = "weblogic.coherence.service.internal.coherenceLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(coherenceLogger.class.getName());
   }

   public static String logCoherenceInitializing(ClassLoader arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194500", 64, args, coherenceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      coherenceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194500";
   }

   public static String logErrorCoherenceInitializing(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194501", 8, args, coherenceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      coherenceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194501";
   }

   public static String logCoherenceShutdown(ClassLoader arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194502", 64, args, coherenceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      coherenceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194502";
   }

   public static String logErrorCoherenceShutdown(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194503", 8, args, coherenceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      coherenceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194503";
   }

   public static String logMessageGARWithoutClusterDefinition(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194504", 16, args, coherenceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      coherenceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194504";
   }

   public static String logWarnWKAWithLocalhost(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194505", 16, args, coherenceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      coherenceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194505";
   }

   public static String logWarnJNDIOverrideNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194506", 16, args, coherenceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      coherenceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194506";
   }

   public static String logErrorCoherenceStarting(Exception arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194507", 8, args, coherenceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      coherenceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194507";
   }

   public static String logWarnCoherenceConfiguration(Exception arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194508", 16, args, coherenceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      coherenceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194508";
   }

   public static String logWarnIncompleteCCSRBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194509", 16, args, coherenceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      coherenceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194509";
   }

   public static String logInfoNoServerMBeanOverride() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2194510", 64, args, coherenceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      coherenceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194510";
   }

   public static String logErrorWKAWithLocalhost(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194511", 8, args, coherenceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      coherenceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194511";
   }

   public static String logWarnGARUndeployFile(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194512", 16, args, coherenceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      coherenceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194512";
   }

   public static String logMessageCoherenceWebWithoutClusterDefinition(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194513", 64, args, coherenceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      coherenceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194513";
   }

   public static String logMessageErrorCreatingTimerManager(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194514", 64, args, coherenceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      coherenceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194514";
   }

   public static String logMessageErrorDuringReapingProcess(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194515", 64, args, coherenceLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      coherenceLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194515";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.coherence.service.internal.coherenceLogLocalizer", coherenceLogger.class.getClassLoader());
      private MessageLogger messageLogger = coherenceLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = coherenceLogger.findMessageLogger();
      }
   }
}
