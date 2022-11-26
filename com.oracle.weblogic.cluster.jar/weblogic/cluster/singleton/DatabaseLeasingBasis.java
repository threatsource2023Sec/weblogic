package weblogic.cluster.singleton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import weblogic.cluster.ClusterExtensionLogger;
import weblogic.cluster.ClusterLogger;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean;
import weblogic.jdbc.common.internal.JDBCUtil;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;
import weblogic.store.io.jdbc.JDBCHelper;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManagerFactory;

public class DatabaseLeasingBasis implements LeasingBasis, NakedTimerListener {
   private static final boolean DEBUG = MigrationDebugLogger.isDebugEnabled();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final long INITIALIZE_TABLE_RETRY_PRRIOD = 30000L;
   private static final String TABLE_CREATION_PREFIX = "weblogic.cluster.singleton.";
   private final TableCreationPolicy tableCreationPolicy;
   private final String tableCreationDDLFile;
   private final ServerMBean server;
   private volatile boolean initializeTableSuccess = false;
   protected final JDBCSystemResourceMBean jdbcSystemResourceMBean;
   protected final String TABLE_NAME;
   protected final int queryTimeoutSeconds;
   private boolean supportsTimeouts = false;
   private DataSource ds;
   private QueryHelper queryHelper;
   private boolean pingDBErrorLogged = false;
   private static int SQL_CONNECTION_RETRY_COUNT = 1;
   private static long SQL_CONNECTION_RETRY_DELAY = 1000L;
   private static String EMPTY_STR = "";

   public DatabaseLeasingBasis(JDBCSystemResourceMBean jdbcSystemResourceMBean, int unresponsiveTimeout, String tableName) {
      this.TABLE_NAME = tableName;
      this.jdbcSystemResourceMBean = jdbcSystemResourceMBean;
      this.queryTimeoutSeconds = unresponsiveTimeout;
      this.server = ManagementService.getRuntimeAccess(kernelId).getServer();
      ClusterMBean cluster = this.server.getCluster();
      if (cluster != null) {
         SQL_CONNECTION_RETRY_COUNT = cluster.getDatabaseLeasingBasisConnectionRetryCount();
         SQL_CONNECTION_RETRY_DELAY = cluster.getDatabaseLeasingBasisConnectionRetryDelay();
      }

      this.tableCreationPolicy = this.getTableCreationPolicy(cluster);
      this.tableCreationDDLFile = this.getTableCreationDDLFile(cluster);
      this.initializeTable();
   }

   private QueryHelper getQueryHelper() throws SQLException {
      if (this.queryHelper == null) {
         Connection con = null;
         SQLException exception = null;

         try {
            int retryCount = SQL_CONNECTION_RETRY_COUNT;

            do {
               try {
                  con = this.getJDBCConnection(false);
               } catch (SQLException var11) {
                  MigrationDebugLogger.debug("Unexpected exception while initializing QueryHelper", var11);
                  --retryCount;
                  if (retryCount < 0) {
                     exception = var11;
                  }

                  try {
                     Thread.currentThread();
                     Thread.sleep(SQL_CONNECTION_RETRY_DELAY);
                  } catch (InterruptedException var10) {
                  }
               }
            } while(con == null && retryCount >= 0);

            if (con == null) {
               throw exception != null ? exception : new SQLException("Unable to obtain connection when initializing QueryHelper");
            }

            this.queryHelper = this.identifyVendorSpecificQuery(this.TABLE_NAME, con);
            if (DEBUG) {
               p("Disable row locking: " + QueryHelperImpl.DISABLE_ROW_LOCKING);
            }

            this.supportsTimeouts = this.queryHelper.supportsTimeouts();
         } finally {
            this.closeSQLConnection(con);
         }
      }

      return this.queryHelper;
   }

   private QueryHelper identifyVendorSpecificQuery(String tableName, Connection conn) {
      int dbmsType;
      String dmlIdentifier;
      try {
         DatabaseMetaData metaData = conn.getMetaData();
         dbmsType = JDBCHelper.getDBMSType(metaData, (String[])null);
         if (this.tableCreationPolicy == DatabaseLeasingBasis.TableCreationPolicy.ALWAYS) {
            dmlIdentifier = JDBCHelper.getDMLIdentifier(metaData, tableName, dbmsType);
         } else {
            dmlIdentifier = tableName;
         }
      } catch (SQLException var14) {
         throw new AssertionError("Could not contact database to get vendor and version: " + var14, var14);
      }

      ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getServer();
      ClusterMBean cluster = server.getCluster();
      String queryHelperClassName = cluster.getSingletonSQLQueryHelper();
      String clusterName = cluster.getName();
      String domainName = server.getParent().getName();
      if (queryHelperClassName != null && queryHelperClassName.length() > 0) {
         try {
            Class clazz = Class.forName(queryHelperClassName);
            Constructor queryHelperClassConstructor = clazz.getConstructor((Class[])null);
            QueryHelper qh = (QueryHelper)queryHelperClassConstructor.newInstance((Object[])null);
            qh.init(dmlIdentifier, domainName, clusterName, dbmsType);
            return qh;
         } catch (Throwable var13) {
            ClusterLogger.logUnableToLoadCustomQueryHelper(queryHelperClassName, var13);
            throw new AssertionError("Failed to load " + queryHelperClassName + " because of " + var13, var13);
         }
      } else {
         return new QueryHelperImpl(tableName, domainName, clusterName, dbmsType);
      }
   }

