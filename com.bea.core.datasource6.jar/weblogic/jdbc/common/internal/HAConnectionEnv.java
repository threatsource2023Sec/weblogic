package weblogic.jdbc.common.internal;

import java.util.Properties;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.ResourcePoolGroup;
import weblogic.jdbc.common.rac.RACConnectionEnv;
import weblogic.jdbc.common.rac.RACConnectionEnvFactory;
import weblogic.jdbc.common.rac.RACPooledConnectionState;

public class HAConnectionEnv extends RACConnectionEnv {
   RACConnectionEnvFactory factory;
   Thread pinnedTo;

   public HAConnectionEnv(Properties poolParams, boolean isXA, RACConnectionEnvFactory factory) {
      super(poolParams, isXA);
      this.factory = factory;
   }

   public HAConnectionEnv(Properties poolParams, RACConnectionEnvFactory factory) {
      super(poolParams);
      this.factory = factory;
   }

   public void initializeGroups() throws ResourceException {
      RACPooledConnectionState racState = this.factory.createRACPooledConnectionState(this);
      this.setRACPooledConnectionState(racState);
      this.clearGroups();
      String instance = this.getInstance();
      if (this.switchingContext != null && this.switchingContext.getPDBName() != null) {
         String pdbname = this.switchingContext.getPDBName();
         String servicename = this.switchingContext.getPDBServiceName();
         ResourcePoolGroup instanceGroup = this.pool.getOrCreateGroup("instance", instance);
         this.setPrimaryGroup(instanceGroup);
         this.addGroup(instanceGroup);
         ((HAJDBCConnectionPool)this.pool).createInstanceRuntime((HAJDBCConnectionPool)this.pool, instanceGroup, instance);
         ResourcePoolGroup pdbGroup = this.pool.getOrCreateGroup("service_pdbname", JDBCUtil.getServicePDBGroupName(servicename, pdbname));
         this.addGroup(pdbGroup);
         ResourcePoolGroup pdbInstanceGroup = this.pool.getOrCreateGroup("service_pdbname_instance", JDBCUtil.getServicePDBInstanceGroupName(servicename, pdbname, instance));
         this.addGroup(pdbInstanceGroup);
         if (this.switchingContext.getPool() instanceof HASharingConnectionPool) {
            ((HAJDBCConnectionPool)this.pool).createInstanceRuntime((HAJDBCConnectionPool)this.switchingContext.getPool(), pdbInstanceGroup, instance);
         }

         ((HAJDBCConnectionPool)this.switchingContext.getPool()).getRACModule().connectionOpened(this);
      } else {
         ResourcePoolGroup instanceGroup = this.pool.getOrCreateGroup("instance", instance);
         this.setPrimaryGroup(instanceGroup);
         this.addGroup(instanceGroup);
         ((HAJDBCConnectionPool)this.pool).createInstanceRuntime((HAJDBCConnectionPool)this.pool, instanceGroup, instance);
      }

   }

   public int test() {
      if (this.destroyed) {
         this.debug("test() destroyed -1");
         return -1;
      } else if (!this.racState.isStatusValid()) {
         this.debug("test() status not valid -1");
         return -1;
      } else {
         int ret = super.test();
         this.debug("test() connection test " + ret);
         return ret;
      }
   }

   public String toString() {
      return "groups=" + this.getGroups() + "," + super.toString();
   }

   private final void debug(String msg) {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         JdbcDebug.JDBCRAC.debug("HAConnectionEnv[" + this + "]: " + msg);
      }

   }

   void setPinnedTo(Thread thread) {
      this.pinnedTo = thread;
   }

   public Thread getPinnedTo() {
      return this.pinnedTo;
   }
}
