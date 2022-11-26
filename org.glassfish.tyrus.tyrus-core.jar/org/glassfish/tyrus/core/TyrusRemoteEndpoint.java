package org.glassfish.tyrus.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.RemoteEndpoint;
import javax.websocket.SendHandler;
import javax.websocket.SendResult;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public abstract class TyrusRemoteEndpoint implements RemoteEndpoint {
   final TyrusSession session;
   final TyrusWebSocket webSocket;
   private final TyrusEndpointWrapper endpointWrapper;
   private static final Logger LOGGER = Logger.getLogger(TyrusRemoteEndpoint.class.getName());

   private TyrusRemoteEndpoint(TyrusSession session, TyrusWebSocket socket, TyrusEndpointWrapper endpointWrapper) {
      this.webSocket = socket;
      this.endpointWrapper = endpointWrapper;
      this.session = session;
   }

   Future sendSyncObject(Object o) {
      Object toSend;
      try {
         this.session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_OUT, "Sending object: ", o);
         toSend = this.endpointWrapper.doEncode(this.session, o);
      } catch (final Exception var5) {
         return new Future() {
            public boolean cancel(boolean mayInterruptIfRunning) {
               return false;
            }

            public boolean isCancelled() {
               return false;
            }

            public boolean isDone() {
               return true;
            }

            public Object get() throws InterruptedException, ExecutionException {
               throw new ExecutionException(var5);
            }

            public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
               throw new ExecutionException(var5);
            }
         };
      }

      if (toSend instanceof String) {
         return this.webSocket.sendText((String)toSend);
      } else if (toSend instanceof ByteBuffer) {
         return this.webSocket.sendBinary(Utils.getRemainingArray((ByteBuffer)toSend));
      } else if (toSend instanceof StringWriter) {
         StringWriter writer = (StringWriter)toSend;
         StringBuffer sb = writer.getBuffer();
         return this.webSocket.sendText(sb.toString());
      } else if (toSend instanceof ByteArrayOutputStream) {
         ByteArrayOutputStream baos = (ByteArrayOutputStream)toSend;
         return this.webSocket.sendBinary(baos.toByteArray());
      } else {
         return null;
      }
   }

   void sendSyncObject(Object o, SendHandler handler) {
      if (o instanceof String) {
         this.webSocket.sendText((String)o, handler);
      } else {
         Object toSend = null;

         try {
            toSend = this.endpointWrapper.doEncode(this.session, o);
         } catch (Exception var6) {
            handler.onResult(new SendResult(var6));
         }

         if (toSend instanceof String) {
            this.webSocket.sendText((String)toSend, handler);
         } else if (toSend instanceof ByteBuffer) {
            this.webSocket.sendBinary(Utils.getRemainingArray((ByteBuffer)toSend), handler);
         } else if (toSend instanceof StringWriter) {
            StringWriter writer = (StringWriter)toSend;
            StringBuffer sb = writer.getBuffer();
            this.webSocket.sendText(sb.toString(), handler);
         } else if (toSend instanceof ByteArrayOutputStream) {
            ByteArrayOutputStream baos = (ByteArrayOutputStream)toSend;
            this.webSocket.sendBinary(baos.toByteArray(), handler);
         }
      }

   }

   public void sendPing(ByteBuffer applicationData) throws IOException {
      if (applicationData != null && applicationData.remaining() > 125) {
         throw new IllegalArgumentException(LocalizationMessages.APPLICATION_DATA_TOO_LONG("Ping"));
      } else {
         this.session.restartIdleTimeoutExecutor();
         this.webSocket.sendPing(Utils.getRemainingArray(applicationData));
      }
   }

   public void sendPong(ByteBuffer applicationData) throws IOException {
      if (applicationData != null && applicationData.remaining() > 125) {
         throw new IllegalArgumentException(LocalizationMessages.APPLICATION_DATA_TOO_LONG("Pong"));
      } else {
         this.session.restartIdleTimeoutExecutor();
         this.webSocket.sendPong(Utils.getRemainingArray(applicationData));
      }
   }

   public String toString() {
      return "Wrapped: " + this.getClass().getSimpleName();
   }

   public void setBatchingAllowed(boolean allowed) {
   }

   public boolean getBatchingAllowed() {
      return false;
   }

   public void flushBatch() {
   }

   public void close(CloseReason cr) {
      LOGGER.fine("Close public void close(CloseReason cr): " + cr);
      this.webSocket.close(cr);
   }

   // $FF: synthetic method
   TyrusRemoteEndpoint(TyrusSession x0, TyrusWebSocket x1, TyrusEndpointWrapper x2, Object x3) {
      this(x0, x1, x2);
   }

   static class Async extends TyrusRemoteEndpoint implements RemoteEndpoint.Async {
      private long sendTimeout;

      Async(TyrusSession session, TyrusWebSocket socket, TyrusEndpointWrapper endpointWrapper) {
         super(session, socket, endpointWrapper, null);
         if (session.getContainer() != null) {
            this.setSendTimeout(session.getContainer().getDefaultAsyncSendTimeout());
         }

      }

      public void sendText(String text, SendHandler handler) {
         Utils.checkNotNull(text, "text");
         Utils.checkNotNull(handler, "handler");
         this.session.restartIdleTimeoutExecutor();
         this.sendAsync(text, handler, TyrusRemoteEndpoint.Async.AsyncMessageType.TEXT);
      }

      public Future sendText(String text) {
         Utils.checkNotNull(text, "text");
         this.session.restartIdleTimeoutExecutor();
         return this.sendAsync(text, TyrusRemoteEndpoint.Async.AsyncMessageType.TEXT);
      }

      public Future sendBinary(ByteBuffer data) {
         Utils.checkNotNull(data, "data");
         this.session.restartIdleTimeoutExecutor();
         return this.sendAsync(data, TyrusRemoteEndpoint.Async.AsyncMessageType.BINARY);
      }

      public void sendBinary(ByteBuffer data, SendHandler handler) {
         Utils.checkNotNull(data, "data");
         Utils.checkNotNull(handler, "handler");
         this.session.restartIdleTimeoutExecutor();
         this.sendAsync(data, handler, TyrusRemoteEndpoint.Async.AsyncMessageType.BINARY);
      }

      public void sendObject(Object data, SendHandler handler) {
         Utils.checkNotNull(data, "data");
         Utils.checkNotNull(handler, "handler");
         this.session.restartIdleTimeoutExecutor();
         this.sendAsync(data, handler, TyrusRemoteEndpoint.Async.AsyncMessageType.OBJECT);
      }

      public Future sendObject(Object data) {
         Utils.checkNotNull(data, "data");
         this.session.restartIdleTimeoutExecutor();
         return this.sendAsync(data, TyrusRemoteEndpoint.Async.AsyncMessageType.OBJECT);
      }

      public long getSendTimeout() {
         return this.sendTimeout;
      }

      public void setSendTimeout(long timeoutmillis) {
         this.sendTimeout = timeoutmillis;
         this.webSocket.setWriteTimeout(timeoutmillis);
      }

      private Future sendAsync(Object message, AsyncMessageType type) {
         final Future result = null;
         switch (type) {
            case TEXT:
               this.session.getDebugContext().appendLogMessage(TyrusRemoteEndpoint.LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_OUT, "Sending text message: ", message);
               result = this.webSocket.sendText((String)message);
               break;
            case BINARY:
               this.session.getDebugContext().appendLogMessage(TyrusRemoteEndpoint.LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_OUT, "Sending binary message");
               result = this.webSocket.sendBinary(Utils.getRemainingArray((ByteBuffer)message));
               break;
            case OBJECT:
               result = this.sendSyncObject(message);
         }

         return new Future() {
            public boolean cancel(boolean mayInterruptIfRunning) {
               return result.cancel(mayInterruptIfRunning);
            }

            public boolean isCancelled() {
               return result.isCancelled();
            }

            public boolean isDone() {
               return result.isDone();
            }

            public Void get() throws InterruptedException, ExecutionException {
               result.get();
               return null;
            }

            public Void get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
               result.get(timeout, unit);
               return null;
            }
         };
      }

      private void sendAsync(Object message, SendHandler handler, AsyncMessageType type) {
         switch (type) {
            case TEXT:
               this.webSocket.sendText((String)message, handler);
               break;
            case BINARY:
               this.webSocket.sendBinary(Utils.getRemainingArray((ByteBuffer)message), handler);
               break;
            case OBJECT:
               this.sendSyncObject(message, handler);
         }

      }

      private static enum AsyncMessageType {
         TEXT,
         BINARY,
         OBJECT;
      }
   }

   static class Basic extends TyrusRemoteEndpoint implements RemoteEndpoint.Basic {
      Basic(TyrusSession session, TyrusWebSocket socket, TyrusEndpointWrapper endpointWrapper) {
         super(session, socket, endpointWrapper, null);
      }

      public void sendText(String text) throws IOException {
         Utils.checkNotNull(text, "text");
         this.session.getDebugContext().appendLogMessage(TyrusRemoteEndpoint.LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_OUT, "Sending text message: ", text);
         Future future = this.webSocket.sendText(text);

         try {
            this.processFuture(future);
         } finally {
            this.session.restartIdleTimeoutExecutor();
         }

      }

      public void sendBinary(ByteBuffer data) throws IOException {
         Utils.checkNotNull(data, "data");
         this.session.getDebugContext().appendLogMessage(TyrusRemoteEndpoint.LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_OUT, "Sending binary message");
         Future future = this.webSocket.sendBinary(Utils.getRemainingArray(data));

         try {
            this.processFuture(future);
         } finally {
            this.session.restartIdleTimeoutExecutor();
         }

      }

      public void sendText(String partialMessage, boolean isLast) throws IOException {
         Utils.checkNotNull(partialMessage, "partialMessage");
         this.session.getDebugContext().appendLogMessage(TyrusRemoteEndpoint.LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_OUT, "Sending partial text message: ", partialMessage);
         Future future = this.webSocket.sendText(partialMessage, isLast);

         try {
            this.processFuture(future);
         } finally {
            this.session.restartIdleTimeoutExecutor();
         }

      }

      public void sendBinary(ByteBuffer partialByte, boolean isLast) throws IOException {
         Utils.checkNotNull(partialByte, "partialByte");
         this.session.getDebugContext().appendLogMessage(TyrusRemoteEndpoint.LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_OUT, "Sending partial binary message");
         Future future = this.webSocket.sendBinary(Utils.getRemainingArray(partialByte), isLast);

         try {
            this.processFuture(future);
         } finally {
            this.session.restartIdleTimeoutExecutor();
         }

      }

      private void processFuture(Future future) throws IOException {
         try {
            future.get();
         } catch (InterruptedException var3) {
            Thread.currentThread().interrupt();
         } catch (ExecutionException var4) {
            if (var4.getCause() instanceof IOException) {
               throw (IOException)var4.getCause();
            }

            throw new IOException(var4.getCause());
         }

      }

      public void sendObject(Object data) throws IOException, EncodeException {
         Utils.checkNotNull(data, "data");
         Future future = this.sendSyncObject(data);

         try {
            future.get();
         } catch (InterruptedException var4) {
            Thread.currentThread().interrupt();
         } catch (ExecutionException var5) {
            if (var5.getCause() instanceof IOException) {
               throw (IOException)var5.getCause();
            }

            if (var5.getCause() instanceof EncodeException) {
               throw (EncodeException)var5.getCause();
            }

            throw new IOException(var5.getCause());
         }

         this.session.restartIdleTimeoutExecutor();
      }

      public OutputStream getSendStream() throws IOException {
         return new OutputStreamToAsyncBinaryAdapter(this.webSocket);
      }

      public Writer getSendWriter() throws IOException {
         return new WriterToAsyncTextAdapter(this.webSocket);
      }
   }
}
