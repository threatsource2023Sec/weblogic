package weblogic.jdbc.common.internal;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import oracle.ucp.ConnectionLabelingCallback;
import oracle.ucp.jdbc.ConnectionInitializationCallback;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceFactory;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ReserveReleaseInterceptor;
import weblogic.common.resourcepool.ResourcePoolGroup;
import weblogic.common.resourcepool.ResourcePoolMaintainer;
import weblogic.common.resourcepool.ResourcePoolProfiler;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.jdbc.extensions.DriverInterceptor;
import weblogic.jdbc.jta.DataSource;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class SharingConnectionPool implements JDBCConnectionPool {
   protected JDBCDataSourceBean dsBean;
   protected JDBCConnectionPool sharedPool;
   protected String pdbName;
   protected String serviceName;
   protected volatile SwitchingContext switchingContext;
   protected ResourcePoolGroup group;
   protected DataSource jtaDataSource;
   protected int state = 100;
   protected boolean suspending;
   protected String appName;
   protected String moduleName;
   protected String compName;
   protected AtomicInteger numLeaked = new AtomicInteger();

   public SharingConnectionPool(JDBCDataSourceBean dsBean, JDBCConnectionPool sharedPool, String appName, String moduleName, String compName) throws ResourceException {
      this.dsBean = dsBean;
      this.sharedPool = sharedPool;
      this.appName = appName;
      this.moduleName = moduleName;
      this.compName = compName;
      this.serviceName = JDBCUtil.getPDBServiceName(dsBean);
      this.pdbName = JDBCUtil.getPDBName(dsBean);
      this.initializeSwitchingContext();
      this.initializeGroups();
   }

   protected void initializeGroups() {
      if (this.switchingContext != null) {
         this.group = this.sharedPool.getOrCreateGroup("service_pdbname", JDBCUtil.getServicePDBGroupName(this.switchingContext.getPDBServiceName(), this.switchingContext.getPDBName()));
      }

   }

   protected void initializeSwitchingContext() throws ResourceException {
      if (this.pdbName != null && this.serviceName != null) {
         this.switchingContext = new SwitchingContextImpl(this, this.pdbName, this.serviceName, this.dsBean);
      } else {
         try {
            if (this.pdbName == null) {
               this.setSwitchingContextToRoot();
            } else if (this.pdbName != null && this.serviceName == null) {
               this.setSwitchingContextToPDBDefaultService();
            }
         } catch (ResourceException var2) {
            if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
               JdbcDebug.JDBCCONN.debug("Shared pool datasource " + this.getName() + ": error initializing switching context", var2);
            }

            throw var2;
         }
      }

   }

   private void setSwitchingContextToRoot() throws ResourceException {
      this.switchingContext = this.sharedPool.getRootSwitchingContext();
      if (this.switchingContext == null) {
         ConnectionEnv ce = null;

         try {
            ce = this.sharedPool.reserveInternal(-1);
            SwitchingContext rootSwitchingContext = this.sharedPool.getRootSwitchingContext();
            if (this.serviceName == null) {
               this.serviceName = rootSwitchingContext.getPDBServiceName();
            }

            if (this.pdbName == null) {
               this.pdbName = rootSwitchingContext.getPDBName();
            }

            if (this.serviceName != null && this.pdbName != null) {
               this.switchingContext = new SwitchingContextImpl(this, this.pdbName, this.serviceName, this.dsBean);
            }
         } finally {
            if (ce != null) {
               try {
                  this.sharedPool.release(ce);
               } catch (ResourceException var8) {
               }
            }

         }
      }

   }

   private void setSwitchingContextToPDBDefaultService() throws ResourceException {
      if (this.pdbName == null) {
         throw new ResourceException("Shared pool datasource " + this.getName() + ": unable to determine default service for PDB " + this.pdbName);
      } else {
         ConnectionEnv ce = null;
         SwitchingContext pdbNoServiceContext = new SwitchingContextImpl(this, this.pdbName, (String)null);
         SwitchingContextManager.getInstance().push(pdbNoServiceContext);

         try {
            ce = this.sharedPool.reserveInternal(-1);
            this.serviceName = this.sharedPool.getOracleHelper().getServiceName(ce);
            this.switchingContext = new SwitchingContextImpl(this, this.pdbName, this.serviceName, this.dsBean);
         } finally {
            if (ce != null) {
               try {
                  this.sharedPool.release(ce);
               } catch (ResourceException var9) {
               }
            }

            SwitchingContextManager.getInstance().pop();
         }

      }
   }

   private SwitchingContext getSwitchingContext() throws ResourceException {
      if (this.switchingContext != null) {
         return this.switchingContext;
      } else {
         this.initializeSwitchingContext();
         this.initializeGroups();
         return this.switchingContext;
      }
   }

   public SwitchingContext getRootSwitchingContext() {
      return null;
   }

   public void setRootSwitchingContext(SwitchingContext sc) {
   }

   public JDBCConnectionPool getSharedPool() {
      return this.sharedPool;
   }

   protected String getPDBName() {
      return this.pdbName;
   }

   protected String getPDBServiceName() {
      return this.serviceName;
   }

   public PooledResourceFactory initPooledResourceFactory(Properties initInfo) throws ResourceException {
      return null;
   }

   public void shrink() throws ResourceException {
      throw new UnsupportedOperationException();
   }

   public void refresh() throws ResourceException {
      throw new UnsupportedOperationException();
   }

   public String getState() {
      synchronized(this) {
         switch (this.state) {
            case 100:
               return "Shutdown";
            case 101:
               return "Running";
            case 102:
               return "Suspended";
            case 103:
               return "Unhealthy";
            default:
               return "Unknown";
         }
      }
   }

   public int getStateAsInt() {
      return this.state;
   }

   public String getDerivedState() {
      return this.getState();
   }

   public PooledResource[] getResources() {
      throw new UnsupportedOperationException();
   }

   public PooledResource reserveResource(int waitSeconds, PooledResourceInfo info) throws ResourceException {
      throw new UnsupportedOperationException();
   }

   public PooledResource reserveResource(PooledResourceInfo info) throws ResourceException {
      throw new UnsupportedOperationException();
   }

   public void releaseResource(PooledResource resource) throws ResourceException {
      throw new UnsupportedOperationException();
   }

   public void createResources(int count, PooledResourceInfo[] infoList) throws ResourceException {
   }

   public void createResources(int count, PooledResourceInfo[] infoList, List created) throws ResourceException {
   }

   public PooledResource matchResource(PooledResourceInfo info) throws ResourceException {
      return null;
   }

   public int getCurrCapacity() {
      return this.group != null ? this.group.getCurrCapacity() : this.sharedPool.getCurrCapacity();
   }

   public int getHighestCurrCapacity() {
      return this.group != null ? this.group.getHighestCurrCapacity() : this.sharedPool.getHighestCurrCapacity();
   }

   public int getNumReserved() {
      return this.group != null ? this.group.getNumReserved() : this.sharedPool.getNumReserved();
   }

   public int getNumAvailable() {
      return this.group != null ? this.group.getNumAvailable() : this.sharedPool.getNumAvailable();
   }

   public int getNumUnavailable() {
      return this.group != null ? this.group.getNumUnavailable() : this.sharedPool.getNumUnavailable();
   }

   public int getTotalNumAllocated() {
      return this.group != null ? this.group.getTotalNumAllocated() : this.sharedPool.getTotalNumAllocated();
   }

   public long getNumReserveRequests() {
      return this.group != null ? (long)this.group.getNumReserveRequests() : this.sharedPool.getNumReserveRequests();
   }

   public int getNumLeaked() {
      return this.numLeaked.get();
   }

   public void incrementNumLeaked() {
      this.numLeaked.incrementAndGet();
      this.sharedPool.incrementNumLeaked();
   }

   public int getNumFailuresToRefresh() {
      return this.sharedPool.getNumFailuresToRefresh();
   }

   public int getCreationDelayTime() {
      return this.sharedPool.getCreationDelayTime();
   }

   public int getNumWaiters() {
      return this.sharedPool.getNumWaiters();
   }

   public int getHighestNumWaiters() {
      return this.sharedPool.getHighestNumWaiters();
   }

   public long getTotalWaitingForConnection() {
      return this.sharedPool.getTotalWaitingForConnection();
   }

   public long getTotalWaitingForConnectionSuccess() {
      return this.sharedPool.getTotalWaitingForConnectionSuccess();
   }

   public long getTotalWaitingForConnectionFailure() {
      return this.sharedPool.getTotalWaitingForConnectionFailure();
   }

   public int getHighestWaitSeconds() {
      return this.sharedPool.getHighestWaitSeconds();
   }

   public int getHighestNumReserved() {
      return this.sharedPool.getHighestNumReserved();
   }

   public int getHighestNumAvailable() {
      return this.sharedPool.getHighestNumAvailable();
   }

   public int getHighestNumUnavailable() {
      return this.sharedPool.getHighestNumUnavailable();
   }

   public int getTotalNumDestroyed() {
      return this.sharedPool.getTotalNumDestroyed();
   }

   public int getMaxCapacity() {
      return this.sharedPool.getMaxCapacity();
   }

   public int getMinCapacity() {
      return this.sharedPool.getMinCapacity();
   }

   public int getAverageReserved() {
      return this.sharedPool.getAverageReserved();
   }

   public long getNumFailedReserveRequests() {
      return this.sharedPool.getNumFailedReserveRequests();
   }

   public long getRepurposeCount() {
      return this.sharedPool.getRepurposeCount();
   }

   public long getFailedRepurposeCount() {
      return this.sharedPool.getFailedRepurposeCount();
   }

   public long getResolvedAsCommittedTotalCount() {
      return this.sharedPool.getResolvedAsCommittedTotalCount();
   }

   public void incrementResolvedAsCommittedTotalCount() {
      this.sharedPool.incrementResolvedAsCommittedTotalCount();
   }

   public long getResolvedAsNotCommittedTotalCount() {
      return this.sharedPool.getResolvedAsNotCommittedTotalCount();
   }

   public void incrementResolvedAsNotCommittedTotalCount() {
      this.sharedPool.incrementResolvedAsNotCommittedTotalCount();
   }

   public long getUnresolvedTotalCount() {
      return this.sharedPool.getUnresolvedTotalCount();
   }

   public void incrementUnresolvedTotalCount() {
      this.sharedPool.incrementUnresolvedTotalCount();
   }

   public long getCommitOutcomeRetryTotalCount() {
      return this.sharedPool.getCommitOutcomeRetryTotalCount();
   }

   public void incrementCommitOutcomeRetryTotalCount() {
      this.sharedPool.incrementCommitOutcomeRetryTotalCount();
   }

   public void setMaximumCapacity(int newVal) throws ResourceException, IllegalArgumentException {
   }

   public void setMinimumCapacity(int newVal) throws ResourceException, IllegalArgumentException {
   }

   public void setInitialCapacity(int newVal) throws ResourceException, IllegalArgumentException {
   }

   public void setCapacityIncrement(int newVal) {
   }

   public void setHighestNumWaiters(int newVal) {
   }

   public void setHighestNumUnavailable(int newVal) {
   }

   public void setInactiveResourceTimeoutSeconds(int newVal) {
   }

   public void setResourceCreationRetrySeconds(int newVal) {
   }

   public void setResourceReserveTimeoutSeconds(int newVal) {
   }

   public void setShrinkFrequencySeconds(int newVal) {
   }

   public void setTestFrequencySeconds(int newVal) {
   }

   public void setShrinkEnabled(boolean newVal) {
   }

   public void setTestOnReserve(boolean newVal) {
   }

   public void setTestOnRelease(boolean newVal) {
   }

   public void setTestOnCreate(boolean newVal) {
   }

   public void setIgnoreInUseResources(boolean newVal) {
   }

   public void setCountOfTestFailuresTillFlush(int newVal) {
   }

   public void setCountOfRefreshFailuresTillDisable(int newVal) {
   }

   public void setProfileHarvestFrequencySeconds(int newVal) {
   }

   public ResourcePoolMaintainer getMaintainer() {
      return null;
   }

   public void setMaintenanceFrequencySeconds(int newVal) {
   }

   public ReserveReleaseInterceptor getReserveReleaseInterceptor() {
      return null;
   }

   public void resetStatistics() {
      this.numLeaked.set(0);
      this.group.resetStatistics();
   }

   public void start(Object initInfo) throws ResourceException {
      this.start(initInfo, false);
   }

   public void start(Object object, boolean isMemberDS) throws ResourceException {
      synchronized(this) {
         switch (this.state) {
            case 100:
               this.state = 102;
            case 101:
            case 102:
            default:
         }
      }
   }

   public void resume() throws ResourceException {
      synchronized(this) {
         switch (this.state) {
            case 100:
               throw new ResourceException("Unable to resume " + this.getName() + ". state=" + this.getState());
            case 102:
               this.state = 101;
            case 101:
            default:
         }
      }
   }

   public void suspend(boolean shuttingDown) throws ResourceException {
      this.internalSuspend(false, 0);
   }

   public void forceSuspend(boolean shuttingDown) throws ResourceException {
      this.internalSuspend(true, 0);
   }

   private void internalSuspend(boolean force, int waitSeconds) throws ResourceException {
      synchronized(this) {
         if (this.state == 100 || this.state == 102) {
            return;
         }

         this.suspending = true;
      }

      if (this.getPDBName() != null) {
         LabelingConnectionInfo pri = new LabelingConnectionInfo(this.getPDBName(), this.getPDBServiceName(), (Properties)null);
         if (force) {
            this.destroyMatchingAvailableAndReservedConnections(pri);
         } else {
            this.repurposeMatchingAvailableAndReservedConnections(pri);
            this.waitForNoReservedMatching(pri, waitSeconds);
         }
      }

      synchronized(this) {
         this.state = 102;
         this.suspending = false;
      }
   }

   public void shutdown() throws ResourceException {
      synchronized(this) {
         if (this.state != 100) {
            if (this.state != 101 && this.state != 104) {
               this.state = 100;
            } else {
               throw new ResourceException("pool " + this.getName() + " in " + this.getState() + " state");
            }
         }
      }
   }

   public void startExternal() throws ResourceException {
      this.start((Object)null);
      this.resume();
   }

   public void forceSuspendExternal() throws ResourceException {
      this.forceSuspend(false);
   }

   public void resumeExternal() throws ResourceException {
      this.resume();
   }

   public void forceShutdownExternal() throws ResourceException {
      this.forceSuspend(true);
      this.shutdown();
   }

   public void shutdownExternal(int operationSecs) throws ResourceException {
      this.internalSuspend(false, operationSecs);
      this.shutdown();
   }

   public void suspendExternal(int operationSecs) throws ResourceException {
      this.internalSuspend(false, operationSecs);
   }

   public String getName() {
      return this.dsBean.getName();
   }

   public void setJDBCDataSource(JDBCDataSourceBean dsBean) {
      this.dsBean = dsBean;
   }

   public JDBCDataSourceBean getJDBCDataSource() {
      return this.dsBean;
   }

   public ClassLoader getClassLoader() {
      return null;
   }

   public Properties getProperties() throws ResourceException {
      return null;
   }

   public boolean areConnsBeingTested() {
      return this.sharedPool.areConnsBeingTested();
   }

   public String getDriverVersion() {
      return this.sharedPool.getDriverVersion();
   }

   public boolean isIdentityBasedConnectionPoolingEnabled() {
      return this.sharedPool.isIdentityBasedConnectionPoolingEnabled();
   }

   public int getInactiveSeconds() {
      return this.sharedPool.getInactiveSeconds();
   }

   public JDBCResourceFactory getResourceFactory() {
      return this.sharedPool.getResourceFactory();
   }

   public void zeroResetFailCount() {
   }

   public boolean isCredentialMappingEnabled() {
      return this.sharedPool.isCredentialMappingEnabled();
   }

   public Vector getDBMSIdentity(AuthenticatedSubject user) {
      throw new UnsupportedOperationException();
   }

   public Object setDBMSIdentity(Object vendorObject, Object id) throws Throwable {
      throw new UnsupportedOperationException();
   }

   public void clearDBMSIdentity(Object vendorObject, Object id, Object initId) throws Throwable {
      throw new UnsupportedOperationException();
   }

   public ResourcePoolProfiler getProfiler() {
      return this.sharedPool.getProfiler();
   }

   public boolean removeResource(ConnectionEnv cc) {
      return this.sharedPool.removeResource(cc);
   }

   public boolean isOracleOptimizeUtf8Conversion() {
      return this.sharedPool.isOracleOptimizeUtf8Conversion();
   }

   public boolean isWrapTypes() {
      return this.sharedPool.isWrapTypes();
   }

   public void removeConnection(ConnectionEnv cc) {
      this.sharedPool.removeConnection(cc);
   }

   public boolean isEnabled() {
      return this.group != null ? this.group.isEnabled() : false;
   }

   public boolean isWrapJdbc() {
      return this.sharedPool.isWrapJdbc();
   }

   public void connectionCallbacks(ConnectionEnv cc) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public OracleHelper getOracleHelper() {
      throw new UnsupportedOperationException();
   }

   public int getReplayInitiationTimeout() {
      return this.sharedPool.getReplayInitiationTimeout();
   }

   public boolean isLocalValidateOnly() {
      return this.sharedPool.isLocalValidateOnly();
   }

   public ConnectionEnv getCachedPooledResource(Connection physical) {
      throw new UnsupportedOperationException();
   }

   public ConnectionEnv putCachedPooledResource(Connection physical, ConnectionEnv pooledResource) {
      throw new UnsupportedOperationException();
   }

   public ConnectionEnv removeCachedPooledResource(Connection physical) {
      throw new UnsupportedOperationException();
   }

   public int getOracleVersion() {
      return this.sharedPool.getOracleVersion();
   }

   public boolean isSharedPool() {
      return false;
   }

   public boolean isSharingPool() {
      return true;
   }

   public ResourcePoolGroup getGroup(String category, String name) {
      return this.sharedPool.getGroup(category, name);
   }

   public ResourcePoolGroup getOrCreateGroup(String category, String name) {
      return this.sharedPool.getOrCreateGroup(category, name);
   }

   protected void checkStateForReserve() throws ResourceException {
      synchronized(this) {
         if (this.state != 101 || this.suspending) {
            throw new ResourceException("sharing pool " + this.getName() + " is " + (this.suspending ? "Suspending" : this.getState()));
         }
      }
   }

   public ConnectionEnv reserve(AuthenticatedSubject user, int waitSeconds, Properties requestedLabels, String username, String password) throws ResourceException {
      this.checkStateForReserve();
      SwitchingContextManager.getInstance().push(this.getSwitchingContext());

      ConnectionEnv var6;
      try {
         var6 = this.sharedPool.reserve(user, waitSeconds, requestedLabels, username, password);
      } finally {
         SwitchingContextManager.getInstance().pop();
      }

      return var6;
   }

   public ConnectionEnv reserve(AuthenticatedSubject subject, int waitSecs) throws ResourceException {
      this.checkStateForReserve();
      SwitchingContextManager.getInstance().push(this.getSwitchingContext());

      ConnectionEnv var3;
      try {
         var3 = this.sharedPool.reserve(subject, waitSecs);
      } finally {
         SwitchingContextManager.getInstance().pop();
      }

      return var3;
   }

   public ConnectionEnv reserveInternal(int defaultWait) throws ResourceException {
      SwitchingContextManager.getInstance().push(this.getSwitchingContext());

      ConnectionEnv var2;
      try {
         var2 = this.sharedPool.reserveInternal(defaultWait);
      } finally {
         SwitchingContextManager.getInstance().pop();
      }

      return var2;
   }

   public void release(ConnectionEnv cc) throws ResourceException {
      this.sharedPool.release(cc);
   }

   public int incrementSharedPoolReferenceCounter() {
      throw new UnsupportedOperationException();
   }

   public int decrementSharedPoolReferenceCounter() {
      throw new UnsupportedOperationException();
   }

   public void setDataSource(DataSource ds) {
      this.jtaDataSource = ds;
   }

   public DataSource getJTADataSource() {
      return this.jtaDataSource;
   }

   public boolean isRemoveInfectedConnectionEnabled() {
      return this.sharedPool.isRemoveInfectedConnectionEnabled();
   }

   public boolean isCreateConnectionInline() {
      return false;
   }

   public boolean getTestOnReserve() {
      return this.sharedPool.getTestOnReserve();
   }

   public int getTestSeconds() {
      return this.sharedPool.getTestSeconds();
   }

   public int getXARetryDurationSeconds() {
      return this.sharedPool.getXARetryDurationSeconds();
   }

   public ConnectionLabelingCallback getLabelingCallback() {
      return null;
   }

   public int getConnectionHarvestTriggerCount() {
      return this.sharedPool.getConnectionHarvestTriggerCount();
   }

   public void setLabelingCallback(ConnectionLabelingCallback cbk) throws SQLException {
   }

   public Object getInitializationCallback() {
      return null;
   }

   public void setInitializationCallback(ConnectionInitializationCallback cbk) throws SQLException {
      throw new UnsupportedOperationException();
   }

   public boolean isNativeXA() {
      return this.sharedPool.isNativeXA();
   }

   public String getURL() {
      return this.sharedPool.getURL();
   }

   public boolean isMemberDS() {
      return this.sharedPool.isMemberDS();
   }

   public void incrementLeakedConnectionCount() {
      this.incrementNumLeaked();
   }

   public DriverInterceptor getDriverInterceptor() {
      return null;
   }

   public long getPrepStmtCacheAccessCount() {
      return this.sharedPool.getPrepStmtCacheAccessCount();
   }

   public long getPrepStmtCacheAddCount() {
      return this.sharedPool.getPrepStmtCacheAddCount();
   }

   public long getPrepStmtCacheDeleteCount() {
      return this.sharedPool.getPrepStmtCacheDeleteCount();
   }

   public int getPrepStmtCacheCurrentSize() {
      return this.sharedPool.getPrepStmtCacheCurrentSize();
   }

   public int getPrepStmtCacheHitCount() {
      return this.sharedPool.getPrepStmtCacheHitCount();
   }

   public int getPrepStmtCacheMissCount() {
      return this.sharedPool.getPrepStmtCacheMissCount();
   }

   public void reset() throws ResourceException {
      throw new UnsupportedOperationException();
   }

   public boolean poolExists(String poolName) throws ResourceException {
      return this.sharedPool.poolExists(poolName);
   }

   public void clearStatementCache() throws Exception {
      throw new UnsupportedOperationException();
   }

   public void dumpPool(PrintWriter object) {
   }

   public List getGroups(String category) {
      return this.sharedPool.getGroups(category);
   }

   public ReplayStatisticsSnapshot getReplayStatisticsSnapshot() {
      return null;
   }

   public boolean getReplayStatistics() {
      return false;
   }

   public boolean clearReplayStatistics() {
      return false;
   }

   public void incrementClosedConnectionReplayStatistics(ConnectionEnv ce) {
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public String getAppName() {
      return this.appName;
   }

   public String getCompName() {
      return this.compName;
   }

   public String getPartitionName() {
      return JDBCUtil.getPartitionName(this.dsBean);
   }

   public void setStatementCacheSize(int statementCacheSize) {
   }

   public void setTestTableName(String testTableName) {
   }

   public void setProfileType(int profileType) {
   }

   public void setSecondsToTrustAnIdlePoolConnection(int secondsToTrustAnIdlePoolConnection) {
   }

   public void setConnectionHarvestMaxCount(int connectionHarvestMaxCount) {
   }

   public void setConnectionHarvestTriggerCount(int connectionHarvestTriggerCount) throws SQLException {
   }

   public void setProfileConnectionLeakTimeoutSeconds(int profileConnectionLeakTimeoutSeconds) {
   }

   public void setOracleOptimizeUtf8Conversion(boolean oracleOptimizeUtf8Conversion) {
   }

   public void setReplayInitiationTimeout(int replayInitiationTimeout) {
   }

   public String toString() {
      return "SharingConnectionPool(" + this.getName() + ")";
   }

   public List getAvailableMatching(PooledResourceInfo pri) {
      return this.sharedPool.getAvailableMatching(pri);
   }

   public List getReservedMatching(PooledResourceInfo pri) {
      return this.sharedPool.getReservedMatching(pri);
   }

   public void repurposeMatchingAvailableAndReservedConnections(PooledResourceInfo pri) throws ResourceException {
      this.sharedPool.repurposeMatchingAvailableAndReservedConnections(pri);
   }

   public void destroyMatchingAvailableAndReservedConnections(PooledResourceInfo pri) throws ResourceException {
      this.sharedPool.destroyMatchingAvailableAndReservedConnections(pri);
   }

   public void waitForNoReservedMatching(PooledResourceInfo pri, int waitSeconds) throws ResourceException {
      this.sharedPool.waitForNoReservedMatching(pri, waitSeconds);
   }

   public void updateCredential(String p) {
   }
}
