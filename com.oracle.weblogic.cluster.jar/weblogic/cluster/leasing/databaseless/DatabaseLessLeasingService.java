package weblogic.cluster.leasing.databaseless;

import java.util.Iterator;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.messaging.internal.SRMResult;
import weblogic.cluster.messaging.internal.ServerInformation;
import weblogic.cluster.singleton.ClusterLeaderListener;
import weblogic.cluster.singleton.ConsensusLeasing;
import weblogic.cluster.singleton.ConsensusServiceGroupViewListener;
import weblogic.cluster.singleton.LeasingBasis;
import weblogic.server.AbstractServerService;
import weblogic.utils.collections.ArraySet;
import weblogic.work.WorkManagerFactory;

@Service
@Named
@RunLevel(10)
public final class DatabaseLessLeasingService extends AbstractServerService implements ConsensusLeasing {
   private static boolean isClusterLeader;
   private static final ArraySet clusterLeaderListeners = new ArraySet();
   private static final ArraySet consensusServiceGroupViewListeners = new ArraySet();

   public LeasingBasis createConsensusBasis(int heartbeatTimeout, int suspectTimeout) {
      return new LeaseClient();
   }

   public void addClusterLeaderListener(ClusterLeaderListener listener) {
      synchronized(clusterLeaderListeners) {
         if (isClusterLeader) {
            listener.localServerIsClusterLeader();
         } else {
            clusterLeaderListeners.add(listener);
         }

      }
   }

   public String getServerState(String server) {
      String srvrState = null;
      SRMResult result = EnvironmentFactory.getServerReachabilityMajorityService().getLastSRMResult();
      if (result != null) {
         srvrState = result.getServerState(server);
      }

      return srvrState;
   }

   public void addConsensusServiceGroupViewListener(ConsensusServiceGroupViewListener listener) {
      consensusServiceGroupViewListeners.add(listener);
   }

   public void removeConsensusServiceGroupViewListener(ConsensusServiceGroupViewListener listener) {
      consensusServiceGroupViewListeners.remove(listener);
   }

   static void fireConsensusServiceGroupViewListenerEvent(final ServerInformation server, boolean memberAdded) {
      Iterator i = consensusServiceGroupViewListeners.iterator();

      while(i.hasNext()) {
         final ConsensusServiceGroupViewListener listener = (ConsensusServiceGroupViewListener)i.next();
         if (memberAdded) {
            WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
               public void run() {
                  listener.memberAdded(server.getServerName());
               }
            });
         } else {
            WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
               public void run() {
                  listener.memberRemoved(server.getServerName());
               }
            });
         }
      }

   }

   public String getLeasingBasisLocation() {
      return ClusterLeaderService.getInstance().getLeaderName();
   }

   static void localServerIsClusterLeader() {
      synchronized(clusterLeaderListeners) {
         Iterator i = clusterLeaderListeners.iterator();

         while(i.hasNext()) {
            final ClusterLeaderListener listener = (ClusterLeaderListener)i.next();
            WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
               public void run() {
                  listener.localServerIsClusterLeader();
               }
            });
         }

         isClusterLeader = true;
      }
   }

   static void localServerLostClusterLeadership() {
      synchronized(clusterLeaderListeners) {
         Iterator i = clusterLeaderListeners.iterator();

         while(i.hasNext()) {
            final ClusterLeaderListener listener = (ClusterLeaderListener)i.next();
            WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
               public void run() {
                  listener.localServerLostClusterLeadership();
               }
            });
         }

         isClusterLeader = false;
      }
   }

   boolean isClusterLeader() {
      return isClusterLeader;
   }
}
