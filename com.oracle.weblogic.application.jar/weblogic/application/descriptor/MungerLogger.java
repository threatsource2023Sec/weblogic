package weblogic.application.descriptor;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class MungerLogger {
   private static final String LOCALIZER_CLASS = "weblogic.application.descriptor.MungerLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(MungerLogger.class.getName());
   }

   public static String logUnableToValidateDescriptor(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2156200", 8, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156200";
   }

   public static String logDescriptorParseError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2156201", 8, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156201";
   }

   public static String logValidPlanMerged(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156202", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156202";
   }

   public static String logMissingVersionAttribute(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156203", 16, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156203";
   }

   public static String logMissingRootElement(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156204", 8, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156204";
   }

   public static Loggable logMissingRootElementLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2156204", 8, args, "weblogic.application.descriptor.MungerLogLocalizer", MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MungerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPlanUpdateStarting(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156205", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156205";
   }

   public static String logNoMatchingPlanUpdates(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156206", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156206";
   }

   public static String logMatchingVariableAssignment(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156207", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156207";
   }

   public static String logUpdatedDescriptor(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156208", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156208";
   }

   public static String logApplyOverridesStarting(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156209", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156209";
   }

   public static String logApplyOverride(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2156210", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156210";
   }

   public static String logDescriptorUpdatedByPlan(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156211", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156211";
   }

   public static String logXPathParseError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156212", 8, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156212";
   }

   public static Loggable logXPathParseErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2156212", 8, args, "weblogic.application.descriptor.MungerLogLocalizer", MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MungerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logXPathParseException(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156213", 8, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156213";
   }

   public static Loggable logXPathParseExceptionLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2156213", 8, args, "weblogic.application.descriptor.MungerLogLocalizer", MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MungerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logXPathInvalidNoKeyNames(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156214", 8, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156214";
   }

   public static Loggable logXPathInvalidNoKeyNamesLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2156214", 8, args, "weblogic.application.descriptor.MungerLogLocalizer", MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MungerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppliedRemoveOverride(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156215", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156215";
   }

   public static String logNoMethod(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2156216", 8, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156216";
   }

   public static Loggable logNoMethodLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2156216", 8, args, "weblogic.application.descriptor.MungerLogLocalizer", MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MungerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPlanOperationFailed(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2156217", 8, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156217";
   }

   public static Loggable logPlanOperationFailedLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2156217", 8, args, "weblogic.application.descriptor.MungerLogLocalizer", MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MungerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppliedUpdateOverride(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2156218", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156218";
   }

   public static String logAppliedCreateOverride(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156219", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156219";
   }

   public static String logAppliedCreateNameOverride(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2156220", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156220";
   }

   public static String logNoMatchingArrayIdx(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2156221", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156221";
   }

   public static String logXPathInvalidNoIdx(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156222", 8, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156222";
   }

   public static Loggable logXPathInvalidNoIdxLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2156222", 8, args, "weblogic.application.descriptor.MungerLogLocalizer", MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, MungerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logXPathInvalidAddNoProperty(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156223", 16, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156223";
   }

   public static String logAppliedAddOverride(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2156224", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156224";
   }

   public static String logXPathInvalidTrailingSlash(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2156225", 16, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156225";
   }

   public static String logSkippingOverride(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156226", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156226";
   }

   public static String logPlanNotSupportedForDTD(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156227", 16, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156227";
   }

   public static String logResourcePlanUpdateStarting(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2156228", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156228";
   }

   public static String logValidResourcePlanMerged(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2156229", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156229";
   }

   public static String logNoMatchingResourcePlanUpdates(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2156230", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156230";
   }

   public static String logApplyResourceOverridesStarting(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2156231", 64, args, MungerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      MungerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156231";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.application.descriptor.MungerLogLocalizer", MungerLogger.class.getClassLoader());
      private MessageLogger messageLogger = MungerLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = MungerLogger.findMessageLogger();
      }
   }
}
