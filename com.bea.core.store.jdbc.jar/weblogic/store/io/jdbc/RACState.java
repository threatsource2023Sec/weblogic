package weblogic.store.io.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import weblogic.store.common.StoreDebug;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;

class RACState {
   private static final int FAILBACk_CHECK_INTERVAL_MILLIS_DEF = 120000;
   private static final int FAILBACk_CHECK_INTERVAL_MILLIS = Integer.getInteger("weblogic.store.jdbc.FailbackCheckMillis", 120000);
   private final String storeName;
   private final DataSource dataSource;
   private final String originalInstance;
   private String affinityInstance;
   private int generation = 0;
   private final ReservedConnection[] rcConns;
   private TimerManager timerManager;
   private volatile Timer failbackTimer;

   RACState(String storeName, String instance, DataSource dataSource, ReservedConnection[] rcConns) {
      this.storeName = storeName;
      this.originalInstance = instance;
      this.affinityInstance = instance;
      this.generation = 0;
      this.dataSource = dataSource;
      this.rcConns = rcConns;
      this.timerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
   }

   private void setAffinityInstance(String instance) {
      assert this.affinityInstance != null;

      this.affinityInstance = instance;
      if (this.originalInstance.equals(instance)) {
         if (this.failbackTimer != null) {
            this.failbackTimer.cancel();
            this.failbackTimer = null;
         }
      } else {
         if (this.failbackTimer == null) {
            this.failbackTimer = this.timerManager.schedule(new NakedTimerListener() {
               public void timerExpired(Timer timer) {
                  if (!RACState.this.failback()) {
                     RACState.this.failbackTimer = RACState.this.timerManager.schedule(this, (long)RACState.FAILBACk_CHECK_INTERVAL_MILLIS);
                  } else {
                     RACState.this.failbackTimer = null;
                  }

               }
            }, (long)FAILBACk_CHECK_INTERVAL_MILLIS);
         }

         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("JDBC " + this.storeName + " scheduled RAC failback timer in " + FAILBACk_CHECK_INTERVAL_MILLIS + " milli-seconds");
         }
      }

   }

   synchronized void failover(ReservedConnection rc) throws JDBCStoreException, SQLException {
      if (this.generation > rc.getGeneration()) {
         rc.createConnection(this.affinityInstance, true);
         rc.setGeneration(this.generation);
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            rc.debug("reset RAC connection, sync to generation=" + this.generation);
         }
      } else {
         assert this.generation == rc.getGeneration();

         rc.createConnection((String)null, true);
         this.setAffinityInstance(rc.getInstance());
         ++this.generation;
         rc.setGeneration(this.generation);
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            rc.debug("reset RAC connection, increase generation to " + this.generation);
         }
      }

   }

   boolean failback() {
      synchronized(this) {
         if (this.originalInstance.equals(this.affinityInstance)) {
            return true;
         }
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("JDBC " + this.storeName + " attempting RAC failback");
      }

      Connection[] jdbcConns = new Connection[this.rcConns.length];
      int idx = 0;

      int i;
      try {
         for(; idx < this.rcConns.length; ++idx) {
            Properties additionalProps = null;
            if (this.rcConns[idx].isAllowPiggybackCommit()) {
               additionalProps = new Properties();
               additionalProps.put("oracle.jdbc.autoCommitSpecCompliant", "false");
            }

            jdbcConns[idx] = (Connection)ReservedConnection.methodCreateConnectionToInstance.invoke(this.dataSource, this.originalInstance, additionalProps);
            if (this.rcConns[idx].isAutoCommit() != jdbcConns[idx].getAutoCommit()) {
               jdbcConns[idx].setAutoCommit(this.rcConns[idx].isAutoCommit());
            }
         }
      } catch (Throwable var14) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("JDBC " + this.storeName + " in RAC failback, failed to create enough JDBC connections", var14);
         }

         for(i = 0; i < idx; ++i) {
            try {
               jdbcConns[i].close();
            } catch (Throwable var10) {
            }
         }

         return false;
      }

      Object[] locks = new Object[this.rcConns.length];
      idx = 0;

      try {
         while(idx < this.rcConns.length) {
            locks[idx] = this.rcConns[idx].innerLock(true);
            ++idx;
         }
      } catch (Throwable var13) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("JDBC " + this.storeName + " in RAC failback, failed to lock ReservedConnection", var13);
         }

         int i;
         for(i = 0; i < idx; ++i) {
            try {
               this.rcConns[i].unlock(locks[i]);
            } catch (Throwable var9) {
               var9.printStackTrace();
            }
         }

         for(i = 0; i < jdbcConns.length; ++i) {
            try {
               jdbcConns[i].close();
            } catch (Throwable var8) {
            }
         }

         return false;
      }

      synchronized(this) {
         ++this.generation;
         this.affinityInstance = this.originalInstance;
      }

      for(i = 0; i < this.rcConns.length; ++i) {
         this.rcConns[i].failbackToConnection(this.originalInstance, jdbcConns[i], this.generation);
      }

      for(i = 0; i < idx; ++i) {
         try {
            this.rcConns[i].unlock(locks[i]);
         } catch (Throwable var11) {
            var11.printStackTrace();
         }
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("JDBC " + this.storeName + " RAC failback succeeded");
      }

      return true;
   }

   synchronized boolean needToResync(int workerGeneration) {
      return workerGeneration < this.generation;
   }
}
