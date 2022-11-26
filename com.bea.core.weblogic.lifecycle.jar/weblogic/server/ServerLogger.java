package weblogic.server;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class ServerLogger {
   private static final String LOCALIZER_CLASS = "weblogic.server.ServerLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(ServerLogger.class.getName());
   }

   public static String logDebugThread(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002603", 128, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002603";
   }

   public static String logDebugThreadException(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002604", 128, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002604";
   }

   public static String logAddedIPAddress(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002605", 64, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002605";
   }

   public static String logUnableToCreateSocket(String arg0, int arg1, Exception arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("002606", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002606";
   }

   public static String logChannelClosed(String arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("002607", 32, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002607";
   }

   public static String logListenThreadFailure(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002608", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002608";
   }

   public static String logChannelsEnabled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002609", 64, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002609";
   }

   public static String logDynamicListenersEnabled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002610", 64, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002610";
   }

   public static String logHostMapsToMultipleAddress(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002611", 16, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002611";
   }

   public static String logChannelHung(String arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("002612", 32, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002612";
   }

   public static String logChannelOpen(String arg0, int arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("002613", 32, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002613";
   }

   public static String logChannelReopening(String arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("002614", 32, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002614";
   }

   public static String logChannelRestored(String arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("002615", 32, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002615";
   }

   public static String logChannelFailed(String arg0, int arg1, String arg2, int arg3, long arg4, Exception arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("002616", 4, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002616";
   }

   public static String logCloseAndReopenChannel(String arg0, int arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("002617", 4, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002617";
   }

   public static String logProtocolNotConfigured(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002618", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002618";
   }

   public static String logListenPortsNotOpenTotally() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002619", 64, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002619";
   }

   public static String logUnknownHostForChannel(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002620", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002620";
   }

   public static String logConnectionRejectedMaxAddresses(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002621", 32, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002621";
   }

   public static String logProtocolConfigured(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002622", 64, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002622";
   }

   public static String logServerUnavailable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002623", 32, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002623";
   }

   public static Loggable logServerUnavailableLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("002623", 32, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAdminProtocolConfigured(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002624", 64, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002624";
   }

   public static String logChannelConfigurationFailed(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002625", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002625";
   }

   public static String logProtocolNotLoaded(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002626", 16, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002626";
   }

   public static String logAdminChannelConflict() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002627", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002627";
   }

   public static Loggable logAdminChannelConflictLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("002627", 8, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSLCExportFailed(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002628", 4, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002628";
   }

   public static String logLookupSLCOperations(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002629", 128, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002629";
   }

   public static String logAcceptingConnections() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002630", 64, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002630";
   }

   public static String logMaxOpenSockets(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002631", 16, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002631";
   }

   public static String logJRMPEnabled(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002632", 32, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002632";
   }

   public static String logPortConflict(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("002633", 1, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002633";
   }

   public static Loggable logPortConflictLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("002633", 1, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServerDisconnect(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002634", 64, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002634";
   }

   public static String logServerConnect(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002635", 64, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002635";
   }

   public static String logServerUpdateFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002636", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002636";
   }

   public static String logDisconnectRegistrationFailed(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002637", 16, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002637";
   }

   public static String logAdminAddress(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002638", 32, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002638";
   }

   public static String logServerRuntimeError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002639", 64, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002639";
   }

   public static String logForceShuttingDownServer() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002640", 64, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002640";
   }

   public static String logUnknownServerType(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002641", 16, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002641";
   }

   public static String logMissingServiceConfigFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002642", 16, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002642";
   }

   public static String logServiceConfigFileException(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002643", 16, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002643";
   }

   public static String logServicePluginException(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002644", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002644";
   }

   public static String logServicePluginManifestException(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002645", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002645";
   }

   public static String getServicePluginActivatorLoadException(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("002646", 8, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logServicePluginAdded(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002647", 64, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002647";
   }

   public static String logRemoteServerLifeCycleRuntimeNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002648", 16, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002648";
   }

   public static Loggable logRemoteServerLifeCycleRuntimeNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("002648", 16, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPortSDPConflict(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("002649", 1, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002649";
   }

   public static Loggable logPortSDPConflictLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("002649", 1, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logScheduleReconnectFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002650", 64, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002650";
   }

   public static String getServerProcessKillMessage(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002651", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002651";
   }

   public static Loggable getServerProcessKillMessageLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("002651", 8, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getServerNoConfiguredChannel() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002652", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002652";
   }

   public static Loggable getServerNoConfiguredChannelLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("002652", 8, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getServerAdministrationChannelConflictWithAdminserver() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("002653", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002653";
   }

   public static Loggable getServerAdministrationChannelConflictWithAdminserverLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("002653", 8, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getServerInitializationFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002654", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002654";
   }

   public static Loggable getServerInitializationFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("002654", 8, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCannotDynamicallyCreateNewChannel(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002655", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002655";
   }

   public static Loggable getCannotDynamicallyCreateNewChannelLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("002655", 8, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCannotDynamicallyEnableDisableAdminChannel(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002656", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002656";
   }

   public static Loggable getCannotDynamicallyEnableDisableAdminChannelLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("002656", 8, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getCannotChangeMaxOpenSockCountEnablementDynamically(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002657", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002657";
   }

   public static Loggable getCannotChangeMaxOpenSockCountEnablementDynamicallyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("002657", 8, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getNoConfiguredOutboundChannel(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002658", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002658";
   }

   public static Loggable getNoConfiguredOutboundChannelLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("002658", 8, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getOnlyLocalHostIDIsSupported(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002659", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002659";
   }

   public static Loggable getOnlyLocalHostIDIsSupportedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("002659", 8, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getFailedToParseReplicationPorts(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002660", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002660";
   }

   public static Loggable getFailedToParseReplicationPortsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("002660", 8, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getBindFailure() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("002661", 8, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logUncheckedAddress(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002662", 16, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002662";
   }

   public static Loggable logUncheckedAddressLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("002662", 16, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUncaughtThrowable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("002663", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002663";
   }

   public static String getAdminChannelFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("002664", 8, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String warnServerResourceUsedAsPartitionResource(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002665", 16, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002665";
   }

   public static Loggable warnServerResourceUsedAsPartitionResourceLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("002665", 16, args, "weblogic.server.ServerLogLocalizer", ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ServerLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRemoteChannelService(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("002666", 8, args, ServerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ServerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "002666";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.server.ServerLogLocalizer", ServerLogger.class.getClassLoader());
      private MessageLogger messageLogger = ServerLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = ServerLogger.findMessageLogger();
      }
   }
}
