package weblogic.transaction.internal;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import javax.transaction.SystemException;
import weblogic.management.configuration.JDBCStoreMBean;

class PeerSiteRecoveryCheck implements Runnable {
   PlatformHelperImpl platformHelper;
   private volatile Map listOfPeerServersRecovered = new HashMap();
   boolean isStillRunning = true;
   private boolean isTableCreationAttempted = false;
   String siteTableName = null;
   String recoverySiteTableName = null;
   private JDBCStoreMBean jBean;
   PeerSiteRecoveryJDBCWrappers.DataSource recoverySiteDataSource = null;
   PeerSiteRecoveryJDBCWrappers peerSiteRecoveryJDBCWrappers = new PeerSiteRecoveryJDBCWrappers();
   PeerSiteRecoveryJDBCWrappers.Connection connection = null;
   private PeerSiteRecoveryJDBCWrappers.PreparedStatement selectAllForClusterDomainSitePreparedStatement = null;
   private PeerSiteRecoveryJDBCWrappers.PreparedStatement selectForUpdateStatement = null;
   private PeerSiteRecoveryJDBCWrappers.PreparedStatement initialTakeoverLeaseUpdateStatement = null;
   private Date currentDBSysDate;
   static final String SELECT_TO_DATE_SYSDATE_FROM_DUAL = "select SYSDATE from dual";
   private static final String TRANSACTIONLOGTABLENAME = "TRANSACTIONLOGTABLENAME";
   private static final String SERVER = "SERVER";
   private static final String TIMEOUT = "TIMEOUT";
   private static final String SINGLE_TABLE_NAME = System.getProperty("weblogic.transaction.internal.cross.site.single.table.name");

   PeerSiteRecoveryCheck(PlatformHelperImpl platformHelper, Object jdbcStoreMBean) {
      this.platformHelper = platformHelper;
      this.jBean = (JDBCStoreMBean)jdbcStoreMBean;
      TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck constructor this: " + this);
   }