   public boolean acquire(String leaseName, String owner, int leaseTimeout) throws LeasingException {
      boolean rowLockingWay = false;

      try {
         rowLockingWay = this.getQueryHelper().supportsRowLockingWithNoWait();
      } catch (Exception var6) {
         MigrationDebugLogger.debug("Exception when trying to choose way of acquiring lease", var6);
         return false;
      }

      return rowLockingWay ? this.updateOrInsertLease(leaseName, owner, leaseTimeout) : this.deleteAndInsertLease(leaseName, owner, leaseTimeout);
   }

   private boolean deleteAndInsertLease(String leaseName, String owner, int leaseTimeout) throws LeasingException {
      if (DEBUG) {
         p("tryAcquire(" + leaseName + ", " + leaseTimeout + ", " + owner + ")");
      }

      int acquireLease = this.acquireLease(leaseName, owner);
      if (DEBUG) {
         p("tryAcquire acquireLease: " + acquireLease);
      }

      int assumeLease = this.assumeLease(leaseName, owner, leaseTimeout);
      if (DEBUG) {
         p("tryAcquire assumeLease: " + assumeLease);
      }

      return assumeLease == 1;
   }

   private boolean updateOrInsertLease(String leaseName, String owner, int leaseTimeout) throws LeasingException {
      Connection con = null;
      int result = -1;
      boolean lockAcquired = false;
      boolean var22 = false;

      label406: {
         boolean var9;
         LeasingException le;
         label407: {
            label408: {
               label409: {
                  try {
                     label412: {
                        var22 = true;
                        this.getQueryHelper();
                        con = this.getJDBCConnectionWithRetry();
                        con.setAutoCommit(false);
                        boolean lockExistingLease = false;

                        try {
                           lockExistingLease = this.lockExistingLease(con, leaseName);
                           lockAcquired = true;
                        } catch (SQLException var29) {
                           if (this.queryHelper.isLeaseRowLocked(var29)) {
                              var9 = false;
                              var22 = false;
                              break label408;
                           }

                           if (this.queryHelper.isLeaseRowConstraintViolated(var29)) {
                              var9 = false;
                              var22 = false;
                              break label412;
                           }
                        }

                        if (lockExistingLease) {
                           result = this.updateLease(con, leaseName, owner, leaseTimeout);
                           var22 = false;
                        } else {
                           result = this.insertLease(con, leaseName, owner, leaseTimeout);
                           var22 = false;
                        }
                        break label406;
                     }
                  } catch (Exception var30) {
                     String message = "Could not acquire lease [" + leaseName + "] by [" + owner + "]";
                     if (MigrationDebugLogger.isDebugEnabled()) {
                        MigrationDebugLogger.debug(message, var30);
                     }

                     if (var30 instanceof SQLIntegrityConstraintViolationException && this.queryHelper.isLeaseRowConstraintViolated((SQLException)var30)) {
                        result = 0;
                        var9 = false;
                        var22 = false;
                        break label407;
                     }

                     if (!(var30 instanceof SQLException) || !this.queryHelper.isLeaseRowLocked((SQLException)var30)) {
                        throw new LeasingException(message, var30);
                     }

                     result = 0;
                     var9 = false;
                     var22 = false;
                     break label409;
                  } finally {
                     if (var22) {
                        if (lockAcquired) {
                           try {
                              if (result < 0 || result > 1) {
                                 LeasingException le = new LeasingException("Should Never Happen! " + result + " leases are updated!");
                                 con.rollback();
                                 throw le;
                              }

                              con.commit();
                           } catch (Exception var25) {
                              if (DEBUG) {
                                 p("Failed during commit or rollback of row locking transaction with exception: " + var25);
                              }
                           }
                        }

                        this.closeSQLConnection(con);
                     }
                  }

                  if (lockAcquired) {
                     try {
                        if (result < 0 || result > 1) {
                           le = new LeasingException("Should Never Happen! " + result + " leases are updated!");
                           con.rollback();
                           throw le;
                        }

                        con.commit();
                     } catch (Exception var27) {
                        if (DEBUG) {
                           p("Failed during commit or rollback of row locking transaction with exception: " + var27);
                        }
                     }
                  }

                  this.closeSQLConnection(con);
                  return var9;
               }

               if (lockAcquired) {
                  try {
                     if (result < 0 || result > 1) {
                        le = new LeasingException("Should Never Happen! " + result + " leases are updated!");
                        con.rollback();
                        throw le;
                     }

                     con.commit();
                  } catch (Exception var24) {
                     if (DEBUG) {
                        p("Failed during commit or rollback of row locking transaction with exception: " + var24);
                     }
                  }
               }

               this.closeSQLConnection(con);
               return var9;
            }

            if (lockAcquired) {
               try {
                  if (result < 0 || result > 1) {
                     le = new LeasingException("Should Never Happen! " + result + " leases are updated!");
                     con.rollback();
                     throw le;
                  }

                  con.commit();
               } catch (Exception var26) {
                  if (DEBUG) {
                     p("Failed during commit or rollback of row locking transaction with exception: " + var26);
                  }
               }
            }

            this.closeSQLConnection(con);
            return var9;
         }

         if (lockAcquired) {
            try {
               if (result < 0 || result > 1) {
                  le = new LeasingException("Should Never Happen! " + result + " leases are updated!");
                  con.rollback();
                  throw le;
               }

               con.commit();
            } catch (Exception var23) {
               if (DEBUG) {
                  p("Failed during commit or rollback of row locking transaction with exception: " + var23);
               }
            }
         }

         this.closeSQLConnection(con);
         return var9;
      }

      if (lockAcquired) {
         try {
            if (result < 0 || result > 1) {
               LeasingException le = new LeasingException("Should Never Happen! " + result + " leases are updated!");
               con.rollback();
               throw le;
            }

            con.commit();
         } catch (Exception var28) {
            if (DEBUG) {
               p("Failed during commit or rollback of row locking transaction with exception: " + var28);
            }
         }
      }

      this.closeSQLConnection(con);
      return result == 1;
   }

