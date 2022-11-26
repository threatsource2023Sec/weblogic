package org.glassfish.tyrus.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import javax.websocket.ClientEndpoint;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.ClientEndpointConfig.Builder;
import javax.websocket.server.ServerEndpointConfig;
import org.glassfish.tyrus.core.AnnotatedEndpoint;
import org.glassfish.tyrus.core.BaseContainer;
import org.glassfish.tyrus.core.ComponentProviderService;
import org.glassfish.tyrus.core.DebugContext;
import org.glassfish.tyrus.core.ErrorCollector;
import org.glassfish.tyrus.core.ReflectionHelper;
import org.glassfish.tyrus.core.TyrusEndpointWrapper;
import org.glassfish.tyrus.core.TyrusFuture;
import org.glassfish.tyrus.core.TyrusSession;
import org.glassfish.tyrus.core.Utils;
import org.glassfish.tyrus.core.cluster.ClusterContext;
import org.glassfish.tyrus.core.monitoring.EndpointEventListener;
import org.glassfish.tyrus.spi.ClientContainer;
import org.glassfish.tyrus.spi.ClientEngine;

public class ClientManager extends BaseContainer implements WebSocketContainer {
   /** @deprecated */
   public static final String HANDSHAKE_TIMEOUT = "org.glassfish.tyrus.client.ClientManager.ContainerTimeout";
   /** @deprecated */
   public static final String RECONNECT_HANDLER = "org.glassfish.tyrus.client.ClientManager.ReconnectHandler";
   /** @deprecated */
   public static final String PROXY_URI = "org.glassfish.tyrus.client.proxy";
   /** @deprecated */
   public static final String PROXY_HEADERS = "org.glassfish.tyrus.client.proxy.headers";
   /** @deprecated */
   public static final String SSL_ENGINE_CONFIGURATOR = "org.glassfish.tyrus.client.sslEngineConfigurator";
   private static final String CONTAINER_PROVIDER_CLASSNAME = "org.glassfish.tyrus.container.grizzly.client.GrizzlyClientContainer";
   public static final String WLS_PROXY_HOST = "weblogic.websocket.client.PROXY_HOST";
   public static final String WLS_PROXY_PORT = "weblogic.websocket.client.PROXY_PORT";
   public static final String WLS_PROXY_USERNAME = "weblogic.websocket.client.PROXY_USERNAME";
   public static final String WLS_PROXY_PASSWORD = "weblogic.websocket.client.PROXY_PASSWORD";
   public static final String WLS_SSL_PROTOCOLS_PROPERTY = "weblogic.websocket.client.SSL_PROTOCOLS";
   public static final String WLS_SSL_TRUSTSTORE_PROPERTY = "weblogic.websocket.client.SSL_TRUSTSTORE";
   public static final String WLS_SSL_TRUSTSTORE_PWD_PROPERTY = "weblogic.websocket.client.SSL_TRUSTSTORE_PWD";
   public static final String WLS_MAX_THREADS = "weblogic.websocket.client.max-aio-threads";
   public static final String WLS_IGNORE_HOSTNAME_VERIFICATION = "weblogic.security.SSL.ignoreHostnameVerification";
   public static final String WLS_HOSTNAME_VERIFIER_CLASS = "weblogic.security.SSL.HostnameVerifier";
   private static final Logger LOGGER = Logger.getLogger(ClientManager.class.getName());
   private final WebSocketContainer webSocketContainer;
   private final ClientContainer container;
   private final ComponentProviderService componentProvider;
   private final Map properties;
   private final ClientActivityListener clientActivityListener;
   private volatile long defaultAsyncSendTimeout;
   private volatile long defaultMaxSessionIdleTimeout;
   private volatile int maxBinaryMessageBufferSize;
   private volatile int maxTextMessageBufferSize;

   public static ClientManager createClient() {
      return createClient("org.glassfish.tyrus.container.grizzly.client.GrizzlyClientContainer");
   }

   public static ClientManager createClient(WebSocketContainer webSocketContainer) {
      return createClient("org.glassfish.tyrus.container.grizzly.client.GrizzlyClientContainer", webSocketContainer);
   }

   public static ClientManager createClient(String containerProviderClassName) {
      return new ClientManager(containerProviderClassName, (WebSocketContainer)null);
   }

   public static ClientManager createClient(String containerProviderClassName, WebSocketContainer webSocketContainer) {
      return new ClientManager(containerProviderClassName, webSocketContainer);
   }

   public ClientManager() {
      this("org.glassfish.tyrus.container.grizzly.client.GrizzlyClientContainer", (WebSocketContainer)null);
   }

