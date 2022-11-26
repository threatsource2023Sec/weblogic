package weblogic.transaction.internal;

import java.beans.PropertyChangeListener;
import java.security.AccessController;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.TransactionLogJDBCStoreMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

class TransactionLogJDBCStoreConfigImpl implements TransactionLogJDBCStoreConfig {
   private TransactionLogJDBCStoreMBean tjdbcMBean;
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   TransactionLogJDBCStoreConfigImpl() {
      this.tjdbcMBean = ManagementService.getRuntimeAccess(kernelID).getServer().getTransactionLogJDBCStore();
   }

   public String getJdbcTLogPrefixName() {
      return this.tjdbcMBean == null ? null : this.tjdbcMBean.getPrefixName();
   }

   public boolean isJdbcTLogEnabled() {
      return this.tjdbcMBean == null ? false : this.tjdbcMBean.isEnabled();
   }

   public int getJdbcTLogMaxRetrySecondsBeforeTLOGFail() {
      return this.tjdbcMBean == null ? 0 : this.tjdbcMBean.getMaxRetrySecondsBeforeTLOGFail();
   }

   public int getJdbcTLogMaxRetrySecondsBeforeTXException() {
      return this.tjdbcMBean == null ? 0 : this.tjdbcMBean.getMaxRetrySecondsBeforeTXException();
   }

   public int getJdbcTLogRetryIntervalSeconds() {
      return this.tjdbcMBean == null ? 0 : this.tjdbcMBean.getRetryIntervalSeconds();
   }

   public String getJdbcTLogDataSource() {
      if (this.tjdbcMBean == null) {
         return null;
      } else {
         JDBCSystemResourceMBean jdbcmb = this.tjdbcMBean.getDataSource();
         return jdbcmb == null ? null : jdbcmb.getJDBCResource().getName();
      }
   }

   public void addPropertyChangeListener(PropertyChangeListener pcl) {
      if (this.tjdbcMBean != null) {
         this.tjdbcMBean.addPropertyChangeListener(pcl);
      }
   }
}
