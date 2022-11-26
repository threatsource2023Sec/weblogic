package org.glassfish.grizzly.http.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.PortRange;
import org.glassfish.grizzly.Processor;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.http.CompressionConfig;
import org.glassfish.grizzly.http.ContentEncoding;
import org.glassfish.grizzly.http.GZipContentEncoding;
import org.glassfish.grizzly.http.LZMAContentEncoding;
import org.glassfish.grizzly.http.CompressionConfig.CompressionMode;
import org.glassfish.grizzly.http.server.filecache.FileCache;
import org.glassfish.grizzly.http.server.jmxbase.JmxEventListener;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.jmxbase.GrizzlyJmxManager;
import org.glassfish.grizzly.memory.ThreadLocalPool;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.ssl.SSLBaseFilter;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.grizzly.threadpool.DefaultWorkerThread;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.grizzly.utils.DelayedExecutor;
import org.glassfish.grizzly.utils.Futures;
import org.glassfish.grizzly.utils.IdleTimeoutFilter;

public class HttpServer {
   private static final Logger LOGGER = Grizzly.logger(HttpServer.class);
   private final ServerConfiguration serverConfig = new ServerConfiguration(this);
   private State state;
   private FutureImpl shutdownFuture;
   private final HttpHandlerChain httpHandlerChain;
   private final Map listeners;
   private volatile ExecutorService auxExecutorService;
   volatile DelayedExecutor delayedExecutor;
   protected volatile GrizzlyJmxManager jmxManager;
   protected volatile Object managementObject;

   public HttpServer() {
      this.state = State.STOPPED;
      this.httpHandlerChain = new HttpHandlerChain(this);
      this.listeners = new HashMap(2);
   }

   public final ServerConfiguration getServerConfiguration() {
      return this.serverConfig;
   }

