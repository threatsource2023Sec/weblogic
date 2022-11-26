package weblogic.cacheprovider.coherence;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.Cluster;
import javax.inject.Named;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(15)
@Rank(100)
public class EnsureClusterService extends AbstractServerService {
   public void start() throws ServiceFailureException {
      this.initialize();
   }

   private void initialize() throws ServiceFailureException {
      ServerMBean serverMBean = CoherenceClusterManager.getServerMBean();
      if (serverMBean != null) {
         CoherenceClusterSystemResourceMBean coherenceClusterSystemResourceMBean = null;
         ClusterMBean clusterMBean = serverMBean.getCluster();
         if (clusterMBean != null) {
            coherenceClusterSystemResourceMBean = clusterMBean.getCoherenceClusterSystemResource();
         }

         if (coherenceClusterSystemResourceMBean == null) {
            coherenceClusterSystemResourceMBean = serverMBean.getCoherenceClusterSystemResource();
         }

         if (coherenceClusterSystemResourceMBean != null) {
            Cluster cluster;
            try {
               cluster = CacheFactory.ensureCluster();
            } catch (Exception var6) {
               throw new ServiceFailureException(var6);
            }

            if (cluster == null) {
               throw new ServiceFailureException("Unable to start coherence.");
            }
         }
      }

   }
}