   private int insertLease(Connection con, String leaseName, String owner, int leaseTimeout) throws SQLException {
      String insertSQL = this.queryHelper.getAssumeLeaseQuery();
      PreparedStatement psInsert = null;

      int var8;
      try {
         psInsert = con.prepareStatement(insertSQL);
         if (this.supportsTimeouts) {
            psInsert.setQueryTimeout(this.queryTimeoutSeconds);
         }

         ArrayList insertParams = new ArrayList();
         insertParams.add(leaseName);
         insertParams.add(owner);
         insertParams.add(new Integer(leaseTimeout / 1000));
         this.bindStatementParameters(psInsert, insertParams);
         var8 = psInsert.executeUpdate();
      } finally {
         this.closePreparedStatement(psInsert);
      }

      return var8;
   }

   private int updateLease(Connection con, String leaseName, String owner, int leaseTimeout) throws SQLException {
      String updateSQL = this.queryHelper.getUpdateLeaseQuery();
      PreparedStatement psUpdate = null;

      int var8;
      try {
         psUpdate = con.prepareStatement(updateSQL);
         if (this.supportsTimeouts) {
            psUpdate.setQueryTimeout(this.queryTimeoutSeconds);
         }

         ArrayList updateParams = new ArrayList();
         updateParams.add(owner);
         updateParams.add(new Integer(leaseTimeout / 1000));
         updateParams.add(leaseName);
         this.bindStatementParameters(psUpdate, updateParams);
         var8 = psUpdate.executeUpdate();
      } finally {
         this.closePreparedStatement(psUpdate);
      }

      return var8;
   }

   private boolean lockExistingLease(Connection con, String leaseName) throws SQLException {
      String rowLockSQL = this.queryHelper.getLockLeaseQuery();
      PreparedStatement psRowLock = null;
      ResultSet rs = null;

      boolean var7;
      try {
         psRowLock = con.prepareStatement(rowLockSQL);
         if (this.supportsTimeouts) {
            psRowLock.setQueryTimeout(this.queryTimeoutSeconds);
         }

         ArrayList rowLockParams = new ArrayList();
         rowLockParams.add(leaseName);
         this.bindStatementParameters(psRowLock, rowLockParams);
         rs = psRowLock.executeQuery();
         var7 = rs.next();
      } finally {
         this.closeResultSet(rs);
         this.closePreparedStatement(psRowLock);
      }

      return var7;
   }

   private Connection getJDBCConnectionWithRetry() throws SQLException {
      Connection con = null;
      int retryCount = SQL_CONNECTION_RETRY_COUNT;
      boolean done = false;
      SQLException sqlException = null;

      do {
         try {
            con = this.getJDBCConnection();
            done = true;
         } catch (SQLException var8) {
            if (MigrationDebugLogger.isDebugEnabled()) {
               MigrationDebugLogger.debug("Unexpected exception", var8);
            }

            sqlException = var8;

            try {
               Thread.currentThread();
               Thread.sleep(SQL_CONNECTION_RETRY_DELAY);
            } catch (InterruptedException var7) {
            }

            --retryCount;
         }
      } while(!done && retryCount >= 0);

      if (sqlException != null) {
         throw sqlException;
      } else {
         return con;
      }
   }

   public void release(String leaseName, String owner) throws IOException {
      if (DEBUG) {
         p("release(" + leaseName + ")");
      }

      int status = this.abdicateLease(leaseName, owner);
      if (status < 0 || status > 1) {
         throw new IOException("Could not release: \"" + leaseName + "\"");
      }
   }

   public int renewAllLeases(int healthCheckPeriod, String owner) throws MissedHeartbeatException {
      if (DEBUG) {
         p("renewAllLeases(" + healthCheckPeriod + ")");
      }

      int leases = this.renewAllLeases(owner, healthCheckPeriod);
      if (leases < 0) {
         throw new MissedHeartbeatException("Could not heartbeat");
      } else {
         return leases;
      }
   }

   public int renewLeases(String owner, Set leases, int healthCheckPeriod) throws IOException {
      if (leases != null && leases.size() != 0) {
         ArrayList lst = new ArrayList();
         String sqlQuery = null;
         boolean useStaticRenewLeasesQuery = false;

         int varCount;
         try {
            varCount = leases.size();
            useStaticRenewLeasesQuery = this.getQueryHelper().useStaticRenewLeasesQuery(varCount);
            if (useStaticRenewLeasesQuery) {
               sqlQuery = this.getQueryHelper().getRenewLeasesQuery();
            } else {
               sqlQuery = this.getQueryHelper().getRenewLeasesQuery(varCount);
            }
         } catch (Exception var11) {
            if (MigrationDebugLogger.isDebugEnabled()) {
               MigrationDebugLogger.debug("Exception when retrieving Renew Leases Query: " + var11);
            }

            return -1;
         }

         lst.add(new Integer(healthCheckPeriod / 1000));
         varCount = 0;
         Iterator itr = leases.iterator();

         String bindValue;
         for(bindValue = null; itr.hasNext() && (!useStaticRenewLeasesQuery || varCount < 10); ++varCount) {
            bindValue = itr.next().toString();
            lst.add(bindValue);
            if (DEBUG) {
               MigrationDebugLogger.debug("Bind: (" + (2 + varCount) + ", " + bindValue + ")");
            }
         }

         for(int i = varCount; i < 10; ++i) {
            lst.add(bindValue);
            if (DEBUG) {
               MigrationDebugLogger.debug("Bind: (" + (2 + i) + ", " + bindValue + ")");
            }

            ++varCount;
         }

         lst.add(owner);
         return this.executeUpdate(sqlQuery, lst);
      } else {
         return 0;
      }
   }

