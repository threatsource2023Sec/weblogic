package org.glassfish.tyrus.core.cluster;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.MessageHandler;
import javax.websocket.RemoteEndpoint;
import javax.websocket.SendHandler;
import javax.websocket.SendResult;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import org.glassfish.tyrus.core.TyrusEndpointWrapper;
import org.glassfish.tyrus.core.Utils;

public class RemoteSession implements Session, DistributedSession {
   private static final Integer SYNC_SEND_TIMEOUT = 30;
   private final RemoteEndpoint.Basic basicRemote;
   private final RemoteEndpoint.Async asyncRemote;
   private final String sessionId;
   private final String connectionId;
   private final ClusterContext clusterContext;
   private final Map distributedPropertyMap;
   private final TyrusEndpointWrapper endpointWrapper;

   public RemoteSession(final String sessionId, final ClusterContext clusterContext, Map distributedPropertyMap, final TyrusEndpointWrapper endpointWrapper, final Session session) {
      this.sessionId = sessionId;
      this.clusterContext = clusterContext;
      this.distributedPropertyMap = distributedPropertyMap;
      this.endpointWrapper = endpointWrapper;
      this.connectionId = distributedPropertyMap.get(RemoteSession.DistributedMapKey.CONNECTION_ID).toString();
      this.basicRemote = new RemoteEndpoint.Basic() {
         public void sendText(String text) throws IOException {
            Utils.checkNotNull(text, "text");
            Future future = clusterContext.sendText(sessionId, text);
            this.processFuture(future);
         }

         public void sendBinary(ByteBuffer data) throws IOException {
            Utils.checkNotNull(data, "data");
            Future future = clusterContext.sendBinary(sessionId, Utils.getRemainingArray(data));
            this.processFuture(future);
         }

         public void sendText(String partialMessage, boolean isLast) throws IOException {
            Utils.checkNotNull(partialMessage, "partialMessage");
            Future future = clusterContext.sendText(sessionId, partialMessage, isLast);
            this.processFuture(future);
         }

         public void sendBinary(ByteBuffer partialByte, boolean isLast) throws IOException {
            Utils.checkNotNull(partialByte, "partialByte");
            Future future = clusterContext.sendBinary(sessionId, Utils.getRemainingArray(partialByte), isLast);
            this.processFuture(future);
         }

         private void processFuture(Future future) throws IOException {
            try {
               future.get((long)RemoteSession.SYNC_SEND_TIMEOUT, TimeUnit.SECONDS);
            } catch (InterruptedException var3) {
               Thread.currentThread().interrupt();
            } catch (ExecutionException var4) {
               if (var4.getCause() instanceof IOException) {
                  throw (IOException)var4.getCause();
               }

               throw new IOException(var4.getCause());
            } catch (TimeoutException var5) {
               throw new IOException(var5.getCause());
            }

         }

         public void sendPing(ByteBuffer applicationData) throws IOException, IllegalArgumentException {
            if (applicationData != null && applicationData.remaining() > 125) {
               throw new IllegalArgumentException("Ping applicationData exceeded the maximum allowed payload of 125 bytes.");
            } else {
               clusterContext.sendPing(sessionId, Utils.getRemainingArray(applicationData));
            }
         }

         public void sendPong(ByteBuffer applicationData) throws IOException, IllegalArgumentException {
            if (applicationData != null && applicationData.remaining() > 125) {
               throw new IllegalArgumentException("Pong applicationData exceeded the maximum allowed payload of 125 bytes.");
            } else {
               clusterContext.sendPong(sessionId, Utils.getRemainingArray(applicationData));
            }
         }

         public void sendObject(Object data) throws IOException, EncodeException {
            Utils.checkNotNull(data, "data");
            Object toSend = endpointWrapper.doEncode(session, data);
            Future future;
            if (toSend instanceof String) {
               future = clusterContext.sendText(sessionId, (String)toSend);
            } else if (toSend instanceof ByteBuffer) {
               future = clusterContext.sendBinary(sessionId, Utils.getRemainingArray((ByteBuffer)toSend));
            } else if (toSend instanceof StringWriter) {
               StringWriter writer = (StringWriter)toSend;
               StringBuffer sb = writer.getBuffer();
               future = clusterContext.sendText(sessionId, sb.toString());
            } else {
               if (!(toSend instanceof ByteArrayOutputStream)) {
                  return;
               }

               ByteArrayOutputStream baos = (ByteArrayOutputStream)toSend;
               future = clusterContext.sendBinary(sessionId, baos.toByteArray());
            }

            this.processFuture(future);
         }

         public OutputStream getSendStream() throws IOException {
            return new OutputStream() {
               public void write(byte[] b, int off, int len) throws IOException {
                  if (b == null) {
                     throw new NullPointerException();
                  } else if (off >= 0 && off <= b.length && len >= 0 && off + len <= b.length && off + len >= 0) {
                     if (len != 0) {
                        byte[] toSend = new byte[len];
                        System.arraycopy(b, off, toSend, 0, len);
                        Future future = clusterContext.sendBinary(sessionId, toSend, false);

                        try {
                           future.get();
                        } catch (InterruptedException var7) {
                           Thread.currentThread().interrupt();
                        } catch (ExecutionException var8) {
                           if (var8.getCause() instanceof IOException) {
                              throw (IOException)var8.getCause();
                           }

                           throw new IOException(var8.getCause());
                        }

                     }
                  } else {
                     throw new IndexOutOfBoundsException();
                  }
               }

               public void write(int i) throws IOException {
                  byte[] byteArray = new byte[]{(byte)i};
                  this.write(byteArray, 0, byteArray.length);
               }

               public void flush() throws IOException {
               }

               public void close() throws IOException {
                  clusterContext.sendBinary(sessionId, new byte[0], true);
               }
            };
         }

         public Writer getSendWriter() throws IOException {
            return new Writer() {
               private String buffer = null;

               private void sendBuffer(boolean last) {
                  clusterContext.sendText(sessionId, this.buffer, last);
               }

               public void write(char[] chars, int index, int len) throws IOException {
                  if (this.buffer != null) {
                     this.sendBuffer(false);
                  }

                  this.buffer = (new String(chars)).substring(index, index + len);
               }

               public void flush() throws IOException {
                  this.sendBuffer(false);
                  this.buffer = null;
               }

               public void close() throws IOException {
                  this.sendBuffer(true);
               }
            };
         }

         public void setBatchingAllowed(boolean allowed) throws IOException {
         }

         public boolean getBatchingAllowed() {
            return false;
         }

         public void flushBatch() throws IOException {
         }
      };
      this.asyncRemote = new RemoteEndpoint.Async() {
         public long getSendTimeout() {
            return 0L;
         }

         public void setSendTimeout(long timeoutmillis) {
         }

         public void sendText(String text, SendHandler handler) {
            Utils.checkNotNull(text, "text");
            Utils.checkNotNull(handler, "handler");
            clusterContext.sendText(sessionId, text, handler);
         }

         public Future sendText(String text) {
            Utils.checkNotNull(text, "text");
            return clusterContext.sendText(sessionId, text);
         }

         public Future sendBinary(ByteBuffer data) {
            Utils.checkNotNull(data, "data");
            return clusterContext.sendBinary(sessionId, Utils.getRemainingArray(data));
         }

         public void sendBinary(ByteBuffer data, SendHandler handler) {
            Utils.checkNotNull(data, "data");
            Utils.checkNotNull(handler, "handler");
            clusterContext.sendBinary(sessionId, Utils.getRemainingArray(data), handler);
         }

         public Future sendObject(Object data) {
            Utils.checkNotNull(data, "data");

            Object toSend;
            try {
               toSend = endpointWrapper.doEncode(session, data);
            } catch (final Exception var6) {
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

                  public Void get() throws InterruptedException, ExecutionException {
                     throw new ExecutionException(var6);
                  }

                  public Void get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                     throw new ExecutionException(var6);
                  }
               };
            }

            Future future;
            if (toSend instanceof String) {
               future = clusterContext.sendText(sessionId, (String)toSend);
            } else if (toSend instanceof ByteBuffer) {
               future = clusterContext.sendBinary(sessionId, Utils.getRemainingArray((ByteBuffer)toSend));
            } else if (toSend instanceof StringWriter) {
               StringWriter writer = (StringWriter)toSend;
               StringBuffer sb = writer.getBuffer();
               future = clusterContext.sendText(sessionId, sb.toString());
            } else if (toSend instanceof ByteArrayOutputStream) {
               ByteArrayOutputStream baos = (ByteArrayOutputStream)toSend;
               future = clusterContext.sendBinary(sessionId, baos.toByteArray());
            } else {
               future = null;
            }

            return future;
         }

