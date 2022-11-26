package weblogic.management.internal;

import java.net.URL;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class ConfigLogger {
   private static final String LOCALIZER_CLASS = "weblogic.management.internal.ConfigLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(ConfigLogger.class.getName());
   }

   public static String logErrorConnectingAdminServerForHome(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("150000", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150000";
   }

   public static Loggable logErrorConnectingAdminServerForHomeLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("150000", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorConnectionAdminServerForBootstrap(URL arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("150001", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150001";
   }

   public static Loggable logErrorConnectionAdminServerForBootstrapLoggable(URL arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("150001", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMSINotEnabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("150014", 1, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150014";
   }

   public static Loggable logMSINotEnabledLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("150014", 1, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStartingIndependentManagerServer() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("150018", 64, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150018";
   }

   public static String logErrorConnectingToAdminServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("150020", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150020";
   }

   public static Loggable logErrorConnectingToAdminServerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("150020", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAuthenticationFailedWhileStartingManagedServer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("150021", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150021";
   }

   public static Loggable logAuthenticationFailedWhileStartingManagedServerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("150021", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServerNameSameAsAdmin(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("150024", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150024";
   }

   public static Loggable logServerNameSameAsAdminLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("150024", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidReleaseLevel(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("150026", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150026";
   }

   public static Loggable logInvalidReleaseLevelLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("150026", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServerNameNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("150027", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150027";
   }

   public static Loggable logServerNameNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("150027", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBootStrapException(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("150028", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150028";
   }

   public static Loggable logBootStrapExceptionLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("150028", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAckAdminServerIsRunning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("150030", 128, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150030";
   }

   public static String logManagedServerConfigWritten(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("150031", 128, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150031";
   }

   public static String logUnknownReleaseLevel() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("150032", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150032";
   }

   public static Loggable logUnknownReleaseLevelLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("150032", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBootstrapMissingCredentials(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("150034", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150034";
   }

   public static String logBootstrapInvalidCredentials(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("150035", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150035";
   }

   public static String logBootstrapUnauthorizedUser(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("150036", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150036";
   }

   public static String logConfigXMLFoundInParentDir(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("150038", 16, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150038";
   }

   public static String logCouldNotDetermineServerName() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("150039", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150039";
   }

   public static Loggable logCouldNotDetermineServerNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("150039", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServerNameDoesNotExist(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("150041", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150041";
   }

   public static Loggable logServerNameDoesNotExistLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("150041", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAdminRequiredButNotSpecified(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("150042", 4, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150042";
   }

   public static String logCannotDeleteServerWhenRunning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("150043", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150043";
   }

   public static Loggable logCannotDeleteServerWhenRunningLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("150043", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInjectionFailure(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("150044", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150044";
   }

   public static Loggable logInjectionFailureLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("150044", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidEditSessionName(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("150045", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getEditSessionNameExist(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("150046", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCanNOtDestroyDefaultEditSession() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("150048", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logNoTemporaryTemplate(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("150049", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150049";
   }

   public static Loggable logNoTemporaryTemplateLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("150049", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCanNotDestroyDomainPartitionES() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("150050", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCanNotRemoveEditSessionOfDifferentPartition() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("150051", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCanNotRemoveLockedEditSession(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("150052", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCanNotRemoveLockedEditSessionAdmin(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("150053", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCanNotRemoveEditSessionWithChanges(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("150054", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCannotDeleteEditSessionOfAnotherUser(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("150055", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String getCannotDeleteEditSessionOfAnotherUserAdmin(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("150056", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logServiceValidationPermissionFailure(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("150057", 8, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150057";
   }

   public static Loggable logServiceValidationPermissionFailureLoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("150057", 8, args, "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ConfigLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidPersistentStoreRestartInPlaceConfiguration(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("150058", 16, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150058";
   }

   public static String logInvalidReplicatedStoreRestartInPlaceConfiguration(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("150059", 16, args, ConfigLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ConfigLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "150059";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.internal.ConfigLogLocalizer", ConfigLogger.class.getClassLoader());
      private MessageLogger messageLogger = ConfigLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = ConfigLogger.findMessageLogger();
      }
   }
}
