package org.glassfish.tyrus.container.jdk.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.Proxy.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import org.glassfish.tyrus.client.ThreadPoolConfig;
import org.glassfish.tyrus.core.ReflectionHelper;
import org.glassfish.tyrus.core.Utils;
import org.glassfish.tyrus.spi.ClientContainer;
import org.glassfish.tyrus.spi.ClientEngine;
import org.glassfish.tyrus.spi.CompletionHandler;
import org.glassfish.tyrus.spi.UpgradeRequest;

public class JdkClientContainer implements ClientContainer {
   private static final int SSL_INPUT_BUFFER_SIZE = 17000;
   private static final int INPUT_BUFFER_SIZE = 2048;
   private static final Logger LOGGER = Logger.getLogger(JdkClientContainer.class.getName());

   public void openClientSocket(final ClientEndpointConfig cec, final Map properties, final ClientEngine clientEngine) throws DeploymentException, IOException {
      final ThreadPoolConfig threadPoolConfig = (ThreadPoolConfig)Utils.getProperty(properties, "org.glassfish.tyrus.client.workerThreadPoolConfig", ThreadPoolConfig.class);
      if (threadPoolConfig == null) {
         threadPoolConfig = ThreadPoolConfig.defaultConfig();
      }

      String wlsMaxThreadsStr = System.getProperty("weblogic.websocket.client.max-aio-threads");
      if (wlsMaxThreadsStr != null) {
         try {
            int wlsMaxThreads = Integer.parseInt(wlsMaxThreadsStr);
            threadPoolConfig.setMaxPoolSize(wlsMaxThreads);
         } catch (Exception var10) {
            LOGGER.log(Level.CONFIG, String.format("Invalid type of configuration property of %s , %s cannot be cast to Integer", "weblogic.websocket.client.max-aio-threads", wlsMaxThreadsStr));
         }
      }

      final Integer containerIdleTimeout = (Integer)Utils.getProperty(properties, "org.glassfish.tyrus.client.sharedContainerIdleTimeout", Integer.class);
      Callable jdkConnector = new Callable() {
         public Void call() throws DeploymentException {
            TimeoutHandlerProxy timeoutHandlerProxy = new TimeoutHandlerProxy();
            UpgradeRequest upgradeRequest = clientEngine.createUpgradeRequest(timeoutHandlerProxy);
            URI uri = upgradeRequest.getRequestURI();
            List proxies = JdkClientContainer.this.processProxy(properties, uri);
            boolean secure = "wss".equalsIgnoreCase(uri.getScheme());
            final TaskQueueFilter writeQueue;
            TransportFilter transportFilter;
            if (secure) {
               transportFilter = JdkClientContainer.this.createTransportFilter(17000, threadPoolConfig, containerIdleTimeout);
               SslFilter sslFilter = JdkClientContainer.this.createSslFilter(cec, properties, transportFilter, uri);
               writeQueue = JdkClientContainer.this.createTaskQueueFilter(sslFilter);
            } else {
               transportFilter = JdkClientContainer.this.createTransportFilter(2048, threadPoolConfig, containerIdleTimeout);
               writeQueue = JdkClientContainer.this.createTaskQueueFilter(transportFilter);
            }

            ClientFilter clientFilter = JdkClientContainer.this.createClientFilter(properties, writeQueue, clientEngine, this, upgradeRequest);
            timeoutHandlerProxy.setHandler(new ClientEngine.TimeoutHandler() {
               public void handleTimeout() {
                  writeQueue.close();
               }
            });
            Throwable exception = null;
            Iterator var9 = proxies.iterator();

            while(var9.hasNext()) {
               Proxy proxy = (Proxy)var9.next();
               if (proxy.type() == Type.DIRECT) {
                  SocketAddress serverAddress = JdkClientContainer.this.getServerAddress(uri);

                  try {
                     JdkClientContainer.this.connectSynchronously(clientFilter, serverAddress, false);
                     return null;
                  } catch (Throwable var15) {
                     exception = var15;
                  }
               }

               JdkClientContainer.LOGGER.log(Level.CONFIG, String.format("Connecting to '%s' via proxy '%s'.", uri, proxy));
               SocketAddress proxyAddress = proxy.address();
               if (proxyAddress instanceof InetSocketAddress) {
                  InetSocketAddress inetSocketAddress = (InetSocketAddress)proxyAddress;
                  if (inetSocketAddress.isUnresolved()) {
                     proxyAddress = new InetSocketAddress(inetSocketAddress.getHostName(), inetSocketAddress.getPort());
                  }

                  try {
                     JdkClientContainer.this.connectSynchronously(clientFilter, (SocketAddress)proxyAddress, true);
                     return null;
                  } catch (Throwable var14) {
                     JdkClientContainer.LOGGER.log(Level.FINE, "Connecting to " + proxyAddress + " failed", var14);
                     clientFilter.close();
                     exception = var14;
                  }
               }
            }

            throw new DeploymentException("Connection failed.", exception);
         }
      };

      try {
         jdkConnector.call();
      } catch (Exception var11) {
         if (var11 instanceof DeploymentException) {
            throw (DeploymentException)var11;
         } else if (var11 instanceof IOException) {
            throw (IOException)var11;
         } else {
            throw new DeploymentException(var11.getMessage(), var11);
         }
      }
   }

