package weblogic.transaction.internal;

import java.beans.PropertyChangeListener;
import java.util.Set;

public interface JTAConfig {
   int getTimeoutSeconds();

   int getAbandonTimeoutSeconds();

   boolean getForgetHeuristics();

   int getBeforeCompletionIterationLimit();

   int getMaxTransactions();

   int getMaxUniqueNameStatistics();

   int getMaxResourceRequestsOnServer();

   long getMaxXACallMillis();

   long getMaxResourceUnavailableMillis();

   int getMigrationCheckpointIntervalSeconds();

   long getMaxTransactionsHealthIntervalMillis();

   int getPurgeResourceFromCheckpointIntervalSeconds();

   int getCheckpointIntervalSeconds();

   long getSerializeEnlistmentsGCIntervalMillis();

   boolean isParallelXAEnabled();

   String getParallelXADispatchPolicy();

   int getUnregisterResourceGracePeriod();

   String getSecurityInteropMode();

   void addPropertyChangeListener(PropertyChangeListener var1);

   int getCompletionTimeoutSeconds();

   boolean isTwoPhaseEnabled();

   boolean isClusterwideRecoveryEnabled();

   boolean isTightlyCoupledTransactionsEnabled();

   String[] getDeterminers();

   boolean isTLOGWriteWhenDeterminerExistsEnabled();

   int getShutdownGracePeriod();

   String getSiteName();

   String getRecoverySiteName();

   int getMaxRetrySecondsBeforeDeterminerFail();

   int getCrossDomainRecoveryRetryInterval();

   int getCrossSiteRecoveryRetryInterval();

   int getCrossSiteRecoveryLeaseExpiration();

   int getCrossSiteRecoveryLeaseUpdate();

   TransactionLogJDBCStoreConfig getTransactionLogJDBCStoreConfig();

   JTAClusterConfig getJTAClusterConfig();

   String[] getSiteInfo();

   boolean isDBPassiveMode();

   int getDBPassiveModeGracePeriodSeconds();

   Set getUsePublicAddressesForRemoteDomains();

   Set getUseNonSecureAddressesForDomains();
}
