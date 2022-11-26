package com.oracle.buzzmessagebus.impl;

import com.oracle.buzzmessagebus.api.BuzzAdmin;
import com.oracle.buzzmessagebus.api.BuzzAdminReceiver;
import com.oracle.buzzmessagebus.api.BuzzException;
import com.oracle.buzzmessagebus.api.BuzzLogger;
import com.oracle.buzzmessagebus.api.BuzzMessageToken;
import com.oracle.buzzmessagebus.api.BuzzSender;
import com.oracle.buzzmessagebus.api.BuzzSubprotocolReceiver;
import com.oracle.buzzmessagebus.api.BuzzTypes;
import com.oracle.buzzmessagebus.impl.internalapi.BuzzAdminInternalApi;
import com.oracle.buzzmessagebus.impl.internalapi.BuzzMessageTokenInternalApi;
import com.oracle.buzzmessagebus.impl.internalapi.BuzzSenderInternalApi;
import com.oracle.buzzmessagebus.impl.internalapi.BuzzSubprotocolReceiverInternalApi;
import com.oracle.common.base.Collector;
import com.oracle.common.io.BufferManagers;
import com.oracle.common.io.BufferSequence;
import com.oracle.common.io.BufferSequenceOutputStream;
import com.oracle.common.io.CompositeBufferSequence;
import com.oracle.common.net.exabus.Depot;
import com.oracle.common.net.exabus.EndPoint;
import com.oracle.common.net.exabus.Event;
import com.oracle.common.net.exabus.MessageBus;
import com.oracle.common.net.exabus.Event.Type;
import com.oracle.common.net.exabus.util.SimpleDepot;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class BuzzImpl implements BuzzAdminInternalApi, BuzzSenderInternalApi {
   private static final int MAX_BUZZ_VERSION_SUPPORTED = 1;
   private static final int MIN_BUZZ_VERSION_SUPPORTED = 1;
   private BuzzLogger l;
   private String name;
   private String address;
   private MessageBus bus;
   private static final String SUBPROTOCOL_MISMATCH_S = "Given subprotocol: %02X; does not match previous subprotocol for this message: %02X";
   private final EpToConnectionInfo epToConnectionInfo = new EpToConnectionInfo();
   private final BuzzSubprotocolReceiver[] subprotocolReceivers = new BuzzSubprotocolReceiver[256];
   private final List adminReceivers = new LinkedList();
   private boolean apiErrorCheckEnabled = true;
   private boolean wireErrorCheckEnabled = true;
   private volatile boolean gotInitReceipt = false;
   private final CountDownLatch closeLatch = new CountDownLatch(1);
   private long closeWaitTimeout = 5L;
   private TimeUnit closeWaitTimeUnit;

   private void setName(String x) {
      this.name = x;
   }

   private void setAddress(String x) {
      this.address = x;
   }

   private void addSubprotocolReceiver(BuzzSubprotocolReceiver v) {
      this.subprotocolReceivers[v.getSubprotocolId()] = v;
   }

   public void addAdminReceiver(BuzzAdminReceiver x) {
      this.adminReceivers.add(x);
   }

   private BuzzImpl() {
      this.closeWaitTimeUnit = TimeUnit.SECONDS;
   }

   public static BuzzAdmin.Builder builder() {
      return new BuilderImpl(new BuzzImpl());
   }

   public int getMaxBuzzVersion() {
      return 1;
   }

   public int getMinBuzzVersion() {
      return 1;
   }

   public synchronized void setLogger(BuzzLogger x) {
      this.l = x;
   }

   public synchronized BuzzLogger getLogger() {
      return this.l;
   }

   public String getName() {
      return this.name;
   }

   public String getAddress() {
      return this.address;
   }

   public EndPoint getLocalEndPoint() {
      return this.bus.getLocalEndPoint();
   }

   public void setApiErrorCheckEnabled(boolean x) {
      this.apiErrorCheckEnabled = x;
   }

   public void setWireErrorCheckEnabled(boolean x) {
      this.wireErrorCheckEnabled = x;
   }

   private void setCloseWaitTimeout(long to, TimeUnit tu) {
      this.closeWaitTimeout = to;
      this.closeWaitTimeUnit = tu;
   }

   public void close() {
      this.l.infoBuzzClosing(this.bus);
      this.bus.close();

      try {
         boolean normal = this.closeLatch.await(this.closeWaitTimeout, this.closeWaitTimeUnit);
         if (!normal) {
            this.l.errorInternalCloseTimeout(this.closeWaitTimeout, this.closeWaitTimeUnit);
         }
      } catch (InterruptedException var2) {
         this.l.infoExCloseInterruptedWhileWaitingForCloseEvent(this.getLocalEndPoint(), var2);
         Thread.currentThread().interrupt();
      }

      this.l.infoBuzzClosed(this.bus);
   }

   public BuzzAdmin getBuzzAdmin() {
      return this;
   }

   public EndPoint resolveEndPoint(String s) {
      return SimpleDepot.getInstance().resolveEndPoint(s);
   }

   public void connect(EndPoint endPoint) {
      synchronized(this.epToConnectionInfo) {
         ConnectionInfo connectionInfo = this.epToConnectionInfo.get(endPoint);
         if (connectionInfo == null) {
            connectionInfo = new ConnectionInfo(endPoint, ConnectionInfo.ConnectionState.PENDING_CONNECT);
            this.epToConnectionInfo.put(endPoint, connectionInfo);
            this.bus.connect(endPoint);
         }
      }
   }

   public void release(EndPoint endPoint) {
      this.epToConnectionInfo.remove(endPoint);
      this.bus.release(endPoint);
   }

   public void flush() {
      this.bus.flush();
   }

   private BuzzMessageToken getToken(EndPoint endPoint, byte subprotocol, boolean checkSubprotocol) {
      BuzzSubprotocolReceiver subprotocolReceiver = this.subprotocolReceivers[subprotocol];
      if (this.apiErrorCheckEnabled && checkSubprotocol) {
         this.sendCheckSubprotocolReceiver(subprotocol, subprotocolReceiver);
      }

      ConnectionInfo connectionInfo = this.epToConnectionInfo.get(endPoint);
      if (connectionInfo == null) {
         throw new BuzzException(BuzzException.ExceptionType.ILLEGAL_ENDPOINT_STATE);
      } else {
         long messageId = connectionInfo.getNextId();
         return new BuzzMessageTokenImpl(this.l, endPoint, connectionInfo, messageId, subprotocol, subprotocolReceiver);
      }
   }

   public BuzzMessageToken getToken(EndPoint endPoint, byte subprotocol) {
      return this.getToken(endPoint, subprotocol, true);
   }

   public BuzzMessageToken getToken(BuzzMessageToken buzzMessageToken) {
      return ((BuzzMessageTokenImpl)buzzMessageToken).getNext();
   }

   public BuzzMessageToken getTokenWithId(EndPoint endPoint, byte subprotocol, long messageId) {
      BuzzMessageToken bmt = this.getToken(endPoint, subprotocol, false);
      ((BuzzMessageTokenInternalApi)bmt).setMessageId(messageId);
      return bmt;
   }

   public void send(BuzzMessageToken buzzMessageToken, byte frameType, short flags, BufferSequence body) {
      this.send(buzzMessageToken, frameType, flags, body, (Object)null);
   }

   public void send(BuzzMessageToken buzzMessageToken, byte frameType, short flags, BufferSequence body, Object receiptCookie) {
      switch (frameType) {
         case -3:
         case -1:
         case 0:
            this.sendInternal(buzzMessageToken, frameType, flags, body, receiptCookie, true);
            return;
         case -2:
         case 3:
            throw new BuzzException(BuzzException.ExceptionType.ILLEGAL_FRAME_TYPE, String.format("%02X", frameType));
         case 1:
         case 2:
         default:
            throw new BuzzException(BuzzException.ExceptionType.UNKNOWN_FRAME_TYPE, String.format("%02X", frameType));
      }
   }

   public void closeMyId(BuzzMessageToken buzzMessageToken, String... extra) {
      this.sendCloseId(8, buzzMessageToken.getEndPoint(), buzzMessageToken.getSubprotocol(), ((BuzzMessageTokenInternalApi)buzzMessageToken).getMessageId(), extra);
   }

   public void closeYourId(BuzzMessageToken buzzMessageToken, String... extra) {
      this.sendCloseId(9, buzzMessageToken.getEndPoint(), buzzMessageToken.getSubprotocol(), ((BuzzMessageTokenInternalApi)buzzMessageToken).getMessageId(), extra);
   }

   private void checkMineYours(EndPoint endPoint, int mineOrYours) {
      if (mineOrYours != 8 && mineOrYours != 9) {
         throw new RuntimeException("SEVERE: INTERNAL: incorrect arg given to sendCloseId: " + mineOrYours);
      }
   }

   private void sendCloseId(int mineOrYours, EndPoint endPoint, byte subprotocol, long messageId, String... extra) {
      this.checkMineYours(endPoint, mineOrYours);
      String extraMsg = null;
      if (extra.length > 0) {
         StringBuilder sb = new StringBuilder();
         String[] var9 = extra;
         int var10 = extra.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            String s = var9[var11];
            sb.append(s);
         }

         extraMsg = sb.toString();
      }

      ConnectionInfo connectionInfo = this.epToConnectionInfo.get(endPoint);
      IdToMsg messageIdToMessageInfo = mineOrYours == 8 ? connectionInfo.getOurMessageIdToMessageInfoMap() : connectionInfo.getTheirMessageIdToMessageInfoMap();
      BuzzMessageTokenImpl buzzMessageToken = messageIdToMessageInfo.get(messageId);
      if (buzzMessageToken != null || mineOrYours != 8) {
         if (buzzMessageToken != null) {
            BufferSequence bufferSequence = buzzMessageToken.getBufferSequence();
            if (bufferSequence != null) {
               this.l.infoSendDisposeCloseId(endPoint, subprotocol, messageId, mineOrYours, extraMsg);
               bufferSequence.dispose();
            }

            buzzMessageToken.close();
            messageIdToMessageInfo.remove(messageId);
         }

         try {
            this.sendRst(endPoint, subprotocol, messageId, mineOrYours, extraMsg);
         } catch (StopDispatch var13) {
         }

      }
   }

   public void sendInternal(BuzzMessageToken buzzMessageTokenInterface, byte frameType, short flags, BufferSequence body, Object receiptCookie, boolean throwOnError) {
      BuzzMessageTokenImpl buzzMessageToken = (BuzzMessageTokenImpl)buzzMessageTokenInterface;
      if (this.apiErrorCheckEnabled && throwOnError) {
         this.sendErrorCheckArguments(buzzMessageToken.getEndPoint(), frameType, flags, buzzMessageToken.getMessageId());
      }

      IdToMsg messageIdToMessageInfo;
      BuzzMessageTokenImpl o;
      switch (frameType) {
         case -3:
         case 0:
            if (this.apiErrorCheckEnabled && throwOnError && this.isIllegalStateForDataFrame(frameType, buzzMessageToken)) {
               throw new BuzzException(BuzzException.ExceptionType.DATA_ON_CLOSED_ID);
            }

            switch (frameType) {
               case -3:
                  if (this.apiErrorCheckEnabled && buzzMessageToken.getMessageState() != BuzzTypes.BuzzMessageState.HALF_CLOSED) {
                     this.l.errorSendInternalIncorrectMsgStateShouldBeHalfClosed(buzzMessageToken.getEndPoint(), frameType, flags, buzzMessageToken.getSubprotocol(), buzzMessageToken.getMessageId(), buzzMessageToken.getMessageState());
                  }
                  break;
               case 0:
                  if (this.apiErrorCheckEnabled && buzzMessageToken.getMessageState() != BuzzTypes.BuzzMessageState.OPEN) {
                     this.l.errorSendInternalIncorrectMsgStateShouldBeOpen(buzzMessageToken.getEndPoint(), frameType, flags, buzzMessageToken.getSubprotocol(), buzzMessageToken.getMessageId(), buzzMessageToken.getMessageState());
                  }
            }

            buzzMessageToken.updateState(flags);
            if (buzzMessageToken.isInMap() && buzzMessageToken.getMessageState() == BuzzTypes.BuzzMessageState.CLOSED) {
               messageIdToMessageInfo = frameType == 0 ? buzzMessageToken.getConnectionInfo().getOurMessageIdToMessageInfoMap() : buzzMessageToken.getConnectionInfo().getTheirMessageIdToMessageInfoMap();
               messageIdToMessageInfo.remove(buzzMessageToken.getMessageId());
               buzzMessageToken.isInMap(false);
            }
         case -2:
         default:
            break;
         case -1:
            if ((flags & 16384) != 16384) {
               buzzMessageToken.updateState(flags);
               messageIdToMessageInfo = buzzMessageToken.getConnectionInfo().getOurMessageIdToMessageInfoMap();
               o = messageIdToMessageInfo.putIfAbsent(buzzMessageToken.getMessageId(), buzzMessageToken);
               if (o != null) {
                  if (throwOnError) {
                     throw new BuzzException(BuzzException.ExceptionType.START_ON_NON_CLOSED_ID);
                  }
               } else {
                  buzzMessageToken.isInMap(true);
               }
            }
      }

      this.l.infoSend(buzzMessageToken.getEndPoint(), frameType, flags, buzzMessageToken.getSubprotocol(), buzzMessageToken.getMessageId(), body, receiptCookie);
      BufferSequenceOutputStream out = new BufferSequenceOutputStream(BufferManagers.getNetworkDirectManager());
      o = null;

      BufferSequence header;
      try {
         out.writeByte(frameType);
         out.writeShort(flags);
         out.writeByte(buzzMessageToken.getSubprotocol());
         out.writeLong(buzzMessageToken.getMessageId());
         header = out.toBufferSequence();
      } catch (IOException var12) {
         this.l.infoSendDisposeEx(buzzMessageToken.getEndPoint(), frameType, flags, buzzMessageToken.getSubprotocol(), buzzMessageToken.getMessageId(), "out.close");
         out.close();
         throw new BuzzException(BuzzException.ExceptionType.IO_EXCEPTION, var12);
      }

      BufferSequence request = body == null ? header : new CompositeBufferSequence(header, body);

      try {
         if (receiptCookie != null && receiptCookie != body) {
            if (receiptCookie instanceof InitCookie) {
               ((InitCookie)receiptCookie).setBufferSequence((BufferSequence)request);
               this.bus.send(buzzMessageToken.getEndPoint(), (BufferSequence)request, receiptCookie);
            } else {
               WrapUserCookie wrapUserCookie = new WrapUserCookie(header, buzzMessageToken.getSubprotocolReceiver(), receiptCookie);
               this.bus.send(buzzMessageToken.getEndPoint(), (BufferSequence)request, wrapUserCookie);
            }
         } else {
            this.bus.send(buzzMessageToken.getEndPoint(), (BufferSequence)request, request);
         }
      } catch (RuntimeException var13) {
         this.l.infoSendDisposeEx(buzzMessageToken.getEndPoint(), frameType, flags, buzzMessageToken.getSubprotocol(), buzzMessageToken.getMessageId(), "header dispose");
         header.dispose();
         throw var13;
      }

      this.l.infoSent(buzzMessageToken.getEndPoint(), frameType, flags, buzzMessageToken.getSubprotocol(), buzzMessageToken.getMessageId(), body, receiptCookie);
   }

   public MessageBus getBus() {
      return this.bus;
   }

   public String getStats() {
      int size = this.epToConnectionInfo.size();
      StringBuilder sb = (new StringBuilder()).append("epToConnectionInfo/").append(size);
      Iterator var3 = this.epToConnectionInfo.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry e = (Map.Entry)var3.next();
         sb.append("\n\t").append(e.getKey()).append(", ").append(e.getValue());
      }

      return sb.toString();
   }

   private void sendErrorCheckArguments(EndPoint endPoint, byte frameType, short flags, long messageId) {
      switch (frameType) {
         case -3:
            this.sendCheckMessageId(messageId == 0L, "data", "");
            this.sendCheckFlags((short)(1 | flags), (short)1);
            break;
         case -2:
            this.sendCheckMessageId(messageId != 0L, "init", "non-");
            this.sendCheckFlags(flags, (short)0);
            break;
         case -1:
            this.sendCheckMessageId(messageId == 0L, "start", "");
            this.sendCheckFlags((short)(-16383 | flags), (short)-16383);
            break;
         case 0:
            this.sendCheckMessageId(messageId == 0L, "data", "");
            this.sendCheckFlags((short)(16385 | flags), (short)16385);
            break;
         case 1:
         case 2:
         default:
            throw new BuzzException(BuzzException.ExceptionType.UNKNOWN_FRAME_TYPE, String.format("%02X", frameType));
         case 3:
            this.sendCheckFlags(flags, (short)0);
      }

   }

   private ConnectionInfo sendInitErrorCheckExistingEpToConnectionInfoEntry(EndPoint endPoint) throws StopDispatch {
      ConnectionInfo connectionInfo = this.epToConnectionInfo.get(endPoint);
      if (connectionInfo == null) {
         return null;
      } else {
         switch (connectionInfo.state()) {
            case PENDING_CONNECT:
               return connectionInfo;
            case CONNECTED:
               this.l.errorSendInternalInitExistingEpToConnectionInfoEntry(endPoint);
               this.sendRst(endPoint, 2, "existing epToConnectionInfo entry");
               return null;
            default:
               throw new RuntimeException("should not happen");
         }
      }
   }

   private void sendCheckSubprotocolReceiver(byte subprotocol, BuzzSubprotocolReceiver subprotocolReceiver) {
      if (subprotocolReceiver == null) {
         throw new BuzzException(BuzzException.ExceptionType.NO_SUBPROTOCOL_RECEIVER_REGISTERED, String.format("%02X", subprotocol));
      } else if (subprotocolReceiver.getSubprotocolId() != subprotocol) {
         throw new BuzzException(BuzzException.ExceptionType.SUBPROTOCOL_MISMATCH, String.format("Given subprotocol: %02X; does not match previous subprotocol for this message: %02X", subprotocol, subprotocolReceiver.getSubprotocolId()));
      }
   }

   private void sendCheckMessageId(boolean isIllegal, String frameType, String non) {
      if (isIllegal) {
         throw new BuzzException(BuzzException.ExceptionType.ILLEGAL_MESSAGE_ID, frameType + " frame with " + non + "zero id");
      }
   }

   private void sendCheckFlags(short given, short allowed) {
      if (given != allowed) {
         throw new BuzzException(BuzzException.ExceptionType.ILLEGAL_FLAGS, String.format("%04X", given));
      }
   }

   private void start(MessageBus messageBus) {
      if (messageBus == null) {
         Depot depot = SimpleDepot.getInstance();
         this.bus = depot.createMessageBus(this.address != null ? depot.resolveEndPoint(this.address) : null);
      } else {
         this.bus = messageBus;
      }

      this.bus.setEventCollector(new Collector() {
         public void add(Event event) {
            boolean shouldDispose = true;

            try {
               switch (event.getType()) {
                  case MESSAGE:
                     shouldDispose = false;
                     EndPoint endPoint = event.getEndPoint();
                     BufferSequence msg = (BufferSequence)event.dispose(true);
                     BuzzImpl.this.l.infoEventMessage(event, endPoint, msg);
                     BuzzImpl.this.recvFrameDispatch(endPoint, msg);
                     break;
                  case RECEIPT:
                     BuzzImpl.this.l.infoEventReceipt(event);
                     Object content = event.getContent();
                     if (content instanceof BufferSequence) {
                        BuzzImpl.this.l.infoEventDisposeReceipt(event.getEndPoint(), "auto dispose");
                        ((BufferSequence)content).dispose();
                     } else if (content instanceof WrapUserCookie) {
                        WrapUserCookie wrapUserCookie = (WrapUserCookie)content;
                        BuzzSubprotocolReceiver subprotocolReceiver = wrapUserCookie.receiver;
                        if (subprotocolReceiver == null) {
                           BuzzImpl.this.l.errorEventInternalNoSubprotocolReceiverForReceiptRDMALeak(event.getEndPoint(), content);
                        } else {
                           BuzzImpl.this.l.infoEventDisposeReceipt(event.getEndPoint(), "header.dispose");
                           wrapUserCookie.header.dispose();
                           BuzzImpl.this.eventCallPublicReceiptReceiver(event.getEndPoint(), subprotocolReceiver, wrapUserCookie.userCookie);
                        }
                     } else if (content instanceof InitCookie) {
                        BuzzImpl.this.handleSendInitReceipt((InitCookie)content);
                     } else {
                        BuzzImpl.this.l.errorEventInternalUnknownReceiptTypeRDMALeak(event.getEndPoint(), content);
                     }
                     break;
                  case CONNECT:
                     BuzzImpl.this.l.infoEventConnect(event);
                     BuzzImpl.this.sendInit(event.getEndPoint());
                     BuzzImpl.this.eventCallPublicConnectReleaseReceivers(Type.CONNECT, event.getEndPoint());
                     break;
                  case DISCONNECT:
                     BuzzImpl.this.l.infoEventDisconnect(event);
                     BuzzImpl.this.epToConnectionInfo.remove(event.getEndPoint());
                     BuzzImpl.this.tryRelease(event.getEndPoint(), "case DISCONNECT");
                     break;
                  case RELEASE:
                     BuzzImpl.this.l.infoEventRelease(event);
                     BuzzImpl.this.eventCallPublicConnectReleaseReceivers(Type.RELEASE, event.getEndPoint());
                     break;
                  case OPEN:
                     BuzzImpl.this.l.infoEventOpen(event);
                     BuzzImpl.this.eventCallPublicOpenCloseAdminReceivers(Type.OPEN, event.getEndPoint());
                     break;
                  case CLOSE:
                     BuzzImpl.this.l.infoEventClose(event);
                     BuzzImpl.this.closeLatch.countDown();
                     BuzzImpl.this.eventCallPublicOpenCloseAdminReceivers(Type.CLOSE, event.getEndPoint());
                     break;
                  case SIGNAL:
                     BuzzImpl.this.l.infoEventSignal(event);
                     break;
                  case BACKLOG_EXCESSIVE:
                     BuzzImpl.this.l.infoEventBacklogExcessive(event);
                     BuzzImpl.this.eventBacklog(true, event.getEndPoint());
                     break;
                  case BACKLOG_NORMAL:
                     BuzzImpl.this.l.infoEventBacklogNormal(event);
                     BuzzImpl.this.eventBacklog(false, event.getEndPoint());
               }
            } catch (StopDispatch var12) {
            } catch (Throwable var13) {
               var13.printStackTrace();
               BuzzImpl.this.l.errorInternalExEventCollector(event, var13);
            } finally {
               if (shouldDispose) {
                  event.dispose();
               }

            }

         }

         public void flush() {
            BuzzSubprotocolReceiver[] var1 = BuzzImpl.this.subprotocolReceivers;
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
               BuzzSubprotocolReceiver h = var1[var3];
               if (h != null) {
                  try {
                     h.flush();
                  } catch (Throwable var6) {
                     BuzzImpl.this.l.errorEventInternalExReceiverFlush(h, var6);
                  }
               }
            }

         }
      });
      this.bus.open();
      this.l.infoBuzzOpened(this.bus);
   }

   private void recvFrameDispatch(EndPoint endPoint, BufferSequence request) throws StopDispatch {
      BSIS is = this.recvErrorCheckCommonLength(endPoint, request);
      ByteBuffer bbUnsafe = request.getUnsafeBuffer(0);
      int p = request.getBufferPosition(0);
      byte frameType = is == null ? bbUnsafe.get(p) : is.readByte();
      short flags = is == null ? bbUnsafe.getShort(p + 1) : is.readShort();
      byte subprotocol = is == null ? bbUnsafe.get(p + 3) : is.readByte();
      long messageId = is == null ? bbUnsafe.getLong(p + 4) : is.readLong();
      this.l.infoRecv(endPoint, frameType, flags, subprotocol, messageId, request);
      if (this.wireErrorCheckEnabled && frameType != -2) {
         this.recvErrorCheckInitialized(endPoint, request, frameType, flags, subprotocol, messageId);
      }

      BuzzMessageTokenImpl buzzMessageToken;
      switch (frameType) {
         case -3:
         case 0:
            this.recvInfoDataRecv(endPoint, frameType, flags, subprotocol, messageId);
            ConnectionInfo connectionInfo = this.recvGetConnectionInfoNotNull(endPoint);
            IdToMsg messageIdToMessageInfo = frameType == -3 ? connectionInfo.getOurMessageIdToMessageInfoMap() : connectionInfo.getTheirMessageIdToMessageInfoMap();
            boolean removeP = false;
            if (frameType == 0 && BuzzMessageTokenImpl.isFlagSet((short)1, flags) && BuzzMessageTokenImpl.isFlagSet((short)16384, flags)) {
               removeP = true;
            } else if (BuzzMessageTokenImpl.isFlagSet((short)1, flags)) {
               removeP = true;
            }

            buzzMessageToken = removeP ? messageIdToMessageInfo.remove(messageId) : messageIdToMessageInfo.get(messageId);
            if (this.wireErrorCheckEnabled) {
               this.recvErrorCheckDataOnClosedIdOrIdZero(endPoint, request, frameType, flags, subprotocol, messageId, buzzMessageToken);
               this.recvErrorCheckReceiver(endPoint, request, frameType, flags, subprotocol, messageId, buzzMessageToken.getSubprotocolReceiver());
            }

            buzzMessageToken.addDataUpdateState(request, flags);
            if (buzzMessageToken.isInMap() && buzzMessageToken.getMessageState() == BuzzTypes.BuzzMessageState.CLOSED) {
               buzzMessageToken.isInMap(false);
            }

            this.recvCallPublicStartOrDataReceiver(endPoint, frameType, flags, messageId, buzzMessageToken);
            break;
         case -2:
            this.recvInit(endPoint, request);
            break;
         case -1:
            BuzzSubprotocolReceiver subprotocolReceiver = this.subprotocolReceivers[subprotocol];
            this.l.infoRecvStart(endPoint, flags, subprotocol, messageId);
            ConnectionInfo connectionInfo = null;
            if (this.wireErrorCheckEnabled) {
               connectionInfo = this.recvGetConnectionInfoNotNull(endPoint);
               IdToMsg messageIdToMessageInfo = connectionInfo.getTheirMessageIdToMessageInfoMap();
               buzzMessageToken = messageIdToMessageInfo.get(messageId);
               this.recvErrorCheckStartOnNonClosedIdOrIdZero(endPoint, flags, subprotocol, messageId, subprotocolReceiver, messageIdToMessageInfo, buzzMessageToken, request);
               this.recvErrorCheckReceiver(endPoint, request, frameType, flags, subprotocol, messageId, subprotocolReceiver);
            }

            BuzzMessageTokenImpl buzzMessageToken = new BuzzMessageTokenImpl(this.l, endPoint, (ConnectionInfo)null, request, flags, messageId, subprotocolReceiver);
            if (buzzMessageToken.getMessageState() == BuzzTypes.BuzzMessageState.OPEN) {
               if (connectionInfo == null) {
                  connectionInfo = this.recvGetConnectionInfoNotNull(endPoint);
               }

               IdToMsg messageIdToMessageInfo = connectionInfo.getTheirMessageIdToMessageInfoMap();
               messageIdToMessageInfo.put(messageId, buzzMessageToken);
               buzzMessageToken.isInMap(true);
               buzzMessageToken.setConnectionInfo(connectionInfo);
            }

            this.recvCallPublicStartOrDataReceiver(endPoint, frameType, flags, messageId, buzzMessageToken);
            break;
         case 1:
         case 2:
         default:
            this.l.infoRecvDisposeErrUnknownFrameType(endPoint, frameType, flags, subprotocol, messageId);
            request.dispose();
            this.l.errorRecvUnknownFrameType(endPoint, frameType, flags, subprotocol, messageId);
            this.sendRst(endPoint, subprotocol, messageId, -1);
            break;
         case 3:
            BSIS eris = new BSIS(this.l, endPoint, request);
            eris.skipBytes(12);
            int errorCode = eris.readInt();
            BuzzSubprotocolReceiver subprotocolReceiver = this.subprotocolReceivers[subprotocol];
            this.l.infoRecvRst(endPoint, subprotocol, messageId, errorCode);
            ConnectionInfo connectionInfo = this.recvGetConnectionInfoNotNull(endPoint);
            IdToMsg messageIdToMessageInfo = errorCode != 9 && errorCode != -5 && errorCode != -4 && errorCode != -6 && errorCode != -3 && errorCode != -1 ? connectionInfo.getTheirMessageIdToMessageInfoMap() : connectionInfo.getOurMessageIdToMessageInfoMap();
            BuzzMessageTokenImpl buzzMessageToken;
            switch (errorCode) {
               case -6:
               case 8:
                  this.recvErrorCheckReceiver(endPoint, request, frameType, flags, subprotocol, messageId, subprotocolReceiver);
                  buzzMessageToken = messageIdToMessageInfo.get(messageId);
                  this.recvForceCloseIdNotifyReceiver(8, endPoint, flags, messageId, messageIdToMessageInfo, this.recvMaybeMakeTmpMessageInfo(this.l, endPoint, request, flags, messageId, subprotocolReceiver, buzzMessageToken));
                  break;
               case -5:
               case -4:
               case -3:
               case -1:
               case 1:
               case 2:
                  buzzMessageToken = messageIdToMessageInfo.get(messageId);
                  BuzzMessageTokenImpl bmt = this.recvMaybeMakeTmpMessageInfo(this.l, endPoint, request, flags, messageId, subprotocolReceiver, buzzMessageToken);
                  switch (errorCode) {
                     case -1:
                     case 1:
                     case 2:
                        bmt.close();
                        this.l.errorRecvInternalUnknownMsgIdScopeForRstRDMALeak(endPoint, flags, subprotocol, messageId, errorCode);
                        break;
                     case 0:
                     default:
                        this.recvForceCloseIdAux(endPoint, messageId, messageIdToMessageInfo, bmt);
                  }

                  this.recvCallInternalRstReceiver(endPoint, flags, messageId, errorCode, subprotocolReceiver, bmt);
                  this.l.infoRecvDisposeRst(endPoint, messageId, errorCode);
                  request.dispose();
                  break;
               case -2:
               case 0:
               case 3:
               case 4:
               case 5:
               case 6:
               case 7:
               default:
                  this.l.infoRecvDisposeErrUnknownErrorCode(endPoint, frameType, flags, subprotocol, messageId, errorCode);
                  request.dispose();
                  this.l.errorRecvUnknownErrorCode(endPoint, frameType, flags, subprotocol, messageId, errorCode);
                  break;
               case 9:
                  this.recvErrorCheckReceiver(endPoint, request, frameType, flags, subprotocol, messageId, subprotocolReceiver);
                  buzzMessageToken = messageIdToMessageInfo.get(messageId);
                  this.recvForceCloseIdNotifyReceiver(9, endPoint, flags, messageId, messageIdToMessageInfo, this.recvMaybeMakeTmpMessageInfo(this.l, endPoint, request, flags, messageId, subprotocolReceiver, buzzMessageToken));
            }
      }

   }

   private BuzzMessageTokenImpl recvMaybeMakeTmpMessageInfo(BuzzLogger l, EndPoint endPoint, BufferSequence request, short flags, long messageId, BuzzSubprotocolReceiver subprotocolReceiver, BuzzMessageTokenImpl buzzMessageToken) {
      BuzzMessageTokenImpl bmt = buzzMessageToken != null ? buzzMessageToken : new BuzzMessageTokenImpl(l, endPoint, (ConnectionInfo)null, request, flags, messageId, subprotocolReceiver);
      if (buzzMessageToken != null) {
         bmt.addData(request);
      }

      return bmt;
   }

   private BSIS recvErrorCheckCommonLength(EndPoint endPoint, BufferSequence request) throws StopDispatch {
      if (request.getBufferLength(0) >= 12) {
         return null;
      } else {
         if (request.getLength() < 12L) {
            this.l.errorRecvMsgTooShort(endPoint, request);
            this.sendRst(endPoint, 1, "Message too short");
         }

         return new BSIS(this.l, endPoint, request);
      }
   }

   private void recvInfoDataRecv(EndPoint endPoint, byte frameType, short flags, byte subprotocol, long messageId) {
      if (frameType == 0) {
         this.l.infoRecvRequestData(endPoint, flags, subprotocol, messageId);
      } else {
         this.l.infoRecvResponseData(endPoint, flags, subprotocol, messageId);
      }

   }

   private void recvErrorCheckReceiver(EndPoint endPoint, BufferSequence request, byte frameType, short flags, byte subprotocol, long messageId, BuzzSubprotocolReceiver subprotocolReceiver) throws StopDispatch {
      if (subprotocolReceiver == null) {
         this.l.infoRecvDisposeErrNoReceiver(endPoint, frameType, flags, subprotocol, messageId, "no subprotocol receiver");
         request.dispose();
         this.l.errorRecvInternalNoSubprotocolReceiver(endPoint, frameType, flags, subprotocol, messageId);
         this.sendRst(endPoint, 2, "no subprotocol receiver for: " + subprotocol);
      } else {
         if (subprotocolReceiver.getSubprotocolId() != subprotocol) {
            String msg = String.format("Given subprotocol: %02X; does not match previous subprotocol for this message: %02X", subprotocol, subprotocolReceiver.getSubprotocolId());
            this.l.infoRecvDisposeErrNoReceiver(endPoint, frameType, flags, subprotocol, messageId, msg);
            request.dispose();
            this.l.errorRecvSubprotocolMismatch(endPoint, frameType, flags, subprotocol, messageId, subprotocolReceiver.getSubprotocolId());
            this.sendRst(endPoint, 1, msg);
         }

      }
   }

   private void recvErrorCheckStartOnNonClosedIdOrIdZero(EndPoint endPoint, short flags, byte subprotocol, long messageId, BuzzSubprotocolReceiver subprotocolReceiver, IdToMsg messageIdToMessageInfo, BuzzMessageTokenImpl buzzMessageToken, BufferSequence bufferSequence) throws StopDispatch {
      if (messageId == 0L) {
         this.l.infoRecvDisposeErrStartId(endPoint, flags, subprotocol, messageId, "START on 0 id");
         bufferSequence.dispose();
         this.l.errorRecvStartWithZeroId(endPoint, flags, subprotocol);
         this.sendRst(endPoint, subprotocol, messageId, -3);
      }

      if (buzzMessageToken != null) {
         this.l.errorRecvStartOnNonClosedId(endPoint, flags, subprotocol, messageId);
         if (buzzMessageToken.getSubprotocolReceiver() == null) {
            this.l.infoRecvDisposeErrStartId(endPoint, flags, subprotocol, messageId, "start on non closed id, no receiver");
            bufferSequence.dispose();
         } else {
            buzzMessageToken.addData(bufferSequence);
            this.recvForceCloseIdNotifyReceiver(8, endPoint, flags, messageId, messageIdToMessageInfo, buzzMessageToken);
         }

         this.sendRst(endPoint, subprotocol, messageId, -6);
      }

   }

   private ConnectionInfo recvGetConnectionInfoNotNull(EndPoint endPoint) {
      ConnectionInfo connectionInfo = this.epToConnectionInfo.get(endPoint);
      if (connectionInfo == null) {
         String msg = "SEVERE: INTERNAL ERROR: no ConnectionInfo";
         this.l.errorRecvInternalNoConnectionInfo(endPoint);
         throw new RuntimeException(endPoint + " " + "SEVERE: INTERNAL ERROR: no ConnectionInfo");
      } else {
         return connectionInfo;
      }
   }

   private void recvForceCloseIdNotifyReceiver(int mineOrYours, EndPoint endPoint, short flags, long messageId, IdToMsg messageIdToMessageInfo, BuzzMessageTokenImpl buzzMessageToken) {
      this.checkMineYours(endPoint, mineOrYours);
      this.recvForceCloseIdAux(endPoint, messageId, messageIdToMessageInfo, buzzMessageToken);
      this.recvCallPublicCloseIdReceiver(mineOrYours, endPoint, flags, messageId, buzzMessageToken, messageIdToMessageInfo);
   }

   private void notifySenderOfExceptionInReceiver(EndPoint endPoint, long messageId, BuzzMessageTokenImpl buzzMessageToken, IdToMsg messageIdToMessageInfo, Throwable t) {
      BufferSequence bs = buzzMessageToken.getBufferSequence();
      if (bs != null) {
         this.l.infoRecvDisposeExReceiver(endPoint, messageId);
         bs.dispose();
      }

      this.recvForceCloseIdAux(endPoint, messageId, messageIdToMessageInfo, buzzMessageToken);
      String reason = t.getMessage() != null ? t.getMessage() : t.toString();

      try {
         this.sendRst(endPoint, buzzMessageToken.getSubprotocolReceiver().getSubprotocolId(), messageId, 2, reason);
      } catch (StopDispatch var10) {
      }

   }

   private void recvForceCloseIdAux(EndPoint endPoint, long messageId, IdToMsg messageIdToMessageInfo, BuzzMessageTokenImpl buzzMessageToken) {
      messageIdToMessageInfo.remove(messageId);
      if (buzzMessageToken == null) {
         this.l.errorRecvInternalForceCloseIdAuxMsgInfoNullRDMALeak(endPoint, messageId);
         throw new RuntimeException("SEVERE: INTERNAL: forceCloseIdAux: message info is null. POTENTIAL RDMA MEMORY LEAK " + messageId);
      } else {
         buzzMessageToken.close();
      }
   }

   private void recvErrorCheckDataOnClosedIdOrIdZero(EndPoint endPoint, BufferSequence request, byte frameType, short flags, byte subprotocol, long messageId, BuzzMessageTokenImpl buzzMessageToken) throws StopDispatch {
      String msg;
      if (messageId == 0L) {
         this.l.infoRecvDisposeErrData(endPoint, frameType, flags, subprotocol, messageId, "DATA with 0 id");
         request.dispose();
         this.l.errorRecvDataWithZeroId(endPoint, frameType, flags, subprotocol, messageId);
         msg = "DATA WITH ZERO ID " + frameType + " " + subprotocol + " " + messageId;
         this.sendRst(endPoint, subprotocol, messageId, -4, msg);
      }

      if (this.isIllegalStateForDataFrame(frameType, buzzMessageToken)) {
         this.l.infoRecvDisposeErrData(endPoint, frameType, flags, subprotocol, messageId, "DATA with illegal state");
         request.dispose();
         this.l.errorRecvDataOnClosedId(endPoint, frameType, flags, subprotocol, messageId);
         msg = "DATA on CLOSED or HALF_CLOSED ID " + frameType + " " + subprotocol + " " + messageId;
         this.sendRst(endPoint, subprotocol, messageId, -5, msg);
      }

   }

   private void recvCallPublicStartOrDataReceiver(EndPoint endPoint, byte frameType, short flags, long messageId, BuzzMessageTokenImpl buzzMessageToken) {
      try {
         buzzMessageToken.getSubprotocolReceiver().receive(frameType, buzzMessageToken);
      } catch (Throwable var10) {
         this.l.errorRecvInternalExReceiver(endPoint, frameType, flags, messageId, buzzMessageToken.getSubprotocolReceiver(), buzzMessageToken, var10);
         ConnectionInfo connectionInfo = this.recvGetConnectionInfoNotNull(endPoint);
         IdToMsg messageIdToMessageInfo = frameType == -3 ? connectionInfo.getOurMessageIdToMessageInfoMap() : connectionInfo.getTheirMessageIdToMessageInfoMap();
         this.notifySenderOfExceptionInReceiver(endPoint, messageId, buzzMessageToken, messageIdToMessageInfo, var10);
      }

   }

   private void recvCallPublicCloseIdReceiver(int mineOrYours, EndPoint endPoint, short flags, long messageId, BuzzMessageTokenImpl buzzMessageToken, IdToMsg messageIdToMessageInfo) {
      try {
         switch (mineOrYours) {
            case 8:
               buzzMessageToken.getSubprotocolReceiver().closeMyId(buzzMessageToken);
               break;
            case 9:
               buzzMessageToken.getSubprotocolReceiver().closeYourId(buzzMessageToken);
               break;
            default:
               throw new RuntimeException("SEVERE: INTERNAL: incorrect arg given to callPublicCloseIdReceiver: " + mineOrYours);
         }
      } catch (Throwable var9) {
         this.l.errorRecvInternalExReceiver(endPoint, (byte)3, flags, messageId, buzzMessageToken.getSubprotocolReceiver(), buzzMessageToken, var9);
         this.notifySenderOfExceptionInReceiver(endPoint, messageId, buzzMessageToken, messageIdToMessageInfo, var9);
      }

   }

   private void eventCallPublicReceiptReceiver(EndPoint endPoint, BuzzSubprotocolReceiver subprotocolReceiver, Object receiptCookie) {
      try {
         subprotocolReceiver.receipt(endPoint, receiptCookie);
      } catch (Throwable var5) {
         this.l.errorEventInternalExReceiverReceipt(endPoint, subprotocolReceiver, receiptCookie, var5);
      }

   }

   private void eventCallPublicConnectReleaseReceivers(Event.Type eventType, EndPoint endPoint) {
      if (eventType != Type.CONNECT && eventType != Type.RELEASE) {
         this.l.errorEventInternalReceiverConnectIncorrectEventType(endPoint, eventType);
         throw new RuntimeException("SEVERE: INTERNAL ERROR: incorrect event type in connect release receiver EndPoint: " + endPoint + " Event.Type: " + eventType);
      } else {
         BuzzSubprotocolReceiver[] var3 = this.subprotocolReceivers;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            BuzzSubprotocolReceiver h = var3[var5];
            if (h != null) {
               try {
                  switch (eventType) {
                     case CONNECT:
                        h.connect(endPoint);
                        break;
                     case RELEASE:
                        h.release(endPoint);
                  }
               } catch (Throwable var8) {
                  this.l.errorEventInternalExReceiverConnectRelease(endPoint, h, eventType, var8);
               }
            }
         }

      }
   }

   private void eventCallPublicFlushReceivers() {
      BuzzSubprotocolReceiver[] var1 = this.subprotocolReceivers;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         BuzzSubprotocolReceiver h = var1[var3];
         if (h != null) {
            try {
               h.flush();
            } catch (Throwable var6) {
               this.l.errorEventInternalExReceiverFlush(h, var6);
            }
         }
      }

   }

   private void recvCallInternalRstReceiver(EndPoint endPoint, short flags, long messageId, int errorCode, BuzzSubprotocolReceiver subprotocolReceiver, BuzzMessageToken buzzMessageToken) {
      if (subprotocolReceiver != null && subprotocolReceiver instanceof BuzzSubprotocolReceiverInternalApi) {
         try {
            ((BuzzSubprotocolReceiverInternalApi)subprotocolReceiver).receiveRstInternal(this, endPoint, messageId, errorCode, buzzMessageToken);
         } catch (Throwable var9) {
            this.l.errorRecvInternalExReceiverInternal(endPoint, (byte)3, flags, messageId, subprotocolReceiver, buzzMessageToken, var9);
         }

      }
   }

   private void sendInit(EndPoint endPoint) throws StopDispatch {
      ConnectionInfo connectionInfo;
      synchronized(this.epToConnectionInfo) {
         connectionInfo = this.sendInitErrorCheckExistingEpToConnectionInfoEntry(endPoint);
         if (connectionInfo == null) {
            connectionInfo = new ConnectionInfo(endPoint, ConnectionInfo.ConnectionState.CONNECTED);
            this.epToConnectionInfo.put(endPoint, connectionInfo);
         } else {
            connectionInfo.state(ConnectionInfo.ConnectionState.CONNECTED);
         }
      }

      BufferSequenceOutputStream out = new BufferSequenceOutputStream(BufferManagers.getNetworkDirectManager());
      BufferSequence bufferSequence = null;
      boolean wasException = false;

      try {
         out.writeInt(1);
         out.writeInt(1);
         bufferSequence = out.toBufferSequence();
         InitCookie initCookie = new InitCookie(connectionInfo, bufferSequence);
         BuzzMessageToken bmt = this.getTokenWithId(endPoint, (byte)0, 0L);
         this.sendInternal(bmt, (byte)-2, (short)0, initCookie.getBufferSequence(), initCookie, true);
         this.bus.flush();
      } catch (IOException var13) {
         wasException = true;
         throw new BuzzException(BuzzException.ExceptionType.IO_EXCEPTION, var13);
      } catch (RuntimeException var14) {
         wasException = true;
         throw var14;
      } finally {
         if (wasException) {
            if (bufferSequence != null) {
               this.l.infoSendDisposeExInit(endPoint);
               bufferSequence.dispose();
            }

            out.close();
         }

      }

   }

   private void recvInit(EndPoint endPoint, BufferSequence request) throws StopDispatch {
      try {
         if (request.getLength() < 20L) {
            this.l.errorRecvInitFrameTooShort(endPoint);
            this.recvInitErrorReleaseEndPointOnInitReceipt(endPoint, "INIT frame not long enough");
         }

         BSIS is = new BSIS(this.l, endPoint, request);
         is.skipBytes(12);
         int peerMaxVersion = is.readInt();
         int peerMinVersion = is.readInt();
         this.l.infoRecvInit(endPoint, peerMinVersion, peerMaxVersion, 1, 1);
         ConnectionInfo connectionInfo = this.recvGetConnectionInfoNotNull(endPoint);
         synchronized(connectionInfo) {
            if (connectionInfo.state() == ConnectionInfo.ConnectionState.INITIALIZED) {
               this.l.errorRecvInitAfterInit(endPoint, peerMinVersion, peerMaxVersion, 1, 1);
               this.recvInitErrorReleaseEndPointOnInitReceipt(endPoint, "duplicate INIT frame received");
            }
         }

         if (1 > peerMaxVersion || peerMinVersion > 1) {
            this.l.errorRecvInitVersionMismatch(endPoint, peerMinVersion, peerMaxVersion, 1, 1);
            this.recvInitErrorReleaseEndPointOnInitReceipt(endPoint, "Buzz version mismatch");
         }

         synchronized(connectionInfo) {
            if (connectionInfo.state() != ConnectionInfo.ConnectionState.CONNECTED) {
               this.l.errorRecvInternalInitConnectionInWrongState(endPoint, peerMinVersion, peerMaxVersion, 1, 1, connectionInfo.state().toString());
               String msg = "SEVERE: INTERNAL ERROR: Connection in wrong state.  Should be CONNECTED. EndPoint: " + endPoint + " state: " + connectionInfo.state() + " peerMaxVersion: " + peerMaxVersion + " peerMinVersion: " + peerMinVersion;
               throw new RuntimeException(endPoint + " " + msg);
            }

            connectionInfo.state(ConnectionInfo.ConnectionState.INITIALIZED);
         }
      } finally {
         this.l.infoRecvDisposeInit(endPoint);
         request.dispose();
      }

   }

   private void handleSendInitReceipt(InitCookie initCookie) {
      this.gotInitReceipt = true;
      this.l.infoSendDisposeInitReceipt(initCookie.connectionInfo.getEndPoint());
      initCookie.getBufferSequence().dispose();
      synchronized(initCookie.connectionInfo) {
         if (initCookie.connectionInfo.state() == ConnectionInfo.ConnectionState.CLOSED) {
            this.tryRelease(initCookie.connectionInfo.getEndPoint(), "RECEIPT / InitCookie / CLOSED");
         }

      }
   }

   private void recvInitErrorReleaseEndPointOnInitReceipt(EndPoint endPoint, String msg) throws StopDispatch {
      ConnectionInfo connectionInfo = this.epToConnectionInfo.remove(endPoint);
      if (connectionInfo != null) {
         if (this.gotInitReceipt) {
            this.tryRelease(connectionInfo.getEndPoint(), msg);
         } else {
            connectionInfo.awaitInitReceiptReceived();
         }
      } else {
         this.l.errorRecvInternalInitErrorConnectionInfoShouldNotBeNull(endPoint);
      }

      throw new StopDispatch();
   }

   private void recvErrorCheckInitialized(EndPoint endPoint, BufferSequence request, byte frameType, short flags, byte subprotocol, long messageId) throws StopDispatch {
      ConnectionInfo connectionInfo = this.epToConnectionInfo.get(endPoint);
      synchronized(connectionInfo) {
         if (connectionInfo.state() == ConnectionInfo.ConnectionState.INITIALIZED) {
            return;
         }
      }

      this.l.infoRecvDisposeErrNotInitialized(endPoint, frameType, flags, subprotocol, messageId);
      request.dispose();
      this.l.errorRecvMsgBeforeConnectionInitialized(endPoint, frameType, flags, subprotocol, messageId);
      this.recvInitErrorReleaseEndPointOnInitReceipt(endPoint, "non-INIT frame before INIT");
   }

   private void eventBacklog(boolean isExcessive, EndPoint endPoint) {
      BuzzSubprotocolReceiver[] var3 = this.subprotocolReceivers;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BuzzSubprotocolReceiver h = var3[var5];

         try {
            if (h != null) {
               h.backlog(isExcessive, endPoint);
            }
         } catch (Throwable var8) {
            this.l.errorEventInternalExReceiverBacklog(endPoint, h, isExcessive, var8);
         }
      }

   }

   private boolean isIllegalStateForDataFrame(byte frameType, BuzzMessageToken buzzMessageToken) {
      return buzzMessageToken == null || buzzMessageToken.getMessageState() == BuzzTypes.BuzzMessageState.CLOSED || frameType == 0 && buzzMessageToken.getMessageState() == BuzzTypes.BuzzMessageState.HALF_CLOSED;
   }

   private void sendRst(EndPoint endPoint, int errorCode, String msg) throws StopDispatch {
      this.sendRst(endPoint, (byte)0, 0L, errorCode, msg);
   }

   private void sendRst(EndPoint endPoint, byte subprotocol, long messageId, int errorCode) throws StopDispatch {
      this.sendRst(endPoint, subprotocol, messageId, errorCode, (String)null);
   }

   private void sendRst(EndPoint endPoint, byte subprotocol, long messageId, int errorCode, String msg) throws StopDispatch {
      BufferSequenceOutputStream out = null;
      BufferSequence body = null;

      try {
         this.l.infoSendRst(endPoint, subprotocol, messageId, errorCode, msg);
         out = new BufferSequenceOutputStream(BufferManagers.getNetworkDirectManager());
         out.writeInt(errorCode);
         if (msg != null) {
            out.writeBytes(msg);
         }

         body = out.toBufferSequence();
         BuzzMessageToken bmt = this.getTokenWithId(endPoint, subprotocol, messageId);
         this.sendInternal(bmt, (byte)3, (short)0, body, (Object)null, true);
         this.bus.flush();
      } catch (RuntimeException | IOException var11) {
         if (body != null) {
            this.l.infoSendDisposeExRst(endPoint, subprotocol, messageId, errorCode, msg != null ? msg : "", var11);
            body.dispose();
         }

         if (out != null) {
            this.l.infoSendDisposeExRst(endPoint, subprotocol, messageId, errorCode, msg != null ? msg : "", var11);
            out.close();
         }

         this.l.errorSendInternalRst(endPoint, subprotocol, messageId, errorCode, msg, var11);
         String m = "SEVERE: INTERNAL ERROR: InternalExceptionWhenSendingRst " + subprotocol + " " + messageId + " " + errorCode + " " + msg + " " + var11;
         if (var11 instanceof IOException) {
            throw new RuntimeException(msg, var11);
         }

         throw (RuntimeException)var11;
      }

      throw new StopDispatch();
   }

   private void eventCallPublicOpenCloseAdminReceivers(Event.Type eventType, EndPoint endPoint) {
      Iterator var3 = this.adminReceivers.iterator();

      while(var3.hasNext()) {
         BuzzAdminReceiver r = (BuzzAdminReceiver)var3.next();
         switch (eventType) {
            case OPEN:
               try {
                  r.open(endPoint);
               } catch (Throwable var7) {
                  this.l.errorEventInternalExAdminReceiverOpenClose(endPoint, r, eventType, var7);
               }
               break;
            case CLOSE:
               try {
                  r.close(endPoint);
               } catch (Throwable var6) {
                  this.l.errorEventInternalExAdminReceiverOpenClose(endPoint, r, eventType, var6);
               }
         }
      }

   }

   private void tryRelease(EndPoint endPoint, String msg) {
      this.l.infoReleaseEndPoint(endPoint, msg);

      try {
         this.bus.release(endPoint);
      } catch (IllegalArgumentException var4) {
         if (var4.getMessage() != null && var4.getMessage().contains("is not open, in state FINAL")) {
            return;
         }

         this.l.errorExInTryRelease(endPoint, msg + ": ", var4);
      }

   }

   public static BuzzMessageToken mkBuzzMessageToken(BufferSequence bufferSequence, BuzzSubprotocolReceiver dummy) {
      return new BuzzMessageTokenImpl((BuzzLogger)null, (EndPoint)null, (ConnectionInfo)null, bufferSequence, (short)0, 1L, dummy);
   }

   public static final class BuilderImpl implements BuzzAdmin.Builder {
      private final BuzzImpl b;
      private MessageBus messageBus;

      BuilderImpl(BuzzImpl b) {
         this.b = b;
      }

      public BuilderImpl messageBus(MessageBus x) {
         this.messageBus = x;
         return this;
      }

      public BuzzSender build() {
         if (this.b.getLogger() == null) {
            this.b.setLogger(new BuzzLoggerDefaultImpl());
         }

         if (this.b.getName() == null) {
            throw new RuntimeException("Missing name");
         } else {
            this.b.getLogger().setBuzzMessageBusName(this.b.getName());
            BuzzSubprotocolReceiver[] var1 = this.b.subprotocolReceivers;
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
               BuzzSubprotocolReceiver r = var1[var3];
               if (r != null) {
                  r.setBuzzSender(this.b);
               }
            }

            Iterator var5 = this.b.adminReceivers.iterator();

            while(var5.hasNext()) {
               BuzzAdminReceiver r = (BuzzAdminReceiver)var5.next();
               r.setBuzzAdmin(this.b);
            }

            this.b.start(this.messageBus);
            return this.b;
         }
      }

      public BuzzAdmin.Builder name(String x) {
         this.b.setName(x);
         return this;
      }

      public BuzzAdmin.Builder address(String x) {
         this.b.setAddress(x);
         return this;
      }

      public BuzzAdmin.Builder addSubprotocolReceiver(BuzzSubprotocolReceiver x) {
         this.b.addSubprotocolReceiver(x);
         return this;
      }

      public BuzzAdmin.Builder addAdminReceiver(BuzzAdminReceiver x) {
         this.b.addAdminReceiver(x);
         return this;
      }

      public BuzzAdmin.Builder logger(BuzzLogger x) {
         this.b.setLogger(x);
         return this;
      }

      public BuzzAdmin.Builder closeWaitTimeout(long to, TimeUnit tu) {
         this.b.setCloseWaitTimeout(to, tu);
         return this;
      }
   }
}
