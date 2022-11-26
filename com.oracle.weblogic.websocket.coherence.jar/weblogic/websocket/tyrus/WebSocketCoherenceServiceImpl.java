package weblogic.websocket.tyrus;

import com.oracle.tyrus.cluster.coherence.CoherenceClusterContext;
import com.tangosol.application.ContainerAdapter;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.SimpleServiceMonitor;
import com.tangosol.run.xml.SimpleElement;
import com.tangosol.run.xml.XmlElement;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;
import java.util.concurrent.Executor;
import javax.servlet.ServletContext;
import javax.websocket.server.ServerContainer;
import org.glassfish.tyrus.core.cluster.ClusterContext;
import org.glassfish.tyrus.core.monitoring.ApplicationEventListener;
import org.glassfish.tyrus.server.TyrusServerContainer;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.servlet.internal.WebAppServletContext;

@Service
public class WebSocketCoherenceServiceImpl implements WebSocketCoherenceService {
   public ClusterContext createClusterContext(Executor executor, Integer clusterHaTimeout) {
      return new CoherenceClusterContext(executor, clusterHaTimeout);
   }

   public void onStartup(Set classes, ServletContext ctx, boolean cluster, Integer clusterHaTimeout, Integer incomingBufferSize, WebAppServletContext wlsSc, ApplicationEventListener applicationEventListener, WebSocketDelegate webSocketDelegate) {
      Executor executor = null;
      ContainerAdapter coherenceContainerAdapter = null;
      ClassLoader loader = Thread.currentThread().getContextClassLoader();

      try {
         if (cluster) {
            coherenceContainerAdapter = this.createContainerAdapter(ctx, loader);
            executor = this.createExecutor(wlsSc, coherenceContainerAdapter);
         }

         TyrusServerContainer serverContainer = webSocketDelegate.createServerContainer(classes, ctx, cluster, clusterHaTimeout, incomingBufferSize, applicationEventListener, executor);
         ctx.setAttribute(ServerContainer.class.getName(), serverContainer);
         webSocketDelegate.createServletFilter(ctx, serverContainer, new CoherenceServletFilterServiceImpl(coherenceContainerAdapter));
         applicationEventListener.onApplicationInitialized(ctx.getContextPath());
      } finally {
         if (cluster) {
            Thread.currentThread().setContextClassLoader(loader);
         }

      }

   }

   private ContainerAdapter createContainerAdapter(ServletContext ctx, ClassLoader loader) {
      ContainerAdapter coherenceContainerAdapter = null;
      ClassLoader containerLoader = new URLClassLoader(new URL[0], loader);
      String partitionId = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionId();
      String sAppName = "websocket-" + ctx.getContextPath() + "-" + partitionId;
      XmlElement xmlAppDescriptor = new SimpleElement("coherence-application");
      Thread.currentThread().setContextClassLoader(containerLoader);
      coherenceContainerAdapter = new ContainerAdapter(containerLoader, "META-INF/coherence-application.xml", sAppName, CacheFactory.getCacheFactoryBuilder(), xmlAppDescriptor, new SimpleServiceMonitor());
      coherenceContainerAdapter.activate();
      Thread.currentThread().setContextClassLoader(containerLoader);
      return coherenceContainerAdapter;
   }

   private Executor createExecutor(final WebAppServletContext wlsSc, final ContainerAdapter finalAdapter) {
      Executor executor = new Executor() {
         public void execute(final Runnable command) {
            wlsSc.getConfigManager().getWorkManager().schedule(new Runnable() {
               public void run() {
                  Thread currentThread = Thread.currentThread();
                  ClassLoader contextClassLoader = currentThread.getContextClassLoader();

                  try {
                     currentThread.setContextClassLoader(finalAdapter.getContextClassLoader());
                     command.run();
                  } finally {
                     currentThread.setContextClassLoader(contextClassLoader);
                  }

               }
            });
         }
      };
      return executor;
   }

   private class CoherenceServletFilterServiceImpl implements CoherenceServletFilterService {
      private final ContainerAdapter coherenceContainer;

      public CoherenceServletFilterServiceImpl(ContainerAdapter coherenceContainer) {
         this.coherenceContainer = coherenceContainer;
      }

      public ClassLoader setContextClassLoader(Thread thread) {
         ClassLoader contextClassLoader = thread.getContextClassLoader();
         if (this.coherenceContainer != null) {
            thread.setContextClassLoader(this.coherenceContainer.getContextClassLoader());
         }

         return contextClassLoader;
      }

      public void deactivate() {
         if (this.coherenceContainer != null) {
            this.coherenceContainer.deactivate();
         }

      }
   }
}
