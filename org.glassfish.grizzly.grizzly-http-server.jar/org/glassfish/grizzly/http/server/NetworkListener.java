package org.glassfish.grizzly.http.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.GracefulShutdownListener;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.PortRange;
import org.glassfish.grizzly.ShutdownContext;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.ShutdownEvent;
import org.glassfish.grizzly.http.CompressionConfig;
import org.glassfish.grizzly.http.HttpCodecFilter;
import org.glassfish.grizzly.http.KeepAlive;
import org.glassfish.grizzly.http.CompressionConfig.CompressionMode;
import org.glassfish.grizzly.http.server.filecache.FileCache;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.nio.transport.TCPNIOServerConnection;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.grizzly.strategies.SameThreadIOStrategy;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.grizzly.utils.ArraySet;
import org.glassfish.grizzly.utils.Futures;

public class NetworkListener {
   private static final Logger LOGGER = Grizzly.logger(NetworkListener.class);
   public static final String DEFAULT_NETWORK_HOST = "0.0.0.0";
   public static final int DEFAULT_NETWORK_PORT = 8080;
   private String host;
   private int port;
   private final boolean isBindToInherited;
   private int transactionTimeout;
   private PortRange portRange;
   private final String name;
   private final KeepAlive keepAliveConfig;
   private FilterChain filterChain;
   private TCPNIOTransport transport;
   private TCPNIOServerConnection serverConnection;
   private ErrorPageGenerator defaultErrorPageGenerator;
   private SessionManager sessionManager;
   private boolean secure;
   private final ArraySet addons;
   private boolean chunkingEnabled;
   private SSLEngineConfigurator sslEngineConfig;
   private int maxHttpHeaderSize;
   private final FileCache fileCache;
   private volatile int maxPendingBytes;
   private State state;
   private FutureImpl shutdownFuture;
   private ShutdownEvent shutdownEvent;
   private HttpServerFilter httpServerFilter;
   private HttpCodecFilter httpCodecFilter;
   private final CompressionConfig compressionConfig;
   private boolean authPassThroughEnabled;
   private int maxFormPostSize;
   private int maxBufferedPostSize;
   private String restrictedUserAgents;
   private int uploadTimeout;
   private boolean disableUploadTimeout;
   private boolean traceEnabled;
   private String uriEncoding;
   private Boolean sendFileEnabled;
   private BackendConfiguration backendConfiguration;
   private int maxRequestHeaders;
   private int maxResponseHeaders;

   public NetworkListener(String name) {
      this(name, false);
   }

   public NetworkListener(String name, boolean isBindToInherited) {
      this.host = "0.0.0.0";
      this.port = 8080;
      this.transactionTimeout = -1;
      this.keepAliveConfig = new KeepAlive();
      TCPNIOTransportBuilder builder = TCPNIOTransportBuilder.newInstance();
      int coresCount = Runtime.getRuntime().availableProcessors() * 2;
      this.transport = ((TCPNIOTransportBuilder)((TCPNIOTransportBuilder)builder.setIOStrategy(SameThreadIOStrategy.getInstance())).setWorkerThreadPoolConfig(ThreadPoolConfig.defaultConfig().setPoolName("Grizzly-worker").setCorePoolSize(coresCount).setMaxPoolSize(coresCount).setMemoryManager(builder.getMemoryManager()))).build();
      this.addons = new ArraySet(AddOn.class);
      this.chunkingEnabled = true;
      this.maxHttpHeaderSize = -1;
      this.fileCache = new FileCache();
      this.maxPendingBytes = -1;
      this.state = State.STOPPED;
      this.compressionConfig = new CompressionConfig();
      this.maxFormPostSize = 2097152;
      this.maxBufferedPostSize = 2097152;
      this.maxRequestHeaders = 100;
      this.maxResponseHeaders = 100;
      validateArg("name", name);
      this.name = name;
      this.isBindToInherited = isBindToInherited;
   }

   public NetworkListener(String name, String host) {
      this(name, host, 8080);
   }

