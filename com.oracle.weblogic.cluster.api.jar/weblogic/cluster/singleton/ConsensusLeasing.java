package weblogic.cluster.singleton;

import java.lang.annotation.Annotation;
import org.jvnet.hk2.annotations.Contract;
import weblogic.server.GlobalServiceLocator;

@Contract
public interface ConsensusLeasing {
   LeasingBasis createConsensusBasis(int var1, int var2);

   void addClusterLeaderListener(ClusterLeaderListener var1);

   void addConsensusServiceGroupViewListener(ConsensusServiceGroupViewListener var1);

   void removeConsensusServiceGroupViewListener(ConsensusServiceGroupViewListener var1);

   String getServerState(String var1);

   String getLeasingBasisLocation();

   public static class Locator {
      public static ConsensusLeasing locate() {
         return (ConsensusLeasing)GlobalServiceLocator.getServiceLocator().getService(ConsensusLeasing.class, new Annotation[0]);
      }
   }
}
