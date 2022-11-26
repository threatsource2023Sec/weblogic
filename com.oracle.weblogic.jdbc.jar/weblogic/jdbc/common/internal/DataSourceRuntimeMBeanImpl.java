package weblogic.jdbc.common.internal;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.ResourcePoolGroup;
import weblogic.common.resourcepool.ResourcePoolProfiler;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JDBCDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCDataSourceTaskRuntimeMBean;
import weblogic.management.runtime.JDBCDriverRuntimeMBean;
import weblogic.management.runtime.JDBCReplayStatisticsRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.work.ContextWrap;
import weblogic.work.WorkManagerFactory;

public class DataSourceRuntimeMBeanImpl extends RuntimeMBeanDelegate implements JDBCDataSourceRuntimeMBean {
   public static final String PROGRESS_SUCCESS = "SUCCESS";
   public static final String PROGRESS_FAILED = "FAILURE";
   public static final String PROGRESS_PROCESSING = "PROCESSING";
   protected JDBCConnectionPool pool;
   protected ResourcePoolGroup group;
   private JDBCDriverRuntimeMBean driverRTMBean;
   private ReentrantLock metaDataLock;
   private DatabaseMetaData metaData;
   private String beanName;
   private JDBCReplayStatisticsRuntimeMBean replayStatisticsRuntimeMBean;
   private Set tasks;
   private static final int TASK_AFTERLIFE_TIME_MILLIS = 1800000;
   JDBCDataSourceTaskRuntimeMBeanImpl lastTask;
   private final String defaultForConnectionMetadata;
   protected String driverVersion;
   protected String databaseProductName;
   protected String databaseProductVersion;
   protected String driverName;
   protected volatile boolean needMetaData;

   public DataSourceRuntimeMBeanImpl(JDBCConnectionPool pool, String beanName, RuntimeMBean parent, RuntimeMBean restParent, DescriptorBean descriptor) throws ManagementException {
      this(pool, (ResourcePoolGroup)null, beanName, parent, restParent, descriptor);
   }

   public DataSourceRuntimeMBeanImpl(JDBCConnectionPool pool, ResourcePoolGroup group, String beanName, RuntimeMBean parent, RuntimeMBean restParent, DescriptorBean descriptor) throws ManagementException {
      super(beanName, parent, true, descriptor);
      this.metaDataLock = new ReentrantLock();
      this.lastTask = null;
      this.defaultForConnectionMetadata = "not available";
      this.driverVersion = "not available";
      this.databaseProductName = "not available";
      this.databaseProductVersion = "not available";
      this.driverName = "not available";
      this.needMetaData = true;
      if (restParent != null) {
         this.setRestParent(restParent);
      }

      this.pool = pool;
      this.group = group;
      this.beanName = beanName;
      this.tasks = Collections.synchronizedSet(new HashSet());
   }

   public int getDeploymentState() {
      if (this.pool != null) {
         String state = this.getState();
         return state.equals("Shutdown") ? 1 : 2;
      } else {
         return 2;
      }
   }

   public void setDeploymentState(int state) {
   }

   public boolean addWorkManagerRuntime(WorkManagerRuntimeMBean wmRuntime) {
      return true;
   }

   public WorkManagerRuntimeMBean[] getWorkManagerRuntimes() {
      return null;
   }

   public String getModuleId() {
      return this.getName();
   }

