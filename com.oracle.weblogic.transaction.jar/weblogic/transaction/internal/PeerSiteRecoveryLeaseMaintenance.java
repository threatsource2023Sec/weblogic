package weblogic.transaction.internal;

import java.sql.Date;

class PeerSiteRecoveryLeaseMaintenance implements Runnable {
   private PeerSiteRecoveryCheck peerSiteRecoveryCheck = null;
   private PeerSiteRecoveryJDBCWrappers.Connection connection;
   boolean isStillRunning = true;
   private boolean isFirstEntryMade = false;
   boolean isExistingRowAtStartup = false;
   private PeerSiteRecoveryJDBCWrappers.PreparedStatement currentTimePreparedStatement = null;
   private long deltaDbTimeMinusLocalTime;
   private PeerSiteRecoveryJDBCWrappers.PreparedStatement insertLeaseStmt;
   private PeerSiteRecoveryJDBCWrappers.PreparedStatement updateLeaseStmt;
   volatile Date currentDate = null;

   PeerSiteRecoveryLeaseMaintenance(PeerSiteRecoveryCheck peerSiteRecoveryCheck) {
      this.peerSiteRecoveryCheck = peerSiteRecoveryCheck;
   }

   private boolean isStillRunning() {
      return this.isStillRunning;
   }

   public void run() {
      TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryLeaseMaintenance.run CrossSiteRecoveryLeaseUpdate:" + this.peerSiteRecoveryCheck.getTransactionManagerImpl().getCrossSiteRecoveryLeaseUpdate());

      do {
         String siteName = TransactionManagerImpl.getTransactionManager().getSiteName();
         if (siteName != null && !siteName.trim().equals("")) {
            if (this.peerSiteRecoveryCheck.recoverySiteDataSource == null) {
               this.peerSiteRecoveryCheck.insureDataSourceIsSet();
            }

            this.insertOrUpdateLease();
         }

         this.doSleep();
      } while(this.isStillRunning());

   }

   private void insertOrUpdateLease() {
      if (!this.setConnectionAndPreparedStatement()) {
         TXLogger.logCrossSiteRecoveryIssue("PeerSiteRecoveryLeaseMaintenance: Unable to create either connection or prepared statements for cross-site recovery.");
      } else {
         java.util.Date currentDate = this.getCurrentDate();
         this.insertOrUpdateLease(!this.isFirstEntryMade && !this.isExistingRowAtStartup ? this.insertLeaseStmt : this.updateLeaseStmt, this.peerSiteRecoveryCheck.siteTableName, TransactionManagerImpl.getTransactionManager().getSiteName(), PlatformHelper.getPlatformHelper().getDomainName(), this.peerSiteRecoveryCheck.getClusterName(), TransactionManagerImpl.getTransactionManager().getServerName(), TransactionManagerImpl.getTransactionManager().getJdbcTLogPrefixName(), TransactionManagerImpl.getTransactionManager().getSiteName(), TransactionManagerImpl.getTransactionManager().getServerName(), currentDate, false);
      }
   }

   private boolean setConnectionAndPreparedStatement() {
      try {
         if (this.connection == null) {
            this.connection = this.peerSiteRecoveryCheck.recoverySiteDataSource.getConnection();
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryLeaseMaintenance.setConnectionAndPreparedStatement acquired connection for lease update :" + this.connection);
         }

         if (this.peerSiteRecoveryCheck.siteTableName == null) {
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryLeaseMaintenance.setConnectionAndPreparedStatement  siteTableName is null (this may be expected during startup) connection :" + this.connection);
            return false;
         }

         if (this.insertLeaseStmt == null) {
            String insertOrUpdateLeaseStmtSQL = " INSERT INTO " + this.peerSiteRecoveryCheck.siteTableName + "( SITE, DOMAIN, CLUSTERIFANY, SERVER, TRANSACTIONLOGTABLENAME, OWNINGSITE, OWNINGSERVER, TIMEOUT ) values ( ?, ?, ?, ?, ?, ?, ?, ?)";
            this.insertLeaseStmt = this.connection.prepareStatement(insertOrUpdateLeaseStmtSQL);
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryLeaseMaintenance.run acquired insertLeaseStmt for lease update :" + this.insertLeaseStmt + "using insertOrUpdateLeaseStmtSQL:" + insertOrUpdateLeaseStmtSQL);
         }

         if (this.updateLeaseStmt == null) {
            this.updateLeaseStmt = this.connection.prepareStatement(this.getUpdateLeaseSQL(this.peerSiteRecoveryCheck.siteTableName));
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryLeaseMaintenance.run acquired updateLeaseStmt for lease update :" + this.updateLeaseStmt);
         }
      } catch (PeerSiteRecoveryJDBCWrappers.PeerRecoveryException var2) {
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryLeaseMaintenance.run Unable to acquire connection or insertLeaseStmt for lease update SQLException:" + var2);
      }

      return this.insertLeaseStmt != null;
   }

   String getUpdateLeaseSQL(String tableName) {
      return "UPDATE " + tableName + "    SET    TIMEOUT = ? ,         OWNINGSITE = ? ,         OWNINGSERVER = ?     WHERE SITE = ?     AND DOMAIN = ?     AND CLUSTERIFANY = ?     AND SERVER = ? ";
   }

