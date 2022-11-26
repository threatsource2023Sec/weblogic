package org.glassfish.tyrus.core;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Extension;
import javax.websocket.WebSocketContainer;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpointConfig;
import org.glassfish.tyrus.core.cluster.ClusterContext;
import org.glassfish.tyrus.core.extension.ExtendedExtension;
import org.glassfish.tyrus.core.frame.CloseFrame;
import org.glassfish.tyrus.core.frame.Frame;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.core.monitoring.ApplicationEventListener;
import org.glassfish.tyrus.core.monitoring.EndpointEventListener;
import org.glassfish.tyrus.core.monitoring.MessageEventListener;
import org.glassfish.tyrus.core.uri.Match;
import org.glassfish.tyrus.core.wsadl.model.Application;
import org.glassfish.tyrus.spi.Connection;
import org.glassfish.tyrus.spi.ReadHandler;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.glassfish.tyrus.spi.UpgradeResponse;
import org.glassfish.tyrus.spi.WebSocketEngine;
import org.glassfish.tyrus.spi.Writer;
import org.glassfish.tyrus.spi.WebSocketEngine.UpgradeStatus;

public class TyrusWebSocketEngine implements WebSocketEngine {
   public static final String INCOMING_BUFFER_SIZE = "org.glassfish.tyrus.incomingBufferSize";
   public static final String MAX_SESSIONS_PER_APP = "org.glassfish.tyrus.maxSessionsPerApp";
   public static final String MAX_SESSIONS_PER_REMOTE_ADDR = "org.glassfish.tyrus.maxSessionsPerRemoteAddr";
   public static final String TRACING_TYPE = "org.glassfish.tyrus.server.tracingType";
   public static final String TRACING_THRESHOLD = "org.glassfish.tyrus.server.tracingThreshold";
   @Beta
   public static final String WSADL_SUPPORT = "org.glassfish.tyrus.server.wsadl";
   public static final String PARALLEL_BROADCAST_ENABLED = "org.glassfish.tyrus.server.parallelBroadcastEnabled";
   private static final int BUFFER_STEP_SIZE = 256;
   private static final Logger LOGGER = Logger.getLogger(TyrusWebSocketEngine.class.getName());
   private static final WebSocketEngine.UpgradeInfo NOT_APPLICABLE_UPGRADE_INFO;
   private static final WebSocketEngine.UpgradeInfo HANDSHAKE_FAILED_UPGRADE_INFO;
   private static final TyrusEndpointWrapper.SessionListener NO_OP_SESSION_LISTENER;
   private final Set endpointWrappers;
   private final ComponentProviderService componentProviderService;
   private final WebSocketContainer webSocketContainer;
   private int incomingBufferSize;
   private final ClusterContext clusterContext;
   private final ApplicationEventListener applicationEventListener;
   private final TyrusEndpointWrapper.SessionListener sessionListener;
   private final Boolean parallelBroadcastEnabled;
   private final DebugContext.TracingType tracingType;
   private final DebugContext.TracingThreshold tracingThreshold;

   public static TyrusWebSocketEngineBuilder builder(WebSocketContainer webSocketContainer) {
      return new TyrusWebSocketEngineBuilder(webSocketContainer);
   }