   public String testPool() {
      String message = null;
      ConnectionEnv cc = null;

      String var5;
      try {
         cc = this.pool.reserveInternal(-1);
         if (cc == null || cc.isConnTested()) {
            return message;
         }

         String testQuery = this.pool.getResourceFactory().getTestQuery();
         if (testQuery != null) {
            int result = cc.testInternal(testQuery);
            switch (result) {
               case -1:
                  message = JDBCUtil.getTextFormatter().testPoolQueryFailed(this.pool.getResourceFactory().getTestQuery());
                  var5 = message;
                  return var5;
               case 0:
               default:
                  break;
               case 1:
                  var5 = null;
                  return var5;
            }
         }

         if (!cc.supportIsValid()) {
            return message;
         }

         boolean valid = cc.conn.jconn.isValid(15);
         if (!valid) {
            message = JDBCUtil.getTextFormatter().testPoolIsValid();
            return message;
         }

         var5 = null;
      } catch (Exception var18) {
         message = JDBCUtil.getTextFormatter().testPoolException(var18.toString());
         return message;
      } finally {
         if (cc != null) {
            try {
               this.pool.release(cc);
            } catch (Exception var17) {
               message = JDBCUtil.getTextFormatter().testPoolException(var17.toString());
            }
         }

      }

      return var5;
   }

   public int getLeakedConnectionCount() {
      return this.group != null ? -1 : this.pool.getNumLeaked();
   }

   public boolean isEnabled() {
      return this.group != null ? this.group.isEnabled() : this.pool.isEnabled();
   }

   public String getState() {
      return this.pool.getDerivedState();
   }

   public int getFailuresToReconnectCount() {
      return this.group != null ? -1 : this.pool.getNumFailuresToRefresh();
   }

   public int getConnectionDelayTime() {
      return this.group != null ? -1 : this.pool.getCreationDelayTime();
   }

   public long getPrepStmtCacheAccessCount() {
      return this.group != null ? -1L : this.pool.getPrepStmtCacheAccessCount();
   }

   public long getPrepStmtCacheAddCount() {
      return this.group != null ? -1L : this.pool.getPrepStmtCacheAddCount();
   }

   public long getPrepStmtCacheDeleteCount() {
      return this.group != null ? -1L : this.pool.getPrepStmtCacheDeleteCount();
   }

   public int getPrepStmtCacheCurrentSize() {
      return this.group != null ? -1 : this.pool.getPrepStmtCacheCurrentSize();
   }

   public int getPrepStmtCacheHitCount() {
      return this.group != null ? -1 : this.pool.getPrepStmtCacheHitCount();
   }

   public int getPrepStmtCacheMissCount() {
      return this.group != null ? -1 : this.pool.getPrepStmtCacheMissCount();
   }

   public int getActiveConnectionsCurrentCount() {
      return this.group != null ? this.group.getNumReserved() : this.pool.getNumReserved();
   }

   public int getWaitingForConnectionCurrentCount() {
      if (this.group != null) {
         return -1;
      } else {
         int x = this.pool.getNumWaiters();
         if (x < 0) {
            x = 0;
         }

         return x;
      }
   }

   public String getVersionJDBCDriver() {
      return this.pool.getDriverVersion();
   }

   public int getActiveConnectionsHighCount() {
      return this.group != null ? -1 : this.pool.getHighestNumReserved();
   }

   public int getWaitingForConnectionHighCount() {
      return this.group != null ? -1 : this.pool.getHighestNumWaiters();
   }

   public long getWaitingForConnectionTotal() {
      return this.group != null ? -1L : this.pool.getTotalWaitingForConnection();
   }

   public long getWaitingForConnectionSuccessTotal() {
      return this.group != null ? -1L : this.pool.getTotalWaitingForConnectionSuccess();
   }

   public long getWaitingForConnectionFailureTotal() {
      return this.group != null ? -1L : this.pool.getTotalWaitingForConnectionFailure();
   }

   public int getWaitSecondsHighCount() {
      return this.group != null ? -1 : this.pool.getHighestWaitSeconds();
   }

   public int getConnectionsTotalCount() {
      return this.group != null ? this.group.getTotalNumAllocated() : this.pool.getTotalNumAllocated();
   }

   public int getCurrCapacity() {
      return this.group != null ? this.group.getCurrCapacity() : this.pool.getCurrCapacity();
   }

   public int getCurrCapacityHighCount() {
      return this.group != null ? this.group.getHighestCurrCapacity() : this.pool.getHighestCurrCapacity();
   }

