package com.oracle.buzzmessagebus.api;

import com.oracle.common.io.BufferSequence;
import com.oracle.common.io.Buffers;
import com.oracle.common.net.exabus.EndPoint;
import com.oracle.common.net.exabus.Event;
import com.oracle.common.net.exabus.MessageBus;
import java.util.concurrent.TimeUnit;

public abstract class BuzzLoggerAbstractBase implements BuzzLogger {
   protected String buzzMessageBusName;
   protected final String s_flags = " flags: %04X";
   protected final String s_subprotocol = " subprotocol: %d";
   protected final String s_messageId = " messageId: %d";
   protected final String s_errorCode = " errorCode: %X";
   protected final String s_subprotocol_messageId = " subprotocol: %d messageId: %d";
   protected final String s_flags_subprotocol_messageId = " flags: %04X subprotocol: %d messageId: %d";
   protected final String s_flags_subprotocol_messageId_errorCode = " flags: %04X subprotocol: %d messageId: %d errorCode: %X";
   protected final String s_subprotocol_messageId_errorCode = " subprotocol: %d messageId: %d errorCode: %X";
   protected final String s_peerMin_peerMax_localMin_localMax = " peer: %d/%d local: %d/%d";

   public abstract void error(String var1);

   public abstract void error(String var1, Throwable var2);

   public abstract void info(String var1);

   public abstract boolean infoEnabled();

   private boolean ie() {
      return this.infoEnabled();
   }

   public abstract boolean msgDumpEnabled();

   public void setBuzzMessageBusName(String x) {
      this.buzzMessageBusName = x;
   }

   public void errorTodo(EndPoint endPoint, String msg) {
      if (this.ie()) {
         this.error(this.b("TODO", endPoint) + " " + msg);
      }

   }

   public void infoTodo(EndPoint endPoint, String msg) {
      if (this.ie()) {
         this.info(this.b("TODO", endPoint) + " " + msg);
      }

   }

   public void errorRecvDataOnClosedId(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId) {
      this.error(this.b("ERROR DATA recv on CLOSED messageId", endPoint) + this.f_frameType_flags_subprotocol_messageId(frameType, flags, subprotocol, messageId));
   }

   public void errorRecvDataWithZeroId(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId) {
      this.error(this.b("ERROR DATA recv with messageId zero", endPoint) + this.f_frameType_flags_subprotocol_messageId(frameType, flags, subprotocol, messageId));
   }

   public void errorRecvInitAfterInit(EndPoint endPoint, int peerMin, int peerMax, int localMin, int localMax) {
      this.error(this.b("ERROR INIT recv after INIT, releasing endpoint", endPoint) + this.f_peerMin_peerMax_localMin_localMax(peerMin, peerMax, localMin, localMax));
   }

   public void errorRecvInitFrameTooShort(EndPoint endPoint) {
      this.error(this.b("ERROR INIT recv frame too short", endPoint));
   }

   public void errorRecvInitVersionMismatch(EndPoint endPoint, int peerMin, int peerMax, int localMin, int localMax) {
      this.error(this.b("ERROR INIT recv version mismatch", endPoint) + this.f_peerMin_peerMax_localMin_localMax(peerMin, peerMax, localMin, localMax));
   }

   public void errorRecvMsgBeforeConnectionInitialized(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId) {
      this.error(this.b("ERROR message received before connection initialized", endPoint) + this.f_frameType_flags_subprotocol_messageId(frameType, flags, subprotocol, messageId));
   }

   public void errorRecvMsgTooShort(EndPoint endPoint, BufferSequence request) {
      this.error(this.b("ERROR recv message too short", endPoint));
   }

   public void errorRecvStartOnNonClosedId(EndPoint endPoint, short flags, byte subprotocol, long messageId) {
      this.error(this.b("ERROR START recv for existing messageId", endPoint) + this.f_flags_subprotocol_messageId(flags, subprotocol, messageId));
   }

   public void errorRecvStartWithZeroId(EndPoint endPoint, short flags, byte subprotocol) {
      this.error(this.b("ERROR START recv with messageId zero", endPoint) + this.f_flags_subprotocol_messageId(flags, subprotocol, 0L));
   }

   public void errorRecvSubprotocolMismatch(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId, byte subprotocolReceiverId) {
      this.error(this.b("ERROR recv subprotocol mismatch", endPoint) + this.f_frameType_flags_subprotocol_messageId(frameType, flags, subprotocol, messageId) + this.f_label("subprotocol of previous message", subprotocolReceiverId));
   }