         public void sendObject(Object data, SendHandler handler) {
            Utils.checkNotNull(data, "data");
            if (data instanceof String) {
               clusterContext.sendText(sessionId, (String)data, handler);
            } else {
               Object toSend;
               try {
                  toSend = endpointWrapper.doEncode(session, data);
               } catch (Throwable var6) {
                  handler.onResult(new SendResult(var6));
                  return;
               }

               if (toSend instanceof String) {
                  clusterContext.sendText(sessionId, (String)toSend, handler);
               } else if (toSend instanceof ByteBuffer) {
                  clusterContext.sendBinary(sessionId, Utils.getRemainingArray((ByteBuffer)toSend), handler);
               } else if (toSend instanceof StringWriter) {
                  StringWriter writer = (StringWriter)toSend;
                  StringBuffer sb = writer.getBuffer();
                  clusterContext.sendText(sessionId, sb.toString(), handler);
               } else if (toSend instanceof ByteArrayOutputStream) {
                  ByteArrayOutputStream baos = (ByteArrayOutputStream)toSend;
                  clusterContext.sendBinary(sessionId, baos.toByteArray(), handler);
               }
            }

         }

         public void sendPing(ByteBuffer applicationData) throws IOException, IllegalArgumentException {
            if (applicationData != null && applicationData.remaining() > 125) {
               throw new IllegalArgumentException("Ping applicationData exceeded the maximum allowed payload of 125 bytes.");
            } else {
               clusterContext.sendPing(sessionId, Utils.getRemainingArray(applicationData));
            }
         }