   public NetworkListener(String name, String host, int port) {
      this.host = "0.0.0.0";
      this.port = 8080;
      this.transactionTimeout = -1;
      this.keepAliveConfig = new KeepAlive();
      TCPNIOTransportBuilder builder = TCPNIOTransportBuilder.newInstance();
      int coresCount = Runtime.getRuntime().availableProcessors() * 2;
      this.transport = ((TCPNIOTransportBuilder)((TCPNIOTransportBuilder)builder.setIOStrategy(SameThreadIOStrategy.getInstance())).setWorkerThreadPoolConfig(ThreadPoolConfig.defaultConfig().setPoolName("Grizzly-worker").setCorePoolSize(coresCount).setMaxPoolSize(coresCount).setMemoryManager(builder.getMemoryManager()))).build();
      this.addons = new ArraySet(AddOn.class);
      this.chunkingEnabled = true;
      this.maxHttpHeaderSize = -1;
      this.fileCache = new FileCache();
      this.maxPendingBytes = -1;
      this.state = State.STOPPED;
      this.compressionConfig = new CompressionConfig();
      this.maxFormPostSize = 2097152;
      this.maxBufferedPostSize = 2097152;
      this.maxRequestHeaders = 100;
      this.maxResponseHeaders = 100;
      validateArg("name", name);
      validateArg("host", host);
      if (port < 0) {
         throw new IllegalArgumentException("Invalid port");
      } else {
         this.name = name;
         this.host = host;
         this.port = port;
         this.isBindToInherited = false;
      }
   }

   public NetworkListener(String name, String host, PortRange portRange) {
      this.host = "0.0.0.0";
      this.port = 8080;
      this.transactionTimeout = -1;
      this.keepAliveConfig = new KeepAlive();
      TCPNIOTransportBuilder builder = TCPNIOTransportBuilder.newInstance();
      int coresCount = Runtime.getRuntime().availableProcessors() * 2;
      this.transport = ((TCPNIOTransportBuilder)((TCPNIOTransportBuilder)builder.setIOStrategy(SameThreadIOStrategy.getInstance())).setWorkerThreadPoolConfig(ThreadPoolConfig.defaultConfig().setPoolName("Grizzly-worker").setCorePoolSize(coresCount).setMaxPoolSize(coresCount).setMemoryManager(builder.getMemoryManager()))).build();
      this.addons = new ArraySet(AddOn.class);
      this.chunkingEnabled = true;
      this.maxHttpHeaderSize = -1;
      this.fileCache = new FileCache();
      this.maxPendingBytes = -1;
      this.state = State.STOPPED;
      this.compressionConfig = new CompressionConfig();
      this.maxFormPostSize = 2097152;
      this.maxBufferedPostSize = 2097152;
      this.maxRequestHeaders = 100;
      this.maxResponseHeaders = 100;
      validateArg("name", name);
      validateArg("host", host);
      this.name = name;
      this.host = host;
      this.port = -1;
      this.portRange = portRange;
      this.isBindToInherited = false;
   }

