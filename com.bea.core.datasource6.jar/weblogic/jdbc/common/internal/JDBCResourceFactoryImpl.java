package weblogic.jdbc.common.internal;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import javax.sql.XAConnection;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ResourceSystemException;
import weblogic.jdbc.JDBCLogger;
import weblogic.utils.wrapper.Wrapper;

public abstract class JDBCResourceFactoryImpl implements JDBCResourceFactory {
   private boolean testValidated = false;
   private boolean testValidationFailed = false;
   private String testQuery = null;
   protected JDBCConnectionPool pool;
   protected int secondsToTrustAnIdlePoolConnection = 0;
   protected Properties poolParams;
   private boolean finishFind = false;
   protected boolean hasAbortMethod = false;
   protected Method abort = null;
   protected boolean hasPingDatabaseMethod = false;
   protected Method pingDatabase = null;
   protected int pingDatabaseOk;
   protected boolean hasOracleProxyConnectionCloseMethod = false;
   protected Method oracleProxyConnectionClose = null;
   protected Method oracleAttachServerConnection = null;
   protected Method oracleDetachServerConnection = null;
   protected Method oracleBeginRequest = null;
   protected Method oracleIsUsable = null;
   protected Method oraclePhysicalConnectionWithin = null;
   protected Method getServerSessionInfo = null;
   protected int proxySession;
   protected boolean hasOracleOpenProxySession = false;
   protected Method oracleOpenProxySession = null;
   protected boolean hasSetProxyObject = false;
   protected String proxyUserName;
   protected String proxyUserPassword;
   protected int proxyTypeUserName;
   protected Method abort41;
   protected Method oracleGetTransactionState;
   protected Object oracleTransactionStateTransactionStarted;
   protected Method beginRequest43;
   protected Method endRequest43;
   protected boolean ignore_init_fails = false;
   protected boolean gotCommandlineProps = false;
   protected boolean endLocalTxOnXaConWithCommit = false;
   protected boolean endLocalTxOnNonXaConWithCommit = false;
   protected boolean endLocalTxOnNonXaConWithRollback = false;
   protected int desiredDefaultIsolationLevel = -1;
   protected int delaySecs = 0;
   protected int maxConcurrentCreateRequests;
   protected Semaphore createRequestsPermits;
   protected int concurrentCreateRequestsTimeoutSeconds;
   protected int vendorId = -1;
   protected String poolname = "";
   protected String appname = null;
   protected String moduleName = null;
   protected String compName = null;
   protected String driver = "";
   protected String url = "";
   protected Properties driverProps = null;
   protected long connTime = 0L;
   protected String rootContainerName;
   protected String rootServiceName;
   private boolean weKnowWhetherIsValidIsSupported = false;
   private boolean isValidIsSupported = false;

   public String getTestQuery() {
      return this.testQuery;
   }

   public JDBCConnectionPool getPool() {
      return this.pool;
   }

   public Properties getPoolParams() {
      return this.poolParams;
   }

   public void clearTestValidation() {
      this.testValidated = false;
   }

   public boolean isTestValidationFailed() {
      return this.testValidationFailed;
   }

   public final void initializeTest(ConnectionEnv cc, String newQuery) throws ResourceException {
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > PRF:initializeTest (10) newQuery = " + newQuery);
      }

