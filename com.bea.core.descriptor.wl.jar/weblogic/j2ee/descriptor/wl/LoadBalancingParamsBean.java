package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface LoadBalancingParamsBean extends SettableBean {
   boolean isLoadBalancingEnabled();

   void setLoadBalancingEnabled(boolean var1) throws IllegalArgumentException;

   boolean isServerAffinityEnabled();

   void setServerAffinityEnabled(boolean var1) throws IllegalArgumentException;

   String getProducerLoadBalancingPolicy();

   void setProducerLoadBalancingPolicy(String var1) throws IllegalArgumentException;
}