   public void errorRecvUnknownErrorCode(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId, int errorCode) {
      this.error(this.b("ERROR recv unknown error code", endPoint) + this.f_frameType_flags_subprotocol_messageId(frameType, flags, subprotocol, messageId) + this.f_errorCode(errorCode));
   }

   public void errorRecvUnknownFrameType(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId) {
      this.error(this.b("ERROR recv unknown frame type", endPoint) + this.f_frameType_flags_subprotocol_messageId(frameType, flags, subprotocol, messageId));
   }

   public void errorEventInternalExAdminReceiverOpenClose(EndPoint endPoint, BuzzAdminReceiver r, Event.Type eventType, Throwable t) {
      this.error(this.b("ERROR INTERNAL open or close admin receiver threw exception (ignored)", endPoint) + this.f_adminReceiver(r) + this.f_eventType(eventType), t);
   }

   public void errorEventInternalExReceiverConnectRelease(EndPoint endPoint, BuzzSubprotocolReceiver subprotocolReceiver, Event.Type eventType, Throwable t) {
      this.error(this.b("ERROR INTERNAL connect or release receiver threw exception (ignored)", endPoint) + this.f_subprotocol(subprotocolReceiver.getSubprotocolId()) + this.f_subprotocolReceiver(subprotocolReceiver) + this.f_eventType(eventType), t);
   }

   public void errorEventInternalExReceiverFlush(BuzzSubprotocolReceiver subprotocolReceiver, Throwable t) {
      this.error(this.b("ERROR INTERNAL flush receiver threw exception (ignored)", (EndPoint)null) + this.f_subprotocol(subprotocolReceiver.getSubprotocolId()) + this.f_subprotocolReceiver(subprotocolReceiver), t);
   }

   public void errorEventInternalExReceiverReceipt(EndPoint endPoint, BuzzSubprotocolReceiver subprotocolReceiver, Object receiptCookie, Throwable t) {
      this.error(this.b("ERROR INTERNAL receipt receiver threw exception (ignored)", endPoint) + this.f_subprotocol(subprotocolReceiver.getSubprotocolId()) + this.f_subprotocolReceiver(subprotocolReceiver) + this.f_receiptCookie(receiptCookie), t);
   }

   public void errorEventInternalExReceiverBacklog(EndPoint endPoint, BuzzSubprotocolReceiver subprotocolReceiver, boolean isExcessive, Throwable t) {
      this.error(this.b("ERROR INTERNAL backlog receiver threw exception (ignored)", endPoint) + this.f_subprotocol(subprotocolReceiver.getSubprotocolId()) + this.f_subprotocolReceiver(subprotocolReceiver) + this.f_isExcessive(isExcessive), t);
   }

   public void errorRecvInternalExReceiver(EndPoint endPoint, byte frameType, short flags, long messageId, BuzzSubprotocolReceiver subprotocolReceiver, BuzzMessageToken buzzMessageToken, Throwable t) {
      byte subprotocol = subprotocolReceiver.getSubprotocolId();
      this.error(this.b("ERROR INTERNAL subprotocol receiver threw exception (ignored)", endPoint) + this.f_frameType_flags_subprotocol_messageId(frameType, flags, subprotocol, messageId), t);
   }

   public void errorRecvInternalExReceiverInternal(EndPoint endPoint, byte frameType, short flags, long messageId, BuzzSubprotocolReceiver subprotocolReceiver, BuzzMessageToken buzzMessageToken, Throwable t) {
      byte subprotocol = subprotocolReceiver.getSubprotocolId();
      this.error(this.b("ERROR INTERNAL subprotocol internal receiver threw exception (ignored)", endPoint) + this.f_frameType_flags_subprotocol_messageId(frameType, flags, subprotocol, messageId), t);
   }

   public void errorInternalExEventCollector(Event event, Throwable t) {
      this.error(this.b("ERROR INTERNAL EventCollector exception (ignored)", event.getEndPoint()), t);
   }

   public void errorExInTryRelease(EndPoint endPoint, String msg, Throwable t) {
      this.error(this.b("ERROR exception in tryRelase (ignored)", endPoint) + this.f_extra(msg), t);
   }

   public void errorInternalCloseTimeout(long closeWaitTimeout, TimeUnit closeWaitTimeUnit) {
      this.error(this.b("ERROR close timed out", (EndPoint)null) + this.f_extra("waited for " + closeWaitTimeout + " " + closeWaitTimeUnit));
   }

