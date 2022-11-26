package org.glassfish.tyrus.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.PongMessage;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import org.glassfish.tyrus.core.cluster.BroadcastListener;
import org.glassfish.tyrus.core.cluster.ClusterContext;
import org.glassfish.tyrus.core.cluster.RemoteSession;
import org.glassfish.tyrus.core.coder.CoderWrapper;
import org.glassfish.tyrus.core.coder.InputStreamDecoder;
import org.glassfish.tyrus.core.coder.NoOpByteArrayCoder;
import org.glassfish.tyrus.core.coder.NoOpByteBufferCoder;
import org.glassfish.tyrus.core.coder.NoOpTextCoder;
import org.glassfish.tyrus.core.coder.PrimitiveDecoders;
import org.glassfish.tyrus.core.coder.ReaderDecoder;
import org.glassfish.tyrus.core.coder.ToStringEncoder;
import org.glassfish.tyrus.core.frame.BinaryFrame;
import org.glassfish.tyrus.core.frame.Frame;
import org.glassfish.tyrus.core.frame.TextFrame;
import org.glassfish.tyrus.core.frame.TyrusFrame;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.core.monitoring.EndpointEventListener;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.glassfish.tyrus.spi.UpgradeResponse;

public class TyrusEndpointWrapper {
   private static final Logger LOGGER = Logger.getLogger(TyrusEndpointWrapper.class.getName());
   private static final int MIN_SESSIONS_PER_THREAD = 16;
   private final WebSocketContainer container;
   private final String contextPath;
   private final String endpointPath;
   private final String serverEndpointPath;
   private final List decoders;
   private final List encoders;
   private final EndpointConfig configuration;
   private final Class endpointClass;
   private final Endpoint endpoint;
   private final Map webSocketToSession;
   private final Map clusteredSessions;
   private final ComponentProviderService componentProvider;
   private final ServerEndpointConfig.Configurator configurator;
   private final Method onOpen;
   private final Method onClose;
   private final Method onError;
   private final SessionListener sessionListener;
   private final EndpointEventListener endpointEventListener;
   private final boolean parallelBroadcastEnabled;
   private final boolean programmaticEndpoint;
   private final ClusterContext clusterContext;
   private static final Session dummySession = new Session() {
      public WebSocketContainer getContainer() {
         return null;
      }

      public void addMessageHandler(MessageHandler handler) throws IllegalStateException {
      }

      public void addMessageHandler(Class clazz, MessageHandler.Whole handler) {
      }

      public void addMessageHandler(Class clazz, MessageHandler.Partial handler) {
      }

      public Set getMessageHandlers() {
         return null;
      }

      public void removeMessageHandler(MessageHandler handler) {
      }

      public String getProtocolVersion() {
         return null;
      }

      public String getNegotiatedSubprotocol() {
         return null;
      }

      public List getNegotiatedExtensions() {
         return null;
      }

      public boolean isSecure() {
         return false;
      }

      public boolean isOpen() {
         return false;
      }

      public long getMaxIdleTimeout() {
         return 0L;
      }

      public void setMaxIdleTimeout(long milliseconds) {
      }

      public void setMaxBinaryMessageBufferSize(int length) {
      }

      public int getMaxBinaryMessageBufferSize() {
         return 0;
      }

      public void setMaxTextMessageBufferSize(int length) {
      }

      public int getMaxTextMessageBufferSize() {
         return 0;
      }

      public RemoteEndpoint.Async getAsyncRemote() {
         return null;
      }

      public RemoteEndpoint.Basic getBasicRemote() {
         return null;
      }

      public String getId() {
         return null;
      }

      public void close() throws IOException {
      }

      public void close(CloseReason closeReason) throws IOException {
      }

      public URI getRequestURI() {
         return null;
      }

      public Map getRequestParameterMap() {
         return null;
      }

      public String getQueryString() {
         return null;
      }

      public Map getPathParameters() {
         return null;
      }

      public Map getUserProperties() {
         return null;
      }

      public Principal getUserPrincipal() {
         return null;
      }

      public Set getOpenSessions() {
         return null;
      }
   };

   public TyrusEndpointWrapper(Class endpointClass, EndpointConfig configuration, ComponentProviderService componentProvider, WebSocketContainer container, String contextPath, ServerEndpointConfig.Configurator configurator, SessionListener sessionListener, ClusterContext clusterContext, EndpointEventListener endpointEventListener, Boolean parallelBroadcastEnabled) throws DeploymentException {
      this((Endpoint)null, endpointClass, configuration, componentProvider, container, contextPath, configurator, sessionListener, clusterContext, endpointEventListener, parallelBroadcastEnabled);
   }

   public TyrusEndpointWrapper(Endpoint endpoint, EndpointConfig configuration, ComponentProviderService componentProvider, WebSocketContainer container, String contextPath, ServerEndpointConfig.Configurator configurator, SessionListener sessionListener, ClusterContext clusterContext, EndpointEventListener endpointEventListener, Boolean parallelBroadcastEnabled) throws DeploymentException {
      this(endpoint, (Class)null, configuration, componentProvider, container, contextPath, configurator, sessionListener, clusterContext, endpointEventListener, parallelBroadcastEnabled);
   }