   public int getNumAvailable() {
      return this.group != null ? this.group.getNumAvailable() : this.pool.getNumAvailable();
   }

   public int getHighestNumAvailable() {
      return this.group != null ? -1 : this.pool.getHighestNumAvailable();
   }

   public int getNumUnavailable() {
      return this.group != null ? this.group.getNumUnavailable() : this.pool.getNumUnavailable();
   }

   public int getHighestNumUnavailable() {
      return this.group != null ? -1 : this.pool.getHighestNumUnavailable();
   }

   public int getActiveConnectionsAverageCount() {
      return this.group != null ? -1 : this.pool.getAverageReserved();
   }

   public long getReserveRequestCount() {
      return this.group != null ? (long)this.group.getNumReserveRequests() : this.pool.getNumReserveRequests();
   }

   public long getFailedReserveRequestCount() {
      return this.group != null ? -1L : this.pool.getNumFailedReserveRequests();
   }

   public long getRepurposeCount() {
      return this.group != null ? -1L : this.pool.getRepurposeCount();
   }

   public long getFailedRepurposeCount() {
      return this.group != null ? -1L : this.pool.getFailedRepurposeCount();
   }

   public long getResolvedAsCommittedTotalCount() {
      return this.group != null ? -1L : this.pool.getResolvedAsCommittedTotalCount();
   }

   public long getResolvedAsNotCommittedTotalCount() {
      return this.group != null ? -1L : this.pool.getResolvedAsNotCommittedTotalCount();
   }

   public long getUnresolvedTotalCount() {
      return this.group != null ? -1L : this.pool.getUnresolvedTotalCount();
   }

   public long getCommitOutcomeRetryTotalCount() {
      return this.group != null ? -1L : this.pool.getCommitOutcomeRetryTotalCount();
   }

   public void shrink() throws ResourceException {
      if (this.group != null) {
         this.throwGroupOperationUnsupported();
      }

      JDBCDataSourceTaskRuntimeMBeanImpl taskMBean;
      try {
         taskMBean = new JDBCDataSourceTaskRuntimeMBeanImpl("Shrink-JDBCDataSource", this, true);
      } catch (Exception var11) {
         this.pool.shrink();
         return;
      }

      this.tasks.add(taskMBean);
      this.lastTask = taskMBean;
      taskMBean.setBeginTime(System.currentTimeMillis());
      taskMBean.setDescription("Shrink JDBCDataSource: " + this.getName());

      try {
         this.pool.shrink();
         taskMBean.setStatus("SUCCESS");
      } catch (ResourceException var8) {
         taskMBean.setStatus("FAILURE");
         taskMBean.setError(var8);
         throw var8;
      } catch (RuntimeException var9) {
         taskMBean.setStatus("FAILURE");
         taskMBean.setError(var9);
         throw var9;
      } finally {
         taskMBean.setEndTime(System.currentTimeMillis());
         this.clearOldTaskRuntimes();
      }

   }

   public void reset() throws ResourceException {
      if (this.group != null) {
         this.throwGroupOperationUnsupported();
      }

      JDBCDataSourceTaskRuntimeMBeanImpl taskMBean;
      try {
         taskMBean = new JDBCDataSourceTaskRuntimeMBeanImpl("Reset-JDBCDataSource", this, true);
      } catch (Exception var11) {
         this.pool.reset();
         return;
      }

      this.tasks.add(taskMBean);
      this.lastTask = taskMBean;
      taskMBean.setBeginTime(System.currentTimeMillis());
      taskMBean.setDescription("Reset JDBCDataSource: " + this.getName());

      try {
         this.pool.reset();
         taskMBean.setStatus("SUCCESS");
      } catch (ResourceException var8) {
         taskMBean.setStatus("FAILURE");
         taskMBean.setError(var8);
         throw var8;
      } catch (RuntimeException var9) {
         taskMBean.setStatus("FAILURE");
         taskMBean.setError(var9);
         throw var9;
      } finally {
         taskMBean.setEndTime(System.currentTimeMillis());
         this.clearOldTaskRuntimes();
      }

   }

