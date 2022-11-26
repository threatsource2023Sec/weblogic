package org.glassfish.tyrus.server;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Future;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.core.BaseContainer;
import org.glassfish.tyrus.core.ErrorCollector;
import org.glassfish.tyrus.spi.ServerContainer;

public abstract class TyrusServerContainer extends BaseContainer implements ServerContainer {
   private final ErrorCollector collector = new ErrorCollector();
   private final Set dynamicallyAddedClasses;
   private final Set dynamicallyAddedEndpointConfigs;
   private final Set classes;
   private final ServerApplicationConfig serverApplicationConfig;
   private boolean canDeploy = true;
   private long defaultMaxSessionIdleTimeout = 0L;
   private long defaultAsyncSendTimeout = 0L;
   private int maxTextMessageBufferSize = Integer.MAX_VALUE;
   private int maxBinaryMessageBufferSize = Integer.MAX_VALUE;
   private ClientManager clientManager = null;
   private volatile int port = -1;

   public TyrusServerContainer(Set classes) {
      this.classes = (Set)(classes == null ? Collections.emptySet() : new HashSet(classes));
      this.dynamicallyAddedClasses = new HashSet();
      this.dynamicallyAddedEndpointConfigs = new HashSet();
      this.serverApplicationConfig = null;
   }

   public TyrusServerContainer(ServerApplicationConfig serverApplicationConfig) {
      this.classes = new HashSet();
      this.dynamicallyAddedClasses = new HashSet();
      this.dynamicallyAddedEndpointConfigs = new HashSet();
      this.serverApplicationConfig = serverApplicationConfig;
   }

   public void start(String rootPath, int port) throws IOException, DeploymentException {
      ServerApplicationConfig configuration = new TyrusServerConfiguration(this.classes, this.dynamicallyAddedClasses, this.dynamicallyAddedEndpointConfigs, this.collector);

      try {
         Iterator var4 = configuration.getAnnotatedEndpointClasses((Set)null).iterator();

         Class endpointClass;
         while(var4.hasNext()) {
            endpointClass = (Class)var4.next();
            this.register(endpointClass);
         }

         var4 = configuration.getEndpointConfigs((Set)null).iterator();

         ServerEndpointConfig serverEndpointConfiguration;
         while(var4.hasNext()) {
            serverEndpointConfiguration = (ServerEndpointConfig)var4.next();
            if (serverEndpointConfiguration != null) {
               this.register(serverEndpointConfiguration);
            }
         }

         if (this.serverApplicationConfig != null) {
            var4 = this.serverApplicationConfig.getAnnotatedEndpointClasses((Set)null).iterator();

            while(var4.hasNext()) {
               endpointClass = (Class)var4.next();
               this.register(endpointClass);
            }

            var4 = this.serverApplicationConfig.getEndpointConfigs((Set)null).iterator();

            while(var4.hasNext()) {
               serverEndpointConfiguration = (ServerEndpointConfig)var4.next();
               if (serverEndpointConfiguration != null) {
                  this.register(serverEndpointConfiguration);
               }
            }
         }
      } catch (DeploymentException var6) {
         this.collector.addException(var6);
      }

      if (!this.collector.isEmpty()) {
         this.stop();
         throw this.collector.composeComprehensiveException();
      } else {
         if (this.port == -1) {
            this.port = port;
         }

      }
   }

   public void stop() {
      this.shutdown();
   }

   public abstract void register(Class var1) throws DeploymentException;

   public abstract void register(ServerEndpointConfig var1) throws DeploymentException;

   public void addEndpoint(Class endpointClass) throws DeploymentException {
      if (this.canDeploy) {
         this.dynamicallyAddedClasses.add(endpointClass);
      } else {
         throw new IllegalStateException("Not in 'deploy' scope.");
      }
   }

   public void addEndpoint(ServerEndpointConfig serverEndpointConfig) throws DeploymentException {
      if (this.canDeploy) {
         this.dynamicallyAddedEndpointConfigs.add(serverEndpointConfig);
      } else {
         throw new IllegalStateException("Not in 'deploy' scope.");
      }
   }

   public int getPort() {
      return this.port;
   }

   protected synchronized ClientManager getClientManager() {
      if (this.clientManager == null) {
         this.clientManager = ClientManager.createClient(this);
      }

      return this.clientManager;
   }

   public Session connectToServer(Class annotatedEndpointClass, URI path) throws DeploymentException, IOException {
      return this.getClientManager().connectToServer(annotatedEndpointClass, path);
   }

   public Session connectToServer(Class endpointClass, ClientEndpointConfig cec, URI path) throws DeploymentException, IOException {
      return this.getClientManager().connectToServer(endpointClass, cec, path);
   }

   public Session connectToServer(Object annotatedEndpointInstance, URI path) throws DeploymentException, IOException {
      return this.getClientManager().connectToServer(annotatedEndpointInstance, path);
   }

   public Session connectToServer(Endpoint endpointInstance, ClientEndpointConfig cec, URI path) throws DeploymentException, IOException {
      return this.getClientManager().connectToServer(endpointInstance, cec, path);
   }

   public Future asyncConnectToServer(Class annotatedEndpointClass, URI path) throws DeploymentException {
      return this.getClientManager().asyncConnectToServer(annotatedEndpointClass, path);
   }

   public Future asyncConnectToServer(Class endpointClass, ClientEndpointConfig cec, URI path) throws DeploymentException {
      return this.getClientManager().asyncConnectToServer(endpointClass, cec, path);
   }

   public Future asyncConnectToServer(Endpoint endpointInstance, ClientEndpointConfig cec, URI path) throws DeploymentException {
      return this.getClientManager().asyncConnectToServer(endpointInstance, cec, path);
   }

   public Future asyncConnectToServer(Object obj, URI path) throws DeploymentException {
      return this.getClientManager().asyncConnectToServer(obj, path);
   }

   public int getDefaultMaxBinaryMessageBufferSize() {
      return this.maxBinaryMessageBufferSize;
   }

   public void setDefaultMaxBinaryMessageBufferSize(int max) {
      this.maxBinaryMessageBufferSize = max;
   }

   public int getDefaultMaxTextMessageBufferSize() {
      return this.maxTextMessageBufferSize;
   }

   public void setDefaultMaxTextMessageBufferSize(int max) {
      this.maxTextMessageBufferSize = max;
   }

   public Set getInstalledExtensions() {
      return Collections.emptySet();
   }

   public long getDefaultAsyncSendTimeout() {
      return this.defaultAsyncSendTimeout;
   }

   public void setAsyncSendTimeout(long timeoutmillis) {
      this.defaultAsyncSendTimeout = timeoutmillis;
   }

   public long getDefaultMaxSessionIdleTimeout() {
      return this.defaultMaxSessionIdleTimeout;
   }

   public void setDefaultMaxSessionIdleTimeout(long defaultMaxSessionIdleTimeout) {
      this.defaultMaxSessionIdleTimeout = defaultMaxSessionIdleTimeout;
   }

   public void doneDeployment() {
      this.canDeploy = false;
   }
}
