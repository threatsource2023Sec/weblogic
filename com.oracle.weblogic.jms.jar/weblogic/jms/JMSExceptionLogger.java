package weblogic.jms;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class JMSExceptionLogger {
   private static final String LOCALIZER_CLASS = "weblogic.jms.JMSExceptionLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(JMSExceptionLogger.class.getName());
   }

   public static String logErrorInJNDIBind(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045002", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045002";
   }

   public static Loggable logErrorInJNDIBindLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045002", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMessagesThresholdTimeExceeded(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045028", 16, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045028";
   }

   public static Loggable logMessagesThresholdTimeExceededLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045028", 16, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMessagesThresholdRunningTimeExceeded(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045029", 16, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045029";
   }

   public static Loggable logMessagesThresholdRunningTimeExceededLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045029", 16, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBytesThresholdTimeExceeded(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045030", 16, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045030";
   }

   public static Loggable logBytesThresholdTimeExceededLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045030", 16, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBytesThresholdRunningTimeExceeded(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045031", 16, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045031";
   }

   public static Loggable logBytesThresholdRunningTimeExceededLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045031", 16, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoBackEnd(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045032", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045032";
   }

   public static Loggable logNoBackEndLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045032", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAddUnknownType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045039", 16, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045039";
   }

   public static Loggable logAddUnknownTypeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045039", 16, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorAddingType(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045040", 16, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045040";
   }

   public static Loggable logErrorAddingTypeLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045040", 16, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeleteUnknownType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045041", 16, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045041";
   }

   public static Loggable logDeleteUnknownTypeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045041", 16, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAddFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045042", 16, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045042";
   }

   public static Loggable logAddFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045042", 16, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRemoveFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045043", 16, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045043";
   }

   public static Loggable logRemoveFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045043", 16, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnknownSubDeployment(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045045", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045045";
   }

   public static Loggable logUnknownSubDeploymentLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045045", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidDeploymentTarget(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045047", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045047";
   }

   public static Loggable logInvalidDeploymentTargetLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045047", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoTemporaryTemplate(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045048", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045048";
   }

   public static Loggable logNoTemporaryTemplateLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045048", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTemporaryTemplateNotConfigured(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045049", 16, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045049";
   }

   public static Loggable logTemporaryTemplateNotConfiguredLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045049", 16, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCreateDestinationIdentifierNameConflict(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("045050", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045050";
   }

   public static Loggable logCreateDestinationIdentifierNameConflictLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("045050", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNameConflict(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045051", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045051";
   }

   public static Loggable logNameConflictLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045051", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTargetChange(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045052", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045052";
   }

   public static Loggable logInvalidTargetChangeLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045052", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotDynamicallyAddDDMember(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045054", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045054";
   }

   public static Loggable logCannotDynamicallyAddDDMemberLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045054", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotDynamicallyRemoveDDMember(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045055", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045055";
   }

   public static Loggable logCannotDynamicallyRemoveDDMemberLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045055", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnknownJMSModuleType(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045060", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045060";
   }

   public static Loggable logUnknownJMSModuleTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045060", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBadDurableSubscription(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("045061", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045061";
   }

   public static Loggable logBadDurableSubscriptionLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("045061", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMoreThanOneInteropModule() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("045062", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045062";
   }

   public static Loggable logMoreThanOneInteropModuleLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("045062", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidInteropModule(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045063", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045063";
   }

   public static Loggable logInvalidInteropModuleLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045063", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidModuleTarget(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045064", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045064";
   }

   public static Loggable logInvalidModuleTargetLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045064", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidSubTargeting(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("045065", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045065";
   }

   public static Loggable logInvalidSubTargetingLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("045065", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidSubDeploymentTarget(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("045066", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045066";
   }

   public static Loggable logInvalidSubDeploymentTargetLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("045066", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUseOfInteropField(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("045067", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045067";
   }

   public static Loggable logUseOfInteropFieldLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("045067", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPhysicalDestinationNotPresent(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("045068", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045068";
   }

   public static Loggable logPhysicalDestinationNotPresentLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("045068", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEntityNotFoundInJMSSystemResource(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045069", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045069";
   }

   public static Loggable logEntityNotFoundInJMSSystemResourceLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045069", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEntityNotFoundInDomain(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045070", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045070";
   }

   public static Loggable logEntityNotFoundInDomainLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045070", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotCreateEntityInJMSSystemResource(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045071", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045071";
   }

   public static Loggable logCannotCreateEntityInJMSSystemResourceLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045071", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotCreateEntityInDomain(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045072", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045072";
   }

   public static Loggable logCannotCreateEntityInDomainLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045072", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotDeleteEntityFromJMSSystemResource(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045073", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045073";
   }

   public static Loggable logCannotDeleteEntityFromJMSSystemResourceLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045073", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotDeleteEntityFromDomain(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045074", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045074";
   }

   public static Loggable logCannotDeleteEntityFromDomainLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045074", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotFindAndModifyEntityFromJMSSystemResource(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045075", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045075";
   }

   public static Loggable logCannotFindAndModifyEntityFromJMSSystemResourceLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045075", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidModuleEntityModifier(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045076", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045076";
   }

   public static Loggable logInvalidModuleEntityModifierLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045076", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeliveryModeMismatch(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045077", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045077";
   }

   public static Loggable logDeliveryModeMismatchLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045077", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeliveryModeMismatch2(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045078", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045078";
   }

   public static Loggable logDeliveryModeMismatch2Loggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045078", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoPersistentMessages(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045079", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045079";
   }

   public static Loggable logNoPersistentMessagesLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045079", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFlowInterval(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045080", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045080";
   }

   public static Loggable logFlowIntervalLoggable(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045080", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBadSessionsMax(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045081", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045081";
   }

   public static Loggable logBadSessionsMaxLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045081", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBindNamingException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045082", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045082";
   }

   public static Loggable logBindNamingExceptionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045082", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLocalBindNamingException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045083", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045083";
   }

   public static Loggable logLocalBindNamingExceptionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045083", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppBindNamingException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045084", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045084";
   }

   public static Loggable logAppBindNamingExceptionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045084", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSServiceNotInitialized2() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("045085", 16, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045085";
   }

   public static Loggable logJMSServiceNotInitialized2Loggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("045085", 16, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSSystemResourceModuleCannotHaveInteropJmsName() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("045086", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045086";
   }

   public static Loggable logJMSSystemResourceModuleCannotHaveInteropJmsNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("045086", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSDeploymentModuleCannotHaveInteropJmsDescriptorName(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045087", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045087";
   }

   public static Loggable logJMSDeploymentModuleCannotHaveInteropJmsDescriptorNameLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045087", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidJMSSystemResourceModuleDescriptorFileName(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045088", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045088";
   }

   public static Loggable logInvalidJMSSystemResourceModuleDescriptorFileNameLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045088", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBadErrorDestination(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045089", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045089";
   }

   public static Loggable logBadErrorDestinationLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045089", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorHandlingNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045090", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045090";
   }

   public static Loggable logErrorHandlingNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045090", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalTargetType(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045091", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045091";
   }

   public static Loggable logIllegalTargetTypeLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045091", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalAgentType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045092", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045092";
   }

   public static Loggable logIllegalAgentTypeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045092", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInteropUDQ(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045093", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045093";
   }

   public static Loggable logInteropUDQLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045093", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInteropUDT(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045094", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045094";
   }

   public static Loggable logInteropUDTLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045094", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInteropSID(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045095", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045095";
   }

   public static Loggable logInteropSIDLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045095", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInteropSRC(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045096", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045096";
   }

   public static Loggable logInteropSRCLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045096", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInteropSEH(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045097", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045097";
   }

   public static Loggable logInteropSEHLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045097", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDuplicateResourceName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045098", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045098";
   }

   public static Loggable logDuplicateResourceNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045098", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoTemporaryTemplates() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("045099", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045099";
   }

   public static Loggable logNoTemporaryTemplatesLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("045099", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoDestinationName() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("045100", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045100";
   }

   public static Loggable logNoDestinationNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("045100", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidDestinationFormat(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045101", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045101";
   }

   public static Loggable logInvalidDestinationFormatLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045101", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDestinationNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045102", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045102";
   }

   public static Loggable logDestinationNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045102", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBackEndUnreachable() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("045103", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045103";
   }

   public static Loggable logBackEndUnreachableLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("045103", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBackEndUnknown() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("045104", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045104";
   }

   public static Loggable logBackEndUnknownLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("045104", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFindFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("045105", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045105";
   }

   public static Loggable logFindFailedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("045105", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidDestinationType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045106", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045106";
   }

   public static Loggable logInvalidDestinationTypeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045106", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorRemovingSubscription() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("045107", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045107";
   }

   public static Loggable logErrorRemovingSubscriptionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("045107", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoMethod(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045108", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045108";
   }

   public static Loggable logNoMethodLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045108", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidForeignServer(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("045109", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045109";
   }

   public static Loggable logInvalidForeignServerLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("045109", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConflictingTargetingInformation(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045110", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045110";
   }

   public static Loggable logConflictingTargetingInformationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045110", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDefaultTargetingNotSupported(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045111", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045111";
   }

   public static Loggable logDefaultTargetingNotSupportedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045111", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFlowLimits(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045112", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045112";
   }

   public static Loggable logFlowLimitsLoggable(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045112", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDDForwardRequestDenied(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045113", 64, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045113";
   }

   public static Loggable logDDForwardRequestDeniedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045113", 64, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidUnrestrictedUnsubscribe(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045114", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045114";
   }

   public static Loggable logInvalidUnrestrictedUnsubscribeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045114", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidSubDeploymentTargeting(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045115", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045115";
   }

   public static Loggable logInvalidSubDeploymentTargetingLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045115", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRDTNotSupportedOnClusteredJMSServer(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("045116", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045116";
   }

   public static Loggable logRDTNotSupportedOnClusteredJMSServerLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("045116", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUOONotSupportedOnClusteredJMSServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045119", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045119";
   }

   public static Loggable logUOONotSupportedOnClusteredJMSServerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045119", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logContextForJMSContextNotActive() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("045120", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045120";
   }

   public static Loggable logContextForJMSContextNotActiveLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("045120", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionCreatingInitialContextWhilstInjectingJMSContext(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045121", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045121";
   }

   public static Loggable logExceptionCreatingInitialContextWhilstInjectingJMSContextLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045121", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionLookingUpConnectionFactoryWhilstInjectingJMSContext(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045122", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045122";
   }

   public static Loggable logExceptionLookingUpConnectionFactoryWhilstInjectingJMSContextLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045122", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMethodForbiddenOnInjectedJMSContext(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045123", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045123";
   }

   public static Loggable logMethodForbiddenOnInjectedJMSContextLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045123", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSessionModeTransactedForbiddenOnInjectedJMSContext() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("045124", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045124";
   }

   public static Loggable logSessionModeTransactedForbiddenOnInjectedJMSContextLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("045124", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSContextInternalMethodUnavailable() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("045125", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045125";
   }

   public static Loggable logJMSContextInternalMethodUnavailableLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("045125", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWLJMSContextMethodUnavailable() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("045126", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045126";
   }

   public static Loggable logWLJMSContextMethodUnavailableLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("045126", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJNDINameAlreadyBound(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045127", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045127";
   }

   public static Loggable logJNDINameAlreadyBoundLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045127", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDuplicateJMSResourceDefinitions(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045128", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045128";
   }

   public static Loggable logDuplicateJMSResourceDefinitionsLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045128", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidJNDINameSpaceInAppXMLAndLibraries(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045129", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045129";
   }

   public static Loggable logInvalidJNDINameSpaceInAppXMLAndLibrariesLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045129", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidJNDINameSpaceForJMSResourceDefinition(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045130", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045130";
   }

   public static Loggable logInvalidJNDINameSpaceForJMSResourceDefinitionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045130", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidValueForJMSResourceDefinitionProperty(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045131", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045131";
   }

   public static Loggable logInvalidValueForJMSResourceDefinitionPropertyLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045131", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTypeForProperty(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("045132", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045132";
   }

   public static Loggable logInvalidTypeForPropertyLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("045132", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUDDNotSupportedOnClusteredJMSServerWithoutDistributedPolicy(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("045133", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045133";
   }

   public static Loggable logUDDNotSupportedOnClusteredJMSServerWithoutDistributedPolicyLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("045133", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMultipleCandidateJMSServers(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("045134", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045134";
   }

   public static Loggable logMultipleCandidateJMSServersLoggable(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("045134", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMultipleCandidateSAFAgents(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("045135", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045135";
   }

   public static Loggable logMultipleCandidateSAFAgentsLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("045135", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidJMSServerForJMSResourceDefinition(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("045136", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045136";
   }

   public static Loggable logInvalidJMSServerForJMSResourceDefinitionLoggable(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("045136", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDistributedDestNotSupported(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045137", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045137";
   }

   public static Loggable logDistributedDestNotSupportedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045137", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRDTopicNotSupported(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045138", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045138";
   }

   public static Loggable logRDTopicNotSupportedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045138", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidStoreForJMSResourceDefinition(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("045139", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045139";
   }

   public static Loggable logInvalidStoreForJMSResourceDefinitionLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("045139", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logClassCastExceptionLookingUpConnectionFactoryWhilstInjectingJMSContext(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045140", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045140";
   }

   public static Loggable logClassCastExceptionLookingUpConnectionFactoryWhilstInjectingJMSContextLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045140", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNamingExceptionLookingUpConnectionFactoryWhilstInjectingJMSContext(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045141", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045141";
   }

   public static Loggable logNamingExceptionLookingUpConnectionFactoryWhilstInjectingJMSContextLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045141", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUseOfInteropModule() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("045142", 16, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045142";
   }

   public static Loggable logUseOfInteropModuleLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("045142", 16, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTargetSingleTonPolicy(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045143", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045143";
   }

   public static Loggable logInvalidTargetSingleTonPolicyLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045143", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTargetClusterPolicy(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045144", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045144";
   }

   public static Loggable logInvalidTargetClusterPolicyLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045144", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionNotSupported(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045145", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045145";
   }

   public static Loggable logPartitionNotSupportedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045145", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionedDestinationNotSupported(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045146", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045146";
   }

   public static Loggable logPartitionedDestinationNotSupportedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045146", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionedConnectionFactoryNotSupported(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045147", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045147";
   }

   public static Loggable logPartitionedConnectionFactoryNotSupportedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045147", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSResDefnMultipleCandidateJMSServers(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045148", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045148";
   }

   public static Loggable logJMSResDefnMultipleCandidateJMSServersLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045148", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidResourceDefinitionInAppClientModule(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("045149", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045149";
   }

   public static Loggable logInvalidResourceDefinitionInAppClientModuleLoggable(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("045149", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidSAFImportedDestinationsTargetInDomain(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("045150", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045150";
   }

   public static Loggable logInvalidSAFImportedDestinationsTargetInDomainLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("045150", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidSAFImportedDestinationsTargetInRG(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("045151", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045151";
   }

   public static Loggable logInvalidSAFImportedDestinationsTargetInRGLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("045151", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTargetingForeignServer(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("045152", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045152";
   }

   public static Loggable logInvalidTargetingForeignServerLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("045152", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidHierarchyForJMSServer(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("045153", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045153";
   }

   public static Loggable logInvalidHierarchyForJMSServerLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("045153", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTargetScope(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045154", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045154";
   }

   public static Loggable logInvalidTargetScopeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045154", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logScopeConflict() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("045155", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045155";
   }

   public static Loggable logScopeConflictLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("045155", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionAbsentInContext() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("045156", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045156";
   }

   public static Loggable logPartitionAbsentInContextLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("045156", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logResourceAbsent(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045157", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045157";
   }

   public static Loggable logResourceAbsentLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045157", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotFind(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045158", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045158";
   }

   public static Loggable logCouldNotFindLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045158", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logResourceGroupAbsent(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("045159", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045159";
   }

   public static Loggable logResourceGroupAbsentLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("045159", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTargetName(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045160", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045160";
   }

   public static Loggable logInvalidTargetNameLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045160", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidParameter(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045161", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045161";
   }

   public static Loggable logInvalidParameterLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045161", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidDeploymentInRGT(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045162", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045162";
   }

   public static Loggable logInvalidDeploymentInRGTLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045162", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUddTargetOverLap(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045163", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045163";
   }

   public static Loggable logUddTargetOverLapLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045163", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionAbsent(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045164", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045164";
   }

   public static Loggable logPartitionAbsentLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045164", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logOBSCFNotSupport(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045165", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045165";
   }

   public static Loggable logOBSCFNotSupportLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045165", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidForeignServerInitialContextFactory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045166", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045166";
   }

   public static Loggable logInvalidForeignServerInitialContextFactoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045166", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logObjectAlreadyBound(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("045167", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045167";
   }

   public static Loggable logObjectAlreadyBoundLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("045167", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getPushExceptionOnConnectionClose(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045168", 64, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045168";
   }

   public static Loggable getPushExceptionOnConnectionCloseLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045168", 64, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidScope(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("045169", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045169";
   }

   public static Loggable logInvalidScopeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("045169", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalSAFErrorHandling(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("045170", 8, args, JMSExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "045170";
   }

   public static Loggable logIllegalSAFErrorHandlingLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("045170", 8, args, "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, JMSExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.jms.JMSExceptionLogLocalizer", JMSExceptionLogger.class.getClassLoader());
      private MessageLogger messageLogger = JMSExceptionLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = JMSExceptionLogger.findMessageLogger();
      }
   }
}