   public void run() {
      TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck run this:" + this + " getTM().getCrossSiteRecoveryLeaseExpiration():" + this.getTransactionManagerImpl().getCrossSiteRecoveryLeaseExpiration() + " getTM().getCrossSiteRecoveryRetryInterval():" + this.getTransactionManagerImpl().getCrossSiteRecoveryRetryInterval());
      String recoverySiteName = null;
      this.listOfPeerServersRecovered = new HashMap();

      do {
         String recoverySiteNameLatest = this.getRecoverySiteName();
         this.selectAllForClusterDomainSitePreparedStatement = this.checkForConfigChange(this.selectAllForClusterDomainSitePreparedStatement, recoverySiteName, recoverySiteNameLatest);
         recoverySiteName = recoverySiteNameLatest;
         if (recoverySiteNameLatest != null && !recoverySiteNameLatest.trim().equals("")) {
            try {
               if (this.recoverySiteDataSource == null) {
                  this.insureDataSourceIsSet();
               }

               if (this.recoverySiteDataSource != null && this.connection == null) {
                  this.connection = this.recoverySiteDataSource.getConnection();
               }

               if (this.connection != null && this.selectAllForClusterDomainSitePreparedStatement == null) {
                  if (!this.isTableCreationAttempted) {
                     this.createTables();
                  }

                  this.setSelectAllForClusterDomainSitePreparedStatement(recoverySiteName);
               }
            } catch (PeerSiteRecoveryJDBCWrappers.PeerRecoveryException var7) {
               TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.run preparedStatement creation  sqlException:" + var7);
               this.doSleep();
               continue;
            }
         }

         String serverName;
         if (!this.listOfPeerServersRecovered.isEmpty()) {
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.run !listOfPeerServersRecovered.isEmpty()");

            try {
               PeerSiteRecoveryJDBCWrappers.ResultSet recoverySiteServerResultSet = this.getSelectAllForClusterDomainSiteResultSet();

               label106:
               while(true) {
                  while(true) {
                     String serverName;
                     do {
                        if (recoverySiteServerResultSet == null || !recoverySiteServerResultSet.next()) {
                           break label106;
                        }

                        serverName = recoverySiteServerResultSet.getString("SERVER");
                     } while(!this.listOfPeerServersRecovered.containsKey(serverName));

                     serverName = recoverySiteServerResultSet.getString("OWNINGSITE");
                     TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.run !listOfPeerServersRecovered.isEmpty() serverName:" + serverName + " owningSite:" + serverName);
                     if (serverName != null && serverName.equals(this.getTM().getRecoverySiteName())) {
                        TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.run !listOfPeerServersRecovered.isEmpty() serverName:" + serverName + " owningSite:" + serverName + " Original site is back up.  suspendRecovery for failback beginning...");
                        this.getTM().suspendRecovery(recoverySiteName + "." + serverName);
                        this.listOfPeerServersRecovered.remove(serverName);
                        TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.run !listOfPeerServersRecovered.isEmpty() serverName:" + serverName + " owningSite:" + serverName + " Original site is back up.  suspendRecovery for failback complete");
                     } else {
                        TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.run !listOfPeerServersRecovered.isEmpty() serverName:" + serverName + " owningSite:" + serverName + " Original site is not back up yet.");
                     }
                  }
               }
            } catch (PeerSiteRecoveryJDBCWrappers.PeerRecoveryException var10) {
               TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.run SQLException processing resultSetrecovery site isLeaseExpired SQLException:" + var10);
            }
         } else {
            this.currentDBSysDate = this.getCurrentDate();
            if (this.selectAllForClusterDomainSitePreparedStatement != null) {
               boolean allLeasesExpired = false;
               PeerSiteRecoveryJDBCWrappers.ResultSet recoverySiteServerResultSet = null;

               try {
                  recoverySiteServerResultSet = this.getSelectAllForClusterDomainSiteResultSet();

                  while(recoverySiteServerResultSet != null && recoverySiteServerResultSet.next()) {
                     serverName = recoverySiteServerResultSet.getString("SERVER");
                     java.util.Date leaseDate = this.peerSiteRecoveryJDBCWrappers.getDate(recoverySiteServerResultSet.getObject("TIMEOUT"));
                     allLeasesExpired = this.isLeaseExpired(recoverySiteName, leaseDate, serverName);
                     if (!allLeasesExpired) {
                        break;
                     }
                  }
               } catch (PeerSiteRecoveryJDBCWrappers.PeerRecoveryException var9) {
                  TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.run SQLException processing resultSetrecovery site isLeaseExpired SQLException:" + var9);
               }

               try {
                  if (recoverySiteServerResultSet != null && allLeasesExpired) {
                     TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.run allLeasesExpired");
                     recoverySiteServerResultSet.beforeFirst();

                     while(recoverySiteServerResultSet.next()) {
                        serverName = recoverySiteServerResultSet.getString("SERVER");
                        this.attemptRecoveryOfRecoverySiteServer(recoverySiteName, recoverySiteServerResultSet, serverName);
                     }
                  }
               } catch (PeerSiteRecoveryJDBCWrappers.PeerRecoveryException var8) {
                  TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.run SQLException processing resultSetrecovery site recovery SQLException:" + var8);
               }
            }
         }

         this.doSleep();
      } while(this.isStillRunning());

   }

   void doSleep() {
      try {
         Thread.sleep((long)(PlatformHelperImpl.getTM().getCrossSiteRecoveryRetryInterval() * 1000));
      } catch (InterruptedException var2) {
         var2.printStackTrace();
      }

   }

   private void doDataSourceRetrySleep() {
      try {
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.doDataSourceRetrySleep waiting 5 seconds to retry datasource lookup");
         Thread.sleep(5000L);
      } catch (InterruptedException var2) {
         var2.printStackTrace();
      }

   }

   void createTables() {
      TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.createTables SINGLE_TABLE_NAME:" + SINGLE_TABLE_NAME);
      if (SINGLE_TABLE_NAME != null && !SINGLE_TABLE_NAME.trim().equals("")) {
         this.createTable("SITELEASING", true);
      } else {
         this.createTable(this.siteTableName = this.getTM().getSiteName() + "SITELEASING", true);
         this.createTable(this.recoverySiteTableName = this.getRecoverySiteName() + "SITELEASING", false);
      }

      this.isTableCreationAttempted = true;
   }