   protected int renewAllLeases(String owner, int healthCheckPeriod) {
      String sqlQuery = null;

      try {
         sqlQuery = this.getQueryHelper().getRenewAllLeasesQuery();
      } catch (Exception var5) {
         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug("Exception when retrieving Renew All Leases Query: " + var5);
         }

         return -1;
      }

      ArrayList lst = new ArrayList();
      lst.add(new Integer(healthCheckPeriod / 1000));
      lst.add(owner);
      return this.executeUpdate(sqlQuery, lst);
   }

   protected int abdicateLease(String leaseName, String owner) {
      String sqlQuery = null;

      try {
         sqlQuery = this.getQueryHelper().getAbdicateLeaseQuery();
      } catch (Exception var5) {
         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug("Exception when retrieving Abdicate Lease Query: " + var5);
         }

         return -1;
      }

      ArrayList lst = new ArrayList();
      lst.add(leaseName);
      lst.add(owner);
      return this.executeUpdate(sqlQuery, lst);
   }

   protected int acquireLease(String leaseName, String owner) {
      String sqlQuery = null;

      try {
         sqlQuery = this.getQueryHelper().getAcquireLeaseQuery();
      } catch (Exception var5) {
         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug("Exception when retrieving Acquire Lease Query: " + var5);
         }

         return -1;
      }

      ArrayList lst = new ArrayList();
      lst.add(leaseName);
      return this.executeUpdate(sqlQuery, lst);
   }

   protected int assumeLease(String leaseName, String owner, int leaseTimeout) {
      String sqlQuery = null;

      try {
         sqlQuery = this.getQueryHelper().getAssumeLeaseQuery();
      } catch (Exception var6) {
         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug("Exception when retrieving Assume Lease Query: " + var6);
         }

         return -1;
      }

      ArrayList lst = new ArrayList();
      lst.add(leaseName);
      lst.add(owner);
      lst.add(new Integer(leaseTimeout / 1000));
      return this.executeUpdate(sqlQuery, lst);
   }

   public String findPreviousOwner(String leaseName) throws IOException {
      String query = null;

      try {
         query = this.getQueryHelper().getPreviousLeaseOwnerQuery();
      } catch (Exception var5) {
         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug("Exception when retrieving Previous Lease Owner Query: " + var5);
         }

         throw new IOException(var5.getMessage());
      }

      ArrayList lst = new ArrayList();
      lst.add(leaseName);
      String[] result = this.executeQuery(query, lst);
      return result.length > 0 ? result[0] : null;
   }

   public String findOwner(String leaseName) throws IOException {
      String query = null;

      try {
         query = this.getQueryHelper().getLeaseOwnerQuery();
      } catch (Exception var5) {
         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug("Exception when retrieving Lease Owner Query: " + var5);
         }

         throw new IOException(var5.getMessage());
      }

      ArrayList lst = new ArrayList();
      lst.add(leaseName);
      String[] result = this.executeQuery(query, lst);
      return result.length > 0 ? result[0] : null;
   }

   public String[] findExpiredLeases(int gracePeriod) {
      String query = null;

      try {
         query = this.getQueryHelper().getUnresponsiveMigratableServersQuery();
      } catch (Exception var5) {
         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug("Exception when retrieving Unresponsive Migratable Servers Query: " + var5);
         }

         return new String[0];
      }

      ArrayList lst = new ArrayList();
      lst.add(new Integer(gracePeriod / 1000));

      try {
         return this.executeQuery(query, lst);
      } catch (IOException var6) {
         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug("Exception when executing Unresponsive Migratable Servers Query: " + var6);
         }

         return new String[0];
      }
   }

   protected void closePreparedStatement(PreparedStatement ps) {
      if (ps != null) {
         try {
            ps.close();
         } catch (SQLException var3) {
            var3.printStackTrace();
         }
      }

   }

   protected void closeSQLConnection(Connection con) {
      if (con != null) {
         try {
            con.close();
         } catch (SQLException var3) {
            var3.printStackTrace();
         }
      }

   }

   protected void closeResultSet(ResultSet rs) {
      if (rs != null) {
         try {
            rs.close();
         } catch (SQLException var3) {
            var3.printStackTrace();
         }
      }

   }

   private Connection pingDB(Connection con) throws SQLException {
      boolean didClose = false;
      PreparedStatement ps = null;

      try {
         String query = this.getQueryHelper().getLeaseOwnerQuery();
         ps = con.prepareStatement(query);
         if (this.supportsTimeouts) {
            ps.setQueryTimeout(this.queryTimeoutSeconds);
         }

         ps.setString(1, "nonexistantlease");
         ps.executeQuery();
         if (this.pingDBErrorLogged) {
            if (MigrationDebugLogger.isDebugEnabled()) {
               MigrationDebugLogger.debug("connection error was logged but now it's resolved.", (Throwable)null);
            }

            this.pingDBErrorLogged = false;
         }
      } catch (SQLException var8) {
         if (!this.pingDBErrorLogged) {
            ClusterExtensionLogger.logFailedToExecutePingSQL(var8.getMessage());
            this.pingDBErrorLogged = true;
         }

         didClose = true;
         this.closeSQLConnection(con);
         throw var8;
      } finally {
         if (!didClose) {
            this.closePreparedStatement(ps);
         }

      }

      return con;
   }

   protected Connection getJDBCConnection() throws SQLException {
      return this.getJDBCConnection(true);
   }

   protected Connection getJDBCConnection(boolean pingDB) throws SQLException {
      Connection con = null;
      if (this.ds != null) {
         try {
            con = this.getConnectionFromDS(this.ds);
         } catch (SQLException var18) {
            if (!ManagementService.getRuntimeAccess(kernelId).getServerRuntime().isShuttingDown()) {
               throw var18;
            }
         }
      } else {
         JDBCDataSourceParamsBean params = this.jdbcSystemResourceMBean.getJDBCResource().getJDBCDataSourceParams();
         String[] dataSourceNames = params.getJNDINames();
         if (dataSourceNames != null && dataSourceNames.length > 0) {
            Context ctx = null;

            try {
               ctx = new InitialContext();
               DataSource datasource = (DataSource)ctx.lookup(dataSourceNames[0]);
               con = this.getConnectionFromDS(datasource);
               this.ds = datasource;
            } catch (NamingException var19) {
            } catch (SQLException var20) {
               if (!ManagementService.getRuntimeAccess(kernelId).getServerRuntime().isShuttingDown()) {
                  throw var20;
               }
            } finally {
               try {
                  if (ctx != null) {
                     ctx.close();
                  }
               } catch (NamingException var17) {
               }

            }
         }
      }

      if (con == null) {
         con = this.createDirectConnection(this.jdbcSystemResourceMBean.getJDBCResource());
         if (pingDB) {
            con = this.pingDB(con);
         }
      }

      return con;
   }

   private Connection getConnectionFromDS(DataSource datasource) throws SQLException {
      try {
         return this.pingDB(datasource.getConnection());
      } catch (SQLException var3) {
         if (isMultiDataSource(this.jdbcSystemResourceMBean.getJDBCResource())) {
            return this.pingDB(datasource.getConnection());
         } else {
            throw var3;
         }
      }
   }

   private Connection createDirectConnection(JDBCDataSourceBean resource) throws SQLException {
      if (!isMultiDataSource(resource)) {
         return this.createDirectConnection(resource.getJDBCDriverParams());
      } else {
         SQLException exception = null;
         String datasources = resource.getJDBCDataSourceParams().getDataSourceList();
         StringTokenizer tokenizer = new StringTokenizer(datasources, ",");
         JDBCSystemResourceMBean[] resources = ManagementService.getRuntimeAccess(kernelId).getDomain().getJDBCSystemResources();

         while(tokenizer.hasMoreTokens()) {
            try {
               String poolName = tokenizer.nextToken().trim();

               for(int lcv = 0; lcv < resources.length; ++lcv) {
                  JDBCDataSourceBean currBean = resources[lcv].getJDBCResource();
                  if (currBean != null && poolName.equals(currBean.getName())) {
                     return this.createDirectConnection(currBean);
                  }
               }
            } catch (SQLException var9) {
               exception = var9;
            }
         }

         if (exception != null) {
            throw exception;
         } else {
            throw new SQLException("No living database found!");
         }
      }
   }

   private Connection createDirectConnection(JDBCDriverParamsBean bean) throws SQLException {
      String driverClassName = bean.getDriverName();
      String password = bean.getPassword();
      Properties properties = JDBCUtil.getProperties(bean.getProperties().getProperties());
      if (properties != null && password != null) {
         properties.setProperty("password", password);
      }

      try {
         Class clazz = Class.forName(driverClassName);
         Constructor driverClassConstructor = clazz.getConstructor((Class[])null);
         Driver driverInstance = (Driver)driverClassConstructor.newInstance((Object[])null);
         Connection conn = driverInstance.connect(bean.getUrl(), properties);
         if (conn == null) {
            throw new SQLException("Failed to get connection to the database due to bad configuration.");
         } else {
            return conn;
         }
      } catch (Throwable var9) {
         if (DEBUG) {
            var9.printStackTrace();
         }

         throw new SQLException("Failed to get connection to the database " + var9.getMessage());
      }
   }

   public static LeasingBasis createBasis(ServerMBean server, JDBCSystemResourceMBean jdbcResource, int unresponsiveTimeout, String tableName) throws ServiceFailureException {
      checkSystemResource(server, jdbcResource);
      if (isMultiDataSource(jdbcResource.getJDBCResource())) {
         JDBCDataSourceBean resource = jdbcResource.getJDBCResource();
         String datasources = resource.getJDBCDataSourceParams().getDataSourceList();
         String driverName = null;
         StringTokenizer tokenizer = new StringTokenizer(datasources, ",");
         JDBCSystemResourceMBean[] resources = ManagementService.getRuntimeAccess(kernelId).getDomain().getJDBCSystemResources();
         JDBCSystemResourceMBean MultiDataSourceResource = null;

         while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            for(int lcv = 0; lcv < resources.length; ++lcv) {
               JDBCDataSourceBean currBean = resources[lcv].getJDBCResource();
               if (currBean != null && token.equals(currBean.getName())) {
                  MultiDataSourceResource = resources[lcv];
                  break;
               }
            }

            String errMessage;
            if (MultiDataSourceResource == null) {
               ClusterLogger.logMissingJDBCConfigurationForAutoMigration(server.getName());
               errMessage = "Invalid migratable server configuration. The pool named  " + token + " which is supposed to be a part of the MultiDataSource was not found. ";
               throw new ServiceFailureException(errMessage);
            }

            checkSystemResource(server, MultiDataSourceResource);
            if (driverName == null) {
               driverName = resource.getJDBCDriverParams().getDriverName();
            } else if (!driverName.equals(resource.getJDBCDriverParams().getDriverName())) {
               ClusterLogger.logMissingJDBCConfigurationForAutoMigration(server.getName());
               errMessage = "Invalid migratable server configuration. All pools in   a MultiDataSource for Singleton Services must have the same driver.";
               throw new ServiceFailureException(errMessage);
            }
         }
      }

      return new DatabaseLeasingBasis(jdbcResource, unresponsiveTimeout, tableName);
   }

   private static String getDriverName(JDBCSystemResourceMBean jdbcResource) {
      if (!isMultiDataSource(jdbcResource.getJDBCResource())) {
         if (DEBUG) {
            p("Driver for " + jdbcResource + " is" + jdbcResource.getJDBCResource().getJDBCDriverParams().getDriverName());
         }

         return jdbcResource.getJDBCResource().getJDBCDriverParams().getDriverName();
      } else {
         String datasources = jdbcResource.getJDBCResource().getJDBCDataSourceParams().getDataSourceList();
         StringTokenizer tokenizer = new StringTokenizer(datasources, ",");
         JDBCSystemResourceMBean[] resources = ManagementService.getRuntimeAccess(kernelId).getDomain().getJDBCSystemResources();
         JDBCSystemResourceMBean MultiDataSourceResource = null;

         while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            for(int lcv = 0; lcv < resources.length; ++lcv) {
               JDBCDataSourceBean currBean = resources[lcv].getJDBCResource();
               if (currBean != null && token.equals(currBean.getName())) {
                  MultiDataSourceResource = resources[lcv];
                  return getDriverName(MultiDataSourceResource);
               }
            }
         }

         throw new AssertionError("No driver found for jdbc resource: " + jdbcResource);
      }
   }

   private static boolean isMultiDataSource(JDBCDataSourceBean resource) {
      return resource.getJDBCDriverParams().getDriverName() == null && resource.getJDBCDataSourceParams() != null && resource.getJDBCDataSourceParams().getDataSourceList() != null;
   }

   private static void checkSystemResource(ServerMBean server, JDBCSystemResourceMBean jdbcResource) throws ServiceFailureException {
      if (jdbcResource == null) {
         ClusterLogger.logMissingJDBCConfigurationForAutoMigration(server.getName());
         String errMessage = "Invalid migratable server configuration. The  DataSourceForAutomaticMigration was not set. Please refer to cluster documents for more information";
         throw new ServiceFailureException(errMessage);
      } else {
         JDBCDataSourceBean resource = jdbcResource.getJDBCResource();
         String[] dataSourceNames = resource.getJDBCDataSourceParams().getJNDINames();
         if (dataSourceNames != null && dataSourceNames.length != 0) {
            if (resource.getJDBCDriverParams().getDriverName() == null && !isMultiDataSource(resource)) {
               ClusterLogger.logMissingJDBCConfigurationForAutoMigration(server.getName());
               throw new ServiceFailureException("Invalid migratable server configuration, please use a fully-populated JDBC resource");
            }
         } else {
            ClusterLogger.logMissingJDBCConfigurationForAutoMigration(server.getName());
            throw new ServiceFailureException("Invalid migratable server configuration");
         }
      }
   }

   private static final void p(String msg) {
      MigrationDebugLogger.debug(Thread.currentThread().getName() + " <DatabaseLeasingBasis>: " + msg);
   }

   private void dumpDBTable() {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      String sqlQuery = null;
      int retryCount = SQL_CONNECTION_RETRY_COUNT;
      boolean transactionCompleted = false;

      do {
         try {
            con = this.getJDBCConnection();
            sqlQuery = this.getQueryHelper().getDumpDBTableQuery();
            if (DEBUG) {
               MigrationDebugLogger.debug("Query: " + sqlQuery);
            }

            ps = con.prepareStatement(sqlQuery);
            if (this.supportsTimeouts) {
               ps.setQueryTimeout(this.queryTimeoutSeconds);
            }

            rs = ps.executeQuery();
            transactionCompleted = true;

            while(rs.next()) {
               String server = rs.getString("SERVER");
               String instance = rs.getString("INSTANCE");
               String domain = rs.getString("DOMAINNAME");
               String cluster = rs.getString("CLUSTERNAME");
               Date date = rs.getTimestamp("TIMEOUT");
               MigrationDebugLogger.debug(server + "\t" + instance + "\t" + domain + "\t" + cluster + "\t" + date);
            }
         } catch (SQLException var17) {
            if (MigrationDebugLogger.isDebugEnabled()) {
               MigrationDebugLogger.debug("Attempted Query: " + sqlQuery);
               MigrationDebugLogger.debug("Unexpected exception", var17);
            }

            try {
               Thread.currentThread();
               Thread.sleep(SQL_CONNECTION_RETRY_DELAY);
            } catch (InterruptedException var16) {
            }

            --retryCount;
         } finally {
            this.closeResultSet(rs);
            this.closePreparedStatement(ps);
            this.closeSQLConnection(con);
         }
      } while(!transactionCompleted && retryCount >= 0);

   }

   protected int executeUpdate(String sqlQuery, ArrayList params) {
      Connection con = null;
      PreparedStatement ps = null;
      int retryCount = SQL_CONNECTION_RETRY_COUNT;
      boolean transactionCompleted = false;
      boolean isDebugOn = MigrationDebugLogger.isDebugEnabled();

      do {
         try {
            con = this.getJDBCConnection();
            if (isDebugOn) {
               MigrationDebugLogger.debug("DML: " + sqlQuery);
            }

            ps = con.prepareStatement(sqlQuery);
            if (this.supportsTimeouts) {
               ps.setQueryTimeout(this.queryTimeoutSeconds);
            }

            this.bindStatementParameters(ps, params);
            int result = ps.executeUpdate();
            transactionCompleted = true;
            int var21 = result;
            return var21;
         } catch (SQLTimeoutException var18) {
            try {
               Thread.currentThread();
               Thread.sleep(SQL_CONNECTION_RETRY_DELAY);
            } catch (InterruptedException var17) {
            }

            --retryCount;
            String paramList = params != null ? params.toString() : EMPTY_STR;
            if (retryCount >= 0) {
               ClusterExtensionLogger.logSqlTimeoutErrorWithRetry(sqlQuery, paramList, var18);
            } else {
               ClusterExtensionLogger.logSqlTimeoutError(sqlQuery, paramList, var18);
            }
         } catch (SQLException var19) {
            if (MigrationDebugLogger.isDebugEnabled()) {
               MigrationDebugLogger.debug("Attempted DML: " + sqlQuery);
               MigrationDebugLogger.debug("Unexpected exception", var19);
            }

            try {
               Thread.currentThread();
               Thread.sleep(SQL_CONNECTION_RETRY_DELAY);
            } catch (InterruptedException var16) {
            }

            --retryCount;
         } finally {
            this.closePreparedStatement(ps);
            this.closeSQLConnection(con);
         }
      } while(!transactionCompleted && retryCount >= 0);

      return -1;
   }

   protected String[] executeQuery(String sqlQuery, ArrayList params) throws IOException {
      Connection con = null;
      PreparedStatement ps = null;
      int retryCount = SQL_CONNECTION_RETRY_COUNT;
      boolean transactionCompleted = false;
      ResultSet rs = null;
      SQLException sqlException = null;
      ArrayList result = new ArrayList();
      boolean isDebugOn = MigrationDebugLogger.isDebugEnabled();

      do {
         try {
            con = this.getJDBCConnection();
            if (isDebugOn) {
               MigrationDebugLogger.debug("Query: " + sqlQuery);
            }

            ps = con.prepareStatement(sqlQuery);
            if (this.supportsTimeouts) {
               ps.setQueryTimeout(this.queryTimeoutSeconds);
            }

            this.bindStatementParameters(ps, params);
            rs = ps.executeQuery();
            transactionCompleted = true;

            while(rs.next()) {
               result.add(rs.getString(1));
            }

            String[] var11 = (String[])((String[])result.toArray(new String[result.size()]));
            return var11;
         } catch (SQLTimeoutException var21) {
            try {
               Thread.currentThread();
               Thread.sleep(SQL_CONNECTION_RETRY_DELAY);
            } catch (InterruptedException var20) {
            }

            --retryCount;
            String paramList = params != null ? params.toString() : EMPTY_STR;
            if (retryCount >= 0) {
               ClusterExtensionLogger.logSqlTimeoutErrorWithRetry(sqlQuery, paramList, var21);
            } else {
               ClusterExtensionLogger.logSqlTimeoutError(sqlQuery, paramList, var21);
            }

            sqlException = var21;
         } catch (SQLException var22) {
            if (isDebugOn) {
               MigrationDebugLogger.debug("Attempted Query: " + sqlQuery);
               MigrationDebugLogger.debug("Unexpected exception", var22);
            }

            sqlException = var22;

            try {
               Thread.currentThread();
               Thread.sleep(SQL_CONNECTION_RETRY_DELAY);
            } catch (InterruptedException var19) {
            }

            --retryCount;
         } finally {
            this.closeResultSet(rs);
            this.closePreparedStatement(ps);
            this.closeSQLConnection(con);
         }
      } while(!transactionCompleted && retryCount >= 0);

      if (sqlException != null) {
         throw new IOException(((SQLException)sqlException).getMessage());
      } else {
         return new String[0];
      }
   }

   private void bindStatementParameters(PreparedStatement ps, ArrayList params) throws SQLException {
      boolean isDebugOn = MigrationDebugLogger.isDebugEnabled();
      int numParams = params.size();
      StringBuffer boundParams = null;
      if (isDebugOn) {
         boundParams = new StringBuffer();
      }

      for(int i = 1; i <= numParams; ++i) {
         Object param = params.get(i - 1);
         ps.setObject(i, param);
         if (isDebugOn) {
            if (i != 1) {
               boundParams.append(", ");
            }

            boundParams.append('\'').append(param).append('\'');
         }
      }

      if (isDebugOn && numParams > 0) {
         MigrationDebugLogger.debug("Bound (" + boundParams.toString() + ')');
      }

   }

   private TableCreationPolicy getTableCreationPolicy(ClusterMBean cluster) {
      String property = "weblogic.cluster.singleton.AutoMigrationTableCreationPolicy";
      String value = System.getProperty(property);
      if (value == null || value.trim().length() == 0) {
         if (cluster == null) {
            return DatabaseLeasingBasis.TableCreationPolicy.DISABLED;
         }

         value = cluster.getAutoMigrationTableCreationPolicy();
      }

      TableCreationPolicy result = DatabaseLeasingBasis.TableCreationPolicy.getPolicy(value);
      if (result != null) {
         return result;
      } else {
         StringBuilder sb = new StringBuilder();
         sb.append(property).append(": invalid value: ").append(value);
         sb.append("; valid values: ");
         int i = 0;
         TableCreationPolicy[] var7 = DatabaseLeasingBasis.TableCreationPolicy.values();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            TableCreationPolicy policy = var7[var9];
            if (i++ > 0) {
               sb.append(", ");
            }

            sb.append(policy);
         }

         throw new IllegalArgumentException(sb.toString());
      }
   }

   private String getTableCreationDDLFile(ClusterMBean cluster) {
      String value = System.getProperty("weblogic.cluster.singleton.AutoMigrationTableCreationDDLFile");
      if ((value == null || value.trim().length() == 0) && cluster != null) {
         value = cluster.getAutoMigrationTableCreationDDLFile();
      }

      return value;
   }

   private void initializeTable() {
      if (this.tableCreationPolicy != DatabaseLeasingBasis.TableCreationPolicy.DISABLED) {
         try {
            Connection conn = this.getJDBCConnection(false);
            Throwable var2 = null;

            try {
               DatabaseMetaData metaData = conn.getMetaData();
               if (JDBCHelper.tableExists(conn, metaData, this.TABLE_NAME)) {
                  ClusterExtensionLogger.logTableAlreadyExists(this.TABLE_NAME);
                  this.initializeTableSuccess = true;
               } else {
                  this.createTable(conn, metaData, JDBCHelper.getDBMSType(metaData, (String[])null));
               }
            } catch (Throwable var12) {
               var2 = var12;
               throw var12;
            } finally {
               if (conn != null) {
                  if (var2 != null) {
                     try {
                        conn.close();
                     } catch (Throwable var11) {
                        var2.addSuppressed(var11);
                     }
                  } else {
                     conn.close();
                  }
               }

            }
         } catch (SQLException var14) {
            ClusterExtensionLogger.logDatabaseAccessError(var14);
            TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(this, 30000L);
         }

      }
   }

   private void createTable(Connection conn, DatabaseMetaData metaData, int dbmsType) {
      String fileName = this.tableCreationDDLFile;
      if (fileName == null) {
         String fileNamePrefix;
         switch (dbmsType) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 12:
               if (DEBUG) {
                  p("The AutomatMigrationTableCreationDDLFile property is not set, use default ddl file.");
               }

               fileNamePrefix = System.getenv("WL_HOME") + "/server/db/" + JDBCHelper.getDBMSTypeString(dbmsType) + "/";
               if (dbmsType == 1) {
                  fileNamePrefix = fileNamePrefix + "920/";
               }

               fileName = fileNamePrefix + "leasing.ddl";
               break;
            case 2:
            case 8:
            case 10:
            case 11:
            default:
               fileNamePrefix = "";
               String dn = "";

               try {
                  fileNamePrefix = metaData.getDatabaseProductName();
                  dn = metaData.getDriverName();
               } catch (SQLException var18) {
               }

               ClusterExtensionLogger.logUnknownDatabase(JDBCHelper.getDBMSTypeString(dbmsType), fileNamePrefix, dn);
               return;
         }
      }

      if (DEBUG) {
         p("loading create table ddl in " + fileName + " from file path");
      }

      try {
         InputStream is = new FileInputStream(fileName);
         Throwable var26 = null;

         try {
            this.executeDDLStream(conn, metaData, dbmsType, is);
            ClusterExtensionLogger.logTableCreateSuccess(this.TABLE_NAME, fileName);
            this.initializeTableSuccess = true;
         } catch (Throwable var21) {
            var26 = var21;
            throw var21;
         } finally {
            if (is != null) {
               if (var26 != null) {
                  try {
                     is.close();
                  } catch (Throwable var20) {
                     var26.addSuppressed(var20);
                  }
               } else {
                  is.close();
               }
            }

         }
      } catch (FileNotFoundException var23) {
         ClusterExtensionLogger.logDDLFileNotFound(fileName);
      } catch (IOException | SQLException var24) {
         boolean tableExists = false;

         try {
            tableExists = JDBCHelper.tableExists(conn, metaData, this.TABLE_NAME);
         } catch (SQLException var19) {
         }

         if (tableExists) {
            ClusterExtensionLogger.logTableAlreadyExists(this.TABLE_NAME);
            this.initializeTableSuccess = true;
         } else {
            ClusterExtensionLogger.logTableCreateFailed(fileName, var24);
            TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(this, 30000L);
         }
      }

   }

   private void executeDDLStream(Connection conn, DatabaseMetaData metaData, int dbmsType, InputStream is) throws SQLException, IOException {
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));

      String line;
      while((line = gatherLine(reader)) != null) {
         if (!line.toUpperCase().contains("DROP ")) {
            line = line.replaceAll("ACTIVE", JDBCHelper.getDDLIdentifier(metaData, this.TABLE_NAME, dbmsType));
            line = line.replaceAll("\\s+", " ");
            if (DEBUG) {
               p("executeDDLString <" + line + ">");
            }

            Statement statement = conn.createStatement();
            Throwable var8 = null;

            try {
               statement.execute(line);
            } catch (Throwable var17) {
               var8 = var17;
               throw var17;
            } finally {
               if (statement != null) {
                  if (var8 != null) {
                     try {
                        statement.close();
                     } catch (Throwable var16) {
                        var8.addSuppressed(var16);
                     }
                  } else {
                     statement.close();
                  }
               }

            }
         }
      }

   }

   private static String gatherLine(BufferedReader br) throws IOException {
      StringBuffer result = null;
      char terminator = 59;

      String line;
      while((line = br.readLine()) != null) {
         line = line.trim();
         if (line.length() > 0 && !line.startsWith("#")) {
            char lastChar = line.charAt(line.length() - 1);
            boolean termination = lastChar == terminator;
            if (result == null) {
               result = new StringBuffer("");
            }

            result.append(termination ? line.substring(0, line.length() - 1) : line + " ");
            if (termination) {
               break;
            }
         }
      }

      return result != null ? result.toString() : null;
   }

   public void timerExpired(Timer timer) {
      if (!this.initializeTableSuccess) {
         this.initializeTable();
      }

   }

   private static enum TableCreationPolicy {
      DISABLED("Disabled"),
      ALWAYS("Always");

      private final String constantName;

      private TableCreationPolicy(String constantName) {
         this.constantName = constantName;
      }

      public String toString() {
         return this.constantName;
      }

      public static TableCreationPolicy getPolicy(String constantName) {
         TableCreationPolicy[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            TableCreationPolicy policy = var1[var3];
            if (policy.constantName.equals(constantName)) {
               return policy;
            }
         }

         return null;
      }
   }
}