   public synchronized void addListener(NetworkListener listener) {
      if (this.state == State.RUNNING) {
         this.configureListener(listener);
         if (!listener.isStarted()) {
            try {
               listener.start();
            } catch (IOException var3) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "Failed to start listener [{0}] : {1}", new Object[]{listener.toString(), var3.toString()});
                  LOGGER.log(Level.SEVERE, var3.toString(), var3);
               }
            }
         }
      }

      this.listeners.put(listener.getName(), listener);
   }

   public synchronized NetworkListener getListener(String name) {
      return (NetworkListener)this.listeners.get(name);
   }

   public synchronized Collection getListeners() {
      return Collections.unmodifiableCollection(this.listeners.values());
   }

   public synchronized NetworkListener removeListener(String name) {
      NetworkListener listener = (NetworkListener)this.listeners.remove(name);
      if (listener != null && listener.isStarted()) {
         try {
            listener.shutdownNow();
         } catch (IOException var4) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "Failed to shutdown listener [{0}] : {1}", new Object[]{listener.toString(), var4.toString()});
               LOGGER.log(Level.SEVERE, var4.toString(), var4);
            }
         }
      }

      return listener;
   }

   public synchronized void start() throws IOException {
      if (this.state != State.RUNNING) {
         if (this.state == State.STOPPING) {
            throw new IllegalStateException("The server is currently in pending shutdown state. Wait for the shutdown to complete or force it by calling shutdownNow()");
         } else {
            this.state = State.RUNNING;
            this.shutdownFuture = null;
            this.configureAuxThreadPool();
            this.delayedExecutor = new DelayedExecutor(this.auxExecutorService);
            this.delayedExecutor.start();
            Iterator var1 = this.listeners.values().iterator();

            NetworkListener listener;
            while(var1.hasNext()) {
               listener = (NetworkListener)var1.next();
               this.configureListener(listener);
            }

            if (this.serverConfig.isJmxEnabled()) {
               this.enableJMX();
            }

            var1 = this.listeners.values().iterator();

            while(var1.hasNext()) {
               listener = (NetworkListener)var1.next();

               try {
                  listener.start();
               } catch (IOException var4) {
                  if (LOGGER.isLoggable(Level.FINEST)) {
                     LOGGER.log(Level.FINEST, "Failed to start listener [{0}] : {1}", new Object[]{listener.toString(), var4.toString()});
                     LOGGER.log(Level.FINEST, var4.toString(), var4);
                  }

                  throw var4;
               }
            }

            this.setupHttpHandler();
            if (this.serverConfig.isJmxEnabled()) {
               var1 = this.serverConfig.getJmxEventListeners().iterator();

               while(var1.hasNext()) {
                  JmxEventListener l = (JmxEventListener)var1.next();
                  l.jmxEnabled();
               }
            }

            if (LOGGER.isLoggable(Level.INFO)) {
               LOGGER.log(Level.INFO, "[{0}] Started.", this.getServerConfiguration().getName());
            }

         }
      }
   }

   private void setupHttpHandler() {
      this.serverConfig.addJmxEventListener(this.httpHandlerChain);
      synchronized(this.serverConfig.handlersSync) {
         Iterator var2 = this.serverConfig.orderedHandlers.iterator();

         while(true) {
            if (!var2.hasNext()) {
               break;
            }

            HttpHandler httpHandler = (HttpHandler)var2.next();
            this.httpHandlerChain.addHandler(httpHandler, (HttpHandlerRegistration[])this.serverConfig.handlers.get(httpHandler));
         }
      }

      this.httpHandlerChain.start();
   }

   private void tearDownHttpHandler() {
      this.httpHandlerChain.destroy();
   }

   public HttpHandler getHttpHandler() {
      return this.httpHandlerChain;
   }

   public boolean isStarted() {
      return this.state != State.STOPPED;
   }

   public Object getManagementObject(boolean clear) {
      if (!clear && this.managementObject == null) {
         synchronized(this.serverConfig) {
            if (this.managementObject == null) {
               this.managementObject = MonitoringUtils.loadJmxObject("org.glassfish.grizzly.http.server.jmx.HttpServer", this, HttpServer.class);
            }
         }
      }

      Object var2;
      try {
         var2 = this.managementObject;
      } finally {
         if (clear) {
            this.managementObject = null;
         }

      }

      return var2;
   }

   public synchronized GrizzlyFuture shutdown(long gracePeriod, TimeUnit timeUnit) {
      if (this.state != State.RUNNING) {
         return (GrizzlyFuture)(this.shutdownFuture != null ? this.shutdownFuture : Futures.createReadyFuture(this));
      } else {
         this.shutdownFuture = Futures.createSafeFuture();
         this.state = State.STOPPING;
         final int listenersCount = this.listeners.size();
         final FutureImpl shutdownFutureLocal = this.shutdownFuture;
         CompletionHandler shutdownCompletionHandler = new EmptyCompletionHandler() {
            final AtomicInteger counter = new AtomicInteger(listenersCount);

            public void completed(NetworkListener networkListener) {
               if (this.counter.decrementAndGet() == 0) {
                  try {
                     shutdownFutureLocal.result(HttpServer.this);
                  } catch (Throwable var3) {
                     shutdownFutureLocal.failure(var3);
                  }
               }

            }
         };
         if (listenersCount > 0) {
            Iterator var7 = this.listeners.values().iterator();

            while(var7.hasNext()) {
               NetworkListener listener = (NetworkListener)var7.next();
               listener.shutdown(gracePeriod, timeUnit).addCompletionHandler(shutdownCompletionHandler);
            }
         } else {
            this.shutdownNow();
            shutdownFutureLocal.result(this);
         }

         return this.shutdownFuture;
      }
   }

   public synchronized GrizzlyFuture shutdown() {
      return this.shutdown(-1L, TimeUnit.MILLISECONDS);
   }

   public synchronized void shutdownNow() {
      if (this.state != State.STOPPED) {
         this.state = State.STOPPED;
         boolean var12 = false;

         Iterator var1;
         NetworkListener listener;
         Processor p;
         label201: {
            try {
               var12 = true;
               if (this.serverConfig.isJmxEnabled()) {
                  var1 = this.serverConfig.getJmxEventListeners().iterator();

                  while(var1.hasNext()) {
                     JmxEventListener l = (JmxEventListener)var1.next();
                     l.jmxDisabled();
                  }
               }

               this.tearDownHttpHandler();
               String[] names = (String[])this.listeners.keySet().toArray(new String[this.listeners.size()]);
               String[] var16 = names;
               int var3 = names.length;

               for(int var4 = 0; var4 < var3; ++var4) {
                  String name = var16[var4];
                  this.removeListener(name);
               }

               this.delayedExecutor.stop();
               this.delayedExecutor.destroy();
               this.delayedExecutor = null;
               this.stopAuxThreadPool();
               if (this.serverConfig.isJmxEnabled()) {
                  this.disableJMX();
                  var12 = false;
               } else {
                  var12 = false;
               }
               break label201;
            } catch (Exception var13) {
               LOGGER.log(Level.WARNING, (String)null, var13);
               var12 = false;
            } finally {
               if (var12) {
                  Iterator var7 = this.listeners.values().iterator();

                  while(var7.hasNext()) {
                     NetworkListener listener = (NetworkListener)var7.next();
                     Processor p = listener.getTransport().getProcessor();
                     if (p instanceof FilterChain) {
                        ((FilterChain)p).clear();
                     }
                  }

                  if (this.shutdownFuture != null) {
                     this.shutdownFuture.result(this);
                  }

               }
            }

            var1 = this.listeners.values().iterator();

            while(var1.hasNext()) {
               listener = (NetworkListener)var1.next();
               p = listener.getTransport().getProcessor();
               if (p instanceof FilterChain) {
                  ((FilterChain)p).clear();
               }
            }

            if (this.shutdownFuture != null) {
               this.shutdownFuture.result(this);
            }

            return;
         }

         var1 = this.listeners.values().iterator();

         while(var1.hasNext()) {
            listener = (NetworkListener)var1.next();
            p = listener.getTransport().getProcessor();
            if (p instanceof FilterChain) {
               ((FilterChain)p).clear();
            }
         }

         if (this.shutdownFuture != null) {
            this.shutdownFuture.result(this);
         }

      }
   }

   /** @deprecated */
   public void stop() {
      this.shutdownNow();
   }

   public static HttpServer createSimpleServer() {
      return createSimpleServer(".");
   }

   public static HttpServer createSimpleServer(String docRoot) {
      return createSimpleServer(docRoot, 8080);
   }

   public static HttpServer createSimpleServer(String docRoot, int port) {
      return createSimpleServer(docRoot, "0.0.0.0", port);
   }

   public static HttpServer createSimpleServer(String docRoot, PortRange range) {
      return createSimpleServer(docRoot, "0.0.0.0", range);
   }

   public static HttpServer createSimpleServer(String docRoot, SocketAddress socketAddress) {
      InetSocketAddress inetAddr = (InetSocketAddress)socketAddress;
      return createSimpleServer(docRoot, inetAddr.getHostName(), inetAddr.getPort());
   }

   public static HttpServer createSimpleServer(String docRoot, String host, int port) {
      return createSimpleServer(docRoot, host, new PortRange(port));
   }

   public static HttpServer createSimpleServer(String docRoot, String host, PortRange range) {
      HttpServer server = new HttpServer();
      ServerConfiguration config = server.getServerConfiguration();
      if (docRoot != null) {
         config.addHttpHandler(new StaticHttpHandler(new String[]{docRoot}), (String[])("/"));
      }

      NetworkListener listener = new NetworkListener("grizzly", host, range);
      server.addListener(listener);
      return server;
   }

   protected void enableJMX() {
      if (this.jmxManager == null) {
         synchronized(this.serverConfig) {
            if (this.jmxManager == null) {
               this.jmxManager = GrizzlyJmxManager.instance();
            }
         }
      }

      this.jmxManager.registerAtRoot(this.getManagementObject(false), this.serverConfig.getName());
   }

   protected void disableJMX() {
      if (this.jmxManager != null) {
         this.jmxManager.deregister(this.getManagementObject(true));
      }

   }

   private void configureListener(NetworkListener listener) {
      FilterChain chain = listener.getFilterChain();
      if (chain == null) {
         FilterChainBuilder builder = FilterChainBuilder.stateless();
         builder.add(new TransportFilter());
         if (listener.isSecure()) {
            SSLEngineConfigurator sslConfig = listener.getSslEngineConfig();
            if (sslConfig == null) {
               sslConfig = new SSLEngineConfigurator(SSLContextConfigurator.DEFAULT_CONFIG, false, false, false);
               listener.setSSLEngineConfig(sslConfig);
            }

            SSLBaseFilter filter = new SSLBaseFilter(sslConfig);
            builder.add(filter);
         }

         int maxHeaderSize = listener.getMaxHttpHeaderSize() == -1 ? 8192 : listener.getMaxHttpHeaderSize();
         org.glassfish.grizzly.http.HttpServerFilter httpServerCodecFilter = new org.glassfish.grizzly.http.HttpServerFilter(listener.isChunkingEnabled(), maxHeaderSize, (String)null, listener.getKeepAlive(), (DelayedExecutor)null, listener.getMaxRequestHeaders(), listener.getMaxResponseHeaders());
         Set contentEncodings = this.configureCompressionEncodings(listener);
         Iterator var7 = contentEncodings.iterator();

         while(var7.hasNext()) {
            ContentEncoding contentEncoding = (ContentEncoding)var7.next();
            httpServerCodecFilter.addContentEncoding(contentEncoding);
         }

         httpServerCodecFilter.setAllowPayloadForUndefinedHttpMethods(this.serverConfig.isAllowPayloadForUndefinedHttpMethods());
         httpServerCodecFilter.setMaxPayloadRemainderToSkip(this.serverConfig.getMaxPayloadRemainderToSkip());
         httpServerCodecFilter.getMonitoringConfig().addProbes(this.serverConfig.getMonitoringConfig().getHttpConfig().getProbes());
         builder.add(httpServerCodecFilter);
         builder.add(new IdleTimeoutFilter(this.delayedExecutor, (long)listener.getKeepAlive().getIdleTimeoutInSeconds(), TimeUnit.SECONDS));
         Transport transport = listener.getTransport();
         FileCache fileCache = listener.getFileCache();
         fileCache.initialize(this.delayedExecutor);
         FileCacheFilter fileCacheFilter = new FileCacheFilter(fileCache);
         fileCache.getMonitoringConfig().addProbes(this.serverConfig.getMonitoringConfig().getFileCacheConfig().getProbes());
         builder.add(fileCacheFilter);
         ServerFilterConfiguration config = new ServerFilterConfiguration(this.serverConfig);
         if (listener.isSendFileExplicitlyConfigured()) {
            config.setSendFileEnabled(listener.isSendFileEnabled());
            fileCache.setFileSendEnabled(listener.isSendFileEnabled());
         }

         if (listener.getBackendConfiguration() != null) {
            config.setBackendConfiguration(listener.getBackendConfiguration());
         }

         if (listener.getDefaultErrorPageGenerator() != null) {
            config.setDefaultErrorPageGenerator(listener.getDefaultErrorPageGenerator());
         }

         if (listener.getSessionManager() != null) {
            config.setSessionManager(listener.getSessionManager());
         }

         config.setTraceEnabled(config.isTraceEnabled() || listener.isTraceEnabled());
         config.setMaxFormPostSize(listener.getMaxFormPostSize());
         config.setMaxBufferedPostSize(listener.getMaxBufferedPostSize());
         HttpServerFilter httpServerFilter = new HttpServerFilter(config, this.delayedExecutor);
         httpServerFilter.setHttpHandler(this.httpHandlerChain);
         httpServerFilter.getMonitoringConfig().addProbes(this.serverConfig.getMonitoringConfig().getWebServerConfig().getProbes());
         builder.add(httpServerFilter);
         AddOn[] addons = (AddOn[])listener.getAddOnSet().getArray();
         if (addons != null) {
            AddOn[] var13 = addons;
            int var14 = addons.length;

            for(int var15 = 0; var15 < var14; ++var15) {
               AddOn addon = var13[var15];
               addon.setup(listener, builder);
            }
         }

         chain = builder.build();
         listener.setFilterChain(chain);
         int transactionTimeout = listener.getTransactionTimeout();
         if (transactionTimeout >= 0) {
            ThreadPoolConfig threadPoolConfig = transport.getWorkerThreadPoolConfig();
            if (threadPoolConfig != null) {
               threadPoolConfig.setTransactionTimeout(this.delayedExecutor, (long)transactionTimeout, TimeUnit.SECONDS);
            }
         }
      }

      this.configureMonitoring(listener);
   }

   protected Set configureCompressionEncodings(NetworkListener listener) {
      CompressionConfig compressionConfig = listener.getCompressionConfig();
      if (compressionConfig.getCompressionMode() != CompressionMode.OFF) {
         ContentEncoding gzipContentEncoding = new GZipContentEncoding(512, 512, new CompressionEncodingFilter(compressionConfig, GZipContentEncoding.getGzipAliases()));
         ContentEncoding lzmaEncoding = new LZMAContentEncoding(new CompressionEncodingFilter(compressionConfig, LZMAContentEncoding.getLzmaAliases()));
         Set set = new HashSet(2);
         set.add(gzipContentEncoding);
         set.add(lzmaEncoding);
         return set;
      } else {
         return Collections.emptySet();
      }
   }

   private void configureMonitoring(NetworkListener listener) {
      TCPNIOTransport transport = listener.getTransport();
      MonitoringConfig transportMonitoringCfg = transport.getMonitoringConfig();
      MonitoringConfig connectionMonitoringCfg = transport.getConnectionMonitoringConfig();
      MonitoringConfig memoryMonitoringCfg = transport.getMemoryManager().getMonitoringConfig();
      MonitoringConfig threadPoolMonitoringCfg = transport.getThreadPoolMonitoringConfig();
      transportMonitoringCfg.addProbes(this.serverConfig.getMonitoringConfig().getTransportConfig().getProbes());
      connectionMonitoringCfg.addProbes(this.serverConfig.getMonitoringConfig().getConnectionConfig().getProbes());
      memoryMonitoringCfg.addProbes(this.serverConfig.getMonitoringConfig().getMemoryConfig().getProbes());
      threadPoolMonitoringCfg.addProbes(this.serverConfig.getMonitoringConfig().getThreadPoolConfig().getProbes());
   }

   private void configureAuxThreadPool() {
      final AtomicInteger threadCounter = new AtomicInteger();
      this.auxExecutorService = Executors.newCachedThreadPool(new ThreadFactory() {
         public Thread newThread(Runnable r) {
            Thread newThread = new DefaultWorkerThread(AttributeBuilder.DEFAULT_ATTRIBUTE_BUILDER, HttpServer.this.serverConfig.getName() + "-" + threadCounter.getAndIncrement(), (ThreadLocalPool)null, r);
            newThread.setDaemon(true);
            return newThread;
         }
      });
   }

   private void stopAuxThreadPool() {
      ExecutorService localThreadPool = this.auxExecutorService;
      this.auxExecutorService = null;
      if (localThreadPool != null) {
         localThreadPool.shutdownNow();
      }

   }

   synchronized void onAddHttpHandler(HttpHandler httpHandler, HttpHandlerRegistration[] registrations) {
      if (this.isStarted()) {
         this.httpHandlerChain.addHandler(httpHandler, registrations);
      }

   }

   synchronized void onRemoveHttpHandler(HttpHandler httpHandler) {
      if (this.isStarted()) {
         this.httpHandlerChain.removeHttpHandler(httpHandler);
      }

   }
}
