package com.oracle.tyrus.cluster.common;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.Future;
import javax.websocket.CloseReason;
import javax.websocket.SendHandler;
import javax.websocket.SendResult;
import javax.websocket.CloseReason.CloseCodes;
import org.glassfish.tyrus.core.TyrusFuture;
import org.glassfish.tyrus.core.cluster.BroadcastListener;
import org.glassfish.tyrus.core.cluster.ClusterContext;
import org.glassfish.tyrus.core.cluster.SessionEventListener;

public abstract class AbstractClusterContext extends ClusterContext {
   protected abstract String createMessageId();

   protected abstract Future sendMessage(String var1, MessageBase var2);

   protected abstract void sendMessage(String var1, MessageBase var2, SendHandler var3);

   protected abstract void broadcast(String var1, BroadcastableMessage var2);

   public Future sendText(String sessionId, String text) {
      return this.sendMessage(sessionId, new TextMessage(this.createMessageId(), text));
   }

   public Future sendText(String sessionId, String text, boolean isLast) {
      return this.sendMessage(sessionId, new PartialTextMessage(this.createMessageId(), text, isLast));
   }

   public Future sendBinary(String sessionId, byte[] data) {
      return this.sendMessage(sessionId, new BinaryMessage(this.createMessageId(), data));
   }

   public Future sendBinary(String sessionId, byte[] data, boolean isLast) {
      return this.sendMessage(sessionId, new PartialBinaryMessage(this.createMessageId(), data, isLast));
   }

   public Future sendPing(String sessionId, byte[] data) {
      return this.sendMessage(sessionId, new PingMessage(this.createMessageId(), data));
   }

   public Future sendPong(String sessionId, byte[] data) {
      return this.sendMessage(sessionId, new PongMessage(this.createMessageId(), data));
   }

   public void sendText(String sessionId, String text, SendHandler sendHandler) {
      this.sendMessage(sessionId, new TextMessage(this.createMessageId(), text), sendHandler);
   }

   public void sendBinary(String sessionId, byte[] data, SendHandler sendHandler) {
      this.sendMessage(sessionId, new BinaryMessage(this.createMessageId(), data), sendHandler);
   }

   public void broadcastText(String endpointPath, String text) {
      this.broadcast(endpointPath, new TextMessage(this.createMessageId(), text));
   }

   public void broadcastBinary(String endpointPath, byte[] data) {
      this.broadcast(endpointPath, new BinaryMessage(this.createMessageId(), data));
   }

   public Future close(String sessionId) {
      return this.sendMessage(sessionId, new CloseMessage(this.createMessageId()));
   }

   public Future close(String sessionId, CloseReason closeReason) {
      return this.sendMessage(sessionId, new CloseMessage(this.createMessageId(), closeReason.getCloseCode().getCode(), closeReason.getReasonPhrase()));
   }

   protected static class PongMessage extends BinaryMessage {
      private static final long serialVersionUID = -1897013495558352442L;

      public PongMessage(String messageId, byte[] message) {
         super(messageId, message, null);
      }

      public void process(SessionEventListener listener) throws IOException {
         listener.onSendPong(this.message);
      }
   }

   protected static class PingMessage extends BinaryMessage {
      private static final long serialVersionUID = -5840896827517417554L;

      public PingMessage(String messageId, byte[] message) {
         super(messageId, message, null);
      }

      public void process(SessionEventListener listener) throws IOException {
         listener.onSendPing(this.message);
      }
   }

   protected static class PartialBinaryMessage extends BinaryMessage {
      private static final long serialVersionUID = -9003046671114815198L;
      private final boolean isLast;

      private PartialBinaryMessage(String messageId, byte[] message, boolean isLast) {
         super(messageId, message, null);
         this.isLast = isLast;
      }

      public void process(SessionEventListener listener) throws IOException {
         listener.onSendBinary(this.message, this.isLast);
      }

