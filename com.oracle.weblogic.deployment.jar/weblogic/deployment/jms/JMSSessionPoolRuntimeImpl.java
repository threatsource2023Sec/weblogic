package weblogic.deployment.jms;

import weblogic.common.resourcepool.ResourcePool;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JMSPooledConnectionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class JMSSessionPoolRuntimeImpl extends RuntimeMBeanDelegate implements JMSPooledConnectionRuntimeMBean, JMSSessionPoolRuntime {
   private ResourcePool pool;
   private JMSSessionPool sessionPool;

   public JMSSessionPoolRuntimeImpl(String poolName, ResourcePool pool, JMSSessionPool sessionPool) throws ManagementException {
      super(poolName);
      this.pool = pool;
      this.sessionPool = sessionPool;
   }

   public int getNumLeaked() {
      return this.pool.getNumLeaked();
   }

   public int getNumFailuresToRefresh() {
      return this.pool.getNumFailuresToRefresh();
   }

   public int getCreationDelayTime() {
      return this.pool.getCreationDelayTime();
   }

   public int getNumWaiters() {
      return this.pool.getNumWaiters();
   }

   public int getHighestNumWaiters() {
      return this.pool.getHighestNumWaiters();
   }

   public int getHighestWaitSeconds() {
      return this.pool.getHighestWaitSeconds();
   }

   public int getNumReserved() {
      return this.pool.getNumReserved();
   }

   public int getHighestNumReserved() {
      return this.pool.getHighestNumReserved();
   }

   public int getNumAvailable() {
      return this.pool.getNumAvailable();
   }

   public int getHighestNumAvailable() {
      return this.pool.getHighestNumAvailable();
   }

   public int getNumUnavailable() {
      return this.pool.getNumUnavailable();
   }

   public int getHighestNumUnavailable() {
      return this.pool.getHighestNumUnavailable();
   }

   public int getTotalNumAllocated() {
      return this.pool.getTotalNumAllocated();
   }

   public int getTotalNumDestroyed() {
      return this.pool.getTotalNumDestroyed();
   }

   public int getMaxCapacity() {
      return this.pool.getMaxCapacity();
   }

   public int getCurrCapacity() {
      return this.pool.getCurrCapacity();
   }

   public int getAverageReserved() {
      return this.pool.getAverageReserved();
   }

   public int getNumConnectionObjects() {
      return this.sessionPool.getNumConnectionObjects();
   }
}