   private TyrusWebSocketEngine(WebSocketContainer webSocketContainer, Integer incomingBufferSize, ClusterContext clusterContext, ApplicationEventListener applicationEventListener, final Integer maxSessionsPerApp, final Integer maxSessionsPerRemoteAddr, DebugContext.TracingType tracingType, DebugContext.TracingThreshold tracingThreshold, Boolean parallelBroadcastEnabled) {
      this.endpointWrappers = Collections.newSetFromMap(new ConcurrentHashMap());
      this.componentProviderService = ComponentProviderService.create();
      this.incomingBufferSize = 4194315;
      if (incomingBufferSize != null) {
         this.incomingBufferSize = incomingBufferSize;
      }

      this.webSocketContainer = webSocketContainer;
      this.clusterContext = clusterContext;
      this.parallelBroadcastEnabled = parallelBroadcastEnabled;
      if (applicationEventListener == null) {
         this.applicationEventListener = ApplicationEventListener.NO_OP;
      } else {
         LOGGER.config("Application event listener " + applicationEventListener.getClass().getName() + " registered");
         this.applicationEventListener = applicationEventListener;
      }

      LOGGER.config("Incoming buffer size: " + this.incomingBufferSize);
      LOGGER.config("Max sessions per app: " + maxSessionsPerApp);
      LOGGER.config("Max sessions per remote address: " + maxSessionsPerRemoteAddr);
      LOGGER.config("Parallel broadcast enabled: " + (parallelBroadcastEnabled != null && parallelBroadcastEnabled));
      this.tracingType = tracingType;
      this.tracingThreshold = tracingThreshold;
      this.sessionListener = maxSessionsPerApp == null && maxSessionsPerRemoteAddr == null ? NO_OP_SESSION_LISTENER : new TyrusEndpointWrapper.SessionListener() {
         private final AtomicInteger counter = new AtomicInteger(0);
         private final Object counterLock = new Object();
         private final Map remoteAddressCounters = new HashMap();

         public TyrusEndpointWrapper.SessionListener.OnOpenResult onOpen(TyrusSession session) {
            if (maxSessionsPerApp != null) {
               synchronized(this.counterLock) {
                  if (this.counter.get() >= maxSessionsPerApp) {
                     return TyrusEndpointWrapper.SessionListener.OnOpenResult.MAX_SESSIONS_PER_APP_EXCEEDED;
                  }

                  this.counter.incrementAndGet();
               }
            }

            if (maxSessionsPerRemoteAddr != null) {
               synchronized(this.remoteAddressCounters) {
                  AtomicInteger remoteAddressCounter = (AtomicInteger)this.remoteAddressCounters.get(session.getRemoteAddr());
                  if (remoteAddressCounter == null) {
                     remoteAddressCounter = new AtomicInteger(1);
                     this.remoteAddressCounters.put(session.getRemoteAddr(), remoteAddressCounter);
                  } else {
                     if (remoteAddressCounter.get() >= maxSessionsPerRemoteAddr) {
                        return TyrusEndpointWrapper.SessionListener.OnOpenResult.MAX_SESSIONS_PER_REMOTE_ADDR_EXCEEDED;
                     }

                     remoteAddressCounter.incrementAndGet();
                  }
               }
            }

            return TyrusEndpointWrapper.SessionListener.OnOpenResult.SESSION_ALLOWED;
         }

         public void onClose(TyrusSession session, CloseReason closeReason) {
            if (maxSessionsPerApp != null) {
               synchronized(this.counterLock) {
                  this.counter.decrementAndGet();
               }
            }

            if (maxSessionsPerRemoteAddr != null) {
               synchronized(this.remoteAddressCounters) {
                  int remoteAddressCounter = ((AtomicInteger)this.remoteAddressCounters.get(session.getRemoteAddr())).decrementAndGet();
                  if (remoteAddressCounter == 0) {
                     this.remoteAddressCounters.remove(session.getRemoteAddr());
                  }
               }
            }

         }
      };
   }

   private static ProtocolHandler loadHandler(UpgradeRequest request) {
      Version[] var1 = Version.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Version version = var1[var3];
         if (version.validate(request)) {
            return version.createHandler(false, (MaskingKeyGenerator)null);
         }
      }

