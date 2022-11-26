package weblogic.messaging.interception;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class MIExceptionLogger {
   private static final String LOCALIZER_CLASS = "weblogic.messaging.interception.MIExceptionLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(MIExceptionLogger.class.getName());
   }

   public static String logSetupJNDIException(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420000", 16, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420000";
   }

   public static Loggable logSetupJNDIExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420000", 16, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAddAssociationInputError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420002", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420002";
   }

   public static Loggable logAddAssociationInputErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420002", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAddAssociationUnknownInterceptionPointTypeError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420003", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420003";
   }

   public static Loggable logAddAssociationUnknownInterceptionPointTypeErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420003", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRemoveAssociationInputError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420004", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420004";
   }

   public static Loggable logRemoveAssociationInputErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420004", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRegisterInterceptionPointNameDescriptionInputError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420005", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420005";
   }

   public static Loggable logRegisterInterceptionPointNameDescriptionInputErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420005", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRegisterInterceptionPointInputError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420006", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420006";
   }

   public static Loggable logRegisterInterceptionPointInputErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420006", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRegisterInterceptionPointUnknownInterceptionPointTypeError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420007", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420007";
   }

   public static Loggable logRegisterInterceptionPointUnknownInterceptionPointTypeErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420007", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnRegisterInterceptionPointInputError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420008", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420008";
   }

   public static Loggable logUnRegisterInterceptionPointInputErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420008", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRegisterProcessorTypeInputError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420009", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420009";
   }

   public static Loggable logRegisterProcessorTypeInputErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420009", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAddProcessorInputError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420010", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420010";
   }

   public static Loggable logAddProcessorInputErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420010", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAddProcessorUnknownProcessorTypeError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420011", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420011";
   }

   public static Loggable logAddProcessorUnknownProcessorTypeErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420011", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRemoveProcessorInputError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420012", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420012";
   }

   public static Loggable logRemoveProcessorInputErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420012", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGetAssociationHandleInputError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420013", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420013";
   }

   public static Loggable logGetAssociationHandleInputErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420013", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGetProcessorHandlesInputError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420014", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420014";
   }

   public static Loggable logGetProcessorHandlesInputErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420014", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGetProcessorHandleInputError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420015", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420015";
   }

   public static Loggable logGetProcessorHandleInputErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420015", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRegisterInterceptionPointNameDescriptionListenerInputError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420016", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420016";
   }

   public static Loggable logRegisterInterceptionPointNameDescriptionListenerInputErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420016", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRemoveAssociationAlreadyRemoveError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420017", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420017";
   }

   public static Loggable logRemoveAssociationAlreadyRemoveErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420017", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logProcessProcessorNotFoundError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420019", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420019";
   }

   public static Loggable logProcessProcessorNotFoundErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420019", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logProcessIllegalError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420020", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420020";
   }

   public static Loggable logProcessIllegalErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420020", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logProcessIllegalException(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420021", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420021";
   }

   public static Loggable logProcessIllegalExceptionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420021", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAddAssociationAlreadyExistError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420025", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420025";
   }

   public static Loggable logAddAssociationAlreadyExistErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420025", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnregisterInterceptionPointAlreayRemoveError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420026", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420026";
   }

   public static Loggable logUnregisterInterceptionPointAlreayRemoveErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420026", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidInterceptionPointName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420027", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420027";
   }

   public static Loggable logInvalidInterceptionPointNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420027", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRemoveProcessorAlreadyRemoveError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420028", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420028";
   }

   public static Loggable logRemoveProcessorAlreadyRemoveErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420028", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logProcessorFactoryCreateError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420029", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420029";
   }

   public static Loggable logProcessorFactoryCreateErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420029", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logProcessorFactoryCreateUnknownError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420030", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420030";
   }

   public static Loggable logProcessorFactoryCreateUnknownErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420030", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logProcessProcessorDepthExceededError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("420031", 8, args, MIExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "420031";
   }

   public static Loggable logProcessProcessorDepthExceededErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("420031", 8, args, "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MIExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.messaging.interception.MIExceptionLogLocalizer", MIExceptionLogger.class.getClassLoader());
      private MessageLogger messageLogger = MIExceptionLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = MIExceptionLogger.findMessageLogger();
      }
   }
}