   synchronized void insertOrUpdateLease(PeerSiteRecoveryJDBCWrappers.PreparedStatement preparedStatement, String tableName, String siteName, String domainName, String clusterName, String serverName, String jdbcTLogPrefixName, String owningSite, String owningServer, java.util.Date currentDate, boolean isTakeover) {
      TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryLeaseMaintenance.insertOrUpdateLease isFirstEntryMade:" + this.isFirstEntryMade + " isTakeover:" + isTakeover);
      String insertOrUpdateLeaseSQLDebug = " INSERT INTO " + tableName + " ( SITE, DOMAIN, CLUSTERIFANY, SERVER, TRANSACTIONLOGTABLENAME, OWNINGSITE, OWNINGSERVER, TIMEOUT ) values ( '" + siteName + "',  '" + domainName + "',  '" + clusterName + "',  '" + serverName + "',  '" + jdbcTLogPrefixName + "',  '" + owningSite + "',  '" + owningServer + "',  " + currentDate + ")";
      String updateLeaseSQLDebug = "    UPDATE " + tableName + "    SET    TIMEOUT =  " + currentDate + ",         OWNINGSITE =  " + owningSite + ",         OWNINGSERVER =  " + owningServer + "     WHERE SITE = '" + siteName + "'    AND DOMAIN = '" + domainName + "'    AND CLUSTERIFANY = '" + clusterName + "'    AND SERVER = '" + serverName + "'";
      if (siteName == null) {
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryLeaseMaintenance not doing insert or update as siteName is null (this may be expected during startup) sql:" + (this.isFirstEntryMade ? updateLeaseSQLDebug : insertOrUpdateLeaseSQLDebug));
      } else {
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryLeaseMaintenance about to sql:" + (!this.isFirstEntryMade && !this.isExistingRowAtStartup ? insertOrUpdateLeaseSQLDebug : updateLeaseSQLDebug));

         try {
            if (!this.isFirstEntryMade && !this.isExistingRowAtStartup) {
               preparedStatement.setString(1, siteName);
               preparedStatement.setString(2, domainName);
               preparedStatement.setString(3, clusterName);
               preparedStatement.setString(4, serverName);
               preparedStatement.setString(5, jdbcTLogPrefixName);
               preparedStatement.setString(6, owningSite);
               preparedStatement.setString(7, owningServer);
               preparedStatement.setDate(8, currentDate);
            } else {
               preparedStatement.setDate(1, currentDate);
               preparedStatement.setString(2, owningSite);
               preparedStatement.setString(3, owningServer);
               preparedStatement.setString(4, siteName);
               preparedStatement.setString(5, domainName);
               preparedStatement.setString(6, clusterName);
               preparedStatement.setString(7, serverName);
            }

            preparedStatement.execute();
            if (!isTakeover) {
               this.isFirstEntryMade = true;
            }
         } catch (PeerSiteRecoveryJDBCWrappers.PeerRecoveryException var15) {
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryLeaseMaintenance failed to sql:" + (this.isFirstEntryMade && !isTakeover ? updateLeaseSQLDebug : insertOrUpdateLeaseSQLDebug) + " SQLException:" + var15);
         }

      }
   }

   private java.util.Date getCurrentDate() {
      if (this.deltaDbTimeMinusLocalTime != 0L) {
         this.currentDate = new Date(System.currentTimeMillis() + this.deltaDbTimeMinusLocalTime);
         return this.currentDate;
      } else {
         try {
            if (this.currentTimePreparedStatement == null && this.connection != null) {
               this.currentTimePreparedStatement = this.connection.prepareStatement("select SYSDATE from dual");
            }

            if (this.currentTimePreparedStatement != null) {
               PeerSiteRecoveryJDBCWrappers.ResultSet resultSet = this.currentTimePreparedStatement.executeQuery();
               if (resultSet.next()) {
                  this.currentDate = this.peerSiteRecoveryCheck.peerSiteRecoveryJDBCWrappers.getDate(resultSet.getObject(1));
               }

               long currentLocalTime = System.currentTimeMillis();
               this.deltaDbTimeMinusLocalTime = this.currentDate.getTime() - currentLocalTime;
               TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryLeaseMaintenance.getCurrentDate currentLocalTime:" + currentLocalTime + " currentDate.getTime():" + this.currentDate.getTime() + " deltaDbTimeMinusLocalTime" + this.deltaDbTimeMinusLocalTime);
               return this.currentDate;
            }
         } catch (PeerSiteRecoveryJDBCWrappers.PeerRecoveryException var4) {
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryLeaseMaintenance.getCurrentDate SQLException processing currentDBSysDate recovery site recovery SQLException:" + var4);
         }

         return null;
      }
   }

   private void doSleep() {
      try {
         Thread.sleep((long)(1000 * this.peerSiteRecoveryCheck.getTransactionManagerImpl().getCrossSiteRecoveryLeaseUpdate()));
      } catch (InterruptedException var2) {
      }

   }

   boolean isFirstEntryMade() {
      return this.isFirstEntryMade;
   }
}