      return null;
   }

   private static void handleUnsupportedVersion(UpgradeRequest request, UpgradeResponse response) {
      response.setStatus(426);
      response.getHeaders().put("Sec-WebSocket-Version", Arrays.asList(Version.getSupportedWireProtocolVersions()));
   }

   TyrusEndpointWrapper getEndpointWrapper(UpgradeRequest request, DebugContext debugContext) throws HandshakeException {
      if (this.endpointWrappers.isEmpty()) {
         return null;
      } else {
         String requestPath = request.getRequestUri();
         Iterator var4 = Match.getAllMatches(requestPath, this.endpointWrappers, debugContext).iterator();

         TyrusEndpointWrapper endpointWrapper;
         do {
            if (!var4.hasNext()) {
               return null;
            }

            Match m = (Match)var4.next();
            endpointWrapper = m.getEndpointWrapper();
            Iterator var7 = m.getParameters().entrySet().iterator();

            while(var7.hasNext()) {
               Map.Entry parameter = (Map.Entry)var7.next();
               request.getParameterMap().put(parameter.getKey(), Arrays.asList((String)parameter.getValue()));
            }
         } while(!endpointWrapper.upgrade(request));

         debugContext.appendTraceMessage(LOGGER, Level.FINE, DebugContext.Type.MESSAGE_IN, "Endpoint selected as a match to the handshake URI: ", endpointWrapper.getEndpointPath());
         debugContext.appendLogMessage(LOGGER, Level.FINER, DebugContext.Type.MESSAGE_IN, "Target endpoint: ", endpointWrapper);
         return endpointWrapper;
      }
   }

   public WebSocketEngine.UpgradeInfo upgrade(UpgradeRequest request, UpgradeResponse response) {
      DebugContext debugContext = this.createDebugContext(request);
      if (LOGGER.isLoggable(Level.FINE)) {
         debugContext.appendLogMessage(LOGGER, Level.FINE, DebugContext.Type.MESSAGE_IN, "Received handshake request:\n" + Utils.stringifyUpgradeRequest(request));
      }

      TyrusEndpointWrapper endpointWrapper;
      try {
         endpointWrapper = this.getEndpointWrapper(request, debugContext);
      } catch (HandshakeException var9) {
         return this.handleHandshakeException(var9, response);
      }

      if (endpointWrapper != null) {
         ProtocolHandler protocolHandler = loadHandler(request);
         if (protocolHandler == null) {
            handleUnsupportedVersion(request, response);
            debugContext.appendTraceMessage(LOGGER, Level.FINE, DebugContext.Type.MESSAGE_IN, "Upgrade request contains unsupported version of Websocket protocol");
            if (LOGGER.isLoggable(Level.FINE)) {
               debugContext.appendLogMessage(LOGGER, Level.FINE, DebugContext.Type.MESSAGE_OUT, "Sending handshake response:\n" + Utils.stringifyUpgradeResponse(response));
            }

            response.getHeaders().putAll(debugContext.getTracingHeaders());
            debugContext.flush();
            return HANDSHAKE_FAILED_UPGRADE_INFO;
         } else {
            ExtendedExtension.ExtensionContext extensionContext = new ExtendedExtension.ExtensionContext() {
               private final Map properties = new HashMap();

               public Map getProperties() {
                  return this.properties;
               }
            };

            try {
               protocolHandler.handshake(endpointWrapper, request, response, extensionContext);
            } catch (HandshakeException var8) {
               return this.handleHandshakeException(var8, response);
            }

            if (LOGGER.isLoggable(Level.FINE)) {
               this.logExtensionsAndSubprotocol(protocolHandler, debugContext);
            }

            if (this.clusterContext != null && request.getHeaders().get("tyrus-cluster-connection-id") == null) {
               String connectionId = this.clusterContext.createConnectionId();
               response.getHeaders().put("tyrus-cluster-connection-id", Collections.singletonList(connectionId));
               debugContext.appendLogMessage(LOGGER, Level.FINE, DebugContext.Type.OTHER, "Connection ID: ", connectionId);
            }

            if (LOGGER.isLoggable(Level.FINE)) {
               debugContext.appendLogMessage(LOGGER, Level.FINE, DebugContext.Type.MESSAGE_OUT, "Sending handshake response:\n" + Utils.stringifyUpgradeResponse(response) + "\n");
            }

            response.getHeaders().putAll(debugContext.getTracingHeaders());
            return new SuccessfulUpgradeInfo(endpointWrapper, protocolHandler, this.incomingBufferSize, request, response, extensionContext, debugContext);
         }
      } else {
         response.setStatus(500);
         response.getHeaders().putAll(debugContext.getTracingHeaders());
         debugContext.flush();
         return NOT_APPLICABLE_UPGRADE_INFO;
      }
   }

   private void logExtensionsAndSubprotocol(ProtocolHandler protocolHandler, DebugContext debugContext) {
      StringBuilder sb = new StringBuilder();
      sb.append("Using negotiated extensions: [");
      boolean isFirst = true;

      Extension extension;
      for(Iterator var5 = protocolHandler.getExtensions().iterator(); var5.hasNext(); sb.append(extension.getName())) {
         extension = (Extension)var5.next();
         if (isFirst) {
            isFirst = false;
         } else {
            sb.append(", ");
         }
      }

      sb.append("]");
      debugContext.appendLogMessage(LOGGER, Level.FINE, DebugContext.Type.OTHER, "Using negotiated extensions: ", sb);
      debugContext.appendLogMessage(LOGGER, Level.FINE, DebugContext.Type.OTHER, "Using negotiated subprotocol: ", protocolHandler.getSubProtocol());
   }

   private DebugContext createDebugContext(UpgradeRequest upgradeRequest) {
      String thresholdHeader = upgradeRequest.getHeader("X-Tyrus-Tracing-Threshold");
      DebugContext.TracingThreshold threshold = this.tracingThreshold;
      Exception thresholdHeaderParsingError = null;
      if (thresholdHeader != null) {
         try {
            threshold = DebugContext.TracingThreshold.valueOf(thresholdHeader);
         } catch (Exception var6) {
            thresholdHeaderParsingError = var6;
         }
      }

      DebugContext debugContext;
      if (this.tracingType != DebugContext.TracingType.ALL && (this.tracingType != DebugContext.TracingType.ON_DEMAND || upgradeRequest.getHeader("X-Tyrus-Tracing-Accept") == null)) {
         debugContext = new DebugContext();
      } else {
         debugContext = new DebugContext(threshold);
      }

      if (thresholdHeaderParsingError != null) {
         debugContext.appendTraceMessageWithThrowable(LOGGER, Level.WARNING, DebugContext.Type.MESSAGE_IN, thresholdHeaderParsingError, "An error occurred while parsing ", "X-Tyrus-Tracing-Threshold", " header:", thresholdHeaderParsingError.getMessage());
      }

      return debugContext;
   }

   private WebSocketEngine.UpgradeInfo handleHandshakeException(HandshakeException handshakeException, UpgradeResponse response) {
      LOGGER.log(Level.CONFIG, handshakeException.getMessage(), handshakeException);
      response.setStatus(handshakeException.getHttpStatusCode());
      return HANDSHAKE_FAILED_UPGRADE_INFO;
   }

   /** @deprecated */
   public void setIncomingBufferSize(int incomingBufferSize) {
      this.incomingBufferSize = incomingBufferSize;
   }

   private void register(TyrusEndpointWrapper endpointWrapper) throws DeploymentException {
      this.checkPath(endpointWrapper);
      LOGGER.log(Level.FINER, "Registered endpoint: " + endpointWrapper);
      this.endpointWrappers.add(endpointWrapper);
   }

   public void register(Class endpointClass, String contextPath) throws DeploymentException {
      ErrorCollector collector = new ErrorCollector();
      EndpointEventListenerWrapper endpointEventListenerWrapper = new EndpointEventListenerWrapper();
      AnnotatedEndpoint endpoint = AnnotatedEndpoint.fromClass(endpointClass, this.componentProviderService, true, this.incomingBufferSize, collector, endpointEventListenerWrapper);
      EndpointConfig config = endpoint.getEndpointConfig();
      TyrusEndpointWrapper endpointWrapper = new TyrusEndpointWrapper(endpoint, config, this.componentProviderService, this.webSocketContainer, contextPath, config instanceof ServerEndpointConfig ? ((ServerEndpointConfig)config).getConfigurator() : null, this.sessionListener, this.clusterContext, endpointEventListenerWrapper, this.parallelBroadcastEnabled);
      if (collector.isEmpty()) {
         this.register(endpointWrapper);
         String endpointPath = config instanceof ServerEndpointConfig ? ((ServerEndpointConfig)config).getPath() : null;
         EndpointEventListener endpointEventListener = this.applicationEventListener.onEndpointRegistered(endpointPath, endpointClass);
         endpointEventListenerWrapper.setEndpointEventListener(endpointEventListener);
      } else {
         throw collector.composeComprehensiveException();
      }
   }

   public void register(ServerEndpointConfig serverConfig, String contextPath) throws DeploymentException {
      Class endpointClass = serverConfig.getEndpointClass();
      Class parent = endpointClass;
      boolean isEndpointClass = false;

      do {
         parent = parent.getSuperclass();
         if (parent.equals(Endpoint.class)) {
            isEndpointClass = true;
         }
      } while(!parent.equals(Object.class));

      EndpointEventListenerWrapper endpointEventListenerWrapper = new EndpointEventListenerWrapper();
      TyrusEndpointWrapper endpointWrapper;
      if (isEndpointClass) {
         endpointWrapper = new TyrusEndpointWrapper(endpointClass, serverConfig, this.componentProviderService, this.webSocketContainer, contextPath, serverConfig.getConfigurator(), this.sessionListener, this.clusterContext, endpointEventListenerWrapper, this.parallelBroadcastEnabled);
      } else {
         ErrorCollector collector = new ErrorCollector();
         AnnotatedEndpoint endpoint = AnnotatedEndpoint.fromClass(endpointClass, this.componentProviderService, true, this.incomingBufferSize, collector, endpointEventListenerWrapper);
         EndpointConfig config = endpoint.getEndpointConfig();
         endpointWrapper = new TyrusEndpointWrapper(endpoint, config, this.componentProviderService, this.webSocketContainer, contextPath, config instanceof ServerEndpointConfig ? ((ServerEndpointConfig)config).getConfigurator() : null, this.sessionListener, this.clusterContext, endpointEventListenerWrapper, this.parallelBroadcastEnabled);
         if (!collector.isEmpty()) {
            throw collector.composeComprehensiveException();
         }
      }

      this.register(endpointWrapper);
      EndpointEventListener endpointEventListener = this.applicationEventListener.onEndpointRegistered(serverConfig.getPath(), endpointClass);
      endpointEventListenerWrapper.setEndpointEventListener(endpointEventListener);
   }

   private void checkPath(TyrusEndpointWrapper endpoint) throws DeploymentException {
      Iterator var2 = this.endpointWrappers.iterator();

      TyrusEndpointWrapper endpointWrapper;
      do {
         if (!var2.hasNext()) {
            return;
         }

         endpointWrapper = (TyrusEndpointWrapper)var2.next();
      } while(!Match.isEquivalent(endpoint.getEndpointPath(), endpointWrapper.getEndpointPath()));

      throw new DeploymentException(LocalizationMessages.EQUIVALENT_PATHS(endpoint.getEndpointPath(), endpointWrapper.getEndpointPath()));
   }

   public void unregister(TyrusEndpointWrapper endpointWrapper) {
      this.endpointWrappers.remove(endpointWrapper);
      this.applicationEventListener.onEndpointUnregistered(endpointWrapper.getEndpointPath());
   }

   public ApplicationEventListener getApplicationEventListener() {
      return this.applicationEventListener;
   }

   @Beta
   public Application getWsadlApplication() {
      Application application = new Application();
      Iterator var2 = this.endpointWrappers.iterator();

      while(var2.hasNext()) {
         TyrusEndpointWrapper wrapper = (TyrusEndpointWrapper)var2.next();
         org.glassfish.tyrus.core.wsadl.model.Endpoint endpoint = new org.glassfish.tyrus.core.wsadl.model.Endpoint();
         endpoint.setPath(wrapper.getServerEndpointPath());
         application.getEndpoint().add(endpoint);
      }

      return application;
   }

   // $FF: synthetic method
   TyrusWebSocketEngine(WebSocketContainer x0, Integer x1, ClusterContext x2, ApplicationEventListener x3, Integer x4, Integer x5, DebugContext.TracingType x6, DebugContext.TracingThreshold x7, Boolean x8, Object x9) {
      this(x0, x1, x2, x3, x4, x5, x6, x7, x8);
   }

   static {
      NOT_APPLICABLE_UPGRADE_INFO = new NoConnectionUpgradeInfo(UpgradeStatus.NOT_APPLICABLE);
      HANDSHAKE_FAILED_UPGRADE_INFO = new NoConnectionUpgradeInfo(UpgradeStatus.HANDSHAKE_FAILED);
      NO_OP_SESSION_LISTENER = new TyrusEndpointWrapper.SessionListener() {
      };
   }

   private static class EndpointEventListenerWrapper implements EndpointEventListener {
      private volatile EndpointEventListener endpointEventListener;

      private EndpointEventListenerWrapper() {
         this.endpointEventListener = EndpointEventListener.NO_OP;
      }

      void setEndpointEventListener(EndpointEventListener endpointEventListener) {
         this.endpointEventListener = endpointEventListener;
      }

      public MessageEventListener onSessionOpened(String sessionId) {
         return this.endpointEventListener.onSessionOpened(sessionId);
      }

      public void onSessionClosed(String sessionId) {
         this.endpointEventListener.onSessionClosed(sessionId);
      }

      public void onError(String sessionId, Throwable t) {
         this.endpointEventListener.onError(sessionId, t);
      }

      // $FF: synthetic method
      EndpointEventListenerWrapper(Object x0) {
         this();
      }
   }

   public static class TyrusWebSocketEngineBuilder {
      private final WebSocketContainer webSocketContainer;
      private Integer incomingBufferSize = null;
      private ClusterContext clusterContext = null;
      private ApplicationEventListener applicationEventListener = null;
      private Integer maxSessionsPerApp = null;
      private Integer maxSessionsPerRemoteAddr = null;
      private DebugContext.TracingType tracingType = null;
      private DebugContext.TracingThreshold tracingThreshold = null;
      private Boolean parallelBroadcastEnabled = null;

      public TyrusWebSocketEngine build() {
         if (this.maxSessionsPerApp != null && this.maxSessionsPerApp <= 0) {
            TyrusWebSocketEngine.LOGGER.log(Level.CONFIG, "Invalid configuration value org.glassfish.tyrus.maxSessionsPerApp (" + this.maxSessionsPerApp + "), expected value greater than 0.");
            this.maxSessionsPerApp = null;
         }

         if (this.maxSessionsPerRemoteAddr != null && this.maxSessionsPerRemoteAddr <= 0) {
            TyrusWebSocketEngine.LOGGER.log(Level.CONFIG, "Invalid configuration value org.glassfish.tyrus.maxSessionsPerRemoteAddr (" + this.maxSessionsPerRemoteAddr + "), expected value greater than 0.");
            this.maxSessionsPerRemoteAddr = null;
         }

         if (this.maxSessionsPerApp != null && this.maxSessionsPerRemoteAddr != null && this.maxSessionsPerApp < this.maxSessionsPerRemoteAddr) {
            TyrusWebSocketEngine.LOGGER.log(Level.FINE, String.format("Invalid configuration - value %s (%d) cannot be greater then %s (%d).", "org.glassfish.tyrus.maxSessionsPerRemoteAddr", this.maxSessionsPerRemoteAddr, "org.glassfish.tyrus.maxSessionsPerApp", this.maxSessionsPerApp));
         }

         return new TyrusWebSocketEngine(this.webSocketContainer, this.incomingBufferSize, this.clusterContext, this.applicationEventListener, this.maxSessionsPerApp, this.maxSessionsPerRemoteAddr, this.tracingType, this.tracingThreshold, this.parallelBroadcastEnabled);
      }

      TyrusWebSocketEngineBuilder(WebSocketContainer webSocketContainer) {
         if (webSocketContainer == null) {
            throw new NullPointerException();
         } else {
            this.webSocketContainer = webSocketContainer;
         }
      }

      public TyrusWebSocketEngineBuilder applicationEventListener(ApplicationEventListener applicationEventListener) {
         this.applicationEventListener = applicationEventListener;
         return this;
      }

      public TyrusWebSocketEngineBuilder incomingBufferSize(Integer incomingBufferSize) {
         this.incomingBufferSize = incomingBufferSize;
         return this;
      }

      public TyrusWebSocketEngineBuilder clusterContext(ClusterContext clusterContext) {
         this.clusterContext = clusterContext;
         return this;
      }

      public TyrusWebSocketEngineBuilder maxSessionsPerApp(Integer maxSessionsPerApp) {
         this.maxSessionsPerApp = maxSessionsPerApp;
         return this;
      }

      public TyrusWebSocketEngineBuilder maxSessionsPerRemoteAddr(Integer maxSessionsPerRemoteAddr) {
         this.maxSessionsPerRemoteAddr = maxSessionsPerRemoteAddr;
         return this;
      }

      public TyrusWebSocketEngineBuilder tracingType(DebugContext.TracingType tracingType) {
         this.tracingType = tracingType;
         return this;
      }

      public TyrusWebSocketEngineBuilder tracingThreshold(DebugContext.TracingThreshold tracingThreshold) {
         this.tracingThreshold = tracingThreshold;
         return this;
      }

      public TyrusWebSocketEngineBuilder parallelBroadcastEnabled(Boolean parallelBroadcastEnabled) {
         this.parallelBroadcastEnabled = parallelBroadcastEnabled;
         return this;
      }
   }

   static class TyrusConnection implements Connection {
      private final ReadHandler readHandler;
      private final Writer writer;
      private final Connection.CloseListener closeListener;
      private final TyrusWebSocket socket;
      private final ExtendedExtension.ExtensionContext extensionContext;
      private final List extensions;

      TyrusConnection(TyrusEndpointWrapper endpointWrapper, ProtocolHandler protocolHandler, int incomingBufferSize, Writer writer, Connection.CloseListener closeListener, UpgradeRequest upgradeRequest, UpgradeResponse upgradeResponse, ExtendedExtension.ExtensionContext extensionContext, DebugContext debugContext) {
         protocolHandler.setWriter(writer);
         this.extensions = protocolHandler.getExtensions();
         this.socket = endpointWrapper.createSocket(protocolHandler);
         List connectionIdHeader = (List)upgradeRequest.getHeaders().get("tyrus-cluster-connection-id");
         String connectionId;
         if (connectionIdHeader != null && connectionIdHeader.size() == 1) {
            connectionId = (String)connectionIdHeader.get(0);
         } else {
            connectionId = upgradeResponse.getFirstHeaderValue("tyrus-cluster-connection-id");
         }

         this.socket.onConnect(upgradeRequest, protocolHandler.getSubProtocol(), this.extensions, connectionId, debugContext);
         this.readHandler = new TyrusReadHandler(protocolHandler, this.socket, endpointWrapper, incomingBufferSize, extensionContext, debugContext);
         this.writer = writer;
         this.closeListener = closeListener;
         this.extensionContext = extensionContext;
      }

      public ReadHandler getReadHandler() {
         return this.readHandler;
      }

      public Writer getWriter() {
         return this.writer;
      }

      public Connection.CloseListener getCloseListener() {
         return this.closeListener;
      }

      public void close(CloseReason reason) {
         if (this.socket.isConnected()) {
            this.socket.close(reason.getCloseCode().getCode(), reason.getReasonPhrase());
            Iterator var2 = this.extensions.iterator();

            while(var2.hasNext()) {
               Extension extension = (Extension)var2.next();
               if (extension instanceof ExtendedExtension) {
                  try {
                     ((ExtendedExtension)extension).destroy(this.extensionContext);
                  } catch (Throwable var5) {
                  }
               }
            }

         }
      }
   }

   private static class SuccessfulUpgradeInfo implements WebSocketEngine.UpgradeInfo {
      private final TyrusEndpointWrapper endpointWrapper;
      private final ProtocolHandler protocolHandler;
      private final int incomingBufferSize;
      private final UpgradeRequest upgradeRequest;
      private final UpgradeResponse upgradeResponse;
      private final ExtendedExtension.ExtensionContext extensionContext;
      private final DebugContext debugContext;

      SuccessfulUpgradeInfo(TyrusEndpointWrapper endpointWrapper, ProtocolHandler protocolHandler, int incomingBufferSize, UpgradeRequest upgradeRequest, UpgradeResponse upgradeResponse, ExtendedExtension.ExtensionContext extensionContext, DebugContext debugContext) {
         this.endpointWrapper = endpointWrapper;
         this.protocolHandler = protocolHandler;
         this.incomingBufferSize = incomingBufferSize;
         this.upgradeRequest = upgradeRequest;
         this.upgradeResponse = upgradeResponse;
         this.extensionContext = extensionContext;
         this.debugContext = debugContext;
      }

      public WebSocketEngine.UpgradeStatus getStatus() {
         return UpgradeStatus.SUCCESS;
      }

      public Connection createConnection(Writer writer, Connection.CloseListener closeListener) {
         TyrusConnection tyrusConnection = new TyrusConnection(this.endpointWrapper, this.protocolHandler, this.incomingBufferSize, writer, closeListener, this.upgradeRequest, this.upgradeResponse, this.extensionContext, this.debugContext);
         this.debugContext.flush();
         return tyrusConnection;
      }
   }

   private static class NoConnectionUpgradeInfo implements WebSocketEngine.UpgradeInfo {
      private final WebSocketEngine.UpgradeStatus status;

      NoConnectionUpgradeInfo(WebSocketEngine.UpgradeStatus status) {
         this.status = status;
      }

      public WebSocketEngine.UpgradeStatus getStatus() {
         return this.status;
      }

      public Connection createConnection(Writer writer, Connection.CloseListener closeListener) {
         return null;
      }
   }

   private static class TyrusReadHandler implements ReadHandler {
      private final ProtocolHandler protocolHandler;
      private final TyrusWebSocket socket;
      private final TyrusEndpointWrapper endpointWrapper;
      private final int incomingBufferSize;
      private final ExtendedExtension.ExtensionContext extensionContext;
      private final DebugContext debugContext;
      private volatile ByteBuffer buffer;

      private TyrusReadHandler(ProtocolHandler protocolHandler, TyrusWebSocket socket, TyrusEndpointWrapper endpointWrapper, int incomingBufferSize, ExtendedExtension.ExtensionContext extensionContext, DebugContext debugContext) {
         this.extensionContext = extensionContext;
         this.protocolHandler = protocolHandler;
         this.socket = socket;
         this.endpointWrapper = endpointWrapper;
         this.incomingBufferSize = incomingBufferSize;
         this.debugContext = debugContext;
      }

      public void handle(ByteBuffer data) {
         try {
            if (data != null && data.hasRemaining()) {
               if (this.buffer != null) {
                  data = Utils.appendBuffers(this.buffer, data, this.incomingBufferSize, 256);
               } else {
                  int newSize = data.remaining();
                  if (newSize > this.incomingBufferSize) {
                     throw new IllegalArgumentException(LocalizationMessages.BUFFER_OVERFLOW());
                  }

                  int roundedSize = newSize % 256 > 0 ? (newSize / 256 + 1) * 256 : newSize;
                  ByteBuffer result = ByteBuffer.allocate(roundedSize > this.incomingBufferSize ? newSize : roundedSize);
                  result.flip();
                  data = Utils.appendBuffers(result, data, this.incomingBufferSize, 256);
               }

               while(true) {
                  Frame incomingFrame = this.protocolHandler.unframe(data);
                  if (incomingFrame == null) {
                     this.buffer = data;
                     break;
                  }

                  Frame frame = incomingFrame;
                  Iterator var13 = this.protocolHandler.getExtensions().iterator();

                  while(var13.hasNext()) {
                     Extension extension = (Extension)var13.next();
                     if (extension instanceof ExtendedExtension) {
                        try {
                           frame = ((ExtendedExtension)extension).processIncoming(this.extensionContext, frame);
                        } catch (Throwable var7) {
                           this.debugContext.appendLogMessageWithThrowable(TyrusWebSocketEngine.LOGGER, Level.FINE, DebugContext.Type.MESSAGE_IN, var7, "Extension '", extension.getName(), "' threw an exception during processIncoming method invocation: ", var7.getMessage());
                        }
                     }
                  }

                  this.protocolHandler.process(frame, this.socket);
               }
            }
         } catch (WebSocketException var8) {
            this.debugContext.appendLogMessageWithThrowable(TyrusWebSocketEngine.LOGGER, Level.FINE, DebugContext.Type.MESSAGE_IN, var8, var8.getMessage());
            this.socket.onClose(new CloseFrame(var8.getCloseReason()));
         } catch (Exception var9) {
            String message = var9.getMessage();
            this.debugContext.appendLogMessageWithThrowable(TyrusWebSocketEngine.LOGGER, Level.FINE, DebugContext.Type.MESSAGE_IN, var9, var9.getMessage());
            if (this.endpointWrapper.onError(this.socket, var9)) {
               if (message != null && message.length() > 123) {
                  message = message.substring(0, 123);
               }

               this.socket.onClose(new CloseFrame(new CloseReason(CloseCodes.UNEXPECTED_CONDITION, message)));
            }
         }

      }

      // $FF: synthetic method
      TyrusReadHandler(ProtocolHandler x0, TyrusWebSocket x1, TyrusEndpointWrapper x2, int x3, ExtendedExtension.ExtensionContext x4, DebugContext x5, Object x6) {
         this(x0, x1, x2, x3, x4, x5);
      }
   }
}
