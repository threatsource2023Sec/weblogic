package org.glassfish.tyrus.core;

import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.websocket.CloseReason;
import javax.websocket.SendHandler;
import javax.websocket.CloseReason.CloseCodes;
import org.glassfish.tyrus.core.frame.BinaryFrame;
import org.glassfish.tyrus.core.frame.CloseFrame;
import org.glassfish.tyrus.core.frame.PingFrame;
import org.glassfish.tyrus.core.frame.PongFrame;
import org.glassfish.tyrus.core.frame.TextFrame;
import org.glassfish.tyrus.core.frame.TyrusFrame;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.core.monitoring.MessageEventListener;
import org.glassfish.tyrus.spi.UpgradeRequest;

public class TyrusWebSocket {
   private final TyrusEndpointWrapper endpointWrapper;
   private final ProtocolHandler protocolHandler;
   private final CountDownLatch onConnectLatch = new CountDownLatch(1);
   private final EnumSet connected;
   private final AtomicReference state;
   private final Lock lock;
   private volatile MessageEventListener messageEventListener;

   public TyrusWebSocket(ProtocolHandler protocolHandler, TyrusEndpointWrapper endpointWrapper) {
      this.connected = EnumSet.range(TyrusWebSocket.State.CONNECTED, TyrusWebSocket.State.CLOSING);
      this.state = new AtomicReference(TyrusWebSocket.State.NEW);
      this.lock = new ReentrantLock();
      this.messageEventListener = MessageEventListener.NO_OP;
      this.protocolHandler = protocolHandler;
      this.endpointWrapper = endpointWrapper;
      protocolHandler.setWebSocket(this);
   }

   public void setWriteTimeout(long timeoutMs) {
   }

   public boolean isConnected() {
      return this.connected.contains(this.state.get());
   }

   public void onClose(CloseFrame frame) {
      boolean locked = this.lock.tryLock();
      if (locked) {
         try {
            CloseReason closeReason = frame.getCloseReason();
            if (this.endpointWrapper != null) {
               this.endpointWrapper.onClose(this, closeReason);
            }

            if (this.state.compareAndSet(TyrusWebSocket.State.CONNECTED, TyrusWebSocket.State.CLOSING)) {
               this.protocolHandler.close(closeReason.getCloseCode().getCode(), closeReason.getReasonPhrase());
            } else {
               this.state.set(TyrusWebSocket.State.CLOSED);
               this.protocolHandler.doClose();
            }
         } finally {
            this.lock.unlock();
         }
      }

   }

   public void onConnect(UpgradeRequest upgradeRequest, String subProtocol, List extensions, String connectionId, DebugContext debugContext) {
      this.state.set(TyrusWebSocket.State.CONNECTED);
      if (this.endpointWrapper != null) {
         this.endpointWrapper.onConnect(this, upgradeRequest, subProtocol, extensions, connectionId, debugContext);
      }

      this.onConnectLatch.countDown();
   }

   public void onFragment(BinaryFrame frame, boolean last) {
      this.awaitOnConnect();
      if (this.endpointWrapper != null) {
         this.endpointWrapper.onPartialMessage(this, ByteBuffer.wrap(frame.getPayloadData()), last);
         this.messageEventListener.onFrameReceived(frame.getFrameType(), frame.getPayloadLength());
      }

   }

   public void onFragment(TextFrame frame, boolean last) {
      this.awaitOnConnect();
      if (this.endpointWrapper != null) {
         this.endpointWrapper.onPartialMessage(this, frame.getTextPayload(), last);
         this.messageEventListener.onFrameReceived(frame.getFrameType(), frame.getPayloadLength());
      }

   }

   public void onMessage(BinaryFrame frame) {
      this.awaitOnConnect();
      if (this.endpointWrapper != null) {
         this.endpointWrapper.onMessage(this, ByteBuffer.wrap(frame.getPayloadData()));
         this.messageEventListener.onFrameReceived(frame.getFrameType(), frame.getPayloadLength());
      }

   }

   public void onMessage(TextFrame frame) {
      this.awaitOnConnect();
      if (this.endpointWrapper != null) {
         this.endpointWrapper.onMessage(this, frame.getTextPayload());
         this.messageEventListener.onFrameReceived(frame.getFrameType(), frame.getPayloadLength());
      }

   }

   public void onPing(PingFrame frame) {
      this.awaitOnConnect();
      if (this.endpointWrapper != null) {
         this.endpointWrapper.onPing(this, ByteBuffer.wrap(frame.getPayloadData()));
         this.messageEventListener.onFrameReceived(frame.getFrameType(), frame.getPayloadLength());
      }

   }

   public void onPong(PongFrame frame) {
      this.awaitOnConnect();
      if (this.endpointWrapper != null) {
         this.endpointWrapper.onPong(this, ByteBuffer.wrap(frame.getPayloadData()));
         this.messageEventListener.onFrameReceived(frame.getFrameType(), frame.getPayloadLength());
      }

   }

   public void close() {
      this.close(CloseCodes.NORMAL_CLOSURE.getCode(), (String)null);
   }

   public void close(int code, String reason) {
      if (this.state.compareAndSet(TyrusWebSocket.State.CONNECTED, TyrusWebSocket.State.CLOSING)) {
         this.protocolHandler.close(code, reason);
      }

   }

   public void close(CloseReason closeReason) {
      this.close(closeReason.getCloseCode().getCode(), closeReason.getReasonPhrase());
   }

   public Future sendBinary(byte[] data) {
      this.checkConnectedState();
      return this.protocolHandler.send(data);
   }

   public void sendBinary(byte[] data, SendHandler handler) {
      this.checkConnectedState();
      this.protocolHandler.send(data, handler);
   }

   public Future sendText(String data) {
      this.checkConnectedState();
      return this.protocolHandler.send(data);
   }

   public void sendText(String data, SendHandler handler) {
      this.checkConnectedState();
      this.protocolHandler.send(data, handler);
   }

   public Future sendRawFrame(ByteBuffer data) {
      this.checkConnectedState();
      return this.protocolHandler.sendRawFrame(data);
   }

   public Future sendPing(byte[] data) {
      return this.send(new PingFrame(data));
   }

   public Future sendPong(byte[] data) {
      return this.send(new PongFrame(data));
   }

   private void awaitOnConnect() {
      try {
         this.onConnectLatch.await();
      } catch (InterruptedException var2) {
      }

   }

   private Future send(TyrusFrame frame) {
      this.checkConnectedState();
      return this.protocolHandler.send(frame);
   }

   public Future sendText(String fragment, boolean last) {
      this.checkConnectedState();
      return this.protocolHandler.stream(last, fragment);
   }

   public Future sendBinary(byte[] bytes, boolean last) {
      return this.sendBinary(bytes, 0, bytes.length, last);
   }

   public Future sendBinary(byte[] bytes, int off, int len, boolean last) {
      this.checkConnectedState();
      return this.protocolHandler.stream(last, bytes, off, len);
   }

   ProtocolHandler getProtocolHandler() {
      return this.protocolHandler;
   }

   void setMessageEventListener(MessageEventListener messageEventListener) {
      this.messageEventListener = messageEventListener;
      this.protocolHandler.setMessageEventListener(messageEventListener);
   }

   MessageEventListener getMessageEventListener() {
      return this.messageEventListener;
   }

   private void checkConnectedState() {
      if (!this.isConnected()) {
         throw new RuntimeException(LocalizationMessages.SOCKET_NOT_CONNECTED());
      }
   }

   private static enum State {
      NEW,
      CONNECTED,
      CLOSING,
      CLOSED;
   }
}