   public void errorEventInternalNoSubprotocolReceiverForReceiptRDMALeak(EndPoint endPoint, Object cookie) {
      this.error(this.b("ERROR INTERNAL POTENTIAL RDMA MEMORY LEAK no subprotocol receiver for receipt cookie", endPoint) + this.f_receiptCookie(cookie));
   }

   public void errorEventInternalReceiverConnectIncorrectEventType(EndPoint endPoint, Event.Type eventType) {
      this.error(this.b("ERROR INTERNAL incorrect event type in connect release receiver", endPoint) + this.f_eventType(eventType));
   }

   public void errorEventInternalUnknownReceiptTypeRDMALeak(EndPoint endPoint, Object receipt) {
      this.error(this.b("ERROR INTERNAL POTENTIAL RDMA MEMORY LEAK unknown receipt type", endPoint) + this.f_receiptCookie(receipt));
   }

   public void errorRecvInternalExReadingBufferSequenceInputStream(EndPoint endPoint, BufferSequence bs, Throwable t) {
      this.error(this.b("ERROR INTERNAL recv exception reading BufferSequenceInputStream", endPoint) + this.f_bytes(bs), t);
   }

   public void errorRecvInternalForceCloseIdAuxMsgInfoNullRDMALeak(EndPoint endPoint, long messageId) {
      this.error(this.b("ERROR INTERNAL recv POTENTIAL RDMA MEMORY LEAK forceCloseIdAux message info is null", endPoint) + this.f_messageId(messageId));
   }

   public void errorRecvInternalNoConnectionInfo(EndPoint endPoint) {
      this.error(this.b("ERROR INTERNAL recv no ConnectionInfo", endPoint));
   }

   public void errorRecvInternalNoEndpointMapEntry(EndPoint endPoint, BufferSequence request) {
      this.error(this.b("ERROR INTERNAL recv no epToMessageMap entry", endPoint));
   }

   public void errorRecvInternalNoSubprotocolReceiver(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId) {
      this.error(this.b("ERROR INTERNAL recv no subprotocol receiver", endPoint) + this.f_frameType_flags_subprotocol_messageId(frameType, flags, subprotocol, messageId));
   }

   public void errorRecvInternalInitConnectionInWrongState(EndPoint endPoint, int peerMin, int peerMax, int localMin, int localMax, String connectionState) {
      this.error(this.b("ERROR INTERNAL INIT recv connection in wrong state, should be CONNECTED", endPoint) + this.f_peerMin_peerMax_localMin_localMax(peerMin, peerMax, localMin, localMax) + this.f_connectionState(connectionState));
   }

   public void errorRecvInternalInitErrorConnectionInfoShouldNotBeNull(EndPoint endPoint) {
      this.error(this.b("ERROR INTERNAL INIT recv error ConnectionInfo should not be null", endPoint));
   }

   public void errorRecvInternalUnknownMsgIdScopeForRstRDMALeak(EndPoint endPoint, short flags, byte subprotocol, long messageId, int errorCode) {
      this.error(this.b("ERROR INTERNAL POTENTIAL RDMA MEMORY LEAK recv unknown messageId scope for RST", endPoint) + this.f_flags_subprotocol_messageId_errorCode(flags, subprotocol, messageId, errorCode));
   }

   public void errorSendInternalIncorrectMsgStateShouldBeHalfClosed(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId, BuzzTypes.BuzzMessageState state) {
      this.error(this.b("ERROR INTERNAL send incorrect message state - should be HALF_CLOSED", endPoint) + this.f_frameType_flags_subprotocol_messageId(frameType, flags, subprotocol, messageId) + this.f_messageState(state));
   }

   public void errorSendInternalIncorrectMsgStateShouldBeOpen(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId, BuzzTypes.BuzzMessageState state) {
      this.error(this.b("ERROR INTERNAL send incorrect message state - should be OPEN", endPoint) + this.f_frameType_flags_subprotocol_messageId(frameType, flags, subprotocol, messageId) + this.f_messageState(state));
   }

   public void errorSendInternalInitExistingEpToConnectionInfoEntry(EndPoint endPoint) {
      this.error(this.b("ERROR INTERNAL send existing endPoint to ConnectionInfo during INIT", endPoint));
   }

   public void errorSendInternalRst(EndPoint endPoint, byte subprotocol, long messageId, int errorCode, String msg, Throwable t) {
      this.error(this.b("ERROR INTERNAL send exception when sending RST", endPoint) + this.f_subprotocol_messageId_errorCode(subprotocol, messageId, errorCode) + this.f_extra(msg), t);
   }