   private void createTable(String tableName, boolean isThisServersSiteTable) {
      String sql = isThisServersSiteTable ? "select * from " + tableName + " where SITE = '" + this.getTM().getSiteName() + "' and SERVER = '" + TransactionManagerImpl.getTransactionManager().getServerName() + "'" : "select count(*) from " + tableName;

      PeerSiteRecoveryJDBCWrappers.PreparedStatement preparedStatement;
      try {
         preparedStatement = this.connection.prepareStatement(sql);
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.createTable table :" + tableName + "isThisServersSiteTable:" + isThisServersSiteTable + " about to execute sql:" + sql);
         PeerSiteRecoveryJDBCWrappers.ResultSet resultSet = preparedStatement.executeQuery();
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.createTable table :" + tableName + " exists isThisServersSiteTable:" + isThisServersSiteTable);
         boolean isNext = resultSet.next();
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.createTable table :" + tableName + " exists isThisServersSiteTable:" + isThisServersSiteTable + " isNext:" + isNext);
         if (isNext && isThisServersSiteTable) {
            this.platformHelper.peerSiteRecoveryLeaseMaintenance.isExistingRowAtStartup = true;
         }
      } catch (PeerSiteRecoveryJDBCWrappers.PeerRecoveryException var8) {
         sql = "CREATE TABLE " + tableName + " ( SITE VARCHAR2(255) NOT NULL, DOMAIN VARCHAR2(255) NOT NULL, CLUSTERIFANY VARCHAR2(255), " + "SERVER" + " VARCHAR2(255) NOT NULL, " + "TRANSACTIONLOGTABLENAME" + " VARCHAR2(255) NOT NULL, OWNINGSITE VARCHAR2(255) NOT NULL, OWNINGSERVER VARCHAR2(255) NOT NULL, " + "TIMEOUT" + "  DATE, PRIMARY KEY (SITE, DOMAIN, SERVER) )";
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.createTable sql:" + sql + " failed with SQLException:" + var8 + "  Attempting to create table with sql:" + sql);

         try {
            preparedStatement = this.recoverySiteDataSource.getConnection().prepareStatement(sql);
            preparedStatement.execute();
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.createTable table created for :" + tableName);
         } catch (PeerSiteRecoveryJDBCWrappers.PeerRecoveryException var7) {
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.createTable  sqlException creating table:" + tableName + " SQLException :" + var7);
         }

         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.createTable  sqlException creating table:" + tableName + " (this is expected if table has been created already) :" + var8);
      }

   }

   private Date getCurrentDate() {
      return this.platformHelper.peerSiteRecoveryLeaseMaintenance == null ? null : this.platformHelper.peerSiteRecoveryLeaseMaintenance.currentDate;
   }

   void insureDataSourceIsSet() {
      if (this.jBean != null) {
         String jndiLookup = this.jBean.getDataSource().getJDBCResource().getJDBCDataSourceParams().getJNDINames()[0];

         try {
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.insureDataSourceIsSet jndiLookup:" + jndiLookup);
            this.recoverySiteDataSource = this.recoverySiteDataSource != null ? this.recoverySiteDataSource : (new PeerSiteRecoveryJDBCWrappers()).getDataSource(jndiLookup);
         } catch (PeerSiteRecoveryJDBCWrappers.PeerRecoveryException var3) {
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.insureDataSourceIsSet jndiLookup:" + jndiLookup + " failed. NamingException: + " + var3 + " Sleeping and retrying...");
            this.doDataSourceRetrySleep();
            this.insureDataSourceIsSet();
         }

      }
   }

   private PeerSiteRecoveryJDBCWrappers.PreparedStatement checkForConfigChange(PeerSiteRecoveryJDBCWrappers.PreparedStatement preparedStatement, String recoverySiteName, String recoverySiteNameLatest) {
      if (recoverySiteName != null & recoverySiteNameLatest != null && !recoverySiteName.equals(recoverySiteNameLatest)) {
         preparedStatement = null;
         this.isTableCreationAttempted = false;
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck recovery-site-name changed to " + recoverySiteNameLatest);
      }

      if (recoverySiteName == null & recoverySiteNameLatest != null) {
         preparedStatement = null;
         this.isTableCreationAttempted = false;
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck recovery-site-name activated, is " + recoverySiteNameLatest);
      }

      if (recoverySiteName != null & recoverySiteNameLatest == null) {
         preparedStatement = null;
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck recovery-site-name deactivated, was " + recoverySiteName);
      }

      return preparedStatement;
   }

