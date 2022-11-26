package weblogic.buzzmessagebus;

import com.oracle.buzzmessagebus.api.BuzzAdminReceiver;
import com.oracle.buzzmessagebus.api.BuzzLoggerAbstractBase;
import com.oracle.buzzmessagebus.api.BuzzMessageToken;
import com.oracle.buzzmessagebus.api.BuzzSubprotocolReceiver;
import com.oracle.buzzmessagebus.api.BuzzTypes;
import com.oracle.common.io.BufferSequence;
import com.oracle.common.net.exabus.EndPoint;
import com.oracle.common.net.exabus.Event;
import java.util.concurrent.TimeUnit;
import weblogic.diagnostics.debug.DebugLogger;

public final class BuzzLoggerWLSImpl extends BuzzLoggerAbstractBase {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugBuzzProtocol");
   private static final DebugLogger detailDebugLogger = DebugLogger.getDebugLogger("DebugBuzzProtocolDetails");

   public void info(String msg) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(msg);
      }

   }

   public void error(String msg) {
      throw new UnsupportedOperationException();
   }

   public void error(String msg, Throwable t) {
      throw new UnsupportedOperationException();
   }

   public boolean infoEnabled() {
      return debugLogger.isDebugEnabled();
   }

   public boolean msgDumpEnabled() {
      return detailDebugLogger.isDebugEnabled();
   }

   public void errorTodo(EndPoint endPoint, String msg) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.b("TODO", endPoint) + " " + msg);
      }

   }

   public void errorRecvDataOnClosedId(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId) {
      BuzzMessageBusLogger.errorRecvDataOnClosedId(this.buzzMessageBusName, endPoint.getCanonicalName(), this.frm2s(frameType), this.flg2s(flags), subprotocol, messageId);
   }

   public void errorRecvDataWithZeroId(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId) {
      BuzzMessageBusLogger.errorRecvDataWithZeroId(this.buzzMessageBusName, endPoint.getCanonicalName(), this.frm2s(frameType), this.flg2s(flags), subprotocol, messageId);
   }

   public void errorRecvInitAfterInit(EndPoint endPoint, int peerMin, int peerMax, int localMin, int localMax) {
      BuzzMessageBusLogger.errorRecvInitAfterInit(this.buzzMessageBusName, endPoint.getCanonicalName(), peerMin, peerMax, localMin, localMax);
   }

   public void errorRecvInitFrameTooShort(EndPoint endPoint) {
      BuzzMessageBusLogger.errorRecvInitFrameTooShort(this.buzzMessageBusName, endPoint.getCanonicalName());
   }

   public void errorRecvInitVersionMismatch(EndPoint endPoint, int peerMin, int peerMax, int localMin, int localMax) {
      BuzzMessageBusLogger.errorRecvInitVersionMismatch(this.buzzMessageBusName, endPoint.getCanonicalName(), peerMin, peerMax, localMin, localMax);
   }

   public void errorRecvMsgBeforeConnectionInitialized(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId) {
      BuzzMessageBusLogger.errorRecvMsgBeforeConnectionInitialized(this.buzzMessageBusName, endPoint.getCanonicalName(), this.frm2s(frameType), this.flg2s(flags), subprotocol, messageId);
   }

   public void errorRecvMsgTooShort(EndPoint endPoint, BufferSequence request) {
      BuzzMessageBusLogger.errorRecvMessageTooShort(this.buzzMessageBusName, endPoint.getCanonicalName(), request.toString());
   }

   public void errorRecvStartOnNonClosedId(EndPoint endPoint, short flags, byte subprotocol, long messageId) {
      BuzzMessageBusLogger.errorRecvStartOnNonClosedId(this.buzzMessageBusName, endPoint.getCanonicalName(), this.flg2s(flags), subprotocol, messageId);
   }

   public void errorRecvStartWithZeroId(EndPoint endPoint, short flags, byte subprotocol) {
      BuzzMessageBusLogger.errorRecvStartWithZeroId(this.buzzMessageBusName, endPoint.getCanonicalName(), this.flg2s(flags), subprotocol);
   }

   public void errorRecvSubprotocolMismatch(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId, byte subprotocolReceiverId) {
      BuzzMessageBusLogger.errorRecvSubprotocolMismatch(this.buzzMessageBusName, endPoint.getCanonicalName(), this.frm2s(frameType), this.flg2s(flags), subprotocol, messageId, subprotocolReceiverId);
   }

   public void errorRecvUnknownErrorCode(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId, int errorCode) {
      BuzzMessageBusLogger.errorRecvUnknownErrorCode(this.buzzMessageBusName, endPoint.getCanonicalName(), this.frm2s(frameType), this.flg2s(flags), subprotocol, messageId, this.ec2s(errorCode));
   }

   public void errorRecvUnknownFrameType(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId) {
      BuzzMessageBusLogger.errorRecvUnknownFrameType(this.buzzMessageBusName, endPoint.getCanonicalName(), this.frm2s(frameType), this.flg2s(flags), subprotocol, messageId);
   }

   public void errorEventInternalExAdminReceiverOpenClose(EndPoint endPoint, BuzzAdminReceiver adminReceiver, Event.Type eventType, Throwable t) {
      BuzzMessageBusLogger.errorEventInternalExAdminReceiverOpenClose(this.buzzMessageBusName, endPoint.getCanonicalName(), adminReceiver.toString(), eventType.toString(), t);
   }

   public void errorEventInternalExReceiverConnectRelease(EndPoint endPoint, BuzzSubprotocolReceiver subprotocolReceiver, Event.Type eventType, Throwable t) {
      BuzzMessageBusLogger.errorEventInternalExReceiverConnectRelease(this.buzzMessageBusName, endPoint.getCanonicalName(), subprotocolReceiver.getSubprotocolId(), subprotocolReceiver.toString(), eventType.toString(), t);
   }

   public void errorEventInternalExReceiverFlush(BuzzSubprotocolReceiver subprotocolReceiver, Throwable t) {
      BuzzMessageBusLogger.errorEventInternalExReceiverFlush(this.buzzMessageBusName, subprotocolReceiver.getSubprotocolId(), subprotocolReceiver.toString(), t);
   }

   public void errorEventInternalExReceiverReceipt(EndPoint endPoint, BuzzSubprotocolReceiver subprotocolReceiver, Object receiptCookie, Throwable t) {
      BuzzMessageBusLogger.errorEventInternalExReceiverReceipt(this.buzzMessageBusName, endPoint.getCanonicalName(), subprotocolReceiver.getSubprotocolId(), subprotocolReceiver.toString(), receiptCookie.toString(), t);
   }

   public void errorEventInternalExReceiverBacklog(EndPoint endPoint, BuzzSubprotocolReceiver subprotocolReceiver, boolean isExcessive, Throwable t) {
      BuzzMessageBusLogger.errorEventInternalExReceiverBacklog(this.buzzMessageBusName, endPoint.getCanonicalName(), subprotocolReceiver.getSubprotocolId(), subprotocolReceiver.toString(), isExcessive, t);
   }

   public void errorRecvInternalExReceiver(EndPoint endPoint, byte frameType, short flags, long messageId, BuzzSubprotocolReceiver subprotocolReceiver, BuzzMessageToken buzzMessageToken, Throwable t) {
      BuzzMessageBusLogger.errorRecvInternalExReceiver(this.buzzMessageBusName, endPoint.getCanonicalName(), this.frm2s(frameType), this.flg2s(flags), subprotocolReceiver.getSubprotocolId(), messageId, subprotocolReceiver.toString(), t);
   }

   public void errorRecvInternalExReceiverInternal(EndPoint endPoint, byte frameType, short flags, long messageId, BuzzSubprotocolReceiver subprotocolReceiver, BuzzMessageToken buzzMessageToken, Throwable t) {
      BuzzMessageBusLogger.errorRecvInternalExReceiverInternal(this.buzzMessageBusName, endPoint.getCanonicalName(), this.frm2s(frameType), this.flg2s(flags), subprotocolReceiver.getSubprotocolId(), messageId, subprotocolReceiver.toString(), t);
   }

   public void errorExInTryRelease(EndPoint endPoint, String msg, Throwable t) {
      BuzzMessageBusLogger.errorExInTryRelease(this.buzzMessageBusName, endPoint.getCanonicalName(), msg, t);
   }

   public void errorInternalExEventCollector(Event event, Throwable t) {
      BuzzMessageBusLogger.errorInternalExEventCollector(this.buzzMessageBusName, event.getEndPoint().getCanonicalName(), t);
   }

   public void errorInternalCloseTimeout(long closeWaitTimeout, TimeUnit closeWaitTimeUnit) {
      BuzzMessageBusLogger.errorInternalCloseTimeout(this.buzzMessageBusName, closeWaitTimeout, closeWaitTimeUnit.toString());
   }

   public void errorEventInternalNoSubprotocolReceiverForReceiptRDMALeak(EndPoint endPoint, Object cookie) {
      BuzzMessageBusLogger.errorEventInternalNoSubprotocolReceiverForReceiptRDMALeak(this.buzzMessageBusName, endPoint.getCanonicalName(), cookie.toString());
   }

   public void errorEventInternalReceiverConnectIncorrectEventType(EndPoint endPoint, Event.Type eventType) {
      BuzzMessageBusLogger.errorEventInternalReceiverConnectIncorrectEventType(this.buzzMessageBusName, endPoint.getCanonicalName(), eventType.toString());
   }

   public void errorEventInternalUnknownReceiptTypeRDMALeak(EndPoint endPoint, Object receipt) {
      BuzzMessageBusLogger.errorEventInternalUnknownReceiptTypeRDMALeak(this.buzzMessageBusName, endPoint.getCanonicalName(), receipt.toString());
   }

   public void errorRecvInternalExReadingBufferSequenceInputStream(EndPoint endPoint, BufferSequence bs, Throwable t) {
      BuzzMessageBusLogger.errorRecvInternalExReadingBufferSequenceInputStream(this.buzzMessageBusName, endPoint.getCanonicalName(), bs.toString(), t);
   }

   public void errorRecvInternalForceCloseIdAuxMsgInfoNullRDMALeak(EndPoint endPoint, long messageId) {
      BuzzMessageBusLogger.errorRecvInternalForceCloseIdAuxMsgInfoNullRDMALeak(this.buzzMessageBusName, endPoint.getCanonicalName(), messageId);
   }

   public void errorRecvInternalNoConnectionInfo(EndPoint endPoint) {
      BuzzMessageBusLogger.errorRecvInternalNoConnectionInfo(this.buzzMessageBusName, endPoint.getCanonicalName());
   }

   public void errorRecvInternalNoEndpointMapEntry(EndPoint endPoint, BufferSequence request) {
      BuzzMessageBusLogger.errorRecvInternalNoEndpointMapEntry(this.buzzMessageBusName, endPoint.getCanonicalName());
   }

   public void errorRecvInternalNoSubprotocolReceiver(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId) {
      BuzzMessageBusLogger.errorRecvInternalNoSubprotocolReceiver(this.buzzMessageBusName, endPoint.getCanonicalName(), this.frm2s(frameType), this.flg2s(flags), subprotocol, messageId);
   }

   public void errorRecvInternalInitConnectionInWrongState(EndPoint endPoint, int peerMin, int peerMax, int localMin, int localMax, String connectionState) {
      BuzzMessageBusLogger.errorRecvInternalInitConnectionInWrongState(this.buzzMessageBusName, endPoint.getCanonicalName(), peerMin, peerMax, localMin, localMax, connectionState);
   }

   public void errorRecvInternalInitErrorConnectionInfoShouldNotBeNull(EndPoint endPoint) {
      BuzzMessageBusLogger.errorRecvInternalInitErrorConnectionInfoShouldNotBeNull(this.buzzMessageBusName, endPoint.getCanonicalName());
   }

   public void errorRecvInternalUnknownMsgIdScopeForRstRDMALeak(EndPoint endPoint, short flags, byte subprotocol, long messageId, int errorCode) {
      BuzzMessageBusLogger.errorRecvInternalUnknownMsgIdScopeForRstRDMALeak(this.buzzMessageBusName, endPoint.getCanonicalName(), this.flg2s(flags), subprotocol, messageId, this.ec2s(errorCode));
   }

   public void errorSendInternalIncorrectMsgStateShouldBeHalfClosed(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId, BuzzTypes.BuzzMessageState state) {
      BuzzMessageBusLogger.errorSendInternalIncorrectMsgStateShouldBeHalfClosed(this.buzzMessageBusName, endPoint.getCanonicalName(), this.frm2s(frameType), this.flg2s(flags), subprotocol, messageId, state.toString());
   }

   public void errorSendInternalIncorrectMsgStateShouldBeOpen(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId, BuzzTypes.BuzzMessageState state) {
      BuzzMessageBusLogger.errorSendInternalIncorrectMsgStateShouldBeOpen(this.buzzMessageBusName, endPoint.getCanonicalName(), this.frm2s(frameType), this.flg2s(flags), subprotocol, messageId, state.toString());
   }

   public void errorSendInternalInitExistingEpToConnectionInfoEntry(EndPoint endPoint) {
      BuzzMessageBusLogger.errorSendInternalInitExistingEpToConnectionInfoEntry(this.buzzMessageBusName, endPoint.getCanonicalName());
   }

   public void errorSendInternalRst(EndPoint endPoint, byte subprotocol, long messageId, int errorCode, String msg, Throwable t) {
      BuzzMessageBusLogger.errorSendInternalRst(this.buzzMessageBusName, endPoint.getCanonicalName(), subprotocol, messageId, this.ec2s(errorCode), msg, t);
   }
}