   public void infoEventBacklogExcessive(Event event) {
      if (this.ie()) {
         this.info(this.b("BACKLOG_EXCESSIVE", event.getEndPoint()));
      }

   }

   public void infoEventBacklogNormal(Event event) {
      if (this.ie()) {
         this.info(this.b("BACKLOG_NORMAL", event.getEndPoint()));
      }

   }

   public void infoEventClose(Event event) {
      if (this.ie()) {
         this.info(this.b("CLOSE", event.getEndPoint()));
      }

   }

   public void infoEventConnect(Event event) {
      if (this.ie()) {
         this.info(this.b("CONNECT", event.getEndPoint()));
      }

   }

   public void infoEventDisconnect(Event event) {
      if (this.ie()) {
         this.info(this.b("DISCONNECT", event.getEndPoint()));
      }

   }

   public void infoEventMessage(Event event, EndPoint endPoint, BufferSequence msg) {
      if (this.ie()) {
         this.info(this.b("MESSAGE", endPoint) + this.f_bytes(msg));
      }

   }

   public void infoEventOpen(Event event) {
      if (this.ie()) {
         this.info(this.b("OPEN", event.getEndPoint()));
      }

   }

   public void infoEventReceipt(Event event) {
      if (this.ie()) {
         this.info(this.b("RECEIPT", event.getEndPoint()));
      }

   }

   public void infoEventRelease(Event event) {
      if (this.ie()) {
         this.info(this.b("RELEASE", event.getEndPoint()));
      }

   }

   public void infoEventSignal(Event event) {
      if (this.ie()) {
         this.info(this.b("SIGNAL", event.getEndPoint()));
      }

   }

   public void infoBuzzClosed(MessageBus bus) {
      if (this.ie()) {
         this.info(this.b("Buzz closed", bus.getLocalEndPoint()));
      }

   }

   public void infoBuzzClosing(MessageBus bus) {
      if (this.ie()) {
         this.info(this.b("Buzz closing", bus.getLocalEndPoint()));
      }

   }

   public void infoBuzzOpened(MessageBus bus) {
      if (this.ie()) {
         this.info(this.b("Buzz Open on", bus.getLocalEndPoint()));
      }

   }

   public void infoExCloseInterruptedWhileWaitingForCloseEvent(EndPoint endPoint, InterruptedException e) {
      if (this.ie()) {
         this.info(this.b("Buzz.close interrupted while waiting for close event", endPoint) + this.f_label("exception", e));
      }

   }

   public void infoReleaseEndPoint(EndPoint endPoint, String msg) {
      if (this.ie()) {
         this.info(this.b("bus.releaseEndPoint", endPoint) + this.f_extra(msg));
      }

   }

   public void infoEventDisposeReceipt(EndPoint endPoint, String msg) {
      if (this.ie()) {
         this.info(this.b("DISPOSE RECEIPT " + msg, endPoint));
      }

   }

   public void infoRecvDisposeInit(EndPoint endPoint) {
      if (this.ie()) {
         this.info(this.b("DISPOSE INIT recv", endPoint));
      }

   }

   public void infoRecvDisposeRst(EndPoint endPoint, long messageId, int errorCode) {
      if (this.ie()) {
         this.info(this.b("DISPOSE RST recv", endPoint) + this.f_messageId(messageId) + this.f_errorCode(errorCode));
      }

   }

   public void infoSendDisposeCloseId(EndPoint endPoint, byte subprotocol, long messageId, int mineOrYours, String extra) {
      if (this.ie()) {
         this.info(this.b("DISPOSE send close*Id", endPoint) + this.f_subprotocol_messageId(subprotocol, messageId) + this.f_mineOrYours(mineOrYours) + this.f_extra(extra));
      }

   }

   public void infoSendDisposeInitReceipt(EndPoint endPoint) {
      if (this.ie()) {
         this.info(this.b("DISPOSE INIT send receipt", endPoint));
      }

   }

   public void infoRecvDisposeErrData(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId, String msg) {
      if (this.ie()) {
         this.info(this.b("DISPOSE Data recv error", endPoint) + this.f_frameType_flags_subprotocol_messageId(frameType, flags, subprotocol, messageId) + this.f_extra(msg));
      }

   }

   public void infoRecvDisposeErrNotInitialized(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId) {
      if (this.ie()) {
         this.info(this.b("DISPOSE recv not initialized", endPoint) + this.f_frameType_flags_subprotocol_messageId(frameType, flags, subprotocol, messageId));
      }

   }