   private ClientManager(String containerProviderClassName, WebSocketContainer webSocketContainer) {
      this.properties = new HashMap();
      this.maxBinaryMessageBufferSize = Integer.MAX_VALUE;
      this.maxTextMessageBufferSize = Integer.MAX_VALUE;
      ErrorCollector collector = new ErrorCollector();
      this.componentProvider = ComponentProviderService.createClient();

      Class engineProviderClazz;
      try {
         engineProviderClazz = ReflectionHelper.classForNameWithException(containerProviderClassName);
      } catch (ClassNotFoundException var6) {
         collector.addException(var6);
         throw new RuntimeException(collector.composeComprehensiveException());
      }

      LOGGER.config(String.format("Provider class loaded: %s", containerProviderClassName));
      this.container = (ClientContainer)ReflectionHelper.getInstance(engineProviderClazz, collector);
      if (!collector.isEmpty()) {
         throw new RuntimeException(collector.composeComprehensiveException());
      } else {
         this.webSocketContainer = webSocketContainer;
         this.clientActivityListener = new ClientActivityListener() {
            private final AtomicInteger activeClientCounter = new AtomicInteger(0);

            public void onConnectionInitiated() {
               this.activeClientCounter.incrementAndGet();
            }

            public void onConnectionTerminated() {
               if (this.activeClientCounter.decrementAndGet() == 0) {
                  ClientManager.this.shutdown(new BaseContainer.ShutDownCondition() {
                     public boolean evaluate() {
                        return activeClientCounter.get() == 0;
                     }
                  });
               }

            }
         };
      }
   }

   public Session connectToServer(Class annotatedEndpointClass, URI path) throws DeploymentException, IOException {
      if (annotatedEndpointClass.getAnnotation(ClientEndpoint.class) == null) {
         throw new DeploymentException(String.format("Class argument in connectToServer(Class, URI) is to be annotated endpoint class. Class %s does not have @ClientEndpoint", annotatedEndpointClass.getName()));
      } else {
         try {
            return (Session)this.connectToServer(annotatedEndpointClass, (ClientEndpointConfig)null, path.toString(), true).get();
         } catch (InterruptedException var5) {
            throw new DeploymentException(var5.getMessage(), var5);
         } catch (ExecutionException var6) {
            Throwable cause = var6.getCause();
            if (cause instanceof DeploymentException) {
               throw (DeploymentException)cause;
            } else if (cause instanceof IOException) {
               throw (IOException)cause;
            } else {
               throw new DeploymentException(cause.getMessage(), cause);
            }
         }
      }
   }

   public Session connectToServer(Class endpointClass, ClientEndpointConfig cec, URI path) throws DeploymentException, IOException {
      try {
         return (Session)this.connectToServer(endpointClass, cec, path.toString(), true).get();
      } catch (InterruptedException var6) {
         throw new DeploymentException(var6.getMessage(), var6);
      } catch (ExecutionException var7) {
         Throwable cause = var7.getCause();
         if (cause instanceof DeploymentException) {
            throw (DeploymentException)cause;
         } else if (cause instanceof IOException) {
            throw (IOException)cause;
         } else {
            throw new DeploymentException(cause.getMessage(), cause);
         }
      }
   }

   public Session connectToServer(Endpoint endpointInstance, ClientEndpointConfig cec, URI path) throws DeploymentException, IOException {
      try {
         return (Session)this.connectToServer(endpointInstance, cec, path.toString(), true).get();
      } catch (InterruptedException var6) {
         throw new DeploymentException(var6.getMessage(), var6);
      } catch (ExecutionException var7) {
         Throwable cause = var7.getCause();
         if (cause instanceof DeploymentException) {
            throw (DeploymentException)cause;
         } else if (cause instanceof IOException) {
            throw (IOException)cause;
         } else {
            throw new DeploymentException(cause.getMessage(), cause);
         }
      }
   }

   public Session connectToServer(Object obj, URI path) throws DeploymentException, IOException {
      try {
         return (Session)this.connectToServer(obj, (ClientEndpointConfig)null, path.toString(), true).get();
      } catch (InterruptedException var5) {
         throw new DeploymentException(var5.getMessage(), var5);
      } catch (ExecutionException var6) {
         Throwable cause = var6.getCause();
         if (cause instanceof DeploymentException) {
            throw (DeploymentException)cause;
         } else if (cause instanceof IOException) {
            throw (IOException)cause;
         } else {
            throw new DeploymentException(cause.getMessage(), cause);
         }
      }
   }

