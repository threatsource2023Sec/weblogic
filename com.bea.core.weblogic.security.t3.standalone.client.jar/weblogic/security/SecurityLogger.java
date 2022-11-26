package weblogic.security;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class SecurityLogger {
   private static final String LOCALIZER_CLASS = "weblogic.security.SecurityLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(SecurityLogger.class.getName());
   }

   public static String logUnsupportedCircularGroup(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090000", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090000";
   }

   public static Loggable logUnsupportedCircularGroupLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090000", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSendingLoginFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090001", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090001";
   }

   public static Loggable logSendingLoginFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090001", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBroadcastUnlockUserFailure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090002", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090002";
   }

   public static Loggable logBroadcastUnlockUserFailureLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090002", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logClosingEnumerationWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090004", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090004";
   }

   public static Loggable logClosingEnumerationWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090004", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonexistentPrincipalWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090010", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090010";
   }

   public static Loggable logNonexistentPrincipalWarningLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090010", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLockoutExpiredInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090020", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090020";
   }

   public static Loggable logLockoutExpiredInfoLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090020", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExplicitUserUnlockInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090022", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090022";
   }

   public static Loggable logExplicitUserUnlockInfoLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090022", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotListeningForSSLInfo(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090034", 1, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090034";
   }

   public static Loggable logNotListeningForSSLInfoLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090034", 1, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCheckUserPermissionInfo(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090038", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090038";
   }

   public static Loggable logCheckUserPermissionInfoLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090038", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAccessFailedInfo(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090039", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090039";
   }

   public static Loggable logAccessFailedInfoLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090039", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMaxUserWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090040", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090040";
   }

   public static Loggable logMaxUserWarningLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090040", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMaxGroupWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090041", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090041";
   }

   public static Loggable logMaxGroupWarningLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090041", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMaxAclWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090042", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090042";
   }

   public static Loggable logMaxAclWarningLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090042", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMissingGroupWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090043", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090043";
   }

   public static Loggable logMissingGroupWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090043", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonexistentPrincipalGroupWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090044", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090044";
   }

   public static Loggable logNonexistentPrincipalGroupWarningLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090044", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonexistentPermissionWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090045", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090045";
   }

   public static Loggable logNonexistentPermissionWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090045", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonexistentAclWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090046", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090046";
   }

   public static Loggable logNonexistentAclWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090046", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonexistentPrincipalAclWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090047", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090047";
   }

   public static Loggable logNonexistentPrincipalAclWarningLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090047", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInMemoryFileRealmChangeWarning() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090048", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090048";
   }

   public static Loggable logInMemoryFileRealmChangeWarningLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090048", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonexistentSystemUserWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090049", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090049";
   }

   public static Loggable logNonexistentSystemUserWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090049", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCreatingSecurityRuntime(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090051", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090051";
   }

   public static Loggable logErrorCreatingSecurityRuntimeLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090051", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorBadPasswordRegistered(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090052", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090052";
   }

   public static Loggable logErrorBadPasswordRegisteredLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090052", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLDAPRealmV1DeprecatedWarning() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090055", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090055";
   }

   public static Loggable logLDAPRealmV1DeprecatedWarningLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090055", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLockingUser(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090056", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090056";
   }

   public static Loggable logLockingUserLoggable(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090056", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAccessDecisionError(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090060", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090060";
   }

   public static Loggable logAccessDecisionErrorLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090060", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeployableRoleProviderError(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090063", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090063";
   }

   public static Loggable logDeployableRoleProviderErrorLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090063", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeployableAuthorizationProviderError(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090064", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090064";
   }

   public static Loggable logDeployableAuthorizationProviderErrorLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090064", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logGettingBootIdentityFromUser() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090065", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090065";
   }

   public static Loggable logGettingBootIdentityFromUserLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090065", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBootPropertiesWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090066", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090066";
   }

   public static Loggable logBootPropertiesWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090066", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRealmLockoutExpiredInfo(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090067", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090067";
   }

   public static Loggable logRealmLockoutExpiredInfoLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090067", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRealmBroadcastUnlockUserFailure(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090070", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090070";
   }

   public static Loggable logRealmBroadcastUnlockUserFailureLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090070", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBootFilterCritical(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090072", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090072";
   }

   public static Loggable logBootFilterCriticalLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090072", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUpdateFilterWarn(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090073", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090073";
   }

   public static Loggable logUpdateFilterWarnLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090073", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInitializingLDIFForProvider(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090074", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090074";
   }

   public static Loggable logInitializingLDIFForProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090074", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLoadedLDIFForProvider(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090075", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090075";
   }

   public static Loggable logLoadedLDIFForProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090075", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailureLoadingLDIFForProvider(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090076", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090076";
   }

   public static Loggable logFailureLoadingLDIFForProviderLoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090076", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailureCreatingProviderInitFile(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090077", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090077";
   }

   public static Loggable logFailureCreatingProviderInitFileLoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090077", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRealmLockingUser(String arg0, String arg1, long arg2, long arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("090078", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090078";
   }

   public static Loggable logRealmLockingUserLoggable(String arg0, String arg1, long arg2, long arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("090078", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRealmSendingLoginFailure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090079", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090079";
   }

   public static Loggable logRealmSendingLoginFailureLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090079", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFoundPrivateKeyInSSLConfig(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090080", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090080";
   }

   public static Loggable logFoundPrivateKeyInSSLConfigLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090080", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInitializingUsingRealm(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090082", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090082";
   }

   public static Loggable logInitializingUsingRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090082", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStoringBootIdentity(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090083", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090083";
   }

   public static Loggable logStoringBootIdentityLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090083", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotInitOnAnyPortInfo() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090087", 1, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090087";
   }

   public static Loggable logNotInitOnAnyPortInfoLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090087", 1, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSSLDidNotFindPrivateKeyAlias(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090088", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090088";
   }

   public static Loggable logSSLDidNotFindPrivateKeyAliasLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090088", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSSLDidNotFindPrivateKeyPassPhrase(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090089", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090089";
   }

   public static Loggable logSSLDidNotFindPrivateKeyPassPhraseLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090089", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSSLCouldNotGetSecurityService() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090091", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090091";
   }

   public static Loggable logSSLCouldNotGetSecurityServiceLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090091", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoKeyStoreConfiguration(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090093", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090093";
   }

   public static Loggable logNoKeyStoreConfigurationLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090093", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSSLCannotInstantiateKeyStoreProvider(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090095", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090095";
   }

   public static Loggable logSSLCannotInstantiateKeyStoreProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090095", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotFindKeyManager(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090097", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090097";
   }

   public static Loggable logCannotFindKeyManagerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090097", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logKeyStoreException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090101", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090101";
   }

   public static Loggable logKeyStoreExceptionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090101", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnexpectedExceptionPrivateKeyStore(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090108", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090108";
   }

   public static Loggable logUnexpectedExceptionPrivateKeyStoreLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090108", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSSLKeyFileNameError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090109", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090109";
   }

   public static Loggable logSSLKeyFileNameErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090109", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSSLCertificateFileNameError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090110", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090110";
   }

   public static Loggable logSSLCertificateFileNameErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090110", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logClientCertEnforcedNoTrustedCA() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090112", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090112";
   }

   public static Loggable logClientCertEnforcedNoTrustedCALoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090112", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTrustedCAsLoadedFromKeyStore(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090113", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090113";
   }

   public static Loggable logTrustedCAsLoadedFromKeyStoreLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090113", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUsingPrivateKeyFromKeyStore(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090116", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090116";
   }

   public static Loggable logUsingPrivateKeyFromKeyStoreLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090116", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLDIFEmptyForProvider(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090118", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090118";
   }

   public static Loggable logLDIFEmptyForProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090118", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLDIFNotFoundForProvider(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090119", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090119";
   }

   public static Loggable logLDIFNotFoundForProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090119", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTrustedCAFileNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090120", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090120";
   }

   public static Loggable logTrustedCAFileNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090120", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTrustedCAsLoadedFromTrustedCAFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090121", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090121";
   }

   public static Loggable logTrustedCAsLoadedFromTrustedCAFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090121", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTrustedCAsLoadedFromDefaultKeyStore(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090122", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090122";
   }

   public static Loggable logTrustedCAsLoadedFromDefaultKeyStoreLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090122", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotAccessTrustedCAFile(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090123", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090123";
   }

   public static Loggable logCannotAccessTrustedCAFileLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090123", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTrustedCAFileFormat(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090124", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090124";
   }

   public static Loggable logInvalidTrustedCAFileFormatLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090124", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTrustedCAsLoadedFromCmdLnKeyStore(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090125", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090125";
   }

   public static Loggable logTrustedCAsLoadedFromCmdLnKeyStoreLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090125", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMisconfiguredWLSProviderUpdateFile(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090126", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090126";
   }

   public static Loggable logMisconfiguredWLSProviderUpdateFileLoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090126", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnreadableWLSProviderUpdateFile(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090127", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090127";
   }

   public static Loggable logUnreadableWLSProviderUpdateFileLoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090127", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUpdatingLDIFForProvider(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090129", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090129";
   }

   public static Loggable logUpdatingLDIFForProviderLoggable(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090129", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailureUpdatingLDIFVersion(String arg0, String arg1, int arg2, Exception arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("090130", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090130";
   }

   public static Loggable logFailureUpdatingLDIFVersionLoggable(String arg0, String arg1, int arg2, Exception arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("090130", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLoadKeyStoreKeyStoreException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090131", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090131";
   }

   public static Loggable logLoadKeyStoreKeyStoreExceptionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090131", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLoadKeyStoreFileNotFoundException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090132", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090132";
   }

   public static Loggable logLoadKeyStoreFileNotFoundExceptionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090132", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLoadKeyStoreIOException(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090133", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090133";
   }

   public static Loggable logLoadKeyStoreIOExceptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090133", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLoadKeyStoreCertificateException(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090134", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090134";
   }

   public static Loggable logLoadKeyStoreCertificateExceptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090134", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLoadKeyStoreNoSuchAlgorithmException(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090135", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090135";
   }

   public static Loggable logLoadKeyStoreNoSuchAlgorithmExceptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090135", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStoreKeyStoreFileNotFoundException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090136", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090136";
   }

   public static Loggable logStoreKeyStoreFileNotFoundExceptionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090136", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStoreKeyStoreIOException(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090137", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090137";
   }

   public static Loggable logStoreKeyStoreIOExceptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090137", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStoreKeyStoreCertificateException(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090138", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090138";
   }

   public static Loggable logStoreKeyStoreCertificateExceptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090138", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStoreKeyStoreNoSuchAlgorithmException(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090139", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090139";
   }

   public static Loggable logStoreKeyStoreNoSuchAlgorithmExceptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090139", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStoreKeyStoreKeyStoreException(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090140", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090140";
   }

   public static Loggable logStoreKeyStoreKeyStoreExceptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090140", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDefaultKeyStorePrivateKeyKeyStoreNotConfigured(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090141", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090141";
   }

   public static Loggable logDefaultKeyStorePrivateKeyKeyStoreNotConfiguredLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090141", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDefaultKeyStoreTrustedCAKeyStoreNotConfigured(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090142", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090142";
   }

   public static Loggable logDefaultKeyStoreTrustedCAKeyStoreNotConfiguredLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090142", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDefaultKeyStorePrivateKeyKeyStoreDoesntExist(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090143", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090143";
   }

   public static Loggable logDefaultKeyStorePrivateKeyKeyStoreDoesntExistLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090143", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDefaultKeyStoreTrustedCAKeyStoreDoesntExist(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090144", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090144";
   }

   public static Loggable logDefaultKeyStoreTrustedCAKeyStoreDoesntExistLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090144", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDefaultKeyStorePrivateKeyKeyStoreError(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("090145", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090145";
   }

   public static Loggable logDefaultKeyStorePrivateKeyKeyStoreErrorLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("090145", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDefaultKeyStoreTrustedCAKeyStoreError(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("090146", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090146";
   }

   public static Loggable logDefaultKeyStoreTrustedCAKeyStoreErrorLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("090146", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDemoTrustCertificateUsed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090152", 2, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090152";
   }

   public static Loggable logDemoTrustCertificateUsedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090152", 2, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDemoIdentityCertificateUsed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090153", 2, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090153";
   }

   public static Loggable logDemoIdentityCertificateUsedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090153", 2, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIdentityCertificateExpired(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090154", 2, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090154";
   }

   public static Loggable logIdentityCertificateExpiredLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090154", 2, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIdentityCertificateNotYetValid(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090155", 2, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090155";
   }

   public static Loggable logIdentityCertificateNotYetValidLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090155", 2, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIdentityCertificateNotValid(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090156", 2, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090156";
   }

   public static Loggable logIdentityCertificateNotValidLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090156", 2, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToVerifyIdentityCertificate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090157", 2, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090157";
   }

   public static Loggable logUnableToVerifyIdentityCertificateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090157", 2, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPrivateKeyStoreNotFound(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("090158", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090158";
   }

   public static Loggable logPrivateKeyStoreNotFoundLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("090158", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTrustedCAKeyStoreNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090160", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090160";
   }

   public static Loggable logTrustedCAKeyStoreNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090160", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailureSavingLDIFForProvider(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090161", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090161";
   }

   public static Loggable logFailureSavingLDIFForProviderLoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090161", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSavedLDIFForProvider(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090162", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090162";
   }

   public static Loggable logSavedLDIFForProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090162", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDuplicateLDAPEntryForProvider(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090163", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090163";
   }

   public static Loggable logDuplicateLDAPEntryForProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090163", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTrustedCAFromKeyStoreLoadFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090164", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090164";
   }

   public static Loggable logTrustedCAFromKeyStoreLoadFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090164", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIdentityKeyStoreFileNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090165", 2, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090165";
   }

   public static Loggable logIdentityKeyStoreFileNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090165", 2, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIdentityKeyStoreLoadFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090166", 2, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090166";
   }

   public static Loggable logIdentityKeyStoreLoadFailedLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090166", 2, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIdentityKeyStoreAliasNotSpecified(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090167", 2, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090167";
   }

   public static Loggable logIdentityKeyStoreAliasNotSpecifiedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090167", 2, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIdentityEntryNotFoundUnderAlias(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090168", 2, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090168";
   }

   public static Loggable logIdentityEntryNotFoundUnderAliasLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090168", 2, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLoadTrustedCAsFromKeyStore(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090169", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090169";
   }

   public static Loggable logLoadTrustedCAsFromKeyStoreLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090169", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLoadIdentityCertificateFromKeyStore(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090171", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090171";
   }

   public static Loggable logLoadIdentityCertificateFromKeyStoreLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090171", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoTrustedCAsLoaded() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090172", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090172";
   }

   public static Loggable logNoTrustedCAsLoadedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090172", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServerTrustKeyStoreConfigError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090173", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090173";
   }

   public static Loggable logServerTrustKeyStoreConfigErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090173", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServerTrustKeyStoreMisMatchError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090174", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090174";
   }

   public static Loggable logServerTrustKeyStoreMisMatchErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090174", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnrecognizedCallback() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090175", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnrecognizedCallbackLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090175", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getIllegalNullSubject() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090176", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getIllegalNullSubjectLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090176", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidMessageDigestRequested() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090177", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidMessageDigestRequestedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090177", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullClass() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090178", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullClassLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090178", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullAction() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090179", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullActionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090179", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCouldNotConvertASToAU(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090180", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCouldNotConvertASToAULoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090180", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getAttemptingToModifySealedSubject() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090181", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getAttemptingToModifySealedSubjectLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090181", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNotAPrincipal(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090182", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNotAPrincipalLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090182", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNPEInAUHashCode(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090183", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNPEInAUHashCodeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090183", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidConstraintsNone() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090184", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidConstraintsNoneLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090184", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoCallbackHandlerSpecified() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090185", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoCallbackHandlerSpecifiedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090185", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getErrorCallbackNotAvailable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090186", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getErrorCallbackNotAvailableLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090186", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnableToDelete(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090187", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnableToDeleteLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090187", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCantUpdateReadonlyPermColl() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090188", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCantUpdateReadonlyPermCollLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090188", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getErrorCreatingFile(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090190", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getErrorCreatingFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090190", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getErrorWritingRealmContents(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090191", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getErrorWritingRealmContentsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090191", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCouldNotCreateTempFileNew(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090192", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCouldNotCreateTempFileNewLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090192", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCouldNotCreateTempFileOld(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090193", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCouldNotCreateTempFileOldLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090193", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCouldNotClearTempFile(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090194", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCouldNotClearTempFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090194", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCouldNotRenameTempFile(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090195", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCouldNotRenameTempFileLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090195", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCouldNotDeleteTempFile(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090196", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCouldNotDeleteTempFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090196", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCantFindPermission(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090197", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCantFindPermissionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090197", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoAppropriateConstructor(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090198", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoAppropriateConstructorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090198", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCantInstantiateClass(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090199", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCantInstantiateClassLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090199", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoPermissionToInstantiate(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090200", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoPermissionToInstantiateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090200", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getIncorrectArgForConstructor(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090201", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getIncorrectArgForConstructorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090201", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExcInConstructor(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090202", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExcInConstructorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090202", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getTrailingTextAfterGrant() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090203", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getTrailingTextAfterGrantLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090203", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnexpectedEndOfGrant() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090204", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnexpectedEndOfGrantLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090204", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExpectedConstantButFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090205", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExpectedConstantButFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090205", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExpectedQuoteButFound(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090206", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExpectedQuoteButFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090206", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getIniVersionMismatch(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090207", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getIniVersionMismatchLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090207", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getIniCorruptFile(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090208", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getIniCorruptFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090208", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getIniCouldNotClose(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090209", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getIniCouldNotCloseLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090209", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getIniErrorOpeningFile(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090210", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getIniErrorOpeningFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090210", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getDecodingError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090218", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getDecodingErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090218", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getErrorDecryptingKey(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090219", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getErrorDecryptingKeyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090219", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getRuleDenied(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090220", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getRuleDeniedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090220", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getConnFilterInternalErr() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090221", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getConnFilterInternalErrLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090221", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnknownProtocol(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090222", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnknownProtocolLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090222", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getBadNetMaskBits(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090223", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getBadNetMaskBitsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090223", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getBadNetMaskTokens(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090224", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getBadNetMaskTokensLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090224", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getBadNetMaskNum(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090225", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getBadNetMaskNumLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090225", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getBadNetMaskFormat(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090226", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getBadNetMaskFormatLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090226", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getBadAction(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090227", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getBadActionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090227", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullFilter() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090228", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullFilterLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090228", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getSetFilterMoreThanOnce() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090229", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getSetFilterMoreThanOnceLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090229", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getZeroLengthPemInputStream() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090230", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getZeroLengthPemInputStreamLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090230", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getSecurityAlreadyConfigured() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090231", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getSecurityAlreadyConfiguredLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090231", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getSaltNotSet() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090232", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getSaltNotSetLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090232", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getProblemWithConnFilterRules() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090233", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getProblemWithConnFilterRulesLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090233", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCouldNotCreateMapper() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090238", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCouldNotCreateMapperLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090238", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getIATypeCanNotBeNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090239", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getIATypeCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090239", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getIATokenCanNotBeNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090240", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getIATokenCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090240", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getIATypeNotConfigured(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090241", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getIATypeNotConfiguredLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090241", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getClientNotTrusted(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090242", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getClientNotTrustedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090242", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUserNameMapperNotConfig() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090243", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUserNameMapperNotConfigLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090243", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnknownIdentityAssertionType(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090244", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnknownIdentityAssertionTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090244", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoMappingForUsername() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090245", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoMappingForUsernameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090245", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getTokenNotTypeAU(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090246", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getTokenNotTypeAULoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090246", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnableToVerify(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090247", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnableToVerifyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090247", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnrecognizedIACallback() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090248", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnrecognizedIACallbackLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090248", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCursorIsNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090249", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCursorIsNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090249", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCursorNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090250", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCursorNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090250", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getHaveCurrentError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090251", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getHaveCurrentErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090251", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getAdvanceError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090252", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getAdvanceErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090252", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCloseError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090253", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCloseErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090253", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMaxCanNotBeLessThanZero() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090258", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getMaxCanNotBeLessThanZeroLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090258", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getGroupNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090259", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getGroupNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090259", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getWildcardCanNotBeNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090261", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getWildcardCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090261", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getErrorListingGroups(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090262", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getErrorListingGroupsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090262", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getParentGroupCanNotBeNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090263", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getParentGroupCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090263", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMemberCanNotBeNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090264", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getMemberCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090264", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMemberCanNotBeParent() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090265", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getMemberCanNotBeParentLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090265", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getGroupNameCanNotBeNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090266", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getGroupNameCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090266", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMemberNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090271", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getMemberNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090271", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getErrorListingMemberGroups(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090278", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getErrorListingMemberGroupsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090278", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getErrorListingUsers(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090279", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getErrorListingUsersLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090279", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUserNameCanNotBeNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090281", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUserNameCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090281", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUserAlreadyExists(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090283", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUserAlreadyExistsLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090283", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUserNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090287", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUserNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090287", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCouldNotGetConnection() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090294", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCouldNotGetConnectionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090294", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidURL(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090296", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidURLLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090296", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoCallbackHdlrSpecified() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090297", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoCallbackHdlrSpecifiedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090297", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoDelegateSpecified() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090298", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoDelegateSpecifiedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090298", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUsernameNotSupplied() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090299", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUsernameNotSuppliedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090299", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUserDoesNotExist(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090300", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUserDoesNotExistLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090300", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getPasswordNotSupplied() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090301", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getPasswordNotSuppliedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090301", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUserDenied(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090302", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUserDeniedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090302", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getAuthFailedNotFnd(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090305", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getAuthFailedNotFndLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090305", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getDefAuthImplConstrFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090307", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getDefAuthImplConstrFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090307", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullSubject() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090309", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullSubjectLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090309", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getFailedToCreateResource() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090310", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getFailedToCreateResourceLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090310", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getFailedToSetResource() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090311", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getFailedToSetResourceLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090311", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getPolicyRemovalError() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090313", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getPolicyRemovalErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090313", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnableToConnectLDAP(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090314", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnableToConnectLDAPLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090314", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getDefAuthImplInitFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090315", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getDefAuthImplInitFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090315", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullHelper() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090316", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullHelperLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090316", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getDefRoleMapperInitFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090318", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getDefRoleMapperInitFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090318", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnknownResourceType(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090319", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnknownResourceTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090319", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getFailedToCreateRole() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090320", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getFailedToCreateRoleLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090320", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getFailedToSetRoleExpr() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090321", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getFailedToSetRoleExprLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090321", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getRoleRemovalError() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090322", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getRoleRemovalErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090322", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMapCanNotBeModified() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090323", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getMapCanNotBeModifiedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090323", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoSuchResourceMapsCursor() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090330", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoSuchResourceMapsCursorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090330", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getValidRealmNameMustBeSpecifed() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090344", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getValidRealmNameMustBeSpecifedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090344", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getAuditorNotInitialized() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090347", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getAuditorNotInitializedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090347", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoAuthAndNoAdjMBeans() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090348", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoAuthAndNoAdjMBeansLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090348", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNeedAtLeastOneAuthMBean() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090349", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNeedAtLeastOneAuthMBeanLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090349", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getRoleMgrMustBeInitBeforeAuth() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090350", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getRoleMgrMustBeInitBeforeAuthLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090350", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCallingIsProtectedBeforeInit() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090357", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCallingIsProtectedBeforeInitLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090357", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getReqParamNotSuppliedIsAccess() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090358", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getReqParamNotSuppliedIsAccessLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090358", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getReqParamNotSuppliedIsProt() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090359", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getReqParamNotSuppliedIsProtLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090359", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvCredMgrConfigMBean() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090361", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvCredMgrConfigMBeanLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090361", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCredentialsTypeNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090364", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCredentialsTypeNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090364", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getDelegateLoginModuleError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090365", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getDelegateLoginModuleErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090365", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullDelegateLoginModule() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090366", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullDelegateLoginModuleLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090366", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoAuthMBeansInvConfig() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090370", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoAuthMBeansInvConfigLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090370", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoCallbackHandlerSuppliedPA() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090372", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoCallbackHandlerSuppliedPALoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090372", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullTokenTypeParam() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090375", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullTokenTypeParamLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090375", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullTokenParam() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090376", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullTokenParamLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090376", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoProviderMBeans() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090384", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoProviderMBeansLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090384", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getRoleMgrNotYetInitialized() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090389", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getRoleMgrNotYetInitializedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090389", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullUserIdentity() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090391", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullUserIdentityLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090391", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getSecServiceMgrNotYetInit() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090392", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getSecServiceMgrNotYetInitLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090392", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getSecServiceNotYetInit(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090393", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getSecServiceNotYetInitLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090393", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMustSpecifyRealm() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090394", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getMustSpecifyRealmLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090394", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMustSpecifySecServiceType() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090395", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getMustSpecifySecServiceTypeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090395", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getRealmDoesNotExist(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090396", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getRealmDoesNotExistLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090396", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getPrincipalSetDoesNotContainRAUser() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090397", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getPrincipalSetDoesNotContainRAUserLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090397", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidSubject(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090398", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidSubjectLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090398", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getSecurityServicesUnavailable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090399", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getSecurityServicesUnavailableLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090399", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBootIdentityNotValid() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090402", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090402";
   }

   public static Loggable logBootIdentityNotValidLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090402", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAuthDeniedForUser(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090403", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090403";
   }

   public static Loggable logAuthDeniedForUserLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090403", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUserNotPermittedToBoot(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090404", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090404";
   }

   public static Loggable logUserNotPermittedToBootLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090404", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCanOnlyInitSecServiceOnce() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090405", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCanOnlyInitSecServiceOnceLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090405", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvSecConfigNoDefaultRealm() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090407", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvSecConfigNoDefaultRealmLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090407", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getSecConfigUnavailable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090408", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getSecConfigUnavailableLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090408", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getSecCredUnavailable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090409", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getSecCredUnavailableLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090409", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmName(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090410", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090410", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getSecRealmInvConfig(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090411", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getSecRealmInvConfigLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090411", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getFailedToInitRealm(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090412", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getFailedToInitRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090412", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoRealmMBeanUnableToInit() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090413", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoRealmMBeanUnableToInitLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090413", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNeedToConfigureOneRoleMapper() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090414", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNeedToConfigureOneRoleMapperLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090414", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNeedToConfigureOneAtzMBean() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090415", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNeedToConfigureOneAtzMBeanLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090415", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCanNotConfigureKeyStoreProviders(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090416", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCanNotConfigureKeyStoreProvidersLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090416", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getSubjectIsNotTheKernelIdentity(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090419", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getSubjectIsNotTheKernelIdentityLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090419", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getSecProvErrorCreationExc(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090420", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getSecProvErrorCreationExcLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090420", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getSecProvErrorNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090421", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getSecProvErrorNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090421", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnableToFindServerCertFile(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090423", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnableToFindServerCertFileLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090423", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCanNotFindPrivateKeyWithAlias(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090424", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCanNotFindPrivateKeyWithAliasLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090424", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCanNotPrivateKeyFile(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090425", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCanNotPrivateKeyFileLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090425", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCanNotReadPrivateKeyFile(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090426", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCanNotReadPrivateKeyFileLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090426", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCanNotFindPrivateKeyFile(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090427", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCanNotFindPrivateKeyFileLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090427", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCanNotCallSetJava2SecurityMoreThanOnce() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090428", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCanNotCallSetJava2SecurityMoreThanOnceLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090428", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getReceivedANullUserName() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090431", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getReceivedANullUserNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090431", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInconsistentInvalidLoginRecord() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090433", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInconsistentInvalidLoginRecordLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090433", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getEnumeratorReturnedNullElement() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090434", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getEnumeratorReturnedNullElementLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090434", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getSubjectDoesNotHavePermissionToUnlock(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090435", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getSubjectDoesNotHavePermissionToUnlockLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090435", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInconsistentHashTableKeyExists() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090436", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInconsistentHashTableKeyExistsLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090436", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getSecurityServiceUnavailable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090437", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getSecurityServiceUnavailableLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090437", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidParameterAdminAccount() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090438", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidParameterAdminAccountLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090438", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidFileParameterAdminAccount(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090439", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidFileParameterAdminAccountLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090439", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getEncryptionError() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090440", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getEncryptionErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090440", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getLocationNullOrEmpty() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090442", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getLocationNullOrEmptyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090442", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullFile() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090443", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullFileLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090443", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullKeystore() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090444", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullKeystoreLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090444", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullOrEmptyPassPhrase() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090445", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullOrEmptyPassPhraseLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090445", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidBaseTemplate() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090446", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidBaseTemplateLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090446", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidFlagValue(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090447", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidFlagValueLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090447", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidFormat(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090448", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidFormatLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090448", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidConstraints() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090449", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidConstraintsLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090449", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidNameSupplied() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090450", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidNameSuppliedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090450", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnableToReadFile(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090451", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnableToReadFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090451", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getImportOnlyAvailableOnAdminServer() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090452", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getImportOnlyAvailableOnAdminServerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090452", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getImportFileError() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090453", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getImportFileErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090453", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoBaseDataToExport() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090454", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoBaseDataToExportLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090454", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExportFileError() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090455", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExportFileErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090455", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getThreeArgumentsRequired() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090456", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getThreeArgumentsRequiredLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090456", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getTypeMustValueIs(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090457", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getTypeMustValueIsLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090457", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getSignatureTypeCanNotBeNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090458", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getSignatureTypeCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090458", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getSignedByCanNotBeNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090459", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getSignedByCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090459", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getClassNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090461", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getClassNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090461", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getIllegalAccessOnContextWrapper(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090462", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getIllegalAccessOnContextWrapperLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090462", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInstantiationExcOnContextWrapper(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090463", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInstantiationExcOnContextWrapperLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090463", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getProblemAccessingPrivateKey() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090464", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getProblemAccessingPrivateKeyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090464", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getProblemWithCertificateChain(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090465", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getProblemWithCertificateChainLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090465", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getAssertionIllegalKeystoresValue(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090466", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getAssertionIllegalKeystoresValueLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090466", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getProblemWithConnFilter() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090467", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getProblemWithConnFilterLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090467", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMustSetAuditProviderClassName() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090468", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getMustSetAuditProviderClassNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090468", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidFileFormat() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090469", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidFileFormatLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090469", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoDeployableProviderProperlyConfigured(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090470", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090470";
   }

   public static Loggable logNoDeployableProviderProperlyConfiguredLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090470", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getDefAuthImplSearchFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090473", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getDefAuthImplSearchFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090473", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCreateTempFileFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090474", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCreateTempFileFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090474", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPlaintextProtocolClientError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090475", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090475";
   }

   public static Loggable logPlaintextProtocolClientErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090475", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logProtocolVersionError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090476", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090476";
   }

   public static Loggable logProtocolVersionErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090476", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHandshakeCertUntrustedError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090477", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090477";
   }

   public static Loggable logHandshakeCertUntrustedErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090477", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHandshakeCertInvalidError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090478", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090478";
   }

   public static Loggable logHandshakeCertInvalidErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090478", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHandshakeCertExpiredError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090479", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090479";
   }

   public static Loggable logHandshakeCertExpiredErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090479", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAlertReceivedFromPeer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090480", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090480";
   }

   public static Loggable logAlertReceivedFromPeerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090480", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoCertificateAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090481", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090481";
   }

   public static Loggable logNoCertificateAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090481", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBadCertificateAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090482", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090482";
   }

   public static Loggable logBadCertificateAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090482", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertificateRevokedAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090483", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090483";
   }

   public static Loggable logCertificateRevokedAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090483", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertificateExpiredAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090484", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090484";
   }

   public static Loggable logCertificateExpiredAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090484", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertificateUnknownAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090485", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090485";
   }

   public static Loggable logCertificateUnknownAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090485", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedCertificateAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090486", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090486";
   }

   public static Loggable logUnsupportedCertificateAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090486", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnknownCAAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090487", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090487";
   }

   public static Loggable logUnknownCAAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090487", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logProtocolVersionAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090488", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090488";
   }

   public static Loggable logProtocolVersionAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090488", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoRenegotiationAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090489", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090489";
   }

   public static Loggable logNoRenegotiationAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090489", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAccessDeniedAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090490", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090490";
   }

   public static Loggable logAccessDeniedAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090490", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInsufficientSecurityAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090491", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090491";
   }

   public static Loggable logInsufficientSecurityAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090491", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnexpectedMessageAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090492", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090492";
   }

   public static Loggable logUnexpectedMessageAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090492", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBadRecordMacAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090493", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090493";
   }

   public static Loggable logBadRecordMacAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090493", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDecryptionFailedAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090494", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090494";
   }

   public static Loggable logDecryptionFailedAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090494", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRecordOverFlowAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090495", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090495";
   }

   public static Loggable logRecordOverFlowAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090495", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDecompressionFailureAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090496", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090496";
   }

   public static Loggable logDecompressionFailureAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090496", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHandshakeFailureAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090497", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090497";
   }

   public static Loggable logHandshakeFailureAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090497", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalParameterAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090498", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090498";
   }

   public static Loggable logIllegalParameterAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090498", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDecodeErrorAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090499", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090499";
   }

   public static Loggable logDecodeErrorAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090499", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDecryptErrorAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090500", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090500";
   }

   public static Loggable logDecryptErrorAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090500", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExportRestrictionAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090501", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090501";
   }

   public static Loggable logExportRestrictionAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090501", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInternalErrorAlertReceivedFromPeer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090502", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090502";
   }

   public static Loggable logInternalErrorAlertReceivedFromPeerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090502", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertificateAndPrivateKeyMismatched() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090503", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090503";
   }

   public static Loggable logCertificateAndPrivateKeyMismatchedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090503", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHostnameVerificationError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090504", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090504";
   }

   public static Loggable logHostnameVerificationErrorLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090504", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHostnameVerificationNoCertificateError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090505", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090505";
   }

   public static Loggable logHostnameVerificationNoCertificateErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090505", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHostnameVerificationExceptionError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090506", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090506";
   }

   public static Loggable logHostnameVerificationExceptionErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090506", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHandshakeCertIncompleteError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090508", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090508";
   }

   public static Loggable logHandshakeCertIncompleteErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090508", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSSLListenPortSameAsServerListenPort(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090509", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090509";
   }

   public static Loggable logSSLListenPortSameAsServerListenPortLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090509", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoUsernameSpecified() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090510", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoUsernameSpecifiedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090510", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStackTrace(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090511", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090511";
   }

   public static Loggable logStackTraceLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090511", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDowngradingUntrustedServerIdentity(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090513", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090513";
   }

   public static Loggable logDowngradingUntrustedServerIdentityLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090513", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertificateChainConstraints() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090514", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090514";
   }

   public static Loggable logCertificateChainConstraintsLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090514", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertificateChainIncompleteConstraintsNotChecked() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090515", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090515";
   }

   public static Loggable logCertificateChainIncompleteConstraintsNotCheckedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090515", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLDAPPreviouslyInitialized(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090516", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090516";
   }

   public static Loggable logLDAPPreviouslyInitializedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090516", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBootPropertiesDecryptionFailure(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("090518", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090518";
   }

   public static Loggable logBootPropertiesDecryptionFailureLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("090518", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090519", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090519", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmNoAuthenticatorWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090520", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmNoAuthenticatorWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090520", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmNoRoleMapperWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090521", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmNoRoleMapperWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090521", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmNoDeployableRoleMapperWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090522", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmNoDeployableRoleMapperWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090522", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmNoDeployableRoleMapperEnabledWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090523", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmNoDeployableRoleMapperEnabledWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090523", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmNoAuthorizerWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090524", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmNoAuthorizerWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090524", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmNoDeployableAuthorizerWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090525", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmNoDeployableAuthorizerWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090525", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmNoDeployableAuthorizerEnabledWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090526", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmNoDeployableAuthorizerEnabledWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090526", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmNoCredentialMapperWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090527", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmNoCredentialMapperWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090527", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmMultipleIdentityAssertersForActiveTokenTypeWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090530", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmMultipleIdentityAssertersForActiveTokenTypeWarningLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090530", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmNoAdjudicatorWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090531", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmNoAdjudicatorWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090531", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmRealmAdapterNoRealmAdapterAuthorizerWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090532", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmRealmAdapterNoRealmAdapterAuthorizerWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090532", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmRealmAdapterNoDefaultAuthorizerWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090533", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmRealmAdapterNoDefaultAuthorizerWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090533", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmRealmAdapterMultipleDefaultAuthorizersWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090534", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmRealmAdapterMultipleDefaultAuthorizersWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090534", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmRealmAdapterUnsupportedAuthorizerWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090535", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmRealmAdapterUnsupportedAuthorizerWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090535", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmRealmAdapterMultipleRealmAdapterAuthorizersWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090536", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmRealmAdapterMultipleRealmAdapterAuthorizersWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090536", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmRealmAdapterMultipleRealmAdapterAuthenticatorsWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090537", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmRealmAdapterMultipleRealmAdapterAuthenticatorsWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090537", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmRealmAdapterNoRealmAdapterAuthenticatorWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090538", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmRealmAdapterNoRealmAdapterAuthenticatorWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090538", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmInvalidKeyStoreProviderWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090539", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmInvalidKeyStoreProviderWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090539", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmMultipleTrustedCAKeyStoresWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090540", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmMultipleTrustedCAKeyStoresWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090540", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmMultiplePrivateKeyStoresWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090541", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmMultiplePrivateKeyStoresWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090541", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFatClientHandshakeCertUntrustedError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090542", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090542";
   }

   public static Loggable logFatClientHandshakeCertUntrustedErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090542", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFatClientHandshakeCertIncompleteError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090543", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090543";
   }

   public static Loggable logFatClientHandshakeCertIncompleteErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090543", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoSearchFilterSupplied() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090544", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoSearchFilterSuppliedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090544", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getDefRoleMapImplSearchFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090545", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getDefRoleMapImplSearchFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090545", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServerDemoCommandLineTrust() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090546", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090546";
   }

   public static Loggable logServerDemoCommandLineTrustLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090546", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertificateChainConstraintsStrictNonCriticalFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090547", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090547";
   }

   public static Loggable logCertificateChainConstraintsStrictNonCriticalFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090547", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertificateChainMissingConstraintsFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090548", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090548";
   }

   public static Loggable logCertificateChainMissingConstraintsFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090548", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertificateChainNotACaConstraintsFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090549", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090549";
   }

   public static Loggable logCertificateChainNotACaConstraintsFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090549", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertificateChainPathLenExceededConstraintsFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090550", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090550";
   }

   public static Loggable logCertificateChainPathLenExceededConstraintsFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090550", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertificateChainConstraintsConversionFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090551", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090551";
   }

   public static Loggable logCertificateChainConstraintsConversionFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090551", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCantCheckKeyMatch() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090552", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090552";
   }

   public static Loggable logCantCheckKeyMatchLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090552", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getMustSetRealmClassName(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090554", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getMustSetRealmClassNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090554", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCannotLoadClass(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("090560", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCannotLoadClassLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("090560", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCannotInstantiateClass(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("090561", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCannotInstantiateClassLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("090561", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getClassNotAssignable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("090562", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getClassNotAssignableLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("090562", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHostnameVerifierInitError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090563", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090563";
   }

   public static Loggable logHostnameVerifierInitErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090563", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHostnameVerifierInvalidError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090564", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090564";
   }

   public static Loggable logHostnameVerifierInvalidErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090564", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedKeyAlgorithm(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090565", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090565";
   }

   public static Loggable logUnsupportedKeyAlgorithmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090565", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertificateChainUnrecognizedExtensionFailure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090566", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090566";
   }

   public static Loggable logCertificateChainUnrecognizedExtensionFailureLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090566", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertificateChainAlgKeyUsageFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090567", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090567";
   }

   public static Loggable logCertificateChainAlgKeyUsageFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090567", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertificateChainCheckKeyUsageFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090568", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090568";
   }

   public static Loggable logCertificateChainCheckKeyUsageFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090568", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertificateChainCertSignKeyUsageFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090569", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090569";
   }

   public static Loggable logCertificateChainCertSignKeyUsageFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090569", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJACCPolicyLoaded(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090571", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090571";
   }

   public static Loggable logJACCPolicyLoadedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090571", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJACCPolicyProviderClassNotFound(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090572", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090572";
   }

   public static Loggable logJACCPolicyProviderClassNotFoundLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090572", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalAccess(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090573", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090573";
   }

   public static Loggable logIllegalAccessLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090573", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInstantiationException(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090574", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090574";
   }

   public static Loggable logInstantiationExceptionLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090574", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotAPolicyObject(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090575", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090575";
   }

   public static Loggable logNotAPolicyObjectLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090575", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPolicyConfigurationFactoryProblem() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090576", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090576";
   }

   public static Loggable logPolicyConfigurationFactoryProblemLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090576", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJACCPolicyConfigurationFactoryLoaded(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090577", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090577";
   }

   public static Loggable logJACCPolicyConfigurationFactoryLoadedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090577", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPolicyContextException(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090578", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090578";
   }

   public static Loggable logPolicyContextExceptionLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090578", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJACCPolicyConfigurationFactoryProviderClassNotFound(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090579", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090579";
   }

   public static Loggable logJACCPolicyConfigurationFactoryProviderClassNotFoundLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090579", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCertPathBuilderParametersIllegalRealm() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090580", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCertPathBuilderParametersIllegalRealmLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090580", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCertPathBuilderParametersIllegalCertPathSelector() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090581", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCertPathBuilderParametersIllegalCertPathSelectorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090581", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCertPathValidatorParametersIllegalRealm() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090582", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCertPathValidatorParametersIllegalRealmLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090582", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getWLSJDKCertPathBuilderIllegalCertPathParameters() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090589", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getWLSJDKCertPathBuilderIllegalCertPathParametersLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090589", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getWLSJDKCertPathValidatorIllegalCertPathParameters() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090590", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getWLSJDKCertPathValidatorIllegalCertPathParametersLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090590", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmNoCertPathProvidersWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090591", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmNoCertPathProvidersWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090591", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmNoCertPathBuilderWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090592", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmNoCertPathBuilderWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090592", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmIllegalCertPathBuilderWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090593", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmIllegalCertPathBuilderWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090593", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDomainControllerListUpdateFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090598", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090598";
   }

   public static Loggable logDomainControllerListUpdateFailedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090598", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDomainControllerListInitializationFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090599", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090599";
   }

   public static Loggable logDomainControllerListInitializationFailedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090599", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getFailedToRetrieveLocalMachineName() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090600", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getFailedToRetrieveLocalMachineNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090600", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotAbleToAccessDomainController(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090601", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090601";
   }

   public static Loggable logNotAbleToAccessDomainControllerLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090601", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getJavaNativeVersionMismatchDetected() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090602", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getJavaNativeVersionMismatchDetectedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090602", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCertPathManagerNullTrustedCAError() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090615", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCertPathManagerNullTrustedCAErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090615", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getGetDefaultTrustedCAsError(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090623", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getGetDefaultTrustedCAsErrorLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090623", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getPolicyContextNotOpen(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090625", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getPolicyContextNotOpenLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090625", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnableToDeletePolicyDirectory(Exception arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090626", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnableToDeletePolicyDirectoryLoggable(Exception arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090626", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCannotLinkPolicyConfigurationToSelf() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090627", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCannotLinkPolicyConfigurationToSelfLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090627", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCannotHaveCircularPolicyConfigurationLinks() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090628", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCannotHaveCircularPolicyConfigurationLinksLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090628", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getBadRoleToPrincipalMap(Exception arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090629", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getBadRoleToPrincipalMapLoggable(Exception arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090629", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnableToCreatePolicyWriterDirectory(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090631", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnableToCreatePolicyWriterDirectoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090631", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getFileInTheWayOfDirectory(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090632", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getFileInTheWayOfDirectoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090632", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCannotOpenPolicyFile(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090633", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCannotOpenPolicyFileLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090633", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCannotWriteToPolicyFile(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090634", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCannotWriteToPolicyFileLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090634", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getDefAuthImplNoSearchResults() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090635", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getDefAuthImplNoSearchResultsLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090635", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getDefRoleMapImplNoSearchResults() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090636", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getDefRoleMapImplNoSearchResultsLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090636", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoRoleNameSupplied() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090637", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoRoleNameSuppliedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090637", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoResourceData() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090638", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoResourceDataLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090638", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoResourceType() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090639", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoResourceTypeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090639", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoResourceIdentifier() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090640", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoResourceIdentifierLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090640", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoResourceKeysFound() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090641", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoResourceKeysFoundLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090641", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidDataTypeForResourceKey(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090642", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidDataTypeForResourceKeyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090642", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidResourceType(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090643", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidResourceTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090643", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExpectedResourceType(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090644", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExpectedResourceTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090644", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExpectedResourceKey(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090645", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExpectedResourceKeyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090645", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnknownResourceKey(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090646", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnknownResourceKeyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090646", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidApplicationName() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090647", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidApplicationNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090647", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidComponentName() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090648", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidComponentNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090648", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidApplicationSearchName() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090649", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidApplicationSearchNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090649", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidComponentSearchName() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090650", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidComponentSearchNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090650", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoComponentType() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090651", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNoComponentTypeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090651", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidComponentType(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090652", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidComponentTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090652", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getEmptyArrayValueFound() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090653", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getEmptyArrayValueFoundLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090653", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnexpectedResourceIdData(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090654", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnexpectedResourceIdDataLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090654", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnexpectedResourceKeyArrayValue(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090655", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnexpectedResourceKeyArrayValueLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090655", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnableToConvertFiletoURL(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090658", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnableToConvertFiletoURLLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090658", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHandshakeCertValidationError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090660", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090660";
   }

   public static Loggable logHandshakeCertValidationErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090660", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFatClientHandshakeCertValidationError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090661", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090661";
   }

   public static Loggable logFatClientHandshakeCertValidationErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090661", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeprecatedDeployableAuthorizer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090662", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090662";
   }

   public static Loggable logDeprecatedDeployableAuthorizerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090662", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeprecatedDeployableRoleMapper(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090663", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090663";
   }

   public static Loggable logDeprecatedDeployableRoleMapperLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090663", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getDeployHandleNotSupplied() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090666", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getDeployHandleNotSuppliedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090666", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getApplicationInformationNotSupplied() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090667", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getApplicationInformationNotSuppliedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090667", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIgnoredDeployRole(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090668", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090668";
   }

   public static Loggable logIgnoredDeployRoleLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090668", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIgnoredDeployPolicy(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090669", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090669";
   }

   public static Loggable logIgnoredDeployPolicyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090669", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnableToVerifyUsernameToken(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090670", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnableToVerifyUsernameTokenLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090670", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getTokenNotTypeUsername(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090672", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getTokenNotTypeUsernameLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090672", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDigestDataSourceNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090673", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090673";
   }

   public static Loggable logDigestDataSourceNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090673", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDigestDataSourceNotInitialized(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090674", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090674";
   }

   public static Loggable logDigestDataSourceNotInitializedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090674", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDigestDataSourceNoConnection(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090675", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090675";
   }

   public static Loggable logDigestDataSourceNoConnectionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090675", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDigestDataSourcePrepareError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090676", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090676";
   }

   public static Loggable logDigestDataSourcePrepareErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090676", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDigestDataSourceCreateError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090677", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090677";
   }

   public static Loggable logDigestDataSourceCreateErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090677", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDigestReplayDetectionFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090678", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090678";
   }

   public static Loggable logDigestReplayDetectionFailedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090678", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDigestReplayAttackOccurred(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090679", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090679";
   }

   public static Loggable logDigestReplayAttackOccurredLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090679", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnableToVerifyExpired(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090680", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnableToVerifyExpiredLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090680", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getDigestAuthenticationDisabled(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090681", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getDigestAuthenticationDisabledLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090681", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeploymentValidationProblem(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090682", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090682";
   }

   public static Loggable logDeploymentValidationProblemLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090682", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJACCRoleMapperFactoryProviderClassNotFound(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090700", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090700";
   }

   public static Loggable logJACCRoleMapperFactoryProviderClassNotFoundLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090700", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRoleMapperFactoryProblem() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090701", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090701";
   }

   public static Loggable logRoleMapperFactoryProblemLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090701", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getJACCWebLogicClassesMustMatch() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090705", 1, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getJACCWebLogicClassesMustMatchLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090705", 1, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJACCRoleMapperFactoryLoaded(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090707", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090707";
   }

   public static Loggable logJACCRoleMapperFactoryLoadedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090707", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInconsistentSecurityConfiguration() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090709", 1, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInconsistentSecurityConfigurationLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090709", 1, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSSLCertPathNotValidated(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090714", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090714";
   }

   public static Loggable logSSLCertPathNotValidatedLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090714", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedReadingIdentityEntry(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090716", 2, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090716";
   }

   public static Loggable logFailedReadingIdentityEntryLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090716", 2, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidServerSSLConfiguration(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090717", 2, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090717";
   }

   public static Loggable logInvalidServerSSLConfigurationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090717", 2, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInitializingUsingJavaSecurityManager() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090718", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090718";
   }

   public static Loggable logInitializingUsingJavaSecurityManagerLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090718", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInitializingUsingJACC() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090719", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090719";
   }

   public static Loggable logInitializingUsingJACCLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090719", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNodeManagerPropertiesNotFound() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090720", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090720";
   }

   public static Loggable logNodeManagerPropertiesNotFoundLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090720", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNodeManagerPropertiesError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090721", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090721";
   }

   public static Loggable logNodeManagerPropertiesErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090721", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getChallengeNotCompleted() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090724", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getChallengeNotCompletedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090724", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getChallengeHasCompleted() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090725", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getChallengeHasCompletedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090725", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logVersionableApplicationProviderError(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090764", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090764";
   }

   public static Loggable logVersionableApplicationProviderErrorLoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090764", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getAppVersionCreatePolicyError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090765", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getAppVersionCreatePolicyErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090765", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getAppVersionCreateRoleError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090766", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getAppVersionCreateRoleErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090766", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getAppVersioningNotSupported(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090772", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getAppVersioningNotSupportedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090772", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getVersionCreateFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090773", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getVersionCreateFailureLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090773", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getArgumentNotEncrypted() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090774", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getArgumentNotEncryptedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090774", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToGenerateSignedSAMLAssertion(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090777", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090777";
   }

   public static Loggable logUnableToGenerateSignedSAMLAssertionLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090777", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDowngradingUntrustedIdentity(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090779", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090779";
   }

   public static Loggable logDowngradingUntrustedIdentityLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090779", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWebAppFilesCaseMismatch(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090780", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090780";
   }

   public static Loggable logWebAppFilesCaseMismatchLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090780", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorProductionModeNoEcho() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090782", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090782";
   }

   public static Loggable logErrorProductionModeNoEchoLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090782", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorDevModeNoEcho() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090783", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090783";
   }

   public static Loggable logErrorDevModeNoEchoLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090783", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullDataSourceName() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090787", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullDataSourceNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090787", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnableToLocateDataSourceConfig(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090788", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnableToLocateDataSourceConfigLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090788", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLoadKeyStoreException(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090812", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090812";
   }

   public static Loggable logLoadKeyStoreExceptionLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090812", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logShutdownSecurityRealm(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090814", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090814";
   }

   public static Loggable logShutdownSecurityRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090814", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCannotActivateChangesNoDefaultRealmError() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090817", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCannotActivateChangesNoDefaultRealmErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090817", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCannotActivateChangesImproperlyConfiguredDefaultRealmError() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090818", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCannotActivateChangesImproperlyConfiguredDefaultRealmErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090818", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getJACCPropertyNotSet(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090819", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getJACCPropertyNotSetLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090819", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnexpectedNullVariable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090820", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnexpectedNullVariableLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090820", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotRegisterWLSX509CertificateFactoryAsDefaultFactory() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090823", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090823";
   }

   public static Loggable logCouldNotRegisterWLSX509CertificateFactoryAsDefaultFactoryLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090823", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLDIFEmptyForCredentialProvider(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090827", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090827";
   }

   public static Loggable logLDIFEmptyForCredentialProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090827", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getJACCWebLogicRoleMapperFactoryNotSupplied() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090838", 1, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getJACCWebLogicRoleMapperFactoryNotSuppliedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090838", 1, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionRegisteringSAMLService(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090839", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090839";
   }

   public static Loggable logExceptionRegisteringSAMLServiceLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090839", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRegisteredSAMLService(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090840", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090840";
   }

   public static Loggable logRegisteredSAMLServiceLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090840", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSAMLProviderBadParam(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090846", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090846";
   }

   public static Loggable logSAMLProviderBadParamLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090846", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSAMLProviderMissingParam(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090847", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090847";
   }

   public static Loggable logSAMLProviderMissingParamLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090847", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmNoCertPathValidatorWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090859", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmNoCertPathValidatorWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090859", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertificatePolicyIdDoesntExistIntheList(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090861", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090861";
   }

   public static Loggable logCertificatePolicyIdDoesntExistIntheListLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090861", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPolicyQualifierIdNotCPS(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090862", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090862";
   }

   public static Loggable logPolicyQualifierIdNotCPSLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090862", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmNoMBeanDelegationWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090865", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmNoMBeanDelegationWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090865", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIgnoredUncheckedPolicy(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090866", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090866";
   }

   public static Loggable logIgnoredUncheckedPolicyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090866", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmSAMLConfigWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090868", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmSAMLConfigWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090868", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPolicyConsumerProviderError(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090869", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090869";
   }

   public static Loggable logPolicyConsumerProviderErrorLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090869", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLoadRealmFailed(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090870", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090870";
   }

   public static Loggable logLoadRealmFailedLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090870", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRoleConsumerProviderError(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090871", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090871";
   }

   public static Loggable logRoleConsumerProviderErrorLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090871", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getServiceNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090872", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getServiceNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090872", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNotInstanceof(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090873", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNotInstanceofLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090873", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullObjectReturned(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090874", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullObjectReturnedLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090874", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullParameterSupplied(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090875", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullParameterSuppliedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090875", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getConsumerNotConfigured(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090876", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getConsumerNotConfiguredLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090876", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getExceptionObtainingService(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090877", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getExceptionObtainingServiceLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090877", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnknownTokenType(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090878", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnknownTokenTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090878", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWarningFailedToLoadJAASConfiguration() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090880", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090880";
   }

   public static Loggable logWarningFailedToLoadJAASConfigurationLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090880", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSSLUsingNullCipher() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090882", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090882";
   }

   public static Loggable logSSLUsingNullCipherLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090882", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullResourceName() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090883", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullResourceNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090883", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullUserName() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090884", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullUserNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090884", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullRemoteUserName() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090885", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullRemoteUserNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090885", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNullIllegalReturnNum() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090886", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getNullIllegalReturnNumLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090886", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUsingDefaultHV() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090908", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090908";
   }

   public static Loggable logUsingDefaultHVLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090908", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUsingConfiguredHV(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090909", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090909";
   }

   public static Loggable logUsingConfiguredHVLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090909", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidGrantSyntax(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090910", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidGrantSyntaxLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090910", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidPermissionSyntax(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090911", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidPermissionSyntaxLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090911", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAttemptingCertRevocCheck(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090912", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090912";
   }

   public static Loggable logAttemptingCertRevocCheckLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090912", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnknownCertRevocStatusNoFail(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090913", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090913";
   }

   public static Loggable logUnknownCertRevocStatusNoFailLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090913", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCertRevocStatus(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090914", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090914";
   }

   public static Loggable logCertRevocStatusLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090914", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIgnoredNonceCertRevocStatus(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090915", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090915";
   }

   public static Loggable logIgnoredNonceCertRevocStatusLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090915", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnknownCertRevocStatusFail(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090916", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090916";
   }

   public static Loggable logUnknownCertRevocStatusFailLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090916", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRevokedCertRevocStatusFail(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090917", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090917";
   }

   public static Loggable logRevokedCertRevocStatusFailLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090917", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotRevokedCertRevocStatusNotFail(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090918", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090918";
   }

   public static Loggable logNotRevokedCertRevocStatusNotFailLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090918", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedSSLMinimumProtocolVersion(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090919", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090919";
   }

   public static Loggable logUnsupportedSSLMinimumProtocolVersionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090919", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCerticomAvailJsseEnabled_sysProp(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090920", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090920";
   }

   public static Loggable logCerticomAvailJsseEnabled_sysPropLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090920", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCerticomNotAvailJsseDisabled_sysProp(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090921", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090921";
   }

   public static Loggable logCerticomNotAvailJsseDisabled_sysPropLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090921", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCerticomAvailJsseEnabled_SSLMBean() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090922", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090922";
   }

   public static Loggable logCerticomAvailJsseEnabled_SSLMBeanLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090922", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCerticomNotAvailJsseDisabled_SSLMBean() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090923", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090923";
   }

   public static Loggable logCerticomNotAvailJsseDisabled_SSLMBeanLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090923", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoSSLMBeanJsseEnabled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090924", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090924";
   }

   public static Loggable logNoSSLMBeanJsseEnabledLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090924", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBadSysPropJsseEnabled(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090925", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090925";
   }

   public static Loggable logBadSysPropJsseEnabledLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090925", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBadKeyStoreSource(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("090926", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090926";
   }

   public static Loggable logBadKeyStoreSourceLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("090926", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBadKeyStoreType(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090927", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090927";
   }

   public static Loggable logBadKeyStoreTypeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090927", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCantInstantiateKeyStore(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("090928", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090928";
   }

   public static Loggable logCantInstantiateKeyStoreLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("090928", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCantLoadKeyStore(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("090929", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090929";
   }

   public static Loggable logCantLoadKeyStoreLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("090929", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logChannelIdentityKeyStoreIncomplete() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090930", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090930";
   }

   public static Loggable logChannelIdentityKeyStoreIncompleteLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090930", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logChannelIdentityKeyStoreInactive() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090931", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090931";
   }

   public static Loggable logChannelIdentityKeyStoreInactiveLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090931", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logChannelIdentityKeyStoreMissingAlias() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090932", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090932";
   }

   public static Loggable logChannelIdentityKeyStoreMissingAliasLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090932", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRestartingRealm(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090936", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090936";
   }

   public static Loggable logRestartingRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090936", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCompletedRestartingRealm(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090937", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090937";
   }

   public static Loggable logCompletedRestartingRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090937", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUserLoginFailureGeneral() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090938", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUserLoginFailureGeneralLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090938", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidJWTToken() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090939", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidJWTTokenLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090939", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getIAJWTTokenWrongType() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090940", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getIAJWTTokenWrongTypeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090940", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getIAJWTTokenFailedPassSignatureVerify() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090941", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getIAJWTTokenFailedPassSignatureVerifyLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090941", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionsRequireNewRealm(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090942", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090942";
   }

   public static Loggable logPartitionsRequireNewRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090942", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getInvalidRealmProviderNotIDDAwareWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090943", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getInvalidRealmProviderNotIDDAwareWarningLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090943", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNotIDDAwareProvider(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090944", 4, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090944";
   }

   public static Loggable logNotIDDAwareProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090944", 4, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logShutdownRetiredSecurityRealm(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090945", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090945";
   }

   public static Loggable logShutdownRetiredSecurityRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090945", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPreInitializingUsingRealm(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090946", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090946";
   }

   public static Loggable logPreInitializingUsingRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090946", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPostInitializingUsingRealm(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090947", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090947";
   }

   public static Loggable logPostInitializingUsingRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090947", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStartingRealm(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090948", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090948";
   }

   public static Loggable logStartingRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090948", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCompletedStartingRealm(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090949", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090949";
   }

   public static Loggable logCompletedStartingRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090949", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logShutdownRealmFailed(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090950", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090950";
   }

   public static Loggable logShutdownRealmFailedLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090950", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getFailureWithRealm(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090951", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getFailureWithRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090951", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getIdentityDomainIgnored(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090952", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getIdentityDomainIgnoredLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090952", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCannotActivateChangesNoAdminIDDSetError() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090953", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCannotActivateChangesNoAdminIDDSetErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090953", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCannotActivateChangesNoPartitionIDDSetError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090954", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCannotActivateChangesNoPartitionIDDSetErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090954", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCannotActivateChangesNoIDDConfiguredError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090955", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCannotActivateChangesNoIDDConfiguredErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090955", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getConflictingPermissionsDeclarationError() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090956", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getConflictingPermissionsDeclarationErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090956", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getProhibitedPermissionsError() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090957", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getProhibitedPermissionsErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090957", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getDeploymentDescriptorGrantDisabledError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090958", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getDeploymentDescriptorGrantDisabledErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090958", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getPackagedPermissionsDisabledError() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090959", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getPackagedPermissionsDisabledErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090959", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoSSLMBeanPossibleFailure() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090960", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090960";
   }

   public static Loggable logNoSSLMBeanPossibleFailureLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090960", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCannotActivateChangesRealmNameExistsError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090961", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCannotActivateChangesRealmNameExistsErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090961", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCannotActivateChangesImproperlyConfiguredRealmError(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090962", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getCannotActivateChangesImproperlyConfiguredRealmErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090962", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logModifiedLDAPEntryForProvider(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090963", 32, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090963";
   }

   public static Loggable logModifiedLDAPEntryForProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090963", 32, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDatasourceConnectionError(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090964", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090964";
   }

   public static Loggable logDatasourceConnectionErrorLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090964", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getUnableToCreatePolicyInstance(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090965", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getUnableToCreatePolicyInstanceLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090965", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logKernelPermissionFailure(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090966", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090966";
   }

   public static Loggable logKernelPermissionFailureLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090966", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getLDAPConnectionParamMissing(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090967", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getLDAPConnectionParamMissingLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090967", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getLDAPConnectionParamError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090968", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getLDAPConnectionParamErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090968", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getOIDCIATokenTypeCanNotBeNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090969", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getOIDCIATokenTypeCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090969", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getOIDCIATokenCanNotBeNull() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090970", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getOIDCIATokenCanNotBeNullLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090970", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getOIDCIATokenTypeInCorrect(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090971", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getOIDCIATokenTypeInCorrectLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090971", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String getResourceTypeAlreadyRegistered(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090972", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getResourceTypeAlreadyRegisteredLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090972", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLdapRequestTimeout(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090973", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090973";
   }

   public static Loggable logLdapRequestTimeoutLoggable(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090973", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getIniFileNotExist(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090974", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static Loggable getIniFileNotExistLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090974", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConnectionNonceExpired() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090975", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090975";
   }

   public static Loggable logConnectionNonceExpiredLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090975", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAuditingNotEnabledInSecureMode(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090976", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090976";
   }

   public static Loggable logAuditingNotEnabledInSecureModeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090976", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAuditingLevelInappropriateInSecureMode(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090977", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090977";
   }

   public static Loggable logAuditingLevelInappropriateInSecureModeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090977", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLockoutSettingNotSecureInSecureMode(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090978", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090978";
   }

   public static Loggable logLockoutSettingNotSecureInSecureModeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090978", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnEncryptedPasswdInCommandLine() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090979", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090979";
   }

   public static Loggable logUnEncryptedPasswdInCommandLineLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090979", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoPasswordValidatorInSecureMode(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090980", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090980";
   }

   public static Loggable logNoPasswordValidatorInSecureModeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090980", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSecurityManagerNotEnabledInSecureMode() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090981", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090981";
   }

   public static Loggable logSecurityManagerNotEnabledInSecureModeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090981", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnixMachinePostBindNotEnabled(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("090982", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090982";
   }

   public static Loggable logUnixMachinePostBindNotEnabledLoggable(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("090982", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAdministrationPortNotEnabledInSecureMode() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("090983", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090983";
   }

   public static Loggable logAdministrationPortNotEnabledInSecureModeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("090983", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFileOwnerInsecureSecureMode(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("090984", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090984";
   }

   public static Loggable logFileOwnerInsecureSecureModeLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("090984", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFilePermissionInsecureSecureMode(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090985", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090985";
   }

   public static Loggable logFilePermissionInsecureSecureModeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090985", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSecureModeRequiresNewRealm(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090986", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090986";
   }

   public static Loggable logSecureModeRequiresNewRealmLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090986", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSSLv3EnabledBySysProp(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090987", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090987";
   }

   public static Loggable logSSLv3EnabledBySysPropLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090987", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logBasicConstraintsValidationEnabledBySysProp(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090988", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090988";
   }

   public static Loggable logBasicConstraintsValidationEnabledBySysPropLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090988", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHostNameVerificationDisabledBySysProp(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090989", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090989";
   }

   public static Loggable logHostNameVerificationDisabledBySysPropLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090989", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHostNameVerificationDisabledBySSLMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090990", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090990";
   }

   public static Loggable logHostNameVerificationDisabledBySSLMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090990", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHostNameVerificationDisabledByNetworkAccessPointMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090991", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090991";
   }

   public static Loggable logHostNameVerificationDisabledByNetworkAccessPointMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090991", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSSLv3MinProtocolEnabledBySysProp(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090992", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090992";
   }

   public static Loggable logSSLv3MinProtocolEnabledBySysPropLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090992", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSSLv3MinProtocolEnabledBySSLMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090993", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090993";
   }

   public static Loggable logSSLv3MinProtocolEnabledBySSLMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090993", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSSLv3MinProtocolEnabledByNetworkAccessPointMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090994", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090994";
   }

   public static Loggable logSSLv3MinProtocolEnabledByNetworkAccessPointMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090994", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullCipherAllowedBySysProp(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090995", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090995";
   }

   public static Loggable logNullCipherAllowedBySysPropLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090995", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullCipherAllowedBySSLMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090996", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090996";
   }

   public static Loggable logNullCipherAllowedBySSLMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090996", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullCipherAllowedByNetworkAccessPointMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090997", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090997";
   }

   public static Loggable logNullCipherAllowedByNetworkAccessPointMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090997", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAllowAnonymousCiphersBySysProp(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090998", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090998";
   }

   public static Loggable logAllowAnonymousCiphersBySysPropLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090998", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTLSClientInitSecureRenegotiationBySSLMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("090999", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "090999";
   }

   public static Loggable logTLSClientInitSecureRenegotiationBySSLMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("090999", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTLSClientInitSecureRenegotiationByNetworkAccessPointMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("091000", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091000";
   }

   public static Loggable logTLSClientInitSecureRenegotiationByNetworkAccessPointMBeanLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("091000", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWeakCipherSuitesBySSLMBean(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("091001", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091001";
   }

   public static Loggable logWeakCipherSuitesBySSLMBeanLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("091001", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWeakCipherSuitesByNetworkAccessPointMBean(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("091002", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091002";
   }

   public static Loggable logWeakCipherSuitesByNetworkAccessPointMBeanLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("091002", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAdminUserInsecureName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("091003", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091003";
   }

   public static Loggable logAdminUserInsecureNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("091003", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSamplesInstalledInSecureMode() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("091004", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091004";
   }

   public static Loggable logSamplesInstalledInSecureModeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("091004", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRetryBootAuthentication(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("091005", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091005";
   }

   public static Loggable logRetryBootAuthenticationLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("091005", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJdk8SslNotEndorsed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("091006", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091006";
   }

   public static Loggable logJdk8SslNotEndorsedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("091006", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIDCSServer429ResponseCount(int arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("091007", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091007";
   }

   public static Loggable logIDCSServer429ResponseCountLoggable(int arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("091007", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIDCSServerTimeoutCount(int arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("091008", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091008";
   }

   public static Loggable logIDCSServerTimeoutCountLoggable(int arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("091008", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWlsSecurityContextInit(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("091009", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091009";
   }

   public static Loggable logWlsSecurityContextInitLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("091009", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWlsSecurityContextAuthenticateException(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("091010", 8, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091010";
   }

   public static Loggable logWlsSecurityContextAuthenticateExceptionLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("091010", 8, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWlsSecurityContextInitAuditor(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("091011", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091011";
   }

   public static Loggable logWlsSecurityContextInitAuditorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("091011", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullArgForCheckAccessToWebResource(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("091012", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091012";
   }

   public static Loggable logNullArgForCheckAccessToWebResourceLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("091012", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCurrentSubjectIsNull(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("091013", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091013";
   }

   public static Loggable logCurrentSubjectIsNullLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("091013", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIsCallerInRole(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("091014", 64, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091014";
   }

   public static Loggable logIsCallerInRoleLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("091014", 64, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIDCSProviderRequestRetryCount(int arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("091015", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091015";
   }

   public static Loggable logIDCSProviderRequestRetryCountLoggable(int arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("091015", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIDCSProviderBackoffCount(int arg0, int arg1, int arg2, long arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("091016", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091016";
   }

   public static Loggable logIDCSProviderBackoffCountLoggable(int arg0, int arg1, int arg2, long arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("091016", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeprecatedSecurityProvider(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("091017", 16, args, SecurityLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "091017";
   }

   public static Loggable logDeprecatedSecurityProviderLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("091017", 16, args, "weblogic.security.SecurityLogLocalizer", SecurityLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SecurityLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.security.SecurityLogLocalizer", SecurityLogger.class.getClassLoader());
      private MessageLogger messageLogger = SecurityLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = SecurityLogger.findMessageLogger();
      }
   }
}
