package weblogic.jdbc.common.internal;

import java.util.Properties;
import weblogic.common.ResourceException;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;

public interface ConnectionPoolConfig {
   Properties getPoolProperties() throws ResourceException;

   Properties getDriverProperties() throws ResourceException;

   ConnectionInfo getDefaultConnectionInfo();

   String getDriver();

   String getURL();

   boolean isPinnedToThread();

   boolean isCreateConnectionInline();

   boolean isRemoveInfectedConnectionEnabled();

   void setCredentialMappingEnabled(boolean var1);

   boolean isOnePinnedConnectionOnly();

   int getProfileType();

   boolean isIdentityBasedConnectionPoolingEnabled();

   boolean isCredentialMappingEnabled();

   boolean isNativeXA();

   int getXaRetryDurationSeconds();

   boolean isOracleOptimizeUtf8Conversion();

   boolean isWrapTypes();

   void setJDBCDataSourceBean(JDBCDataSourceBean var1);

   boolean isWrapJdbc();

   int getConnectionLabelingHighCost();

   boolean isConnectionLabelingHighCostSet();

   int getHighCostConnectionReuseThreshold();

   int getSecurityCacheTimeoutSeconds();

   int getHarvestingFrequencySeconds();

   boolean isUCPDataSource();

   boolean isProxyDataSource();

   int getProfileConnectionLeakTimeoutSeconds();

   boolean isSharedPool();

   boolean isStartupCritical();

   boolean isStartupRetryEnabled();

   int getStartupRetryCount();

   int getStartupRetryDelaySeconds();

   boolean isContinueMakeResourceAttemptsAfterFailure();

   boolean isInvokeBeginEndRequest();
}
