package com.oracle.buzzmessagebus.api;

import com.oracle.common.io.BufferSequence;
import com.oracle.common.net.exabus.EndPoint;
import com.oracle.common.net.exabus.Event;
import com.oracle.common.net.exabus.MessageBus;
import java.util.concurrent.TimeUnit;

public interface BuzzLogger {
   void setBuzzMessageBusName(String var1);

   void errorTodo(EndPoint var1, String var2);

   void infoTodo(EndPoint var1, String var2);

   void errorRecvDataOnClosedId(EndPoint var1, byte var2, short var3, byte var4, long var5);

   void errorRecvDataWithZeroId(EndPoint var1, byte var2, short var3, byte var4, long var5);

   void errorRecvInitAfterInit(EndPoint var1, int var2, int var3, int var4, int var5);

   void errorRecvInitFrameTooShort(EndPoint var1);

   void errorRecvInitVersionMismatch(EndPoint var1, int var2, int var3, int var4, int var5);

   void errorRecvMsgBeforeConnectionInitialized(EndPoint var1, byte var2, short var3, byte var4, long var5);

   void errorRecvMsgTooShort(EndPoint var1, BufferSequence var2);

   void errorRecvStartOnNonClosedId(EndPoint var1, short var2, byte var3, long var4);

   void errorRecvStartWithZeroId(EndPoint var1, short var2, byte var3);

   void errorRecvSubprotocolMismatch(EndPoint var1, byte var2, short var3, byte var4, long var5, byte var7);

   void errorRecvUnknownErrorCode(EndPoint var1, byte var2, short var3, byte var4, long var5, int var7);

   void errorRecvUnknownFrameType(EndPoint var1, byte var2, short var3, byte var4, long var5);

   void errorEventInternalExAdminReceiverOpenClose(EndPoint var1, BuzzAdminReceiver var2, Event.Type var3, Throwable var4);

   void errorEventInternalExReceiverConnectRelease(EndPoint var1, BuzzSubprotocolReceiver var2, Event.Type var3, Throwable var4);

   void errorEventInternalExReceiverFlush(BuzzSubprotocolReceiver var1, Throwable var2);

   void errorEventInternalExReceiverReceipt(EndPoint var1, BuzzSubprotocolReceiver var2, Object var3, Throwable var4);

   void errorEventInternalExReceiverBacklog(EndPoint var1, BuzzSubprotocolReceiver var2, boolean var3, Throwable var4);

   void errorRecvInternalExReceiver(EndPoint var1, byte var2, short var3, long var4, BuzzSubprotocolReceiver var6, BuzzMessageToken var7, Throwable var8);

   void errorRecvInternalExReceiverInternal(EndPoint var1, byte var2, short var3, long var4, BuzzSubprotocolReceiver var6, BuzzMessageToken var7, Throwable var8);

   void errorExInTryRelease(EndPoint var1, String var2, Throwable var3);

   void errorInternalCloseTimeout(long var1, TimeUnit var3);

   void errorInternalExEventCollector(Event var1, Throwable var2);

   void errorEventInternalNoSubprotocolReceiverForReceiptRDMALeak(EndPoint var1, Object var2);

   void errorEventInternalReceiverConnectIncorrectEventType(EndPoint var1, Event.Type var2);

   void errorEventInternalUnknownReceiptTypeRDMALeak(EndPoint var1, Object var2);

   void errorRecvInternalExReadingBufferSequenceInputStream(EndPoint var1, BufferSequence var2, Throwable var3);

   void errorRecvInternalForceCloseIdAuxMsgInfoNullRDMALeak(EndPoint var1, long var2);

   void errorRecvInternalNoConnectionInfo(EndPoint var1);

   void errorRecvInternalNoEndpointMapEntry(EndPoint var1, BufferSequence var2);

   void errorRecvInternalNoSubprotocolReceiver(EndPoint var1, byte var2, short var3, byte var4, long var5);

   void errorRecvInternalInitConnectionInWrongState(EndPoint var1, int var2, int var3, int var4, int var5, String var6);

