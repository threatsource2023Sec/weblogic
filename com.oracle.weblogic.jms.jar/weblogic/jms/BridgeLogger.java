package weblogic.jms;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class BridgeLogger {
   private static final String LOCALIZER_CLASS = "weblogic.jms.BridgeLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(BridgeLogger.class.getName());
   }

   public static String logBridgeFailedInit() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("200000", 8, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200000";
   }

   public static String logBridgeShutdown() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("200001", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200001";
   }

   public static String logErrorCreateBridgeWhenShutdown(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200002", 8, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200002";
   }

   public static String logErrorCreateBridge(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("200003", 8, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200003";
   }

   public static String logBridgeDeployed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200004", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200004";
   }

   public static String logErrorStartBridge(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("200005", 8, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200005";
   }

   public static String logErrorNoSource(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200008", 8, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200008";
   }

   public static String logErrorNoTarget(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200009", 8, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200009";
   }

   public static String logWarningAdapterNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("200011", 16, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200011";
   }

   public static String logInfoAdaptersFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200012", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200012";
   }

   public static String logErrorInvalidSourceProps(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200013", 8, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200013";
   }

   public static String logErrorInvalidTargetProps(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200014", 8, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200014";
   }

   public static String logErrorProcessMsgs(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("200015", 8, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200015";
   }

   public static String logInfoStopped(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200020", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200020";
   }

   public static String logInfoAdaptersLookupFailed(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("200021", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200021";
   }

   public static String logErrorFailGetAdpInfo(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("200022", 8, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200022";
   }

   public static String logInfoShuttingdown(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200024", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200024";
   }

   public static String logErrorQOSNotAvail(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("200025", 8, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200025";
   }

   public static String logInfoReconnect(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("200026", 16, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200026";
   }

   public static String logInfoAsyncReconnect(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200027", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200027";
   }

   public static String logInfoBeginForwaring(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200028", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200028";
   }

   public static String logInfoWorkMode(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("200030", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200030";
   }

   public static String logInfoQOSDegradationAllowed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200031", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200031";
   }

   public static String logInfoQOSDegradationNotAllowed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200032", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200032";
   }

   public static String logInfoGetConnections(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200033", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200033";
   }

   public static String logInfoShutdown(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200034", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200034";
   }

   public static String logInfoSyncReconnect(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200035", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200035";
   }

   public static String logInfoAttributeStartedChanged(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("200036", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200036";
   }

   public static String logInfoAttributeChanged(String arg0, String arg1, long arg2, long arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("200037", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200037";
   }

   public static String logInfoInitiallyStopped(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200038", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200038";
   }

   public static String logErrorSameSourceTarget(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200039", 8, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200039";
   }

   public static String logErrorInvalidURL(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200040", 8, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200040";
   }

   public static String logErrorNeedsJNDINames(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200041", 8, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200041";
   }

   public static String logErrorFailedToConnectToSource(String arg0, Exception arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("200042", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200042";
   }

   public static String logErrorFailedToConnectToTarget(String arg0, Exception arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("200043", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200043";
   }

   public static String logStackTrace(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200044", 8, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200044";
   }

   public static String logFailedStart(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200045", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200045";
   }

   public static String logInfoStringAttributeChanged(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("200046", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200046";
   }

   public static String logFailedStartMigratable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("200047", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200047";
   }

   public static String logInfoAdapterJNDINameChanged(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("200048", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200048";
   }

   public static String logInfoConfiguredAdapterJNDIName(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("200049", 64, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200049";
   }

   public static String logWarningAdapterIgnored(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("200050", 16, args, BridgeLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BridgeLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "200050";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.jms.BridgeLogLocalizer", BridgeLogger.class.getClassLoader());
      private MessageLogger messageLogger = BridgeLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = BridgeLogger.findMessageLogger();
      }
   }
}