   private SslFilter createSslFilter(ClientEndpointConfig cec, Map properties, TransportFilter transportFilter, URI uri) {
      Object sslEngineConfiguratorObject = properties.get("org.glassfish.tyrus.client.sslEngineConfigurator");
      SslFilter sslFilter = null;
      if (sslEngineConfiguratorObject != null) {
         if (sslEngineConfiguratorObject instanceof org.glassfish.tyrus.client.SslEngineConfigurator) {
            sslFilter = new SslFilter(transportFilter, (org.glassfish.tyrus.client.SslEngineConfigurator)sslEngineConfiguratorObject, uri.getHost());
         } else if (sslEngineConfiguratorObject instanceof SslEngineConfigurator) {
            sslFilter = new SslFilter(transportFilter, (SslEngineConfigurator)sslEngineConfiguratorObject);
         } else {
            LOGGER.log(Level.WARNING, "Invalid 'org.glassfish.tyrus.client.sslEngineConfigurator' property value: " + sslEngineConfiguratorObject + ". Using system defaults.");
         }
      }

      if (sslFilter == null) {
         org.glassfish.tyrus.client.SslContextConfigurator defaultConfig = new org.glassfish.tyrus.client.SslContextConfigurator();
         defaultConfig.retrieve(System.getProperties());
         String wlsSslTrustStore = (String)cec.getUserProperties().get("weblogic.websocket.client.SSL_TRUSTSTORE");
         String wlsSslTrustStorePassword = (String)cec.getUserProperties().get("weblogic.websocket.client.SSL_TRUSTSTORE_PWD");
         if (wlsSslTrustStore != null) {
            defaultConfig.setTrustStoreFile(wlsSslTrustStore);
            if (wlsSslTrustStorePassword != null) {
               defaultConfig.setTrustStorePassword(wlsSslTrustStorePassword);
            }
         }

         org.glassfish.tyrus.client.SslEngineConfigurator sslEngineConfigurator = new org.glassfish.tyrus.client.SslEngineConfigurator(defaultConfig, true, false, false);
         String wlsSslProtocols = (String)cec.getUserProperties().get("weblogic.websocket.client.SSL_PROTOCOLS");
         if (wlsSslProtocols != null) {
            sslEngineConfigurator.setEnabledProtocols(wlsSslProtocols.split(","));
         }

         String wlsIgnoreHostnameVerification = System.getProperties().getProperty("weblogic.security.SSL.ignoreHostnameVerification");
         if ("true".equalsIgnoreCase(wlsIgnoreHostnameVerification)) {
            sslEngineConfigurator.setHostVerificationEnabled(false);
         } else {
            String className = System.getProperties().getProperty("weblogic.security.SSL.HostnameVerifier");
            if (className != null && !className.isEmpty()) {
               Class hostnameVerifierClass = ReflectionHelper.classForName(className);
               if (hostnameVerifierClass != null) {
                  try {
                     HostnameVerifier hostnameVerifier = (HostnameVerifier)ReflectionHelper.getInstance(hostnameVerifierClass);
                     sslEngineConfigurator.setHostnameVerifier(hostnameVerifier);
                  } catch (InstantiationException | IllegalAccessException var16) {
                     LOGGER.log(Level.INFO, String.format("Cannot instantiate class set as a value of '%s' property: %s", "weblogic.security.SSL.HostnameVerifier", className), var16);
                  }
               }
            }
         }

         sslFilter = new SslFilter(transportFilter, sslEngineConfigurator, uri.getHost());
      }

      return sslFilter;
   }

   private TransportFilter createTransportFilter(int sslInputBufferSize, ThreadPoolConfig threadPoolConfig, Integer containerIdleTimeout) {
      return new TransportFilter(sslInputBufferSize, threadPoolConfig, containerIdleTimeout);
   }

   private TaskQueueFilter createTaskQueueFilter(Filter downstreamFilter) {
      return new TaskQueueFilter(downstreamFilter);
   }

   private ClientFilter createClientFilter(Map properties, Filter downstreamFilter, ClientEngine clientEngine, Callable jdkConnector, UpgradeRequest upgradeRequest) throws DeploymentException {
      return new ClientFilter(downstreamFilter, clientEngine, properties, jdkConnector, upgradeRequest);
   }