         public void sendPong(ByteBuffer applicationData) throws IOException, IllegalArgumentException {
            if (applicationData != null && applicationData.remaining() > 125) {
               throw new IllegalArgumentException("Pong applicationData exceeded the maximum allowed payload of 125 bytes.");
            } else {
               clusterContext.sendPong(sessionId, Utils.getRemainingArray(applicationData));
            }
         }

         public void setBatchingAllowed(boolean allowed) throws IOException {
         }

         public boolean getBatchingAllowed() {
            return false;
         }

         public void flushBatch() throws IOException {
         }
      };
   }

   public String getProtocolVersion() {
      return "13";
   }

   public String getNegotiatedSubprotocol() {
      return (String)this.distributedPropertyMap.get(RemoteSession.DistributedMapKey.NEGOTIATED_SUBPROTOCOL);
   }

   public List getNegotiatedExtensions() {
      return (List)this.distributedPropertyMap.get(RemoteSession.DistributedMapKey.NEGOTIATED_EXTENSIONS);
   }

   public boolean isSecure() {
      return (Boolean)this.distributedPropertyMap.get(RemoteSession.DistributedMapKey.SECURE);
   }

   public boolean isOpen() {
      return this.clusterContext.isSessionOpen(this.sessionId, this.endpointWrapper.getEndpointPath());
   }

   public long getMaxIdleTimeout() {
      return (Long)this.distributedPropertyMap.get(RemoteSession.DistributedMapKey.MAX_IDLE_TIMEOUT);
   }

   public int getMaxBinaryMessageBufferSize() {
      return (Integer)this.distributedPropertyMap.get(RemoteSession.DistributedMapKey.MAX_BINARY_MESSAGE_BUFFER_SIZE);
   }

   public int getMaxTextMessageBufferSize() {
      return (Integer)this.distributedPropertyMap.get(RemoteSession.DistributedMapKey.MAX_TEXT_MESSAGE_BUFFER_SIZE);
   }

   public RemoteEndpoint.Async getAsyncRemote() {
      return this.asyncRemote;
   }

   public RemoteEndpoint.Basic getBasicRemote() {
      return this.basicRemote;
   }

   public String getId() {
      return this.sessionId;
   }

   public void close() throws IOException {
      this.clusterContext.close(this.sessionId);
   }

   public void close(CloseReason closeReason) throws IOException {
      this.clusterContext.close(this.sessionId, closeReason);
   }

   public URI getRequestURI() {
      return (URI)this.distributedPropertyMap.get(RemoteSession.DistributedMapKey.REQUEST_URI);
   }

   public Map getRequestParameterMap() {
      return (Map)this.distributedPropertyMap.get(RemoteSession.DistributedMapKey.REQUEST_PARAMETER_MAP);
   }

   public String getQueryString() {
      return (String)this.distributedPropertyMap.get(RemoteSession.DistributedMapKey.QUERY_STRING);
   }

   public Map getPathParameters() {
      return (Map)this.distributedPropertyMap.get(RemoteSession.DistributedMapKey.PATH_PARAMETERS);
   }

   public Map getUserProperties() {
      throw new UnsupportedOperationException();
   }

   public Map getDistributedProperties() {
      return this.clusterContext.getDistributedUserProperties(this.connectionId);
   }

   public Principal getUserPrincipal() {
      return (Principal)this.distributedPropertyMap.get(RemoteSession.DistributedMapKey.USER_PRINCIPAL);
   }

   public String toString() {
      return "RemoteSession{sessionId='" + this.sessionId + '\'' + ", clusterContext=" + this.clusterContext + '}';
   }

   public WebSocketContainer getContainer() {
      throw new UnsupportedOperationException();
   }

   public void addMessageHandler(MessageHandler handler) throws IllegalStateException {
      throw new UnsupportedOperationException();
   }

   public void addMessageHandler(Class clazz, MessageHandler.Whole handler) {
      throw new UnsupportedOperationException();
   }

   public void addMessageHandler(Class clazz, MessageHandler.Partial handler) {
      throw new UnsupportedOperationException();
   }

   public Set getMessageHandlers() {
      throw new UnsupportedOperationException();
   }

   public void removeMessageHandler(MessageHandler handler) {
      throw new UnsupportedOperationException();
   }

   public void setMaxIdleTimeout(long milliseconds) {
      throw new UnsupportedOperationException();
   }

   public void setMaxBinaryMessageBufferSize(int length) {
      throw new UnsupportedOperationException();
   }

   public void setMaxTextMessageBufferSize(int length) {
      throw new UnsupportedOperationException();
   }

   public Set getOpenSessions() {
      throw new UnsupportedOperationException();
   }

   public static enum DistributedMapKey implements Serializable {
      NEGOTIATED_SUBPROTOCOL("negotiatedSubprotocol"),
      NEGOTIATED_EXTENSIONS("negotiatedExtensions"),
      SECURE("secure"),
      MAX_IDLE_TIMEOUT("maxIdleTimeout"),
      MAX_BINARY_MESSAGE_BUFFER_SIZE("maxBinaryBufferSize"),
      MAX_TEXT_MESSAGE_BUFFER_SIZE("maxTextBufferSize"),
      REQUEST_URI("requestURI"),
      REQUEST_PARAMETER_MAP("requestParameterMap"),
      QUERY_STRING("queryString"),
      PATH_PARAMETERS("pathParameters"),
      USER_PRINCIPAL("userPrincipal"),
      CONNECTION_ID("connectionId");

      private final String key;

      private DistributedMapKey(String key) {
         this.key = key;
      }

      public String toString() {
         return this.key;
      }
   }
}