   private TyrusEndpointWrapper(Endpoint endpoint, Class endpointClass, EndpointConfig configuration, ComponentProviderService componentProvider, WebSocketContainer container, String contextPath, final ServerEndpointConfig.Configurator configurator, SessionListener sessionListener, final ClusterContext clusterContext, EndpointEventListener endpointEventListener, Boolean parallelBroadcastEnabled) throws DeploymentException {
      this.decoders = new ArrayList();
      this.encoders = new ArrayList();
      this.webSocketToSession = new ConcurrentHashMap();
      this.clusteredSessions = new ConcurrentHashMap();
      this.endpointClass = endpointClass;
      this.endpoint = endpoint;
      this.programmaticEndpoint = endpoint != null;
      this.container = container;
      this.contextPath = contextPath;
      this.configurator = configurator;
      this.sessionListener = sessionListener;
      this.clusterContext = clusterContext;
      if (endpointEventListener != null) {
         this.endpointEventListener = endpointEventListener;
      } else {
         this.endpointEventListener = EndpointEventListener.NO_OP;
      }

      if (parallelBroadcastEnabled == null) {
         this.parallelBroadcastEnabled = false;
      } else {
         this.parallelBroadcastEnabled = parallelBroadcastEnabled;
      }

      if (configuration instanceof ServerEndpointConfig) {
         this.serverEndpointPath = ((ServerEndpointConfig)configuration).getPath();
         this.endpointPath = (contextPath.endsWith("/") ? contextPath.substring(0, contextPath.length() - 1) : contextPath) + "/" + (this.serverEndpointPath.startsWith("/") ? this.serverEndpointPath.substring(1) : this.serverEndpointPath);
      } else {
         this.serverEndpointPath = null;
         this.endpointPath = null;
      }

      this.componentProvider = configurator == null ? componentProvider : new ComponentProviderService(componentProvider) {
         public Object getEndpointInstance(Class endpointClass) throws InstantiationException {
            return configurator.getEndpointInstance(endpointClass);
         }
      };
      Class clazz = endpointClass == null ? endpoint.getClass() : endpointClass;
      Method onOpenMethod = null;
      Method onCloseMethod = null;
      Method onErrorMethod = null;
      Method[] var16 = Endpoint.class.getMethods();
      int var17 = var16.length;

      for(int var18 = 0; var18 < var17; ++var18) {
         Method m = var16[var18];
         if (m.getName().equals("onOpen")) {
            onOpenMethod = m;
         } else if (m.getName().equals("onClose")) {
            onCloseMethod = m;
         } else if (m.getName().equals("onError")) {
            onErrorMethod = m;
         }
      }

      try {
         assert onOpenMethod != null;

         assert onCloseMethod != null;

         assert onErrorMethod != null;

         onOpenMethod = clazz.getMethod(onOpenMethod.getName(), onOpenMethod.getParameterTypes());
         onCloseMethod = clazz.getMethod(onCloseMethod.getName(), onCloseMethod.getParameterTypes());
         onErrorMethod = clazz.getMethod(onErrorMethod.getName(), onErrorMethod.getParameterTypes());
      } catch (NoSuchMethodException var22) {
         throw new DeploymentException(var22.getMessage(), var22);
      }

      if (this.programmaticEndpoint) {
         this.onOpen = onOpenMethod;
         this.onClose = onCloseMethod;
         this.onError = onErrorMethod;
      } else {
         this.onOpen = componentProvider.getInvocableMethod(onOpenMethod);
         this.onClose = componentProvider.getInvocableMethod(onCloseMethod);
         this.onError = componentProvider.getInvocableMethod(onErrorMethod);
      }

      this.configuration = configuration == null ? new EndpointConfig() {
         private final Map properties = new HashMap();

         public List getEncoders() {
            return Collections.emptyList();
         }

         public List getDecoders() {
            return Collections.emptyList();
         }

         public Map getUserProperties() {
            return this.properties;
         }
      } : configuration;
      Iterator var23 = this.configuration.getDecoders().iterator();

      Class decoderClass;
      Class type;
      while(var23.hasNext()) {
         decoderClass = (Class)var23.next();
         type = this.getDecoderClassType(decoderClass);
         if (getDefaultDecoders().contains(decoderClass)) {
            try {
               this.decoders.add(new CoderWrapper(ReflectionHelper.getInstance(decoderClass), type));
            } catch (Exception var21) {
               throw new DeploymentException(var21.getMessage(), var21);
            }
         } else {
            this.decoders.add(new CoderWrapper(decoderClass, type));
         }
      }

      if (endpoint == null || !(endpoint instanceof AnnotatedEndpoint)) {
         var23 = getDefaultDecoders().iterator();

         while(var23.hasNext()) {
            decoderClass = (Class)var23.next();
            type = this.getDecoderClassType(decoderClass);

            try {
               this.decoders.add(new CoderWrapper(ReflectionHelper.getInstance(decoderClass), type));
            } catch (Exception var20) {
               throw new DeploymentException(var20.getMessage(), var20);
            }
         }
      }

      var23 = this.configuration.getEncoders().iterator();

      while(var23.hasNext()) {
         decoderClass = (Class)var23.next();
         type = this.getEncoderClassType(decoderClass);
         this.encoders.add(new CoderWrapper(decoderClass, type));
      }

      this.encoders.add(new CoderWrapper(new NoOpTextCoder(), String.class));
      this.encoders.add(new CoderWrapper(new NoOpByteBufferCoder(), ByteBuffer.class));
      this.encoders.add(new CoderWrapper(new NoOpByteArrayCoder(), byte[].class));
      this.encoders.add(new CoderWrapper(new ToStringEncoder(), Object.class));
      if (clusterContext != null) {
         clusterContext.registerSessionListener(this.getEndpointPath(), new org.glassfish.tyrus.core.cluster.SessionListener() {
            public void onSessionOpened(String sessionId) {
               Map distributedSessionProperties = clusterContext.getDistributedSessionProperties(sessionId);
               TyrusEndpointWrapper.this.clusteredSessions.put(sessionId, new RemoteSession(sessionId, clusterContext, distributedSessionProperties, TyrusEndpointWrapper.this, TyrusEndpointWrapper.dummySession));
            }

            public void onSessionClosed(String sessionId) {
               TyrusEndpointWrapper.this.clusteredSessions.remove(sessionId);
            }
         });
         clusterContext.registerBroadcastListener(this.getEndpointPath(), new BroadcastListener() {
            public void onBroadcast(String text) {
               TyrusEndpointWrapper.this.broadcast(text, true);
            }

            public void onBroadcast(byte[] data) {
               TyrusEndpointWrapper.this.broadcast(ByteBuffer.wrap(data), true);
            }
         });
         var23 = clusterContext.getRemoteSessionIds(this.getEndpointPath()).iterator();

         while(var23.hasNext()) {
            String sessionId = (String)var23.next();
            Map distributedSessionProperties = clusterContext.getDistributedSessionProperties(sessionId);
            this.clusteredSessions.put(sessionId, new RemoteSession(sessionId, clusterContext, distributedSessionProperties, this, dummySession));
         }
      }

   }

   static List getDefaultDecoders() {
      List classList = new ArrayList();
      classList.addAll(PrimitiveDecoders.ALL);
      classList.add(NoOpTextCoder.class);
      classList.add(NoOpByteBufferCoder.class);
      classList.add(NoOpByteArrayCoder.class);
      classList.add(ReaderDecoder.class);
      classList.add(InputStreamDecoder.class);
      return classList;
   }

   private static URI getURI(String uri, String queryString) {
      return queryString != null && !queryString.isEmpty() ? URI.create(String.format("%s?%s", uri, queryString)) : URI.create(uri);
   }

   private Object getCoderInstance(Session session, CoderWrapper wrapper) {
      Object coder = wrapper.getCoder();
      if (coder == null) {
         ErrorCollector collector = new ErrorCollector();
         Object coderInstance = this.componentProvider.getCoderInstance(wrapper.getCoderClass(), session, this.getEndpointConfig(), collector);
         if (!collector.isEmpty()) {
            DeploymentException deploymentException = collector.composeComprehensiveException();
            LOGGER.log(Level.WARNING, deploymentException.getMessage(), deploymentException);
            return null;
         } else {
            return coderInstance;
         }
      } else {
         return coder;
      }
   }

