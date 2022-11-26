package weblogic.ldap;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class EmbeddedLDAPLogger {
   private static final String LOCALIZER_CLASS = "weblogic.ldap.EmbeddedLDAPLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(EmbeddedLDAPLogger.class.getName());
   }

   public static String logConfigFileNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("171500", 8, args, EmbeddedLDAPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "171500";
   }

   public static Loggable logConfigFileNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("171500", 8, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotGetAdminListenAddress() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("171503", 8, args, EmbeddedLDAPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "171503";
   }

   public static Loggable logCouldNotGetAdminListenAddressLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("171503", 8, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorWritingReplicasFile(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("171507", 8, args, EmbeddedLDAPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "171507";
   }

   public static Loggable logErrorWritingReplicasFileLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("171507", 8, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotDeleteOnCleanup(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("171512", 4, args, EmbeddedLDAPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "171512";
   }

   public static Loggable logCouldNotDeleteOnCleanupLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("171512", 4, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotScheduleTrigger(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("171513", 4, args, EmbeddedLDAPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "171513";
   }

   public static Loggable logCouldNotScheduleTriggerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("171513", 4, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorGettingExclusiveAccess(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("171517", 4, args, EmbeddedLDAPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "171517";
   }

   public static Loggable logErrorGettingExclusiveAccessLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("171517", 4, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStackTrace(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("171518", 64, args, EmbeddedLDAPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "171518";
   }

   public static Loggable logStackTraceLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("171518", 64, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEmbeddedLDAPServerAlreadyRunning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("171519", 8, args, EmbeddedLDAPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "171519";
   }

   public static Loggable logEmbeddedLDAPServerAlreadyRunningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("171519", 8, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEmbeddedLDAPServerRunningRetry(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("171520", 16, args, EmbeddedLDAPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "171520";
   }

   public static Loggable logEmbeddedLDAPServerRunningRetryLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("171520", 16, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInitializingLDAPReplica(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("171521", 4, args, EmbeddedLDAPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "171521";
   }

   public static Loggable logErrorInitializingLDAPReplicaLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("171521", 4, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInitializingLDAPMaster(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("171522", 4, args, EmbeddedLDAPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "171522";
   }

   public static Loggable logErrorInitializingLDAPMasterLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("171522", 4, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCredUnavailable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("171523", 8, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCredUnavailableLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("171523", 8, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidAdminListenAddress(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("171524", 8, args, EmbeddedLDAPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "171524";
   }

   public static Loggable logInvalidAdminListenAddressLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("171524", 8, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getErrLoadInitReplicaFile() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("171525", 8, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getErrLoadInitReplicaFileLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("171525", 8, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReloadInitReplicaFile() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("171526", 32, args, EmbeddedLDAPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "171526";
   }

   public static Loggable logReloadInitReplicaFileLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("171526", 32, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSuccessReloadInitReplicaFile() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("171527", 32, args, EmbeddedLDAPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "171527";
   }

   public static Loggable logSuccessReloadInitReplicaFileLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("171527", 32, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotMarkReplicaInvalid(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("171528", 16, args, EmbeddedLDAPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "171528";
   }

   public static Loggable logCannotMarkReplicaInvalidLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("171528", 16, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToDownloadReplica(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("171529", 16, args, EmbeddedLDAPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "171529";
   }

   public static Loggable logUnableToDownloadReplicaLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("171529", 16, args, "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, EmbeddedLDAPLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.ldap.EmbeddedLDAPLogLocalizer", EmbeddedLDAPLogger.class.getClassLoader());
      private MessageLogger messageLogger = EmbeddedLDAPLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = EmbeddedLDAPLogger.findMessageLogger();
      }
   }
}
