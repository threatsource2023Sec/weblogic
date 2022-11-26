package weblogic.jms;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class JMSLogger {
   private static final String LOCALIZER_CLASS = "weblogic.jms.JMSLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(JMSLogger.class.getName());
   }

   public static String logCntPools(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040010", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040010";
   }

   public static String logJMSFailedInit() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("040014", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040014";
   }

   public static String logJMSShutdown() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("040015", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040015";
   }

   public static String logConnFactoryFailed(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040017", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040017";
   }

   public static String logErrorInitialCtx(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040018", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040018";
   }

   public static String logBackEndBindingFailed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040019", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040019";
   }

   public static String logBytesThresholdHighDestination(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040024", 2, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040024";
   }

   public static String logBytesThresholdLowDestination(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040025", 2, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040025";
   }

   public static String logMessagesThresholdHighDestination(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040026", 2, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040026";
   }

   public static String logMessagesThresholdLowDestination(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040027", 2, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040027";
   }

   public static String logBytesThresholdHighServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040028", 2, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040028";
   }

   public static String logBytesThresholdLowServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040029", 2, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040029";
   }

   public static String logMessagesThresholdHighServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040030", 2, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040030";
   }

   public static String logMessagesThresholdLowServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040031", 2, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040031";
   }

   public static String logErrorUnregisteringBackEndDestination(String arg0, Object arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040068", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040068";
   }

   public static String logErrorUnregisteringProducer(String arg0, Object arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040069", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040069";
   }

   public static String logErrorUnregisteringFrontEndConnection(String arg0, Object arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040070", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040070";
   }

   public static String logErrorUnregisteringFrontEndSession(String arg0, Object arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040071", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040071";
   }

   public static String logErrorUnregisteringConsumer(String arg0, Object arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040072", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040072";
   }

   public static String logInfoMigrationOkay(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040089", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040089";
   }

   public static String logCntDefCFactory(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040090", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040090";
   }

   public static String logStoreError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040095", 2, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040095";
   }

   public static String logCntDefCFactoryUndeployed(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040107", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040107";
   }

   public static String logCFactoryDeployed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040108", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040108";
   }

   public static String logJMSServerDeployed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040109", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040109";
   }

   public static String logStoreOpen(String arg0, String arg1, String arg2, int arg3, long arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("040113", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040113";
   }

   public static String logErrorCreateCF(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040119", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040119";
   }

   public static String logErrorBindCF(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040120", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040120";
   }

   public static String logErrorBEMultiDeployed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040121", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040121";
   }

   public static String logErrorCreateBE(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040122", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040122";
   }

   public static String logErrorStartBE(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040123", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040123";
   }

   public static String logErrorCreateSSP(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040124", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040124";
   }

   public static String logErrorCreateCC(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040125", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040125";
   }

   public static String logErrorMulticastOpen(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040127", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040127";
   }

   public static String logIllegalThresholdValue(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040215", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040215";
   }

   public static String logJMSInitialized() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("040305", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040305";
   }

   public static String logJMSActive() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("040306", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040306";
   }

   public static String logJMSSuspending() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("040307", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040307";
   }

   public static String logJMSForceSuspending() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("040308", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040308";
   }

   public static String logJMSServerResuming(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040321", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040321";
   }

   public static String logJMSServerSuspending(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040324", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040324";
   }

   public static String logJMSServerSuspended(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040325", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040325";
   }

   public static String logExpiredMessageHeaderProperty(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040351", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040351";
   }

   public static String logExpiredMessageHeader(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040352", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040352";
   }

   public static String logExpiredMessageProperty(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040353", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040353";
   }

   public static String logExpiredMessageNoHeaderProperty(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040354", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040354";
   }

   public static String logJMSDDNullMember(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040359", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040359";
   }

   public static String logStackTrace(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040368", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040368";
   }

   public static String logStackTraceLinked(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040370", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040370";
   }

   public static String logAddedSessionPoolToBeRemoved(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040371", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040371";
   }

   public static String logErrorUnregisterJMSServer(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040372", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040372";
   }

   public static String logJMSServiceNotInitialized() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("040373", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040373";
   }

   public static String logProductionPauseOfDestination(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040376", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040376";
   }

   public static String logProductionResumeOfDestination(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040377", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040377";
   }

   public static String logInsertionPauseOfDestination(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040378", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040378";
   }

   public static String logInsertionResumeOfDestination(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040379", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040379";
   }

   public static String logConsumptionPauseOfDestination(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040380", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040380";
   }

   public static String logConsumptionResumeOfDestination(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040381", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040381";
   }

   public static String logProductionPauseOfJMSServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040382", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040382";
   }

   public static String logProductionResumeOfJMSServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040383", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040383";
   }

   public static String logInsertionPauseOfJMSServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040384", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040384";
   }

   public static String logInsertionResumeOfJMSServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040385", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040385";
   }

   public static String logConsumptionPauseOfJMSServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040386", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040386";
   }

   public static String logConsumptionResumeOfJMSServer(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040387", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040387";
   }

   public static String logForeignJMSDeployed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040404", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040404";
   }

   public static String logErrorBindForeignJMS(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040405", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040405";
   }

   public static String logDDDeployed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040406", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040406";
   }

   public static String logDefaultCFactoryDeployed(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040407", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040407";
   }

   public static String logErrorBindDefaultCF(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040408", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040408";
   }

   public static String logExpiredSAFMessageHeaderProperty(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040409", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040409";
   }

   public static String logExpiredSAFMessageHeader(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040410", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040410";
   }

   public static String logExpiredSAFMessageProperty(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040411", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040411";
   }

   public static String logExpiredSAFMessageNoHeaderProperty(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040412", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040412";
   }

   public static String logAdminForceCommit(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040420", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040420";
   }

   public static String logAdminForceCommitError(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040421", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040421";
   }

   public static String logAdminForceRollback(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040422", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040422";
   }

   public static String logAdminForceRollbackError(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040423", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040423";
   }

   public static String logUserTransactionsEnabledDeprecated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040430", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040430";
   }

   public static String logXAServerEnabledDeprecated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040431", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040431";
   }

   public static String logNameConflictBindingGlobalJNDIName(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040442", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040442";
   }

   public static String logNameConflictChangingGlobalJNDIName(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("040443", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040443";
   }

   public static String logCouldNotUnbindGlobalJNDIName(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040444", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040444";
   }

   public static String logNameConflictChangingLocalJNDIName(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("040445", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040445";
   }

   public static String logCouldNotUnbindLocalJNDIName(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040446", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040446";
   }

   public static String logNameConflictBindingLocalJNDIName(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040447", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040447";
   }

   public static String logReplacingJMSFileStoreMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040448", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040448";
   }

   public static String logReplacingJMSJDBCStoreMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040449", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040449";
   }

   public static String logReplacingJMSPagingStore(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040450", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040450";
   }

   public static String logReplacingJMSJDBCPagingStore(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040451", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040451";
   }

   public static String logServerSessionPoolsDeprecated() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("040452", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040452";
   }

   public static String logServerPagingParametersDeprecated(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040453", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040453";
   }

   public static String logFlowControlEnabledDueToLowMemory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040455", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040455";
   }

   public static String logInvalidJMSModuleSubDeploymentConfiguration(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("040456", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040456";
   }

   public static String logTemplateOnDDNotSupported(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040457", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040457";
   }

   public static String logUnprepareFailedInPrepare(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040458", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040458";
   }

   public static String logDeactivateFailedInActivate(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040459", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040459";
   }

   public static String logComponentCloseFailure(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040460", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040460";
   }

   public static String logDeactivateFailedInRollbackUpdate(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040461", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040461";
   }

   public static String logDestroyFailedInAdd(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040464", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040464";
   }

   public static String logDeactivateFailedInInit(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040467", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040467";
   }

   public static String logDeactivateFailedInActivateUpdate(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040470", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040470";
   }

   public static String logBytesMaximumNoEffect(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040475", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040475";
   }

   public static String logMessagesMaximumNoEffect(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040476", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040476";
   }

   public static String logErrorInJNDIUnbind(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040477", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040477";
   }

   public static String logErrorDeployingDefaultFactories(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040478", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040478";
   }

   public static String logErrorRollingBackConnectionConsumer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040479", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040479";
   }

   public static String logErrorRemovingConnectionConsumer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040480", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040480";
   }

   public static String logDestinationNameConflict(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040490", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040490";
   }

   public static String logSplitDeployment(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040491", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040491";
   }

   public static String logJMSServerShutdownError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040494", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040494";
   }

   public static String logTemplateBytesMaximumNoEffect(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040496", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040496";
   }

   public static String logTemplateMessagesMaximumNoEffect(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040497", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040497";
   }

   public static String logDDForwardingError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040498", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040498";
   }

   public static String logErrorPushingMessage(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040499", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040499";
   }

   public static String logRollbackChangeFailedInInit(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040500", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040500";
   }

   public static String logRollbackChangedFailedInRollbackUpdate(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040501", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040501";
   }

   public static String logActivateFailedDuringTargetingChange(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040502", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040502";
   }

   public static String logChangingDeliveryModeOverride(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("040503", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040503";
   }

   public static String logUnableToAddEntity(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040504", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040504";
   }

   public static String logNoEARSubDeployment(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040505", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040505";
   }

   public static String logSAFForwarderConnected(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040506", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040506";
   }

   public static String logSAFForwarderDisconnected(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040507", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040507";
   }

   public static String logReplacingBridgeDestinationMBean(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040508", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040508";
   }

   public static String logErrorDeployingBE(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040509", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040509";
   }

   public static String logErrorEstablishingJNDIListener(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040510", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040510";
   }

   public static String logInfoWaitForUnbind(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040511", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040511";
   }

   public static String logErrorWaitForUnbind(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040512", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040512";
   }

   public static String logErrorRemovingJNDIListener(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040513", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040513";
   }

   public static String logJNDIDynamicChangeException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040514", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040514";
   }

   public static String logFailedToUnregisterInterceptionPoint(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040515", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040515";
   }

   public static String logExistedWDDDeprecated(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040520", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040520";
   }

   public static String logCreatedWDDDeprecated(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040521", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040521";
   }

   public static String logCrossDomainSecurityFailureInCDS(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040522", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040522";
   }

   public static String logInvalidJMSResourceDefinitionProperties(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("040523", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040523";
   }

   public static String logSubdeploymentNotTargeted(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("040524", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040524";
   }

   public static String logShutdownConnectionsForResourceGroup(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040525", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040525";
   }

   public static String logMatchingJMSServerNotFound(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("040526", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040526";
   }

   public static String logMatchingSAFAgentNotFound(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("040527", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040527";
   }

   public static String logStartJMSServiceForPartition(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040528", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040528";
   }

   public static String logShutdownJMSServiceForPartition(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("040529", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040529";
   }

   public static String logJMSResDefnMatchingJMSServerNotFound(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040530", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040530";
   }

   public static String logRestrictedJMSServerHosts(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("040531", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040531";
   }

   public static String logJMSResDefRestrictedJMSServerHosts(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("040532", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040532";
   }

   public static String logGeneratedJMSModuleAndDestinationName(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("040533", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040533";
   }

   public static String logSubscriptionLimit(String arg0, String arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("040534", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040534";
   }

   public static String logInvalidSubscriptionLimit(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("040535", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040535";
   }

   public static String logMultiSameNamedDestinations(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040536", 16, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040536";
   }

   public static String logMultiSameNamedDestinationsCleared(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("040537", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040537";
   }

   public static String logInfoProducerLoadBalancingPolicy(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("040538", 64, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040538";
   }

   public static String logErrorProducerLoadBalancingPolicy(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("040539", 8, args, JMSLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JMSLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "040539";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.jms.JMSLogLocalizer", JMSLogger.class.getClassLoader());
      private MessageLogger messageLogger = JMSLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = JMSLogger.findMessageLogger();
      }
   }
}
