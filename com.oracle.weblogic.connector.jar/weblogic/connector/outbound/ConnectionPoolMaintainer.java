package weblogic.connector.outbound;

import weblogic.common.ResourceException;
import weblogic.common.resourcepool.ResourcePoolMaintainer;
import weblogic.connector.common.Debug;

public class ConnectionPoolMaintainer implements ResourcePoolMaintainer {
   ConnectionPool pool;

   public ConnectionPoolMaintainer(ConnectionPool pool) {
      this.pool = pool;
   }

   public void performMaintenance() {
      if (!this.pool.isRmHealthy()) {
         this.pool.getRwLock4ReregisterXAResource().writeLock().lock();

         try {
            if (Debug.isPoolingEnabled()) {
               Debug.pooling("Re-register XAResource for the pool : [" + this.pool.getName() + "] due to RM is not availble.");
            }

            this.pool.undoSetupForXARecovery();
            if (!this.pool.isWLSMessagingBridgeConnection()) {
               try {
                  this.pool.setupForXARecovery();
               } catch (ResourceException var5) {
               }
            }
         } finally {
            this.pool.getRwLock4ReregisterXAResource().writeLock().unlock();
         }
      }

   }
}