   public void suspend() throws ResourceException {
      JDBCDataSourceTaskRuntimeMBeanImpl taskMBean = this.setTask(false);
      this.doOperation(false, 0, taskMBean);
   }

   public void forceSuspend() throws ResourceException {
      JDBCDataSourceTaskRuntimeMBeanImpl taskMBean;
      try {
         taskMBean = new JDBCDataSourceTaskRuntimeMBeanImpl("ForceSuspend-JDBCDataSource", this, true);
      } catch (Exception var11) {
         this.pool.forceSuspendExternal();
         return;
      }

      this.tasks.add(taskMBean);
      this.lastTask = taskMBean;
      taskMBean.setBeginTime(System.currentTimeMillis());
      taskMBean.setDescription("ForceSuspend JDBCDataSource: " + this.getName());

      try {
         this.pool.forceSuspendExternal();
         taskMBean.setStatus("SUCCESS");
      } catch (ResourceException var8) {
         taskMBean.setStatus("FAILURE");
         taskMBean.setError(var8);
         throw var8;
      } catch (RuntimeException var9) {
         taskMBean.setStatus("FAILURE");
         taskMBean.setError(var9);
         throw var9;
      } finally {
         taskMBean.setEndTime(System.currentTimeMillis());
         this.clearOldTaskRuntimes();
      }

   }

   public void resume() throws ResourceException {
      this.clearCachedValues();

      JDBCDataSourceTaskRuntimeMBeanImpl taskMBean;
      try {
         taskMBean = new JDBCDataSourceTaskRuntimeMBeanImpl("Resume-JDBCDataSource", this, true);
      } catch (Exception var11) {
         this.pool.resumeExternal();
         return;
      }

      this.tasks.add(taskMBean);
      this.lastTask = taskMBean;
      taskMBean.setBeginTime(System.currentTimeMillis());
      taskMBean.setDescription("Resume JDBCDataSource: " + this.getName());

      try {
         this.pool.resumeExternal();
         taskMBean.setStatus("SUCCESS");
      } catch (ResourceException var8) {
         taskMBean.setStatus("FAILURE");
         taskMBean.setError(var8);
         throw var8;
      } catch (RuntimeException var9) {
         taskMBean.setStatus("FAILURE");
         taskMBean.setError(var9);
         throw var9;
      } finally {
         taskMBean.setEndTime(System.currentTimeMillis());
         this.clearOldTaskRuntimes();
      }

   }

   public void shutdown() throws ResourceException {
      JDBCDataSourceTaskRuntimeMBeanImpl taskMBean = this.setTask(true);
      this.doOperation(true, 0, taskMBean);
   }

   public void forceShutdown() throws ResourceException {
      JDBCDataSourceTaskRuntimeMBeanImpl taskMBean;
      try {
         taskMBean = new JDBCDataSourceTaskRuntimeMBeanImpl("ForceShutdown-JDBCDataSource", this, true);
      } catch (Exception var11) {
         this.pool.forceShutdownExternal();
         return;
      }

      this.tasks.add(taskMBean);
      this.lastTask = taskMBean;
      taskMBean.setBeginTime(System.currentTimeMillis());
      taskMBean.setDescription("ForceShutdown JDBCDataSource: " + this.getName());

      try {
         this.pool.forceShutdownExternal();
         taskMBean.setStatus("SUCCESS");
      } catch (ResourceException var8) {
         taskMBean.setStatus("FAILURE");
         taskMBean.setError(var8);
         throw var8;
      } catch (RuntimeException var9) {
         taskMBean.setStatus("FAILURE");
         taskMBean.setError(var9);
         throw var9;
      } finally {
         taskMBean.setEndTime(System.currentTimeMillis());
         this.clearOldTaskRuntimes();
      }

   }

