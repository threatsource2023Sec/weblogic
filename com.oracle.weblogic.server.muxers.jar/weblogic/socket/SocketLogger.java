package weblogic.socket;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class SocketLogger {
   private static final String LOCALIZER_CLASS = "weblogic.socket.SocketLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(SocketLogger.class.getName());
   }

   public static String logDebug(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000400", 128, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000400";
   }

   public static String logSocketQueueFull(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000401", 4, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000401";
   }

   public static String logSocketConfig(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000402", 16, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000402";
   }

   public static String logIOException(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000403", 8, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000403";
   }

   public static String logThreadDeath(ThreadDeath arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000404", 4, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000404";
   }

   public static String logThrowable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000405", 8, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000405";
   }

   public static String logTimeStamp(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000406", 64, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000406";
   }

   public static String logRegisterSocketProblem(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000409", 8, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000409";
   }

   public static String logInitPerf() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000414", 2, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000414";
   }

   public static String logFdLimit(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000415", 64, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000415";
   }

   public static String logFdCurrent(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000416", 64, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000416";
   }

   public static String logUncaughtThrowable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000421", 8, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000421";
   }

   public static String logMuxerError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000429", 8, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000429";
   }

   public static String logDebugThrowable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000430", 128, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000430";
   }

   public static String logInfoAcceptConnection(boolean arg0, String arg1, int arg2, String arg3, int arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("000431", 64, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000431";
   }

   public static String logNativeMuxerError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000432", 8, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000432";
   }

   public static String logPosixMuxerMaxFdExceededError(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000435", 8, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000435";
   }

   public static String logAllocSocketReaders(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000436", 64, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000436";
   }

   public static String logMuxerUnsatisfiedLinkError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000438", 8, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000438";
   }

   public static String logJavaMuxerCreationError2() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000439", 8, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000439";
   }

   public static String logNTMuxerInitiateIOError(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000440", 8, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000440";
   }

   public static String logNTMuxerSocketInfoNotFound(String arg0, boolean arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000441", 16, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000441";
   }

   public static String logConnectionRejected(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000442", 16, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000442";
   }

   public static String logConnectionRejectedProtocol(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000443", 16, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000443";
   }

   public static String logNativeDevPollMuxerError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000444", 16, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000444";
   }

   public static String logConnectionRejectedFilterEx(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000445", 32, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000445";
   }

   public static Loggable logConnectionRejectedFilterExLoggable(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("000445", 32, args, "weblogic.socket.SocketLogLocalizer", SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger, SocketLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNativeIOEnabled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000446", 64, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000446";
   }

   public static String logNativeIODisabled() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000447", 64, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000447";
   }

   public static String logNoSocketChannelSupportForVM() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000448", 8, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000448";
   }

   public static String logSocketIdleTimeout(long arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000449", 16, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000449";
   }

   public static String logSocketInfoNotFound(int arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000450", 128, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000450";
   }

   public static String logONSSocketEndOfStream(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000451", 16, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000451";
   }

   public static String logONSSocketHasException(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000452", 16, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000452";
   }

   public static String logONSDeliverMessageException(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000453", 8, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000453";
   }

   public static String logONSHandShakeException(String arg0, int arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("000454", 8, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000454";
   }

   public static String logUnregisteredHandshakeCompletedListener(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000455", 8, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000455";
   }

   public static String logNoMuxerSpecified(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000437", 64, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000437";
   }

   public static String logNativeMuxerError2() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("000456", 8, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000456";
   }

   public static String logNIOSelectorError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000457", 16, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000457";
   }

   public static String logNIOSelectorMsg(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("000458", 16, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000458";
   }

   public static String logSocketMarkedCloseOnly(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("000459", 128, args, SocketLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      SocketLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "000459";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.socket.SocketLogLocalizer", SocketLogger.class.getClassLoader());
      private MessageLogger messageLogger = SocketLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = SocketLogger.findMessageLogger();
      }
   }
}
