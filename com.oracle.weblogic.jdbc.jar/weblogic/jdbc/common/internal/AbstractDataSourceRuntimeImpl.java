package weblogic.jdbc.common.internal;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import weblogic.common.ResourceException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JDBCAbstractDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCDataSourceTaskRuntimeMBean;
import weblogic.management.runtime.JDBCDriverRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;

public class AbstractDataSourceRuntimeImpl extends DataSourceRuntimeMBeanImpl implements JDBCAbstractDataSourceRuntimeMBean {
   protected DataSource nds;

   public AbstractDataSourceRuntimeImpl(DataSource nds, String beanName, RuntimeMBean parent, RuntimeMBean restParent, DescriptorBean dsBean) throws ManagementException {
      super((JDBCConnectionPool)null, beanName, parent, restParent, dsBean);
      this.nds = nds;
   }

   public void setDeploymentState(int state) {
      super.setDeploymentState(state);
   }

   public WorkManagerRuntimeMBean[] getWorkManagerRuntimes() {
      return super.getWorkManagerRuntimes();
   }

   public String testPool() {
      return "not implemented";
   }

   public boolean isEnabled() {
      return true;
   }

   public String getState() {
      return "Running";
   }

   public int getPrepStmtCacheMissCount() {
      return -1;
   }

   public int getWaitingForConnectionCurrentCount() {
      return -1;
   }

   public String getVersionJDBCDriver() {
      return "None";
   }

   public int getWaitingForConnectionHighCount() {
      return -1;
   }

   public long getWaitingForConnectionTotal() {
      return -1L;
   }

   public long getWaitingForConnectionSuccessTotal() {
      return -1L;
   }

   public long getWaitingForConnectionFailureTotal() {
      return -1L;
   }

   public int getWaitSecondsHighCount() {
      return -1;
   }

   public long getReserveRequestCount() {
      return -1L;
   }

   public void shrink() throws ResourceException {
   }

   public void reset() throws ResourceException {
   }

   public void suspend() throws ResourceException {
   }

   public void resume() throws ResourceException {
   }

   public void shutdown() throws ResourceException {
   }

   public Properties getProperties() throws ResourceException {
      return null;
   }

   public boolean poolExists(String poolName) throws Exception {
      return false;
   }

   public void setJDBCDriverRuntime(JDBCDriverRuntimeMBean mbean) {
      super.setJDBCDriverRuntime(mbean);
   }

   public void start() throws Exception {
   }

   public boolean addWorkManagerRuntime(WorkManagerRuntimeMBean wmRuntime) {
      return super.addWorkManagerRuntime(wmRuntime);
   }

   public int getLeakedConnectionCount() {
      return -1;
   }

   public int getFailuresToReconnectCount() {
      return -1;
   }

   public int getConnectionDelayTime() {
      return -1;
   }

   public long getPrepStmtCacheAccessCount() {
      return -1L;
   }

   public long getPrepStmtCacheAddCount() {
      return -1L;
   }

   public long getPrepStmtCacheDeleteCount() {
      return -1L;
   }

   public int getPrepStmtCacheCurrentSize() {
      return -1;
   }

   public int getPrepStmtCacheHitCount() {
      return -1;
   }

   public int getActiveConnectionsCurrentCount() {
      return -1;
   }

   public int getActiveConnectionsHighCount() {
      return -1;
   }

   public int getConnectionsTotalCount() {
      return -1;
   }

   public int getCurrCapacity() {
      return -1;
   }

   public int getCurrCapacityHighCount() {
      return -1;
   }

   public int getNumAvailable() {
      return -1;
   }

   public int getHighestNumAvailable() {
      return -1;
   }

   public int getNumUnavailable() {
      return -1;
   }

   public int getHighestNumUnavailable() {
      return -1;
   }

   public int getActiveConnectionsAverageCount() {
      return -1;
   }

   public long getFailedReserveRequestCount() {
      return -1L;
   }

   public void forceSuspend() throws ResourceException {
   }

   public void forceShutdown() throws ResourceException {
   }

   public void clearStatementCache() throws Exception {
   }

   public void dumpPool() throws Exception {
   }

   public void dumpPoolProfile() throws Exception {
   }

   public boolean isOperationAllowed(String operation) throws IllegalArgumentException {
      if (!"Start".equalsIgnoreCase(operation) && !"Shutdown".equalsIgnoreCase(operation) && !"Suspend".equalsIgnoreCase(operation) && !"Resume".equalsIgnoreCase(operation) && !"Reset".equalsIgnoreCase(operation) && !"Shrink".equalsIgnoreCase(operation) && !"Clear".equalsIgnoreCase(operation)) {
         throw new IllegalArgumentException("Invalid argument: " + operation);
      } else {
         return false;
      }
   }

   public int getDeploymentState() {
      return super.getDeploymentState();
   }

   public String getModuleId() {
      return super.getModuleId();
   }

   public JDBCDriverRuntimeMBean getJDBCDriverRuntime() {
      return super.getJDBCDriverRuntime();
   }

   public JDBCDataSourceTaskRuntimeMBean getLastTask() {
      return super.getLastTask();
   }

   protected void lookupMetaData() throws ResourceException, SQLException {
      XAConnection xaconn = null;
      Connection conn = null;

      try {
         if (this.nds instanceof XADataSource) {
            XADataSource pxads = (XADataSource)this.nds;
            xaconn = pxads.getXAConnection();
            conn = xaconn.getConnection();
         } else {
            conn = this.nds.getConnection();
         }

         DatabaseMetaData metaData = conn.getMetaData();
         this.driverVersion = metaData.getDriverVersion();
         this.databaseProductName = metaData.getDatabaseProductName();
         this.databaseProductVersion = metaData.getDatabaseProductVersion();
         this.driverName = metaData.getDriverName();
      } finally {
         if (conn != null) {
            try {
               conn.close();
            } catch (SQLException var12) {
            }
         }

         if (xaconn != null) {
            try {
               xaconn.close();
            } catch (SQLException var11) {
            }
         }

      }

   }
}
