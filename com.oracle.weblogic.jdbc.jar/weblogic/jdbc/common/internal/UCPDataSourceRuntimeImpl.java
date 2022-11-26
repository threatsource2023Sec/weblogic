package weblogic.jdbc.common.internal;

import javax.sql.DataSource;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.jdbc.common.rac.internal.UCPRuntime;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JDBCUCPDataSourceRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;

public class UCPDataSourceRuntimeImpl extends AbstractDataSourceRuntimeImpl implements JDBCUCPDataSourceRuntimeMBean {
   UCPRuntime runtime = null;
   String driver = null;

   public UCPDataSourceRuntimeImpl(DataSource ds, String beanName, RuntimeMBean parent, RuntimeMBean restParent, DescriptorBean dsBean) throws ManagementException {
      super(ds, beanName, parent, restParent, dsBean);
      this.runtime = new UCPRuntime(ds);
      this.driver = ((JDBCDataSourceBean)dsBean).getJDBCDriverParams().getDriverName();
   }

   public String getVersionJDBCDriver() {
      return this.driver;
   }

   public int getCurrCapacity() {
      return this.runtime.getCurrCapacity();
   }

   public int getActiveConnectionsCurrentCount() {
      return this.runtime.getActiveConnectionsCurrentCount();
   }

   public int getNumAvailable() {
      return this.runtime.getNumAvailable();
   }

   public long getReserveRequestCount() {
      return this.runtime.getReserveRequestCount();
   }

   public int getActiveConnectionsAverageCount() {
      return this.runtime.getActiveConnectionsAverageCount();
   }

   public int getCurrCapacityHighCount() {
      return this.runtime.getCurrCapacityHighCount();
   }

   public int getConnectionsTotalCount() {
      return this.runtime.getConnectionsTotalCount();
   }

   public int getNumUnavailable() {
      return this.runtime.getNumUnavailable();
   }

   public long getWaitingForConnectionSuccessTotal() {
      return this.runtime.getWaitingForConnectionSuccessTotal();
   }

   public String testPool() {
      return this.runtime.testPool();
   }
}
