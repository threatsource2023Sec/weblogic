package weblogic.jdbc.common.internal;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import oracle.ucp.ConnectionLabelingCallback;
import oracle.ucp.jdbc.ConnectionInitializationCallback;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ResourcePool;
import weblogic.common.resourcepool.ResourcePoolGroup;
import weblogic.common.resourcepool.ResourcePoolProfiler;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.jdbc.extensions.DriverInterceptor;
import weblogic.jdbc.jta.DataSource;
import weblogic.security.acl.internal.AuthenticatedSubject;

public interface JDBCConnectionPool extends ResourcePool {
   String getName();

   JDBCDataSourceBean getJDBCDataSource();

   ClassLoader getClassLoader();

   Properties getProperties() throws ResourceException;

   boolean areConnsBeingTested();

   String getDriverVersion();

   boolean isIdentityBasedConnectionPoolingEnabled();

   int getInactiveSeconds();

   JDBCResourceFactory getResourceFactory();

   void zeroResetFailCount();

   boolean isCredentialMappingEnabled();

   Vector getDBMSIdentity(AuthenticatedSubject var1);

   Object setDBMSIdentity(Object var1, Object var2) throws Throwable;

   void clearDBMSIdentity(Object var1, Object var2, Object var3) throws Throwable;

   ResourcePoolProfiler getProfiler();

   boolean removeResource(ConnectionEnv var1);

   boolean isOracleOptimizeUtf8Conversion();

   boolean isWrapTypes();

   void removeConnection(ConnectionEnv var1);

   boolean isEnabled();

   boolean isWrapJdbc();

   void connectionCallbacks(ConnectionEnv var1) throws SQLException;

   OracleHelper getOracleHelper();

   int getReplayInitiationTimeout();

   boolean isLocalValidateOnly();

   ConnectionEnv getCachedPooledResource(Connection var1);

   ConnectionEnv putCachedPooledResource(Connection var1, ConnectionEnv var2);

   ConnectionEnv removeCachedPooledResource(Connection var1);

   int getOracleVersion();

   int getStateAsInt();

   boolean isSharedPool();

   boolean isSharingPool();

   ResourcePoolGroup getGroup(String var1, String var2);

   ResourcePoolGroup getOrCreateGroup(String var1, String var2);

   ConnectionEnv reserve(AuthenticatedSubject var1, int var2, Properties var3, String var4, String var5) throws ResourceException;

   ConnectionEnv reserve(AuthenticatedSubject var1, int var2) throws ResourceException;

   ConnectionEnv reserveInternal(int var1) throws ResourceException;

   void release(ConnectionEnv var1) throws ResourceException;

   int incrementSharedPoolReferenceCounter();

   void start(Object var1, boolean var2) throws ResourceException;

   int decrementSharedPoolReferenceCounter();

   void setDataSource(DataSource var1);

   DataSource getJTADataSource();

   boolean isRemoveInfectedConnectionEnabled();

   boolean isCreateConnectionInline();

   boolean getTestOnReserve();

   int getTestSeconds();

   int getXARetryDurationSeconds();

   ConnectionLabelingCallback getLabelingCallback();

   int getConnectionHarvestTriggerCount();

   void setLabelingCallback(ConnectionLabelingCallback var1) throws SQLException;

   Object getInitializationCallback();

   void setInitializationCallback(ConnectionInitializationCallback var1) throws SQLException;

   boolean isNativeXA();

   String getURL();

   boolean isMemberDS();

   void incrementLeakedConnectionCount();

   DriverInterceptor getDriverInterceptor();

   String getDerivedState();

   long getPrepStmtCacheAccessCount();

   long getPrepStmtCacheAddCount();

   long getPrepStmtCacheDeleteCount();

   int getPrepStmtCacheCurrentSize();

   int getPrepStmtCacheHitCount();

   int getPrepStmtCacheMissCount();

   void reset() throws ResourceException;

   void forceSuspendExternal() throws ResourceException;

   void resumeExternal() throws ResourceException;

   void forceShutdownExternal() throws ResourceException;

   void shutdownExternal(int var1) throws ResourceException;

   void suspendExternal(int var1) throws ResourceException;

   boolean poolExists(String var1) throws ResourceException;

   void clearStatementCache() throws Exception;

   void dumpPool(PrintWriter var1);

   void startExternal() throws ResourceException;

   List getGroups(String var1);

   ReplayStatisticsSnapshot getReplayStatisticsSnapshot();

   void incrementClosedConnectionReplayStatistics(ConnectionEnv var1);

   boolean getReplayStatistics();

   boolean clearReplayStatistics();

   String getModuleName();

   String getAppName();

   String getCompName();

   String getPartitionName();

   void setJDBCDataSource(JDBCDataSourceBean var1);

   void setStatementCacheSize(int var1);

   void setTestTableName(String var1);

   void setProfileType(int var1);

   void setSecondsToTrustAnIdlePoolConnection(int var1);

   void setConnectionHarvestMaxCount(int var1) throws SQLException;

   void setConnectionHarvestTriggerCount(int var1) throws SQLException;

   void setProfileConnectionLeakTimeoutSeconds(int var1);

   void setOracleOptimizeUtf8Conversion(boolean var1);

   void setReplayInitiationTimeout(int var1) throws SQLException;

   List getAvailableMatching(PooledResourceInfo var1);

   List getReservedMatching(PooledResourceInfo var1);

   void repurposeMatchingAvailableAndReservedConnections(PooledResourceInfo var1) throws ResourceException;

   void destroyMatchingAvailableAndReservedConnections(PooledResourceInfo var1) throws ResourceException;

   void waitForNoReservedMatching(PooledResourceInfo var1, int var2) throws ResourceException;

   long getRepurposeCount();

   long getFailedRepurposeCount();

   SwitchingContext getRootSwitchingContext();

   void setRootSwitchingContext(SwitchingContext var1);

   long getResolvedAsCommittedTotalCount();

   void incrementResolvedAsCommittedTotalCount();

   long getResolvedAsNotCommittedTotalCount();

   void incrementResolvedAsNotCommittedTotalCount();

   long getUnresolvedTotalCount();

   void incrementUnresolvedTotalCount();

   long getCommitOutcomeRetryTotalCount();

   void incrementCommitOutcomeRetryTotalCount();

   void updateCredential(String var1) throws SQLException;
}
