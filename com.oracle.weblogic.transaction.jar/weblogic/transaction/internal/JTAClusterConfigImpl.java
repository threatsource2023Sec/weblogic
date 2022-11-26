package weblogic.transaction.internal;

import java.beans.PropertyChangeListener;
import java.security.AccessController;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.JTAClusterMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

class JTAClusterConfigImpl implements JTAClusterConfig {
   private ClusterMBean clusterMBean;
   private JTAClusterMBean jTAClusterMBean;
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   JTAClusterConfigImpl() {
      this.clusterMBean = ManagementService.getRuntimeAccess(kernelID).getServer().getCluster();
      this.jTAClusterMBean = this.clusterMBean == null ? null : this.clusterMBean.getJTACluster();
   }

   public boolean isMemberOfCluster() {
      return this.jTAClusterMBean != null;
   }

   public int getTimeoutSeconds() {
      return this.jTAClusterMBean == null ? 30 : this.jTAClusterMBean.getTimeoutSeconds();
   }

   public int getAbandonTimeoutSeconds() {
      return this.jTAClusterMBean == null ? 86400 : this.jTAClusterMBean.getAbandonTimeoutSeconds();
   }

   public int getCompletionTimeoutSeconds() {
      return this.jTAClusterMBean == null ? 0 : this.jTAClusterMBean.getCompletionTimeoutSeconds();
   }

   public boolean isTwoPhaseEnabled() {
      return this.jTAClusterMBean == null ? true : this.jTAClusterMBean.isTwoPhaseEnabled();
   }

   public boolean getForgetHeuristics() {
      return this.jTAClusterMBean == null ? true : this.jTAClusterMBean.getForgetHeuristics();
   }

   public int getBeforeCompletionIterationLimit() {
      return this.jTAClusterMBean == null ? 10 : this.jTAClusterMBean.getBeforeCompletionIterationLimit();
   }

   public int getMaxTransactions() {
      return this.jTAClusterMBean == null ? 10000 : this.jTAClusterMBean.getMaxTransactions();
   }

   public int getMaxUniqueNameStatistics() {
      return this.jTAClusterMBean == null ? 1000 : this.jTAClusterMBean.getMaxUniqueNameStatistics();
   }

   public int getMaxResourceRequestsOnServer() {
      return this.jTAClusterMBean == null ? 50 : this.jTAClusterMBean.getMaxResourceRequestsOnServer();
   }

   public long getMaxXACallMillis() {
      return this.jTAClusterMBean == null ? 120000L : this.jTAClusterMBean.getMaxXACallMillis();
   }

   public long getMaxResourceUnavailableMillis() {
      return this.jTAClusterMBean == null ? 1800000L : this.jTAClusterMBean.getMaxResourceUnavailableMillis();
   }

   public int getMigrationCheckpointIntervalSeconds() {
      return this.jTAClusterMBean == null ? 60 : this.jTAClusterMBean.getMigrationCheckpointIntervalSeconds();
   }

   public long getMaxTransactionsHealthIntervalMillis() {
      return this.jTAClusterMBean == null ? 60000L : this.jTAClusterMBean.getMaxTransactionsHealthIntervalMillis();
   }

   public int getPurgeResourceFromCheckpointIntervalSeconds() {
      return this.jTAClusterMBean == null ? 86400 : this.jTAClusterMBean.getPurgeResourceFromCheckpointIntervalSeconds();
   }

   public int getCheckpointIntervalSeconds() {
      return this.jTAClusterMBean == null ? 300 : this.jTAClusterMBean.getCheckpointIntervalSeconds();
   }

   public long getSerializeEnlistmentsGCIntervalMillis() {
      return this.jTAClusterMBean == null ? 30000L : this.jTAClusterMBean.getSerializeEnlistmentsGCIntervalMillis();
   }

   public boolean isParallelXAEnabled() {
      return this.jTAClusterMBean == null ? true : this.jTAClusterMBean.getParallelXAEnabled();
   }

   public String getParallelXADispatchPolicy() {
      return this.jTAClusterMBean == null ? "" : this.jTAClusterMBean.getParallelXADispatchPolicy();
   }

   public int getUnregisterResourceGracePeriod() {
      return this.jTAClusterMBean == null ? 30 : this.jTAClusterMBean.getUnregisterResourceGracePeriod();
   }

   public String getSecurityInteropMode() {
      return this.jTAClusterMBean == null ? "default" : this.jTAClusterMBean.getSecurityInteropMode();
   }

   public boolean isClusterwideRecoveryEnabled() {
      return this.jTAClusterMBean == null ? true : this.jTAClusterMBean.isClusterwideRecoveryEnabled();
   }

   public boolean isTightlyCoupledTransactionsEnabled() {
      return this.jTAClusterMBean == null ? true : this.jTAClusterMBean.isTightlyCoupledTransactionsEnabled();
   }

   public boolean isTLOGWriteWhenDeterminerExistsEnabled() {
      return this.jTAClusterMBean == null ? true : this.jTAClusterMBean.isTLOGWriteWhenDeterminerExistsEnabled();
   }

   public int getShutdownGracePeriod() {
      return this.jTAClusterMBean == null ? 180 : this.jTAClusterMBean.getShutdownGracePeriod();
   }

   public String getSiteName() {
      String siteName = this.clusterMBean == null ? null : this.clusterMBean.getSiteName();
      if (siteName != null && siteName.length() == 0) {
         siteName = null;
      }

      return siteName;
   }

   public String getRecoverySiteName() {
      return this.jTAClusterMBean == null ? null : this.jTAClusterMBean.getRecoverySiteName();
   }

   public String[] getDeterminers() {
      return this.jTAClusterMBean == null ? new String[0] : this.jTAClusterMBean.getDeterminers();
   }

   public int getMaxRetrySecondsBeforeDeterminerFail() {
      return this.jTAClusterMBean == null ? 300 : this.jTAClusterMBean.getMaxRetrySecondsBeforeDeterminerFail();
   }

   public int getCrossDomainRecoveryRetryInterval() {
      return this.jTAClusterMBean == null ? 60 : this.jTAClusterMBean.getCrossDomainRecoveryRetryInterval();
   }

   public int getCrossSiteRecoveryRetryInterval() {
      return this.jTAClusterMBean == null ? 60 : this.jTAClusterMBean.getCrossSiteRecoveryRetryInterval();
   }

   public int getCrossSiteRecoveryLeaseExpiration() {
      return this.jTAClusterMBean == null ? 30 : this.jTAClusterMBean.getCrossSiteRecoveryLeaseExpiration();
   }

   public int getCrossSiteRecoveryLeaseUpdate() {
      return this.jTAClusterMBean == null ? 10 : this.jTAClusterMBean.getCrossSiteRecoveryLeaseUpdate();
   }

   public void addPropertyChangeListener(PropertyChangeListener pcl) {
      if (this.jTAClusterMBean != null) {
         this.jTAClusterMBean.addPropertyChangeListener(pcl);
      }
   }

   public String[] getSiteInfo() {
      String[] siteInfo = new String[]{this.getSiteName(), this.getRecoverySiteName(), Integer.toString(this.getCrossSiteRecoveryRetryInterval()), Integer.toString(this.getCrossSiteRecoveryLeaseExpiration()), Integer.toString(this.getCrossSiteRecoveryLeaseUpdate())};
      return siteInfo;
   }
}
