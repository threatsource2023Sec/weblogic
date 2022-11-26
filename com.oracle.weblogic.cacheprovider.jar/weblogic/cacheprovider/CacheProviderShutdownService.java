package weblogic.cacheprovider;

import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.cacheprovider.coherence.CoherenceClusterManager;
import weblogic.coherence.api.internal.CoherenceException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class CacheProviderShutdownService extends AbstractServerService {
   public void halt() throws ServiceFailureException {
      CoherenceClusterSystemResourceMBean mbean = null;
      ServerMBean server = CoherenceClusterManager.getServerMBean();
      ClusterMBean cluster = server.getCluster();
      if (cluster != null) {
         mbean = cluster.getCoherenceClusterSystemResource();
      }

      if (mbean == null) {
         mbean = server.getCoherenceClusterSystemResource();
      }

      if (mbean != null) {
         CoherenceClusterManager mgr = CoherenceClusterManager.getInstance();
         ClassLoader loader = Thread.currentThread().getContextClassLoader();
         if (mgr.isCoherenceAvailable(loader)) {
            try {
               mgr.shutdownClusterService(loader);
            } catch (CoherenceException var7) {
               throw new ServiceFailureException(var7.getMessage(), var7);
            }
         }
      }

   }

   public void stop() throws ServiceFailureException {
      this.halt();
   }
}