   boolean isLeaseExpired(String recoverySiteName, java.util.Date leaseDate, String serverName) throws PeerSiteRecoveryJDBCWrappers.PeerRecoveryException {
      if (this.currentDBSysDate == null) {
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.isLeaseExpired \n serverName:" + serverName + "\n leaseDate (null leaseDate indicates server is taken over):" + leaseDate + "\n currentDBSysDate:" + null);
         return false;
      } else if (leaseDate == null) {
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.isLeaseExpired \n serverName:" + serverName + "\n leaseDate (null leaseDate indicates server is taken over):" + null + "\n currentDBSysDate:" + this.currentDBSysDate);
         return false;
      } else {
         long currentTimeMinusLeaseTime = this.currentDBSysDate.getTime() - leaseDate.getTime();
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.isLeaseExpired \n serverName:" + serverName + "\n currentDBSysDate:" + this.currentDBSysDate.toGMTString() + "\n leaseDate       :" + leaseDate.toGMTString() + "\n currentDBSysDate.getTime():" + this.currentDBSysDate.getTime() + "\n leaseDate       .getTime():" + leaseDate.getTime() + "\n currentTimeMinusLeaseTime:" + currentTimeMinusLeaseTime + "\n from recoverySiteName:" + recoverySiteName);
         if (currentTimeMinusLeaseTime > (long)(this.getTransactionManagerImpl().getCrossSiteRecoveryLeaseExpiration() * 1000)) {
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.isLeaseExpired lease expired for  serverName:" + serverName + " currentDBSysDate:" + this.currentDBSysDate + " leaseDate       :" + leaseDate + " from recoverySiteName:" + recoverySiteName);
            return true;
         } else {
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.isLeaseExpired lease not expired for  serverName:" + serverName + " currentDBSysDate:" + this.currentDBSysDate + " leaseDate       :" + leaseDate + " from recoverySiteName:" + recoverySiteName);
            return false;
         }
      }
   }

   private void attemptRecoveryOfRecoverySiteServer(String recoverySiteName, PeerSiteRecoveryJDBCWrappers.ResultSet resultSet, String serverName) throws PeerSiteRecoveryJDBCWrappers.PeerRecoveryException {
      String tableName = resultSet.getString("TRANSACTIONLOGTABLENAME");
      TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.attemptRecoveryOfRecoverySiteServer tableName:" + tableName + " from recoverySiteName:" + recoverySiteName);
      String recoverySiteServerName = tableName.substring(recoverySiteName.length() + 1, tableName.length() - 1);
      TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.attemptRecoveryOfRecoverySiteServer string:" + tableName + " from recoverySiteName:" + recoverySiteName + " recoverySiteServerName:" + recoverySiteServerName);

      try {
         if (!this.acquireLeaseLock(recoverySiteName, recoverySiteServerName)) {
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.attemptRecoveryOfRecoverySiteServer lock not acquired for recoverySiteName: " + recoverySiteName + " recoverySiteServerName:" + recoverySiteServerName + " lockNotAcquired");
            return;
         }

         this.listOfPeerServersRecovered.put(serverName, this.getMigratedTLog(recoverySiteName, serverName, new RecoveryCompletionListener(recoverySiteName, recoverySiteServerName)));
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.attemptRecoveryOfRecoverySiteServer lock acquired for recoverySiteName: " + recoverySiteName + " recoverySiteServerName:" + recoverySiteServerName + " Now attempting registerAnyMigratedLLRResources");

         try {
            this.getTM().registerAnyMigratedLLRResources(recoverySiteServerName, false);
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.attemptRecoveryOfRecoverySiteServer registerAnyMigratedLLRResources successful for recoverySiteName: " + recoverySiteName + " recoverySiteServerName:" + recoverySiteServerName + " Now attempting updateForTakeoverLeaseInitialRecord");
            this.setUpdateLeaseStmt();
            this.platformHelper.peerSiteRecoveryTakeoverLeaseMaintenance.updateForTakeoverLeaseInitialRecord(this.initialTakeoverLeaseUpdateStatement, recoverySiteName, recoverySiteServerName);
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.attemptRecoveryOfRecoverySiteServer updateForTakeoverLeaseInitialRecord successful for recoverySiteName: " + recoverySiteName + " recoverySiteServerName:" + recoverySiteServerName + " Now attempting commit and release of lock");
            this.connection.commit();
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.attemptRecoveryOfRecoverySiteServer registerCrossSiteJTAPeerSiteRecoveryRuntime successful for recoverySiteServerName: recoverySiteName: " + recoverySiteName + " recoverySiteServerName:" + recoverySiteServerName + " Takeover complete.");
         } catch (SystemException var9) {
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.attemptRecoveryOfRecoverySiteServer registerAnyMigratedLLRResources systemException:" + var9 + " for recoverySiteServerName:" + recoverySiteServerName);

            try {
               if (this.connection != null) {
                  this.connection.rollback();
               }
            } catch (PeerSiteRecoveryJDBCWrappers.PeerRecoveryException var8) {
               TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.attemptRecoveryOfRecoverySiteServer registerAnyMigratedLLRResources systemException:" + var9 + " for recoverySiteServerName:" + recoverySiteServerName + " sqlException during cleanup rollback:" + var8);
            }
         }
      } catch (Exception var10) {
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.attemptRecoveryOfRecoverySiteServer Exception during table lock query (this is the expected case unless a lock has been released) e:" + var10);
      }

   }

