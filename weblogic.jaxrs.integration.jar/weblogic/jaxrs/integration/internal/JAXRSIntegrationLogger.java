package weblogic.jaxrs.integration.internal;

import java.lang.reflect.Method;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class JAXRSIntegrationLogger {
   private static final String LOCALIZER_CLASS = "weblogic.jaxrs.integration.internal.JAXRSIntegrationLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(JAXRSIntegrationLogger.class.getName());
   }

   public static String logApplicationMBeanNotInitialized(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192500", 16, args, JAXRSIntegrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192500";
   }

   public static Loggable logApplicationMBeanNotInitializedLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192500", 16, args, "weblogic.jaxrs.integration.internal.JAXRSIntegrationLogLocalizer", JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JAXRSIntegrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotFindUriForResourceClass(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192501", 64, args, JAXRSIntegrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192501";
   }

   public static Loggable logCannotFindUriForResourceClassLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192501", 64, args, "weblogic.jaxrs.integration.internal.JAXRSIntegrationLogLocalizer", JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JAXRSIntegrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDoNotCallJaxRsApplicationRuntimeMBeanSetApplicationEnabled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2192502", 64, args, JAXRSIntegrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192502";
   }

   public static Loggable logDoNotCallJaxRsApplicationRuntimeMBeanSetApplicationEnabledLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2192502", 64, args, "weblogic.jaxrs.integration.internal.JAXRSIntegrationLogLocalizer", JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JAXRSIntegrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getINITIAL_CONTEXT_NOT_AVAILABLE() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2192503", 8, args, "weblogic.jaxrs.integration.internal.JAXRSIntegrationLogLocalizer", JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JAXRSIntegrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getEJB_COMPONENT_PROVIDER_NOT_INITIALIZED_PROPERLY() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2192504", 8, args, "weblogic.jaxrs.integration.internal.JAXRSIntegrationLogLocalizer", JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JAXRSIntegrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logEJB_CLASS_SIMPLE_LOOKUP_FAILED(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192505", 16, args, JAXRSIntegrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192505";
   }

   public static Loggable logEJB_CLASS_SIMPLE_LOOKUP_FAILEDLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192505", 16, args, "weblogic.jaxrs.integration.internal.JAXRSIntegrationLogLocalizer", JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JAXRSIntegrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJB_INTERFACE_HANDLING_METHOD_LOOKUP_EXCEPTION(Method arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2192506", 16, args, JAXRSIntegrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192506";
   }

   public static Loggable logEJB_INTERFACE_HANDLING_METHOD_LOOKUP_EXCEPTIONLoggable(Method arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("2192506", 16, args, "weblogic.jaxrs.integration.internal.JAXRSIntegrationLogLocalizer", JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JAXRSIntegrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJB_CLASS_BOUND_WITH_CDI(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192507", 64, args, JAXRSIntegrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192507";
   }

   public static Loggable logEJB_CLASS_BOUND_WITH_CDILoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192507", 64, args, "weblogic.jaxrs.integration.internal.JAXRSIntegrationLogLocalizer", JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JAXRSIntegrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logException(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192508", 8, args, JAXRSIntegrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192508";
   }

   public static Loggable logExceptionLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192508", 8, args, "weblogic.jaxrs.integration.internal.JAXRSIntegrationLogLocalizer", JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JAXRSIntegrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logChangingServletClass(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2192509", 16, args, JAXRSIntegrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192509";
   }

   public static Loggable logChangingServletClassLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2192509", 16, args, "weblogic.jaxrs.integration.internal.JAXRSIntegrationLogLocalizer", JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JAXRSIntegrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCanNotAddJerseyServlet(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192510", 16, args, JAXRSIntegrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192510";
   }

   public static Loggable logCanNotAddJerseyServletLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192510", 16, args, "weblogic.jaxrs.integration.internal.JAXRSIntegrationLogLocalizer", JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JAXRSIntegrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logListOfResourcePackages(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2192511", 64, args, JAXRSIntegrationLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2192511";
   }

   public static Loggable logListOfResourcePackagesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2192511", 64, args, "weblogic.jaxrs.integration.internal.JAXRSIntegrationLogLocalizer", JAXRSIntegrationLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JAXRSIntegrationLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.jaxrs.integration.internal.JAXRSIntegrationLogLocalizer", JAXRSIntegrationLogger.class.getClassLoader());
      private MessageLogger messageLogger = JAXRSIntegrationLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = JAXRSIntegrationLogger.findMessageLogger();
      }
   }
}