   public JDBCDataSourceTaskRuntimeMBean suspend(int operationSecs) throws ResourceException {
      JDBCDataSourceTaskRuntimeMBeanImpl taskMBean = this.setTask(false);
      if (taskMBean != null) {
         taskMBean.setStatus("PROCESSING");
      }

      OperationRequest rq = new OperationRequest(false, operationSecs, taskMBean);
      WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(rq));
      return taskMBean;
   }

   public JDBCDataSourceTaskRuntimeMBean shutdown(int operationSecs) throws ResourceException {
      JDBCDataSourceTaskRuntimeMBeanImpl taskMBean = this.setTask(true);
      if (taskMBean != null) {
         taskMBean.setStatus("PROCESSING");
      }

      OperationRequest rq = new OperationRequest(true, operationSecs, taskMBean);
      WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(rq));
      return taskMBean;
   }

   private JDBCDataSourceTaskRuntimeMBeanImpl setTask(boolean shutdown) {
      String op = "";
      if (shutdown) {
         op = "Shutdown";
      } else {
         op = "Suspend";
      }

      JDBCDataSourceTaskRuntimeMBeanImpl taskMBean;
      try {
         taskMBean = new JDBCDataSourceTaskRuntimeMBeanImpl(op + "-JDBCDataSource", this, true);
         this.tasks.add(taskMBean);
         this.lastTask = taskMBean;
      } catch (Exception var5) {
         taskMBean = null;
      }

      return taskMBean;
   }

   private void doOperation(boolean shutdown, int operationSecs, JDBCDataSourceTaskRuntimeMBeanImpl task) throws ResourceException {
      String op = "";
      if (shutdown) {
         op = "Shutdown";
      } else {
         op = "Suspend";
      }

      if (task != null) {
         task.setBeginTime(System.currentTimeMillis());
         task.setDescription(op + " JDBCDataSource: " + this.getName());
      }

      try {
         if (shutdown) {
            this.pool.shutdownExternal(operationSecs);
         } else {
            this.pool.suspendExternal(operationSecs);
         }

         if (task != null) {
            task.setStatus("SUCCESS");
         }
      } catch (ResourceException var10) {
         if (task != null) {
            task.setStatus("FAILURE");
            task.setError(var10);
         }

         throw var10;
      } catch (RuntimeException var11) {
         if (task != null) {
            task.setStatus("FAILURE");
            task.setError(var11);
         }

         throw var11;
      } finally {
         if (task != null) {
            task.setEndTime(System.currentTimeMillis());
         }

         this.clearOldTaskRuntimes();
      }

   }

   public Properties getProperties() throws ResourceException {
      if (this.pool != null && this.pool.getProperties() != null) {
         Properties prop = (Properties)((Properties)this.pool.getProperties().clone());
         prop.remove("password");
         return prop;
      } else {
         return null;
      }
   }

   public boolean poolExists(String poolName) throws Exception {
      return this.pool.poolExists(poolName);
   }

   public void clearStatementCache() throws Exception {
      JDBCDataSourceTaskRuntimeMBeanImpl taskMBean;
      try {
         taskMBean = new JDBCDataSourceTaskRuntimeMBeanImpl("ClearStatementCache-JDBCDataSource", this, true);
      } catch (Exception var11) {
         this.pool.clearStatementCache();
         return;
      }

      this.tasks.add(taskMBean);
      this.lastTask = taskMBean;
      taskMBean.setBeginTime(System.currentTimeMillis());
      taskMBean.setDescription("ClearStatementCache JDBCDataSource: " + this.getName());

      try {
         this.pool.clearStatementCache();
         taskMBean.setStatus("SUCCESS");
      } catch (ResourceException var8) {
         taskMBean.setStatus("FAILURE");
         taskMBean.setError(var8);
         throw var8;
      } catch (RuntimeException var9) {
         taskMBean.setStatus("FAILURE");
         taskMBean.setError(var9);
         throw var9;
      } finally {
         taskMBean.setEndTime(System.currentTimeMillis());
         this.clearOldTaskRuntimes();
      }

   }

   public void dumpPool() throws Exception {
      this.pool.dumpPool((PrintWriter)null);
   }

   public void dumpPoolProfile() throws Exception {
      ResourcePoolProfiler profiler = this.pool.getProfiler();
      if (profiler != null) {
         profiler.dumpData();
      }

   }

   public void setJDBCDriverRuntime(JDBCDriverRuntimeMBean mbean) {
      this.driverRTMBean = mbean;
   }

   public JDBCDriverRuntimeMBean getJDBCDriverRuntime() {
      return this.driverRTMBean;
   }

   public JDBCDataSourceTaskRuntimeMBean getLastTask() {
      return this.lastTask;
   }

   public JDBCDataSourceTaskRuntimeMBean[] getTasks() {
      return (JDBCDataSourceTaskRuntimeMBean[])((JDBCDataSourceTaskRuntimeMBean[])this.tasks.toArray(new JDBCDataSourceTaskRuntimeMBean[this.tasks.size()]));
   }

   public void start() throws Exception {
      this.clearCachedValues();

      JDBCDataSourceTaskRuntimeMBeanImpl taskMBean;
      try {
         taskMBean = new JDBCDataSourceTaskRuntimeMBeanImpl("Start-JDBCDataSource", this, true);
      } catch (Exception var11) {
         this.pool.startExternal();
         return;
      }

      this.tasks.add(taskMBean);
      this.lastTask = taskMBean;
      taskMBean.setBeginTime(System.currentTimeMillis());
      taskMBean.setDescription("Start JDBCDataSource: " + this.getName());

      try {
         this.pool.startExternal();
         taskMBean.setStatus("SUCCESS");
      } catch (ResourceException var8) {
         taskMBean.setStatus("FAILURE");
         taskMBean.setError(var8);
         throw var8;
      } catch (RuntimeException var9) {
         taskMBean.setStatus("FAILURE");
         taskMBean.setError(var9);
         throw var9;
      } finally {
         taskMBean.setEndTime(System.currentTimeMillis());
         this.clearOldTaskRuntimes();
      }

   }

   public boolean isOperationAllowed(String operation) throws IllegalArgumentException {
      int state = this.pool.getStateAsInt();
      if ("Start".equalsIgnoreCase(operation)) {
         if (state == 100) {
            return true;
         }
      } else if ("Shutdown".equalsIgnoreCase(operation)) {
         if (state == 101 || state == 102) {
            return true;
         }
      } else if ("Suspend".equalsIgnoreCase(operation)) {
         if (state == 101) {
            return true;
         }
      } else if ("Resume".equalsIgnoreCase(operation)) {
         if (state == 102) {
            return true;
         }
      } else if ("Reset".equalsIgnoreCase(operation)) {
         if (state == 101 || state == 102) {
            return true;
         }
      } else if ("Shrink".equalsIgnoreCase(operation)) {
         if (state == 101 || state == 102) {
            return true;
         }
      } else {
         if (!"Clear".equalsIgnoreCase(operation)) {
            throw new IllegalArgumentException("Invalid argument: " + operation);
         }

         if (state == 101 || state == 102) {
            return true;
         }
      }

      return false;
   }

   private void clearCachedValues() {
      this.driverVersion = "not available";
      this.databaseProductName = "not available";
      this.databaseProductVersion = "not available";
      this.driverName = "not available";
      this.needMetaData = true;
   }

   protected void lookupMetaData() throws ResourceException, SQLException {
      ConnectionEnv cc = null;

      try {
         if (this.getCurrCapacity() > 0) {
            cc = this.pool.reserveInternal(-1);
            if (cc == null) {
               throw new ResourceException("Unable to obtain a connection from data source " + this.getName());
            }

            this.driverVersion = cc.conn.jconn.getMetaData().getDriverVersion();
            this.databaseProductName = cc.conn.jconn.getMetaData().getDatabaseProductName();
            this.databaseProductVersion = cc.conn.jconn.getMetaData().getDatabaseProductVersion();
            this.driverName = cc.conn.jconn.getMetaData().getDriverName();
            this.needMetaData = false;
         }
      } finally {
         if (cc != null) {
            this.pool.release(cc);
         }

      }

   }

   private void getMetaData() throws ResourceException, SQLException {
      try {
         if (!this.metaDataLock.tryLock(60L, TimeUnit.SECONDS)) {
            throw new ResourceException("Operation timed out waiting to get JDBC meta data");
         }
      } catch (InterruptedException var5) {
         throw new ResourceException(var5.getMessage());
      }

      try {
         if (this.needMetaData) {
            this.lookupMetaData();
         }
      } finally {
         this.metaDataLock.unlock();
      }

   }

   public String getDatabaseProductName() throws RemoteException {
      try {
         this.getMetaData();
         return this.databaseProductName;
      } catch (ResourceException var2) {
         throw new RemoteException(var2.getMessage());
      } catch (SQLException var3) {
         throw new RemoteException(var3.getMessage());
      }
   }

   public String getDatabaseProductVersion() throws RemoteException {
      try {
         this.getMetaData();
         return this.databaseProductVersion;
      } catch (ResourceException var2) {
         throw new RemoteException(var2.getMessage());
      } catch (SQLException var3) {
         throw new RemoteException(var3.getMessage());
      }
   }

   public String getDriverName() throws RemoteException {
      try {
         this.getMetaData();
         return this.driverName;
      } catch (ResourceException var2) {
         throw new RemoteException(var2.getMessage());
      } catch (SQLException var3) {
         throw new RemoteException(var3.getMessage());
      }
   }

   public String getDriverVersion() throws RemoteException {
      try {
         this.getMetaData();
         return this.driverVersion;
      } catch (ResourceException var2) {
         throw new RemoteException(var2.getMessage());
      } catch (SQLException var3) {
         throw new RemoteException(var3.getMessage());
      }
   }

   public JDBCReplayStatisticsRuntimeMBean getJDBCReplayStatisticsRuntimeMBean() throws Exception {
      if (this.pool != null && this.pool.getReplayStatisticsSnapshot() != null) {
         if (this.replayStatisticsRuntimeMBean == null) {
            this.replayStatisticsRuntimeMBean = new JDBCReplayStatisticsRuntimeMBeanImpl(this.pool, this.beanName + ".ReplayStatistics", this);
         }

         return this.replayStatisticsRuntimeMBean;
      } else {
         return null;
      }
   }

   public void clearOldTaskRuntimes() {
      synchronized(this.tasks) {
         Iterator iter = this.tasks.iterator();

         while(iter.hasNext()) {
            JDBCDataSourceTaskRuntimeMBeanImpl task = (JDBCDataSourceTaskRuntimeMBeanImpl)iter.next();
            if (task.getEndTime() > 0L && System.currentTimeMillis() - task.getEndTime() > 1800000L) {
               try {
                  task.unregister();
               } catch (ManagementException var6) {
               }

               iter.remove();
            }
         }

      }
   }

   private void throwGroupOperationUnsupported() {
      throw new UnsupportedOperationException("operation not supported for subset " + this.group + " of pool " + this.pool.getName());
   }

   public void resetStatistics() {
      this.pool.resetStatistics();
   }

   private final class OperationRequest implements Runnable {
      private int timeout;
      private boolean shutdown;
      private final JDBCDataSourceTaskRuntimeMBeanImpl taskMBean;

      OperationRequest(boolean shutdown, int timeout, JDBCDataSourceTaskRuntimeMBeanImpl taskMBean) {
         this.shutdown = shutdown;
         this.timeout = timeout;
         this.taskMBean = taskMBean;
      }

      public void run() {
         try {
            DataSourceRuntimeMBeanImpl.this.doOperation(this.shutdown, this.timeout, this.taskMBean);
         } catch (Exception var2) {
            this.taskMBean.setError(var2);
         }

      }
   }
}
