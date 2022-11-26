package weblogic.deployment.jms;

import weblogic.common.resourcepool.ResourcePool;
import weblogic.management.ManagementException;

public class CoreJMSSessionPoolRuntimeImpl implements JMSSessionPoolRuntime {
   private ResourcePool pool;
   private JMSSessionPool sessionPool;

   public CoreJMSSessionPoolRuntimeImpl(String poolName, ResourcePool pool, JMSSessionPool sessionPool) throws ManagementException {
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("CoreJMSSessionPoolRuntimeImpl(poolName=" + poolName + ", ResourcePool@" + (pool == null ? "null" : pool.hashCode()) + ", sessionPool@" + (sessionPool == null ? "null" : sessionPool.hashCode()) + ")");
      }

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

   public void unregister() throws ManagementException {
   }
}