      // $FF: synthetic method
      PartialBinaryMessage(String x0, byte[] x1, boolean x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   public static class BinaryMessage extends MessageBase implements BroadcastableMessage {
      private static final long serialVersionUID = 6343568996551485749L;
      protected final byte[] message;

      private BinaryMessage(String messageId, byte[] message) {
         super(messageId);
         this.message = message;
      }

      public void process(SessionEventListener listener) throws IOException {
         listener.onSendBinary(this.message);
      }

      public void processBroadcast(BroadcastListener listener) {
         listener.onBroadcast(this.message);
      }

      // $FF: synthetic method
      BinaryMessage(String x0, byte[] x1, Object x2) {
         this(x0, x1);
      }
   }

   protected static class PartialTextMessage extends TextMessage {
      private static final long serialVersionUID = -1851161089584048045L;
      private final boolean isLast;

      private PartialTextMessage(String messageId, String message, boolean isLast) {
         super(messageId, message, null);
         this.isLast = isLast;
      }

      public void process(SessionEventListener listener) throws IOException {
         listener.onSendText(this.message, this.isLast);
      }

      // $FF: synthetic method
      PartialTextMessage(String x0, String x1, boolean x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   protected static class TextMessage extends MessageBase implements BroadcastableMessage {
      private static final long serialVersionUID = -4550155433407549097L;
      protected final String message;

      private TextMessage(String messageId, String message) {
         super(messageId);
         this.message = message;
      }

      public void process(SessionEventListener listener) throws IOException {
         listener.onSendText(this.message);
      }

      public void processBroadcast(BroadcastListener listener) {
         listener.onBroadcast(this.message);
      }

      // $FF: synthetic method
      TextMessage(String x0, String x1, Object x2) {
         this(x0, x1);
      }
   }

   protected static class CloseMessage extends MessageBase {
      private static final long serialVersionUID = 8544796774089111306L;
      private final int closeCode;
      private final String closeReason;

      private CloseMessage(String messageId) {
         super(messageId);
         this.closeCode = -1;
         this.closeReason = null;
      }

      private CloseMessage(String messageId, int closeCode, String closeReason) {
         super(messageId);
         this.closeCode = closeCode;
         this.closeReason = closeReason;
      }

      public void process(SessionEventListener listener) throws IOException {
         if (this.closeCode == -1) {
            listener.onClose();
         } else {
            listener.onClose(new CloseReason(CloseCodes.getCloseCode(this.closeCode), this.closeReason));
         }

      }

      // $FF: synthetic method
      CloseMessage(String x0, Object x1) {
         this(x0);
      }

      // $FF: synthetic method
      CloseMessage(String x0, int x1, String x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   protected static class Nack extends ControlMessageBase {
      private static final long serialVersionUID = -1301628282544892260L;
      private final Throwable throwable;

      public Nack(String messageId, Throwable throwable) {
         super(messageId);
         this.throwable = throwable;
      }

      public void process(TyrusFuture future, SendHandler sendHandler) {
         if (future != null) {
            future.setFailure(this.throwable);
         }

         if (sendHandler != null) {
            sendHandler.onResult(new SendResult(this.throwable));
         }

      }
   }

   protected static class Ack extends ControlMessageBase {
      private static final long serialVersionUID = 2996773791588387889L;

      public Ack(String messageId) {
         super(messageId);
      }

      public void process(TyrusFuture future, SendHandler sendHandler) {
         if (future != null) {
            future.setResult((Object)null);
         }

         if (sendHandler != null) {
            sendHandler.onResult(new SendResult());
         }

      }
   }

   protected abstract static class ControlMessageBase implements Serializable {
      private final String messageId;

      protected ControlMessageBase(String messageId) {
         this.messageId = messageId;
      }

      public abstract void process(TyrusFuture var1, SendHandler var2);

      public final String getId() {
         return this.messageId;
      }
   }

   protected abstract static class MessageBase implements Serializable {
      private final String messageId;

      protected MessageBase(String messageId) {
         this.messageId = messageId;
      }

      public abstract void process(SessionEventListener var1) throws IOException;

      public final String getId() {
         return this.messageId;
      }
   }

   protected interface BroadcastableMessage {
      void processBroadcast(BroadcastListener var1);
   }
}