      synchronized(this) {
         if (!this.testValidated) {
            this.testValidated = true;

            try {
               DatabaseMetaData metaData = cc.conn.jconn.getMetaData();
               if (metaData != null) {
                  JDBCLogger.logDatabaseInfo(cc.getPoolName(), metaData.getDatabaseProductName(), metaData.getDatabaseProductVersion());
                  JDBCLogger.logDriverInfo(cc.getPoolName(), metaData.getDriverName(), metaData.getDriverVersion());
               }
            } catch (SQLException var6) {
            }

            String fixedNewQuery = this.checkTestQuery(cc.getConnectionPool(), newQuery);
            if (fixedNewQuery == null) {
               this.testQuery = null;
               if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                  JDBCUtil.JDBCInternal.debug(" < PRF:initializeTest (100.1)");
               }

            } else {
               if (cc.testDynamic(fixedNewQuery) == -1) {
                  if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                     JDBCUtil.JDBCInternal.debug("* PRF:initializeTest (20)");
                  }

                  this.refreshResource(cc);
                  cc.initialize();
                  if (cc.testDynamic(fixedNewQuery) == -1) {
                     JDBCLogger.logTestVerifFailed(this.getPool().getName(), fixedNewQuery);
                     if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                        JDBCUtil.JDBCInternal.debug(" <* PRF:initializeTest (100.2)");
                     }

                     this.testValidationFailed = true;
                     throw new IllegalArgumentException("setTestTableName failed");
                  }
               }

