package weblogic.cluster.singleton;

public interface ConsensusServiceGroupViewListener {
   void memberAdded(String var1);

   void memberRemoved(String var1);
}