   public void infoRecvDisposeErrNoReceiver(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId, String msg) {
      if (this.ie()) {
         this.info(this.b("DISPOSE no receiver", endPoint) + this.f_frameType_flags_subprotocol_messageId(frameType, flags, subprotocol, messageId) + this.f_extra(msg));
      }

   }

   public void infoRecvDisposeErrStartId(EndPoint endPoint, short flags, byte subprotocol, long messageId, String extra) {
      if (this.ie()) {
         this.info(this.b("DISPOSE START recv error", endPoint) + this.f_flags_subprotocol_messageId(flags, subprotocol, messageId) + this.f_extra(extra));
      }

   }

   public void infoRecvDisposeErrUnknownErrorCode(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId, int errorCode) {
      if (this.ie()) {
         this.info(this.b("DISPOSE recv UnknownErrorCode", endPoint) + this.f_frameType_flags_subprotocol_messageId(frameType, flags, subprotocol, messageId) + this.f_errorCode(errorCode));
      }

   }

   public void infoRecvDisposeErrUnknownFrameType(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId) {
      if (this.ie()) {
         this.info(this.b("DISPOSE recv UnknownFrameType", endPoint) + this.f_frameType_flags_subprotocol_messageId(frameType, flags, subprotocol, messageId));
      }

   }

   public void infoRecvDisposeExReceiver(EndPoint endPoint, long messageId) {
      if (this.ie()) {
         this.info(this.b("DISPOSE exception in receiver", endPoint) + this.f_messageId(messageId));
      }

   }

   public void infoSendDisposeEx(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId, String msg) {
      if (this.ie()) {
         this.info(this.b("DISPOSE exception sendInternal", endPoint) + this.f_frameType_flags_subprotocol_messageId(frameType, flags, subprotocol, messageId) + this.f_extra(msg));
      }

   }

   public void infoSendDisposeExInit(EndPoint endPoint) {
      if (this.ie()) {
         this.info(this.b("DISPOSE exception sendInitFrame", endPoint));
      }

   }

   public void infoSendDisposeExRst(EndPoint endPoint, byte subprotocol, long messageId, int errorCode, String msg, Throwable t) {
      if (this.ie()) {
         this.info(this.b("DISPOSE exception in RST", endPoint) + this.f_subprotocol_messageId(subprotocol, messageId) + this.f_extra(msg) + this.f_label("exception", t));
      }

   }

   public void infoRecv(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId, BufferSequence bytes) {
      if (this.ie()) {
         this.info(this.b(this.frm2s(frameType) + " recv", endPoint) + this.f_flags_subprotocol_messageId(flags, subprotocol, messageId) + this.f_bytes(bytes));
      }

   }

   public void infoRecvInit(EndPoint endPoint, int peerMin, int peerMax, int localMin, int localMax) {
      if (this.ie()) {
         this.info(this.b("INIT recv", endPoint) + this.f_peerMin_peerMax_localMin_localMax(peerMin, peerMax, localMin, localMax));
      }

   }

   public void infoRecvRequestData(EndPoint endPoint, short flags, byte subprotocol, long messageId) {
      if (this.ie()) {
         this.info(this.b("REQUEST_DATA recv", endPoint) + this.f_flags_subprotocol_messageId(flags, subprotocol, messageId));
      }

   }

   public void infoRecvResponseData(EndPoint endPoint, short flags, byte subprotocol, long messageId) {
      if (this.ie()) {
         this.info(this.b("RESPONSE_DATA recv", endPoint) + this.f_flags_subprotocol_messageId(flags, subprotocol, messageId));
      }

   }

   public void infoRecvRst(EndPoint endPoint, byte subprotocol, long messageId, int errorCode) {
      if (this.ie()) {
         this.info(this.b("RST recv", endPoint) + this.f_subprotocol_messageId_errorCode(subprotocol, messageId, errorCode));
      }

   }

   public void infoRecvStart(EndPoint endPoint, short flags, byte subprotocol, long messageId) {
      if (this.ie()) {
         this.info(this.b("START recv", endPoint) + this.f_flags_subprotocol_messageId(flags, subprotocol, messageId));
      }

   }

   public void infoSend(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId, BufferSequence bytes, Object optionalReceiptCookie) {
      if (this.ie()) {
         this.info(this.b(this.frm2s(frameType) + " send", endPoint) + this.f_flags_subprotocol_messageId(flags, subprotocol, messageId) + this.f_bytes(bytes));
      }

   }