   void errorRecvInternalInitErrorConnectionInfoShouldNotBeNull(EndPoint var1);

   void errorRecvInternalUnknownMsgIdScopeForRstRDMALeak(EndPoint var1, short var2, byte var3, long var4, int var6);

   void errorSendInternalIncorrectMsgStateShouldBeHalfClosed(EndPoint var1, byte var2, short var3, byte var4, long var5, BuzzTypes.BuzzMessageState var7);

   void errorSendInternalIncorrectMsgStateShouldBeOpen(EndPoint var1, byte var2, short var3, byte var4, long var5, BuzzTypes.BuzzMessageState var7);

   void errorSendInternalInitExistingEpToConnectionInfoEntry(EndPoint var1);

   void errorSendInternalRst(EndPoint var1, byte var2, long var3, int var5, String var6, Throwable var7);

   void infoEventBacklogExcessive(Event var1);

   void infoEventBacklogNormal(Event var1);

   void infoEventClose(Event var1);

   void infoEventConnect(Event var1);

   void infoEventDisconnect(Event var1);

   void infoEventMessage(Event var1, EndPoint var2, BufferSequence var3);

   void infoEventOpen(Event var1);

   void infoEventReceipt(Event var1);

   void infoEventRelease(Event var1);

   void infoEventSignal(Event var1);

   void infoBuzzClosed(MessageBus var1);

   void infoBuzzClosing(MessageBus var1);

   void infoBuzzOpened(MessageBus var1);

   void infoExCloseInterruptedWhileWaitingForCloseEvent(EndPoint var1, InterruptedException var2);

   void infoReleaseEndPoint(EndPoint var1, String var2);

   void infoEventDisposeReceipt(EndPoint var1, String var2);

   void infoRecvDisposeInit(EndPoint var1);

   void infoRecvDisposeRst(EndPoint var1, long var2, int var4);

   void infoSendDisposeCloseId(EndPoint var1, byte var2, long var3, int var5, String var6);

   void infoSendDisposeInitReceipt(EndPoint var1);

   void infoRecvDisposeErrData(EndPoint var1, byte var2, short var3, byte var4, long var5, String var7);

   void infoRecvDisposeErrNotInitialized(EndPoint var1, byte var2, short var3, byte var4, long var5);

   void infoRecvDisposeErrNoReceiver(EndPoint var1, byte var2, short var3, byte var4, long var5, String var7);

   void infoRecvDisposeErrStartId(EndPoint var1, short var2, byte var3, long var4, String var6);

   void infoRecvDisposeErrUnknownErrorCode(EndPoint var1, byte var2, short var3, byte var4, long var5, int var7);

   void infoRecvDisposeErrUnknownFrameType(EndPoint var1, byte var2, short var3, byte var4, long var5);

   void infoRecvDisposeExReceiver(EndPoint var1, long var2);

   void infoSendDisposeEx(EndPoint var1, byte var2, short var3, byte var4, long var5, String var7);

   void infoSendDisposeExInit(EndPoint var1);

   void infoSendDisposeExRst(EndPoint var1, byte var2, long var3, int var5, String var6, Throwable var7);

   void infoRecv(EndPoint var1, byte var2, short var3, byte var4, long var5, BufferSequence var7);

   void infoRecvInit(EndPoint var1, int var2, int var3, int var4, int var5);

   void infoRecvRequestData(EndPoint var1, short var2, byte var3, long var4);

   void infoRecvResponseData(EndPoint var1, short var2, byte var3, long var4);

   void infoRecvRst(EndPoint var1, byte var2, long var3, int var5);

   void infoRecvStart(EndPoint var1, short var2, byte var3, long var4);

   void infoSend(EndPoint var1, byte var2, short var3, byte var4, long var5, BufferSequence var7, Object var8);

   void infoSent(EndPoint var1, byte var2, short var3, byte var4, long var5, BufferSequence var7, Object var8);

   void infoSendRst(EndPoint var1, byte var2, long var3, int var5, String var6);
}
