package weblogic.cache.webapp;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.MulticastSession;
import weblogic.cluster.RecoverListener;
import weblogic.cluster.ClusterServicesActivator.Locator;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.spi.WebServerRegistry;

public class ClusterListener implements CacheListener, ServletContextListener {
   private MulticastSession multicastSession;
   private ServletContext sc;
   private String httpServerName;
   private String contextName;

   public void contextInitialized(ServletContextEvent sce) {
      this.sc = sce.getServletContext();
      WebAppServletContext wasc = (WebAppServletContext)this.sc;
      if (WebServerRegistry.getInstance().getHttpServerManager().isDefaultHttpServer(wasc.getServer())) {
         this.httpServerName = null;
      } else {
         this.httpServerName = wasc.getServer().getName();
      }

      this.contextName = wasc.getContextPath();

      try {
         ClusterServices cs = Locator.locateClusterServices();
         if (cs == null) {
            this.sc.log("This server is not in a cluster.  Cluster caching disabled");
            return;
         }

         this.sc.log("Cluster caching enabled");
         this.multicastSession = cs.createMulticastSession((RecoverListener)null, -1);
         ServletCacheUtils.addCacheListener(this.sc, this);
      } catch (Exception var4) {
         this.sc.log("Could not register", var4);
      }

   }

   public void contextDestroyed(ServletContextEvent sce) {
      ServletCacheUtils.removeCacheListener(this.sc, this);
   }

   public void cacheUpdateOccurred(CacheListener.CacheEvent ce) {
      if (ce.getScope().equals("cluster")) {
         try {
            CacheMessage cm = new CacheMessage(this.httpServerName, this.contextName, ce);
            this.multicastSession.send(cm);
         } catch (IOException var3) {
            this.sc.log("Could not send cache message", var3);
         }
      }

   }

   public void cacheFlushOccurred(CacheListener.CacheEvent ce) {
      this.cacheUpdateOccurred(ce);
   }

   public void cacheAccessOccurred(CacheListener.CacheEvent ce) {
   }
}