   private SocketAddress getServerAddress(URI uri) throws DeploymentException {
      int port = Utils.getWsPort(uri);

      try {
         return new InetSocketAddress(uri.getHost(), port);
      } catch (IllegalArgumentException var4) {
         throw new DeploymentException(var4.getMessage(), var4);
      }
   }

   private void connectSynchronously(ClientFilter clientFilter, SocketAddress address, boolean proxy) throws Throwable {
      final AtomicReference exception = new AtomicReference((Object)null);
      final CountDownLatch connectLatch = new CountDownLatch(1);

      try {
         clientFilter.connect(address, proxy, new CompletionHandler() {
            public void completed(Void result) {
               connectLatch.countDown();
            }

            public void failed(Throwable exc) {
               exception.set(exc);
               connectLatch.countDown();
            }
         });
         connectLatch.await();
         Throwable throwable = (Throwable)exception.get();
         if (throwable != null) {
            throw throwable;
         }
      } catch (InterruptedException var7) {
         Thread.currentThread().interrupt();
         throw new DeploymentException("The thread waiting for client to connect has been interrupted before the connection has finished", var7);
      }
   }

   private List processProxy(Map properties, URI uri) throws DeploymentException {
      List proxies = new ArrayList();
      String wlsProxyHost = null;
      Integer wlsProxyPort = null;
      Object value = properties.get("weblogic.websocket.client.PROXY_HOST");
      if (value != null) {
         if (!(value instanceof String)) {
            throw new DeploymentException("weblogic.websocket.client.PROXY_HOST only accept String values.");
         }

         wlsProxyHost = (String)value;
      }

      value = properties.get("weblogic.websocket.client.PROXY_PORT");
      if (value != null) {
         if (!(value instanceof Integer)) {
            throw new DeploymentException("weblogic.websocket.client.PROXY_PORT only accept Integer values.");
         }

         wlsProxyPort = (Integer)value;
      }

      if (wlsProxyHost != null) {
         proxies.add(new Proxy(Type.HTTP, new InetSocketAddress(wlsProxyHost, wlsProxyPort == null ? 80 : wlsProxyPort)));
      } else {
         Object proxyString = properties.get("org.glassfish.tyrus.client.proxy");

         try {
            if (proxyString != null) {
               URI proxyUri = new URI(proxyString.toString());
               if (proxyUri.getHost() == null) {
                  LOGGER.log(Level.WARNING, String.format("Invalid proxy '%s'.", proxyString));
               } else {
                  int proxyPort = proxyUri.getPort() == -1 ? 80 : proxyUri.getPort();
                  proxies.add(new Proxy(Type.HTTP, new InetSocketAddress(proxyUri.getHost(), proxyPort)));
               }
            }
         } catch (URISyntaxException var10) {
            LOGGER.log(Level.WARNING, String.format("Invalid proxy '%s'.", proxyString), var10);
         }
      }

      ProxySelector proxySelector = ProxySelector.getDefault();
      if (proxySelector != null) {
         this.addProxies(proxySelector, uri, "socket", proxies);
         this.addProxies(proxySelector, uri, "https", proxies);
         this.addProxies(proxySelector, uri, "http", proxies);
      }

      if (proxies.isEmpty()) {
         proxies.add(Proxy.NO_PROXY);
      }

      return proxies;
   }

   private void addProxies(ProxySelector proxySelector, URI uri, String scheme, List proxies) {
      Iterator var5 = proxySelector.select(this.getProxyUri(uri, scheme)).iterator();

      while(var5.hasNext()) {
         Proxy p = (Proxy)var5.next();
         switch (p.type()) {
            case HTTP:
               LOGGER.log(Level.FINE, String.format("Found proxy: '%s'", p));
               proxies.add(p);
               break;
            case SOCKS:
               LOGGER.log(Level.INFO, String.format("Socks proxy is not supported, please file new issue at https://java.net/jira/browse/TYRUS. Proxy '%s' will be ignored.", p));
         }
      }

   }

   private URI getProxyUri(URI wsUri, String scheme) {
      try {
         return new URI(scheme, wsUri.getUserInfo(), wsUri.getHost(), wsUri.getPort(), wsUri.getPath(), wsUri.getQuery(), wsUri.getFragment());
      } catch (URISyntaxException var4) {
         LOGGER.log(Level.WARNING, String.format("Exception during generating proxy URI '%s'", wsUri), var4);
         return wsUri;
      }
   }

   private static class TimeoutHandlerProxy implements ClientEngine.TimeoutHandler {
      private volatile ClientEngine.TimeoutHandler handler;

      private TimeoutHandlerProxy() {
      }

      public void handleTimeout() {
         if (this.handler != null) {
            this.handler.handleTimeout();
         }

      }

      public void setHandler(ClientEngine.TimeoutHandler handler) {
         this.handler = handler;
      }

      // $FF: synthetic method
      TimeoutHandlerProxy(Object x0) {
         this();
      }
   }
}
