package weblogic.jms.common;

public interface LoadBalancer {
   DistributedDestinationImpl getNext(DDTxLoadBalancingOptimizer var1);

   DistributedDestinationImpl getNext(int var1);

   void refresh(DistributedDestinationImpl[] var1);

   int getSize();
}