   Object decodeCompleteMessage(TyrusSession session, Object message, Class type, CoderWrapper selectedDecoder) throws DecodeException, IOException {
      Class decoderClass = selectedDecoder.getCoderClass();
      if (Decoder.Text.class.isAssignableFrom(decoderClass)) {
         if (type != null && type.isAssignableFrom(selectedDecoder.getType())) {
            Decoder.Text decoder = (Decoder.Text)this.getCoderInstance(session, selectedDecoder);
            session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Decoding with ", selectedDecoder);
            return decoder.decode((String)message);
         }
      } else if (Decoder.Binary.class.isAssignableFrom(decoderClass)) {
         if (type != null && type.isAssignableFrom(selectedDecoder.getType())) {
            Decoder.Binary decoder = (Decoder.Binary)this.getCoderInstance(session, selectedDecoder);
            session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Decoding with ", selectedDecoder);
            return decoder.decode((ByteBuffer)message);
         }
      } else if (Decoder.TextStream.class.isAssignableFrom(decoderClass)) {
         if (type != null && type.isAssignableFrom(selectedDecoder.getType())) {
            session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Decoding with ", selectedDecoder);
            return ((Decoder.TextStream)this.getCoderInstance(session, selectedDecoder)).decode(new StringReader((String)message));
         }
      } else if (Decoder.BinaryStream.class.isAssignableFrom(decoderClass) && type != null && type.isAssignableFrom(selectedDecoder.getType())) {
         byte[] array = ((ByteBuffer)message).array();
         session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Decoding with ", selectedDecoder);
         return ((Decoder.BinaryStream)this.getCoderInstance(session, selectedDecoder)).decode(new ByteArrayInputStream(array));
      }

      return null;
   }

   private ArrayList findApplicableDecoders(TyrusSession session, Object message, boolean isString) {
      ArrayList result = new ArrayList();
      Iterator var5 = this.decoders.iterator();

      while(true) {
         while(var5.hasNext()) {
            CoderWrapper dec = (CoderWrapper)var5.next();
            if (isString && Decoder.Text.class.isAssignableFrom(dec.getCoderClass())) {
               Decoder.Text decoder = (Decoder.Text)this.getCoderInstance(session, dec);
               if (decoder.willDecode((String)message)) {
                  result.add(dec);
               }
            } else if (!isString && Decoder.Binary.class.isAssignableFrom(dec.getCoderClass())) {
               Decoder.Binary decoder = (Decoder.Binary)this.getCoderInstance(session, dec);
               if (decoder.willDecode((ByteBuffer)message)) {
                  result.add(dec);
               }
            } else if (isString && Decoder.TextStream.class.isAssignableFrom(dec.getCoderClass())) {
               result.add(dec);
            } else if (!isString && Decoder.BinaryStream.class.isAssignableFrom(dec.getCoderClass())) {
               result.add(dec);
            }
         }

         session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Applicable decoders: ", result);
         return result;
      }
   }

   public Object doEncode(Session session, Object message) throws EncodeException, IOException {
      Iterator var3 = this.encoders.iterator();

      while(var3.hasNext()) {
         CoderWrapper enc = (CoderWrapper)var3.next();
         Class encoderClass = enc.getCoderClass();
         if (Encoder.Binary.class.isAssignableFrom(encoderClass)) {
            if (enc.getType().isAssignableFrom(message.getClass())) {
               Encoder.Binary encoder = (Encoder.Binary)this.getCoderInstance(session, enc);
               this.logUsedEncoder(enc, session);
               return encoder.encode(message);
            }
         } else if (Encoder.Text.class.isAssignableFrom(encoderClass)) {
            if (enc.getType().isAssignableFrom(message.getClass())) {
               Encoder.Text encoder = (Encoder.Text)this.getCoderInstance(session, enc);
               this.logUsedEncoder(enc, session);
               return encoder.encode(message);
            }
         } else if (Encoder.BinaryStream.class.isAssignableFrom(encoderClass)) {
            if (enc.getType().isAssignableFrom(message.getClass())) {
               ByteArrayOutputStream stream = new ByteArrayOutputStream();
               Encoder.BinaryStream encoder = (Encoder.BinaryStream)this.getCoderInstance(session, enc);
               this.logUsedEncoder(enc, session);
               encoder.encode(message, stream);
               return stream;
            }
         } else if (Encoder.TextStream.class.isAssignableFrom(encoderClass) && enc.getType().isAssignableFrom(message.getClass())) {
            Writer writer = new StringWriter();
            Encoder.TextStream encoder = (Encoder.TextStream)this.getCoderInstance(session, enc);
            this.logUsedEncoder(enc, session);
            encoder.encode(message, writer);
            return writer;
         }
      }

      throw new EncodeException(message, LocalizationMessages.ENCODING_FAILED());
   }