   public Future asyncConnectToServer(Class annotatedEndpointClass, URI path) throws DeploymentException {
      if (annotatedEndpointClass.getAnnotation(ClientEndpoint.class) == null) {
         throw new DeploymentException(String.format("Class argument in connectToServer(Class, URI) is to be annotated endpoint class. Class %s does not have @ClientEndpoint", annotatedEndpointClass.getName()));
      } else {
         return this.connectToServer(annotatedEndpointClass, (ClientEndpointConfig)null, path.toString(), false);
      }
   }

   public Future asyncConnectToServer(Class endpointClass, ClientEndpointConfig cec, URI path) throws DeploymentException {
      return this.connectToServer(endpointClass, cec, path.toString(), false);
   }

   public Future asyncConnectToServer(Endpoint endpointInstance, ClientEndpointConfig cec, URI path) throws DeploymentException {
      return this.connectToServer(endpointInstance, cec, path.toString(), false);
   }

   public Future asyncConnectToServer(Object obj, URI path) throws DeploymentException {
      return this.connectToServer(obj, (ClientEndpointConfig)null, path.toString(), false);
   }

   Future connectToServer(final Object o, final ClientEndpointConfig configuration, final String url, boolean synchronous) throws DeploymentException {
      final Map copiedProperties = new HashMap(this.properties);
      this.clientActivityListener.onConnectionInitiated();
      Object executorService;
      if (synchronous) {
         executorService = new SameThreadExecutorService();
      } else {
         executorService = this.getExecutorService();
      }

      final TyrusFuture future = new TyrusFuture() {
         public void setFailure(Throwable throwable) {
            super.setFailure(throwable);
            ClientManager.this.clientActivityListener.onConnectionTerminated();
         }
      };

      try {
         URI uri = new URI(url);
         String scheme = uri.getScheme();
         if (scheme == null || !scheme.equals("ws") && !scheme.equals("wss")) {
            throw new DeploymentException("Incorrect scheme in WebSocket endpoint URI=" + url);
         }
      } catch (URISyntaxException var10) {
         throw new DeploymentException("Incorrect WebSocket endpoint URI=" + url, var10);
      }

      final int handshakeTimeout = this.getHandshakeTimeout();
      ((ExecutorService)executorService).submit(new Runnable() {
         public void run() {
            ErrorCollector collector = new ErrorCollector();
            Integer tyrusIncomingBufferSize = (Integer)Utils.getProperty(copiedProperties, "org.glassfish.tyrus.incomingBufferSize", Integer.class);
            Integer wlsIncomingBufferSize = configuration == null ? null : (Integer)Utils.getProperty(configuration.getUserProperties(), "weblogic.websocket.tyrus.incoming-buffer-size", Integer.class);
            int incomingBufferSize;
            if (tyrusIncomingBufferSize == null && wlsIncomingBufferSize == null) {
               incomingBufferSize = 4194315;
            } else if (wlsIncomingBufferSize != null) {
               incomingBufferSize = wlsIncomingBufferSize;
            } else {
               incomingBufferSize = tyrusIncomingBufferSize;
            }

            final ClientEndpointConfig config;
            final Object endpoint;
            try {
               if (o instanceof Endpoint) {
                  endpoint = (Endpoint)o;
                  config = configuration == null ? Builder.create().build() : configuration;
               } else if (o instanceof Class) {
                  if (Endpoint.class.isAssignableFrom((Class)o)) {
                     endpoint = (Endpoint)ReflectionHelper.getInstance((Class)o, collector);
                     config = configuration == null ? Builder.create().build() : configuration;
                  } else if (((Class)o).getAnnotation(ClientEndpoint.class) != null) {
                     endpoint = AnnotatedEndpoint.fromClass((Class)o, ClientManager.this.componentProvider, false, incomingBufferSize, collector, EndpointEventListener.NO_OP);
                     config = (ClientEndpointConfig)((AnnotatedEndpoint)endpoint).getEndpointConfig();
                  } else {
                     collector.addException(new DeploymentException(String.format("Class %s in not Endpoint descendant and does not have @ClientEndpoint", ((Class)o).getName())));
                     endpoint = null;
                     config = null;
                  }
               } else {
                  endpoint = AnnotatedEndpoint.fromInstance(o, ClientManager.this.componentProvider, false, incomingBufferSize, collector);
                  config = (ClientEndpointConfig)((AnnotatedEndpoint)endpoint).getEndpointConfig();
               }

               if (!collector.isEmpty()) {
                  future.setFailure(collector.composeComprehensiveException());
                  return;
               }
            } catch (Exception var10) {
               future.setFailure(var10);
               return;
            }

            final boolean retryAfterEnabled = (Boolean)Utils.getProperty(copiedProperties, "org.glassfish.tyrus.client.http.retryAfter", Boolean.class, false);
            final ReconnectHandler userReconnectHandler = (ReconnectHandler)Utils.getProperty(copiedProperties, "org.glassfish.tyrus.client.ClientManager.ReconnectHandler", ReconnectHandler.class);
            Runnable connector = new Runnable() {
               private final ReconnectHandler reconnectHandler = retryAfterEnabled ? new RetryAfterReconnectHandler(userReconnectHandler) : userReconnectHandler;

               public void run() {
                  while(true) {
                     final CountDownLatch responseLatch = new CountDownLatch(1);
                     final DebugContext debugContext = new DebugContext();
                     ClientManagerHandshakeListener listener = new ClientManagerHandshakeListener() {
                        private volatile Session session;
                        private volatile Throwable throwable;

                        public void onSessionCreated(Session session) {
                           this.session = session;
                           debugContext.flush();
                           responseLatch.countDown();
                        }

                        public void onError(Throwable exception) {
                           this.throwable = exception;
                           debugContext.flush();
                           responseLatch.countDown();
                        }

                        public Session getSession() {
                           return this.session;
                        }

                        public Throwable getThrowable() {
                           return this.throwable;
                        }
                     };

                     try {
                        TyrusEndpointWrapper clientEndpoint = new TyrusEndpointWrapper((Endpoint)endpoint, config, ClientManager.this.componentProvider, (WebSocketContainer)(ClientManager.this.webSocketContainer == null ? ClientManager.this : ClientManager.this.webSocketContainer), url, (ServerEndpointConfig.Configurator)null, new TyrusEndpointWrapper.SessionListener() {
                           public void onClose(TyrusSession session, CloseReason closeReason) {
                              if (reconnectHandler != null && reconnectHandler.onDisconnect(closeReason)) {
                                 long delay = reconnectHandler.getDelay();
                                 if (delay <= 0L) {
                                    run();
                                 } else {
                                    ClientManager.this.getScheduledExecutorService().schedule(<VAR_NAMELESS_ENCLOSURE>, delay, TimeUnit.SECONDS);
                                 }
                              } else {
                                 ClientManager.this.clientActivityListener.onConnectionTerminated();
                              }

                           }
                        }, (ClusterContext)null, (EndpointEventListener)null, (Boolean)null);

                        URI uri;
                        try {
                           uri = new URI(url);
                        } catch (URISyntaxException var10) {
                           throw new DeploymentException("Invalid URI.", var10);
                        }

                        TyrusClientEngine clientEngine = new TyrusClientEngine(clientEndpoint, listener, copiedProperties, uri, debugContext);
                        ClientManager.this.container.openClientSocket(config, copiedProperties, clientEngine);

                        try {
                           boolean countedDown = responseLatch.await((long)handshakeTimeout, TimeUnit.MILLISECONDS);
                           if (countedDown) {
                              Throwable exception = listener.getThrowable();
                              if (exception != null) {
                                 if (exception instanceof DeploymentException) {
                                    throw (DeploymentException)exception;
                                 }

                                 throw new DeploymentException("Handshake error.", exception);
                              }

                              future.setResult(listener.getSession());
                              return;
                           }

                           ClientEngine.TimeoutHandler timeoutHandler = clientEngine.getTimeoutHandler();
                           if (timeoutHandler != null) {
                              timeoutHandler.handleTimeout();
                           }
                        } catch (DeploymentException var11) {
                           throw var11;
                        } catch (Exception var12) {
                           throw new DeploymentException("Handshake response not received.", var12);
                        }

                        throw new DeploymentException("Handshake response not received.");
                     } catch (Exception var13) {
                        if (this.reconnectHandler != null && this.reconnectHandler.onConnectFailure(var13)) {
                           long delay = this.reconnectHandler.getDelay();
                           if (delay <= 0L) {
                              continue;
                           }

                           ClientManager.this.getScheduledExecutorService().schedule(this, delay, TimeUnit.SECONDS);
                           return;
                        }

                        future.setFailure(var13);
                        return;
                     }
                  }
               }
            };
            connector.run();
         }
      });
      return future;
   }

