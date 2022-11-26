package weblogic.jms.frontend;

import weblogic.jms.common.DistributedDestinationImpl;

public interface LoadBalancingContext {
   FESession getSession();

   DistributedDestinationImpl getDistributedDestinationInstance();
}
