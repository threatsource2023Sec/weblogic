package weblogic.jms.common;

public interface DDTxLoadBalancingOptimizer {
   boolean visited(DistributedDestinationImpl var1);

   void addVisitedDispatcher(DistributedDestinationImpl var1);

   void addCachedDest(DistributedDestinationImpl var1);

   DistributedDestinationImpl getCachedDest(String var1, boolean var2);

   void cleanFailure(DestinationImpl var1);

   void cleanAll();
}
