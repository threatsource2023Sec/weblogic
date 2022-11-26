package weblogic.buzzmessagebus;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class BuzzMessageBusLogger {
   private static final String LOCALIZER_CLASS = "weblogic.buzzmessagebus.BuzzMessageBusLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(BuzzMessageBusLogger.class.getName());
   }

   public static String errorRecvDataOnClosedId(String arg0, String arg1, String arg2, String arg3, byte arg4, long arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("2190000", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190000";
   }

   public static String errorRecvDataWithZeroId(String arg0, String arg1, String arg2, String arg3, byte arg4, long arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("2190001", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190001";
   }

   public static String errorRecvInitAfterInit(String arg0, String arg1, int arg2, int arg3, int arg4, int arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("2190002", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190002";
   }

   public static String errorRecvInitFrameTooShort(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2190003", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190003";
   }

   public static String errorRecvInitVersionMismatch(String arg0, String arg1, int arg2, int arg3, int arg4, int arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("2190004", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190004";
   }

   public static String errorRecvMsgBeforeConnectionInitialized(String arg0, String arg1, String arg2, String arg3, byte arg4, long arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("2190005", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190005";
   }

   public static String errorRecvMessageTooShort(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2190006", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190006";
   }

   public static String errorRecvStartOnNonClosedId(String arg0, String arg1, String arg2, byte arg3, long arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("2190007", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190007";
   }

   public static String errorRecvStartOnEndPointInGsdState(String arg0, String arg1, String arg2, byte arg3, long arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("2190008", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190008";
   }

   public static String errorRecvStartWithZeroId(String arg0, String arg1, String arg2, byte arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2190009", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190009";
   }

   public static String errorRecvSubprotocolMismatch(String arg0, String arg1, String arg2, String arg3, byte arg4, long arg5, byte arg6) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
      CatalogMessage catalogMessage = new CatalogMessage("2190010", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190010";
   }

   public static String errorRecvUnknownErrorCode(String arg0, String arg1, String arg2, String arg3, byte arg4, long arg5, String arg6) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
      CatalogMessage catalogMessage = new CatalogMessage("2190011", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190011";
   }

   public static String errorRecvUnknownFrameType(String arg0, String arg1, String arg2, String arg3, byte arg4, long arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("2190012", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190012";
   }

   public static String errorEventInternalExAdminReceiverOpenClose(String arg0, String arg1, String arg2, String arg3, Throwable arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("2190013", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190013";
   }

   public static String errorRecvSendInternalExAdminReceiverGsdAckReceivedDone(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2190014", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190014";
   }

   public static String errorRecvInternalExAdminReceiverGsdAck(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2190015", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190015";
   }

   public static String errorEventInternalExReceiverConnectRelease(String arg0, String arg1, byte arg2, String arg3, String arg4, Throwable arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("2190016", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190016";
   }

   public static String errorEventInternalExReceiverReceipt(String arg0, String arg1, byte arg2, String arg3, String arg4, Throwable arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("2190017", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190017";
   }

   public static String errorEventInternalExReceiverBacklog(String arg0, String arg1, byte arg2, String arg3, boolean arg4, Throwable arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("2190018", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190018";
   }

   public static String errorRecvSentInternalExReceiverGsdAckSentDone(String arg0, String arg1, byte arg2, String arg3, Throwable arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("2190019", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190019";
   }

   public static String errorRecvInternalExReceiver(String arg0, String arg1, String arg2, String arg3, byte arg4, long arg5, String arg6, Throwable arg7) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7};
      CatalogMessage catalogMessage = new CatalogMessage("2190020", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190020";
   }

   public static String errorSendInternalExReceiverGsd(String arg0, byte arg1, String arg2, String arg3, Throwable arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("2190021", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190021";
   }

   public static String errorRecvInternalExReceiverInternal(String arg0, String arg1, String arg2, String arg3, byte arg4, long arg5, String arg6, Throwable arg7) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7};
      CatalogMessage catalogMessage = new CatalogMessage("2190022", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190022";
   }

   public static String errorRecvInternalExReceiverInternalGsd(String arg0, String arg1, byte arg2, String arg3, String arg4, Throwable arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("2190023", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190023";
   }

   public static String errorExInTryRelease(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2190025", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190025";
   }

   public static String errorInternalExEventCollector(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2190024", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190024";
   }

   public static String errorEventInternalNoSubprotocolReceiverForReceiptRDMALeak(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2190026", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190026";
   }

   public static String errorEventInternalReceiverConnectIncorrectEventType(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2190027", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190027";
   }

   public static String errorEventInternalUnknownReceiptTypeRDMALeak(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2190028", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190028";
   }

   public static String errorRecvInternalExReadingBufferSequenceInputStream(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2190029", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190029";
   }

   public static String errorRecvInternalForceCloseIdAuxMsgInfoNullRDMALeak(String arg0, String arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2190030", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190030";
   }

   public static String errorRecvInternalNoConnectionInfo(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2190031", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190031";
   }

   public static String errorRecvInternalNoEndpointMapEntry(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2190032", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190032";
   }

   public static String errorRecvInternalNoSubprotocolReceiver(String arg0, String arg1, String arg2, String arg3, byte arg4, long arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("2190033", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190033";
   }

   public static String errorRecvInternalInitConnectionInWrongState(String arg0, String arg1, int arg2, int arg3, int arg4, int arg5, String arg6) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
      CatalogMessage catalogMessage = new CatalogMessage("2190034", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190034";
   }

   public static String errorRecvInternalInitErrorConnectionInfoShouldNotBeNull(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2190035", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190035";
   }

   public static String errorRecvInternalUnknownMsgIdScopeForRstRDMALeak(String arg0, String arg1, String arg2, byte arg3, long arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("2190036", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190036";
   }

   public static String errorSendInternalIncorrectMsgStateShouldBeHalfClosed(String arg0, String arg1, String arg2, String arg3, byte arg4, long arg5, String arg6) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
      CatalogMessage catalogMessage = new CatalogMessage("2190037", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190037";
   }

   public static String errorSendInternalIncorrectMsgStateShouldBeOpen(String arg0, String arg1, String arg2, String arg3, byte arg4, long arg5, String arg6) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
      CatalogMessage catalogMessage = new CatalogMessage("2190038", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190038";
   }

   public static String errorSendInternalInitExistingEpToConnectionInfoEntry(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2190039", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190039";
   }

   public static String errorSendInternalRst(String arg0, String arg1, byte arg2, long arg3, String arg4, String arg5, Throwable arg6) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
      CatalogMessage catalogMessage = new CatalogMessage("2190040", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190040";
   }

   public static String errorEventInternalExReceiverFlush(String arg0, byte arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2190041", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190041";
   }

   public static String errorInternalCloseTimeout(String arg0, long arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2190042", 4, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190042";
   }

   public static String logDummyBuzzChannelUsage(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2190043", 16, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190043";
   }

   public static String logBuzzMessageBusInitialized(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2190044", 64, args, BuzzMessageBusLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2190044";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.buzzmessagebus.BuzzMessageBusLogLocalizer", BuzzMessageBusLogger.class.getClassLoader());
      private MessageLogger messageLogger = BuzzMessageBusLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = BuzzMessageBusLogger.findMessageLogger();
      }
   }
}
