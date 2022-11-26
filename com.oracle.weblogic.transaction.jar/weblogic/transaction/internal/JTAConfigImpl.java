package weblogic.transaction.internal;

import java.beans.PropertyChangeListener;
import java.security.AccessController;
import java.util.HashSet;
import java.util.Set;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JTAMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

class JTAConfigImpl implements JTAConfig {
   static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private DomainMBean domainMBean;
   private JTAMBean jtaMBean;

   JTAConfigImpl(DomainMBean domain, JTAMBean jta) {
      this.domainMBean = domain;
      this.jtaMBean = jta;
   }

   public int getTimeoutSeconds() {
      return this.jtaMBean.getTimeoutSeconds();
   }

   public int getAbandonTimeoutSeconds() {
      return this.jtaMBean.getAbandonTimeoutSeconds();
   }

   public int getCompletionTimeoutSeconds() {
      return this.jtaMBean.getCompletionTimeoutSeconds();
   }

   public boolean isTwoPhaseEnabled() {
      return this.jtaMBean.isTwoPhaseEnabled();
   }

   public boolean getForgetHeuristics() {
      return this.jtaMBean.getForgetHeuristics();
   }

   public int getBeforeCompletionIterationLimit() {
      return this.jtaMBean.getBeforeCompletionIterationLimit();
   }

   public int getMaxTransactions() {
      return this.jtaMBean.getMaxTransactions();
   }

   public int getMaxUniqueNameStatistics() {
      return this.jtaMBean.getMaxUniqueNameStatistics();
   }

   public int getMaxResourceRequestsOnServer() {
      return this.jtaMBean.getMaxResourceRequestsOnServer();
   }

   public long getMaxXACallMillis() {
      return this.jtaMBean.getMaxXACallMillis();
   }

   public long getMaxResourceUnavailableMillis() {
      return this.jtaMBean.getMaxResourceUnavailableMillis();
   }

   public int getMigrationCheckpointIntervalSeconds() {
      return this.jtaMBean.getMigrationCheckpointIntervalSeconds();
   }

   public long getMaxTransactionsHealthIntervalMillis() {
      return this.jtaMBean.getMaxTransactionsHealthIntervalMillis();
   }

   public int getPurgeResourceFromCheckpointIntervalSeconds() {
      return this.jtaMBean.getPurgeResourceFromCheckpointIntervalSeconds();
   }

   public int getCheckpointIntervalSeconds() {
      return this.jtaMBean.getCheckpointIntervalSeconds();
   }

   public long getSerializeEnlistmentsGCIntervalMillis() {
      return this.jtaMBean.getSerializeEnlistmentsGCIntervalMillis();
   }

   public boolean isParallelXAEnabled() {
      return this.jtaMBean.getParallelXAEnabled();
   }

   public String getParallelXADispatchPolicy() {
      return this.jtaMBean.getParallelXADispatchPolicy();
   }

   public int getUnregisterResourceGracePeriod() {
      return this.jtaMBean.getUnregisterResourceGracePeriod();
   }

   public String getSecurityInteropMode() {
      return this.jtaMBean.getSecurityInteropMode();
   }

   public boolean isClusterwideRecoveryEnabled() {
      return this.jtaMBean.isClusterwideRecoveryEnabled();
   }

   public boolean isTightlyCoupledTransactionsEnabled() {
      return this.jtaMBean.isTightlyCoupledTransactionsEnabled();
   }

   public String[] getDeterminers() {
      return this.jtaMBean.getDeterminers();
   }

   public boolean isTLOGWriteWhenDeterminerExistsEnabled() {
      return this.jtaMBean.isTLOGWriteWhenDeterminerExistsEnabled();
   }

   public int getShutdownGracePeriod() {
      return this.jtaMBean.getShutdownGracePeriod();
   }

   public int getMaxRetrySecondsBeforeDeterminerFail() {
      return this.jtaMBean.getMaxRetrySecondsBeforeDeterminerFail();
   }

   public int getCrossDomainRecoveryRetryInterval() {
      return this.jtaMBean.getCrossDomainRecoveryRetryInterval();
   }

   public int getCrossSiteRecoveryRetryInterval() {
      return this.jtaMBean.getCrossSiteRecoveryRetryInterval();
   }

   public int getCrossSiteRecoveryLeaseExpiration() {
      return this.jtaMBean.getCrossSiteRecoveryLeaseExpiration();
   }

   public int getCrossSiteRecoveryLeaseUpdate() {
      return this.jtaMBean.getCrossSiteRecoveryLeaseUpdate();
   }

   public String getSiteName() {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      if (runtimeAccess == null) {
         return null;
      } else {
         DomainMBean domainMBean = runtimeAccess.getDomain();
         if (domainMBean == null) {
            return null;
         } else {
            String siteName = domainMBean.getSiteName();
            return siteName != null && siteName.length() != 0 ? siteName : null;
         }
      }
   }

   public String getRecoverySiteName() {
      return this.jtaMBean.getRecoverySiteName();
   }

   public void addPropertyChangeListener(PropertyChangeListener pcl) {
      this.jtaMBean.addPropertyChangeListener(pcl);
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      if (runtimeAccess != null) {
         DomainMBean domainMBean = runtimeAccess.getDomain();
         if (domainMBean != null) {
            domainMBean.addPropertyChangeListener(pcl);
         }
      }
   }

   public TransactionLogJDBCStoreConfig getTransactionLogJDBCStoreConfig() {
      return new TransactionLogJDBCStoreConfigImpl();
   }

   public JTAClusterConfig getJTAClusterConfig() {
      return new JTAClusterConfigImpl();
   }

   public String[] getSiteInfo() {
      String[] siteInfo = new String[]{this.getSiteName(), this.getRecoverySiteName(), Integer.toString(this.getCrossSiteRecoveryRetryInterval()), Integer.toString(this.getCrossSiteRecoveryLeaseExpiration()), Integer.toString(this.getCrossSiteRecoveryLeaseUpdate())};
      return siteInfo;
   }

   public boolean isDBPassiveMode() {
      return this.domainMBean.isDBPassiveMode();
   }

   public int getDBPassiveModeGracePeriodSeconds() {
      return this.domainMBean.getDBPassiveModeGracePeriodSeconds();
   }

   public Set getUsePublicAddressesForRemoteDomains() {
      String[] domains = null;
      String commaSeparatedList = System.getProperty("weblogic.transaction.usePublicAddressesForRemoteDomains");
      if (commaSeparatedList != null) {
         domains = commaSeparatedList.split(",");
      } else {
         domains = this.jtaMBean.getUsePublicAddressesForRemoteDomains();
      }

      if (domains == null) {
         return null;
      } else {
         Set domainSet = new HashSet();
         String[] var4 = domains;
         int var5 = domains.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String domain = var4[var6];
            domainSet.add(domain.trim());
         }

         return domainSet;
      }
   }

   public Set getUseNonSecureAddressesForDomains() {
      String[] domains = null;
      String commaSeparatedList = System.getProperty("weblogic.transaction.useNonSecureAddressesForDomains");
      if (commaSeparatedList != null) {
         domains = commaSeparatedList.split(",");
      } else {
         domains = this.jtaMBean.getUseNonSecureAddressesForDomains();
      }

      if (domains == null) {
         return null;
      } else {
         Set domainSet = new HashSet();
         String[] var4 = domains;
         int var5 = domains.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String domain = var4[var6];
            domainSet.add(domain.trim());
         }

         return domainSet;
      }
   }
}