   private boolean acquireLeaseLock(String recoverySiteName, String recoverySiteServerName) {
      TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.acquireLeaseLock start attempting lock on recoverySiteName: " + recoverySiteName + " recoverySiteServerName:" + recoverySiteServerName);

      try {
         this.connection.setAutoCommit(false);
         if (this.selectForUpdateStatement == null) {
            String sqlString = "select * from " + this.recoverySiteTableName + " where SITE = ? and SERVER = ? for update nowait";
            this.selectForUpdateStatement = this.connection.prepareStatement(sqlString);
         }

         this.selectForUpdateStatement.setString(1, recoverySiteName);
         this.selectForUpdateStatement.setString(2, recoverySiteServerName);
         this.selectForUpdateStatement.executeQuery();
      } catch (PeerSiteRecoveryJDBCWrappers.PeerRecoveryException var7) {
         String sqlString = "select * from " + this.recoverySiteTableName + " where SITE = ? and SERVER = ? for update nowait";
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.acquireLeaseLock SQLException attempting to execute selectForUpdateSQL:" + sqlString + " This is expected if another server has acquired the lease lock before this one.  SQLException:" + var7);

         try {
            if (this.connection != null) {
               this.connection.rollback();
            }
         } catch (PeerSiteRecoveryJDBCWrappers.PeerRecoveryException var6) {
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.acquireLeaseLock SQLException attempting to execute selectForUpdateSQL and cleanup rollback failed with sqlException:" + var6);
         }

         return false;
      }

      TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.acquireLeaseLock successful attempting lock on recoverySiteName: " + recoverySiteName + " recoverySiteServerName:" + recoverySiteServerName);
      return true;
   }

   private boolean setUpdateLeaseStmt() {
      if (this.initialTakeoverLeaseUpdateStatement == null) {
         try {
            this.initialTakeoverLeaseUpdateStatement = this.connection.prepareStatement(this.platformHelper.peerSiteRecoveryLeaseMaintenance.getUpdateLeaseSQL(this.recoverySiteTableName));
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.run acquired initialTakeoverLeaseUpdateStatement for lease update :" + this.initialTakeoverLeaseUpdateStatement);
         } catch (PeerSiteRecoveryJDBCWrappers.PeerRecoveryException var2) {
            TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.run Unable to acquire connection or initialTakeoverLeaseUpdateStatement for lease update SQLException:" + var2);
         }
      }

      return this.initialTakeoverLeaseUpdateStatement != null;
   }

