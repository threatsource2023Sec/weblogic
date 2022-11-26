package weblogic.management.configuration;

public interface JTAClusterMBean extends JTAMBean {
   int getTimeoutSeconds();

   int getAbandonTimeoutSeconds();

   int getCompletionTimeoutSeconds();

   boolean getForgetHeuristics();

   int getBeforeCompletionIterationLimit();

   int getMaxTransactions();

   int getMaxUniqueNameStatistics();

   int getMaxResourceRequestsOnServer();

   long getMaxXACallMillis();

   long getMaxResourceUnavailableMillis();

   long getRecoveryThresholdMillis();

   int getMigrationCheckpointIntervalSeconds();

   long getMaxTransactionsHealthIntervalMillis();

   int getPurgeResourceFromCheckpointIntervalSeconds();

   int getCheckpointIntervalSeconds();

   long getSerializeEnlistmentsGCIntervalMillis();

   boolean getParallelXAEnabled();

   String getParallelXADispatchPolicy();

   int getUnregisterResourceGracePeriod();

   String getSecurityInteropMode();

   boolean isTwoPhaseEnabled();

   boolean isClusterwideRecoveryEnabled();

   boolean isTightlyCoupledTransactionsEnabled();

   String[] getDeterminers();

   boolean isTLOGWriteWhenDeterminerExistsEnabled();

   int getShutdownGracePeriod();

   int getMaxRetrySecondsBeforeDeterminerFail();

   /** @deprecated */
   @Deprecated
   String getRecoverySiteName();

   /** @deprecated */
   @Deprecated
   int getCrossDomainRecoveryRetryInterval();

   /** @deprecated */
   @Deprecated
   int getCrossSiteRecoveryRetryInterval();

   /** @deprecated */
   @Deprecated
   int getCrossSiteRecoveryLeaseExpiration();

   /** @deprecated */
   @Deprecated
   int getCrossSiteRecoveryLeaseUpdate();
}
