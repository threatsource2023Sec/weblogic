package org.glassfish.tyrus.core;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.DecodeException;
import javax.websocket.MessageHandler;
import javax.websocket.PongMessage;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.CloseReason.CloseCodes;
import org.glassfish.tyrus.core.cluster.ClusterContext;
import org.glassfish.tyrus.core.cluster.DistributedSession;
import org.glassfish.tyrus.core.cluster.RemoteSession;
import org.glassfish.tyrus.core.cluster.SessionEventListener;
import org.glassfish.tyrus.core.coder.CoderWrapper;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public class TyrusSession implements Session, DistributedSession {
   private static final Logger LOGGER = Logger.getLogger(TyrusSession.class.getName());
   private final WebSocketContainer container;
   private final TyrusEndpointWrapper endpointWrapper;
   private final TyrusRemoteEndpoint.Basic basicRemote;
   private final TyrusRemoteEndpoint.Async asyncRemote;
   private final boolean isSecure;
   private final URI requestURI;
   private final String queryString;
   private final Map pathParameters;
   private final Principal userPrincipal;
   private final Map requestParameterMap;
   private final Object idleTimeoutLock = new Object();
   private final String id;
   private final String connectionId;
   private final Map userProperties;
   private final MessageHandlerManager handlerManager;
   private final AtomicReference state;
   private final TextBuffer textBuffer;
   private final BinaryBuffer binaryBuffer;
   private final List negotiatedExtensions;
   private final String negotiatedSubprotocol;
   private final String remoteAddr;
   private final DebugContext debugContext;
   private final Map distributedPropertyMap;
   private final Map distributedUserProperties;
   private volatile long maxIdleTimeout;
   private volatile ScheduledFuture idleTimeoutFuture;
   private int maxBinaryMessageBufferSize;
   private int maxTextMessageBufferSize;
   private ScheduledExecutorService service;
   private ReaderBuffer readerBuffer;
   private InputStreamBuffer inputStreamBuffer;
   private volatile long heartbeatInterval;
   private volatile ScheduledFuture heartbeatTask;

   TyrusSession(WebSocketContainer container, TyrusWebSocket socket, TyrusEndpointWrapper endpointWrapper, String subprotocol, List extensions, boolean isSecure, URI requestURI, String queryString, Map pathParameters, Principal principal, Map requestParameterMap, ClusterContext clusterContext, String connectionId, String remoteAddr, DebugContext debugContext) {
      this.state = new AtomicReference(TyrusSession.State.RUNNING);
      this.textBuffer = new TextBuffer();
      this.binaryBuffer = new BinaryBuffer();
      this.maxIdleTimeout = 0L;
      this.idleTimeoutFuture = null;
      this.maxBinaryMessageBufferSize = Integer.MAX_VALUE;
      this.maxTextMessageBufferSize = Integer.MAX_VALUE;
      this.container = container;
      this.endpointWrapper = endpointWrapper;
      this.negotiatedExtensions = extensions == null ? Collections.emptyList() : Collections.unmodifiableList(extensions);
      this.negotiatedSubprotocol = subprotocol == null ? "" : subprotocol;
      this.isSecure = isSecure;
      this.requestURI = requestURI;
      this.queryString = queryString;
      this.pathParameters = pathParameters == null ? Collections.emptyMap() : Collections.unmodifiableMap(new HashMap(pathParameters));
      this.basicRemote = new TyrusRemoteEndpoint.Basic(this, socket, endpointWrapper);
      this.asyncRemote = new TyrusRemoteEndpoint.Async(this, socket, endpointWrapper);
      this.handlerManager = MessageHandlerManager.fromDecoderInstances(endpointWrapper.getDecoders());
      this.userPrincipal = principal;
      this.requestParameterMap = requestParameterMap == null ? Collections.emptyMap() : Collections.unmodifiableMap(new HashMap(requestParameterMap));
      this.connectionId = connectionId;
      this.remoteAddr = remoteAddr;
      this.debugContext = debugContext;
      if (container != null) {
         this.maxTextMessageBufferSize = container.getDefaultMaxTextMessageBufferSize();
         this.maxBinaryMessageBufferSize = container.getDefaultMaxBinaryMessageBufferSize();
         this.service = ((ExecutorServiceProvider)container).getScheduledExecutorService();
         this.setMaxIdleTimeout(container.getDefaultMaxSessionIdleTimeout());
      }

      if (clusterContext != null) {
         this.id = clusterContext.createSessionId();
         this.distributedPropertyMap = clusterContext.getDistributedSessionProperties(this.id);
         this.distributedPropertyMap.put(RemoteSession.DistributedMapKey.NEGOTIATED_SUBPROTOCOL, this.negotiatedSubprotocol);
         this.distributedPropertyMap.put(RemoteSession.DistributedMapKey.NEGOTIATED_EXTENSIONS, this.negotiatedExtensions);
         this.distributedPropertyMap.put(RemoteSession.DistributedMapKey.SECURE, isSecure);
         this.distributedPropertyMap.put(RemoteSession.DistributedMapKey.MAX_IDLE_TIMEOUT, this.maxIdleTimeout);
         this.distributedPropertyMap.put(RemoteSession.DistributedMapKey.MAX_BINARY_MESSAGE_BUFFER_SIZE, this.maxBinaryMessageBufferSize);
         this.distributedPropertyMap.put(RemoteSession.DistributedMapKey.MAX_TEXT_MESSAGE_BUFFER_SIZE, this.maxTextMessageBufferSize);
         this.distributedPropertyMap.put(RemoteSession.DistributedMapKey.REQUEST_URI, requestURI);
         this.distributedPropertyMap.put(RemoteSession.DistributedMapKey.REQUEST_PARAMETER_MAP, requestParameterMap);
         this.distributedPropertyMap.put(RemoteSession.DistributedMapKey.QUERY_STRING, queryString == null ? "" : queryString);
         this.distributedPropertyMap.put(RemoteSession.DistributedMapKey.PATH_PARAMETERS, this.pathParameters);
         this.distributedPropertyMap.put(RemoteSession.DistributedMapKey.CONNECTION_ID, this.connectionId);
         if (this.userPrincipal != null) {
            this.distributedPropertyMap.put(RemoteSession.DistributedMapKey.USER_PRINCIPAL, this.userPrincipal);
         }

         this.distributedUserProperties = clusterContext.getDistributedUserProperties(connectionId);
         clusterContext.registerSession(this.id, endpointWrapper.getEndpointPath(), new SessionEventListener(this));
      } else {
         this.id = UUID.randomUUID().toString();
         this.distributedPropertyMap = null;
         this.distributedUserProperties = new HashMap();
      }

      debugContext.setSessionId(this.id);
      this.userProperties = new HashMap();
   }

   public String getProtocolVersion() {
      return "13";
   }

   public String getNegotiatedSubprotocol() {
      return this.negotiatedSubprotocol;
   }

   public RemoteEndpoint.Async getAsyncRemote() {
      this.checkConnectionState(TyrusSession.State.CLOSED);
      return this.asyncRemote;
   }

   public RemoteEndpoint.Basic getBasicRemote() {
      this.checkConnectionState(TyrusSession.State.CLOSED);
      return this.basicRemote;
   }

   public boolean isOpen() {
      return this.state.get() != TyrusSession.State.CLOSED;
   }

   public void close() throws IOException {
      this.cleanAfterClose();
      this.changeStateToClosed();
      this.basicRemote.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, (String)null));
   }

   public void close(CloseReason closeReason) throws IOException {
      this.cleanAfterClose();
      this.checkConnectionState(TyrusSession.State.CLOSED);
      this.changeStateToClosed();
      this.basicRemote.close(closeReason);
   }

   public int getMaxBinaryMessageBufferSize() {
      return this.maxBinaryMessageBufferSize;
   }

   public void setMaxBinaryMessageBufferSize(int maxBinaryMessageBufferSize) {
      this.checkConnectionState(TyrusSession.State.CLOSED);
      this.maxBinaryMessageBufferSize = maxBinaryMessageBufferSize;
      if (this.distributedPropertyMap != null) {
         this.distributedPropertyMap.put(RemoteSession.DistributedMapKey.MAX_BINARY_MESSAGE_BUFFER_SIZE, maxBinaryMessageBufferSize);
      }

   }

   public int getMaxTextMessageBufferSize() {
      return this.maxTextMessageBufferSize;
   }

   public void setMaxTextMessageBufferSize(int maxTextMessageBufferSize) {
      this.checkConnectionState(TyrusSession.State.CLOSED);
      this.maxTextMessageBufferSize = maxTextMessageBufferSize;
      if (this.distributedPropertyMap != null) {
         this.distributedPropertyMap.put(RemoteSession.DistributedMapKey.MAX_TEXT_MESSAGE_BUFFER_SIZE, maxTextMessageBufferSize);
      }

   }

   public Set getOpenSessions() {
      Set openSessions = new HashSet();
      openSessions.addAll(this.endpointWrapper.getOpenSessions());
      return Collections.unmodifiableSet(openSessions);
   }

   public Set getRemoteSessions() {
      return this.endpointWrapper.getRemoteSessions();
   }

   public Set getAllSessions() {
      Set result = new HashSet();
      result.addAll(this.endpointWrapper.getOpenSessions());
      result.addAll(this.endpointWrapper.getRemoteSessions());
      return Collections.unmodifiableSet(result);
   }

   public List getNegotiatedExtensions() {
      return this.negotiatedExtensions;
   }

   public long getMaxIdleTimeout() {
      return this.maxIdleTimeout;
   }

   public void setMaxIdleTimeout(long maxIdleTimeout) {
      this.checkConnectionState(TyrusSession.State.CLOSED);
      this.maxIdleTimeout = maxIdleTimeout;
      this.restartIdleTimeoutExecutor();
      if (this.distributedPropertyMap != null) {
         this.distributedPropertyMap.put(RemoteSession.DistributedMapKey.MAX_IDLE_TIMEOUT, maxIdleTimeout);
      }

   }

   public boolean isSecure() {
      return this.isSecure;
   }

   public WebSocketContainer getContainer() {
      return this.container;
   }

   /** @deprecated */
   public void addMessageHandler(MessageHandler handler) {
      this.checkConnectionState(TyrusSession.State.CLOSED);
      synchronized(this.handlerManager) {
         this.handlerManager.addMessageHandler(handler);
      }
   }

   public void addMessageHandler(Class clazz, MessageHandler.Whole handler) {
      this.checkConnectionState(TyrusSession.State.CLOSED);
      synchronized(this.handlerManager) {
         this.handlerManager.addMessageHandler(clazz, handler);
      }
   }

   public void addMessageHandler(Class clazz, MessageHandler.Partial handler) {
      this.checkConnectionState(TyrusSession.State.CLOSED);
      synchronized(this.handlerManager) {
         this.handlerManager.addMessageHandler(clazz, handler);
      }
   }

   public Set getMessageHandlers() {
      synchronized(this.handlerManager) {
         return this.handlerManager.getMessageHandlers();
      }
   }

   public void removeMessageHandler(MessageHandler handler) {
      this.checkConnectionState(TyrusSession.State.CLOSED);
      synchronized(this.handlerManager) {
         this.handlerManager.removeMessageHandler(handler);
      }
   }

   public URI getRequestURI() {
      return this.requestURI;
   }

   public Map getRequestParameterMap() {
      return this.requestParameterMap;
   }

   public Map getPathParameters() {
      return this.pathParameters;
   }

   public Map getUserProperties() {
      return this.userProperties;
   }

   public Map getDistributedProperties() {
      return this.distributedUserProperties;
   }

   public String getQueryString() {
      return this.queryString;
   }

   public String getId() {
      return this.id;
   }

   public Principal getUserPrincipal() {
      return this.userPrincipal;
   }

   public Map broadcast(String message) {
      return this.endpointWrapper.broadcast(message);
   }

   public Map broadcast(ByteBuffer message) {
      return this.endpointWrapper.broadcast(message);
   }

   public long getHeartbeatInterval() {
      return this.heartbeatInterval;
   }

   public void setHeartbeatInterval(long heartbeatInterval) {
      this.checkConnectionState(TyrusSession.State.CLOSED);
      this.heartbeatInterval = heartbeatInterval;
      this.cancelHeartBeatTask();
      if (heartbeatInterval >= 1L) {
         this.heartbeatTask = this.service.scheduleAtFixedRate(new HeartbeatCommand(), heartbeatInterval, heartbeatInterval, TimeUnit.MILLISECONDS);
      }
   }

   void restartIdleTimeoutExecutor() {
      if (this.maxIdleTimeout < 1L) {
         synchronized(this.idleTimeoutLock) {
            if (this.idleTimeoutFuture != null) {
               this.idleTimeoutFuture.cancel(true);
            }

         }
      } else {
         synchronized(this.idleTimeoutLock) {
            if (this.idleTimeoutFuture != null) {
               this.idleTimeoutFuture.cancel(false);
            }

            this.idleTimeoutFuture = this.service.schedule(new IdleTimeoutCommand(), this.getMaxIdleTimeout(), TimeUnit.MILLISECONDS);
         }
      }
   }

   private void checkConnectionState(State... states) {
      State sessionState = (State)this.state.get();
      State[] var3 = states;
      int var4 = states.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         State s = var3[var5];
         if (sessionState == s) {
            throw new IllegalStateException(LocalizationMessages.CONNECTION_HAS_BEEN_CLOSED());
         }
      }

   }

   private void checkMessageSize(Object message, long maxMessageSize) {
      if (maxMessageSize != -1L) {
         long messageSize = (long)(message instanceof String ? ((String)message).getBytes(Charset.defaultCharset()).length : ((ByteBuffer)message).remaining());
         if (messageSize > maxMessageSize) {
            throw new MessageTooBigException(LocalizationMessages.MESSAGE_TOO_LONG(maxMessageSize, messageSize));
         }
      }

   }

   void notifyMessageHandlers(Object message, List availableDecoders) throws DecodeException, IOException {
      boolean decoded = false;
      if (availableDecoders.isEmpty()) {
         LOGGER.warning(LocalizationMessages.NO_DECODER_FOUND());
      }

      List orderedMessageHandlers;
      synchronized(this.handlerManager) {
         orderedMessageHandlers = this.handlerManager.getOrderedWholeMessageHandlers();
      }

      Iterator var5 = availableDecoders.iterator();

      while(var5.hasNext()) {
         CoderWrapper decoder = (CoderWrapper)var5.next();
         Iterator var7 = orderedMessageHandlers.iterator();

         while(var7.hasNext()) {
            Map.Entry entry = (Map.Entry)var7.next();
            MessageHandler mh = (MessageHandler)entry.getValue();
            Class type = (Class)entry.getKey();
            if (type.isAssignableFrom(decoder.getType())) {
               if (mh instanceof BasicMessageHandler) {
                  this.checkMessageSize(message, ((BasicMessageHandler)mh).getMaxMessageSize());
               }

               Object object = this.endpointWrapper.decodeCompleteMessage(this, message, type, decoder);
               if (object != null) {
                  State currentState = (State)this.state.get();
                  if (currentState != TyrusSession.State.CLOSED) {
                     ((MessageHandler.Whole)mh).onMessage(object);
                  }

                  decoded = true;
                  break;
               }
            }
         }

         if (decoded) {
            break;
         }
      }

   }

   MessageHandler.Whole getMessageHandler(Class c) {
      List orderedMessageHandlers;
      synchronized(this.handlerManager) {
         orderedMessageHandlers = this.handlerManager.getOrderedWholeMessageHandlers();
      }

      Iterator var3 = orderedMessageHandlers.iterator();

      Map.Entry entry;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         entry = (Map.Entry)var3.next();
      } while(!((Class)entry.getKey()).equals(c));

      return (MessageHandler.Whole)entry.getValue();
   }

   void notifyMessageHandlers(Object message, boolean last) {
      boolean handled = false;
      Iterator var4 = this.getMessageHandlers().iterator();

      while(var4.hasNext()) {
         MessageHandler handler = (MessageHandler)var4.next();
         if (handler instanceof MessageHandler.Partial && MessageHandlerManager.getHandlerType(handler).isAssignableFrom(message.getClass())) {
            if (handler instanceof AsyncMessageHandler) {
               this.checkMessageSize(message, ((AsyncMessageHandler)handler).getMaxMessageSize());
            }

            State currentState = (State)this.state.get();
            if (currentState != TyrusSession.State.CLOSED) {
               ((MessageHandler.Partial)handler).onMessage(message, last);
            }

            handled = true;
            break;
         }
      }

      if (!handled) {
         if (message instanceof ByteBuffer) {
            this.notifyMessageHandlers(((ByteBuffer)message).array(), last);
         } else {
            LOGGER.warning(LocalizationMessages.UNHANDLED_TEXT_MESSAGE(this));
         }
      }

   }

   void notifyPongHandler(PongMessage pongMessage) {
      Set messageHandlers = this.getMessageHandlers();
      Iterator var3 = messageHandlers.iterator();

      while(var3.hasNext()) {
         MessageHandler handler = (MessageHandler)var3.next();
         if (MessageHandlerManager.getHandlerType(handler).equals(PongMessage.class)) {
            ((MessageHandler.Whole)handler).onMessage(pongMessage);
         }
      }

   }

   boolean isWholeTextHandlerPresent() {
      return this.handlerManager.isWholeTextHandlerPresent();
   }

   boolean isWholeBinaryHandlerPresent() {
      return this.handlerManager.isWholeBinaryHandlerPresent();
   }

   boolean isPartialTextHandlerPresent() {
      return this.handlerManager.isPartialTextHandlerPresent();
   }

   boolean isPartialBinaryHandlerPresent() {
      return this.handlerManager.isPartialBinaryHandlerPresent();
   }

   boolean isReaderHandlerPresent() {
      return this.handlerManager.isReaderHandlerPresent();
   }

   boolean isInputStreamHandlerPresent() {
      return this.handlerManager.isInputStreamHandlerPresent();
   }

   boolean isPongHandlerPresent() {
      return this.handlerManager.isPongHandlerPresent();
   }

   State getState() {
      return (State)this.state.get();
   }

   String getConnectionId() {
      return this.connectionId;
   }

   DebugContext getDebugContext() {
      return this.debugContext;
   }

   void setState(State state) {
      if (!state.equals(this.state.get())) {
         this.checkConnectionState(TyrusSession.State.CLOSED);
         this.state.set(state);
         if (state.equals(TyrusSession.State.CLOSED)) {
            this.cleanAfterClose();
         }
      }

   }

   TextBuffer getTextBuffer() {
      return this.textBuffer;
   }

   BinaryBuffer getBinaryBuffer() {
      return this.binaryBuffer;
   }

   ReaderBuffer getReaderBuffer() {
      return this.readerBuffer;
   }

   void setReaderBuffer(ReaderBuffer readerBuffer) {
      this.readerBuffer = readerBuffer;
   }

   InputStreamBuffer getInputStreamBuffer() {
      return this.inputStreamBuffer;
   }

   void setInputStreamBuffer(InputStreamBuffer inputStreamBuffer) {
      this.inputStreamBuffer = inputStreamBuffer;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("TyrusSession");
      sb.append("{uri=").append(this.requestURI);
      sb.append(", id='").append(this.id).append('\'');
      sb.append(", endpointWrapper=").append(this.endpointWrapper);
      sb.append('}');
      return sb.toString();
   }

   private void changeStateToClosed() {
      this.state.compareAndSet(TyrusSession.State.RUNNING, TyrusSession.State.CLOSED);
      this.state.compareAndSet(TyrusSession.State.RECEIVING_BINARY, TyrusSession.State.CLOSED);
      this.state.compareAndSet(TyrusSession.State.RECEIVING_TEXT, TyrusSession.State.CLOSED);
   }

   private void cancelHeartBeatTask() {
      if (this.heartbeatTask != null && !this.heartbeatTask.isCancelled()) {
         this.heartbeatTask.cancel(true);
      }

   }

   private void cleanAfterClose() {
      if (this.readerBuffer != null) {
         this.readerBuffer.onSessionClosed();
      }

      if (this.inputStreamBuffer != null) {
         this.inputStreamBuffer.onSessionClosed();
      }

      this.cancelHeartBeatTask();
   }

   public String getRemoteAddr() {
      return this.remoteAddr;
   }

   private class HeartbeatCommand implements Runnable {
      private HeartbeatCommand() {
      }

      public void run() {
         TyrusSession session = TyrusSession.this;
         if (session.isOpen() && session.getHeartbeatInterval() > 0L) {
            try {
               session.getBasicRemote().sendPong((ByteBuffer)null);
            } catch (IOException var3) {
               TyrusSession.LOGGER.log(Level.FINE, "Pong could not have been sent " + var3.getMessage());
            }
         } else {
            TyrusSession.this.cancelHeartBeatTask();
         }

      }

      // $FF: synthetic method
      HeartbeatCommand(Object x1) {
         this();
      }
   }

   private class IdleTimeoutCommand implements Runnable {
      private IdleTimeoutCommand() {
      }

      public void run() {
         TyrusSession session = TyrusSession.this;
         if (session.getMaxIdleTimeout() > 0L && session.isOpen()) {
            try {
               session.close(new CloseReason(CloseCodes.CLOSED_ABNORMALLY, LocalizationMessages.SESSION_CLOSED_IDLE_TIMEOUT()));
            } catch (IOException var3) {
               TyrusSession.LOGGER.log(Level.FINE, "Session could not been closed. " + var3.getMessage());
            }
         }

      }

      // $FF: synthetic method
      IdleTimeoutCommand(Object x1) {
         this();
      }
   }

   static enum State {
      RUNNING,
      RECEIVING_TEXT,
      RECEIVING_BINARY,
      CLOSED;
   }
}