   ServerTransactionManagerImpl getTM() {
      return (ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTransactionManager();
   }

   TransactionManagerImpl getTransactionManagerImpl() {
      return ServerTransactionManagerImpl.getTransactionManager();
   }

   private ServerTransactionManagerImpl.MigratedTLog getMigratedTLog(String recoverySiteName, String recoverySiteServerName, ServerTransactionManagerImpl.MigratedTLog.RecoveryCompletionListener recoveryCompletionListener) throws Exception {
      return new ServerTransactionManagerImpl.MigratedTLog(recoverySiteServerName, recoverySiteName, recoveryCompletionListener);
   }

   String getRecoverySiteName() {
      return PlatformHelperImpl.getTM().getRecoverySiteName();
   }

   private boolean isStillRunning() {
      return this.isStillRunning;
   }

   private void setSelectAllForClusterDomainSitePreparedStatement(String recoverySiteName) throws PeerSiteRecoveryJDBCWrappers.PeerRecoveryException {
      TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.setSelectAllForClusterDomainSitePreparedStatement sqlDebugString:" + this.getSelectAllForSQLDebugString(recoverySiteName));
      if (this.selectAllForClusterDomainSitePreparedStatement == null) {
         String sqlString = "select SERVER, TIMEOUT, TRANSACTIONLOGTABLENAME, OWNINGSITE  from " + this.recoverySiteTableName + " where SITE = ? and DOMAIN = ? and CLUSTERIFANY = ? ";
         this.selectAllForClusterDomainSitePreparedStatement = this.connection.prepareStatementScrollInsensitive(sqlString);
      }

      this.selectAllForClusterDomainSitePreparedStatement.setString(1, recoverySiteName);
      this.selectAllForClusterDomainSitePreparedStatement.setString(2, this.getDomainName());
      this.selectAllForClusterDomainSitePreparedStatement.setString(3, this.getClusterName());
   }

   String getClusterName() {
      String clusterName = this.platformHelper.getClusterName();
      return clusterName != null && !clusterName.trim().equals("") ? clusterName : "~";
   }

   String getDomainName() {
      return this.platformHelper.getDomainName();
   }

   String getSelectAllForSQLDebugString(String recoverySiteName) {
      return "select SERVER, TIMEOUT, TRANSACTIONLOGTABLENAME, OWNINGSITE  from '" + this.recoverySiteTableName + "' where SITE = '" + recoverySiteName + "' and DOMAIN = '" + this.getDomainName() + "' and CLUSTERIFANY = '" + this.getClusterName() + "' ";
   }

   private PeerSiteRecoveryJDBCWrappers.ResultSet getSelectAllForClusterDomainSiteResultSet() throws PeerSiteRecoveryJDBCWrappers.PeerRecoveryException {
      try {
         return this.doGetSelectAllForClusterDomainSiteResultSet();
      } catch (PeerSiteRecoveryJDBCWrappers.PeerRecoveryException var2) {
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck.getSelectAllForClusterDomainSiteResultSet selectAllForClusterDomainSitePreparedStatement = [" + this.selectAllForClusterDomainSitePreparedStatement + "] sqlex:" + var2);
         return null;
      }
   }

   private PeerSiteRecoveryJDBCWrappers.ResultSet doGetSelectAllForClusterDomainSiteResultSet() throws PeerSiteRecoveryJDBCWrappers.PeerRecoveryException {
      return this.selectAllForClusterDomainSitePreparedStatement.executeQuery();
   }

   private class RecoveryCompletionListener implements ServerTransactionManagerImpl.MigratedTLog.RecoveryCompletionListener {
      String recoverySiteName;
      String recoverySiteServerName;

      RecoveryCompletionListener(String recoverySiteName, String recoverySiteServerName) {
         this.recoverySiteName = recoverySiteName;
         this.recoverySiteServerName = recoverySiteServerName;
      }

      public void onCompletion() {
         TxDebug.JTAPeerSiteRecovery.debug("PeerSiteRecoveryCheck MigratedTLog.RecoveryCompletionListener for recoverySiteName: " + this.recoverySiteName + " recoverySiteServerName:" + this.recoverySiteServerName);
         PeerSiteRecoveryCheck.this.platformHelper.peerSiteRecoveryTakeoverLeaseMaintenance.insertOrUpdateTakeoverLeaseRecoveryComplete(this.recoverySiteName, this.recoverySiteServerName);
         PeerSiteRecoveryCheck.this.listOfPeerServersRecovered.remove(this.recoverySiteServerName);
      }
   }
}
