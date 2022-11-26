package weblogic.websocket.tyrus;

import java.lang.annotation.Annotation;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.container.jdk.client.JdkClientContainer;
import org.glassfish.tyrus.core.TyrusWebSocketEngine;
import org.glassfish.tyrus.core.cluster.ClusterContext;
import org.glassfish.tyrus.core.monitoring.ApplicationEventListener;
import org.glassfish.tyrus.server.TyrusServerConfiguration;
import org.glassfish.tyrus.server.TyrusServerContainer;
import org.glassfish.tyrus.spi.WebSocketEngine;
import weblogic.server.GlobalServiceLocator;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.websocket.tyrus.monitoring.ApplicationMonitor;

@HandlesTypes({ServerEndpoint.class, ServerApplicationConfig.class, Endpoint.class})
public class TyrusServletContainerInitializer implements ServletContainerInitializer, WebSocketDelegate {
   private static final Set FILTERED_CLASSES = new HashSet() {
      private static final long serialVersionUID = 1L;

      {
         this.add(TyrusServerConfiguration.class);
      }
   };
   private static final String FRAME_BUFFER_SIZE = "weblogic.websocket.tyrus.incoming-buffer-size";
   private static final String CLUSTER = "weblogic.websocket.tyrus.cluster";
   private static final String CLUSTER_HA_TIMEOUT = "weblogic.websocket.tyrus.cluster.ha.timeout";
   private volatile ClusterContext clusterContext;

   private synchronized ClusterContext getClusterContext(Executor executor, Integer clusterHaTimeout) {
      if (this.clusterContext == null) {
         WebSocketCoherenceService wscs = (WebSocketCoherenceService)GlobalServiceLocator.getServiceLocator().getService(WebSocketCoherenceService.class, new Annotation[0]);
         if (wscs != null) {
            this.clusterContext = wscs.createClusterContext(executor, clusterHaTimeout);
         }
      }

      return this.clusterContext;
   }

   public void onStartup(Set classes, ServletContext ctx) throws ServletException {
      if (classes != null && !classes.isEmpty()) {
         classes.removeAll(FILTERED_CLASSES);
         String frameBufferSize = ctx.getInitParameter("weblogic.websocket.tyrus.incoming-buffer-size");
         String clusterFlag = ctx.getInitParameter("weblogic.websocket.tyrus.cluster");
         String clusterHaTimeoutString = ctx.getInitParameter("weblogic.websocket.tyrus.cluster.ha.timeout");
         boolean cluster = clusterFlag != null && clusterFlag.equalsIgnoreCase("true");

         Integer clusterHaTimeout;
         try {
            clusterHaTimeout = clusterHaTimeoutString != null ? Integer.valueOf(clusterHaTimeoutString) : null;
         } catch (NumberFormatException var14) {
            clusterHaTimeout = null;
         }

         Integer incomingBufferSize;
         try {
            incomingBufferSize = frameBufferSize != null ? Integer.parseInt(frameBufferSize) : null;
         } catch (NumberFormatException var13) {
            incomingBufferSize = null;
         }

         WebAppServletContext wlsSc = (WebAppServletContext)ctx;
         ApplicationEventListener applicationEventListener = new ApplicationMonitor(wlsSc.getRuntimeMBean());
         WebSocketCoherenceService wscs = (WebSocketCoherenceService)GlobalServiceLocator.getServiceLocator().getService(WebSocketCoherenceService.class, new Annotation[0]);
         if (wscs != null) {
            wscs.onStartup(classes, ctx, cluster, clusterHaTimeout, incomingBufferSize, wlsSc, applicationEventListener, this);
         } else {
            TyrusServerContainer serverContainer = this.createServerContainer(classes, ctx, cluster, clusterHaTimeout, incomingBufferSize, applicationEventListener, (Executor)null);
            ctx.setAttribute(ServerContainer.class.getName(), serverContainer);
            this.createServletFilter(ctx, serverContainer, (CoherenceServletFilterService)null);
            applicationEventListener.onApplicationInitialized(ctx.getContextPath());
         }

      }
   }

   public void createServletFilter(ServletContext ctx, TyrusServerContainer serverContainer, CoherenceServletFilterService coherenceServletFilterService) {
      TyrusServletFilter filter = new TyrusServletFilter(serverContainer.getWebSocketEngine(), coherenceServletFilterService);
      ctx.addListener(filter);
      FilterRegistration.Dynamic reg = ctx.addFilter("WebSocket filter", filter);
      reg.setAsyncSupported(true);
      reg.addMappingForUrlPatterns((EnumSet)null, true, new String[]{"/*"});
   }

   public TyrusServerContainer createServerContainer(Set classes, final ServletContext ctx, final boolean cluster, final Integer clusterHaTimeout, final Integer incomingBufferSize, final ApplicationEventListener applicationEventListener, final Executor executor) {
      TyrusServerContainer serverContainer = new TyrusServerContainer(classes) {
         private final TyrusWebSocketEngine engine = TyrusWebSocketEngine.builder(this).incomingBufferSize(incomingBufferSize).applicationEventListener(applicationEventListener).clusterContext(cluster ? TyrusServletContainerInitializer.this.getClusterContext(executor, clusterHaTimeout) : null).parallelBroadcastEnabled(false).build();
         private ClientManager clientManager = null;

         public void register(Class endpointClass) throws DeploymentException {
            this.engine.register(endpointClass, ctx.getContextPath());
         }

         public void register(ServerEndpointConfig serverEndpointConfig) throws DeploymentException {
            this.engine.register(serverEndpointConfig, ctx.getContextPath());
         }

         public WebSocketEngine getWebSocketEngine() {
            return this.engine;
         }

         protected synchronized ClientManager getClientManager() {
            if (this.clientManager == null) {
               this.clientManager = ClientManager.createClient(JdkClientContainer.class.getName(), this);
               if (incomingBufferSize != null) {
                  this.clientManager.getProperties().put("weblogic.websocket.tyrus.incoming-buffer-size", incomingBufferSize);
               }
            }

            return this.clientManager;
         }

         public void stop() {
            super.stop();
            this.engine.getApplicationEventListener().onApplicationDestroyed();
         }
      };
      return serverContainer;
   }
}