   private void logUsedEncoder(CoderWrapper encoder, Session session) {
      if (LOGGER.isLoggable(Level.FINEST) && session instanceof TyrusSession) {
         ((TyrusSession)session).getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_OUT, "Encoding with: ", encoder);
      }

   }

   public String getEndpointPath() {
      return this.endpointPath;
   }

   String getServerEndpointPath() {
      return this.serverEndpointPath;
   }

   List getNegotiatedExtensions(List clientExtensions) {
      return this.configuration instanceof ServerEndpointConfig ? this.configurator.getNegotiatedExtensions(((ServerEndpointConfig)this.configuration).getExtensions(), clientExtensions) : Collections.emptyList();
   }

   String getNegotiatedProtocol(List clientProtocols) {
      return this.configuration instanceof ServerEndpointConfig ? this.configurator.getNegotiatedSubprotocol(((ServerEndpointConfig)this.configuration).getSubprotocols(), clientProtocols) : null;
   }

   Set getOpenSessions() {
      Set result = new HashSet();
      Iterator var2 = this.webSocketToSession.values().iterator();

      while(var2.hasNext()) {
         TyrusSession session = (TyrusSession)var2.next();
         if (session.isOpen()) {
            result.add(session);
         }
      }

      return Collections.unmodifiableSet(result);
   }

   Set getRemoteSessions() {
      Set result = new HashSet();
      if (this.clusterContext != null) {
         result.addAll(this.clusteredSessions.values());
      }

      return Collections.unmodifiableSet(result);
   }

   public Session createSessionForRemoteEndpoint(TyrusWebSocket socket, String subprotocol, List extensions, DebugContext debugContext) {
      TyrusSession session = new TyrusSession(this.container, socket, this, subprotocol, extensions, false, getURI(this.contextPath, (String)null), (String)null, Collections.emptyMap(), (Principal)null, Collections.emptyMap(), (ClusterContext)null, (String)null, (String)null, debugContext);
      this.webSocketToSession.put(socket, session);
      return session;
   }

   private TyrusSession getSession(TyrusWebSocket socket) {
      return (TyrusSession)this.webSocketToSession.get(socket);
   }

   Session onConnect(TyrusWebSocket socket, UpgradeRequest upgradeRequest, String subProtocol, List extensions, String connectionId, DebugContext debugContext) {
      TyrusSession session = (TyrusSession)this.webSocketToSession.get(socket);
      if (session == null) {
         Map templateValues = new HashMap();
         Iterator var9 = upgradeRequest.getParameterMap().entrySet().iterator();

         while(var9.hasNext()) {
            Map.Entry entry = (Map.Entry)var9.next();
            templateValues.put(entry.getKey(), ((List)entry.getValue()).get(0));
         }

         session = new TyrusSession(this.container, socket, this, subProtocol, extensions, upgradeRequest.isSecure(), getURI(upgradeRequest.getRequestURI().toString(), upgradeRequest.getQueryString()), upgradeRequest.getQueryString(), templateValues, upgradeRequest.getUserPrincipal(), upgradeRequest.getParameterMap(), this.clusterContext, connectionId, ((RequestContext)upgradeRequest).getRemoteAddr(), debugContext);
         this.webSocketToSession.put(socket, session);
         boolean maxSessionPerEndpointExceeded = this.configuration instanceof TyrusServerEndpointConfig && ((TyrusServerEndpointConfig)this.configuration).getMaxSessions() > 0 && this.webSocketToSession.size() > ((TyrusServerEndpointConfig)this.configuration).getMaxSessions();
         SessionListener.OnOpenResult onOpenResult = this.sessionListener.onOpen(session);
         if (maxSessionPerEndpointExceeded || !onOpenResult.equals(TyrusEndpointWrapper.SessionListener.OnOpenResult.SESSION_ALLOWED)) {
            try {
               this.webSocketToSession.remove(socket);
               String refuseDetail;
               if (maxSessionPerEndpointExceeded) {
                  refuseDetail = LocalizationMessages.MAX_SESSIONS_PER_ENDPOINT_EXCEEDED();
               } else {
                  switch (onOpenResult) {
                     case MAX_SESSIONS_PER_APP_EXCEEDED:
                        refuseDetail = LocalizationMessages.MAX_SESSIONS_PER_APP_EXCEEDED();
                        break;
                     case MAX_SESSIONS_PER_REMOTE_ADDR_EXCEEDED:
                        refuseDetail = LocalizationMessages.MAX_SESSIONS_PER_REMOTEADDR_EXCEEDED();
                        break;
                     default:
                        refuseDetail = null;
                  }
               }

               debugContext.appendLogMessage(LOGGER, Level.FINE, DebugContext.Type.MESSAGE_IN, "Session opening refused: ", refuseDetail);
               session.close(new CloseReason(CloseCodes.TRY_AGAIN_LATER, refuseDetail));
            } catch (IOException var12) {
               debugContext.appendLogMessageWithThrowable(LOGGER, Level.WARNING, DebugContext.Type.MESSAGE_IN, var12, var12.getMessage());
            }

            return null;
         }

         socket.setMessageEventListener(this.endpointEventListener.onSessionOpened(session.getId()));
      }

      ErrorCollector collector = new ErrorCollector();
      Object toCall = this.programmaticEndpoint ? this.endpoint : this.componentProvider.getInstance(this.endpointClass, session, collector);
      if (toCall == null) {
         if (!collector.isEmpty()) {
            Throwable t = collector.composeComprehensiveException();
            debugContext.appendLogMessageWithThrowable(LOGGER, Level.FINE, DebugContext.Type.MESSAGE_IN, t, t.getMessage());
         }

         this.webSocketToSession.remove(socket);
         this.sessionListener.onClose(session, CloseReasons.UNEXPECTED_CONDITION.getCloseReason());

         try {
            session.close(CloseReasons.UNEXPECTED_CONDITION.getCloseReason());
         } catch (IOException var13) {
            debugContext.appendLogMessageWithThrowable(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, var13, var13.getMessage());
         }

         return null;
      } else {
         try {
            if (!collector.isEmpty()) {
               throw collector.composeComprehensiveException();
            }

            if (this.programmaticEndpoint) {
               ((Endpoint)toCall).onOpen(session, this.configuration);
            } else {
               try {
                  this.onOpen.invoke(toCall, session, this.configuration);
               } catch (InvocationTargetException var15) {
                  throw var15.getCause();
               }
            }
         } catch (Throwable var16) {
            Throwable t = var16;
            if (this.programmaticEndpoint) {
               ((Endpoint)toCall).onError(session, var16);
            } else {
               try {
                  this.onError.invoke(toCall, session, t);
               } catch (Exception var14) {
                  debugContext.appendLogMessageWithThrowable(LOGGER, Level.WARNING, DebugContext.Type.MESSAGE_IN, var16, var16.getMessage());
               }
            }

            this.endpointEventListener.onError(session.getId(), var16);
         }

         return session;
      }
   }

   void onMessage(TyrusWebSocket socket, ByteBuffer messageBytes) {
      TyrusSession session = this.getSession(socket);
      if (session == null) {
         LOGGER.log(Level.FINE, "Message received on already closed connection.");
      } else {
         session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Received binary message");

         try {
            session.restartIdleTimeoutExecutor();
            TyrusSession.State state = session.getState();
            if (state == TyrusSession.State.RECEIVING_BINARY || state == TyrusSession.State.RECEIVING_TEXT) {
               session.setState(TyrusSession.State.RUNNING);
            }

            if (session.isWholeBinaryHandlerPresent()) {
               session.notifyMessageHandlers(messageBytes, this.findApplicableDecoders(session, messageBytes, false));
            } else {
               if (!session.isPartialBinaryHandlerPresent()) {
                  throw new IllegalStateException(LocalizationMessages.BINARY_MESSAGE_HANDLER_NOT_FOUND(session));
               }

               session.notifyMessageHandlers(messageBytes, true);
            }
         } catch (Throwable var9) {
            Throwable t = var9;
            if (!this.processThrowable(var9, session)) {
               ErrorCollector collector = new ErrorCollector();
               Object toCall = this.programmaticEndpoint ? this.endpoint : this.componentProvider.getInstance(this.endpointClass, session, collector);
               if (toCall != null) {
                  if (this.programmaticEndpoint) {
                     ((Endpoint)toCall).onError(session, var9);
                  } else {
                     try {
                        this.onError.invoke(toCall, session, t);
                     } catch (Exception var8) {
                        LOGGER.log(Level.WARNING, var9.getMessage(), var9);
                     }
                  }
               } else if (!collector.isEmpty()) {
                  DeploymentException deploymentException = collector.composeComprehensiveException();
                  LOGGER.log(Level.WARNING, deploymentException.getMessage(), deploymentException);
               }

               this.endpointEventListener.onError(session.getId(), var9);
            }
         }

      }
   }

   void onMessage(TyrusWebSocket socket, String messageString) {
      TyrusSession session = this.getSession(socket);
      if (session == null) {
         LOGGER.log(Level.FINE, "Message received on already closed connection.");
      } else {
         session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Received text message");

         try {
            session.restartIdleTimeoutExecutor();
            TyrusSession.State state = session.getState();
            if (state == TyrusSession.State.RECEIVING_BINARY || state == TyrusSession.State.RECEIVING_TEXT) {
               session.setState(TyrusSession.State.RUNNING);
            }

            if (session.isWholeTextHandlerPresent()) {
               session.notifyMessageHandlers(messageString, this.findApplicableDecoders(session, messageString, true));
            } else {
               if (!session.isPartialTextHandlerPresent()) {
                  throw new IllegalStateException(LocalizationMessages.TEXT_MESSAGE_HANDLER_NOT_FOUND(session));
               }

               session.notifyMessageHandlers(messageString, true);
            }
         } catch (Throwable var9) {
            Throwable t = var9;
            if (!this.processThrowable(var9, session)) {
               ErrorCollector collector = new ErrorCollector();
               Object toCall = this.programmaticEndpoint ? this.endpoint : this.componentProvider.getInstance(this.endpointClass, session, collector);
               if (toCall != null) {
                  if (this.programmaticEndpoint) {
                     ((Endpoint)toCall).onError(session, var9);
                  } else {
                     try {
                        this.onError.invoke(toCall, session, t);
                     } catch (Exception var8) {
                        LOGGER.log(Level.WARNING, var9.getMessage(), var9);
                     }
                  }
               } else if (!collector.isEmpty()) {
                  DeploymentException deploymentException = collector.composeComprehensiveException();
                  LOGGER.log(Level.WARNING, deploymentException.getMessage(), deploymentException);
               }

               this.endpointEventListener.onError(session.getId(), var9);
            }
         }

      }
   }

   void onPartialMessage(TyrusWebSocket socket, String partialString, boolean last) {
      TyrusSession session = this.getSession(socket);
      if (session == null) {
         LOGGER.log(Level.FINE, "Message received on already closed connection.");
      } else {
         session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Received partial text message");

         try {
            session.restartIdleTimeoutExecutor();
            TyrusSession.State state = session.getState();
            if (session.isPartialTextHandlerPresent()) {
               session.notifyMessageHandlers(partialString, last);
               if (state == TyrusSession.State.RECEIVING_BINARY || state == TyrusSession.State.RECEIVING_TEXT) {
                  session.setState(TyrusSession.State.RUNNING);
               }
            } else if (session.isReaderHandlerPresent()) {
               ReaderBuffer buffer = session.getReaderBuffer();
               switch (state) {
                  case RUNNING:
                     if (buffer == null) {
                        buffer = new ReaderBuffer(((BaseContainer)this.container).getExecutorService());
                        session.setReaderBuffer(buffer);
                     }

                     buffer.resetBuffer(session.getMaxTextMessageBufferSize());
                     buffer.setMessageHandler(session.getMessageHandler(Reader.class));
                     buffer.appendMessagePart(partialString, last);
                     session.setState(TyrusSession.State.RECEIVING_TEXT);
                     break;
                  case RECEIVING_TEXT:
                     buffer.appendMessagePart(partialString, last);
                     if (last) {
                        session.setState(TyrusSession.State.RUNNING);
                     }
                     break;
                  default:
                     if (state == TyrusSession.State.RECEIVING_BINARY) {
                        session.setState(TyrusSession.State.RUNNING);
                     }

                     throw new IllegalStateException(LocalizationMessages.PARTIAL_TEXT_MESSAGE_OUT_OF_ORDER(session));
               }
            } else if (session.isWholeTextHandlerPresent()) {
               switch (state) {
                  case RUNNING:
                     session.getTextBuffer().resetBuffer(session.getMaxTextMessageBufferSize());
                     session.getTextBuffer().appendMessagePart(partialString);
                     session.setState(TyrusSession.State.RECEIVING_TEXT);
                     break;
                  case RECEIVING_TEXT:
                     session.getTextBuffer().appendMessagePart(partialString);
                     if (last) {
                        String message = session.getTextBuffer().getBufferedContent();
                        session.notifyMessageHandlers(message, this.findApplicableDecoders(session, message, true));
                        session.setState(TyrusSession.State.RUNNING);
                     }
                     break;
                  default:
                     if (state == TyrusSession.State.RECEIVING_BINARY) {
                        session.setState(TyrusSession.State.RUNNING);
                     }

                     throw new IllegalStateException(LocalizationMessages.TEXT_MESSAGE_OUT_OF_ORDER(session));
               }
            }
         } catch (Throwable var10) {
            Throwable t = var10;
            if (!this.processThrowable(var10, session)) {
               ErrorCollector collector = new ErrorCollector();
               Object toCall = this.programmaticEndpoint ? this.endpoint : this.componentProvider.getInstance(this.endpointClass, session, collector);
               if (toCall != null) {
                  if (this.programmaticEndpoint) {
                     ((Endpoint)toCall).onError(session, var10);
                  } else {
                     try {
                        this.onError.invoke(toCall, session, t);
                     } catch (Exception var9) {
                        LOGGER.log(Level.WARNING, var10.getMessage(), var10);
                     }
                  }
               } else if (!collector.isEmpty()) {
                  DeploymentException deploymentException = collector.composeComprehensiveException();
                  LOGGER.log(Level.WARNING, deploymentException.getMessage(), deploymentException);
               }

               this.endpointEventListener.onError(session.getId(), var10);
            }
         }

      }
   }

   void onPartialMessage(TyrusWebSocket socket, ByteBuffer partialBytes, boolean last) {
      TyrusSession session = this.getSession(socket);
      if (session == null) {
         LOGGER.log(Level.FINE, "Message received on already closed connection.");
      } else {
         session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Received partial binary message");

         try {
            session.restartIdleTimeoutExecutor();
            TyrusSession.State state = session.getState();
            if (session.isPartialBinaryHandlerPresent()) {
               session.notifyMessageHandlers(partialBytes, last);
               if (state == TyrusSession.State.RECEIVING_BINARY || state == TyrusSession.State.RECEIVING_TEXT) {
                  session.setState(TyrusSession.State.RUNNING);
               }
            } else if (session.isInputStreamHandlerPresent()) {
               InputStreamBuffer buffer = session.getInputStreamBuffer();
               switch (state) {
                  case RUNNING:
                     if (buffer == null) {
                        buffer = new InputStreamBuffer(((BaseContainer)this.container).getExecutorService());
                        session.setInputStreamBuffer(buffer);
                     }

                     buffer.resetBuffer(session.getMaxBinaryMessageBufferSize());
                     buffer.setMessageHandler(session.getMessageHandler(InputStream.class));
                     buffer.appendMessagePart(partialBytes, last);
                     session.setState(TyrusSession.State.RECEIVING_BINARY);
                     break;
                  case RECEIVING_BINARY:
                     buffer.appendMessagePart(partialBytes, last);
                     if (last) {
                        session.setState(TyrusSession.State.RUNNING);
                     }
                     break;
                  default:
                     if (state == TyrusSession.State.RECEIVING_TEXT) {
                        session.setState(TyrusSession.State.RUNNING);
                     }

                     throw new IllegalStateException(LocalizationMessages.PARTIAL_BINARY_MESSAGE_OUT_OF_ORDER(session));
               }
            } else if (session.isWholeBinaryHandlerPresent()) {
               switch (state) {
                  case RUNNING:
                     session.getBinaryBuffer().resetBuffer(session.getMaxBinaryMessageBufferSize());
                     session.getBinaryBuffer().appendMessagePart(partialBytes);
                     session.setState(TyrusSession.State.RECEIVING_BINARY);
                     break;
                  case RECEIVING_BINARY:
                     session.getBinaryBuffer().appendMessagePart(partialBytes);
                     if (last) {
                        ByteBuffer bb = session.getBinaryBuffer().getBufferedContent();
                        session.notifyMessageHandlers(bb, this.findApplicableDecoders(session, bb, false));
                        session.setState(TyrusSession.State.RUNNING);
                     }
                     break;
                  default:
                     if (state == TyrusSession.State.RECEIVING_TEXT) {
                        session.setState(TyrusSession.State.RUNNING);
                     }

                     throw new IllegalStateException(LocalizationMessages.BINARY_MESSAGE_OUT_OF_ORDER(session));
               }
            }
         } catch (Throwable var10) {
            Throwable t = var10;
            if (!this.processThrowable(var10, session)) {
               ErrorCollector collector = new ErrorCollector();
               Object toCall = this.programmaticEndpoint ? this.endpoint : this.componentProvider.getInstance(this.endpointClass, session, collector);
               if (toCall != null) {
                  if (this.programmaticEndpoint) {
                     ((Endpoint)toCall).onError(session, var10);
                  } else {
                     try {
                        this.onError.invoke(toCall, session, t);
                     } catch (Exception var9) {
                        LOGGER.log(Level.WARNING, var10.getMessage(), var10);
                     }
                  }
               } else if (!collector.isEmpty()) {
                  DeploymentException deploymentException = collector.composeComprehensiveException();
                  LOGGER.log(Level.WARNING, deploymentException.getMessage(), deploymentException);
               }

               this.endpointEventListener.onError(session.getId(), var10);
            }
         }

      }
   }

   private boolean processThrowable(Throwable throwable, Session session) {
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, String.format("Exception thrown while processing message. Session: '%session'.", session), throwable);
      }

      if (throwable instanceof WebSocketException) {
         try {
            session.close(((WebSocketException)throwable).getCloseReason());
            return false;
         } catch (IOException var4) {
         }
      }

      return false;
   }

   void onPong(TyrusWebSocket socket, final ByteBuffer bytes) {
      TyrusSession session = this.getSession(socket);
      if (session == null) {
         LOGGER.log(Level.FINE, "Pong received on already closed connection.");
      } else {
         session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Received pong message");
         session.restartIdleTimeoutExecutor();
         if (session.isPongHandlerPresent()) {
            try {
               session.notifyPongHandler(new PongMessage() {
                  public ByteBuffer getApplicationData() {
                     return bytes;
                  }

                  public String toString() {
                     return "PongMessage: " + bytes;
                  }
               });
            } catch (Throwable var9) {
               Throwable t = var9;
               if (!this.processThrowable(var9, session)) {
                  ErrorCollector collector = new ErrorCollector();
                  Object toCall = this.programmaticEndpoint ? this.endpoint : this.componentProvider.getInstance(this.endpointClass, session, collector);
                  if (toCall != null) {
                     if (this.programmaticEndpoint) {
                        ((Endpoint)toCall).onError(session, var9);
                     } else {
                        try {
                           this.onError.invoke(toCall, session, t);
                        } catch (Exception var8) {
                           LOGGER.log(Level.WARNING, var9.getMessage(), var9);
                        }
                     }
                  } else if (!collector.isEmpty()) {
                     DeploymentException deploymentException = collector.composeComprehensiveException();
                     LOGGER.log(Level.WARNING, deploymentException.getMessage(), deploymentException);
                  }

                  this.endpointEventListener.onError(session.getId(), var9);
               }
            }
         } else {
            session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Unhandled pong message");
         }

      }
   }

   void onPing(TyrusWebSocket socket, ByteBuffer bytes) {
      TyrusSession session = this.getSession(socket);
      if (session == null) {
         LOGGER.log(Level.FINE, "Ping received on already closed connection.");
      } else {
         session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Received ping message");
         session.restartIdleTimeoutExecutor();

         try {
            session.getBasicRemote().sendPong(bytes);
         } catch (IOException var5) {
         }

      }
   }

   void onClose(TyrusWebSocket socket, CloseReason closeReason) {
      TyrusSession session = this.getSession(socket);
      if (session != null) {
         session.setState(TyrusSession.State.CLOSED);
         ErrorCollector collector = new ErrorCollector();
         Object toCall = this.programmaticEndpoint ? this.endpoint : this.componentProvider.getInstance(this.endpointClass, session, collector);

         try {
            if (!collector.isEmpty()) {
               throw collector.composeComprehensiveException();
            }

            if (this.programmaticEndpoint) {
               ((Endpoint)toCall).onClose(session, closeReason);
            } else {
               try {
                  this.onClose.invoke(toCall, session, closeReason);
               } catch (InvocationTargetException var14) {
                  throw var14.getCause();
               }
            }
         } catch (Throwable var15) {
            Throwable t = var15;
            if (toCall != null) {
               if (this.programmaticEndpoint) {
                  ((Endpoint)toCall).onError(session, var15);
               } else {
                  try {
                     this.onError.invoke(toCall, session, t);
                  } catch (Exception var13) {
                     LOGGER.log(Level.WARNING, var15.getMessage(), var15);
                  }
               }
            } else {
               LOGGER.log(Level.WARNING, var15.getMessage(), var15);
            }

            this.endpointEventListener.onError(session.getId(), var15);
         } finally {
            if (this.clusterContext != null) {
               this.clusterContext.removeSession(session.getId(), this.getEndpointPath());
               if (!CloseCodes.CLOSED_ABNORMALLY.equals(closeReason.getCloseCode()) && !CloseCodes.GOING_AWAY.equals(closeReason.getCloseCode())) {
                  this.clusterContext.destroyDistributedUserProperties(session.getConnectionId());
               }
            }

            session.setState(TyrusSession.State.CLOSED);
            this.webSocketToSession.remove(socket);
            this.endpointEventListener.onSessionClosed(session.getId());
            this.componentProvider.removeSession(session);
            this.sessionListener.onClose(session, closeReason);
         }

      }
   }

   public EndpointConfig getEndpointConfig() {
      return this.configuration;
   }

   Map broadcast(String message) {
      return this.broadcast(message, false);
   }

   private Map broadcast(final String message, boolean local) {
      if (!local && this.clusterContext != null) {
         this.clusterContext.broadcastText(this.getEndpointPath(), message);
         return new HashMap();
      } else if (this.webSocketToSession.isEmpty()) {
         return new HashMap();
      } else {
         TyrusWebSocket webSocket = (TyrusWebSocket)this.webSocketToSession.keySet().iterator().next();
         Frame dataFrame = new TextFrame(message, false, true);
         ByteBuffer byteBuffer = webSocket.getProtocolHandler().frame(dataFrame);
         final byte[] frame = new byte[byteBuffer.remaining()];
         byteBuffer.get(frame);
         final long payloadLength = dataFrame.getPayloadLength();
         SessionCallable broadcastCallable = new SessionCallable() {
            public Future call(TyrusWebSocket webSocket, TyrusSession session) {
               ProtocolHandler protocolHandler = webSocket.getProtocolHandler();
               if (protocolHandler.hasExtensions()) {
                  Frame dataFrame = new TextFrame(message, false, true);
                  return TyrusEndpointWrapper.this.sendBroadcast(webSocket, dataFrame, TyrusFrame.FrameType.TEXT);
               } else {
                  Future frameFuture = webSocket.sendRawFrame(ByteBuffer.wrap(frame));
                  webSocket.getMessageEventListener().onFrameSent(TyrusFrame.FrameType.TEXT, payloadLength);
                  return frameFuture;
               }
            }
         };
         return this.broadcast(broadcastCallable);
      }
   }

   Map broadcast(ByteBuffer message) {
      return this.broadcast(message, false);
   }

   private Map broadcast(ByteBuffer message, boolean local) {
      final byte[] byteArrayMessage = Utils.getRemainingArray(message);
      if (!local && this.clusterContext != null) {
         this.clusterContext.broadcastBinary(this.getEndpointPath(), byteArrayMessage);
         return new HashMap();
      } else if (this.webSocketToSession.isEmpty()) {
         return new HashMap();
      } else {
         TyrusWebSocket webSocket = (TyrusWebSocket)this.webSocketToSession.keySet().iterator().next();
         Frame dataFrame = new BinaryFrame(byteArrayMessage, false, true);
         ByteBuffer byteBuffer = webSocket.getProtocolHandler().frame(dataFrame);
         final byte[] frame = new byte[byteBuffer.remaining()];
         byteBuffer.get(frame);
         final long payloadLength = dataFrame.getPayloadLength();
         SessionCallable broadcastCallable = new SessionCallable() {
            public Future call(TyrusWebSocket webSocket, TyrusSession session) {
               ProtocolHandler protocolHandler = webSocket.getProtocolHandler();
               if (protocolHandler.hasExtensions()) {
                  Frame dataFrame = new BinaryFrame(byteArrayMessage, false, true);
                  return TyrusEndpointWrapper.this.sendBroadcast(webSocket, dataFrame, TyrusFrame.FrameType.BINARY);
               } else {
                  Future frameFuture = webSocket.sendRawFrame(ByteBuffer.wrap(frame));
                  webSocket.getMessageEventListener().onFrameSent(TyrusFrame.FrameType.BINARY, payloadLength);
                  return frameFuture;
               }
            }
         };
         return this.broadcast(broadcastCallable);
      }
   }

   private Map broadcast(SessionCallable broadcastCallable) {
      if (this.parallelBroadcastEnabled) {
         return this.executeInParallel(broadcastCallable);
      } else {
         Map futures = new HashMap();
         Iterator var3 = this.webSocketToSession.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry e = (Map.Entry)var3.next();
            if (((TyrusSession)e.getValue()).isOpen()) {
               Future future = broadcastCallable.call((TyrusWebSocket)e.getKey(), (TyrusSession)e.getValue());
               futures.put(e.getValue(), future);
            }
         }

         return futures;
      }
   }

   private Future sendBroadcast(TyrusWebSocket webSocket, Frame dataFrame, TyrusFrame.FrameType frameType) {
      ByteBuffer byteBuffer = webSocket.getProtocolHandler().frame(dataFrame);
      byte[] tempFrame = new byte[byteBuffer.remaining()];
      byteBuffer.get(tempFrame);
      Future frameFuture = webSocket.sendRawFrame(ByteBuffer.wrap(tempFrame));
      webSocket.getMessageEventListener().onFrameSent(frameType, dataFrame.getPayloadLength());
      return frameFuture;
   }

   private Map executeInParallel(final SessionCallable broadcastCallable) {
      final List sessions = new ArrayList();
      Iterator var3 = this.webSocketToSession.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry e = (Map.Entry)var3.next();
         if (((TyrusSession)e.getValue()).isOpen()) {
            sessions.add(e);
         }
      }

      if (sessions.isEmpty()) {
         return new HashMap();
      } else {
         ExecutorService executor = ((BaseContainer)((TyrusSession)((Map.Entry)sessions.get(0)).getValue()).getContainer()).getExecutorService();
         Map submitFutures = new HashMap();
         int sessionCount = sessions.size();
         int maxThreadCount = sessionCount / 16 == 0 ? 1 : sessionCount / 16;
         int threadCount = Math.min(Runtime.getRuntime().availableProcessors(), maxThreadCount);

         for(int i = 0; i < threadCount; ++i) {
            final int lowerBound = (sessionCount + threadCount - 1) / threadCount * i;
            final int upperBound = Math.min((sessionCount + threadCount - 1) / threadCount * (i + 1), sessionCount);
            Future submitFuture = executor.submit(new Callable() {
               public Map call() throws Exception {
                  Map futures = new HashMap();

                  for(int j = lowerBound; j < upperBound; ++j) {
                     Map.Entry e = (Map.Entry)sessions.get(j);
                     Future future = broadcastCallable.call((TyrusWebSocket)e.getKey(), (TyrusSession)e.getValue());
                     futures.put(e.getValue(), future);
                  }

                  return futures;
               }
            });
            submitFutures.put(submitFuture, new int[]{lowerBound, upperBound});
         }

         Map futures = new HashMap();
         Iterator var17 = submitFutures.keySet().iterator();

         while(var17.hasNext()) {
            Future submitFuture = (Future)var17.next();

            try {
               futures.putAll((Map)submitFuture.get());
            } catch (InterruptedException var12) {
               this.handleSubmitException(futures, sessions, (int[])submitFutures.get(submitFuture), var12);
            } catch (ExecutionException var13) {
               this.handleSubmitException(futures, sessions, (int[])submitFutures.get(submitFuture), var13);
            }
         }

         return futures;
      }
   }

   private void handleSubmitException(Map futures, List sessions, int[] bounds, Exception e) {
      for(int j = bounds[0]; j < bounds[1]; ++j) {
         TyrusFuture future = new TyrusFuture();
         future.setFailure(e);
         futures.put(((Map.Entry)sessions.get(j)).getValue(), future);
      }

   }

   List getDecoders() {
      return this.decoders;
   }

   private Class getEncoderClassType(Class encoderClass) {
      if (Encoder.Binary.class.isAssignableFrom(encoderClass)) {
         return ReflectionHelper.getClassType(encoderClass, Encoder.Binary.class);
      } else if (Encoder.Text.class.isAssignableFrom(encoderClass)) {
         return ReflectionHelper.getClassType(encoderClass, Encoder.Text.class);
      } else if (Encoder.BinaryStream.class.isAssignableFrom(encoderClass)) {
         return ReflectionHelper.getClassType(encoderClass, Encoder.BinaryStream.class);
      } else {
         return Encoder.TextStream.class.isAssignableFrom(encoderClass) ? ReflectionHelper.getClassType(encoderClass, Encoder.TextStream.class) : null;
      }
   }

   private Class getDecoderClassType(Class decoderClass) {
      if (Decoder.Binary.class.isAssignableFrom(decoderClass)) {
         return ReflectionHelper.getClassType(decoderClass, Decoder.Binary.class);
      } else if (Decoder.Text.class.isAssignableFrom(decoderClass)) {
         return ReflectionHelper.getClassType(decoderClass, Decoder.Text.class);
      } else if (Decoder.BinaryStream.class.isAssignableFrom(decoderClass)) {
         return ReflectionHelper.getClassType(decoderClass, Decoder.BinaryStream.class);
      } else {
         return Decoder.TextStream.class.isAssignableFrom(decoderClass) ? ReflectionHelper.getClassType(decoderClass, Decoder.TextStream.class) : null;
      }
   }

   final boolean upgrade(UpgradeRequest request) throws HandshakeException {
      String upgradeHeader = request.getHeader("Upgrade");
      if (request.getHeaders().get("Upgrade") != null && "websocket".equalsIgnoreCase(upgradeHeader)) {
         if (!(this.configuration instanceof ServerEndpointConfig)) {
            return false;
         } else if (this.configurator.checkOrigin(request.getHeader("Origin"))) {
            return true;
         } else {
            throw new HandshakeException(403, LocalizationMessages.ORIGIN_NOT_VERIFIED());
         }
      } else {
         return false;
      }
   }

   TyrusWebSocket createSocket(ProtocolHandler handler) {
      return new TyrusWebSocket(handler, this);
   }

   boolean onError(TyrusWebSocket socket, Throwable t) {
      Logger.getLogger(TyrusEndpointWrapper.class.getName()).log(Level.WARNING, LocalizationMessages.UNEXPECTED_ERROR_CONNECTION_CLOSE(), t);
      return true;
   }

   void onHandShakeResponse(UpgradeRequest request, UpgradeResponse response) {
      EndpointConfig configuration = this.getEndpointConfig();
      if (configuration instanceof ServerEndpointConfig) {
         ServerEndpointConfig serverEndpointConfig = (ServerEndpointConfig)configuration;
         serverEndpointConfig.getConfigurator().modifyHandshake(serverEndpointConfig, this.createHandshakeRequest(request), response);
      }

   }

   private HandshakeRequest createHandshakeRequest(UpgradeRequest webSocketRequest) {
      if (webSocketRequest instanceof RequestContext) {
         RequestContext requestContext = (RequestContext)webSocketRequest;
         requestContext.lock();
         return requestContext;
      } else {
         return null;
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("TyrusEndpointWrapper");
      sb.append("{endpointClass=").append(this.endpointClass);
      sb.append(", endpoint=").append(this.endpoint);
      sb.append(", contextPath='").append(this.contextPath).append('\'');
      sb.append(", endpointPath=").append(this.endpointPath);
      sb.append(", encoders=[");
      boolean first = true;

      Iterator var3;
      CoderWrapper decoder;
      for(var3 = this.encoders.iterator(); var3.hasNext(); sb.append(decoder)) {
         decoder = (CoderWrapper)var3.next();
         if (first) {
            first = false;
         } else {
            sb.append(", ");
         }
      }

      sb.append("]");
      sb.append(", decoders=[");
      first = true;

      for(var3 = this.decoders.iterator(); var3.hasNext(); sb.append(decoder)) {
         decoder = (CoderWrapper)var3.next();
         if (first) {
            first = false;
         } else {
            sb.append(", ");
         }
      }

      sb.append("]");
      sb.append('}');
      return sb.toString();
   }

   private interface SessionCallable {
      Future call(TyrusWebSocket var1, TyrusSession var2);
   }

   public abstract static class SessionListener {
      public OnOpenResult onOpen(TyrusSession session) {
         return TyrusEndpointWrapper.SessionListener.OnOpenResult.SESSION_ALLOWED;
      }

      public void onClose(TyrusSession session, CloseReason closeReason) {
      }

      public static enum OnOpenResult {
         SESSION_ALLOWED,
         MAX_SESSIONS_PER_APP_EXCEEDED,
         MAX_SESSIONS_PER_REMOTE_ADDR_EXCEEDED;
      }
   }
}
