package weblogic.jdbc.common.internal;

import weblogic.common.resourcepool.ResourcePoolGroup;
import weblogic.jdbc.common.rac.RACInstance;
import weblogic.jdbc.common.rac.RACModule;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JDBCOracleDataSourceInstanceRuntimeMBean;
import weblogic.management.runtime.JDBCOracleDataSourceRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class OracleDataSourceInstanceRuntimeImpl extends RuntimeMBeanDelegate implements JDBCOracleDataSourceInstanceRuntimeMBean, HADataSourceInstanceRuntime {
   ResourcePoolGroup group;
   String instanceName;
   HAJDBCConnectionPool hacp;
   String signature;

   public OracleDataSourceInstanceRuntimeImpl(ResourcePoolGroup group, RuntimeMBean parent, String instanceName) throws ManagementException {
      super(instanceName, parent, true);
      this.group = group;
      this.instanceName = instanceName;
      this.hacp = (HAJDBCConnectionPool)((OracleDataSourceRuntimeImpl)parent).pool;
      RACModule racModule = this.hacp.getRACModule();
      RACInstance racInstance = racModule.getRACInstance(instanceName);
      if (racInstance == null) {
         this.signature = "instance=" + instanceName + ",service=" + ((JDBCOracleDataSourceRuntimeMBean)parent).getServiceName();
      } else {
         this.signature = "instance=" + racInstance.getInstance() + ",service=" + racInstance.getService() + ",database=" + racInstance.getDatabase() + ",host=" + racInstance.getHost();
      }

   }

   public ResourcePoolGroup getGroup() {
      return this.group;
   }

   public int getActiveConnectionsCurrentCount() {
      return this.group.getNumReserved();
   }

   public int getConnectionsTotalCount() {
      return this.group.getTotalNumAllocated();
   }

   public int getCurrCapacity() {
      return this.group.getCurrCapacity();
   }

   public int getCurrentWeight() {
      return this.hacp.getWeightForInstance(this.instanceName);
   }

   public String getInstanceName() {
      return this.instanceName;
   }

   public int getNumAvailable() {
      return this.group.getNumAvailable();
   }

   public int getNumUnavailable() {
      return this.group.getNumUnavailable();
   }

   public long getReserveRequestCount() {
      return (long)this.group.getNumReserveRequests();
   }

   public String getSignature() {
      return this.signature;
   }

   public String getState() {
      return this.group.getState();
   }

   public boolean isEnabled() {
      return this.group.isEnabled();
   }

   public boolean isAffEnabled() {
      return this.hacp.getAffForInstance(this.instanceName);
   }

   public String toString() {
      return "OracleDataSourceInstanceRuntimeImpl[name=" + this.getName() + ", instance=" + this.instanceName + ", pool=" + this.hacp + ", group=" + this.group + "]";
   }
}