   private int getHandshakeTimeout() {
      Object o = this.properties.get("org.glassfish.tyrus.client.ClientManager.ContainerTimeout");
      return o != null && o instanceof Integer ? (Integer)o : 30000;
   }

   public int getDefaultMaxBinaryMessageBufferSize() {
      return this.webSocketContainer == null ? this.maxBinaryMessageBufferSize : this.webSocketContainer.getDefaultMaxBinaryMessageBufferSize();
   }

   public void setDefaultMaxBinaryMessageBufferSize(int i) {
      if (this.webSocketContainer == null) {
         this.maxBinaryMessageBufferSize = i;
      } else {
         this.webSocketContainer.setDefaultMaxBinaryMessageBufferSize(i);
      }

   }

   public int getDefaultMaxTextMessageBufferSize() {
      return this.webSocketContainer == null ? this.maxTextMessageBufferSize : this.webSocketContainer.getDefaultMaxTextMessageBufferSize();
   }

   public void setDefaultMaxTextMessageBufferSize(int i) {
      if (this.webSocketContainer == null) {
         this.maxTextMessageBufferSize = i;
      } else {
         this.webSocketContainer.setDefaultMaxTextMessageBufferSize(i);
      }

   }

   public Set getInstalledExtensions() {
      return this.webSocketContainer == null ? Collections.emptySet() : this.webSocketContainer.getInstalledExtensions();
   }

