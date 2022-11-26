package weblogic.transaction.internal;

import java.sql.Date;

class PeerSiteRecoveryTakeoverLeaseMaintenance implements Runnable {
   PlatformHelperImpl platformHelper = null;
   private PeerSiteRecoveryJDBCWrappers.Connection connection;
   boolean isStillRunning = true;
   private PeerSiteRecoveryJDBCWrappers.PreparedStatement updateLeaseStmt;

   PeerSiteRecoveryTakeoverLeaseMaintenance(PlatformHelperImpl platformHelper) {
      this.platformHelper = platformHelper;
   }

   public void run() {
      do {
         String siteName = TransactionManagerImpl.getTransactionManager().getSiteName();
         if (siteName != null && !siteName.trim().equals("")) {
            if (this.platformHelper.peerSiteRecoveryCheck.recoverySiteDataSource == null) {
               this.platformHelper.peerSiteRecoveryCheck.insureDataSourceIsSet();
            }

            try {
               this.setConnectionAndPreparedStatement();
            } catch (PeerSiteRecoveryJDBCWrappers.PeerRecoveryException var3) {
               TXLogger.logCrossSiteRecoveryIssue("PeerSiteRecoveryTakeoverLeaseMaintenance: Unable to create either connection or prepared statements for cross-site recovery. sqlException:" + var3);
            }
         }

         this.doSleep();
      } while(this.isStillRunning());

   }

   private void setConnectionAndPreparedStatement() throws PeerSiteRecoveryJDBCWrappers.PeerRecoveryException {
      if (this.connection == null) {
         this.connection = this.platformHelper.peerSiteRecoveryCheck.recoverySiteDataSource.getConnection();
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryLeaseMaintenance.run acquired connection for lease update :" + this.connection);
      }

      if (this.updateLeaseStmt == null) {
         this.updateLeaseStmt = this.connection.prepareStatement(this.platformHelper.peerSiteRecoveryLeaseMaintenance.getUpdateLeaseSQL(this.platformHelper.peerSiteRecoveryCheck.recoverySiteTableName));
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryLeaseMaintenance.run acquired updateLeaseStmt for lease update :" + this.updateLeaseStmt);
      }

   }

   void insertOrUpdateTakeoverLeaseRecoveryComplete(String siteName, String serverName) {
      TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryTakeoverLeaseMaintenance.insertOrUpdateTakeoverLeaseRecoveryComplete for siteName:" + siteName + " serverName:" + serverName);
   }

   void updateForTakeoverLeaseInitialRecord(PeerSiteRecoveryJDBCWrappers.PreparedStatement preparedStatement, String siteName, String serverName) {
      TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryTakeoverLeaseMaintenance.updateForTakeoverLeaseInitialRecord for siteName:" + siteName + " serverName:" + serverName);
      this.insertOrUpdateTakeoverLease(preparedStatement, siteName, serverName, new Date(this.platformHelper.peerSiteRecoveryLeaseMaintenance.currentDate.getTime() + (long)(this.platformHelper.peerSiteRecoveryCheck.getTransactionManagerImpl().getCrossSiteRecoveryLeaseExpiration() * 1000)));
   }

   private void insertOrUpdateTakeoverLease(PeerSiteRecoveryJDBCWrappers.PreparedStatement preparedStatement, String siteName, String serverName, java.util.Date time) {
      this.platformHelper.peerSiteRecoveryLeaseMaintenance.insertOrUpdateLease(preparedStatement, this.platformHelper.peerSiteRecoveryCheck.recoverySiteTableName, siteName, PlatformHelper.getPlatformHelper().getDomainName(), this.platformHelper.peerSiteRecoveryCheck.getClusterName(), serverName, TransactionManagerImpl.getTransactionManager().getJdbcTLogPrefixName(), TransactionManagerImpl.getTransactionManager().getSiteName(), TransactionManagerImpl.getTransactionManager().getServerName(), time, true);
   }

   private void doSleep() {
      try {
         Thread.sleep((long)(1000 * this.platformHelper.peerSiteRecoveryCheck.getTransactionManagerImpl().getCrossSiteRecoveryLeaseUpdate()));
      } catch (InterruptedException var2) {
      }

   }

   private boolean isStillRunning() {
      return this.isStillRunning;
   }
}