   public String getName() {
      return this.name;
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public PortRange getPortRange() {
      return this.portRange;
   }

   public KeepAlive getKeepAlive() {
      return this.keepAliveConfig;
   }

   public TCPNIOTransport getTransport() {
      return this.transport;
   }

   public void setTransport(TCPNIOTransport transport) {
      if (transport != null) {
         if (transport.isStopped()) {
            this.transport = transport;
         }
      }
   }

   public Connection getServerConnection() {
      return this.serverConnection;
   }

   public AddOn[] getAddOns() {
      return (AddOn[])this.addons.obtainArrayCopy();
   }

   protected ArraySet getAddOnSet() {
      return this.addons;
   }

   public boolean registerAddOn(AddOn addon) {
      return this.addons.add(addon);
   }

   public boolean deregisterAddOn(AddOn addon) {
      return this.addons.remove(addon);
   }

   public boolean isChunkingEnabled() {
      return this.chunkingEnabled;
   }

   public void setChunkingEnabled(boolean chunkingEnabled) {
      this.chunkingEnabled = chunkingEnabled;
   }

   public boolean isSecure() {
      return this.secure;
   }

   public void setSecure(boolean secure) {
      if (this.isStopped()) {
         this.secure = secure;
      }
   }

   public String getScheme() {
      BackendConfiguration config = this.backendConfiguration;
      return config != null ? config.getScheme() : null;
   }

   public void setScheme(String scheme) {
      BackendConfiguration config = this.backendConfiguration;
      if (config == null) {
         config = new BackendConfiguration();
      }

      config.setScheme(scheme);
      this.backendConfiguration = config;
   }

   public BackendConfiguration getBackendConfiguration() {
      return this.backendConfiguration;
   }

   public void setBackendConfiguration(BackendConfiguration backendConfiguration) {
      this.backendConfiguration = backendConfiguration;
   }

   public int getMaxRequestHeaders() {
      return this.maxRequestHeaders;
   }

   public void setMaxRequestHeaders(int maxRequestHeaders) {
      this.maxRequestHeaders = maxRequestHeaders;
   }

   public int getMaxResponseHeaders() {
      return this.maxResponseHeaders;
   }

   public void setMaxResponseHeaders(int maxResponseHeaders) {
      this.maxResponseHeaders = maxResponseHeaders;
   }

   public SSLEngineConfigurator getSslEngineConfig() {
      return this.sslEngineConfig;
   }

   public void setSSLEngineConfig(SSLEngineConfigurator sslEngineConfig) {
      if (this.isStopped()) {
         this.sslEngineConfig = sslEngineConfig;
      }
   }

   public int getMaxHttpHeaderSize() {
      return this.maxHttpHeaderSize;
   }

   public void setMaxHttpHeaderSize(int maxHttpHeaderSize) {
      if (this.isStopped()) {
         this.maxHttpHeaderSize = maxHttpHeaderSize;
      }
   }

   public FilterChain getFilterChain() {
      return this.filterChain;
   }

   void setFilterChain(FilterChain filterChain) {
      if (this.isStopped()) {
         if (filterChain != null) {
            this.filterChain = filterChain;
         }

      }
   }

   public FileCache getFileCache() {
      return this.fileCache;
   }

   public int getMaxPendingBytes() {
      return this.maxPendingBytes;
   }

   public void setMaxPendingBytes(int maxPendingBytes) {
      this.maxPendingBytes = maxPendingBytes;
      this.transport.getAsyncQueueIO().getWriter().setMaxPendingBytesPerConnection(maxPendingBytes);
   }

   public boolean isPaused() {
      return this.state == State.PAUSED;
   }

   public boolean isStarted() {
      return this.state != State.STOPPED;
   }

   public synchronized void start() throws IOException {
      if (!this.isStarted()) {
         this.shutdownFuture = null;
         if (this.filterChain == null) {
            throw new IllegalStateException("No FilterChain available.");
         } else {
            this.transport.setProcessor(this.filterChain);
            if (this.isBindToInherited) {
               this.serverConnection = this.transport.bindToInherited();
            } else {
               this.serverConnection = this.port != -1 ? this.transport.bind(this.host, this.port) : this.transport.bind(this.host, this.portRange, this.transport.getServerConnectionBackLog());
            }

            this.port = ((InetSocketAddress)this.serverConnection.getLocalAddress()).getPort();
            this.transport.addShutdownListener(new GracefulShutdownListener() {
               public void shutdownRequested(final ShutdownContext shutdownContext) {
                  final FutureImpl shutdownFutureLocal = NetworkListener.this.shutdownFuture;
                  NetworkListener.this.filterChain.fireEventDownstream(NetworkListener.this.serverConnection, NetworkListener.this.shutdownEvent, new EmptyCompletionHandler() {
                     public void completed(FilterChainContext result) {
                        final Set tasks = NetworkListener.this.shutdownEvent.getShutdownTasks();
                        if (!tasks.isEmpty()) {
                           final ExecutorService shutdownService = Executors.newFixedThreadPool(Math.min(5, tasks.size()) + 1);
                           shutdownService.submit(new Runnable() {
                              public void run() {
                                 try {
                                    List futures;
                                    if (NetworkListener.this.shutdownEvent.getGracePeriod() == -1L) {
                                       futures = shutdownService.invokeAll(tasks);
                                    } else {
                                       futures = shutdownService.invokeAll(tasks, NetworkListener.this.shutdownEvent.getGracePeriod(), NetworkListener.this.shutdownEvent.getTimeUnit());
                                    }

                                    Iterator var2 = futures.iterator();

                                    while(var2.hasNext()) {
                                       Future future = (Future)var2.next();

                                       try {
                                          future.get();
                                       } catch (ExecutionException var5) {
                                          if (NetworkListener.LOGGER.isLoggable(Level.SEVERE)) {
                                             NetworkListener.LOGGER.log(Level.SEVERE, "Error processing shutdown task filter.", var5);
                                          }
                                       }
                                    }

                                    shutdownFutureLocal.result(NetworkListener.this);
                                    shutdownContext.ready();
                                    shutdownService.shutdownNow();
                                 } catch (InterruptedException var6) {
                                    if (NetworkListener.LOGGER.isLoggable(Level.WARNING)) {
                                       NetworkListener.LOGGER.warning("NetworkListener shutdown interrupted.");
                                    }

                                    if (NetworkListener.LOGGER.isLoggable(Level.FINE)) {
                                       NetworkListener.LOGGER.log(Level.FINE, var6.toString(), var6);
                                    }
                                 }

                              }
                           });
                        }

                     }
                  });
               }

               public void shutdownForced() {
                  NetworkListener.this.serverConnection = null;
                  if (NetworkListener.this.shutdownFuture != null) {
                     NetworkListener.this.shutdownFuture.result(NetworkListener.this);
                  }

               }
            });
            this.transport.start();
            this.state = State.RUNNING;
            if (LOGGER.isLoggable(Level.INFO)) {
               LOGGER.log(Level.INFO, "Started listener bound to [{0}]", this.host + ':' + this.port);
            }

         }
      }
   }

   public synchronized GrizzlyFuture shutdown(long gracePeriod, TimeUnit timeUnit) {
      if (this.state != State.STOPPING && this.state != State.STOPPED) {
         if (this.state == State.PAUSED) {
            this.resume();
         }

         this.shutdownEvent = new ShutdownEvent(gracePeriod, timeUnit);
         this.state = State.STOPPING;
         this.shutdownFuture = Futures.createSafeFuture();
         this.transport.shutdown(gracePeriod, timeUnit);
         return this.shutdownFuture;
      } else {
         return (GrizzlyFuture)(this.shutdownFuture != null ? this.shutdownFuture : Futures.createReadyFuture(this));
      }
   }

   public synchronized GrizzlyFuture shutdown() {
      return this.shutdown(-1L, TimeUnit.MILLISECONDS);
   }

   public synchronized void shutdownNow() throws IOException {
      if (this.state != State.STOPPED) {
         try {
            this.serverConnection = null;
            this.transport.shutdownNow();
            if (LOGGER.isLoggable(Level.INFO)) {
               LOGGER.log(Level.INFO, "Stopped listener bound to [{0}]", this.host + ':' + this.port);
            }
         } finally {
            this.state = State.STOPPED;
            if (this.shutdownFuture != null) {
               this.shutdownFuture.result(this);
            }

         }

      }
   }

   /** @deprecated */
   public void stop() throws IOException {
      this.shutdownNow();
   }

   public synchronized void pause() {
      if (this.state == State.RUNNING) {
         this.transport.pause();
         this.state = State.PAUSED;
         if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Paused listener bound to [{0}]", this.host + ':' + this.port);
         }

      }
   }

