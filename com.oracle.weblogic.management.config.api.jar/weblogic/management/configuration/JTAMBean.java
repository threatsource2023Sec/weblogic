package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

public interface JTAMBean extends ConfigurationMBean {
   String SSLNOTREQUIRED = "SSLNotRequired";
   String SSLREQUIRED = "SSLRequired";
   String CLIENTCERTREQUIRED = "ClientCertRequired";
   String JMS_DETERMINER_RESOURCE_INTERNAL_NAME = "WebLogic_JMS";

   int getTimeoutSeconds();

   void setTimeoutSeconds(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getAbandonTimeoutSeconds();

   void setAbandonTimeoutSeconds(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getCompletionTimeoutSeconds();

   void setCompletionTimeoutSeconds(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean getForgetHeuristics();

   void setForgetHeuristics(boolean var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getBeforeCompletionIterationLimit();

   void setBeforeCompletionIterationLimit(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getMaxTransactions();

   void setMaxTransactions(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getMaxUniqueNameStatistics();

   void setMaxUniqueNameStatistics(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getMaxResourceRequestsOnServer();

   void setMaxResourceRequestsOnServer(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   long getMaxXACallMillis();

   void setMaxXACallMillis(long var1) throws InvalidAttributeValueException, DistributedManagementException;

   long getMaxResourceUnavailableMillis();

   void setMaxResourceUnavailableMillis(long var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   long getRecoveryThresholdMillis();

   /** @deprecated */
   @Deprecated
   void setRecoveryThresholdMillis(long var1);

   int getMigrationCheckpointIntervalSeconds();

   void setMigrationCheckpointIntervalSeconds(int var1);

   long getMaxTransactionsHealthIntervalMillis();

   void setMaxTransactionsHealthIntervalMillis(long var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getPurgeResourceFromCheckpointIntervalSeconds();

   void setPurgeResourceFromCheckpointIntervalSeconds(int var1);

   int getCheckpointIntervalSeconds();

   void setCheckpointIntervalSeconds(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   long getSerializeEnlistmentsGCIntervalMillis();

   void setSerializeEnlistmentsGCIntervalMillis(long var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean getParallelXAEnabled();

   void setParallelXAEnabled(boolean var1) throws InvalidAttributeValueException, DistributedManagementException;

   String getParallelXADispatchPolicy();

   void setParallelXADispatchPolicy(String var1);

   int getUnregisterResourceGracePeriod();

   void setUnregisterResourceGracePeriod(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   String getSecurityInteropMode();

   void setSecurityInteropMode(String var1) throws InvalidAttributeValueException, DistributedManagementException;

   String getWSATTransportSecurityMode();

   void setWSATTransportSecurityMode(String var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean isWSATIssuedTokenEnabled();

   void setWSATIssuedTokenEnabled(boolean var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean isTwoPhaseEnabled();

   void setTwoPhaseEnabled(boolean var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean isClusterwideRecoveryEnabled();

   void setClusterwideRecoveryEnabled(boolean var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean isTightlyCoupledTransactionsEnabled();

   void setTightlyCoupledTransactionsEnabled(boolean var1) throws InvalidAttributeValueException, DistributedManagementException;

   String[] getDeterminers();

   DeterminerCandidateResourceInfoVBean[] getDeterminerCandidateResourceInfoList();

   void setDeterminers(String[] var1) throws InvalidAttributeValueException;

   boolean isTLOGWriteWhenDeterminerExistsEnabled();

   void setTLOGWriteWhenDeterminerExistsEnabled(boolean var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getShutdownGracePeriod();

   void setShutdownGracePeriod(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   int getMaxRetrySecondsBeforeDeterminerFail();

   void setMaxRetrySecondsBeforeDeterminerFail(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   String getRecoverySiteName();

   /** @deprecated */
   @Deprecated
   void setRecoverySiteName(String var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   int getCrossDomainRecoveryRetryInterval();

   /** @deprecated */
   @Deprecated
   void setCrossDomainRecoveryRetryInterval(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   int getCrossSiteRecoveryRetryInterval();

   /** @deprecated */
   @Deprecated
   void setCrossSiteRecoveryRetryInterval(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   int getCrossSiteRecoveryLeaseExpiration();

   /** @deprecated */
   @Deprecated
   void setCrossSiteRecoveryLeaseExpiration(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   int getCrossSiteRecoveryLeaseUpdate();

   /** @deprecated */
   @Deprecated
   void setCrossSiteRecoveryLeaseUpdate(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   String[] getUsePublicAddressesForRemoteDomains();

   void setUsePublicAddressesForRemoteDomains(String[] var1) throws InvalidAttributeValueException;

   String[] getUseNonSecureAddressesForDomains();

   void setUseNonSecureAddressesForDomains(String[] var1) throws InvalidAttributeValueException;
}