   public long getDefaultAsyncSendTimeout() {
      return this.webSocketContainer == null ? this.defaultAsyncSendTimeout : this.webSocketContainer.getDefaultAsyncSendTimeout();
   }

   public void setAsyncSendTimeout(long timeoutmillis) {
      if (this.webSocketContainer == null) {
         this.defaultAsyncSendTimeout = timeoutmillis;
      } else {
         this.webSocketContainer.setAsyncSendTimeout(timeoutmillis);
      }

   }

   public long getDefaultMaxSessionIdleTimeout() {
      return this.webSocketContainer == null ? this.defaultMaxSessionIdleTimeout : this.webSocketContainer.getDefaultMaxSessionIdleTimeout();
   }

   public void setDefaultMaxSessionIdleTimeout(long defaultMaxSessionIdleTimeout) {
      if (this.webSocketContainer == null) {
         this.defaultMaxSessionIdleTimeout = defaultMaxSessionIdleTimeout;
      } else {
         this.webSocketContainer.setDefaultMaxSessionIdleTimeout(defaultMaxSessionIdleTimeout);
      }

   }

   public Map getProperties() {
      return this.properties;
   }

   private interface ClientActivityListener {
      void onConnectionInitiated();

      void onConnectionTerminated();
   }

   private static class RetryAfterReconnectHandler extends ReconnectHandler {
      private static final int RETRY_AFTER_THRESHOLD = 5;
      private static final int RETRY_AFTER_MAX_DELAY = 300;
      private final AtomicInteger retryCounter = new AtomicInteger(0);
      private final ReconnectHandler userReconnectHandler;
      private long delay = 0L;

      RetryAfterReconnectHandler(ReconnectHandler userReconnectHandler) {
         this.userReconnectHandler = userReconnectHandler;
      }

      public boolean onDisconnect(CloseReason closeReason) {
         return this.userReconnectHandler != null && this.userReconnectHandler.onDisconnect(closeReason);
      }

      public boolean onConnectFailure(Exception exception) {
         if (exception instanceof DeploymentException) {
            Throwable t = exception.getCause();
            if (t != null && t instanceof RetryAfterException) {
               RetryAfterException retryAfterException = (RetryAfterException)t;
               if (retryAfterException.getDelay() != null && this.retryCounter.getAndIncrement() < 5 && retryAfterException.getDelay() <= 300L) {
                  this.delay = retryAfterException.getDelay() < 0L ? 0L : retryAfterException.getDelay();
                  return true;
               }
            }
         }

         return this.userReconnectHandler != null && this.userReconnectHandler.onConnectFailure(exception);
      }

      public long getDelay() {
         return this.delay;
      }
   }

   public static class ReconnectHandler {
      private static final long RECONNECT_DELAY = 5L;

      public boolean onDisconnect(CloseReason closeReason) {
         return false;
      }

      public boolean onConnectFailure(Exception exception) {
         return false;
      }

      public long getDelay() {
         return 5L;
      }
   }

   private static class SameThreadExecutorService extends AbstractExecutorService {
      private SameThreadExecutorService() {
      }

      public void shutdown() {
      }

      public List shutdownNow() {
         return Collections.emptyList();
      }

      public boolean isShutdown() {
         return false;
      }

      public boolean isTerminated() {
         return false;
      }

      public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
         return false;
      }

      public void execute(Runnable command) {
         command.run();
      }

      // $FF: synthetic method
      SameThreadExecutorService(Object x0) {
         this();
      }
   }

   private interface ClientManagerHandshakeListener extends TyrusClientEngine.ClientHandshakeListener {
      Session getSession();

      Throwable getThrowable();
   }
}