               this.testQuery = fixedNewQuery;
               if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
                  JDBCUtil.JDBCInternal.debug(" < PRF:initializeTest (100.3)");
               }

            }
         } else {
            if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
               JDBCUtil.JDBCInternal.debug(" < PRF:initializeTest (100.4)");
            }

         }
      }
   }

   public void setSecondsToTrustAnIdlePoolConnection(int newVal) {
      this.secondsToTrustAnIdlePoolConnection = newVal;
   }

   public void setStatementCacheSize(int newVal) {
      this.poolParams.setProperty("PSCacheSize", Integer.toString(newVal));
   }

   public PooledResource createResource(PooledResourceInfo connInfo) throws ResourceException {
      if (this.pool.getOracleVersion() >= 1200) {
         this.endLocalTxOnNonXaConWithCommit = false;
      } else if (this.pool.getOracleVersion() != -1) {
         this.endLocalTxOnNonXaConWithCommit = true;
      }

      this.getCommandLineProps();
      if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
         JDBCUtil.JDBCInternal.debug(" > CEF:createResource (10)");
      }

      ConnectionEnv cc = null;
      boolean needDetach = false;

      ConnectionEnv var15;
      try {
         cc = this.instantiatePooledResource(this.poolParams);
         cc.setConnectionPool(this.pool);
         this.setDataSourceProperties(cc, connInfo);
         this.setXADataSourceProperties(cc, connInfo);
         cc.conn = new ConnectionHolder();
         cc.setPoolName(this.poolname);
         cc.setAppName(this.appname);
         cc.setModuleName(this.moduleName);
         cc.setCompName(this.compName);
         cc.setVendorId(this.vendorId);
         this.setConnection(cc, connInfo);
         cc.setInitialIsolationLevel(cc.conn.jconn.getTransactionIsolation());
         this.setDbVersion(cc);
         cc.setSecondsToTrustAnIdlePoolConnection(this.secondsToTrustAnIdlePoolConnection);
         cc.setConnectTime(this.connTime);
         if (this.ignore_init_fails) {
            cc.ignore_init_fails = true;
         }

         cc.setupInitSQL();
         cc.initStmtCache();
         this.findVendorMethod(cc);
         cc.conn.hasAbortMethod = this.hasAbortMethod;
         cc.conn.abort = this.abort;
         cc.conn.hasPingDatabaseMethod = this.hasPingDatabaseMethod;
         cc.conn.pingDatabase = this.pingDatabase;
         cc.conn.pingDatabaseOk = this.pingDatabaseOk;
         cc.conn.hasOracleProxyConnectionCloseMethod = this.hasOracleProxyConnectionCloseMethod;
         cc.conn.oracleProxyConnectionClose = this.oracleProxyConnectionClose;
         cc.conn.oracleAttachServerConnection = this.oracleAttachServerConnection;
         cc.conn.oracleDetachServerConnection = this.oracleDetachServerConnection;
         cc.conn.oraclePhysicalConnectionWithin = this.oraclePhysicalConnectionWithin;
         cc.conn.oracleBeginRequest = this.oracleBeginRequest;
         cc.conn.oracleIsUsable = this.oracleIsUsable;
         cc.conn.getServerSessionInfo = this.getServerSessionInfo;
         cc.conn.abort41 = this.abort41;
         cc.conn.abort41Supported = this.abort41 != null;
         cc.conn.beginRequest43 = this.beginRequest43;
         cc.conn.endRequest43 = this.endRequest43;
         cc.conn.proxySession = this.proxySession;
         cc.conn.hasOracleOpenProxySession = this.hasOracleOpenProxySession;
         cc.conn.hasSetProxyObject = this.hasSetProxyObject;
         cc.conn.oracleOpenProxySession = this.oracleOpenProxySession;
         cc.conn.proxyUserName = this.proxyUserName;
         cc.conn.proxyUserPassword = this.proxyUserPassword;
         cc.conn.proxyTypeUserName = this.proxyTypeUserName;
         cc.endLocalTxOnXaConWithCommit = this.endLocalTxOnXaConWithCommit;
         cc.endLocalTxOnNonXaConWithCommit = this.endLocalTxOnNonXaConWithCommit;
         cc.endLocalTxOnNonXaConWithRollback = this.endLocalTxOnNonXaConWithRollback;
         cc.conn.oracleGetTransactionState = this.oracleGetTransactionState;
         cc.conn.oracleTransactionStateTransactionStarted = this.oracleTransactionStateTransactionStarted;
         if (this.url == null || this.url.equals("")) {
            throw new ResourceException("URL is not set");
         }

         if (this.oracleAttachServerConnection != null) {
            String connectionClass = null;
            if (this.driverProps != null) {
               connectionClass = this.driverProps.getProperty("oracle.jdbc.DRCPConnectionClass");
            }

            boolean hasConnectionClass = connectionClass != null;
            boolean hasPool = this.url.toUpperCase().matches("(.*\\(SERVER=POOLED\\).*)|(.*:POOLED)");
            if (hasPool != hasConnectionClass) {
               JDBCLogger.logDrcpError();
               throw new ResourceException("oracle.jdbc.DRCPConnectionClass must be specified if and only if a DRCP pool is defined");
            }

            cc.drcpEnabled = hasConnectionClass;
            cc.OracleAttachServerConnection();
            needDetach = true;
         }

         if (this.pool.isSharedPool()) {
            this.rootContainerName = this.pool.getOracleHelper().getPDBName(cc);
            this.rootServiceName = this.pool.getOracleHelper().getServiceName(cc);
            SwitchingContext sc = new SwitchingContextImpl(this.pool, this.rootContainerName, this.rootServiceName);
            cc.setRootSwitchingContext(sc);
            cc.setSwitchingContext(sc);
            if (this.pool.getRootSwitchingContext() == null) {
               this.pool.setRootSwitchingContext(sc);
            }
         }

         cc.setConnectionLate();
         cc.setSupportIsValid(this.getSupportIsValid(cc.conn.jconn));

         try {
            this.initializeTest(cc, this.poolParams.getProperty("testName"));
         } catch (IllegalArgumentException var11) {
         }

         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" < CEF:createResource (20)");
         }

         var15 = cc;
      } catch (Exception var12) {
         if (JDBCUtil.JDBCInternal.isDebugEnabled()) {
            JDBCUtil.JDBCInternal.debug(" <* CEF:createResource (30) throws " + var12.getMessage());
         }

         if (var12 instanceof ResourceSystemException) {
            throw (ResourceSystemException)var12;
         }

         throw new ResourceException(var12);
      } finally {
         if (needDetach) {
            this.doDRCPDetachIfNeeded(cc);
         }

      }

      return var15;
   }

   protected void doDRCPDetachIfNeeded(ConnectionEnv cc) throws ResourceException {
      if (cc.drcpEnabled) {
         try {
            cc.OracleDetachServerConnection();
         } catch (Exception var3) {
            cc.forceDestroy();
            throw new ResourceException("Failed to do initial detach of new DRCP connection: " + var3.getMessage());
         }
      }

   }

   protected void setConnection(ConnectionEnv cc, PooledResourceInfo connInfo) throws ResourceException {
   }

   protected void setXADataSourceProperties(ConnectionEnv cc, PooledResourceInfo connInfo) throws SQLException {
   }

   protected void setDataSourceProperties(ConnectionEnv cc, PooledResourceInfo connInfo) throws Exception {
   }

   protected void setDbVersion(ConnectionEnv cc) {
   }

   protected void initialize() {
      String delay = this.poolParams.getProperty("createDelay");
      if (delay != null) {
         this.delaySecs = Integer.parseInt(delay);
      }

      String sttaic = this.poolParams.getProperty("secondsToTrustAnIdlePoolConnection");
      if (sttaic != null) {
         try {
            this.secondsToTrustAnIdlePoolConnection = Integer.parseInt(sttaic);
         } catch (Exception var8) {
         }
      }

      String drivername = this.poolParams.getProperty("drivername");
      if (drivername != null && (this.vendorId = VendorId.get(drivername)) != -1) {
         JDBCUtil.JDBCInternal.debug("Using Driver " + VendorId.toString(this.vendorId));
      } else {
         this.vendorId = VendorId.get(this.driver);
      }

      String desDefaultIsolationLevel = "TRANSACTION_NONE";
      if (this.driverProps != null) {
         desDefaultIsolationLevel = (String)((String)this.driverProps.remove("desiredtxisolevel"));
         String maxProp = this.driverProps.getProperty("weblogic.jdbc.maxConcurrentCreateRequests");
         if (maxProp != null) {
            int max = Integer.parseInt(maxProp);
            if (max > 0) {
               this.maxConcurrentCreateRequests = max;
               this.createRequestsPermits = new Semaphore(max, true);
            }

            String timeoutProp = this.driverProps.getProperty("weblogic.jdbc.concurrentCreateRequestsTimeoutSeconds", "60");
            this.concurrentCreateRequestsTimeoutSeconds = Integer.parseInt(timeoutProp);
         }
      }

      if ("TRANSACTION_NONE".equalsIgnoreCase(desDefaultIsolationLevel)) {
         this.desiredDefaultIsolationLevel = 0;
      } else if ("TRANSACTION_READ_COMMITTED".equalsIgnoreCase(desDefaultIsolationLevel)) {
         this.desiredDefaultIsolationLevel = 2;
      } else if ("TRANSACTION_READ_UNCOMMITTED".equalsIgnoreCase(desDefaultIsolationLevel)) {
         this.desiredDefaultIsolationLevel = 1;
      } else if ("TRANSACTION_REPEATABLE_READ".equalsIgnoreCase(desDefaultIsolationLevel)) {
         this.desiredDefaultIsolationLevel = 4;
      } else if ("TRANSACTION_SERIALIZABLE".equalsIgnoreCase(desDefaultIsolationLevel)) {
         this.desiredDefaultIsolationLevel = 8;
      }

   }

   protected void getCommandLineProps() {
      if (!this.gotCommandlineProps) {
         this.gotCommandlineProps = true;
         if (System.getProperty("weblogic.jdbc.ignoreConnectionInitializationFailure") != null) {
            this.ignore_init_fails = true;
         }

         String endLocalTxOnXaConWithCommitProp = System.getProperty("weblogic.datasource.endLocalTxOnXaConWithCommit");
         if ("true".equalsIgnoreCase(endLocalTxOnXaConWithCommitProp)) {
            this.endLocalTxOnXaConWithCommit = true;
         }

         String endLocalTxOnNonXaConWithCommitProp = System.getProperty("weblogic.datasource.endLocalTxOnNonXaConWithCommit");
         if ("true".equalsIgnoreCase(endLocalTxOnNonXaConWithCommitProp)) {
            this.endLocalTxOnNonXaConWithCommit = true;
         }

         String endLocalTxOnNonXaConWithRollbackProp = System.getProperty("weblogic.datasource.endLocalTxOnNonXaConWithRollback");
         if ("true".equalsIgnoreCase(endLocalTxOnNonXaConWithRollbackProp)) {
            this.endLocalTxOnNonXaConWithRollback = true;
            this.endLocalTxOnNonXaConWithCommit = false;
         }

      }
   }

   private String checkTestQuery(JDBCConnectionPool pool, String query) {
      if (query != null && !query.trim().equals("")) {
         if (query.indexOf("SQL ") == 0) {
            query = query.substring(4);
         } else {
            query = "select count(*) from " + query;
         }

         return query;
      } else {
         if (pool.getTestOnReserve() || pool.getTestSeconds() > 0) {
            JDBCLogger.logWarnNoTestTable(pool.getName());
         }

         return null;
      }
   }

   public final void findVendorMethod(ConnectionEnv cc) {
      if (!this.finishFind) {
         this.finishFind = true;
         if (cc != null && cc.conn != null && cc.conn.jconn != null) {
            Class ocCls;
            Class ocTransactionState;
            Method mysqlCls;
            Method tsvalueof;
            try {
               ocCls = null;
               if (cc.conn.jconn instanceof Wrapper) {
                  ocCls = ((Wrapper)cc.conn.jconn).getVendorObj().getClass();
               } else {
                  ocCls = cc.conn.jconn.getClass();
               }

               ocTransactionState = Class.forName("java.sql.Connection");
               if (ocTransactionState != null & ocTransactionState.isAssignableFrom(ocCls)) {
                  mysqlCls = ocCls.getMethod("abort", Executor.class);
                  if (mysqlCls != null && !ocTransactionState.equals(mysqlCls.getDeclaringClass())) {
                     this.abort41 = ocTransactionState.getMethod("abort", Executor.class);
                     if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                        JdbcDebug.JDBCCONN.debug("found JDBC 4.1 abort method on connection " + cc.conn.jconn);
                     }
                  } else if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                     JdbcDebug.JDBCCONN.debug("JDBC 4.1 abort method not found on vendor connection " + cc.conn.jconn);
                  }

                  Method vendor43BeginRequest = ocCls.getMethod("beginRequest");
                  if (vendor43BeginRequest != null) {
                     this.beginRequest43 = vendor43BeginRequest;
                     if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                        JdbcDebug.JDBCCONN.debug("found JDBC 4.3 beginRequest method on connection " + cc.conn.jconn);
                     }
                  } else if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                     JdbcDebug.JDBCCONN.debug("JDBC 4.3 beginRequest method not found on vendor connection " + cc.conn.jconn);
                  }

                  tsvalueof = ocCls.getMethod("endRequest");
                  if (tsvalueof != null) {
                     this.endRequest43 = tsvalueof;
                     if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                        JdbcDebug.JDBCCONN.debug("found JDBC 4.3 endRequest method on connection " + cc.conn.jconn);
                     }
                  } else if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                     JdbcDebug.JDBCCONN.debug("JDBC 4.3 endRequest method not found on vendor connection " + cc.conn.jconn);
                  }
               }
            } catch (Exception var26) {
               if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                  JdbcDebug.JDBCCONN.debug("JDBC 4.1 abort method not found on connection " + cc.conn.jconn);
               }
            }

            Class sybCls3;
            if (cc.getVendorId() == 0 || cc.getVendorId() == 100) {
               ClassLoader jcl;
               try {
                  ocCls = null;
                  ocTransactionState = null;
                  sybCls3 = cc.conn.jconn.getClass();
                  jcl = sybCls3.getClassLoader();
                  if (jcl != null) {
                     ocCls = jcl.loadClass("oracle.jdbc.internal.OracleConnection");
                     ocTransactionState = jcl.loadClass("oracle.jdbc.internal.OracleConnection$TransactionState");
                  } else {
                     ocCls = Class.forName("oracle.jdbc.internal.OracleConnection", true, (ClassLoader)null);
                     ocTransactionState = Class.forName("oracle.jdbc.internal.OracleConnection$TransactionState");
                  }

                  if (ocCls != null && ocCls.isAssignableFrom(sybCls3)) {
                     try {
                        this.abort = ocCls.getMethod("abort", (Class[])null);
                        this.hasAbortMethod = true;
                        if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                           JdbcDebug.JDBCCONN.debug("found Oracle proprietary abort method '" + this.abort.getName() + "' on connection " + cc.conn.jconn);
                        }
                     } catch (Throwable var23) {
                     }

                     try {
                        this.oracleAttachServerConnection = ocCls.getMethod("attachServerConnection", (Class[])null);
                     } catch (Throwable var22) {
                     }

                     try {
                        this.oracleDetachServerConnection = ocCls.getMethod("detachServerConnection", String.class);
                     } catch (Throwable var21) {
                     }

                     try {
                        this.oraclePhysicalConnectionWithin = ocCls.getMethod("physicalConnectionWithin", (Class[])null);
                     } catch (Throwable var20) {
                     }

                     try {
                        this.oracleIsUsable = ocCls.getMethod("isUsable", (Class[])null);
                     } catch (Throwable var19) {
                     }

                     try {
                        this.getServerSessionInfo = ocCls.getMethod("getServerSessionInfo", (Class[])null);
                     } catch (Throwable var18) {
                     }

                     try {
                        this.oracleGetTransactionState = ocCls.getMethod("getTransactionState", (Class[])null);
                        tsvalueof = ocTransactionState.getMethod("valueOf", String.class);
                        this.oracleTransactionStateTransactionStarted = tsvalueof.invoke((Object)null, "TRANSACTION_STARTED");
                     } catch (Throwable var17) {
                     }
                  }
               } catch (Throwable var24) {
               }

               ClassLoader jcl;
               try {
                  ocCls = null;
                  ocTransactionState = cc.conn.jconn.getClass();
                  jcl = ocTransactionState.getClassLoader();
                  if (jcl != null) {
                     ocCls = jcl.loadClass("oracle.jdbc.OracleConnection");
                  } else {
                     ocCls = Class.forName("oracle.jdbc.OracleConnection", true, (ClassLoader)null);
                  }

                  if (ocCls.isAssignableFrom(ocTransactionState)) {
                     try {
                        this.pingDatabase = ocCls.getMethod("pingDatabase", Integer.TYPE);
                        this.pingDatabaseOk = (Integer)ocCls.getField("DATABASE_OK").get((Object)null);
                        this.hasPingDatabaseMethod = this.pingDatabase != null;
                        if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                           JdbcDebug.JDBCCONN.debug("find pingDatabase method on connection " + cc.conn.jconn);
                        }
                     } catch (Throwable var15) {
                        this.pingDatabase = null;
                        this.hasPingDatabaseMethod = false;
                     }

                     try {
                        this.oracleBeginRequest = ocCls.getMethod("beginRequest", (Class[])null);
                     } catch (Throwable var14) {
                        this.oracleBeginRequest = null;
                     }

                     try {
                        this.oracleProxyConnectionClose = ocCls.getMethod("close", Integer.TYPE);
                        this.hasOracleProxyConnectionCloseMethod = this.oracleProxyConnectionClose != null;
                        this.proxySession = (Integer)ocCls.getField("PROXY_SESSION").get((Object)null);
                        if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                           JdbcDebug.JDBCCONN.debug("find oracleProxyConnectionClose method on connection " + cc.conn.jconn);
                        }
                     } catch (Throwable var13) {
                        this.oracleProxyConnectionClose = null;
                        this.hasOracleProxyConnectionCloseMethod = false;
                     }

                     try {
                        this.oracleOpenProxySession = ocCls.getMethod("openProxySession", Integer.TYPE, Class.forName("java.util.Properties"));
                        this.hasOracleOpenProxySession = this.oracleOpenProxySession != null;
                        this.proxyUserName = (String)ocCls.getField("PROXY_USER_NAME").get((Object)null);
                        this.proxyUserPassword = (String)ocCls.getField("PROXY_USER_PASSWORD").get((Object)null);
                        this.proxyTypeUserName = (Integer)ocCls.getField("PROXYTYPE_USER_NAME").get((Object)null);
                        if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                           JdbcDebug.JDBCCONN.debug("find oracleOpenProxySession method on connection " + cc.conn.jconn);
                        }
                     } catch (Throwable var12) {
                        this.oracleOpenProxySession = null;
                        this.hasOracleOpenProxySession = false;
                     }
                  }
               } catch (Throwable var16) {
               }

               try {
                  ocCls = null;
                  if (cc.conn.jconn instanceof XAConnection) {
                     Connection rmConn = ((XAConnection)cc.conn.jconn).getConnection();
                     sybCls3 = rmConn.getClass();
                     jcl = sybCls3.getClassLoader();
                     if (jcl != null) {
                        ocCls = jcl.loadClass("oracle.jdbc.replay.driver.LogicalConnectionImpl");
                     } else {
                        ocCls = Class.forName("oracle.jdbc.replay.driver.LogicalConnectionImpl", true, (ClassLoader)null);
                     }

                     if (ocCls != null && ocCls.isAssignableFrom(sybCls3)) {
                        this.hasSetProxyObject = true;
                     }
                  } else {
                     ocTransactionState = cc.conn.jconn.getClass();
                     jcl = ocTransactionState.getClassLoader();
                     if (jcl != null) {
                        ocCls = jcl.loadClass("oracle.jdbc.replay.driver.TxnReplayableConnection");
                     } else {
                        ocCls = Class.forName("oracle.jdbc.replay.driver.TxnReplayableConnection", true, (ClassLoader)null);
                     }

                     if (ocCls != null && ocCls.isAssignableFrom(ocTransactionState)) {
                        this.hasSetProxyObject = true;
                     }
                  }
               } catch (Throwable var11) {
               }
            }

            ClassLoader jcl;
            if (cc.getVendorId() == 111 || cc.getVendorId() == 17) {
               try {
                  ocCls = cc.conn.jconn.getClass();
                  jcl = ocCls.getClassLoader();
                  mysqlCls = null;
                  if (jcl != null) {
                     sybCls3 = jcl.loadClass("com.mysql.jdbc.Connection");
                  } else {
                     sybCls3 = Class.forName("com.mysql.jdbc.Connection", true, (ClassLoader)null);
                  }

                  if (sybCls3 != null && sybCls3.isAssignableFrom(ocCls)) {
                     this.abort = sybCls3.getMethod("abortInternal", (Class[])null);
                     if (this.abort != null) {
                        this.hasAbortMethod = true;
                        if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                           JdbcDebug.JDBCCONN.debug("found MySQL proprietary abort method '" + this.abort.getName() + "' on connection " + cc.conn.jconn);
                        }
                     }
                  }
               } catch (Throwable var10) {
               }
            }

            if (cc.getVendorId() == 102 || cc.getVendorId() == 4) {
               try {
                  ocCls = cc.conn.jconn.getClass();
                  jcl = ocCls.getClassLoader();
                  sybCls3 = null;
                  Class sybCls4 = null;

                  try {
                     if (jcl != null) {
                        sybCls4 = jcl.loadClass("com.sybase.jdbc4.jdbc.SybConnection");
                     } else {
                        sybCls4 = DataSourceUtil.loadDriverClass("com.sybase.jdbc4.jdbc.SybConnection", (ClassLoader)null);
                     }
                  } catch (Throwable var9) {
                  }

                  try {
                     if (jcl != null) {
                        sybCls3 = jcl.loadClass("com.sybase.jdbc3.jdbc.SybConnection");
                     } else {
                        sybCls3 = DataSourceUtil.loadDriverClass("com.sybase.jdbc3.jdbc.SybConnection", (ClassLoader)null);
                     }
                  } catch (Throwable var8) {
                  }

                  if (sybCls4 != null && sybCls4.isAssignableFrom(ocCls)) {
                     this.abort = sybCls4.getMethod("markDead", (Class[])null);
                     if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                        JdbcDebug.JDBCCONN.debug("found Sybase jconn4 proprietary abort method '" + this.abort.getName() + "' on connection " + cc.conn.jconn);
                     }

                     this.hasAbortMethod = true;
                  } else if (sybCls3 != null && sybCls3.isAssignableFrom(ocCls)) {
                     this.abort = sybCls3.getMethod("markDead", (Class[])null);
                     this.hasAbortMethod = true;
                     if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                        JdbcDebug.JDBCCONN.debug("found Sybase jconn3 proprietary abort method '" + this.abort.getName() + "' on connection " + cc.conn.jconn);
                     }
                  }
               } catch (Throwable var25) {
               }
            }

            if (cc.getVendorId() == 8 || cc.getVendorId() == 104 || cc.getVendorId() == 9 || cc.getVendorId() == 112 || cc.getVendorId() == 10 || cc.getVendorId() == 106 || cc.getVendorId() == 16 || cc.getVendorId() == 113 || cc.getVendorId() == 11 || cc.getVendorId() == 107) {
               try {
                  ocCls = Class.forName("java.sql.Connection");
                  ocTransactionState = cc.conn.jconn.getClass();
                  if (ocTransactionState != null && ocCls.isAssignableFrom(ocTransactionState)) {
                     this.abort = ocTransactionState.getMethod("abortConnection", (Class[])null);
                     if (this.abort != null) {
                        this.hasAbortMethod = true;
                        if (JdbcDebug.JDBCCONN.isDebugEnabled()) {
                           JdbcDebug.JDBCCONN.debug("found datadirect proprietary abort method '" + this.abort.getName() + "' on connection " + cc.conn.jconn);
                        }
                     }
                  }
               } catch (Throwable var7) {
               }
            }

         }
      }
   }

   protected boolean getSupportIsValid(Connection con) {
      if (!this.weKnowWhetherIsValidIsSupported) {
         try {
            con.isValid(0);
            this.isValidIsSupported = true;
         } catch (Throwable var3) {
         }
      }

      this.weKnowWhetherIsValidIsSupported = true;
      return this.isValidIsSupported;
   }

   protected void acquireCreateRequestPermit() throws ResourceException {
      if (this.createRequestsPermits != null) {
         try {
            if (!this.createRequestsPermits.tryAcquire((long)this.concurrentCreateRequestsTimeoutSeconds, TimeUnit.SECONDS)) {
               throw new ResourceException("Unable to acquire make connection permit; permits=" + this.maxConcurrentCreateRequests + ", timeout=" + this.concurrentCreateRequestsTimeoutSeconds + ", waitingThreads=" + this.createRequestsPermits.getQueueLength());
            }
         } catch (InterruptedException var2) {
            throw new ResourceException("Unable to acquire make connection permit; permits=" + this.maxConcurrentCreateRequests + ", timeout=" + this.concurrentCreateRequestsTimeoutSeconds + ", waitingThreads=" + this.createRequestsPermits.getQueueLength(), var2);
         }
      }

   }

   protected void releaseCreateRequestPermit() throws ResourceException {
      if (this.createRequestsPermits != null) {
         this.createRequestsPermits.release();
      }

   }

   static {
      try {
         System.setProperty("oracle.jdbc.noImplicitBeginRequest", "true");
      } catch (Exception var1) {
      }

   }
}
