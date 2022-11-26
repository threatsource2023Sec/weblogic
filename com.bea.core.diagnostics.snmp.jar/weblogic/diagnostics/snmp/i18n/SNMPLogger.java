package weblogic.diagnostics.snmp.i18n;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class SNMPLogger {
   private static final String LOCALIZER_CLASS = "weblogic.diagnostics.snmp.i18n.SNMPLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(SNMPLogger.class.getName());
   }

   public static String logUnableToProxy(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320900", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320900";
   }

   public static String logAttrChangeCreationError(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("320913", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320913";
   }

   public static String logTrapLogAddNotifError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320918", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320918";
   }

   public static String logMonitorCreationError(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("320923", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320923";
   }

   public static String logMonitorNotificationError(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("320925", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320925";
   }

   public static String logAgentInitializing() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320927", 64, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320927";
   }

   public static String logAgentInitFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320928", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320928";
   }

   public static String logAgentInitComplete() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320929", 64, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320929";
   }

   public static String logAgentColdStartSent() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320930", 64, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320930";
   }

   public static String logTrapVersionInfo(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320931", 32, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320931";
   }

   public static String logTCPProviderSendException(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320937", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320937";
   }

   public static String logCannotModifyMBeanAttributes() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320938", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320938";
   }

   public static Loggable logCannotModifyMBeanAttributesLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("320938", 8, args, "weblogic.diagnostics.snmp.i18n.SNMPLogLocalizer", SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SNMPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorAddingRowForMBeanInstance(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320939", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320939";
   }

   public static String logErrorDeletingRowForMBeanInstance(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320940", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320940";
   }

   public static String logUseOfDefaultSnmpChannelDetected() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320941", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320941";
   }

   public static Loggable logUseOfDefaultSnmpChannelDetectedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("320941", 8, args, "weblogic.diagnostics.snmp.i18n.SNMPLogLocalizer", SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SNMPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTypeName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320942", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320942";
   }

   public static Loggable logInvalidTypeNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("320942", 8, args, "weblogic.diagnostics.snmp.i18n.SNMPLogLocalizer", SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SNMPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidAttributeName(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320943", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320943";
   }

   public static Loggable logInvalidAttributeNameLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("320943", 8, args, "weblogic.diagnostics.snmp.i18n.SNMPLogLocalizer", SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SNMPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSecurityNameNotSpecifiedForV3TrapDestination(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320944", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320944";
   }

   public static Loggable logSecurityNameNotSpecifiedForV3TrapDestinationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("320944", 8, args, "weblogic.diagnostics.snmp.i18n.SNMPLogLocalizer", SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SNMPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSNMPInvalidTrapVersion() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320945", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320945";
   }

   public static Loggable logSNMPInvalidTrapVersionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("320945", 8, args, "weblogic.diagnostics.snmp.i18n.SNMPLogLocalizer", SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SNMPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSNMPAgentDeployedToServer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320946", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320946";
   }

   public static Loggable logSNMPAgentDeployedToServerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("320946", 8, args, "weblogic.diagnostics.snmp.i18n.SNMPLogLocalizer", SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SNMPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidAuthenticationProtocol(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("320947", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320947";
   }

   public static Loggable logInvalidAuthenticationProtocolLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("320947", 8, args, "weblogic.diagnostics.snmp.i18n.SNMPLogLocalizer", SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SNMPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidSNMPTrapDestinationSecurityLevel(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320948", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320948";
   }

   public static Loggable logInvalidSNMPTrapDestinationSecurityLevelLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("320948", 8, args, "weblogic.diagnostics.snmp.i18n.SNMPLogLocalizer", SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SNMPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSNMPProxyInvalidSecurityLevel(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("320949", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320949";
   }

   public static Loggable logSNMPProxyInvalidSecurityLevelLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("320949", 8, args, "weblogic.diagnostics.snmp.i18n.SNMPLogLocalizer", SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SNMPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSNMPServiceFailure(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320950", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320950";
   }

   public static String logSNMPAgentXInitializationFailure(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320951", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320951";
   }

   public static String logErrorSendingSNMPNotification(int arg0, int arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("320952", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320952";
   }

   public static String logStartedSNMPagent(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320953", 32, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320953";
   }

   public static String logSNMPExtensionProviderError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320954", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320954";
   }

   public static String logAgentShutdownComplete() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320955", 64, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320955";
   }

   public static String logInvalidTrapDestination(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("320956", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320956";
   }

   public static String logInvalidCommunityPrefix() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("320957", 8, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320957";
   }

   public static Loggable logInvalidCommunityPrefixLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("320957", 8, args, "weblogic.diagnostics.snmp.i18n.SNMPLogLocalizer", SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SNMPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSNMPv1OrSNMPv2Deprecated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("320958", 16, args, SNMPLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "320958";
   }

   public static Loggable logSNMPv1OrSNMPv2DeprecatedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("320958", 16, args, "weblogic.diagnostics.snmp.i18n.SNMPLogLocalizer", SNMPLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SNMPLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.diagnostics.snmp.i18n.SNMPLogLocalizer", SNMPLogger.class.getClassLoader());
      private MessageLogger messageLogger = SNMPLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = SNMPLogger.findMessageLogger();
      }
   }
}
