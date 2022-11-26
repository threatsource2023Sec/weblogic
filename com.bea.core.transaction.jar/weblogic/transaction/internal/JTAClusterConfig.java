package weblogic.transaction.internal;

import java.beans.PropertyChangeListener;

public interface JTAClusterConfig {
   boolean isMemberOfCluster();

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

   void addPropertyChangeListener(PropertyChangeListener var1);

   String[] getSiteInfo();
}