   public synchronized void resume() {
      if (this.state == State.PAUSED) {
         this.transport.resume();
         this.state = State.RUNNING;
         if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Resumed listener bound to [{0}]", this.host + ':' + this.port);
         }

      }
   }

   public String toString() {
      return "NetworkListener{name='" + this.name + '\'' + ", host='" + this.host + '\'' + ", port=" + this.port + ", secure=" + this.secure + ", state=" + this.state + '}';
   }

   public Object createManagementObject() {
      return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.http.server.jmx.NetworkListener", this, NetworkListener.class);
   }

   public HttpServerFilter getHttpServerFilter() {
      if (this.httpServerFilter == null) {
         int idx = this.filterChain.indexOfType(HttpServerFilter.class);
         if (idx == -1) {
            return null;
         }

         this.httpServerFilter = (HttpServerFilter)this.filterChain.get(idx);
      }

      return this.httpServerFilter;
   }

   public HttpCodecFilter getHttpCodecFilter() {
      if (this.httpCodecFilter == null) {
         int idx = this.filterChain.indexOfType(HttpCodecFilter.class);
         if (idx == -1) {
            return null;
         }

         this.httpCodecFilter = (HttpCodecFilter)this.filterChain.get(idx);
      }

      return this.httpCodecFilter;
   }

   private static void validateArg(String name, String value) {
      if (value == null || value.length() == 0) {
         throw new IllegalArgumentException("Argument " + name + " cannot be " + (value == null ? "null" : "have a zero length"));
      }
   }

   public boolean isAuthPassThroughEnabled() {
      return this.authPassThroughEnabled;
   }

   public void setAuthPassThroughEnabled(boolean authPassthroughEnabled) {
      this.authPassThroughEnabled = authPassthroughEnabled;
   }

   public CompressionConfig getCompressionConfig() {
      return this.compressionConfig;
   }

   /** @deprecated */
   public String getCompression() {
      return this.compressionConfig.getCompressionMode().name();
   }

   /** @deprecated */
   public void setCompression(String compression) {
      this.compressionConfig.setCompressionMode(CompressionMode.fromString(compression));
   }

   /** @deprecated */
   public int getCompressionMinSize() {
      return this.compressionConfig.getCompressionMinSize();
   }

   /** @deprecated */
   public void setCompressionMinSize(int compressionMinSize) {
      this.compressionConfig.setCompressionMinSize(compressionMinSize);
   }

   /** @deprecated */
   public String getCompressibleMimeTypes() {
      return setToString(this.compressionConfig.getCompressibleMimeTypes());
   }

   /** @deprecated */
   public void setCompressibleMimeTypes(String compressibleMimeTypes) {
      this.compressionConfig.setCompressibleMimeTypes(stringToSet(compressibleMimeTypes));
   }

   /** @deprecated */
   public String getNoCompressionUserAgents() {
      return setToString(this.compressionConfig.getNoCompressionUserAgents());
   }

   /** @deprecated */
   public void setNoCompressionUserAgents(String noCompressionUserAgents) {
      this.compressionConfig.setNoCompressionUserAgents(stringToSet(noCompressionUserAgents));
   }

   public boolean isDisableUploadTimeout() {
      return this.disableUploadTimeout;
   }

   public void setDisableUploadTimeout(boolean disableUploadTimeout) {
      this.disableUploadTimeout = disableUploadTimeout;
   }

   public int getMaxFormPostSize() {
      return this.maxFormPostSize;
   }

   public void setMaxFormPostSize(int maxFormPostSize) {
      this.maxFormPostSize = maxFormPostSize < 0 ? -1 : maxFormPostSize;
   }

   public int getMaxBufferedPostSize() {
      return this.maxBufferedPostSize;
   }

   public void setMaxBufferedPostSize(int maxBufferedPostSize) {
      this.maxBufferedPostSize = maxBufferedPostSize < 0 ? -1 : maxBufferedPostSize;
   }

   public String getRestrictedUserAgents() {
      return this.restrictedUserAgents;
   }

   public void setRestrictedUserAgents(String restrictedUserAgents) {
      this.restrictedUserAgents = restrictedUserAgents;
   }

   public boolean isTraceEnabled() {
      return this.traceEnabled;
   }

   public void setTraceEnabled(boolean traceEnabled) {
      this.traceEnabled = traceEnabled;
   }

   public int getUploadTimeout() {
      return this.uploadTimeout;
   }

   public void setUploadTimeout(int uploadTimeout) {
      this.uploadTimeout = uploadTimeout;
   }

   public String getUriEncoding() {
      return this.uriEncoding;
   }

   public void setUriEncoding(String uriEncoding) {
      this.uriEncoding = uriEncoding;
   }

   public int getTransactionTimeout() {
      return this.transactionTimeout;
   }

   public void setTransactionTimeout(int transactionTimeout) {
      this.transactionTimeout = transactionTimeout;
   }

   public boolean isSendFileEnabled() {
      return this.sendFileEnabled;
   }

   public void setSendFileEnabled(boolean sendFileEnabled) {
      this.sendFileEnabled = sendFileEnabled;
   }

   public ErrorPageGenerator getDefaultErrorPageGenerator() {
      return this.defaultErrorPageGenerator;
   }

   public void setDefaultErrorPageGenerator(ErrorPageGenerator defaultErrorPageGenerator) {
      this.defaultErrorPageGenerator = defaultErrorPageGenerator;
   }

   public SessionManager getSessionManager() {
      return this.sessionManager;
   }

   public void setSessionManager(SessionManager sessionManager) {
      this.sessionManager = sessionManager;
   }

   boolean isSendFileExplicitlyConfigured() {
      return this.sendFileEnabled != null;
   }

   private boolean isStopped() {
      return this.state == State.STOPPED || this.state == State.STOPPING;
   }

   private static String setToString(Set set) {
      StringBuilder sb = new StringBuilder(set.size() * 10);

      String elem;
      for(Iterator var2 = set.iterator(); var2.hasNext(); sb.append(elem)) {
         elem = (String)var2.next();
         if (sb.length() > 0) {
            sb.append(',');
         }
      }

      return sb.toString();
   }

   private static Set stringToSet(String s) {
      return s == null ? null : new HashSet(Arrays.asList(s.split(",")));
   }
}