   public void infoSent(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId, BufferSequence bytes, Object optionalReceiptCookie) {
      if (this.ie()) {
         this.info(this.b(this.frm2s(frameType) + " sent", endPoint) + this.f_flags_subprotocol_messageId(flags, subprotocol, messageId));
      }

   }

   public void infoSendRst(EndPoint endPoint, byte subprotocol, long messageId, int errorCode, String msg) {
      if (this.ie()) {
         this.info(this.b("RST send", endPoint) + this.f_subprotocol_messageId_errorCode(subprotocol, messageId, errorCode) + this.f_extra(msg));
      }

   }

   protected String flg2s(short x) {
      return String.format("%04X", x);
   }

   protected String ec2s(int x) {
      return String.format("%X", x);
   }

   protected String f_frameType(byte frameType) {
      return " frameType: " + this.frm2s(frameType);
   }

   protected String f_frameType_flags_subprotocol_messageId(byte frameType, short flags, byte subprotocol, long messageId) {
      return this.f_frameType(frameType) + this.f_flags_subprotocol_messageId(flags, subprotocol, messageId);
   }

   protected String f_flags_subprotocol_messageId(short flags, byte subprotocol, long messageId) {
      return String.format(" flags: %04X subprotocol: %d messageId: %d", flags, subprotocol, messageId);
   }

   protected String f_subprotocol_messageId(byte subprotocol, long messageId) {
      return String.format(" subprotocol: %d messageId: %d", subprotocol, messageId);
   }

   protected String f_subprotocol_messageId_errorCode(byte subprotocol, long messageId, int errorCode) {
      return String.format(" subprotocol: %d messageId: %d errorCode: %X", subprotocol, messageId, errorCode);
   }

   protected String f_flags_subprotocol_messageId_errorCode(short flags, byte subprotocol, long messageId, int errorCode) {
      return String.format(" flags: %04X subprotocol: %d messageId: %d errorCode: %X", flags, subprotocol, messageId, errorCode);
   }

   protected String f_subprotocol(byte subprotocol) {
      return String.format(" subprotocol: %d", subprotocol);
   }

   protected String f_messageId(long messageId) {
      return String.format(" messageId: %d", messageId);
   }

   protected String f_errorCode(int errorCode) {
      return String.format(" errorCode: %X", errorCode);
   }

   protected String f_peerMin_peerMax_localMin_localMax(int peerMin, int peerMax, int localMin, int localMax) {
      return String.format(" peer: %d/%d local: %d/%d", peerMin, peerMax, localMin, localMax);
   }

   protected String f_label(String l, Object o) {
      return String.format(" %s: %s", l, o);
   }

   protected String f_adminReceiver(BuzzAdminReceiver x) {
      return this.f_label("adminReceiver", x);
   }

   protected String f_bytes(BufferSequence x) {
      return this.msgDumpEnabled() ? this.f_label("bytes", Buffers.toString(x)) : "";
   }

   protected String f_connectionState(String x) {
      return this.f_label("connectionState", x);
   }

   protected String f_eventType(Event.Type x) {
      return this.f_label("eventType", x);
   }

   protected String f_extra(String x) {
      return x == null ? "" : this.f_label("extra", x);
   }

   protected String f_isExcessive(boolean x) {
      return this.f_label("isExcessive", x);
   }

   protected String f_messageState(BuzzTypes.BuzzMessageState x) {
      return this.f_label("messageState", x);
   }

   protected String f_receiptCookie(Object x) {
      return this.f_label("receiptCookie", x);
   }

   protected String f_subprotocolReceiver(BuzzSubprotocolReceiver x) {
      return this.f_label("subprotocolReceiver", x);
   }

   protected String f_mineOrYours(int mineOrYours) {
      return this.f_label("mineOrYours", mineOrYours == 8 ? "BUZZ_CLOSE_MY_ID" : "BUZZ_CLOSE_YOUR_ID");
   }

   protected String b(String msg, EndPoint endPoint) {
      return this.buzzMessageBusName + " " + msg + " " + (endPoint == null ? "" : endPoint.toString());
   }

   protected String frm2s(byte frameType) {
      switch (frameType) {
         case -3:
            return "RESPONSE_DATA";
         case -2:
            return "INIT";
         case -1:
            return "START";
         case 0:
            return "REQUEST_DATA";
         case 1:
         case 2:
         default:
            return String.format("%02X", frameType);
         case 3:
            return "RST";
      }
   }
}
