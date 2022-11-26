package weblogic.jdbc.common.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import weblogic.common.resourcepool.ResourcePoolGroup;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JDBCOracleDataSourceInstanceRuntimeMBean;
import weblogic.management.runtime.JDBCOracleDataSourceRuntimeMBean;
import weblogic.management.runtime.ONSClientRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class OracleDataSourceRuntimeImpl extends DataSourceRuntimeMBeanImpl implements JDBCOracleDataSourceRuntimeMBean, HADataSourceRuntime {
   List racInstances;
   ONSClientRuntimeMBean onsClient;

   public OracleDataSourceRuntimeImpl(JDBCConnectionPool pool, String beanName, RuntimeMBean parent, RuntimeMBean restParent, DescriptorBean descriptor) throws ManagementException {
      this(pool, (ResourcePoolGroup)null, (String)null, beanName, parent, restParent, descriptor);
   }

   public OracleDataSourceRuntimeImpl(JDBCConnectionPool pool, ResourcePoolGroup group, String instanceGroupCategory, String beanName, RuntimeMBean parent, RuntimeMBean restParent, DescriptorBean descriptor) throws ManagementException {
      super(pool, (ResourcePoolGroup)null, beanName, parent, restParent, descriptor);
      this.racInstances = Collections.synchronizedList(new ArrayList());
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         JdbcDebug.JDBCRAC.debug(pool.getName() + ": creating runtime MBean for group=" + group + ", instanceGroupCategory=" + instanceGroupCategory);
      }

      ((HAJDBCConnectionPool)pool).addHADataSourceRuntime((HAJDBCConnectionPool)pool, this);
      if (instanceGroupCategory == null) {
         instanceGroupCategory = "instance";
      }

      List instanceGroups = pool.getGroups(instanceGroupCategory);
      Iterator var9 = instanceGroups.iterator();

      while(true) {
         ResourcePoolGroup instanceGroup;
         String instanceName;
         do {
            do {
               if (!var9.hasNext()) {
                  this.onsClient = new ONSClientRuntimeImpl(pool, beanName, this);
                  return;
               }

               instanceGroup = (ResourcePoolGroup)var9.next();
               instanceName = instanceGroup.getName();
            } while(instanceName == null);
         } while(group != null && (!group.getCategoryName().equals("service_pdbname") || !instanceName.endsWith(group.getName())));

         this.createInstanceRuntime(instanceGroup, instanceName);
      }
   }

   public void unregister() throws ManagementException {
      super.unregister();
      if (this.pool instanceof HAJDBCConnectionPool) {
         ((HAJDBCConnectionPool)this.pool).removeHADataSourceRuntime((HAJDBCConnectionPool)this.pool);
      }

      Iterator var1 = this.racInstances.iterator();

      while(var1.hasNext()) {
         HADataSourceInstanceRuntime instanceRuntime = (HADataSourceInstanceRuntime)var1.next();
         if (instanceRuntime instanceof RuntimeMBeanDelegate) {
            ((RuntimeMBeanDelegate)instanceRuntime).unregister();
         }
      }

   }

   public HADataSourceInstanceRuntime createInstanceRuntime(ResourcePoolGroup group, String instanceName) throws ManagementException {
      if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
         JdbcDebug.JDBCRAC.debug(this.pool.getName() + ": creating runtime MBean for instance=" + instanceName + ", group=" + group.getName());
      }

      HADataSourceInstanceRuntime instanceRuntime = new OracleDataSourceInstanceRuntimeImpl(group, this, instanceName);
      this.racInstances.add(instanceRuntime);
      return instanceRuntime;
   }

   public boolean instanceExists(ResourcePoolGroup group) {
      Iterator var2 = this.racInstances.iterator();

      ResourcePoolGroup rpg;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         HADataSourceInstanceRuntime instance = (HADataSourceInstanceRuntime)var2.next();
         rpg = instance.getGroup();
      } while(rpg == null || !rpg.getName().equals(group.getName()));

      return true;
   }

   public JDBCOracleDataSourceInstanceRuntimeMBean[] getInstances() {
      return (JDBCOracleDataSourceInstanceRuntimeMBean[])((JDBCOracleDataSourceInstanceRuntimeMBean[])this.racInstances.toArray(new JDBCOracleDataSourceInstanceRuntimeMBean[this.racInstances.size()]));
   }

   public ONSClientRuntimeMBean getONSClientRuntime() {
      return this.onsClient;
   }

   public String getServiceName() {
      return ((HAJDBCConnectionPool)this.pool).getServiceName();
   }

   public long getFailedAffinityBasedBorrowCount() {
      return ((HAJDBCConnectionPool)this.pool).getFailedAffinityBasedBorrowCount();
   }

   public long getFailedRCLBBasedBorrowCount() {
      return ((HAJDBCConnectionPool)this.pool).getFailedRCLBBasedBorrowCount();
   }

   public long getSuccessfulAffinityBasedBorrowCount() {
      return ((HAJDBCConnectionPool)this.pool).getSuccessfulAffinityBasedBorrowCount();
   }

   public long getSuccessfulRCLBBasedBorrowCount() {
      return ((HAJDBCConnectionPool)this.pool).getSuccessfulRCLBBasedBorrowCount();
   }

   public String testPool() {
      String message = null;
      ConnectionEnv cc = null;

      try {
         cc = this.pool.reserveInternal(-1);
         if (cc != null && cc.supportIsValid()) {
            boolean valid = cc.conn.jconn.isValid(15);
            if (valid) {
               Object var4 = null;
               return (String)var4;
            }

            message = JDBCUtil.getTextFormatter().testPoolIsValid();
         }
      } catch (Exception var15) {
         message = JDBCUtil.getTextFormatter().testPoolException(var15.toString());
      } finally {
         if (cc != null) {
            try {
               this.pool.release(cc);
            } catch (Exception var14) {
               message = JDBCUtil.getTextFormatter().testPoolException(var14.toString());
            }
         }

      }

      return message;
   }

   public void resetStatistics() {
      super.resetStatistics();
      Iterator var1 = this.racInstances.iterator();

      while(var1.hasNext()) {
         HADataSourceInstanceRuntime instanceRuntime = (HADataSourceInstanceRuntime)var1.next();
         ResourcePoolGroup instanceGroup = instanceRuntime.getGroup();
         if (instanceGroup != null) {
            instanceGroup.resetStatistics();
         }
      }

   }

   public String toString() {
      return "OracleDataSourceRuntimeImpl[name=" + this.getName() + ", id=" + System.identityHashCode(this) + ", pool=" + this.pool + ", group=" + this.group + ", instances=" + this.racInstances + "]";
   }
}
